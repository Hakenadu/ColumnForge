package com.github.hakenadu.columnforge.service.transformation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakenadu.columnforge.model.ComplexTransformationRequestData;
import com.github.hakenadu.columnforge.model.TransformationRequest;
import com.github.hakenadu.columnforge.model.TransformationRequestData;
import com.github.hakenadu.columnforge.service.completion.CompletionService;

@Service
public class ComplexTransformationService implements TransformationService {

	@Autowired
	private CompletionService chatCompletionService;

	@Autowired
	private QueryResolver queryResolver;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public String getTransformation(final TransformationRequest request) {
		final ComplexTransformationRequestData requestData = (ComplexTransformationRequestData) request.getData();

		final ComplexTransformationRequestData result = new ComplexTransformationRequestData();
		result.setHeader(Stream.concat(requestData.getHeader().stream(), Stream.of("forgedColumn")).toList());
		result.setRecords(requestData.getRecords().stream()
				.map(inputRecord -> processRecord(inputRecord, requestData.getHeader(), request)).toList());
		try {
			return objectMapper.writeValueAsString(result);
		} catch (final JsonProcessingException jsonProcessingException) {
			throw new IllegalStateException("error writing complex result", jsonProcessingException);
		}
	}

	private List<String> processRecord(final List<String> inputRecord, final List<String> header,
			final TransformationRequest request) {
		final Map<String, String> systemContextVariables = new LinkedHashMap<>();
		for (int headerIndex = 0; headerIndex < header.size(); headerIndex++) {
			systemContextVariables.put(header.get(headerIndex), inputRecord.get(headerIndex));
		}

		final String query = queryResolver.resolve(request.getQuery(), systemContextVariables);

		final String completion = chatCompletionService.getCompletion(request.getModel(), query, request.getApiKey());

		return Stream.concat(inputRecord.stream(), Stream.of(completion)).toList();
	}

	@Override
	public boolean supportsData(final TransformationRequestData requestData) {
		return requestData instanceof ComplexTransformationRequestData;
	}
}

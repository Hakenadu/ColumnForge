package com.github.hakenadu.columnforge.service.transformation;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hakenadu.columnforge.model.CsvTransformationRequestData;
import com.github.hakenadu.columnforge.model.TransformationRequest;
import com.github.hakenadu.columnforge.model.TransformationRequestData;
import com.github.hakenadu.columnforge.service.completion.CompletionService;

@Service
public class CsvTransformationService implements TransformationService {

	@Autowired
	private CompletionService chatCompletionService;

	@Autowired
	private QueryResolver queryResolver;

	@Override
	public String getTransformation(final TransformationRequest request) {
		final CsvTransformationRequestData requestData = (CsvTransformationRequestData) request.getData();

		try (final CSVParser parser = new CSVParser(new StringReader(requestData.getContent()),
				CSVFormat.DEFAULT.builder().setDelimiter(requestData.getDelimiter()).setHeader()
						.setSkipHeaderRecord(true).setTrim(true).build());
				final StringWriter writer = new StringWriter();
				final CSVPrinter printer = new CSVPrinter(writer,
						CSVFormat.DEFAULT.builder().setDelimiter(requestData.getDelimiter())
								.setHeader(Stream
										.concat(parser.getHeaderMap().keySet().stream(), Stream.of("forgedColumn"))
										.toArray(String[]::new))
								.setTrim(true).build())) {

			parser.stream().forEach(parserRecord -> processRecord(parserRecord, parser, printer, request));

			return writer.toString();
		} catch (final IOException ioException) {
			throw new IllegalStateException("failed to process request " + requestData.getContent(), ioException);
		}

	}

	private void processRecord(final CSVRecord parserRecord, final CSVParser parser, final CSVPrinter printer,
			final TransformationRequest request) {
		final Map<String, String> systemContextVariables = new LinkedHashMap<>();
		for (final String header : parser.getHeaderNames()) {
			systemContextVariables.put(header, parserRecord.get(header));
		}

		final String query = queryResolver.resolve(request.getQuery(), systemContextVariables);

		final String completion = chatCompletionService.getCompletion(request.getModel(), query, request.getApiKey());

		final List<String> values = Arrays.stream(parserRecord.values())
				.collect(Collectors.toCollection(ArrayList::new));
		values.add(completion);
		try {
			printer.printRecord(values);
		} catch (final IOException ioException) {
			throw new IllegalStateException("failed to process query " + query, ioException);
		}
	}

	@Override
	public boolean supportsData(final TransformationRequestData requestData) {
		return requestData instanceof CsvTransformationRequestData;
	}
}

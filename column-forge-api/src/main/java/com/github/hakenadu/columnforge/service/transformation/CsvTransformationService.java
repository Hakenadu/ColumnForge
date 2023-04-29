package com.github.hakenadu.columnforge.service.transformation;

import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hakenadu.columnforge.model.CsvTransformationRequestData;
import com.github.hakenadu.columnforge.model.TransformationRequest;
import com.github.hakenadu.columnforge.model.TransformationRequestData;
import com.github.hakenadu.columnforge.service.completion.CompletionService;

import reactor.core.publisher.Flux;

@Service
public class CsvTransformationService implements TransformationService {

	private static final CSVFormat INPUT_FORMAT = CSVFormat.DEFAULT.builder().setSkipHeaderRecord(false).build();

	@Autowired
	private CompletionService chatCompletionService;

	@Override
	public Flux<String> streamTransformation(TransformationRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransformation(TransformationRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsData(final TransformationRequestData requestData) {
		return requestData instanceof CsvTransformationRequestData;
	}
}

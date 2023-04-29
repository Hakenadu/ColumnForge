package com.github.hakenadu.columnforge.service.transformation;

import com.github.hakenadu.columnforge.model.TransformationRequest;
import com.github.hakenadu.columnforge.model.TransformationRequestData;

import reactor.core.publisher.Flux;

public interface TransformationService {

	Flux<String> streamTransformation(TransformationRequest request);

	String getTransformation(TransformationRequest request);

	boolean supportsData(TransformationRequestData requestData);
}

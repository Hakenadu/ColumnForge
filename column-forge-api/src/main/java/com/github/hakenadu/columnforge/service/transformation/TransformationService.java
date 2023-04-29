package com.github.hakenadu.columnforge.service.transformation;

import com.github.hakenadu.columnforge.model.TransformationRequest;
import com.github.hakenadu.columnforge.model.TransformationRequestData;

public interface TransformationService {

	String getTransformation(TransformationRequest request);

	boolean supportsData(TransformationRequestData requestData);
}

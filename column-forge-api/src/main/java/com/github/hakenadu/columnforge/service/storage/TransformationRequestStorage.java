package com.github.hakenadu.columnforge.service.storage;

import java.util.UUID;

import com.github.hakenadu.columnforge.model.TransformationRequest;

public interface TransformationRequestStorage {

	UUID storeRequest(TransformationRequest request);

	TransformationRequest getRequest(UUID id);
}

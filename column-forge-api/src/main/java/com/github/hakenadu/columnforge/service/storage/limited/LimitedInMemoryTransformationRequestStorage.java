package com.github.hakenadu.columnforge.service.storage.limited;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.hakenadu.columnforge.model.TransformationRequest;
import com.github.hakenadu.columnforge.service.storage.TransformationRequestStorage;

@Service
public class LimitedInMemoryTransformationRequestStorage implements TransformationRequestStorage {

	private final ConcurrentMaxLifetimeMap<UUID, TransformationRequest> requests = new ConcurrentMaxLifetimeMap<>();

	@Override
	public UUID storeRequest(final TransformationRequest request) {
		final UUID uuid = UUID.randomUUID();
		requests.put(uuid, request, Duration.ofMinutes(5).toMillis());
		return uuid;
	}

	@Override
	public TransformationRequest getRequest(final UUID id) {
		// TODO Auto-generated method stub
		return null;
	}
}

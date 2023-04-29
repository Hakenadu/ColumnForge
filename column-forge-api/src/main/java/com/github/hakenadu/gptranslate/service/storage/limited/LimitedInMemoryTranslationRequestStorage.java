package com.github.hakenadu.gptranslate.service.storage.limited;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.hakenadu.gptranslate.model.TranslationRequest;
import com.github.hakenadu.gptranslate.service.storage.TranslationRequestStorage;

@Service
public class LimitedInMemoryTranslationRequestStorage implements TranslationRequestStorage {

	private final ConcurrentMaxLifetimeMap<UUID, TranslationRequest> translationRequests = new ConcurrentMaxLifetimeMap<>();

	@Override
	public UUID storeTranslationRequest(final TranslationRequest translationRequest) {
		final UUID uuid = UUID.randomUUID();
		translationRequests.put(uuid, translationRequest, Duration.ofMinutes(5).toMillis());
		return uuid;
	}

	@Override
	public TranslationRequest getTranslationRequest(final UUID id) {
		// TODO Auto-generated method stub
		return null;
	}
}

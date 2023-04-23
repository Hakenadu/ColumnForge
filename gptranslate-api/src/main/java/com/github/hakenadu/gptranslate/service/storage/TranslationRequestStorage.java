package com.github.hakenadu.gptranslate.service.storage;

import java.util.UUID;

import com.github.hakenadu.gptranslate.model.TranslationRequest;

public interface TranslationRequestStorage {

	UUID storeTranslationRequest(TranslationRequest translationRequest);

	TranslationRequest getTranslationRequest(UUID id);
}

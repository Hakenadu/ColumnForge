package com.github.hakenadu.gptranslate.service.translation;

import com.github.hakenadu.gptranslate.model.TranslationRequest;
import com.github.hakenadu.gptranslate.model.TranslationRequestData;

import reactor.core.publisher.Flux;

public interface TranslationService {

	Flux<String> streamTranslations(TranslationRequest request);

	String getTranslation(TranslationRequest request);

	boolean supportsData(TranslationRequestData translationRequestData);
}

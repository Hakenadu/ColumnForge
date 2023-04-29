package com.github.hakenadu.gptranslate.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakenadu.gptranslate.model.TranslationEventData;
import com.github.hakenadu.gptranslate.model.TranslationEventData.TranslationEventType;
import com.github.hakenadu.gptranslate.model.TranslationRequest;
import com.github.hakenadu.gptranslate.service.storage.TranslationRequestStorage;
import com.github.hakenadu.gptranslate.service.translation.TranslationService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/translations")
public class TranslationsController {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TranslationRequestStorage translationRequestStorage;

	@Autowired
	private List<TranslationService> translationServices;

	@PostMapping
	public String createTranslation(final @RequestBody TranslationRequest request) {
		if (Boolean.TRUE.equals(request.getStreaming())) {
			final UUID uuid = this.translationRequestStorage.storeTranslationRequest(request);
			return uuid.toString();
		}
		return translationServices.stream().filter(ts -> ts.supportsData(request.getData())).findAny().orElseThrow()
				.getTranslation(request);
	}

	@GetMapping
	@RequestMapping("/{translationId}")
	public Flux<ServerSentEvent<String>> getTranslation(final @PathVariable UUID translationId) {
		final TranslationRequest request = translationRequestStorage.getTranslationRequest(translationId);

		return translationServices.stream().filter(ts -> ts.supportsData(request.getData())).findAny().orElseThrow()
				.streamTranslations(request)
				.map(record -> ServerSentEvent.<String>builder().event("message")
						.data(new TranslationEventData<>(TranslationEventType.TOKEN, record).toString(objectMapper))
						.build())
				.concatWith(Flux.just(ServerSentEvent.<String>builder().event("message")
						.data(new TranslationEventData<>(TranslationEventType.TOKEN, null).toString(objectMapper))
						.build()));
	}
}

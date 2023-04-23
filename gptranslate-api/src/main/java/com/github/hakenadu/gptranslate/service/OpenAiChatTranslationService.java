package com.github.hakenadu.gptranslate.service;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class OpenAiChatTranslationService implements TranslationService {

	@Autowired
	private OpenAiServiceProvider openAiServiceProvider;

	@Override
	public Stream<String> getCompletion(final String model, final Supplier<String> textPublisher) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Flux<String> streamCompletion(final String model, final String text) {
		throw new UnsupportedOperationException();
	}

}

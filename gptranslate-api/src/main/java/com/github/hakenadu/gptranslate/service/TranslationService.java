package com.github.hakenadu.gptranslate.service;

import java.util.function.Supplier;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;

public interface TranslationService {

	Stream<String> getCompletion(String model, Supplier<String> textPublisher);

	Flux<String> streamCompletion(String model, String text);
}

package com.github.hakenadu.gptranslate.service.completion;

import reactor.core.publisher.Flux;

public interface CompletionService {

	String getCompletion(String model, String systemContext, String text);

	Flux<String> streamCompletion(String model, String systemContext, String text);
}

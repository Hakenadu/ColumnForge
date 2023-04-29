package com.github.hakenadu.columnforge.service.completion;

import reactor.core.publisher.Flux;

public interface CompletionService {

	String getCompletion(String model, String query);

	Flux<String> streamCompletion(String model, String query);
}

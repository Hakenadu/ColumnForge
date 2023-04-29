package com.github.hakenadu.columnforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakenadu.columnforge.model.CompletionEventData;
import com.github.hakenadu.columnforge.model.CompletionEventData.TransformationEventType;
import com.github.hakenadu.columnforge.service.completion.CompletionService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/completions")
public class CompletionsController {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CompletionService completionService;

	@GetMapping
	public Flux<ServerSentEvent<String>> getCompletion(final @RequestParam String model,
			final @RequestParam String systemContext, final @RequestParam String text) {
		return completionService.streamCompletion(model, systemContext, text)
				.map(token -> ServerSentEvent.<String>builder().event("message")
						.data(new CompletionEventData<>(TransformationEventType.TOKEN, token)
								.toString(objectMapper))
						.build())
				.concatWith(Flux.just(ServerSentEvent.<String>builder().event("message")
						.data(new CompletionEventData<>(TransformationEventType.CLOSE, null).toString(objectMapper))
						.build()));
	}
}

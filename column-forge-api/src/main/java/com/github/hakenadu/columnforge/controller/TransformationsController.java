package com.github.hakenadu.columnforge.controller;

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
import com.github.hakenadu.columnforge.model.CompletionEventData;
import com.github.hakenadu.columnforge.model.TransformationRequest;
import com.github.hakenadu.columnforge.model.CompletionEventData.TransformationEventType;
import com.github.hakenadu.columnforge.service.storage.TransformationRequestStorage;
import com.github.hakenadu.columnforge.service.transformation.TransformationService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/transformations")
public class TransformationsController {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TransformationRequestStorage transformationRequestStorage;

	@Autowired
	private List<TransformationService> transformationServices;

	@PostMapping
	public String createTransformation(final @RequestBody TransformationRequest request) {
		if (Boolean.TRUE.equals(request.getStreaming())) {
			final UUID uuid = this.transformationRequestStorage.storeRequest(request);
			return uuid.toString();
		}
		return transformationServices.stream().filter(ts -> ts.supportsData(request.getData())).findAny().orElseThrow()
				.getTransformation(request);
	}

	@GetMapping
	@RequestMapping("/{transformationId}")
	public Flux<ServerSentEvent<String>> getTransformation(final @PathVariable UUID transformationId) {
		final TransformationRequest request = transformationRequestStorage.getRequest(transformationId);

		return transformationServices.stream().filter(ts -> ts.supportsData(request.getData())).findAny().orElseThrow()
				.streamTransformation(request)
				.map(record -> ServerSentEvent.<String>builder().event("message")
						.data(new CompletionEventData<>(TransformationEventType.TOKEN, record).toString(objectMapper))
						.build())
				.concatWith(Flux.just(ServerSentEvent.<String>builder().event("message")
						.data(new CompletionEventData<>(TransformationEventType.TOKEN, null).toString(objectMapper))
						.build()));
	}
}

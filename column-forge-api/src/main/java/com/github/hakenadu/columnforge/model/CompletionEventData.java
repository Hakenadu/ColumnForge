package com.github.hakenadu.columnforge.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class CompletionEventData<T> {

	public static enum TransformationEventType {
		TOKEN, CLOSE
	}

	private final TransformationEventType type;
	private final T data;

	public CompletionEventData(final TransformationEventType type, final T data) {
		this.type = type;
		this.data = data;
	}

	public TransformationEventType getType() {
		return type;
	}

	public T getData() {
		return data;
	}

	public String toString(final ObjectMapper objectMapper) {
		try {
			return objectMapper.writeValueAsString(new CompletionEventData<>(type, data));
		} catch (final JsonProcessingException jsonProcessingException) {
			throw new IllegalStateException("could not serialize event data", jsonProcessingException);
		}
	}
}

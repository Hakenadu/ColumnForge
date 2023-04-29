package com.github.hakenadu.gptranslate.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class TranslationEventData<T> {

	public static enum TranslationEventType {
		TOKEN, CLOSE
	}

	private final TranslationEventType type;
	private final T data;

	public TranslationEventData(final TranslationEventType type, final T data) {
		this.type = type;
		this.data = data;
	}

	public TranslationEventType getType() {
		return type;
	}

	public T getData() {
		return data;
	}

	public String toString(final ObjectMapper objectMapper) {
		try {
			return objectMapper.writeValueAsString(new TranslationEventData<>(type, data));
		} catch (final JsonProcessingException jsonProcessingException) {
			throw new IllegalStateException("could not serialize event data", jsonProcessingException);
		}
	}
}

package com.github.hakenadu.gptranslate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakenadu.gptranslate.openai.api.OpenAiApi;
import com.github.hakenadu.gptranslate.openai.service.ApiClient;

@Configuration
public class OpenAiConfig {

	@Bean
	public OpenAiApi openAiApi(final ApiClient apiClient) {
		return new OpenAiApi(apiClient);
	}

	@Bean
	public ApiClient apiClient(final WebClient webClient, final ObjectMapper objectMapper,
			final @Value("${gptranslate.openai.api-key}") String openAiApiKey) {
		final ApiClient apiClient = new ApiClient(webClient, objectMapper, ApiClient.createDefaultDateFormat());
		apiClient.setBearerToken(openAiApiKey);
		return apiClient;
	}

	@Bean
	public WebClient webClient(final ObjectMapper objectMapper) {
		return ApiClient.buildWebClient(objectMapper);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return ApiClient.createDefaultObjectMapper(ApiClient.createDefaultDateFormat());
	}
}

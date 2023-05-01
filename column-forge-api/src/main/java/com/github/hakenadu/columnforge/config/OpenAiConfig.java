package com.github.hakenadu.columnforge.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.service.OpenAiService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Configuration
public class OpenAiConfig {

	@Bean
	public OpenAiService apiClient(final @Value("${column-forge.openai.api-key:#{null}}") String openAiApiKey) {
		return createOpenAiService(OpenAiService.defaultObjectMapper(), openAiApiKey);
	}

	public static OpenAiService createOpenAiService(final ObjectMapper objectMapper, final String openAiApiKey) {
		final OkHttpClient client = OpenAiService.defaultClient(openAiApiKey, Duration.ofSeconds(10L));
		final Retrofit retrofit = OpenAiService.defaultRetrofit(client, objectMapper);
		final OpenAiApi api = retrofit.create(OpenAiApi.class);
		return new OpenAiService(api, client.dispatcher().executorService());
	}
}

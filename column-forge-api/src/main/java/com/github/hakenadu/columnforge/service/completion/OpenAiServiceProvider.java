package com.github.hakenadu.columnforge.service.completion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakenadu.columnforge.config.OpenAiConfig;
import com.theokanning.openai.service.OpenAiService;

@Component
public class OpenAiServiceProvider {

	@Autowired
	private OpenAiService openAiService;

	@Autowired
	private ObjectMapper objectMapper;

	public OpenAiService getService(final String apiKey) {
		if (apiKey == null) {
			return openAiService;
		}
		return OpenAiConfig.createOpenAiService(objectMapper, apiKey);
	}
}

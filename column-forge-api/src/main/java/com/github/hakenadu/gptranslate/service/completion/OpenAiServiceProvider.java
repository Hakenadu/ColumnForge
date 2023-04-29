package com.github.hakenadu.gptranslate.service.completion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hakenadu.gptranslate.config.OpenAiConfig;
import com.github.hakenadu.gptranslate.filter.OpenAiApiKeyFilter;
import com.theokanning.openai.service.OpenAiService;

@Component
public class OpenAiServiceProvider {

	@Autowired
	private OpenAiService openAiService;

	@Autowired
	private ObjectMapper objectMapper;

	public OpenAiService getService() {
		final String apiKeyForCurrentRequest = OpenAiApiKeyFilter.getApiKey();
		if (apiKeyForCurrentRequest == null) {
			return openAiService;
		}
		return OpenAiConfig.createOpenAiService(objectMapper, apiKeyForCurrentRequest);
	}
}

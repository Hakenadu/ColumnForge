package com.github.hakenadu.columnforge.service.completion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;

import reactor.core.publisher.Flux;

@Service
public class OpenAiChatCompletionService implements CompletionService {

	@Autowired
	private OpenAiServiceProvider openAiServiceProvider;

	@Override
	public String getCompletion(final String model, final String context, final String query, final String apiKey) {
		final ChatCompletionRequest request = createRequest(model, context, query);
		return openAiServiceProvider.getService(apiKey).createChatCompletion(request).getChoices().get(0).getMessage()
				.getContent();
	}

	@Override
	public Flux<String> streamCompletion(final String model, final String context, final String query,
			final String apiKey) {
		final ChatCompletionRequest request = createRequest(model, context, query);
		return Flux.from(openAiServiceProvider.getService(apiKey).streamChatCompletion(request)
				.filter(chunk -> !chunk.getChoices().isEmpty()).map(chunk -> chunk.getChoices().get(0))
				.map(ChatCompletionChoice::getMessage).filter(message -> message.getContent() != null)
				.map(ChatMessage::getContent));
	}

	private ChatCompletionRequest createRequest(final String model, final String context, final String query) {
		return ChatCompletionRequest.builder().temperature(0D).model(model)
				.messages(List.of(new ChatMessage("system", context), new ChatMessage("user", query))).build();
	}

}

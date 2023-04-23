package com.github.hakenadu.gptranslate.openai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.hakenadu.gptranslate.openai.api.OpenAiApi;
import com.github.hakenadu.gptranslate.openai.model.ChatCompletionRequestMessage;
import com.github.hakenadu.gptranslate.openai.model.ChatCompletionResponseMessage;
import com.github.hakenadu.gptranslate.openai.model.ChatMessageRole;
import com.github.hakenadu.gptranslate.openai.model.CreateChatCompletionRequest;
import com.github.hakenadu.gptranslate.openai.model.CreateChatCompletionResponse;
import com.github.hakenadu.gptranslate.openai.model.CreateChatCompletionResponseChoicesInner;

@SpringBootTest
public class OpenAiApiTest {

	@Autowired
	private OpenAiApi openAiApi;

	@Test
	public void testCreateChatCompletion() {
		final CreateChatCompletionRequest request = new CreateChatCompletionRequest().model("gpt-3.5-turbo")
				.addMessagesItem(new ChatCompletionRequestMessage().role(ChatMessageRole.USER).content("Hallo"));

		final CreateChatCompletionResponse response = openAiApi.createChatCompletion(request).block();
		assertNotNull(response, "no response");
		assertNotNull(response.getChoices(), "null instead of choices");
		assertEquals(1, response.getChoices().size(), "unexpected choices count");

		final CreateChatCompletionResponseChoicesInner choice = response.getChoices().get(0);

		final ChatCompletionResponseMessage message = choice.getMessage();
		assertNotNull(message, "no message");
		assertNotNull(message.getContent(), "no message content");
	}
}

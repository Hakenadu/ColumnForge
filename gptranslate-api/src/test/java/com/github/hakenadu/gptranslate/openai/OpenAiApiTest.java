package com.github.hakenadu.gptranslate.openai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

@SpringBootTest
public class OpenAiApiTest {

	@Autowired
	private OpenAiService openAiService;

	@Test
	public void testCreateChatCompletion() {
		final ChatCompletionRequest request = ChatCompletionRequest.builder().model("gpt-3.5-turbo")
				.messages(List.of(new ChatMessage("user", "Hallo Welt"))).build();

		final ChatCompletionResult response = openAiService.createChatCompletion(request);
		assertNotNull(response, "no response");
		assertNotNull(response.getChoices(), "null instead of choices");
		assertEquals(1, response.getChoices().size(), "unexpected choices count");

		final ChatCompletionChoice choice = response.getChoices().get(0);

		final ChatMessage message = choice.getMessage();
		assertNotNull(message, "no message");
		assertNotNull(message.getContent(), "no message content");
	}
}

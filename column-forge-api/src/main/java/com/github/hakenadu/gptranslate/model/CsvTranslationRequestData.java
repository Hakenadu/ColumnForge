package com.github.hakenadu.gptranslate.model;

public class CsvTranslationRequestData extends TranslationRequestData {

	private String content;
	private String delimiter;

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(final String delimiter) {
		this.delimiter = delimiter;
	}

	@Override
	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}
}

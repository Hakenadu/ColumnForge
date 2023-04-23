package com.github.hakenadu.gptranslate.model;

public class TranslationRequest {

	private String model;
	private String systemScope;
	private Boolean streaming;
	private TranslationRequestData data;

	public String getModel() {
		return model;
	}

	public void setModel(final String model) {
		this.model = model;
	}

	public String getSystemScope() {
		return systemScope;
	}

	public void setSystemScope(final String systemScope) {
		this.systemScope = systemScope;
	}

	public TranslationRequestData getData() {
		return data;
	}

	public void setData(final TranslationRequestData data) {
		this.data = data;
	}

	public Boolean getStreaming() {
		return streaming;
	}

	public void setStreaming(final Boolean streaming) {
		this.streaming = streaming;
	}
}

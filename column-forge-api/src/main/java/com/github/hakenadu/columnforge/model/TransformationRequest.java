package com.github.hakenadu.columnforge.model;

public class TransformationRequest {

	private String model;
	private String systemScope;
	private Boolean streaming;
	private TransformationRequestData data;

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

	public TransformationRequestData getData() {
		return data;
	}

	public void setData(final TransformationRequestData data) {
		this.data = data;
	}

	public Boolean getStreaming() {
		return streaming;
	}

	public void setStreaming(final Boolean streaming) {
		this.streaming = streaming;
	}
}

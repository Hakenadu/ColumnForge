package com.github.hakenadu.columnforge.model;

public class TransformationRequest {

	private String model;
	private String apiKey;
	private String query;
	private String context;
	private TransformationRequestData data;

	public String getModel() {
		return model;
	}

	public void setModel(final String model) {
		this.model = model;
	}

	public TransformationRequestData getData() {
		return data;
	}

	public void setData(final TransformationRequestData data) {
		this.data = data;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(final String query) {
		this.query = query;
	}

	public String getContext() {
		return context;
	}

	public void setContext(final String context) {
		this.context = context;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(final String apiKey) {
		this.apiKey = apiKey;
	}
}

package com.github.hakenadu.columnforge.model;

public class TransformationRequest {

	private String model;
	private String query;
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
}

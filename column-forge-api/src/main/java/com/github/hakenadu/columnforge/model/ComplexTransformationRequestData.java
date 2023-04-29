package com.github.hakenadu.columnforge.model;

import java.util.List;

public class ComplexTransformationRequestData extends TransformationRequestData {

	private List<String> header;
	private List<List<String>> records;

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<List<String>> getRecords() {
		return records;
	}

	public void setRecords(List<List<String>> records) {
		this.records = records;
	}
}

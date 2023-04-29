package com.github.hakenadu.columnforge.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = CsvTransformationRequestData.class, name = "csv") })
public abstract class TransformationRequestData {

	public abstract String getContent();
}

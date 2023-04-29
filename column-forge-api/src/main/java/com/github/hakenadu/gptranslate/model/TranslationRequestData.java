package com.github.hakenadu.gptranslate.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = CsvTranslationRequestData.class, name = "csv") })
public abstract class TranslationRequestData {

	public abstract String getContent();
}

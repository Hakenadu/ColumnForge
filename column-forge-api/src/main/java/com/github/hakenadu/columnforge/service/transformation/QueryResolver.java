package com.github.hakenadu.columnforge.service.transformation;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Component;

@Component
public class QueryResolver {

	public String resolve(final String systemContextPattern, final Map<String, String> variables) {
		// TODO Validation
		return new StringSubstitutor(variables).replace(systemContextPattern);
	}
}

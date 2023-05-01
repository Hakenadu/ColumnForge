package com.github.hakenadu.columnforge.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.hakenadu.columnforge.model.TransformationRequest;
import com.github.hakenadu.columnforge.service.transformation.TransformationService;

@RestController
@CrossOrigin
@RequestMapping("/v1/transformations")
public class TransformationsController {

	@Autowired
	private List<TransformationService> transformationServices;

	@Value("${column-forge.openai.api-key-whitelist}")
	private Set<String> apiKeyWhitelist;

	@PostMapping
	public String createTransformation(final @RequestBody TransformationRequest request) {
		if (request.getApiKey() != null && apiKeyWhitelist != null && !apiKeyWhitelist.contains(request.getApiKey())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		return transformationServices.stream().filter(ts -> ts.supportsData(request.getData())).findAny().orElseThrow()
				.getTransformation(request);
	}

}

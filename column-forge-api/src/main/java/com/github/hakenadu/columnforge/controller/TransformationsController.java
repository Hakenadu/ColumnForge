package com.github.hakenadu.columnforge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.hakenadu.columnforge.model.TransformationRequest;
import com.github.hakenadu.columnforge.service.transformation.TransformationService;

@RestController
@RequestMapping("/v1/transformations")
public class TransformationsController {

	@Autowired
	private List<TransformationService> transformationServices;

	@PostMapping
	public String createTransformation(final @RequestBody TransformationRequest request) {
		return transformationServices.stream().filter(ts -> ts.supportsData(request.getData())).findAny().orElseThrow()
				.getTransformation(request);
	}

}

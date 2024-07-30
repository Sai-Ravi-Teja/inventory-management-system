package com.ims.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ims.dto.ProductRequest;
import com.ims.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "Create and Save a Product")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created and Saved a Product", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ProductRequest.class)) }) })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String createProduct(@RequestBody ProductRequest productRequest) {

		productService.createProduct(productRequest);
		return "Product added to DB Successfully";
	}

}

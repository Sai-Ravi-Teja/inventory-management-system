package com.ims.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ims.dto.ProductRequest;
import com.ims.model.Product;
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

	@Operation(summary = "Create a product")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Product created successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ProductRequest.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String createProduct(@RequestBody ProductRequest productRequest) {

		return productService.createProduct(productRequest);
	}

	@Operation(summary = "Update a product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product updated successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public String updateProductById(@RequestBody Product product) {

		return productService.updateProductById(product);
	}

	@Operation(summary = "Get the list of all products")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returened list of all products", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/getAllProducts")
	@ResponseStatus(HttpStatus.OK)
	public List<Product> getAllProducts() {

		return productService.getAllProducts();
	}

	@Operation(summary = "Get the Count of products")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returened count of products", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/getAllProducts/Count")
	@ResponseStatus(HttpStatus.OK)
	public Integer getAllProductsCount() {

		return productService.getAllProductsCount();
	}
}

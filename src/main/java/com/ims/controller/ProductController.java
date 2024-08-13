package com.ims.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ims.common.InventoryConstants;
import com.ims.dto.ProductRequest;
import com.ims.model.Product;
import com.ims.service.ProductService;
import com.ims.util.ExportUtil;

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

	private final ExportUtil exportUtil;

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
			@ApiResponse(responseCode = "404", description = "Product not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public String updateProductById(@RequestBody ProductRequest productRequest) {

		return productService.updateProductById(productRequest);
	}

	@Operation(summary = "Get a product by Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Product retrived successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ProductRequest.class)) }),
			@ApiResponse(responseCode = "404", description = "Product not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductRequest getProductById(@PathVariable UUID id) {

		return productService.getProductById(id);
	}

	@Operation(summary = "Delete a product by Id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Product deleted successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ProductRequest.class)) }),
			@ApiResponse(responseCode = "404", description = "Product not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{id}")
	public String deleteProductById(@PathVariable UUID id) {

		return productService.deleteProductById(id);
	}

	@Operation(summary = "Get products by date range")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Products retrieved successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid date format", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/date-range")
	@ResponseStatus(HttpStatus.OK)
	public List<Product> getProductsByDateRange(@RequestParam("startDate") String startDateStr,
			@RequestParam("endDate") String endDateStr) {

		return productService.getProductsByDateRange(startDateStr, endDateStr);
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
	@GetMapping("/getAllProducts/count")
	@ResponseStatus(HttpStatus.OK)
	public long getAllProductsCount() {

		return productService.getAllProductsCount();
	}

	@Operation(summary = "Export the products in CSV format")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Exported to CSV Format", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/export/csv")
	public void exportProductsToCsv(HttpServletResponse response) throws IOException {

		String fileName = exportUtil.getFileName(InventoryConstants.CSV_FILE_FORMAT);
		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

		List<Product> products = productService.getAllProducts();

		try (PrintWriter writer = response.getWriter()) {
			exportUtil.writeProductsToCsv(writer, products);
		}
	}

	@Operation(summary = "Export the products in PDF format")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Exported to PDF Format", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/export/pdf")
	public void exportProductsToPdf(HttpServletResponse response) throws IOException {

		String fileName = exportUtil.getFileName(InventoryConstants.PDF_FILE_FORMAT);
		response.setContentType("application/pdf");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

		List<Product> products = productService.getAllProducts();

		exportUtil.writeProductsToPdf(response.getOutputStream(), products);
	}

}

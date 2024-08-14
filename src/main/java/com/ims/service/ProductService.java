package com.ims.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ims.common.InventoryConstants;
import com.ims.dto.ProductRequest;
import com.ims.exception.InvalidDateFormatException;
import com.ims.model.Product;
import com.ims.repository.ProductRepository;
import com.ims.util.DatabaseUtils;
import com.ims.util.DateTimeUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public String createProduct(ProductRequest productRequest) {

		Product product = new Product();
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		product.setDescription(productRequest.getDescription());

		productRepository.save(product);
		logger.info("Created product and saved in db with details : {}", product);
		return "Product created successfully";
	}

	public String updateProductById(ProductRequest productRequest) {

		Optional<Product> optionalDbProduct = productRepository.findById(productRequest.getId());

		if (!optionalDbProduct.isPresent()) {
			throw new EntityNotFoundException("Product not found with id: " + productRequest.getId());
		}

		Product dbProduct = optionalDbProduct.get();
		Product product = new Product(productRequest.getId(), productRequest.getName(), productRequest.getPrice(),
				productRequest.getDescription(), null, null);
		DatabaseUtils.updateNonNullProperties(product, dbProduct);
		productRepository.save(dbProduct);

		logger.info("Updated product in db with details : {}", dbProduct);
		return "Product updated successfully";
	}

	public ProductRequest getProductById(UUID id) {

		Optional<Product> optionalDbProduct = productRepository.findById(id);

		if (!optionalDbProduct.isPresent()) {
			throw new EntityNotFoundException("Product not found with id: " + id);
		}

		Product product = optionalDbProduct.get();
		return new ProductRequest(product.getId(), product.getName(), product.getPrice(), product.getDescription());
	}

	public String deleteProductById(UUID id) {

		Optional<Product> optionalDbProduct = productRepository.findById(id);

		if (!optionalDbProduct.isPresent()) {
			throw new EntityNotFoundException("Product not found with id : " + id);
		}

		productRepository.deleteById(id);
		logger.info("Deleted product in db with id : {}", id);
		return "Product deleted successfully";
	}

	public List<Product> getProductsByDateRange(String startDateStr, String endDateStr) {

		try {
			LocalDateTime startDate = DateTimeUtils.formatDateTime(startDateStr, InventoryConstants.DATE_TIME_PATTERN);
			LocalDateTime endDate = DateTimeUtils.formatDateTime(endDateStr, InventoryConstants.DATE_TIME_PATTERN);

			return productRepository.findByCreatedDateBetween(startDate, endDate);

		} catch (DateTimeParseException e) {
			throw new InvalidDateFormatException("Invalid date format: " + e.getParsedString());
		}
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public long getAllProductsCount() {
		return productRepository.count();
	}

}

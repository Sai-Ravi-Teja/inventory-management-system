package com.ims.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ims.dto.ProductRequest;
import com.ims.model.Product;
import com.ims.repository.ProductRepository;
import com.ims.util.DatabaseUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final DatabaseUtils dbUtils;

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

	public String updateProductById(Product product) {

		Optional<Product> optionalDbProduct = productRepository.findById(product.getId());

		if (!optionalDbProduct.isPresent()) {
			return "Product not found";
		}

		Product dbProduct = optionalDbProduct.get();
		dbUtils.updateNonNullProperties(product, dbProduct);
		productRepository.save(dbProduct);

		logger.info("Updated product in db with details : {}", dbProduct);
		return "Product updated successfully";
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Integer getAllProductsCount() {
		return productRepository.findAll().size();
	}
}

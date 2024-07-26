package com.ims.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ims.dto.ProductRequest;
import com.ims.model.Product;
import com.ims.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void createProduct(ProductRequest productRequest) {

		Product product = new Product();
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		product.setDescription(productRequest.getDescription());

		productRepository.save(product);
		logger.info("Product created and saved in db with details : {}", product);
	}
}

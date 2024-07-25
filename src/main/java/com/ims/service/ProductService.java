package com.ims.service;

import org.springframework.stereotype.Service;

import com.ims.dto.ProductRequest;
import com.ims.model.Product;
import com.ims.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public void createProduct(ProductRequest productRequest) {

		Product product = new Product();
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		product.setDescription(productRequest.getDescription());

		productRepository.save(product);
	}
}

package com.ims.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {

	List<Product> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}

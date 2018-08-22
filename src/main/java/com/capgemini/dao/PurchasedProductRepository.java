package com.capgemini.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.domain.PurchasedProductEntity;

public interface PurchasedProductRepository extends JpaRepository<PurchasedProductEntity, Long> {

	PurchasedProductEntity findById(Long id);

	void deleteById(Long id);

	void deleteByProductName(String productName);
}

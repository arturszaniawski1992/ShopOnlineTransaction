package com.capgemini.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.domain.PurchasedProductEntity;

public interface PurchasedProductRepository
		extends JpaRepository<PurchasedProductEntity, Long>, CustomizedPurchasedProductRepository {

	PurchasedProductEntity findById(Long id);
	
	List<PurchasedProductEntity> findByProductName(String productName);

	void deleteById(Long id);

	void deleteByProductName(String productName);
}

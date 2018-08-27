package com.capgemini.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.domain.PurchasedProductEntity;

public interface PurchasedProductRepository
		extends JpaRepository<PurchasedProductEntity, Long>, CustomizedPurchasedProductRepository {
	/**
	 * This is the method which find product by id.
	 * 
	 * @param Long
	 *            as id of product.
	 * 
	 * @return Product as ProductEntity.
	 */
	PurchasedProductEntity findById(Long id);

	/**
	 * This is the method which find order by amount.
	 * 
	 * @param String
	 *            as product name.
	 * 
	 * @return List of products.
	 */
	List<PurchasedProductEntity> findByProductName(String productName);

	/**
	 * This is the method which remove order from collection.
	 * 
	 * @param Long
	 *            as id of product.
	 * 
	 */
	void deleteById(Long id);

	/**
	 * This is the method which remove order from collection.
	 * 
	 * @param String
	 *            as product name.
	 * 
	 */
	void deleteByProductName(String productName);
}

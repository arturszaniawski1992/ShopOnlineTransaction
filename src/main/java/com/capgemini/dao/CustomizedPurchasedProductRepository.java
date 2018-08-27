package com.capgemini.dao;

import java.util.List;

import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.types.PurchasedProductTOWithNameAndAmount;

public interface CustomizedPurchasedProductRepository {
	/**
	 * This is the method which find products with status in progress.
	 * 
	 * @return List of products with status in progress.
	 */
	public List<PurchasedProductTOWithNameAndAmount> findListProductsWithTransactionInProgress();

	/**
	 * This is the method which find best selling products.
	 * 
	 * @param Int
	 *            as amount of products.
	 * 
	 * @return List of products.
	 */
	public List<PurchasedProductEntity> getBestSellingProducts(int amount);

}

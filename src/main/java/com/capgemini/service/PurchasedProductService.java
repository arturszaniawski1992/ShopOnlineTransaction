package com.capgemini.service;

import java.util.List;

import com.capgemini.types.PurchasedProductTO;
import com.capgemini.types.PurchasedProductTOWithNameAndAmount;

public interface PurchasedProductService {
	/**
	 * This is the method which find product by id.
	 * 
	 * @param Long
	 *            as id of product.
	 * 
	 * @return PurchasedProductTO as product.
	 */
	public PurchasedProductTO findPurchasedProductById(Long id);

	/**
	 * This is the method which find all orders.
	 * 
	 * 
	 * @return List of orders.
	 */
	public List<PurchasedProductTO> findAllPurchasedProducts();

	/**
	 * This is the method which update product.
	 * 
	 * @param PurchasedProductTO
	 *            as product.
	 * 
	 * @return PurchasedProductTO as product.
	 */
	public PurchasedProductTO updateProduct(PurchasedProductTO purchasedProductTO);

	/**
	 * This is the method which save product.
	 * 
	 * @param PurchasedProductTO
	 *            as product.
	 * 
	 * @return PurchasedProductTO as product.
	 */
	public PurchasedProductTO savePurchasedProduct(PurchasedProductTO purchasedProductTO);

	/**
	 * This is the method which remove product from collection.
	 * 
	 * @param Long
	 *            as id of product.
	 * 
	 */
	void removeProduct(Long id);

	/**
	 * This is the method which count products.
	 * 
	 * 
	 * @return Count of products.
	 */
	public Long countProducts();

	/**
	 * This is the method which find list of product with status in progress.
	 * 
	 * 
	 * @return List products with name and amount.
	 */
	public List<PurchasedProductTOWithNameAndAmount> findListProductsWithTransactionInProgress();

	/**
	 * This is the method which find best selling products.
	 * 
	 * @param int
	 *            as amount products to show.
	 * 
	 * @return List of best selling products.
	 */
	public List<PurchasedProductTO> getBestSellingProducts(int amount);

}

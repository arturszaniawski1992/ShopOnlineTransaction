package com.capgemini.service;

import java.util.List;

import com.capgemini.enums.TransactionStatus;
import com.capgemini.types.PurchasedProductTO;
import com.capgemini.types.PurchasedProductTOWithNameAndAmount;

public interface PurchasedProductService {

	public PurchasedProductTO findPurchasedProductById(Long id);

	public List<PurchasedProductTO> findAllPurchasedProducts();

	public PurchasedProductTO updateProduct(PurchasedProductTO purchasedProductTO);

	public PurchasedProductTO savePurchasedProduct(PurchasedProductTO purchasedProductTO);

	void removeProduct(Long id);

	public Long countProducts();

	public List<PurchasedProductTOWithNameAndAmount> findListProductsWithTransactionInProgress();

	public List<PurchasedProductTO> getBestSellingProducts(int amount);

}

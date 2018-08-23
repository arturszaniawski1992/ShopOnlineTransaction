package com.capgemini.service;

import java.util.List;

import com.capgemini.types.PurchasedProductTO;

public interface PurchasedProductService {

	PurchasedProductTO findPurchasedProductById(Long id);

	List<PurchasedProductTO> findAllPurchasedProducts();

	PurchasedProductTO updateProduct(PurchasedProductTO purchasedProductTO);

	PurchasedProductTO savePurchasedProduct(PurchasedProductTO purchasedProductTO);

	void removeProduct(Long id);

	Long countProducts();
}

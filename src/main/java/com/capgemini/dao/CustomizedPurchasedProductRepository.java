package com.capgemini.dao;

import java.util.List;

public interface CustomizedPurchasedProductRepository {
	public List<String> getBestSellingProducts(int amountProducts);

}

package com.capgemini.dao.customize;

import java.util.List;

import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.enums.TransactionStatus;

public interface CustomizedPurchasedProductRepository {
	public List<PurchasedProductEntity> findListProductsWithTransactionInProgress(TransactionStatus transactionStatus);

	public List<PurchasedProductEntity> getBestSellingProducts(int amount);

}

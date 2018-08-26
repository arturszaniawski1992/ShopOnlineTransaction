package com.capgemini.dao.customize.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.dao.customize.CustomizedPurchasedProductRepository;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.QOrderEntity;
import com.capgemini.domain.QPurchasedProductEntity;
import com.capgemini.domain.QTransactionEntity;
import com.capgemini.enums.TransactionStatus;
import com.capgemini.types.PurchasedProductTOWithNameAndAmount;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class PurchasedProductRepositoryImpl implements CustomizedPurchasedProductRepository {

	@Autowired
	EntityManager entityManager;

	// 2f
	@Override
	public List<PurchasedProductTOWithNameAndAmount> findListProductsWithTransactionInProgress() {
		JPAQuery<PurchasedProductEntity> query = new JPAQuery(entityManager);
		QPurchasedProductEntity purchasedProductEntity = QPurchasedProductEntity.purchasedProductEntity;
		QTransactionEntity transactionEntity = QTransactionEntity.transactionEntity;
		QOrderEntity orderEntity = QOrderEntity.orderEntity;

		List<Tuple> results = query.from(purchasedProductEntity).join(purchasedProductEntity.orders, orderEntity)
				.join(orderEntity.transactionEntity, transactionEntity)
				.select(purchasedProductEntity.productName, orderEntity.amount.sum())
				.where(transactionEntity.transactionStatus.eq(TransactionStatus.IN_PROGRESS))
				.groupBy(purchasedProductEntity.productName).orderBy(orderEntity.amount.sum().desc()).fetch();

		List<PurchasedProductTOWithNameAndAmount> products = new ArrayList<>();
		for (Tuple tuple : results) {
			products.add(new PurchasedProductTOWithNameAndAmount(tuple.get(purchasedProductEntity.productName),
					tuple.get(orderEntity.amount.sum())));

		}

		return products;
	}

	// 2d
	@Override
	public List<PurchasedProductEntity> getBestSellingProducts(int amount) {
		QPurchasedProductEntity purchasedProductEntity = QPurchasedProductEntity.purchasedProductEntity;
		QOrderEntity orderEntity = QOrderEntity.orderEntity;
		JPAQuery<PurchasedProductEntity> query = new JPAQuery(entityManager);

		List<PurchasedProductEntity> bestSellingProducts = query.from(purchasedProductEntity)
				.select(purchasedProductEntity).join(purchasedProductEntity.orders, orderEntity).where()
				.groupBy(purchasedProductEntity.id).orderBy(orderEntity.amount.sum().desc())
				.limit(Integer.toUnsignedLong(amount)).fetch();

		return bestSellingProducts;

	}

}

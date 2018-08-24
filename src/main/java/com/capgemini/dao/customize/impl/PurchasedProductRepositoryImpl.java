package com.capgemini.dao.customize.impl;

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
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class PurchasedProductRepositoryImpl implements CustomizedPurchasedProductRepository {

	@Autowired
	EntityManager entityManager;

	@Override
	public List<PurchasedProductEntity> findListProductsWithTransactionInProgress(TransactionStatus transactionStatus) {
		QTransactionEntity transactionEntity = QTransactionEntity.transactionEntity;
		QOrderEntity orderEntity = QOrderEntity.orderEntity;
		QPurchasedProductEntity purchasedProductEntity = QPurchasedProductEntity.purchasedProductEntity;// produkt
		JPAQueryFactory query = new JPAQueryFactory(entityManager);

		List<PurchasedProductEntity> productsWithStatusInProgress = query.from(transactionEntity)
				.innerJoin(transactionEntity.orders, orderEntity)
				.innerJoin(orderEntity.productEntity, purchasedProductEntity).select(purchasedProductEntity)
				.where(transactionEntity.transactionStatus.eq(TransactionStatus.IN_PROGRESS)).fetch();

		return productsWithStatusInProgress;
	}

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

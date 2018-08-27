package com.capgemini.dao.customize.impl;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.dao.CustomizedTransactionRepository;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.QCustomerEntity;
import com.capgemini.domain.QOrderEntity;
import com.capgemini.domain.QPurchasedProductEntity;
import com.capgemini.domain.QTransactionEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.TransactionStatus;
import com.capgemini.types.TransactionSearchCriteria;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class TransactionRepositoryImpl implements CustomizedTransactionRepository {

	@Autowired
	EntityManager entityManager;

	@Override
	public TransactionEntity assignCustomer(TransactionEntity transactionEntity, CustomerEntity customerEntity) {
		transactionEntity.setCustomerEntity(customerEntity);
		return transactionEntity;
	}

	// 2c
	@Override
	public Double getTotalAmountOfTransactionsWithStatus(Long id, TransactionStatus status) {
		JPAQuery<Double> query = new JPAQuery(entityManager);
		QTransactionEntity transactionEntity = QTransactionEntity.transactionEntity;
		QOrderEntity orderEntity = QOrderEntity.orderEntity;
		QPurchasedProductEntity purchasedProductEntity = QPurchasedProductEntity.purchasedProductEntity;

		Double countOftransactions = query.from(transactionEntity)
				.select((orderEntity.amount.multiply(purchasedProductEntity.price).doubleValue()).sum().doubleValue())
				.join(transactionEntity.orders, orderEntity).join(orderEntity.productEntity, purchasedProductEntity)
				.where(transactionEntity.transactionStatus.eq(status)).fetchOne();
		return countOftransactions;
	}

	// 2a
	@Override
	public List<TransactionEntity> searchForTransactionsBySearchCriteria(TransactionSearchCriteria searchCriteria) {

		JPAQueryFactory queryFactory = new JPAQueryFactory(this.entityManager);
		QTransactionEntity transactionEntity = QTransactionEntity.transactionEntity;
		QPurchasedProductEntity purchasedProductEntity = QPurchasedProductEntity.purchasedProductEntity;
		QOrderEntity orderEntity = QOrderEntity.orderEntity;

		BooleanBuilder query = new BooleanBuilder();

		if (searchCriteria.getCustomerName() != null) {
			query.and(transactionEntity.customerEntity.lastName.eq(searchCriteria.getCustomerName()));
		}

		if (searchCriteria.getDateFrom() != null && searchCriteria.getDateTo() != null) {
			query.and(transactionEntity.dateTransaction.between(searchCriteria.getDateFrom(),
					searchCriteria.getDateTo()));
		}

		if (searchCriteria.getProductId() != null) {
			query.and(purchasedProductEntity.id.eq(searchCriteria.getProductId()));
		}
		if (searchCriteria.getTotalTransactionAmount() == null) {
			return queryFactory.selectFrom(transactionEntity).innerJoin(transactionEntity.orders, orderEntity)
					.innerJoin(orderEntity.productEntity, purchasedProductEntity).where(query)
					.groupBy(transactionEntity.id).fetch();
		} else {
			return queryFactory
					.selectFrom(
							transactionEntity)
					.innerJoin(transactionEntity.orders, orderEntity)
					.innerJoin(orderEntity.productEntity, purchasedProductEntity)
					.where(transactionEntity.id
							.in(JPAExpressions.select(transactionEntity.id).from(transactionEntity)
									.innerJoin(transactionEntity.orders, orderEntity)
									.innerJoin(orderEntity.productEntity, purchasedProductEntity)
									.groupBy(transactionEntity.id)
									.having(orderEntity.amount.multiply(purchasedProductEntity.price).doubleValue()
											.sum().doubleValue().eq(searchCriteria.getTotalTransactionAmount())))
							.and(query))
					.groupBy(transactionEntity.id).fetch();
		}

	}

	// 2g
	@Override
	public Double calculateProfitFromPeriod(Date dateFrom, Date dateTo) {

		QTransactionEntity transactionEntity = QTransactionEntity.transactionEntity;
		QPurchasedProductEntity purchasedProductEntity = QPurchasedProductEntity.purchasedProductEntity;
		QOrderEntity orderEntity = QOrderEntity.orderEntity;

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		Double result = query.from(transactionEntity).join(transactionEntity.orders, orderEntity)
				.join(orderEntity.productEntity, purchasedProductEntity)
				.select((orderEntity.amount.multiply(purchasedProductEntity.price)
						.multiply(purchasedProductEntity.margin)).sum().doubleValue())
				.where(transactionEntity.dateTransaction.between(dateFrom, dateTo)).fetchOne();

		return result;

	}

	// 2b
	@Override
	public Double calculateTotalCostOfCustomerTransactions(Long customerId) {
		JPAQuery<Double> query = new JPAQuery(entityManager);
		QTransactionEntity transactionEntity = QTransactionEntity.transactionEntity;
		QPurchasedProductEntity purchasedProductEntity = QPurchasedProductEntity.purchasedProductEntity;
		QCustomerEntity customerEntity = QCustomerEntity.customerEntity;
		QOrderEntity orderEntity = QOrderEntity.orderEntity;

		Double result = query.from(transactionEntity)
				.select((orderEntity.amount.multiply(purchasedProductEntity.price).doubleValue()).sum().doubleValue())
				.join(transactionEntity.customerEntity, customerEntity).join(transactionEntity.orders, orderEntity)
				.join(orderEntity.productEntity, purchasedProductEntity).where(customerEntity.id.eq(customerId))
				.fetchOne();

		return result;
	}

}

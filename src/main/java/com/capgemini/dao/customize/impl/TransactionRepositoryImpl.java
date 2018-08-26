package com.capgemini.dao.customize.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.dao.customize.CustomizedTransactionRepository;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.QCustomerEntity;
import com.capgemini.domain.QOrderEntity;
import com.capgemini.domain.QPurchasedProductEntity;
import com.capgemini.domain.QTransactionEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.TransactionStatus;
import com.capgemini.types.TransactionSearchCriteria;
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

	// c
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
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<TransactionEntity> query = cb.createQuery(TransactionEntity.class);
		Root<TransactionEntity> transactionEntity = query.from(TransactionEntity.class);

		List<Predicate> predicates = new ArrayList<>();

		if (searchCriteria.getCustomerName() != null) {
			predicates.add(cb.equal(transactionEntity.get("customerEntity"), searchCriteria.getCustomerName()));
		}

		if (searchCriteria.getDateFrom() != null) {
			predicates.add(cb.equal(transactionEntity.get("dateFrom"), searchCriteria.getDateFrom()));
		}

		if (searchCriteria.getDateTo() != null) {
			predicates.add(cb.equal(transactionEntity.get("dateTo"), searchCriteria.getDateTo()));
		}

		if (searchCriteria.getProductName() != null) {
			predicates.add(cb.equal(transactionEntity.get("productName"), searchCriteria.getProductName()));
		}

		if (searchCriteria.getTotalTransactionAmount() != null) {
			predicates.add(cb.equal(transactionEntity.get("amount"), searchCriteria.getTotalTransactionAmount()));
		}

		query.select(transactionEntity).where(predicates.toArray(new Predicate[] {}));

		return entityManager.createQuery(query).getResultList();
	}

	// 2g
	@Override
	public Double calculateProfitFromPeriod(Date dateFrom, Date dateTo) {

		QTransactionEntity transactionEntity = QTransactionEntity.transactionEntity;
		QPurchasedProductEntity purchasedProductEntity = QPurchasedProductEntity.purchasedProductEntity;
		QOrderEntity orderEntity = QOrderEntity.orderEntity;

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		Double result = query.from(transactionEntity).innerJoin(transactionEntity.orders, orderEntity)
				.innerJoin(orderEntity.productEntity, purchasedProductEntity)
				.where(transactionEntity.dateTransaction.between(dateFrom, dateTo)
						.and(transactionEntity.transactionStatus.eq(TransactionStatus.FINISHED)))
				.select((orderEntity.amount.doubleValue().multiply(purchasedProductEntity.price)
						.multiply((purchasedProductEntity.margin).divide(100))).sum())
				.fetchOne();
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

package com.capgemini.dao.customize.impl;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.dao.customize.CustomizedCustomerRepository;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.QCustomerEntity;
import com.capgemini.domain.QOrderEntity;
import com.capgemini.domain.QPurchasedProductEntity;
import com.capgemini.domain.QTransactionEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CustomerRepositoryImpl implements CustomizedCustomerRepository {

	@Autowired
	EntityManager entityManager;

	@Override
	public List<CustomerEntity> findTopThreeClientsWhoSpentTheMostInPeriod(short mounthFrom, short yearFrom,
			short mounthTo, short yearTo, int amountOfClients) {

		LocalDateTime from = LocalDateTime.of(yearFrom, mounthFrom, 1, 0, 0, 0);
		LocalDateTime tempDate = LocalDateTime.of(yearTo, mounthTo, 15, 0, 0, 0);
		LocalDateTime to = tempDate.with(TemporalAdjusters.lastDayOfMonth());

		QTransactionEntity transactionEntity = QTransactionEntity.transactionEntity;
		QPurchasedProductEntity purchasedProductEntity = QPurchasedProductEntity.purchasedProductEntity;
		QCustomerEntity customerEntity = QCustomerEntity.customerEntity;
		QOrderEntity orderEntity = QOrderEntity.orderEntity;

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		List<CustomerEntity> topThreeClients = query.from(customerEntity)
				.innerJoin(customerEntity.transactions, transactionEntity)
				.innerJoin(transactionEntity.orders, orderEntity)
				.innerJoin(orderEntity.productEntity, purchasedProductEntity).select(customerEntity)
				.groupBy(customerEntity).where(transactionEntity.dateTransaction.between(from, to))
				.orderBy((purchasedProductEntity.price.multiply(orderEntity.amount)).sum().desc())
				.limit(amountOfClients).fetch();
		return topThreeClients;

	}
		
}

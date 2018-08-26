package com.capgemini.dao.customize.impl;

import java.sql.Date;
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
import com.capgemini.domain.TransactionEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CustomerRepositoryImpl implements CustomizedCustomerRepository {

	@Autowired
	EntityManager entityManager;

	@Override
	public CustomerEntity assignTransaction(CustomerEntity customerEntity, TransactionEntity transactionEntity) {
		customerEntity.addTransaction(transactionEntity);
		return customerEntity;
	}

	@Override
	public List<CustomerEntity> findTopThreeClientsWhoSpentTheMostInPeriod(Date dateFrom, Date dateTo,
			int amountOfClients) {
		QTransactionEntity transactionEntity = QTransactionEntity.transactionEntity;
		QPurchasedProductEntity purchasedProductEntity = QPurchasedProductEntity.purchasedProductEntity;
		QCustomerEntity customerEntity = QCustomerEntity.customerEntity;
		QOrderEntity orderEntity = QOrderEntity.orderEntity;

		JPAQueryFactory query = new JPAQueryFactory(entityManager);
		List<CustomerEntity> topThreeClients = query.from(customerEntity)
				.innerJoin(customerEntity.transactions, transactionEntity)
				.innerJoin(transactionEntity.orders, orderEntity)
				.innerJoin(orderEntity.productEntity, purchasedProductEntity).select(customerEntity)
				.groupBy(customerEntity).where(transactionEntity.dateTransaction.between(dateFrom, dateTo))
				.orderBy((purchasedProductEntity.price.multiply(orderEntity.amount)).sum().desc())
				.limit(amountOfClients).fetch();
		return topThreeClients;

	}

}

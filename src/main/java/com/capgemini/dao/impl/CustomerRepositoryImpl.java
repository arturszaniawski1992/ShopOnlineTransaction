package com.capgemini.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.dao.CustomizedCustomerRepository;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.enums.TransactionStatus;

@Repository
public class CustomerRepositoryImpl implements CustomizedCustomerRepository {

	@Autowired
	EntityManager entityManager;

	@Override
	public List<CustomerEntity> findByTransactionStatus(TransactionStatus transactionStatus) {
		return null;

	}

}

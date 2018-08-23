package com.capgemini.dao;

import java.util.List;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.enums.TransactionStatus;

public interface CustomizedCustomerRepository {
	List<CustomerEntity> findByTransactionStatus(TransactionStatus transactionStatus);

}

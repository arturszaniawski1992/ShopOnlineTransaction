package com.capgemini.dao.customize;

import java.util.List;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.TransactionStatus;
import com.capgemini.types.TransactionSearchCriteria;

public interface CustomizedTransactionRepository {

	public TransactionEntity assignCustomer(TransactionEntity transactionEntity, CustomerEntity customerEntity);

	public Double getTotalAmountOfTransactionsWithStatus(Long customerId, TransactionStatus status);

	public List<TransactionEntity> searchForTransactionsBySearchCriteria(TransactionSearchCriteria searchCriteria);

	public double calculateProfitFromPeriod(short mounthFrom, short yearFrom, short mounthTo, short yearTo);
}

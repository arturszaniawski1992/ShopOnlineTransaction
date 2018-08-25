package com.capgemini.service;

import java.util.List;

import com.capgemini.enums.TransactionStatus;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.TransactionSearchCriteria;
import com.capgemini.types.TransactionTO;

public interface TransactionService {

	public TransactionTO findTransactionById(Long id);

	public List<TransactionTO> findAllTranactions();

	public TransactionTO updateTransaction(TransactionTO transactionTO);

	public TransactionTO saveTransaction(TransactionTO transactionTO);

	public TransactionTO assignCustomer(TransactionTO savedTransaction, CustomerTO savedCustomer);

	public Double getTotalAmountOfTransactionsWithStatus(Long customerId, TransactionStatus status);

	public List<TransactionTO> searchForTransactionsBySearchCriteria(TransactionSearchCriteria searchCriteria);

	public double calculateProfitFromPeriod(short mounthFrom, short yearFrom, short mounthTo, short yearTo);

}

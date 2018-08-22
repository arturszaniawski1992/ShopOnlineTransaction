package com.capgemini.service;

import java.util.List;

import com.capgemini.types.TransactionTO;

public interface TransactionService {
	TransactionTO findTransactionById(Long id);

	List<TransactionTO> findAllTranactions();

	TransactionTO updateTransaction(TransactionTO transactionTO);

	TransactionTO saveTransaction(TransactionTO transactionTO);

	void removeTransaction(Long id);
}

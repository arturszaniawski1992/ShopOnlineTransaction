package com.capgemini.service;

import java.sql.Date;
import java.util.List;

import com.capgemini.enums.TransactionStatus;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.TransactionSearchCriteria;
import com.capgemini.types.TransactionTO;

public interface TransactionService {
	/**
	 * This is the method which remove transaction by id.
	 * 
	 * @param Long
	 *            as id of transaction.
	 * @return
	 * 
	 */
	void removeTransaction(Long id);

	/**
	 * This is the method which find transaction by id.
	 * 
	 * @param Long
	 *            as id of transaction.
	 * 
	 * @return TransactionTO as transaction.
	 */
	public TransactionTO findTransactionById(Long id);

	/**
	 * This is the method which find all transactions.
	 * 
	 * 
	 * @return TransactionTO as transaction.
	 */
	public List<TransactionTO> findAllTranactions();

	/**
	 * This is the method which update transaction.
	 * 
	 * @param TransactionTO
	 *            as transaction.
	 * 
	 * @return TransactionTO as transaction.
	 */
	public TransactionTO updateTransaction(TransactionTO transactionTO);

	/**
	 * This is the method which update transaction by id.
	 * 
	 * @param TransactionTO
	 *            as transaction.
	 * 
	 * @return TransactionTO as transaction.
	 */
	public TransactionTO saveTransaction(TransactionTO transactionTO);

	/**
	 * This is the method which assign customer to transaction.
	 * 
	 * @param TransactionTO
	 *            as transaction and CustomerTO customer..
	 * 
	 * @return TransactionTO as transaction.
	 */
	public TransactionTO assignCustomer(TransactionTO savedTransaction, CustomerTO savedCustomer);

	/**
	 * This is the method which assign customer to transaction.
	 * 
	 * @param Long
	 *            customerId and TransactionStatus as status of transaction.
	 * 
	 * @return Amount of transaction with status and customer.
	 */
	public Double getTotalAmountOfTransactionsWithStatus(Long customerId, TransactionStatus status);

	/**
	 * This is the method which search transaction by criteria.
	 * 
	 * @param TransactionSearchCriteria
	 *            as criteria to search.
	 * 
	 * @return List of transaction with criteria.
	 */
	public List<TransactionTO> searchForTransactionsBySearchCriteria(TransactionSearchCriteria searchCriteria);

	/**
	 * This is the method which calculate profit transaction.
	 * 
	 * @param Date
	 *            dateFrom, Date dateTo as dates.
	 * 
	 * @return Profit from transactions in period time.f
	 */
	public Double calculateProfitFromPeriod(Date dateFrom, Date dateTo);

	/**
	 * This is the method which calculate total cost of customer.
	 * 
	 * @param Long
	 *            as id of customer.
	 * 
	 * @return Total amount of transaction per customer.
	 */
	public Double calculateTotalCostOfCustomerTransactions(Long customerId);

}

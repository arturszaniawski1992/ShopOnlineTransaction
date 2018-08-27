package com.capgemini.dao.customize;

import java.sql.Date;
import java.util.List;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.TransactionStatus;
import com.capgemini.types.TransactionSearchCriteria;

public interface CustomizedTransactionRepository {
	/**
	 * This is the method which assign transaction to customer.
	 * 
	 * @param CustomerEntity
	 *            as customer and TransactionEntity as transaction.
	 * 
	 * @return TransactionEntity as transaction.
	 */
	public TransactionEntity assignCustomer(TransactionEntity transactionEntity, CustomerEntity customerEntity);

	/**
	 * This is the method which get total amount of transaction with given
	 * status and customer.
	 * 
	 * @param Long
	 *            as customer id and TransactionStatus as status of transaction.
	 * 
	 * @return Double as amount.
	 */
	public Double getTotalAmountOfTransactionsWithStatus(Long customerId, TransactionStatus status);

	/**
	 * This is the method which search transactions by given search criteria.
	 * 
	 * @param TransactionSearchCriteria
	 *            as criteria to search transaction.
	 * 
	 * @return List of transactions with given search criteria.
	 */
	public List<TransactionEntity> searchForTransactionsBySearchCriteria(TransactionSearchCriteria searchCriteria);

	/**
	 * This is the method which calculate profit from period of time.
	 * 
	 * @param Date
	 *            as date from and Date as date to as period.
	 * 
	 * @return Double as amount from period.
	 */
	public Double calculateProfitFromPeriod(Date dateFrom, Date dateTo);

	/**
	 * This is the method which calculate total cost spent by customer.
	 * 
	 * @param Long
	 *            as id of customer.
	 * 
	 * @return Double as cost spent by customer.
	 */
	public Double calculateTotalCostOfCustomerTransactions(Long customerId);

}

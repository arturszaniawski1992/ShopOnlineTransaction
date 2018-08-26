package com.capgemini.dao.customize;

import java.sql.Date;
import java.util.List;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;

public interface CustomizedCustomerRepository {
	/**
	 * This is the method which assign customer to transaction.
	 * 
	 * @param CustomerEntity
	 *            as customer and TransactionEntity as transaction.
	 * 
	 * @return CustomerEntity as customer.
	 */
	public CustomerEntity assignTransaction(CustomerEntity customerEntity, TransactionEntity transactionEntity);

	/**
	 * This is the method which find customers who spent the most money in
	 * period of time.
	 * 
	 * @param Date
	 *            from, date to and integer as amount of customers. as id of
	 *            order.
	 * 
	 * @return List of customers.
	 */
	public List<CustomerEntity> findTopThreeClientsWhoSpentTheMostInPeriod(Date dateFrom, Date dateTo,
			int amountOfClients);

}

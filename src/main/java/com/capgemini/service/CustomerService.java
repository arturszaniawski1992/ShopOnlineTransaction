package com.capgemini.service;

import java.sql.Date;
import java.util.List;

import com.capgemini.types.CustomerTO;
import com.capgemini.types.TransactionTO;

public interface CustomerService {
	/**
	 * This is the method which find customer by id.
	 * 
	 * @param Long
	 *            as id of customer.
	 * 
	 * @return CustomerTO as customer.
	 */
	public CustomerTO findCustomerById(Long id);

	/**
	 * This is the method which find all customers.
	 * 
	 * @return List of customers.
	 */
	public List<CustomerTO> findAllCustomers();

	/**
	 * This is the method which update customer dates.
	 * 
	 * @param CustomerTO
	 *            as customer.
	 * 
	 * @return CustomerTO as customer.
	 */
	public CustomerTO updateCustomer(CustomerTO customerTO);

	/**
	 * This is the method which save customer.
	 * 
	 * @param CustomerTO
	 *            as customer.
	 * 
	 * @return CustomerTO as customer.
	 */
	public CustomerTO saveCustomer(CustomerTO customerTO);

	/**
	 * This is the method which remove customer.
	 * 
	 * @param CustomerTO
	 *            as customer.
	 */
	void removeCustomer(Long id);

	/**
	 * This is the method which assign transaction to customer.
	 * 
	 * @param CustomerTO
	 *            as customer.
	 * 
	 * @return CustomerTO as customer.
	 */
	public CustomerTO assignTransaction(CustomerTO customerTO, TransactionTO transactionTO);

	/**
	 * This is the method which find top three client who spent the most money.
	 * 
	 * @param Date
	 *            dateFrom, Date dateTo, int as amount of customers.
	 * 
	 * @return List of top three customers.
	 */
	public List<CustomerTO> findTopThreeClientsWhoSpentTheMostInPeriod(Date dateFrom, Date dateTo,
			int amountOfCustomers);

}
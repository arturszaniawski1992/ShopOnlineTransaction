package com.capgemini.service;

import java.util.List;

import com.capgemini.types.CustomerTO;
import com.capgemini.types.TransactionTO;

public interface CustomerService {

	public CustomerTO findCustomerById(Long id);

	public List<CustomerTO> findAllCustomers();

	public CustomerTO updateCustomer(CustomerTO customerTO);

	public CustomerTO saveCustomer(CustomerTO customerTO);

	void removeClient(Long id);

	public CustomerTO assignTransaction(CustomerTO customerTO, TransactionTO transactionTO);

	public List<CustomerTO> findTopThreeClientsWhoSpentTheMostInPeriod(short mounthFrom, short yearFrom, short mounthTo,
			short yearTo, int amountOfClients);
}
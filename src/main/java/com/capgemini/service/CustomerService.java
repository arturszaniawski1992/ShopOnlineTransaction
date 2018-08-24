package com.capgemini.service;

import java.util.List;

import com.capgemini.types.CustomerTO;
import com.capgemini.types.TransactionTO;

public interface CustomerService {

	CustomerTO findCustomerById(Long id);

	List<CustomerTO> findAllCustomers();

	CustomerTO updateCustomer(CustomerTO customerTO);

	CustomerTO saveCustomer(CustomerTO customerTO);

	void removeClient(Long id);

}
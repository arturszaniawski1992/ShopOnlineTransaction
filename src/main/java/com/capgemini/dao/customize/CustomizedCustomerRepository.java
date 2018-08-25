package com.capgemini.dao.customize;

import java.util.List;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;

public interface CustomizedCustomerRepository {

	public List<CustomerEntity> findTopThreeClientsWhoSpentTheMostInPeriod(short mounthFrom, short yearFrom,
			short mounthTo, short yearTo, int amountOfClients);

	public CustomerEntity assignTransaction(CustomerEntity customerEntity, TransactionEntity transactionEntity);

}

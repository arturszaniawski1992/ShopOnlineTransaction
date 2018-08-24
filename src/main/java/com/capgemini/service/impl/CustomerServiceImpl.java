package com.capgemini.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.dao.CustomerRepository;
import com.capgemini.dao.TransactionRepository;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.mappers.AdressMapper;
import com.capgemini.mappers.CustomerMapper;
import com.capgemini.mappers.TransactionMapper;
import com.capgemini.service.CustomerService;
import com.capgemini.types.CustomerTO;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	private final CustomerMapper customerMapper;

	private final TransactionRepository transactionRepository;

	private final TransactionMapper transactionMappers;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,
			TransactionRepository transactionRepository, TransactionMapper transactionMappers) {
		super();
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
		this.transactionRepository = transactionRepository;
		this.transactionMappers = transactionMappers;
	}

	@Override
	public CustomerTO findCustomerById(Long id) {
		return customerMapper.toCustomerTO(customerRepository.findById(id));
	}

	@Override
	public CustomerTO saveCustomer(CustomerTO customerTO) {
		CustomerEntity customerEntity = customerRepository.save(customerMapper.toCustomerEntity(customerTO));
		return customerMapper.toCustomerTO(customerEntity);
	}

	@Override
	public List<CustomerTO> findAllCustomers() {
		List<CustomerEntity> allCustomers = customerRepository.findAll();
		return customerMapper.map2TOs(allCustomers);
	}

	@Override
	public CustomerTO updateCustomer(CustomerTO customerTO) {
		CustomerEntity customerEntity = customerRepository.findById(customerTO.getId());
		customerEntity.setFirstName(customerTO.getFirstName());
		customerEntity.setLastName(customerTO.getLastName());
		customerEntity.setDateOfBirth(customerTO.getDateOfBirth());
		customerEntity.setAdressData(AdressMapper.toAdressDataEntity(customerTO.getAdressData()));
		customerEntity.setMail(customerTO.getMail());
		customerEntity.setMobile(customerTO.getMobile());
		customerRepository.save(customerEntity);
		return customerMapper.toCustomerTO(customerEntity);
	}

	@Override
	public void removeClient(Long id) {
		customerRepository.deleteById(id);

	}

}

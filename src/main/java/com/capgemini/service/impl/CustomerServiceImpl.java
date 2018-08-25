package com.capgemini.service.impl;

import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.capgemini.dao.CustomerRepository;
import com.capgemini.dao.TransactionRepository;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.mappers.AdressMapper;
import com.capgemini.mappers.CustomerMapper;
import com.capgemini.service.CustomerService;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.TransactionTO;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	private final CustomerMapper customerMapper;

	private final TransactionRepository transactionRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,
			TransactionRepository transactionRepository) {
		super();
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
		this.transactionRepository = transactionRepository;
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
		if (customerEntity.getVersion() != customerTO.getVersion()) {
			throw new OptimisticLockException("incorrect version");
		}
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

	@Override
	public CustomerTO assignTransaction(CustomerTO customerTO, TransactionTO transactionTO) {
		return customerMapper.toCustomerTO(customerRepository.assignTransaction(
				customerRepository.getOne(customerTO.getId()), transactionRepository.getOne(transactionTO.getId())));
	}

	@Override
	public List<CustomerTO> findTopThreeClientsWhoSpentTheMostInPeriod(short mounthFrom, short yearFrom, short mounthTo,
			short yearTo, int amountOfClients) {
		List<CustomerEntity> allCustomers = customerRepository.findTopThreeClientsWhoSpentTheMostInPeriod(mounthFrom,
				yearFrom, mounthTo, yearTo, amountOfClients);
		return customerMapper.map2TOs(allCustomers);
	}

}

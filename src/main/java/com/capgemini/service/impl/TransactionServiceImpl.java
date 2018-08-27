package com.capgemini.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.CustomerRepository;
import com.capgemini.dao.OrderRepository;
import com.capgemini.dao.TransactionRepository;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.OrderEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.TransactionStatus;
import com.capgemini.exception.TransactionNotAllowedException;
import com.capgemini.mappers.TransactionMapper;
import com.capgemini.service.TransactionService;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.TransactionSearchCriteria;
import com.capgemini.types.TransactionTO;

@Service
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

	private final CustomerRepository customerRepository;

	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;

	private final OrderRepository orderRepository;

	public TransactionServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository,
			TransactionMapper transactionMapper, OrderRepository orderRepository) {
		super();
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
		this.orderRepository = orderRepository;
	}

	public void validateTransaction(Long transactionId) throws TransactionNotAllowedException {
		validateTransactionValueForBeginnerCustomer(transactionId);
		validateProductsWeight(transactionId);
	}

	private void validateTransactionValueForBeginnerCustomer(Long transactionId) throws TransactionNotAllowedException {
		TransactionEntity transaction = transactionRepository.findOne(transactionId);
		if (transaction.getCustomerEntity() == null)
			return;
		if (customerRepository.getNumberOfTransationsForCustomer(transaction.getId()) < 3) {
			Double transactionValue = 0.0;
			for (OrderEntity order : transaction.getOrders()) {
				transactionValue += order.getAmount() * order.getProductEntity().getPrice();
			}
			if (transactionValue > 5000.0) {
				throw new TransactionNotAllowedException("You can not buy these items!");
			}
		}
	}

	private void validateProductsWeight(Long transactionId) throws TransactionNotAllowedException {
		TransactionEntity transaction = transactionRepository.findOne(transactionId);

		Double productsWeight = 0.0;
		for (OrderEntity order : transaction.getOrders()) {
			productsWeight = order.getAmount() * order.getProductEntity().getWeight();
		}
		if (productsWeight > 25.0) {
			throw new TransactionNotAllowedException("Maximum weight of items can not be over 25 kilos!");
		}
	}

	@Override
	public TransactionTO findTransactionById(Long id) {
		return transactionMapper.toTransactionTO(transactionRepository.findById(id));

	}

	@Override
	public List<TransactionTO> findAllTranactions() {
		List<TransactionEntity> allTransactions = transactionRepository.findAll();
		return transactionMapper.map2TOs(allTransactions);
	}

	@Override
	public TransactionTO updateTransaction(TransactionTO transactionTO) {
		TransactionEntity transactionEntity = transactionRepository.findById(transactionTO.getId());
		transactionEntity.setTransactionStatus(transactionTO.getTransactionStatus());
		transactionEntity.setDateTransaction(transactionTO.getDateTransaction());

		transactionRepository.save(transactionEntity);
		return transactionMapper.toTransactionTO(transactionEntity);
	}

	@Override
	public TransactionTO saveTransaction(TransactionTO transactionTO) {
		TransactionEntity transactionEntity = transactionRepository
				.save(transactionMapper.toTransactionEntity(transactionTO));

		CustomerEntity customerEntity = customerRepository.findById(transactionEntity.getCustomerEntity().getId());
		transactionEntity.setCustomerEntity(customerEntity);
		List<OrderEntity> orderEntity = orderRepository.findAll();
		transactionEntity.setOrders(orderEntity);
		validateTransaction(transactionEntity.getId());
		transactionRepository.save(transactionEntity);

		return transactionMapper.toTransactionTO(transactionEntity);
	}

	@Override
	public TransactionTO assignCustomer(TransactionTO savedTransaction, CustomerTO savedCustomer) {
		return transactionMapper.toTransactionTO(
				transactionRepository.assignCustomer(transactionRepository.getOne(savedTransaction.getId()),
						customerRepository.getOne(savedCustomer.getId())));
	}

	@Override
	public Double getTotalAmountOfTransactionsWithStatus(Long customerId, TransactionStatus status) {
		Double amount = transactionRepository.getTotalAmountOfTransactionsWithStatus(customerId, status);
		return amount;
	}

	@Override
	public List<TransactionTO> searchForTransactionsBySearchCriteria(TransactionSearchCriteria searchCriteria) {
		return transactionMapper.map2TOs(transactionRepository.searchForTransactionsBySearchCriteria(searchCriteria));
	}

	@Override
	public Double calculateProfitFromPeriod(Date dateFrom, Date dateTo) {
		Double profit = transactionRepository.calculateProfitFromPeriod(dateFrom, dateTo);
		return profit;
	}

	@Override
	public Double calculateTotalCostOfCustomerTransactions(Long customerId) {
		Double amount = transactionRepository.calculateTotalCostOfCustomerTransactions(customerId);
		return amount;
	}

	@Override
	public void removeTransaction(Long id) {
		TransactionEntity transactionEntity = transactionRepository.getOne(id);

		CustomerEntity customerEntity = transactionEntity.getCustomerEntity();
		removeTransactionFromCustomer(transactionEntity, customerEntity);

		transactionRepository.deleteById(id);
	}

	private void removeTransactionFromCustomer(TransactionEntity transactionEntity, CustomerEntity customerEntity) {
		List<TransactionEntity> clientTransactions = customerEntity.getTransactions();
		clientTransactions.remove(transactionEntity);
		customerEntity.setTransactions(clientTransactions);
		customerRepository.save(customerEntity);
	}

}

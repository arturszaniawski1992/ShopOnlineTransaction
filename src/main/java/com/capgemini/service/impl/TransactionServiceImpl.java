package com.capgemini.service.impl;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.capgemini.dao.CustomerRepository;
import com.capgemini.dao.OrderRepository;
import com.capgemini.dao.PurchasedProductRepository;
import com.capgemini.dao.TransactionRepository;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.OrderEntity;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.TransactionStatus;
import com.capgemini.exception.TransactionNotAllowedException;
import com.capgemini.mappers.TransactionMapper;
import com.capgemini.service.TransactionService;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.TransactionSearchCriteria;
import com.capgemini.types.TransactionTO;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private final CustomerRepository customerRepository;

	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;

	private final PurchasedProductRepository purchasedProductRepository;

	private final OrderRepository orderRepository;

	public TransactionServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository,
			TransactionMapper transactionMapper, PurchasedProductRepository purchasedProductRepository,
			OrderRepository orderRepository) {
		super();
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
		this.purchasedProductRepository = purchasedProductRepository;
		this.orderRepository = orderRepository;
	}

	private void checkIfTransactionIsAllowed(CustomerEntity customerEntity, List<PurchasedProductEntity> products)
			throws TransactionNotAllowedException {
		checkIfCustomerHasLessThanThreeTransaction(customerEntity, products);
		checkIfWeightOfProductsIsLessThanLimit(products);

	}

	private void checkIfCustomerHasLessThanThreeTransaction(CustomerEntity customerEntity,
			List<PurchasedProductEntity> products) throws TransactionNotAllowedException {
		if (customerEntity.getTransactions() == null || customerEntity.getTransactions().size() <= 3) {
			Double sumOfOrders = 0.0;
			for (PurchasedProductEntity product : products) {
				sumOfOrders = product.getPrice();
			}
			if (sumOfOrders > 5000.0) {
				throw new TransactionNotAllowedException();
			}
		}
	}

	private void checkIfWeightOfProductsIsLessThanLimit(List<PurchasedProductEntity> products)
			throws TransactionNotAllowedException {
		Double limitWeight = 25.0;
		Double sumOfWeight = 0.0;
		for (PurchasedProductEntity productEntity : products) {
			sumOfWeight = productEntity.getWeight();
		}
		if (sumOfWeight > limitWeight) {
			throw new TransactionNotAllowedException();
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
		List<PurchasedProductEntity> products = purchasedProductRepository.findAll();

		checkIfTransactionIsAllowed(customerEntity, products);

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

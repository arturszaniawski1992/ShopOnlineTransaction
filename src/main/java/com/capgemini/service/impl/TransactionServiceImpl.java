package com.capgemini.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.dao.CustomerRepository;
import com.capgemini.dao.OrderRepository;
import com.capgemini.dao.PurchasedProductRepository;
import com.capgemini.dao.TransactionRepository;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.OrderEntity;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.exception.TransactionNotAllowedException;
import com.capgemini.mappers.CustomerMapper;
import com.capgemini.mappers.OrderMapper;
import com.capgemini.mappers.PurchasedProductMapper;
import com.capgemini.mappers.TransactionMapper;
import com.capgemini.service.TransactionService;
import com.capgemini.types.TransactionTO;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;

	private final PurchasedProductRepository purchasedProductRepository;
	private final PurchasedProductMapper purchasedProductMapper;

	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;

	public TransactionServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,
			TransactionRepository transactionRepository, TransactionMapper transactionMapper,
			PurchasedProductRepository purchasedProductRepository, PurchasedProductMapper purchasedProductMapper,
			OrderRepository orderRepository, OrderMapper orderMapper) {
		super();
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
		this.purchasedProductRepository = purchasedProductRepository;
		this.purchasedProductMapper = purchasedProductMapper;
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
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

}

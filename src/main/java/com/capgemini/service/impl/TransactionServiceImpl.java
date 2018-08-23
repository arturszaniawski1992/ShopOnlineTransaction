package com.capgemini.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.dao.CustomerRepository;
import com.capgemini.dao.PurchasedProductRepository;
import com.capgemini.dao.TransactionRepository;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.mappers.CustomerMapper;
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

	public TransactionServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,
			TransactionRepository transactionRepository, TransactionMapper transactionMapper,
			PurchasedProductRepository purchasedProductRepository, PurchasedProductMapper purchasedProductMapper) {
		super();
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
		this.purchasedProductRepository = purchasedProductRepository;
		this.purchasedProductMapper = purchasedProductMapper;
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
		TransactionEntity transactionEntity = transactionRepository.findById(transactionTO.getCustomerId());
		transactionEntity.setDateTransaction(transactionTO.getDateTransaction());
		transactionEntity.setTransactionStatus(transactionTO.getTransactionStatus());
		transactionRepository.save(transactionEntity);
		return transactionMapper.toTransactionTO(transactionEntity);
	}

	@Override
	public TransactionTO saveTransaction(TransactionTO transactionTO) {
		TransactionEntity transactionEntity = transactionRepository
				.save(transactionMapper.toTransactionEntity(transactionTO));
		return transactionMapper.toTransactionTO(transactionEntity);
	}

	@Override
	public void removeTransaction(Long id) {
		transactionRepository.deleteById(id);
	}

}

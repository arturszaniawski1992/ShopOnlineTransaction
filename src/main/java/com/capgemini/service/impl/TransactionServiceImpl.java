package com.capgemini.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.dao.CustomerRepository;
import com.capgemini.dao.PurchasedProductRepository;
import com.capgemini.dao.TransactionRepository;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionTO> findAllTranactions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionTO updateTransaction(TransactionTO transactionTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionTO saveTransaction(TransactionTO transactionTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeTransaction(Long id) {
		// TODO Auto-generated method stub

	}

}

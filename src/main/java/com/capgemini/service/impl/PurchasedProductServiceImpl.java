package com.capgemini.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.dao.CustomerRepository;
import com.capgemini.dao.PurchasedProductRepository;
import com.capgemini.dao.TransactionRepository;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.mappers.CustomerMapper;
import com.capgemini.mappers.PurchasedProductMapper;
import com.capgemini.mappers.TransactionMapper;
import com.capgemini.service.PurchasedProductService;
import com.capgemini.types.PurchasedProductTO;

@Service
public class PurchasedProductServiceImpl implements PurchasedProductService {
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;

	private final PurchasedProductRepository purchasedProductRepository;
	private final PurchasedProductMapper purchasedProductMapper;

	public PurchasedProductServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,
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
	public PurchasedProductTO findPurchasedProductById(Long id) {
		return purchasedProductMapper.toPurchasedProductTO(purchasedProductRepository.findById(id));

	}

	@Override
	public List<PurchasedProductTO> findAllPurchasedProducts() {
		List<PurchasedProductEntity> allProducts = purchasedProductRepository.findAll();
		return purchasedProductMapper.map2TOs(allProducts);
	}

	@Override
	public PurchasedProductTO updateProduct(PurchasedProductTO purchasedProductTO) {
		PurchasedProductEntity productEntity = purchasedProductRepository.findById(purchasedProductTO.getId());
		productEntity.setWeight(purchasedProductTO.getWeight());
		productEntity.setMargin(purchasedProductTO.getMargin());
		productEntity.setPrice(purchasedProductTO.getPrice());
		purchasedProductRepository.save(productEntity);
		return purchasedProductMapper.toPurchasedProductTO(productEntity);
	}

	@Override
	public PurchasedProductTO savePurchasedProduct(PurchasedProductTO purchasedProductTO) {
		PurchasedProductEntity purchasedProductEntity = purchasedProductRepository
				.save(purchasedProductMapper.toPurchasedProductEntity(purchasedProductTO));
		return purchasedProductMapper.toPurchasedProductTO(purchasedProductEntity);
	}

	@Override
	public void removeProduct(Long id) {
		customerRepository.deleteById(id);

	}

	@Override
	public Long countProducts() {
		return purchasedProductRepository.count();
	}

}
package com.capgemini.service.impl;

import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.CustomerRepository;
import com.capgemini.dao.PurchasedProductRepository;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.mappers.PurchasedProductMapper;
import com.capgemini.service.PurchasedProductService;
import com.capgemini.types.PurchasedProductTO;
import com.capgemini.types.PurchasedProductTOWithNameAndAmount;

@Service
@Transactional(readOnly = true)
public class PurchasedProductServiceImpl implements PurchasedProductService {

	private final CustomerRepository customerRepository;
	private final PurchasedProductRepository purchasedProductRepository;
	private final PurchasedProductMapper purchasedProductMapper;

	public PurchasedProductServiceImpl(CustomerRepository customerRepository,
			PurchasedProductRepository purchasedProductRepository, PurchasedProductMapper purchasedProductMapper) {
		super();
		this.customerRepository = customerRepository;
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
	@Transactional(readOnly = false)
	public PurchasedProductTO updateProduct(PurchasedProductTO purchasedProductTO) {
		PurchasedProductEntity productEntity = purchasedProductRepository.findById(purchasedProductTO.getId());
		if (productEntity.getVersion() != purchasedProductTO.getVersion()) {
			throw new OptimisticLockException("incorrect version");
		}
		productEntity.setWeight(purchasedProductTO.getWeight());
		productEntity.setMargin(purchasedProductTO.getMargin());
		productEntity.setPrice(purchasedProductTO.getPrice());
		purchasedProductRepository.save(productEntity);
		return purchasedProductMapper.toPurchasedProductTO(productEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public PurchasedProductTO savePurchasedProduct(PurchasedProductTO purchasedProductTO) {
		return purchasedProductMapper.toPurchasedProductTO(
				purchasedProductRepository.save(purchasedProductMapper.toPurchasedProductEntity(purchasedProductTO)));
	}

	@Override
	@Transactional(readOnly = false)
	public void removeProduct(Long id) {
		customerRepository.deleteById(id);

	}

	@Override
	public Long countProducts() {
		return purchasedProductRepository.count();
	}

	@Override
	public List<PurchasedProductTOWithNameAndAmount> findListProductsWithTransactionInProgress() {
		return purchasedProductRepository.findListProductsWithTransactionInProgress();
	}

	@Override
	public List<PurchasedProductTO> getBestSellingProducts(int amount) {
		return purchasedProductMapper.map2TOs((purchasedProductRepository.getBestSellingProducts(amount)));

	}

}
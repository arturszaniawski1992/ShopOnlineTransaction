package com.capgemini.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.dao.CustomizedPurchasedProductRepository;
import com.capgemini.domain.QPurchasedProductEntity;
import com.capgemini.domain.QTransactionEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class PurchasedProductRepositoryImpl implements CustomizedPurchasedProductRepository {

	@Autowired
	EntityManager entityManager;

	@Override
	public List<String> getBestSellingProducts(int amountProducts) {
		return null;
	}

}

package com.capgemini.dao.customize.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.dao.customize.CustomizedTransactionRepository;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.QOrderEntity;
import com.capgemini.domain.QPurchasedProductEntity;
import com.capgemini.domain.QTransactionEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class TransactionRepository implements CustomizedTransactionRepository {

	@Autowired
	EntityManager entityManager;

}

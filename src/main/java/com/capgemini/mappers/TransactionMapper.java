package com.capgemini.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.domain.TransactionEntity.TransactionEntityBuilder;
import com.capgemini.types.TransactionTO;
import com.capgemini.types.TransactionTO.TransactionTOBuilder;

@Component
public class TransactionMapper {
	@PersistenceContext
	private EntityManager entityManager;

	public TransactionTO toTransactionTO(TransactionEntity transactionEntity) {
		if (transactionEntity == null)
			return null;

		TransactionTOBuilder transactionTOBuilder = new TransactionTOBuilder()
				.withVersion(transactionEntity.getVersion()).withId(transactionEntity.getId())
				.withCustomerId(transactionEntity.getCustomerEntity().getId()).withAmount(transactionEntity.getAmount())
				.withDateTransaction(transactionEntity.getDateTransaction())
				.withTransactionStatus(transactionEntity.getTransactionStatus());

		if (transactionEntity.getProducts() != null) {
			transactionTOBuilder.withProducts(
					transactionEntity.getProducts().stream().map(e -> e.getId()).collect(Collectors.toList()));

		}
		return transactionTOBuilder.build();
	}

	public TransactionEntity toTransactionEntity(TransactionTO transactionTO) {
		if (transactionTO == null)
			return null;

		List<Long> products = transactionTO.getProducts();
		List<TransactionEntity> listOfProducts = new ArrayList<>();
		if (products != null) {
			for (Long id : products) {
				listOfProducts.add(entityManager.getReference(TransactionEntity.class, id));
			}
		}
		CustomerEntity customerEntity = new CustomerEntity();
		CustomerEntity customer = entityManager.getReference(CustomerEntity.class, customerEntity.getId());

		TransactionEntityBuilder transactionEntityBuilder = new TransactionEntityBuilder()
				.withVersion(transactionTO.getVersion()).withId(transactionTO.getId()).withCustomerEntity(customer)
				.withAmount(transactionTO.getAmount()).withDateTransaction(transactionTO.getDateTransaction())
				.withTransactionStatus(transactionTO.getTransactionStatus());

		return transactionEntityBuilder.build();

	}

	public List<TransactionTO> map2TOs(List<TransactionEntity> transactionEntities) {
		return transactionEntities.stream().map(this::toTransactionTO).collect(Collectors.toList());
	}

	public List<TransactionEntity> map2Entities(List<TransactionTO> transactionTOs) {
		return transactionTOs.stream().map(this::toTransactionEntity).collect(Collectors.toList());
	}
}

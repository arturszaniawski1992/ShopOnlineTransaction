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

	/**
	 * This is the method which map transaction entity to transaction TOs.
	 * 
	 * @param TransactionEntity
	 *            as transaction. F
	 * @return TransactionTO as transaction.
	 */
	public TransactionTO toTransactionTO(TransactionEntity transactionEntity) {
		if (transactionEntity == null)
			return null;

		TransactionTOBuilder transactionTOBuilder = new TransactionTOBuilder()
				.withVersion(transactionEntity.getVersion()).withId(transactionEntity.getId())
				.withCustomerId(transactionEntity.getCustomerEntity().getId())
				.withDateTransaction(transactionEntity.getDateTransaction())
				.withTransactionStatus(transactionEntity.getTransactionStatus());

		if (transactionEntity.getOrders() != null) {
			transactionTOBuilder.withOrders(
					transactionEntity.getOrders().stream().map(e -> e.getId()).collect(Collectors.toList()));

		}
		return transactionTOBuilder.build();
	}

	/**
	 * This is the method which map transaction TO to transaction entity.
	 * 
	 * @param TransactionTO
	 *            as transaction.
	 * 
	 * @return TransactionEntity as transaction.
	 */
	public TransactionEntity toTransactionEntity(TransactionTO transactionTO) {
		if (transactionTO == null)
			return null;

		List<Long> orders = transactionTO.getOrders();
		List<TransactionEntity> listOfProductOrders = new ArrayList<>();
		if (orders != null) {
			for (Long id : orders) {
				listOfProductOrders.add(entityManager.getReference(TransactionEntity.class, id));
			}
		}
		CustomerEntity customer = entityManager.getReference(CustomerEntity.class, transactionTO.getCustomerId());
		TransactionEntityBuilder transactionEntityBuilder = new TransactionEntityBuilder()
				.withVersion(transactionTO.getVersion()).withId(transactionTO.getId()).withCustomerEntity(customer)
				.withDateTransaction(transactionTO.getDateTransaction())
				.withTransactionStatus(transactionTO.getTransactionStatus());

		return transactionEntityBuilder.build();

	}

	/**
	 * This is the method which map list of transaction entities to transaction
	 * TOs.
	 * 
	 * @param List
	 *            of transactionEntities as list of transactions.
	 * 
	 * @return List of TransactionTOs.
	 */
	public List<TransactionTO> map2TOs(List<TransactionEntity> transactionEntities) {
		return transactionEntities.stream().map(this::toTransactionTO).collect(Collectors.toList());
	}

	/**
	 * This is the method which map list of transaction TOs to transaction
	 * entities.
	 * 
	 * @param List
	 *            of transactionTOs as list of transactions.
	 * 
	 * @return List of TransactionEntities.
	 */
	public List<TransactionEntity> map2Entities(List<TransactionTO> transactionTOs) {
		return transactionTOs.stream().map(this::toTransactionEntity).collect(Collectors.toList());
	}
}

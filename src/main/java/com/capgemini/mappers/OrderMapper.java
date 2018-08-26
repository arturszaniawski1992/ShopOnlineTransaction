package com.capgemini.mappers;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.capgemini.domain.OrderEntity;
import com.capgemini.domain.OrderEntity.OrderEntityBuilder;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.types.OrderTO;
import com.capgemini.types.OrderTO.OrderTOBuilder;

@Component
public class OrderMapper {
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * This is the method which map order entity to order TO.
	 * 
	 * @param OrderEntity
	 *            as order.
	 * 
	 * @return OrderTO as order.
	 */
	public OrderTO toOrderTO(OrderEntity orderEntity) {
		if (orderEntity == null)
			return null;

		OrderTOBuilder orderEntityBuilder = new OrderTOBuilder().withId(orderEntity.getId())
				.withVersion(orderEntity.getVersion()).withAmount(orderEntity.getAmount());

		if (orderEntity.getTransactionEntity() != null) {
			if (orderEntity.getTransactionEntity().getId() != null)
				orderEntityBuilder.withTransactionTO(orderEntity.getTransactionEntity().getId());
		}
		if (orderEntity.getProductEntity() != null) {
			if (orderEntity.getProductEntity().getId() != null) {
				orderEntityBuilder.withProductTOId(orderEntity.getProductEntity().getId());
			}
		}
		return orderEntityBuilder.build();

	}

	/**
	 * This is the method which map order TO to order entity.
	 * 
	 * @param OrderTO
	 *            as order.
	 * 
	 * @return OrderEntity as order.
	 */
	public OrderEntity toOrderEntity(OrderTO orderTO) {
		if (orderTO == null)
			return null;

		OrderEntityBuilder orderEntityBuilder = new OrderEntityBuilder().withVersion(orderTO.getVersion())
				.withId(orderTO.getId()).withAmount(orderTO.getAmount());

		if (orderTO.getTransactionTOId() != null) {
			TransactionEntity transaction = entityManager.getReference(TransactionEntity.class,
					orderTO.getTransactionTOId());
			orderEntityBuilder.withTransactionEntity(transaction);
		}
		if (orderTO.getProductTOId() != null) {
			PurchasedProductEntity product = entityManager.getReference(PurchasedProductEntity.class,
					orderTO.getProductTOId());
			orderEntityBuilder.withProductEntity(product);
		}
		return orderEntityBuilder.build();
	}

	/**
	 * This is the method which map list of order entities to order TOs.
	 * 
	 * @param List
	 *            of OrderEntities as list of orders.
	 * 
	 * @return List of orderTOs.
	 */
	public List<OrderTO> map2TOs(List<OrderEntity> orderEntities) {
		return orderEntities.stream().map(this::toOrderTO).collect(Collectors.toList());
	}

	/**
	 * This is the method which map list of order TOs to order entities.
	 * 
	 * @param List
	 *            of OrderTOs as list of orders.
	 * 
	 * @return List of orderEntities.
	 */
	public List<OrderEntity> map2Entities(List<OrderTO> orderTOs) {
		return orderTOs.stream().map(this::toOrderEntity).collect(Collectors.toList());
	}
}

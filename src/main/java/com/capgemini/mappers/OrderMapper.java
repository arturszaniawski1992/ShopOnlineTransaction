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

	public OrderTO toOrderTO(OrderEntity orderEntity) {
		if (orderEntity == null)
			return null;

		return new OrderTOBuilder().withId(orderEntity.getId()).withVersion(orderEntity.getId())
				.withAmount(orderEntity.getAmount()).withProductTOId(orderEntity.getProductEntity().getId())
				.withTransactionTO(orderEntity.getTransactionEntity().getId()).build();

	}

	public OrderEntity toOrderEntity(OrderTO orderTO) {
		if (orderTO == null)
			return null;
		PurchasedProductEntity product = entityManager.getReference(PurchasedProductEntity.class,
				orderTO.getProductTOId());

		TransactionEntity transaction = entityManager.getReference(TransactionEntity.class,
				orderTO.getTransactionTOId());

		return new OrderEntityBuilder().withVersion(orderTO.getVersion()).withId(orderTO.getVersion())
				.withAmount(orderTO.getAmount()).withProductEntity(product).withTransactionEntity(transaction).build();

	}

	public List<OrderTO> map2TOs(List<OrderEntity> orderEntities) {
		return orderEntities.stream().map(this::toOrderTO).collect(Collectors.toList());
	}

	public List<OrderEntity> map2Entities(List<OrderTO> orderTOs) {
		return orderTOs.stream().map(this::toOrderEntity).collect(Collectors.toList());
	}
}

package com.capgemini.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.capgemini.domain.OrderEntity;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.PurchasedProductEntity.PurchasedProductEntityBuilder;
import com.capgemini.types.PurchasedProductTO;
import com.capgemini.types.PurchasedProductTO.PurchasedProductTOBuilder;

@Component
public class PurchasedProductMapper {
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * This is the method which map product TO to product entity.
	 * 
	 * @param PurchasedProductEntity
	 *            as product.
	 * 
	 * @return PurchasedProductTO as product.
	 */
	public PurchasedProductTO toPurchasedProductTO(PurchasedProductEntity purchasedProductEntity) {
		if (purchasedProductEntity == null)
			return null;

		PurchasedProductTOBuilder purchasedProductTOBuilder = new PurchasedProductTOBuilder()
				.withVersion(purchasedProductEntity.getVersion()).withId(purchasedProductEntity.getId())
				.withProductName(purchasedProductEntity.getProductName()).withPrice(purchasedProductEntity.getPrice())
				.withMargin(purchasedProductEntity.getMargin()).withWeight(purchasedProductEntity.getWeight());
		if (purchasedProductEntity.getOrders() != null) {
			purchasedProductTOBuilder.withOrders(
					purchasedProductEntity.getOrders().stream().map(e -> e.getId()).collect(Collectors.toList()));

		}
		return purchasedProductTOBuilder.build();
	}

	/**
	 * This is the method which map product entity to product TO.
	 * 
	 * @param PurchasedProductTO
	 *            as product.
	 * 
	 * @return PurchasedProductEntity as product.
	 */
	public PurchasedProductEntity toPurchasedProductEntity(PurchasedProductTO purchasedProductTO) {
		if (purchasedProductTO == null)
			return null;

		List<Long> orders = purchasedProductTO.getOrders();
		List<OrderEntity> listOfOrders = new ArrayList<>();
		if (orders != null) {
			for (Long id : orders) {
				listOfOrders.add(entityManager.getReference(OrderEntity.class, id));
			}
		}

		PurchasedProductEntityBuilder purchasedProductEntityBuilder = new PurchasedProductEntityBuilder()
				.withVersion(purchasedProductTO.getVersion()).withId(purchasedProductTO.getId())
				.withProductName(purchasedProductTO.getProductName()).withPrice(purchasedProductTO.getPrice())
				.withMargin(purchasedProductTO.getMargin()).withWeight(purchasedProductTO.getWeight());

		return purchasedProductEntityBuilder.build();

	}

	/**
	 * This is the method which map list of product entities to product TOs.
	 * 
	 * @param List
	 *            of purchasedProductEntities as list of products.
	 * 
	 * @return List of productTOs.
	 */
	public List<PurchasedProductTO> map2TOs(List<PurchasedProductEntity> purchasedProductEntities) {
		return purchasedProductEntities.stream().map(this::toPurchasedProductTO).collect(Collectors.toList());
	}

	/**
	 * This is the method which map list of product TOs to product entities.
	 * 
	 * @param List
	 *            of purchasedProductTOs as list of products.
	 * 
	 * @return List of PurchasedProductEntities.
	 */
	public List<PurchasedProductEntity> map2Entities(List<PurchasedProductTO> purchasedProductTOs) {
		return purchasedProductTOs.stream().map(this::toPurchasedProductEntity).collect(Collectors.toList());
	}

}

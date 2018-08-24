package com.capgemini.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.domain.OrderEntity;
import com.capgemini.domain.OrderEntity.OrderEntityBuilder;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.PurchasedProductEntity.PurchasedProductEntityBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class OrderRepositoryTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	PurchasedProductRepository purchasedProductRepository;

	@Test
	public void shouldFindOrderById() {
		// given

		PurchasedProductEntity product = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity saveProduct = purchasedProductRepository.save(product);

		OrderEntity order = new OrderEntityBuilder().withAmount(15).withProductEntity(saveProduct).build();
		OrderEntity savedOrder = orderRepository.save(order);

		// when
		OrderEntity selectedOrder = orderRepository.findById(savedOrder.getId());

		// then
		assertThat(savedOrder.getAmount()).isEqualTo(selectedOrder.getAmount());
		assertThat(savedOrder.getId()).isEqualTo(selectedOrder.getId());
	}

	@Test
	public void shouldFindByAmount() {
		// given
		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity product2 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();

		PurchasedProductEntity saveProduct1 = purchasedProductRepository.save(product1);
		PurchasedProductEntity saveProduct2 = purchasedProductRepository.save(product2);

		OrderEntity order1 = new OrderEntityBuilder().withAmount(2).withProductEntity(saveProduct1).build();
		OrderEntity order2 = new OrderEntityBuilder().withAmount(5).withProductEntity(saveProduct2).build();
		OrderEntity order3 = new OrderEntityBuilder().withAmount(2).withProductEntity(saveProduct1).build();
		OrderEntity savedOrder1 = orderRepository.save(order1);
		orderRepository.save(order2);
		orderRepository.save(order3);

		// when
		List<OrderEntity> selectedOrder = orderRepository.findByAmount(savedOrder1.getAmount());
		// then
		assertEquals(2, selectedOrder.size());

	}

}

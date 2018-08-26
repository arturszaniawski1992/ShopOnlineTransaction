package com.capgemini.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.CustomerEntity.CustomerEntityBuilder;
import com.capgemini.domain.OrderEntity;
import com.capgemini.domain.OrderEntity.OrderEntityBuilder;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.PurchasedProductEntity.PurchasedProductEntityBuilder;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.domain.TransactionEntity.TransactionEntityBuilder;
import com.capgemini.embeded.AdressData;
import com.capgemini.embeded.AdressData.AdressDataEntityBuilder;
import com.capgemini.enums.TransactionStatus;

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

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TransactionRepository transactionRepository;

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

	@Test
	public void shouldRemoveOrderById() {
		// given
		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity product2 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();

		PurchasedProductEntity saveProduct1 = purchasedProductRepository.save(product1);
		PurchasedProductEntity saveProduct2 = purchasedProductRepository.save(product2);
		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity cust1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();
		customerRepository.save(cust1);

		purchasedProductRepository.save(product1);
		List<PurchasedProductEntity> products = new ArrayList<>();
		products.add(product1);

		TransactionEntity transaction = new TransactionEntityBuilder().withCustomerEntity(cust1)
				.withTransactionStatus(TransactionStatus.IN_PROGRESS).build();

		TransactionEntity savedTransaction = transactionRepository.save(transaction);
		OrderEntity order1 = new OrderEntityBuilder().withAmount(2).withProductEntity(saveProduct1).build();
		OrderEntity order2 = new OrderEntityBuilder().withAmount(5).withProductEntity(saveProduct2).build();
		OrderEntity order3 = new OrderEntityBuilder().withAmount(2).withProductEntity(saveProduct1)
				.withTransactionEntity(savedTransaction).build();
		OrderEntity savedOrder1 = orderRepository.save(order1);
		OrderEntity savedOrder2 = orderRepository.save(order2);
		OrderEntity savedOrder3 = orderRepository.save(order3);

		List<OrderEntity> orders = new ArrayList<>();
		orders.add(savedOrder1);
		orders.add(savedOrder2);
		orders.add(savedOrder3);

		// when
		orderRepository.deleteById(savedOrder1.getId());
		// then
		assertEquals(2, orderRepository.findAll().size());
		assertEquals(3, orders.size());

	}
}

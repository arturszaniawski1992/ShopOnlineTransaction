package com.capgemini.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
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
import com.capgemini.domain.OrderEntity;
import com.capgemini.domain.CustomerEntity.CustomerEntityBuilder;
import com.capgemini.domain.OrderEntity.OrderEntityBuilder;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.PurchasedProductEntity.PurchasedProductEntityBuilder;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.domain.TransactionEntity.TransactionEntityBuilder;
import com.capgemini.embeded.AdressData;
import com.capgemini.embeded.AdressData.AdressDataEntityBuilder;
import com.capgemini.enums.TransactionStatus;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=mysql")
public class TransactionRepositoryTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PurchasedProductRepository purchasedProductRepository;
	@Autowired
	private OrderRepository orderRepository;

	@Test
	public void shouldFindTransactionById() {
		// given

		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity cust1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();
		customerRepository.save(cust1);
		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(1.0)
				.withProductName("ball").withWeight(0.5).build();
		purchasedProductRepository.save(product1);
		List<PurchasedProductEntity> products = new ArrayList<>();
		products.add(product1);

		TransactionEntity transaction = new TransactionEntityBuilder().withCustomerEntity(cust1)
				.withTransactionStatus(TransactionStatus.IN_PROGRESS).build();

		TransactionEntity savedTransaction = transactionRepository.save(transaction);

		// when
		TransactionEntity selectedTransaction = transactionRepository.findById(savedTransaction.getId());

		// then
		assertThat(savedTransaction.getId()).isEqualTo(selectedTransaction.getId());
	}

	@Test
	public void shouldFindByTransactionStatus() {
		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity cust1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();
		customerRepository.save(cust1);
		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(1.0)
				.withProductName("ball").withWeight(0.5).build();
		purchasedProductRepository.save(product1);
		List<PurchasedProductEntity> products = new ArrayList<>();
		products.add(product1);

		TransactionEntity transaction = new TransactionEntityBuilder().withCustomerEntity(cust1)
				.withTransactionStatus(TransactionStatus.IN_PROGRESS).build();

		TransactionEntity savedTransaction = transactionRepository.save(transaction);
		// when
		List<TransactionEntity> selectedTransaction = transactionRepository
				.findByTransactionStatus(savedTransaction.getTransactionStatus());

		// then
		assertEquals(5, customerRepository.count());
		assertEquals(8, selectedTransaction.size());
	}

	@Test
	public void shouldFindThreeCustomersWhoSPentTheMost() {
		// given

		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity customer1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();
		CustomerEntity savedCustomer = customerRepository.save(customer1);

		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity product2 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity product3 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity product4 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(31.0).build();
		PurchasedProductEntity product5 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(1.0).build();
		PurchasedProductEntity product6 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity product7 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity product8 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(5.0).build();
		PurchasedProductEntity product9 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity product10 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(4.0).build();
		PurchasedProductEntity product11 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(3.0).build();
		PurchasedProductEntity product12 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(1.0).build();
		PurchasedProductEntity product13 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity product14 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductEntity product15 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();

		PurchasedProductEntity saveProduct1 = purchasedProductRepository.save(product1);
		PurchasedProductEntity saveProduct2 = purchasedProductRepository.save(product2);
		PurchasedProductEntity saveProduct3 = purchasedProductRepository.save(product3);
		PurchasedProductEntity saveProduct4 = purchasedProductRepository.save(product4);
		PurchasedProductEntity saveProduct5 = purchasedProductRepository.save(product5);
		PurchasedProductEntity saveProduct6 = purchasedProductRepository.save(product6);
		PurchasedProductEntity saveProduct7 = purchasedProductRepository.save(product7);
		PurchasedProductEntity saveProduct8 = purchasedProductRepository.save(product8);
		PurchasedProductEntity saveProduct9 = purchasedProductRepository.save(product9);
		PurchasedProductEntity saveProduct10 = purchasedProductRepository.save(product10);
		PurchasedProductEntity saveProduct11 = purchasedProductRepository.save(product11);
		PurchasedProductEntity saveProduct12 = purchasedProductRepository.save(product12);
		PurchasedProductEntity saveProduct13 = purchasedProductRepository.save(product13);
		PurchasedProductEntity saveProduct14 = purchasedProductRepository.save(product14);

		OrderEntity order1 = new OrderEntityBuilder().withAmount(2).withProductEntity(saveProduct1).build();
		OrderEntity order2 = new OrderEntityBuilder().withAmount(5).withProductEntity(saveProduct2).build();
		OrderEntity order3 = new OrderEntityBuilder().withAmount(2).withProductEntity(saveProduct3).build();
		OrderEntity order4 = new OrderEntityBuilder().withAmount(55).withProductEntity(saveProduct4).build();
		OrderEntity order5 = new OrderEntityBuilder().withAmount(12).withProductEntity(saveProduct5).build();
		OrderEntity order6 = new OrderEntityBuilder().withAmount(1).withProductEntity(saveProduct6).build();
		OrderEntity order7 = new OrderEntityBuilder().withAmount(5).withProductEntity(saveProduct7).build();
		OrderEntity order8 = new OrderEntityBuilder().withAmount(67).withProductEntity(saveProduct8).build();
		OrderEntity order9 = new OrderEntityBuilder().withAmount(2).withProductEntity(saveProduct9).build();
		OrderEntity order10 = new OrderEntityBuilder().withAmount(1).withProductEntity(saveProduct10).build();
		OrderEntity order11 = new OrderEntityBuilder().withAmount(15).withProductEntity(saveProduct11).build();
		OrderEntity order12 = new OrderEntityBuilder().withAmount(123).withProductEntity(saveProduct12).build();
		OrderEntity order13 = new OrderEntityBuilder().withAmount(15).withProductEntity(saveProduct13).build();
		OrderEntity order14 = new OrderEntityBuilder().withAmount(3).withProductEntity(saveProduct14).build();
		OrderEntity savedOrder1 = orderRepository.save(order1);
		OrderEntity savedOrder2 = orderRepository.save(order2);
		OrderEntity savedOrder3 = orderRepository.save(order3);
		OrderEntity savedOrder4 = orderRepository.save(order3);
		OrderEntity savedOrder5 = orderRepository.save(order4);
		OrderEntity savedOrder6 = orderRepository.save(order5);
		OrderEntity savedOrder7 = orderRepository.save(order6);
		OrderEntity savedOrder8 = orderRepository.save(order7);
		OrderEntity savedOrder9 = orderRepository.save(order8);
		OrderEntity savedOrder10 = orderRepository.save(order9);
		OrderEntity savedOrder11 = orderRepository.save(order10);
		OrderEntity savedOrder12 = orderRepository.save(order11);

		List<OrderEntity> orders = new ArrayList<>();
		orders.add(savedOrder12);
		orders.add(savedOrder1);
		orders.add(savedOrder5);

		TransactionEntity transactionEntity1 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.IN_PROGRESS)
				.withDateTransaction(LocalDateTime.of(2018, 3, 1, 0, 0)).build();
		TransactionEntity transactionEntity2 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.ON_DELIVERY)
				.withDateTransaction(LocalDateTime.of(2018, 2, 1, 0, 0)).build();
		TransactionEntity transactionEntity3 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.IN_PROGRESS)
				.withDateTransaction(LocalDateTime.of(2018, 3, 1, 0, 0)).build();
		TransactionEntity transactionEntity4 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.IN_PROGRESS)
				.withDateTransaction(LocalDateTime.of(2018, 2, 1, 0, 0)).build();

		// when
		List<CustomerEntity> customers = customerRepository.findTopThreeClientsWhoSpentTheMostInPeriod((short) 2,
				(short) 2018, (short) 4, (short) 2018, 1);

		// then
		assertNotNull(customers);
		assertEquals(0, customers.size());

	}

}

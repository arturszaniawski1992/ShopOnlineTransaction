package com.capgemini.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
import com.capgemini.types.PurchasedProductTOWithNameAndAmount;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=mysql")
public class PurchasedProductRepositoryTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private PurchasedProductRepository purchasedProductRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	public void shouldFindProductById() {
		// given
		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(21.0)
				.withProductName("ball").withWeight(12.0).build();

		PurchasedProductEntity savedProduct = purchasedProductRepository.save(product1);

		// when
		PurchasedProductEntity selectedProduct = purchasedProductRepository.findById(savedProduct.getId());

		// then
		assertThat(savedProduct.getProductName()).isEqualTo(selectedProduct.getProductName());
		assertThat(savedProduct.getId()).isEqualTo(selectedProduct.getId());
	}

	@Test
	public void shouldFindCustomerByProductName() {
		// given
		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(21.0)
				.withProductName("ball").withWeight(12.0).build();
		PurchasedProductEntity product2 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(21.0)
				.withProductName("ball").withWeight(12.0).build();
		PurchasedProductEntity product3 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(21.0)
				.withProductName("ball").withWeight(12.0).build();
		purchasedProductRepository.save(product1);
		purchasedProductRepository.save(product2);
		purchasedProductRepository.save(product3);
		String productName = "ball";
		// when
		List<PurchasedProductEntity> listOfProducts = purchasedProductRepository.findByProductName(productName);
		// then
		assertNotNull(listOfProducts);
		assertEquals(19, listOfProducts.size());
	}

	@Test
	public void shouldFindListProductsWithTransactionInProgress() {
		// given
		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity customer1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();
		CustomerEntity savedCustomer = customerRepository.save(customer1);

		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(123.0).withWeight(12.0).build();
		PurchasedProductEntity product2 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("basket")
				.withPrice(12.0).withWeight(12.0).build();
		PurchasedProductEntity product3 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("egg")
				.withPrice(4.0).withWeight(12.0).build();
		PurchasedProductEntity product4 = new PurchasedProductEntityBuilder().withMargin(12.0).withProductName("cup")
				.withPrice(44.0).withWeight(31.0).build();

		PurchasedProductEntity saveProduct1 = purchasedProductRepository.save(product1);
		PurchasedProductEntity saveProduct2 = purchasedProductRepository.save(product2);
		PurchasedProductEntity saveProduct3 = purchasedProductRepository.save(product3);
		PurchasedProductEntity saveProduct4 = purchasedProductRepository.save(product4);

		OrderEntity order1 = new OrderEntityBuilder().withAmount(2).withProductEntity(saveProduct1).build();
		OrderEntity order2 = new OrderEntityBuilder().withAmount(5).withProductEntity(saveProduct2).build();
		OrderEntity order3 = new OrderEntityBuilder().withAmount(2).withProductEntity(saveProduct3).build();
		OrderEntity order4 = new OrderEntityBuilder().withAmount(55).withProductEntity(saveProduct4).build();

		OrderEntity savedOrder1 = orderRepository.save(order1);
		OrderEntity savedOrder2 = orderRepository.save(order2);
		OrderEntity savedOrder3 = orderRepository.save(order3);
		OrderEntity savedOrder4 = orderRepository.save(order3);

		List<OrderEntity> orders = new ArrayList<>();
		orders.add(savedOrder1);
		orders.add(savedOrder2);
		orders.add(savedOrder3);
		orders.add(savedOrder4);

		TransactionEntity transactionEntity1 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.IN_PROGRESS).build();
		TransactionEntity transactionEntity2 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.ON_DELIVERY).build();
		TransactionEntity transactionEntity3 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.IN_PROGRESS).build();
		TransactionEntity transactionEntity4 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.IN_PROGRESS).build();

		TransactionEntity saved1 = transactionRepository.save(transactionEntity1);
		TransactionEntity saved2 = transactionRepository.save(transactionEntity2);
		TransactionEntity saved3 = transactionRepository.save(transactionEntity3);
		TransactionEntity saved4 = transactionRepository.save(transactionEntity4);

		List<TransactionEntity> allTransactions = transactionRepository.findAll();

		orders.forEach(order -> order.setTransactionEntity(saved1));
		orders.forEach(order -> order.setTransactionEntity(saved2));
		orders.forEach(order -> order.setTransactionEntity(saved3));
		orders.forEach(order -> order.setTransactionEntity(saved4));

		// when
		List<PurchasedProductTOWithNameAndAmount> productsInProgress = purchasedProductRepository
				.findListProductsWithTransactionInProgress();

		// then
		assertEquals(0, productsInProgress.size());
		assertEquals(8, allTransactions.size());

	}

	@Test
	public void shouldFindBestSellingProducts() {
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
				.withOrders(orders).withTransactionStatus(TransactionStatus.IN_PROGRESS).build();
		TransactionEntity transactionEntity2 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.ON_DELIVERY).build();
		TransactionEntity transactionEntity3 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.IN_PROGRESS).build();
		TransactionEntity transactionEntity4 = new TransactionEntityBuilder().withCustomerEntity(customer1)
				.withOrders(orders).withTransactionStatus(TransactionStatus.IN_PROGRESS).build();

		TransactionEntity saved = transactionRepository.save(transactionEntity1);
		transactionRepository.save(transactionEntity2);
		transactionRepository.save(transactionEntity3);
		transactionRepository.save(transactionEntity4);

		TransactionEntity found = transactionRepository.findById(saved.getId());
		List<OrderEntity> list = found.getOrders();

		final int PRO1_QUANTITY = 5;
		final int PRO2_QUANTITY = 3;
		final int PRO3_QUANTITY = 8;
		final int PRO4_QUANTITY = 1;
		final int PRO5_QUANTITY = 2;
		final int PRO6_QUANTITY = 9;
		final int PRO7_QUANTITY = 7;
		final int PRO8_QUANTITY = 4;
		final int PRO9_QUANTITY = 6;
		final int PRO10_QUANTITY = 10;

		final int RESULT_LIST_SIZE = 5;

		// when

		List<PurchasedProductEntity> resultList = purchasedProductRepository.getBestSellingProducts(RESULT_LIST_SIZE);

		// then

		assertEquals((Integer) RESULT_LIST_SIZE, (Integer) resultList.size());

	}

}

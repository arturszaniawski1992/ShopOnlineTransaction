package com.capgemini.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
public class CustomerRepositioryTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private PurchasedProductRepository purchasedProductRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	public void shouldFindCustomerById() {
		// given
		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity cust1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();

		CustomerEntity savedCustomer = customerRepository.save(cust1);

		// when
		CustomerEntity selectedCustomer = customerRepository.findById(savedCustomer.getId());

		// then
		assertThat(savedCustomer.getFirstName()).isEqualTo(selectedCustomer.getFirstName());
		assertThat(savedCustomer.getId()).isEqualTo(selectedCustomer.getId());
	}

	@Test
	public void shouldFindCustomerByFirstNameAndLastName() {
		// given
		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity cust1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();
		CustomerEntity cust2 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();

		customerRepository.save(cust1);
		customerRepository.save(cust2);

		// when
		List<CustomerEntity> listOfCustomers = customerRepository.findByFirstNameAndLastName("Artur", "Szaniawski");
		// then
		assertNotNull(listOfCustomers);
		assertEquals(2, listOfCustomers.size());
	}

	@Test
	public void shouldRemoveCustomerById() {
		// given
		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity cust1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();

		CustomerEntity savedCustomer = customerRepository.save(cust1);

		// when

		customerRepository.deleteById(savedCustomer.getId());
		// then
		assertEquals(0, customerRepository.count());
	}

	@Test
	public void shouldRemoveCustomerByLastName() {
		// given
		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity cust1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();

		CustomerEntity savedCustomer = customerRepository.save(cust1);

		// when
		customerRepository.deleteByLastName(savedCustomer.getLastName());
		// then
		assertEquals(0, customerRepository.count());
	}

	@Test
	public void shoulCantFindClientByFirstNameAndLastName() {

		// given
		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity cust1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();
		CustomerEntity cust2 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();
		CustomerEntity cust3 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();

		customerRepository.save(cust1);
		customerRepository.save(cust2);
		customerRepository.save(cust3);

		// when
		List<CustomerEntity> findClient = customerRepository.findByFirstNameAndLastName("xxx", "xxx");

		// then
		assertEquals(0, findClient.size());
	}

	@Test
	public void shouldFindClientsWhoSpentTheMostMoney() {
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
		// when
		List<CustomerEntity> customers = customerRepository.findTopThreeClientsWhoSpentTheMostInPeriod((short) 2,
				(short) 2018, (short) 4, (short) 2018, 3);

		// then
	}
}

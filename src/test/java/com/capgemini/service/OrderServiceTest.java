package com.capgemini.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.domain.OrderEntity;
import com.capgemini.domain.OrderEntity.OrderEntityBuilder;
import com.capgemini.exception.NoValidConnection;
import com.capgemini.types.AdressDataTO;
import com.capgemini.types.AdressDataTO.AdressDataTOBuilder;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.CustomerTO.CustomerTOBuilder;
import com.capgemini.types.OrderTO;
import com.capgemini.types.OrderTO.OrderTOBuilder;
import com.capgemini.types.PurchasedProductTO;
import com.capgemini.types.PurchasedProductTO.PurchasedProductTOBuilder;
import com.capgemini.types.TransactionTO;
import com.capgemini.types.TransactionTO.TransactionTOBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class OrderServiceTest {
	@Autowired
	private OrderService orderService;

	@Autowired
	PurchasedProductService purchasedProductService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TransactionService transactionService;

	@Test
	public void shouldTesVersion() throws NoValidConnection {
		// given
		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);
		PurchasedProductTO product2 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("dssd")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct2 = purchasedProductService.savePurchasedProduct(product2);

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		TransactionTO transaction = new TransactionTOBuilder().withCustomerId(savedCustomer.getId()).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		OrderTO order = new OrderTOBuilder().withAmount(15).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction.getId()).build();
		OrderTO savedOrder = orderService.saveOrder(order);
		// when
		savedOrder.setProductTOId(savedProduct2.getId());
		Long ver1 = orderService.findOrderById(savedOrder.getId()).getVersion();
		orderService.updateOrder(savedOrder);
		Long ver2 = orderService.findOrderById(savedOrder.getId()).getVersion();
		Long verTest = 1L;
		// then
		assertThat(ver1).isNotEqualTo(ver2);
		assertEquals(verTest, ver2);
	}

	@Test
	public void shouldFindOrderById() throws NoValidConnection {
		// given

		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		TransactionTO transaction = new TransactionTOBuilder().withCustomerId(savedCustomer.getId()).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		OrderTO order = new OrderTOBuilder().withAmount(45).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction.getId()).build();

		OrderTO savedOrder = orderService.saveOrder(order);

		// when
		OrderTO selectedOrder = orderService.findOrderById(savedOrder.getId());

		// then
		assertNotNull(savedOrder);
		assertNotNull(savedOrder.getId());
		assertEquals(savedProduct.getId(), savedOrder.getProductTOId());
		assertEquals(savedTransaction.getId(), savedOrder.getTransactionTOId());

	}

	@Test
	public void shouldRemoveOrderById() throws NoValidConnection {
		// given

		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		TransactionTO transaction = new TransactionTOBuilder().withCustomerId(savedCustomer.getId()).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		OrderTO order1 = new OrderTOBuilder().withAmount(45).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction.getId()).build();
		OrderTO order2 = new OrderTOBuilder().withAmount(3).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction.getId()).build();
		OrderTO order3 = new OrderTOBuilder().withAmount(12).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction.getId()).build();

		OrderTO savedOrder1 = orderService.saveOrder(order1);
		OrderTO savedOrder2 = orderService.saveOrder(order2);
		OrderTO savedOrder3 = orderService.saveOrder(order3);

		// when
		orderService.removeOrder(savedOrder1.getId());

		// then
		assertNotNull(orderService.findAllOrders());
		assertEquals(2, orderService.findAllOrders().size());

	}

	@Test(expected = NoValidConnection.class)
	public void shoudlReturnError() throws NoValidConnection {
		// given

		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		OrderTO order1 = new OrderTOBuilder().withAmount(45).withProductTOId(savedProduct.getId()).build();
		// when
		OrderTO savedOrder1 = orderService.saveOrder(order1);

	}

}

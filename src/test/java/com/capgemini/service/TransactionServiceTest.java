package com.capgemini.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.enums.TransactionStatus;
import com.capgemini.exception.TransactionNotAllowedException;
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
public class TransactionServiceTest {

	@Autowired
	TransactionService transactionService;
	@Autowired
	CustomerService customerService;
	@Autowired
	PurchasedProductService purchasedProductService;
	@Autowired
	OrderService orderService;

	@Test
	public void shouldTesVersion() {
		// given
		Integer amount = 12;
		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);
		List<Long> products = new ArrayList<>();
		products.add(savedProduct.getId());

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		TransactionTO transaction = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		// when
		savedTransaction.setTransactionStatus(TransactionStatus.CANCELLED);
		Long ver1 = transactionService.findTransactionById(savedTransaction.getId()).getVersion();
		transactionService.updateTransaction(savedTransaction);

		Long ver2 = transactionService.findTransactionById(savedTransaction.getId()).getVersion();

		Long verTest = 2L;
		// then
		assertThat(ver1).isNotEqualTo(ver2);
		assertEquals(verTest, ver2);
	}

	@Test
	public void shouldFindTransactionById() {
		// given
		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);
		List<Long> products = new ArrayList<>();
		products.add(savedProduct.getId());

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		TransactionTO transaction = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		// when
		TransactionTO selectedTransaction = transactionService.findTransactionById(savedTransaction.getId());

		// then
		assertThat(savedTransaction.getAmount()).isEqualTo(selectedTransaction.getAmount());
		assertThat(savedTransaction.getId()).isEqualTo(selectedTransaction.getId());
	}

	@Test(expected = TransactionNotAllowedException.class)
	public void testTransactionNotAllowedExceptions() {
		PurchasedProductTO product1 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(10.0).build();
		PurchasedProductTO product2 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(10.0).build();
		PurchasedProductTO product3 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(10.0).build();
		PurchasedProductTO savedProduct1 = purchasedProductService.savePurchasedProduct(product1);
		PurchasedProductTO savedProduct2 = purchasedProductService.savePurchasedProduct(product2);
		PurchasedProductTO savedProduct3 = purchasedProductService.savePurchasedProduct(product3);

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();
		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		OrderTO order1 = new OrderTOBuilder().withAmount(5).withProductTOId(savedProduct1.getId()).build();
		OrderTO order2 = new OrderTOBuilder().withAmount(4).withProductTOId(savedProduct2.getId()).build();
		OrderTO order3 = new OrderTOBuilder().withAmount(1).withProductTOId(savedProduct3.getId()).build();
		OrderTO order4 = new OrderTOBuilder().withAmount(3).withProductTOId(savedProduct1.getId()).build();
		OrderTO savedO1 = orderService.saveOrder(order1);
		OrderTO savedO2 = orderService.saveOrder(order2);
		OrderTO savedO3 = orderService.saveOrder(order3);
		OrderTO savedO4 = orderService.saveOrder(order4);

		List<Long> orders = new ArrayList();
		orders.add(savedO1.getId());
		orders.add(savedO2.getId());
		orders.add(savedO3.getId());
		orders.add(savedO4.getId());

		TransactionTO transaction = new TransactionTOBuilder().withCustomerId(savedCustomer.getId()).withOrders(orders)
				.build();
		transactionService.saveTransaction(transaction);

	}

}

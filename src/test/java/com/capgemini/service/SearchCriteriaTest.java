package com.capgemini.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.enums.TransactionStatus;
import com.capgemini.exception.NoValidConnection;
import com.capgemini.types.AdressDataTO;
import com.capgemini.types.AdressDataTO.AdressDataTOBuilder;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.CustomerTO.CustomerTOBuilder;
import com.capgemini.types.OrderTO;
import com.capgemini.types.OrderTO.OrderTOBuilder;
import com.capgemini.types.PurchasedProductTO;
import com.capgemini.types.PurchasedProductTO.PurchasedProductTOBuilder;
import com.capgemini.types.TransactionSearchCriteria;
import com.capgemini.types.TransactionTO;
import com.capgemini.types.TransactionTO.TransactionTOBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class SearchCriteriaTest {
	@Autowired
	TransactionService transactionService;
	@Autowired
	CustomerService customerService;
	@Autowired
	PurchasedProductService purchasedProductService;
	@Autowired
	OrderService orderService;

	@Test
	public void shouldFindTransactionsByAmountUsingSearchCriteria() throws NoValidConnection {
		// given
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

		TransactionTO transaction = new TransactionTOBuilder().withAmount(10).withCustomerId(savedCustomer.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);
		OrderTO order = new OrderTOBuilder().withAmount(10).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction.getId()).build();
		OrderTO savedOrder = orderService.saveOrder(order);
		// when
		TransactionSearchCriteria searchCriteria = new TransactionSearchCriteria();
		searchCriteria.setTotalTransactionAmount(1250.0);
		List<TransactionTO> resultList = transactionService.searchForTransactionsBySearchCriteria(searchCriteria);

		// then
		assertNotNull(resultList);
		assertEquals(1, resultList.size());
	}

	@Test
	public void shouldFindTransactionsByProductUsingSearchCriteria() throws NoValidConnection {
		// given
		PurchasedProductTO product1 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(10.0).build();
		PurchasedProductTO savedProduct1 = purchasedProductService.savePurchasedProduct(product1);

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();
		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		Date dateTransaction = java.sql.Date.valueOf("2017-11-15");
		TransactionTO transaction1 = new TransactionTOBuilder().withAmount(15).withDateTransaction(dateTransaction)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction1 = transactionService.saveTransaction(transaction1);

		TransactionTO transaction2 = new TransactionTOBuilder().withAmount(15).withDateTransaction(dateTransaction)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction2 = transactionService.saveTransaction(transaction2);

		OrderTO order1 = new OrderTOBuilder().withAmount(15).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction1.getId()).build();

		OrderTO order2 = new OrderTOBuilder().withAmount(15).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction2.getId()).build();
		OrderTO savedOrder1 = orderService.saveOrder(order2);
		OrderTO savedOrder2 = orderService.saveOrder(order2);

		customerService.assignTransaction(savedCustomer, savedTransaction1);
		transactionService.assignCustomer(savedTransaction1, savedCustomer);
		customerService.assignTransaction(savedCustomer, savedTransaction2);
		transactionService.assignCustomer(savedTransaction2, savedCustomer);
		// when
		TransactionSearchCriteria searchCriteria = new TransactionSearchCriteria();
		searchCriteria.setProductId(savedProduct1.getId());
		List<TransactionTO> resultList = transactionService.searchForTransactionsBySearchCriteria(searchCriteria);

		// then
		assertNotNull(resultList);
		assertEquals(1, resultList.size());
	}

	@Test
	public void shouldFindTransactionsByDateToUsingSearchCriteria() throws NoValidConnection {
		// given
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

		Date date1 = new Date(1500L);

		TransactionTO transaction1 = new TransactionTOBuilder().withAmount(15).withDateTransaction(date1)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction1 = transactionService.saveTransaction(transaction1);

		TransactionTO transaction2 = new TransactionTOBuilder().withAmount(98).withDateTransaction(date1)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction2 = transactionService.saveTransaction(transaction2);

		OrderTO order = new OrderTOBuilder().withAmount(15).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction1.getId()).build();
		OrderTO savedOrder = orderService.saveOrder(order);

		OrderTO order2 = new OrderTOBuilder().withAmount(15).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction2.getId()).build();
		OrderTO savedOrder2 = orderService.saveOrder(order2);

		customerService.assignTransaction(savedCustomer, savedTransaction1);
		transactionService.assignCustomer(savedTransaction1, savedCustomer);
		customerService.assignTransaction(savedCustomer, savedTransaction2);
		transactionService.assignCustomer(savedTransaction2, savedCustomer);
		// when
		Date dateFrom = new Date(1000L);
		Date dateTo = new Date(2000L);
		List<TransactionTO> transactions = transactionService.findAllTranactions();
		TransactionSearchCriteria searchCriteria = new TransactionSearchCriteria();
		searchCriteria.setDateFrom(dateFrom);
		searchCriteria.setDateTo(dateTo);
		List<TransactionTO> resultList = transactionService.searchForTransactionsBySearchCriteria(searchCriteria);

		// then

		assertEquals(date1, savedTransaction1.getDateTransaction());
		assertNotNull(resultList);
		assertEquals(2, resultList.size());
		assertEquals(2, transactions.size());
	}

	@Test
	public void shouldFindTransactionsByCustomerNameProductUsingSearchCriteria() throws NoValidConnection {
		// given
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
		CustomerTO cust2 = new CustomerTOBuilder().withFirstName("Adam").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer2 = customerService.saveCustomer(cust2);
		Date dateTransaction = java.sql.Date.valueOf("2018-11-15");

		TransactionTO transaction = new TransactionTOBuilder().withAmount(15).withDateTransaction(dateTransaction)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);
		TransactionTO transaction2 = new TransactionTOBuilder().withAmount(15).withDateTransaction(dateTransaction)
				.withCustomerId(savedCustomer2.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction2 = transactionService.saveTransaction(transaction2);
		customerService.assignTransaction(savedCustomer, savedTransaction);
		transactionService.assignCustomer(savedTransaction, savedCustomer);

		OrderTO order1 = new OrderTOBuilder().withAmount(10).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction.getId()).build();
		OrderTO savedOrder = orderService.saveOrder(order1);
		List<Long> orders = new ArrayList<>();
		orders.add(order1.getId());
		// when
		TransactionSearchCriteria searchCriteria = new TransactionSearchCriteria();
		searchCriteria.setCustomerName("Szaniawski");
		List<TransactionTO> resultList = transactionService.searchForTransactionsBySearchCriteria(searchCriteria);

		// then
		assertNotNull(resultList);
		assertEquals(1, resultList.size());
	}

	@Test
	public void shouldFindTransactionsByCustomerNameAndProductUsingSearchCriteria() throws NoValidConnection {
		// given
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
		CustomerTO cust2 = new CustomerTOBuilder().withFirstName("Adam").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer2 = customerService.saveCustomer(cust2);
		Date dateTransaction = java.sql.Date.valueOf("2018-11-15");

		TransactionTO transaction = new TransactionTOBuilder().withAmount(15).withDateTransaction(dateTransaction)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);
		TransactionTO transaction2 = new TransactionTOBuilder().withAmount(15).withDateTransaction(dateTransaction)
				.withCustomerId(savedCustomer2.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction2 = transactionService.saveTransaction(transaction2);
		customerService.assignTransaction(savedCustomer, savedTransaction);
		transactionService.assignCustomer(savedTransaction, savedCustomer);

		OrderTO order1 = new OrderTOBuilder().withAmount(10).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction.getId()).build();
		OrderTO savedOrder = orderService.saveOrder(order1);
		List<Long> orders = new ArrayList<>();
		orders.add(order1.getId());
		// when
		TransactionSearchCriteria searchCriteria = new TransactionSearchCriteria();
		searchCriteria.setCustomerName("Szaniawski");
		searchCriteria.setProductId(savedProduct1.getId());
		List<TransactionTO> resultList = transactionService.searchForTransactionsBySearchCriteria(searchCriteria);

		// then
		assertNotNull(resultList);
		assertEquals(1, resultList.size());
	}

	@Test
	public void shouldFindTransactionsByDateByCustomerNameByAmountByProductNameUsingSearchCriteria()
			throws NoValidConnection {
		// given
		PurchasedProductTO product1 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(1000.0).withWeight(10.0).build();
		PurchasedProductTO product2 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(10.0).build();
		PurchasedProductTO product3 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(10.0).build();
		PurchasedProductTO savedProduct1 = purchasedProductService.savePurchasedProduct(product1);

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();
		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		Date date1 = new Date(1500L);

		TransactionTO transaction1 = new TransactionTOBuilder().withAmount(1).withDateTransaction(date1)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction1 = transactionService.saveTransaction(transaction1);

		TransactionTO transaction2 = new TransactionTOBuilder().withAmount(1).withDateTransaction(date1)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction2 = transactionService.saveTransaction(transaction2);

		OrderTO order = new OrderTOBuilder().withAmount(1).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction1.getId()).build();
		OrderTO savedOrder = orderService.saveOrder(order);

		OrderTO order2 = new OrderTOBuilder().withAmount(1).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction1.getId()).build();
		OrderTO savedOrder2 = orderService.saveOrder(order2);

		customerService.assignTransaction(savedCustomer, savedTransaction1);
		transactionService.assignCustomer(savedTransaction1, savedCustomer);
		customerService.assignTransaction(savedCustomer, savedTransaction2);
		transactionService.assignCustomer(savedTransaction2, savedCustomer);
		// when
		Date dateFrom = new Date(1000L);
		Date dateTo = new Date(2000L);
		List<TransactionTO> transactions = transactionService.findAllTranactions();
		TransactionSearchCriteria searchCriteria = new TransactionSearchCriteria();
		searchCriteria.setDateFrom(dateFrom);
		searchCriteria.setDateTo(dateTo);
		searchCriteria.setCustomerName("Szaniawski");
		searchCriteria.setProductId(savedProduct1.getId());
		searchCriteria.setTotalTransactionAmount(2000.0);
		List<TransactionTO> resultList = transactionService.searchForTransactionsBySearchCriteria(searchCriteria);

		// then

		assertEquals(date1, savedTransaction1.getDateTransaction());
		assertNotNull(resultList);
		assertEquals(1, resultList.size());
		assertEquals(2, transactions.size());
		assertEquals(savedProduct1.getId(), order.getProductTOId());
	}

	@Test
	public void shouldFindAllTransactionsWithNoCriteria() throws NoValidConnection {
		// given
		PurchasedProductTO product1 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(1000.0).withWeight(10.0).build();
		PurchasedProductTO product2 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(10.0).build();
		PurchasedProductTO product3 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(10.0).build();
		PurchasedProductTO savedProduct1 = purchasedProductService.savePurchasedProduct(product1);

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();
		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		Date date1 = new Date(1500L);

		TransactionTO transaction1 = new TransactionTOBuilder().withAmount(1).withDateTransaction(date1)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction1 = transactionService.saveTransaction(transaction1);

		TransactionTO transaction2 = new TransactionTOBuilder().withAmount(1).withDateTransaction(date1)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction2 = transactionService.saveTransaction(transaction2);

		OrderTO order = new OrderTOBuilder().withAmount(1).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction1.getId()).build();
		OrderTO savedOrder = orderService.saveOrder(order);

		OrderTO order2 = new OrderTOBuilder().withAmount(1).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction2.getId()).build();
		OrderTO savedOrder2 = orderService.saveOrder(order2);

		customerService.assignTransaction(savedCustomer, savedTransaction1);
		transactionService.assignCustomer(savedTransaction1, savedCustomer);
		customerService.assignTransaction(savedCustomer, savedTransaction2);
		transactionService.assignCustomer(savedTransaction2, savedCustomer);
		// when
		Date dateFrom = new Date(1000L);
		Date dateTo = new Date(2000L);
		List<TransactionTO> transactions = transactionService.findAllTranactions();
		TransactionSearchCriteria searchCriteria = new TransactionSearchCriteria();

		List<TransactionTO> resultList = transactionService.searchForTransactionsBySearchCriteria(searchCriteria);

		// then

		assertEquals(date1, savedTransaction1.getDateTransaction());
		assertNotNull(resultList);
		assertEquals(2, resultList.size());
	}

	@Test
	public void shouldFindAllTransactionsWithAllCriteria() throws NoValidConnection {
		// given
		PurchasedProductTO product1 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(1000.0).withWeight(10.0).build();
		PurchasedProductTO product2 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(10.0).build();
		PurchasedProductTO product3 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(10.0).build();
		PurchasedProductTO savedProduct1 = purchasedProductService.savePurchasedProduct(product1);

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();
		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		Date date1 = new Date(1500L);

		TransactionTO transaction1 = new TransactionTOBuilder().withAmount(1).withDateTransaction(date1)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction1 = transactionService.saveTransaction(transaction1);

		TransactionTO transaction2 = new TransactionTOBuilder().withAmount(1).withDateTransaction(date1)
				.withCustomerId(savedCustomer.getId()).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction2 = transactionService.saveTransaction(transaction2);

		OrderTO order = new OrderTOBuilder().withAmount(1).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction1.getId()).build();
		OrderTO savedOrder = orderService.saveOrder(order);

		OrderTO order2 = new OrderTOBuilder().withAmount(1).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction2.getId()).build();
		OrderTO savedOrder2 = orderService.saveOrder(order2);

		customerService.assignTransaction(savedCustomer, savedTransaction1);
		transactionService.assignCustomer(savedTransaction1, savedCustomer);
		customerService.assignTransaction(savedCustomer, savedTransaction2);
		transactionService.assignCustomer(savedTransaction2, savedCustomer);
		// when
		Date dateFrom = new Date(1000L);
		Date dateTo = new Date(2000L);
		List<TransactionTO> transactions = transactionService.findAllTranactions();
		TransactionSearchCriteria searchCriteria = new TransactionSearchCriteria();
		searchCriteria.setDateFrom(dateFrom);
		List<TransactionTO> resultList = transactionService.searchForTransactionsBySearchCriteria(searchCriteria);

		// then

		assertEquals(date1, savedTransaction1.getDateTransaction());
		assertNotNull(resultList);
		assertEquals(2, resultList.size());
	}

}

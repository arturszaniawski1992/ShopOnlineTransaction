package com.capgemini.service;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.enums.TransactionStatus;
import com.capgemini.exception.NoValidConnection;
import com.capgemini.exception.TransactionNotAllowedException;
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

	@Test(expected = NoValidConnection.class)
	public void testTransactionNotAllowedExceptions() throws NoValidConnection {
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

		TransactionTO transaction = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		OrderTO order1 = new OrderTOBuilder().withAmount(5).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction.getId()).build();
		OrderTO order2 = new OrderTOBuilder().withAmount(4).withProductTOId(savedProduct2.getId())
				.withTransactionTO(savedTransaction.getId()).build();
		OrderTO order3 = new OrderTOBuilder().withAmount(1).withProductTOId(savedProduct3.getId())
				.withTransactionTO(savedTransaction.getId()).build();
		OrderTO order4 = new OrderTOBuilder().withAmount(3).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction.getId()).build();
		OrderTO savedO1 = orderService.saveOrder(order1);
		OrderTO savedO2 = orderService.saveOrder(order2);
		OrderTO savedO3 = orderService.saveOrder(order3);
		OrderTO savedO4 = orderService.saveOrder(order4);

		List<Long> orders = new ArrayList();
		orders.add(savedO1.getId());
		orders.add(savedO2.getId());
		orders.add(savedO3.getId());
		orders.add(savedO4.getId());

		TransactionTO transaction1 = new TransactionTOBuilder().withCustomerId(savedCustomer.getId()).withOrders(orders)
				.build();
		transactionService.saveTransaction(transaction);

	}

	@Test
	public void shouldFindAllTransactions() {
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

		TransactionTO transaction = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		// when
		List<TransactionTO> selectedTransaction = transactionService.findAllTranactions();

		// then
		assertEquals(1, selectedTransaction.size());
	}

	@Test
	public void shouldFindTransactionsByPriceUsingSearchCriteria() {
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

		TransactionTO transaction = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		// when
		TransactionSearchCriteria searchCriteria = new TransactionSearchCriteria();
		searchCriteria.setTotalTransactionAmount(1.0);
		List<TransactionTO> resultList = transactionService.searchForTransactionsBySearchCriteria(searchCriteria);

		// then
		assertNotNull(resultList);
		assertEquals((Integer) 0, (Integer) resultList.size());
	}

	@Test
	public void shouldFindTransactionsByCustomerNameUsingSearchCriteria() {
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

		TransactionTO transaction = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		// when
		TransactionSearchCriteria searchCriteria = new TransactionSearchCriteria();
		searchCriteria.setCustomerName(savedCustomer.getLastName());
		List<TransactionTO> resultList = transactionService.searchForTransactionsBySearchCriteria(searchCriteria);

		// then
		assertNotNull(resultList);
		assertEquals((Integer) 1, (Integer) resultList.size());
	}

	@Test
	public void shouldAssignTransactionToCustomer() {

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("sd").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);
		TransactionTO transaction = new TransactionTOBuilder().withCustomerId(savedCustomer.getId()).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		// when
		TransactionTO transactionWithCustomer = transactionService.assignCustomer(savedTransaction, savedCustomer);

		// then

		assertNotNull(savedTransaction.getId());
		assertEquals(savedCustomer.getId(), transactionWithCustomer.getCustomerId());

	}

	@Test
	public void shouldFindSumOfTransactionInDifferentStatus() throws NoValidConnection {
		// given

		final Date date1 = java.sql.Date.valueOf("2017-11-15");
		final Date date2 = java.sql.Date.valueOf("2017-12-15");
		final Date date3 = java.sql.Date.valueOf("2018-01-15");
		final Date date4 = java.sql.Date.valueOf("2018-02-15");
		final Date date5 = java.sql.Date.valueOf("2018-03-15");

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("malysz").withAdressData(adress)
				.withMobile("79845654").build();
		CustomerTO savedCustomer1 = customerService.saveCustomer(cust1);

		CustomerTO cust2 = new CustomerTOBuilder().withFirstName("Jan").withLastName("kowalski").withAdressData(adress)
				.withMobile("78545121").build();
		CustomerTO savedCustomer2 = customerService.saveCustomer(cust2);

		CustomerTO cust3 = new CustomerTOBuilder().withFirstName("Karol").withLastName("opcje").withAdressData(adress)
				.withMobile("878952").build();
		CustomerTO savedCustomer3 = customerService.saveCustomer(cust3);

		CustomerTO cust4 = new CustomerTOBuilder().withFirstName("Ewa").withLastName("miska").withAdressData(adress)
				.withMobile("546546").build();
		CustomerTO savedCustomer4 = customerService.saveCustomer(cust4);

		CustomerTO cust5 = new CustomerTOBuilder().withFirstName("Adam").withLastName("janusz").withAdressData(adress)
				.withMobile("546546").build();
		CustomerTO savedCustomer5 = customerService.saveCustomer(cust5);

		CustomerTO cust6 = new CustomerTOBuilder().withFirstName("Anna").withLastName("Pol").withAdressData(adress)
				.withMobile("7978562").build();
		CustomerTO savedCustomer6 = customerService.saveCustomer(cust6);

		CustomerTO cust7 = new CustomerTOBuilder().withFirstName("Kamil").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("879875").build();
		CustomerTO savedCustomer7 = customerService.saveCustomer(cust7);

		CustomerTO cust8 = new CustomerTOBuilder().withFirstName("Ewelina").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("987989755").build();
		CustomerTO savedCustomer8 = customerService.saveCustomer(cust8);

		TransactionTO transaction1 = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer1.getId())
				.withTransactionStatus(TransactionStatus.IN_PROGRESS).withDateTransaction(date1).build();
		TransactionTO savedTransaction1 = transactionService.saveTransaction(transaction1);
		TransactionTO transaction2 = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer2.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).withDateTransaction(date2).build();
		TransactionTO savedTransaction2 = transactionService.saveTransaction(transaction2);
		TransactionTO transaction3 = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer3.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).withDateTransaction(date3).build();
		TransactionTO savedTransaction3 = transactionService.saveTransaction(transaction3);
		TransactionTO transaction4 = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer4.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).withDateTransaction(date4).build();
		TransactionTO savedTransaction4 = transactionService.saveTransaction(transaction4);
		TransactionTO transaction5 = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer5.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).withDateTransaction(date5).build();
		TransactionTO savedTransaction5 = transactionService.saveTransaction(transaction5);

		customerService.assignTransaction(savedCustomer1, savedTransaction1);
		transactionService.assignCustomer(savedTransaction1, savedCustomer1);
		customerService.assignTransaction(savedCustomer2, savedTransaction2);
		transactionService.assignCustomer(savedTransaction2, savedCustomer2);
		customerService.assignTransaction(savedCustomer3, savedTransaction3);
		transactionService.assignCustomer(savedTransaction3, savedCustomer3);
		customerService.assignTransaction(savedCustomer4, savedTransaction4);
		transactionService.assignCustomer(savedTransaction4, savedCustomer4);
		customerService.assignTransaction(savedCustomer5, savedTransaction5);
		transactionService.assignCustomer(savedTransaction5, savedCustomer5);

		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);

		OrderTO order1 = new OrderTOBuilder().withAmount(2).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction1.getId()).build();
		OrderTO savedOrder1 = orderService.saveOrder(order1);

		OrderTO order2 = new OrderTOBuilder().withAmount(3).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction2.getId()).build();
		OrderTO savedOrder2 = orderService.saveOrder(order2);

		OrderTO order3 = new OrderTOBuilder().withAmount(4).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction3.getId()).build();
		OrderTO savedOrder3 = orderService.saveOrder(order3);

		OrderTO order4 = new OrderTOBuilder().withAmount(6).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction4.getId()).build();
		OrderTO savedOrder4 = orderService.saveOrder(order4);

		OrderTO order5 = new OrderTOBuilder().withAmount(10).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction5.getId()).build();
		OrderTO savedOrder5 = orderService.saveOrder(order5);

		// when
		Double result = transactionService.getTotalAmountOfTransactionsWithStatus(savedCustomer1.getId(),
				transaction1.getTransactionStatus());

		assertNotNull(result);
		assertThat(result = 125.0);

	}

	@Test
	public void shouldCalculateProfitFromPeriod() throws NoValidConnection {
		final Date date1 = java.sql.Date.valueOf("2017-11-15");
		final Date date2 = java.sql.Date.valueOf("2017-12-15");
		final Date date3 = java.sql.Date.valueOf("2018-01-15");
		final Date date4 = java.sql.Date.valueOf("2018-02-15");
		final Date date5 = java.sql.Date.valueOf("2018-03-15");

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("malysz").withAdressData(adress)
				.withMobile("79845654").build();
		CustomerTO savedCustomer1 = customerService.saveCustomer(cust1);

		CustomerTO cust2 = new CustomerTOBuilder().withFirstName("Jan").withLastName("kowalski").withAdressData(adress)
				.withMobile("78545121").build();
		CustomerTO savedCustomer2 = customerService.saveCustomer(cust2);

		TransactionTO transaction1 = new TransactionTOBuilder().withAmount(1).withCustomerId(savedCustomer1.getId())
				.withTransactionStatus(TransactionStatus.IN_PROGRESS).withDateTransaction(date1).build();
		TransactionTO savedTransaction1 = transactionService.saveTransaction(transaction1);
		TransactionTO transaction2 = new TransactionTOBuilder().withAmount(1).withCustomerId(savedCustomer2.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).withDateTransaction(date2).build();
		TransactionTO savedTransaction2 = transactionService.saveTransaction(transaction1);

		customerService.assignTransaction(savedCustomer1, savedTransaction1);
		transactionService.assignCustomer(savedTransaction1, savedCustomer1);
		customerService.assignTransaction(savedCustomer2, savedTransaction2);
		transactionService.assignCustomer(savedTransaction2, savedCustomer2);

		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(10.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);

		OrderTO order1 = new OrderTOBuilder().withAmount(2).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction1.getId()).build();
		OrderTO savedOrder1 = orderService.saveOrder(order1);

		OrderTO order2 = new OrderTOBuilder().withAmount(3).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction2.getId()).build();
		OrderTO savedOrder2 = orderService.saveOrder(order2);

		Date dateFrom = java.sql.Date.valueOf("1992-11-15");
		Date dateTo = java.sql.Date.valueOf("2018-11-15");
		// when
		transactionService.saveTransaction(transaction1);
		transactionService.saveTransaction(transaction2);
		Double result = transactionService.calculateProfitFromPeriod(dateFrom, dateTo);
		// then
		assertEquals(125.0, result, 0.001);
	}

	@Test
	public void shouldCalculateTotalCostOfCustomerTransactions() throws NoValidConnection {
		final Date date1 = java.sql.Date.valueOf("2017-11-15");
		final Date date2 = java.sql.Date.valueOf("2017-12-15");
		final Date date3 = java.sql.Date.valueOf("2018-01-15");
		final Date date4 = java.sql.Date.valueOf("2018-02-15");
		final Date date5 = java.sql.Date.valueOf("2018-03-15");

		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("malysz").withAdressData(adress)
				.withMobile("79845654").build();
		CustomerTO savedCustomer1 = customerService.saveCustomer(cust1);

		CustomerTO cust2 = new CustomerTOBuilder().withFirstName("Jan").withLastName("kowalski").withAdressData(adress)
				.withMobile("78545121").build();
		CustomerTO savedCustomer2 = customerService.saveCustomer(cust2);

		TransactionTO transaction1 = new TransactionTOBuilder().withAmount(1).withCustomerId(savedCustomer1.getId())
				.withTransactionStatus(TransactionStatus.IN_PROGRESS).withDateTransaction(date1).build();
		TransactionTO savedTransaction1 = transactionService.saveTransaction(transaction1);
		TransactionTO transaction2 = new TransactionTOBuilder().withAmount(1).withCustomerId(savedCustomer2.getId())
				.withTransactionStatus(TransactionStatus.EXECUTED).withDateTransaction(date2).build();
		TransactionTO savedTransaction2 = transactionService.saveTransaction(transaction1);

		customerService.assignTransaction(savedCustomer1, savedTransaction1);
		transactionService.assignCustomer(savedTransaction1, savedCustomer1);
		customerService.assignTransaction(savedCustomer2, savedTransaction2);
		transactionService.assignCustomer(savedTransaction2, savedCustomer2);

		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(10.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);

		OrderTO order1 = new OrderTOBuilder().withAmount(2).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction1.getId()).build();
		OrderTO savedOrder1 = orderService.saveOrder(order1);

		OrderTO order2 = new OrderTOBuilder().withAmount(3).withProductTOId(savedProduct.getId())
				.withTransactionTO(savedTransaction2.getId()).build();
		OrderTO savedOrder2 = orderService.saveOrder(order2);

		Date dateFrom = java.sql.Date.valueOf("1992-11-15");
		Date dateTo = java.sql.Date.valueOf("2018-11-15");
		// when
		Double result1 = transactionService.calculateTotalCostOfCustomerTransactions(savedCustomer1.getId());
		Double result2 = transactionService.calculateTotalCostOfCustomerTransactions(savedCustomer2.getId());
		// then
		assertEquals(20.0, result1, 0.001);
		assertEquals(30.0, result2, 0.001);
	}

	@Test(expected = TransactionNotAllowedException.class)
	public void shoudlThrowTransactionNotAllowed() {

	}

	@Test
	public void shouldRemoveTransaction() {
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
		transactionService.removeTransaction(savedTransaction.getId());

		// then
		assertNotNull(transactionService.findAllTranactions());

	}

}

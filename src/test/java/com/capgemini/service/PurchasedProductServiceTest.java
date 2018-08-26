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

import com.capgemini.enums.TransactionStatus;
import com.capgemini.exception.NoValidConnection;
import com.capgemini.mappers.PurchasedProductMapper;
import com.capgemini.types.AdressDataTO;
import com.capgemini.types.AdressDataTO.AdressDataTOBuilder;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.CustomerTO.CustomerTOBuilder;
import com.capgemini.types.OrderTO;
import com.capgemini.types.OrderTO.OrderTOBuilder;
import com.capgemini.types.PurchasedProductTO;
import com.capgemini.types.PurchasedProductTO.PurchasedProductTOBuilder;
import com.capgemini.types.PurchasedProductTOWithNameAndAmount;
import com.capgemini.types.TransactionTO;
import com.capgemini.types.TransactionTO.TransactionTOBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class PurchasedProductServiceTest {

	@Autowired
	private PurchasedProductService purchasedProductService;
	@Autowired
	CustomerService customerService;
	@Autowired
	TransactionService transactionService;
	@Autowired
	OrderService orderService;
	@Autowired
	PurchasedProductMapper productMapper;

	@Test
	public void shouldFindProductById() {
		// given
		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);

		// when
		PurchasedProductTO selectedProduct = purchasedProductService.findPurchasedProductById(savedProduct.getId());

		// then
		assertThat(savedProduct.getWeight()).isEqualTo(selectedProduct.getWeight());
		assertThat(savedProduct.getId()).isEqualTo(selectedProduct.getId());
	}

	@Test
	public void shouldFindAllProducts() {
		// given
		PurchasedProductTO product1 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(15.0).build();
		PurchasedProductTO product2 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(15.0).build();

		List<PurchasedProductTO> products = new ArrayList<>();
		products.add(purchasedProductService.savePurchasedProduct(product1));
		products.add(purchasedProductService.savePurchasedProduct(product2));
		// when
		List<PurchasedProductTO> selectedProducts = purchasedProductService.findAllPurchasedProducts();

		// then
		assertEquals(2, selectedProducts.size());
	}

	@Test
	public void shouldRemoveProduct() {
		// given
		PurchasedProductTO product1 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(15.0).build();
		PurchasedProductTO product2 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(15.0).build();
		PurchasedProductTO product3 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(15.0).build();
		PurchasedProductTO product4 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(15.0).build();

		purchasedProductService.savePurchasedProduct(product1);
		purchasedProductService.savePurchasedProduct(product2);
		purchasedProductService.savePurchasedProduct(product3);
		purchasedProductService.savePurchasedProduct(product4);

		// when

		List<PurchasedProductTO> products = purchasedProductService.findAllPurchasedProducts();
		purchasedProductService.removeProduct(product1.getId());

		// then
		assertEquals(4, products.size());

	}

	@Test
	public void shouldUpdateProduct() {
		// given

		Double newPrice = 15.0;
		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(1.0).withPrice(12.0)
				.withProductName("ball").withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);

		// when
		savedProduct.setPrice(newPrice);
		PurchasedProductTO updatedProduct = purchasedProductService.updateProduct(savedProduct);
		List<PurchasedProductTO> products = purchasedProductService.findAllPurchasedProducts();

		// then
		assertThat(updatedProduct.getPrice().equals(newPrice));
		assertThat(updatedProduct.getId()).isEqualTo(savedProduct.getId());
		assertThat(products.size()).isEqualTo(1);
	}

	@Test
	public void shouldFindListProductsWithTransactionInProgress() throws NoValidConnection {
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
				.withTransactionStatus(TransactionStatus.IN_PROGRESS).withDateTransaction(date3).build();
		TransactionTO savedTransaction3 = transactionService.saveTransaction(transaction3);
		TransactionTO transaction4 = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer4.getId())
				.withTransactionStatus(TransactionStatus.WAITING_FOR_PAYMENT).withDateTransaction(date4).build();
		TransactionTO savedTransaction4 = transactionService.saveTransaction(transaction4);
		TransactionTO transaction5 = new TransactionTOBuilder().withAmount(15).withCustomerId(savedCustomer5.getId())
				.withTransactionStatus(TransactionStatus.IN_PROGRESS).withDateTransaction(date5).build();
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

		PurchasedProductTO product1 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct1 = purchasedProductService.savePurchasedProduct(product1);
		PurchasedProductTO product2 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("egg")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct2 = purchasedProductService.savePurchasedProduct(product2);
		PurchasedProductTO product3 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("basket")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct3 = purchasedProductService.savePurchasedProduct(product3);

		OrderTO order1 = new OrderTOBuilder().withAmount(2).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction1.getId()).build();
		OrderTO savedOrder1 = orderService.saveOrder(order1);

		OrderTO order2 = new OrderTOBuilder().withAmount(3).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction2.getId()).build();
		OrderTO savedOrder2 = orderService.saveOrder(order2);

		OrderTO order3 = new OrderTOBuilder().withAmount(4).withProductTOId(savedProduct2.getId())
				.withTransactionTO(savedTransaction3.getId()).build();
		OrderTO savedOrder3 = orderService.saveOrder(order3);

		OrderTO order4 = new OrderTOBuilder().withAmount(6).withProductTOId(savedProduct3.getId())
				.withTransactionTO(savedTransaction4.getId()).build();
		OrderTO savedOrder4 = orderService.saveOrder(order4);

		OrderTO order5 = new OrderTOBuilder().withAmount(10).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction5.getId()).build();
		OrderTO savedOrder5 = orderService.saveOrder(order5);

		Date dateFrom1 = java.sql.Date.valueOf("2017-11-01");
		Date dateTo1 = java.sql.Date.valueOf("2018-01-01");
		Date dateFrom2 = java.sql.Date.valueOf("2017-10-01");
		Date dateTo2 = java.sql.Date.valueOf("2018-05-01");
		int amountOfClients = 3;
		// when
		List<PurchasedProductTOWithNameAndAmount> resultList1 = purchasedProductService
				.findListProductsWithTransactionInProgress();

		// then
		assertNotNull(resultList1);
		assertEquals((Integer) 2, (Integer) resultList1.size());
	}

	@Test
	public void shouldGetTenBestSellingProducts() throws NoValidConnection {
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
				.withTransactionStatus(TransactionStatus.EXECUTED).withDateTransaction(date1).build();
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

		PurchasedProductTO product1 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct1 = purchasedProductService.savePurchasedProduct(product1);
		PurchasedProductTO product2 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("egg")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct2 = purchasedProductService.savePurchasedProduct(product2);
		PurchasedProductTO product3 = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("basket")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct3 = purchasedProductService.savePurchasedProduct(product3);

		OrderTO order1 = new OrderTOBuilder().withAmount(2).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction1.getId()).build();
		OrderTO savedOrder1 = orderService.saveOrder(order1);

		OrderTO order2 = new OrderTOBuilder().withAmount(3).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction2.getId()).build();
		OrderTO savedOrder2 = orderService.saveOrder(order2);

		OrderTO order3 = new OrderTOBuilder().withAmount(4).withProductTOId(savedProduct2.getId())
				.withTransactionTO(savedTransaction3.getId()).build();
		OrderTO savedOrder3 = orderService.saveOrder(order3);

		OrderTO order4 = new OrderTOBuilder().withAmount(6).withProductTOId(savedProduct3.getId())
				.withTransactionTO(savedTransaction4.getId()).build();
		OrderTO savedOrder4 = orderService.saveOrder(order4);

		OrderTO order5 = new OrderTOBuilder().withAmount(10).withProductTOId(savedProduct1.getId())
				.withTransactionTO(savedTransaction5.getId()).build();
		OrderTO savedOrder5 = orderService.saveOrder(order5);

		Date dateFrom1 = java.sql.Date.valueOf("2017-11-01");
		Date dateTo1 = java.sql.Date.valueOf("2018-01-01");
		Date dateFrom2 = java.sql.Date.valueOf("2017-10-01");
		Date dateTo2 = java.sql.Date.valueOf("2018-05-01");
		int amountOfClients = 3;
		// when
		List<PurchasedProductTO> resultList1 = purchasedProductService.getBestSellingProducts(5);

		// then
		assertNotNull(resultList1);
		assertEquals((Integer) 3, (Integer) resultList1.size());
		assertEquals("ball", resultList1.get(0).getProductName());

	}
}

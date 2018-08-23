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
import com.capgemini.types.AdressDataTO;
import com.capgemini.types.AdressDataTO.AdressDataTOBuilder;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.CustomerTO.CustomerTOBuilder;
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
				.withProducts(products).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		// when
		savedTransaction.setAmount(amount);
		Long ver1 = transactionService.findTransactionById(savedTransaction.getId()).getVersion();
		transactionService.updateTransaction(savedTransaction);
		Long ver2 = transactionService.findTransactionById(savedTransaction.getId()).getVersion();

		Long verTest = 1L;
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
				.withProducts(products).withTransactionStatus(TransactionStatus.EXECUTED).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		// when
		TransactionTO selectedTransaction = transactionService.findTransactionById(savedTransaction.getId());

		// then
		assertThat(savedTransaction.getAmount()).isEqualTo(selectedTransaction.getAmount());
		assertThat(savedTransaction.getId()).isEqualTo(selectedTransaction.getId());
	}

}

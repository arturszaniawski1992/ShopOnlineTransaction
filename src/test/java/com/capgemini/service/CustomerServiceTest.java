package com.capgemini.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import com.capgemini.exception.InvalidCreationException;
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
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PurchasedProductService purchasedProductService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private TransactionService transactionService;

	@Test
	public void shouldTesVersion() {
		// given

		String lastName = "Kowalski";
		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		// when
		savedCustomer.setLastName(lastName);
		Long ver1 = customerService.findCustomerById(savedCustomer.getId()).getVersion();
		customerService.updateCustomer(savedCustomer);
		Long ver2 = customerService.findCustomerById(savedCustomer.getId()).getVersion();

		Long verTest = 1L;
		// then
		assertThat(ver1).isNotEqualTo(ver2);
		assertEquals(verTest, ver2);
	}

	@Test(expected = InvalidCreationException.class)
	public void shouldThrowExceptionWhileCreatingCustomer() {

		CustomerTO customer1 = new CustomerTOBuilder().withFirstName("Artur").build();
		customerService.saveCustomer(customer1);

	}

	@Test
	public void shouldFindCustomerById() {
		// given
		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();

		CustomerTO savedCustomer = customerService.saveCustomer(cust1);

		// when
		CustomerTO selectedCustomer = customerService.findCustomerById(savedCustomer.getId());

		// then
		assertThat(savedCustomer.getFirstName()).isEqualTo(selectedCustomer.getFirstName());
		assertThat(savedCustomer.getId()).isEqualTo(selectedCustomer.getId());
	}

	@Test
	public void shouldCannotFoundCustomer() {
		// given
		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();

		customerService.saveCustomer(cust1);

		// when
		CustomerTO selectedCustomer = customerService.findCustomerById(3L);

		// then
		assertNull(selectedCustomer);

	}

	@Test
	public void shouldFindAllCustomers() {
		// given
		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO cust2 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		List<CustomerTO> customers = new ArrayList<>();
		customers.add(customerService.saveCustomer(cust1));
		customers.add(customerService.saveCustomer(cust2));

		// when
		List<CustomerTO> selectedCustomers = customerService.findAllCustomers();

		// then
		assertEquals(2, selectedCustomers.size());
	}

	@Test
	public void shouldUpdateCustomer() {
		// given
		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO addedCustomer = customerService.saveCustomer(cust1);

		String lastName = "Kowalski";

		// when
		addedCustomer.setLastName(lastName);
		CustomerTO updatedCustomer = customerService.updateCustomer(addedCustomer);
		List<CustomerTO> clients = customerService.findAllCustomers();

		// then
		assertThat(updatedCustomer.getLastName().equals(lastName));
		assertThat(updatedCustomer.getId()).isEqualTo(addedCustomer.getId());
		assertThat(customerService.findCustomerById(updatedCustomer.getId()).getLastName()).isEqualTo(lastName);
		assertThat(clients.size()).isEqualTo(1);
	}

	@Test(expected = InvalidCreationException.class)
	public void shouldNotUpdateCustomer() {
		// given
		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("4564564564").build();
		CustomerTO addedCustomer = customerService.saveCustomer(cust1);

		// when
		addedCustomer.setLastName(null);
		CustomerTO updatedCustomer = customerService.updateCustomer(addedCustomer);

		// then
		assertNull(updatedCustomer);
	}

	@Test
	public void shouldRemoveCustomerById() {
		// given
		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("sd").build();
		CustomerTO cust2 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("sda").build();
		CustomerTO cust3 = new CustomerTOBuilder().withFirstName("Artur").withLastName("sda").withAdressData(adress)
				.withMobile("4564564564").build();
		CustomerTO addedCustomer1 = customerService.saveCustomer(cust1);
		CustomerTO addedCustomer2 = customerService.saveCustomer(cust1);
		CustomerTO addedCustomer3 = customerService.saveCustomer(cust1);
		// when
		customerService.removeCustomer(addedCustomer1.getId());
		List<CustomerTO> customers = customerService.findAllCustomers();

		// then
		assertEquals(2, customers.size());

	}

	@Test
	public void shouldAssignCustomerToTransaction() {
		// given
		AdressDataTO adress = new AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerTO cust1 = new CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withAdressData(adress).withMobile("sd").build();
		CustomerTO savedCustomer = customerService.saveCustomer(cust1);
		TransactionTO transaction = new TransactionTOBuilder().withCustomerId(savedCustomer.getId()).build();
		TransactionTO savedTransaction = transactionService.saveTransaction(transaction);

		// when
		CustomerTO customerWithTransaction = customerService.assignTransaction(savedCustomer, savedTransaction);

		// then

		assertNotNull(savedTransaction.getId());
		assertEquals(savedCustomer.getId(), customerWithTransaction.getId());
		assertEquals(savedCustomer.getFirstName(), customerWithTransaction.getFirstName());

	}

	@Test
	public void shouldFindMostProfitableClientsInGivenTimePeriod() throws NoValidConnection {
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

		Date dateFrom1 = java.sql.Date.valueOf("2017-11-01");
		Date dateTo1 = java.sql.Date.valueOf("2018-01-01");
		Date dateFrom2 = java.sql.Date.valueOf("2017-10-01");
		Date dateTo2 = java.sql.Date.valueOf("2018-05-01");
		int amountOfClients = 3;
		// when
		List<CustomerTO> resultList1 = customerService.findTopThreeClientsWhoSpentTheMostInPeriod(dateFrom1, dateTo1,
				amountOfClients);
		List<CustomerTO> resultList2 = customerService.findTopThreeClientsWhoSpentTheMostInPeriod(dateFrom2, dateTo2,
				amountOfClients);

		assertNotNull(resultList1);
		assertEquals((Integer) 2, (Integer) resultList1.size());
		assertEquals("kowalski", resultList1.get(0).getLastName());
		assertEquals("malysz", resultList1.get(1).getLastName());

		assertNotNull(resultList2);
		assertEquals((Integer) 3, (Integer) resultList2.size());
		assertEquals("janusz", resultList2.get(0).getLastName());
		assertEquals("miska", resultList2.get(1).getLastName());
		assertEquals("opcje", resultList2.get(2).getLastName());

	}
}

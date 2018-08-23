package com.capgemini.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.persister.walking.spi.AssociationVisitationStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.types.AdressDataTO;
import com.capgemini.types.AdressDataTO.AdressDataTOBuilder;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.CustomerTO.CustomerTOBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;

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
}

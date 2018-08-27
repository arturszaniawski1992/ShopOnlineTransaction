package com.capgemini.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.capgemini.embeded.AdressData;
import com.capgemini.embeded.AdressData.AdressDataEntityBuilder;
import com.capgemini.exception.InvalidCreationException;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class CustomerRepositioryTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private CustomerRepository customerRepository;

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
		CustomerEntity save2 = customerRepository.save(cust2);

		// when
		List<CustomerEntity> listOfCustomers = customerRepository.findByFirstNameAndLastName("Artur", "Szaniawski");
		// then
		assertNotNull(listOfCustomers);
		assertEquals(2, listOfCustomers.size());
		assertEquals(save2.getAdressData(), listOfCustomers.get(0).getAdressData());
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

	@Test(expected = InvalidCreationException.class)
	public void shouldThrowInvalidCreationExcception() {
		CustomerEntity customerEntity = new CustomerEntityBuilder().withFirstName("Artur").build();
	}
}

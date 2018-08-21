package com.capgemini.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.CustomerEntity.CustomerEntityBuilder;

import embedded.AdressDataEntity;
import embedded.AdressDataEntity.AdressDataEntityBuilder;
import exception.InvalidCreationException;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class CustomerRepositioryTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private CustomerRepository customerRepository;
	CustomerEntity customer1;
	CustomerEntity customer2;
	CustomerEntity customer3;

	@Before
	public void setup() throws InvalidCreationException {

		AdressDataEntity adress = new AdressDataEntityBuilder().withStreet("Warszawska").withCity("Poznan")
				.withNumber(15).withPostCode("45-210").build();
		customer1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("789456123").withAdressData(adress).build();
		customer2 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("789456123").withAdressData(adress).build();
		customer3 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("789456123").withAdressData(adress).build();
		customer1 = customerRepository.save(customer1);
		customer2 = customerRepository.save(customer2);
		customer3 = customerRepository.save(customer3);

	}

	@Test
	public void shouldFindCustomerById() {
		// given
		// when
		// then

	}

}

package com.capgemini.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.PurchasedProductEntity.PurchasedProductEntityBuilder;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.domain.TransactionEntity.TransactionEntityBuilder;
import com.capgemini.embeded.AdressData;
import com.capgemini.embeded.AdressData.AdressDataEntityBuilder;
import com.capgemini.enums.TransactionStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class TransactionRepositoryTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PurchasedProductRepository purchasedProductRepository;

	@Test
	public void shouldFindTransactionById() {
		// given

		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity cust1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();
		customerRepository.save(cust1);
		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(1.0)
				.withProductName("ball").withWeight(0.5).build();
		purchasedProductRepository.save(product1);
		List<PurchasedProductEntity> products = new ArrayList<>();
		products.add(product1);

		TransactionEntity transaction = new TransactionEntityBuilder().withCustomerEntity(cust1)
				.withTransactionStatus(TransactionStatus.IN_PROGRESS).build();

		TransactionEntity savedTransaction = transactionRepository.save(transaction);

		// when
		TransactionEntity selectedTransaction = transactionRepository.findById(savedTransaction.getId());

		// then
		assertThat(savedTransaction.getCustomerEntity()).isEqualTo(selectedTransaction.getCustomerEntity());
	}

	@Test
	public void shouldFindByTransactionStatus() {
		AdressData adress = new AdressDataEntityBuilder().withCity("Poznan").withPostCode("21-400").withNumber(15)
				.withStreet("Warszawska").build();

		CustomerEntity cust1 = new CustomerEntityBuilder().withFirstName("Artur").withLastName("Szaniawski")
				.withMobile("456123456").withAdressData(adress).build();
		customerRepository.save(cust1);
		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(1.0)
				.withProductName("ball").withWeight(0.5).build();
		purchasedProductRepository.save(product1);
		List<PurchasedProductEntity> products = new ArrayList<>();
		products.add(product1);

		TransactionEntity transaction = new TransactionEntityBuilder().withCustomerEntity(cust1)
				.withTransactionStatus(TransactionStatus.IN_PROGRESS).build();

		TransactionEntity savedTransaction = transactionRepository.save(transaction);
		// when
		List<TransactionEntity> selectedTransaction = transactionRepository
				.findByTransactionStatus(savedTransaction.getTransactionStatus());

		// then
		assertEquals(1, customerRepository.count());
		assertEquals(1, selectedTransaction.size());
	}
}

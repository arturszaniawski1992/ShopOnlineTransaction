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

import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.PurchasedProductEntity.PurchasedProductEntityBuilder;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.domain.TransactionEntity.TransactionEntityBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class PurchasedProductRepositoryTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private PurchasedProductRepository purchasedProductRepository;

	@Test
	public void shouldFindProductById() {
		// given
		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(21.0)
				.withProductName("ball").withWeight(12.0).build();

		PurchasedProductEntity savedProduct = purchasedProductRepository.save(product1);

		// when
		PurchasedProductEntity selectedProduct = purchasedProductRepository.findById(savedProduct.getId());

		// then
		assertThat(savedProduct.getProductName()).isEqualTo(selectedProduct.getProductName());
		assertThat(savedProduct.getId()).isEqualTo(selectedProduct.getId());
	}

	@Test
	public void shouldFindCustomerByProductName() {
		// given
		PurchasedProductEntity product1 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(21.0)
				.withProductName("ball").withWeight(12.0).build();
		PurchasedProductEntity product2 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(21.0)
				.withProductName("ball").withWeight(12.0).build();
		PurchasedProductEntity product3 = new PurchasedProductEntityBuilder().withPrice(125.0).withMargin(21.0)
				.withProductName("ball").withWeight(12.0).build();
		purchasedProductRepository.save(product1);
		purchasedProductRepository.save(product2);
		purchasedProductRepository.save(product3);
		String productName = "ball";
		// when
		List<PurchasedProductEntity> listOfProducts = purchasedProductRepository.findByProductName(productName);
		// then
		assertNotNull(listOfProducts);
		assertEquals(3, listOfProducts.size());
	}


}

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

import com.capgemini.types.PurchasedProductTO;
import com.capgemini.types.PurchasedProductTO.PurchasedProductTOBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class PurchasedProductServiceTest {

	@Autowired
	private PurchasedProductService purchasedProductService;

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
}

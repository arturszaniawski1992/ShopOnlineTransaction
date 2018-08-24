package com.capgemini.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.types.CustomerTO;
import com.capgemini.types.OrderTO;
import com.capgemini.types.OrderTO.OrderTOBuilder;
import com.capgemini.types.PurchasedProductTO;
import com.capgemini.types.PurchasedProductTO.PurchasedProductTOBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
@Transactional
public class OrderServiceTest {
	@Autowired
	private OrderService orderService;

	@Autowired
	PurchasedProductService purchasedProductService;

	/*
	 * @Test public void shouldTesVersion() { // given
	 * 
	 * String lastName = "Kowalski"; AdressDataTO adress = new
	 * AdressDataTOBuilder().withCity("Poznan").withPostCode("21-400").
	 * withNumber(15) .withStreet("Warszawska").build();
	 * 
	 * CustomerTO cust1 = new
	 * CustomerTOBuilder().withFirstName("Artur").withLastName("Szaniawski")
	 * .withAdressData(adress).withMobile("4564564564").build(); CustomerTO
	 * savedCustomer = customerService.saveCustomer(cust1);
	 * 
	 * // when savedCustomer.setLastName(lastName); Long ver1 =
	 * customerService.findCustomerById(savedCustomer.getId()).getVersion();
	 * customerService.updateCustomer(savedCustomer); Long ver2 =
	 * customerService.findCustomerById(savedCustomer.getId()).getVersion();
	 * 
	 * Long verTest = 1L; // then assertThat(ver1).isNotEqualTo(ver2);
	 * assertEquals(verTest, ver2); }
	 */

	@Test
	public void shouldFindCustomerById() {
		// given

		PurchasedProductTO product = new PurchasedProductTOBuilder().withMargin(12.0).withProductName("ball")
				.withPrice(125.0).withWeight(12.0).build();
		PurchasedProductTO savedProduct = purchasedProductService.savePurchasedProduct(product);
		
		OrderTO order = new OrderTOBuilder().withAmount(15).withProductTOId(savedProduct.getId()).build();
		OrderTO savedOrder = orderService.saveOrder(order);

		// when
		OrderTO selectedOrder = orderService.findOrderById(savedOrder.getId());

		// then
		assertThat(savedOrder.getAmount()).isEqualTo(selectedOrder.getAmount());
		assertThat(savedOrder.getId()).isEqualTo(selectedOrder.getId());
	}

}

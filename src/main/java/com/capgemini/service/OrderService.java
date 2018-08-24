package com.capgemini.service;

import java.util.List;

import com.capgemini.types.OrderTO;

public interface OrderService {
	OrderTO findOrderById(Long id);

	List<OrderTO> findAllPurchasedProducts();

	OrderTO updateOrder(OrderTO orderTO);

	OrderTO saveOrder(OrderTO orderTO);

	void removeOrder(Long id);

	OrderTO findByAmount(Long amount);

}

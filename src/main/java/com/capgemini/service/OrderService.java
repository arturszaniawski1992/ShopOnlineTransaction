package com.capgemini.service;

import java.util.List;

import com.capgemini.exception.NoValidConnection;
import com.capgemini.types.OrderTO;

public interface OrderService {
	OrderTO findOrderById(Long id);

	List<OrderTO> findAllOrders();

	OrderTO updateOrder(OrderTO orderTO);

	OrderTO saveOrder(OrderTO orderTO) throws NoValidConnection;

	void removeOrder(Long id);

	OrderTO findByAmount(Long amount);

}

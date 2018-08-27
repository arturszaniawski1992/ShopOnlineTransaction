package com.capgemini.service;

import java.util.List;

import com.capgemini.exception.NoValidConnection;
import com.capgemini.types.OrderTO;

public interface OrderService {
	/**
	 * This is the method which find order by id.
	 * 
	 * @param Long
	 *            as id of order.
	 * 
	 * @return OrderTO as order.
	 */
	public OrderTO findOrderById(Long id);

	/**
	 * This is the method which find all orders.
	 * 
	 * @return List of orders.
	 */
	public List<OrderTO> findAllOrders();

	/**
	 * This is the method which update order.
	 * 
	 * @param OrderTO
	 *            as order.
	 * 
	 * @return OrderTO as order.
	 */
	public OrderTO updateOrder(OrderTO orderTO);

	/**
	 * This is the method which save order.
	 * 
	 * @param OrderTO
	 *            as order.
	 * 
	 * @return OrderTO as order.
	 */
	public OrderTO saveOrder(OrderTO orderTO) throws NoValidConnection;

	/**
	 * This is the method which remove order by its id.
	 * 
	 * @param Long
	 *            as id of order.
	 * 
	 */
	void removeOrder(Long id);

}

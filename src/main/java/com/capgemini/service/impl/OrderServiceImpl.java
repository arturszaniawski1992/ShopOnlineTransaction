package com.capgemini.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.dao.OrderRepository;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.OrderEntity;
import com.capgemini.mappers.OrderMapper;
import com.capgemini.service.OrderService;
import com.capgemini.types.OrderTO;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	private final OrderMapper orderMapper;

	public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
		super();
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
	}

	@Override
	public OrderTO findOrderById(Long id) {
		return orderMapper.toOrderTO(orderRepository.findById(id));

	}

	@Override
	public List<OrderTO> findAllPurchasedProducts() {
		List<OrderEntity> allOrders = orderRepository.findAll();
		return orderMapper.map2TOs(allOrders);
	}

	@Override
	public OrderTO updateOrder(OrderTO orderTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderTO saveOrder(OrderTO orderTO) {
		OrderEntity orderEntity = orderRepository.save(orderMapper.toOrderEntity(orderTO));
		return orderMapper.toOrderTO(orderEntity);

	}

	@Override
	public void removeOrder(Long id) {
		orderRepository.delete(id);
	}

	@Override
	public OrderTO findByAmount(Long amount) {
		return orderMapper.toOrderTO(orderRepository.findByAmount(amount));

	}

}

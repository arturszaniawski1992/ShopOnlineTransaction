package com.capgemini.service.impl;

import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.capgemini.dao.OrderRepository;
import com.capgemini.dao.PurchasedProductRepository;
import com.capgemini.dao.TransactionRepository;
import com.capgemini.domain.OrderEntity;
import com.capgemini.domain.PurchasedProductEntity;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.exception.NoValidConnection;
import com.capgemini.mappers.OrderMapper;
import com.capgemini.service.OrderService;
import com.capgemini.types.OrderTO;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	private final TransactionRepository transactionRepository;

	private final PurchasedProductRepository purchasedProductRepository;
	private final OrderMapper orderMapper;

	public OrderServiceImpl(OrderRepository orderRepository, TransactionRepository transactionRepository,
			PurchasedProductRepository purchasedProductRepository, OrderMapper orderMapper) {
		super();
		this.orderRepository = orderRepository;
		this.transactionRepository = transactionRepository;
		this.purchasedProductRepository = purchasedProductRepository;
		this.orderMapper = orderMapper;
	}

	@Override
	public OrderTO findOrderById(Long id) {
		return orderMapper.toOrderTO(orderRepository.findById(id));

	}

	@Override
	public List<OrderTO> findAllOrders() {
		List<OrderEntity> allOrders = orderRepository.findAll();
		return orderMapper.map2TOs(allOrders);
	}

	@Override
	public OrderTO updateOrder(OrderTO orderTO) {
		OrderEntity orderEntity = orderRepository.getOne(orderTO.getId());
		if (orderEntity.getVersion() != orderTO.getVersion()) {
			throw new OptimisticLockException("incorrect version");
		}
		PurchasedProductEntity productEntity = purchasedProductRepository.findOne(orderTO.getProductTOId());
		orderEntity.setAmount(orderTO.getAmount());
		orderEntity.setProductEntity(productEntity);
		return orderMapper.toOrderTO(orderRepository.save(orderEntity));
	}

	@Override
	public OrderTO saveOrder(OrderTO orderTO) throws NoValidConnection {
		if (orderTO.getProductTOId() == null || orderTO.getTransactionTOId() == null) {
			throw new NoValidConnection();
		}

		PurchasedProductEntity productEntity = purchasedProductRepository.findOne(orderTO.getProductTOId());
		TransactionEntity transactionEntity = transactionRepository.findOne(orderTO.getTransactionTOId());

		if (productEntity == null || transactionEntity == null) {
			throw new NoValidConnection();
		}
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setAmount(orderTO.getAmount());
		orderEntity.setProductEntity(productEntity);
		orderEntity.setTransactionEntity(transactionEntity);

		return orderMapper.toOrderTO(orderRepository.save(orderEntity));
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

package com.capgemini.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.dao.customize.CustomizedOrderRepository;
import com.capgemini.domain.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>, CustomizedOrderRepository {
	/**
	 * This is the method which find order by id.
	 * 
	 * @param Long
	 *            as id of order.
	 * 
	 * @return Order as OrderEntity.
	 */
	OrderEntity findById(Long id);

	/**
	 * This is the method which find order by amount.
	 * 
	 * @param Integer
	 *            as amount of orders.
	 * 
	 * @return List of orders.
	 */
	List<OrderEntity> findByAmount(Integer amount);

	/**
	 * This is the method which remove order from collection.
	 * 
	 * @param Long
	 *            as id of order.
	 * 
	 */
	void deleteById(Long id);

	/**
	 * This is the method which find order by amount.
	 * 
	 * @param Long
	 *            as amount of orders.
	 * 
	 * @return Order as OrderEntity.
	 */
	OrderEntity findByAmount(Long amount);

}

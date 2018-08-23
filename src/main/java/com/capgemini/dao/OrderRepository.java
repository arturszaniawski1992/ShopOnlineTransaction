package com.capgemini.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.domain.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

	OrderEntity findById(Long id);

	List<OrderEntity> findByAmount(Integer amount);

	void deleteById(Long id);

}

package com.capgemini.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.capgemini.domain.CustomerEntity;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

	List<CustomerEntity> findById(Long id);

}

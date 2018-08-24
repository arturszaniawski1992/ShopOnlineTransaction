package com.capgemini.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.dao.customize.CustomizedCustomerRepository;
import com.capgemini.domain.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, CustomizedCustomerRepository {

	CustomerEntity findById(Long id);

	List<CustomerEntity> findByFirstNameAndLastName(String firstName, String lastName);

	void deleteById(Long id);

	void deleteByLastName(String lastName);

}

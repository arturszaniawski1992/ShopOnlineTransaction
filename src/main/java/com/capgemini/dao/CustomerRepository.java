package com.capgemini.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.dao.customize.CustomizedCustomerRepository;
import com.capgemini.domain.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, CustomizedCustomerRepository {

	/**
	 * This is the method which find customer by id.
	 * 
	 * @param Long
	 *            as Id of customer.
	 * 
	 * @return Customer as CustomerEntity.
	 */
	CustomerEntity findById(Long id);

	/**
	 * This is the method which find customer by firstname and lastname.
	 * 
	 * @param String
	 *            as firstname and string as lastname.
	 * 
	 * @return List of customers.
	 */
	List<CustomerEntity> findByFirstNameAndLastName(String firstName, String lastName);

	/**
	 * This is the method which remove customer from collection by id.
	 * 
	 * @param Long
	 *            as Id of customer.
	 * 
	 */
	void deleteById(Long id);

	/**
	 * This is the method which remove customer from collection by lastname.
	 * 
	 * @param String
	 *            as lastname of customer.
	 * 
	 */
	void deleteByLastName(String lastName);

}

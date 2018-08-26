package com.capgemini.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.CustomerEntity.CustomerEntityBuilder;
import com.capgemini.domain.TransactionEntity;
import com.capgemini.types.CustomerTO;
import com.capgemini.types.CustomerTO.CustomerTOBuilder;

@Component
public class CustomerMapper {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * This is the method which map customer entity to customer TO.
	 * 
	 * @param CustomerEntity
	 *            as customer.
	 * 
	 * @return CustomerTO as customer.
	 */
	public CustomerTO toCustomerTO(CustomerEntity customerEntity) {
		if (customerEntity == null)
			return null;

		CustomerTOBuilder customerTOBuilder = new CustomerTOBuilder().withVersion(customerEntity.getVersion())
				.withId(customerEntity.getId()).withFirstName(customerEntity.getFirstName())
				.withAdressData(AdressMapper.toAdressDataTO((customerEntity.getAdressData())))
				.withLastName(customerEntity.getLastName()).withDateOfBirth(customerEntity.getDateOfBirth())
				.withMail(customerEntity.getMail()).withMobile(customerEntity.getMobile());

		if (customerEntity.getTransactions() != null) {
			customerTOBuilder.withTransactions(
					customerEntity.getTransactions().stream().map(e -> e.getId()).collect(Collectors.toList()));

		}
		return customerTOBuilder.build();
	}

	/**
	 * This is the method which map customer TO to customer entity.
	 * 
	 * @param CustomerTO
	 *            as customer.
	 * 
	 * @return CustomerEntity as customer.
	 */
	public CustomerEntity toCustomerEntity(CustomerTO customerTO) {
		if (customerTO == null)
			return null;

		List<Long> transactions = customerTO.getTransactions();
		List<TransactionEntity> listOfTransactions = new ArrayList<>();
		if (transactions != null) {
			for (Long id : transactions) {
				listOfTransactions.add(entityManager.getReference(TransactionEntity.class, id));
			}
		}

		CustomerEntityBuilder customerEntityBuilder = new CustomerEntityBuilder().withVersion(customerTO.getVersion())
				.withId(customerTO.getId()).withFirstName(customerTO.getFirstName())
				.withAdressData(AdressMapper.toAdressDataEntity(customerTO.getAdressData()))
				.withLastName(customerTO.getLastName()).withDateOfBirth(customerTO.getDateOfBirth())
				.withMail(customerTO.getMail()).withMobile(customerTO.getMobile());

		return customerEntityBuilder.build();

	}

	/**
	 * This is the method which map list of customers entities to customer TOs.
	 * 
	 * @param List
	 *            of CustomerEntities as list of customers.
	 * 
	 * @return List of customerTOs.
	 */
	public List<CustomerTO> map2TOs(List<CustomerEntity> carEntities) {
		return carEntities.stream().map(this::toCustomerTO).collect(Collectors.toList());
	}

	/**
	 * This is the method which map list of customers TOs to customer entities.
	 * 
	 * @param List
	 *            of CustomerTOs as list of customers.
	 * 
	 * @return List of customerEntities.
	 */
	public List<CustomerEntity> map2Entities(List<CustomerTO> customerTOs) {
		return customerTOs.stream().map(this::toCustomerEntity).collect(Collectors.toList());
	}

}

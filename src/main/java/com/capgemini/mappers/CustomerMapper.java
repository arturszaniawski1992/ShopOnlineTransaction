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

	public CustomerTO toCustomerTO(CustomerEntity customerEntity) {
		if (customerEntity == null)
			return null;

		CustomerTOBuilder customerTOBuilder = new CustomerTOBuilder().withId(customerEntity.getId())
				.withFirstName(customerEntity.getFirstName()).withLastName(customerEntity.getLastName())
				.withDateOfBirth(customerEntity.getDateOfBirth()).withMail(customerEntity.getMail())
				.withMobile(customerEntity.getMobile());

		if (customerEntity.getTransactions() != null) {
			customerTOBuilder.withTransactions(
					customerEntity.getTransactions().stream().map(e -> e.getId()).collect(Collectors.toList()));

		}
		return customerTOBuilder.build();
	}

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

		CustomerEntityBuilder customerEntityBuilder = new CustomerEntityBuilder().withId(customerTO.getId())
				.withFirstName(customerTO.getFirstName()).withLastName(customerTO.getLastName())
				.withDateOfBirth(customerTO.getDateOfBirth()).withMail(customerTO.getMail())
				.withMobile(customerTO.getMobile());

		return customerEntityBuilder.build();

	}

	public List<CustomerTO> map2TOs(List<CustomerEntity> carEntities) {
		return carEntities.stream().map(this::toCustomerTO).collect(Collectors.toList());
	}

	public List<CustomerEntity> map2Entities(List<CustomerTO> carTOs) {
		return carTOs.stream().map(this::toCustomerEntity).collect(Collectors.toList());
	}

}

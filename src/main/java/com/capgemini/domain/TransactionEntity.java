package com.capgemini.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import com.capgemini.enums.TransactionStatus;
import com.capgemini.exception.InvalidCreationException;
import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

@Entity
@Table(name = "transaction")
@EntityListeners({ UpdateListener.class, InsertListener.class })
public class TransactionEntity extends AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Version
	public Long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@DateTimeFormat
	LocalDateTime dateTransaction;
	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;
	@Column(length = 50, nullable = false)
	private Integer amount;

	@ManyToOne
	@JoinColumn(name = "id_client")
	private CustomerEntity customerEntity;
	@ManyToMany
	@JoinTable(name = "purchase", joinColumns = { @JoinColumn(name = "id_transaction") }, inverseJoinColumns = {
			@JoinColumn(name = "id_product") })
	private List<PurchasedProductEntity> products;

	public TransactionEntity() {
	}

	public TransactionEntity(TransactionEntityBuilder builder) {
		this.version = builder.version;
		this.id = builder.id;
		this.dateTransaction = builder.dateTransaction;
		this.transactionStatus = builder.transactionStatus;
		this.amount = builder.amount;
		this.customerEntity = builder.customerEntity;
		this.products = builder.products;

	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(LocalDateTime dateTransaction) {
		this.dateTransaction = dateTransaction;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public CustomerEntity getCustomerEntity() {
		return customerEntity;
	}

	public void setCustomerEntity(CustomerEntity customerEntity) {
		this.customerEntity = customerEntity;
	}

	public List<PurchasedProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<PurchasedProductEntity> products) {
		this.products = products;
	}

	public static class TransactionEntityBuilder {
		private Long version;
		private Long id;
		LocalDateTime dateTransaction;
		private TransactionStatus transactionStatus;
		private Integer amount;
		private CustomerEntity customerEntity;
		private List<PurchasedProductEntity> products;

		public TransactionEntityBuilder() {
		}

		public TransactionEntityBuilder withVersion(Long version) {
			this.version = version;
			return this;
		}

		public TransactionEntityBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public TransactionEntityBuilder withDateTransaction(LocalDateTime dateTransaction) {
			this.dateTransaction = dateTransaction;
			return this;
		}

		public TransactionEntityBuilder withAmount(Integer amount) {
			this.amount = amount;
			return this;
		}

		public TransactionEntityBuilder withTransactionStatus(TransactionStatus transactionStatus) {
			this.transactionStatus = transactionStatus;
			return this;
		}

		public TransactionEntityBuilder withCustomerEntity(CustomerEntity customerEntity) {
			this.customerEntity = customerEntity;
			return this;
		}

		public TransactionEntityBuilder withProducts(List<PurchasedProductEntity> products) {
			this.products = products;
			return this;
		}

		public TransactionEntity build() {
			if (customerEntity == null || amount == null || products == null) {
				throw new InvalidCreationException("Incorrect transaction to be created");
			}

			return new TransactionEntity(this);
		}

	}
}

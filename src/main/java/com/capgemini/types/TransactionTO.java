package com.capgemini.types;

import java.time.LocalDateTime;
import java.util.List;

import com.capgemini.enums.TransactionStatus;

import exception.InvalidCreationException;

public class TransactionTO {

	private Long id;
	LocalDateTime dateTransaction;
	private TransactionStatus transactionStatus;
	private Integer amount;
	private Long customerId;
	private List<Long> products;

	public TransactionTO() {
	}

	public TransactionTO(TransactionTOBuilder builder) {
		this.id = builder.id;
		this.dateTransaction = builder.dateTransaction;
		this.transactionStatus = builder.transactionStatus;
		this.amount = builder.amount;
		this.customerId = builder.customerId;
		this.products = builder.products;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<Long> getProducts() {
		return products;
	}

	public void setProducts(List<Long> products) {
		this.products = products;
	}

	public static class TransactionTOBuilder {
		private Long id;
		LocalDateTime dateTransaction;
		private TransactionStatus transactionStatus;
		private Integer amount;
		private Long customerId;
		private List<Long> products;

		public TransactionTOBuilder() {
		}

		public TransactionTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public TransactionTOBuilder withDateTransaction(LocalDateTime dateTransaction) {
			this.dateTransaction = dateTransaction;
			return this;
		}

		public TransactionTOBuilder withTransactionStatus(TransactionStatus transactionStatus) {
			this.transactionStatus = transactionStatus;
			return this;
		}

		public TransactionTOBuilder withAmount(Integer amount) {
			this.amount = amount;
			return this;
		}

		public TransactionTOBuilder withCustomerId(Long customerId) {
			this.customerId = customerId;
			return this;
		}

		public TransactionTOBuilder withProducts(List<Long> products) {
			this.products = products;
			return this;
		}

		public TransactionTO build() {
			if (customerId == null || amount == null || products == null) {
				throw new InvalidCreationException("Incorrect transaction to be created");
			}

			return new TransactionTO(this);
		}
	}
}

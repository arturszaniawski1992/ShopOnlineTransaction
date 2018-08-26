package com.capgemini.types;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.capgemini.enums.TransactionStatus;
import com.capgemini.exception.InvalidCreationException;

public class TransactionTO {
	private Long version;
	private Long id;
	private Date dateTransaction;
	private TransactionStatus transactionStatus;
	private Integer amount;
	private Long customerId;
	private List<Long> orders;

	public TransactionTO() {
	}

	public TransactionTO(TransactionTOBuilder builder) {
		this.version = builder.version;
		this.id = builder.id;
		this.dateTransaction = builder.dateTransaction;
		this.transactionStatus = builder.transactionStatus;
		this.amount = builder.amount;
		this.customerId = builder.customerId;
		this.orders = builder.orders;
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

	public Date getDateTransaction() {
		return dateTransaction;
	}

	public void setDateTransaction(Date dateTransaction) {
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

	public List<Long> getOrders() {
		return orders;
	}

	public void setOrders(List<Long> orders) {
		this.orders = orders;
	}

	public static class TransactionTOBuilder {
		private Long version;
		private Long id;
		private Date dateTransaction;
		private TransactionStatus transactionStatus;
		private Integer amount;
		private Long customerId;
		private List<Long> orders;

		public TransactionTOBuilder() {
		}

		public TransactionTOBuilder withVersion(Long version) {
			this.version = version;
			return this;
		}

		public TransactionTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public TransactionTOBuilder withDateTransaction(Date dateTransaction) {
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

		public TransactionTOBuilder withOrders(List<Long> orders) {
			this.orders = orders;
			return this;
		}

		public TransactionTO build() {
			if (customerId == null) {
				throw new InvalidCreationException("Incorrect transaction to be created");
			}

			return new TransactionTO(this);
		}
	}
}

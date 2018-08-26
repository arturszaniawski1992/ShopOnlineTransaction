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

		/**
		 * Default constructor for transaction TO builder.
		 *
		 */
		public TransactionTOBuilder() {
		}

		/**
		 * This is the method which add version to transaction.
		 * 
		 * @param Long
		 *            as version of transaction.
		 * @return version of transaction.
		 */
		public TransactionTOBuilder withVersion(Long version) {
			this.version = version;
			return this;
		}

		/**
		 * This is the method which add id to transaction.
		 * 
		 * @param Long
		 *            as id of transaction.
		 * @return Id of transaction.
		 */
		public TransactionTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		/**
		 * This is the method which add date to transaction.
		 * 
		 * @param Date
		 *            as date of transaction.
		 * @return Date of transaction.
		 */
		public TransactionTOBuilder withDateTransaction(Date dateTransaction) {
			this.dateTransaction = dateTransaction;
			return this;
		}

		/**
		 * This is the method which add status to transaction.
		 * 
		 * @param TransactionStatus
		 *            as status of transaction.
		 * @return Status of transaction.
		 */
		public TransactionTOBuilder withTransactionStatus(TransactionStatus transactionStatus) {
			this.transactionStatus = transactionStatus;
			return this;
		}

		/**
		 * This is the method which add amount to transaction.
		 * 
		 * @param integer
		 *            as amount of transaction.
		 * @return Amount of transaction.
		 */
		public TransactionTOBuilder withAmount(Integer amount) {
			this.amount = amount;
			return this;
		}

		/**
		 * This is the method which add customer to transaction.
		 * 
		 * @param Long
		 *            as id of customer.
		 * @return Customer of transaction.
		 */
		public TransactionTOBuilder withCustomerId(Long customerId) {
			this.customerId = customerId;
			return this;
		}

		/**
		 * This is the method which add orders to transaction.
		 * 
		 * @param List
		 *            of Long as orders.
		 * @return Orders of transaction.
		 */
		public TransactionTOBuilder withOrders(List<Long> orders) {
			this.orders = orders;
			return this;
		}

		/**
		 * This is the method which build transaction TO and if there is no
		 * demanded params throw exception.
		 * 
		 * @param Obligatory
		 *            customer id.
		 * @return Transaction TO.
		 */
		public TransactionTO build() {
			if (customerId == null) {
				throw new InvalidCreationException("Incorrect transaction to be created");
			}

			return new TransactionTO(this);
		}
	}
}

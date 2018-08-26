package com.capgemini.types;

import com.capgemini.exception.InvalidCreationException;

public class OrderTO {
	public Long version;
	private Long id;
	private Integer amount;
	private Long productTOId;
	private Long transactionTOId;

	public OrderTO() {
	}

	public OrderTO(OrderTOBuilder builder) {
		this.version = builder.version;
		this.id = builder.id;
		this.amount = builder.amount;
		this.productTOId = builder.productTOId;
		this.transactionTOId = builder.transactionTOId;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Long getProductTOId() {
		return productTOId;
	}

	public void setProductTOId(Long productTOId) {
		this.productTOId = productTOId;
	}

	public Long getTransactionTOId() {
		return transactionTOId;
	}

	public void setTransactionTOId(Long transactionTOId) {
		this.transactionTOId = transactionTOId;
	}

	public static class OrderTOBuilder {
		public Long version;
		private Long id;
		private Integer amount;
		private Long productTOId;
		private Long transactionTOId;

		/**
		 * Default constructor for order TO builder.
		 *
		 */
		public OrderTOBuilder() {
		}

		/**
		 * This is the method which add version to order.
		 * 
		 * @param Long
		 *            as version for order.
		 * @return Id of order.
		 */
		public OrderTOBuilder withVersion(Long version) {
			this.version = version;
			return this;
		}

		/**
		 * This is the method which add id to order.
		 * 
		 * @param Long
		 *            as id for order.
		 * @return Id of order.
		 */
		public OrderTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		/**
		 * This is the method which add amount to order.
		 * 
		 * @param Integer
		 *            as amount for order.
		 * @return Amount of order.
		 */
		public OrderTOBuilder withAmount(Integer amount) {
			this.amount = amount;
			return this;
		}

		/**
		 * This is the method which add product to order.
		 * 
		 * @param Long
		 *            as id of product.
		 * @return Product of order.
		 */
		public OrderTOBuilder withProductTOId(Long productTOId) {
			this.productTOId = productTOId;
			return this;
		}

		/**
		 * This is the method which add transaction to order.
		 * 
		 * @param TransactionEntity
		 *            as transaction for order.
		 * @return Transaction of order.
		 */

		public OrderTOBuilder withTransactionTO(Long transactionTOId) {
			this.transactionTOId = transactionTOId;
			return this;
		}

		/**
		 * This is the method which build order TO and if there is no demanded
		 * params throw exception.
		 * 
		 * @param Obligatory
		 *            String amount.
		 * @return Order TO.
		 */
		public OrderTO build() {
			if (amount == null) {
				throw new InvalidCreationException("Incorrect order to be created");
			}

			return new OrderTO(this);
		}
	}
}

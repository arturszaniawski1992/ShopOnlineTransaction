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

		public OrderTOBuilder() {
		}

		public OrderTOBuilder withVersion(Long version) {
			this.version = version;
			return this;
		}

		public OrderTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public OrderTOBuilder withAmount(Integer amount) {
			this.amount = amount;
			return this;
		}

		public OrderTOBuilder withProductTOId(Long productTOId) {
			this.productTOId = productTOId;
			return this;
		}

		public OrderTOBuilder withTransactionTO(Long transactionTOId) {
			this.transactionTOId = transactionTOId;
			return this;
		}

		public OrderTO build() {
			if (amount == null) {
				throw new InvalidCreationException("Incorrect order to be created");
			}

			return new OrderTO(this);
		}
	}
}

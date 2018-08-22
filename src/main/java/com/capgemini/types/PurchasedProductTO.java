package com.capgemini.types;

import java.util.List;

import com.capgemini.exception.InvalidCreationException;

public class PurchasedProductTO {
	private Long version;
	private Long id;
	private Double price;
	private String productName;
	private Double margin;
	private Double weight;
	private List<Long> transactions;

	public PurchasedProductTO() {
	}

	public PurchasedProductTO(PurchasedProductTOBuilder builder) {
		this.version = builder.version;
		this.id = builder.id;
		this.price = builder.price;
		this.productName = builder.productName;
		this.margin = builder.margin;
		this.weight = builder.weight;
		this.transactions = builder.transactions;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public List<Long> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Long> transactions) {
		this.transactions = transactions;
	}

	public static class PurchasedProductTOBuilder {
		private Long version;
		private Long id;
		private Double price;
		private String productName;
		private Double margin;
		private Double weight;
		private List<Long> transactions;

		public PurchasedProductTOBuilder() {
		}

		public PurchasedProductTOBuilder withVersion(Long version) {
			this.version = version;
			return this;
		}

		public PurchasedProductTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public PurchasedProductTOBuilder withPrice(Double price) {
			this.price = price;
			return this;
		}

		public PurchasedProductTOBuilder withProductName(String productName) {
			this.productName = productName;
			return this;
		}

		public PurchasedProductTOBuilder withMargin(Double margin) {
			this.margin = margin;
			return this;
		}

		public PurchasedProductTOBuilder withWeight(Double weight) {
			this.weight = weight;
			return this;
		}

		public PurchasedProductTOBuilder withTransactions(List<Long> transactions) {
			this.transactions = transactions;
			return this;
		}

		public PurchasedProductTO build() {
			if (price == null || productName == null || margin == null || weight == null) {
				throw new InvalidCreationException("Incorrect purchased product to be created");
			}

			return new PurchasedProductTO(this);
		}
	}
}

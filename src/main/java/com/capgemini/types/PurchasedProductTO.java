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
	private List<Long> orders;

	public PurchasedProductTO() {
	}

	public PurchasedProductTO(PurchasedProductTOBuilder builder) {
		this.version = builder.version;
		this.id = builder.id;
		this.price = builder.price;
		this.productName = builder.productName;
		this.margin = builder.margin;
		this.weight = builder.weight;
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

	public List<Long> getOrders() {
		return orders;
	}

	public void setOrders(List<Long> orders) {
		this.orders = orders;
	}

	public static class PurchasedProductTOBuilder {
		private Long version;
		private Long id;
		private Double price;
		private String productName;
		private Double margin;
		private Double weight;
		private List<Long> orders;

		/**
		 * Default constructor for product TO builder.
		 *
		 */
		public PurchasedProductTOBuilder() {
		}

		/**
		 * This is the method which add version to product.
		 * 
		 * @param Long
		 *            as version of product.
		 * @return Version of product.
		 */
		public PurchasedProductTOBuilder withVersion(Long version) {
			this.version = version;
			return this;
		}

		/**
		 * This is the method which add id to product.
		 * 
		 * @param Long
		 *            as id of product.
		 * @return Id of product.
		 */
		public PurchasedProductTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		/**
		 * This is the method which add price to product.
		 * 
		 * @param Double
		 *            as price name.
		 * @return Price of product.
		 */
		public PurchasedProductTOBuilder withPrice(Double price) {
			this.price = price;
			return this;
		}

		/**
		 * This is the method which add name to product.
		 * 
		 * @param String
		 *            as product name.
		 * @return name of product.
		 */
		public PurchasedProductTOBuilder withProductName(String productName) {
			this.productName = productName;
			return this;
		}

		/**
		 * This is the method which add margin to product.
		 * 
		 * @param Double
		 *            as margin of product.
		 * @return margin of product.
		 */
		public PurchasedProductTOBuilder withMargin(Double margin) {
			this.margin = margin;
			return this;
		}

		/**
		 * This is the method which add weight to product.
		 * 
		 * @param Double
		 *            as weight of product.
		 * @return weight of product.
		 */
		public PurchasedProductTOBuilder withWeight(Double weight) {
			this.weight = weight;
			return this;
		}

		/**
		 * This is the method which add orders to product.
		 * 
		 * @param List
		 *            of Longs as orders.
		 * @return Orders of product.
		 */
		public PurchasedProductTOBuilder withOrders(List<Long> orders) {
			this.orders = orders;
			return this;
		}

		/**
		 * This is the method which build product TO and if there is no demanded
		 * params throw exception.
		 * 
		 * @param Obligatory
		 *            String price, String productName, Double weight.
		 * @return Product TO.
		 */
		public PurchasedProductTO build() {
			if (price == null || productName == null || weight == null) {
				throw new InvalidCreationException("Incorrect purchased product to be created");
			}

			return new PurchasedProductTO(this);
		}
	}
}

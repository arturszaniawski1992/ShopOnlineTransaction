package com.capgemini.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.capgemini.exception.InvalidCreationException;
import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

@Entity
@Table(name = "purchased_product")
@EntityListeners({ UpdateListener.class, InsertListener.class })
public class PurchasedProductEntity extends AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Version
	public Long version;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "price", nullable = false, length = 50)
	private Double price;
	@Column(name = "product_name", nullable = false, length = 50)
	private String productName;
	@Column(nullable = false)
	private Double margin;
	@Column(name = "weight", nullable = false)
	private Double weight;

	@ManyToMany(mappedBy = "products", cascade = CascadeType.REMOVE)
	private List<TransactionEntity> transactions;

	public PurchasedProductEntity() {
	}

	public PurchasedProductEntity(PurchasedProductEntityBuilder builder) {
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

	public List<TransactionEntity> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionEntity> transactions) {
		this.transactions = transactions;
	}

	public static class PurchasedProductEntityBuilder {
		private Long version;
		private Long id;
		private Double price;
		private String productName;
		private Double margin;
		private Double weight;
		private List<TransactionEntity> transactions;

		public PurchasedProductEntityBuilder() {
		}

		public PurchasedProductEntityBuilder withVersion(Long version) {
			this.version = version;
			return this;
		}

		public PurchasedProductEntityBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public PurchasedProductEntityBuilder withPrice(Double price) {
			this.price = price;
			return this;
		}

		public PurchasedProductEntityBuilder withProductName(String productName) {
			this.productName = productName;
			return this;
		}

		public PurchasedProductEntityBuilder withMargin(Double margin) {
			this.margin = margin;
			return this;
		}

		public PurchasedProductEntityBuilder withWeight(Double weight) {
			this.weight = weight;
			return this;
		}

		public PurchasedProductEntityBuilder withTransactions(List<TransactionEntity> transactions) {
			this.transactions = transactions;
			return this;
		}

		public PurchasedProductEntity build() {
			if (price == null || productName == null || weight == null) {
				throw new InvalidCreationException("Incorrect purchased product to be created");
			}

			return new PurchasedProductEntity(this);
		}
	}

}

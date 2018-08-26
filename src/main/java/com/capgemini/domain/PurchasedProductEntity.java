package com.capgemini.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.capgemini.exception.InvalidCreationException;
import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

@Entity
@Table(name = "purchased_product")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
	@OneToMany(mappedBy = "productEntity", cascade = CascadeType.REMOVE)
	private List<OrderEntity> orders = new ArrayList<>();

	public PurchasedProductEntity() {
	}

	public PurchasedProductEntity(PurchasedProductEntityBuilder builder) {
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

	public List<OrderEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}

	/**
	 * This is the method which add order to product.
	 * 
	 * @param OrderEntity
	 *            as order.
	 */
	public boolean addOrder(OrderEntity orderEntity) {
		if (orders == null) {
			orders = new ArrayList<>();
		}
		return orders.add(orderEntity);
	}

	/**
	 * This is the method which remove order from product.
	 * 
	 * @param OrderEntity
	 *            as order.
	 */
	public boolean removeOrder(OrderEntity orderEntity) {
		if (orders == null) {
			return false;
		}
		return orders.remove(orderEntity);
	}

	public static class PurchasedProductEntityBuilder {
		private Long version;
		private Long id;
		private Double price;
		private String productName;
		private Double margin;
		private Double weight;
		private List<OrderEntity> orders;

		/**
		 * Default constructor for product entity builder.
		 *
		 */
		public PurchasedProductEntityBuilder() {
		}

		/**
		 * This is the method which add version to product.
		 * 
		 * @param Long
		 *            as version of product.
		 * @return Version of product.
		 */
		public PurchasedProductEntityBuilder withVersion(Long version) {
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
		public PurchasedProductEntityBuilder withId(Long id) {
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
		public PurchasedProductEntityBuilder withPrice(Double price) {
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
		public PurchasedProductEntityBuilder withProductName(String productName) {
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
		public PurchasedProductEntityBuilder withMargin(Double margin) {
			this.margin = margin;
			return this;
		}

		/**
		 * This is the method which add weight to product.
		 * 
		 * @param Double
		 *            as weight of product.
		 * @return weith of product.
		 */
		public PurchasedProductEntityBuilder withWeight(Double weight) {
			this.weight = weight;
			return this;
		}

		/**
		 * This is the method which add transactions to product.
		 * 
		 * @param List
		 *            of TransactionEntity as transactions.
		 * @return Orders of product.
		 */
		public PurchasedProductEntityBuilder withOrders(List<OrderEntity> orders) {
			this.orders = orders;
			return this;
		}

		/**
		 * This is the method which build product entity and if there is no
		 * demanded params throw exception.
		 * 
		 * @param Obligatory
		 *            String price, String productName, Double weight.
		 * @return Customer entity.
		 */
		public PurchasedProductEntity build() {
			if (price == null || productName == null || weight == null) {
				throw new InvalidCreationException("Incorrect purchased product to be created");
			}

			return new PurchasedProductEntity(this);
		}
	}

}

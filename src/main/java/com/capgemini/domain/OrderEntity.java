package com.capgemini.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.capgemini.exception.InvalidCreationException;
import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

@Entity
@Table(name = "orders")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({ UpdateListener.class, InsertListener.class })
public class OrderEntity extends AbstractEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Version
	public Long version;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private Integer amount;
	@ManyToOne
	@JoinColumn(name = "id_product")
	private PurchasedProductEntity productEntity;
	@ManyToOne
	@JoinColumn(name = "id_transaction")
	private TransactionEntity transactionEntity;

	public OrderEntity() {
	}

	public OrderEntity(OrderEntityBuilder builder) {
		this.id = builder.id;
		this.version = builder.version;
		this.amount = builder.amount;
		this.productEntity = builder.productEntity;
		this.transactionEntity = builder.transactionEntity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public PurchasedProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(PurchasedProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public TransactionEntity getTransactionEntity() {
		return transactionEntity;
	}

	public void setTransactionEntity(TransactionEntity transactionEntity) {
		this.transactionEntity = transactionEntity;
	}

	public static class OrderEntityBuilder {
		public Long version;
		private Long id;
		private Integer amount;
		private PurchasedProductEntity productEntity;
		private TransactionEntity transactionEntity;

		/**
		 * Default constructor for order entity builder.
		 *
		 */
		public OrderEntityBuilder() {
		}

		/**
		 * This is the method which add version to order.
		 * 
		 * @param Long
		 *            as version for order.
		 * @return Id of order.
		 */
		public OrderEntityBuilder withVersion(Long version) {
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
		public OrderEntityBuilder withId(Long id) {
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
		public OrderEntityBuilder withAmount(Integer amount) {
			this.amount = amount;
			return this;
		}

		/**
		 * This is the method which add product to order.
		 * 
		 * @param PurchasedProductEntity
		 *            as product for order.
		 * @return Product of order.
		 */
		public OrderEntityBuilder withProductEntity(PurchasedProductEntity productEntity) {
			this.productEntity = productEntity;
			return this;
		}

		/**
		 * This is the method which add transaction to order.
		 * 
		 * @param TransactionEntity
		 *            as transaction for order.
		 * @return Transaction of order.
		 */
		public OrderEntityBuilder withTransactionEntity(TransactionEntity transactionEntity) {
			this.transactionEntity = transactionEntity;
			return this;
		}

		/**
		 * This is the method which build order entity and if there is no
		 * demanded params throw exception.
		 * 
		 * @param Obligatory
		 *            String amount.
		 * @return Order entity.
		 */
		public OrderEntity build() {
			if (amount == null) {
				throw new InvalidCreationException("Incorrect order to be created");
			}

			return new OrderEntity(this);
		}
	}

	/**
	 * This is the method which assign order to transaction.
	 * 
	 * @param TransactionEntity
	 *            as transaction for order.
	 */
	public void assignTransaction(TransactionEntity transactionEntity) {
		this.transactionEntity = transactionEntity;
		transactionEntity.addOrder(this);
	}

	/**
	 * This is the method which remove order from transaction.
	 * 
	 * @param TransactionEntity
	 *            as transaction for order.
	 */
	public void removeTransaction() {
		transactionEntity.removeOrder(this);
		transactionEntity = null;
	}

	/**
	 * This is the method which assign order to product.
	 * 
	 * @param TransactionEntity
	 *            as transaction for order.
	 */
	public void assignProduct(PurchasedProductEntity productEntity) {
		this.productEntity = productEntity;
		productEntity.addOrder(this);
	}

	/**
	 * This is the method which remove order from product.
	 * 
	 * @param TransactionEntity
	 *            as transaction for order.
	 */
	public void removeProduct() {
		productEntity.removeOrder(this);
		productEntity = null;
	}

}

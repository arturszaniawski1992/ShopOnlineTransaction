package com.capgemini.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.capgemini.exception.InvalidCreationException;
import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

@Entity
@Table(name = "orders")
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

		public OrderEntityBuilder() {
		}

		public OrderEntityBuilder withVersion(Long version) {
			this.version = version;
			return this;
		}

		public OrderEntityBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public OrderEntityBuilder withAmount(Integer amount) {
			this.amount = amount;
			return this;
		}

		public OrderEntityBuilder withProductEntity(PurchasedProductEntity productEntity) {
			this.productEntity = productEntity;
			return this;
		}

		public OrderEntityBuilder withTransactionEntity(TransactionEntity transactionEntity) {
			this.transactionEntity = transactionEntity;
			return this;
		}

		public OrderEntity build() {
			if (amount == null) {
				throw new InvalidCreationException("Incorrect order to be created");
			}

			return new OrderEntity(this);
		}
	}

}

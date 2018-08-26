package com.capgemini.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.capgemini.enums.TransactionStatus;
import com.capgemini.exception.InvalidCreationException;
import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners({ UpdateListener.class, InsertListener.class })
public class TransactionEntity extends AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Version
	public Long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private Date dateTransaction;
	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;
	@Column
	Integer amount;
	@ManyToOne
	@JoinColumn(name = "id_client")
	private CustomerEntity customerEntity;
	@OneToMany(mappedBy = "transactionEntity", cascade = CascadeType.REMOVE)
	private List<OrderEntity> orders = new ArrayList<>();

	public TransactionEntity() {
	}

	public TransactionEntity(TransactionEntityBuilder builder) {
		this.version = builder.version;
		this.id = builder.id;
		this.amount = builder.amount;
		this.dateTransaction = builder.dateTransaction;
		this.transactionStatus = builder.transactionStatus;
		this.customerEntity = builder.customerEntity;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setDateTransaction(Date dateTransaction) {
		this.dateTransaction = dateTransaction;

	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public CustomerEntity getCustomerEntity() {
		return customerEntity;
	}

	public void setCustomerEntity(CustomerEntity customerEntity) {
		this.customerEntity = customerEntity;
		customerEntity.addTransaction(this);
	}

	public List<OrderEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}

	/**
	 * This is the method which remove customer from transaction.
	 * 
	 */
	public void removeCustomer() {
		customerEntity.removeTransaction(this);
		this.customerEntity = null;
	}

	/**
	 * This is the method which add order to transaction.
	 * 
	 * @param OrderEntity
	 *            as order for transaction.
	 */
	public boolean addOrder(OrderEntity orderEntity) {
		if (orders == null) {
			orders = new ArrayList<>();
		}
		return orders.add(orderEntity);
	}

	/**
	 * This is the method which remove order from transaction.
	 * 
	 * @param OrderEntity
	 *            as order for transaction.
	 */
	public boolean removeOrder(OrderEntity orderEntity) {
		if (orders == null) {
			return false;
		}
		return orders.remove(orderEntity);
	}

	public static class TransactionEntityBuilder {
		private Long version;
		private Long id;
		private Integer amount;
		private Date dateTransaction;
		private TransactionStatus transactionStatus;
		private CustomerEntity customerEntity;
		private List<OrderEntity> orders;

		/**
		 * Default constructor for transaction entity builder.
		 *
		 */
		public TransactionEntityBuilder() {
		}

		/**
		 * This is the method which add version to transaction.
		 * 
		 * @param Long
		 *            as version of transaction.
		 * @return version of transaction.
		 */
		public TransactionEntityBuilder withVersion(Long version) {
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
		public TransactionEntityBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		/**
		 * This is the method which add amount to transaction.
		 * 
		 * @param integer
		 *            as amount of transaction.
		 * @return Amount of transaction.
		 */
		public TransactionEntityBuilder withAmount(Integer amount) {
			this.amount = amount;
			return this;
		}

		/**
		 * This is the method which add date to transaction.
		 * 
		 * @param Date
		 *            as date of transaction.
		 * @return Date of transaction.
		 */
		public TransactionEntityBuilder withDateTransaction(Date dateTransaction) {
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
		public TransactionEntityBuilder withTransactionStatus(TransactionStatus transactionStatus) {
			this.transactionStatus = transactionStatus;
			return this;
		}

		/**
		 * This is the method which add customer to transaction.
		 * 
		 * @param CustomerEntity
		 *            as customer.
		 * @return Customer of transaction.
		 */
		public TransactionEntityBuilder withCustomerEntity(CustomerEntity customerEntity) {
			this.customerEntity = customerEntity;
			return this;
		}

		/**
		 * This is the method which add orders to transaction.
		 * 
		 * @param List
		 *            of orders.
		 * @return Orders of transaction.
		 */
		public TransactionEntityBuilder withOrders(List<OrderEntity> orders) {
			this.orders = orders;
			return this;
		}

		/**
		 * This is the method which build transaction entity and if there is no
		 * demanded params throw exception.
		 * 
		 * @param Obligatory
		 *            customer.
		 * @return Transaction entity.
		 */
		public TransactionEntity build() {
			if (customerEntity == null) {
				throw new InvalidCreationException("Incorrect transaction to be created");
			}

			return new TransactionEntity(this);
		}

	}

}

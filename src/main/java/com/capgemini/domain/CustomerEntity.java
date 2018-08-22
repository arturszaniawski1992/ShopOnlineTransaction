package com.capgemini.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.capgemini.embeded.AdressData;
import com.capgemini.exception.InvalidCreationException;
import com.capgemini.listeners.InsertListener;
import com.capgemini.listeners.UpdateListener;

@Entity
@Table(name = "customer")
@EntityListeners({ UpdateListener.class, InsertListener.class })
public class CustomerEntity extends AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Version
	public Long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, length = 50)
	private String firstName;
	@Column(nullable = false, length = 50)
	private String lastName;
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	@Column(length = 50)
	private String mobile;
	@Column(length = 50)
	private String mail;
	@Embedded
	private AdressData adressData;

	@OneToMany(mappedBy = "customerEntity", cascade = CascadeType.REMOVE)
	private List<TransactionEntity> transactions;

	/**
	 * Default constructor for customer entity.
	 *
	 */
	public CustomerEntity() {
	}

	public CustomerEntity(CustomerEntityBuilder builder) {
		this.version = builder.version;
		this.id = builder.id;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.mobile = builder.mobile;
		this.mail = builder.mail;
		this.dateOfBirth = builder.dateOfBirth;
		this.adressData = builder.adressData;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public AdressData getAdressData() {
		return adressData;
	}

	public void setAdressData(AdressData adressData) {
		this.adressData = adressData;
	}

	public List<TransactionEntity> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionEntity> transactions) {
		this.transactions = transactions;
	}

	public static CustomerEntityBuilder builder() {
		return new CustomerEntityBuilder();
	}

	public static class CustomerEntityBuilder {
		private Long version;
		private Long id;
		private String firstName;
		private String lastName;
		private LocalDate dateOfBirth;
		private String mobile;
		private String mail;
		private AdressData adressData;
		private List<TransactionEntity> transactions;

		/**
		 * Default constructor for customer entity builder.
		 *
		 */
		public CustomerEntityBuilder() {
		}

		public CustomerEntityBuilder withVersion(Long version) {
			this.version = version;
			return this;
		}

		/**
		 * This is the method which add id to customer.
		 * 
		 * @param Long
		 *            as id for customer.
		 * @return Id of customer.
		 */
		public CustomerEntityBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		/**
		 * This is the method which add first name to customer.
		 * 
		 * @param String
		 *            as first name of customer.
		 * @return first name of customer.
		 */
		public CustomerEntityBuilder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		/**
		 * This is the method which add last name to customer.
		 * 
		 * @param String
		 *            as last name of customer.
		 * @return last name of customer.
		 */
		public CustomerEntityBuilder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		/**
		 * This is the method which add date of birth to customer.
		 * 
		 * @param LocalDate
		 *            as date of birth of customer.
		 * @return date of birth of customer.
		 */
		public CustomerEntityBuilder withDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		/**
		 * This is the method which add mobile to customer.
		 * 
		 * @param String
		 *            as mobile of customer.
		 * @return mobile of customer.
		 */
		public CustomerEntityBuilder withMobile(String mobile) {
			this.mobile = mobile;
			return this;
		}

		/**
		 * This is the method which add mail to customer.
		 * 
		 * @param String
		 *            as mail of customer.
		 * @return mail of customer.
		 */
		public CustomerEntityBuilder withMail(String mail) {
			this.mail = mail;
			return this;
		}

		/**
		 * This is the method which add adress to customer.
		 * 
		 * @param AdressData
		 *            as adress for customer.
		 * @return adress of customer.
		 */
		public CustomerEntityBuilder withAdressData(AdressData adressData) {
			this.adressData = adressData;
			return this;
		}

		public CustomerEntityBuilder withTransactions(List<TransactionEntity> transactions) {
			this.transactions = transactions;
			return this;
		}

		/**
		 * This is the method which build customer entity and if there is no
		 * demanded params throw exception.
		 * 
		 * @param Obligatory
		 *            String firstname, String lastname, String mobile,.
		 * @return Customer entity.
		 */
		public CustomerEntity build() {
			if (firstName == null || lastName == null || mobile == null) {
				throw new InvalidCreationException("Incorrect customer to be created");
			}

			return new CustomerEntity(this);
		}

	}
}

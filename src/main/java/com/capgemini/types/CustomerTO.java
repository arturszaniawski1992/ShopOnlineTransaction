package com.capgemini.types;

import java.time.LocalDate;
import java.util.List;

import embedded.AdressDataEntity;
import exception.InvalidCreationException;

public class CustomerTO {

	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String mobile;
	private String mail;
	private AdressDataEntity adressData;
	private List<Long> transactions;

	public CustomerTO() {
	}

	public CustomerTO(CustomerTOBuilder builder) {
		this.id = builder.id;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.dateOfBirth = builder.dateOfBirth;
		this.mobile = builder.mobile;
		this.mail = builder.mail;
		this.adressData = builder.adressData;
		this.transactions = builder.transactions;
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

	public AdressDataEntity getAdressData() {
		return adressData;
	}

	public void setAdressData(AdressDataEntity adressData) {
		this.adressData = adressData;
	}

	public List<Long> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Long> transactions) {
		this.transactions = transactions;
	}

	public static class CustomerTOBuilder {
		private Long id;
		private String firstName;
		private String lastName;
		private LocalDate dateOfBirth;
		private String mobile;
		private String mail;
		private AdressDataEntity adressData;
		private List<Long> transactions;

		/**
		 * Default constructor for customer entity builder.
		 *
		 */
		public CustomerTOBuilder() {

		}

		/**
		 * This is the method which add id to customer.
		 * 
		 * @param Long
		 *            as id for customer.
		 * @return Id of customer.
		 */
		public CustomerTOBuilder withId(Long id) {
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
		public CustomerTOBuilder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		/**
		 * This is the method which add first name to customer.
		 * 
		 * @param String
		 *            as first name of customer.
		 * @return first name of customer.
		 */
		public CustomerTOBuilder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		/**
		 * This is the method which add last name to customer.
		 * 
		 * @param String
		 *            as last name of customer.
		 * @return last name of customer.
		 */
		public CustomerTOBuilder withDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		/**
		 * This is the method which add credit card number to customer.
		 * 
		 * @param String
		 *            as credit card number.
		 * @return credit card number of customer.
		 */
		public CustomerTOBuilder withMobile(String mobile) {
			this.mobile = mobile;
			return this;
		}

		/**
		 * This is the method which add date of birth to customer.
		 * 
		 * @param LocalDate
		 *            as date of birth of customer.
		 * @return date of birth of customer.
		 */
		public CustomerTOBuilder withMail(String mail) {
			this.mail = mail;
			return this;
		}

		/**
		 * This is the method which add adress to customer.
		 * 
		 * @param AdressDataTO
		 *            as adress for customer.
		 * @return adress of customer.
		 */
		public CustomerTOBuilder withAdressData(AdressDataEntity adressData) {
			this.adressData = adressData;
			return this;
		}

		/**
		 * This is the method which add contracts to customer.
		 * 
		 * @param List
		 *            as contracts id for customer.
		 * @return contracts of customer.
		 */
		public CustomerTOBuilder withTransactions(List<Long> transactions) {
			this.transactions = transactions;
			return this;
		}

		/**
		 * This is the method which build customer TO and if there is no
		 * demanded params throw exception.
		 * 
		 * @param Obligatory
		 *            String firstname, String lastname, String mobile,.
		 * @return Customer entity.
		 */
		public CustomerTO build() {
			if (firstName == null || lastName == null || mobile == null) {
				throw new InvalidCreationException("Incorrect customer to be created");
			}
			return new CustomerTO(this);
		}

	}
}

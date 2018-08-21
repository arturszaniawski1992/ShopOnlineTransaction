package com.capgemini.types;

public class AdressDataTO {
	private String street;
	private int number;
	private String city;
	private String postCode;

	public AdressDataTO() {
		super();
	}

	public AdressDataTO(AdressDataTOBuilder builder) {
		this.street = builder.street;
		this.number = builder.number;
		this.city = builder.city;
		this.postCode = builder.postCode;

	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public static AdressDataTOBuilder builder() {
		return new AdressDataTOBuilder();
	}

	public static class AdressDataTOBuilder {
		private String street;
		private int number;
		private String city;
		private String postCode;

		public AdressDataTOBuilder() {
			super();
		}

		/**
		 * This is the method which add street to adress.
		 * 
		 * @param String
		 *            as street in adress.
		 * @return Street.
		 */
		public AdressDataTOBuilder withStreet(String street) {
			this.street = street;
			return this;
		}

		/**
		 * This is the method which add number to adress.
		 * 
		 * @param Integer
		 *            as number in adress.
		 * @return Number.
		 */
		public AdressDataTOBuilder withNumber(int number) {
			this.number = number;
			return this;
		}

		/**
		 * This is the method which add city to adress.
		 * 
		 * @param String
		 *            as city in adress.
		 * @return City.
		 */
		public AdressDataTOBuilder withCity(String city) {
			this.city = city;
			return this;
		}

		/**
		 * This is the method which add post code to adress.
		 * 
		 * @param String
		 *            as post code in adress.
		 * @return Post code.
		 */
		public AdressDataTOBuilder withPostCode(String postCode) {
			this.postCode = postCode;
			return this;
		}

		/**
		 * This is the method which create new AdressTO Object.
		 * 
		 * @return New object.
		 */
		public AdressDataTO build() {

			return new AdressDataTO(this);
		}
	}

}

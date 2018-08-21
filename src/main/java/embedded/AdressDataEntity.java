package embedded;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AdressDataEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "street", length = 40, nullable = false)
	private String street;
	@Column(name = "number", length = 30, nullable = false)
	private Integer number;
	@Column(name = "city", length = 30, nullable = false)
	private String city;
	@Column(name = "post_code", length = 6, nullable = false)
	private String postCode;

	public AdressDataEntity() {
		super();
	}

	public AdressDataEntity(AdressDataEntityBuilder builder) {
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

	public static AdressDataEntityBuilder builder() {
		return new AdressDataEntityBuilder();
	}

	public static class AdressDataEntityBuilder {

		private String street;
		private int number;
		private String city;
		private String postCode;

		public AdressDataEntityBuilder() {
			super();
		}

		/**
		 * This is the method which add street to adress.
		 * 
		 * @param String
		 *            as street in adress.
		 * @return Street.
		 */
		public AdressDataEntityBuilder withStreet(String street) {
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
		public AdressDataEntityBuilder withNumber(Integer number) {
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
		public AdressDataEntityBuilder withCity(String city) {
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
		public AdressDataEntityBuilder withPostCode(String postCode) {
			this.postCode = postCode;
			return this;

		}

		/**
		 * This is the method which create new AdressEntity Object.
		 * 
		 * @return New object.
		 */
		public AdressDataEntity build() {
			return new AdressDataEntity(this);
		}
	}

}

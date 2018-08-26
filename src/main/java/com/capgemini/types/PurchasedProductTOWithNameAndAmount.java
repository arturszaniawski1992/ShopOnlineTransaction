package com.capgemini.types;

public class PurchasedProductTOWithNameAndAmount {

	private String productName;
	private Integer amount;

	public PurchasedProductTOWithNameAndAmount(String productName, Integer amount) {
		super();
		this.productName = productName;
		this.amount = amount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}

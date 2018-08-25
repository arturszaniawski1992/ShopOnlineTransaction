package com.capgemini.types;

import java.sql.Date;

public class TransactionSearchCriteria {

	private String customerName;
	private Date dateFrom;
	private Date dateTo;
	private String productName;
	private Double totalTransactionAmountFrom;
	private Double totalTransactionAmountTo;

	public TransactionSearchCriteria(String customerName, Date dateFrom, Date dateTo, String productName,
			Double totalTransactionAmountFrom, Double totalTransactionAmountTo) {
		super();
		this.customerName = customerName;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.productName = productName;
		this.totalTransactionAmountFrom = totalTransactionAmountFrom;
		this.totalTransactionAmountTo = totalTransactionAmountTo;
	}

	public String getName() {
		return customerName;
	}

	public void setName(String name) {
		this.customerName = name;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getTotalTransactionAmountFrom() {
		return totalTransactionAmountFrom;
	}

	public void setTotalTransactionAmountFrom(Double totalTransactionAmountFrom) {
		this.totalTransactionAmountFrom = totalTransactionAmountFrom;
	}

	public Double getTotalTransactionAmountTo() {
		return totalTransactionAmountTo;
	}

	public void setTotalTransactionAmountTo(Double totalTransactionAmountTo) {
		this.totalTransactionAmountTo = totalTransactionAmountTo;
	}

}

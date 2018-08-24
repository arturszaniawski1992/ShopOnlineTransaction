package com.capgemini.exception;

public class TransactionNotAllowedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransactionNotAllowedException() {
	}

	public TransactionNotAllowedException(String message) {
		super(message);
	}

}

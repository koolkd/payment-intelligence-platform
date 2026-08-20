package com.payment.platform.exception;

public class PaymentAlreadyProcessingException
		extends RuntimeException {

	public PaymentAlreadyProcessingException(String message) {
		super(message);
	}
}

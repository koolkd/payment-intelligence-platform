package com.payment.platform.dto.request;


import com.payment.platform.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentCreatedEvent {

	private String paymentId;
	private String customerId;
	private BigDecimal amount;
	private String currency;
	private PaymentStatus status;
	private LocalDateTime createdAt;

	public PaymentCreatedEvent() {
	}

	public PaymentCreatedEvent(
			String paymentId,
			String customerId,
			BigDecimal amount,
			String currency,
			PaymentStatus status,
			LocalDateTime createdAt) {

		this.paymentId = paymentId;
		this.customerId = customerId;
		this.amount = amount;
		this.currency = currency;
		this.status = status;
		this.createdAt = createdAt;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}

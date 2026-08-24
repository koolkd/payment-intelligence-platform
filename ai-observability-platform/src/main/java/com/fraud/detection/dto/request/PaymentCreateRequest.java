package com.fraud.detection.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class PaymentCreateRequest {

	@NotBlank(message = "Customer ID is required")
	private String customerId;

	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be greater than zero")
	private BigDecimal amount;

	@NotBlank(message = "Currency is required")
	@Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
	private String currency;

	public PaymentCreateRequest() {
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}

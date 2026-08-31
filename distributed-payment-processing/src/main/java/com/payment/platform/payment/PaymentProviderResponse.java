package com.payment.platform.payment;

import com.payment.platform.entity.PaymentStatus;

public class PaymentProviderResponse {

	private PaymentStatus status;
	private String providerTransactionId;
	private String failureReason;
	private PaymentProviderResult result;

	public PaymentProviderResponse(PaymentStatus status, String providerTransactionId, String failureReason,PaymentProviderResult result) {
		this.status = status;
		this.providerTransactionId = providerTransactionId;
		this.failureReason = failureReason;
		this.result = result;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public String getProviderTransactionId() {
		return providerTransactionId;
	}

	public void setProviderTransactionId(String providerTransactionId) {
		this.providerTransactionId = providerTransactionId;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public PaymentProviderResult getResult() {
		return result;
	}

	public void setResult(PaymentProviderResult result) {
		this.result = result;
	}
}

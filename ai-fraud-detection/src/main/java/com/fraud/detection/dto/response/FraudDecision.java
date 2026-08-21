package com.fraud.detection.dto.response;

import com.fraud.detection.entity.FraudDecisionType;

public class FraudDecision {

	private String paymentId;
	private double riskScore;
	private FraudDecisionType decision;
	private String reason;

	public FraudDecision() {
	}

	public FraudDecision(String paymentId,
	                     double riskScore,
	                     FraudDecisionType decision,
	                     String reason) {
		this.paymentId = paymentId;
		this.riskScore = riskScore;
		this.decision = decision;
		this.reason = reason;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public double getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(double riskScore) {
		this.riskScore = riskScore;
	}

	public FraudDecisionType getDecision() {
		return decision;
	}

	public void setDecision(FraudDecisionType decision) {
		this.decision = decision;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "FraudDecision{" +
				"paymentId=" + paymentId +
				", riskScore=" + riskScore +
				", decision=" + decision +
				", reason='" + reason + '\'' +
				'}';
	}
}
package com.fraud.detection.service.impl;

import com.fraud.detection.dto.request.PaymentCreatedEvent;
import com.fraud.detection.dto.response.FraudDecision;
import com.fraud.detection.dto.response.PaymentResponse;
import com.fraud.detection.entity.FraudDecisionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FraudDecisionService {

	public FraudDecision evaluate(PaymentCreatedEvent payment) {

		BigDecimal amount = payment.getAmount();

		if (amount.compareTo(BigDecimal.valueOf(50000)) > 0) {

			return new FraudDecision(
					payment.getPaymentId(),
					0.90,
					FraudDecisionType.BLOCK,
					"High transaction amount"
			);

		} else if (amount.compareTo(BigDecimal.valueOf(10000)) > 0) {

			return new FraudDecision(
					payment.getPaymentId(),
					0.60,
					FraudDecisionType.REVIEW,
					"Medium transaction amount"
			);

		} else {

			return new FraudDecision(
					payment.getPaymentId(),
					0.10,
					FraudDecisionType.APPROVE,
					"Low transaction amount"
			);
		}
	}
}
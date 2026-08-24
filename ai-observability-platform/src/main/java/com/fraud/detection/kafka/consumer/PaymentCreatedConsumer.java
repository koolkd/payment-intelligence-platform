package com.fraud.detection.kafka.consumer;

import com.fraud.detection.dto.request.PaymentCreatedEvent;
import com.fraud.detection.dto.response.FraudDecision;
import com.fraud.detection.dto.response.PaymentResponse;
import com.fraud.detection.service.impl.FraudDecisionService;
import jakarta.annotation.PostConstruct;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCreatedConsumer {

	private final FraudDecisionService fraudDecisionService;

	public PaymentCreatedConsumer(FraudDecisionService fraudDecisionService) {
		this.fraudDecisionService = fraudDecisionService;
	}

	@KafkaListener(
			topics = "payment.created",
			groupId = "fraud-service-group"
	)
	public void consume(PaymentCreatedEvent payment) {

		FraudDecision decision =
				fraudDecisionService.evaluate(payment);

		System.out.println("Fraud decision: " + decision);
	}
}

package com.payment.platform.kafka.producer;

import com.payment.platform.dto.request.PaymentCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventProducer {

	private static final String TOPIC = "payment.created";

	private final KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate;

	public PaymentEventProducer(
			KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate) {

		this.kafkaTemplate = kafkaTemplate;
	}

	public void publish(PaymentCreatedEvent event) {

		kafkaTemplate.send(
				TOPIC,
				event.getPaymentId(),
				event
		);

		System.out.println(
				"Published payment event: " + event.getPaymentId()
		);
	}
}

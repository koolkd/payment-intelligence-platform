package com.payment.platform.kafka.consumer;

import com.payment.platform.dto.request.PaymentCreatedEvent;
import com.payment.platform.service.PaymentProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCreatedConsumer {

    private static final String TOPIC = "payment.created";
    private static final String GROUP_ID = "payment-processing-group";

    private final PaymentProcessingService paymentProcessingService;

    public PaymentCreatedConsumer(PaymentProcessingService paymentProcessingService) {
        this.paymentProcessingService = paymentProcessingService;
    }

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void consume(PaymentCreatedEvent event) {
        System.out.println("Received payment event: " + event.getPaymentId());

        paymentProcessingService.process(event);
    }
}

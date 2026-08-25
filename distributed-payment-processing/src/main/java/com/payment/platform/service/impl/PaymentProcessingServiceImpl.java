package com.payment.platform.service.impl;

import com.payment.platform.dto.request.PaymentCreatedEvent;
import com.payment.platform.entity.Payment;
import com.payment.platform.entity.PaymentStatus;
import com.payment.platform.repository.PaymentRepository;
import com.payment.platform.service.PaymentProcessingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentProcessingServiceImpl implements PaymentProcessingService {

    private final PaymentRepository paymentRepository;

    public PaymentProcessingServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public void process(PaymentCreatedEvent event) {
        Payment payment = paymentRepository.findByPaymentId(event.getPaymentId())
                .orElseThrow(() -> new IllegalStateException(
                        "Payment not found: " + event.getPaymentId()));

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            System.out.println("Payment already completed: " + event.getPaymentId());
            return;
        }

        payment.setStatus(PaymentStatus.PROCESSING);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        System.out.println("Payment moved to PROCESSING: " + event.getPaymentId());
    }
}

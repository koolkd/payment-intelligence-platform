package com.payment.platform.service.impl;

import com.payment.platform.dto.request.PaymentCreatedEvent;
import com.payment.platform.entity.Payment;
import com.payment.platform.entity.PaymentStatus;
import com.payment.platform.payment.PaymentProcessingResult;
import com.payment.platform.payment.PaymentProviderClient;
import com.payment.platform.payment.PaymentProviderResponse;
import com.payment.platform.payment.PaymentProviderResult;
import com.payment.platform.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentProcessingService {

    private final PaymentRepository paymentRepository;
	private final PaymentProviderClient paymentProviderClient;

    public PaymentProcessingService(PaymentRepository paymentRepository,PaymentProviderClient paymentProviderClient) {
        this.paymentRepository = paymentRepository;
		this.paymentProviderClient = paymentProviderClient;
    }


    @Transactional
    public PaymentProcessingResult process(PaymentCreatedEvent event) {
        Payment payment = paymentRepository.findByPaymentId(event.getPaymentId())
                .orElseThrow(() -> new IllegalStateException(
                        "Payment not found: " + event.getPaymentId()));

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            System.out.println("Payment already completed: " + event.getPaymentId());
             return PaymentProcessingResult.SUCCESS;
        }

        payment.setStatus(PaymentStatus.PROCESSING);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

	    PaymentProviderResponse response =
			    paymentProviderClient.processPayment(payment);
	    if (response.getResult() == PaymentProviderResult.SUCCESS) {
		    payment.setStatus(PaymentStatus.COMPLETED);
		    payment.setProviderTransactionId(
				    response.getProviderTransactionId()
		    );
		    payment.setUpdatedAt(LocalDateTime.now());
		    paymentRepository.save(payment);
		    return PaymentProcessingResult.SUCCESS;
	    }
	    if (response.getResult() == PaymentProviderResult.BUSINESS_FAILURE) {
		    payment.setStatus(PaymentStatus.FAILED);
		    payment.setUpdatedAt(LocalDateTime.now());
		    paymentRepository.save(payment);

		    return PaymentProcessingResult.FAILED;
	    }
	    if (response.getResult() == PaymentProviderResult.TIMEOUT) {
		    return PaymentProcessingResult.RETRY;
	    }

	    throw new IllegalStateException(
			    "Unexpected provider result: " + response.getResult()
	    );
    }
}

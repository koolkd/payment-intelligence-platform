package com.payment.platform.service.impl;


import com.payment.platform.dto.request.PaymentCreateRequest;
import com.payment.platform.dto.request.PaymentCreatedEvent;
import com.payment.platform.dto.response.PaymentResponse;
import com.payment.platform.entity.Payment;
import com.payment.platform.entity.PaymentStatus;
import com.payment.platform.exception.PaymentAlreadyProcessingException;
import com.payment.platform.idempotency.IdempotencyService;
import com.payment.platform.kafka.producer.PaymentEventProducer;
import com.payment.platform.repository.PaymentRepository;
import com.payment.platform.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final IdempotencyService idempotencyService;
	private final PaymentLockService paymentLockService;
	private final PaymentEventProducer paymentEventProducer;

	public PaymentServiceImpl(PaymentRepository paymentRepository, IdempotencyService idempotencyService,PaymentLockService paymentLockService,PaymentEventProducer paymentEventProducer) {
		this.paymentRepository = paymentRepository;
		this.idempotencyService = idempotencyService;
		this.paymentLockService = paymentLockService;
		this.paymentEventProducer=paymentEventProducer;
	}

	@Override
	public PaymentResponse createPayment(PaymentCreateRequest request, String idempotencyKey) {
		String lockValue = paymentLockService.acquireLock(idempotencyKey);

		if (lockValue == null) {
			throw new PaymentAlreadyProcessingException(
					"Payment is already being processed"
			);
		}
		try {

	        PaymentResponse existingResponse =
			        idempotencyService.get(idempotencyKey);

	        if (existingResponse != null) {
		        return existingResponse;
	        }
	        Payment payment = new Payment();

	        payment.setPaymentId(generatePaymentId());
	        payment.setCustomerId(request.getCustomerId());
	        payment.setAmount(request.getAmount());
	        payment.setCurrency(request.getCurrency());
	        payment.setStatus(PaymentStatus.PENDING);

	        LocalDateTime now = LocalDateTime.now();
	        payment.setCreatedAt(now);
	        payment.setUpdatedAt(now);

	        Payment savedPayment = paymentRepository.save(payment);

			PaymentCreatedEvent event = new PaymentCreatedEvent(
					savedPayment.getPaymentId(),
					savedPayment.getCustomerId(),
					savedPayment.getAmount(),
					savedPayment.getCurrency(),
					savedPayment.getStatus(),
					savedPayment.getCreatedAt()
			);

			paymentEventProducer.publish(event);

	        PaymentResponse response = mapToResponse(savedPayment);
	        idempotencyService.save(idempotencyKey, response);

	        return mapToResponse(savedPayment);
        }finally {

	        paymentLockService.releaseLock(
			        idempotencyKey,
			        lockValue
	        );
        }
	}

	private String generatePaymentId() {
		return "PAY-" + UUID.randomUUID()
				.toString()
				.substring(0, 8)
				.toUpperCase();
	}

	private PaymentResponse mapToResponse(Payment payment) {

		PaymentResponse response = new PaymentResponse();

		response.setPaymentId(payment.getPaymentId());
		response.setCustomerId(payment.getCustomerId());
		response.setAmount(payment.getAmount());
		response.setCurrency(payment.getCurrency());
		response.setStatus(payment.getStatus());
		response.setCreatedAt(payment.getCreatedAt());

		return response;
	}
}
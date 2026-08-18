package com.payment.platform.service.impl;

import com.payment.platform.dto.request.PaymentCreateRequest;
import com.payment.platform.dto.response.PaymentResponse;
import com.payment.platform.entity.Payment;
import com.payment.platform.entity.PaymentStatus;
import com.payment.platform.repository.PaymentRepository;
import com.payment.platform.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	public PaymentServiceImpl(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@Override
	public PaymentResponse createPayment(PaymentCreateRequest request) {

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

		return mapToResponse(savedPayment);
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
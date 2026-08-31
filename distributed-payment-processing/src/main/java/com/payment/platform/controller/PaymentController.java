package com.payment.platform.controller;


import com.payment.platform.dto.request.PaymentCreateRequest;
import com.payment.platform.dto.response.PaymentResponse;
import com.payment.platform.service.impl.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostMapping
	public ResponseEntity<PaymentResponse> createPayment(
			@RequestHeader("Idempotency-Key") String idempotencyKey,
			@Valid @RequestBody PaymentCreateRequest request) {

		PaymentResponse response = paymentService.createPayment(request,idempotencyKey);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);
	}
}

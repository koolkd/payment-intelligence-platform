package com.payment.platform.service;

import com.payment.platform.dto.request.PaymentCreateRequest;
import com.payment.platform.dto.response.PaymentResponse;
import com.payment.platform.entity.Payment;

public interface PaymentService {

	PaymentResponse createPayment(PaymentCreateRequest request,String idempotencyKey);

}



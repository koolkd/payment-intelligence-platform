package com.payment.platform.service;

import com.payment.platform.dto.request.PaymentCreatedEvent;

public interface PaymentProcessingService {

    void process(PaymentCreatedEvent event);
}

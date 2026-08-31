package com.payment.platform.payment;

import com.payment.platform.entity.Payment;

public interface PaymentProviderClient {
	PaymentProviderResponse processPayment(Payment payment);
}

package com.payment.platform.payment;

import com.payment.platform.entity.Payment;
import com.payment.platform.entity.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentProviderSimulator implements PaymentProviderClient {

	@Override
	public PaymentProviderResponse processPayment(Payment payment) {

		String transactionId = "TXN-" + UUID.randomUUID();
		PaymentProviderResult result = PaymentProviderResult.SUCCESS;

		return new PaymentProviderResponse(
				PaymentStatus.PROCESSING,
				null,
				"Provider timeout",
				PaymentProviderResult.TIMEOUT
		);
	}
}

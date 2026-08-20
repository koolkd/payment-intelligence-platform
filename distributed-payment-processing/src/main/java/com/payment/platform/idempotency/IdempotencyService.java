package com.payment.platform.idempotency;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.platform.dto.response.PaymentResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class  IdempotencyService {

	private static final String KEY_PREFIX = "idempotency:";

	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	public IdempotencyService(
			RedisTemplate<String, String> redisTemplate,
			ObjectMapper objectMapper) {

		this.redisTemplate = redisTemplate;
		this.objectMapper = objectMapper;
	}


	public PaymentResponse get(String idempotencyKey) {

		String key = KEY_PREFIX + idempotencyKey;

		String value = redisTemplate.opsForValue().get(key);

		if (value == null) {
			return null;
		}

		try {
			return objectMapper.readValue(value, PaymentResponse.class);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(
					"Failed to deserialize idempotency response", e);
		}
	}


	public void save(
			String idempotencyKey,
			PaymentResponse response) {

		String key = KEY_PREFIX + idempotencyKey;

		try {
			String value = objectMapper.writeValueAsString(response);

			redisTemplate.opsForValue()
					.set(key, value, Duration.ofHours(24));

		} catch (JsonProcessingException e) {
			throw new IllegalStateException(
					"Failed to serialize idempotency response", e);
		}
	}
}

package com.payment.platform.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class PaymentLockService {

	private final RedisTemplate<String, String> redisTemplate;

	public PaymentLockService(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public String acquireLock(String idempotencyKey) {

		String lockKey = "lock:payment:" + idempotencyKey;

		String lockValue = UUID.randomUUID().toString();

		Boolean acquired = redisTemplate
				.opsForValue()
				.setIfAbsent(
						lockKey,
						lockValue,
						Duration.ofSeconds(30)
				);

		if (Boolean.TRUE.equals(acquired)) {
			return lockValue;
		}

		return null;
	}


	public void releaseLock(String idempotencyKey, String lockValue) {

		String lockKey = "lock:payment:" + idempotencyKey;

		String currentValue = redisTemplate
				.opsForValue()
				.get(lockKey);

		if (lockValue.equals(currentValue)) {
			redisTemplate.delete(lockKey);
		}
	}
}

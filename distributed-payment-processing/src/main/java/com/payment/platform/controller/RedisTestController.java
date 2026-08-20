package com.payment.platform.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

	private final RedisTemplate<String, String> redisTemplate;

	public RedisTestController(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@GetMapping("/api/v1/redis-test")
	public String testRedis() {

		redisTemplate.opsForValue()
				.set("redis-test", "hello-redis");

		return redisTemplate.opsForValue()
				.get("redis-test");
	}
}
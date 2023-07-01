package com.dreamcup.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OnlineStatusService {

	private static final int SESSION_EXPIRY_TIME = 300;

	private final RedisTemplate redisTemplate;

	public void refreshUserSession(String username, String jwtToken) {
		String sessionKey = "session_" + username;
		redisTemplate.opsForValue().set(sessionKey, jwtToken, SESSION_EXPIRY_TIME, TimeUnit.SECONDS);
	}

	public boolean isUserOnline(String username) {
		String sessionKey = "session_" + username;
		return redisTemplate.hasKey(sessionKey);
	}

}

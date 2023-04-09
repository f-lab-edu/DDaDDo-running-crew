package com.flab.comen.global.jwt.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.flab.comen.global.jwt.domain.RefreshToken;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Component
public class TokenCache {

	private final Cache<String, RefreshToken> cache;

	private static final int expirationDate = 14;

	public TokenCache() {
		this.cache = Caffeine.newBuilder()
			.expireAfterWrite(expirationDate, TimeUnit.DAYS)
			.build();
	}

	public void saveToken(String email, String refreshToken) {
		cache.put(email, RefreshToken.of(email, refreshToken, LocalDateTime.now().plusDays(expirationDate)));
	}

	public void deleteToken(String email) {
		cache.invalidate(email);
	}

	public Optional<RefreshToken> hasToken(String email) {
		return Optional.ofNullable(cache.getIfPresent(email));
	}
}

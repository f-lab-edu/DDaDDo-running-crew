package com.flab.comen.global.jwt.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {
	private String email;
	private String refreshToken;
	private LocalDateTime expiredTime;

	public static RefreshToken of(String email, String refreshToken, LocalDateTime expiredTime) {
		RefreshToken token = new RefreshToken();
		token.email = email;
		token.refreshToken = refreshToken;
		token.expiredTime = expiredTime;
		return token;
	}
}

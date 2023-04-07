package com.flab.comen.member.dto.response;

public record TokenResponse(
	String accessToken,
	String refreshToken
) {
}

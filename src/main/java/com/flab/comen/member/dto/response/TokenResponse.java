package com.flab.comen.member.dto.response;

import lombok.Getter;

@Getter
public class TokenResponse {

	private String accessToken;

	public TokenResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}

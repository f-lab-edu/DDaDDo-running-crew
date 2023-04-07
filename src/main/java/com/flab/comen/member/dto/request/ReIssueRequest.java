package com.flab.comen.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReIssueRequest(
	@NotBlank(message = "액세스 토큰 정보가 필요합니다.")
	String accessToken,
	@NotBlank(message = "리프레시 토큰 정보가 필요합니다.")
	String refreshToken
) {
}

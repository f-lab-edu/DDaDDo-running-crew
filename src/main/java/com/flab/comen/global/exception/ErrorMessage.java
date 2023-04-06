package com.flab.comen.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorMessage {

	DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 등록된 이메일 주소입니다."),
	NOT_EXISTED_MEMBER(HttpStatus.NOT_FOUND, "등록된 회원 정보가 없습니니다."),
	NOT_ACTIVATED_MEMBER(HttpStatus.NOT_FOUND, "휴면 중이거나 삭제된 계정입니다. 관리자에게 문의해 주세요."),
	NOT_MATCHED_LOGIN_INFORMATION(HttpStatus.CONFLICT, "아이디나 패스워드가 일치하지 않습니다."),

	// jwt
	UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED, "인증이 필요한 토큰입니다."),
	FORBIDDEN_TOKEN(HttpStatus.FORBIDDEN, "권한이 필요한 토큰입니다.");

	private HttpStatus status;
	private String message;

	ErrorMessage(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}

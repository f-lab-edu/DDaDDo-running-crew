package com.flab.comen.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorMessage {

	DUPLICATED_EMAIL(409, HttpStatus.CONFLICT, "이미 등록된 이메일 주소입니다."),
	NOT_EXISTED_MEMBER(404, HttpStatus.NOT_FOUND, "등록된 회원 정보가 없습니니다."),
	NOT_ACTIVATED_MEMBER(404, HttpStatus.NOT_FOUND, "휴면 중이거나 삭제된 계정입니다. 관리자에게 문의해 주세요."),
	NOT_MATCHED_LOGIN_INFORMATION(409, HttpStatus.CONFLICT, "아이디나 패스워드가 일치하지 않습니다."),

	// jwt
	UNAUTHORIZED_TOKEN(401, HttpStatus.UNAUTHORIZED, "인증이 필요한 토큰입니다."),
	FORBIDDEN_TOKEN(403, HttpStatus.FORBIDDEN, "권한이 필요한 토큰입니다.");

	private int status;
	private HttpStatus error;
	private String message;

	ErrorMessage(int status, HttpStatus error, String message) {
		this.status = status;
		this.error = error;
		this.message = message;
	}
}

package com.flab.comen.global.exception;

public enum ErrorCode {

	DUPLICATED_EMAIL("이미 등록된 이메일 주소입니다.");

	private String message;

	ErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}

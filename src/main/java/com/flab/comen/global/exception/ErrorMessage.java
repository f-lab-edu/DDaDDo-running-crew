package com.flab.comen.global.exception;

public enum ErrorMessage {

	DUPLICATED_EMAIL("이미 등록된 이메일 주소입니다."),
	MEMBER_NOT_FOUND("등록된 회원 정보가 없습니니다.");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}

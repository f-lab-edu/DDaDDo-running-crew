package com.flab.comen.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorMessage {

	DUPLICATED_EMAIL(409, HttpStatus.CONFLICT, "이미 등록된 이메일 주소입니다."),
	NOT_EXISTED_MEMBER(404, HttpStatus.NOT_FOUND, "등록된 회원 정보가 없습니니다.");

	private int status;
	private HttpStatus error;
	private String message;

	ErrorMessage(int status, HttpStatus error, String message) {
		this.status = status;
		this.error = error;
		this.message = message;
	}
}

package com.flab.comen.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
	private final int status;
	private final HttpStatus error;
	private final String message;

	public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorMessage errorMessage) {
		return ResponseEntity
			.status(errorMessage.getStatus())
			.body(ErrorResponse.builder()
				.status(errorMessage.getStatus())
				.error(errorMessage.getError())
				.message(errorMessage.getMessage())
				.build()
			);
	}
}

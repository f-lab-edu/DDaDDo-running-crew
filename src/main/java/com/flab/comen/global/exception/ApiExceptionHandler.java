package com.flab.comen.global.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.flab.comen.member.exception.MemberNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new HashMap<>();

		exception.getBindingResult().getAllErrors()
			.forEach(e -> errors.put(((FieldError)e).getField(), e.getDefaultMessage()));

		log.error("MethodArgumentNotValidException : ", exception);

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
		log.error("IllegalArgumentException : ", exception);

		return ResponseEntity.badRequest().body(exception.getMessage());
	}

	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException exception) {
		log.error("MemberNotFoundException : ", exception);

		return ResponseEntity.badRequest().body(exception.getMessage());
	}
}

package com.flab.comen.global.exception;

import static com.flab.comen.global.exception.ErrorMessage.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.flab.comen.member.exception.DuplicatedEmailException;
import com.flab.comen.member.exception.NotActivatedMemberException;
import com.flab.comen.member.exception.NotExistedMemberException;
import com.flab.comen.member.exception.NotExistedTokenException;
import com.flab.comen.member.exception.NotMatchedInformationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {
		log.error("MethodArgumentNotValidException : ", exception);

		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getAllErrors()
			.forEach(e -> errors.put(((FieldError)e).getField(), e.getDefaultMessage()));

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(DuplicatedEmailException.class)
	private ResponseEntity<ErrorResponse> handleDuplicatedEmailException(DuplicatedEmailException exception) {
		log.error("DuplicatedEmailException : ", exception);
		return ErrorResponse.toResponseEntity(DUPLICATED_EMAIL);
	}

	@ExceptionHandler(NotExistedMemberException.class)
	private ResponseEntity<ErrorResponse> handleNotExistedMemberException(NotExistedMemberException exception) {
		log.error("NotExistedMemberException : ", exception);
		return ErrorResponse.toResponseEntity(NOT_EXISTED_MEMBER);
	}

	@ExceptionHandler(NotMatchedInformationException.class)
	private ResponseEntity<ErrorResponse> handleNotMatchedInformationException(
		NotMatchedInformationException exception) {
		log.error("NotMatchedInformationException : ", exception);
		return ErrorResponse.toResponseEntity(NOT_MATCHED_LOGIN_INFORMATION);
	}

	@ExceptionHandler(NotActivatedMemberException.class)
	private ResponseEntity<ErrorResponse> handleNotActivatedMemberException(NotActivatedMemberException exception) {
		log.error("NotActivatedMemberException : ", exception);
		return ErrorResponse.toResponseEntity(NOT_ACTIVATED_MEMBER);
	}

	@ExceptionHandler(NotExistedTokenException.class)
	private ResponseEntity<ErrorResponse> handleInvalidTokenException(NotExistedTokenException exception) {
		log.error("InvalidTokenException : ", exception);
		return ErrorResponse.toResponseEntity(NOT_EXISTED_TOKEN);
	}
}

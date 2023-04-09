package com.flab.comen.member.exception;

import com.flab.comen.global.exception.ErrorMessage;

public class NotExistedTokenException extends RuntimeException {
	public NotExistedTokenException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
	}
}

package com.flab.comen.member.exception;

import com.flab.comen.global.exception.ErrorMessage;

public class NotMatchedInformationException extends RuntimeException {
	public NotMatchedInformationException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
	}
}

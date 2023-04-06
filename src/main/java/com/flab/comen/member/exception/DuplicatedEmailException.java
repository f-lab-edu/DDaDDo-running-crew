package com.flab.comen.member.exception;

import com.flab.comen.global.exception.ErrorMessage;

public class DuplicatedEmailException extends RuntimeException {
	public DuplicatedEmailException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());

	}
}

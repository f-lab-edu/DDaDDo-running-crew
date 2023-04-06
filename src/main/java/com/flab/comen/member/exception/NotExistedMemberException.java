package com.flab.comen.member.exception;

import com.flab.comen.global.exception.ErrorMessage;

public class NotExistedMemberException extends RuntimeException {
	public NotExistedMemberException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
	}
}

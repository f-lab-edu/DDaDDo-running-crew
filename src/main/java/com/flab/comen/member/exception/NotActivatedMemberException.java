package com.flab.comen.member.exception;

import com.flab.comen.global.exception.ErrorMessage;

public class NotActivatedMemberException extends RuntimeException {
	public NotActivatedMemberException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
	}
}

package com.flab.comen.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

	@Email
	@NotBlank(message = "이메일 주소를 입력해 주세요.")
	private String email;

	@NotBlank(message = "비밀번호를 입력해 주세요.")
	private String password;
}

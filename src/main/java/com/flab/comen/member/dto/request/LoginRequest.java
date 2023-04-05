package com.flab.comen.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequest {

	@Email
	@NotBlank(message = "이메일 주소를 입력해 주세요.")
	private String email;

	@NotBlank(message = "비밀번호를 입력해 주세요.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,16}")
	@SuppressWarnings("ant:checkstyle")
	private String password;
}

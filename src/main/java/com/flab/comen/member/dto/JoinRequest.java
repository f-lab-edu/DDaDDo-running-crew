package com.flab.comen.member.dto;

import com.flab.comen.member.domain.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinRequest {

	@NotBlank(message = "이메일 주소를 입력해 주세요.")
	@Email(message = "이메일 형식이 맞지 않습니다.")
	String email;

	@NotBlank(message = "비밀번호를 입력해 주세요.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,16}",
		message = "비밀번호는 8자 이상, 16자 이하로 영문 대소문자/숫자/특수문자가 적어도 1개 이상 포함돼야 합니다.")
	String password;

	@NotBlank(message = "이름을 입력해 주세요.")
	String name;
	@NotNull(message = "역할을 선택해 주세요.")
	Role role;

	public void setPassword(String encryptPassword) {
		this.password = encryptPassword;
	}

	@Builder
	public JoinRequest(String email, String password, String name, Role role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
	}
}

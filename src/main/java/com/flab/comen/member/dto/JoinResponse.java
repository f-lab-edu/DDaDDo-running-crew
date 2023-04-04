package com.flab.comen.member.dto;

import com.flab.comen.member.domain.ActiveType;
import com.flab.comen.member.domain.Role;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinResponse {

	private String email;
	private String name;
	private Role role;
	private ActiveType activeType;

	@Builder
	public JoinResponse(String email, String name, Role role, ActiveType activeType) {
		this.email = email;
		this.name = name;
		this.role = role;
		this.activeType = activeType;
	}
}

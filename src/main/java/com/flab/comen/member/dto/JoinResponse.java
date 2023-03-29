package com.flab.comen.member.dto;

import com.flab.comen.member.domain.ActiveType;
import com.flab.comen.member.domain.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinResponse {

	private String email;
	private String name;
	private Role role;
	private ActiveType activeType;
}

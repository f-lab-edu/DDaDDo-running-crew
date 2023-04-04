package com.flab.comen.member.domain;

import com.flab.comen.global.domain.BaseTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseTime {

	private String email;
	private String password;
	private String name;
	private Role role;
	private ActiveType activeType;

	public static Member of(String email, String password, String name, Role role, ActiveType activeType) {
		Member member = new Member();
		member.email = email;
		member.password = password;
		member.name = name;
		member.role = role;
		member.activeType = activeType;
		return member;
	}

}

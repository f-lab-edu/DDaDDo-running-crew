package com.flab.comen.member.domain;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

	private Long tid;
	private String email;
	private String password;
	private String name;
	private Role role;
	private ActiveType activeType;

	private LocalDateTime createdDt;

	private LocalDateTime updatedDt;

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

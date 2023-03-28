package com.flab.comen.member.service;

import static com.flab.comen.global.exception.ErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.comen.member.dto.JoinRequest;
import com.flab.comen.member.mapper.MemberMapper;

@Service
public class MemberService {

	private final MemberMapper memberMapper;

	private final PasswordEncoder passwordEncoder;

	public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
		this.memberMapper = memberMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public void join(JoinRequest joinRequest) {

		if (isDuplicatedEmail(joinRequest.getEmail())) {
			throw new IllegalArgumentException(DUPLICATED_EMAIL.getMessage());
		}

		String encryptPassword = passwordEncoder.encode(joinRequest.getPassword());
		joinRequest.setPassword(encryptPassword);

		memberMapper.join(joinRequest);
	}

	public boolean isDuplicatedEmail(String email) {
		return memberMapper.existsByEmail(email);
	}
}

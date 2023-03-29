package com.flab.comen.member.service;

import static com.flab.comen.global.exception.ErrorMessage.*;

import java.util.NoSuchElementException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.comen.member.domain.Member;
import com.flab.comen.member.dto.JoinRequest;
import com.flab.comen.member.dto.JoinResponse;
import com.flab.comen.member.mapper.MemberMapper;

@Service
public class MemberService {

	private final MemberMapper memberMapper;

	private final PasswordEncoder passwordEncoder;

	public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
		this.memberMapper = memberMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public JoinResponse join(JoinRequest joinRequest) {

		memberMapper.findByEmail(joinRequest.getEmail()).ifPresent(it -> {
			throw new IllegalArgumentException(DUPLICATED_EMAIL.getMessage());
		});

		String encryptPassword = passwordEncoder.encode(joinRequest.getPassword());
		joinRequest.setPassword(encryptPassword);

		memberMapper.join(joinRequest);

		Member savedMember = getByTid(joinRequest.getTid());

		return JoinResponse.builder()
			.email(savedMember.getEmail())
			.name(savedMember.getName())
			.role(savedMember.getRole())
			.activeType(savedMember.getActiveType()).build();
	}

	public Member getByTid(Long tid) {
		return memberMapper.findByTid(tid).orElseThrow(() -> {
			throw new NoSuchElementException(MEMBER_NOT_FOUND.getMessage());
		});
	}
}

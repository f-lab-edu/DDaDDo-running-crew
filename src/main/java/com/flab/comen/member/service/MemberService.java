package com.flab.comen.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.comen.member.domain.Member;
import com.flab.comen.member.dto.JoinRequest;
import com.flab.comen.member.dto.JoinResponse;
import com.flab.comen.member.exception.DuplicatedEmailException;
import com.flab.comen.member.exception.NotExistedMemberException;
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
			throw new DuplicatedEmailException();
		});

		String encryptPassword = passwordEncoder.encode(joinRequest.getPassword());
		joinRequest.setPassword(encryptPassword);

		memberMapper.join(joinRequest);

		Member savedMember = getByEmail(joinRequest.getEmail());

		return JoinResponse.builder()
			.email(savedMember.getEmail())
			.name(savedMember.getName())
			.role(savedMember.getRole())
			.activeType(savedMember.getActiveType()).build();
	}

	public Member getByEmail(String email) {
		return memberMapper.findByEmail(email).orElseThrow(() -> {
			throw new NotExistedMemberException();
		});
	}
}

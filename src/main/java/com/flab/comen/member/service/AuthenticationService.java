package com.flab.comen.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.comen.global.exception.ErrorMessage;
import com.flab.comen.global.jwt.JwtTokenProvider;
import com.flab.comen.member.domain.ActiveType;
import com.flab.comen.member.domain.Member;
import com.flab.comen.member.dto.request.LoginRequest;
import com.flab.comen.member.dto.response.TokenResponse;
import com.flab.comen.member.exception.NotActivatedMemberException;
import com.flab.comen.member.exception.NotMatchedInformationException;
import com.flab.comen.member.mapper.MemberMapper;

@Service
public class AuthenticationService {

	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	public AuthenticationService(MemberMapper memberMapper, PasswordEncoder passwordEncoder,
		JwtTokenProvider jwtTokenProvider) {
		this.memberMapper = memberMapper;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public TokenResponse login(LoginRequest loginRequest) {
		Member member = memberMapper.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
			new NotMatchedInformationException(ErrorMessage.NOT_MATCHED_LOGIN_INFORMATION));

		if (!isMatchedPassword(loginRequest.getPassword(), member.getPassword())) {
			throw new NotMatchedInformationException(ErrorMessage.NOT_MATCHED_LOGIN_INFORMATION);
		}

		if (!ActiveType.ACTIVE.equals(member.getActiveType())) {
			throw new NotActivatedMemberException(ErrorMessage.NOT_ACTIVATED_MEMBER);
		}

		return new TokenResponse(jwtTokenProvider.generateAccessToken(member.getEmail(), member.getRole()));
	}

	public boolean isMatchedPassword(String password, String existedPassword) {
		return passwordEncoder.matches(password, existedPassword);
	}
}

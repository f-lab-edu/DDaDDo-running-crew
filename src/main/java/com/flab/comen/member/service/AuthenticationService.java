package com.flab.comen.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.comen.global.exception.ErrorMessage;
import com.flab.comen.global.jwt.JwtTokenProvider;
import com.flab.comen.global.jwt.repository.TokenCache;
import com.flab.comen.member.domain.Member;
import com.flab.comen.member.domain.Role;
import com.flab.comen.member.dto.request.LoginRequest;
import com.flab.comen.member.dto.request.ReIssueRequest;
import com.flab.comen.member.dto.response.TokenResponse;
import com.flab.comen.member.exception.NotActivatedMemberException;
import com.flab.comen.member.exception.NotExistedTokenException;
import com.flab.comen.member.exception.NotMatchedInformationException;
import com.flab.comen.member.mapper.MemberMapper;

@Service
public class AuthenticationService {

	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final TokenCache tokenCache;
	private final MemberService memberService;

	public AuthenticationService(MemberMapper memberMapper, PasswordEncoder passwordEncoder,
		JwtTokenProvider jwtTokenProvider, TokenCache tokenCache, MemberService memberService) {
		this.memberMapper = memberMapper;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.tokenCache = tokenCache;
		this.memberService = memberService;
	}

	public TokenResponse login(LoginRequest loginRequest) {
		Member member = memberMapper.findByEmail(loginRequest.email())
			.filter(m -> isMatchedPassword(loginRequest.password(), m.getPassword()))
			.orElseThrow(() -> new NotMatchedInformationException(ErrorMessage.NOT_MATCHED_LOGIN_INFORMATION));

		if (!memberService.isActiveMember(member)) {
			throw new NotActivatedMemberException(ErrorMessage.NOT_ACTIVATED_MEMBER);
		}

		String accessToken = jwtTokenProvider.generateAccessToken(member.getEmail(), member.getRole());
		String refreshToken = jwtTokenProvider.generateRefreshToken();

		tokenCache.saveToken(member.getEmail(), refreshToken);

		return new TokenResponse(accessToken, refreshToken);
	}

	public boolean isMatchedPassword(String password, String existedPassword) {
		return passwordEncoder.matches(password, existedPassword);
	}

	public TokenResponse reissueToken(ReIssueRequest request) {
		String accessToken = request.accessToken();
		String refreshToken = request.refreshToken();

		String memberEmail = jwtTokenProvider.getMemberEmail(accessToken);
		String role = jwtTokenProvider.getRole(accessToken);

		validateRefreshToken(memberEmail);

		accessToken = jwtTokenProvider.generateAccessToken(memberEmail, Role.valueOf(role));

		return new TokenResponse(accessToken, refreshToken);
	}

	public void validateRefreshToken(String email) {
		tokenCache.hasToken(email).orElseThrow(() -> new NotExistedTokenException(ErrorMessage.NOT_EXISTED_TOKEN));
	}
}

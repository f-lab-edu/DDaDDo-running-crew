package com.flab.comen.member.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flab.comen.global.jwt.JwtTokenProvider;
import com.flab.comen.global.jwt.repository.TokenCache;
import com.flab.comen.member.exception.NotExistedTokenException;
import com.flab.comen.member.mapper.MemberMapper;

@SpringBootTest
public class AuthenticationServiceTest {

	@Autowired
	MemberMapper memberMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	TokenCache tokenCache;

	@Autowired
	AuthenticationService authenticationService;

	@Nested
	@DisplayName("로그인")
	class LoginTest {
		@Test
		@DisplayName("패스워드가 일치하면 로그인이 성공한다.")
		void when_passwordMatched_expects_loginToSuccess() {
			String password = "1234Q!wert";
			String encodedPassword = passwordEncoder.encode(password);

			Assertions.assertTrue(
				authenticationService.isMatchedPassword(password, encodedPassword)
			);
		}

		@Test
		@DisplayName("패스워드가 일치하지 않으면 로그인이 실패한다.")
		void when_passwordNotMatched_expects_loginToFail() {
			String password = "1234Q!wert";
			String otherPassword = "password";
			String encodedPassword = passwordEncoder.encode(password);

			Assertions.assertFalse(
				authenticationService.isMatchedPassword(otherPassword, encodedPassword)
			);
		}
	}

	@Nested
	@DisplayName("액세스 토큰 재발급")
	class ReissueTest {
		@Test
		@DisplayName("등록된 리프레시 토큰이 없다면 NotExistedTokenException이 발생한다.")
		void when_tokenIsNotExist_expect_throwsNotExistedTokenException() {
			String email = "comen@comen.com";
			tokenCache.saveToken(email, "refreshToken");

			tokenCache.deleteToken(email);

			Assertions.assertThrows(NotExistedTokenException.class, () -> {
				authenticationService.validateRefreshToken(email);
			});
		}
	}
}

package com.flab.comen.member.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flab.comen.global.jwt.JwtTokenProvider;
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
	AuthenticationService authenticationService;

	@Nested
	@DisplayName("로그인")
	class LoginTest {
		@Test
		@DisplayName("패스워드가 일치하면 로그인이 성공한다.")
		void whenPasswordMatched_expects_loginToSuccess() {
			String password = "1234Q!wert";
			String encodedPassword = passwordEncoder.encode(password);

			Assertions.assertTrue(
				authenticationService.isMatchedPassword(password, encodedPassword)
			);
		}

		@Test
		@DisplayName("패스워드가 일치하지 않으면 로그인이 실패한다.")
		void whenPasswordNotMatched_expects_loginToFail() {
			String password = "1234Q!wert";
			String otherPassword = "password";
			String encodedPassword = passwordEncoder.encode(password);

			Assertions.assertFalse(
				authenticationService.isMatchedPassword(otherPassword, encodedPassword)
			);
		}
	}

}

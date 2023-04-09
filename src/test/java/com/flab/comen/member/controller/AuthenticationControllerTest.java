package com.flab.comen.member.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.comen.member.dto.request.LoginRequest;
import com.flab.comen.member.dto.request.ReIssueRequest;
import com.flab.comen.member.dto.response.TokenResponse;
import com.flab.comen.member.service.AuthenticationService;
import com.flab.comen.member.util.ControllerTest;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest extends ControllerTest {

	@MockBean
	private AuthenticationService authenticationService;

	@Autowired
	private ObjectMapper objectMapper;

	@Nested
	@DisplayName("로그인")
	class LoginTest {
		@Test
		@DisplayName("조건에 맞는 모든 필드를 입력받으면 로그인이 성공한다.")
		void when_allFieldsAreEntered_expects_loginToSuccess() throws Exception {
			LoginRequest loginRequest = new LoginRequest("comen@comen.com", "1234!Comen");
			TokenResponse tokenResponse = new TokenResponse("accessToken", "refreshToken");

			BDDMockito.given(authenticationService.login(BDDMockito.any())).willReturn(tokenResponse);

			ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(loginRequest))
				)
				.andDo(MockMvcResultHandlers.print())
				.andDo(document("auth-login",
					preprocessRequest(modifyUris()
						.port(9090), prettyPrint()),
					requestFields(
						fieldWithPath("email").type(JsonFieldType.STRING).description("등록된 이메일 주소"),
						fieldWithPath("password").type(JsonFieldType.STRING).description("등록된 비밀번호")
					),
					responseFields(
						fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
						fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰")
					)));

			response.andExpect(MockMvcResultMatchers.status().isOk());
		}

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {" "})
		@DisplayName("email 필드가 null, empty, blank 상태라면 로그인이 실패한다.")
		void when_emailFieldIsNullAndEmptyAndBlank_expect_loginToFail(String email) throws Exception {
			LoginRequest loginRequest = new LoginRequest(email, "1234!Comen");

			ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(loginRequest))
				)
				.andDo(MockMvcResultHandlers.print());

			response.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {" "})
		@DisplayName("password 필드가 null, empty, blank 상태라면 로그인이 실패한다.")
		void when_passwordFieldIsNullAndEmptyAndBlank_expect_loginToFail(String password) throws Exception {
			LoginRequest loginRequest = new LoginRequest("comen@comen.com", password);

			ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(loginRequest))
				)
				.andDo(MockMvcResultHandlers.print());

			response.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}
	}

	@Nested
	@DisplayName("액세스 토큰 재발급")
	class ReissueTest {
		@Test
		@DisplayName("조건에 맞는 모든 필드를 입력받으면 재발급이 성공한다.")
		void when_allFieldsAreEntered_expects_reissueToSuccess() throws Exception {
			ReIssueRequest reIssueRequest = new ReIssueRequest("accessToken", "refreshToken");
			TokenResponse tokenResponse = new TokenResponse("newAccessToken", "newRefreshToken");

			BDDMockito.given(authenticationService.reissueToken(BDDMockito.any())).willReturn(tokenResponse);

			ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/reissue")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(reIssueRequest))
				)
				.andDo(MockMvcResultHandlers.print())
				.andDo(document("auth-reissue",
					preprocessRequest(modifyUris()
						.port(9090), prettyPrint()),
					requestFields(
						fieldWithPath("accessToken").type(JsonFieldType.STRING).description("기존에 발급받은 액세스 토큰"),
						fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("기존에 발급받은 리프레시 토큰")
					),
					responseFields(
						fieldWithPath("accessToken").type(JsonFieldType.STRING).description("재발급된 액세스 토큰"),
						fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("기존에 발급받은 리프레시 토큰")
					)));

			response.andExpect(MockMvcResultMatchers.status().isOk());
		}

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {" "})
		@DisplayName("accessToken 필드가 null, empty, blank 상태라면 로그인이 실패한다.")
		void when_accessTokenFieldIsNullAndEmptyAndBlank_expect_loginToFail(String accessToken) throws Exception {
			ReIssueRequest reIssueRequest = new ReIssueRequest(accessToken, "refreshToken");

			ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/reissue")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(reIssueRequest))
				)
				.andDo(MockMvcResultHandlers.print());

			response.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {" "})
		@DisplayName("refreshToken 필드가 null, empty, blank 상태라면 로그인이 실패한다.")
		void when_refreshTokenFieldIsNullAndEmptyAndBlank_expect_loginToFail(String refreshToken) throws Exception {
			ReIssueRequest reIssueRequest = new ReIssueRequest("accessToken", refreshToken);

			ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/reissue")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(reIssueRequest))
				)
				.andDo(MockMvcResultHandlers.print());

			response.andExpect(MockMvcResultMatchers.status().isBadRequest());
		}
	}
}

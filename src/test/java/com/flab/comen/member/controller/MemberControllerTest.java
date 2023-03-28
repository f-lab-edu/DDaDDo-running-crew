package com.flab.comen.member.controller;

import static com.flab.comen.member.domain.Role.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.comen.member.domain.Role;
import com.flab.comen.member.dto.JoinRequest;
import com.flab.comen.member.service.MemberService;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureMybatis
@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberService memberService;

	@Autowired
	private ObjectMapper objectMapper;

	public static final String EMAIL = "comen@comen.com";
	public static final String PASSWORD = "1234!Comen";
	public static final String NAME = "김코멘";
	public static final Role ROLE = MENTEE;

	@Nested
	@DisplayName("회원가입")
	class joinTest {
		@Test
		@DisplayName("조건에 맞는 모든 필드를 입력받으면 회원가입이 성공한다.")
		void when_allFieldsAreEntered_expect_joinToSuccess() throws Exception {
			JoinRequest joinRequest = new JoinRequest(EMAIL, PASSWORD, NAME, ROLE);

			ResultActions response = mockMvc.perform(post("/api/v1/members/join")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(joinRequest))
				)
				.andDo(print());

			response.andExpect(status().isCreated());
		}

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {" "})
		@DisplayName("email 필드가 null, empty, blank 상태라면 회원가입이 실패한다.")
		void when_emailFieldIsNullAndEmptyAndBlank_expect_joinToFail(String email) throws Exception {
			JoinRequest joinRequest = new JoinRequest(email, PASSWORD, NAME, ROLE);

			ResultActions response = mockMvc.perform(post("/api/v1/members/join")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(joinRequest))
				)
				.andDo(print());

			response.andExpect(status().isBadRequest());

		}

		@Test
		@DisplayName("email 필드가 형식에 맞지 않다면 회원가입이 실패한다.")
		void when_emailFieldIsInvalid_expect_joinToFail() throws Exception {
			JoinRequest joinRequest = new JoinRequest("comen.com", PASSWORD, NAME, ROLE);

			ResultActions response = mockMvc.perform(post("/api/v1/members/join")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(joinRequest))
				)
				.andDo(print());

			response.andExpect(status().isBadRequest());

		}

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {" "})
		@DisplayName("password 필드가 null, empty, blank 상태라면 회원가입이 실패한다.")
		void when_passwordFieldIsNullAndEmptyAndBlank_expect_joinToFail(String password) throws Exception {
			JoinRequest joinRequest = new JoinRequest(EMAIL, password, NAME, ROLE);

			ResultActions response = mockMvc.perform(post("/api/v1/members/join")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(joinRequest))
				)
				.andDo(print());

			response.andExpect(status().isBadRequest());

		}

		@ParameterizedTest
		@ValueSource(strings = {"1234!Co", "12345678!Comentest", "1234!COMEN", "1234!comen", "1234"})
		@DisplayName("password 필드가 8~16자 이상, 특수문자와 영문 대소문자 조합이 아니면 회원가입이 실패한다.")
		void when_passwordFieldIsInvalid_expect_joinToFail(String password) throws Exception {
			JoinRequest joinRequest = new JoinRequest(EMAIL, password, NAME, ROLE);

			ResultActions response = mockMvc.perform(post("/api/v1/members/join")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(joinRequest))
				)
				.andDo(print());

			response.andExpect(status().isBadRequest());

		}

		@ParameterizedTest
		@NullAndEmptySource
		@ValueSource(strings = {" "})
		@DisplayName("name 필드가 null, empty, blank 상태라면 회원가입이 실패한다.")
		void when_nameFieldIsInvalid_expect_joinToFail(String name) throws Exception {
			JoinRequest joinRequest = new JoinRequest(EMAIL, PASSWORD, name, ROLE);

			ResultActions response = mockMvc.perform(post("/api/v1/members/join")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(joinRequest))
				)
				.andDo(print());

			response.andExpect(status().isBadRequest());

		}

		@ParameterizedTest
		@NullSource
		@DisplayName("name 필드가 null 상태라면 회원가입이 실패한다.")
		void when_roleFieldIsNull_expect_joinToFail(Role role) throws Exception {
			JoinRequest joinRequest = new JoinRequest(EMAIL, PASSWORD, NAME, role);

			ResultActions response = mockMvc.perform(post("/api/v1/members/join")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(joinRequest))
				)
				.andDo(print());

			response.andExpect(status().isBadRequest());

		}
	}
}

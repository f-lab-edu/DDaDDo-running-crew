package com.flab.comen.member.controller;

import static com.flab.comen.member.domain.Role.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.comen.member.domain.ActiveType;
import com.flab.comen.member.domain.Role;
import com.flab.comen.member.dto.request.JoinRequest;
import com.flab.comen.member.dto.response.JoinResponse;
import com.flab.comen.member.service.MemberService;
import com.flab.comen.member.util.ControllerTest;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends ControllerTest {

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
	class JoinTest {
		@Test
		@DisplayName("조건에 맞는 모든 필드를 입력받으면 회원가입이 성공한다.")
		void when_allFieldsAreEntered_expect_joinToSuccess() throws Exception {
			JoinRequest joinRequest = new JoinRequest(EMAIL, PASSWORD, NAME, ROLE);
			JoinResponse joinResponse = new JoinResponse(EMAIL, NAME, ROLE, ActiveType.ACTIVE);

			given(memberService.join(any())).willReturn(joinResponse);

			ResultActions response = mockMvc.perform(post("/api/v1/members/join")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(joinRequest))
				)
				.andDo(print())
				.andDo(document("member-join",
					preprocessRequest(modifyUris()
						.port(9090), prettyPrint()),
					preprocessResponse(prettyPrint()),
					requestFields(
						fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("password").type(JsonFieldType.STRING)
							.description("비밀번호(8자~16자 이하의 영문 대소문자/숫자/특수문자 조합)"),
						fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("role").type(JsonFieldType.STRING).description("에프랩 내에서의 역할(COACH|MENTEE)")
					),
					responseFields(
						fieldWithPath("email").type(JsonFieldType.STRING).description("가입된 이메일 정보"),
						fieldWithPath("name").type(JsonFieldType.STRING).description("가입된 이름 정보"),
						fieldWithPath("role").type(JsonFieldType.STRING).description("가입된 역할 정보"),
						fieldWithPath("activeType").type(JsonFieldType.STRING).description("가입된 계정 활성화 정보")
					)));

			response.andExpect(status().isOk());
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

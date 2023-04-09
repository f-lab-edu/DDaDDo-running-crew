package com.flab.comen.member.service;

import static com.flab.comen.member.domain.ActiveType.*;
import static com.flab.comen.member.domain.Role.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.comen.member.domain.ActiveType;
import com.flab.comen.member.domain.Member;
import com.flab.comen.member.domain.Role;
import com.flab.comen.member.dto.request.JoinRequest;
import com.flab.comen.member.exception.DuplicatedEmailException;
import com.flab.comen.member.exception.NotExistedMemberException;
import com.flab.comen.member.mapper.MemberMapper;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	MemberMapper memberMapper;

	@InjectMocks
	MemberService memberService;

	@Nested
	@DisplayName("회원가입")
	class JoinTest {
		@Test
		@DisplayName("email이 중복되었다면 DuplicatedEmailException이 발생한다.")
		void when_emailIsDuplicated_expect_throwsDuplicatedEmailException() {
			Member member = Member.of("email", "password", "name", MENTEE, ACTIVE);
			JoinRequest joinRequest = new JoinRequest("email", "password", "name", MENTEE);

			given(memberMapper.findByEmail(anyString())).willReturn(Optional.of(member));

			assertThrows(DuplicatedEmailException.class, () -> {
				memberService.join(joinRequest);
			});
		}

		@Test
		@DisplayName("등록된 회원 정보가 없다면 NotExistedMemberException이 발생한다.")
		void when_memberDoesNotExist_expect_throwsNotExistedMemberException() {
			String email = "comen@comen.com";

			given(memberMapper.findByEmail(anyString())).willThrow(NotExistedMemberException.class);

			assertThrows(NotExistedMemberException.class, () -> {
				memberService.getByEmail(email);
			});
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"INACTIVE", "DELETE"})
	@DisplayName("회원이 활성화 상태가 아니면 false를 반환한다.")
	void when_memberINotActive_expects_returnFalse(String activeType) {
		Member member = Member.of("comen@comen.com", "1234Q!wert", "김코멘", Role.MENTEE,
			ActiveType.valueOf(activeType));

		Assertions.assertFalse(
			memberService.isActiveMember(member)
		);
	}
}

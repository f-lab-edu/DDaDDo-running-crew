package com.flab.comen.member.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.comen.member.mapper.MemberMapper;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	MemberMapper memberMapper;

	@InjectMocks
	MemberService memberService;

	@Test
	void when_emailIsDuplicated_expect_throwsIllegalArgumentException() {
		String existedEmail = "comen@comen.com";

		willThrow(IllegalArgumentException.class).given(memberMapper).existsByEmail(anyString());

		assertThrows(IllegalArgumentException.class, () -> {
			memberService.isDuplicatedEmail(existedEmail);
		});
	}

}

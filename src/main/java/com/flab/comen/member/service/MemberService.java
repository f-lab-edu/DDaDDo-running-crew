package com.flab.comen.member.service;

import org.springframework.stereotype.Service;

import com.flab.comen.member.mapper.MemberMapper;

@Service
public class MemberService {

	private final MemberMapper memberMapper;

	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
}

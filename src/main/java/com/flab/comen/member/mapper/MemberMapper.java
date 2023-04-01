package com.flab.comen.member.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.flab.comen.member.domain.Member;
import com.flab.comen.member.dto.JoinRequest;

@Mapper
public interface MemberMapper {
	Optional<Member> findByEmail(String email);

	Optional<Member> findByTid(Long tid);

	void join(JoinRequest dto);
}

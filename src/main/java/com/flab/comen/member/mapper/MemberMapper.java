package com.flab.comen.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.flab.comen.member.dto.JoinRequest;

@Mapper
public interface MemberMapper {

	boolean existsByEmail(String email);

	void join(JoinRequest dto);
}

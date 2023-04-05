package com.flab.comen.global.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flab.comen.member.domain.Member;
import com.flab.comen.member.exception.NotExistedMemberException;
import com.flab.comen.member.mapper.MemberMapper;

@Service
public class PrincipalDetailsService implements UserDetailsService {

	private final MemberMapper memberMapper;

	public PrincipalDetailsService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberMapper.findByEmail(email).orElseThrow(() ->
			new NotExistedMemberException());

		return new PrincipalDetails(member);
	}
}

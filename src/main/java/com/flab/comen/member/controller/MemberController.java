package com.flab.comen.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.comen.member.dto.request.JoinRequest;
import com.flab.comen.member.dto.request.LoginRequest;
import com.flab.comen.member.dto.response.JoinResponse;
import com.flab.comen.member.dto.response.TokenResponse;
import com.flab.comen.member.service.AuthenticationService;
import com.flab.comen.member.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

	private final MemberService memberService;

	private final AuthenticationService authenticationService;

	public MemberController(MemberService memberService, AuthenticationService authenticationService) {
		this.memberService = memberService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/join")
	public ResponseEntity<JoinResponse> join(@RequestBody @Valid JoinRequest dto) {
		return ResponseEntity.ok(memberService.join(dto));
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest dto) {
		return ResponseEntity.ok(authenticationService.login(dto));
	}
}

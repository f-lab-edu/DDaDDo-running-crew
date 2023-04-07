package com.flab.comen.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.comen.member.dto.request.LoginRequest;
import com.flab.comen.member.dto.request.ReIssueRequest;
import com.flab.comen.member.dto.response.TokenResponse;
import com.flab.comen.member.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest dto) {
		return ResponseEntity.ok(authenticationService.login(dto));
	}

	@PostMapping("/reissue")
	public ResponseEntity<TokenResponse> reissueToken(@RequestBody @Valid ReIssueRequest dto) {
		return ResponseEntity.ok(authenticationService.reissueToken(dto));
	}
}

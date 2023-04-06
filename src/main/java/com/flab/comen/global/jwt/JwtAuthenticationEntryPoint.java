package com.flab.comen.global.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.comen.global.exception.ErrorMessage;
import com.flab.comen.global.exception.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {
		log.error("JWT - returns 401 unauthorized. Message : {}", authException.getMessage());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		objectMapper.writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(),
			ErrorResponse.builder()
				.status(ErrorMessage.UNAUTHORIZED_TOKEN.getStatus().toString())
				.message(ErrorMessage.UNAUTHORIZED_TOKEN.getMessage())
				.build());
	}
}

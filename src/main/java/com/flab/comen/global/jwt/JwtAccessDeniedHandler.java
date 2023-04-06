package com.flab.comen.global.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.comen.global.exception.ErrorMessage;
import com.flab.comen.global.exception.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	public JwtAccessDeniedHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		log.error("JWT - returns 403 forbidden. Message : {}", accessDeniedException.getMessage());

		objectMapper.writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(),
			ErrorResponse.builder()
				.status(ErrorMessage.FORBIDDEN_TOKEN.getStatus().toString())
				.message(ErrorMessage.FORBIDDEN_TOKEN.getMessage())
				.build());
	}
}

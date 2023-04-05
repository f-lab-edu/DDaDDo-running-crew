package com.flab.comen.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.flab.comen.global.jwt.JwtAccessDeniedHandler;
import com.flab.comen.global.jwt.JwtAuthenticationEntryPoint;
import com.flab.comen.global.jwt.JwtAuthenticationFilter;
import com.flab.comen.global.jwt.JwtTokenProvider;
import com.flab.comen.member.domain.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	private final JwtTokenProvider jwtTokenProvider;

	public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
		JwtAccessDeniedHandler jwtAccessDeniedHandler, JwtTokenProvider jwtTokenProvider) {
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeHttpRequests(authorize ->
				authorize
					.requestMatchers("/api/v1/members/join", "/api/v1/members/login").permitAll()
					.requestMatchers("/api/v1/mentees/**").hasAuthority(String.valueOf(Role.MENTEE))
					.requestMatchers("/api/v1/coaches/**").hasAuthority(String.valueOf(Role.COACH))
					.requestMatchers("/api/v1/members/test").hasAuthority(String.valueOf(Role.COACH))
					.anyRequest().authenticated())
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

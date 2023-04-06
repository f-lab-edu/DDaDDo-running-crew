package com.flab.comen.global.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.flab.comen.global.auth.PrincipalDetailsService;
import com.flab.comen.member.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	private String secretKey;
	private long tokenValidityInMilliseconds;
	private final PrincipalDetailsService principalDetailsService;

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORIZATION_TYPE = "Bearer";
	private static final String ROLE = "role";

	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds,
		PrincipalDetailsService principalDetailsService) {
		this.secretKey = secretKey;
		this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
		this.principalDetailsService = principalDetailsService;
	}

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String generateAccessToken(String email, Role role) {
		Claims claims = Jwts.claims().setSubject(email);
		claims.put(ROLE, role);

		Date now = new Date();
		Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = principalDetailsService.loadUserByUsername(getMemberEmail(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getMemberEmail(String token) {
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody().getSubject();
	}

	public Optional<String> resolveToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
			.filter(StringUtils::hasText)
			.filter(header -> header.startsWith(AUTHORIZATION_TYPE))
			.map(header -> header.substring(7));
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (RuntimeException exception) {
			log.error("Invalid token : {} ", token, exception);
		}
		return false;
	}
}

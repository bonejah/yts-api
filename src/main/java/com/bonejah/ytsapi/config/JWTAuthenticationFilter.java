package com.bonejah.ytsapi.config;

import static com.bonejah.ytsapi.config.SecurityConstants.EXPIRATION_TIME;
import static com.bonejah.ytsapi.config.SecurityConstants.HEADER_STRING;
import static com.bonejah.ytsapi.config.SecurityConstants.SECRET;
import static com.bonejah.ytsapi.config.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bonejah.ytsapi.model.YTSUser;
import com.bonejah.ytsapi.model.Token;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 *
 * brunolima on Mar 15, 2021
 * 
 */

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		YTSUser applicationUser;
		try {
			applicationUser = new ObjectMapper().readValue(request.getInputStream(), YTSUser.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					applicationUser.getUsername(), applicationUser.getPassword()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME, ChronoUnit.MILLIS);

		String token = Jwts.builder()
						.setSubject(((User) authResult.getPrincipal()).getUsername())
						.setExpiration(Date.from(expirationTimeUTC.toInstant()))
						.signWith(SignatureAlgorithm.HS256, SECRET)
						.compact();
		
		String accesTokenString = new ObjectMapper().writeValueAsString(new Token(token));
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(accesTokenString);
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}

}

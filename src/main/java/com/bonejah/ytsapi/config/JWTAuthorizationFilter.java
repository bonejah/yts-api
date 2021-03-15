package com.bonejah.ytsapi.config;

import static com.bonejah.ytsapi.config.SecurityConstants.HEADER_STRING;
import static com.bonejah.ytsapi.config.SecurityConstants.SECRET;
import static com.bonejah.ytsapi.config.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bonejah.ytsapi.service.YTSUserDetailService;

import io.jsonwebtoken.Jwts;

/**
 *
 * brunolima on Mar 15, 2021
 * 
 */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final YTSUserDetailService ytsUserDetailService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
			YTSUserDetailService ytsUserDetailService) {
		super(authenticationManager);
		this.ytsUserDetailService = ytsUserDetailService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(HEADER_STRING);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken usernamePassword = getAuthenticationToken(request);

		SecurityContextHolder.getContext().setAuthentication(usernamePassword);

		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);

		if (token == null)
			return null;

		String username = Jwts.parser()
								.setSigningKey(SECRET)
								.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
								.getBody()
								.getSubject();

		UserDetails userDetails = ytsUserDetailService.loadUserByUsername(username);

		return username != null
				? new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
				: null;
	}

}

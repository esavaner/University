package com.example.calendarserver.security;

import com.auth0.jwt.JWT;
import com.example.calendarserver.user.User;
import com.example.calendarserver.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	private UserRepository repository;

	public JWTAuthenticationFilter(UserRepository repository, AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.repository = repository;
		setFilterProcessesUrl(SecurityConstants.SIGN_IN_URL);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getLogin(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		org.springframework.security.core.userdetails.User secUser = (org.springframework.security.core.userdetails.User) auth
				.getPrincipal();
		User user = repository.findByLogin(secUser.getUsername()).get();
		String token = JWT.create().withSubject(secUser.getUsername()).withClaim("admin", user.isAdmin())
				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION_TIME))
				.sign(HMAC512(SecurityConstants.SECRET.getBytes()));
		res.addHeader(SecurityConstants.AUTHORIZATION_HEADER, SecurityConstants.TOKEN_PREFIX + token);
		res.addHeader(SecurityConstants.USER_ID_HEADER, Long.toString(user.getId()));
	}
}

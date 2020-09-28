package com.project.quizo.web.Security.Filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.quizo.exception.BadLoginRequestException;
import com.project.quizo.resource.UserLoginPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UserLoginPostDTO postDTO = new UserLoginPostDTO();
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (requiresAuthentication(request, response)) {
			try {
				postDTO = objectMapper.readValue(req.getInputStream(), UserLoginPostDTO.class);

			} catch (Exception ex) {
				authenticationEntryPoint.commence(request, response, new BadLoginRequestException());
				return;
			}
		}

		super.doFilter(req, res, chain);

	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		return postDTO.getUsername();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		return postDTO.getPassword();
	}

}
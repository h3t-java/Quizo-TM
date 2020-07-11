package com.project.quizo.Web.Security.Handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.quizo.Resource.LoginUser;
import com.project.quizo.Resource.LoginUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		LoginUserDTO loggedInUser = new LoginUserDTO(LoginUser.class.cast(authentication.getPrincipal()).getId());
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().append(objectMapper.writeValueAsString(loggedInUser));
	}

}
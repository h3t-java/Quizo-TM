package com.project.quizo.web.Security.Handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.quizo.exception.StatusAuthenticationException;
import com.project.quizo.web.Rest.ExceptionHandling.CustomErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ObjectMapper objectMapper;

	private CustomErrorResponse errors = new CustomErrorResponse();

	private HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {

		if (authException instanceof StatusAuthenticationException) {
			httpStatus = ((StatusAuthenticationException) authException).getStatus();
		}

		setResponseProperties(response);
		buildEroorMessage(authException);
		buildResponseBody(response);
		resetStaus();
	}

	private void buildEroorMessage(AuthenticationException authException) {
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(authException.getMessage());
	}

	private void setResponseProperties(HttpServletResponse response) throws IOException {
		response.setStatus(httpStatus.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.sendRedirect("/access-denied");
	}

	private void buildResponseBody(HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		String jsonString = objectMapper.writeValueAsString(errors);

		out.write(jsonString);
		out.flush();
	}

	private void resetStaus() {
		httpStatus = HttpStatus.UNAUTHORIZED;
	}
}

package com.project.quizo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public abstract class StatusAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 8853725788888643636L;
	
	private final HttpStatus httpStatus;
	
	public StatusAuthenticationException(String msg, HttpStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}
	
	public HttpStatus getStatus() {
		return httpStatus;
	} 

}
package com.project.quizo.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedAuthException extends StatusAuthenticationException {

	private static final long serialVersionUID = -4404548646300418108L;

	private static final String ERROR_MESSAGE = "You do not have permission to access this resource";
	private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

	public AccessDeniedAuthException() {
		super(ERROR_MESSAGE, HTTP_STATUS);
	}
	
}
package com.project.quizo.exception;

import org.springframework.http.HttpStatus;

public class BadLoginRequestException extends StatusAuthenticationException {

	private static final long serialVersionUID = -6105808466398508439L;

	private static final String ERROR_MESSAGE = "Bad request";
	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

	public BadLoginRequestException() {
		super(ERROR_MESSAGE, HTTP_STATUS);
	}

}
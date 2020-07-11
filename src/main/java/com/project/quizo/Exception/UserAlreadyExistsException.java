package com.project.quizo.Exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends CustomException {

	private static final long serialVersionUID = -4494535939670502476L;
	
	private static final String ERROR_MESSAGE = "User already exists.";
	private static final HttpStatus STATUS = HttpStatus.CONFLICT;

	public UserAlreadyExistsException(String error) {
		super(ERROR_MESSAGE, STATUS, error);
	}

}
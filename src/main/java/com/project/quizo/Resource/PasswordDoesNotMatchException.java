package com.project.quizo.Resource;

import com.project.quizo.Exception.CustomException;
import org.springframework.http.HttpStatus;

public class PasswordDoesNotMatchException extends CustomException {

	private static final long serialVersionUID = 3432455844362797716L;
	
	private static final String ERROR_MESSAGE = "Password does not match.";
	private static final HttpStatus STATUS = HttpStatus.FORBIDDEN;
	
	public PasswordDoesNotMatchException() {
		super(ERROR_MESSAGE, STATUS);
	}

}
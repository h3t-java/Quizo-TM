package com.project.quizo.resource;

import com.project.quizo.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException {

	private static final long serialVersionUID = -2458589474391949532L;
	
	private static final String ERROR_MESSAGE = "Resource not found.";
	private static final String NOT_FOUND_FOR_ID_FORMAT = "Resource not found for id: %s.";
	private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

	public ResourceNotFoundException(Long id) {
		this(String.format(NOT_FOUND_FOR_ID_FORMAT, id.toString()));
	}

	public ResourceNotFoundException(String message) {
		super(ERROR_MESSAGE, STATUS, message);
	}
	
}
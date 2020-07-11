package com.project.quizo.Exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

	private static final long serialVersionUID = 4128706044863469915L;

	private final List<String> errors = new ArrayList<>();
	private final HttpStatus status;

	public CustomException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public CustomException(String message, HttpStatus status, String error) {
		this(message, status);
		errors.add(error);
	}

	public CustomException(String message, HttpStatus status, List<String> errors) {
		this(message, status);
		this.errors.addAll(errors);
	}

	public List<String> getErrors() {
		return errors;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
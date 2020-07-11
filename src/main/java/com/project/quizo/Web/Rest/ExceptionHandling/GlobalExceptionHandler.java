package com.project.quizo.Web.Rest.ExceptionHandling;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.project.quizo.Exception.CustomException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String ERROR_MESSAGE_INVALID_INPUT = "Invalid input.";
	private static final String ERROR_MESSAGE_ILLEGAL_ARGUMENT = "Illegal argument.";
	private static final String ERROR_MESSAGE_MESSAGE_NOT_READABLE = "Request not readable.";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> details = ex.getBindingResult().getFieldErrors().stream()
				.map(e -> e.getField() + " - " + e.getDefaultMessage()).collect(Collectors.toList());

		return ResponseEntity.badRequest().body(new CustomErrorResponse(ERROR_MESSAGE_INVALID_INPUT, details));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex,
			HttpServletResponse response) {

		List<String> details = ex.getConstraintViolations().stream()
				.map(e -> ((PathImpl) e.getPropertyPath()).getLeafNode().getName() + " - " + e.getMessage())
				.collect(Collectors.toList());

		return ResponseEntity.badRequest().body(new CustomErrorResponse(ERROR_MESSAGE_INVALID_INPUT, details));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<CustomErrorResponse> handleIllegalArgumentException(Exception ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.body(new CustomErrorResponse(ERROR_MESSAGE_ILLEGAL_ARGUMENT, ex.getMessage()));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new CustomErrorResponse(ERROR_MESSAGE_MESSAGE_NOT_READABLE, ex.getMessage()));
	}

	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<CustomErrorResponse> handlePropertyReferenceException(PropertyReferenceException ex,
			WebRequest request) {

		String message = String.format("No property found with name : %s !", ex.getPropertyName());
		return ResponseEntity.badRequest().body(new CustomErrorResponse(ERROR_MESSAGE_ILLEGAL_ARGUMENT, message));
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
		return ResponseEntity.status(ex.getStatus()).body(new CustomErrorResponse(ex.getMessage(), ex.getErrors()));
	}

}
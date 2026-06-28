package com.herrywang.apibanking.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiBankingExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<List<String>> handleValidationException(final ValidationException ex) {
		return ResponseEntity.badRequest().body(ex.getErrorMsg());
	}


}
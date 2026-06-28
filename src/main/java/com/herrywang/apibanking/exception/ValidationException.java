package com.herrywang.apibanking.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

	private final List<String> errors;

	public ValidationException(final List<String> errors) {
		this.errors = errors;
	}

	public ValidationException(final String message) {
		this.errors = new ArrayList<>();
		this.errors.add(message);
	}
}

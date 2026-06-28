package com.herrywang.apibanking.exception;

import java.util.List;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

	private final List<String> errorMsg;

	public ValidationException(final List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}
}

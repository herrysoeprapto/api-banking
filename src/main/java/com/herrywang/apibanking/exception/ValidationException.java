package com.herrywang.apibanking.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

	private final List<String> errorMsg;

	public ValidationException(final List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	public ValidationException(final String message) {
		this.errorMsg = new ArrayList<>();
		this.errorMsg.add(message);
	}
}

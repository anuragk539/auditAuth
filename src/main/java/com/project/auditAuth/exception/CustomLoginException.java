package com.project.auditAuth.exception;

public class CustomLoginException extends Exception {
	
	public CustomLoginException(String message) {
		super(message);
	}

	public CustomLoginException(Throwable cause) {
		super(cause);
	}

	public CustomLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomLoginException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

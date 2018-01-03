package com.devyok.web.exception;

public class HybridMessageRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HybridMessageRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public HybridMessageRuntimeException(String message) {
		super(message);
	}

	
	
}

package com.devyok.web.common;
public class HandlerException extends Exception {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public HandlerException(Exception e) {
		super(e);
	}

	public HandlerException(String message, Throwable cause) {
		super(message, cause);
	}

}

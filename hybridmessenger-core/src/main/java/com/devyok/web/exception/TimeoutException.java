package com.devyok.web.exception;
/**
 * @author wei.deng
 */
public class TimeoutException extends HybridMessageException {

	public TimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

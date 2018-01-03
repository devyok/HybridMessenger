package com.devyok.web.exception;
/**
 * @author wei.deng
 */
public class SendException extends HybridMessageException {

	public SendException(String message, Throwable cause) {
		super(message, cause);
	}

	public SendException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

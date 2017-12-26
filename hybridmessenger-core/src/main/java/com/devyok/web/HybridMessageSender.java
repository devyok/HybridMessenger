package com.devyok.web;

import com.devyok.web.exception.SendException;
import com.devyok.web.exception.TimeoutException;
import com.devyok.web.exception.HybridMessageException;

/**
 * 
 * @author wei.deng
 *
 */
public interface HybridMessageSender {

	/**
	 * 将webmessage发送到指定端({@link HybridMessage#WebMessageType#H5} , {@link HybridMessage#WebMessageType#NATIVE})
	 * @param webMessage
	 * @return
	 */
	public boolean send(HybridMessage webMessage)throws SendException,TimeoutException,HybridMessageException;
	
}

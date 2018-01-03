package com.devyok.web;

import com.devyok.web.exception.SendException;
import com.devyok.web.exception.TimeoutException;
import com.devyok.web.exception.HybridMessageException;
import com.devyok.web.exception.HybridMessageRuntimeException;

import java.util.HashMap;

public final class HybridMessageSenderFactory {

	private static final HashMap<HybridMessageType,HybridMessageSender> senders = new HashMap<HybridMessageType, HybridMessageSender>();

	static {
		create(HybridMessageType.UNKONW,new HybridMessageEmptyImpl());
		create(HybridMessageType.H5,new HybridMessageSenderNativeImpl());
		create(HybridMessageType.NATIVE,new HybridMessageSenderH5Impl());
	}

	static HybridMessageSender getSender(HybridMessageType messageType){

		if(messageType == null){
			throw new HybridMessageRuntimeException("messageType object must be not null");
		}

		HybridMessageSender messageSender = senders.get(messageType);
		if(messageSender == null){
			throw new HybridMessageRuntimeException("can't found sender by messagetype("+messageType+")");
		}
		return messageSender;
	}

	public static HybridMessageSender create(HybridMessageType messageType, final HybridMessageSender messageSender){
		HybridMessageSender proxy = new HybridMessageSenderImplProxy(messageSender);
		senders.put(messageType, messageSender);
		return proxy;
	}

	static class HybridMessageSenderImplProxy implements HybridMessageSender {

		private HybridMessageSender target;

		public HybridMessageSenderImplProxy(HybridMessageSender webMessageSender) {
			this.target = webMessageSender;
		}

		@Override
		public boolean send(HybridMessage webMessage) throws SendException, TimeoutException, HybridMessageException {

			//intercept all sendimpl

			if(target!=null){
				return target.send(webMessage);
			}

			return false;
		}

	}

	static class HybridMessageEmptyImpl implements HybridMessageSender {

		@Override
		public boolean send(HybridMessage webMessage) throws SendException, TimeoutException, HybridMessageException {
			throw new SendException("empty impl，please check web message type");
		}

	}

	/**
	 * 接收到H5发来的消息之后，将消息发送到Native端
	 * @author wei.deng
	 *
	 */
	static class HybridMessageSenderNativeImpl implements HybridMessageSender {

		@Override
		public boolean send(HybridMessage webMessage) throws SendException, TimeoutException, HybridMessageException {
			return HybridMessengerImpl.enqueue(webMessage);
		}

	}

	/**
	 * 将消息发送到H5端
	 * @author wei.deng
	 */
	static class HybridMessageSenderH5Impl extends HybridMessageSenderTemplete {

		private static final String COMMAND = "javascript:window.HybridMessenger.onReceiveHybridMessage('%s');";

		@Override
		public String buildScriptCommand(final HybridMessage webMessage) {
			return String.format(COMMAND, String.valueOf(webMessage.toJson()));
		}
	}

	/**
	 * 当接收到h5端发来的消息时，{@link HybridMessageH5Replier}主要负责应答发送者
	 * @author wei.deng
	 */
	static class HybridMessageH5Replier extends HybridMessageSenderH5Impl {
	}

}

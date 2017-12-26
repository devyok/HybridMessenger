package com.devyok.web;


/**
 * @author wei.deng
 */
public abstract class HybridMessenger implements HybridMessageReceiverManager, HybridMessageSender {

	public interface WebMessageInterceptor {
		public boolean intercept(HybridMessage webMessage);
	}

	public HybridMessenger(){
	}

	public static final HybridMessenger getMessenger(){
		return HybridMessengerImpl.instance;
	}

	public abstract void setWebMessageInterceptor(WebMessageInterceptor interceptor);

	/**
	 * 配置WebMessenger,可影响WebMessenger运行时
	 * @param configuration
	 */
	public abstract void config(Configuration configuration);

	public abstract boolean startReceiveWebMessage(WebClient webClient);



}

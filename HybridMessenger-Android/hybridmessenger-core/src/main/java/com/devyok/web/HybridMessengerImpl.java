package com.devyok.web;

import com.devyok.web.common.HandlerException;
import com.devyok.web.common.HandlerFilter;
import com.devyok.web.common.HandlerManager;
import com.devyok.web.exception.SendException;
import com.devyok.web.exception.TimeoutException;
import com.devyok.web.exception.HybridMessageException;
import com.devyok.web.exception.HybridMessageRuntimeException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wei.deng
 */
final class HybridMessengerImpl extends HybridMessenger {
	/**
	 * 存储所有uri对应的消息的处理器(用于接收)
	 * map.key = uri
	 * map.value：对应uri模块的处理器
	 */
	final ConcurrentHashMap<String, HandlerManager<HybridMessageReceiver>> webMessageReceiverMapping = new ConcurrentHashMap<String, HandlerManager<HybridMessageReceiver>>();
	/**
	 * 存储所有native端发送的消息
	 * map.key = {@link HybridMessage#getId()}
	 * map.value：对应id的消息
	 */
	final ConcurrentHashMap<String, HybridMessage> sendContainer = new ConcurrentHashMap<String, HybridMessage>();

	/**
	 * 所有接收到的消息都将进入这个线程队列
	 */
	private HybridMessageHandlerThread thread = new HybridMessageHandlerThreadImpl();
	private Driver driver;

	private WebMessageInterceptor mWebMessageInterceptor;

	static HybridMessengerImpl instance = new HybridMessengerImpl();

	private HybridMessengerImpl(){
	}

	private void check(HybridMessageReceiver receiver) {
		if(receiver == null){
			throw new HybridMessageRuntimeException("receiver object must be not null");
		}

		WebUri uri = receiver.getFilter().getWebUri();

		if(uri == null){
			throw new HybridMessageRuntimeException("receiver.filter.uri object must be not null");
		}
	}

	@Override
	public boolean registerHybridMessageReceiver(HybridMessageReceiver receiver) {

		check(receiver);

		HmLogger.info("WebContextImpl", "[HybridMessage native] registerHybridMessageReceiver enter");

		WebUri webUri = receiver.getFilter().getWebUri();

		HandlerManager<HybridMessageReceiver> handlerManager = getHandlerManager(webUri);

		if(handlerManager == null){
			String uriString = WebUri.WebUriHelper.getQueryParamsPrefix(webUri);
			handlerManager = new HandlerManager<HybridMessageReceiver>(new HybridMessagePriorityComparator<HybridMessageReceiver>());
			webMessageReceiverMapping.put(uriString, handlerManager);
		}
		handlerManager.add(receiver);
		HmLogger.info("WebContextImpl", "[HybridMessage native] registerHybridMessageReceiver handlerManager size = " + handlerManager.count());
		return true;
	}

	@Override
	public boolean unregisterHybridMessageReceiver(HybridMessageReceiver receiver) {

		check(receiver);
		HmLogger.info("WebContextImpl", "[HybridMessage native] unregisterHybridMessageReceiver enter");
		HandlerManager<HybridMessageReceiver> handlerManager = getHandlerManager(receiver.getFilter().getWebUri());

		if(handlerManager!=null){
			handlerManager.delete(receiver);
			return true;
		}
		return false;
	}

	static boolean dispatchOnReceive(HybridMessage webMessage) throws HandlerException{

		HmLogger.info("WebContextImpl", "[HybridMessage native] dispatchOnReceive enter");

		if(instance.onInterceptWebMessage(webMessage)){
			return true;
		}

		String messageId = webMessage.getId();

		HybridMessage webMessageCached = instance.sendContainer.get(messageId);
		if(webMessageCached!=null && webMessageCached.getCallback()!=null){

			HmLogger.error("WebContextImpl", "[HybridMessage native] webMessageCached = " + webMessageCached.toString());

			if(webMessageCached.getCallback().onReceive(webMessage)){

			}
			return true;
		}

		HandlerManager<HybridMessageReceiver> handlerManager = instance.getHandlerManager(webMessage.getUri());

		if(handlerManager!=null){
			handlerManager.notifyHandlers("onReceive",webMessage,new WebMessageReceiverFilter(),HybridMessage.class);
			return true;
		}
		return false;
	}

	private boolean onInterceptWebMessage(HybridMessage webMessage) {
		if(mWebMessageInterceptor!=null){
			return mWebMessageInterceptor.intercept(webMessage);
		}
		return false;
	}

	private static class WebMessageReceiverFilter implements HandlerFilter<HybridMessage> {

		@Override
		public boolean filter(HybridMessage t) {

			HmLogger.error("WebContextImpl", "[HybridMessage native] filter message isAborted = " + t.isAborted());

			return t.isAborted();
		}

	}

	@Override
	public boolean send(HybridMessage webMessage) throws SendException, TimeoutException, HybridMessageException {

		if(webMessage == null){
			throw new HybridMessageRuntimeException("webMessage object must be not null");
		}

		if(webMessage.getType() == HybridMessageType.NATIVE) {
			HmLogger.info("WebContextImpl", "[HybridMessage native] cache  = {"+webMessage.getId()+"}");
			sendContainer.put(webMessage.getId(), webMessage);
		} else {
			HmLogger.info("WebContextImpl", "[HybridMessage native] enqueue = {"+webMessage+"}");
		}

		return HybridMessageSenderFactory.getSender(webMessage.getType()).send(webMessage);
	}

	@Override
	public void config(Configuration configuration) {
		Class<? extends Driver> driverClass = configuration.getDriver();
		Class<? extends HybridMessageHandlerThread> threadClass = configuration.getThread();

		try {
			if(driverClass!=null)
				driver = driverClass.newInstance();
		} catch (Exception e) {
			HmLogger.error("WebContextImpl", "config driver exception message = " + e.getMessage());
		}


		try {
			if(threadClass!=null)
				thread = threadClass.newInstance();
		} catch (Exception e) {
			HmLogger.error("WebContextImpl", "config thread exception message = " + e.getMessage());
		}

	}

	static boolean enqueue(HybridMessage webMessage) {

		HmLogger.info("WebContextImpl", "[HybridMessage native] receive message queue thread state = " + instance.thread.getState());

		if(instance.thread.getState() == Thread.State.NEW) {
			instance.thread.start();
		}

		return instance.thread.enqueue(webMessage);
	}

	private HandlerManager<HybridMessageReceiver> getHandlerManager(WebUri uri){

		HmLogger.info("WebContextImpl", "[HybridMessage native] getHandlerManager weburi string = " + uri.toUriString());

		String uriString = WebUri.WebUriHelper.getQueryParamsPrefix(uri);

		HmLogger.info("WebContextImpl", "[HybridMessage native] getHandlerManager getQueryParamsPrefix = " + uriString);

		HandlerManager<HybridMessageReceiver> handlerManager = webMessageReceiverMapping.get(uriString);

		HmLogger.info("WebContextImpl", "[HybridMessage native] getHandlerManager handlerManager = " + handlerManager);

		return handlerManager;
	}

	@Override
	public boolean startReceiveWebMessage(WebClient webClient) {
		return webClient.startReceiveWebMessage();
	}

	@Override
	public void setWebMessageInterceptor(WebMessageInterceptor interceptor) {
		mWebMessageInterceptor = interceptor;
	}

	static void destoryWebMessage(HybridMessage wm){
		if(wm!=null && wm.getId()!=null){
			instance.sendContainer.remove(wm.getId());
		}
	}

}

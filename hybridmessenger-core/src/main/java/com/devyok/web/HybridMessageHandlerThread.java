package com.devyok.web;

import com.devyok.web.common.HandlerException;

public abstract class HybridMessageHandlerThread extends Thread {

	protected String LOG_TAG = HybridMessageHandlerThread.class.getSimpleName();
	
	protected boolean handle(HybridMessage webMessage) throws HandlerException {
		return HybridMessengerImpl.dispatchOnReceive(webMessage);
	}
	
	public abstract boolean enqueue(HybridMessage webMessage);
	
	public abstract boolean quit();

	@Override
	public synchronized void start() {
		super.start();
		HmLogger.info(LOG_TAG, "[HybridMessage native] start");
	}

	@Override
	public void run() {
		super.run();
		HmLogger.info(LOG_TAG, "[HybridMessage native] run");
	}
	
	
	
}

package com.devyok.web;

import com.devyok.web.common.HandlerException;

import java.util.concurrent.LinkedBlockingQueue;


class HybridMessageHandlerThreadImpl extends HybridMessageHandlerThread {
	
	private final LinkedBlockingQueue<HybridMessage> mWorkBlockingQueue = new LinkedBlockingQueue<HybridMessage>();
	
	@Override
	public boolean enqueue(HybridMessage webMessage) {
		mWorkBlockingQueue.add(webMessage);
		return true;
	}
	
	@Override
	public void run() {
		super.run();
		
		while(true){
			
			try {
				
				HmLogger.info(LOG_TAG, "[HybridMessage native] start take");
				HybridMessage message = mWorkBlockingQueue.take();
				HmLogger.info(LOG_TAG, "[HybridMessage native] take one message = " + message);
				
				handle(message);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				HmLogger.info(LOG_TAG, "[HybridMessage native] InterruptedException");
			} catch (HandlerException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public boolean quit() {
		return false;
	}
	
	

}

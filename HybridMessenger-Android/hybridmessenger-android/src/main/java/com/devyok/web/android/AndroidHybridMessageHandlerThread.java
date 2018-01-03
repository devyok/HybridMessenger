package com.devyok.web.android;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

import com.devyok.web.HybridMessage;
import com.devyok.web.HybridMessageHandlerThread;
import com.devyok.web.HmLogger;
import com.devyok.web.common.HandlerException;

/**
 * @author wei.deng
 */
public final class AndroidHybridMessageHandlerThread extends HybridMessageHandlerThread implements Callback{

    private static final int DISPATCH = 0x0001;
    
    Handler mHandler;
    Looper mLooper;
    
    @Override
	public boolean handleMessage(Message msg) {
		
		int what = msg.what;
		
		if(DISPATCH == what){
			Object obj = msg.obj;
			if(obj!=null && obj instanceof HybridMessage){
				HybridMessage webMessage = (HybridMessage)obj;
				HmLogger.error("AndroidHybridMessageHandlerThread", "[HybridMessage native] handle one webMessage");
				try {
					handle(webMessage);
				} catch (HandlerException e) {
					e.printStackTrace();
				}
				
			}
			return true;
		}
		
		return false;
	}
    
    
    @Override
    public void run() {
        Looper.prepare();
        HmLogger.info("AndroidHybridMessageHandlerThread", "[HybridMessage native] run***");
        
        synchronized (this) {
            mLooper = Looper.myLooper();
            mHandler = new Handler(mLooper,this);
            notifyAll();
        }
        
        Looper.loop();
    }
    
    public Handler getHandler() {
        if (!isAlive()) {
            return null;
        }
        
        // If the thread has been started, wait until the looper has been created.
        synchronized (this) {
            while (isAlive() && mHandler == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        return mHandler;
    }
    
    @Override
    public boolean quit() {
        Looper looper = mLooper;
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }
    
	@Override
	public boolean enqueue(HybridMessage webMessage) {
		Handler handler = getHandler();
		Message msg = Message.obtain(handler, DISPATCH);
    	msg.obj = webMessage;
    	return handler.sendMessage(msg);
	}
	
}

package com.devyok.web.android;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.devyok.web.Driver;
import com.devyok.web.HybridMessage;
import com.devyok.web.HybridMessenger;
import com.devyok.web.HmLogger;

/**
 * @author wei.deng
 */
public class HybridMessageReceiveClient extends WebChromeClient implements Driver{
	
	private static final String LOG_TAG = HybridMessageReceiveClient.class.getSimpleName();
	
	@Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
    	HmLogger.info(LOG_TAG, "[HybridMessage native] onJsPrompt enter");
    	
    	HybridMessage webMessage = HybridMessage.Builder.create(message, WebClientImpl.create(view)).build();
    	
    	HmLogger.error(LOG_TAG, "[HybridMessage native] onJsPrompt receive webMessage = " + webMessage);
    	
    	if(webMessage == null){
    		webMessage = HybridMessage.MESSAGE_INIT_ERROR;
    	}
    	
    	boolean dispatchResult = false;
		try {
			dispatchResult = HybridMessenger.getMessenger().send(webMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	HmLogger.info(LOG_TAG, "[HybridMessage native] onJsPrompt WebMessageDispatcher result = " + dispatchResult);
    	
        result.confirm(String.valueOf(dispatchResult));
        HmLogger.info(LOG_TAG, "[HybridMessage native] onJsPrompt exit");
    	
    	
        return true;
    }
	
	
}

package com.devyok.web.android;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
/**
 * @author wei.deng
 */
class WebViewClientProxy extends WebViewClient{

	private WebViewClient mBase; 
	
	public WebViewClientProxy(WebViewClient base){
		this.mBase = base;
	}
	
	@Override
	public void doUpdateVisitedHistory(WebView view, String url,
			boolean isReload) {
		super.doUpdateVisitedHistory(view, url, isReload);
		if(mBase!=null){
			mBase.doUpdateVisitedHistory(view, url, isReload);
		}
	}

	@Override
	public void onFormResubmission(WebView view, Message dontResend,
			Message resend) {
		// TODO Auto-generated method stub
		super.onFormResubmission(view, dontResend, resend);
		if(mBase!=null){
			mBase.onFormResubmission(view, dontResend, resend);
		}
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onLoadResource(view, url);
		if(mBase!=null){
			mBase.onLoadResource(view, url);
		}
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onPageFinished(view, url);
		if(mBase!=null){
			mBase.onPageFinished(view, url);
		}
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		if(mBase!=null){
			mBase.onPageStarted(view, url, favicon);
		} 
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		super.onReceivedError(view, errorCode, description, failingUrl);
		if(mBase!=null){
			mBase.onReceivedError(view, errorCode, description, failingUrl);
		}
	}

	@Override
	public void onReceivedHttpAuthRequest(WebView view,
			HttpAuthHandler handler, String host, String realm) {
		super.onReceivedHttpAuthRequest(view, handler, host, realm);
		if(mBase!=null){
			mBase.onReceivedHttpAuthRequest(view, handler, host, realm);
		}
	}

	@Override
	public void onReceivedLoginRequest(WebView view, String realm,
			String account, String args) {
		super.onReceivedLoginRequest(view, realm, account, args);
		if(mBase!=null){
			mBase.onReceivedLoginRequest(view, realm, account, args);
		}
	}

	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler,
			SslError error) {
		super.onReceivedSslError(view, handler, error);
		if(mBase!=null){
			mBase.onReceivedSslError(view, handler, error);
		}
	}

	@Override
	public void onScaleChanged(WebView view, float oldScale, float newScale) {
		super.onScaleChanged(view, oldScale, newScale);
		if(mBase!=null){
			mBase.onScaleChanged(view, oldScale, newScale);
		}
	}

	@Override
	public void onTooManyRedirects(WebView view, Message cancelMsg,
			Message continueMsg) {
		super.onTooManyRedirects(view, cancelMsg, continueMsg);
		if(mBase!=null){
			mBase.onTooManyRedirects(view, cancelMsg, continueMsg);
		}
	}

	@Override
	public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
		// TODO Auto-generated method stub
		super.onUnhandledKeyEvent(view, event);
		if(mBase!=null){
			mBase.onUnhandledKeyEvent(view,event);
		}
	}

	@Override
	public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
		
		if(mBase!=null){
			return mBase.shouldInterceptRequest(view, url);
		}
		
		return super.shouldInterceptRequest(view, url);
	}

	@Override
	public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
		
		if(mBase!=null){
			return mBase.shouldOverrideKeyEvent(view, event);
		}
		
		return super.shouldOverrideKeyEvent(view, event);
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		
		if(mBase!=null){
			return mBase.shouldOverrideUrlLoading(view, url);
		}
		
		return super.shouldOverrideUrlLoading(view, url);
	}

}

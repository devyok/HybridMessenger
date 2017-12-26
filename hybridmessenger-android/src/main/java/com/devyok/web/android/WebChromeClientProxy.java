package com.devyok.web.android;

import android.graphics.Bitmap;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
/**
 * @author wei.deng
 */
class WebChromeClientProxy extends WebChromeClient{

	private WebChromeClient mBase;
	
	public WebChromeClientProxy(WebChromeClient base){
		this.mBase = base;
	}
	
	@Override
	public Bitmap getDefaultVideoPoster() {
		// TODO Auto-generated method stub
		
		if(mBase!=null){
			return mBase.getDefaultVideoPoster();
		}
		
		return super.getDefaultVideoPoster();
	}

	@Override
	public View getVideoLoadingProgressView() {
		// TODO Auto-generated method stub
		if(mBase!=null){
			return mBase.getVideoLoadingProgressView();
		}
		return super.getVideoLoadingProgressView();
	}

	@Override
	public void getVisitedHistory(ValueCallback<String[]> callback) {
		// TODO Auto-generated method stub
		super.getVisitedHistory(callback);
		
		if(mBase!=null){
			mBase.getVisitedHistory(callback);
		}
		
	}

	@Override
	public void onCloseWindow(WebView window) {
		if(mBase!=null){
			mBase.onCloseWindow(window);
		} else {
			super.onCloseWindow(window);
		}
	}

	@Override
	public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
		// TODO Auto-generated method stub
		
		if(mBase!=null){
			return mBase.onConsoleMessage(consoleMessage);
		} 
		
		return super.onConsoleMessage(consoleMessage);
	}

	@Override
	public void onConsoleMessage(String message, int lineNumber, String sourceID) {
		
		if(mBase!=null){
			mBase.onConsoleMessage(message, lineNumber, sourceID);
		} else {
			super.onConsoleMessage(message, lineNumber, sourceID);
		}
	}

	@Override
	public boolean onCreateWindow(WebView view, boolean isDialog,
			boolean isUserGesture, Message resultMsg) {
		// TODO Auto-generated method stub
		
		if(mBase!=null){
			return mBase.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
		} 
		
		return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
	}

	@Override
	public void onExceededDatabaseQuota(String url, String databaseIdentifier,
			long quota, long estimatedDatabaseSize, long totalQuota,
			QuotaUpdater quotaUpdater) {
		// TODO Auto-generated method stub
		
		if(mBase!=null){
			mBase.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
		} else {
			super.onExceededDatabaseQuota(url, databaseIdentifier, quota,
					estimatedDatabaseSize, totalQuota, quotaUpdater);
		}
		
	}

	@Override
	public void onGeolocationPermissionsHidePrompt() {
		// TODO Auto-generated method stub
		super.onGeolocationPermissionsHidePrompt();
		
		if(mBase!=null){
			mBase.onGeolocationPermissionsHidePrompt();
		} else {
			super.onGeolocationPermissionsHidePrompt();
		}
		
	}

	@Override
	public void onGeolocationPermissionsShowPrompt(String origin,
			Callback callback) {
		// TODO Auto-generated method stub
		if(mBase!=null){
			mBase.onGeolocationPermissionsShowPrompt(origin, callback);
		} else {
			super.onGeolocationPermissionsShowPrompt(origin, callback);
		}
	}

	@Override
	public void onHideCustomView() {
		// TODO Auto-generated method stub
		
		if(mBase!=null){
			mBase.onHideCustomView();
		} else {
			super.onHideCustomView();
			
		}
		
	}

	@Override
	public boolean onJsAlert(WebView view, String url, String message,
			JsResult result) {
		// TODO Auto-generated method stub
		
		if(mBase!=null){
			return mBase.onJsAlert(view, url, message, result);
		} 
		
		return super.onJsAlert(view, url, message, result);
	}

	@Override
	public boolean onJsBeforeUnload(WebView view, String url, String message,
			JsResult result) {
		
		if(mBase!=null){
			return mBase.onJsBeforeUnload(view, url, message, result);
		} 
		
		return super.onJsBeforeUnload(view, url, message, result);
	}

	@Override
	public boolean onJsConfirm(WebView view, String url, String message,
			JsResult result) {
		
		if(mBase!=null){
			return mBase.onJsConfirm(view, url, message, result);
		} 
		
		// TODO Auto-generated method stub
		return super.onJsConfirm(view, url, message, result);
	}

	@Override
	public boolean onJsPrompt(WebView view, String url, String message,
			String defaultValue, JsPromptResult result) {
		if(mBase!=null){
			return mBase.onJsPrompt(view, url, message, defaultValue, result);
		} 
		// TODO Auto-generated method stub
		return super.onJsPrompt(view, url, message, defaultValue, result);
	}

	@Override
	public boolean onJsTimeout() {
		if(mBase!=null){
			return mBase.onJsTimeout();
		} 
		// TODO Auto-generated method stub
		return super.onJsTimeout();
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		// TODO Auto-generated method stub
		if(mBase!=null){
			mBase.onProgressChanged(view, newProgress);
		} else {
			super.onProgressChanged(view, newProgress);
		}
	}

	@Override
	public void onReachedMaxAppCacheSize(long requiredStorage, long quota,
			QuotaUpdater quotaUpdater) {
		
		if(mBase!=null){
			mBase.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
		} else {
			super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
		}
		
		// TODO Auto-generated method stub
	}

	@Override
	public void onReceivedIcon(WebView view, Bitmap icon) {
		if(mBase!=null){
			mBase.onReceivedIcon(view, icon);
		} else {
			// TODO Auto-generated method stub
			super.onReceivedIcon(view, icon);
		}
	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		
		if(mBase!=null){
			mBase.onReceivedTitle(view,title);
		} else {
			// TODO Auto-generated method stub
			super.onReceivedTitle(view, title);
		}
		
	}

	@Override
	public void onReceivedTouchIconUrl(WebView view, String url,
			boolean precomposed) {
		
		if(mBase!=null){
			mBase.onReceivedTouchIconUrl(view, url, precomposed);
		} else {
			// TODO Auto-generated method stub
			super.onReceivedTouchIconUrl(view, url, precomposed);
			
		}
		
		
	}

	@Override
	public void onRequestFocus(WebView view) {
		if(mBase!=null){
			mBase.onRequestFocus(view);
		} else {
			// TODO Auto-generated method stub
			super.onRequestFocus(view);
		}
	}

	@Override
	public void onShowCustomView(View view, CustomViewCallback callback) {
		if(mBase!=null){
			mBase.onShowCustomView(view, callback);
		} else {
			// TODO Auto-generated method stub
			super.onShowCustomView(view, callback);
		}
	}

	@Override
	public void onShowCustomView(View view, int requestedOrientation,
			CustomViewCallback callback) {
		if(mBase!=null){
			mBase.onShowCustomView(view, requestedOrientation, callback);
		} else {
			// TODO Auto-generated method stub
			super.onShowCustomView(view, requestedOrientation, callback);
		}
		
	}

}

package com.devyok.bybrid.messenger.sample;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.devyok.web.HybridMessage;
import com.devyok.web.HybridMessenger;
import com.devyok.web.HybridMessenger.WebMessageInterceptor;
import com.devyok.web.HmLogger;
import com.devyok.web.android.WebClientImpl;
import com.devyok.web.hybridmessenger.sample.R;

public class H5toNativeTest03Activity extends Activity{

	WebView webView = null;
	
	WebMessageInterceptor interceptor = new WebMessageInterceptor() {
		
		@Override
		public boolean intercept(final HybridMessage webMessage) {

			HmLogger.error("H5toNativeTest03Activity", "[HybridMessage native] 拦截到的消息 = " + webMessage);
			
			return false;
		}
		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.h5tonative_activity);
		
		webView = (WebView)this.findViewById(R.id.wb01);
		webView.loadUrl("file:///android_asset/www/pages/sample/H5sendMessageToNative.html");
		
		HybridMessenger.getMessenger().startReceiveWebMessage(WebClientImpl.create(webView));
		
		HybridMessenger.getMessenger().setWebMessageInterceptor(interceptor);
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		HmLogger.info("H5toNativeTest03Activity", "[HybridMessage native] WbPromptIndexActivity destory webView");
		if(webView!=null){
			webView.destroy();
			webView = null;
		}
	}
	
	
	
}

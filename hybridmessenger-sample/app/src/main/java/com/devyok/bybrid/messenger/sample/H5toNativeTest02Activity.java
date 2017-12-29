package com.devyok.bybrid.messenger.sample;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.devyok.web.HybridMessage;
import com.devyok.web.HybridMessageFilter;
import com.devyok.web.HybridMessageReceiver;
import com.devyok.web.HybridMessenger;
import com.devyok.web.WebUri;
import com.devyok.web.HmLogger;
import com.devyok.web.android.WebClientImpl;
import com.devyok.web.exception.SendException;
import com.devyok.web.exception.TimeoutException;
import com.devyok.web.exception.HybridMessageException;
import com.devyok.web.hybridmessenger.sample.R;

public class H5toNativeTest02Activity extends Activity{

	static final String LOG_TAG = H5toNativeTest02Activity.class.getSimpleName();
	
	WebView webView = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.h5tonative_activity);
		
		webView = (WebView)this.findViewById(R.id.wb01);
		webView.loadUrl("file:///android_asset/www/pages/sample/H5sendMessageToNative.html");
		
		WebClientImpl.create(webView).startReceiveWebMessage();
		
		HybridMessenger.getMessenger().registerHybridMessageReceiver(messageReceiver2);
		HybridMessenger.getMessenger().registerHybridMessageReceiver(messageReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		HmLogger.info(LOG_TAG, "[HybridMessage Native] H5toNativeTest02Activity destory webView");
		if(webView!=null){
			webView.destroy();
			webView = null;
		}
		HybridMessenger.getMessenger().unregisterHybridMessageReceiver(messageReceiver2);
		HybridMessenger.getMessenger().unregisterHybridMessageReceiver(messageReceiver);
	}
	
	HybridMessageReceiver messageReceiver = new HybridMessageReceiver() {
		
		@Override
		public boolean onReceive(HybridMessage hybridMessage) {
			
			HmLogger.error(LOG_TAG, "[HybridMessage Native] high priority handle message = " + hybridMessage);
			
			HybridMessage replyMessage = new HybridMessage.Builder(hybridMessage).setData("response data3").build();
			try {
				hybridMessage.reply(replyMessage);
			} catch (SendException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (HybridMessageException e) {
				e.printStackTrace();
			}
			
			return true;
		}
		
		@Override
		public HybridMessageFilter getFilter() {
			return new HybridMessageFilter() {
				
				@Override
				public int getPriority() {
					return HybridMessageFilter.DEFAULT.getPriority() + 1;
				}

				@Override
				public WebUri getWebUri() {
					return WebUris.USER_MODULE_URI;
				}
			};
		}
		
	};
	
	HybridMessageReceiver messageReceiver2 = new HybridMessageReceiver() {
		
		@Override
		public boolean onReceive(HybridMessage hybridMessage) {
			
			HmLogger.error(LOG_TAG, "[HybridMessage Native] low priority message = " + hybridMessage);
			
			try {
				hybridMessage.reply("response data2");
			} catch (SendException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (HybridMessageException e) {
				e.printStackTrace();
			}
			
			return true;
		}
		
		@Override
		public HybridMessageFilter getFilter() {
			return new HybridMessageFilter() {
				
				@Override
				public int getPriority() {
					return HybridMessageFilter.DEFAULT.getPriority();
				}

				@Override
				public WebUri getWebUri() {
					return WebUris.USER_MODULE_URI;
				}
			};
		}
		
	};
	
}

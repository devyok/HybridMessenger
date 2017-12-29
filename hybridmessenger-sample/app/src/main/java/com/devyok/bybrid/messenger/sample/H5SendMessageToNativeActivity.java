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

public class H5SendMessageToNativeActivity extends Activity{

	WebView webView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.h5tonative_activity);
		
		webView = (WebView)this.findViewById(R.id.wb01);
		webView.loadUrl("file:///android_asset/www/pages/prompt/H5sendMessageToNative.html");
		
		WebClientImpl.create(webView).startReceiveWebMessage();
		
		HybridMessenger.getMessenger().registerHybridMessageReceiver(new HybridMessageReceiver() {
			
			@Override
			public boolean onReceive(final HybridMessage hybridMessage) {
				
				HmLogger.error("WbPromptIndexActivity", "[HybridMessage native] WbPromptIndexActivity receive1 message = " + hybridMessage);
				
				return true;
			}
			
		});
		
		HybridMessenger.getMessenger().registerHybridMessageReceiver(new HybridMessageReceiver() {
			
			@Override
			public boolean onReceive(HybridMessage hybridMessage) {
				
				HmLogger.error("WbPromptIndexActivity", "[HybridMessage native] WbPromptIndexActivity receive2 message = " + hybridMessage);
				
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
						return HybridMessageFilter.DEFAULT.getPriority() + 1;
					}

					@Override
					public WebUri getWebUri() {
						
						
						return null;
					}
				};
			}
			
		});
		
		HybridMessenger.getMessenger().registerHybridMessageReceiver(new HybridMessageReceiver() {
			
			@Override
			public boolean onReceive(HybridMessage hybridMessage) {
				
				HmLogger.error("WbPromptIndexActivity", "[HybridMessage native] WbPromptIndexActivity receive3 message = " + hybridMessage);
				
				
				HybridMessage replyMessage = new HybridMessage.Builder(hybridMessage)
														.setData("response data3")
													    .build();
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
			
		});
		
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		HmLogger.info("WbPromptIndexActivity", "[HybridMessage native] WbPromptIndexActivity destory webView");
		if(webView!=null){
			webView.destroy();
			webView = null;
		}
	}
	
	
	
}

package com.devyok.bybrid.messenger.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebChromeClient;
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

/**
 * @author wei.deng
 */
public class H5toNativeTest01Activity extends Activity{

	private static final String LOG_TAG = H5toNativeTest01Activity.class.getSimpleName();

	WebView webView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.h5tonative_activity);

		webView = (WebView)this.findViewById(R.id.wb01);

		HybridMessenger.getMessenger().startReceiveWebMessage(WebClientImpl.create(webView));

		HybridMessenger.getMessenger().registerHybridMessageReceiver(userMessageReceiver);
		HybridMessenger.getMessenger().registerHybridMessageReceiver(productMessageReceiver);

		webView.loadUrl("file:///android_asset/www/pages/sample/H5sendMessageToNative.html");

		//验证当通过HybridMessenger框架管理之后，将不能再次设置WebChromeClient
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				HmLogger.info("WebViewProviderProxy", "delay setWebChromeClient");

				webView.setWebChromeClient(new WebChromeClient());


			}
		}, 3*1000);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		HmLogger.info(LOG_TAG, "[HybridMessage Native] WbPromptIndexActivity destory webView");
		if(webView!=null){
			webView.destroy();
			webView = null;
		}
		HybridMessenger.getMessenger().unregisterHybridMessageReceiver(userMessageReceiver);
		HybridMessenger.getMessenger().unregisterHybridMessageReceiver(productMessageReceiver);
	}



	HybridMessageReceiver userMessageReceiver = new HybridMessageReceiver() {

		@Override
		public boolean onReceive(final HybridMessage hybridMessage) {

			HmLogger.error(LOG_TAG, "[HybridMessage Native] 登陆 接收到H5端的消息 = " + hybridMessage);

			try {

				//
				if(hybridMessage.getData().equals("h5 200")){

					HmLogger.error(LOG_TAG, "[HybridMessage Native] 登陆 接收到h5 200消息");

					hybridMessage.reply("Native 200");

					return true;
				}

				HmLogger.error(LOG_TAG, "[HybridMessage Native] 登陆  发送处理h5消息的结果");
				hybridMessage.reply("user module handle result data");
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
					return HybridMessageFilter.MAX_PRIORITY;
				}

				@Override
				public WebUri getWebUri() {
					return WebUris.USER_MODULE_URI;
				}
			};
		}
	};

	HybridMessageReceiver productMessageReceiver = new HybridMessageReceiver() {

		@Override
		public boolean onReceive(final HybridMessage hybridMessage) {

			HmLogger.error(LOG_TAG, "[HybridMessage Native] product module web message receiver = " + hybridMessage);

			try {
				hybridMessage.reply("response product module handle result");
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
					return HybridMessageFilter.MAX_PRIORITY;
				}

				@Override
				public WebUri getWebUri() {
					return WebUris.PRODUCT_MODULE_URI;
				}
			};
		}
	};

}

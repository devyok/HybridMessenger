package com.devyok.bybrid.messenger.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.devyok.web.HybridMessenger;
import com.devyok.web.WebClient;
import com.devyok.web.HybridMessage;
import com.devyok.web.HybridMessageFilter;
import com.devyok.web.HybridMessageReceiver;
import com.devyok.web.HybridMessageType;
import com.devyok.web.WebUri;
import com.devyok.web.HmLogger;
import com.devyok.web.android.WebClientImpl;
import com.devyok.web.exception.SendException;
import com.devyok.web.exception.TimeoutException;
import com.devyok.web.exception.HybridMessageException;
import com.devyok.web.hybridmessenger.sample.R;

/**
 *
 * @author wei.deng
 *
 */
public class H5toNativeTest04Activity extends Activity{

	WebView webView = null;

	static int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.h5tonative04_activity);

		webView = (WebView)this.findViewById(R.id.wb01);
		webView.loadUrl("file:///android_asset/www/pages/sample/H5sendMessageToNative.html");

		final WebClient viewClient = WebClientImpl.create(webView);

		HybridMessenger.getMessenger().startReceiveWebMessage(viewClient);

		this.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				count = 0;

				HybridMessage webMessage = new HybridMessage.Builder(viewClient)
						.setData("send json data("+(++count)+")")
						.setMessageType(HybridMessageType.NATIVE)
						.setUri(WebUris.USER_MODULE_URI)
						.setCallback(new HybridMessageReceiver() {
							@Override
							public boolean onReceive(HybridMessage hybridMessage) {

								HmLogger.error("H5toNativeTest04Activity", "[HybridMessage Native] 主动 接收到H5的反馈 = " + hybridMessage);

								try {

									if(hybridMessage.getData().equals("reply data10")){
										hybridMessage.reply("exit");

										//务必要destory
										//webMessage.destory();

										HmLogger.error("H5toNativeTest04Activity", "[HybridMessage Native] 主动 接收到H5的反馈达到了10个消息");
									} else {
										HmLogger.error("H5toNativeTest04Activity", "[HybridMessage Native] 主动 接收到H5的反后，并反馈给H5，说已经收到消息了");
										hybridMessage.reply("12345");
									}

								} catch (SendException e) {
									e.printStackTrace();
								} catch (TimeoutException e) {
									e.printStackTrace();
								} catch (HybridMessageException e) {
									e.printStackTrace();
								}

								return true;
							}
						}).build();

				try {

					HmLogger.error("H5toNativeTest04Activity", "[HybridMessage Native] 主动 向H5发送消息 = " + webMessage);

					HybridMessenger.getMessenger().send(webMessage);
				} catch (SendException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				} catch (HybridMessageException e) {
					e.printStackTrace();
				}

			}
		});

		HybridMessenger.getMessenger().registerHybridMessageReceiver(userMessageReceiver);
		HybridMessenger.getMessenger().registerHybridMessageReceiver(productMessageReceiver);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		HmLogger.info("H5toNativeTest04Activity", "[HybridMessage Native] WbPromptIndexActivity destory webView");
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

			HmLogger.error("H5toNativeTest04Activity", "[HybridMessage Native] user web message receiver = " + hybridMessage);

			try {
				hybridMessage.reply("response user handle result");
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

			HmLogger.error("H5toNativeTest04Activity", "[HybridMessage Native] product web message receiver = " + hybridMessage);

			try {
				hybridMessage.reply("response product handle result");
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

package com.devyok.web;

import com.devyok.web.exception.SendException;
import com.devyok.web.exception.TimeoutException;
import com.devyok.web.exception.HybridMessageException;
import com.devyok.web.exception.HybridMessageRuntimeException;

public abstract class HybridMessageSenderTemplete implements HybridMessageSender {

	static String LOG_TAG = "HybridMessageSenderTemplete";

	@Override
	public boolean send(final HybridMessage webMessage) throws SendException,
			TimeoutException, HybridMessageException {

		if (webMessage == null) {
			throw new HybridMessageRuntimeException("webMessage is null");
		}

		try {

			final String jsScript = buildScriptCommand(webMessage);

			final WebClient webEnv = webMessage.getWebClient();

			HmLogger.info(LOG_TAG, "[HybridMessage native] run webView = " + webEnv);

			if (webEnv != null) {
				HmLogger.info(LOG_TAG, "[HybridMessage native] run jsScript = " + jsScript);
				webEnv.runJavaScript(jsScript);
			} else {
				webMessage.recycle();
				HmLogger.error(LOG_TAG, "[HybridMessage native] jsScript can't run , webMessage's webview is null");
			}

		} catch (Throwable e) {
			throw new HybridMessageException("HybridMessage Exception", e);
		}

		return true;
	}

	public abstract String buildScriptCommand(final HybridMessage webMessage);

}

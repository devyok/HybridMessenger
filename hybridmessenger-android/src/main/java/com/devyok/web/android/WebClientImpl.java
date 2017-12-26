package com.devyok.web.android;

import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.devyok.web.Executor;
import com.devyok.web.WebClient;
import com.devyok.web.HmLogger;
import com.devyok.web.android.WebViewProviderProxy.OnInterceptMethod;
import com.devyok.web.exception.HybridMessageRuntimeException;

import java.lang.reflect.Method;
/**
 * @author wei.deng
 */
public class WebClientImpl implements WebClient , OnInterceptMethod{

	private WebView mWebView;

	private WebChromeClient androidReceiveClient;

	private WebViewClient webViewClient;

	private WebViewProviderProxy viewProviderProxy;

	private WebClientImpl(WebView wv) {
		this(wv, new HybridMessageReceiveClient(),null);
	}

	private WebClientImpl(WebView wv,WebChromeClient webChromeClient) {
		this(wv, webChromeClient,null);
	}

	private WebClientImpl(WebView wv,WebChromeClient webChromeClient,WebViewClient webViewClient) {
		this.mWebView = wv;

		viewProviderProxy = new WebViewProviderProxy(wv,this);
		this.androidReceiveClient = new WebChromeClientProxy(webChromeClient);
		this.webViewClient = new WebViewClientProxy(webViewClient);
	}

	public static WebClientImpl create(WebView wv) {

		if(wv == null){
			throw new HybridMessageRuntimeException("WebView object must be not null");
		}

		return new WebClientImpl(wv);
	}


	public static WebClientImpl create(WebView wv,WebChromeClient webChromeClient) {

		if(wv == null || webChromeClient == null){
			throw new HybridMessageRuntimeException("please check WebClientImpl#create method parametors");
		}

		return new WebClientImpl(wv,webChromeClient);
	}


	public static WebClientImpl create(WebView wv,WebChromeClient webChromeClient,WebViewClient webViewClient) {

		if(wv == null || webChromeClient == null || webViewClient == null){
			throw new HybridMessageRuntimeException("please check WebClientImpl#create method parametors");
		}

		return new WebClientImpl(wv,webChromeClient,webViewClient);
	}


	@Override
	public void runJavaScript(final String script) {

		if(script == null){
			throw new HybridMessageRuntimeException("script must be not null");
		}

		getExecutor().execute(new Runnable() {

			@Override
			public void run() {
				if(mWebView!=null){
					mWebView.loadUrl(script);
				}
			}
		});
	}

	@Override
	public Executor getExecutor() {
		return new Executor() {

			@Override
			public boolean execute(Runnable r) {
				return mWebView.getHandler().post(r);
			}
		};
	}

	@SuppressLint("NewApi")
	@Override
	public boolean startReceiveWebMessage() {
		WebView webView = mWebView;
		WebSettings webSettings = webView.getSettings();

		if(webViewClient!=null) {
			webView.setWebViewClient(webViewClient);
		}

		if(androidReceiveClient!=null){
			webView.setWebChromeClient(androidReceiveClient);
		}

		viewProviderProxy.addProxy();

		webSettings.setJavaScriptEnabled(true);
		// 可以读取文件缓存(manifest生效)
		webSettings.setAllowFileAccess(true);
		// 设置可以使用localStorage
		webSettings.setDomStorageEnabled(true);


		return true;
	}

	@Override
	public boolean onIntercept(Object target, Object proxy, Method method,
							   Object[] args) {

		String methodName = method.getName();

		if(methodName.equals("setWebViewClient") || "setWebChromeClient".equals(methodName)) {

			HmLogger.error("WebViewProviderProxy", "intercept method name = "+ methodName + " , don't invoke");

			return true;
		}

		return false;
	}

}

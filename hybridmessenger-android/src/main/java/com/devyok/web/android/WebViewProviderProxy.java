package com.devyok.web.android;

import android.webkit.WebView;

import com.devyok.web.HmLogger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 只拦截当前提供的webview的provider所有方法
 * @author wei.deng
 */
public class WebViewProviderProxy implements InvocationHandler{

	public interface OnInterceptMethod {
		public boolean onIntercept(Object target,Object proxy,Method method, Object[] args);
	}

	WebView mWebView;
	Object mProviderInstance;
	OnInterceptMethod mOnInterceptMethod;

	public WebViewProviderProxy(final WebView webView,OnInterceptMethod onInterceptMethod){
		this.mWebView = webView;
		this.mOnInterceptMethod = onInterceptMethod;
	}

	public void addProxy() {

		if (mWebView != null) {

			try {

				Field mProviderFiled = mWebView.getClass().getDeclaredField("mProvider");

				mProviderFiled.setAccessible(true);

				mProviderInstance = mProviderFiled.get(mWebView);

				HmLogger.info("WebViewProviderProxy", "mProviderInstance = " + mProviderInstance);

				Object proxy = Proxy.newProxyInstance(mProviderInstance.getClass().getClassLoader(), mProviderInstance.getClass().getInterfaces(), this);
				mProviderFiled.set(mWebView, proxy);

				HmLogger.info("WebViewProviderProxy", "web provider proxy completed");

			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if(mOnInterceptMethod!=null){
			if(mOnInterceptMethod.onIntercept(mProviderInstance,proxy, method, args)){
				return null;
			}
		}
		return method.invoke(mProviderInstance, args);
	}

}

package com.devyok.web.android;

import android.net.Uri;

import com.devyok.web.HmLogger;
import com.devyok.web.WebUri;

import java.util.Set;
/**
 * @author wei.deng
 */
public class AndroidUri implements WebUri{

	private Uri uri;
	
	public AndroidUri(Uri uri) {
		this.uri = uri;
	}

	public Uri getUri() {
		return uri;
	}
	
	public static WebUri parse(String uriString) {
		return new AndroidUri(Uri.parse(uriString));
	}
	
	public static AndroidUri create(String path,WebUri base) {
		return new AndroidUri(Uri.parse(base.toUriString() + "/" + path));
	}

	@Override
	public String getScheme() {
		return uri.getScheme();
	}

	@Override
	public String getHost() {
		return uri.getHost();
	}

	@Override
	public int getPort() {
		return uri.getPort();
	}

	@Override
	public String getPath() {
		return uri.getPath();
	}

	@Override
	public String getQuery() {
		return uri.getQuery();
	}

	@Override
	public String getQueryParameter(String key) {
		return uri.getQueryParameter(key);
	}

	@Override
	public String toUriString() {
		return uri.toString();
	}

	public String toString(){
		return this.uri.toString();
	}
	
	public void printDetails(){
		String scheme = uri.getScheme();
    	String host = uri.getHost();
    	int port = uri.getPort();
    	String path = uri.getPath();
    	String params = uri.getQuery();
    	HmLogger.info("WebUri", "[WebMessage native] = " + scheme + " , host = " + host + " , port = " + port + " , path = " + path + " , params = " + params);
	}

	@Override
	public Set<String> getQueryParameterNames() {
		return this.uri.getQueryParameterNames();
	}
	
}

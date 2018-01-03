package com.devyok.bybrid.messenger.sample;

import com.devyok.web.WebUri;
import com.devyok.web.WebUri.WebUriAdapter;
import com.devyok.web.android.AndroidUri;

public class WebUris {

	static final WebUri base = new WebUriAdapter(){

		@Override
		public String getScheme() {
			return "HybridMessage";
		}
		
		@Override
		public String getHost() {
			return "devyok";
		}

		@Override
		public int getPort() {
			return 80;
		}

		@Override
		public String toUriString() {
			return getScheme()+"://"+getHost()+":"+getPort();
		}
		
	};
	
	public static WebUri USER_MODULE_URI = AndroidUri.create("user", base);
	
	public static WebUri PRODUCT_MODULE_URI = AndroidUri.create("product", base);
	
}

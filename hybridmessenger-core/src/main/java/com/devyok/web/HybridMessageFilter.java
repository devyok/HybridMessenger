package com.devyok.web;

import com.devyok.web.WebUri.WebUriAdapter;

/**
 * 
 * @author wei.deng
 * 
 */
public abstract class HybridMessageFilter {
	/**
	 * 最大优先级
	 */
	public static final int MAX_PRIORITY = 1000;
	
	public int getPriority() {
		return 500;
	}
	
	public abstract WebUri getWebUri() ;
	
	public static final HybridMessageFilter DEFAULT = new HybridMessageFilter() {
		
		@Override
		public int getPriority() {
			return 500;
		}

		@Override
		public WebUri getWebUri() {
			return new WebUriAdapter(){

				@Override
				public String getScheme() {
					return "HybridMessage";
				}
				
				@Override
				public String getHost() {
					return "global";
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
		}
	};
	
}

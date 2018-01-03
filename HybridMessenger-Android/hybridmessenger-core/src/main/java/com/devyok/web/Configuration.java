package com.devyok.web;

/**
 * @author wei.deng
 */
public class Configuration {
	
	public static final Configuration DEFAULT = new Configuration();
	
	private Class<? extends Driver> driver; 
	private Class<? extends HybridMessageHandlerThread> thread;
			
	private Configuration(){
	}
	
	public static class Builder {
		
		static final String DEFAULT_DRIVER_CLASS = "com.devyok.web.android.AndroidWebChromeClient";
		static final String DEFAULT_THREAD_CLASS = "com.devyok.web.HybridMessageHandlerThreadImpl";
		
		private Class<? extends Driver> driver; 
		private Class<? extends HybridMessageHandlerThread> thread;
		
		public Builder setThread(Class<? extends HybridMessageHandlerThread> thread) {
			this.thread = thread;
			return this;
		}
		
		public Builder setDriver(Class<? extends Driver> driver) {
			this.driver = driver;
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Configuration build(){
			Configuration configuration = new Configuration();
			configuration.driver = (Class<? extends Driver>) (this.driver == null ? getClass(DEFAULT_DRIVER_CLASS) : this.driver);
			configuration.thread = (Class<? extends HybridMessageHandlerThread>) (this.thread == null ? getClass(DEFAULT_THREAD_CLASS) : this.thread);
			return configuration;
		}
		
		private Class<?> getClass(String className){
			try {
				Class<?> d = (Class<?>) Class.forName(className);
				return d;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}


	public Class<? extends Driver> getDriver() {
		return driver;
	}
	
	
	public Class<? extends HybridMessageHandlerThread> getThread() {
		return thread;
	}
	
	
}

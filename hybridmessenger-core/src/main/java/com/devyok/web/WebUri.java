package com.devyok.web;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public interface WebUri {

	public String getScheme();
	
	public String getHost();
	
	public int getPort();
	
	public String getPath();
	
	public String getQuery();
	
	public String getQueryParameter(String key);
	
	public Set<String> getQueryParameterNames();
	
	public String toUriString();
	
	public static abstract class WebUriAdapter implements WebUri {

		@Override
		public Set<String> getQueryParameterNames() {
			return null;
		}

		@Override
		public String getScheme() {
			return null;
		}

		@Override
		public String getHost() {
			return null;
		}

		@Override
		public int getPort() {
			return 0;
		}

		@Override
		public String getPath() {
			return null;
		}

		@Override
		public String getQuery() {
			return null;
		}

		@Override
		public String getQueryParameter(String key) {
			return null;
		}

		@Override
		public String toUriString() {
			return null;
		}
		
	}
	
	public static final class WebUriHelper {

		public interface JsonKey {
			public static final String KEY_SCHEME = "scheme";
			public static final String KEY_HOST = "host";
			public static final String KEY_PORT = "port";
			public static final String KEY_PATH = "path";
			public static final String KEY_QUERY = "query";
		}
		
		static String helperStringClass = "com.devyok.web.android.AndroidUri";
		
		public static void setHelperClass(String clazz){
			if(!HybridMessageUtils.isEmpty(clazz)){
				helperStringClass = clazz;
			}
		}
		
		public static WebUri create(final String scheme,final String host,final int port,final String path){
			return new WebUri.WebUriAdapter() {

				@Override
				public String getScheme() {
					return scheme;
				}

				@Override
				public String getHost() {
					return host;
				}

				@Override
				public int getPort() {
					return port;
				}

				@Override
				public String getPath() {
					return path;
				}

				@Override
				public String toUriString() {
					StringBuffer stringBuffer = new StringBuffer();
					
					stringBuffer.append(getScheme())
								.append("://")
								.append(getHost())
								.append(":")
								.append(getPort())
								.append("/")
								.append(getPath());
					
					return stringBuffer.toString();
				}
			};
		}
		
		public static String getQueryParamsPrefix(WebUri webUri) {
			
			if(webUri == null){
				return HybridMessageUtils.EMPTY;
			}
			
			StringBuffer stringBuffer = new StringBuffer();
			
			stringBuffer.append(webUri.getScheme())
						.append("://")
						.append(webUri.getHost())
						.append(":")
						.append(webUri.getPort())
						.append("/")
						.append(webUri.getPath());
			
			return stringBuffer.toString();
		}
		
		public static String getPathPrefix(WebUri webUri) {
			
			if(webUri == null){
				return HybridMessageUtils.EMPTY;
			}
			
			StringBuffer stringBuffer = new StringBuffer();
			
			stringBuffer.append(webUri.getScheme())
						.append("://")
						.append(webUri.getHost())
						.append(":")
						.append(webUri.getPort());
			
			return stringBuffer.toString();
		}
		
		public static WebUri parse(String uriString){
			try {
				Class<?> helperClass = Class.forName(helperStringClass);
				
				Method m = helperClass.getDeclaredMethod("parse", String.class);
				
				WebUri uri = (WebUri)m.invoke(null, uriString);
				
				return uri;
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		

		public static WebUri toWebUri(JsonObject uri) {
			
			StringBuffer uriBuffer = new StringBuffer();
			
			String scheme = uri.get(JsonKey.KEY_SCHEME).getAsString();
			uriBuffer.append(scheme).append("://");
			
			String host = uri.get(JsonKey.KEY_HOST).getAsString();
			uriBuffer.append(host);
			
			
			JsonElement portEle = uri.get(JsonKey.KEY_PORT);
			if(portEle!=null){
				int port = portEle.getAsInt();
				uriBuffer.append(":").append(port);
			}
			
			JsonElement pathEle = uri.get(JsonKey.KEY_PATH);
			if(pathEle!=null){
				String path = pathEle.getAsString();
				uriBuffer.append("/").append(path);
			}
			
			
			JsonElement queryEle = uri.get(JsonKey.KEY_QUERY);
			if(queryEle!=null){
				
				JsonObject query = queryEle.getAsJsonObject();
				
				StringBuffer queryBuffer = new StringBuffer("?");
				
				for(Iterator<Entry<String, JsonElement>> iter = query.entrySet().iterator();iter.hasNext();){
					Entry<String, JsonElement> item = iter.next();
					
					String key = item.getKey();
					String value = item.getValue().getAsString();
					
					queryBuffer.append(key).append("=").append(value).append("&");
					
				}
				queryBuffer = queryBuffer.deleteCharAt(queryBuffer.length() - 1);
				uriBuffer.append(queryBuffer.toString());
			}
			
			return parse(uriBuffer.toString());
		}
		
	}
	
}

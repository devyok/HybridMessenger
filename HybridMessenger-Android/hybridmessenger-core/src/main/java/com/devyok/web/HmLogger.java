package com.devyok.web;

import android.util.Log;

public final class HmLogger {

	public static void info(String tag,String msg){
		Log.i(tag, msg);
	}
	
	public static void debug(String tag,String msg){
		Log.d(tag, msg);
	}

	public static void error(String tag, String msg) {
		Log.e(tag, msg);
	}
	
}

package com.devyok.web;

public final class HybridMessageUtils {

	public static final String EMPTY = "";
	
	public static boolean isEmpty(String text){
		if(text == null || EMPTY.equals(text)) {
			return true;
		}
		return false;
	}
	
}

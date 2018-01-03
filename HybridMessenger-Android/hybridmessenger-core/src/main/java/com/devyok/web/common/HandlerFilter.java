package com.devyok.web.common;

public interface HandlerFilter<T> {
	
	public boolean filter(T t);
	
}

package com.devyok.web;

/**
 * 
 * @author wei.deng
 *
 */
public abstract class HybridMessageReceiver {
	
	public abstract boolean onReceive(HybridMessage hybridMessage);
	
	public HybridMessageFilter getFilter(){
		return HybridMessageFilter.DEFAULT;
	}
	
}

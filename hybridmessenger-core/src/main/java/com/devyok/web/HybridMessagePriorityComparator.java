package com.devyok.web;
import java.util.Comparator;

/**
 * 
 * @author wei.deng
 *
 * @param <T>
 */
public class HybridMessagePriorityComparator<T> implements Comparator<T>{
	
	public int compare(T o1, T o2) {
		
		int wm1Priority = 0;
		int wm2Priority = 0;
		
		if(o1 instanceof HybridMessageReceiver) {
			wm1Priority = ((HybridMessageReceiver) o1).getFilter().getPriority();
		} 
		
		if(o2 instanceof HybridMessageReceiver) {
			wm2Priority = ((HybridMessageReceiver) o2).getFilter().getPriority();
		} 
		
		if(wm1Priority < wm2Priority) {  
            return 1;  
        } else if(wm1Priority == wm2Priority) {  
            return 0;  
        } else {  
            return -1;  
        } 
	}
}

package com.devyok.web.common;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HandlerManager<T> {

	List<T> handlers = new ArrayList<T>();

	Comparator<T> comparator;

	boolean changed = false;

	public HandlerManager() {
	}

	public HandlerManager(Comparator<T> com) {
		this.comparator = com;
	}

	public void add(T handler) {
		if (handler == null) {
			throw new NullPointerException();
		}
		synchronized (this) {
			if (!handlers.contains(handler)) {
				handlers.add(handler);
				if (comparator != null) {
					Collections.sort(handlers, comparator);
				}
			}
		}
	}

	protected void clearChanged() {
		changed = false;
	}

	public int count() {
		return handlers.size();
	}

	public synchronized void delete(T handler) {
		handlers.remove(handler);
	}

	public synchronized void deleteAll() {
		handlers.clear();
	}

	public boolean hasChanged() {
		return changed;
	}

	public <E> void notifyHandlers(String methodName, Object data, HandlerFilter<E> filter , Class<?>... paramTypes) throws HandlerException{
		setChanged();
		int size = 0;
		Object[] arrays = null;
		synchronized (this) {
			if (hasChanged()) {
				clearChanged();
				size = handlers.size();
				arrays = new Object[size];
				handlers.toArray(arrays);
			}
		}
		if (arrays != null) {
			for (Object obj : arrays) {
				try {
					Method m = obj.getClass().getDeclaredMethod(methodName,
							data.getClass());
					m.setAccessible(true);
					m.invoke(obj, data);
					
					if(filter!=null){
						if(filter.filter((E) data)){
							break;
						}
					}
					
				}  catch (Exception e) {
					e.printStackTrace();
					throw new HandlerException(e);
				}
			}
		}
	}

	protected void setChanged() {
		changed = true;
	}

}

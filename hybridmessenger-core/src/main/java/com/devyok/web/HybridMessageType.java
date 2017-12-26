package com.devyok.web;

import com.devyok.web.exception.HybridMessageRuntimeException;

/**
 * 消息类型
 * @author wei.deng
 */
public class HybridMessageType {

	public static final HybridMessageType UNKONW = new HybridMessageType("unknow");
	/**
	 * native message,需要发送给h5端
	 */
	public static final HybridMessageType NATIVE = new HybridMessageType("native");
	/**
	 * html5 message,需要由native端来接收
	 */
	public static final HybridMessageType H5 = new HybridMessageType("h5");

	private String type;

	public HybridMessageType(String type){
		if(HybridMessageUtils.isEmpty(type)) {
			throw new HybridMessageRuntimeException("webmessage type must be not null");
		}
		this.type = type;
	}

	public static HybridMessageType create(String type){
		return new HybridMessageType(type);
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		HybridMessageType wmType = (HybridMessageType)obj;
		if(this.type.equals(wmType.getType())) {
			return true;
		}

		return super.equals(obj);
	}

	public String toString(){
		return type;
	}

}

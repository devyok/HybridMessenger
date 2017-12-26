package com.devyok.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
/**
 * 消息头
 * @author wei.deng
 */
public class HybridMessageHeader {

	/**
	 * 生成json object时需要的key定义
	 * @author wei.deng
	 *
	 */
	public interface JsonKey {
		public static final String KEY_HEADER = "header";
		public static final String KEY_ID = "messageId";
		public static final String KEY_TYPE = "type";
		public static final String KEY_FROM = "from";
		public static final String KEY_URI = "uri";
	}

	/**
	 * 消息ID，运行时唯一
	 */
	private String messageId;
	/**
	 * 消息类型
	 */
	private HybridMessageType messageType = HybridMessageType.UNKONW;
	/**
	 * 消息来自(H5|Native|其他)
	 */
	private String from;

	private WebUri webUri;

	private HybridMessageHeader(String messageId){
		this.messageId = messageId;
	}

	public String getMessageId() {
		return messageId;
	}

	public HybridMessageType getMessageType() {
		return messageType;
	}

	public String getFrom() {
		return from;
	}

	public WebUri getWebUri() {
		return webUri;
	}

	public static class Builder {
		private String messageId;
		private HybridMessageType messageType = HybridMessageType.UNKONW;
		private String from;
		private WebUri webUri;


		//{"header":{"messageId":"a13d3a2f-068e-b506-6e61-257bdb388c5f","uri":{"schema":"HybridMessage","host":"devyok","port":"80","path":"user","query":{"action":"login","uname":"dw","upwd":"123456"}},"from":"H5","type":""},"body":{"data":""}}"
		public static Builder create(String json){
			JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

			JsonObject header = jsonObject.getAsJsonObject(HybridMessageHeader.JsonKey.KEY_HEADER);

			String msgId = header.get(HybridMessageHeader.JsonKey.KEY_ID).getAsString();
			String from = header.get(HybridMessageHeader.JsonKey.KEY_FROM).getAsString();
			String type = header.get(HybridMessageHeader.JsonKey.KEY_TYPE).getAsString();

			JsonObject uri = header.get(HybridMessageHeader.JsonKey.KEY_URI).getAsJsonObject();

			WebUri weburi = WebUri.WebUriHelper.toWebUri(uri);

			return new Builder(msgId).setFrom(from).setMessageType(new HybridMessageType(type)).setWebUri(weburi);
		}

		public Builder(String messageId){
			this.messageId = messageId;
		}

		public Builder setMessageType(HybridMessageType webMessageType) {
			this.messageType = webMessageType;
			return this;
		}

		public Builder setFrom(String from){
			this.from = from;
			return this;
		}

		public Builder setWebUri(WebUri webUri){
			this.webUri = webUri;
			return this;
		}

		public HybridMessageHeader build(){
			HybridMessageHeader header = new HybridMessageHeader(this.messageId);
			header.messageType = this.messageType;
			header.webUri = this.webUri;
			header.from = this.from;
			return header;
		}

	}

	public JsonObject toJsonObject() {
		JsonObject header = new JsonObject();

		header.addProperty(JsonKey.KEY_ID, getMessageId());
		header.addProperty(JsonKey.KEY_TYPE, getMessageType().getType());
		header.addProperty(JsonKey.KEY_FROM, getFrom());
		header.addProperty(JsonKey.KEY_URI, getWebUri().toUriString());
		return header;
	}

}

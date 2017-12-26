package com.devyok.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * 消息体
 * @author wei.deng
 */
public class HybridMessageBody {

	/**
	 * 生成json object时需要的key定义
	 * @author wei.deng
	 */
	public interface JsonKey {
		public static final String KEY_BODY = "body";
		public static final String KEY_DATA = "data";
	}

	private String data;

	private HybridMessageBody(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

	public static class Builder {
		private String data;


		//{"header":{"messageId":"a13d3a2f-068e-b506-6e61-257bdb388c5f","uri":{"schema":"HybridMessage","host":"devyok","port":"80","path":"user","query":{"action":"login","uname":"dw","upwd":"123456"}},"from":"H5","type":""},"body":{"data":""}}"
		public static Builder create(String json){
			JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

			JsonObject body = jsonObject.getAsJsonObject(HybridMessageBody.JsonKey.KEY_BODY);

			String data = body.get(HybridMessageBody.JsonKey.KEY_DATA).getAsString();

			return new Builder().setData(data);
		}

		public Builder(){
		}

		public Builder setData(String data){
			this.data = data;
			return this;
		}

		public HybridMessageBody build(){
			HybridMessageBody body = new HybridMessageBody(this.data);
			return body;
		}

	}

	public JsonObject toJsonObject() {
		JsonObject body = new JsonObject();
		body.addProperty(JsonKey.KEY_DATA, getData());
		return body;
	}

}

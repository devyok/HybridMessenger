package com.devyok.web;

import com.devyok.web.exception.SendException;
import com.devyok.web.exception.TimeoutException;
import com.devyok.web.exception.HybridMessageException;
import com.devyok.web.exception.HybridMessageRuntimeException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.UUID;

/**
 * @author wei.deng
 */
public class HybridMessage {

	/**
	 * web message初始化异常
	 */
	public static final HybridMessage MESSAGE_INIT_ERROR = new HybridMessage(new HybridMessageHeader.Builder("message_init_error").build());
	/**
	 * 内部处理错误
	 */
	public static final HybridMessage INTERNAL_ERROR = new HybridMessage(new HybridMessageHeader.Builder("interna_error").build());

	/**
	 * 消息头
	 */
	private HybridMessageHeader header;

	/**
	 * 消息体，数据部分
	 */
	private HybridMessageBody body;


	/**
	 * 是否消息被中断
	 * true:将不会将此消息派发给其他接收者
	 */
	private boolean isAbort;
	/**
	 * 当接收到h5的消息时，通过此对象将native处理的结果返回给h5
	 */
	private HybridMessageSender replyTo = new HybridMessageSenderFactory.HybridMessageH5Replier();
	/**
	 * 这个web message的宿主环境
	 */
	private WebClient webClient;
	/**
	 * 当主动向h5发送消息时，h5的响应结果会通过这个接口返回给client
	 */
	private HybridMessageReceiver callback;

	private HybridMessage(HybridMessageHeader header){
		this(header,null,null);
	}

	private HybridMessage(HybridMessageHeader header, HybridMessageBody body, WebClient webClient){
		this.header = header;
		this.body = body;
		this.webClient = webClient;
	}

	public WebUri getUri() {
		return header.getWebUri();
	}

	public String getId(){
		return header.getMessageId();
	}

	public String getData(){
		return body.getData();
	}

	public WebClient getWebClient() {
		return this.webClient;
	}

	public HybridMessageType getType() {
		return header.getMessageType();
	}

	public HybridMessage reply(String data) throws SendException, TimeoutException, HybridMessageException {
		HybridMessage replyMessage = new HybridMessage.Builder(this)
				.setData(data)
				.build();
		return reply(replyMessage);
	}

	public HybridMessage reply(HybridMessage webMessage) throws SendException, TimeoutException, HybridMessageException {
		replyTo.send(webMessage);
		return this;
	}

	public HybridMessageReceiver getCallback(){
		return this.callback;
	}

	/**
	 * 是否是error类型消息,error类型消息,webview为null
	 * @return
	 */
	public boolean isError(){
		String id = getId();
		if(id == null || "".equals(id)) {
			return true;
		}
		return id.contains("_error");
	}
	/**
	 * 终端消息
	 */
	public void abort(){
		this.isAbort = true;
	}

	public boolean isAborted(){
		return isAbort;
	}

	public String toJson(){
		String json = new Gson().toJson(toJsonObject());
		return json;
	}

	public JsonObject toJsonObject(){
		JsonObject jsonObject = new JsonObject();
		jsonObject.add(HybridMessageHeader.JsonKey.KEY_HEADER, header.toJsonObject());
		jsonObject.add(HybridMessageBody.JsonKey.KEY_BODY, body.toJsonObject());
		return jsonObject;
	}

	public String toString(){

		StringBuffer buffer = new StringBuffer();

		buffer.append("{ header.id = ").append(header.getMessageId())
				.append(",header.type = ").append(header.getMessageType().getType())
				.append(",header.from = ").append(header.getFrom())
				.append(",header.uri = ").append(header.getWebUri().toUriString())
				.append(",body.data = ").append(body.getData())
				.append(",isAbort = ").append(isAbort)
				.append(",callback = ").append(callback)
				.append(" }");

		return buffer.toString();
	}

	public static class Builder {

		private String id;
		private WebUri uri;
		private String data;
		private WebClient webEnv;
		private String from;
		private HybridMessageType messageType;
		private HybridMessageReceiver callback;

		private HybridMessageHeader header;
		private HybridMessageBody body;

		public Builder(WebClient client){
			this(UUID.randomUUID().toString(),client);
		}

		public Builder(String id,WebClient client){
			this.id = id;
			this.webEnv = client;
		}

		public Builder(HybridMessage webMessage) {
			this.webEnv = webMessage.getWebClient();
			this.id = webMessage.getId();
			this.data = webMessage.body.getData();
			this.uri = webMessage.getUri();
			this.messageType = webMessage.getType();
		}

		//{"header":{"messageId":"a13d3a2f-068e-b506-6e61-257bdb388c5f","uri":{"schema":"HybridMessage","host":"devyok","port":"80","path":"user","query":{"action":"login","uname":"dw","upwd":"123456"}},"from":"H5","type":""},"body":{"data":""}}"
		public static Builder create(String json,WebClient client){

			Builder builder = new Builder(client);
			builder.webEnv = client;
			HybridMessageHeader header = HybridMessageHeader.Builder.create(json).build();
			builder.header = header;
			builder.id = header.getMessageId();
			builder.uri = header.getWebUri();

			HybridMessageBody body = HybridMessageBody.Builder.create(json).build();
			builder.body = body;

			return builder;
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}
		public Builder setUri(WebUri uri) {
			this.uri = uri;
			return this;
		}
		public Builder setData(String data) {
			this.data = data;
			return this;
		}
		public Builder setCallback(HybridMessageReceiver callback) {
			this.callback = callback;
			return this;
		}
		public Builder setFrom(String from){
			this.from = from;
			return this;
		}
		public Builder setMessageType(HybridMessageType messageType) {
			this.messageType = messageType;
			return this;
		}
		public HybridMessage build(){

			if(this.webEnv == null){
				throw new HybridMessageRuntimeException("can't build webmessage , webclient is null");
			}

			if(this.id == null){
				throw new HybridMessageRuntimeException("can't build webmessage , this message's id is null");
			}

			if(this.uri == null){
				throw new HybridMessageRuntimeException("can't build webmessage , this message's uri is null");
			}

			if(this.header!=null && this.body!=null){
				return buildInternal(header,body,this.callback);
			}

			HybridMessageHeader header = new HybridMessageHeader.Builder(this.id)
					.setMessageType(this.messageType)
					.setWebUri(this.uri)
					.setFrom(from)
					.build();

			HybridMessageBody body = new HybridMessageBody.Builder()
					.setData(this.data)
					.build();

			return buildInternal(header,body,this.callback);
		}

		HybridMessage buildInternal(HybridMessageHeader header, HybridMessageBody body, HybridMessageReceiver callback){
			HybridMessage message = new HybridMessage(header,body,webEnv);
			message.callback = callback;
			return message;
		}

	}

	public void recycle() {
		HmLogger.info("HybridMessage", "[HybridMessage native] recycle enter");
		isAbort = false;
		header = null;
		body = null;
		webClient = null;
		callback = null;
		replyTo = null;
	}

	public void destory(){
		HybridMessengerImpl.destoryWebMessage(this);
	}

}

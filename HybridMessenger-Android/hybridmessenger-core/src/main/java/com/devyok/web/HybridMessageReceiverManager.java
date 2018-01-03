package com.devyok.web;
/**
 * 
 * @author wei.deng
 *
 */
interface HybridMessageReceiverManager {
	/**
	 * 过滤器的优先级调整到最大,最大优先级的消息接受者只能被注册一个(第一个被主持的最大优先级接收者)
	 */
	public boolean registerHybridMessageReceiver(HybridMessageReceiver receiver);
	public boolean unregisterHybridMessageReceiver(HybridMessageReceiver receiver);
}

package com.devyok.web;

/**
 * @author wei.deng
 */
public interface WebClient{

	/**
	 * 执行Js代码
	 * @param script
	 */
	public void runJavaScript(String script);
	/**
	 * 获取当前Client的{@link Executor}
	 * @return
	 */
	public Executor getExecutor();
	/**
	 *
	 */
	public boolean startReceiveWebMessage();

	public void setUserAgent(String str);

}

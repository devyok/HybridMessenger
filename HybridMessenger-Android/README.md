# HybridMessenger-Android
在Android Hybrid开发中Native与H5通信框架


# 如何使用 #

[直接看实例代码](https://github.com/devyok/HybridMessenger/tree/master/hybridmessenger-sample)


## 引入 ##

[前端JS库引入](https://github.com/devyok/HybridMessenger-JavaScript/blob/master/README.md)

### 第一步 ###
在gradle中引入HybridMessenger
	
	dependencies {
	    compile 'com.devyok.web:hybridmessenger-android:0.0.1'
	}

### 第二步 ###
在Application#onCreate中配置HybridMessenger

	Configuration configuration = new Configuration.Builder();

    configuration.setDriver	(HybridMessageReceiveClient.class)
   				 .setThread(AndroidHybridMessageHandlerThread.class)
   				 .build();
			
	HybridMessenger.getMessenger().config(configuration);


## 接收 ##

### 第一步 ###


- 启动接收消息
	
	WebClientImpl.create(webView).startReceiveWebMessage();

### 第二步 ###
	
- 创建HybridMessageReceiver,接收指定Uri的消息


		HybridMessageReceiver userMessageReceiver = new HybridMessageReceiver() {
			@Override
			public boolean onReceive(final HybridMessage hybridMessage) {
				return true;
			}
	
			@Override
			public HybridMessageFilter getFilter() {
				return null;
			}
		};

HybridMessageReceiver共包含两个方法：

1.onReceive： 当web端发送一个消息后，所有消息都将进入这个方法，方法包含一个HybridMessage，可以从这个消息中根据双发定义的协议来获取数据。

2.getFilter： 告诉框架，你要接收指定哪些消息(根据URI来区分，所有这个URI对应的消息均会被此接收器接收)，同时告诉框架接收器的优先级。如果优先级为HybridMessageFilter.MAX_PRIORITY，表示所有消息将第一个被收到，如果注册多个高优先级的接收器，则按照注册顺序通知。getFilter方法不是强制要重写的，在不重写时将接受所有URI的消息。[示例代码](https://github.com/devyok/HybridMessenger/tree/master/hybridmessenger-sample)


- 注册接收器
	
		HybridMessenger.getMessenger().registerHybridMessageReceiver(userMessageReceiver);

## 响应 ##


- 当你获取到任意一个HybridMessage对象之后，就可以通过HybridMessage#reply方法来响应消息；


## 发送 ##

### 第一步 ###

- 创建

		HybridMessage webMessage = new HybridMessage.Builder(viewClient)
						.setData("json data")
						.setMessageType(HybridMessageType.NATIVE)
						.setUri(WebUris.USER_MODULE_URI)
						.setCallback(new HybridMessageReceiver() {
							@Override
							public boolean onReceive(HybridMessage hybridMessage) {

								return true;
							}
						}).build();


- 发送
	
		try {
			HybridMessenger.getMessenger().send(webMessage);
		} catch (SendException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (HybridMessageException e) {
			e.printStackTrace();
		}



## License ##
HybridMessenger is released under the [Apache 2.0 license](https://github.com/devyok/HybridMessenger/blob/master/LICENSE).

Copyright (C) 2017 DengWei.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
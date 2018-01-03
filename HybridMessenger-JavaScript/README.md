# HybridMessenger-JavaScript

Hybrid开发中Web向Native发送消息的支持库

# 如何使用 #

[直接看实例代码](https://github.com/devyok/HybridMessenger/tree/master/HybridMessenger-JavaScript/hybridmessenger-js-sample)

## 引入 ##

### 第一步 ###
将HybridMessenger-JavaScript下将hybrid_messenger.js复制到你对应的js库目录下，通过
script标签引入。

注意：如果要调试hybrid_messenger.js,请打开debug log；

	Logger.setDebugable(true);

## 接收 ##

### 第一步 ###
- 创建HybridMessageCallback,接收指定Uri的消息

		var weburi = WebUri.prototype.setBaseUriPath("user");
		
		function webMessageCallback(webMessage){
			
			//接收到的消息体
			var bodyData = webMessage.body.data;
			
			//响应
			webMessage.reply("200 ok",function(){
				//reply的callback
			});
		}
		

- 注册接收器

		//注册
		HybridMessenger.registerHybridMessageReceiver(weburi,webMessageCallback);


## 响应 ##

- 当你获取到任意一个Native端的HybridMessage对象之后，就可以通过HybridMessage#reply方法来响应消息，并同时仍然可以指定本次响应的callback。
	

		//响应native端,数据为: reply data
		webMessage.reply("reply data",function(hybridMessage){
			//handle
	
		});


## 发送 ##

通过调用HybridMessenger#sendHybridMessage方法发送消息到Native端

	var weburi = WebUri.prototype.createBy(baseUri);
	weburi.path = 'user';
    
	//要发送的数据
	var bodyData = "{value:'1000'}";
	
	//发送
	HybridMessenger.sendHybridMessage(weburi,bodyData,function(webMessage){
		
		var messageId = webMessage.header.messageId;
		var messageData = webMessage.body.data;
		
		webMessage.reply("200 ok",function(){
			//reply的callback
		});
	});

## License ##
HybridMessenger.js is released under the [Apache 2.0 license](https://github.com/devyok/HybridMessenger-JavaScript/blob/master/LICENSE).

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
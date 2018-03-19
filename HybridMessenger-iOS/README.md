[![license](http://img.shields.io/badge/license-Apache2.0-brightgreen.svg?style=flat)](https://github.com/devyok/HybridMessenger/blob/master/LICENSE)
[![Release Version](https://img.shields.io/badge/release-0.2.4-brightgreen.svg)](#)

# HybridMessenger-iOS
在iOS Hybrid开发中Native与H5通信框架


# 如何使用 #

[直接看实例代码](https://github.com/devyok/HybridMessenger/tree/master/HybridMessenger-iOS/HybridMessenger-Sample)


## 引入 ##

[前端JS库引入](https://github.com/devyok/HybridMessenger/blob/master/HybridMessenger-JavaScript/README.md)

### 第一步 ###
- **cocoapods安装**

	1. 在项目目录下新建podfile

			pod 'HybridMessenger', '~> 0.2.4'

	2. pod install命令安装

- **手动安装**

	将源代码[HybridMessenger](https://github.com/devyok/HybridMessenger/tree/master/HybridMessenger-iOS/HybridMessenger)直接copy到工程中即可。


### 第二步 ###
- **导入头文件**

		#import "HybridMessenger.h"

- **创建user模块的URL**

		NSURL *_userModulWebUri = [NSURL createWithModuleName:@"user"];	

- **根据user的url接收来自web端的消息**

		[HybridMessenger registerCallbck:_userModulWebUri :^(HMMessage *message) {
        
        	[HybridMessenger unregisterCallbck:_webUri];
        
        	NSLog(@"接收到的数据 = %@ " , message.body.data);
        
       		//[message replyWithData:@"reply to h5"];
        
    	}];

- **绑定webview**

		[HybridMessenger bindWebView:self.webview];

- **发送消息**

		HMMessage *message = [[HMMessage alloc] init];
    
    	HMMessageHeader *header = [[HMMessageHeader alloc] init];
    	header.messageType = MESSAGETYPE_NATIVE;
    	header.from = @"native";
    	header.webUrl = webUri;
    
    	HMMessageBody *body = [[HMMessageBody alloc] init];
    	body.data = @"iOS Native Hybrid Message";
    
    	message.header = header;
    	message.body = body;

    	[HybridMessenger sendHybridMessage:message];



## 设计 ##
**lib-core类图，见下图：**

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-iOS/lib_design_package.png)

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-iOS/lib_design_class_core.png)


**Native向Web发送消息，见下图：**

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-iOS/lib_design_seq_hybridmessenger_native_send.png)

## License ##
HybridMessenger is released under the [Apache 2.0 license](https://github.com/devyok/HybridMessenger/blob/master/LICENSE).

Copyright (C) 2017 DengWei.


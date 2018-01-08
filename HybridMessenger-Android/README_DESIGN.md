# HybridMessenger

进程间通信框架

## 设计 ##

## 整体架构，见下图 ##

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger.png)


**各组件依赖关系，见下图：**

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-Android/lib_design_component.png)

**包依赖，见下图：**

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-Android/lib_design_package.png)

**lib-core类图，见下图：**

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-Android/lib_design_class_hybridmessenger_core.png)

**lib-exception类图，见下图：**

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-Android/lib_design_class_hybridmessenger_exception.png)

**lib-android类图，见下图：**

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-Android/lib_design_class_hybridmessenger_android.png)


**Web向Native发送消息，见下图：**

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-Android/lib_design_seq_hybridmessenger_web_send.png)

**Native向Web发送消息，见下图：**

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-Android/lib_design_seq_hybridmessenger_native_send.png)

**Native响应Web消息，见下图：**

![](https://raw.githubusercontent.com/devyok/HybridMessenger/master/HybridMessenger-Android/lib_design_seq_hybridmessenger_native_reply.png)


[马上集成](https://github.com/devyok/HybridMessenger/blob/master/HybridMessenger-Android/README.md)

## License ##
ServiceManager is released under the [Apache 2.0 license](https://github.com/devyok/ServiceManager/blob/master/LICENSE).

Copyright (C) 2017 DengWei.

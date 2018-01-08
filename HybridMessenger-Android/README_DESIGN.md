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

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
(function (win) {

	var baseUri = createBaseUri('HybridMessage','devyok','80');
	var useragent = navigator.userAgent;

	//值的定义需要与native对应
	var MessageType = {
        TYPE_H5: 'h5',
        TYPE_NATIVE: 'native',
        TYPE_UNKNOW: 'unknow'
    }

    var Logger = {
        debugable: false,
        setDebugable: function(isDebugable){
            this.debugable = isDebugable;
        },
        isDebugable: function(){
            return this.isDebugable;
        },
        debug: function(msg){
            if(this.debugable){
                this.printLog(msg);
            }
        },
        info: function(msg) {
            this.printLog(msg);
        },
        printLog: function(msg){
            console.log(msg);
        }

    }
	
	var Utils = {
        S4: function(){
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        },

        guid: function(){
            return (this.S4()+this.S4()+"-"+this.S4()+"-"+this.S4()+"-"+this.S4()+"-"+this.S4()+this.S4()+this.S4());
        },

        isJson: function(jsonString){
			
            try {
                var result = JSON.parse(jsonString);

                if(typeof result == 'object') {
                    return true;
                }

            } catch(e){
            }

            return false;
        }

    }

    Logger.info("navigator.userAgent = " + navigator.userAgent);

    var NativeInfo = {
        packageName: '',
        version: '',
        defaultUserAgent:'',
        platform:'',
        extInfos:'',
        build: function(newUserAgentJson) {

            if(Utils.isJson(newUserAgentJson)){

			
                Logger.info("typeof = "+(typeof newUserAgentJson));

                var userAgentJson = JSON.parse(newUserAgentJson);

                this.packageName = userAgentJson.packageName;
                Logger.info("nativeInfo.packageName = " + this.packageName);

                this.version = userAgentJson.version;
                Logger.info("nativeInfo.version = " + this.version);

                this.defaultUserAgent = userAgentJson.defaultUserAgent;
                Logger.info("nativeInfo.defaultUserAgent = " + this.defaultUserAgent);

                this.platform = userAgentJson.platform;
                Logger.info("nativeInfo.platform = " + this.platform);

                this.extInfos = userAgentJson.extInfos;
                Logger.info("nativeInfo.extInfos = " + this.extInfos);

            } else {
				
                this.extInfos = newUserAgentJson;
                Logger.info("nativeInfo.extInfos = " + this.extInfos);
            }


        },
        isAndroid: function(){
             if(this.platform == 'Android'){
                return true;
             }
             return false;
        },
        isiOS: function(){
             if(this.platform == 'iOS'){
                return true;
             }
             return false;
        }

    }

    var HybridMessenger = {
    	callbackList: {},
        _send: function (hybridMessage,callback) {
        	this.callbackList[hybridMessage.header.messageId] = callback;

        	var callbackSize = Object.keys(this.callbackList).length;
        	Logger.debug('[HybridMessage H5] [FW] Messenger.sendMessage callbackList size = ' + callbackSize);

            var webMessageJsonString = JSON.stringify(hybridMessage);

            Logger.debug('[HybridMessage H5] [FW] Messenger.sendMessage webMessage json string = ' + webMessageJsonString);

            Logger.debug('[HybridMessage H5] [FW] NativeInfo.isAndroid = ' + NativeInfo.isAndroid());
 
            if(NativeInfo.isAndroid()){
                var result = window.prompt(webMessageJsonString, "");
                Logger.debug('[HybridMessage H5] [FW] prompt result = ' + result);
            } else if(NativeInfo.isiOS()) {
                var iframeCom = document.createElement('iframe');
                iframeCom.style.display = 'none';
                iframeCom.src = (baseUri.toUriString()+'action?sendMessage='+webMessageJsonString);
                document.documentElement.appendChild(iframeCom);
            } else {
                 var result = window.prompt(webMessageJsonString, "");
                 Logger.debug('[HybridMessage H5] [FW] prompt result = ' + result);
            }
 
            return true;
        },
        //1.当发送到native端的消息，native端已经处理完成之后，通过此入口进入
        //2.当native主动向web端发送消息，也在此接收消息
        //3.这两种情况，都均通过WMessage#type来进行区分
        onReceiveHybridMessage: function(hybridMessage) {
 
            Logger.info('[HybridMessage H5] [FW] receive hybrid message = ' + hybridMessage);
 
        	var jsonHybridMessage = JSON.parse(hybridMessage);
 
        	Logger.info('[HybridMessage H5] [FW] receive reponse hybrid message uri = ' + jsonHybridMessage.header.uri);

        	var responseHybridMessage = HybridMessage.prototype.createBy(jsonHybridMessage);

        	responseHybridMessage.printToConsole();

        	var callback = this.callbackList[jsonHybridMessage.header.messageId];

        	if(callback == undefined){
        		Logger.debug('[HybridMessage H5] [FW] receive reponse hybrid message, but callback not found, Look at what listen on this message uri ');

        		//此uri不包含query
        		var uri = responseHybridMessage.header.uri.getUri();

        		callback = this.callbackList[uri];

        		if(callback == undefined) {
        			Logger.debug('[HybridMessage H5] [FW] receive reponse hybrid message , do nothing');
        			return ;
        		}

        		callback(responseHybridMessage);

        	} else {
        		Logger.debug('[HybridMessage H5] [FW] receive reponse hybrid message callback typeof = ' + typeof(callback));
        		callback.call(this,responseHybridMessage);
        	}
        },
        registerHybridMessageReceiver: function(webUri,receiver){
        	var uri = webUri.getUri();

        	Logger.debug("[HybridMessage H5] [FW] registerWebMessageReceiver uri = " + uri);

        	this.callbackList[uri] = receiver;
        },
        sendHybridMessage: function(weburi,bodyData,callback){

        	if(typeof(weburi) == 'undefined') {
        		Logger.debug("weburi is undefined,please check");
        		return false;
        	}

        	Logger.debug("[HybridMessage H5] [FW] sendhybridMessage weburi typeof = " + typeof(weburi));

        	if(typeof(parameters) == 'string') {
        		parameters = eval("("+parameters+")");
        	}

        	var header = new Header(Utils.guid(),weburi);
        	var body = new Body();
        	body.data = bodyData;

        	var hybridMessage = HybridMessage.prototype.create(header,body);

        	return this._send(hybridMessage,callback);

        }
    };

	function Header(messageId,uri){
    	this.messageId = messageId;
        this.uri = uri;
        this.from = MessageType.TYPE_H5;
        this.type = MessageType.TYPE_H5;

        this.buildUri = function(){

        	var uriString = this.uri.toUriString();
        	var paramIndex = uriString.indexOf("?");
        	var idParams = '';
        	if(paramIndex == -1){
        		idParams = "?id="+this.messageId;
        	}

        	return this.uri.toUriString() + idParams;
        }

        this.printToConsole = function(){
    		Logger.info(" header.messageId = " + this.messageId);
    		Logger.info(" header.uri = " + this.uri.toUriString());
    		Logger.info(" header.from = " + this.from);
    		Logger.info(" header.type = " + this.type);

    		this.uri.printToConsole();
    	}

    }

    function Body(){
    	this.data = '';

    	this.printToConsole = function(){
    		Logger.info(" body.data = " + this.data);
    	}

    }

    function HybridMessage(){

        this.header = {};
        this.body = {};

        this.toString = function(){
    		return this.header.uri;
        }

        this.printToConsole = function(){
        	Logger.info("*****  hybridmessage infos start *****");

        	this.header.printToConsole();
        	this.body.printToConsole();

        	Logger.info("*****  hybridmessage infos end   *****");
        }

        this.reply = function(data,callback){
        	this.body.data = data;
        	HybridMessenger._send(this,callback);
        }

    }

    function WebUri(){

        this.scheme = '';
        this.host = '';
        this.port = '';
        this.path = '';
        this.query = {};

        this.getUri = function(){
        	return this.scheme+"://" + this.host + ":" + this.port +"/" + this.path;
        }

        this.toUriString = function(){
            var params = '';

            for(key in this.query){

                params += key+"=" + this.query[key] + "&";

                Logger.info("uri.query.key = "+ key +" , value = " + this.query[key]);
            }

            if(params!=''){
            	params = "?"+params;
            }

            return this.scheme+"://" + this.host + ":" + this.port +"/" + this.path + params;
        }

        this.printToConsole = function(){
    		Logger.info("[HybridMessage H5] webUri.scheme = " + this.scheme);
    		Logger.info("[HybridMessage H5] webUri.host = " + this.host);
    		Logger.info("[HybridMessage H5] webUri.port = " + this.port);
    		Logger.info("[HybridMessage H5] webUri.path = " + this.path);
    	}

    }

    function createBaseUri(scheme,host,port){
    	var base = new WebUri();
    	base.scheme = scheme;
    	base.host = host;
    	base.port = port;
    	return base;
    }

    WebUri.prototype.create = function(scheme,host,port){
    	var uri = new WebUri();
    	uri.scheme = scheme;
    	uri.host = host;
    	uri.port = port;
    	return uri;
    }

    WebUri.prototype.createBy = function(baseUri){
    	var uri = new WebUri();
    	uri.scheme = baseUri.scheme;
    	uri.host = baseUri.host;
    	uri.port = baseUri.port;
    	return uri;
    }

    WebUri.prototype.setBaseUriPath = function(path){
 
        var uri = WebUri.prototype.createBy(baseUri);
    	uri.path = path;
 
    	return uri;
    }

    WebUri.prototype.parse = function(uriString){

    	var webUri = new WebUri();

    	var schemeIndex = uriString.indexOf("://");
        var paramsIndex = uriString.indexOf("?");

        var scheme = uriString.substring(0,schemeIndex);
        webUri.scheme = scheme;

        var newUriString = uriString.substring(schemeIndex + 3,uriString.length);
        //查看是否存在端口
        var portIndex = newUriString.indexOf(":");

        //不存在端口
        if(portIndex == -1){

            var pathIndex = newUriString.indexOf("/");
            //不存在path
            if(pathIndex == -1){
                var host = newUriString.substring(0,newUriString.length);
                webUri.host = host;
                return;
            }

            var host = newUriString.substring(0,pathIndex);

            if(paramsIndex > 0){
                var newParamsIndex = newUriString.indexOf("?");
                var path = newUriString.substring(pathIndex + 1,newParamsIndex);
            } else {
                var path = newUriString.substring(pathIndex + 1,newUriString.length);
            }
            webUri.host = host;
            webUri.path = path;
        } else { //端口存在

            var pathIndex = newUriString.indexOf("/");
            var host = newUriString.substring(0,portIndex);

            if(pathIndex>0){
                var port = newUriString.substring(portIndex + 1,pathIndex);
                webUri.port = port;
                if(paramsIndex > 0){
                    var newParamsIndex = newUriString.indexOf("?");
                    var path = newUriString.substring(pathIndex + 1,newParamsIndex);
                } else {
                    var path = newUriString.substring(pathIndex + 1,newUriString.length);
                }

                webUri.path = path;

            } else {
                //如果没有path
                var port = newUriString.substring(portIndex + 1,newUriString.length);
            }
            webUri.host = host;
            webUri.port = port;
        }

        if(paramsIndex > 0){

            var queryString = uriString.substring(uriString.indexOf("?") + 1,uriString.length);
            var query = {};

            var queryMap = queryString.split("&");

            for(var i = 0;i < queryMap.length;i++){

                var queryKeyValue = queryMap[i].split("=");
                var key = queryKeyValue[0];
                var value = queryKeyValue[1];

                query[key] = value;

            }
            webUri.query = query;
        }

        return webUri;

    };

    HybridMessage.prototype.create = function(header,body){

        var hybridMessage = new HybridMessage();

        hybridMessage.header = header;
        hybridMessage.body = body;

        return hybridMessage;
    };

    HybridMessage.prototype.createBy = function(hybridMessageJson){

        var hybridMessage = new HybridMessage();

        var weburi = WebUri.prototype.parse(hybridMessageJson.header.uri);

    	var header = new Header(hybridMessageJson.header.messageId,weburi);
    	var body = new Body();
    	body.data = hybridMessageJson.body.data;

    	hybridMessage.header = header;
    	hybridMessage.body = body;

        return hybridMessage;

    };

    NativeInfo.build(useragent);
    win.NativeInfo = NativeInfo;
    win.HybridMessenger = HybridMessenger;
    win.Logger = Logger;
    win.WebUri = WebUri;
    win.baseUri = baseUri;
    win.MessageType = MessageType;


})(window);


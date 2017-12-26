(function (win) {

	var baseUri = createBaseUri('HybridMessage','devyok','80');

	//值的定义需要与native对应
	var MessageType = {
        TYPE_H5: 'h5',
        TYPE_NATIVE: 'native',
        TYPE_UNKNOW: 'unknow'
    }

    var HybridMessenger = {
    	callbackList: {},
        _send: function (hybridMessage,callback) {
        	this.callbackList[hybridMessage.header.messageId] = callback;

        	var callbackSize = Object.keys(this.callbackList).length;
        	console.log('[HybridMessage H5] [FW] Messenger.sendMessage callbackList size = ' + callbackSize);

            var webMessageJsonString = JSON.stringify(hybridMessage);

            console.log('[HybridMessage H5] [FW] Messenger.sendMessage webMessage json string = ' + webMessageJsonString);

            var result = window.prompt(webMessageJsonString, "");
            console.log('[HybridMessage H5] [FW] prompt result = ' + result);
            return true;
        },
        //1.当发送到native端的消息，native端已经处理完成之后，通过此入口进入
        //2.当native主动向web端发送消息，也在此接收消息
        //3.这两种情况，都均通过WMessage#type来进行区分
        onReceiveHybridMessage: function(hybridMessage) {
        	console.log('[HybridMessage H5] [FW] receive reponse hybrid message = ' + hybridMessage);
        	var jsonHybridMessage = JSON.parse(hybridMessage);

        	console.log('[HybridMessage H5] [FW] receive reponse hybrid message uri = ' + jsonHybridMessage.header.uri);

        	var responseHybridMessage = HybridMessage.prototype.createBy(jsonHybridMessage);

        	responseHybridMessage.printToConsole();

        	var callback = this.callbackList[jsonHybridMessage.header.messageId];

        	if(callback == undefined){
        		console.log('[HybridMessage H5] [FW] receive reponse hybrid message, but callback not found, Look at what listen on this message uri ');

        		//此uri不包含query
        		var uri = responseHybridMessage.header.uri.getUri();

        		callback = this.callbackList[uri];

        		if(callback == undefined) {
        			console.log('[HybridMessage H5] [FW] receive reponse hybrid message , do nothing');
        			return ;
        		}

        		callback(responseHybridMessage);

        	} else {
        		console.log('[HybridMessage H5] [FW] receive reponse hybrid message callback typeof = ' + typeof(callback));
        		callback.call(this,responseHybridMessage);
        	}
        },
        registerHybridMessageReceiver: function(webUri,receiver){
        	var uri = webUri.getUri();

        	console.log("[HybridMessage H5] [FW] registerWebMessageReceiver uri = " + uri);

        	this.callbackList[uri] = receiver;
        },
        sendHybridMessage: function(weburi,bodyData,callback){

        	if(typeof(weburi) == 'undefined') {
        		console.log("weburi is undefined,please check");
        		return false;
        	}

        	console.log("[HybridMessage H5] [FW] sendhybridMessage weburi typeof = " + typeof(weburi));

        	if(typeof(parameters) == 'string') {
        		parameters = eval("("+parameters+")");
        	}

        	var header = new Header(utils.guid(),weburi);
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
    		console.log(" header.messageId = " + this.messageId);
    		console.log(" header.uri = " + this.uri.toUriString());
    		console.log(" header.from = " + this.from);
    		console.log(" header.type = " + this.type);

    		this.uri.printToConsole();
    	}

    }

    function Body(){
    	this.data = '';

    	this.printToConsole = function(){
    		console.log(" body.data = " + this.data);
    	}

    }

    function HybridMessage(){

        this.header = {};
        this.body = {};

        this.toString = function(){
    		return this.header.uri;
        }

        this.printToConsole = function(){
        	console.log("*****  hybridmessage infos start *****");

        	this.header.printToConsole();
        	this.body.printToConsole();

        	console.log("*****  hybridmessage infos end   *****");
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

                console.log("uri.query.key = "+ key +" , value = " + this.query[key]);
            }

            if(params!=''){
            	params = "?"+params;
            }

            return this.scheme+"://" + this.host + ":" + this.port +"/" + this.path + params;
        }

        this.printToConsole = function(){
    		console.log("[HybridMessage H5] webUri.scheme = " + this.scheme);
    		console.log("[HybridMessage H5] webUri.host = " + this.host);
    		console.log("[HybridMessage H5] webUri.port = " + this.port);
    		console.log("[HybridMessage H5] webUri.path = " + this.path);
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
    	baseUri.path = path;
    	return baseUri;
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

    win.HybridMessenger = HybridMessenger;
    win.WebUri = WebUri;
    win.baseUri = baseUri;
    win.MessageType = MessageType;

    var utils = {
        S4: function(){
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        },

        guid: function(){
            return (this.S4()+this.S4()+"-"+this.S4()+"-"+this.S4()+"-"+this.S4()+"-"+this.S4()+this.S4()+this.S4());
        }
    }

})(window);


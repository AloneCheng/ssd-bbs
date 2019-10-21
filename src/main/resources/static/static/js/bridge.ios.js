(function (window) {
	function AppBridge(params){
		this.pageParam = params.pageParam || {};
		this.appVersion = params.appVersion;
		this.systemType = params.systemType;
		this.systemVersion = params.systemVersion;
		this.deviceId = params.deviceId;
		this.winName = params.winName;
		this.safeArea = params.safeArea || {top: 0, bottom: 0};
		this.safeArea.top = this.safeArea.top - 0;
		this.safeArea.bottom = this.safeArea.bottom - 0;
	}

	AppBridge.prototype.setStorage = function (key, value) { //{key: String, value: JSON}
	if(key){
		dsBridge.call("setStorage", JSON.stringify({
			key: key,
			value: value
		}));
	}
	}
	AppBridge.prototype.getStorage = function (key) {
		if(key){
			return dsBridge.call("getStorage", key);
		}
		return null;
	}
	AppBridge.prototype.removeStorage = function (key) {
		dsBridge.call("removeStorage", key);
	}
	AppBridge.prototype.clearStorage = function () {
	}
	AppBridge.prototype.openWin = function (options) {
		var params = {
			name: options.name,
			url: options.url,
			pageParam: JSON.stringify(options.pageParam || {}),
			reload: false,
			bounces: false,
			toolbar: {
				visible: '1'
			}
		}
		if(options.toolbar){
			params.toolbar = {
				visible: options.toolbar.visible ? '1' : '0'
			};
			if(options.toolbar.title){
				params.toolbar.title = options.toolbar.title;
			}
			if(options.toolbar.background){
				params.toolbar.background =  { //可选
					start: options.toolbar.background.start, //#3F75F3,
					end: options.toolbar.background.end, // #72AEFF,
				}
			}
		}
		dsBridge.call("openWin", params);
	}
	AppBridge.prototype.setToolbar = function(options){

		var params = {}
		if(options.title){
			params.title = options.title;
		}
		if(options.background){
			params.background =  { //可选
				start: options.background.start, //#3F75F3,
				end: options.background.end, // #72AEFF,
			}
		}
		if(options.rightButton){
			params.rightButton =  { //可选
				text: options.rightButton.text, //'保存' (可选， text/icon 二选一)
				icon:  options.rightButton.icon, //'more' (可选)
				color: options.rightButton.color, //'#ffffff' (text颜色)
				click: '__toolbarRightButtonClick' // function(){}
			}
			if(options.rightButton.onClick){
				dsBridge.register('__toolbarRightButtonClick', function(){
					window.document.activeElement.blur();
					options.rightButton.onClick();
				});
			}
		}
		dsBridge.call("setToolbar", params)
	}
	AppBridge.prototype.closeWin = function () {
		dsBridge.call("closeWin", "");
	}
	AppBridge.prototype.closeToWin = function (name) {
	}
	AppBridge.prototype.sendEvent = function (name, data) { //name: string, data: JSON Object
		dsBridge.call("sendEvent", {
			name: name,
			data: JSON.stringify(data || {})
		});
	}
	AppBridge.prototype.addEventListener = function (name, listener) { //name: string, listener: function
		this.__EventListener = this.__EventListener || {};
		this.__EventListener[name] = this.__EventListener[name] || 0;
		this.__EventListener[name] = this.__EventListener[name]  + 1;

		var listenerName = '_event_' + name + '_'+ this.__EventListener[name];
		listener['_event_' + name] = this.__EventListener[name];

		dsBridge.register(listenerName, function(data){
			listener(JSON.parse(data));
		});
		dsBridge.call("addEventListener", {
			name: name, // String
			listener: listenerName // String
		});
	}
	AppBridge.prototype.removeEventListener = function (name, listener) { //name: string, listener: function
		if(listener['_event_' + name]){
			var listenerName = '_event_' + name + '_' + listener['_event_' + name];
			listener['_event_' + name] = 0;

			dsBridge.call("removeEventListener", {
				name: name, // String
				listener: listenerName // String
			})
		}
	}
	AppBridge.prototype.goHome = function () {
		dsBridge.call('goHome', '');
	}
	AppBridge.prototype.goMallHome = function () {
		dsBridge.call('goMallHome', '');
	}
	AppBridge.prototype.login = function () {
		dsBridge.call('login', '');
	}
	AppBridge.prototype.getPicture = function(options, callback){
		this.getPicture.index = this.getPicture.index || 0;
		this.getPicture.index++;
		var listenerName = 'getPictureCallback_' + this.getPicture.index;
		dsBridge.register(listenerName, function(base64Data){
			callback({base64Data: base64Data});
		});
		options.listener = listenerName;
		dsBridge.call("getPicture", options);
	},
	AppBridge.prototype.takePhoto = function(options, callback){
		this.takePhoto.index = this.takePhoto.index || 0;
		this.takePhoto.index++;
		var listenerName = 'takePhotoCallback_' + this.takePhoto.index;
		dsBridge.register(listenerName, function(base64Data){
			callback({base64Data: base64Data});
		});
		options.listener = listenerName;
		dsBridge.call("takePhoto", options);
	}
	AppBridge.prototype.onShow = function(callback){
		dsBridge.register('onShow', function(){
			callback();
		});
	}
	AppBridge.prototype.joinRoom = function(options){
		dsBridge.call('joinRoom', options);
	}
	AppBridge.prototype.startChat = function(options) {
		dsBridge.call('startChat', options);
	}
	AppBridge.prototype.phoneCall = function(options){
		dsBridge.call('phoneCall', options)
	}
	AppBridge.prototype.showFloatButton = function(options){
		dsBridge.call('showFloatButton', options);
	}
	AppBridge.prototype.hideFloatButton = function(){
		dsBridge.call('hideFloatButton', {});
	}
	AppBridge.prototype.showMap = function(options){
		dsBridge.call('showMap', options)
	}
	AppBridge.prototype.weixinPay = function(options, callback){
		this.weixinPay.index = this.weixinPay.index || 0;
		this.weixinPay.index++;
		var listenerName = 'weixinPayCallback_' + this.weixinPay.index;
		dsBridge.register(listenerName, function(data){
			var obj = JSON.parse(data);
			obj.errCode = obj.errCode - 0; 
			callback(obj);
		});
		options.listener = listenerName;
		dsBridge.call('weixinPay', options, callback)
	}
	AppBridge.prototype.weixinShareWebPage = function(options){
		dsBridge.call('weixinShareWebPage', options)
	},
	AppBridge.prototype.aliPay = function(options, callback){
		this.aliPay.index = this.aliPay.index || 0;
		this.aliPay.index++;
		var listenerName = 'aliPayCallback_' + this.aliPay.index;
		dsBridge.register(listenerName, function(data){
			var obj = JSON.parse(data);
			callback(obj);
		});
		options.listener = listenerName;
		dsBridge.call('aliPay', options)
	}
	AppBridge.prototype.scanDev = function(options, onAdd){
		this.scanDev.index = this.scanDev.index || 0;
		this.scanDev.index++;
		var listenerName = 'scanDevCallback_' + this.scanDev.index;
		dsBridge.register(listenerName, function(data){
			if(typeof onAdd === 'function'){
				onAdd(data);
			}
		});
		options.listener = listenerName;

		dsBridge.call('scanDev', options)
	}
	AppBridge.prototype.connectDev = function(options, onStatusChange){
		this.connectDev.index = this.connectDev.index || 0;
		this.connectDev.index++;
		var listenerName = 'connectDevCallback_' + this.connectDev.index;
		dsBridge.register(listenerName, function(data){
			if(typeof onStatusChange === 'function'){
				onStatusChange(data);
			}
		});
		options.listener = listenerName;
		dsBridge.call('connectDev', options)
	}
	AppBridge.prototype.startDev = function(options, onResult){
		this.startDev.index = this.startDev.index || 0;
		this.startDev.index++;
		var listenerName = 'startDevCallback_' + this.startDev.index;
		dsBridge.register(listenerName, function(data){
			if(typeof onResult === 'function'){
				onResult(data);
			}
		});
		options.listener = listenerName;
		dsBridge.call('startDev', options)
	}
	AppBridge.prototype.request = function(config, callback){
		var options = {
			url: config.url,
			method: (config.method || 'get').toLocaleLowerCase(),
			headers: config.headers || {},
			body: ""
		}
		if(!/^http([s]?)\:/i.test(config.url)){
			if(config.baseURL){
				options.url = config.baseURL.replace(/(\/)$/, '') + '/' + config.url.replace(/^(\/)/, '')
			}
		}
		if(options.method === 'post' && config.data){
			options.body = JSON.stringify(config.data);
		}
		if(config.params){
			var arr = [];
			for(var k in config.params){
				arr.push(k + '=' + encodeURIComponent(config.params[k]));
			}
			if(arr.length){
				if(/[\&\?]$/.test(options.url)){
					options.url = options.url + arr.join('&');
				}else if(/\?/.test(options.url)){
					options.url = options.url + '&' + arr.join('&');
				}else{
					options.url = options.url + '?' + arr.join('&');
				}
			}
		}

		this.request.index = this.request.index || 0;
		this.request.index++;
		var listenerName = 'requestCallback_' + this.request.index;
		dsBridge.register(listenerName, function(res){
			if(typeof callback === 'function'){
				if(res.data){
					res.data = JSON.parse(res.data);
				}
				callback(res);
			}
		});
		options.listener = listenerName;
		dsBridge.call('request', options)
	}
	
	AppBridge.prototype.showNavBar = function(options){
		dsBridge.call('showNavBar', options);
	}
	window.dsBridgeReady = function(params){
		window.bridge = new AppBridge(params);
		if(typeof window.onBridgeReady === 'function'){
			window.onBridgeReady(window.bridge);
			
			window.bodyScrollTop = 0;
			window.document.body.addEventListener('focusin', () => {
				window.bodyScrollTop = Math.max(0, window.document.body.scrollTop - 0);
			});
			window.document.body.addEventListener('focusout', () => {
				//软键盘收起的事件处理
				window.document.body.scrollTop = window.bodyScrollTop;
			});
		}
	}
})(window)
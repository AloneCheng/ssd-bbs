(function (window) {
	function AppBridge(params){
		this.pageParam = (params.pageParam && JSON.parse(params.pageParam)) || {};
		this.appVersion = params.appVersion;
		this.systemType = params.systemType;
		this.systemVersion = params.systemVersion;
		this.deviceId = params.deviceId;
		this.winName = params.winName;
		this.safeArea = params.safeArea;
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
			var data = dsBridge.call("getStorage", key);
			if(key === 'user'){
				return data ? JSON.parse(data) : null;
			}
			return data;
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
			pageParam: JSON.stringify(options.pageParam),
			reload: false,
			bounces: false,
			toolbar: {
				visible: true
			}
		}
		if(options.toolbar){
			params.toolbar = {
				visible: !!options.toolbar.visible
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
			data: JSON.stringify(data)
		});
	}
	AppBridge.prototype.addEventListener = function (name, listener) { //name: string, listner: function
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
	AppBridge.prototype.removeEventListener = function (name, listener) { //name: string, listner: function
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
		dsBridge.call('goHome');
	}
	AppBridge.prototype.goMallHome = function () {
		dsBridge.call('goMallHome');
	}
	AppBridge.prototype.login = function () {
		dsBridge.call('login');
	}
	AppBridge.prototype.getPicture = function(options, callback){
		dsBridge.call('getPicture', options, function(data){
			callback({
				base64Data: data
			})
		});
	},
	AppBridge.prototype.takePhoto = function(options, callback){
		dsBridge.call('takePhoto', options, function(data){
			callback({
				base64Data: data
			})
		});
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
		dsBridge.call('weixinPay', options, function(data){
			callback(JSON.parse(data));
		})
	}
	AppBridge.prototype.weixinShareWebPage = function(options){
		dsBridge.call('weixinShareWebPage', options)
	}
	AppBridge.prototype.aliPay = function(options, callback){
		dsBridge.call('aliPay', options, function(data){
			callback(JSON.parse(data));
		})
	}
	AppBridge.prototype.scanDev = function(options, onAdd){
		dsBridge.call('scanDev', options, function(data){
			if(typeof onAdd === 'function'){
				onAdd(JSON.parse(data));
			}
		})
	}
	AppBridge.prototype.connectDev = function(options, onStatusChange){
		dsBridge.call('connectDev', options, function(data){
			if(typeof onStatusChange === 'function'){
				onStatusChange(JSON.parse(data));
			}
		})
	}
	AppBridge.prototype.startDev = function(options, onResult){
		dsBridge.call('startDev', options, function(data){
			if(typeof onResult === 'function'){
				onResult(JSON.parse(data));
			}
		})
	}
	
	AppBridge.prototype.showNavBar = function(options){
		dsBridge.call('showNavBar', options);
	}
	window.dsBridgeReady = function(params){
		window.bridge = new AppBridge(params);
		if(typeof window.onBridgeReady === 'function'){
			window.onBridgeReady(window.bridge);
		}
	}
})(window)
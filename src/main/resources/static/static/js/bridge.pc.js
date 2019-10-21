(function (window) {
	function createBridge(){
		var bridge = { //fake
			pageParam: {}, //openWin传入的pageParam参数
			appVersion: '0.0.1',
			systemType: 'win',
			systemVersion: '',
			deviceId: '', 
			winName: '', //openWin传入的name参数
			safeArea: {
					top: 20,　// 状态栏高度(px)
					bottom: /(iPhone)/i.test(window.navigator.userAgent) && window.screen.height === 812 ? 20 : 0 // iPhoneX的底部虚拟横条键高度(px)，避免与页面内容重合
			},
			setStorage: function (key, value) { //{key: String, value: JSON}
				if(key){
					localStorage.setItem(key, JSON.stringify(value));
				}
			},
			getStorage: function (key) {
				if(key){
					var data = localStorage.getItem(key);
					if(data){
						try{
							return JSON.parse(data);
						}catch(e){
							return data;
						}
					}
					return data;
				}
			},
			removeStorage: function (key) {
				localStorage.removeItem(key);
			},
			clearStorage: function (key) {
				localStorage.clear();
			},
			openWin: function (options) {
				options = _.assign({
					name: 'win_' + new Date().valueOf(), //winName
					url: '',
					pageParam: null, //JSON Object
					reload: false,
					bounces: false //页面是否弹动
				}, options)
				var url = options.url;
				var query = [];
				if(options.toolbar && options.toolbar.title){
					query.push('__title=' + window.encodeURIComponent(options.toolbar.title));
				}
				if(options.pageParam){
					query.push('__pageParam=' + window.encodeURIComponent(JSON.stringify(options.pageParam)));
				}
				if(/[\?\&]$/.test(url)){
					url += query.join('&')
				}
				else if(/[\?]/.test(url)){
					url += "&" + query.join('&');
				}else{
					url += '?'  + query.join('&');
				}
				window.open(url, options.name, null);
			},
			setToolbar: function(options){
				if(options.title){
					window.document.title = options.title;
				}
				if(options.rightButton){
					var div = document.createElement('DIV');
					if(options.rightButton.text){
						div.innerHTML  = options.rightButton.text;
					}else if(options.rightButton.icon){
						div.innerHTML = '<img src="' + options.rightButton.icon + '" style="width: 22px; height: 22px;"/>';
					}
					if(div.innerHTML){
						div.style.position = "fixed";
						div.style.top = 0;
						div.style.right = 0;
						div.style.zIndex = 999;
						div.style.background = "rgba(0, 0, 0, 0.5)";
						div.style.padding = "5px 10px";
						div.style.color = "#fff";
						div.addEventListener('click', options.rightButton.onClick, { passive: true });
						document.body.appendChild(div);
					}
				}
			},
			closeWin: function(){
				window.close();
			},
			closeToWin: function(name){
				var win = window;
				var arr = [];
				while(win){
					if(win.name == name){
						break;
					}
					arr.push[win];
					if(win.opener && win.opener != win){
						win = win.opener
					}else{
						win = null;
					}
				}
				for(var i=arr.length - 1; i >= 0; i--){
					arr[i].close();
				}
			},
			sendEvent: function (name, data) {
				var win = window;
				while(win){
					win.__raiseEvent(name, JSON.stringify(data));
					if(win.opener && win.opener != win){
						win = win.opener;
					}else{
						win = null;
					}
				}
			},
			addEventListener: function (name, listener, options) {
				window.addEventListener(name, function(evt){
					listener(typeof evt.detail === 'undefined' ? null : evt.detail);
				});
			},
			removeEventListener: function (name, listener) {
				window.removeEventListener(name, listener);
			},
			login: function(){
				var url = window.location.href;
				window.location.href = './login.html?from=' + window.encodeURIComponent(window.location.href);
			},
			goHome: function(){
				window.close();
			},
			goMallHome: function(){
				window.close();
			},
			getPicture: function(options, callback){
				options = {
					allowEdit: true, //是否截取
					targetWidth: 100, //图片截取宽度，可选
					targetHeight: 100, //图片截取高度，可选
				};
				setTimeout(function(){
					callback({base64Data: 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAEsASwDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+iiigAooooAKKKKACiiigAooooAQ9DXPalcFJq6I9DXKa6Sko96TAmtNQCuNxxW/DOsyBlOa4JZWBrSsr50AAbFIDsKBVe2uRLGvrVjNUAtFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFNZtoz2p1VbyTy4z70ANlvVTpTYb9WOGIFZcj/ACEmst7wxSdTSuM7RWDDIp1c3p+sgnYxz9TXQRSrKgINMRJRRRQAHpXO+IoSUSQD610VZ+qwiaxkz1AoYHD7sGnoxDZBqvuwzKexqWIgmpGb2nakVwpPSujt7gTpkHmuEDhGBHFbumXoVlG7rTQHSiimIwYZBzT6YgooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACqOo8xir1U9QXMQPoaT2HHcxXO0EGsy8h3KStac4JUkVUUiQMj/Ke1ZxnqayhoYBmeA5XrXQaLrbFlSRuKxr+0dWJxxVCORopAy5BFaGZ6qjrIgZTkGn1ymi60ABHI3HvXUqwZQwPBpiHVHIgkjZD0IqTtSUCPPNUtWtL5xjCk8VWBI5FdV4ltA0IlC8jvXJIe1IY8sT3qSCdopAQTUdML4PSkVY7XSNREihHJya2xXn2n3zRMPXPrXc2swmgVu/emiWWKKKKYgooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKr3Kb4GHtVimkZBFD2Gmc28gyV71XkQEbscip72MRXhPTmo87lrklozsjqio0qS5jk5YVh3cPlSn0rUu90cm5RUWEuMbuGrSFQynAy43aNgynkV2eh62JkEUx57Vx1xEYpSMUkM7QuGU4xWidzA9VByARS1z+hayLtBE5+YVv5qwKuo24uLKRO+DivOCGiuJI26g16iQCMHoa8+8UWhs9QMwGEbnNJgU80xhk1W+1qQMMKaboetSxltCVkBB7122h3IaIRnqa4KCbfk5re0i+ZbmNRTQmd1RTVYHpS5HrVCFoozRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUlLRQBjaxb7gJFHNZiH5RXTzxCWJlPcVzLRmKV0PY1jUjrc6KUug2SESoc44rHeNoZOfXitocAiopYVkTn86xN7XMyaJZhu71Qlh2qTWijIc7Scg4xUF0BsNbwZz1I2My3vJLS53xtjBr0jRb/AO32KyFgWxzXllwxRia0/DHiRdOvUglI8mQ4ye1aIyPUq53xfbedo8koBzGMnHpVq+8R2NiQGfcSM/LXPav4ziltWhtkB3gg7qGxHnjXyg8HIqaF55toVTljgVGLeJZmkA5JzitaydVIIHIqBlq0s5YUIkK5J6Vbj/dMGVsMDk0xpy446nvVckg8mmhHVDxRKIFUKu8dc1Lb+KJS+JUQj2rkPMOMZpRIynhiKq4z0m21aC4x2NX1YMMg15lb38kLDn8a6fTNcO1VcDHrRcR1FFRxSrKgZTwakpiCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKDRQaAErI1K3Cv5o79a16iniWWMoamSuiouzOaf5T1pWAMR+lR6hE8LHdnHY0QNviANc8lY64sxLtGt7gyr908UTHMAY9xVrUkkXkpmPv7VlTTK0LorZXrVQepFUw9QmwSB3rNjhYuDk8VYbM9ztHrWlDaADkVv0OcpSTTFVCk575qP5yctnNbAtl9KPsyk9KkGZHPSrNvkLVxrYL1ApqwgHNMRNGTt607OaaOOKXBpAKBS9KUnNRtTAfmrdvIVx82KoZ5qwJF29aAOr0nVjGQjtxXURSrKgZT1ry+G42Sda63Q9TD/JI30pjsdPS0xTkU8UyQooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACkIzS0UAUb6yW6hKnr24rE+ztbMUb8OK6iqt3aLOnA+bsaznG5rCpY5y4i82Jl9q5C9t3g3quQDXcSoICUk6+tczrWN4x0IqYxsVOakrGHZ2gQGRuSaug01ZAIwvpSA1ojIkzRk9QKQVIvSiwELjdzTcVMRTQo5oAiYgVGZTmll4aoTSEPWbPBFS9ap5qQSsODTAlkYLUPn80jtvOarnqaALySg96uW140EqsCeKxlJBq2h/dk55phc9S0a9F5Zo4PPfmtOvP/AAtrEdtJ5Mp4Jru4rmKdco4NCAmopBS0xBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAGKTFLRQBTvbJLqJhjD9jXB6vHNaMY7mP/dbFej4rnfFkay2QQqPXNA0zzuIkuxPSrKiogoRyKmV1FSMlRd1OI2mo1bDZqVnGMmk2MhkkxUDSnFJLMu7rUTOrDii4DTIc00vnqaa2R2qMkk9KCbEoOelBIpsfAoPWgdhwNNbFLSMOKAsM71ZTp7VVwc1chXK9KYWC3YrdcV1VjfyRH7xrmY4W87cMYragouFjsLDVfMwrmtcEEAjpXCwSsjDBro9MvS+I2NO4rGzRRRTEFFFFABRRRQAlFBqKSZYwSxwBzQxpX2JaKpW2pQXUhSNskVcz7UkxyTjox1FFFMkKKKKACiiigAooooAKwPE//HqDW9WB4pkUWQj/AImPBoGjzuUkSGo0JDVZmi5zjrUXlYpAyeI78+1JLkcUts0asQ5qW8urC3UGVgvuamxRkTKynPao1c5xRca3pjOqR3SNk4xzUnlqQHQ5U96LAWI1BWlMOe1SQLnGelTkY7UAUDDzxSeV61PJ8rZAqB58UAKIwDgGnBAar+fzxTkmJIHNAFhYQelWETAxikgHGadKxHQ80ATxbc4q7D0rFDuDnJzV+2usjDcGgDTQZYVp6exW5Xmsu2cO1atpgSimgOqjO5Afan1DAf3Y+lSiqJFooooEFFFFAEch2qT6CuTv9Tml82IgBScCuuYZBrndU0kyMZIBg9SPWomm9jrwsoKXvGNpLNBfx7GOScV3SnKg1xdlbyR6hGHQjmuzUEKBUxugxTjKXuklFFFanIFFFFABRRRQAUUUUAJXJa4zT3u3HypxXWMQoJJwBXJ37eZcyMozzQNGLLbBh93FUHgKthlyK2GqBotz7s0gOc1C0uhGXtwTjtmuHn0zVPE2rT6a1ytsbeMyMHPUYz69a9gghXfk9iK8x+K+i3en6nFq9q0kdtcqUZ42xtOAOcfjSHc8ouEa0u5IhMzMjkZz6Gus8IeMrixYWN9IZbdzgbuSK4w2tx9oIwXzzuznNSwQyG4CD5X7c0MZ9I2DRXFurDHPSp57cAcCuX8H6gbrSI3yS6fKw9K7ENvjGaQGW0ZIIIqjPblmAArYndIlOeapCdJHxtxQBUhssnkVZjs3UnK8Vo21sHPBq20BAxii6GlcpQ2+RjHJpk1oyN8y4q/DvimDFcgetXptk0JZgAO1K5Tg0rnOeUQcAUphJGRwa0REo7U4RLjAFMgo2czRzbSa6WyO51Oa5qZCkmRxzWpp9z+8QfrTQHc2/wBwfSpqit8GBCDn5RUopki0UUUxBSUtFADTSbacaXtQBEYUZgxUZHepKMUUDFooooEFFFFABRRRQAUhIVck4FHes7VrnyoCgPLcUAU9R1TLeTEeO5FVNhEJYDkjHNZM0xjYsDk1pW12s1oFON2KlsZnPhSRmm7lPao7tihNVBcc1NwNBXwanmgs9Ts5LHUIkmt3HKsM7fcVnxSljjFXI14yBzRcZw1/8ILKa8D6ZqXkQMfmVhkj9a1tG+Fvh3Qphc3spv58cblwAf1rpRxSMSwxnNFxlK10fTdPMwsoQiSnLCpZVjQYDAH0qwB6Csq5f/SG56GmIq3K7t2TVQYjYYqzO/ynNZzMfM68UDN/Trna4BxW/DCLhGKsobHGa5G3OADmtSG6kjHDcEVDLTsZPiK51vT43A8qSIHJZMZA/CtDS7+S90yFnwOMfXmklCzF/MOVkUqc+9Znhu42Qy2BGTEx2/QnP9alHRJpwOhUA4qRF5FV0bA5NPe5CxnGM9q1WxzNFfUTGrBUILd6W1jKopziqqwtJKHYmtAEKoApok6/RpzLbhSckCtSuV0K6KzhCeCcV1VNEi0UmaM0xC0lFNZwozUymoq7GkOoqBLqNiQWGfrUodWOAQamNWEtmNxa3H5pM0UVpckWiiigAooooAKKKKAEPFcvqlwzzNnpXTvwpNclfHdOx96TAxpSWfnpUdvdGGcID17VLKPnqnPCd3mKfmAqWUjVu4/Nj3DrWOYyr81o2Nx50Gxj8y8Ulxa9GGeaQEMDhSM1eEny8GstgUODUiStnFAIvhiTzTwaYo4FOPtQO4rEmJtvWsKYkSHJ5rej2rnccCsLVYmikL4OD0xTERsvmLwOKrfZ/wB50qxa3CsoVsA1eht/Nk+XFIoqLGQQqjtVvYyqMjtWlb2PI3AVPcS6fbqPOnVWH8PrSYI52+mEFm7nPA4A9a4bT9RubPVWlYn5jnA7iur1Kf7bO/lA7M8VmnTQicqDxzWbg+ho5aWNyPWortB5IGe4NTRq2AZCcmuXisWV/wBxIUYdu1Xo7y+sCFuEOP51n7SUNGZXOkRwQBwKkDVk22pxSgZBU+9akRWYZRga1hWi9mBpaZIEnB754rsoJPMjB745rhoVMbA55rrtMk3wA5ycVvFqWzJNCkPSlzUbttBJqKk+WNwRVmvRGSMHP0rPur+RlAXg96LyQGXj86pSNmviMbmVV1HBPQ76VJbsQyuWBB5zmtSC7IGe5AzWRmlEjDpxWdHHzorc3lSUjp4bgS9DyOtTcd653T5mW5Azw3WugVhivrcBjViKd2edVhyuxJRRRXpGIUUUUAFFFJQA2Q/I30rlLsZlb610d86pAcmucnIZjikxmbLH81RtFkHirriomqWCM1R9ncsB1q5HeJKmGPTpTZYgwqk9qwbKikBYkjV2zmmpGBJUYaVBzUUs7gZU5NA7mkrbSKFmG1j71hteyjqKYb2TFNAbDXS52mlluo5YsECude4kZqYpmZ8g8UDL0ltGr7g1SrqEduuFbLVRe2aUgsTUqWYQZC0MB8up3NwNoYqPUU0WxfDOdxxUypjoKnRTgUguQRwKi4AxU4t0I5FWkPy4oK0wuUZbBHHy8GmRGSNtkoDL71o4qU2q3Cc9aTSe47mY2m2lzyuVPbHrUY0y8hG+Eh1BxweauIj204U/d7VsRJlQy8VlOjGQjnI9QuoH2zA8djXS6V4qtbdQlwrJwOe1QzW8c4IlTcakt/CNvdwl45HjkxwO1YexqR1gI6a11qxuseVcKxPam6hOxQiJga4a+8M6pZMHiQSAH7wq3GdRUL5x2kjmvMzDF1YQ5JI2oxTkapc5560mcioFbjn8aUvxXyE7yldnpqKQFsNQJRTOtHlDrmlNlJE8chDhl6+tdHbktApPJrmYsGRRXT2y4gX6V9FlEpqm0jz8R8Rdooor7E4gooooADVa6ufJXjrU8jhELGud1C63ueeKTGiG6u5Z5Tlvl9Kqk81GG96jlmK1Nxk74xVZ25qMzk96aHJoFYl7VGy45p6nNKw+WkBVl5WqDfeq84zxVUp81AEJjDdajaFR0FWsUBaENFIxe1SwwDOas7BigDaaYxRAMVII1xipAOKNvFAERRR2pFwBzwKfjNRzghaQCrMu7FTjDDjms1etaFnTAeEJFW4QQlOULT1XtTBDGhWUcjmn2/7v5CelTKgFRSrtYMKLASPHkg1v6UhENYcbhkB9K6DTHVrbihCLmAetcjqdlPb6hJMku5JCPlPXpXXE/Kcdaxr6NjIXcDA6V4ueL91ojfD/ABGOEyBxzUbcHFWuM8VXcZevhY6nqDRTto25zSbecU/Kn5R1ppNg3Ydbr+8B966qAfuF+lc1bQsZlXtmupiTbGo9BX2WTUb02eZiJe8S0UUV9GcoUUUx22qx9BQBn6nc7F2A1zUsnmOeelW9SuiztznmsZp9rYB5NQykXBxVOZstjNO85qqzMc0hkh9qcDVdZD3qQNQBYjbBqdvuYqop5FWlYE8GgCHyS1Ma2JPSrwA4oI9qAMl4ypxinLH7VeeMN1HNM24GMUICt5dAj56VZ2+1PWP2pgV9pxShateXgdKcsQPWgCoI89BTZoWZcbTWmsYXtTto9BQBgpaNzkGrFtCY85zWqUU+lNKL6UCK2cYqzEwJAIqBxycdKnjGWGKY0WQowahcZFT9F5qFulAFYyhCVzjNa2k3XljazcGufn5n/GrkDFVBFJbgdmpDqGFZuonAO7p2p+nXfmRhTUeqQjyzIScegrzc2pupQsjSk7SMc4GcVEeTUzRsqjIIz0zUQB3Yr4Z4WpF2sekpISpGgxD5wHINPgiMkgGK10sSYiuOpr1cuwEqnxIyq1UhNMjEkSyFea1gMCoreEQxKgHSpq+xw9CNKFkedOXMxaKKK6CAqjqMuy3bBwavVg6xcBAy0ho568kyTz3qhjLZqWd9zGoxUMpEgNQTHmpRSSIDQMrr1qUGkKYoFOwiRWBHXmnQzFZQT0qoxKPnPFIsg3Dmh6AbizKVzU6OhQVkxvkdanRiB1pBYvMoPQVGUFNWcZAPSoDc/f5+lAWLG0U5cVFDIJIsk81KtO4h5xSgUmKcOKVxi0uKRjtXdTBLQBKRgdKjHzZpQ24UKMZpiImXmnwcGmTShPrTIZg3ApjRakkqIvsjLmnBS5xSXiBYwh6UAY8srvOWxgZq1DcFcAjiopECjiotxBpAdBp9wFkGDXRhVniGQDXFWb7HB9a6rT59wAzQ4pqzC5YazR23MAcdKhXS4wDn5s98VoZpayeFpvWw/aSRRTT0jkDLxVwDAp2KMCtKdKNNWiiXJvcKWiitCQoNFFADH4Un2rktWn3SOK6m5YrCze1cdqT7mY0mNGUTk0L1pKkQVBQoFWfs5ZcgVCBWhEwAxQBnvDioNpya12iVuarzQgDgU0Bjzjg1VUnOa2mgV0II5rPEH7zaKGAsMxHWr8cqkdaptbtH1qEvsNIDTJHNVJEJfrxVf7VjuakS5DYoGXYhsxg8VMs+2cZPFQK4K1Gx5NAjSa4XfwakWXNZSyZjHrVm3k3EA0AX2PyYqEYzTmYVEzAZxQImU02WYRYyetUhc4k2E1Xv7jABz0FMCS6udxJFT6epkXee1YUVx5jHJ4q/HcmOMqvGRTGbqSIrgbhk9qjumy3PpWXYM81xk54rQuTnmpbAoSPlj9aZSP8AfNApXAtQOBgE81v6XKfMArmo/vg+lbmnSBXBNWhM6scgU6o4mDoCBUlUIWiiigQUUUUAFBooNAFW+OLVz7Vx158wJrsb/wD49HrjrroaTGjMqZOlQmnxHNQWTgc1aVulVl+6KnTnFAifPFIRk0CpAucUICu8PUiswpsuBmt7bxWXfRFTupiLi26XEAI6gdqxr60aJywHHetvS2zE3PIxU1zEkiHKjNAHESIykntQhZSOa3LiwAyQoxWc9oVPSgZJHNwKd5wqsVZFqk9yysQQaANcXCAU9bkA8Vhfa6elyS4FAG618o2gnrT5bmNV4Y8iuclnLPUqzO4C85oAtS3P7zcDzUEsryrtzSLbszc5P1q/FaDGdtAGdbwOrda0uvA61MLfb2ot4jJcY9KANbT4AlrvP3jTZQQDVtAEjCioJl4qWBlv9+hetOlGHNJGPmpICUIFwR3rSsG5FU9o8vNT2Wd1WhM7K1/1Yqeq9p/qh9KsVZIoooFFABRRRQAUUUUAV7wE2zgda4284dh3ruHAKEH0rjtTRRI+B3NJjRit3p0XSmv1p0fSoHcmFWoulVRVqL7tAyVetWUHFVl+9VpelCAkVM1Vu4t0bVfgqG5A5piMqwcxtIv0q48mRVVVC3DY9qlbvQBHKcr1qsyK3UVO/Sou1MZWkgVx0qhcWanORmtRutMkUbDSA428hMUntUCXXzgZrX1ZFCE4rlNxFzwe9AjpbeP7RIoroLfT0UA1k6GAyAnk10nQDHpQMiECqc4qVFUmkb7tMyRQA6chRio9JUvM0h6dqJj8tXNPRVgXA60AXRyM1Gw5qUU1qlgUriAPyOtQrGVFX3HzVE4wv40IYwjC4qeyX56iboKsWX+sFWiWdbbLiIVNUcP+qX6VJVEhRRRQAUUUUAf/2Q=='});
				}, 100)
			},
			takePhoto: function(options, callback){
				options = {
					allowEdit: true, //是否截取
					targetWidth: 100, //图片截取宽度，可选
					targetHeight: 100, //图片截取高度，可选
					saveToPhotoAlbum: false, //是否保存图片
					encodingType: 'jpg', //（可选项）返回图片类型，jpg或png
					quality: 90 //图片质量，只针对jpg格式图片（0-100整数）
				};
				setTimeout(function(){
					callback({base64Data: 'data:image/jpeg;base64,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'});
				}, 100)
			},
			onShow: function(callback){

			},
			joinRoom: function(options){
				alert('joinRoom:' + JSON.stringify(options));
				console.log(options);
			},
			startChat: function(options) {
				alert('startChat:' + JSON.stringify(options));
				console.log(options);
			},
			phoneCall: function(options){
				alert('phoneCall ' + options.number);
			},
			showFloatButton: function(options){
				alert('showFloatButton ' + JSON.stringify(options));
				console.log(options);
			},
			hideFloatButton: function(){
				alert('hideFloatButton ');
			},
			showMap: function(options){
				alert('showMap ' + (options.name || ''));
				console.log(options);
			},
			weixinPay: function(options, callback) {
				alert('weixinPay: ' + JSON.stringify(options));
				console.log(options);
			},
			weixinShareWebPage: function(options){
				alert('weixinShareWebPage: ' + JSON.stringify(options));
				console.log(options);
			},
			aliPay: function(options, callback) {
				alert('aliPay: ' + JSON.stringify(options));
				console.log(options);
			},
			scanDev: function(options, onAdd){
				setTimeout(function(){
					onAdd({
						devType: options.devType || 0,
						devName: 'MOCK-DEVICE',
						devMac: new Date().valueOf()
					})
				}, 500);
			},
			connectDev: function(options, onStatusChange){
				setTimeout(function(){
					onStatusChange({
						devName: options.devName,
						devType: options.devType,
						devStatus: 1
					})
				}, 1000);
				setTimeout(function(){
					onStatusChange({
						devName: options.devName,
						devType: options.devType,
						devStatus: 0
					})
				}, 15000);
			},
			startDev: function(options, onResult){
				setTimeout(function(){
					onResult({
						devName: options.devName,
						devType: options.devType,
						devMac: options.devMac,
						data: {
							lbp: 74,
							hbp: 126,
							p: 83
						}
					});
				}, 3000);
			},			
			showNavBar: function(options){
				console.log(options);
			}
		};
		if(window.location.search){
			var arr = window.location.search.replace('?', '').split('&');
			var query = {};
			for(var i=0; i< arr.length; i++){
				if(arr[i]){
					var kv = arr[i].split('=');
					query[kv[0]] = kv.length > 1 ? window.decodeURIComponent(kv[1]) : '';
				}
			}
			if(query.__pageParam){
				bridge.pageParam = JSON.parse(query.__pageParam);
			}
		}
		window.__raiseEvent = function(name, str){
			var data = null;
			if(str){
				data = JSON.parse(str);
			}
			var evt = new CustomEvent(name, {detail: data});
			window.dispatchEvent(evt);
		}
		return bridge;
	}

	window.addEventListener('load', function(){
		setTimeout(function(){
			if(!window.bridge){
				if(/__title=([^?&=]+)/.test(window.location.search)){
					window.document.title = window.decodeURIComponent(window.location.search.match(/__title=([^?&=]+)/)[1]);
				}
				window.bridge = createBridge();
				window.onBridgeReady && window.onBridgeReady();
			}
		}, 1500)
	});
})(window)
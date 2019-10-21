window.onerror = function(errorMessage, scriptURI, lineNumber, columnNumber, errorObj){
	var data = {
		page: window.location.href,
		errorMessage: errorMessage || '',
		scriptURI: scriptURI || '',
		lineNumber: lineNumber || '',
		columnNumber: columnNumber || '',
		stack: errorObj && errorObj.stack || ''
	};
	if(/[\?\&]showerror/i.test(window.location.href)){
		var divEle = document.createElement("DIV");
		divEle.innerHTML = JSON.stringify(data);
		divEle.style.zIndex = "999";
		divEle.style.position = "absolute";
		divEle.style.left = "0";
		divEle.style.top = "0";
		divEle.style.width = "100%";
		if(document.body){
			document.body.appendChild(divEle);
		}else{
			window.addEventListener('load', function(){
				document.body.appendChild(divEle);
			});
		}
	}

	var parms = [];
	for(var k in data){
		parms.push(k + '=' + encodeURIComponent(data[k]));
	}
	var img = document.createElement('IMG');
	img.style.display="none";
	img.style.width = '0px';
	img.style.height = '0px';
	img.src = "http://error-report.lugangtech.com/error-h5?" + parms.join('&');
	if(document.body){
		document.body.appendChild(img);
	}else{
		window.addEventListener('load', function(){
			document.body.appendChild(img);
		});
	}
};
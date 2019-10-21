document.write('<script type="text/javascript" src="' + './static/js/error-report.js' + '?_=' + new Date().valueOf() + '"></script>');

document.write('<style type="text/css">');
document.write(''
+'#app[v-cloak]{'
+' width: 100vw; height:100vh;'
+ 'background: #fff url(/static/img/page-loading.gif) no-repeat 50% 50%;'
+ 'background-color: transparent;'
+ 'background-size: 99px auto;'
+'}'



+'');
document.write('</style>');

var scripts = [];
if(/(AndroidApp)/i.test(window.navigator.userAgent)){
	scripts = ['./static/js/dsbridge.js', './static/js/bridge.android.js'];
} else if(/(iOSApp)/i.test(window.navigator.userAgent)){
	scripts = ['./static/js/dsbridge.js', './static/js/bridge.ios.js'];
} else {
	scripts = ['./static/js/bridge.pc.js'];
}
for(var i = 0; i < scripts.length; i++){
	document.write('<script type="text/javascript" src="' + scripts[i] + '?_=' + new Date().valueOf() + '"></script>');
}

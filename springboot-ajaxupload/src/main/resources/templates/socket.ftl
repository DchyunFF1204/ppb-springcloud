<html lang="zh-CN">
<head>  
	<meta charset="UTF-8">
    <title>Hello WebSocket</title>  
    <script src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.min.js"></script>  
    <script src="http://cdn.bootcss.com/stomp.js/2.3.3/stomp.js"></script>  
    <script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>  
    <script type="text/javascript">  
	    var socketDomain = "http://127.0.0.1:8080";
        var stompClient = null;

    function setConnected(connected) {
        document.getElementById('connect').disabled = connected;
        document.getElementById('disconnect').disabled = !connected;
        document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
        $('#response').html();
    }
    
    function connect() {
        var socket = new SockJS(socketDomain+"/hello"); //1
        stompClient = Stomp.over(socket);//2
        stompClient.connect({}, function(frame) {//3
            setConnected(true);
            console.log('开始进行连接Connected: ' + frame);
			// 全局订阅
            stompClient.subscribe('/queue/greetings', function(respnose){ //4
				//console.log(respnose);
                //showResponse(respnose.body);
            });
			// 私人订阅
			stompClient.subscribe("/queue/message1", function(respnose){ //4
				console.log("res="+JSON.parse(respnose.body));
                showResponse(JSON.parse(respnose.body).message);
            });
        });
    }
    
    
    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function sendName() {
        var name = $('#name').val();
        stompClient.send("/app/message", {}, JSON.stringify({ 'userId': 2,'message':'1发送的消息' }));//5
    }

    function showResponse(message) {
          var response = $("#response");
          response.append(message+"<br/>");
    }
    </script>  
</head>  
<body onload="disconnect()">  
	<noscript><h2 style="color: #ff0000">貌似你的浏览器不支持websocket</h2></noscript>
	<div>
		<div>
			<button id="connect" onclick="connect();">连接</button>
			<button id="disconnect" disabled="disabled" onclick="disconnect();">断开连接</button>
		</div>
		<div id="conversationDiv">
			<label>输入你的名字</label><input type="text" id="name" />
			<button id="sendName" onclick="sendName();">发送</button>
			<p id="response"></p>
		</div>
	</div>
</body>  
</html>  
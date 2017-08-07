<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;">
<title>聊天</title>
<style type="text/css">
	body,html{
		margin:0px;
	}
   	#left {  
        width: 20%;    /*这里是百分比或者像素值，对应下面的center就是百分比或者像素值*/  
        background: red;    
        height:100%;
        overflow:auto; 
        float:left;
    }  
    #right {  
        width: 80%;  /*这里是百分比或者像素值，对应下面的center就是百分比或者像素值*/  
        height: 100%;  
        background: pink;  
        overflow:auto;
        float:right;
    }
    .rightTop{
    	height:80%;
    	width:100%;
    	background:blue;
    	float:right;
    }
    .rightBottom{
    	height:20%;
    	width:100%;
    	background:yellow;
    	float:right;
    }
	.leftTop{
		height:20%;
		width:100%;
		background:green;
	}
	.leftMiddle{
		height:10%;
		width:100%;
		background:black;
	}
	.leftBottom{
		height:70%;
		width:100%;
	}
</style>
<script type="text/javascript">
function initAjax(){//初始化AJAX
	var xmlHttp;
	try{
		xmlHttp = new XMLHttpRequest();
	} catch(e){
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch(e){
			try {
				xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
			}catch (e){
				alert ("您的浏览器不支持AJAX！");
			}
		}
	}
	return xmlHttp;
}
function getUsersOnline(){
	var httpRequest = initAjax();
	httpRequest.open("get","getusersonline?usersonline=check",true);
	if (httpRequest.readyState == 4){
		if (httpRequest.status == 200){
			var usersonline = httpRequest.responseText;
			if (usersonline != null){
				document.getElementById("users").innerHTML.value = usersonline;
			}
		}
	}
}
function checkEmpty(){
	var message = document.getElementById("message").value;
	if (message != null && message != ""){
		document.getElementById("send").disabled = false;
	} else {
		document.getElementById("send").disabled = true;
	}
}
</script>
</head>

<div>

	<div id="left">
		<div class="leftTop">
			用户信息
		</div>
		<div class="leftMiddle">
			功能键
		</div>
		<div class="leftBottom">
			<div id="users">
			</div>
		</div>
	</div>
	<div id="right">
		<div class="rightTop">
			消息
		</div>
		<div class="rightBottom">
			<form name="form1">
				<table>
					<tr><td><textarea name="message" id="message" style="width:720px;height:60px;"></textarea></td><td><input type="submit" name="send" id="send" value="发送" disabled="true" onclick="checkEmpty()"></td></tr>
				</table>
			</form>
		</div>
	</div>

</div>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height: 100%;">
<head>
<meta http-equiv="Content-Type" content="text/html;">
<title>聊天</title>
<style type="text/css">
	body,html{
		margin:0px;
	}
	  
   	#left {  
        width: 20%;    /*这里是百分比或者像素值，对应下面的center就是百分比或者像素值*/  
        //background: red;    
        height:100%;
        overflow:auto; 
        float:left;
        border:1px;
    }  
    #right {  
        width: 80%;  /*这里是百分比或者像素值，对应下面的center就是百分比或者像素值*/  
        height: 100%;  
        //background: pink;  
        overflow:auto;
        float:right;
        border:1px;
    }
    .rightTop{
    	height:80%;
    	width:100%;
    	//background:blue;
    	float:right;
    	border:1px;
    }
    .rightBottom{
    	height:20%;
    	width:100%;
    	//background:yellow;
    	float:right;
    	border:1px;
    }
	.leftTop{
		height:20%;
		width:100%;
		//background:green;
		border:1px;
	}
	.leftMiddle{
		height:10%;
		width:100%;
		//background:black;
		border:1px;
	}
	.leftBottom{
		height:70%;
		width:100%;
		border:1px;
	}
	.messageBox{
		word-break:break-all;
		-webkit-line-clamp:4;
		-webkit-box-orient:vertical;
		overflow:hidden;
		height:500px;
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
	httpRequest.open("get","getusersonline",true);
	httpRequest.onreadystatechange = function (){
		if (httpRequest.readyState == 4){
			if (httpRequest.status == 200){
				var nameList = httpRequest.responseText;
				document.getElementById("nameList").innerHTML = nameList;
			}
		}
	}
	httpRequest.send(null);
}
function sendMessages(){
	var message = document.getElementById("message").value;
	var httpRequest = initAjax();
	httpRequest.open("get","sendmessages?message="+message,true);
	httpRequest.onreadystatechange = function (){
		if (httpRequest.readyState == 4){
			if (httpRequest.status == 200){
				document.getElementById("message").value = "";
			}
		}
	}
	httpRequest.send(null);
}
function showMessages(){
	var httpRequest = initAjax();
	httpRequest.open("get","showmessages",true);
	httpRequest.onreadystatechange = function (){
		if (httpRequest.readyState == 4){
			if (httpRequest.status == 200){
				var messages = httpRequest.responseText;
				document.getElementById("messages").innerHTML = messages;
			}
		}
	}
	httpRequest.send(null);
}
function logout(){
	window.location.href="logout";
}
</script>
</head>
<body style="height: 100%;">
<script type="text/javascript">
window.setInterval("showMessages();",2000);
window.setInterval("getUsersOnline();",5000);
</script>
<div  style="height: 100%;">
	<div id="left" style="height: 100%;" class="left">
		<div class="leftTop">
			<table align="center">
				<tr>
					<td><img src="showclienticon" width="50" height="50"></td>
				</tr>
				<tr>
					<td>昵称：<%= (String)session.getAttribute("nickname")%></td>
				</tr>
				<tr>
					<td><input type="button" value="注销" onclick="logout();"></td>
				</tr>
			</table>
		</div>
		<div class="leftMiddle">
			功能键
		</div>
		<div class="leftBottom" id="nameList">
		</div>
	</div>
	<div id="right" style="height: 100%;" class="right">
		<table>
			<div class="rightTop" overflow:hidden>
				<div class="messageBox" id="messages" ></div>
			</div>
		</table>
		<div class="rightBottom">
			<table align="center">
				<tr>
					<td>
						<textarea name="message" id="message" style="width:720px;height:60px;" onclick="check()" onblur="check()"></textarea>
					</td>
					<td>
						<input type="submit" name="send" id="send" value="发送" onclick="sendMessages();">
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>
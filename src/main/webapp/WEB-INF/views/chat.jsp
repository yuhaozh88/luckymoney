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
    }  
    #right {  
        width: 80%;  /*这里是百分比或者像素值，对应下面的center就是百分比或者像素值*/  
        height: 100%;  
        //background: pink;  
        overflow:auto;
        float:right;
    }
    .rightTop{
    	height:80%;
    	width:100%;
    	//background:blue;
    	float:right;
    }
    .rightBottom{
    	height:20%;
    	width:100%;
    	//background:yellow;
    	float:right;
    }
	.leftTop{
		height:20%;
		width:100%;
		//background:green;
	}
	.leftMiddle{
		height:10%;
		width:100%;
		//background:black;
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
function logout(){
	window.location.href="logout";
}
</script>
</head>
<body style="height: 100%;">
<script type="text/javascript">
window.setInterval("getUsersOnline();",5000);
</script>
<div  style="height: 100%;">
	<div id="left" style="height: 100%;" class="left">
		<div class="leftTop">
			<table align="center">
				<tr><td><img src="showicon" width="50" height="50"></td>
					<td><%= (String)session.getAttribute("nickname")%></td>
				</tr>
				<tr>
					<td><input value="注销" onclick=""></td>
				</tr>
			</table>
		</div>
		<div class="leftMiddle">
			功能键
		</div>
		<div class="leftBottom" id="nameList">
			<h2>在线用户</h2><br>
		</div>
	</div>
	<div id="right" style="height: 100%;" class="right">
		<div class="rightTop">
			消息
		</div>
		<div class="rightBottom">
			<table align="center">
			<tr>
				<td>
					<textarea name="message" id="message" style="width:720px;height:60px;" onclick="check()" onblur="check()"></textarea>
				</td>
				<td><input type="submit" name="send" id="send" value="SEND"></td>
			</tr>
			</table>
		</div>
	</div>

</div>
</body>
</html>
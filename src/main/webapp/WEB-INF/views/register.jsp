<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<script type="text/javascript">
var check1 = false;
var check2 = false;
var check3 = false;
var check4 = false;
var check5 = false;
var check6 = false;
var check7 = false;
var check8 = false;
function checkItcode(){
	var itcode = document.getElementById("itcode").value;
	if (itcode != null && itcode != ""){
		check1 = true;
		document.getElementById("warningItcode").style.color = "green";
		document.getElementById("warningItcode").innerHTML = "√";
	} else {
		check1 = false;
		document.getElementById("warningItcode").style.color = "red";
		document.getElementById("warningItcode").innerHTML = "员工号不能为空！";
	}
}
function checkNickname(){
	var nickname = document.getElementById("nickname").value;
	if (nickname != null && nickname != ""){
		check2 = true;
		document.getElementById("warningNickename").style.color = "green";
		document.getElementById("warningNickename").innerHTML = "√";
	} else {
		check2 = false;
		document.getElementById("warningNickename").style.color = "red";
		document.getElementById("warningNickename").innerHTML = "昵称不能为空！";
	}
}
function checkPassword(){
	var password = document.getElementById("password").value;
	if (password != null && password != ""){
		check3 = true;
		document.getElementById("warningPassword").style.color = "green";
		document.getElementById("warningPassword").innerHTML = "√";
	} else {
		check3 = false;
		document.getElementById("warningPassword").style.color = "red";
		document.getElementById("warningPassword").innerHTML ="密码不能为空！";
	}
}
function checkConfirm(){
	var password = document.getElementById("password").value;
	var confirm = document.getElementById("confirm").value;
	if (confirm != null && confirm != "" && confirm == password){
		check4 = true;
		document.getElementById("warningConfirm").style.color = "green";
		document.getElementById("warningConfirm").innerHTML = "√";
	} else if (confirm != null && confirm != "" && confirm != password){//确认密码和密码不相同
		check4 = false;
		document.getElementById("warningConfirm").style.color = "red";
		document.getElementById("warningConfirm").innerHTML = "确认密码不正确！";
	} else {
		check4 = false;
		document.getElementById("warningConfirm").style.color = "red";
		document.getElementById("warningConfirm").innerHTML = "确认密码不能为空！";
	}
}
function checkQuestion(){
	var question = document.getElementById("question").value;
	if (question != null && question != ""){
		check5 = true;
		document.getElementById("warningQuestion").style.color = "green";
		document.getElementById("warningQuestion").innerHTML = "√";
	} else {
		check5 = false;
		document.getElementById("warningQuestion").style.color = "red";
		document.getElementById("warningQuestion").innerHTML = "密保问题不能为空！";
	}
}
function checkAnswer(){
	var answer = document.getElementById("answer").value;
	if (answer != null && answer != ""){
		check6 = true;
		document.getElementById("warningAnswer").style.color = "green";
		document.getElementById("warningAnswer").innerHTML = "√";
	} else {
		check6 = false;
		document.getElementById("warningAnswer").style.color = "red";
		document.getElementById("warningAnswer").innerHTML = "密保问题答案不能为空！";
	}
}
function checkDeptName(){
	var dept_name = document.getElementById("dept_name").value;
	if (dept_name != null && dept_name != ""){
		check8 = true;
		document.getElementById("warningDeptName").style.color = "green";
		document.getElementById("warningDeptName").innerHTML = "√";
	} else {
		check8 = false;
		document.getElementById("warningDeptName").style.color = "red";
		document.getElementById("warningDeptName").innerHTML = "部门名称不能为空！";
	}
}
function checkEmpty(){
	if (check1 && check2 && check3 && check4 && check6 && check7){
		document.getElementById("submit").disabled = false;
	} else {
		document.getElementById("submit").disabled = true;
	}
}//end checkEmpty()
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

function getRealname(){
	var httpRequest = initAjax();
	var itcode = document.getElementById("itcode").value;
	httpRequest.open("get","getrealname?itcode="+itcode,true);
	httpRequest.onreadystatechange = function(){
		if (httpRequest.readyState == 4){
			if (httpRequest.status == 200){
				var realname = httpRequest.responseText;
				if (realname != null && realname != ""){
					check7 = true;
					document.getElementById("realname").style.color = "green";
					document.getElementById("realname").innerHTML = "欢迎" + realname + "！";
				} else {
					check7 = false;
					document.getElementById("realname").style.color = "red";
					document.getElementById("realname").innerHTML = "工号不存在!";
				}
			}
		}
	}
	httpRequest.send(null);
}
function checkChangeIcon(){
	var path = document.getElementById("icon").value;
	if (path != null && path != ""){
		document.getElementById("preview").disabled = false;
	} else {
		document.getElementById("preview").disabled = true;
	}
}
function changeIcon(){
	var path = document.getElementById("icon").value;
	if (path != null && path != ""){
		document.getElementById("iconImage").src = path;
	} 
}
</script>
</head>
<body>
<script type="text/javascript">
window.setInterval("checkEmpty();checkChangeIcon();",1000);
</script>

<form name="form1" id="form1" action="login">
<table align="center" style="border-collapse:separate; border-spacing:0px 20px;" id="teble1" border="0">
<tr><td>&nbsp;&nbsp;<img alt=""  src="resources/img/default.jpg" id="iconImage" width="100" height="100"></td></tr><br><br>
</table>
<table align="center" style="border-collapse:separate; border-spacing:0px 20px;" id="teble1" border="0">
<tr><td>员工号</td><td><div style="color:red;">*</div></td><td><input type="text" name="itcode" id ="itcode" onclick="checkEmpty()" onblur="checkItcode();getRealname();checkEmpty();"></td><td><div id="warningItcode"></div></td></tr>
<tr><td>真实姓名</td><td><div style="color:red;">*</div></td><td><div id="realname" ></div></td><td><div id="warningRealname"></div></td></tr>
<tr><td>部门</td><td><div style="color:red;">*</div></td><td>
															<select name="dept_name" id="dept_name" onclick="checkEmpty();" onblur="checkDeptName();checkEmpty();">
																<option value="dept1">
																	部门1
																</option>	
																<option value="dept2">
																	部门2
																</option>
																<option value="dept3">
																	部门3
																</option>
																<option value="dept4">
																	部门4
																</option>
															</select>
														</td><td><div id="warningDeptName"></div></td></tr>
<tr><td>昵称</td><td><div style="color:red;">*</div></td><td><input type ="text" name="nickname" id="nickname" onclick="checkEmpty()" onblur="checkNickname();checkEmpty();"></td><td><div id="warningNickename"></div></td></tr>
<tr><td>密码</td><td><div style="color:red;">*</div></td><td><input type="password" name="password" id="password" onclick="checkEmpty()" onblur="checkPassword();checkConfirm();checkEmpty();"></td><td><div id="warningPassword"></div></td></tr>
<tr><td>确认密码</td><td><div style="color:red;">*</div></td><td><input type="password" name="confirm" id="confirm" onclick="checkEmpty()" onblur="checkConfirm();checkEmpty();"></td><td><div id="warningConfirm"></div></td></tr>
<tr><td>密保问题</td><td><div style="color:red;">*</div></td><td>
																<select name="question" id="question">
																	<option value="question1">
																		您父亲的名字
																	</option>
																	<option value="question2">
																		您母亲的名字
																	</option>
																	<option value="question3">
																		您小学班主任的名字
																	</option>
																	<option value="question4">
																		您初中学校的名字
																	</option>
																	<option value="question5">
																		您高中班主任的名字
																	</option>
																</select>
															  </td><td><div id="warningQuestion"></div></td></tr>
<tr><td>密保问题答案</td><td><div style="color:red;">*</div></td><td><input type="text" name="answer" id="answer" onclick="checkEmpty()" onblur="checkAnswer();checkEmpty();"></td><td><div id="warningAnswer"></div></td></tr>
<tr><td>上传头像</td><td></td><td><input type="file" name="icon" id="icon" onblur="checkChangeIcon()"></td></tr>
</table>
<table id="table2" align="center">
<tr><td><input type="button" name="preview" id="preview" value="预览头像" disabled="true" onclick="changeIcon();"></td><td><input type="submit" name="submit" id="submit" value="注册" disabled="true"></td></tr>
</table>
</form>
</body>
</html>
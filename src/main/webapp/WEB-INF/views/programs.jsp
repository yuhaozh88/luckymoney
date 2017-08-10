<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page import="com.chinasoft.project.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.chinasoft.project.DAO.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>节目打赏</title>

</head>
<body>
<h1>为以下节目打赏</h1>
<form name="form1" action="dashang">
	<table>
		<c:forEach var="programs" items="${programs}">
			<tr>
				<td>${programs.program_name}</td>
				<td><input type="text" name="${programs.pid}"></td>
			</tr>
		</c:forEach>
	</table>
	<input type="submit" value="打赏">
	<input type="button" value="充值" onclick=""> 
</form>
</body>
</html>
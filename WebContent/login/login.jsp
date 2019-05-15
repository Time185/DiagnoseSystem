<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import = "java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>登录</title>
</head>
<script src="../js/jquery-1.11.3.js" type="text/javascript"></script>
			<%
			String errorMsg0 =(String)request.getAttribute("errorMsg0");
			if(errorMsg0 == null){
				errorMsg0 = "";
			}
			%>
			<%
			String errorMsg =(String)request.getAttribute("errorMsg");
			if(errorMsg == null){
				errorMsg = "";
			}
			%>
<body >

	<div align="center" >
	<form action="user_login.action" name="form" method="post">
		<table>
<!--  		<caption>登录</caption> -->
			<tr><td>用户名</td><td><input type="text" name="loginname"></td></tr>
			<tr><td>密码</td><td><input type="password" name="password"></td></tr>
			<tr><td>用户类型</td><td><input type="radio" name="status" value="0">患者<input type="radio" name="status" value="1">医生</td></tr>
		</table>
			<span style="color:rgb(255,0,0)"><%=errorMsg %></span><span style="color:rgb(255,0,0)"><%=errorMsg0 %></span>
			<input type="submit" value="登录">
			<input type="button" name="regist" value="注册" onclick="window.location.href='http://localhost:8080/SH/login/regist.jsp'">
	</form>
	</div>

</body>
</html>
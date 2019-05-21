<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import = "java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style type="text/css">
        input::-webkit-input-placeholder{
            color:red;
        }
        input::-moz-placeholder{   /* Mozilla Firefox 19+ */
            color:red;
        }
        input:-moz-placeholder{    /* Mozilla Firefox 4 to 18 */
            color:red;
        }
        input:-ms-input-placeholder{  /* Internet Explorer 10-11 */ 
            color:red;
        }
        
        #error{
        	font-size:smaller;
            color:red;
        }
</style>
<title>登录</title>
<script>
function inputNull(form)
{
	//window.alert(form.length);
	if(form.elements[0].value==""){
		form.elements[0].placeholder="用户名不能为空";
		return false;
	}
	else if(form.elements[1].value==""){
		form.elements[1].placeholder="密码不能为空";
		return false;		
	}
	else if((!form.elements[2].checked) && (!form.elements[3].checked)){
		var errorMsg = document.createTextNode("用户类型不能为空");
		if(document.getElementById("error").childNodes.length==0){
			document.getElementById("error").appendChild(errorMsg)
		}
		return false;
	}
}
</script>
</head>
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
<body>
	<form action="user_login.action" method="post" onSubmit="return inputNull(this)">
		<table>
<!--  		<caption>登录</caption> -->
			<tr><td>用户名</td><td><input type="text" name="loginname"></td></tr>
			<tr><td>密码</td><td><input type="password" name="password" ></td></tr>
			<tr><td>用户类型</td><td><input type="radio" name="status" value="0" >患者<input type="radio" name="status" value="1" >医生</td><td id="error" ></td></tr>
			<tr><td><input type="submit" value="登录"></td><td><span style="color:rgb(255,0,0)"><%=errorMsg %></span><span style="color:rgb(255,0,0)"><%=errorMsg0 %></span>
			</td></tr>			
		</table>
	</form>
</body>
</html>
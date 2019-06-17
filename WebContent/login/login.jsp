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
        #alert{
        	font-size:smaller;
            color:red;
            text-align: left;
        }
</style>
<title>登录</title>
<link href="./image/login.css" type="text/css" rel="stylesheet"/>
<script src="../js/jquery-1.11.3.min.js"></script>
<script>
function inputNull(form)
{
	//window.alert(form.length);
	if(form.elements[0].value==""){
		//form.elements[0].placeholder="用户名不能为空";
		$("#alert").text("用户名不能为空")
		return false;
	}
	else if(form.elements[1].value==""){
		//form.elements[1].placeholder="密码不能为空";
		$("#alert").text("您还没有输入密码")
		return false;		
	}
	else if((!form.elements[2].checked) && (!form.elements[3].checked)){
		/*var errorMsg = document.createTextNode("用户类型未选择");
		if(document.getElementById("error").childNodes.length==0){
			document.getElementById("error").appendChild(errorMsg)
		}*/
		$("#alert").text("用户类型未选择")
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
<body background="./image/bglogin.jpg">
	<div  class="main1" id="main">
        <div class="login1">
	      <div class="login2">
			<div class="loginTopDiv">
              <span class="loginTop">会员登录</span>
            </div>
            <hr class="thickLine" noshade="noshade">
            <hr class="thinLine" >
            <div>
		<form action="user_login.action" method="post" id="form" name="form" onSubmit="return inputNull(this)">
			<table>
		
				<tr><td>用户名</td><td><input class="userInput" type="text" name="loginname"></td></tr>
				<tr>
                 	 <td height="20">&nbsp;</td>
                  	<td></td>
           	 	</tr>
				<tr><td>密码</td><td><input class="passwordInput" type="password" name="password" ></td></tr>
					<tr>
                	  	<td height="20">&nbsp;</td>
                  		<td id="alert"><%= errorMsg %></td>
            		</tr>
					<tr><td>&nbsp;&nbsp;用户类型</td><td><input type="radio" name="status" value="0" >普通用户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="status" value="1" >专家用户</td><td id="error" ></td></tr>
			</table>		
			<div>
					<div style="margin-top:14px;height:41px;width:105px;float:left;margin-left:55px;margin-right:25px"><input type="submit" value="登录" style="width:105px" class="button_blue"></div>
					<div style="margin-top:14px;height:41px;width:105px;float:left"><input type="button" id="regist" value="注册" onclick="regist00()" style="width:105px" class="button_blue"></div>
			</div>				
		</form>
		</div>
		</div>
        </div>
       </div>
<script type="text/javascript">
	function regist00() {
		document.form.action="user_regist.action";
		document.form.submit();
		
		
	}
</script>
</body>
</html>
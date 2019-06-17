<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>

<html>
<head>
<script src="../js/jquery-1.11.3.min.js"></script>

<meta charset="UTF-8">
<title>注册</title>
<link href="./image/regist.css" type="text/css" rel="stylesheet"/>
</head>
<body background="./image/bglogin.jpg">
<div class="main1" id="main">
	<div class="login1">
		<div class="login2">
		<div class="loginTopDiv">
              <span class="loginTop">会员注册</span>
            </div>
            <hr class="thickLine" noshade="noshade">
            <hr class="thinLine" >
<s:form action="user_regist.action" theme="simple" onsubmit="return checkForm()">
	
	姓名:<s:textfield name="user.name" id="name" onblur="checkUsername()" style="width:210px;height:25px"></s:textfield><span id="span01" style="color:red"></span><br><br>
	年龄:<s:textfield name="user.age" id="age" style="width:210px;height:25px"></s:textfield><br><br>
	账号:<s:textfield name="user.loginname" id="loginname" onblur="checkname()" style="width:210px;height:25px"></s:textfield><span id="span02" style="color:red"></span><br><span id="span02"></span><br>
	密码:<s:password name="user.password" id="password" onblur="checkPassword()" style="width:210px;height:25px"></s:password><span id="span03" style="color:red"></span><br><br>
	邮箱:<s:textfield name="user.email" id="email" onblur="checkEmail()" style="width:210px;height:25px"></s:textfield><span id="span04" style="color:red"></span><br><br>
	电话:<s:textfield name="user.phone" id="phone" onblur="checkPhone()" style="width:210px;height:25px"></s:textfield><span id="span05" style="color:red"></span><br><br>
	<div style="margin-top:4px;margin-left:auto;margin-right:auto;height:41px;width:105px"><s:submit name="name" value="注册"  style="width:105px" class="button_blue"></s:submit></div>
</s:form>
		</div>
	</div>
</div>

<script type="text/javascript">
	function checkUsername(){
		var name = $("#name").val();
		if(name == ""){
			$("#span01").text("*姓名不能为空")
			return false
		  }else{
			  return true
		  }				
	}
	$("#name").change(function(){
		var name = $("#name").val();
			$("#span01").text("")
		
	})
	 function checkname (){
		var loginname = $("#loginname").val();
		$.ajax({
			type:"post",
			url:"user_check.action",
			data:{
				loginname:loginname
			},
			dataType:"text",
			success:function(data){
				$("#span02").text(data)
				if (data=="用户名不能为空"&&data=="用户名重名"){
					return false
				}else{
					return true
				}
			}
		})
	
	}
		function checkPassword(){
		$("#password").blur(function(){
		var password = $("#password").val();
		if(password == ""){
			$("#span03").text("*密码不能为空")
			return false
		}else{
			return true
		}				
		
		
	})
		}
	   $("#password").change(function(){
		var password = $("#password").val();
			$("#span03").text("")
	})
	   function checkEmail(){
		   var email = $("#email").val();
		   var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
		   if (!re.test(email)){
			   $("#span04").text("邮箱格式不正确")
			   return false
		   }else{
			   $("#span04").text("")
			   return true
		   }
		}
	   function checkPhone(){
		   var phone = $("#phone").val();
		   var re= /^[1][3,4,5,7,8][0-9]{9}$/ ;
		   if(!re.test(phone)){
			   $("#span05").text("手机号格式不正确")
			   return false
		   }else{
			   $("#span05").text("")
			   return true
		   }

	   }
	   function checkForm(){
		  var flag=checkUsername()&&checkname()&&checkPassword()&&checkEmail()&&checkPhone()
		  return flag
	   }
</script>

</body>
</html>
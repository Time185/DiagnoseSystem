<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>

<html>
<head>
<script src="../js/jquery-1.11.3.min.js"></script>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<s:form action="user_regist.action" theme="simple" onsubmit="return checkForm()" >
	
	姓名:<s:textfield name="user.name" id="name" onblur="checkUsername()" ></s:textfield><span id="span01" style="color:red"></span><br>
	年龄:<s:textfield name="user.age" id="age"></s:textfield><br>
	账号:<s:textfield name="user.loginname" id="loginname" onblur="checkname()"></s:textfield><span id="span02" style="color:red"></span><br><span id="span02"></span>
	密码:<s:password name="user.password" id="password" onblur="checkPassword()"></s:password><span id="span03" style="color:red"></span><br>
	邮箱:<s:textfield name="user.email" id="email" onblur="checkEmail()" ></s:textfield><span id="span04" style="color:red"></span><br>
	电话:<s:textfield name="user.phone" id="phone" onblur="checkPhone()" ></s:textfield><span id="span05" style="color:red"></span><br>
	<s:submit name="name" value="提交" ></s:submit>
</s:form>


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
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<script src="../js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	function diagnose(){
		$.ajax({
			type:"get",
			dataType:"text",
			url:"diagnose.action",
			success:function(data){
				alert(data)
			}		
		})
	}
</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>辅助诊断界面</h3>
<button id="diagnose" onclick="diagnose()">点击诊断文件</button>

</body>
</html>
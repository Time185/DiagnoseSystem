<%@ page language="java"  import="java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
	<link type="text/css" rel="stylesheet" href="../css/jquery.galpop.css" media="screen" />
	<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery.galpop.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		$('.galpop-multiple').galpop();
		});
		</script>		
</head>


<body> 

	<div class="gallery-wrapper" id="picture-div">
	<%
		ArrayList<String> url = (ArrayList<String>)request.getAttribute("url");
		if(url != null){
			for(int i = 0; i < url.size(); i++){
				%>
				<a class="galpop-multiple" data-galpop-group="multiple" href="pictureTransport.action?name=<%=url.get(i)%>"><img src="pictureTransport.action?name=<%=url.get(i)%>" alt="诊断图片" id="small-icon" /></a> 
	<%		}
		}			
%>
	
<!-- 		<a class="galpop-multiple" data-galpop-group="multiple" href="images/gallery/apocalypse.jpg"><img src="file://f:/Image.png" alt="An apocalyptic Earth." id="small-icon" /></a> -->
<!-- 		<a class="galpop-multiple" data-galpop-group="multiple" href="images/gallery/vintage.jpg"><img src="images/gallery/vintage.jpg" alt="An old, vintage poster." id="small-icon" /></a> -->
<!-- 		<a class="galpop-multiple" data-galpop-group="multiple" href="images/gallery/magicLake.jpg"><img src="images/gallery/magicLake.jpg" alt="A scene of a magical forest." id="small-icon" /></a> -->
<!-- 		<a class="galpop-multiple" data-galpop-group="multiple" href="images/gallery/underwater.jpg"><img src="images/gallery/underwater.jpg" alt="An underwater scene with lots of tension." id="small-icon"  /></a> -->
<!-- 		<a class="galpop-multiple" data-galpop-group="multiple" href="images/gallery/goodBoy.jpg"><img src="images/gallery/goodBoy.jpg" alt="A dog and his pet." id="small-icon" /></a> -->
<!-- 		<a class="galpop-multiple" data-galpop-group="multiple" href="images/gallery/darkroad.jpg"><img src="images/gallery/darkroad.jpg" alt="A scene where nothing is what it seems." id="small-icon" /></a> -->
<!-- 		<a class="galpop-multiple" data-galpop-group="multiple" href="images/gallery/roadkill.jpg"><img src="images/gallery/roadkill.jpg" alt="Either an anti-hunting poster or a pro-hunting one, depending on how you look at it." id="small-icon" /></a> -->
<!-- 		<a class="galpop-multiple" data-galpop-group="multiple" href="images/gallery/wolfMarine.jpg"><img src="images/gallery/wolfMarine.jpg" alt="A portrait of a wolf marine." id="small-icon" /></a> -->
<!-- 		<a class="galpop-multiple" data-galpop-group="multiple" href="images/gallery/alice.jpg"><img src="images/gallery/alice.jpg" alt="The scene from Alice in Wonderland where she meets the caterpillar." id="small-icon" /></a> -->
<!-- 		<a class="galpop-multiple" data-galpop-group="multiple" href="images/gallery/reflection.jpg"><img src="images/gallery/reflection.jpg" alt="A magical poster with a reflection." id="small-icon" /></a>	 -->
	</div>
</body>
</html>
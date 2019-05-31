<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首页</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
}
.STYLE1 {	font-size: 26px;
	font-family: Microsoft YaHei;
	color: rgb( 251, 247, 247 );
	line-height: 1.278;
	position: absolute;
	left: 153px;
	top: 25px;
	z-index: 15;
	width: 293px;
	height: 55px;
}
.STYLE2 {font-size: 10px}

.STYLE3 {
	font-size: 18px;
	font-weight: bold;
}
.STYLE4 {
	color: #3EAA42;
	font-size: 16px;
}
a:link{text-decoration:none; color:#504c4c;}
a:visited{text-decoration:none; color:#504c4c;}
a:hover{text-decoration:underline; color:#3EAA42;}
-->
</style>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="101" colspan="3" bordercolor="#000000" bgcolor="#000000"><table width="408" height="100" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="66" align="center">&nbsp;</td>
        <td width="91" align="center"><img src="../images/logo.png" width="80" height="82" /></td>
        <td width="251" align="center"><span class="STYLE1">大智慧医疗辅助诊断平台<span class="STYLE2"> Great Wisdom Medical Aided Diagnosis Platform</span></span></td>
      </tr>
      
    </table></td>
  </tr>
  <tr>
    <td width="100" height="72">&nbsp;</td>
    <td width="85%" height="72"><table width="100%" height="60" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="31"><img src="../images/shouye1.png" width="31" height="27" /></td>
        <td width="1044"><span class="STYLE4">首页</span></td>
        <td width="204"><div align="right" class="STYLE4"><a href="#"></a>${sessionScope.user.loginname },欢迎您!</div></td>
        <td width="36"><div align="right"><img src="../images/user1.png" width="35" height="35" /></div></td>
      </tr>
    </table> </td>
    <td width="100" height="72">&nbsp;</td>
  </tr>
  <tr>
    <td width="100" height="100%">&nbsp;</td>
    <td width="80%" height="670" bgcolor="#F0F1F5"><table width="100%" height="361" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="46%" height="361"><table width="330" height="243" border="0" align="right" cellpadding="0" cellspacing="0" background="../images/miantan1.png">
          <tr>
            <td> <div align="center" class="STYLE3"><a href="http://222.24.62.91:8090/SH/upload/upload.jsp">小肠癌AI辅助诊断</a></div></td>
          </tr>
          
        </table></td>
        <td width="8%">&nbsp;</td>
        <td width="46%"><table width="330" height="243" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td background="../images/miantan1.png"><div align="center" class="STYLE3"><a href="#">面瘫AI辅助诊断</a></div></td>
          </tr>
          
        </table></td>
      </tr>
    </table></td>
    <td width="100" height="100%">&nbsp;</td>
  </tr>
</table>
</body>
</html>
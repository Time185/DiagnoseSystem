<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>诊断结果</title>
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
.STYLE4 {
	color: #3EAA42;
	font-size: 16px;
}
a:link{text-decoration:none; color:#504c4c;}
a:visited{text-decoration:none; color:#504c4c;}
a:hover{text-decoration:underline; color:#3EAA42;}
.STYLE5 {
	font-size: 18px;
	font-weight: bold;
}
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
        <td width="1075"><span class="STYLE4">首页</span> <span class="STYLE4">&gt; 小肠癌AI辅助诊断</span></td>
        <td width="204"><div align="right" class="STYLE4"><a href="#"></a>${sessionScope.user.loginname },欢迎您!</div></td>
        <td width="36"><div align="right"><img src="../images/user1.png" width="35" height="35" /></div></td>
      </tr>
    </table> </td>
    <td width="100" height="72">&nbsp;</td>
  </tr>
  <tr>
    <td width="100" height="100%">&nbsp;</td>
    <td width="80%" height="670" bgcolor="#F0F1F5"><table width="539" height="314" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="593" height="100" bordercolor="#F0F0F0" bgcolor="#FFFFFF"><table width="397" height="70" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="192"><div align="right"><img src="../images/zhenduan.png" width="80" height="96" /></div></td>
            <td width="33">&nbsp;</td>
            <td width="248"><div align="center" class="STYLE5">
              <div align="left"><a href="getResult.action">查看诊断报告</a></div>
            </div></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="100">&nbsp;</td>
      </tr>
      <tr>
        <td height="100" bgcolor="#FFFFFF"><table width="397" height="70" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="192"><div align="right"><img src="../images/picture.png" width="80" height="90" /></div></td>
            <td width="33">&nbsp;</td>
            <td width="248"><div align="center" class="STYLE5">
                <div align="left"><a href="scanDCM.action" target="_blank">浏览切片信息</a></div>
            </div></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="100">&nbsp;</td>
      </tr>
      <tr>
        <td height="100" bgcolor="#FFFFFF"><table width="397" height="70" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="192"><div align="right"><img src="../images/xiazai.png" width="80" height="96" /></div></td>
            <td width="33">&nbsp;</td>
            <td width="248"><div align="center" class="STYLE5">
                <div align="left"><a href="getResult.action">下载诊断文件</a></div>
            </div></td>
          </tr>
        </table></td>
      </tr>
      
    </table></td>
    <td width="100" height="100%">&nbsp;</td>
  </tr>
</table>
</body>
</html>
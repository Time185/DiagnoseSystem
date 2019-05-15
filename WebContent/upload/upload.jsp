<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文件上传test1</title>
<link rel="stylesheet" type="text/css" href="Huploadify.css"/>
<link rel="stylesheet" type="text/css" href="webuploader.css" />
<link rel="stylesheet" type="text/css" href="style.css" />

<style>
	.width_auto{
		width: 750px;
        margin: 100px auto;
	}
</style>
<script src="jquery-1.11.3.js" type="text/javascript"></script>
<script src="jquery.js" type="text/javascript"></script>
<script src="plugin/jszip.js"></script>
<script src="plugin/jszip-utils.js"></script>
<script src="jquery.Huploadify.js" type="text/javascript"></script>
</head>

<body>
<!--  <form action="uploads" method="post" enctype="multipart/form-data"> -->
<div class="width_auto">
    <div id="container">
        <!--头部，相册选择和格式选择-->
        <div id="uploader" >
            <div class="item_container">                             
               <div id="" class="queueList" >
                  <div style="position:relative; font:normal 14px/24px 'MicroSoft YaHei';">
                       <p>说明：仅支持.zip压缩文件的上传，每次上传文件个数不超过5个，文件大小不超过800M</p>                  
                  		<div id="dndArea" class="placeholder">
                        	<div id="upload" style="text-align:middle"></div>     					
     					</div>
     			  </div>
     		   </div>
      		</div>
      	</div>  
      </div>
    <center>
    	<button id="repalce"  name="uploadImage" class="upload-button" >点击检查文件</button>
    	<button id="btn2"     class="upload-button" >点击上传文件</button>	
    	<button id="diagnose" name="diagnose"  class="upload-button" style="visibility: hidden" onclick="window.location.href='diagnose.action'">进行辅助诊断</button>	
    </center>   	   
</div>
<script type="text/javascript">  
	//用来存放服务器端传过来文件库的数据
	var patientName ;
	var a;
	//用来存放不符合规定的文件条目
    var btn = document.getElementById("repalce");  
    btn.onclick =function(){  
    	/* $.get("PacientFileNameServlet",function(data,status)
    	{
   			patientName = data;
   			a = patientName;
   		}); */
    	document.getElementById("file_upload_1-button").click();    	
    };
</script> 
<script type="text/javascript">

    $(function(){
    	var up = $('#upload').Huploadify({
    		auto:false,
    		fileTypeExts:'*.zip',
    		multi:false,
    		fileSizeLimit:5120000,//允许上传的文件大小，单位KB
    		showUploadedPercent:true,
    		showUploadedSize:true,
    		removeTimeout:10000000,
    		uploader:"http://localhost:8080/SH/UploadServlet",
    		onUploadStart:function(file){
    			console.log(file.name+'开始上传');
    		},
    		onInit:function(obj){
    			console.log('初始化');
    			console.log(obj);
    		},
    		onUploadComplete:function(file){
    			console.log(file.name+'上传完成');
    			$("#diagnose").attr("style","visibility: show")
    		},
    		onCancel:function(file){
    			
    		},
    		onClearQueue:function(queueItemCount){
    			console.log('有'+queueItemCount+'个文件被删除了');
    		},
    		onDestroy:function(){
    			console.log('destroyed!');
    		},
    		onSelect:function(files){
    	  		
    			console.log(files.name+'加入上传队列');
    		},
    		onQueueComplete:function(queueData){
    			console.log('队列中的文件全部上传完成',queueData);
    		}
    	});

    	$('#btn2').click(function(){
    		up.upload('*');
    	});
    	$('#btn3').click(function(){
    		up.cancel('*');
    	});
    	$('#btn4').click(function(){
    		//up.disable();
    		up.Huploadify('disable');
    	});
    	$('#btn5').click(function(){
    		up.ennable();
    	});
    	$('#btn6').click(function(){
    		up.destroy();
    	});
    });
</script> 
</body>
</html>


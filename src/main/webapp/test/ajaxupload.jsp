<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${pageContext.request.contextPath }/static/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath }/static/js/ajaxfileupload.js"></script>
<script src="${pageContext.request.contextPath }/static/js/layer/layer.js"></script>
 
<script src="${pageContext.request.contextPath }/static/js/easydropdown/jquery.easydropdown.min.js"></script>
<script src="${pageContext.request.contextPath }/static/js/validate/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath }/static/js/validate/validate-methods.js"></script>
<script src="${pageContext.request.contextPath }/static/js/My97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath }/static/js/ajaxfileupload.js" charset="UTF-8"></script>

	
<script type="text/javascript">
var staticpath="${pageContext.request.contextPath}/";
	/* $.handleError = function(a, b, c, e) {
		alert("handlerrror::"+e.toString());
	} */
	
	
/* 	function SetupFileUpload(fileElementId)
	{
		 $('#'+fileElementId).ajaxfileupload({
		  action: "/lqzp2/file/upload.do",
		  valid_extensions : ['jpg','png','jpeg','bmp'],
		  onComplete: function(data) 
		  {
			 alert(data);
		     switch(data.code) 
		     {
	        	case 201:
	        		layer.msg("请选择文件");
	        		break;
	        	case 202:
	        		layer.msg("图片太大，请上传最小的1M的图片");
	        		break;
	        	case 203:
	        		layer.msg("图片上传出现异常");
	        		break;
	        	default:
	        		layer.msg("成功...");
	        		break;
		    }
		  },
		  onStart: function() {
//					    if(weWantedTo) return false; // cancels upload
		  },
		  onCancel: function() {
		    console.log('no file selected');
		  }
	  });
	}
	
	 */
	
  /* 	$(function(){
	    SetupFileUpload("backImg");
    });  */
    
  //===================================================================
	  
	  
	function changImg() {
	//	alert("upload is already");
		$.ajaxFileUpload({
			valid_extensions : ['jpg','png','jpeg','bmp'],
			url : '${pageContext.request.contextPath}/file/upload.do', //上传文件的服务端
			secureuri : false, //是否启用安全提交
			dataType : 'json', //数据类型 
			data:{imagetype:"face"},
			fileElementId : 'backImg', //表示文件域ID
			//提交成功后处理函数      html为返回值，status为执行的状态
			success : function(data, status) {
				//alert("成功...\r\n" + data.url);
				var surl =staticpath+data.url;
				alert(surl);
				$("#imgface").attr("src",surl);
				
			},
			//提交失败处理函数
			error : function(html, status, e) {
				alert("错误:"+status);
			}
		})
	}
	  
    
</script>
</head>
<body>

<div>

<div style="position:relative; width:110px; height:110px; background:none;">
	<a href="javascript:void(0)" style=" display:block; width:110px; height:110px;">
	<img id="imgface" width="100%" src="${pageContext.request.contextPath }/static/images/head.png"></a>
    <input  onchange="changImg()" id="backImg" name="backImg" type="file" style="position:absolute; cursor:pointer; left:-18px; top:0; width:110px; height:110px; opacity:0;filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0);">
</div>

</div>

</body>
</html>
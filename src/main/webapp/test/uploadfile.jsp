<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.selectmenu
{
	color:red;
}

.unselectmenu
{
  color:black
}
</style>
 
 
 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/basic.css"/>
 
<script src="${pageContext.request.contextPath }/static/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath }/static/js/ajaxfileupload.js"></script>

<script src="${pageContext.request.contextPath }/static/js/public.js"></script>
</head>
<script>
var spath = "${pageContext.request.contextPath}";
</script>
<script type="text/javascript">
	//上传文件
	function ajaxFileUploadFileOnChange() {
		$.ajaxFileUpload({
			url : "${pageContext.request.contextPath}/file/upload.do",
			// 是否启用安全提交
			secureuri : false,
			data : {
				imagetype :"222"
			},
			valid_extensions:"jpg,png,bmp",
			dataType : "json",
			// 表示文件域ID
			fileElementId : 'headUpload',
			error:function(xh,msg)
			{
				alert(msg);
			},
			success : function(resultData, resultStatus) {
				
				alert(resultData.url);
			}
		});
	}
</script>
<body>
<br/>
<br/>
<br/>
<br/>
 	<input type="file" id="headUpload" name="headUpload" onchange="ajaxFileUploadFileOnChange()" /> 
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.lanqiao.common.*" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/view/include/publicjs.jsp"%>


</head>
<body>
 <input type="button" onclick="dotest()" value="打开窗口">
</body>
</html>
<script>
var gltest= null;
 function dotest()
 {
	 
	 gltest=layer.open({
		  content: '测试回调',
		  yes: function(index, layero){
		    //do something
		    console.log(layero);
		    layer.close(index); //如果设定了yes回调，需进行手工关闭
		    
		    
		  }
		});     
 }

 
 
</script>
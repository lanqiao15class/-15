<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.lanqiao.common.*" %>

<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/view/include/publiccss.jsp"%>
<%@ include file="/WEB-INF/view/include/publicjs.jsp"%>
</head>
<body>



<br/>
<br/>
<br/>
<br/>
<br/>
<input type="button" id="testBtn" value="測試按鈕">
<script type="text/javascript">
	$(function(){
		$("#testBtn").on('click',function(){
			//将数组转换成字符串，遍历数组，移除一个元素後若还能找到该元素，那么这个元素就是重复的
			var ary = [5,5];
			var s = ary.join(",");
			console.log(s);
			for(var i=0;i<ary.length;i++) {
			    if(s.replace(ary[i]+",","").indexOf(ary[i]+",")>-1) {
			    alert("数组中有重复元素：" + ary[i]);
			    break;
			    }else{
			    	 alert();
			    }
			}
		});
	});
</script>
</body>
</html>
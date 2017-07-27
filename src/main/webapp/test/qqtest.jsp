<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath }/static/js/jquery-1.9.1.min.js"></script>
</head>
<body>
<script>
var spath = "${pageContext.request.contextPath}";
</script>


<a href="${pageContext.request.contextPath}/startqqlogin.do" target="_blank">QQ 登录</a><br/>

<a href="${pageContext.request.contextPath}/startsinalogin.do" target="_blank">Sina 登录</a><br/>


</body>
</html>
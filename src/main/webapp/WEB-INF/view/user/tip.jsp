<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">

<%@ include file="/WEB-INF/view/include/publiccss.jsp"%>
<title>蓝桥-登录</title>
</head>

<body class="grey">
<%@ include file="/WEB-INF/view/include/header_html1.jsp" %>
<script>
  var basePath="${pageContext.request.contextPath}";
</script>
<script src="${ctxStatic }/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic }/js/layer/layer.js"></script>



<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/js/public.js" />
</jsp:include>



<script>
$(function(){
	layer.msg('您是内部员工，请联系您的领导进行注册！',{time:86400000});//24小时后关闭
});
</script>


</body>
</html>

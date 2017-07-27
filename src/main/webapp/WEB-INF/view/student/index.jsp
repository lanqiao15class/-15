<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>蓝桥软件学院</title>

<%@ include file="/WEB-INF/view/include/publiccss.jsp"%>

</head>
<body>
<!--   学生首页 -->
<c:set var="usertype" value="0" />

<!--头部开始-->
<%@include file ="/WEB-INF/view/include/header_html.jsp" %>

<%@include file ="/WEB-INF/view/student/leftmenu_student.jsp" %>

<div class="main-right">
    <div class="inner-box">
        <div class="content" id="contentDiv">
        
       	
       		
        </div>
    </div>
</div>
<!--学生报名页面结束-->
</div>
<%@ include file="/WEB-INF/view/include/publicjs.jsp"%>
<script>

</script>
</body>
</html>
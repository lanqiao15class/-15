<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.lanqiao.common.*" %>

<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>

<c:set var="usertype" value="1" />
<%@ include file="/WEB-INF/view/include/publiccss.jsp"%>
<%@ include file="/WEB-INF/view/include/publicjs.jsp"%>

<script src="${ctxStatic}/js/select_bag/selectableExtend.js"></script>
</head>
<body style="margin:50px">


<br/>
<br/>
<br/>
<br/>
<br/>

<span class="select-search-box">
   <select id="testid"  name="testid" >
   </select>
</span>
 	<script>
 function MyselectItem(sid, stext)
 {
 		console.log("选中了:" + sid +" text:" + stext);
 }
 	
 $(function(){
	
	 var select_testid = new selectableExtend({
		 inputid:"testid",
		 placeholder:"测试了",
		 afterSelectItem:MyselectItem,
		 data:gl_schoolllist});
	 
	 select_testid.init();
	 
	// select_testid.selectOption("10008000","噢噢噢噢");
	 
 });
 
 
</script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lq" uri="http://www.lanqiao.org/functions"%>
 


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath }/static/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath }/static/js/public.js"></script>

</head>
<body>
<%

  /* 	String str="bbbb<br/>cccc";
  		out.println (str); */
  		Integer it[] = new Integer[]{1,2};
  		
  		request.setAttribute("myit", "1");
  		
%>
<div id="div1"> aaa fff </div>
<c:out value="nnnn<br/>大多数范德萨" ></c:out>
<div id="div1">

老师身份:${lq:getTeacherTypeName("1")} <br/>

老师身份:${lq:getTeacherTypeName("2")}

<c:if test='${lq:HasRight(SessionScope.loginuser,FunctionEnum.Fun_CollegeDelete) 
||  lq:HasRight(SessionScope.loginuser,FunctionEnum.Fun_CollegeEdit)  }'>
 <br/>  dddddddddddd
</c:if>

${ttt}
</div>
<script>
String.prototype.trim = function() { 
	  return this.replace(/(^\s*)|(\s*$)/g, ''); 
}; 
	
	
var spath = "${pageContext.request.contextPath}";
 function dotest()
 {
	var shtml = $("#div1").html();
	
	alert("["+shtml.trim()+"]");
	alert("["+shtml+"]");
	//$("#div1").html(str);
	//alert(str.toHtml());
	
 }

 
</script>
	<input type="button" value="click me" onclick="dotest()">


</body>
</html>
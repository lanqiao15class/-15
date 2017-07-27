<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath }/static/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath }/static/js/jsrender/jsrender.min.js"></script>

</head>
<body>
<script>
var spath = "${pageContext.request.contextPath}";

function dorender()
{
	$.ajax({
	type:"post",
	url:spath+"/jsrender.do",
	canback:true,
	data:{},
	dataType:"html",
	success:function(ssdata){
		
		//eval(ssdata);
		 $("#hidden_div").html(ssdata);
		 var tmpl = $.templates("#myTemplate");
		 var html = tmpl.render(data);
		 $("#contentdiv").html(html);
		 
		
		 
	}
});

}

$(function(){
	
	dorender();
});

</script>
<div id="hidden_div" style="display:none"></div>
<div id="contentdiv">
</div>

</body>
</html>
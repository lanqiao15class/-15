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
<script src="${pageContext.request.contextPath }/static/js/layer/layer.js"></script>
<script src="${pageContext.request.contextPath }/static/js/public.js"></script>
</head>
<script>
var spath = "${pageContext.request.contextPath}";
$(function(){

	//绑定菜单点击事件
	$("li[canback=true]").bind("click",function(){
		selectMenu(this);
		
	var url = $(this).attr("url");
	
		$.ajax({
			type:"post",
			url:url,
			canback:true,
			data:{},
			dataType:"html",
			success:function(data){
				$("#contentdiv").html(data);
			}
		});
		
	});
	
	// 不支持back
	$("li[canback=false]").bind("click",function(){
		selectMenu(this);
		
	var url = $(this).attr("url");
	
		$.ajax({
			type:"post",
			url:url,
			data:{},
			dataType:"html",
			success:function(data){
				$("#contentdiv").html(data);
			}
		});
		
	});
	
});


function dotest()
{
	loadingopen();	
}

function doMenu3()
{
	selectMenu($("#menu3")[0]);
	var url = $("#menu3").attr("url");
	
	$.ajax({
		type:"post",
		url:url,
		data:{},
		notloading:true,
		canback:true,
		dataType:"html",
		success:function(data){
			$("#contentdiv").html(data);
		}
	});
}
</script>

<body>
<ul>
<li url="1.html" id="menu1" canback="true">测试1</li>
<li url="/lqzp2/ajaxtest.do" id="menu2" canback="true">测试2</li>
<li url="/lqzp2/ajaxtest.do" id="menu3" onclick="doMenu3()">测试3---不提示加载层</li>
<li url="4.html" id="menu4" canback="false">测试4--不支持后退事件</li>
</ul>

<input type="button" value="test" onclick="dotest()">
<div id="contentdiv">
</div>

<a href="ajaxtest.jsp">跳转......</a>
</body>
</html>
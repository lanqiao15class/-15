<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>蓝桥软件学院</title>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/js/select_bag/jquery.searchableSelect.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/font/iconfont.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/basic.css">
</head>
<body>
<!--头部开始-->
<div class="header">
	<div class="inner-hearder">
    	<a class="logo" href="javascript:void(0)"><img src="${ctxStatic }/images/logo.png" alt=""></a>
        <div class="userInfo fr">
        </div>
    </div>
</div>
<!--头部结束-->
	<input type="hidden" id="openidType" value="${openidType}"/>
    <div class="main-right">
    	<div class="inner-box">
        	<div class="content">
            	<!--绑定成功页面开始-->
                <div class="bind-seccess-box">
                	<div class="qq-pic mb20"><img src="${ctxStatic}/images/img_head.jpg" width="200" alt=""></div>
                    <p class="mb20">
                    		恭喜,你以后就能直接通过
                    		<c:if test="${openidType==0}">
                    			腾讯QQ
                    		</c:if>
                    		<c:if test="${openidType==1}">
                    			新浪微博
                    		</c:if>
                    		登录了</p>
                    <button class="btn btn-blue" type="button" onclick="closeWindow();">关闭窗口</button>
                </div>
                <!--绑定成功页面结束-->
            </div>
        </div>
    </div>

<script src="${ctxStatic}/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic}/js/placeholder.js"></script>
<script src="${ctxStatic}/js/select_bag/jquery.searchableSelect.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="${ctxStatic}/js/effect.js"></script>
<script src="${ctxStatic}/js/jquery.cookie.js"></script>

<script>
$(".editor-btn").click(function(){
	$(".justlook").hide();
	$(".editor").hide();//隐藏编辑按钮
	$(".editmodify").addClass("inh");
});
$("#canceledit").click(function(){
	$(".justlook").show();
	$(".editor").show();//显示编辑按钮
	$(".editmodify").removeClass("inh");	
	$(".item_tips").html("");
});

//设置cookie,7天
$.cookie('openidType', $("#openidType").val(),{ expires: 7, path: '/' });


function closeWindow(){
	window.close();
}
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>蓝桥软件学院-重置密码</title>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/js/select_bag/jquery.searchableSelect.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/font/iconfont.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/basic.css">
</head>
<body>
<input type="hidden" id="basePath" value="${ctxBase }" />
<!--头部开始-->
<%@ include file="/WEB-INF/view/include/header_html.jsp"%>
<!--头部结束-->
<!--重置密码开始-->
<div class="accont-box user-accont-box">
	<h1 class="accont-tit">重置密码</h1>
    <div class="accont-form-box">
    <form id="pwdForm" action="${ctxBase}/user/toUpdatePwd.do" method="post" novalidate>
    	<input type="hidden" id="userId" name="userId"/>
		<input type="hidden" id="loginTel" name="loginTel"/>
		<input type="hidden" id="loginEmail" name="loginEmail"/>
		<input type="hidden" id="type" name="type"/>
    	<p class="fs16 accont-tips-p">请输入注册的手机号、邮箱</p>
    	<div class="lineRow">
            <div class="rowPart">
            <input id="userName" name="userName" disableautocomplete autocomplete="off" type="text" placeholder="请输入您的手机号、邮箱">
            </div>
            <p class="wrongTips"></p>
        </div>
        <div class="lineRow">
            <div class="rowPart">
                <input disableautocomplete autocomplete="off" type="text" class="codeVer" placeholder="请输入图片验证码" id="validateCode" name="validateCode">
                <span class="verPic"><img id="validateCodeImg" src="${ctxBase}/user/validateCode/resetPwd.do" width="100%"></span>
                <a class="code-link" href="javascript:PWD.refreshCode();">看不清？换一张</a>
            </div>
            <p class="wrongTips"></p>
        </div>
        <button type="submit" class="btn btn-blue login-btn" id="nextBtn">下一步</button>
    </form>
    </div>
</div>
<!--重置密码结束-->
<script src="${ctxStatic}/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic}/js/validate/jquery.validate.js"></script>
<script src="${ctxStatic}/js/placeholder.js"></script>
<script src="${ctxStatic}/js/select_bag/jquery.searchableSelect.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="${ctxStatic}/js/effect.js"></script>
<script src="${ctxStatic}/myjs/user/resetPwd.js"></script>
<script type="text/javascript">
$(function(){
	var basePath = $("#basePath").val();
	PWDRESET.initPwdCheck();
	
})
	
</script>
</body>
</html>
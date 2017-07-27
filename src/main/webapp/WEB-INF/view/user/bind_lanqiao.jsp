<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<%@ include file="/WEB-INF/view/include/publiccss.jsp"%>
<title>蓝桥软件学院-重置密码</title>
</head>
<body>
<%@ include file="/WEB-INF/view/include/header_html.jsp" %>
<!--账号绑定开始-->
<form id="loginForm" method="post">

<input type="hidden" id="openid" value="${openid }" name="openid" />

<div class="accont-box user-accont-box">
	<h1 class="accont-tit">绑定您的账户</h1>
    <div class="accont-form-box">
        <div class="lineRow">
            <div class="rowPart">
            <input id="userName" name="userName" disableautocomplete autocomplete="off" type="text" placeholder="请输入您的手机号、邮箱">
            </div>
            <p class="wrongTips"></p>
        </div>
        <div class="lineRow">
            <div class="rowPart">
                <input id="password" name="password" disableautocomplete autocomplete="off" type="password" placeholder="请输入您的密码">
            </div>
            <p class="wrongTips"></p>
        </div>
        <div class="lineRow">
            <div class="rowPart">
                <input id="validateCode" name="validateCode" disableautocomplete autocomplete="off" type="text" class="codeVer" placeholder="请输入图片验证码" id="validateCode" name="validateCode">
                <span class="verPic"><img id="validateCodeImg" src="${ctxBase }/user/validateCode/login.do" width="100%"></span>
                <a class="code-link" href="javascript:USER.refreshCode();">看不清？换一张</a>
            </div>
            <p class="wrongTips"></p>
        </div>
        <button type="submit" class="btn btn-blue login-btn" id="loginBtn">确定 </button>
        <div class="clearfix mt10">
            <span class="fl">
            	如当前没有蓝桥账户
            	<a class="c-blue" target="_blank" href="javascript:window.location.href='${ctxBase}/regist/goRegist.do'">注册新用户</a>
            </span>
        </div>
    </div>
</div>
</form>
<!--账号绑定结束-->
<%@ include file="/WEB-INF/view/include/publicjs.jsp"%>
<script>
$(function(){
	Login.init();
});
</script>
</body>
</html>
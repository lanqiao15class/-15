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
    <form id="pwdForm" action="${ctxBase}/user/updatePwd.do" method="post" novalidate>
    	<p class="fs16 accont-tips-p">您正在使用
    	<c:choose>
			<c:when test="${type eq '0' }">
				短信验证码
			</c:when>
			<c:otherwise>
				邮件验证码
			</c:otherwise>
		</c:choose>
    	验证身份，验证码将以
    	<c:choose>
			<c:when test="${type eq '0' }">
				短信
			</c:when>
			<c:otherwise>
				邮件
			</c:otherwise>
		</c:choose>
    	方式发送至
    	<c:choose>
			<c:when test="${type eq '0' }">
				<i class="c-blue">${loginTel }</i>手机号
			</c:when>
			<c:otherwise>
				<i class="c-blue">${loginEmail }</i>邮箱
			</c:otherwise>
		</c:choose>
    	</p>
    	<div class="lineRow">
            <div class="rowPart">
                <input disableautocomplete autocomplete="off" id="validateCode" name="validateCode" type="text" class="smscodeVer" placeholder="请输入验证码" >
                <button hasGet='true' type="button" class="btn btn-blue btn-getcode" id="sendCodeBtn">获取验证码</button>
            </div>
            <p class="wrongTips"></p>
        </div>
        <div class="lineRow">
            <div class="rowPart">
                <input disableautocomplete autocomplete="off" id="pwd" name="pwd" type="password" placeholder="请输入您的密码">
            </div>
            <p class="wrongTips"></p>
        </div>
        <div class="lineRow">
            <div class="rowPart">
                <input disableautocomplete autocomplete="off" id="rePwd" name="rePwd" type="password" placeholder="请再次输入您的密码">
            </div>
            <p class="wrongTips"></p>
        </div>
        <p class="fs12 mb40">密码长度8~16位，其中数字，字母和符号至少包含两种</p>
        <button type="submit" class="btn btn-blue login-btn">重置密码</button>
        <input type="hidden" id="userId" name="userId" value="${userId }" />
   		<input type="hidden" id="tel" name="tel" value="${loginTel }" />
   		<input type="hidden" id="email" name="email" value="${loginEmail }" />
   		<input type="hidden" id="type" name="type" value="${type }" />
    </form>
    <div class="mt10 center"><a href="${ctxBase }/user/toResetPwd.do" class="c-blue">重新选择验证方式</a></div>
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
	PWDRESET.initPwdUpdate();	
	
})
	
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!-- 初始信息开始 -->
	<input type="hidden" id="userId" value="${userId }" />
	<input type="hidden" id="originTel" value="${originTel}" />
	<input type="hidden" id="email" value="${email }" />
	<input type="hidden" id="way" value="${way}"/>
	<input type="hidden" id="frequent" value="0">
<!-- 初始信息结束 -->
<!--修改密码开始-->
<div class="accont-box user-accont-box">
	<h1 class="accont-tit">修改密码</h1>
    <div class="accont-form-box  user-accont-box">
        <!-- 以手机号方式修改密码开始 -->
	    <form id="telPwdForm">
	    	<p class="fs16 accont-tips-p">您正在使用短信验证码验证身份，验证码将以短信方式发送至<i class="c-blue"><c:out value="${tel}"></c:out></i>手机号</p>
	    	<div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="text" class="smscodeVer" placeholder="请输入验证码" id="msgValidateCode" name="msgValidateCode">
	                <button type="button" class="btn btn-blue btn-getcode" id="telSendCodeBtn">获取验证码</button>
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="password" placeholder="请输入您的原始密码" id="oldPwd" name="oldPwd">
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="password" placeholder="请输入您的新密码" id="pwd" name="pwd">
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="password" placeholder="请再次输入您的新密码" id="rePwd" name="rePwd">
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <p class="fs12 mb40">密码长度8~16位，其中数字，字母和符号至少包含两种</p>
	        <button type="submit" class="btn btn-blue login-btn" id="telPwdBtn">修改密码</button>
	    </form>
    <!-- 以手机号方式修改密码结束-->
    <!-- 以邮箱方式修改密码开始 -->
	    <form id="emailPwdForm">
	    	<p class="fs16 accont-tips-p">您正在使用邮件验证码验证身份，验证码将以邮件方式发送至<i class="c-blue"><c:out value="${email}"></c:out></i>邮箱</p>
	    	<div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="text" class="smscodeVer" placeholder="请输入验证码" id="emailValidateCode" name="emailValidateCode">
	                <button class="btn btn-blue btn-getcode" id="emailSendCodeBtn" type="button">获取验证码</button>
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="password" placeholder="请输入您的原始密码" id="eOldPwd" name="eOldPwd">
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="password" placeholder="请输入您的新密码" id="ePwd" name="ePwd">
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="password" placeholder="请再次输入您的新密码" id="erePwd" name="erePwd">
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <p class="fs12 mb40">密码长度8~16位，其中数字，字母和符号至少包含两种</p>
	        <button type="submit" class="btn btn-blue login-btn" id="emailPwdBtn">修改密码</button>
	    </form>
    <!-- 以邮箱方式修改密码结束 -->
    <div class="mt10 center"><a href="javascript:void(0)" class="c-blue" id="chooseValidWay">重新选择验证方式</a></div>
    </div>
</div>
<!--修改密码结束-->
<script>
$(function(){
	PWD.init();	
});
</script>
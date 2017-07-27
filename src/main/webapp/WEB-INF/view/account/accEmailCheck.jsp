<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!--重置密码开始-->
<div class="accont-box user-accont-box">
	<h1 class="accont-tit">验证邮箱</h1>
    <div class="accont-form-box">
    <form id="checkForm">
    	<p class="fs16 accont-tips-p">您正在使用邮件验证码验证身份，验证码将以邮件方式发送至<i class="c-blue">${loginEmail }</i>邮箱</p>
    	<div class="lineRow">
            <div class="rowPart">
                <input disableautocomplete autocomplete="off" type="text" class="smscodeVer" placeholder="请输入验证码" id="validateCode" name="bindCode">
                <button type="button" hasGet='true' class="btn btn-blue btn-getcode" id="emailSendCodeBtn">获取验证码</button>
            </div>
            <p class="wrongTips"></p>
        </div>
        <button type="submit" class="btn btn-blue login-btn" id="nextBtn">下一步</button>
        <input type="hidden" id="email" name="email" value="${email }" /> 
		<input type="hidden" id="type" name="type" value="${type }" />
		<input type="hidden" id="tipType" name="tipType" value="${tipType }" />
    </form>
    <div class="mt10 center"><a href="javascript:void(0)" id="returnBtn" class="c-blue">重新选择验证方式</a></div>
    </div>
</div>
<!--重置密码结束-->

<script type="text/javascript">
	$(function(){
		CHECKBINDCODE.clearTimer();//清除定时器
		CHECKBINDCODE.initEmailCheck();
	});
</script>


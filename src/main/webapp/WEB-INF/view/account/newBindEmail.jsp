<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<input type="hidden" id="newAccountSaved">

<!--账号绑定开始-->
<div class="accont-box user-accont-box">
	<h1 class="accont-tit">绑定邮箱</h1>
    <div class="accont-form-box">
		<form id="checkForm">
	        <div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="text" class="smscodeVer" placeholder="请输入邮箱" id="newAccount" name="newAccount">
	                <button type="button" class="btn btn-blue btn-getcode" id="emailSendCodeBtn" name="showCodeSpecial">获取验证码</button>
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="text" placeholder="请输入邮箱验证码" id="remoteValidateCode" name="remoteValidateCode">
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <div class="lineRow">
	            <div class="rowPart">
	                <input disableautocomplete autocomplete="off" type="text" class="codeVer" placeholder="请输入图片验证码" id="validateCode" name="validateCode">
	                <span class="verPic"><img id="validateCodeImg" src="${ctxBase }/user/validateCode/bindEmail.do" width="100%"></span>
	                <a class="code-link" href="javascript:NEWBINDCODE.refreshCode('2');">看不清？换一张</a>
	            </div>
	            <p class="wrongTips"></p>
	        </div>
	        <button type="submit" class="btn btn-blue login-btn" id="saveBtn">保存 </button>
	        <button type="button" class="mt15 btn btn-blue login-btn" id="returnSourceBtn">返回 </button>
	        <div class="clearfix mt10">
	            <span class="fl">如当前没有蓝桥账户<a class="c-blue" target="_blank" href="javascript:window.location.href='regist.html'">注册新用户</a></span>
	        </div>
	    </form>
    </div>
</div>
<!--账号绑定结束-->
<script type="text/javascript">
		NEWBINDCODE.refreshCode('2');//页面加载的同时，验证码刷新一次（js早已全部加载）
	$(function(){
		NEWBINDCODE.clearTimer();//清除定时器 
		NEWBINDCODE.initEmailCheck();
	});
</script>



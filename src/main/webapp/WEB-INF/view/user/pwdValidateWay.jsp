<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!-- 初始化信息开始 -->
<input type="hidden" id="originTel" value="${tel}">
<input type="hidden" id="loginEmail" value="${email}">
<!-- 初始化信息结束 -->
<!--修改密码选择验证方式开始-->
<div class="accont-box user-accont-box">
	<h1 class="accont-tit">修改密码</h1>
    <div class="accont-form-box">
    	<p class="fs16 accont-tips-p">请在下面的选项中选择一种修改密码的方式，以修改
	    	<i class="c-blue">
		    	 <c:if test="${not empty nickname}">
		              <c:out value="${nickname}" /> 
		         </c:if>
		         <!-- 昵称为空则显示userid -->
		        	<c:if test="${empty nickname}">
		         	<c:out value="${userId}" />
		      	</c:if>
	    	</i>的密码
    	</p>
    	<c:if test="${not empty tel}">
	        <div class="mt20 mb20">
	            <label way="0"><input type="radio"  name="way" id="telCheck"><span class="lab-name-word">获取手机验证码</span></label>
	            <p class="pl20">我们会将验证码发送至您${tel}手机号</p>
	        </div>
        </c:if>
        <c:if test="${not empty email}">
	        <div class="mt20 mb20">
	            <label way="1"><input type="radio"  name="way" id="emailCheck"><span class="lab-name-word">获取邮箱验证码</span></label>
	            <p class="pl20">我们会将验证码发送至您${email}邮箱</p>
	        </div>
        </c:if>
        <button type="button" class="btn btn-blue login-btn mt15" id="nextBtn">下一步</button>
        <button type="button" class="btn btn-blue login-btn mt15" id="returnBtn">取消</button>
    </div>
</div>
<!--修改密码选择验证码结束-->
<script>
$(function(){
	VALIDATEWAY.init();
});
</script>
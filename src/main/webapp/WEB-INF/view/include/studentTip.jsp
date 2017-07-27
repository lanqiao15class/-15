<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!-- 顶部状态条   start -->
<c:if test="${not empty showTips}">
	<c:if test="${showTips == 'noSignUp' }">
		<div class="warn"><span class="warn-tip-words">请注意，您尚未报名学习，请先</span><a class="ml10 btn btn-green" href="javascript:void(0)" onclick="goSignUpBox();">去报名</a></div> 
	</c:if>
	<c:if test="${showTips == 'notMatchInfo'}">
		<div class="warn"><span class="warn-tip-words">请注意，您的报名姓名与身份证信息不相符，需要修改资料</span><a class="ml10 btn btn-green" href="javascript:void(0)" onclick="goSignUpBox();">重新报名</a></div> 
	</c:if>
	<c:if test="${showTips ==  'noPaidMoney'}">showTips:${ showTips}
		<div class="warn">请注意，您的报名费还未缴纳，请及时联系您的顾问老师缴费</div> 
	</c:if>
	<c:if test="${showTips ==  'bothRefused'}">
		<div class="warn"><span class="warn-tip-words">请注意，您的报名姓名与身份证信息不相符，需要修改资料，且报名费还未缴纳，请及时联系您的顾问老师缴费</span><a class="ml10 btn btn-green" href="javascript:void(0)" onclick="goSignUpBox();">重新报名</a></div> 
	</c:if>
	<c:if test="${showTips == 'waitForCheck' }">
		<div class="warn">您的报名资料已提交，请耐心等待顾问老师审核</div> 
	</c:if>
</c:if>
<!-- 顶部状态条  end -->

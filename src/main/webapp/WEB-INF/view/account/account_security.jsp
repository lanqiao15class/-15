<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<div class="accont-inner-box">
<!--个人信息页面开始-->
<div class="user-info-basic">    
    <div class="tit-h1-box">
       <h1 class="tit-first">
           <span>账户管理</span>
       </h1>
     </div>
     <div class="tit-h2 clearfix">
       <div class="tab-change-inner tab-accont-change fl account-inner-content">
           <a href="javascript:void(0)" id="userInfo">个人信息</a>
            <a class="curr" href="javascript:void(0)" id="account">账户安全</a>
            <a href="javascript:void(0)" id="bind">帐号绑定</a>
       </div>
     </div>
<!-- 账户安全开始 -->
  <div class="accont-tabbox user-info-savety mt30" style="display:block;">
    <ul class="user-safe-ulbox">
        <li>
            <span class="user-safe-tit">账户密码</span>
            <span class="user-safe-middle">
            	<i class="fs12 safe-middle-text">用于保护账号信息和登录安全</i>
            </span>
            <span class="user-safe-bind">
            	<button href="javascript:void(0)" onclick="window.open('<%=GlobalConstant.SSOURL%>user/toResetPwd.do')" class="btn btn-green">修改</button>
            </span>
        </li>
        <%-- <li class="clearfix">
            <span class="user-safe-tit">安全手机</span>
            <c:choose>
            	<c:when test="${empty loginTel }">
		            <span class="user-safe-middle">
		            	<i class="fs12 safe-middle-text">安全手机可用于登录，重置密码</i>
		            </span>
		            <span class="user-safe-bind">
		            	<button href="javascript:void(0)" class="btn btn-green" onclick="ACCOUNT_SECURITY.jumpUrl('${ctxBase}/account/toChooseType/0/0.do');">绑定</button>
		            </span>
		        </c:when>
		        <c:otherwise>
		       		<span class="user-safe-middle">
		            	<i class="fs12 user-bind-accontnum">${ loginTel}</i>
		            	<i class="fs12 safe-middle-text">若该手机号丢失或停用，建议立即修改</i>
		            </span>
		            <span class="user-safe-bind">
		            	<button href="javascript:void(0)" class="btn btn-green" onclick="ACCOUNT_SECURITY.jumpUrl('${ctxBase}/account/toChooseType/0/1.do');">修改</button>
		            </span>
		        </c:otherwise>
            </c:choose>
        </li>
        <li class="clearfix">
            <span class="user-safe-tit">安全邮箱</span>
            <c:choose>
            	<c:when test="${empty loginEmail }">
		            <span class="user-safe-middle">
		            	<i class="fs12 safe-middle-text">安全邮箱可用于登录，重置密码</i>
		            </span>
		            <span class="user-safe-bind">
		            	<button href="javascript:void(0)" class="btn btn-green" onclick="ACCOUNT_SECURITY.jumpUrl('${ctxBase}/account/toChooseType/1/0.do');">绑定</button>
		            </span>
		        </c:when>
		        <c:otherwise>
		       		<span class="user-safe-middle">
		            	<i class="fs12 user-bind-accontnum">${ loginEmail}</i>
		            	<i class="fs12 safe-middle-text">若该邮箱丢失或停用，建议立即修改</i>
		            </span>
		            <span class="user-safe-bind">
		            	<button href="javascript:void(0)" class="btn btn-green" onclick="ACCOUNT_SECURITY.jumpUrl('${ctxBase}/account/toChooseType/1/1.do');">修改</button>
		            </span>
		        </c:otherwise>
            </c:choose>
        </li> --%>
    </ul>
</div>
  <!-- 账户安全结束-->
</div>
<!--个人信息页面结束-->
</div>
<script>
$(function(){
ACCOUNT_SECURITY.init();
});
</script>

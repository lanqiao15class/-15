<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
	
<div style="background:#56a0ee" class="header">
	<div class="inner-hearder">
    	<a class="logo" href="http://xueyuan.lanqiao.org/"><img src="${ctxStatic }/images/logo.png" alt=""></a>
        <div class="userInfo fr">
               
          <label class="pointer">
          <c:if test="${not empty realname }">
                您好,<label  id="gl_realName">${realname } </label>
                   <a class="outside-btn ml10" title="退出登录" href="${ctxBase }/sso/exitlogin.do">退出</a>
          </c:if>
            </label>
           
        </div>
    </div>
</div>
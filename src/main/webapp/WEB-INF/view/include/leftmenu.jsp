<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.lanqiao.constant.GlobalConstant"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
	<div class="nav-left">
        <div class="nav-left-inner">
            <div class="user-inf">
                <div class="clearfix show">
                    <div class="head-img">
                    <c:if test="${empty sessionScope.loginuser.photo }">
                      <img id="gl_photoimage"  src="<%=GlobalConstant.defaultheadface %>">
                    </c:if>
                  
                   <c:if test="${not empty sessionScope.loginuser.photo }">
                      <img id="gl_photoimage" src=" ${ctxResource}${sessionScope.loginuser.photo}" >
                    </c:if>
                   
                    </div>
                    <div class="nick-role">
                        <label id="gl_realName_menu">${realname}</label>
                        <span>${identityname }</span>
                        
                    </div>
                </div>
                <!--<a class="set-btn-icon hide" style="display:none;" href="javascript:void(0)"><i class="Hui-iconfont">&#xe61d;</i></a>-->
            </div>
            <div class="nav-menu">

               
<c:forEach var="one" items="${sessionScope.loginuser.sysmenus }" >
                <dl>
                    <dt>
                        <a class="nav-line-row" href="javascript:void(0);">
                            <i class="${one.icon }"></i><span class="nav-content">${one.name }</span><i class="icon-tro Hui-iconfont">&#xe6d7;</i>
                        </a>
                    </dt>
                    <dd class="nav-sub">
                  	<c:set var="sublength" value="${fn:length(one.sortSubMenus)}" />
                       <c:forEach var="subone" items="${one.sortSubMenus }" varStatus="status">
                        
                        <a id='m${subone.id }' <c:if test="${status.index == sublength-1 }" >class='last'</c:if> data-href='${subone.href}' href="javascript:void(0);">${subone.name }</a>
                     </c:forEach>
                    </dd>
                </dl>
</c:forEach>

                <dl>
                    <dt>
                        <a class="nav-line-row" href="javascript:void(0);">
                            <i class="icon i-store"></i><span class="nav-content">账户管理</span><i class="icon-tro Hui-iconfont">&#xe6d7;</i>
                        </a>
                    </dt>
                    <dd class="nav-sub account-nav-sub">
                        <a id='m01222'  data-href='/user/goUserInfo.do' href="javascript:void(0);">个人信息</a>
                        <a id='m01322'  data-href='/account/accountPage.do' href="javascript:void(0);">账号安全</a>
                        <a class="last" id='m01422'  data-href='/goAccountBinding.do' href="javascript:void(0);">账号绑定</a>
                    </dd>
                </dl>

            </div>
        </div>
    </div>
    <script>
    
    </script>
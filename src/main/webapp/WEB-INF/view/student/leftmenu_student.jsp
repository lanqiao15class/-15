<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="com.lanqiao.constant.GlobalConstant"%>
	<div class="nav-left">
        <div class="nav-left-inner">
            <div class="user-inf">
                <div class="clearfix show" style="display:none;">
                    <div class="head-img">
                    <c:if test="${empty sessionScope.loginuser.photo }">
                        <img id="gl_photoimage" src="<%=GlobalConstant.defaultheadface %>">
                    </c:if>
                  
                   <c:if test="${not empty sessionScope.loginuser.photo }">
                      <img id="gl_photoimage" src=" ${ctxResource}${sessionScope.loginuser.photo}" >
                    </c:if>
                   
                    </div>
                    <div class="nick-role">
                        <label id="gl_realName_menu">${realname }</label>
                        <span>${identityname }</span>
                    </div>
                </div>
            </div>
            <div class="nav-menu">
               
                <dl class="current">
                    <dt>
                        <a class="nav-line-row" href="javascript:void(0);">
                            <i class="icon i-file"></i><span class="nav-content">个人资料</span><i class="icon-tro Hui-iconfont">&#xe6d7;</i>
                        </a>
                    </dt>
                    <dd class="nav-sub" style="display:inherit;">
                        <a class="current" id='005' data-href='/student/goBasic.do' href="javascript:void(0);">基本信息</a>
                        <a id='006' data-href='/student/golink.do' href="javascript:void(0);" class="last">联系方式</a>
                    </dd>
                </dl>
                
                <dl>
                    <dt>
                        <a class="nav-line-row" href="javascript:void(0);">
                            <i class="icon i-info"></i><span class="nav-content">我的班级</span><i class="icon-tro Hui-iconfont">&#xe6d7;</i>
                        </a>
                    </dt>
                    <dd class="nav-sub">
                        <a id='007' data-href='/stu/goMyClassInfo.do' href="javascript:void(0);" class="last">班级信息</a>
                    </dd>   
                </dl>
                <dl>
                    <dt>
                        <a class="nav-line-row" href="javascript:void(0);">
                            <i class="icon i-rent"></i><span class="nav-content">就业服务</span><i class="icon-tro Hui-iconfont">&#xe6d7;</i>
                        </a>
                    </dt>
                    <dd class="nav-sub">
                        <a id='012' data-href='/jobVacancy/showJob.do' href="javascript:void(0);" class="last">招聘职位</a>
                        <a id='011' data-href='/stu/goMyWorkExperience.do' href="javascript:void(0);" class="last">我的职业经历</a>
                    
                    </dd>
                    
                    
                </dl>
               
                
                <dl>
                    <dt>
                        <a class="nav-line-row" href="javascript:void(0);">
                            <i class="icon i-store"></i><span class="nav-content">账户管理</span><i class="icon-tro Hui-iconfont">&#xe6d7;</i>
                        </a>
                    </dt>
                    <dd class="nav-sub account-nav-sub">
                        <a id='017'  data-href='/user/goUserInfo.do' href="javascript:void(0);">个人信息</a>
                        <a id='018'  data-href='/account/accountPage.do' href="javascript:void(0);">账号安全</a>
                        <a id='019'  data-href='/goAccountBinding.do' href="javascript:void(0);" class="last">账号绑定</a>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
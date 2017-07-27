<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>蓝桥软件学院-注册</title>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/js/select_bag/jquery.searchableSelect.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/font/iconfont.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/basic.css">
</head>
<body class="grey">
<input type="hidden" id="basePath" value="${ctxBase}" />

<%@ include file="/WEB-INF/view/include/header_html.jsp"%>

<div class="user-accont-box">
	<div class="regist">
    	<div class="tab-change tab-change-regist">
            <a class="curr" href="javascript:void(0)"><i class="icon-regist Hui-iconfont">&#xe696;</i><span>手机注册</span></a>
            <a href="javascript:void(0)"><i class="icon-regist Hui-iconfont">&#xe68a;</i><span>邮箱注册</span></a>
            <span class="tab_active tab_active_regist"></span>
        </div>
        <div class="tab-content">
        
        	<!-- 手机注册  start -->
        	<div class="tab-inner" style="display:block;">
            	<form id="telRegister" autocomplete="off" novalidate enctype="multipart/form-data">
                    <div class="lineRow">
                        <div class="rowPart">
                        	<input id="tel" name="tel" disableautocomplete autocomplete="off" type="text" placeholder="请输入手机号码">
                        	<input type="hidden" id="telTemp" name="telTemp"/>
                        </div>
                        <p class="wrongTips"></p>
                    </div>
                    <div class="lineRow">
                        <div class="rowPart">
                            <input id="jqauthcode" name="jqauthcode" disableautocomplete autocomplete="off" type="text" class="codeVer" placeholder="请输入图片验证码">
                            <span class="verPic"><img id="validateCodeImg" src="${ctxBase}/user/validateCode/login.do" width="100%"></span>
                            <a class="code-link" href="javascript:REGIST.refreshCode();">看不清？换一张</a>
                        </div>
                        <p class="wrongTips"></p>
                    </div>
                    <div class="lineRow">
                        <div class="rowPart">
                            <input id="authcode" name="authcode" disableautocomplete autocomplete="off" type="text" class="smscodeVer" placeholder="请输入短信验证码">
                            <button id="getVer" type="button" class="btn btn-blue btn-getcode" onclick="REGIST.sendRegisterVerificationCode();">获取验证码</button>
                        </div>
                        <p class="wrongTips"></p>
                    </div>
                    <div class="lineRow">
                        <div class="rowPart">
                            <input name="pwd" id="pwd" disableautocomplete autocomplete="off" type="password" placeholder="请输入密码">
                        </div>
                        <p class="wrongTips"></p>
                    </div>
                    <div class="lineRow">
                        <div class="rowPart">
                            <input name="cpwd" id="cpwd" disableautocomplete autocomplete="off" type="password" placeholder="请再次输入密码">
                        </div>
                        <p class="wrongTips"></p>
                    </div>
                    <div class="lineRow">
                    	<div class="rowPart">
                    		<input id='agreement' type="checkbox" checked="checked" name="telXY"/>
                    	 	<span>我已阅读并同意
                    	 	<a href="javascript:void(0);" class="c-blue"  onclick="window.open('${ctxBase }/regist/goRegisterAgreement.do')">《用户协议》</a>
                    	 	</span>
                    	</div>
                    	<p class="wrongTips"></p>
                    </div>
                    <button type="submit" class="btn btn-blue login-btn" id="loginBtn">注册</button>
                </form>
            </div>
            <!-- 手机注册  end  -->
            
            
            <!-- 邮箱注册  start -->
            <div class="tab-inner">
            	<form id="emailRegister" autocomplete="off" novalidate enctype="multipart/form-data">
                    <div class="lineRow">
                        <div class="rowPart">
                        	<input id="email" name="email" disableautocomplete autocomplete="off" type="text" placeholder="请输入您的邮箱">
                        	<input type="hidden" id="emailTemp"/>
                        </div>
                        <p class="wrongTips"></p>
                    </div>
                    <div class="lineRow">
                        <div class="rowPart">
                            <input name="emailauthcode" disableautocomplete autocomplete="off" type="text" class="smscodeVer" placeholder="请输入验证码" id="" name="validateCode">
                            <button id="getVerEmail" type="button" class="btn btn-blue btn-getcode" onclick="REGIST.sendEmailRegisterCode();">获取验证码</button>
                        </div>
                        <p class="wrongTips"></p>
                    </div>
                    <div class="lineRow">
                        <div class="rowPart">
                            <input name="ePwd" id="ePwd"  disableautocomplete autocomplete="off" type="password" placeholder="请输入您的密码">
                        </div>
                        <p class="wrongTips"></p>
                    </div>
                    <div class="lineRow">
                        <div class="rowPart">
                            <input name="ecPwd" id="ecPwd" disableautocomplete autocomplete="off" type="password" placeholder="请再次输入您的密码">
                        </div>
                        <p class="wrongTips"></p>
                    </div>
                    <div class="lineRow">
                    	<div class="rowPart">
                    		<input id='emailagreement' type="checkbox" checked="checked" name="emailXY"/>
                    	 	<span>我已阅读并同意
                    	 	<a href="javascript:void(0);" class="c-blue"  onclick="window.open('${ctxBase }/regist/goRegisterAgreement.do')">《用户协议》</a>
                    	 	</span>
                    	</div>
                    	<p class="wrongTips"></p>
                    </div>
                    <button type="submit" class="btn btn-blue login-btn" id="loginBtn">注册</button>
                </form>
            </div>
            <!-- 邮箱注册  end  -->
            
            <div class="mt10 center"><a href="javascript:window.location.href='<%=GlobalConstant.SSOURL%>login.do?appid=<%=GlobalConstant.appid%>&backurl=<%=GlobalConstant.GlobalWebURL%>'" class="c-blue">已有账号，直接登录</a></div>
        </div>
    </div>
</div>
<script src="${ctxStatic}/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic}/js/placeholder.js"></script>
<script src="${ctxStatic}/js/select_bag/jquery.searchableSelect.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="${ctxStatic}/js/effect.js"></script>
<script src="${ctxStatic}/js/validate/jquery.validate.js"></script>
<script src="${ctxStatic}/js/validate/validate-methods.js"></script>
<script src="${ctxStatic}/myjs/user/regist.js"></script>
</body>
</html>
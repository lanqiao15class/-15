<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">

<%@ include file="/WEB-INF/view/include/publiccss.jsp"%>
<title>蓝桥-登录</title>
</head>

<body class="grey">
<%@ include file="/WEB-INF/view/include/header_html.jsp" %>

<form id="loginForm" method="post">

<input type="hidden" name="backurl" value="${backurl }" />

<div class="login-box user-accont-box">
    <div class="lineRow">
        <div class="rowPart">
        <input  id="userName" name="userName"  disableautocomplete autocomplete="off" type="text" placeholder="请输入您的手机号、邮箱">
        </div>
        <p class="wrongTips"></p>
    </div>
    
    <div class="lineRow">
        <div class="rowPart">
            <input  id="password" name="password" disableautocomplete autocomplete="off" type="password" placeholder="请输入您的密码">
        </div>
        <p class="wrongTips"></p>
    </div>
    <div class="lineRow">
        <div class="rowPart">
            <input  id="validateCode" name="validateCode" disableautocomplete autocomplete="off" type="text" class="codeVer" placeholder="请输入图片验证码" >
            <span class="verPic"><img id="validateCodeImg" src="${ctxBase }/user/validateCode/login.do" width="100%"></span>
            <a class="code-link" href="javascript:Login.refreshCode();">看不清？换一张</a>
        </div>
        <p  class="wrongTips" ></p>
    </div>
    <button type="submit" class="btn btn-blue login-btn" id="loginBtn" >登录 </button>
     <p id="alltip" class="wrongTips" ></p>
     
    <div class="clearfix mt10">
        <span class="fl"><a class="c-blue" href="${ctxBase}/regist/goRegist.do">注册新用户</a></span>
        <a class="fr c-blue" href="${ctxBase}/user/toResetPwd.do">忘记密码</a>
    </div>
    <div class="line-center">
        <p class="line"></p>
        <p class="txt">其他账号登录</p>
    </div>
    <div class="other-way clearfix">
        <!--<a href="" class="i weixin"></a>-->
        <a href="${ctxBase}/startsinalogin.do" class="i weibo"></a>
        <a href="${ctxBase}/startqqlogin.do" class="i qq"></a>
    </div>
</div>
</form>
<script>
  var basePath="${pageContext.request.contextPath}";
</script>
<script src="${ctxStatic }/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic }/js/layer/layer.js"></script>
<script src="${ctxStatic }/js/validate/jquery.validate.js"></script>
<script src="${ctxStatic }/js/validate/validate-methods.js"></script>
<script src="${ctxStatic }/js/placeholder.js"></script>
<script src="${ctxStatic }/js/select_bag/jquery.searchableSelect.js"></script>
<script src="${ctxStatic }/js/ajaxfileupload.js"></script>
<script src="${ctxStatic }/js/My97DatePicker/WdatePicker.js"></script>

<!--搜索框下拉自动填充js开始-->
<script src="${ctxStatic }/js/search_input_bag/jquery.autocompleter.js"></script>
<!--搜索框下拉自动填充js结束-->

<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/js/public.js" />
</jsp:include>

<!-- 登录 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/user/login.js" />
</jsp:include>

<script>
$(function(){
	Login.init();
});
</script>


</body>
</html>

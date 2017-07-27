<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!-- 这里路径不可删除 -->
<input type="hidden" value="${ctxBase }" id="basePath">
<input type="hidden" id="type" value="${type }"/>
<input type="hidden" id="tipType" value="${tipType }"/>
<div class="accont-box user-accont-box">
	<h1 class="accont-tit">选择验证方式
<%-- 	<c:if test="${type == 0 and tipType == 0}">手机绑定</c:if> --%>
<%-- 	<c:if test="${type == 0 and tipType == 1}">手机修改</c:if> --%>
<%-- 	<c:if test="${type == 1 and tipType == 0}">邮箱绑定</c:if> --%>
<%-- 	<c:if test="${type == 1 and tipType == 1}">邮箱修改</c:if> --%>
	</h1>
    <div class="accont-form-box">
    	<p class="fs16 accont-tips-p">请在下面的选项中选择一种验证账号安全的方式，以验证
    	<i class="c-blue">
			<c:if test="${not empty user.nickname}">
              	<c:out value="${user.nickname}" /> 
             	</c:if>
             	 <!-- 昵称为空则显示userid -->
             	<c:if test="${empty user.nickname}">
              	<c:out value="${user.userId}" />
           	</c:if>
		</i>的安全</p>
		<c:if test="${not empty loginTel}">
	        <div class="mt20 mb20">
	            <label way="0" name="radio"><input type="radio" name="way" value="0" checked="checked"><span class="lab-name-word">获取手机验证码</span></label>
	            <p class="pl20">我们会将验证码发送至您${loginTel }手机号</p>
	        </div>
        </c:if>
        
        <c:if test="${not empty loginEmail }">
	        <div class="mt20 mb20">
	            <label way="1" name="radio"><input type="radio" name="way" value="1" <c:if test="${empty loginTel }">checked="checked"</c:if>><span class="lab-name-word">获取邮箱验证码</span></label>
	            <p class="pl20">我们会将验证码发送至您${loginEmail }邮箱</p>
	        </div>
        </c:if>
        
        <button type="submit" class="btn btn-blue login-btn mt15" id="nextBtn">下一步</button>
        <button type="submit" class="btn btn-blue login-btn mt15" id="returnBtn">取消</button>
    </div>
</div>

	

<%-- <%@ include file="/WEB-INF/view/include/publicjs.jsp"%> --%>

<script>
var basePath = $("#basePath").val();
// $("label[name=radio]").click(function() {
// 	$("label[name=radio]").removeClass("active");
// 	$("input[name=way]").removeAttr("checked");
// 	$(this).find("input[name='way']").attr("checked",true);
// 	$(this).addClass("active");
// });
$(function(){

	$("#nextBtn").click(function(){
		var way = 0;
	// 	$("label").each(function(){
	// 		if($(this).hasClass("active")) {
	// 			way = $(this).attr("way");
	// 			return;
	// 		}
	// 	});
	 	way = $("input[name='way']:checked").val();
		var data = {
			"type":	$("#type").val(),
			"tipType": $("#tipType").val(),
			"way": way
		};
		$.ajax({
	        type:"post",
	        url:$("#basePath").val()+"/account/toCheckAcc.do",
	        data:data,
	        dataType:"html",
	        success:function(html){
	        	$("#contentDiv").html(html);
	        },
	        error:function(err) {
	        }
	    });
	});

	$("#returnBtn").click(function(){
		$.ajax({
	        type:"post",
	        url:$("#basePath").val() +"/account/accountPage.do",
	        dataType:"html",
	        success:function(html){
	        	$("#contentDiv").html(html);
	        },
	        error:function(err) {
	        }
	    });
	});
});
</script>


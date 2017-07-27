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
            <a href="javascript:void(0)" id="account">账户安全</a>
            <a class="curr" href="javascript:void(0)" id="bind">帐号绑定</a>
        </div>
    </div>
    <!--账号绑定开始-->
    <div class="accont-tabbox user-info-bind mt30" style="display:block;">
        <ul class="user-bind-third">
            <li>
                <label class="label-bind-name">
                    <i class="icon-bind icon-wx"></i><span class="bind-name-text">新浪微博</span>
                </label>
                <div class="right-bind-align">
                         <c:choose> 
                            <c:when test="${sinaBind.openidType!=null}"> 
                                <span style="display:block;" name="sinaJB">
                                    <span class="user-bd"><i class="icon-bd-status icon-hadbd"></i>已绑定</span>
                                    <!-- <button type="button" onclick="sinaDelBD();" class="btn btn-wihte">解除绑定</button> -->
                                </span>
                                <span style="display:none;" name="sinaBD">
                                    <span class="user-bd"><i class="icon-bd-status icon-nobd"></i>未绑定</span>
                                    <button onclick="sinaAddBD();" type="button"  class="btn btn-blue">添加绑定</button>
                                </span>
                            </c:when> 
                            <c:otherwise>
                                <span style="display: block;" name="sinaBD">
                                    <span class="user-bd"><i class="icon-bd-status icon-nobd"></i>未绑定</span>
                                    <button onclick="sinaAddBD();" type="button"  class="btn btn-blue">添加绑定</button>
                                </span>
                                <span style="display: none;" name="sinaJB">
                                    <span class="user-bd"><i class="icon-bd-status icon-hadbd"></i>已绑定</span>
                                  <!--   <button type="button" class="btn btn-wihte" disabled="disabled">解除绑定</button> -->
                                </span>
                            </c:otherwise> 
                         </c:choose> 
                </div>
            </li>
            <li>
                <label class="label-bind-name">
                    <i class="icon-bind icon-qq"></i><span class="bind-name-text">腾讯QQ</span>
                </label>
                <div class="right-bind-align">
                    
                        <c:choose> 
                            <c:when test="${qqBind.openidType!=null}">
                                <span style="display:block;" name="qqJB">   
                                    <span class="user-bd"><i class="icon-bd-status icon-hadbd"></i>已绑定</span>
                                   <!--  <button type="button" disabled="disabled" class="btn btn-wihte">解除绑定</button> -->
                                </span>
                                <span style="display:none;" name="qqBD">
                                    <span class="user-bd"><i class="icon-bd-status icon-nobd"></i>未绑定</span>
                                    <button onclick="qqAddBD();" type="button" class="btn btn-blue">添加绑定</button>
                                </span>
                            </c:when> 
                            <c:otherwise>   
                                <span style="display:block;" name="qqBD">
                                    <span class="user-bd"><i class="icon-bd-status icon-nobd"></i>未绑定</span>
                                    <button onclick="qqAddBD();" type="button" class="btn btn-blue">添加绑定</button>
                                </span>
                                <span style="display:none;" name="qqJB">
                                    <span class="user-bd"><i class="icon-bd-status icon-hadbd"></i>已绑定</span>
                                    <!-- <button type="button" onclick="qqDelBD();" class="btn btn-wihte">解除绑定</button> -->
                                </span>
                            </c:otherwise> 
                        </c:choose> 
                    
                </div>
            </li>
        </ul>
    </div>
    <!--账号绑定结束-->  
</div>
<!--个人信息页面结束-->
</div>
<script src="${ctxStatic}/js/jquery.cookie.js"></script>    
<script type="text/javascript">
$(function(){
	USERINFO.init();	
});
var ssourl='<%=GlobalConstant.SSOURL%>';
/** 新浪添加绑定  */
function sinaAddBD(){
	window.open("<%=GlobalConstant.SSOURL%>/startqqlogin.do");
	var t=setInterval(function(){
	console.log($.cookie('openidType'));
			if($.cookie('openidType')=='1'){//新浪绑定成功
				$("span[name=sinaBD]").css("display","none");
				$("span[name=sinaJB]").css("display","block");
				clearInterval(t);
			}
	},1000);
}

/** qq添加绑定  */
function qqAddBD(){
	window.open("<%=GlobalConstant.SSOURL%>/startqqlogin.do");
	var t=setInterval(function(){
	console.log($.cookie('openidType'));
			if($.cookie('openidType')=='0'){//qq绑定成功
				$("span[name=qqBD]").css("display","none");
				$("span[name=qqJB]").css("display","block");
				clearInterval(t);
			}
	},1000);
}

/** sina解除绑定  */
function sinaDelBD(){
	layer.open({
		  title:'提示',
		  content: '确定要解除新浪微博绑定吗?',
		  yes: function(index, layero){
			  $.ajax({
			        type:"POST",
			        url:"${ctxBase}/telBind.do",
			        async:false, 
			        data:{"openidType":1},
			        dataType:"json",
			        success:function(data){
			            if(data.code == 200) {
			            	$.ajax({
						        type:"POST",
						        url:"${ctxBase}/goAccountBinding.do",
						        async:false, 
						        dataType:"html",
						        success:function(html){
					            	$("#contentDiv").html(html);
						        }
						  });
						}
			        }
			  });
			  layer.close(index); //如果设定了yes回调，需进行手工关闭
		  },
		  cancel: function(){ 
			 
		  }
	}); 
}
/** qq解除绑定  */
function qqDelBD(){
	layer.open({
		  title:'提示',
		  content: '确定要解除腾讯QQ绑定吗?',
		  yes: function(index, layero){
			  $.ajax({
			        type:"POST",
			        url:"${ctxBase}/telBind.do",
			        async:false, 
			        data:{"openidType":0},
			        dataType:"json",
			        success:function(data){
			            if(data.code == 200) {
			            	$.ajax({
						        type:"POST",
						        url:"${ctxBase}/goAccountBinding.do",
						        async:false, 
						        dataType:"html",
						        success:function(html){
					            	$("#contentDiv").html(html);
						        }
						  });
						}
			        }
			  });
			  layer.close(index); //如果设定了yes回调，需进行手工关闭
		  },
		  cancel: function(){ 
			  
		  }
	}); 
}
</script>
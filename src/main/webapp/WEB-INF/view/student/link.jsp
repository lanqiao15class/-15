<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!--右侧内容部分开始-->
<div class="student-info-part">
   	<div class="inner-reletive">
       	<!--右侧标题部分开始-->
		<div class="tit-h1-box">
            <h1 class="tit-first">
                <span>个人资料</span><i class="gt-icon">&gt;</i><span class="curr">联系方式</span>
            </h1>
        </div>
		<!--右侧标题部分结束-->
        <!--右侧内容部分背景白色开始-->
        <div class="inner-white">
        	<!-- 这里显示学员提示，去报名等等 -->
            <div id="studentTips"></div>
            
           
            
            <div class="student-info-form">
            	<div class="inner-stuInfo">
                	<form>
            		<p>如果您的联系方式变更，请及时更改联系方式，以便蓝桥老师和您保持联系畅通，持续为您提供咨询和服务</p><br>
                    <div class="stu-info-inner">
                        <div class="line-item">
                        	<div class="clearfix">
	                            <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">电话：</i></label>
	                            <a href="javascript:void(0);" class="read-span able-edit">
	                            	<em name="tel">
                                		<c:if test="${empty user.tel}">点击填写</c:if>
                                		<c:if test="${!empty user.tel}">${user.tel}</c:if>
                                	</em>
	                            	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
	                            </a>
	                            <span class="eidt-span"><input id="tel" disableautocomplete autocomplete="off" type="text"></span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                        	<div class="clearfix">
	                            <label class="left-label"><i class="must-input-icon"></i><i class="label-text">QQ：</i></label>
	                            <a class="read-span able-edit" href="javascript:void(0);">
	                            	<em name="qq">
	                                	<c:if test="${empty user.qq}">点击填写</c:if>
	                                	<c:if test="${!empty user.qq}">${user.qq}</c:if>
	                                </em>
	                            	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
	                            </a>
	                            <span class="eidt-span"><input id="qq" disableautocomplete autocomplete="off" type="text"></span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                        	<div class="clearfix">
	                            <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">电子邮箱：</i></label>
	                            <a class="read-span able-edit" href="javascript:void(0);">
	                            	<em name="email">
                                		<c:if test="${empty user.email}">点击填写</c:if>
                                		<c:if test="${!empty user.email}">${user.email}</c:if>
                                	</em>
	                            	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
	                            </a>
	                            <span class="eidt-span"><input id="email" disableautocomplete autocomplete="off" type="text"></span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        
                        <div class="line-item">
                        	<div class="clearfix">
	                            <label class="left-label"><i class="must-input-icon"></i><i class="label-text">通讯地址：</i></label>
	                            <a href="javascript:void(0);" class="read-span able-edit">
	                            	<em name="address">
                                		<c:if test="${empty stuInfo.address}">点击填写</c:if>
                                		<c:if test="${!empty stuInfo.address}">${stuInfo.address}</c:if>
                                	</em>
	                            	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
	                            </a>
	                            <span class="eidt-span"><input id="address" disableautocomplete autocomplete="off" type="text"></span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                        	<div class="clearfix">
	                            <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">家庭联系人：</i></label>
	                            <a href="javascript:void(0);" class="read-span able-edit">
	                            	<em name="parentInfo">
	                                	<c:if test="${empty stuInfo.parentInfo}">点击填写</c:if>
	                                	<c:if test="${!empty stuInfo.parentInfo}">${stuInfo.parentInfo}</c:if>
	                                </em>
	                            	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
	                            </a>
	                            <span class="eidt-span"><input id="parentInfo" disableautocomplete autocomplete="off" type="text"></span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                        	<div class="clearfix">
	                            <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">家庭联系人电话：</i></label>
	                            <a href="javascript:void(0);" class="read-span able-edit">
	                            	<em name="parentTel">
	                                	<c:if test="${empty stuInfo.parentTel}">
	                                		点击填写
	                                	</c:if>
	                                	<c:if test="${!empty stuInfo.parentTel}">
	                                		${stuInfo.parentTel}
	                                	</c:if>
	                                </em>
	                            	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
	                            </a>
	                            <span class="eidt-span"><input id="parentTel" disableautocomplete autocomplete="off" type="text"></span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        
                    </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!--右侧内容部分结束-->

<div style="height:0; overflow:hidden;" id="dialogcontant"></div>
<script>
$(function(){
	//表头提示
	STUDENTTIPS.init();
	
	//点击编辑icon
	$(".able-edit").click(function(e){
		e.stopPropagation();
		var _this=$(this).parents(".line-item");
		$(".eidt-span").hide();
		$(".read-span").show();
		_this.find(".read-span").hide();
		_this.find(".eidt-span").show();
		if(_this.find(".eidt-span").children("input")){
			_this.find(".eidt-span").children("input").focus();
		}else if(_this.find("select")){
			alert(0);
		}
		
		//1.编辑后赋值到文本框zzh
		//alert($(this).children("em").html());
		if($(this).children("em").html().trim()=="点击填写"||$(this).children("em").html().trim()=="暂无"){
			$(this).next("span").children("input").val("");//为空					
		}else{
			$(this).next("span").children("input").val($(this).children("em").html().trim());//赋值
		}
	});
	
	$(".eidt-span").click(function(e){
		e.stopPropagation();
	});
	//========文本框-失去焦点提交(姓名,身份证号,所在年级,在校担任职务,所在专业,宿舍楼号,电话,QQ,电子邮箱,家庭联系人,家庭联系人电话)============
	$(".eidt-span input[type='text']").blur(function(e){
		e.stopPropagation();
		var _this=$(this).parents(".line-item");
		//1.非空验证zzh
		if((_this.find(".label-text").html().replace("：","")=="电话"&&($(this).val()==null||$(this).val()==""))||
		   (_this.find(".label-text").html().replace("：","")=="QQ"&&($(this).val()==null))||
		   (_this.find(".label-text").html().replace("：","")=="电子邮箱"&&($(this).val()==null||$(this).val()==""))||
		   (_this.find(".label-text").html().replace("：","")=="通讯地址"&&($(this).val()==null))||
		   (_this.find(".label-text").html().replace("：","")=="家庭联系人"&&($(this).val()==null||$(this).val()==""))||
		   (_this.find(".label-text").html().replace("：","")=="家庭联系人电话"&&($(this).val()==null||$(this).val()==""))
		){//为空
			$(".eidt-span").hide();
			$(".read-span").show();
			_this.find(".read-span").hide();
			_this.find(".eidt-span").show();
			if(_this.find(".eidt-span").children("input")){
				_this.find(".eidt-span").children("input").focus();
			}
			//非空验证zzh
			if(_this.find(".label-text").html().replace("：","")=="QQ"){
				_this.find(".error-tips").html("");//不进行提示
				_this=$(this).parents(".line-item");
				_this.find(".read-span").show();
				_this.find(".eidt-span").hide();
			}else if(_this.find(".label-text").html().replace("：","")=="通讯地址"){
				_this.find(".error-tips").html("");//不进行提示
				_this=$(this).parents(".line-item");
				_this.find(".read-span").show();
				_this.find(".eidt-span").hide();
			}else{
				_this.find(".error-tips").html("请输入"+_this.find(".label-text").html().replace("：",""));
			}
		}else{//不为空
			_this=$(this).parents(".line-item");
			_this.find(".read-span").show();
			_this.find(".eidt-span").hide();
			
			//1.清空提示
			_this.find(".error-tips").html("");
			//2.定义传递的参数
			var parameterName;
			var parameterValue;
			//3.获取当前文本框的id名称
			var textId=_this.find(".eidt-span").children("input").attr("id");
			//4.验证是否相等,相等则不执行修改
			if($("#"+textId).val().trim()==$("em[name="+textId+"]").html().trim()){
				return;
			}
			//4.1验证
			var textCheck=$("#"+textId).val();
			if(textId=="tel"){//电话
				if(isMobile(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("手机号码格式不正确");
					return;
				}
			}else if(textId=="qq"){//qq
				if(!/^[1-9]\d{4,12}$/.test(textCheck)&&textCheck!=""){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("QQ号格式不正确");
					return;
				}
			}else if(textId=="email"){//邮箱
				if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("邮箱格式不正确");
					return;
				}
			}else if(textId=="address"){//通讯地址
				if(isContainsSpecialChar(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("通讯地址包含非法字符");
					return;
				}
			}else if(textId=="parentInfo"){//家庭联系人
				if(isContainsSpecialChar(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("家庭联系人包含非法字符");
					return;
				}
			}else if(textId=="parentTel"){//家庭联系人电话
				if(isTel(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("联系电话格式不正确");
					return;
				}
			}
			//5赋值
			parameterName=textId;
			parameterValue=$("#"+textId).val();
			//6.组装
			var data=parameterName+"="+parameterValue;
			//7.异步并回显
			 $.ajax({
		    	  type:"post",
		             url:basePath+"/student/editStuBasic.do",  
		             data:data,
		             dataType:"json",
		             success:function(data){
		         		if(data.code == 200){
		         			//8
		         			var qqHtml=$.trim($("em[name=qq]").html());//qq
		         			var addressHtml=$.trim($("em[name=address]").html());//地址
		         			//9.
		         			$("em[name="+textId+"]").html(parameterValue);
		         			//10
		         			if(textId=="qq"){
			         			if($("#qq").val()=="" && qqHtml =="点击填写"){
			         				$("em[name=qq]").html("点击填写");
			         				return;
			         			}else if($("#qq").val()==""&& qqHtml !="点击填写"){
			         				$("em[name=qq]").html("点击填写");
			         			}
		         			}
		         			//11
		         			if(textId=="address"){
			         			if($("#address").val()=="" && addressHtml =="点击填写"){
			         				$("em[name=address]").html("点击填写");
			         				return;
			         			}else if($("#address").val()==""&& addressHtml !="点击填写"){
			         				$("em[name=address]").html("点击填写");
			         			}
		         			}
	         				layer.msg("修改成功");
		         		}else{
		          			layer.msg("修改失败");
		          		}
		             }
		    });
		}
	});
	//选择空白处提交
	$("body").click(function(e){
		$(".read-span").show();
		$(".eidt-span").hide();
	});
});

/** 手机号码验证  */
function isMobile(value){
   var length = value.length;   
   if(length == 11 && /^1[3|4|5|7|8]\d{9}$/.test(value)){
	   return false;
   }else{
	   return true;
   }
}

/** 判断是否包含中英文特殊字符，除英文"-_"字符外  */
function isContainsSpecialChar(value){
	   var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\‘)(\；)(\：)(\”)(\“)(\’)(\。)(\，)(\、)(\？)]+/);   
    if(reg.test(value)){
 	   return true;
    }else{
 	   return false;
    }
}

/** 联系电话(手机/电话皆可)验证   */
function isTel(value){
   var length = value.length;
   var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
   var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
   if((length == 11 && mobile.test(value)) || (length == 7 && tel.test(value))){
	   return false;
   }else{
	   return true;
   }
}
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<input type="hidden" id="resourcePath" value="${ctxResource}">
<!--填写报名信息开始-->

<div class="sign-up-box">
	<div class="tit-sign-up">
    	<h1>填写报名信息：</h1>
        <p>（说明： 您提供的资料，我们将会予以保密，不会挪作他用）</p>
    </div>
    <div class="sign-up-form">
        <div class="sign-up-outside">
            <form id="formSignUp"  method="post" enctype="multipart/form-data">
            	<div class="clearfix">
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">姓名：</i></label>
                            <input name="realName" value="${user.realName}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">性别：</i></label>
                            <span class="sex-check"><input type="radio" name="sex" value="0" <c:if test='${user.sex==0}'>checked</c:if>> 男</span>
                            <span class="sex-check"><input type="radio" name="sex" value="1" <c:if test='${user.sex==1}'>checked</c:if>> 女</span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">民族：</i></label>
                            <input type="hidden" name="nation" value="${user.nation}"/>
                            <c:if test="${empty user.nation}">
		                        <jsp:include page="/WEB-INF/view/include/stuInfoSignUp_nation_select_search.jsp">
									<jsp:param name="inputid" value="nation_selectSignup" />
								</jsp:include>
		                    </c:if>
		                    <c:if test="${!empty user.nation}">
		                            <jsp:include page="/WEB-INF/view/include/stuInfoSignUp_nation_select_search.jsp">
									<jsp:param name="inputid" value="nation_selectSignup" />
									<jsp:param name="selectid" value="${user.nation}" />
									<jsp:param name="selecttext" value="${user.nation}" />
								</jsp:include>
                            </c:if>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">身份证号：</i></label>
                            <input name="idCard" value="${user.idCard}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">出生年月：</i></label>
                        <span class="read-span only-read">
                        	<em>
                        		<c:if test="${empty user.birth}">
                             		暂无
                             	</c:if>
                             	<c:if test="${!empty user.birth}">
                               		<fmt:formatDate value="${user.birth}" pattern="yyyy.MM.dd"/> 
                             	</c:if>
                             </em>
                        </span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">所在院校：</i></label>
                            <input type="hidden" name="univCode" value=""/>
                            <c:if test="${empty stuInfo.univCode}">
                                <jsp:include page="/WEB-INF/view/include/stuInfoSignUp_school_select_likesearch.jsp">
                                    <jsp:param name="inputid" value="signup_select_school_name" />
                                </jsp:include>
                            </c:if>
                            <c:if test="${!empty stuInfo.univCode}">
                                <jsp:include page="/WEB-INF/view/include/stuInfoSignUp_school_select_likesearch.jsp">
                                    <jsp:param name="inputid" value="signup_select_school_name" />
                                    <jsp:param name="selectid" value="${stuInfo.univCode}" />
                                    <jsp:param name="selecttext" value="${univName}" />
                                </jsp:include>
                            </c:if>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">院校类别：</i></label>
                         <select name="schoolTypeCode">
                         	<option value="">请选择</option>
                         	<c:forEach items="${schoolType}" var="st">
                     			<option value="${st.value}" <c:if test='${st.value==stuInfo.schoolTypeCode}'>selected</c:if>>${st.label}</option>
                     		</c:forEach>
                         </select>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">院校所在省：</i></label>
                            <select id="provSignUp" name="provCodeSignUp" onchange="tm_change_provinceSignUp(this)" >
                        		<!-- 省 -->
                            </select>
                            <input type="hidden"  id="provCodeSignUp" value="${stuInfo.schoolProvCode}"/><!-- 用于比较  -->
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">院校所在市：</i></label>
                            <select id="citySignUp" name="cityCodeSignUp" onchange="editProvAndCitySignUp(this);">
                              	<!-- 市 --> 
                            </select>
                            <input type="hidden"  id="cityCodeSignUp" value="${stuInfo.schoolCityCode}"/><!-- 用于比较 -->
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">所在年级：</i></label>
                            <input name="grade" value="${stuInfo.grade}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">在校担任职务：</i></label>
                            <input name="schoolDuty" value="${stuInfo.schoolDuty}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">所在专业：</i></label>
                            <input name="major" value="${stuInfo.major}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">所在学院：</i></label>
                             <input name="schoolSubname" value="${stuInfo.schoolSubname}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">宿舍楼号：</i></label>
                            <input name="schoolDormitory" value="${stuInfo.schoolDormitory}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>                            
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">就业意向：</i></label>
                            <span class="stu-info-input">
	                            <c:forEach items="${jobCityCode}" var="jcc">
					       			<span class="addr-check">
					        			<input type="checkbox" title="${jcc.label}" name="jobCityCode" value="${jcc.value}" <c:if test="${fn:contains(stuInfo.jobCityCode, jcc.value)}">
										checked</c:if> >${jcc.label}
					        		</span>
					       		</c:forEach>
                            </span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">课程类别：</i></label>
                            <select name="courseTypeCode">
                            	<option value="">请选择</option>
                               	<c:forEach items="${courseType}" var="ct">
                               		<option value="${ct.value}" <c:if test='${ct.value==stuInfo.courseType}'>selected</c:if>>${ct.label}</option>
                               	</c:forEach>
                            </select>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">电话：</i></label>
                            <input name="tel" value="${user.tel}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">QQ：</i></label>
                            <input name="qq" value="${user.qq}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">电子邮箱：</i></label>
                            <input name="email" value="${user.email}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">通讯地址：</i></label>
                            <input name="address" value="${stuInfo.address}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">家庭联系人：</i></label>
                            <input name="parentInfo" value="${stuInfo.parentInfo}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">家庭联系人电话：</i></label>
                            <input name="parentTel" value="${stuInfo.parentTel}" disableautocomplete autocomplete="off" type="text">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="idcard-guige mb10 c-green">身份证正反面图片的规格不大于3M</div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">身份证正面：</i></label>
                            <span class="inbox-card-span">
                                <div class="idcard-file-box">
                                	<c:if test="${empty stuInfo.idcardFrontImg}">
                                		<img src="" id="returnShowFront" name="returnShowFront">
                                	</c:if>
                                	<c:if test="${!empty stuInfo.idcardFrontImg}">
                                		<img src="${ctxResource}${stuInfo.idcardFrontImg}" id="returnShowFront" name="returnShowFront">
                                	</c:if>
                                    <input id="frontImgName2" type="hidden" name="frontImgName2" value="${stuInfo.idcardFrontImg}"><!-- 返回的图片名称  -->
                                    <input class="upload-input-file" type="file" id="frontImgFile2" name="imgFile">
                                    <div class="upload-bgimg">
                                        <p class="upload-icon"><i class="Hui-iconfont">&#xe642;</i></p>
                                        <p class="upload-word">上传文件</p>
                                    </div>
                                </div>
                            </span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">身份证反面：</i></label>
                            <span class="inbox-card-span">
                                <div class="idcard-file-box">
                                	<c:if test="${empty stuInfo.idcardBackImg}">
                                		<img src="" id="returnShowBack" name="returnShowBack">
                                	</c:if>
                                	<c:if test="${!empty stuInfo.idcardBackImg}">
                                		<img src="${ctxResource}${stuInfo.idcardBackImg}" id="returnShowBack" name="returnShowBack">
                                	</c:if>
                                    <input id="backImgName2" type="hidden" name="backImgName2" value="${stuInfo.idcardBackImg}"><!-- 返回的图片名称  -->
                                    <input class="upload-input-file"  type="file" id="backImgFile2" name="imgFile">
                                    <div class="upload-bgimg">
                                        <p class="upload-icon"><i class="Hui-iconfont">&#xe642;</i></p>
                                        <p class="upload-word">上传文件</p>
                                    </div>
                                </div>
                            </span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver">
                        <div class="clearfix">
	                        <label class="align-right-label"></label>
	                        <span>
	                        	<input type="checkbox" name="serviceXY"/>
	                        	<span class="protocol">我已阅读并同意
	                        		<a class="c-blue" href="javascript:void(0);" onclick="window.open('${ctxBase}/signup/goAgreement.do')">《人才输送服务协议》</a>
	                        	</span>
	                        </span>
                    	</div>
	                    <span class="error-tips" name="gxtyfwxy"></span>
                	</div>
                <div class="clearfix fl">
                    <p class="tips-shenhe">资料提交成功后，蓝桥顾问老师会在7个工作日内与您取得联系，请您保持手机畅通并耐心等候。如果您在报名现场，请在提交完报名资料后与顾问老师联系。如果仍然有疑问，请拨打服务电话：4006-588-662</p>
                </div>
                <div class="center mt30 fl width_full"><button class="btn btn-blue">提交资料</button></div>
            </form>
        </div>
    </div>
</div>

<!--填写报名信息结束-->
<script>
$(function(){
	//=====================上传图片监听====================
	$(document).on('change','#frontImgFile2',function(){
		signUpSubmitImg("A");
	});
	$(document).on('change','#backImgFile2',function(){
		signUpSubmitImg("B");
	});
	//=======================省市==========================
	var provCodeSignUp=$("#provCodeSignUp").val();
    var cityCodeSignUp=$("#cityCodeSignUp").val();
	//1.异步加载省
    $.ajax({
         type:"post",
         url:basePath+"/student/getProvList.do",
         async:false,
         data:{},
         dataType:"json",
         success:function(data){
         	 var data=data.provList;
            	 var html="<option value=''> --请选择省份-- </option>";
              for(var i=0;i<data.length;i++){
                if(provCodeSignUp==data[i].cityId){
                     html+="<option value='"+data[i].cityId+"' selected='selected' >"+data[i].city+"</option>";
                }else{
                     html+="<option value='"+data[i].cityId+"' >"+data[i].city+"</option>"; 
                }
              }
              $("#provSignUp").html(html);
              $("#citySignUp").html("<option value=''> --请选择城市-- </option>");
              if(cityCodeSignUp!=null&&cityCodeSignUp!=''){
                $("#provSignUp").trigger("change");//触发城市下拉框加载
              }
          } 
     });  
	//=================================================
	/** 表单验证+提交   */	
	$("#formSignUp").validate({
		onfocusout: function(element) { //是否在失去焦点时验证
			$(element).valid();
		},
		onfocusin: function(element) { $(element).valid(); },
		onsubmit:true,
		onkeyup:false,//是否在敲击键盘时验证
		ignore: "",//验证隐藏域
		rules:{
			realName:{
				required:true,
				isContainsSpecialChar:true
			},
			sex:"required",
			nation:"required",
			idCard:{
				required:true,
				isIdCardNo:true
			},
			univCode:"required",
			schoolTypeCode:"required",
			provCodeSignUp:"required",
			cityCodeSignUp:"required",
			grade:{
				required:true,
				isContainsSpecialChar:true,
				isDigits:true
			},
			schoolDuty:{
				required:true,
				isContainsSpecialChar:true
			},
			major:{
				required:true,
				isContainsSpecialChar:true
			},
			schoolSubname:{
				required:true,
				isContainsSpecialChar:true
			},
			schoolDormitory:{
				required:true,
				isContainsSpecialChar:true
			},
			jobCityCode:"required",
			courseTypeCode:"required",
			tel:{
				required:true,
				isMobile:true/*,
				checkStuTel:true*/
			},
			qq:{
				isQq:true
			},
			email:{
				required:true,
				isEmail:true
			},
			address:{
				isContainsSpecialChar:true
			},
			parentInfo:{
				required:true,
				isContainsSpecialChar:true
			},
			parentTel:{
				required:true,
				isMobile:true
			},
			frontImgName2:"required",
			backImgName2:"required",
			serviceXY:"required"
		},
		messages:{
			realName:{
				required:"请输入学员姓名",
				isContainsSpecialChar:"学员姓名包含非法字符"
			},
			sex:"请选择性别",
			nation:"请选择民族",
			idCard:{
				required:"请输入身份证号码",
				isIdCardNo:"身份证号码格式不正确"
			},
			univCode:"请选择院校",
			schoolTypeCode:"请选择院校类别",
			provCodeSignUp:"请选择院校所在省",
			cityCodeSignUp:"请选择院校所在市",
			grade:{
				required:"请输入年级",
				isContainsSpecialChar:"年级包含非法字符",
				isDigits:"请填写正确的年份数字，如2012"
			},
			schoolDuty:{
				required:"请输入职务",
				isContainsSpecialChar:"职务包含非法字符"
			},
			major:{
				required:"请输入专业",
				isContainsSpecialChar:"专业包含非法字符"
			},
			schoolSubname:{
				required:"请输入所在学院",
				isContainsSpecialChar:"所在学院包含非法字符"
			},
			schoolDormitory:{
				required:"请输入宿舍楼号",
				isContainsSpecialChar:"宿舍楼号包含非法字符"
			},
			jobCityCode:"请勾选就业意向",
			courseTypeCode:"请选择课程类别",
			tel:{
				required:"请输入手机号",
				isMobile:"手机号码格式不正确"/*,
				checkStuTel:"手机号已存在"*/
			},
			qq:{
				isQq:"QQ号格式不正确"
			},
			email:{
				required:"请输入电子邮箱",
				isEmail:"邮箱格式不正确"
			},
			address:{
				isContainsSpecialChar:"通讯地址包含非法字符"
			},
			parentInfo:{
				required:"请输入家庭联系人",
				isContainsSpecialChar:"家庭联系人包含非法字符"
			},
			parentTel:{
				required:"请输入家庭联系人电话",
				isMobile:"手机号码格式不正确"
				//isTel:"家庭联系人电话格式不正确"
			},
			frontImgName2:"请上传身份证正面",
			backImgName2:"请上传身份证反面",
			serviceXY:"请勾选同意人才输送服务协议"
		},
		errorCallback:function(element){//自己添加的验证失败回调方法 
		},
		submitHandler:function(){
			
			//修改
			$.ajax({
                type: "POST",
                url:basePath+"/signup/editSignUpInfo.do",
                data:$('#formSignUp').serialize(),
                dataType:"json",
                async:false,
                success: function(data) {
                	console.log(JSON.stringify(data)+"       11111111");
                	if(data.code=="105"){
                		
                		layer.msg("您的身份证已经注册。如本人注册请选择找回密码，或者联系招生老师",{time: 10000, icon:2});
                	}else{
                		//1
                    	layer.open({
    	      				  title:'提示',
    	      				  content: data.message,
    	      				  yes: function(index, layero){
    	      					$(".fl ul li:eq(0)").trigger("click");
    	      					layer.closeAll(); //如果设定了yes回调，需进行手工关闭
    	      				  }
          				});
                    	//2
                    	gl_syncUserInfo();//同步更新左侧菜单
                    	//3
                    	$.ajax({
                    		type:"post",
                    		url:basePath+"/student/goBasic.do",
                    		sync:false,
                    		data:{},
                    		dataType:"html",
                    		success:function(html){
                    			$("#contentDiv").html(html);
                    		}
                    		
                    		
                    	});	
                		
                	}
                
                }
            });
		},
		errorPlacement: function(error, element) {  
			error.appendTo(element.parents('.clearfix').next('.error-tips'));  
		}
	});
		
		
		
		
		
		
		
});

/**======================================图片上传开始===========================================**/
function signUpSubmitImg(type){
	var fileElementId = null,returnShow = null,imgName = null;
	if(type === 'A'){
//		alert("正面");
		fileElementId = 'frontImgFile2';returnShow = 'returnShowFront';imgName = 'frontImgName2';
	} 
	if(type === 'B'){
//		alert("反面");
		fileElementId = 'backImgFile2'; returnShow = 'returnShowBack';imgName = 'backImgName2';
	}
		
	$.ajaxFileUpload({
		valid_extensions : ['jpg','png','jpeg','bmp'],
		url : basePath+"/signup/signupUploadImg.do", //上传文件的服务端
		secureuri : false, //是否启用安全提交
		dataType : 'json', //数据类型 
		data:{
			"userId":"${loginUserId}",
			"type":type
		},
		fileElementId : fileElementId, //表示文件域ID
		//提交成功后处理函数      data为返回值，status为执行的状态
		success : function(data, status) {
	    switch(data.code) {
        	case 201:
        		layer.msg("请选择文件");
        		break;
        	case 202:
        		layer.msg("图片太大，请上传小于的3M的图片");
        		break;
        	case 203:
        		layer.msg("图片上传出现异常");
        		break;
        	case 204:
        		layer.msg("请选择bmp,jpg,jpeg,png格式的图片");
        	default:
        		var resourcePath = $("#resourcePath").val();
        		$("img[name="+returnShow+"]").attr("src",resourcePath + data.fileName);//显示图片
        		$("#"+imgName).val(data.fileName);//返回的图片名称至隐藏域
        		//清除提示
        		if(type=="A"){
	        		$("#frontImgName2").parents('.clearfix').next('.error-tips').html("");
        		}
        		if(type=="B"){
	        		$("#backImgFile2").parents('.clearfix').next('.error-tips').html("");
        		}
        		break;
	        }
			
		},
		//提交失败处理函数
		error : function(html, status, e) {
			alert("错误:"+status);
		}
	});
};
/**======================================图片上传結束===========================================**/
/** 省改变触发  */
   function tm_change_provinceSignUp(obj){
	   schoolProvName=$(obj).find("option:selected").html();//获取文本
	   $("em[name=prov]").html(schoolProvName);
	  
       var cityCodeSignUp=$("#cityCodeSignUp").val();
       var  objid=obj.value;
       schoolProvCode=obj.value;//保存时用
       if(!objid){//如果省为空
           $("#citySignUp").html("<option value=''> --请选择城市-- </option>");//清空市
       }
       $.ajax({
           type:"post",
           url:basePath+"/student/findCitys.do",
           data:{"id":objid},
           dataType:"json",
           success:function(data){
        	  var data=data.cityList;
              var html="<option value=''> --请选择城市-- </option>";
              for(var i=0;i<data.length;i++){
                if(cityCodeSignUp==data[i].cityId){
                    html+="<option value='"+data[i].cityId+"' selected='selected' >"+data[i].city+"</option>";
                }else{
                    html+="<option value='"+data[i].cityId+"'>"+data[i].city+"</option>"; 
                }
              }
              $("#citySignUp").html(html);
           } 
        });
    }



</script>
SUBMIT_STUDENT_INFO = function(){};

//获取上下文路径
SUBMIT_STUDENT_INFO.basePath = basePath;

//初始化加载
/**=====================================init=============================================== */
SUBMIT_STUDENT_INFO.init = function(){
	
	//1，身份证号提交事件（自动填充生日）
	$("#idCard").blur(function(){
		var idCardNum = $("#idCard").val();
		var isIdCard = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/;
		if(!isIdCard.test(idCardNum)){
			layer.msg("请输入正确的身份证号");
			return;
		}
		var date = idCardNum.substr(6,8);
		$("#birth").val(date);
		$("#birthShow").val(date.substr(0,4)+"年"+date.substr(4,2)+"月"+date.substr(6,2)+"日");
	});
	
	//2，身份证上传
	$(document).on('change','#frontImgFile',function(){
		SUBMIT_STUDENT_INFO.uploadImage("A");
	});
	$(document).on('change','#backImgFile',function(){
		SUBMIT_STUDENT_INFO.uploadImage("B");
	});
	
	//3,返回按钮(这种关闭方法不起作用，用添加css样式的方法关闭)
//	$("#returnBtn").on("click",function(){
//		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
//        parent.layer.close(index);
//	});
	
	//4,提交表单
	$("#submitStuInfo").on("click",function(){
		
		//1,验证身份证号码
		jQuery.validator.addMethod("isRightIdCard",function(value,element){
			var idCardCheck = /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/;
			return this.optional(element) || idCardCheck.test(value);
		},$.validator.format("请输入合法的身份证号码"));
		
		//2,电话号码和邮箱必填其一(去空格判定)
		jQuery.validator.addMethod("mobileOrEmail",function(value,element){
			var tel = $.trim($("#loginTel").val());
			var email = $.trim($("#loginEmail").val());
			return tel != "" || email != "" ;
		},"请填写手机号码和邮箱中的至少一种");
		
		//3,验证手机号码
		jQuery.validator.addMethod("isRightMobile",function(value,element){
			var telTest = /^(1+\d{10})$/;
			return this.optional(element) || telTest.test(value);
		},"请输入正确的手机号码");
		
		//4,空格验证：输入是否只是空格
		jQuery.validator.addMethod("allSpace",function(value,element){
			if(value.length > 0 && $.trim(value).length == 0){
				return false;
			}else{
				return true;
			}
		},"请勿输入空格");
		
		//5,手机号和邮箱重复性验证
		jQuery.validator.addMethod("validAccount",function(value,element,parm){
			var deferred = $.Deferred();//创建一个延迟对象
			if($.trim(value).length == 0){
				return true;
			}else{
				$.ajax({
					type:"post",
					url:basePath+"/student/validAccount.do",
					data:{"newAccount":value},
					dataType:"json",
					async:false,//要指定不能异步,必须等待后台服务校验完成再执行后续代码
					success:function(data){
						if(data.code == 200){
							deferred.resolve();   
						}else{
							deferred.reject();
						}
					},
				
				});
			}
//			deferred.state()有3个状态:pending:还未结束,rejected:失败,resolved:成功
			return deferred.state() == "resolved" ? true : false;
		},$.format("该{0}已被其他用户绑定"));
		
		
		//6,提交validate验证
		$("#addYxStudentInfo").validate({
			  ignore: "",//验证隐藏域
//		  	  onfocusin: function(element) { $(element).valid(); },
//		 	  onfocusout: function(element) { $(element).valid(); },
			  rules:{
				 realName:"required",
				 loginTel:{
					  mobileOrEmail:true,
					  isRightMobile:true,
//					  validAccount:["手机号"], 
				 },
			 	 loginEmail:{
			 		 mobileOrEmail:true,
			 		 email:true,
//			 		validAccount:["邮箱"],
			 	 },
			 	 qq:{
			 		digits:true,
			 		minlength:4,
			 		maxlength:20,
			 	 },
			 	 address:{
			 		maxlength:64,
			 	 },
			 	parent_info_name:{
			 		maxlength:40,
			 	},
			 	 parent_info_tel:{
			 		isRightMobile:true,
			 	 },
			 	 idCard:{
			 		isRightIdCard:true,
			 	 },
			 	 grade:{
			 		digits:true,
			 		rangelength:[4,4],
			 	 },
			 	 major:{
			 		maxlength:50, 
			 	 },
			 	 schoolDuty:{
			 		maxlength:32,
			 	 },
			 	 schoolSubname:{
			 		maxlength:64,
			 	 },
			 	 schoolDormitory:{
			 		maxlength:50,
			 	 },
			 	 protocolAgree:{
			 		required:true
			 	 }
			 	 //*****************rules*****************
			  },
			  messages:{
				  realName:"请输入姓名",
				  loginEmail:{
					  email:"请输入正确的邮箱"
				  },
				  qq:{
				 		digits:"请输入整数",
				 		minlength:"请输入长度大于4位的QQ号码",
				 		maxlength:"请输入长度小于20位的QQ号码",
				  },
				  address:{
					  	maxlength:"请输入不大于64个字符的地址",
				  },
				  parent_info_name:{
				 	 	maxlength:"请输入不大于40个字符的信息",
				  },
				  grade:{
				 		digits:"请输入整数",
				 		rangelength:"请输入年份",
				  },
				  major:{
					  maxlength:"请输入不大于50个字符的专业名称",
				  },
			 	  schoolDuty:{
			 		 maxlength:"请输入不大于32个字符的职务名称",
			 	  },
			 	  schoolSubname:{
			 		 maxlength:"请输入不大于64个字符的学院名称",
			 	  },
			 	  schoolDormitory:{
			 		 maxlength:"请输入不大于50个字符的宿舍号",
			 	  },
				  protocolAgree:{
			 		required:"请勾选接受按钮"
				  }
				//****************messages******************
			  },
			  errorCallback:function(element){
				  $("#loginTel").on('input',function(){
					  $(loginEmail).valid();
				  });
				  $("#loginEmail").on('input',function(){
					  $(loginTel).valid();
				  });
			  },
			  submitHandler:function(){
				  //手机号和邮箱是否填写性检验
//					 if(!SUBMIT_STUDENT_INFO.inputOne()){
//						 layer.msg("请填写手机号和邮箱中的至少一个");
//						 return;
//					 }
				  
				  //提交表单//
				  SUBMIT_STUDENT_INFO.submitStuInfo();
			  },
			  errorPlacement : function(error, element) {
				  error.appendTo(element.parents(".line-item-ver").find('.error-tips'));
				}
			    
		  });
		
	});
	
	
	//7,当前输入者是老师所做的处理
	if($("#type").val() == 'teacherAdd'){
		//去除非必填项目的*号
		$(".must-input-icon:not(.teacherAdd)").each(function(index,element){
			$(element).html("");
		});
		//勾选框隐藏,并且设置选上
		$("#protocolAgreeDiv").hide();
		$("#protocolAgree").prop("checked",true);
	};
	
	
	
//	$("#test").click(function(){
//		alert($("#frontImgName").val());
//		alert($("#backImgName").val());
//	});
	
};
/**=====================================init=============================================== */

/**======================================== 页面跳转========================================== */
SUBMIT_STUDENT_INFO.jumpUrl = function(url,data){
	$.ajax({
        type:"post",
        url:basePath+url,
        data:data,
        dataType:"html",
        success:function(html){
        	$("#contentDiv").html(html);
        },
        error:function(err) {
        }
    });
};
/**======================================== 页面跳转========================================== */

/**==================================== 省改变触发市加载  ========================================*/
SUBMIT_STUDENT_INFO.getCity = function(){
	  var  objid = $("#schoolProvCode").val();
	  if(!objid){//如果省为空
        $("#city").html("<option value=''> 请选择城市 </option>");//清空市
	  }
	  $.ajax({
        type:"post",
        url:SUBMIT_STUDENT_INFO.basePath+"/student/findCitysByProv.do",
        data:{"cid":objid},
        success:function(data){
               var jdata=eval("("+data+")");
               var html="<option value=''>请选择城市</option>";
               for(var i=0;i<jdata.length;i++){
                      html+="<option value='"+jdata[i].cityId+"'>"+jdata[i].city+"</option>";
               }
               $("#schoolCityCode").html(html);
        } 
 });
};
/**==================================== 省改变触发市加载  ========================================*/

/** =====================================Form提交验证 =========================================*/
SUBMIT_STUDENT_INFO.submitStuInfo = function(){
	//手机号和邮箱存在性检验
	 var flag = SUBMIT_STUDENT_INFO.validaAccount();
	 if(flag == 1){
		 layer.msg("该手机号已被其他用户绑定");
		 $("#loginTel").focus();//输入框获取焦点
		 return;
	 }else if(flag == 2){
		 layer.msg("该邮箱已被其他用户绑定");
		 $("#loginEmail").focus();
		 return;
	 }else if(flag == 3){
		 layer.msg("手机号和邮箱已被其他用户绑定");
		 $("#loginTel").focus();
		 return;
	 }
	
	
	var data  = $("#addYxStudentInfo").serialize();
	
	var jobCitys = [];//处理意向就业地点
	$("input[name='jobCityCode']").each(function(index,element){
		if($(element).is(':checked'))
			jobCitys.push(element.value);
	});
	data += "&jobCityCodes="+(jobCitys.join(","));
//	console.log(data);
    $.ajax({
    	  type:"post",
             url:basePath+"/student/addYxStudent.do",  
             data:data,
             dataType:"json",
             success:function(data){
         		if(data.code == 200){
         			layer.msg("意向学员添加成功");
         			$("#closeDialogBtn").trigger("click");
         			//刷新原网页[opType在弹窗父网页上]
         			SUBMIT_STUDENT_INFO.jumpUrl("/student/"+$("#opType").val()+"/goYxStu.do",null);
         			
         		}else{
          			layer.msg("系统异常，请稍后再试");
          		}
             }
    });
	
	
};
/** =====================================Form提交验证 =========================================*/

/**======================================图片上传开始===========================================**/
SUBMIT_STUDENT_INFO.uploadImage = function(type){
	var fileElementId = null,returnShow = null,imgName = null;
	if(type === 'A'){
//		alert("正面");
		fileElementId = 'frontImgFile';returnShow = 'returnShowFront';imgName = 'frontImgName';
	} 
	if(type === 'B'){
//		alert("反面");
		fileElementId = 'backImgFile'; returnShow = 'returnShowBack';imgName = 'backImgName';
	}
	
	$.ajaxFileUpload({
		valid_extensions : ['jpg','png','jpeg','bmp'],
		url : basePath+"/student/uploadIdCardImg.do", //上传文件的服务端
		secureuri : false, //是否启用安全提交
		dataType : 'json', //数据类型 
		data:{},
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
        		$("#"+returnShow).attr("src",resourcePath + data.fileName);
        		$("#"+imgName).val(data.fileName);
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


/**==================================手机号和邮箱是否输入其一检验=========================================**/
SUBMIT_STUDENT_INFO.inputOne = function(){
	var tel = $.trim($("#loginTel").val());
	var email = $.trim($("#loginEmail").val());
	return tel != "" || email != "" ;
};
/**==================================手机号和邮箱是否输入其一检验=========================================**/

/**==================================手机号和邮箱存在性检验=========================================**/
var telValid,emailValid;
SUBMIT_STUDENT_INFO.validaAccount = function(){
	var tel = $.trim($("#loginTel").val());
	var email = $.trim($("#loginEmail").val());
	SUBMIT_STUDENT_INFO.validaAccountCommon(tel,'A');
	SUBMIT_STUDENT_INFO.validaAccountCommon(email,'B');

	if(telValid && emailValid){
		return 0;//两者都不存在重复
	}else if(!telValid && emailValid){
		return 1;//手机号重复
	}else if(telValid && !emailValid){
		return 2;//邮箱重复
	}else{
		return 3;//手机号和邮箱都重复
	}
	
};
/**==================================手机号和邮箱存在性检验=========================================**/

//var telValid,emailValid;(全局变量)
/**===================================检验存在性公用方法==============================================**/
SUBMIT_STUDENT_INFO.validaAccountCommon = function(value,type){
	if(value != ""){
		$.ajax({
			type:"post",
			url:basePath+"/student/validAccount.do",
			data:{"newAccount":value},
			dataType:"json",
			async:false,
			success:function(data){
				if(data.code == 200){
					if(type == 'A'){
						telValid = true;
					}else{
						emailValid = true;
					}
				}else{
					if(type == 'A'){
						telValid = false;
					}else{
						emailValid = false;
					}
				}
			},
			
		});
	}else{//是空值的话直接返回true（数据库肯定不存在）
		if(type == 'A'){
			telValid = true;
		}else{
			emailValid = true;
		}
	}
};
/**====================================检验存在性公用方法==============================================**/


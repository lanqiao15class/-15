PWDRESET = function(){};

//获取上下文路径
PWDRESET.basePath = $("#basePath").val();

//定时器变量
PWDRESET.interValObj;

//发送验证码短信间隔时间(单位：秒)
PWDRESET.intervalTime = 60;

//剩余间隔时间(单位：秒)
PWDRESET.restTime;

/**
 * 刷新验证码
 */
PWDRESET.refreshCode = function() {
	$("#validateCodeImg").attr("src",PWDRESET.basePath+"/user/validateCode/resetPwd.do?n="+Math.random());
};

/**
 * 初始化用户检查页面
 */
PWDRESET.initPwdCheck = function(){
	//校验表单
	PWDRESET.validateUserForm();
};

/**
 * 表单验证
 */
PWDRESET.validateUserForm = function(){
	$("#pwdForm").validate({
		ignore:"ignore",
		onfocusout:false,
		rules: {
			userName:{
				required: true,
				remote: {
                    type: "post",
                    url: PWDRESET.basePath+"/user/userNameCheck.do",
                    dataType: "json",
                    data: {
                        username: function() {
                            return $("#userName").val();
                        }
                    },
                    dataFilter: function(data, type) {
                    	data = jQuery.parseJSON(data);
                    	if(data.code == 200) {
                    		$("#userId").val(data.user_id);
                    		$("#loginTel").val(data.login_tel);
                    		$("#loginEmail").val(data.login_email);
                    		return true;
                    	} else {
                    		return false;
                    	}
                    }
				}
			},
			validateCode:{
				required: true,
				remote: {
                    type: "post",
                    url: PWDRESET.basePath+"/user/validateCodeCheck.do",
                    data: {
                    	validateCode: function() {
                            return $("#validateCode").val();
                        },
                        type:function(){
                        	return "RANDOMCODEKEY_RESETPWDRESET";
                        }
                    },
                    dataType: "json",
                    dataFilter: function(data, type) {
                    	data = jQuery.parseJSON(data);
                    	if(data.code == 200) {
                    		return data.isRightCode;
                    	} else {
                    		return false;
                    	}
                    }
				}
			}
		},
		messages: {
			userName: {
				required: "请输入注册的手机号、邮箱",
				remote: "该用户不存在"
			},
			validateCode: {
				required: "请输入验证码",
				remote: "验证码输入错误"
			}
		},
		errorPlacement : function(error, element){
			error.appendTo(element.parents('.rowPart').next('p.wrongTips'));
		},
		errorCallback:function(element){//自己添加的验证失败回调方法 
			if($('#validateCode').parents('.rowPart').next(".wrongTips").find(".error").html()=='验证码输入错误'){
				PWDRESET.refreshCode();
			}
		}, 
		submitHandler : function(form) {
			form.submit();
			return false;
		},
		invalidHandler: function(form, validator) {   
            return false;  
        },
	});
};
//==================================================================================================================
/**
 * 初始化修改密码页面
 */
PWDRESET.initPwdUpdate = function(){
	//校验表单
	PWDRESET.validateUpdatePwdForm();
	//发送验证码按钮点击事件
	$("#sendCodeBtn").click(function(){
		if($(this).hasClass("act")){
			return;
		}
		
		var type = $("#type").val();
		if(type == "0") {//发送短信验证码
			PWDRESET.sendValidateCodeMsg(this);
		} else {//发送邮件验证码
			PWDRESET.sendValidateCodeEmail(this);
		}
		
	});
};

/**
 * 表单验证
 */
PWDRESET.validateUpdatePwdForm = function(){
	//扩展密码检测方法
	jQuery.validator.addMethod("pwdCheck", function(value, element) {
		var partten = /((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$/gi;
		return partten.test(value);
	}, "密码长度8~16位，其中数字，字母和符号至少包含两种");
	
	$("#pwdForm").validate({
		ignore:"ignore",
		rules: {
			validateCode:{
				required: true,
				remote: {
                    type: "post",
                    url: PWDRESET.basePath+"/user/msgVerificationCodeCheckZh.do",
                    dataType: "json",
                    data: {
                    	validateCode: function() {
                            return $("#validateCode").val();
                        },
						type: function() {//类型；0：短信；1：邮件
							return $("#type").val();
						}
                    },
                    dataFilter: function(data, type) {
                    	data = jQuery.parseJSON(data);
                    	if(data.code == 200) {
                    		return data.isRightCode;
                    	} else {
                    		return false;
                    	}
                    }
				}
			},
			pwd:{
				required: true,
				pwdCheck:true
			},
			rePwd:{
				required: true,
				equalTo: "#pwd"
			}
		},
		messages: {
			validateCode: {
				required: "请输入验证码",
				remote: "验证码不正确"
			},
			pwd: {
				required: "请输入密码",
				pwdCheck: "密码长度8~16位，其中数字，字母和符号至少包含两种"
			},
			rePwd: {
				required: "请重新输入新密码",
				equalTo:"两次输入密码不一致"
			}
		},
		errorPlacement : function(error, element){
			error.appendTo(element.parents('.rowPart').next('p.wrongTips')); 
		},
		submitHandler : function(form) {
			PWDRESET.updatePwd();
			return false;
		}
	});
};

/**
 * 修改密码
 */
PWDRESET.updatePwd = function() {
	$.ajax({
        type:"post",
        url:PWDRESET.basePath+"/user/updatePwd.do",
        data:{"userId":$("#userId").val(),"pwd":$("#pwd").val()},
        dataType:"json",
        success:function(json){
        	if(json.code == 200) {
        		layer.msg("重置密码成功，请重新登录");
        		setTimeout(function(){window.location.href = PWDRESET.basePath+"/login.do";}, 2000);
        	}
        },
        error:function(err) {
        }
    });
};

/**
 * 发送短信验证码
 */
PWDRESET.sendValidateCodeMsg = function(obj) {
	//初始化剩余时间
	PWDRESET.restTime = PWDRESET.intervalTime - 1;
	//按钮不可用，移除可用样式
	$("#sendCodeBtn").addClass("act");
	//修改按钮文本
	$(obj).text("重新获取(" + PWDRESET.restTime + ")");
	//修改按钮显示的文本
	PWDRESET.interValObj = window.setInterval(PWDRESET.intervalFunc, 1000); //启动计时器，1秒执行一次
	//保存定时器返回值
	$.ajax({
		type: "POST",
		url:PWDRESET.basePath+"/user/setTimerReturnVal.do",
		data:{"timerInterVal":PWDRESET.interValObj},
		dataType:"json",
		success: function(data) {
			if(data.code == 200) {
//	 	 		console.log(PWDRESET.interValObj+"保存定时器返回值执行成功");
			}
		}
	});
	//调用发送短信接口
	if($("#sendCodeBtn").attr("hasGet")=="true"){
		$.ajax({
			type: "POST",
			url:PWDRESET.basePath+"/user/sendSmsVerificationCode.do",
			data:{"tel":$("#tel").val()},
			dataType:"json",
			success: function(data) {
				if(data.code != 200) {
					console.log("发送短信验证码出现异常");
				}
			}
		});
	}else {
		$("#sendCodeBtn").parent(".rowPart").next(".wrongTips").html("不能重复获取验证码");
	}
};

/**
 * 定时器处理函数
 */
PWDRESET.intervalFunc = function() {
	if(PWDRESET.restTime == 1) {
		//停止计时器
        window.clearInterval(PWDRESET.interValObj);
        //启用发短信按钮
		$("#sendCodeBtn").removeClass("act");
//		//启用按钮
        $("#sendCodeBtn").text("获取验证码");
		$("#sendCodeBtn").attr("hasGet","true");
	} else {
		PWDRESET.restTime--;
		$("#sendCodeBtn").addClass("act");
		$("#sendCodeBtn").text("重新获取(" + PWDRESET.restTime + ")");
		$("#sendCodeBtn").attr("hasGet","false");
	}
};

/**
 * 发送邮件验证码
 */
PWDRESET.sendValidateCodeEmail = function(obj) {
	//初始化剩余时间
	PWDRESET.restTime = PWDRESET.intervalTime - 1;
	//按钮不可用，移除可用样式
	$("#sendCodeBtn").addClass("act");
	//修改按钮文本
	$(obj).text("重新获取(" + PWDRESET.restTime + ")");
	PWDRESET.interValObj = window.setInterval(PWDRESET.intervalFunc, 1000); //启动计时器，1秒执行一次
	//保存定时器返回值
	$.ajax({
		type: "POST",
		url:PWDRESET.basePath+"/user/setTimerReturnVal.do",
		data:{"timerInterVal":PWDRESET.interValObj},
		dataType:"json",
		success: function(data) {
			if(data.code == 200) {
//	 	 		console.log(PWDRESET.interValObj+"保存定时器返回值执行成功");
			}
		}
	});
	//调用发送短信接口
	$.ajax({
		type: "POST",
		url:PWDRESET.basePath+"/user/sendEmailRegisterCode.do",
		data:{"email":$("#email").val(),"type":"resetPwd"},
		dataType:"json",
		success: function(data) {
//			if(data.code != 200) {
//				console.log("发送邮件验证码出现异常");
//			}
		}
	});
};
PWD = function(){};

//获取上下文路径
PWD.basePath =basePath;

//定时器变量
PWD.interValObj;

//默认发送时间间隔
PWD.defaultIntervalTime = 60;

//发送验证码短信间隔时间(单位：秒)
PWD.intervalTime = 60;

//剩余间隔时间(单位：秒)
PWD.restTime;


/**
 * 初始化
 */
PWD.init = function() {
	//选择验证方式跳转到该页面需根据way的值显示隐藏手机/邮箱form
	var way=$("#way").val();
	var originTel=$("#originTel").val();
	var loginEmail=$("#email").val();
	if(way =="0" || way =="1"){
		if(way=="0"){
				$("#telPwdForm").show();
				$("#emailPwdForm").hide();
		}else{
				$("#telPwdForm").hide();
				$("#emailPwdForm").show();	
			}
	}else{
		console.log("originTel"+originTel);
		console.log("loginEmail"+loginEmail);
		if((originTel!=null && originTel!="")  && (loginEmail!=null && loginEmail!="")){
				$("#telPwdForm").show();
				$("#emailPwdForm").hide();	
				$("#chooseValidWay").show();
		}else if((loginEmail!=null && loginEmail!="") && (originTel==null || originTel=="") ){
			$("#telPwdForm").hide();
			$("#emailPwdForm").show();
			$("#chooseValidWay").hide();
		}else{
			$("#telPwdForm").show();
			$("#emailPwdForm").hide();
			$("#chooseValidWay").hide();
		}
	}
	//默认手机修改密码
	$("#telSendCodeBtn").on("click",function(event){
		//event.preventDefault();
		if(!$(this).hasClass("act")) {
			PWD.sendValidateCodeMsg();
		}
	});
	
	$("#telPwdBtn").on("click",function(){
		PWD.validateTelPwdForm();
	});
	
	$("#emailSendCodeBtn").on("click",function(event){
		event.preventDefault();
		if(!$(this).hasClass("act")) {
			PWD.sendValidateCodeEmail();
		}
	});
	
	$("#emailPwdBtn").on("click",function(){
		PWD.validateEmailPwdForm();
	});
	
	//修改密码方式切换
/*	$(".tabChange a").click(function(){
		//还原时间间隔
		PWD.intervalTime = PWD.defaultIntervalTime;
		//停止计时器
		if(PWD.interValObj != null) {
			window.clearInterval(PWD.interValObj);
		}
		//清空表单
		PWD.resetForm($(this));
		//切换tab效果
		$(this).addClass("curr").siblings("a").removeClass("curr");
		$(".formBox form").hide().eq($(this).index()).show();
	});*/
	
	//跳转到选择验证方式
	$("#chooseValidWay").click(function(){
		//还原时间间隔
		PWD.intervalTime = PWD.defaultIntervalTime;
		//停止计时器
		if(PWD.interValObj != null) {
			window.clearInterval(PWD.interValObj);
		}
		//清空表单
	$("form").each(function(){
		if($(this).is(":visible")){
			PWD.resetForm($(this).attr("id"));	
		}
	});
		PWD.goValidateWay();
	});
};

/**
 * 重置表单
 */
PWD.resetForm = function(id){
	if("telPwdForm" == id) {
		$("#msgValidateCode").val("");
		$("#oldPwd").val("");
		$("#pwd").val("");
		$("#rePwd").val("");
		
	} else {
		$("#emailValidateCode").val("");
		$("#eOldPwd").val("");
		$("#ePwd").val("");
		$("#erePwd").val("");
	}
	if($("#way").val()!=null){
		$("#way").val("");
	}
	$("#telSendCodeBtn").removeClass("act");
	$("#emailSendCodeBtn").removeClass("act");
	$("#telSendCodeBtn").text("获取验证码");
	$("#emailSendCodeBtn").text("获取验证码");
};


/**
 * 表单验证
 */
PWD.validateTelPwdForm = function(){
	//扩展密码检测方法
	jQuery.validator.addMethod("pwdCheck", function(value, element) {
//		var partten = /(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,16}$/gi;
		var partten = /((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$/gi;
		return partten.test(value);
	}, "密码长度8~16位，其中数字，字母和符号至少包含两种");
	
	jQuery.validator.addMethod("pwdEqOld", function(value, element) {
        var flag=true;
        if($.trim(value)==$.trim($("#oldPwd").val())){
        	flag=false;
        }
		return flag;
	}, "新旧密码一致，无需修改");
	
	$("#telPwdForm").validate({
		ignore:"ignore",
		rules: {
			msgValidateCode:{
				required: true,
				remote: {
                    type: "post",
                    url: PWD.basePath+"/user/msgVerificationCodeCheck.do",
                    dataType: "json",
                    data: {
                    	validateCode: function() {
                            return $("#msgValidateCode").val();
                        },
                        type: function() {//类型；smValidCode：短信；emailValidCode：邮件
							return "smValidCode";
						}
                    },
                    dataFilter: function(data, type) {
                    	data = jQuery.parseJSON(data);
                    	if(data.code == 200 && data.isRightCode) {
                    		window.clearInterval(PWD.interValObj);
                    		$("#telSendCodeBtn").text("获取验证码");
                    		return true;
                    	} else {
                    		return false;
                    	}
                    }
				}
			},
			oldPwd:{
				required: true,
				remote: {
                    type: "post",
                    url: PWD.basePath+"/user/oldPwdCheck.do",
                    dataType: "json",
                    data: {
                    	validateCode: function() {
                            return $("#oldPwd").val();
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
				pwdCheck:true,
				pwdEqOld:true
				
			},
			rePwd:{
				required: true,
				equalTo: "#pwd"
			}
		},
		messages: {
			msgValidateCode: {
				required: "请输入验证码",
				remote: "验证码不正确"
			},
			oldPwd: {
				required: "请输入原密码",
				remote: "原密码不正确"
			},
			pwd: {
				required: "请输入新密码",
				pwdCheck: "密码长度8~16位，其中数字，字母和符号至少包含两种",
				pwdEqOld:"新旧密码一致，无需修改"
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
			PWD.updatePwd();
		}
	});
};

/**
 * 修改密码
 */
PWD.updatePwd = function() {
	var pwd = typeof($("#pwd").val()) == "undefined" || $("#pwd").val() == "" ? $("#ePwd").val():$("#pwd").val();
	$.ajax({
        type:"post",
        url:PWD.basePath+"/user/updateNewPwd.do",
        data:{"userId":$("#userId").val(),"pwd":pwd},
        dataType:"json",
        success:function(json){
        	if(json.code == 200) {
        		layer.open({
  				  title:'修改密码提示',
  				  content: '修改密码成功，请重新登录',
  				  yes: function(index, layero){
  					layer.close(index); //如果设定了yes回调，需进行手工关闭
  					window.location.href = PWD.basePath+"/login.do";
  				  }
  				});
        	}
        },
        error:function(err) {
        }
    });
};

/**
 * 发送短信验证码
 */
PWD.sendValidateCodeMsg = function() {
	if($("#frequent").val()=="0"){
		//初始化剩余时间
		PWD.restTime = PWD.intervalTime -1;
		//按钮不可用，添加灰色样式
		$("#telSendCodeBtn").addClass("act");
		//修改按钮显示的文本
		$("#telSendCodeBtn").text("重新获取(" + PWD.restTime + ")");
		PWD.interValObj = window.setInterval(PWD.intervalFunc, 1000); //启动计时器，1秒执行一次
		//保存定时器返回值
		$.ajax({
			type: "POST",
			url:PWD.basePath+"/user/setTimerReturnVal.do",
			data:{"timerInterVal":PWD.interValObj},
			dataType:"json",
			success: function(data) {
				if(data.code == 200) {
//		 	 		console.log(PWD.interValObj+"保存定时器返回值执行成功");
				}
			}
		});	
	}
		//调用发送短信接口
		$.ajax({
			type: "POST",
			url:PWD.basePath+"/user/sendSmsVerificationCode.do",
			data:{"tel":$("#originTel").val()},
			dataType:"json",
			success: function(data) {
				if(data.code == 201) {
					layer.msg("发送短信验证码出现异常");
				}else if(data.code==202){
					$("#telSendCodeBtn").removeClass("act");
					$("#telSendCodeBtn").text("获取验证码");
					//停止计时器
			        window.clearInterval(PWD.interValObj);
					$("#frequent").val("1");//设置操作频繁
					layer.msg("发送短信验证码过于频繁");
				}
			}
		});
};

/**
 * 定时器处理函数
 */
PWD.intervalFunc = function() {
	if(PWD.restTime == 1) {
		//停止计时器
        window.clearInterval(PWD.interValObj);
        //启用发短信按钮
		$("#telSendCodeBtn").removeClass("act");
		//启用按钮
        $("#telSendCodeBtn").text("获取验证码");
	} else {
		PWD.restTime--;
		$("#telSendCodeBtn").text("重新获取(" + PWD.restTime + ")");
	}
};

/**
 * 邮箱表单验证
 */
PWD.validateEmailPwdForm = function(){
	//扩展密码检测方法
	jQuery.validator.addMethod("pwdCheck", function(value, element) {
//		var partten = /(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,16}$/gi;
		var partten = /((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$/gi;
		return partten.test(value);
	}, "密码长度8~16位，其中数字，字母和符号至少包含两种");
	
	jQuery.validator.addMethod("pwdEqOld", function(value, element) {
        var flag=true;
        if($.trim(value)==$.trim($("#eOldPwd").val())){
        	flag=false;
        }
		return flag;
	}, "新旧密码一致，无需修改");
	
	$("#emailPwdForm").validate({
		ignore:"ignore",
		rules: {
			emailValidateCode:{
				required: true,
				remote: {
                    type: "post",
                    url: PWD.basePath+"/regist/emailValidate.do",
                    dataType: "json",
                    data: {
                    	emailAuthcode: function() {
                            return $("#emailValidateCode").val();
                        }
                    },
                    dataFilter: function(data, type) {
                    	data = jQuery.parseJSON(data);
                    	if(!data.message) {
                    		window.clearInterval(PWD.interValObj);
                    		$("#emailSendCodeBtn").text("获取验证码");
                    		return true;
                    	} else {
                    		return false;
                    	}
                    }
				}
			},
			eOldPwd:{
				required: true,
				remote: {
                    type: "post",
                    url: PWD.basePath+"/user/oldPwdCheck.do",
                    dataType: "json",
                    data: {
                    	oldPwd: function() {
                            return $("#eOldPwd").val();
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
			ePwd:{
				required: true,
				pwdCheck:true,
				pwdEqOld:true
			},
			erePwd:{
				required: true,
				equalTo: "#ePwd"
			}
		},
		messages: {
			emailValidateCode: {
				required: "请输入验证码",
				remote: "验证码不正确"
			},
			eOldPwd: {
				required: "请输入原密码",
				remote: "原密码不正确"
			},
			ePwd: {
				required: "请输入新密码",
				pwdCheck: "密码长度8~16位，其中数字，字母和符号至少包含两种",
				pwdEqOld:"新旧密码一致，无需修改"
			},
			erePwd: {
				required: "请重新输入新密码",
				equalTo:"两次输入密码不一致"
			}
		},
		errorPlacement : function(error, element){
			error.appendTo(element.parents('.rowPart').next('p.wrongTips')); 
		},
		submitHandler : function(form) {
			PWD.updatePwd();
		}
	});
};

/**
 * 发送邮件验证码
 */
PWD.sendValidateCodeEmail = function() {
	//初始化剩余时间
	PWD.restTime = PWD.intervalTime - 1;
	//按钮不可用，添加灰色样式
	$("#emailSendCodeBtn").addClass("act");
	//修改按钮显示的文本
	$("#emailSendCodeBtn").text("重新获取(" + PWD.restTime + ")");
	PWD.interValObj = window.setInterval(PWD.emailIntervalFunc, 1000); //启动计时器，1秒执行一次
	//保存定时器返回值
	$.ajax({
		type: "POST",
		url:PWD.basePath+"/user/setTimerReturnVal.do",
		data:{"timerInterVal":PWD.interValObj},
		dataType:"json",
		success: function(data) {
			if(data.code == 200) {
//	 	 		console.log(PWD.interValObj+"保存定时器返回值执行成功");
			}
		}
	});
	//调用发送短信接口
	$.ajax({
		type: "POST",
		url:PWD.basePath+"/user/sendEmailRegisterCode.do",
		data:{"email":$("#email").val(),"type":"updatePwd"},
		dataType:"json",
		success: function(data) {
//			if(data.code != 200) {
//				console.log("发送邮件验证码出现异常");
//			}
		}
	});
};

/**
 * 发送邮件定时器处理函数
 */
PWD.emailIntervalFunc = function() {
	if(PWD.restTime == 1) {
		//停止计时器
        window.clearInterval(PWD.interValObj);
        //启用发短信按钮
		$("#emailSendCodeBtn").removeClass("act");
		//启用按钮
        $("#emailSendCodeBtn").text("获取验证码");
	} else {
		PWD.restTime--;
		$("#emailSendCodeBtn").text("重新获取(" + PWD.restTime + ")");
	}
};

/**
 *选择验证方式
 */
PWD.goValidateWay = function() {
	$.ajax({
        type:"post",
        url:PWD.basePath+"/user/goChooseValidation.do",
        dataType:"html",
        success:function(html){
        	$("#contentDiv").html(html);
        },
        error:function(err) {
        }
    });
};
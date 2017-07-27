CHECKBINDCODE = function(){};

CHECKBINDCODE.basePath = basePath;

//定时器变量
CHECKBINDCODE.interValObj;

//默认发送时间间隔
CHECKBINDCODE.defaultIntervalTime = 60;

//发送验证码间隔时间(单位：秒)
CHECKBINDCODE.intervalTime = 60;

//剩余间隔时间(单位：秒)
CHECKBINDCODE.restTime;

//邮件部分**********************************************************************************************/


/*** 获取邮件*/
CHECKBINDCODE.initEmailCheck = function() {
	
	$("#emailSendCodeBtn").on("click",function(event){
		
		event.preventDefault();
		if($(this).hasClass("act"))
			return;
		
		//发送邮件
		$.ajax({
			type:"post",
			url:CHECKBINDCODE.basePath+"/account/sendEmailBind.do",
			data:{"email":$("#email").val(),"type":"bindEmail"},
			dataType:"json",
			success:function(data){
				if(data.code == 200){
					layer.msg("邮件发送成功");
					{//A开始
						//初始化剩余时间
						CHECKBINDCODE.restTime = CHECKBINDCODE.intervalTime -1;
						//按钮不可用，添加灰色样式
						$("#emailSendCodeBtn").addClass("act");
						//修改按钮显示的文本
						$("#emailSendCodeBtn").text("重新获取(" + CHECKBINDCODE.restTime + ")");
						CHECKBINDCODE.interValObj = window.setInterval(CHECKBINDCODE.intervalFuncEmail, 1000); //启动计时器，1秒执行一次
						//保存定时器返回值
						$.ajax({
							type: "POST",
							url:CHECKBINDCODE.basePath+"/account/setTimerReturnVal.do",
							data:{"timerInterVal":CHECKBINDCODE.interValObj},
							dataType:"json",
							success: function(data) {
								if(data.code == 200) {
//						 	 		console.log(PWD.interValObj+"保存定时器返回值执行成功");
								}
							}
						});	
					}//A结束
				}else{
					layer.msg("邮件发送失败");
				}
			}
		});
		
	});
	
	
	/** 回到验证方式选择界面 */
	$("#returnBtn").click(function(){
		var tipType = $("#tipType").val();
		var url = CHECKBINDCODE.basePath + "/account/toChooseType/1/." + tipType+".do";
		CHECKBINDCODE.junpUrl(url);
	});
	
	/** 检查验证码是否正确 */
	$("#nextBtn").on("click",function(){
		CHECKBINDCODE.bindCodeValidate("2");
	});
	
};
	
//短信**************************************************************************************************/
	/**
	 * 发送短信
	 */
	CHECKBINDCODE.initTelCheck = function() {
		
		$("#telSendCodeBtn").on("click",function(event){
			event.preventDefault();
			if($(this).hasClass("act"))
				return;
			
			
			//发送短信
			$.ajax({
				type:"post",
				url:CHECKBINDCODE.basePath+"/account/sendSMSBind.do",
				data:{"tel":$("#tel").val(),"type":"bindTel","newOrOld":"old"},//newOrOld 区别绑定页面的手机短信发送（以免造成新绑定页面的超时限制）
				dataType:"json",
				success:function(data){
//					alert(data.code);
					if(data.code == 201){
						layer.msg("短信发送失败");
					}else if(data.code == 202){
						layer.msg("发送失败：发送短信过于频繁");
					}else{//200
						layer.msg("短信发送成功");
						{//A开始
							//初始化剩余时间
							CHECKBINDCODE.restTime = CHECKBINDCODE.intervalTime -1;
							//按钮不可用，添加灰色样式
							$("#telSendCodeBtn").addClass("act");
							//修改按钮显示的文本
							$("#telSendCodeBtn").text("重新获取(" + CHECKBINDCODE.restTime + ")");
							CHECKBINDCODE.interValObj = window.setInterval(CHECKBINDCODE.intervalFuncSMS, 1000); //启动计时器，1秒执行一次
							//保存定时器返回值
							$.ajax({
								type: "POST",
								url:CHECKBINDCODE.basePath+"/account/setTimerReturnVal.do",
								data:{"timerInterVal":CHECKBINDCODE.interValObj},
								dataType:"json",
								success: function(data) {
									if(data.code == 200) {
//							 	 		console.log(PWD.interValObj+"保存定时器返回值执行成功");
									}
								}
							});	
						}//A结束
					}
				}
			});
			
		});
		
		
		/** 回到验证方式选择界面 */
		$("#returnBtn").click(function(){
			var tipType = $("#tipType").val();
			var url = CHECKBINDCODE.basePath + "/account/toChooseType/0/" + tipType+".do";
			CHECKBINDCODE.junpUrl(url);
		});
		
		
		/** 检查验证码是否正确 */
		$("#nextBtn").on("click",function(){
			CHECKBINDCODE.bindCodeValidate("1");
		});
	
	};
	
//共用部分************************************************************************************************************/
	/**
	 * 跳转页面，参数：url
	 */
	CHECKBINDCODE.junpUrl = function(url){
		$.ajax({
	        type:"post",
	        url:url,
	        dataType:"html",
	        success:function(html){
	        	$("#contentDiv").html(html);
	        },
	        error:function(err) {
	        }
	    });
		
	};
	
	
	/**
	 * 验证（原手机或原邮箱）验证码是否和session中的一样
	 * codeType:1手机     2邮箱
	 */	
	CHECKBINDCODE.bindCodeValidate = function(codeType){
		
		//判断是否是空
		$("#checkForm").validate({
			rules: {
				bindCode:{
					required: true,
//					remote: {
//	                    type: "post",
//	                    url:CHECKBINDCODE.basePath+"/account/bindCodeValidate.do",
//	                    dataType: "json",
//	                    data: {
//	                    	bindCode:function(){
//	                    		return $("#validateCode").val();
//	                    	},
//	                    	codeType:codeType
//	                    	},
//	                    dataFilter: function(data,type) {
//	                    	data = jQuery.parseJSON(data);
//	                    	return !data.message;
//	                    }
//					}
				},
			},
			messages: {
				bindCode: {
					required: "请输入验证码",
//					remote: "验证码不正确"
				}
			},
			errorPlacement : function(error, element){
				error.appendTo(element.parents('.rowPart').next('p.wrongTips')); 
			},
			submitHandler : function(form) {
				var bindCode = $("#validateCode").val();
				var type = $("#type").val();
				var tipType = $("#tipType").val();
//				alert(bindCode);
				$.ajax({
					type:"post",
					url:CHECKBINDCODE.basePath+"/account/bindCodeValidate.do",
					data:{"bindCode":bindCode,"codeType":codeType},
					dataType:"json",
					success:function(data){
						if(data.message){
							layer.msg("验证码不正确");
						}else{
							$.ajax({
								type:"post",
								url:CHECKBINDCODE.basePath+"/account/newBind.do",
								data:{"type":type,"tipType":tipType},
								dataType:"html",
								success:function(html){
									$("#contentDiv").html(html);
								}
							});//跳转结束
						}
						
					}
				});//内部ajax
			}
		});
		
	};
	
	/*** 邮件定时器处理函数 */
	CHECKBINDCODE.intervalFuncEmail = function() {
		if(CHECKBINDCODE.restTime == 1) {
			//停止计时器
	        window.clearInterval(CHECKBINDCODE.interValObj);
	        //启用发短信按钮
			$("#emailSendCodeBtn").removeClass("act");
			//启用按钮
	        $("#emailSendCodeBtn").text("获取验证码");
		} else {
			CHECKBINDCODE.restTime--;
			$("#emailSendCodeBtn").text("重新获取" + CHECKBINDCODE.restTime + "");
		}
	};

	
	/*** 短信定时器处理函数 */
	CHECKBINDCODE.intervalFuncSMS = function() {
		if(CHECKBINDCODE.restTime == 1) {
			//停止计时器
	        window.clearInterval(CHECKBINDCODE.interValObj);
	        //启用发短信按钮
			$("#telSendCodeBtn").removeClass("act");
			//启用按钮
	        $("#telSendCodeBtn").text("获取验证码");
		} else {
			CHECKBINDCODE.restTime--;
			$("#telSendCodeBtn").text("重新获取" + CHECKBINDCODE.restTime + "");
		}
	};
	
	/** * 清除倒计时定时器 */
	CHECKBINDCODE.clearTimer = function() {
		$.ajax({
			type: "POST",
			url:CHECKBINDCODE.basePath+"/account/getTimerReturnVal.do",
			dataType:"json",
			async:false,
			success: function(data) {
				if(data.code == 200) {
		 	 		window.clearInterval(data.timerInterVal);
//		 	 		console.log(data.timerInterVal+"清除定时器执行成功");
				}
			}
		});
	};
	
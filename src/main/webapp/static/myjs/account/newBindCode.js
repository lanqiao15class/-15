NEWBINDCODE = function(){};

//获取上下文路径
NEWBINDCODE.basePath = basePath;

//定时器变量
NEWBINDCODE.interValObj;

//默认发送时间间隔
NEWBINDCODE.defaultIntervalTime = 60;

//发送验证码间隔时间(单位：秒)
NEWBINDCODE.intervalTime = 60;

//剩余间隔时间(单位：秒)
NEWBINDCODE.restTime;


//邮件部分**********************************************************************************************/
/**
 * 获取邮件
 */
NEWBINDCODE.initEmailCheck = function() {
	
	/** 点击获取邮箱验证码 */
	$("#emailSendCodeBtn").on("click",function(){
		NEWBINDCODE.sendNewBindCode("2");
	});
	
	/** 检查邮箱、图片、验证码正确性,更新绑定 */
	$("#saveBtn").on("click",function(){
		NEWBINDCODE.bindCodeValidate("2");
	});
	
	/** 回到账号安全界面 */
	$("#returnSourceBtn").on("click",function(){
		var url = NEWBINDCODE.basePath + "/account/accountPage.do";
		NEWBINDCODE.junpUrl(url);
	});
	
};
	
//短信**************************************************************************************************/

NEWBINDCODE.initTelCheck = function() {
		
		/** 点击发送短信 */
		$("#telSendCodeBtn").on("click",function(){
			NEWBINDCODE.sendNewBindCode("1");
		});
		
		
		/** 检查手机号、图片、验证码正确性，更新绑定 */
		$("#saveBtn").on("click",function(){
			NEWBINDCODE.bindCodeValidate("1");
		});
		
		
		/** 回到账号安全界面 */
		$("#returnSourceBtn").on("click",function(){
			var url = NEWBINDCODE.basePath + "/account/accountPage.do";
			NEWBINDCODE.junpUrl(url);
		});
	
	};
	
//共用部分************************************************************************************************************/
	/**
	 * 跳转页面，参数：url
	 */
	NEWBINDCODE.junpUrl = function(url){
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
	
	/** 给新的绑定账号发送验证码 参数type:1手机  2，邮箱 */
	NEWBINDCODE.sendNewBindCode = function(type){
		//1，组织数据
		var newAccount = $("#newAccount").val();
		var method,media,url,dataForBind,formatTest;
		if(type == "1"){
			method = "手机号";media = "短信"; dataForBind = {"tel": newAccount,"type":"bindTel","newOrOld":"new"};
			url = NEWBINDCODE.basePath+"/account/sendSMSBind.do";
			formatTest = /^(1+\d{10})$/;
		}else{
			method = "邮箱"; media = "邮件";dataForBind = {"email": newAccount,"type":"bindEmail"};
			url = NEWBINDCODE.basePath+"/account/sendEmailBind.do";
			formatTest = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
		}
		
		//2，判空
		if(newAccount == ""){
			layer.msg("请输入要绑定的"+method);
			return;
		}
		
		//3，判断格式
		if(!formatTest.test(newAccount)){
			layer.msg("请输入正确的"+method);
			return;
		}
		
		
		//4，判重
		$.ajax({
			type:"post",
			url:NEWBINDCODE.basePath+"/account/existedCount.do",
			data:{"newAccount":newAccount,"type":type},
			dataType:"json",
			success:function(data){
				if(data.message == '200'){//新输入的手机绑定数据与当前的绑定一样
					layer.msg("更换绑定的手机号不能与原来的一致");
					return;
				}else if(data.message == '201'){
					layer.msg("更换绑定的邮箱不能与原来的一致");
					return;
				}else if(data.message == '202'){
					layer.msg("该"+method+"已被其他账号绑定");
					return;
				}else if(data.message == '204'){
					layer.msg("程序异常，请联系管理员");
					return;
				}else if(data.message == '203'){
					//可以发送短信（邮件）
					$.ajax({
						type:"post",
						url:url,
						data:dataForBind,//前面组装的url,data：【type和账号】
						dataType:"json",
//						async:false,
						success:function(data){
							if(data.code == 201){
								layer.msg(media+"发送失败");
							}else if(data.code == 202){
								layer.msg(media+"发送失败：发送过于频繁");
							}else{//200
								layer.msg(media+"发送成功");
								$("#newAccountSaved").val(newAccount);//将发送了短信的号码(邮箱)保存，防止在填写正确验证码后修改号码
								NEWBINDCODE.showCodeSpecial();//处理按钮
							}
						}
					});//发送短信（邮件）
				}
			}
		});
		
		
	};
	
	
	
	
	/**
	 * 1，验证号码或者邮箱是否在发送短信（邮件）后更换
	 * 2,验证图片验证码
	 * 3,验证（原手机或原邮箱）验证码是否和session中的一样
	 * type：1，手机   2，邮箱
	 */
	
	NEWBINDCODE.bindCodeValidate = function(type){
		$("#checkForm").validate({
			rules: {
				remoteValidateCode:"required",
				validateCode:"required",
				newAccount:"required"
			},
			messages: {
				remoteValidateCode: "请输入验证码",
				validateCode:"请输入图片验证码",
				newAccount:"请输入新账号"
			},
			errorPlacement : function(error, element){
				error.appendTo(element.parents('.rowPart').next('p.wrongTips')); 
			},
			submitHandler : function() {
				//检查 号码是否在发送短信（邮件）后更换
				var bindAccount = $("#newAccountSaved").val();
				var newAccount = $("#newAccount").val();
				if(bindAccount == ""){
					layer.msg("尚未获取验证码");
					return;
				}else if(bindAccount != newAccount){
					layer.msg("请确认此账号是您获取验证码的账号");
					return;
				}
				
				//首先验证图片验证码
				var inputImageCode = $("#validateCode").val();
				$.ajax({
			        type:"post",
			        url:NEWBINDCODE.basePath+"/account/checkTelJqCode.do",
			        data:{"type":type,"inputImageCode":inputImageCode},
			        dataType:"json",
//			        async:false,//设置同步
			        success:function(data){
			        	if(data.message){
			        		layer.msg("图片验证码不正确");
			        		NEWBINDCODE.refreshCode(type);
			        		return;
			        	}else{
			        		//图片验证码正确
			        		NEWBINDCODE.remoteCode(type,bindAccount);
			        	}
			        },
			        error:function(err) {
			        }
			    });//内部ajax
			}
		});
		
		
	};
	
	/** 验证 手机或者邮箱 验证码 */
	NEWBINDCODE.remoteCode = function(type,bindAccount){
		//验证短信或者邮箱验证码
		var bindCode = $("#remoteValidateCode").val();
		$.ajax({
			type:"post",
			url:NEWBINDCODE.basePath+"/account/bindCodeValidate.do",
			data:{"bindCode":bindCode,"codeType":type},
			dataType:"json",
//			async:false,//设置同步
			success:function(data){
				if(data.message){
					layer.msg("验证码不正确");
					NEWBINDCODE.refreshCode(type);
					return;
				}else{
					$.ajax({//验证码正确，修改绑定
						type:"post",
						url:NEWBINDCODE.basePath+"/account/changeBind.do",
						data:{"type":type,"bindAccount":bindAccount},
						dataType:"json",
						success:function(data){
							if(data.message){//跳转回账号安全界面
								layer.msg("绑定更新成功");
								NEWBINDCODE.junpUrl(NEWBINDCODE.basePath+"/account/accountPage.do");
							}else{
								layer.msg("绑定更新失败");
								return;
							}
						}
					});//修改结束
				}
				
			}
		});//验证短信或者邮箱验证码
	};
	
	
	
	/** 刷新验证码 */
	NEWBINDCODE.refreshCode = function(type){
		if(type == '1')
			$("#validateCodeImg").attr("src",NEWBINDCODE.basePath+"/user/validateCode/bindMobile.do?n="+Math.random());
		else if(type == '2')
			$("#validateCodeImg").attr("src",NEWBINDCODE.basePath+"/user/validateCode/bindEmail.do?n="+Math.random());
	};
	
	/** 定时器处理函数 */
	NEWBINDCODE.intervalFunc = function() {
		if(NEWBINDCODE.restTime == 1) {
			//停止计时器
	        window.clearInterval(NEWBINDCODE.interValObj);
	        //启用发短信按钮
			$("button[name='showCodeSpecial']").removeClass("act");
			//启用按钮
	        $("button[name='showCodeSpecial']").text("获取验证码");
		} else {
			NEWBINDCODE.restTime--;
			$("button[name='showCodeSpecial']").text("重新获取" + NEWBINDCODE.restTime + "");
		}
	};
	
	/** 发送完邮件或者短信后的按钮 */
	NEWBINDCODE.showCodeSpecial = function(){
		{//A开始
			//初始化剩余时间
			NEWBINDCODE.restTime = NEWBINDCODE.intervalTime -1;
			//按钮不可用，添加灰色样式
			$("button[name='showCodeSpecial']").addClass("act");
			//修改按钮显示的文本
			$("button[name='showCodeSpecial']").text("重新获取(" + NEWBINDCODE.restTime + ")");
			NEWBINDCODE.interValObj = window.setInterval(NEWBINDCODE.intervalFunc, 1000); //启动计时器，1秒执行一次
			//保存定时器返回值
			$.ajax({
				type: "POST",
				url:NEWBINDCODE.basePath+"/account/setTimerReturnVal.do",
				data:{"timerInterVal":NEWBINDCODE.interValObj},
				dataType:"json",
				success: function(data) {
					if(data.code == 200) {
//			 	 		console.log(PWD.interValObj+"保存定时器返回值执行成功");
					}
				}
			});	
		}//A结束
		
	};
	
	
	/** 清除倒计时定时器 */
	NEWBINDCODE.clearTimer = function() {
		$.ajax({
			type: "POST",
			url:NEWBINDCODE.basePath+"/account/getTimerReturnVal.do",
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
	

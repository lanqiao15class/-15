/**==================注册相关js==========================*/
/** ====================公共========================== */
REGIST = function(){}

/** 获取上下文路径 */
REGIST.basePath = $("#basePath").val();

/** 刷新验证码 */
REGIST.refreshCode = function() {
	$("#validateCodeImg").attr("src",REGIST.basePath+"/user/validateCode/login.do?n="+Math.random());
}

/** 验证-密码是否包含空格 */
$.validator.addMethod("checkPwdIndexOfNull",function(value,element){
	var str =value;
	if (str.indexOf(" ") == -1) {
	    res = true;
	} else {
	    res = false;
	}
    return res;
},"密码不能包含空格");

/** 验证密码 */
jQuery.validator.addMethod("pwdCheck", function(value, element) {
	var partten = /((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$/gi;
	return partten.test(value);
}, "密码长度8~16位，其中数字，字母和符号至少包含两种");

/** ===================手机注册============================= */
/** 手机号码验证 */
jQuery.validator.addMethod("isMobileCheck", function(value, element) {  
	$("input[name='tel']").parent().next(".wrongTips").html('');
    var length = value.length;    
    return this.optional(element) || (length == 11 && /^1[3|4|5|7|8]\d{9}$/.test(value));    
  }, "请正确填写您的手机号码。");

/** 验证-手机号是否存在 */
$.validator.addMethod("loginTelCheck",function(value,element){
    $.ajax({
        type:"POST",
        url:$("#basePath").val()+"/regist/loginTelCheck.do",
        async:false, 
        data:{"loginTel":value},
        dataType:"json",
        success:function(data){
            if(data.code == 200) {
				res = false;
			} else {
				res = true;
			}
        }
    });
    return res;
},"手机号已存在");

/** 验证-输入机器验证码是否正确 */
$.validator.addMethod("checkjqValiCode",function(value,element){
    $.ajax({
        type:"POST",
        url:$("#basePath").val()+"/regist/checkTelJqCode.do",
        async:false, 
        data:{"jqauthcode":value},
        dataType:"json",
        success:function(data){
        	$("input[name='jqauthcode']").parent().next(".wrongTips").html('');
            if(data.message){
                res = false;
            }else{
                res = true;
            }
        }
    });
    return res;
},"输入的验证码不正确");

var telsendstatus=false;//手机发送状态
/** 手机注册-获取验证码  */
REGIST.sendRegisterVerificationCode=function(){
	var tel=$("input[name='tel']").val().replace(/(^\s*)|(\s*$)/g, "");
	//1.非空验证
	if((tel==null||tel=="")&&(jqauthcode==null||jqauthcode=="")){
		$('#tel').parent().next(".wrongTips").text('');//清空
		$('#tel').parent().next(".wrongTips").append("<label class='error'>请输入手机号码</label>");
		$('#jqauthcode').parent().next(".wrongTips").text('');//清空
		$('#jqauthcode').parent().next(".wrongTips").append("<label class='error'>请输入图片验证码</label>");
		return;
	}else{
		//2.正则验证
		if(length != 11 &&! /^1[3|4|5|7|8]\d{9}$/.test(tel)){
			$('#tel').parent().next(".wrongTips").text('');
			$('#tel').parent().next(".wrongTips").append("<label class='error'>手机号码格式不正确</label>");
			return;
		}else{
			//3.验证右边的验证码
			var jqauthcode=$("input[name='jqauthcode']").val();
			if(jqauthcode==null||jqauthcode==""){
				$("input[name='jqauthcode']").parent().next(".wrongTips").text('');
				$("input[name='jqauthcode']").parent().next(".wrongTips").append("<label class='error'>请输入图片验证码</label>");
				return;
			}else{
				//4.验证验证码是否正确
				 $.ajax({
			        type:"POST",
			        url:$("#basePath").val()+"/regist/checkTelJqCode.do",
			        async:false, 
			        data:{"jqauthcode":jqauthcode},
			        dataType:"json",
			        success:function(data){
			            if(data.message){//验证码有误
			            	$("input[name='jqauthcode']").parent().next(".wrongTips").text('');
			            	$("input[name='jqauthcode']").parent().next(".wrongTips").append("<label class='error'>验证码不正确</label>");
			            	REGIST.refreshCode();
			            	return;
			            }else{//正确
			            	//5.判断手机号是否存在
		            		$.ajax({
		            	        type:"POST",
		            	        url:$("#basePath").val()+"/regist/loginTelCheck.do",
		            	        async:false, 
		            	        data:{"loginTel":$("#tel").val()},
		            	        dataType:"json",
		            	        success:function(data){
		            	            if(data.code==200){//已存在手机号
		            	            	$("input[name='tel']").parent().next(".wrongTips").text('');
		    			            	$("input[name='tel']").parent().next(".wrongTips").append("<label class='error'>手机号已存在</label>");
		            	                return;
		            	            }else{//不存在
		            	            	//赋值,用于手机号和验证码绑定
		            	            	$("#telTemp").val($("#tel").val());
		            	            	//
		            	            	$("input[name='jqauthcode']").parent().next(".wrongTips").text('');
		    			            	if(telsendstatus==false){
		    			    				telsendstatus=true;
		    			    				//定时显示秒数
		    			    				var time=60;
		    			    				var t=setInterval(function(){
		    			    					time--;
		    			    					//$("#getVer").css("background-color","#d9d9d9");
		    			    					$("#getVer").addClass("act");
		    			    					$("#getVer").attr("disabled",true);
		    			    					$("#getVer").html(""+time+"秒");
		    			    					if (time==0) {
		    			    						clearInterval(t);
		    			    						$("#getVer").html("重新获取");
		    			    						$("#getVer").removeAttr("disabled");
		    			    						//$("#getVer").css("background-color","#589cdf");
		    			    						$("#getVer").removeClass("act");
		    			    						telsendstatus=false;
		    			    					}
		    			    				},1000);
		    			    				//4.发送短信
		    			    				$.ajax({
		    			    					type: "POST",
		    			    					url:$("#basePath").val()+"/regist/sendRegisterVerificationCode.do",
		    			    					data:{"tel":tel},
		    			    					dataType:"json",
		    			    					success: function(data) {
		    			    					}
		    			    				});
		    			    			 } 
		            	            }
		            	        }
		            	    });
			            }
			        }
			    });
			}
		}
	}
}

/** 验证-输入短信验证码是否正确 */
$.validator.addMethod("checkValidateCode",function(value,element){
    $.ajax({
        type:"POST",
        url:$("#basePath").val()+"/regist/smsValidate.do",
        async:false, 
        data:{"authcode":value},
        dataType:"json",
        success:function(data){
        	//验证短信验证码||验证短信验证码和手机号码是否绑定
            if(data.message||$("#tel").val()!=$("#telTemp").val()){//不通过
                res = false;
            }else{//通过
                res = true;
            }
        }
    });
    return res;
},"输入的验证码不正确");

$(function(){
	/** 手机注册-提交 */
	$("#telRegister").validate({
		onfocusout: function(element) {//是否在失去时验证 
			$(element).valid(); 
		},
		rules:{
			 tel:{
				required:true,
				isMobileCheck:true,
				loginTelCheck:true
			 },
			 jqauthcode:{
				required:true,
				checkjqValiCode:true
			 },
			 authcode:{
				required:true,
				checkValidateCode:true
			 },
			 pwd:{
				 required:true,
				 checkPwdIndexOfNull:true,
				 pwdCheck:true
			 },
             cpwd:{
            	 required:true,
                 equalTo:"#pwd"//验证密码是否一致
             }
		},
		messages:{
			 tel:{
				required:"请输入手机号码",
				isMobileCheck:"手机号码格式不正确",
				loginTelCheck:"手机号已存在"
			 },
			 jqauthcode:{
				required:"请输入图片验证码",
				checkjqValiCode:"图片验证码不正确"
			 },
			 authcode:{
				required:"请输入短信验证码",
				checkValidateCode:"短信验证码不正确"
			 },
			 pwd:{
				 required:"请输入您的密码",
				 checkPwdIndexOfNull:"密码不能包含空格",
				 pwdCheck:"密码长度8~16位，其中数字，字母和符号至少包含两种"
			 },
             cpwd:{
            	 required:"请再次输入您的密码",
            	 equalTo:"两次密码输入不一致"
             }
		},
		errorPlacement: function(error, element) {  
			error.appendTo(element.parents('.rowPart').next('.wrongTips'));  
		},
		errorCallback:function(element){//自己添加的验证失败回调方法 
			if(!$("#agreement").is(":checked")){
				$("#agreement").parents('.rowPart').next('.wrongTips').html("<label class='error'>请勾选同意用户协议</label>");
			}
			$('#tel').focus(function(){
				if($('#tel').parent().next(".wrongTips").children(".error").html()=='请输入手机号码'){
					$('#tel').parent().next(".wrongTips").text('');//清空
					$('#tel').parent().next(".wrongTips").append("<label class='error'>请输入手机号码</label>");
				}
				if($('#jqauthcode').parent().next(".wrongTips").children(".error").html()=='请输入图片验证码'){
					$('#jqauthcode').parent().next(".wrongTips").text('');//清空
					$('#jqauthcode').parent().next(".wrongTips").append("<label class='error'>请输入图片验证码</label>");
				}
			});
			$('#jqauthcode').focus(function(){
				if($('#tel').parent().next(".wrongTips").children(".error").html()=='请输入手机号码'){
					$('#tel').parent().next(".wrongTips").text('');//清空
					$('#tel').parent().next(".wrongTips").append("<label class='error'>请输入手机号码</label>");
				}
				if($('#jqauthcode').parent().next(".wrongTips").children(".error").html()=='请输入图片验证码'){
					$('#jqauthcode').parent().next(".wrongTips").text('');//清空
					$('#jqauthcode').parent().next(".wrongTips").append("<label class='error'>请输入图片验证码</label>");
				}
			});
			$('#authcode').focus(function(){
				if($('#tel').parent().next(".wrongTips").children(".error").html()=='请输入手机号码'){
					$('#tel').parent().next(".wrongTips").text('');//清空
					$('#tel').parent().next(".wrongTips").append("<label class='error'>请输入手机号码</label>");
				}
				if($('#jqauthcode').parent().next(".wrongTips").children(".error").html()=='请输入图片验证码'){
					$('#jqauthcode').parent().next(".wrongTips").text('');//清空
					$('#jqauthcode').parent().next(".wrongTips").append("<label class='error'>请输入图片验证码</label>");
				}
			});
			
			$('#tel').blur(function(){
				if($('#tel').parent().next(".wrongTips").children(".error").html()=='请输入手机号码'){
					$('#tel').parent().next(".wrongTips").text('');//清空
					$('#tel').parent().next(".wrongTips").append("<label class='error'>请输入手机号码</label>");
				}
				if($('#jqauthcode').parent().next(".wrongTips").children(".error").html()=='请输入图片验证码'){
					$('#jqauthcode').parent().next(".wrongTips").text('');//清空
					$('#jqauthcode').parent().next(".wrongTips").append("<label class='error'>请输入图片验证码</label>");
				}
			});
			$('#jqauthcode').blur(function(){
				if($('#tel').parent().next(".wrongTips").children(".error").html()=='请输入手机号码'){
					$('#tel').parent().next(".wrongTips").text('');//清空
					$('#tel').parent().next(".wrongTips").append("<label class='error'>请输入手机号码</label>");
				}
				if($('#jqauthcode').parent().next(".wrongTips").children(".error").html()=='请输入图片验证码'){
					$('#jqauthcode').parent().next(".wrongTips").text('');//清空
					$('#jqauthcode').parent().next(".wrongTips").append("<label class='error'>请输入图片验证码</label>");
				}
			});
		},  
		submitHandler:function(){//验证通过后进入
			if($("#pwd").val().replace(/(^\s*)|(\s*$)/g, "")==""){
				$("#pwd").parent(".lrow").next("p.err").html("");
				$("#pwd").parent(".lrow").next("p.err").append("<label class='error'>请输入密码</label>"); 
			}else{
				//保存
				if($("#agreement").is(":checked")){
					$.ajax({
						type: "POST",
						url:$("#basePath").val()+"/regist/saveRegister.do",
						data:$('#telRegister').serialize(),
						dataType:"json",
						async:false,
						success: function(data) {
							if(data.code==200){
								layer.open({
								  title:'提示',
								  content: '注册成功!',
								  yes: function(index, layero){
									window.location.href=$("#basePath").val()+"/sso/login.do";	
									layer.close(index); //如果设定了yes回调，需进行手工关闭
								  },
								  cancel: function(){ 
									  window.location.href=$("#basePath").val()+"/sso/login.do";	 
								  }
								}); 
							}else{
								layer.open({
								  title:'提示',
								  content: '注册失败!',
								  yes: function(index, layero){
									layer.close(index); //如果设定了yes回调，需进行手工关闭
								  }
								}); 

							}
						}
					});
				}else {
					$("#agreement").parents('.rowPart').next('.wrongTips').html("<label class='error'>请勾选同意用户协议</label>");
				}
			}
		}
	});
});


/** ===================邮箱注册============================= */

/** 验证-邮箱是否存在 */
$.validator.addMethod("loginEmailCheck",function(value,element){
  $.ajax({
      type:"POST",
      url:$("#basePath").val()+"/regist/loginEmailCheck.do",
      async:false, 
      data:{"loginEmail":value},
      dataType:"json",
      success:function(data){
    	  $('#email').parent().next(".wrongTips").text('');//清空
          if(data.code==200){
              res = false;
          }else{
              res = true;
          }
      }
  });
  return res;
},"邮箱已存在");

/** 邮箱注册-获取验证码 */
var emailsendstatus=false;
REGIST.sendEmailRegisterCode=function(){
	$('#email').parent().next(".wrongTips").text('');
	var email=$("input[name='email']").val().replace(/(^\s*)|(\s*$)/g, "");
	//1.非空
	if(email==null||email==""){
		$('#email').parent().next(".wrongTips").text('');
		$('#email').parent().next(".wrongTips").append("<label class='error'>请输入邮箱</label>");
		return;
	}else{
		//2.验证邮箱
		if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(email)){
			$('#email').parent().next(".wrongTips").text('');
			$('#email').parent().next(".wrongTips").append("<label class='error'>邮箱格式不正确</label>");
			return;
		}else{
			//3.邮箱是否存在
			$.ajax({
		        type:"POST",
		        url:$("#basePath").val()+"/regist/loginEmailCheck.do",
		        async:false, 
		        data:{"email":$("#email").val()},
		        dataType:"json",
		        success:function(data){
		            if(data.code==200){//已存在
		            	$("#email").parent('.rowPart').next('.wrongTips').html("");  
		            	$("#email").parent('.rowPart').next('.wrongTips').append("<label class='error'>邮箱已存在</label>"); 
		            }else{//不存在
		            	if(emailsendstatus==false){
		    				emailsendstatus=true;
		    				
		    				//邮箱与验证码绑定
		    				$("#emailTemp").val($("#email").val());
		    				//3.定时显示秒数
		    				var time=60;
		    				var t=setInterval(function(){
		    					time--;
		    					//$("#getVerEmail").css("background-color","#d9d9d9");
		    					$("#getVerEmail").addClass("act");
		    					$("#getVerEmail").attr("disabled",true);
		    					$("#getVerEmail").html(""+time+"秒");
		    					if (time==0) {
		    						clearInterval(t);
		    						$("#getVerEmail").html("重新获取");
		    						$("#getVerEmail").removeAttr("disabled");
		    						//$("#getVerEmail").css("background-color","#589cdf");
		    						$("#getVerEmail").removeClass("act");
		    						emailsendstatus=false;
		    					}
		    				},1000);
		    				//4.发送邮件
		    				$.ajax({
		    					type: "POST",
		    					url:$("#basePath").val()+"/regist/sendEmailRegisterCode.do",
		    					data:{"email":email,"type":"register"},
		    					dataType:"json",
		    					success: function(data) {
		    					}
		    				});
		    			}
		            }
		        }
		    });
		}
	}
}

/** 验证-输入邮箱验证码是否正确 */
$.validator.addMethod("checkEmailValidateCode",function(value,element){
    $.ajax({
        type:"POST",
        url:$("#basePath").val()+"/regist/emailValidate.do",
        async:false, 
        data:{"emailAuthcode":value},
        dataType:"json",
        success:function(data){
        	$('#email').parent().next(".err").text('');//清空
        	//验证验证码||验证验证码和邮箱是否绑定
            if(data.message||$("#email").val()!=$("#emailTemp").val()){//不通过
                res = false;
            }else{//通过
                res = true;
            }
        }
    });
    return res;
},"输入的邮箱验证码不正确");

/** 邮箱注册-提交 */
$(function(){
	$("#emailRegister").validate({
		onfocusout: function(element) {//是否在获取焦点时验证 
			$(element).valid(); 
		},
		onkeyup:false,//是否在敲击键盘时验证
		rules:{
			email:{
				required:true,
				isEmail:true,
				loginEmailCheck:true
			 },
			 emailauthcode:{
				required:true,
				checkEmailValidateCode:true
			 },
			 ePwd:{
				required:true,
				checkPwdIndexOfNull:true,
				pwdCheck:true
			 },
			 ecPwd:{
				 required:true,
                 equalTo:"#ePwd"//验证密码是否一致
             }
		},
		messages:{
			 email:{
				required:"请输入邮箱",
				isEmail:"邮箱格式不正确",
				loginEmailCheck:"邮箱已存在"
			 },
			 emailauthcode:{
				required:"请输入邮箱验证码",
				checkEmailValidateCode:"邮箱验证码不正确"
			 },
			 ePwd:{
				 required:"请输入密码",
				 checkPwdIndexOfNull:"密码不能包含空格",
				 pwdCheck:"密码长度8~16位，其中数字，字母和符号至少包含两种"
			 },
			 ecPwd:{
				 required:"请再次输入您的密码",
				 equalTo:"两次密码输入不一致"
			 }
		},
		errorPlacement: function(error, element) {  
			error.appendTo(element.parent('.rowPart').next('.wrongTips'));  
		},
		errorCallback:function(element){//自己添加的验证失败回调方法 
			if(!$("#emailagreement").is(":checked")){
				$("#emailagreement").parents('.rowPart').next('.wrongTips').html("<label class='error'>请勾选同意用户协议</label>");
			}
			$('#email').blur(function(){
				if($('#email').parent().next(".wrongTips").children(".error").html()=='请输入邮箱'){
					$('#email').parent().next(".wrongTips").text('');//清空
					$('#email').parent().next(".wrongTips").append("<label class='error'>请输入邮箱</label>");
				}
			});
			
		},  
		submitHandler:function(){
			if($("#ePwd").val().replace(/(^\s*)|(\s*$)/g, "")==""){
				$("#ePwd").parent(".rowPart").next(".wrongTips").html("");
				$("#ePwd").parent(".rowPart").next(".wrongTips").append("<label class='error'>请输入密码</label>");
				 
			}else{
				if($("#emailagreement").is(":checked")){
					//保存
					$.ajax({
						type: "POST",
						url:$("#basePath").val()+"/regist/saveEmailRegister.do",
						data:$('#emailRegister').serialize(),// 你的formid
						dataType:"json",
						async:false,
						success: function(data) {
							if(data.code==200){
								layer.open({
								  title:'提示',
								  content: '注册成功!',
								  yes: function(index, layero){
									window.location.href=$("#basePath").val()+"/sso/login.do";	
									layer.close(index); //如果设定了yes回调，需进行手工关闭
								  },
								  cancel: function(){ 
									  window.location.href=$("#basePath").val()+"/sso/login.do";	 
								  }
								}); 
							}else{
								layer.open({
								  title:'提示',
								  content: '注册失败!',
								  yes: function(index, layero){
									layer.close(index); //如果设定了yes回调，需进行手工关闭
								  }
								}); 
							}
						}
					});
				} else {
					$("#emailagreement").parents('.rowPart').next('.wrongTips').html("<label class='error'>请勾选同意用户协议</label>");
				}
			}
		}
	});
});

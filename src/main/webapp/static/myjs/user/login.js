
Login = function(){};

//获取上下文路径
Login.basePath = basePath;

/**
 * 用户登录
 */
Login.init = function() {
	Login.validateForm();
};

/**
 * 
 * 登录校验
 * 
 */
Login.validateForm = function(){
	//检查空
	jQuery.validator.addMethod("emptyCheck", function(value, element) {
		return value != "";
	}, "密码格式不正确");
	//空格检测
	jQuery.validator.addMethod("blankCheck", function(value, element) {
		var partten = /^((\s.*)|(.*\s))$/gi;
		return !partten.test(value);
	}, "密码格式不正确");
	$("#loginForm").validate({
		ignore:"ignore",
		rules: {
			userName:{
				required: true
			},
			password:{
				emptyCheck: true,
				blankCheck: true
			},
			validateCode:{
				required: true
				
			}
		},
		messages: {
			userName: {
				required: "请输入邮箱，手机号或者蓝桥账号",
				remote: "该用户不存在"
			},
			password: {
				emptyCheck: "请输入密码",
				blankCheck: "密码格式不正确"
			},
			validateCode: {
				required: "请输入验证码",
				remote: "验证码输入错误"
			}
		},
		success : function(element) {
			
		},
		errorPlacement : function(error, element){
		//	alert("errorPlacement:"+ error);
			error.appendTo(element.parent().next(".wrongTips"));
		},
		invalidHandler:function() {
			return false;
		},
		submitHandler : function(form) {
			Login.login();
			return false;
		}
	});
};

/**
 * 
 * 登录
 * 
 */
Login.login = function(){
	var backurl = $("input[name=backurl]").val();
	
	//console.log("backurl:" + backurl);
	//	alert(backurl);
	$.ajax({
        type:"post",
        url:Login.basePath+"/user/loginpost.do",
        canback:false,
        data:{"userName":$("#userName").val(),
        	"password":$("#password").val(),
        	"vcode":$("#validateCode").val(),
        	"backurl":backurl,
        	 openid:$("#openid").val()},
        dataType:"json",
        success:function(data){
        	
        	switch (data.code) 
        	{
        		case 300:
        			{
        				document.location.href=data.backurl;
        			}
        		break;
				case 200:
					$("#alltip").html("登录成功,正在跳转到主页...");
					setTimeout(function(){
						if(data.usertype =="1")
							window.location.href = Login.basePath+"/user/home.do";
						else if(data.usertype =="0")
							window.location.href = Login.basePath+"/student/home.do";
							
					},1000);
					break;
				default:
					$("#alltip").html(data.msg);
					Login.refreshCode();
					break;
			}
        },
        error:function(err) {
        	layer.msg("系统异常");
        }
	});
};


/**
 * 刷新验证码
 */
Login.refreshCode = function() {
	$("#validateCodeImg").attr("src",Login.basePath+"/user/validateCode/login.do?n="+Math.random());
};

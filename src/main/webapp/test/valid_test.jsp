<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/view/include/publicjs.jsp"%>

</head>
<body>
<form id="form1">
	
	用户名: <input type="text" name="username" ><span></span> <br/>
	年龄: <input type="text" name="age" ><span></span><br/>
	
	别名: <input type="text" name="lastname" ><span></span><br/>
	 <span></span>
	<br/>
	<input type="submit" value="提交">
</form>



<input type="button" value="更换验证方式" onclick="changeValid()">

<script>
var gl_setting = null;

$(function(){
	
	setupValidate();
	
});

function changeValid()
{
	$("span").text("");
	//移除 用户名的验证规则.
	delete gl_setting.settings.rules.username; 
	delete gl_setting.settings.messages.username; 
	//printObject(gl_setting.settings.rules);
	//printObject(gl_setting.settings.messages);
	//添加别名 规则
	gl_setting.settings.rules.lastname={required:true};
	gl_setting.settings.messages.lastname={required:"别名不能为空"};
	
	console.log("ignore:"+gl_setting.settings.ignore);
	gl_setting.settings.ignore="#id4,id5";
	
	delete gl_setting.settings;
	
	
}

function setupValidate()
{
	gl_setting= $("#form1").validate({
	/*	onfocusout: function(element) {//是否在失去时验证 
		//$(element).valid(); 
	},*/
	ignore: "#id1,#id2",//验证隐藏域
	rules:{
		username:{
			required:true
		},
		age:{
			required:true,
			digits:true
		}
		
	},

	messages:{
		username:{
			required:"不能为空"
		},
		age:{
			required:"不能为空",
			digits:"必须为数字"
		}
	},
	success : function(element) {
		
	},
	errorPlacement: function(error, element) {  
		error.appendTo(element.next("span"));
	},
	errorCallback:function(element){
		//console.log("errorCallback  name=" +$(element).attr("name"));
	},  
	submitHandler:function(form){
	//保存处理
		console.log("submitHandler...");

		
	}
});
  
  
  console.log(gl_setting);
}


 
</script>

</body>
</html>
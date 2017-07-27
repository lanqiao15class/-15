var Dimission = {};

/** 离职登记初始化 */
Dimission.init = function(userids) {
	Dimission.checkInfo();
}


Dimission.callback=null;//
/** 点击查看学员详情(获取数据) */
Dimission.leaveRegistrationDio=function(userId,blcallback){
	Dimission.callback=blcallback;
	 $.ajax({
		  url:basePath+'/dimission/goDimission.do',
		  notloading:true,//是否显示加载层.
		  data:{"userId":userId},
		  type:"post",
		  dataType:"html",
		  success: function(data){
			  Dimission.dimissionOpen(data);
		  }
	 });
}
Dimission.layerdialog=null;
/** 打开离职登记弹窗 */
Dimission.dimissionOpen=function(data){
	Dimission.layerdialog=layer.open({
		  type: 1, 
		  title: ['离职登记'],
		  skin: 'demo-class',
		  shade: 0.6,
		  area: '520px',
		  anim:8,
		  content: data //这里content是一个普通的String
	});
}


/** 离职登记验证提交 */
Dimission.checkInfo=function(){
	$("#dimissionForm").validate({
		onfocusout: function(element) {//是否在失去时验证 
			$(element).valid(); 
		},
		rules:{
			dismissTime:{
				required:true,
				compareDate:true
			 },
			 dismissReason:{
				required:true
			 }
		},
		messages:{
			dismissTime:{
				required:"请选择日期",
				compareDate:"离职不能比入职时间早"
			 },
			 dismissReason:{
				required:"请填写离职原因"
			 }
		},
		errorPlacement: function(error, element) {  
			error.appendTo(element.parents('.clearfix').next('.error-tips'));  
		},
		errorCallback:function(element){//自己添加的验证失败回调方法 
			
		},  
		submitHandler:function(){//验证通过后进入
			//保存
			$.ajax({
				type: "POST",
				url:basePath+"/dimission/saveDimission.do",
				data:$('#dimissionForm').serialize(),
				dataType:"json",
				async:false,
				success: function(data) {
					if(data.code==200){
						layer.alert("离职登记成功");
						Dimission.closeDialog(); 
					}else{
						layer.alert("离职登记失败");
					}
				}
			});
		}
	});
}



/** 关闭窗口 */
Dimission.closeDialog = function() {
	layer.close(Dimission.layerdialog);
	
	if(Dimission.callback)
		Dimission.callback.apply();
};




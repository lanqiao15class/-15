UpdateClassStatus={};
UpdateClassStatus.init=function()
{
	UpdateClassStatus.setupValid();
	
};

/**
 * 是否可以修改班级的状态,
 * statuscode:当前班级的状态. 
 */
UpdateClassStatus.canUpdateClassStatus=function(statuscode)
{
	var bret =false;
	if(gl_classStatus.NOCLASSES== statuscode
			|| gl_classStatus.INTHELECTURE== statuscode
			|| gl_classStatus.BEFORETHETRAININGSESSION== statuscode
			|| gl_classStatus.INTHETRAINING== statuscode
			|| gl_classStatus.THEGRADUATION== statuscode
					
	)
	{
		  bret =true;
	}
	return bret;
};




UpdateClassStatus.callback =null;
UpdateClassStatus.layerdialog = null;

UpdateClassStatus.opendialog= function (classid ,clback)
{
	UpdateClassStatus.callback= clback;
	
	 $.ajax({
		  url:basePath+'/status/showclassstatus.do',
		  data:{"classid":classid},
		  type:"post",
		  dataType:"html",
		  success: function(data){
			  
				UpdateClassStatus.layerdialog=layer.open({
					  type: 1, 
					  title: ['修改班级状态'],
					  skin: 'demo-class',
					  shade: 0.6,
					  area: '520px',
					  anim:8,
					  area: ['540px'],
					  content: data //这里content是一个普通的String
				});
				
				
		  }
	 });
	 
	
	
};

/**
 * 关闭窗口
 */
UpdateClassStatus.closeDialog=function()
{
	layer.close(UpdateClassStatus.layerdialog);
	if(UpdateClassStatus.callback)
		{
			UpdateClassStatus.callback.apply();
		}
	
};

UpdateClassStatus.setupValid =function(stcode)
{

	//验证规则. 
	$("#classform1").validate({
		onfocusout: function(element) {//是否在失去时验证 
			$(element).valid(); 
		},
		ignore: "",//验证隐藏域
		rules:{
			happentime:{
				required:true
			},
			finishcount:{
				required:true,
				digits:true
			},
			remark:{
				required:true,
				maxlength:200
			},
			jobbegintime:{
				required:true
			}
			
		},
		messages:{
			jobbegintime:{
				required:"不能为空"
			},
			happentime:{
				required:"不能为空"
			},
			finishcount:{
				required:"课时数量不能为空",
				digits:"必须为数字"
			},
			remark:{
				required:"请输入备注信息",
				maxlength:"最大长度超过了200字"
			 }
			
		},
		success : function(element) {
			
		},
		errorPlacement: function(error, element) {  
			error.appendTo(element.parents('.clearfix').next('.error-tips'));
		},
		errorCallback:function(element){
			//console.log("errorCallback  name=" +$(element).attr("name"));
		},  
		submitHandler:function(form){
		//保存处理
			console.log("submitHandler...");
			var senddata  = $(form).serialize();
			var strurl =basePath + "/status/postclassstatus.do";
			 $.ajax({
				  dataType:"json",
				  url:strurl,
				  data:senddata,
				  type:"post",
				  success: function(k_data){
					if(k_data.code ==200)
					{
						UpdateClassStatus.closeDialog(); 
					}else
						{
							layer.alert("错误:" + k_data.message);
						}
				  }
			  });
			
		}
	});
		
}
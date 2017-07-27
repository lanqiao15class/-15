ChangeStudentStatus={};

///页面加载后执行.
ChangeStudentStatus.init=function(_options)
{
	console.log("init ....");
	$("form[status-code]").each(function(ind,obj){
		
		if(ind ==0)
			$(obj).show();
		else
			$(obj).hide();
		
	});
	//批量安装表单验证
	if(_options && _options.statuslist)
	{
		for(i=0;i<_options.statuslist.length;i++)
			ChangeStudentStatus.setupValid(_options.statuslist[i]);
	}

	
}

ChangeStudentStatus.selfDialog= null;
//关闭dialog
ChangeStudentStatus.closeDialog=function()
{
	//console.log("closeDialog ...");
	//$("#formfire").submit();
	 if(ChangeStudentStatus.selfDialog)
	 {
		  layer.close(ChangeStudentStatus.selfDialog);
	 }
	 
	 if(ChangeStudentStatus.closecallback)
		 ChangeStudentStatus.closecallback.apply();
}


ChangeStudentStatus.closecallback= null;


//弹出修改状态窗口. 
//stuid :学生id
//closecallback : 窗口关闭时,调用的回调函数.
//
ChangeStudentStatus.openDialog=function(stuid,closecallback)
{
	//回调函数
	ChangeStudentStatus.closecallback = closecallback;
	
	var strurl = basePath + "/status/showstatus.do";
	 $.ajax({
		  dataType:"html",
		  url:strurl,
		  data:{studentid:stuid},
		  type:"post",
		  success: function(k_data){
			  ChangeStudentStatus.selfDialog= layer.open({
				  type: 1, 
				  title: ['修改学员状态'],
				  skin: 'demo-class',
				  shade: 0.6,
				  anim:8,
				  area: '520px',
				  content: k_data 
				});
		  }
	  });

}

//状态变化的时候, 改变显示的内容,输入不同的数据项. 
ChangeStudentStatus.changediv=function(_this)
{
	var newstatus = $(_this).val();
	console.log("newstatus==" + newstatus);
	{
		//休学状态. 
		$("form[status-code]").each(function(ind,obj){
			
			$(obj).hide();
		});
		
		$("form[status-code="+newstatus+"]").show();
		$("form[status-code="+newstatus+"] select[name=newstatus]").val(newstatus);
		
		
	}

}


ChangeStudentStatus.setupValid =function(stcode)
{

	//验证规则. 
	$("form[status-code="+stcode+"]").validate({
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
			endstudytime:{
				required:true,
			},
			otherdate:{
				required:true
			}
			
		},
		messages:{
			endstudytime:{
				required:"不能为空",
			},
			otherdate:{
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
			var strurl =basePath + "/status/postshowstatus.do";
			 $.ajax({
				  dataType:"json",
				  url:strurl,
				  data:senddata,
				  type:"post",
				  success: function(k_data){
					if(k_data.code ==200)
					{
						ChangeStudentStatus.closeDialog(); 
					}else
						{
						layer.alert("错误:" + k_data.message);
						
						}
				  }
			  });
			
		}
	});
		
}

/*
 * 是否可以弹出修改状态的窗口. 
 *  主页内调用 .
 */
ChangeStudentStatus.canEnterIn=function(current_status)
{
	var bret =true;
	if(current_status == gl_enumStatus.WORKED 
			||current_status == gl_enumStatus.XIUXUEBack 
			||current_status == gl_enumStatus.EXPEL 
			||current_status == gl_enumStatus.QUANTUI 
			||current_status == gl_enumStatus.LEAVESCHOLL 
			||current_status == gl_enumStatus.FindJobing 
			
			)
		bret =false;
	return bret;
}




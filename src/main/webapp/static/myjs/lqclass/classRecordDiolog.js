CLASSRECORDDIOLOG=function(){};

CLASSRECORDDIOLOG.basePath=basePath;

CLASSRECORDDIOLOG.init=function(){
	CLASSRECORDDIOLOG.validRecord();
};

CLASSRECORDDIOLOG.validRecord=function(){
	$("#classRecordForm").validate({
		onfocusout: function(element) {//是否在失去时验证 
			//$(element).valid(); 
		},
		rules:{
			visitContent:{
				required:true,
				maxlength:200
			}
		},
		messages:{
			visitContent:{
				required:"内容不能为空",
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
			var strurl =basePath + "/classVisit/saveClassRecord.do";
			 $.ajax({
				  dataType:"json",
				  url:strurl,
				  data:senddata,
				  type:"post",
				  success: function(k_data){
					  if(k_data.code==200){
						  CLASSRECORDDIOLOG.closeDialog();   
					  }else{
						  layer.msg(k_data.message);  
					  }
				  }
			  });
			
		}
	});	
};

//打开弹窗
CLASSRECORDDIOLOG.callback =null;
CLASSRECORDDIOLOG.layerdialog = null;

CLASSRECORDDIOLOG.opendialog= function (lqClassId ,clback)
{
	CLASSRECORDDIOLOG.callback= clback;
	
	 $.ajax({
		  url:basePath+'/lqClass/showClassRecord.do',
		  data:{"lqClassId":lqClassId},
		  type:"post",
		  dataType:"html",
		  success: function(data){
			  
				CLASSRECORDDIOLOG.layerdialog=layer.open({
					  type: 1, 
					  title: ['创建班级记录'],
					  skin: 'demo-class',
					  shade: 0.6,
					  area: '520px',
					  anim:8,
					  content: data //这里content是一个普通的String
				});
				
				
		  }
	 });
	 
	
	
};
//关闭弹窗
CLASSRECORDDIOLOG.closeDialog=function(){
	layer.close(CLASSRECORDDIOLOG.layerdialog);
	if(CLASSRECORDDIOLOG.callback){
		CLASSRECORDDIOLOG.callback.apply();
	}
};
CLASS_ADD_DIALOG = function(){};

//获取上下文路径
CLASS_ADD_DIALOG.basePath = basePath;

//初始化加载
/**=====================================init=============================================== */

CLASS_ADD_DIALOG.init = function(){
	
	//1,点击选择班级一级类型事件
	$("#classTypePre").on("change",function(){
		CLASS_ADD_DIALOG.showClassTypeReal();
	});
	
	//2,点击执行班级添加
	$("#addClassBtn").on("click",function(){
		CLASS_ADD_DIALOG.submitClassAdd();
	});
	
	//3,控制班长的显隐
	$(".monitor").hide();//将班长隐藏
	
	//4,移入班级名称，显示提示
	var tipContent = "1，全程基地班：年份+基地名称+序号，<br> 比如北京基地2016年的第1个班：2016北京基地01班 <br>2，非全程基地班：开班申请上的拟开班日期+院校名称+X期X班 <br>3，班级名称不允许重复";
	$("#className").mouseenter(function(){
    	layer.tips('<span style="color: #778a99;">'+tipContent+'</span>', '#className', {
    		  tips: [2, 'yellow'],
    		  time:5000,
    		  tipsMore: false,
    		  color:'black'
    		});
    });
    //5,移出班级名称位置，提示关闭
    $("#className").mouseout(function(){
    	layer.closeAll('tips');
    });
	
  //6,设置监听，处理各个老师的错误提示不消失问题
	$("div[class=searchable-select-holder").bind('DOMNodeInserted', function(e) {
//	    alert('element now contains: ' + $(e.target).html());
		if($(e.target).html().length > 0)
			$(e.target).parents('.line-item-ver').find('.error-tips').css({"opacity":"0"});
		else
			$(e.target).parents('.line-item-ver').find('.error-tips').css({"opacity":"1"});
	});

};
/**=====================================init=============================================== */

/**======================================== 跳转界面========================================== */
CLASS_ADD_DIALOG.jumpUrl = function(data,url){
	$.ajax({
        type:"post",
        url:CLASS_ADD_DIALOG.basePath+url,
        data:data,
        dataType:"html",
        success:function(html){
        	$("#contentDiv").html(html);
        },
        error:function(err) {
        }
    });
};
/**========================================  跳转界面========================================== */


/**========================================  获取对应的二级类型开始========================================== */
CLASS_ADD_DIALOG.showClassTypeReal = function(){
	var classTypePre = $("#classTypePre").val();
	if(classTypePre == ""){
		$("#classTypeReal").html("");//清空二级 
		return;
	}
	//获取二级类型
	$.ajax({
		type:"post",
		url:basePath+"/student/getClassTypeReal.do",
		data:{"typePre":classTypePre},
		dataType:"json",
		success:function(data){
			if(data.code == 200){
				var tdata = data.list;
				var html = "<option value=''>请设置班级二级类型 </option>"; 
				for ( var i = 0; i < tdata.length; i++) {
					html += "<option value='"+tdata[i].value+"'>" + tdata[i].label + "</option>";
				}
				$("#classTypeReal").html(html);
			}else{
				layer.msg("获取二级类型失败，请稍后再试");
			}
		}
	});
	
};
/**========================================  获取对应的二级类型结束========================================== */


/**==========================================表单验证开始========================================== */
CLASS_ADD_DIALOG.submitClassAdd = function(){
	
	//开课时间和结业时间对比
	jQuery.validator.addMethod("timeCompare", function(value, element) {
		var startTime1 = $("#startTime1").val();
		startTime1 = startTime1.replace("-","").replace("-","");
		var graduateTime1 = value.replace("-","").replace("-","");
		return Number(graduateTime1)-Number(startTime1) > 0;
	}, "结业时间必须大于开课时间");
	
	//validate验证
	$("#classAddForm").validate({
		ignore:"",
		rules : {
			className : {
				required:true,
				remote: {
                    type: "post",
                    url: basePath+"/student/classNameUniqueCheck.do",
                    dataType: "json",
                    data: {
                    	className: function() {
                            return $("#className").val();
                        },
//                        classId: function() {
//							return null;
//						}
                    },
                    dataFilter: function(data, type) {
                    	data = jQuery.parseJSON(data);
                    	if(data.code == 200) {
                    		return false;
                    	} else {
                    		return true;
                    	}
                    }
				}
			},
			typePre : "required",
			typeReal : "required",
			courseType : "required",
			startTime1 : "required",
			schoolTime1:"required",
			graduateTime1 : {
				required:true,
				timeCompare:true
			}, 
			qqGroup : {
//					required : true,
				digits : true
			},
			stuCount : {
				required : true,
				digits : true,
				min : 1
			},
			comTeacherId : "required",
//			cepTeacherId : "required",
			chrTeacherId : "required",
			classGoal:"required"
		},
		messages : {
			className : {
				required:"请输入班级名称",
				remote:"该班级名称已存在",
			},
			typePre : "请选择一级类型",
			typeReal : "请选择二级类型",
			courseType : "请设置课程类别",
			startTime1 : "请设置拟开课时间",
			schoolTime1:"请设置拟校园结课时间",
			graduateTime1 : {
				"required":"请设置拟结业时间",
				"timeCompare":"结业时间必须大于开课时间"
			},
			qqGroup : {
//					required : "输入ＱＱ群",
				digits : "输入格式不正确"
			},
			stuCount :{
				required : "请输入开班人数",
				digits : "输入格式不正确",
				min : "输入值不能小于1"
			},
			comTeacherId : "请选择教学老师",
//			cepTeacherId : "请选择cep老师",
			chrTeacherId : "请选择职业经纪人",
			classGoal:"请选择开班目的"
		},
		errorCallback:function(element){
		},
		submitHandler : function(form) {
			var myData = $("#classAddForm").serialize();
			$.ajax({
				type : "post",
				url : basePath + "/student/addClass.do",
				data : myData,
				dataType : "json",
				success : function(data) {
					if(data.code == 200){
						$("#classAddReturnBtn").trigger('click');//关闭弹窗
						if($("#type").val() == 'mergeClass'){//合并班级界面
							CLASSES_MERGE.select_class_classMerge.selectOption(data.newClassId,data.className);
						}else{//学员分班转班界面
							STUDENT_INTO_CLASS.select_class.selectOption(data.newClassId,data.className);
						}
						layer.msg("班级添加成功,列表已刷新");
					}else {
						layer.msg("班级添加失败");
					}
					
				}
			});
			
		},errorPlacement : function(error, element) {
			element.parents('.line-item-ver').find('.error-tips').html("");
			error.appendTo(element.parents('.line-item-ver').find('.error-tips'));
		}
	});
	
};
/**==========================================表单验证结束========================================== */

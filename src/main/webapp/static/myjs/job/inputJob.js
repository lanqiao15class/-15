var InputJob = {};
InputJob.Userids=null;

InputJob.init = function(userids) {
	console.log("userids:"+userids);
	InputJob.Userids = userids;
	InputJob.setupValid();
}

InputJob.initSlickTable=function()
{
	/** ============表格开始============* */
	// 定义表格的功能
	var options = {
		editable : false, // 是否可以表格内编辑
		enableCellNavigation : true,
		asyncEditorLoading : false,
		enableColumnReorder : false,
		forceFitColumns : true,// 自动占满一行
		rowHeight : 35 // 行高
	// 高度自动
	};

	// checkbox 列的定义.
/*	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass : "slick-cell-checkboxsel"
	});*/

	// 定义表格的字段
	var columns = [];
	//columns.push(checkboxSelector.getColumnDefinition()); // checkbox 列

	columns.push({
		id : "indexNo", // 唯一标识
		name : "序号",// 字段名字
		field : "indexNo", // 显示数据中的某一个字段.对应到数据对象中的属性名
		width : 120,
		formatter : InputJob.CommonRender
	});

	columns.push({
		id : "stuNo",
		name : "学员编号",
		field : "stuNo",
		width : 200,
		formatter : InputJob.CommonRender
	});

	columns.push({
		id : "realName",
		name : "学员姓名",
		field : "realName",
		width : 150,
		formatter : InputJob.CommonRender
	});

	columns.push({
		id : "className",
		name : "班级名称",
		field : "className",
		width : 150,
		formatter : InputJob.CommonRender
	});

	columns.push({
		id : "courseType",
		name : "课程类别",
		field : "courseType",
		width : 150,
		formatter : InputJob.CommonRender
	});

	columns.push({
		id : "schoolName",
		name : "所在院校",
		field : "schoolName",
		width : 150,
		formatter : InputJob.CommonRender
	});


	columns.push({
		id : "option",
		name : "操作",
		field : "option",
		cssClass : "slick-cell-action",
		width : 200,
		formatter : InputJob.actionRender
	});
	// 产生grid 控件
	InputJob.Grid = new Slick.Grid("#myinputjobTable", [], columns, options);

	InputJob.Grid.registerPlugin(new Slick.AutoTooltips({
		enableForHeaderCells : true
	}));
	// 设置为行选无效
	InputJob.Grid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
	// 注册插件. checkbox 显示方式做为一种插件
	//InputJob.Grid.registerPlugin(checkboxSelector);
	// 检测表格选中事件
	InputJob.Grid.onSelectedRowsChanged.subscribe(function(e, args) {

		// ZSDIVIDECLASS.updateRowHashMap();

	});

	// 移动到行上, 改变背景颜色
	$(document).on("mouseover", "div.slick-cell", function(e) {
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	});

	$(document).on("mouseout", "div.slick-cell", function(e) {
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	});
	/** ============表格结束============* */
	
}

InputJob.CommonRender = function(row, cell, value, columnDef, dataContext) {
	var s = value;
	
	return s;
};

InputJob.actionRender = function(row, cell, value, columnDef, dataContext) {

	var s = "";
	var icon_del='<a onclick="InputJob.deleteRow('+dataContext.userid+')" class="c-blue opt-incon-btn" href="javascript:void(0);" title="删除"><i class="opti-icon Hui-iconfont">&#xe609;</i></a>';
	
	return icon_del;

};



InputJob.deleteRow=function (uid)
{
	var rowindex =-1;
	var tdata = InputJob.Grid.getData();
	//查找到记录. 得到序号.
	for(i=0;i<tdata.length;i++)
	{
		//console.log("userid:"+tdata[i].userid);
		if(tdata[i].userid == uid)
		{
			rowindex =i;
			break;
		}
	}
	//console.log("after deleterow rowindex " + rowindex +"uid=" + uid);
	 tdata.splice(rowindex ,1);
	
	 //重新排序
	 for(i=0;i<tdata.length;i++)
		{
		  tdata[i].indexNo=i+1;	
		}
	 
	 InputJob.Grid.setData(tdata);
	 InputJob.Grid.render();
	
	 
	 $("#span_mancount").text(tdata.length);
		
}


InputJob.submitClick=function(){
	
	console.log("submitClick...");
	$("#inputjobformid").submit();
	
};

InputJob.doTeacherChange=function(sid, stext)
{
	console.log("doTeacherChange:" + sid +":"+stext);
	if(sid !=null && sid !="")
	{
		$("#span_teacher_tip").html("");
	}
	
};


InputJob.setupValid=function()
{
	// 判断浮点型
	jQuery.validator.addMethod("isTeacherId", function(value, element) {
		var bret = ($("#teacherId").val()==null || $("#teacherId").val() == "" ) ?  false:true;
		//alert($("#teacherId").val());
		return bret;
		
	}, "不能为空");
	//验证规则. 
	$("#inputjobformid").validate({
			onfocusout: function(element) {//是否在失去时验证 
			$(element).valid(); 
		},
		ignore: "",//验证隐藏域
		rules:{

			hideteacherId:{
				isTeacherId:true
			},
			companyName:{
				required:true
			},
			companyType:{
				required:true
			},
			
			companyScale:{
				required:true
			},
			
			positionName:{
				required:true
			},
			positionType:{
				required:true
			},
			positionSalary:{
				required:true,
				digits:true
			},
			provId:{
				required:true
			},
			cityId:{
				required:true
			},
			entryTime:{
				required:true
			}
			
		},
		messages:{
			hideteacherId:{
				isTeacherId:"不能为空"
			},
			companyName:{
					required:"不能为空"
			},
			companyType:{
				required:"不能为空"
			},
			
			companyScale:{
				required:"不能为空"
			},
			
			positionName:{
				required:"不能为空"
			},
			positionType:{
				required:"不能为空"
			},
			positionSalary:{
				required:"不能为空",
				digits:"必须为数字"
			},
			provId:{
				required:"不能为空"
			},
			cityId:{
				required:"不能为空"
			},
			entryTime:{
				required:"不能为空"
			}
		},
		success : function(element) {
	/*		console.log("success " + element[0].tagName);

			var sval = $("#teacherId").val();
			
			if(sval ==null || sval =='')
			{
				$("#span_teacher_tip").html("不能为空");
				return false;
			}
			return true;*/
		},
		errorPlacement: function(error, element) 
		{ 
			
		//	var sname = element.attr("name");
		//	console.log("errorPlacement11111:"+sname +" text:" + error.text());
			
//			if("hideteacherId" == sname)
//				{
//					error.appendTo($("#span_teacher_tip"));
//				}
//			else
			error.appendTo(element.parents('.clearfix').next('.error-tips'));
			//console.log("errorPlacement22:"+element.attr("name"));
		},
		errorCallback:function(element){
			console.log("errorCallback  name=" +$(element).attr("name"));
		},  
		submitHandler:function(form){
		//保存处理
			console.log("submitHandler...");
			
			var tdata = InputJob.Grid.getData();
			if(tdata==null || tdata.length==0)
			{
				layer.alert("学生列表不能为空.");
				return;
			}
			
			var uids = "";
			for(i=0;i<tdata.length;i++)
			{
				if(i==0)
					uids +=tdata[i].userid;
				else
					uids +=","+tdata[i].userid
			}
			var senddata  = $(form).serialize();
			
			senddata = senddata +"&userids=" + uids;
			//printObject(senddata);
			
			var strurl =basePath + "/job/postjob.do";
			 $.ajax({
				  dataType:"json",
				  url:strurl,
				  data:senddata,
				  type:"post",
				  success: function(k_data){
					if(k_data.code ==200)
					{
						layer.alert("入职登记成功,人数:" + tdata.length);
						InputJob.closeDialog(); 
						
					}else
						{
							layer.alert("错误:" + k_data.message);
						}
				  }
			  });
			
		}
	});
};
InputJob.loadData = function() {
	$.ajax({
		url : basePath + '/job/getstudentByid.do',
		data : {
			"userids" : InputJob.Userids
		},
		type : "post",
		dataType : "json",
		success : function(jsondata) {
			if (jsondata.code == 200) {
				$("#span_mancount").text(jsondata.datalist.length);
				
				//test ====
				//for(var i=0;i<100;i++)
				//jsondata.datalist.push(jsondata.datalist[0]);
				
				InputJob.Grid.setData(jsondata.datalist);
				InputJob.Grid.render();
			} else
				layer.alert(jsondata.message);
		}
	});
};


InputJob.Grid = null;

InputJob.layerdialog = null;
InputJob.callback = null;
InputJob.opendialog = function(struserid,blcallback) {
	InputJob.callback=blcallback;
	
	$.ajax({
		url : basePath + '/job/showjobview.do',
		data : {
			userids:struserid
		},
		type : "post",
		dataType : "html",
		success : function(htmldata) {
			var dialogue_height = $(".content").height(), dialogue_width = $(document)
			.width()-50;
			InputJob.layerdialog =layer.open({
					type : 1,
					title : [ '入职登记' ],
					skin : 'demo-class',
					offset : 'rb',
					shade : 0.6,
					area : [ dialogue_width + 'px', dialogue_height + 'px' ],
					anim : 5,
					content : htmldata
				});
			
			//设置表格高度.
			 var h = $(".table-entry").height();
			$("#myinputjobTable").height(h);
			 //初始化表格. 
			 InputJob.initSlickTable();
			 // 载入数据
			 InputJob.loadData();
	

		}
	});
};


/**
 * 关闭窗口
 */
InputJob.closeDialog = function() {
	layer.close(InputJob.layerdialog);
	
	if(InputJob.callback)
		InputJob.callback.apply();
	
};


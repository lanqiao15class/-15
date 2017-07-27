ClassMember = {};
ClassMember.ClassId = null;
ClassMember.init = function(clsid) {
	ClassMember.ClassId = clsid;

	//设置学员转班权限
	if(gl_isHashRight(functionIds,55) || gl_isHashRight(functionIds,56) ){
		$("#changeClass_member").show();
	}else{
		$("#changeClass_member").hide().next().hide();
	}
	
	// 转班级按钮事件
	ClassMember.setupChangeClass();

	/** ============表格开始============* */
	// 定义表格的功能
	var options = {
		editable : false, // 是否可以表格内编辑
		enableCellNavigation : true,
		asyncEditorLoading : false,
		enableColumnReorder : false,
		forceFitColumns : true,// 自动占满一行
		rowHeight : 35, // 行高
		autoEdit : false,
		autoHeight : false
		
	};

	// checkbox 列的定义.
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass : "slick-cell-checkboxsel"
	});

	// 定义表格的字段
	var columns = [];
	columns.push(checkboxSelector.getColumnDefinition()); // checkbox 列

	columns.push({
		id : "indexNo", // 唯一标识
		name : "序号",// 字段名字
		field : "indexNo", // 显示数据中的某一个字段.对应到数据对象中的属性名
		width : 50,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "stuNo",
		name : "学员编号",
		field : "stuNo",
		width :170,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "realName",
		name : "学员姓名",
		field : "realName",
		width : 100,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "courseType",
		name : "课程类别",
		field : "courseType",
		width : 100,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "schoolName",
		name : "所在院校",
		field : "schoolName",
		width : 200,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "statusName",
		name : "状态",
		field : "statusName",
		width : 80,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "availName",
		name : "报名费",
		field : "availName",
		width : 120,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "hasPaidName",
		name : "实训费",
		field : "hasPaidName",
		width : 120,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "updateTime",
		name : "更新时间",
		field : "updateTime",
		width : 120,
		formatter : ClassMember.CommonRender
	});
columns.push({
		id : "cot",
		name : "访谈次数",
		field : "cot",
		width : 80,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "option",
		name : "操作",
		field : "option",
		cssClass : "slick-cell-action",
		width : 100,
		formatter : ClassMember.actionRender
	});
	// 产生grid 控件
	ClassMember.Grid = new Slick.Grid("#myclassTable", [], columns, options);

	ClassMember.Grid.registerPlugin(new Slick.AutoTooltips({
		enableForHeaderCells : true
	}));
	// 设置为行选无效
	ClassMember.Grid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
	// 注册插件. checkbox 显示方式做为一种插件
	ClassMember.Grid.registerPlugin(checkboxSelector);
	// 检测表格选中事件
	ClassMember.Grid.onSelectedRowsChanged.subscribe(function(e, args) {

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

	ClassMember.loadData(ClassMember.ClassId);

}

ClassMember.CommonRender = function(row, cell, value, columnDef, dataContext) {
	var s = value;
	if (dataContext.isgray ==true) 
	{
		s = "<span style='color:#cccccc'>" + value + "</span>"
	}
	return s;
};


ClassMember.lajistuInfoDialogue=function (userId,qx){
	 //1.关闭当前弹框
	// layer.closeAll();
	//alert("stuInfoDialogue");
	 $.ajax({
		  url:basePath+'/student/goYxStuInfo.do',
		  notloading:false,//是否显示加载层.
		  data:{"userId":userId,"qx":qx},
		  type:"post",
		  dataType:"html",
		  success: function(data){
			  ClassMember.dialogOpen(data);
		  }
	 });
};

/** 打开学员详情弹窗 */
ClassMember.dialogOpen=function (data){
	//1.打开窗口
	 var dialogue_height=$(".content").height(),
	  	 dialogue_width=$(document).width()/3;
	layer.open({
		  type: 1, 
		  title: ['学员详情'],
		  skin: 'demo-class',
		  offset: 'rb',
		  shade: 0,
		  anim:5,
		  area: ['740px', dialogue_height+'px'],
		  content: data //这里content是一个普通的String
	});
	$(".cbox-rel").css({"height":$(".cbox-rel").height()-30});
	
};


ClassMember.actionRender = function(row, cell, value, columnDef, dataContext) {

	var s = "";
	if (dataContext.isgray==true) 
	{
		s = "<span style='color:#cccccc'>不在当前班级</span>"
	} else {
			var sclick = "ClassMember.lajistuInfoDialogue("+ dataContext.userid+ ",'zsStu')";
			
				icon_start = '<div class="gird-box">',
				icon_look = '<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="查看" onclick="'+sclick+'"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>',
				icon_editStatus = '<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="修改学员状态" onclick="ClassMember.ChangeStudentStatus('
						+ dataContext.userid
						+ ')"><i class="opti-icon Hui-iconfont">&#xe6e3;</i></a>',
				icon_editStatusnoclick = '<a class="c-gray opt-incon-btn" href="javascript:void(0);" title="修改学员状态"><i class="opti-icon Hui-iconfont">&#xe6e3;</i></a>',
				
				icon_end = '</div>';
				var icon_visitStatusnoclick="";
				if(gl_isHashRight(functionIds,34) || gl_isHashRight(functionIds,37) ){
				icon_visitStatusnoclick = '<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="添加学员追踪记录" onclick="VisitMain.showaddDialog(1,'
						+ dataContext.userid
						+ ')"><i class="opti-icon Hui-iconfont">&#xe600;</i></a>';
				}
				
		// 根据行数据确定是否显示icon2.png
		if(gl_isHashRight(functionIds,56))
		{//有设置学员状态权限
			var flag = ChangeStudentStatus.canEnterIn(dataContext.statusid);
			if (flag) {
				s += icon_start + icon_look + icon_editStatus +icon_visitStatusnoclick+ icon_end;
			} else {
				s += icon_start + icon_look + icon_editStatusnoclick+icon_visitStatusnoclick + icon_end;
			}
		}
		else
			{
				s += icon_start + icon_look + icon_editStatusnoclick+icon_visitStatusnoclick + icon_end;
			
			}
	}
	return s;

};

ClassMember.ChangeStudentStatus = function(userid) {
	ChangeStudentStatus.openDialog(userid, function() {
		ClassMember.loadData(ClassMember.ClassId);
	});
};

/** ============跳转到学员转班页面开始===============* */
ClassMember.setupChangeClass = function() {
	$("#changeClass_member")
			.click(
					function() {
						var selectedRows = ClassMember.Grid.getSelectedRows();
						var tData = ClassMember.Grid.getData();
						var userIds = new Array();

						for (i = 0; i < selectedRows.length; i++) {
							// 是否选择了不在本班级的记录
							if (tData[selectedRows[i]].nowclassid != tData[selectedRows[i]].classid) {
								layer.alert("选择列表中含有异常状态的学员, 不能换班");
								return;
							}
							userIds.push(tData[selectedRows[i]].userid);
						}

						// console.log("userIds:"+userIds.join(","));
						if (selectedRows.length > 0) {
							var flag = true;
							for (var i = 0; i < selectedRows.length; i++) {

								if (!ZSSTUDENTLIST
										.canEnterIn(tData[selectedRows[i]].statusid)) {
									flag = false;
									break;
								}
							}

							if (flag) {
								var stuUserIds = userIds.join(",");
								// console.log("stuUserIds="+stuUserIds);
								//关闭当前窗口.
								ClassMember.closeDialog();
								
								ZSSTUDENTLIST.jumpUrl({"ids":stuUserIds,
													"type":"changeClass",
													"opType":$("#opType").val(),
													"backUrl":"/lqClass/100/goClassManageList.do"
													},
													ZSSTUDENTLIST.basePath+'/student/toChangeClassPage.do');	
								
								
								
							} else {
								layer
										.alert("状态为在读/休学重返/延期结业的学员才可转班，请核对您所选择的学员状态");
							}
						} else {
							layer.msg("请选择转班学员");
						}
						;

					});
};

ClassMember.loadData = function(classid) {
	$.ajax({
		url : basePath + '/class/classmemberjson.do',
		data : {
			"classid" : classid
		},
		type : "post",
		dataType : "json",
		success : function(jsondata) {
			if (jsondata.code == 200) {
				console.log("ClassMember datalist length::"+jsondata.datalist.length);
				ClassMember.Grid.setData(jsondata.datalist);
				ClassMember.Grid.render();
				//调整高度
			var i=$(".slick-viewport").width();
			$(".grid-canvas").width(((i+8)));
			} else
				layer.alert(jsondata.message);
		}
	});
}

ClassMember.Grid = null;

ClassMember.layerdialog = null;

ClassMember.opendialog = function(classid) {

	$.ajax({
		url : basePath + '/class/showclassmember.do',
		data : {
			"classid" : classid
		},
		type : "post",
		dataType : "html",
		success : function(data) {

			ClassMember.layerdialog = ClassMember
					.opendialog_classOfStudent(data);

		}
	});
}

// 班级学员列表
ClassMember.opendialog_classOfStudent = function(htmldata) {
	var dialogue_height = $(".content").height(), dialogue_width = $(document)
			.width()-50;
	var tableh= dialogue_height -170;
	
	var hwin = layer.open({
		type : 1,
		title : [ '班级学员列表' ],
		skin : 'demo-class',
		offset : 'rb',
		shade : 0.6,
		area : [ dialogue_width + 'px', dialogue_height + 'px' ],
		anim : 5,
		content : htmldata
	});
	console.log("table height:"+tableh);
	$("#myclassTable").css("height",tableh);
	return hwin ;
	
}

/**
 * 关闭窗口
 */
ClassMember.closeDialog = function() {
	layer.close(ClassMember.layerdialog);

}

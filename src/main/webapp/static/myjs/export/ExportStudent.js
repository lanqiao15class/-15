ExportStudent = {};
ExportStudent.Grid = null;
ExportStudent.init = function() {

	ExportStudent.setTableHeight();
	
	//ExportStudent.loadData(ExportStudent.ClassId);
	ExportStudent.initSlick();
	
	//访谈查询
	$("#reportselectTime").change(function(){
		$(".ser-out-span").hide();
		$("#exportclass").hide();
		var num=$('#reportselectTime option:selected').val()-1;
		console.log("num="+ num);
		if(num>=0&&num<=5){
			$(".ser-out-span").eq(0).show();
		}else if(num==6){
		$("#exportclass").show();
		}
	});
	
	$(".much-more").click(function(){
		$(this).hide();
		$(".put-away").show();
		$(this).parents(".query-box-parent").find(".query-box").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").hide();
		setTableHeight();
	});
	$(".put-away").click(function(){
		$(this).hide();
		$(".much-more").show();
		$(this).parents(".query-box-parent").find(".query-box").hide();
		$(this).parents(".query-box-parent").find(".query-box:first").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").show();
		setTableHeight();
	});
};
//设置表格高度
ExportStudent.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
};

ExportStudent.initSlick=function()
{
	/** ============表格开始============* */
	// 定义表格的功能
	var options = {
		editable : false, // 是否可以表格内编辑
		enableCellNavigation : true,
		asyncEditorLoading : false,
		enableColumnReorder : false,
		//forceFitColumns : true,// 自动占满一行
		rowHeight : 35, // 行高
		autoEdit : false
		
	};

	// checkbox 列的定义.
//	var checkboxSelector = new Slick.CheckboxSelectColumn({
//		cssClass : "slick-cell-checkboxsel"
//	});

	// 定义表格的字段
	var columns = [];
//	columns.push(checkboxSelector.getColumnDefinition()); // checkbox 列

	columns.push({
		id : "indexNo", // 唯一标识
		name : "序号",// 字段名字
		field : "indexNo", // 显示数据中的某一个字段.对应到数据对象中的属性名
		width : 80
		
	});

	columns.push({
		id : "stuNo",
		name : "学员编号",
		field : "stuNo",
		width : 200
		
	});

	columns.push({
		id : "realName",
		name : "学员姓名",
		field : "realName",
		width : 150
		
	});

	columns.push({
		id : "idCard",
		name : "身份证号",
		field : "idCard",
		width : 150
		
	});

	columns.push({
		id : "schoolName",
		name : "所在院校",
		field : "schoolName",
		width : 150
		
	});

	columns.push({
		id : "quyuManager",
		name : "区域总监",
		field : "quyuManager",
		width : 150
	
	});

	columns.push({
		id : "jiliManager",
		name : "招生经理",
		field : "jiliManager",
		width : 100
		
	});

	columns.push({
		id : "guwenManager",
		name : "课程顾问",
		field : "guwenManager",
		width : 150
	
	});

	columns.push({
		id : "signStatus",
		name : "报名费状态",
		field : "signStatus",
		width : 100
	
	});

	columns.push({
		id : "standardMoney",
		name : "报名费合同金额",
		field : "standardMoney"
		
	});
	columns.push({
		id : "favourMoney",
		name : "报名费减免金额",
		field : "favourMoney"
	
	});
	columns.push({
		id : "currentPayMoney",
		name : "报名费累计收费金额",
		field : "currentPayMoney"
	});
	
	// 产生grid 控件
	ExportStudent.Grid = new Slick.Grid("#myexportTable", [], columns, options);

	ExportStudent.Grid.registerPlugin(new Slick.AutoTooltips({
		enableForHeaderCells : true
	}));
	// 设置为行选无效
	ExportStudent.Grid.setSelectionModel(new Slick.RowSelectionModel({
		selectActiveRow : false
	}));
	// 注册插件. checkbox 显示方式做为一种插件
//	ExportStudent.Grid.registerPlugin(checkboxSelector);
	// 检测表格选中事件
	ExportStudent.Grid.onSelectedRowsChanged.subscribe(function(e, args) {

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
	
};


ExportStudent.actionRender = function(row, cell, value, columnDef, dataContext) {

	var s = "";
	return s;
};


ExportStudent.export_dialog_open=function()
{
	var param =ExportStudent.getFindParam();
	if(param ==null) 
	{
			
			return null;
	}
	
	  layer.open({
		  type: 1, 
		  title: ['选择报表导出月份'],
		  skin: 'demo-class',
		  shade: 0.6,
		  area: '520px',
		  anim:8,
		  content: $("#Exportdialogcontant") //这里content是一个普通的String
		});
}

ExportStudent.find=function()
{
	var param = ExportStudent.getFindParam();
	
	if(param ==null) return ;
	ExportStudent.queryParam= param;
	ExportStudent.loadData();	
};


//填充form 内的数据项. 提交form
ExportStudent.exportfile=function()
{
	var param =ExportStudent.getFindParam();
	if(param ==null) 
		{
			
			return null;
		}
	$("#exportform input[name=select_school_id]").val(param.select_school_id);
	$("#exportform input[name=stime]").val(param.stime);
	$("#exportform input[name=etime]").val(param.etime);
	$("#exportform input[name=stuStatus]").val(param.stuStatus);
	$("#exportform input[name=signStatus]").val(param.signStatus);
	$("#exportform input[name=studyStatus]").val(param.studyStatus);
	$("#exportform input[name=student_name_export]").val(param.student_name_export);
	$("#exportform input[name=optype]").val(param.optype);
	
	
	var  stext = $("#stuStatus option:selected").text();
	$("#exportform input[name=stuStatusName]").val(stext);
	
	stext = $("#signStatus option:selected").text();

	$("#exportform input[name=signStatusName]").val(stext);
	
	stext = $("#studyStatus option:selected").text();
	$("#exportform input[name=studyStatusName]").val(stext);
	
	stext = $("#select_school_id option:selected").text();
	$("#exportform input[name=select_school_idName]").val(stext);

	
	var omonth  = $("#exportmonth");
	if(omonth.val() =='' )
	{
		$("#exportmonth").showTipError("请选择报表统计月份");	
		return ;
	}
	
	$("#exportform").submit();
	
	layer.closeAll();
	
};


ExportStudent.getFindParam=function()
{
	var selNum = $("#reportselectTime").val();
	
	var param={};
	
	param.optype = selNum;
	if(selNum >0&&selNum<=6)
	{
		param.stime = $("#starttime").val();
		param.etime = $("#endtime").val();
		console.log("stime:" + param.stime +" etime:" + param.etime);
		if(param.stime =="")
		{
			$("#starttime").showTipError("不能为空");
			return null;
		}
		if(param.etime =="")
		{
			$("#endtime").showTipError("不能为空");
			return null;
		}
		
		
	}else  if(selNum==7){
	var zzx_exportStudent=   $("#zzx_exportStudent").val();
	if(!isNull(zzx_exportStudent)){
			param.classid= zzx_exportStudent;
	}
	}
	
	
	
param.stuStatus = $("#stuStatus").val();
param.signStatus = $("#signStatus").val();
param.studyStatus = $("#studyStatus").val();
param.select_school_id = $("#select_school_id").val();
param.student_name_export = $("#student_name_export").val();
param.pageno=1;
param.pagesize = PageWiget.getPageSize();
//printObject(param);

//ajax 查询. 
return param;

};

ExportStudent.queryParam = null;

ExportStudent.loadData = function() 
{
	$.ajax({
		url : basePath + '/report/ExportStudentjson.do',
		data : ExportStudent.queryParam,
		type : "post",
		dataType : "json",
		success : function(jsondata) {
			if (jsondata.code == 200) {
				console.log("datalist : " + jsondata.datalist.length);
				ExportStudent.Grid.setData(jsondata.datalist);
				ExportStudent.Grid.render();
				if(jsondata.recordcount)
				{
					//显示分页
					PageWiget.init(jsondata.recordcount,PageWiget.getPageSize());
				}
				
				
			} else
				layer.alert(jsondata.message);
		}
	});
};


ExportStudent.dopagechange= function(pageno,pagesize)
{
	console.log("dotest :" + pageno +" "+ pagesize);
	//alert("选中了;" + pageno +":" + pagesize);	
	ExportStudent.queryParam.pageno = pageno;
	ExportStudent.queryParam.pagesize = pagesize;
	ExportStudent.loadData();
	
};



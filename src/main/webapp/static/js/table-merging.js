// 测试通过ajax 动态获取数据并用grid 显示
function dosetdata(){
	data = [];
	//产生测试数据. 可以通过ajax 获取后台数据
	for (var i = 0; i < 5; i++) {
		var d = (data[i] = {});
		d.indexno=i+"";
		d.realname="雷雷";
		d.coursetype="java";
		d.unicode="青岛大学";
		d.status="在读";
		d.nstatus=1;
		d.teacher="扯淡";
		d.updatetime="2015-01-01";
		//d.ismain=1; // 是否是重点. 
		d.userid=10001; // 学生id
		d.ifmy= 1; // 是否是我的.	
	} 
}
var grid;
//表格数据
var data = [];
//定义表格的功能
var options = {
	editable: false,		//是否可以表格内编辑
	enableCellNavigation: true,  
	asyncEditorLoading: false,
	enableColumnReorder: false,
	forceFitColumns: true,//自动占满一行
	rowHeight:35, // 行高
	autoEdit: false,
	autoHeight: true//高度自动
};
 
$(document).ready(function () {
	
	//产生测试数据. 可以通过ajax 获取后台数据
	for (var i = 0; i < 5; i++) {
		var d = (data[i] = {});
		d.indexno=i+"";
		d.classname="蓝桥1班";
		d.coursetype="iOS";
		d.status="授课中";
		d.num='20';
		d.action='';
		d.nstatus=1;
		//d.ismain=1; // 是否是重点. 
		d.userid=10001; // 学生id
		d.ifmy= 1; // 是否是我的.	
	}
	
	//checkbox 列的定义. 
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass: "slick-cell-checkboxsel"
	});
	
	//定义表格的字段
	var columns = [];
	columns.push(checkboxSelector.getColumnDefinition()); //checkbox 列
	
	columns.push({
		id: "indexno",  // 唯一标识
		name: "序号",// 字段名字
		field: "indexno", // 显示数据中的某一个字段.对应到数据对象中的属性名
		width: 120			//宽度.
	});
	
	columns.push({
		id: "classname", 
		name: "班级名称",
		field: "classname", 
		width: 200,
		formatter:realnameRender
	});
	
	columns.push({
		id: "coursetype", 
		name: "课程类别",
		field: "coursetype", 
		width: 100
	});
	
	columns.push({
		id: "status", 
		name: "状态",
		field: "status", 
		width: 100
	});
	
	columns.push({
		id: "num", 
		name: "当前人数",
		field: "num", 
		width: 100
	});
	
	columns.push({
		id: "action", 
		name: "操作",
		field: "action", 
		cssClass:"slick-cell-action",
		width: 200,
		formatter: actionRender
	});
	
	//产生grid 控件
	if($("#myGrid").length!=0){
		grid = new Slick.Grid("#myGrid", data, columns, options);
		//设置为行选无效
		grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
		//注册插件. checkbox 显示方式做为一种插件
		grid.registerPlugin(checkboxSelector);
	}
	
	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 
	
	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	/* 	
	$("div.slick-cell").live("mouseover",function (e) {
	$(e.target).parent("div.slick-row").css("background","green");
	
	})
	.live("mouseout",function (e) {
	
	$(e.target).parent("div.slick-row").css("background","white");
	}); */
   
});
  
//每行输出的操作按钮. 
function actionRender(row, cell, value, columnDef, dataContext) {
	console.log(1)
	var s= "",
		icon_start='<div class="gird-box">',
		icon_del='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="删除" onclick="delTableRow(this)"><i class="opti-icon Hui-iconfont">&#xe609;</i></a>',
		icon_end='</div>';
		//根据行数据确定是否显示icon2.png
		if(dataContext.nstatus==1)
		s += icon_start+icon_del+icon_end;
		return s;
		console.log(0)
}
	
/**
  自定义显示姓名, 是否是重点, 是否是我的,  显示不同的图标. 
**/	
function realnameRender(row, cell, value, columnDef, dataContext){
	var s= '<div class="gird-box-hasmark">'+value,
		icon_start='<div class="mark-float">',
		icon_ismain='<a class="icon-mark bg-blue" href="javascript:void(0)"><span class="mark-rel"><em class="mark-name">重点</em></span></a>',
		icon_ifmy='<a class="icon-mark bg-orange" href="javascript:void(0)"><span class="mark-rel"><em class="mark-name">我的</em></span></a>',
		icon_end='</div ></div>';
	s += icon_start;
	if(dataContext.ismain==1)
	{
		s += icon_ismain;
	}
	
	if(dataContext.ifmy==1)
	{
		s +=icon_ifmy;
	}
	s +=icon_end;
	return s;
}
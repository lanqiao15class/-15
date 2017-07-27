COURSELIST=function(){};

COURSELIST.basePath=basePath;

//表格对象. 
COURSELIST.Grid=null;

COURSELIST.url=null;

COURSELIST.dataParam={};

//点击查询时保存查询参数for分页
COURSELIST.queryParam={};

//存放表格选中行的stuUserIds
COURSELIST.selectStudentId=new HashMap();

//存放表格选中行的status
COURSELIST.selectStatus=new HashMap();


COURSELIST.init=function(){
	//设置表格部分的高度
	COURSELIST.setTableHeight();
	
	/**============表格开始============**/
	//定义表格的功能
	var options = {
			editable: false,		//是否可以表格内编辑
			enableCellNavigation: true,  
			asyncEditorLoading: false,
			enableColumnReorder: false,
			forceFitColumns: true,//自动占满一行
			rowHeight:35, // 行高
			autoEdit: false
		};
	
	//checkbox 列的定义. 
//	var checkboxSelector = new Slick.CheckboxSelectColumn({
//		cssClass: "slick-cell-checkboxsel"
//	});
	
	//定义表格的字段
	var columns = [];
//	columns.push(checkboxSelector.getColumnDefinition()); //checkbox 列
	
	columns.push({
		id: "courseId",  // 唯一标识
		name: "序号",// 字段名字
		field: "courseId", // 显示数据中的某一个字段.对应到数据对象中的属性名
		width: 120,		//宽度.
	});
	
	columns.push({
		id: "courseName",
		name: "课程名称",
		field: "courseName",
		width: 200
	});
	
	columns.push({
		id: "typeStr",
		name: "状态",
		field: "typeStr",
		width: 120,
		formatter:COURSELIST.realnameRender
	});
	
	columns.push({
		id: "standardMoney",
		name: "合同报名费",
		field: "standardMoney",
		width: 150
	});
	
	columns.push({
		id: "entryfee",
		name: "合同实训费",
		field: "entryfee",
		width: 150
	});

    columns.push({
        id: "lqCoursetypeStr",
        name: "课程类型",
        field: "lqCoursetypeStr",
        width: 150
    });

	columns.push({
		id: "timeStr",
		name: "创建日期",
		field: "timeStr",
		width: 200
	});

	columns.push({
		id: "option", 
		name: "操作",
		field: "option", 
		cssClass:"slick-cell-action",
		width: 120,
		formatter: COURSELIST.actionRender
	});
	//产生grid 控件
	COURSELIST.Grid = new Slick.Grid("#myGrid",[], columns, options);
	
	COURSELIST.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	COURSELIST.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
//	COURSELIST.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
//	COURSELIST.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
//		
//		COURSELIST.updateRowHashMap();
//		
//		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 
	
	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	/**============表格结束============**/
	COURSELIST.getTableData();
	
};//init end
//每行输出的操作按钮. 
//value : 列的内容。 
//dataContext : 行数据对象。 
COURSELIST.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',
	icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="查看" onclick="COURSELIST.lookCourse('+dataContext.courseId+')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>',
	icon_editStatus='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="修改" onclick="COURSELIST.updateCourse('+dataContext.courseId+')"><i class="opti-icon Hui-iconfont">&#xe6e3;</i></a>',
	icon_end='</div>';
	s=icon_start+icon_look+icon_editStatus+icon_end;
	return s;
};


//获取选中的rows
COURSELIST.resolveSelectIndex=function(){
	 //获取选中的行rows数组
	  var datarows = COURSELIST.Grid.getData();
	   var rows=new Array();
	   var selectuserid = COURSELIST.selectStudentId.keys();
	   
	   for(var i=0;i<datarows.length;i++){
		   for(var j=0;j<selectuserid.length;j++){
			    if(selectuserid[j] == datarows[i].stuUserId){
			    	rows.push(i);
			    	break;
			    }
			 }
	   }
	   return rows;
};

COURSELIST.createCourse=function(){
	$.get(COURSELIST.basePath+'/course/showCreateCourse.do', null, function(form) {
		layer.open({
			type : 1,
			title : '创建课程',
			content : form,
			area : [ '800px', '550px' ],
			yes : function(index) {

			},
			full : function(elem) {
				var win = window.top === window.self ? window : parent.window;
				$(win).on(
					'resize',
					function() {
						var $this = $(this);
						elem.width($this.width()).height($this.height()).css({top : 0,left : 0});
						elem.children('div.layui-layer-content').height($this.height() - 95);
					}
				);
			},
		});
	});
}

COURSELIST.lookCourse=function(id){
	$.get(COURSELIST.basePath+'/course/lookCourse.do?t=1&id='+id, null, function(form) {
		layer.open({
			type : 1,
			title : '课程详情',
			content : form,
			area : [ '750px', '450px' ],
			yes : function(index) {

			},
			full : function(elem) {
				var win = window.top === window.self ? window : parent.window;
				$(win).on(
					'resize',
					function() {
						var $this = $(this);
						elem.width($this.width()).height($this.height()).css({top : 0,left : 0});
						elem.children('div.layui-layer-content').height($this.height() - 95);
					}
				);
			},
		});
	});
}

COURSELIST.updateCourse=function(id){
	$.get(COURSELIST.basePath+'/course/lookCourse.do?t=2&id='+id, null, function(form) {
		layer.open({
			type : 1,
			title : '课程修改',
			content : form,
			area : [ '800px', '550px' ],
			yes : function(index) {

			},
			full : function(elem) {
				var win = window.top === window.self ? window : parent.window;
				$(win).on(
					'resize',
					function() {
						var $this = $(this);
						elem.width($this.width()).height($this.height()).css({top : 0,left : 0});
						elem.children('div.layui-layer-content').height($this.height() - 95);
					}
				);
			},
		});
	});
}


//设置表格高度
COURSELIST.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
};


//ajax提交-for获取表格数据(公用)
COURSELIST.ajaxSubmit=function(url){
	$.ajax({
		type: "POST",
		url:url,
		dataType:"json",
		success: function(data) {
			if(data.recordcount<=0){
				if(dataParam.opType=="0"){
					layer.msg('您还没有标记“我关注的”学员，可从“我管理的”或“全部”学员列表中选择标记“我关注的”学员',{time:86400000});//24小时后关闭
				}else if(dataParam.opType=="2"){
					layer.msg('您还没有“我管理的”学员，可从“全部”学员列表中选择标记“我关注的”学员',{time:86400000});//24小时后关闭
				}else{
					layer.closeAll();
				}
			}else{
				layer.closeAll();
			}
				//清除当前选择.
			COURSELIST.isuserselect = false;
			COURSELIST.Grid.setSelectedRows([]);
			  //填充表格数据
			COURSELIST.Grid.setData(data);
			COURSELIST.Grid.render();
			  //设置选中多行
//			 var rows =  COURSELIST.resolveSelectIndex();
//			 COURSELIST.Grid.setSelectedRows(rows);
		}
	});
};

COURSELIST.getTableData=function(){
	COURSELIST.url=COURSELIST.basePath+"/course/showlist.do";
	COURSELIST.ajaxSubmit(COURSELIST.url);
};
/**=============获取表格数据结束====================**/
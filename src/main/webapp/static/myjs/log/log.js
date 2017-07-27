
 LOGINFO={};
 //表格对象
LOGINFO.Grid= null;
LOGINFO.columns = [];
LOGINFO.id="";

//点击查询时保存查询参数for分页
LOGINFO.queryParam={};
//初始化
 LOGINFO.init=function(logdatacolumn){
 	LOGINFO.setTableHeight();
	//初始化表格
	LOGINFO.initgrad(logdatacolumn);
	LOGINFO.dopagechange(1,20);

 }



 /**===============分页处理开始==============**/
LOGINFO.dopagechange=function(pageno,pagesize){
	//console.log(pageno+"   wwwww   "+pagesize);
	LOGINFO.url=	basePath+"/log/showlog.do";
	LOGINFO.queryParam.currpage=pageno;
	LOGINFO.queryParam.pageSize=pagesize;
	LOGINFO.queryParam.starttime=$("#starttime").val();
	LOGINFO.queryParam.endtime=$("#endtime").val();
	LOGINFO.queryParam.userid=$("#zzx_teacherLOG").val();
	LOGINFO.queryParam.optype=$("#typelog").val();
	//alert(JSON.stringify(LOGINFO.queryParam));
	LOGINFO.ajaxSubmit(LOGINFO.queryParam,LOGINFO.url);
}
/**===============分页处理结束==============**/


/**================================表格开始======================================*/
	LOGINFO.initgrad=function(logdatacolumn){

	//定义表格的功能
	var options = {
			editable: false,		//是否可以表格内编辑
			enableCellNavigation: true,  
			asyncEditorLoading: false,
			enableColumnReorder: false,
			forceFitColumns: true,//自动占满一行
			rowHeight:35, // 行高
			autoEdit: false,
			autoHeight: false//高度自动
		};

	//checkbox 列的定义. 
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass: "slick-cell-checkboxsel"
	});

	//定义表格的字段
	var columns = [];
//	columns.push(checkboxSelector.getColumnDefinition()); //checkbox 列

	columns.push({
		id: "indexNo",  // 唯一标识
		name: "序号",// 字段名字
		field: "indexNo", // 显示数据中的某一个字段.对应到数据对象中的属性名
		width: 50	//宽度.

	});
	columns.push({
		id: "real_name", 
		name: "操作者",
		field: "real_name", 
		width: 80
		//formatter:STUDENT_INTO_CLASS.colorRender
	});
	columns.push({
		id: "optypeName", 
		name: "操作方式",
		field: "optypeName", 
		width: 80
	});
	
	columns.push({
		id: "datetime", 
		name: "操作时间",
		field: "datetime", 
		width:100
	});

	columns.push({
		id: "content", 
		name: "操作内容",
		field: "content", 
		//cssClass:"slick-cell-action",
		width: 200
	//	formatter:STUDENT_INTO_CLASS.actionRender
	});
	 //权限判断是否可以编辑。

	if(!isNull(logdatacolumn)){
	columns.push(logdatacolumn);
	}
	LOGINFO.columns = columns;
	//产生grid 控件
	LOGINFO.Grid = new Slick.Grid("#myGrid",[], columns, options);

	LOGINFO.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	LOGINFO.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	LOGINFO.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	LOGINFO.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		 var selectedRows = LOGINFO.Grid.getSelectedRows();
	// console.log("选择了:" + selectedRows.length +"条");
		 
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 

	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	
	//LOGINFO.ajaxSubmit({"ids":LOGINFO.id.substring(1)},basePath+"/LOGINFO/getUserByLOG.do");


	}
/**================================表格结束======================================*/
	
	
/**=====================================获取表格数据===================================================*/
//ajax提交-for获取表格数据(公用)
LOGINFO.ajaxSubmit=function(data,url){
	$.ajax({
		type: "POST",
		url:url,
		data:data,
		dataType:"json",
		success: function(rdata) {
			if(rdata.code=="201"){
			window.location.href=basePath+"/login.do";
			}else if(rdata.code=="202"){
			
			}else{
				//console.log("    123123    "+JSON.stringify(rdata));
			  //填充表格数据
			  LOGINFO.Grid.setData(rdata.getLogPage);
			/*$(".grid-canvas").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
			$(".slick-viewport").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
			$(".slick-viewport").css({"overflow-y": "auto"});*/
			
			LOGINFO.Grid.render();
			if(LOGINFO.queryParam.currpage==1){
			//初始化分页-总记录数/每页条数
			PageWiget.init(rdata.recordcount,PageWiget.getPageSize());
			}
		
			if(rdata.getLogPage==""||rdata.getLogPage==null){
				  //填充表格数据
			  LOGINFO.Grid.setData("");
			LOGINFO.Grid.render();
				  LOGINFO.layerc=layer.msg('没有操作日志',{time:86400000});//24小时后关闭
			}else{
				if(isNull(LOGINFO.layerc)){
				}else{
					layer.close(LOGINFO.layerc);
				}
			
				
			}
			}
			
		}
	});
	
};
/**========================================获取表格数据结束================================================*/

//设置表格高度
LOGINFO.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
	$(".zTreeDemoBackground").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});

}
	

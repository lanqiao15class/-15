ZSDIVIDECLASS=function(){};

ZSDIVIDECLASS.basePath=basePath;

//表格对象. 
ZSDIVIDECLASS.Grid=null;

ZSDIVIDECLASS.url=null;

ZSDIVIDECLASS.dataParam={};

//点击查询时保存查询参数for分页
ZSDIVIDECLASS.queryParam={};

//存放表格选中行的stuUserIds
ZSDIVIDECLASS.selectStudentId=new HashMap();

/**===================初始化开始======================**/
ZSDIVIDECLASS.init=function(){
	//设置表格部分的高度
	ZSDIVIDECLASS.setTableHeight();
	
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
		width: 120,		//宽度.
	});
	
	columns.push({
		id: "stuNo", 
		name: "学员编号",
		field: "stuNo", 
		width: 200
	});
	
	columns.push({
		id: "realName", 
		name: "学员姓名",
		field: "realName", 
		width: 150,
		formatter:ZSDIVIDECLASS.realnameRender
	});
	
	columns.push({
		id: "courseType", 
		name: "课程类别",
		field: "courseType", 
		width: 150
	});
	
	columns.push({
		id: "schoolName", 
		name: "所在院校",
		field: "schoolName", 
		width: 150
	});
	
	columns.push({
		id: "grade", 
		name: "所在年级",
		field: "grade", 
		width: 100
	});
	
	columns.push({
		id: "stuStatus", 
		name: "状态",
		field: "stuStatus", 
		width: 150
	});
	
	columns.push({
		id: "recruitManager", 
		name: "招生经理",
		field: "recruitManager", 
		width: 150
	});
	
	columns.push({
		id: "updateTime", 
		name: "更新时间",
		field: "updateTime", 
		width: 150
	});
	
	columns.push({
		id: "option", 
		name: "操作",
		field: "option", 
		cssClass:"slick-cell-action",
		width: 200,
		formatter: ZSDIVIDECLASS.actionRender
	});
	//产生grid 控件
	ZSDIVIDECLASS.Grid = new Slick.Grid("#myGrid",[], columns, options);
	
	ZSDIVIDECLASS.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	ZSDIVIDECLASS.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	ZSDIVIDECLASS.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	ZSDIVIDECLASS.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		
		ZSDIVIDECLASS.updateRowHashMap();
		
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 
	
	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	/**============表格结束============**/
	
     //初始化tab(控制列表可操作权限)
	ZSDIVIDECLASS.initTab();
	
	/**========tab点击事件开始=======**/
	$("#myFocusStu_lq").click(function(){
		if($("#opType")!="0"){//如果本来就是在我的关注页面，则无需跳转，否则跳转页面
			//1跳转页面
			ZSDIVIDECLASS.jumpUrl({},ZSDIVIDECLASS.basePath+"/student/0/goZsDivideClass.do");
			//2.设置选中的tab样式
			$("#myFocusStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空表格选中行的stuUserIds和courseTypeValue
			ZSDIVIDECLASS.selectStudentId=new HashMap();
			
		}
	});
	$("#allStu_lq").click(function(){
		if($("#opType")!="1"){//如果本来就是在全部学员页面，则无需跳转，否则跳转页面
			//1跳转页面
			ZSDIVIDECLASS.jumpUrl({},ZSDIVIDECLASS.basePath+"/student/1/goZsDivideClass.do");	
			//2.设置选中的tab样式
			$("#allStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空表格选中行的stuUserIds和courseTypeValue
			ZSDIVIDECLASS.selectStudentId=new HashMap();
		}
	});
	/**========tab点击事件结束=======**/
	
	//筛选查询的更多筛选按钮点击事件
	ZSDIVIDECLASS.muchmore();
	
	//收起筛选按钮点击事件
	ZSDIVIDECLASS.putaway();
	
    //标记为我关注的
	ZSDIVIDECLASS.remarkMyFocus();
	//取消关注
	ZSDIVIDECLASS.cancelFocus();
	
	//跳转到新学员分班页面
	ZSDIVIDECLASS.jumpDivideClass();
	
	//学员信息导出
	ZSDIVIDECLASS.exportStu();
};//init end
/**===================初始化结束======================**/

/**=========跳转页面开始========**/
ZSDIVIDECLASS.jumpUrl=function(pdata,url){
    $.ajax({
       	type:"post",
       	data:pdata,
 	   	url:url,
		dataType:"html",
 		success:function(html){
 			$("#contentDiv").html(html);
 			
 		}
		});
};
/**=========跳转页面结束========**/

/**===========更多筛选开始======**/
ZSDIVIDECLASS.muchmore=function(){
	$(".much-more").click(function(){
		$(this).hide();
		$(".put-away").show();
		$(this).parents(".query-box-parent").find(".query-box").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").hide();
		ZSDIVIDECLASS.setTableHeight();
	});	
};


ZSDIVIDECLASS.putaway=function(){
	$(".put-away").click(function(){
		$(this).hide();
		$(".much-more").show();
		$(this).parents(".query-box-parent").find(".query-box").hide();
		$(this).parents(".query-box-parent").find(".query-box:first").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").show();
		ZSDIVIDECLASS.setTableHeight();
		//清空学员姓名筛选
		$("#student_name_lq").val("");
	});	
};

//设置表格高度
ZSDIVIDECLASS.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
};

///更新选中的记录
ZSDIVIDECLASS.updateRowHashMap=function ()
{
		if(ZSDIVIDECLASS.isuserselect==false) 
		{
			ZSDIVIDECLASS.isuserselect=true;
		  return ;
		}
	 //清空本页内有的记录
	 var griddata = ZSDIVIDECLASS.Grid.getData();
	 for(var i=0;i<griddata.length;i++)
	 {
		if(ZSDIVIDECLASS.selectStudentId.get(griddata[i].stuUserId))
		{
		    //console.log("remove key:" +griddata[i].stuUserId);
		 	ZSDIVIDECLASS.selectStudentId.remove(griddata[i].stuUserId);
	 	} 
	 }
	 
	   var selectedRows = ZSDIVIDECLASS.Grid.getSelectedRows();

	   for(var i=0;i<selectedRows.length;i++)
	   {
	     // 得到一行的数据
		var rowdata = ZSDIVIDECLASS.Grid.getDataItem(selectedRows[i]);
		//将该行的stuUserId放入hashMap中
		ZSDIVIDECLASS.selectStudentId.put(rowdata.stuUserId,rowdata.stuUserId);
	   }
	     //更新选中的数量
	  $("#hasChoosed").html("已选:"+ZSDIVIDECLASS.selectStudentId.size());
	 //  console.log("hashmap data=="+ZSDIVIDECLASS.selectStudentId.keys().join(","));	   
};

//每行输出的操作按钮. 
//value : 列的内容。 
//dataContext : 行数据对象。 
ZSDIVIDECLASS.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',
	icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="查看" onclick="stuInfoDialogue('+dataContext.stuUserId+',\'zsStu\')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>',
	icon_end='</div>';
	//根据行数据确定是否显示icon2.png
	if(dataContext.nstatus==1)
		s += icon_start+icon_look+icon_end;
	return s;
};

/**
自定义显示姓名, 是否是重点, 是否是我的,  显示不同的图标. 
**/	
ZSDIVIDECLASS.realnameRender=function (row, cell, value, columnDef, dataContext){
	var disval ="";
	if(value) disval=value;
	var s= '<div class="gird-box-hasmark">'+disval,
	icon_start='<div class="mark-float">',
	icon_ifmy='<a class="icon-mark bg-orange" href="javascript:void(0)"><span class="mark-rel"><em class="mark-name">关注</em></span></a>',
	icon_end='</div ></div>';
	s += icon_start;
	if(dataContext.ifmy==1)
	{
		s +=icon_ifmy;
    }
	s +=icon_end;
	return s;
};

//ajax提交-for获取表格数据(公用)
ZSDIVIDECLASS.ajaxSubmit=function(dataParam,url){
	$.ajax({
		type: "POST",
		url:url,
		data:dataParam,
		dataType:"json",
		success: function(data) {
			if(data.recordcount<=0){
				if(dataParam.opType=="0"){
					layer.msg('您还没有标记"我关注的"学员，可从全部学员列表中选择标记"我关注的"学员',{time:86400000});//24小时后关闭
				}else{
					layer.closeAll();
				}
			}else{
				layer.closeAll();
			}
				//清除当前选择.
			ZSDIVIDECLASS.isuserselect = false;
			ZSDIVIDECLASS.Grid.setSelectedRows([]);
			  //填充表格数据
			ZSDIVIDECLASS.Grid.setData(data.datalist);
			ZSDIVIDECLASS.Grid.render();
			  //设置选中多行
			 var rows =  ZSDIVIDECLASS.resolveSelectIndex();
			 ZSDIVIDECLASS.Grid.setSelectedRows(rows);
				if(dataParam.currpage==1){
					//初始化分页-总记录数/每页条数
					PageWiget.init(data.recordcount,PageWiget.getPageSize());
				} 
			 
		}
	});
};

//获取选中的rows
ZSDIVIDECLASS.resolveSelectIndex=function(){
	 //获取选中的行rows数组
	  var datarows = ZSDIVIDECLASS.Grid.getData();
	   var rows=new Array();
	   var selectuserid = ZSDIVIDECLASS.selectStudentId.keys();
	   
	   for(var i=0;i<datarows.length;i++){
		   for(var j=0;j<selectuserid.length;j++){
			    if(selectuserid[j] == datarows[i].stuUserId)
			    {
			    	rows.push(i);
			    	break;
			    }
			   
			 }
		 
		//console.log("rows="+rows.join(":"));
	   }
	   
	   return rows;
	   
};

/**=============标记为我关注开始=============**/
ZSDIVIDECLASS.remarkMyFocus=function(){
	$("#remarkMyFocus_lq").click(function(){
		var stids = ZSDIVIDECLASS.selectStudentId.keys();//将selectStudentId Map转成数组
		if(stids.length>0){
			stids=stids.join(",");
			var ajUrl=ZSDIVIDECLASS.basePath+"/student/remarkMyFocus.do";
			var dataPa={"stuUserIds":stids};
			ZSDIVIDECLASS.ajaxForRemark(dataPa,ajUrl);
		}else{
			layer.msg('请选择“标记为我关注”的学员');
		}
	});
};
/**=============标记为我关注结束=============**/
/**=============取消关注开始================**/
ZSDIVIDECLASS.cancelFocus=function(){
	$("#cancelFocus_lq").click(function(){
		var stids = ZSDIVIDECLASS.selectStudentId.keys();
		if(stids.length>0){
			stids=stids.join(",");
			var ajUrl=ZSDIVIDECLASS.basePath+"/student/cancelFocus.do";
			var dataPa={"stuUserIds":stids};
			ZSDIVIDECLASS.ajaxForRemark(dataPa,ajUrl);
		}else{
			layer.msg('请选择“取消关注”的学员');
		}
	});//click end
};
/**=============取消关注结束================**/

/**==========ajax提交for标记关注/取消关注开始===========**/
ZSDIVIDECLASS.ajaxForRemark=function(data,url){
	$.ajax({
	type: "POST",
	url:url,
	data:data,
	dataType:"json",
	success: function(data) {
		//删除选择项列表.
		ZSDIVIDECLASS.selectStudentId.removeAll();
		
		layer.msg(data.message);
       if(data.code==200){
    		ZSDIVIDECLASS.url=ZSDIVIDECLASS.basePath+"/student/"+$("#opType").val()+"/goZsDivideClass.do";
    	   //重新刷新页面
    	   ZSDIVIDECLASS.jumpUrl({},ZSDIVIDECLASS.url);   
       }
	}
});
};
/**==========ajax提交for标记关注/取消关注结束===========**/

/**============跳转到新学员分班页面开始===============**/
ZSDIVIDECLASS.jumpDivideClass=function(){
	$("#divideClass_lq").click(function(){
		var userIds=ZSDIVIDECLASS.selectStudentId.keys();
		if(userIds.length>0){
			var stuUserIds=userIds.join(",");
			console.log("stuUserIds="+stuUserIds);
			ZSDIVIDECLASS.jumpUrl({"ids":stuUserIds,"type":"newIntoClass","opType":$("#opType").val(),"backUrl":"/student/"+$("#opType").val()+"/goZsDivideClass.do"},ZSDIVIDECLASS.basePath+'/student/toChangeClassPage.do');
		}else{
			layer.open({
				 title:'提示',
				  content: '请选择分班学员',
				  yes: function(index, layero){
					//do something
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				  }
				});
		}

	});	
};
/**============跳转到新学员分班页面结束===============**/

/**============学员信息导出开始===============**/
ZSDIVIDECLASS.exportStu=function(){
  $("#exportStu_lq").click(function(){
	 $("#exporStuForm").submit();
  });	
};
/**============学员信息导出结束===============**/

/**===============分页处理开始==============**/
ZSDIVIDECLASS.dopagechange=function(pageno,pagesize){
	ZSDIVIDECLASS.url=	ZSDIVIDECLASS.basePath+"/student/getNoClassStuList.do";
	ZSDIVIDECLASS.queryParam.currpage=pageno;
	ZSDIVIDECLASS.queryParam.pageSize=pagesize;
	ZSDIVIDECLASS.queryParam.opType=$("#opType").val();
	ZSDIVIDECLASS.ajaxSubmit(ZSDIVIDECLASS.queryParam,ZSDIVIDECLASS.url);
};
/**===============分页处理结束==============**/

/**==============查询按钮点击操作开始================**/
ZSDIVIDECLASS.queryData=function(opType){
	$(".query_lq").click(function(){
		if($(".much-more").is(":visible")){//收起筛选显示，加上学员姓名条件
			//1查询数据
			ZSDIVIDECLASS.dataParam={
			  "select_school_name_lq":$("#select_school_name_lq").val(),
			  "courseType_lq":$("#courseType_lq").val(),
			  "regional_director_name":$("#regional_director_name").val(),
			  "recruit_manager_name":$("#recruit_manager_name").val(),
			  "course_advisor_name":$("#course_advisor_name").val(),
			  "opType":opType,
			  "currpage":1,
	          "pageSize":PageWiget.getPageSize()
			};
		}else{
			//1查询数据
			ZSDIVIDECLASS.dataParam={
			  "select_school_name_lq":$("#select_school_name_lq").val(),
			  "courseType_lq":$("#courseType_lq").val(),
			  "regional_director_name":$("#regional_director_name").val(),
			  "recruit_manager_name":$("#recruit_manager_name").val(),
			  "course_advisor_name":$("#course_advisor_name").val(),
			  "student_name_lq":$("#student_name_lq").val(),
			  "opType":opType,
			  "currpage":1,
	          "pageSize":PageWiget.getPageSize()
			};
		}

		ZSDIVIDECLASS.url=ZSDIVIDECLASS.basePath+"/student/getNoClassStuList.do";
		ZSDIVIDECLASS.ajaxSubmit(ZSDIVIDECLASS.dataParam,ZSDIVIDECLASS.url);
		//2设置查询参数for分页
		ZSDIVIDECLASS.queryParam={
				  "select_school_name_lq":$("#select_school_name_lq").val(),
				  "courseType_lq":$("#courseType_lq").val(),
				  "regional_director_name":$("#regional_director_name").val(),
				  "recruit_manager_name":$("#recruit_manager_name").val(),
				  "course_advisor_name":$("#course_advisor_name").val(),
				  "student_name_lq":$("#student_name_lq").val(),
				  "opType":opType	
		};	
	});
};
/**==============查询按钮点击操作结束================**/

/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示开始===========**/
ZSDIVIDECLASS.initTab=function(){
	if(gl_isHashRight(functionIds,50) && !gl_isHashRight(functionIds,51)){//具有查看全部待分班学员列表权限但不具有查看我关注的列表权限
		$("#myFocusStu_lq").hide();//隐藏我关注的tab
		$("#allStu_lq").addClass("curr").siblings().removeClass("curr");	
		//获取表格数据
		ZSDIVIDECLASS.dataParam={
	               "opType":1,
	               "currpage":1,
	               "pageSize":PageWiget.getPageSize()
          };
		ZSDIVIDECLASS.url=ZSDIVIDECLASS.basePath+"/student/getNoClassStuList.do";
		ZSDIVIDECLASS.ajaxSubmit(ZSDIVIDECLASS.dataParam,ZSDIVIDECLASS.url);
		//查询按钮点击并设置opType
		ZSDIVIDECLASS.queryData(1);
	}else if(!gl_isHashRight(functionIds,50) && gl_isHashRight(functionIds,51)){//具有查看我关注的待分班学员列表权限但不具有查看全部的列表权限
		$("#allStu_lq").hide();//隐藏全部tab
		$("#myFocusStu_lq").addClass("curr").siblings().removeClass("curr");
		//获取表格数据
		ZSDIVIDECLASS.dataParam={
	               "opType":0,
	               "currpage":1,
	               "pageSize":PageWiget.getPageSize()
          };
		ZSDIVIDECLASS.url=ZSDIVIDECLASS.basePath+"/student/getNoClassStuList.do";
		ZSDIVIDECLASS.ajaxSubmit(ZSDIVIDECLASS.dataParam,ZSDIVIDECLASS.url);
		//查询按钮点击并设置opType
		ZSDIVIDECLASS.queryData(0);
	}else if(gl_isHashRight(functionIds,50) && gl_isHashRight(functionIds,51)){//具有查看全部待分班学员列表权限和我关注的列表权限
		if($("#opType").val()=="0"){
			$("#myFocusStu_lq").addClass("curr").siblings().removeClass("curr");
			$("#remarkMyFocus_lq").hide();//隐藏标记为我关注的按钮
			$(".i_lq").hide();//隐藏|
		}else{
			$("#allStu_lq").addClass("curr").siblings().removeClass("curr");	
		}
		//获取表格数据
		ZSDIVIDECLASS.dataParam={
	               "opType":$("#opType").val(),
	               "currpage":1,
	               "pageSize":PageWiget.getPageSize()
          };
		ZSDIVIDECLASS.url=ZSDIVIDECLASS.basePath+"/student/getNoClassStuList.do";
		ZSDIVIDECLASS.ajaxSubmit(ZSDIVIDECLASS.dataParam,ZSDIVIDECLASS.url);
		//查询按钮点击并设置opType
		ZSDIVIDECLASS.queryData($("#opType").val());
	}else{//查看两个列表的权限都没有
		$("#myFocusStu_lq").hide();//隐藏我关注的tab
		$("#allStu_lq").hide();//隐藏全部tab
	}
};
/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示结束===========**/	
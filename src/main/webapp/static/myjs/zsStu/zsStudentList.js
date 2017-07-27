ZSSTUDENTLIST=function(){};

ZSSTUDENTLIST.basePath=basePath;

//表格对象. 
ZSSTUDENTLIST.Grid=null;

ZSSTUDENTLIST.url=null;

ZSSTUDENTLIST.dataParam={};

//点击查询时保存查询参数for分页
ZSSTUDENTLIST.queryParam={};

//存放表格选中行的stuUserIds
ZSSTUDENTLIST.selectStudentId=new HashMap();

//存放表格选中行的status
ZSSTUDENTLIST.selectStatus=new HashMap();


ZSSTUDENTLIST.init=function(){
	//设置表格部分的高度
	ZSSTUDENTLIST.setTableHeight();
	
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
		width: 200,
		formatter:ZSSTUDENTLIST.realnameRender
	});
	
	columns.push({
		id: "className", 
		name: "班级名称",
		field: "className", 
		width: 200
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
		id: "schoolType", 
		name: "院校类别",
		field: "schoolType", 
		width: 100
	});
	
	columns.push({
		id: "stuStatus", 
		name: "状态",
		field: "stuStatus", 
		width: 150
	});
	
	columns.push({
		id: "isAvaiable", 
		name: "报名费",
		field: "isAvaiable", 
		width: 150
	});
	
	columns.push({
		id: "hasPaid", 
		name: "学费",
		field: "hasPaid", 
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
		formatter: ZSSTUDENTLIST.actionRender
	});
	//产生grid 控件
	ZSSTUDENTLIST.Grid = new Slick.Grid("#myGrid",[], columns, options);
	
	ZSSTUDENTLIST.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	ZSSTUDENTLIST.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	ZSSTUDENTLIST.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	ZSSTUDENTLIST.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		
		ZSSTUDENTLIST.updateRowHashMap();
		
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 
	
	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	/**============表格结束============**/

	/**+++++++++++权限控制开始+++++++++++++++++++**/
	//设置学员导出权限
	if(gl_isHashRight(functionIds,57)){
		$("#exportStu_lq").show();
	}else{
		$("#exportStu_lq").hide().prev().hide();
	}
	//设置学员转班权限
	if(gl_isHashRight(functionIds,55)){
		$("#changeClass_lq").show();
	}else{
		$("#changeClass_lq").hide().next().hide();
	}
	//设置列表权限
	/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示开始===========**/
			var listFlag1=gl_isHashRight(functionIds,52);//是否可查看我管理的列表
			var listFlag2=gl_isHashRight(functionIds,53);//是否可查看我关注的列表
			var listFlag3=gl_isHashRight(functionIds,54);//是否可查看全部的列表
			
			if(listFlag1 && !listFlag2 && !listFlag3){//只可查看我管理的列表
				//1
				$("#myFocusStu_lq").hide();//隐藏我关注的tab
				$("#allStu_lq").hide();//隐藏全部学员的tab
			}else if(!listFlag1 && listFlag2 && !listFlag3){//只可查看我关注的列表
				$("#myManage_lq").hide();//隐藏我管理的tab
				$("#allStu_lq").hide();//隐藏全部学员的tab
			}else if(!listFlag1 && !listFlag2 && listFlag3){//只可查看全部的列表
				$("#myFocusStu_lq").hide();//隐藏我关注的tab
				$("#myManage_lq").hide();//隐藏我管理的学员的tab
			}else if(listFlag1 && listFlag2 && !listFlag3){//只可查看我管理的和我关注的(默认加载我管理的列表数据)
				    $("#allStu_lq").hide();//隐藏全部学员的tab
			}else if(listFlag1 && !listFlag2 && listFlag3){//只可查看我管理的和全部的(默认加载我管理的列表数据)
				$("#myFocusStu_lq").hide();//隐藏我关注的学员的tab
			}else if(!listFlag1 && listFlag2 && listFlag3){//只可查看我关注的和全部的(默认加载我关注的列表数据)
				$("#myManage_lq").hide();//隐藏我管理的学员的tab
			}else if(!listFlag1 && !listFlag2 && !listFlag3){
				$("#myFocusStu_lq").hide();//隐藏我关注的tab
				$("#myManage_lq").hide();//隐藏我管理的学员的tab
				$("#allStu_lq").hide();//隐藏全部学员的tab
			}
		/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示结束===========**/
		      if($("#opType").val()!=""){//opType不为空
					//获取表格数据
					ZSSTUDENTLIST.getTableData();
					//查询数据
					ZSSTUDENTLIST.queryData();	
		      }
		/**+++++++++++权限控制结束+++++++++++++++++++**/
	/**========tab点击事件开始=======**/
	$("#myFocusStu_lq").click(function(){
			//1跳转页面
			ZSSTUDENTLIST.jumpUrl({},ZSSTUDENTLIST.basePath+"/student/0/goZsStu.do");
			//2.设置选中的tab样式
			$("#myFocusStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空表格选中行的stuUserIds和status
			ZSSTUDENTLIST.selectStudentId=new HashMap();
			ZSSTUDENTLIST.selectStatus=new HashMap();
	});
	$("#allStu_lq").click(function(){
			//1跳转页面
			ZSSTUDENTLIST.jumpUrl({},ZSSTUDENTLIST.basePath+"/student/1/goZsStu.do");	
			//2.设置选中的tab样式
			$("#allStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空表格选中行的stuUserIds和status
			ZSSTUDENTLIST.selectStudentId=new HashMap();
			ZSSTUDENTLIST.selectStatus=new HashMap();
	});
	$("#myManage_lq").click(function(){
		//1跳转页面
		ZSSTUDENTLIST.jumpUrl({},ZSSTUDENTLIST.basePath+"/student/2/goZsStu.do");	
		//2.设置选中的tab样式
		$("#myManage_lq").addClass("curr").siblings().removeClass("curr");
		//清空表格选中行的stuUserIds和status
		ZSSTUDENTLIST.selectStudentId=new HashMap();
		ZSSTUDENTLIST.selectStatus=new HashMap();
	});
	/**========tab点击事件结束=======**/
	
	//筛选查询的更多筛选按钮点击事件
	ZSSTUDENTLIST.muchmore();
	
	//收起筛选按钮点击事件
	ZSSTUDENTLIST.putaway();
	
	//更多筛选-选择筛选项显示相应下拉列表
	ZSSTUDENTLIST.zsSelectSearch();

    //标记为我关注的
	ZSSTUDENTLIST.remarkMyFocus();
	//取消关注
	ZSSTUDENTLIST.cancelFocus();
	
	//跳转到学员转班页面
	ZSSTUDENTLIST.jumpChangeClass();
	
	//导出学员信息
	ZSSTUDENTLIST.exportStu();
	
};//init end

/**
自定义显示姓名, 是否是重点, 是否是我的,  显示不同的图标. 
**/	
ZSSTUDENTLIST.realnameRender=function (row, cell, value, columnDef, dataContext){
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

//每行输出的操作按钮. 
//value : 列的内容。 
//dataContext : 行数据对象。 
ZSSTUDENTLIST.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',
	icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="查看" onclick="stuInfoDialogue('+dataContext.stuUserId+',\'zsStu\')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>',
	icon_editStatus='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="修改学员状态" onclick="ZSSTUDENTLIST.ChangeStudentStatus('+dataContext.stuUserId+')"><i class="opti-icon Hui-iconfont">&#xe6e3;</i></a>',
	icon_editStatusnoclick='<a class="c-gray opt-incon-btn" href="javascript:void(0);" title="修改学员状态"><i class="opti-icon Hui-iconfont">&#xe6e3;</i></a>',
	icon_end='</div>';
	//根据行数据确定是否显示icon2.png
	if(dataContext.nstatus==1){
		if(gl_isHashRight(functionIds,56)){//有设置学员状态权限
			var flag=ChangeStudentStatus.canEnterIn(dataContext.status);
			if(flag){
				s += icon_start+icon_look+icon_editStatus+icon_end;
			}else{
				s += icon_start+icon_look+icon_editStatusnoclick+icon_end;
			}
		}else{
			s += icon_start+icon_look+icon_editStatusnoclick+icon_end;
		}
	}
	return s;
};

//获取选中的rows
ZSSTUDENTLIST.resolveSelectIndex=function(){
	 //获取选中的行rows数组
	  var datarows = ZSSTUDENTLIST.Grid.getData();
	   var rows=new Array();
	   var selectuserid = ZSSTUDENTLIST.selectStudentId.keys();
	   
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

/**===========更多筛选开始======**/
ZSSTUDENTLIST.muchmore=function(){
	$(".much-more").click(function(){
		$(this).hide();
		$(".put-away").show();
		$(this).parents(".query-box-parent").find(".query-box").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").hide();
		ZSSTUDENTLIST.setTableHeight();
	});	
};


ZSSTUDENTLIST.putaway=function(){
	$(".put-away").click(function(){
		$(this).hide();
		$(".much-more").show();
		$(this).parents(".query-box-parent").find(".query-box").hide();
		$(this).parents(".query-box-parent").find(".query-box:first").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").show();
		ZSSTUDENTLIST.setTableHeight();
		//重置更多筛选出来的筛选项
		$("#zsSelectSearch").val("0");
		$(".ser-out-span").hide();
	});	
};


//设置表格高度
ZSSTUDENTLIST.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
};

//更多筛选里，选择筛选项，显示对应的框
ZSSTUDENTLIST.zsSelectSearch=function(){
	$("#zsSelectSearch").change(function(){
		$(".ser-out-span").hide().removeClass("forClearOthers");
		var num=$('#zsSelectSearch option:selected').val()-1;
		if(num>=0){
			$(".ser-out-span").eq(num).show();
			$(".ser-out-span").eq(num).addClass("forClearOthers");
		}
		//清空除了选中的其他筛选项
		$(".ser-out-span").each(function(){
			if(!$(this).hasClass("forClearOthers")){
				$(".zs_search .searchable-select-close").click();	
				$("#beginTime_lq,#endTime_lq").val("");
			}
		});
	});	
};

///更新选中的记录
ZSSTUDENTLIST.updateRowHashMap=function ()
{
		if(ZSSTUDENTLIST.isuserselect==false) 
		{
			ZSSTUDENTLIST.isuserselect=true;
		  return ;
		}
	 //清空本页内有的记录
	 var griddata = ZSSTUDENTLIST.Grid.getData();
	 for(var i=0;i<griddata.length;i++)
	 {
		if(ZSSTUDENTLIST.selectStudentId.get(griddata[i].stuUserId))
		{
		    //console.log("remove key:" +griddata[i].stuUserId);
		 	ZSSTUDENTLIST.selectStudentId.remove(griddata[i].stuUserId);
	 	} 
		
		if(ZSSTUDENTLIST.selectStatus.get(griddata[i].status)){
			ZSSTUDENTLIST.selectStatus.remove(griddata[i].status);	
		}
	 }
	 
	   var selectedRows = ZSSTUDENTLIST.Grid.getSelectedRows();

	   for(var i=0;i<selectedRows.length;i++)
	   {
	     // 得到一行的数据
		var rowdata = ZSSTUDENTLIST.Grid.getDataItem(selectedRows[i]);
		//将该行的stuUserId放入hashMap中
		ZSSTUDENTLIST.selectStudentId.put(rowdata.stuUserId,rowdata.stuUserId);
		//将该行的status放入hashMap中
		ZSSTUDENTLIST.selectStatus.put(rowdata.status,rowdata.status);
	   }
	     //更新选中的数量
	  $("#hasChoosed").html("已选:"+ZSSTUDENTLIST.selectStudentId.size());
	 //  console.log("hashmap data=="+ZSSTUDENTLIST.selectStudentId.keys().join(","));	   
};

/**=========跳转页面开始========**/
ZSSTUDENTLIST.jumpUrl=function(pdata,url){
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

//ajax提交-for获取表格数据(公用)
ZSSTUDENTLIST.ajaxSubmit=function(dataParam,url){
	$.ajax({
		type: "POST",
		url:url,
		data:dataParam,
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
			ZSSTUDENTLIST.isuserselect = false;
			ZSSTUDENTLIST.Grid.setSelectedRows([]);
			  //填充表格数据
			ZSSTUDENTLIST.Grid.setData(data.datalist);
			ZSSTUDENTLIST.Grid.render();
			  //设置选中多行
			 var rows =  ZSSTUDENTLIST.resolveSelectIndex();
			 ZSSTUDENTLIST.Grid.setSelectedRows(rows);
				if(dataParam.currpage==1){
					//初始化分页-总记录数/每页条数
					PageWiget.init(data.recordcount,PageWiget.getPageSize());
					$("#sp_otherpagemsg").text("当前年份学员总数"+data.recordcount+"人,不包含开除"+data.expelCount+"人，劝退"+data.quanTuiCount+"人，退学"+data.leaveSchollCount+"人");
					 //console.log("劝退：quanTuiCount="+data.quanTuiCount);
					 //console.log("开除：expelCount="+data.expelCount);
					 //console.log("退学：leaveSchollCount="+data.leaveSchollCount);
					// console.log("总数:recordcount="+data.recordcount);
				} 
		}
	});
};

/**===============分页处理开始==============**/
ZSSTUDENTLIST.dopagechange=function(pageno,pagesize){
	ZSSTUDENTLIST.url=	ZSSTUDENTLIST.basePath+"/student/getZsStudentList.do";
	ZSSTUDENTLIST.queryParam.currpage=pageno;
	ZSSTUDENTLIST.queryParam.pageSize=pagesize;
	ZSSTUDENTLIST.queryParam.opType=$("#opType").val();
	ZSSTUDENTLIST.ajaxSubmit(ZSSTUDENTLIST.queryParam,ZSSTUDENTLIST.url);
};
/**===============分页处理结束==============**/

/**=============标记为我关注开始=============**/
ZSSTUDENTLIST.remarkMyFocus=function(){
	$("#remarkMyFocus_lq").click(function(){
		var stids = ZSSTUDENTLIST.selectStudentId.keys();//将selectStudentId Map转成数组
		if(stids.length>0){
			stids=stids.join(",");
			var ajUrl=ZSSTUDENTLIST.basePath+"/student/remarkMyFocus.do";
			var dataPa={"stuUserIds":stids};
			ZSSTUDENTLIST.ajaxForRemark(dataPa,ajUrl);
		}else{
			layer.msg('请选择“标记为我关注”的学员');
		}
	});
};
/**=============标记为我关注结束=============**/
/**=============取消关注开始================**/
ZSSTUDENTLIST.cancelFocus=function(){
	$("#cancelFocus_lq").click(function(){
		var stids = ZSSTUDENTLIST.selectStudentId.keys();
		if(stids.length>0){
			stids=stids.join(",");
			var ajUrl=ZSSTUDENTLIST.basePath+"/student/cancelFocus.do";
			var dataPa={"stuUserIds":stids};
			ZSSTUDENTLIST.ajaxForRemark(dataPa,ajUrl);
		}else{
			layer.msg('请选择“取消关注”的学员');
		}
	});//click end
};
/**=============取消关注结束================**/

/**==========ajax提交for标记关注/取消关注开始===========**/
ZSSTUDENTLIST.ajaxForRemark=function(data,url){
	$.ajax({
	type: "POST",
	url:url,
	data:data,
	dataType:"json",
	success: function(data) {
		//删除选择项列表.
		ZSSTUDENTLIST.selectStudentId.removeAll();
		
		layer.msg(data.message);
       if(data.code==200){
    		ZSSTUDENTLIST.url=ZSSTUDENTLIST.basePath+"/student/"+$("#opType").val()+"/goZsStu.do";
    	   //重新刷新页面
    	   ZSSTUDENTLIST.jumpUrl({},ZSSTUDENTLIST.url);   
       }
	}
});
};
/**==========ajax提交for标记关注/取消关注结束===========**/

/**============跳转到学员转班页面开始===============**/
ZSSTUDENTLIST.jumpChangeClass=function(){
	$("#changeClass_lq").click(function(){
		var userIds=ZSSTUDENTLIST.selectStudentId.keys();
		var statuss=ZSSTUDENTLIST.selectStatus.keys();
		
		console.log("转班学生 userIds :"+userIds.join(","));
		
		if(userIds.length>0){
			var flag=true;
			for(var i=0;i<statuss.length;i++){
				//console.log("canEnterIn="+ZSSTUDENTLIST.canEnterIn(statuss[i]));
				//console.log("status="+statuss[i]);
				if(!ZSSTUDENTLIST.canEnterIn(statuss[i])){
					flag=false;
					break;
				}
			}
			if(flag){
				var stuUserIds=userIds.join(",");
				//console.log("stuUserIds="+stuUserIds);
				ZSSTUDENTLIST.jumpUrl({"ids":stuUserIds,"type":"changeClass","opType":$("#opType").val(),"backUrl":"/student/"+$("#opType").val()+"/goZsStu.do"},ZSSTUDENTLIST.basePath+'/student/toChangeClassPage.do');	
			}else{
				layer.open({
					 title:'提示',
					  content: '状态为在读/休学重返/延期结业的学员才可转班，请核对您所选择的学员状态',
					  yes: function(index, layero){
						//do something
						layer.close(index); //如果设定了yes回调，需进行手工关闭
					  }
					});
			}
		}else{
			layer.open({
				 title:'提示',
				  content: '请选择转班学员',
				  yes: function(index, layero){
					//do something
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				  }
				});
		};

	});	
};
/**============跳转到学员转班页面结束===============**/

/**============学员信息导出开始===============**/
ZSSTUDENTLIST.exportStu=function(){
  $("#exportStu_lq").click(function(){
	$("#exporStuForm").submit();
  });	
};
/**============学员信息导出结束===============**/
/**===========弹出设置学员状态弹窗开始============**/
ZSSTUDENTLIST.ChangeStudentStatus=function(data){
	ChangeStudentStatus.openDialog(data,function(){
		ZSSTUDENTLIST.jumpUrl({},ZSSTUDENTLIST.basePath+"/student/"+$("#opType").val()+"/goZsStu.do");
	});	
};
/**===========弹出设置学员状态弹窗结束============**/
/**==========判断是否可转班开始==================**/
ZSSTUDENTLIST.canEnterIn=function(current_status){
	var bret =false;
	if(current_status == gl_enumStatus.BESTUDY 
			||current_status == gl_enumStatus.XIUXUEBack 
			||current_status == gl_enumStatus.DELAYGRADUATE 
			)
		bret =true;
	return bret;
};
/**==========判断是否可转班结束==================**/

/**==============查询按钮点击操作开始================**/
ZSSTUDENTLIST.queryData=function(){
	$(".query_lq").click(function(){
		if($("#zsSelectSearch").val()=="0"){
			//1查询数据
			ZSSTUDENTLIST.dataParam={
					  "select_school_name_lq":$("#select_school_name_lq").val(),
					  "select_class_name_lq":$("#select_class_name_lq").val(),
					  "stuStatus_lq":$("#stuStatus_lq").val(),
					  "isAvaiable_lq":$("#isAvaiable_lq").val(),
					  "hasPaid_lq":$("#hasPaid_lq").val(),
					  "opType":$("#opType").val(),
					  "currpage":1,
			          "pageSize":PageWiget.getPageSize()
					};	
		}else{
			//1查询数据
			ZSSTUDENTLIST.dataParam={
					  "select_school_name_lq":$("#select_school_name_lq").val(),
					  "select_class_name_lq":$("#select_class_name_lq").val(),
					  "stuStatus_lq":$("#stuStatus_lq").val(),
					  "isAvaiable_lq":$("#isAvaiable_lq").val(),
					  "hasPaid_lq":$("#hasPaid_lq").val(),
					  "regional_director_name":$("#regional_director_name").val(),
					  "recruit_manager_name":$("#recruit_manager_name").val(),
					  "course_advisor_name":$("#course_advisor_name").val(),
					  "beginTime_lq":$("#beginTime_lq").val(),
					  "endTime_lq":$("#endTime_lq").val(),
					  "student_name_lq":$("#student_name_lq").val(),
					  "opType":$("#opType").val(),
					  "currpage":1,
			          "pageSize":PageWiget.getPageSize()
					};	
		}
		//更多筛选按钮显示的时候置空隐藏的查询条件
		if($(".much-more").is(":visible")){
			$("#regionalDirectorHidden").val("");
			$("#recruitManagerHidden").val("");
			$("#courseAdvisorHidden").val("");
			$("#studentNameHidden").val("");
			$("#beginTimeHidden").val("");
			$("#endTimeHidden").val("");
		}

		ZSSTUDENTLIST.url=ZSSTUDENTLIST.basePath+"/student/getZsStudentList.do";
		var flag=true;
		if($(".put-away").is(":visible")){
			if($("#zsSelectSearch").val()=="5"){
				 var beginTime_lq=$("#beginTime_lq").val();
				 var endTime_lq=$("#endTime_lq").val();
				 if(beginTime_lq==null || beginTime_lq==""){
					 $("#beginTime_lq").showTipError("请输入开始时间...");
					 flag=false;
				 }
				 if(endTime_lq==null || endTime_lq==""){
					 $("#endTime_lq").showTipError("请输入结束时间..."); 
					 flag=false;
				 }
			}
		}
		 if(flag){
				ZSSTUDENTLIST.ajaxSubmit(ZSSTUDENTLIST.dataParam,ZSSTUDENTLIST.url);
				//2设置查询参数for分页
				if($("#zsSelectSearch").val()=="0"){
					ZSSTUDENTLIST.queryParam={
							  "select_school_name_lq":$("#select_school_name_lq").val(),
							  "select_class_name_lq":$("#select_class_name_lq").val(),
							  "stuStatus_lq":$("#stuStatus_lq").val(),
							  "isAvaiable_lq":$("#isAvaiable_lq").val(),
							  "hasPaid_lq":$("#hasPaid_lq").val(),
							  "opType":$("#opType").val()	
					}; 
				}else{
					ZSSTUDENTLIST.queryParam={
							  "select_school_name_lq":$("#select_school_name_lq").val(),
							  "select_class_name_lq":$("#select_class_name_lq").val(),
							  "stuStatus_lq":$("#stuStatus_lq").val(),
							  "isAvaiable_lq":$("#isAvaiable_lq").val(),
							  "hasPaid_lq":$("#hasPaid_lq").val(),
							  "regional_director_name":$("#regional_director_name").val(),
							  "recruit_manager_name":$("#recruit_manager_name").val(),
							  "course_advisor_name":$("#course_advisor_name").val(),
							  "beginTime_lq":$("#beginTime_lq").val(),
							  "endTime_lq":$("#endTime_lq").val(),
							  "student_name_lq":$("#student_name_lq").val(),
							  "opType":$("#opType").val()	
					}; 
				}
		 }	
	});	
};
/**==============查询按钮点击操作结束================**/
/**=============获取表格数据开始====================**/
ZSSTUDENTLIST.getTableData=function(){
	ZSSTUDENTLIST.dataParam={
            "opType":$("#opType").val(),
            "currpage":1,
            "pageSize":PageWiget.getPageSize()
   };
	ZSSTUDENTLIST.url=ZSSTUDENTLIST.basePath+"/student/getZsStudentList.do";
	ZSSTUDENTLIST.ajaxSubmit(ZSSTUDENTLIST.dataParam,ZSSTUDENTLIST.url);
};
/**=============获取表格数据结束====================**/
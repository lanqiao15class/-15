STUEMPLOYLIST=function(){};

STUEMPLOYLIST.basePath=basePath;

//表格对象. 
STUEMPLOYLIST.Grid=null;

STUEMPLOYLIST.url=null;

STUEMPLOYLIST.dataParam={};

//点击查询时保存查询参数for分页
STUEMPLOYLIST.queryParam={};

//存放表格选中行的stuId
STUEMPLOYLIST.selectStudentId=new HashMap();

//存放表格选中行的status for判断是否可合并班级操作
STUEMPLOYLIST.selectStatus=new HashMap();

STUEMPLOYLIST.init=function(){
	//设置表格部分的高度
	STUEMPLOYLIST.setTableHeight();
	
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
		width: 100,		//宽度.
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
		formatter:STUEMPLOYLIST.realnameRender
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
		id: "stuStatus", 
		name: "状态",
		field: "stuStatus", 
		width: 150
	});
	
	columns.push({
		id: "jobStatus", 
		name: "职业状态",
		field: "jobStatus", 
		width: 100
	});
	
	columns.push({
		id: "jobWay", 
		name: "就业方式",
		field: "jobWay", 
		width: 150
	});
	
	columns.push({
		id: "jobserviceStarttime", 
		name: "就业开始时间",
		field: "jobserviceStarttime", 
		width: 200
	});
	
	columns.push({
		id: "empTime", 
		name: "首次入职时间",
		field: "empTime", 
		width: 200
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
		formatter: STUEMPLOYLIST.actionRender
	});
	//产生grid 控件
	STUEMPLOYLIST.Grid = new Slick.Grid("#myGrid",[], columns, options);
	
	STUEMPLOYLIST.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	STUEMPLOYLIST.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	STUEMPLOYLIST.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	STUEMPLOYLIST.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		
		STUEMPLOYLIST.updateRowHashMap();
		
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
	//设置入职登记权限
	if(gl_isHashRight(functionIds,72)){
		$("#inputJob_lq").show();
	}else{
		$("#inputJob_lq").hide().next().hide();
	}
	//设置列表权限
	/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示开始===========**/
			var listFlag1=gl_isHashRight(functionIds,69);//是否可查看我管理的列表
			var listFlag2=gl_isHashRight(functionIds,71);//是否可查看我关注的列表
			var listFlag3=gl_isHashRight(functionIds,70);//是否可查看全部的列表
			
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
		    	  STUEMPLOYLIST.getTableData();
					//查询数据
		    	  STUEMPLOYLIST.queryData();	
		      }
		/**+++++++++++权限控制结束+++++++++++++++++++**/
	
	/**========tab点击事件开始=======**/
	$("#myFocusStu_lq").click(function(){
			//1跳转页面
			STUEMPLOYLIST.jumpUrl({},STUEMPLOYLIST.basePath+"/job/0/goStuEmployList.do");
			//2.设置选中的tab样式
			$("#myFocusStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空表格选中行的stuId和status
			STUEMPLOYLIST.selectStudentId=new HashMap();
			STUEMPLOYLIST.selectStatus=new HashMap();
	});
	$("#allStu_lq").click(function(){
			//1跳转页面
			STUEMPLOYLIST.jumpUrl({},STUEMPLOYLIST.basePath+"/job/1/goStuEmployList.do");	
			//2.设置选中的tab样式
			$("#allStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空表格选中行的stuId和status
			STUEMPLOYLIST.selectStudentId=new HashMap();
			STUEMPLOYLIST.selectStatus=new HashMap();
	});
	$("#myManage_lq").click(function(){
		//1跳转页面
		STUEMPLOYLIST.jumpUrl({},STUEMPLOYLIST.basePath+"/job/2/goStuEmployList.do");	
		//2.设置选中的tab样式
		$("#myManage_lq").addClass("curr").siblings().removeClass("curr");
		//清空表格选中行的stuId和status
		STUEMPLOYLIST.selectStudentId=new HashMap();
		STUEMPLOYLIST.selectStatus=new HashMap();
	});
	/**========tab点击事件结束=======**/	
	
	//更多筛选
	STUEMPLOYLIST.muchmore();
	
	//收起筛选
	STUEMPLOYLIST.putaway();
	
	//更多筛选-选择筛选项显示相应下拉列表
	STUEMPLOYLIST.zsSelectSearch();
	
	//标记为我关注
	STUEMPLOYLIST.remarkMyFocus();
	
	//取消关注
	STUEMPLOYLIST.cancelFocus();
	
	//入职登记
	STUEMPLOYLIST.inputJob();
};//init end

/**
自定义显示姓名, 是否是重点, 是否是我的,  显示不同的图标. 
**/	
STUEMPLOYLIST.realnameRender=function (row, cell, value, columnDef, dataContext){
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
STUEMPLOYLIST.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',
	icon_leaveEntry='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="离职登记" onclick="STUEMPLOYLIST.leaveRegistrationDio('+dataContext.stuUserId+');"><i class="opti-icon Hui-iconfont">&#xe692;</i></a>',
	icon_leaveEntry_noclick='<a class="c-gray opt-incon-btn" href="javascript:void(0);" title="离职登记"><i class="opti-icon Hui-iconfont">&#xe692;</i></a>',
	icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="学员就业详情" onclick="stuInfoDialogue('+dataContext.stuUserId+',\'jyStu\')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>',
	icon_end='</div>';
	//根据行数据确定是否显示icon2.png
	if(dataContext.nstatus==1){
	   if(gl_isHashRight(functionIds,73)){//设置离职登记权限
			var flag=STUEMPLOYLIST.canEnterIn(dataContext.jobstatus);
			s+=icon_start;
			if(flag){
			  s+=icon_leaveEntry;	
			}else{
				s+=icon_leaveEntry_noclick;
			}
		}else{
			s+=icon_leaveEntry_noclick;
		}
		s+=icon_look+icon_end;
	}
	return s;
};
/**=========跳转页面开始========**/
STUEMPLOYLIST.jumpUrl=function(pdata,url){
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


STUEMPLOYLIST.checkStudentForInputjob=function(stuids){
	var bret = false;
    $.ajax({
       	type:"post",
       	data:{userids:stuids},
 	   	url:basePath+"/job/checkjobstatus.do",
		dataType:"json",
		async:false,
 		success:function(jsondata){
 			ncode = jsondata.code;
 			if(ncode ==200)
 			{
 					bret = true;
 			}
 			else
 				{
 				  layer.alert(jsondata.message);
 				}
 			
 		}
		});
   
    return bret;
};
//ajax提交-for获取表格数据(公用)
STUEMPLOYLIST.ajaxSubmit=function(dataParam,url){
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
			STUEMPLOYLIST.isuserselect = false;
			STUEMPLOYLIST.Grid.setSelectedRows([]);
			  //填充表格数据
			STUEMPLOYLIST.Grid.setData(data.datalist);
			STUEMPLOYLIST.Grid.render();
			  //设置选中多行
			 var rows =  STUEMPLOYLIST.resolveSelectIndex();
			 STUEMPLOYLIST.Grid.setSelectedRows(rows);
				if(dataParam.currpage==1){
					//初始化分页-总记录数/每页条数
					PageWiget.init(data.recordcount,PageWiget.getPageSize());
					$("#sp_otherpagemsg").text("当前年份已就业学员人数"
							                   +data.hasWorkCount+"人,其中在职"
							                   +data.inWorkCount+"人，离职"
							                   +data.outWorkCount+"人，求职中学员"+
							                   data.jobHuntingCount+"人");
				} 
		}
	});
};

/**===========更多筛选开始======**/
STUEMPLOYLIST.muchmore=function(){
	$(".much-more").click(function(){
		$(this).hide();
		$(".put-away").show();
		$(this).parents(".query-box-parent").find(".query-box").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").hide();
		STUEMPLOYLIST.setTableHeight();
	});	
};


STUEMPLOYLIST.putaway=function(){
	$(".put-away").click(function(){
		$(this).hide();
		$(".much-more").show();
		$(this).parents(".query-box-parent").find(".query-box").hide();
		$(this).parents(".query-box-parent").find(".query-box:first").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").show();
		STUEMPLOYLIST.setTableHeight();
		//重置更多筛选出来的筛选项
		$("#zsSelectSearch").val("0");
		$(".ser-out-span").hide();
	});	
};

//设置表格高度
STUEMPLOYLIST.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
};

//更多筛选里，选择筛选项，显示对应的框
STUEMPLOYLIST.zsSelectSearch=function(){
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
				$("#jSerBeginTime_lq,#jSerEndTime_lq,#fJobBeginTime_lq,#fJobEndTime_lq").val("");
			}
		});
	});	
};

/**=============标记为我关注开始=============**/
STUEMPLOYLIST.remarkMyFocus=function(){
	$("#remarkMyFocus_lq").click(function(){
		var stids = STUEMPLOYLIST.selectStudentId.keys();//将selectStudentId Map转成数组
		if(stids.length>0){
			stids=stids.join(",");
			var ajUrl=STUEMPLOYLIST.basePath+"/student/remarkMyFocus.do";
			var dataPa={"stuUserIds":stids};
			STUEMPLOYLIST.ajaxForRemark(dataPa,ajUrl);
		}else{
			layer.msg('请选择“标记为我关注”的学员');
		}
	});
};
/**=============标记为我关注结束=============**/
/**=============取消关注开始================**/
STUEMPLOYLIST.cancelFocus=function(){
	$("#cancelFocus_lq").click(function(){
		var stids = STUEMPLOYLIST.selectStudentId.keys();
		if(stids.length>0){
			stids=stids.join(",");
			var ajUrl=STUEMPLOYLIST.basePath+"/student/cancelFocus.do";
			var dataPa={"stuUserIds":stids};
			STUEMPLOYLIST.ajaxForRemark(dataPa,ajUrl);
		}else{
			layer.msg('请选择“取消关注”的学员');
		}
	});//click end
};
/**=============取消关注结束================**/

/**==========ajax提交for标记关注/取消关注开始===========**/
STUEMPLOYLIST.ajaxForRemark=function(data,url){
	$.ajax({
	type: "POST",
	url:url,
	data:data,
	dataType:"json",
	success: function(data) {
		//删除选择项列表.
		STUEMPLOYLIST.selectStudentId.removeAll();
		
		layer.msg(data.message);
       if(data.code==200){
    		STUEMPLOYLIST.url=STUEMPLOYLIST.basePath+"/job/"+$("#opType").val()+"/goStuEmployList.do";
    	   //重新刷新页面
    	   STUEMPLOYLIST.jumpUrl({},STUEMPLOYLIST.url);   
       }
	}
});
};
/**==========ajax提交for标记关注/取消关注结束===========**/

///更新选中的记录
STUEMPLOYLIST.updateRowHashMap=function ()
{
		if(STUEMPLOYLIST.isuserselect==false) 
		{
			STUEMPLOYLIST.isuserselect=true;
		  return ;
		}
	 //清空本页内有的记录
	 var griddata = STUEMPLOYLIST.Grid.getData();
	 for(var i=0;i<griddata.length;i++)
	 {
		if(STUEMPLOYLIST.selectStudentId.get(griddata[i].stuUserId))
		{
		    //console.log("remove key:" +griddata[i].stuUserId);
		 	STUEMPLOYLIST.selectStudentId.remove(griddata[i].stuUserId);
	 	} 
		
		if(STUEMPLOYLIST.selectStatus.get(griddata[i].status)){
			STUEMPLOYLIST.selectStatus.remove(griddata[i].status);	
		}
	 }
	 
	   var selectedRows = STUEMPLOYLIST.Grid.getSelectedRows();

	   for(var i=0;i<selectedRows.length;i++)
	   {
	     // 得到一行的数据
		var rowdata = STUEMPLOYLIST.Grid.getDataItem(selectedRows[i]);
		//将该行的stuUserId放入hashMap中
		STUEMPLOYLIST.selectStudentId.put(rowdata.stuUserId,rowdata.stuUserId);
		//将该行的status放入hashMap中
		STUEMPLOYLIST.selectStatus.put(rowdata.status,rowdata.status);
	   }
	     //更新选中的数量
	  $("#hasChoosed").html("已选:"+STUEMPLOYLIST.selectStudentId.size());
	 //  console.log("hashmap data=="+STUEMPLOYLIST.selectStudentId.keys().join(","));	   
};

//获取选中的rows
STUEMPLOYLIST.resolveSelectIndex=function(){
	 //获取选中的行rows数组
	  var datarows = STUEMPLOYLIST.Grid.getData();
	   var rows=new Array();
	   var selectuserid = STUEMPLOYLIST.selectStudentId.keys();
	   
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

/**===============分页处理开始==============**/
STUEMPLOYLIST.dopagechange=function(pageno,pagesize){
	STUEMPLOYLIST.url=	STUEMPLOYLIST.basePath+"/job/getStuEmployList.do";
	STUEMPLOYLIST.queryParam.currpage=pageno;
	STUEMPLOYLIST.queryParam.pageSize=pagesize;
	STUEMPLOYLIST.queryParam.opType=$("#opType").val();
	STUEMPLOYLIST.ajaxSubmit(STUEMPLOYLIST.queryParam,STUEMPLOYLIST.url);
};
/**===============分页处理结束==============**/

/**==============查询按钮点击操作开始================**/
STUEMPLOYLIST.queryData=function(){
	$(".query_lq").click(function(){
		if($("#zsSelectSearch").val()=="0"){
			//1查询数据
			STUEMPLOYLIST.dataParam={
					  "select_school_id":$("#select_school_id").val(),
					  "select_class_name":$("#select_class_name").val(),
					  "student_name_lq":$("#student_name_lq").val(),
					  "jobStatus_lq":$("#jobStatus_lq").val(),
					  "jobWay_lq":$("#jobWay_lq").val(),
					  "opType":$("#opType").val(),
					  "currpage":1,
			          "pageSize":PageWiget.getPageSize()
					};	
		}else{
			//1查询数据
			STUEMPLOYLIST.dataParam={
					  "select_school_id":$("#select_school_id").val(),
					  "select_class_name":$("#select_class_name").val(),
					  "student_name_lq":$("#student_name_lq").val(),
					  "jobStatus_lq":$("#jobStatus_lq").val(),
					  "jobWay_lq":$("#jobWay_lq").val(),
					  "regional_director_userid":$("#regional_director_userid").val(),
					  "recruit_manager_userid":$("#recruit_manager_userid").val(),
					  "course_advisor_userid":$("#course_advisor_userid").val(),
					  "jSerBeginTime_lq":$("#jSerBeginTime_lq").val(),
					  "endTime_lq":$("#endTime_lq").val(),
					  "jSerEndTime_lq":$("#jSerEndTime_lq").val(),
					  "fJobBeginTime_lq":$("#fJobBeginTime_lq").val(),
					  "fJobEndTime_lq":$("#fJobEndTime_lq").val(),
					  "opType":$("#opType").val(),
					  "currpage":1,
			          "pageSize":PageWiget.getPageSize()
					};	
		}
		STUEMPLOYLIST.url=STUEMPLOYLIST.basePath+"/job/getStuEmployList.do";
		var flag=true;
		if($(".put-away").is(":visible")){
			if($("#zsSelectSearch").val()=="4"){
				 var jSerBeginTime_lq=$("#jSerBeginTime_lq").val();
				 var jSerEndTime_lq=$("#jSerEndTime_lq").val();	
				 if(jSerBeginTime_lq==null || jSerBeginTime_lq==""){
					 $("#jSerBeginTime_lq").showTipError("请输入开始时间...");
					 flag=false;
				 }
				 if(jSerEndTime_lq==null || jSerEndTime_lq==""){
					 $("#jSerEndTime_lq").showTipError("请输入结束时间..."); 
					 flag=false;
				 }
			}
			if($("#zsSelectSearch").val()=="5"){
				 var fJobBeginTime_lq=$("#fJobBeginTime_lq").val();
				 var fJobEndTime_lq=$("#fJobEndTime_lq").val();
				 if(fJobBeginTime_lq==null || fJobBeginTime_lq==""){
					 $("#fJobBeginTime_lq").showTipError("请输入开始时间...");
					 flag=false; 
				 }
				 if(fJobEndTime_lq==null || fJobEndTime_lq==""){
					 $("#fJobEndTime_lq").showTipError("请输入结束时间..."); 
					 flag=false; 
				 }
			}
		}
		 if(flag){
				STUEMPLOYLIST.ajaxSubmit(STUEMPLOYLIST.dataParam,STUEMPLOYLIST.url);
				//2设置查询参数for分页
				if($("#zsSelectSearch").val()=="0"){
					STUEMPLOYLIST.queryParam={
						  "select_school_id":$("#select_school_id").val(),
						  "select_class_name":$("#select_class_name").val(),
						  "student_name_lq":$("#student_name_lq").val(),
						  "jobStatus_lq":$("#jobStatus_lq").val(),
						  "jobWay_lq":$("#jobWay_lq").val(),
						  "opType":$("#opType").val()
					}; 
				}else{
					STUEMPLOYLIST.queryParam={
						  "select_school_id":$("#select_school_id").val(),
						  "select_class_name":$("#select_class_name").val(),
						  "student_name_lq":$("#student_name_lq").val(),
						  "jobStatus_lq":$("#jobStatus_lq").val(),
						  "jobWay_lq":$("#jobWay_lq").val(),
						  "regional_director_userid":$("#regional_director_userid").val(),
						  "recruit_manager_userid":$("#recruit_manager_userid").val(),
						  "course_advisor_userid":$("#course_advisor_userid").val(),
						  "jSerBeginTime_lq":$("#jSerBeginTime_lq").val(),
						  "endTime_lq":$("#endTime_lq").val(),
						  "jSerEndTime_lq":$("#jSerEndTime_lq").val(),
						  "fJobBeginTime_lq":$("#fJobBeginTime_lq").val(),
						  "fJobEndTime_lq":$("#fJobEndTime_lq").val(),
						  "opType":$("#opType").val()
					}; 
				}
		 }	
	});	
};
/**==============查询按钮点击操作结束================**/
/**===========入职登记按钮点击开始===================**/
STUEMPLOYLIST.inputJob=function(){
	$("#inputJob_lq").click(function(){
		
		
		var selectSudentIds=STUEMPLOYLIST.selectStudentId.keys();
		console.log(selectSudentIds.join(","))
		if(selectSudentIds.length>0){
			//判断所选择的学生职业状态, 没离职的不能再次入职登记. 
			var bcheck = STUEMPLOYLIST.checkStudentForInputjob(selectSudentIds.join(","));
			if(bcheck)
				{
					InputJob.opendialog(selectSudentIds.join(","),function(){
						STUEMPLOYLIST.url=STUEMPLOYLIST.basePath+"/job/"+$("#opType").val()+"/goStuEmployList.do";
				    	   //重新刷新页面
				    	  STUEMPLOYLIST.jumpUrl({},STUEMPLOYLIST.url);  
					});	
				}
		}else{
			layer.open({
				 title:'提示',
				  content: '请选择学员',
				  yes: function(index, layero){
					//do something
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				  }
				});
		}
	});
};
/**===========入职登记按钮点击结束===================**/
/**============离职登记开始=======================**/
STUEMPLOYLIST.leaveRegistrationDio=function(userid){
	Dimission.leaveRegistrationDio(userid,function(){
		STUEMPLOYLIST.url=STUEMPLOYLIST.basePath+"/job/"+$("#opType").val()+"/goStuEmployList.do";
 	   //重新刷新页面
 	  STUEMPLOYLIST.jumpUrl({},STUEMPLOYLIST.url);  
	});
};
/**============判断是否可以离职登记开始==========**/
STUEMPLOYLIST.canEnterIn=function(jobStatus){
var ret=true;
if(jobStatus==gl_jobStatus.LEAVEJOB){//已经为离职状态则不可离职登记
	ret =false;
}
return ret;
};
/**============判断是否可以离职登记开始==========**/
/**========ajax请求获取表格数据开始==========**/
STUEMPLOYLIST.getTableData=function(){
	STUEMPLOYLIST.dataParam={
            "opType":$("#opType").val(),
            "currpage":1,
            "pageSize":PageWiget.getPageSize()
      };
STUEMPLOYLIST.url=STUEMPLOYLIST.basePath+"/job/getStuEmployList.do";
STUEMPLOYLIST.ajaxSubmit(STUEMPLOYLIST.dataParam,STUEMPLOYLIST.url);	
};
/**========ajax请求获取表格数据结束==========**/

STUFEELIST=function(){};

STUFEELIST.basePath=basePath;

//表格对象. 
STUFEELIST.Grid=null;

STUFEELIST.url=null;

STUFEELIST.dataParam={};

//点击查询时保存查询参数for分页
STUFEELIST.queryParam={};

//存放表格选中行的stuUserIds
STUFEELIST.selectStudentId=new HashMap();

//存放表格选中行的status for判断是否可合并班级操作
STUFEELIST.selectStatus=new HashMap();

STUFEELIST.init=function(canEdit){
	//设置表格部分的高度
	STUFEELIST.setTableHeight();
	
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
		name: "姓名",
		field: "realName", 
		width: 200
	});
	
	columns.push({
		id: "className", 
		name: "班级名称",
		field: "className", 
		width: 200
	});
	
	columns.push({
		id: "schoolName", 
		name: "所在院校",
		field: "schoolName", 
		width: 200
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
		id: "avStanMoney", 
		name: "报名费合同金额",
		field: "avStanMoney", 
		width: 200
	});
	
	columns.push({
		id: "hasPaid", 
		name: "实训费",
		field: "hasPaid", 
		width: 150
	});
	
	columns.push({
		id: "hasStanMoney", 
		name: "实训费合同金额",
		field: "hasStanMoney", 
		width: 200
	});
	
	columns.push({
		id: "hasFavMoney", 
		name: "实训费减免金额",
		field: "hasFavMoney", 
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
		formatter: STUFEELIST.actionRender
	});
	//产生grid 控件
	STUFEELIST.Grid = new Slick.Grid("#myGrid",[], columns, options);
	
	STUFEELIST.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	STUFEELIST.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	STUFEELIST.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	STUFEELIST.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		
		STUFEELIST.updateRowHashMap();
		
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 
	
	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	/**============表格结束============**/
	
	/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示开始===========**/
    if($("#opType").val()=="1"){
		$("#allStu_lq").addClass("curr").siblings().removeClass("curr");	
	}else{
		$("#myManage_lq").addClass("curr").siblings().removeClass("curr");	
	}
	/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示结束===========**/
    
	/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示开始===========**/
	 if(gl_isHashRight(functionIds,61) && !gl_isHashRight(functionIds,62)){//具有查看我管理的列表
		 $("#allStu_lq").hide();//隐藏全部tab
		 $("#myManage_lq").addClass("curr");	
		 //获取全部意向学员表格数据
		  STUFEELIST.getTableData(1);
          //点击查询按钮并设置opType
          STUFEELIST.queryData(1);
	 }else if(!gl_isHashRight(functionIds,61) && gl_isHashRight(functionIds,62)){//具有查看全部列表权限
		    $("#myManage_lq").hide(); //隐藏我管理的tab
		    $("#allStu_lq").addClass("curr");
			//获取表格数据
			STUFEELIST.getTableData(0);
         //点击查询按钮并设置opType
         STUFEELIST.queryData(0);
	 }else if(gl_isHashRight(functionIds,61) && gl_isHashRight(functionIds,62)){//具有查看我管理的和全部列表权限
		    if($("#opType").val()=="1"){
				$("#allStu_lq").addClass("curr").siblings().removeClass("curr");	
			}else{
				$("#myManage_lq").addClass("curr").siblings().removeClass("curr");	
			}
	    //获取表格数据
		STUFEELIST.getTableData($("#opType").val());
       //点击查询按钮并设置opType
       STUFEELIST.queryData($("#opType").val());
	 }else{
		 $("#allStu_lq").hide();//隐藏全部tab
		 $("#myManage_lq").hide(); //隐藏我管理的tab
	 }
	/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示结束===========**/
	
	/**========tab点击事件开始=======**/
	$("#allStu_lq").click(function(){
			//1跳转页面
			STUFEELIST.jumpUrl({},STUFEELIST.basePath+"/income/1/goStuFeeList.do");	
			//2.设置选中的tab样式
			$("#allStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空表格选中行的stuUserIds和status
			STUFEELIST.selectStudentId=new HashMap();
			STUFEELIST.selectStatus=new HashMap();
	});
	$("#myManage_lq").click(function(){
		//1跳转页面
		STUFEELIST.jumpUrl({},STUFEELIST.basePath+"/income/2/goStuFeeList.do");	
		//2.设置选中的tab样式
		$("#myManage_lq").addClass("curr").siblings().removeClass("curr");
		//清空表格选中行的stuUserIds和status
		STUFEELIST.selectStudentId=new HashMap();
		STUFEELIST.selectStatus=new HashMap();
	});
	/**========tab点击事件结束=======**/
	
	//更多筛选
	STUFEELIST.muchmore();
	//收起筛选
	STUFEELIST.putaway();
	
	if(canEdit)
	{
		//添加减免记录
		STUFEELIST.addReduRecord();
		//添加回款记录
		STUFEELIST.addCashRecord();
		//添加退费记录
		STUFEELIST.addRefundRecord();
	}
	
	
};//init end

//每行输出的操作按钮. 
//value : 列的内容。 
//dataContext : 行数据对象。 
STUFEELIST.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',
	icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="学员收费详情" onclick="stuInfoDialogue('+dataContext.stuUserId+',\'hkStu\')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>',
	icon_end='</div>';
	//根据行数据确定是否显示icon2.png
	if(dataContext.nstatus==1){
		s += icon_start+icon_look+icon_end;
	}
	return s;
};

/**=========跳转页面开始========**/
STUFEELIST.jumpUrl=function(pdata,url){
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
STUFEELIST.muchmore=function(){
	$(".much-more").click(function(){
		$(this).hide();
		$(".put-away").show();
		$(this).parents(".query-box-parent").find(".query-box").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").hide();
		STUFEELIST.setTableHeight();
	});	
};


STUFEELIST.putaway=function(){
	$(".put-away").click(function(){
		$(this).hide();
		$(".much-more").show();
		$(this).parents(".query-box-parent").find(".query-box").hide();
		$(this).parents(".query-box-parent").find(".query-box:first").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").show();
		STUFEELIST.setTableHeight();
	});	
};

//设置表格高度
STUFEELIST.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
};

//ajax提交-for获取表格数据(公用)
STUFEELIST.ajaxSubmit=function(dataParam,url){
	$.ajax({
		type: "POST",
		url:url,
		data:dataParam,
		dataType:"json",
		success: function(data) {
				//清除当前选择.
			STUFEELIST.isuserselect = false;
			STUFEELIST.Grid.setSelectedRows([]);
			  //填充表格数据
			STUFEELIST.Grid.setData(data.datalist);
			STUFEELIST.Grid.render();
			  //设置选中多行
			 var rows =  STUFEELIST.resolveSelectIndex();
			 STUFEELIST.Grid.setSelectedRows(rows);
				if(dataParam.currpage==1){
					//初始化分页-总记录数/每页条数
					PageWiget.init(data.recordcount,PageWiget.getPageSize());
				    var pageMsg="当前年份学员总数为:"
				    	       +data.recordcount+"人，其中开除"
				    	       +data.expelCount+"人，劝退"
				               +data.quanTuiCount+"人，退学"
				               +data.leaveSchollCount+"人";
			       $("#sp_otherpagemsg").text(pageMsg);
				} 
		}
	});
};

///更新选中的记录
STUFEELIST.updateRowHashMap=function ()
{
		if(STUFEELIST.isuserselect==false) 
		{
			STUFEELIST.isuserselect=true;
		  return ;
		}
	 //清空本页内有的记录
	 var griddata = STUFEELIST.Grid.getData();
	 for(var i=0;i<griddata.length;i++)
	 {
		if(STUFEELIST.selectStudentId.get(griddata[i].stuUserId))
		{
		    //console.log("remove key:" +griddata[i].stuUserId);
		 	STUFEELIST.selectStudentId.remove(griddata[i].stuUserId);
	 	} 
		
		if(STUFEELIST.selectStatus.get(griddata[i].status)){
			STUFEELIST.selectStatus.remove(griddata[i].status);	
		}
	 }
	 
	   var selectedRows = STUFEELIST.Grid.getSelectedRows();

	   for(var i=0;i<selectedRows.length;i++)
	   {
	     // 得到一行的数据
		var rowdata = STUFEELIST.Grid.getDataItem(selectedRows[i]);
		//将该行的stuUserId和status放入hashMap中
		STUFEELIST.selectStudentId.put(rowdata.stuUserId,rowdata.stuUserId);
		STUFEELIST.selectStatus.put(rowdata.status,rowdata.status);
	   }
	     //更新选中的数量
	  $("#hasChoosed").html("已选:"+STUFEELIST.selectStudentId.size());
	 //  console.log("hashmap data=="+STUFEELIST.selectStudentId.keys().join(","));	   
};
//获取选中的rows
STUFEELIST.resolveSelectIndex=function(){
	 //获取选中的行rows数组
	  var datarows = STUFEELIST.Grid.getData();
	   var rows=new Array();
	   var selectuserid = STUFEELIST.selectStudentId.keys();
	   
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
STUFEELIST.dopagechange=function(pageno,pagesize){
	STUFEELIST.url=	STUFEELIST.basePath+"/income/getStuFeeList.do";
	STUFEELIST.queryParam.currpage=pageno;
	STUFEELIST.queryParam.pageSize=pagesize;
	STUFEELIST.queryParam.opType=$("#opType").val();
	STUFEELIST.ajaxSubmit(STUFEELIST.queryParam,STUFEELIST.url);
};
/**===============分页处理结束==============**/
/**==============查询按钮点击操作开始================**/
STUFEELIST.queryData=function(){
	$(".query_lq").click(function(){
	    if($(".much-more").is(":visible")){
			//1查询数据
			STUFEELIST.dataParam={
			  "isAvaiable_lq":$("#isAvaiable_lq").val(),
			  "hasPaid_lq":$("#hasPaid_lq").val(),
			  "select_school_id":$("#select_school_id").val(),
			  "stuStatus_lq":$("#stuStatus_lq").val(),
			  "regional_director_userid":$("#regional_director_userid").val(),
			  "opType":$("#opType").val(),
			  "currpage":1,
	          "pageSize":PageWiget.getPageSize()
			};
			//2.保存分页查询参数
			STUFEELIST.queryParam={
			  "isAvaiable_lq":$("#isAvaiable_lq").val(),
			  "hasPaid_lq":$("#hasPaid_lq").val(),
			  "select_school_id":$("#select_school_id").val(),
			  "stuStatus_lq":$("#stuStatus_lq").val(),
			  "regional_director_userid":$("#regional_director_userid").val(),
			  "opType":$("#opType").val()
		   }; 
	    }else{
			//1查询数据
			STUFEELIST.dataParam={
			  "isAvaiable_lq":$("#isAvaiable_lq").val(),
			  "hasPaid_lq":$("#hasPaid_lq").val(),
			  "select_school_id":$("#select_school_id").val(),
			  "stuStatus_lq":$("#stuStatus_lq").val(),
			  "regional_director_userid":$("#regional_director_userid").val(),
			  "recruit_manager_userid":$("#recruit_manager_userid").val(),
			  "course_advisor_useid":$("#course_advisor_useid").val(),
			  "student_name_lq":$("#student_name_lq").val(),
			  "opType":$("#opType").val(),
			  "currpage":1,
	          "pageSize":PageWiget.getPageSize()
			};
			//2保存分页查询参数
			STUFEELIST.queryParam={
			  "isAvaiable_lq":$("#isAvaiable_lq").val(),
			  "hasPaid_lq":$("#hasPaid_lq").val(),
			  "select_school_id":$("#select_school_id").val(),
			  "stuStatus_lq":$("#stuStatus_lq").val(),
			  "regional_director_userid":$("#regional_director_userid").val(),
			  "recruit_manager_userid":$("#recruit_manager_userid").val(),
			  "course_advisor_useid":$("#course_advisor_useid").val(),
			  "student_name_lq":$("#student_name_lq").val(),
			  "opType":$("#opType").val()
			}; 
	    }
	    //3请求url
	    STUFEELIST.url=STUFEELIST.basePath+"/income/getStuFeeList.do";
	    //提交查询
		STUFEELIST.ajaxSubmit(STUFEELIST.dataParam,STUFEELIST.url);
	});
};
/**==============查询按钮点击操作结束================**/
/**=============添加减免记录开始============**/
STUFEELIST.addReduRecord=function(){
$("#addReduRecord_lq").click(function(){
	 var selectuserid = STUFEELIST.selectStudentId.keys();
	 if(selectuserid.length>0){
		 console.log("selectuserid="+selectuserid.join(","));
		 FEE_SERVICE_PAGE.loadPage(selectuserid.join(","),$("#opType").val(),'JIANMIAN');
		 //打开添加减免记录弹窗
	 }else{
			layer.open({
				 title:'提示',
				  content: '您尚未选择待操作的学员',
				  yes: function(index, layero){
					//do something
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				  }
				});
	 }
});	
};
/**=============添加减免记录结束============**/
/**=============添加回款记录开始============**/
STUFEELIST.addCashRecord=function(){
$("#addCashRecord_lq").click(function(){
	 var selectuserid = STUFEELIST.selectStudentId.keys();
	 if(selectuserid.length>0){
		 console.log("selectuserid="+selectuserid.join(","));
		 FEE_SERVICE_PAGE.loadPage(selectuserid.join(","),$("#opType").val(),'HUIKUAN');
		 //打开添加回款记录弹窗
	 }else{
			layer.open({
				 title:'提示',
				  content: '您尚未选择待操作的学员',
				  yes: function(index, layero){
					//do something
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				  }
				});
	 }
});	
};
/**=============添加回款记录结束============**/
/**=============添加退费记录开始============**/
STUFEELIST.addRefundRecord=function(){
$("#addRefundRecord_lq").click(function(){
	 var selectuserid = STUFEELIST.selectStudentId.keys();
	 if(selectuserid.length>0){
		 console.log("selectuserid="+selectuserid.join(","));
		 FEE_SERVICE_PAGE.loadPage(selectuserid.join(","),$("#opType").val(),'TUIFEI');
		 //打开添加减免记录弹窗
	 }else{
			layer.open({
				 title:'提示',
				  content: '您尚未选择待操作的学员',
				  yes: function(index, layero){
					//do something
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				  }
				});
	 }
});	
};
/**=============添加退费记录结束============**/

/**========ajax请求获取表格数据开始==========**/
STUFEELIST.getTableData=function(opType){
	STUFEELIST.dataParam={
            "opType":opType,
            "currpage":1,
            "pageSize":PageWiget.getPageSize()
      };
STUFEELIST.url=STUFEELIST.basePath+"/income/getStuFeeList.do";
STUFEELIST.ajaxSubmit(STUFEELIST.dataParam,STUFEELIST.url);	
};
/**========ajax请求获取表格数据结束==========**/
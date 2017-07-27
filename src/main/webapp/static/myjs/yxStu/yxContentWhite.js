/**
 * 
 * 意向学院主页js 代码. 
 * 作者: 罗玉琳
 * 
 */
YXCONTENTWHITE=function(){};

YXCONTENTWHITE.basePath=basePath;

//表格对象. 
YXCONTENTWHITE.Grid=null;

YXCONTENTWHITE.url=null;

YXCONTENTWHITE.dataParam={};

//点击查询时保存查询参数for分页
YXCONTENTWHITE.queryParam={};

//存放表格选中行的stuUserIds
YXCONTENTWHITE.selectStudentId=  new HashMap();

YXCONTENTWHITE.init=function(){
	/**===========功能权限判断开始==================**/
	//设置新增意向学员是否可操作
	 if(gl_isHashRight(functionIds,32)){//是否具有新增意向学员的功能
		 $("#showAddStuDialog").show();
	 }else{
		 $("#showAddStuDialog").hide();
	 }
	/**===========功能权限判断结束==================**/
	
	/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示开始===========**/
	 if(gl_isHashRight(functionIds,47) && !gl_isHashRight(functionIds,48)){//具有查看全部意向学员列表但无查看我关注的意向学员列表权限
		 $("#myFocusStu_lq").hide();//隐藏我关注的意向tab
		 $("#allStu_lq").addClass("curr");	
		 //获取全部意向学员表格数据
		  YXCONTENTWHITE.getTableData(1);
           //点击查询按钮并设置opType
           YXCONTENTWHITE.queryData(1);
	 }else if(!gl_isHashRight(functionIds,47) && gl_isHashRight(functionIds,48)){//具有查看我关注的意向学员列表但无查看我全部意向学员列表权限
		    $("#allStu_lq").hide(); //隐藏全部意向tab
		    $("#myFocusStu_lq").addClass("curr");
			$("#remarkMyFocus_lq").hide();//隐藏标记为我关注的按钮
			$("#i_lq").hide();//隐藏|
			//获取表格数据
			YXCONTENTWHITE.getTableData(0);
          //点击查询按钮并设置opType
          YXCONTENTWHITE.queryData(0);
	 }else if(gl_isHashRight(functionIds,47) && gl_isHashRight(functionIds,48)){//具有查看我关注的和全部的意向学员列表权限
	    if($("#opType").val()=="0"){
				$("#myFocusStu_lq").addClass("curr").siblings().removeClass("curr");
				$("#remarkMyFocus_lq").hide();//隐藏标记为我关注的按钮
				$("#i_lq").hide();//隐藏|
		}else{
				$("#allStu_lq").addClass("curr").siblings().removeClass("curr");	
		}
	    //获取表格数据
		YXCONTENTWHITE.getTableData($("#opType").val());
        //点击查询按钮并设置opType
        YXCONTENTWHITE.queryData($("#opType").val());
	 }else{
		 $("#myFocusStu_lq").hide();//隐藏我关注的意向tab
		 $("#allStu_lq").hide(); //隐藏全部意向tab
	 }
	/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示结束===========**/
	
	/**========tab点击事件开始=======**/
	$("#myFocusStu_lq").click(function(){
		if($("#opType")!="0"){//如果本来就是在我的关注页面，则无需跳转，否则跳转页面
			//1跳转页面
			YXCONTENTWHITE.jumpUrl({},YXCONTENTWHITE.basePath+"/student/0/goYxStu.do");
			//2.设置选中的tab样式
			$("#myFocusStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空存放学生userid的hashMap
			YXCONTENTWHITE.selectStudentId=new HashMap();
		}
	});
	
	$("#allStu_lq").click(function(){
		if($("#opType")!="1"){//如果本来就是在全部学员页面，则无需跳转，否则跳转页面
			//1跳转页面
			YXCONTENTWHITE.jumpUrl({},YXCONTENTWHITE.basePath+"/student/1/goYxStu.do");	
			//2.设置选中的tab样式
			$("#allStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空存放学生userid的hashMap
			YXCONTENTWHITE.selectStudentId=new HashMap();
		}
	});
	/**========tab点击事件结束=======**/
	//设置表格部分的高度
	YXCONTENTWHITE.setTableHeight();
	
	//显示添加意向学员弹窗
	$("#showAddStuDialog").on("click",function(){
		YXCONTENTWHITE.loadPage(YXCONTENTWHITE.basePath+"/student/showSubmitStuInfoDialog.do",null);
	});
	
	//点击更多筛选出现隐藏的搜索框
	YXCONTENTWHITE.moreSearch();
	//点击收起筛选隐藏搜索框
	YXCONTENTWHITE.closeMoreSea();

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
		formatter:YXCONTENTWHITE.renderForCheckBox
	});
	
	columns.push({
		id: "realName", 
		name: "姓名",
		field: "realName", 
		width: 200,
		formatter:YXCONTENTWHITE.realnameRender
	});
	
	columns.push({
		id: "tel", 
		name: "电话",
		field: "tel", 
		width: 150
	});
	
	columns.push({
		id: "schoolName", 
		name: "所在院校",
		field: "schoolName", 
		width: 150
	});
	
	columns.push({
		id: "major", 
		name: "所在专业",
		field: "major", 
		width: 150
	});
	
	columns.push({
		id: "schoolSubname", 
		name: "所在学院",
		field: "schoolSubname", 
		width: 150
	});
	
	columns.push({
		id: "grade", 
		name: "所在年级",
		field: "grade", 
		width: 100
	});
	
	columns.push({
		id: "auditStatus", 
		name: "审核状态",
		field: "auditStatus", 
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
		formatter: YXCONTENTWHITE.actionRender
	});
	//产生grid 控件
	YXCONTENTWHITE.Grid = new Slick.Grid("#myGrid",[], columns, options);
	
	YXCONTENTWHITE.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	YXCONTENTWHITE.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	YXCONTENTWHITE.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	YXCONTENTWHITE.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		
		YXCONTENTWHITE.updateRowHashMap();
		
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 
	
	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	
	
    //标记为我关注的
	YXCONTENTWHITE.remarkMyFocus();
	//取消关注
	YXCONTENTWHITE.cancelFocus();
};//init end

//是否是用户导致选择项变化
YXCONTENTWHITE.isuserselect =true;

///更新选中的记录
YXCONTENTWHITE.updateRowHashMap=function ()
{
		if(YXCONTENTWHITE.isuserselect==false) 
		{
		  YXCONTENTWHITE.isuserselect=true;
		  return ;
		}
	 //清空本页内有的记录
	 var griddata = YXCONTENTWHITE.Grid.getData();
	 for(var i=0;i<griddata.length;i++)
	 {
		if(YXCONTENTWHITE.selectStudentId.get(griddata[i].stuUserId))
		{
		    //console.log("remove key:" +griddata[i].stuUserId);
		 	YXCONTENTWHITE.selectStudentId.remove(griddata[i].stuUserId);
	 	}
		 
	 }
	 
	   var selectedRows = YXCONTENTWHITE.Grid.getSelectedRows();

	   var struserid ="";
	   for(var i=0;i<selectedRows.length;i++)
	   {
	     // 得到一行的数据
		var rowdata = YXCONTENTWHITE.Grid.getDataItem(selectedRows[i]);
		//将该行的stuUserId放入hashMap中
		YXCONTENTWHITE.selectStudentId.put(rowdata.stuUserId,rowdata.stuUserId);
	   }
	     //更新选中的数量
	  $("#hasChoosed").html("已选:"+YXCONTENTWHITE.selectStudentId.size());
	 //  console.log("hashmap data=="+YXCONTENTWHITE.selectStudentId.keys().join(","));
	   
};
//点击更多筛选出现隐藏的搜索框
YXCONTENTWHITE.moreSearch=function(){
	$(".much-more").click(function(){
		$(this).hide();
		$(".put-away").show();
		$(this).parents(".query-box-parent").find(".query-box").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").hide();
		YXCONTENTWHITE.setTableHeight();
	});
};


//每行输出的操作按钮. 
// value : 列的内容。 
// dataContext : 行数据对象。 
YXCONTENTWHITE.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',
	icon_edit='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="审核" onclick="CHECK_STUDENT_INFO.loadPage('+dataContext.stuUserId+');"><i class="opti-icon Hui-iconfont">&#xe6df;</i></a>',
	icon_edit_noclick='<a class="c-gray opt-incon-btn" href="javascript:void(0);" title="审核"><i class="opti-icon Hui-iconfont">&#xe6df;</i></a>',
	icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="查看" onclick="stuInfoDialogue('+dataContext.stuUserId+',\'yxStu\')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>',
	icon_end='</div>';
	//根据行数据确定是否显示icon2.png
	if(dataContext.nstatus==1){
		if(gl_isHashRight(functionIds,49)){//设置审核权限
			s += icon_start+icon_edit+icon_look+icon_end;
		}else{
			s += icon_start+icon_edit_noclick+icon_look+icon_end;
		}
	}
	return s;
};

YXCONTENTWHITE.renderForCheckBox=function(row,cell,value,columnDef,dataContext){
	var s="";
	s=dataContext.indexno+"<input type='hidden' value='"+dataContext.stuUserId+"'>";
	return s;
};


/**
自定义显示姓名, 是否是重点, 是否是我的,  显示不同的图标. 
**/	
YXCONTENTWHITE.realnameRender=function (row, cell, value, columnDef, dataContext){
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

//点击收起筛选隐藏搜索框
YXCONTENTWHITE.closeMoreSea=function(){
	$(".put-away").click(function(){
		$(this).hide();
		$(".much-more").show();
		$(this).parents(".query-box-parent").find(".query-box").hide();
		$(this).parents(".query-box-parent").find(".query-box:first").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").show();
		YXCONTENTWHITE.setTableHeight();
	});
};

//设置表格高度
YXCONTENTWHITE.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
};

/** 展示弹窗 */
YXCONTENTWHITE.loadPage = function(strurl,idcontant){
  $.ajax({
	  dataType:"html",
	  url:strurl,
	  data:{"type":"teacherAdd"},//老师为学生添加信息（与学生自己报名验证提交的方法不一致）
	  type:"post",
	  success: function(html){
		  YXCONTENTWHITE.showDialog(html);
//		  $("#"+idcontant).html(html);
	  }
  });
};

/** 加载弹窗 */
YXCONTENTWHITE.showDialog = function(html){
	var body_height=$("body").height();
	layer.open({
	  type: 1, 
	  title: ['新增意向学员'],
	  skin: 'demo-class',
	  shade: 0.6,
	  anim:8,
	  area: ['600px', '650'+'px'],
	  content: html //这里content是一个普通的String
	});
};

//获取选中的rows
YXCONTENTWHITE.resolveSelectIndex=function(){
	 //获取选中的行rows数组
	  var datarows = YXCONTENTWHITE.Grid.getData();
	   var rows=new Array();
	   var selectuserid = YXCONTENTWHITE.selectStudentId.keys();
	   
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
//ajax提交-for获取表格数据(公用)
YXCONTENTWHITE.ajaxSubmit=function(dataParam,url){
	$.ajax({
		type: "POST",
		url:url,
		data:dataParam,
		dataType:"json",
		success: function(data) {
			if(data.recordcount<=0){
				if(dataParam.opType=="0"){
					layer.msg('您还没有标记"我关注的"意向学员，可从全部意向学员列表中选择标记"我关注的"意向学员',{time:86400000});//24小时后关闭
				}else{
					layer.closeAll();
				}
			}else{
				layer.closeAll();
			}
				//清除当前选择.
			  YXCONTENTWHITE.isuserselect = false;
			   YXCONTENTWHITE.Grid.setSelectedRows([]);
			  //填充表格数据
			  YXCONTENTWHITE.Grid.setData(data.datalist);
			  YXCONTENTWHITE.Grid.render();
			  //设置选中多行
			 var rows =  YXCONTENTWHITE.resolveSelectIndex();
			  YXCONTENTWHITE.Grid.setSelectedRows(rows);
				if(dataParam.currpage==1){
					//初始化分页-总记录数/每页条数
					PageWiget.init(data.recordcount,PageWiget.getPageSize());
				} 
			 
		}
	});
};

/**=========跳转页面开始========**/
YXCONTENTWHITE.jumpUrl=function(pdata,url){
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

/**===============分页处理开始==============**/
YXCONTENTWHITE.dopagechange=function(pageno,pagesize){
	YXCONTENTWHITE.url=	YXCONTENTWHITE.basePath+"/student/getYxStuList.do";
	YXCONTENTWHITE.queryParam.currpage=pageno;
	YXCONTENTWHITE.queryParam.pageSize=pagesize;
	YXCONTENTWHITE.queryParam.opType=$("#opType").val();
	YXCONTENTWHITE.ajaxSubmit(YXCONTENTWHITE.queryParam,YXCONTENTWHITE.url);
};
/**===============分页处理结束==============**/

/**=============标记为我关注开始=============**/
YXCONTENTWHITE.remarkMyFocus=function(){
	$("#remarkMyFocus_lq").click(function(){
		var stids = YXCONTENTWHITE.selectStudentId.keys();
		if(stids.length>0){
			stids=stids.join(",");
			var ajUrl=YXCONTENTWHITE.basePath+"/student/remarkMyFocus.do";
			var dataPa={"stuUserIds":stids};
			YXCONTENTWHITE.ajaxForRemark(dataPa,ajUrl);
		}else{
			layer.msg('请选择“标记为我关注”的学员');
		}
	});
};
/**=============标记为我关注结束=============**/
/**=============取消关注开始================**/
YXCONTENTWHITE.cancelFocus=function(){
	$("#cancelFocus_lq").click(function(){
		var stids = YXCONTENTWHITE.selectStudentId.keys();
		if(stids.length>0){
			stids=stids.join(",");
			var ajUrl=YXCONTENTWHITE.basePath+"/student/cancelFocus.do";
			var dataPa={"stuUserIds":stids};
			YXCONTENTWHITE.ajaxForRemark(dataPa,ajUrl);
		}else{
			layer.msg('请选择“取消关注”的学员');
		}
	});//click end
};
/**=============取消关注结束================**/

/**==========ajax提交for标记关注/取消关注开始===========**/
YXCONTENTWHITE.ajaxForRemark=function(data,url){
	$.ajax({
	type: "POST",
	url:url,
	data:data,
	dataType:"json",
	success: function(data) {
		//删除选择项列表.
		YXCONTENTWHITE.selectStudentId.removeAll();
		
		layer.msg(data.message);
       if(data.code==200){
    		YXCONTENTWHITE.url=YXCONTENTWHITE.basePath+"/student/"+$("#opType").val()+"/goYxStu.do";
    	   //重新刷新页面
    	   YXCONTENTWHITE.jumpUrl({},YXCONTENTWHITE.url);  
    	   
    	   
       }
	}
});
};
/**==========ajax提交for标记关注/取消关注结束===========**/

/**==============查询按钮点击操作开始================**/
YXCONTENTWHITE.queryData=function(opType){
	$("#query_lq").click(function(){
		//1查询数据
		YXCONTENTWHITE.dataParam={
		  "select_school_name_lq":$("#select_school_name_lq").val(),
		  "school_sub_name_lq":$("#school_sub_name_lq").val(),
		  "student_name_lq":$("#student_name_lq").val(),
		  "stuStatus_lq":$("#stuStatus_lq").val(),
		  "grade_lq":$("#grade_lq").val(),
		  "opType":opType,
		  "currpage":1,
          "pageSize":PageWiget.getPageSize()
		};
		YXCONTENTWHITE.url=YXCONTENTWHITE.basePath+"/student/getYxStuList.do";
		YXCONTENTWHITE.ajaxSubmit(YXCONTENTWHITE.dataParam,YXCONTENTWHITE.url);
		//2设置查询参数for分页
		YXCONTENTWHITE.queryParam={
				  select_school_name_lq:$("#select_school_name_lq").val(),
				  school_sub_name_lq:$("#school_sub_name_lq").val(),
				  student_name_lq:$("#student_name_lq").val(),
				  stuStatus_lq:$("#stuStatus_lq").val(),
				  grade_lq:$("#grade_lq").val(),
				  opType:opType		
		};
		
	});
};
/**==============查询按钮点击操作结束================**/
/**==========获取表格数据====================**/
YXCONTENTWHITE.getTableData=function(opType){
	YXCONTENTWHITE.dataParam={
            "opType":opType,
            "currpage":1,
            "pageSize":PageWiget.getPageSize()
   };
YXCONTENTWHITE.url=YXCONTENTWHITE.basePath+"/student/getYxStuList.do";
YXCONTENTWHITE.ajaxSubmit(YXCONTENTWHITE.dataParam,YXCONTENTWHITE.url);	
};

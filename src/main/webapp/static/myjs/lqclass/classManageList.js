CLASSMANAGELIST=function(){};

CLASSMANAGELIST.basePath=basePath;

//表格对象. 
CLASSMANAGELIST.Grid=null;

CLASSMANAGELIST.url=null;

CLASSMANAGELIST.dataParam={};

//点击查询时保存查询参数for分页
CLASSMANAGELIST.queryParam={};

//存放表格选中行的lqClassIds
CLASSMANAGELIST.selectClassId=new HashMap();

//存放表格选中行的status for判断是否可合并班级操作
CLASSMANAGELIST.selectStatus=new HashMap();

CLASSMANAGELIST.init=function(){
	//设置表格部分的高度
	CLASSMANAGELIST.setTableHeight();
	
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
		width: 120	//宽度.
	});
	
	columns.push({
		id: "className", 
		name: "班级名称",
		field: "className", 
		width: 250,
		formatter:CLASSMANAGELIST.realnameRender
	});
	
	columns.push({
		id: "courseType", 
		name: "课程名称",
		field: "courseType", 
		width: 100
	});
	
	columns.push({
		id: "classStatus", 
		name: "状态",
		field: "classStatus", 
		width: 100
	});
	
	columns.push({
		id: "realCount", 
		name: "开班人数",
		field: "realCount", 
		width: 100
	});
	
	columns.push({
		id: "currentCount", 
		name: "当前人数",
		field: "currentCount", 
		width: 100
	});
	columns.push({
		id: "totalClass", 
		name: "已上课时",
		field: "totalClass", 
		width: 180,
		formatter: CLASSMANAGELIST.totalClass
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
		width: 100,
		formatter: CLASSMANAGELIST.actionRender
	});
	//产生grid 控件
	CLASSMANAGELIST.Grid = new Slick.Grid("#myGrid",[], columns, options);
	
	CLASSMANAGELIST.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	CLASSMANAGELIST.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	CLASSMANAGELIST.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	CLASSMANAGELIST.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		
		CLASSMANAGELIST.updateRowHashMap();
		
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
	//设置合并班级权限
	if(gl_isHashRight(functionIds,67)){
		$("#mergeClass_lq").show();
	}else{
		$("#mergeClass_lq").hide().next().hide();
	}
	//设置列表权限
	/**==========初始化设置tab选中样式以及设置标记和取消标记按钮的隐藏显示开始===========**/
			var listFlag1=gl_isHashRight(functionIds,63);//是否可查看我管理的列表
			var listFlag2=gl_isHashRight(functionIds,65);//是否可查看我关注的列表
			var listFlag3=gl_isHashRight(functionIds,64);//是否可查看全部的列表
			
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
		    	  CLASSMANAGELIST.getTableData();
					//查询数据
					CLASSMANAGELIST.queryData();	
		      }
		/**+++++++++++权限控制结束+++++++++++++++++++**/
	
	/**========tab点击事件开始=======**/
	$("#myFocusStu_lq").click(function(){
			//1跳转页面
			CLASSMANAGELIST.jumpUrl({},CLASSMANAGELIST.basePath+"/lqClass/0/goClassManageList.do");
			//2.设置选中的tab样式
			$("#myFocusStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空表格选中行的lqClassIds和status
			CLASSMANAGELIST.selectClassId=new HashMap();
			CLASSMANAGELIST.selectStatus=new HashMap();
	});
	$("#allStu_lq").click(function(){
			//1跳转页面
			CLASSMANAGELIST.jumpUrl({},CLASSMANAGELIST.basePath+"/lqClass/1/goClassManageList.do");	
			//2.设置选中的tab样式
			$("#allStu_lq").addClass("curr").siblings().removeClass("curr");
			//清空表格选中行的lqClassIds和status
			CLASSMANAGELIST.selectClassId=new HashMap();
			CLASSMANAGELIST.selectStatus=new HashMap();
	});
	$("#myManage_lq").click(function(){
		//1跳转页面
		CLASSMANAGELIST.jumpUrl({},CLASSMANAGELIST.basePath+"/lqClass/2/goClassManageList.do");	
		//2.设置选中的tab样式
		$("#myManage_lq").addClass("curr").siblings().removeClass("curr");
		//清空表格选中行的lqClassIds和status
		CLASSMANAGELIST.selectClassId=new HashMap();
		CLASSMANAGELIST.selectStatus=new HashMap();
	});
	/**========tab点击事件结束=======**/
	
	//更多筛选
	CLASSMANAGELIST.muchmore();
	//收起筛选
	CLASSMANAGELIST.putaway();
	
	//标记为我关注
	CLASSMANAGELIST.remarkMyFocus();
	//取消关注
	CLASSMANAGELIST.cancelFocus();
	
	//跳转到合并分班页面
	CLASSMANAGELIST.jumpMergeClass();
};//init end

/**=========跳转页面开始========**/
CLASSMANAGELIST.jumpUrl=function(pdata,url){
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
CLASSMANAGELIST.muchmore=function(){
	$(".much-more").click(function(){
		$(this).hide();
		$(".put-away").show();
		$(this).parents(".query-box-parent").find(".query-box").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").hide();
		CLASSMANAGELIST.setTableHeight();
	});	
};


CLASSMANAGELIST.putaway=function(){
	$(".put-away").click(function(){
		$(this).hide();
		$(".much-more").show();
		$(this).parents(".query-box-parent").find(".query-box").hide();
		$(this).parents(".query-box-parent").find(".query-box:first").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").show();
		CLASSMANAGELIST.setTableHeight();
		//重置更多筛选出来的筛选项
		$("#zsSelectSearch").val("0");
		$(".ser-out-span").hide();
	});	
};

//设置表格高度
CLASSMANAGELIST.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
};

/**
自定义显示姓名, 是否是重点, 是否是我的,  显示不同的图标. 
**/	
CLASSMANAGELIST.realnameRender=function (row, cell, value, columnDef, dataContext){
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
CLASSMANAGELIST.totalClass=function (row, cell, value, columnDef, dataContext) {
	var  a=dataContext.sum/dataContext.totalClass;
	var s=  "<div class='y-process'><div class='inner'><div style='width:"+a*100+"%;'>" +
			"</div></div><p>"+dataContext.sum+"/"+ dataContext.totalClass+"</p> </div>";
	console.log(s);
	return s;
};

//每行输出的操作按钮. 
//value : 列的内容。 
//dataContext : 行数据对象。 
CLASSMANAGELIST.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',
	icon_stu_list='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="班级学员列表" onclick="ClassMember.opendialog('+dataContext.lqClassId+');"><i class="opti-icon Hui-iconfont">&#xe611;</i></a>',
	icon_look_class='',
	/*icon_look_class='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="班级详情" onclick="classInfoByClassId('+dataContext.lqClassId+')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>',
	*/icon_update_status='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="修改班级状态" onclick="CLASSMANAGELIST.updateClassStatus('+dataContext.lqClassId+');"><i class="opti-icon Hui-iconfont">&#xe6e3;</i></a>',
	icon_updateStatus_noclick='<a class="c-gray opt-incon-btn" href="javascript:void(0);" title="修改班级状态"><i class="opti-icon Hui-iconfont">&#xe6e3;</i></a>',
	icon_optset_class='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="创建班级记录" onclick="CLASSMANAGELIST.createClassRecord('+dataContext.lqClassId+')"><i class="opti-icon Hui-iconfont">&#xe70c;</i></a>',
	icon_optsetClass_noclick='<a class="c-gray opt-incon-btn" href="javascript:void(0);" title="创建班级记录"><i class="opti-icon Hui-iconfont">&#xe70c;</i></a>',
	icon_end='</div>';
	//根据行数据确定是否显示icon2.png
	if(dataContext.nstatus==1){
		//头
		s += icon_start+icon_stu_list+icon_look_class;
		if(gl_isHashRight(functionIds,68)){//有设置班级状态权限
			var flag=UpdateClassStatus.canUpdateClassStatus(dataContext.status);
			if(flag){
			    s+=icon_update_status;	
			}else{
				s+=icon_updateStatus_noclick;
			}
		}else{
			s+=icon_updateStatus_noclick;
		}
		if(gl_isHashRight(functionIds,66)){//是否具有创建班级跟踪记录(班级记录)的权限
			s+=icon_optset_class;	
		}else{
			s+=icon_optsetClass_noclick;
		}
		//尾
		s+=icon_end;
	}
	return s;
};

///更新选中的记录
CLASSMANAGELIST.updateRowHashMap=function ()
{
		if(CLASSMANAGELIST.isuserselect==false) 
		{
			CLASSMANAGELIST.isuserselect=true;
		  return ;
		}
	 //清空本页内有的记录
	 var griddata = CLASSMANAGELIST.Grid.getData();
	 for(var i=0;i<griddata.length;i++)
	 {
		if(CLASSMANAGELIST.selectClassId.get(griddata[i].lqClassId))
		{
		    //console.log("remove key:" +griddata[i].lqClassId);
		 	CLASSMANAGELIST.selectClassId.remove(griddata[i].lqClassId);
	 	} 
		
		if(CLASSMANAGELIST.selectStatus.get(griddata[i].status)){
			CLASSMANAGELIST.selectStatus.remove(griddata[i].status);	
		}
	 }
	 
	   var selectedRows = CLASSMANAGELIST.Grid.getSelectedRows();

	   for(var i=0;i<selectedRows.length;i++)
	   {
	     // 得到一行的数据
		var rowdata = CLASSMANAGELIST.Grid.getDataItem(selectedRows[i]);
		//将该行的lqClassId和status放入hashMap中
		CLASSMANAGELIST.selectClassId.put(rowdata.lqClassId,rowdata.lqClassId);
		CLASSMANAGELIST.selectStatus.put(rowdata.status,rowdata.status);
	   }
	     //更新选中的数量
	  $("#hasChoosed").html("已选:"+CLASSMANAGELIST.selectClassId.size());
	 //  console.log("hashmap data=="+CLASSMANAGELIST.selectClassId.keys().join(","));	   
};

//ajax提交-for获取表格数据(公用)
CLASSMANAGELIST.ajaxSubmit=function(dataParam,url){
	$.ajax({
		type: "POST",
		url:url,
		data:dataParam,
		dataType:"json",
		success: function(data) {
			if(data.recordcount<=0){
				if(dataParam.opType=="0"){
					layer.msg('您还没有标记“我关注的”班级，可从“全部”班级列表中选择标记“我关注的”班级',{time:86400000});//24小时后关闭
				}else if(dataParam.opType=="2"){
					layer.msg('您还没有标记“我管理的”班级，可从“全部”班级列表中选择标记“我关注的”班级',{time:86400000});//24小时后关闭
				}else{
					layer.closeAll();
				}
			}else{
				layer.closeAll();
			}
				//清除当前选择.
			CLASSMANAGELIST.isuserselect = false;
			CLASSMANAGELIST.Grid.setSelectedRows([]);
			  //填充表格数据
			CLASSMANAGELIST.Grid.setData(data.datalist);
			CLASSMANAGELIST.Grid.render();
			  //设置选中多行
			 var rows =  CLASSMANAGELIST.resolveSelectIndex();
			 CLASSMANAGELIST.Grid.setSelectedRows(rows);
				if(dataParam.currpage==1){
					//初始化分页-总记录数/每页条数
				    $("#sp_i").text("个");
					PageWiget.init(data.recordcount,PageWiget.getPageSize());
				    var pageMsg="当前年份新开班的班级数量为:"+data.newClassCount+"个";
			       $("#sp_otherpagemsg").text(pageMsg);
				} 
		}
	});
};

/**===============分页处理开始==============**/
CLASSMANAGELIST.dopagechange=function(pageno,pagesize){
	CLASSMANAGELIST.url=	CLASSMANAGELIST.basePath+"/lqClass/getClassManageList.do";
	CLASSMANAGELIST.queryParam.currpage=pageno;
	CLASSMANAGELIST.queryParam.pageSize=pagesize;
	CLASSMANAGELIST.queryParam.opType=$("#opType").val();
	CLASSMANAGELIST.ajaxSubmit(CLASSMANAGELIST.queryParam,CLASSMANAGELIST.url);
};
/**===============分页处理结束==============**/

//获取选中的rows
CLASSMANAGELIST.resolveSelectIndex=function(){
	 //获取选中的行rows数组
	  var datarows = CLASSMANAGELIST.Grid.getData();
	   var rows=new Array();
	   var selectuserid = CLASSMANAGELIST.selectClassId.keys();
	   
	   for(var i=0;i<datarows.length;i++){
		   for(var j=0;j<selectuserid.length;j++){
			    if(selectuserid[j] == datarows[i].lqClassId)
			    {
			    	rows.push(i);
			    	break;
			    }
			   
			 }
		 
		//console.log("rows="+rows.join(":"));
	   }
	   
	   return rows;
	   
};

/**==============查询按钮点击操作开始================**/
CLASSMANAGELIST.queryData=function(){
$(".query_lq").click(function(){
    if($(".much-more").is(":visible")){
		//1查询数据
		CLASSMANAGELIST.dataParam={
		  "classStatus":$("#classStatus").val(),
		  "courseType":$("#courseType").val(),
		  "js_teacher_userid":$("#js_teacher_userid").val(),
		  "broker_teacher_userid":$("#broker_teacher_userid").val(),
		  "cep_teacher_userid":$("#cep_teacher_userid").val(),
		  "opType":$("#opType").val(),
		  "currpage":1,
          "pageSize":PageWiget.getPageSize()
		};
		//2.保存分页查询参数
		CLASSMANAGELIST.queryParam={
		  "classStatus":$("#classStatus").val(),
		  "courseType":$("#courseType").val(),
		  "js_teacher_userid":$("#js_teacher_userid").val(),
		  "broker_teacher_userid":$("#broker_teacher_userid").val(),
		  "cep_teacher_userid":$("#cep_teacher_userid").val(),
		  "opType":$("#opType").val()
		}; 
    }else{
		//1查询数据
		CLASSMANAGELIST.dataParam={
		  "classStatus":$("#classStatus").val(),
		  "courseType":$("#courseType").val(),
		  "js_teacher_userid":$("#js_teacher_userid").val(),
		  "broker_teacher_userid":$("#broker_teacher_userid").val(),
		  "cep_teacher_userid":$("#cep_teacher_userid").val(),
		  "select_class_name":$("#select_class_name").val(),
		  "opType":$("#opType").val(),
		  "currpage":1,
          "pageSize":PageWiget.getPageSize()
		};
		//2保存分页查询参数
		CLASSMANAGELIST.queryParam={
		  "classStatus":$("#classStatus").val(),
		  "courseType":$("#courseType").val(),
		  "js_teacher_userid":$("#js_teacher_userid").val(),
		  "broker_teacher_userid":$("#broker_teacher_userid").val(),
		  "cep_teacher_userid":$("#cep_teacher_userid").val(),
		  "select_class_name":$("#select_class_name").val(),
		  "opType":$("#opType").val()	
		}; 
    }
    //3请求url
    CLASSMANAGELIST.url=CLASSMANAGELIST.basePath+"/lqClass/getClassManageList.do";
    //提交查询
	CLASSMANAGELIST.ajaxSubmit(CLASSMANAGELIST.dataParam,CLASSMANAGELIST.url);
});
};
/**==============查询按钮点击操作结束================**/

/**=============标记为我关注开始=============**/
CLASSMANAGELIST.remarkMyFocus=function(){
	$("#remarkMyFocus_lq").click(function(){
		var stids = CLASSMANAGELIST.selectClassId.keys();//将selectClassId Map转成数组
		if(stids.length>0){
			stids=stids.join(",");
			var ajUrl=CLASSMANAGELIST.basePath+"/lqClass/remarkMyFocus.do";
			var dataPa={"lqClassIds":stids};
			CLASSMANAGELIST.ajaxForRemark(dataPa,ajUrl);
		}else{
			layer.msg('请选择“标记为我关注”的班级');
		}
	});
};
/**=============标记为我关注结束=============**/
/**=============取消关注开始================**/
CLASSMANAGELIST.cancelFocus=function(){
	$("#cancelFocus_lq").click(function(){
		var stids = CLASSMANAGELIST.selectClassId.keys();
		if(stids.length>0){
			stids=stids.join(",");
			var ajUrl=CLASSMANAGELIST.basePath+"/lqClass/cancelFocus.do";
			var dataPa={"lqClassIds":stids};
			CLASSMANAGELIST.ajaxForRemark(dataPa,ajUrl);
		}else{
			layer.msg('请选择“取消关注”的班级');
		}
	});//click end
};
/**=============取消关注结束================**/

/**==========ajax提交for标记关注/取消关注开始===========**/
CLASSMANAGELIST.ajaxForRemark=function(data,url){
	$.ajax({
	type: "POST",
	url:url,
	data:data,
	dataType:"json",
	success: function(data) {
		//删除选择项列表.
		CLASSMANAGELIST.selectClassId.removeAll();
		CLASSMANAGELIST.selectStatus.removeAll();
		layer.msg(data.message);
       if(data.code==200){
    		CLASSMANAGELIST.url=CLASSMANAGELIST.basePath+"/lqClass/"+$("#opType").val()+"/goClassManageList.do";
    	   //重新刷新页面
    	   CLASSMANAGELIST.jumpUrl({},CLASSMANAGELIST.url);   
       }
	}
});
};
/**==========ajax提交for标记关注/取消关注结束===========**/
/**==========跳转到合并班级页面开始=================**/
CLASSMANAGELIST.jumpMergeClass=function(){
	$("#mergeClass_lq").click(function(){
		var userIds=CLASSMANAGELIST.selectClassId.keys();
		var statuss=CLASSMANAGELIST.selectStatus.keys();
		var flag=true;
		if(userIds.length>0){
			for(var i=0;i<statuss.length;i++){
				if(!CLASSMANAGELIST.canEnterIn(statuss[i])){
					flag=false;
					break;
				}
			}
			if(flag){
				var lqClassIds=userIds.join(",");
				//console.log("lqClassIds="+lqClassIds);
				CLASSMANAGELIST.jumpUrl({"ids":lqClassIds,"opType":$("#opType").val()},CLASSMANAGELIST.basePath+'/lqClass/goMergerClass.do');	
			}else{
				layer.open({
					 title:'提示',
					  content: '状态为未开课/授课中/集训前结课/集训中的班级才可合并，请核对您所选择的班级状态',
					  yes: function(index, layero){
						//do something
						layer.close(index); //如果设定了yes回调，需进行手工关闭
					  }
					});
			}

		}else{
			layer.open({
				 title:'提示',
				  content: '请选择拟合并班级',
				  yes: function(index, layero){
					//do something
					layer.close(index); //如果设定了yes回调，需进行手工关闭
				  }
				});
		};

	});	
};
/**==========跳转到合并班级页面结束=================**/

/**==========判断是否可合并班级开始==================**/
CLASSMANAGELIST.canEnterIn=function(current_status){
	var bret =false;
	if(current_status == gl_classStatus.NOCLASSES 
			||current_status == gl_classStatus.INTHELECTURE 
			||current_status == gl_classStatus.BEFORETHETRAININGSESSION 
			||current_status == gl_classStatus.INTHETRAINING 
			)
		bret =true;
	return bret;
};
/**==========判断是否可合并班级结束==================**/
/**============修改班级状态开始================**/
CLASSMANAGELIST.updateClassStatus=function(classid){
UpdateClassStatus.opendialog(classid,function(){
	CLASSMANAGELIST.jumpUrl({},CLASSMANAGELIST.basePath+"/lqClass/"+$("#opType").val()+"/goClassManageList.do");		
});	
};
/**============修改班级状态结束================**/
/**============创建班级记录开始================**/
CLASSMANAGELIST.createClassRecord=function(lqClassId){
	CLASSRECORDDIOLOG.opendialog(lqClassId,function(){
	   CLASSMANAGELIST.jumpUrl({},CLASSMANAGELIST.basePath+"/lqClass/"+$("#opType").val()+"/goClassManageList.do");	
	});
};
/**============创建班级记录结束================**/

/**========ajax请求获取表格数据开始==========**/
CLASSMANAGELIST.getTableData=function(){
	CLASSMANAGELIST.dataParam={
            "opType":$("#opType").val(),
            "currpage":1,
            "pageSize":PageWiget.getPageSize()
      };
CLASSMANAGELIST.url=CLASSMANAGELIST.basePath+"/lqClass/getClassManageList.do";
CLASSMANAGELIST.ajaxSubmit(CLASSMANAGELIST.dataParam,CLASSMANAGELIST.url);
};
/**========ajax请求获取表格数据结束==========**/
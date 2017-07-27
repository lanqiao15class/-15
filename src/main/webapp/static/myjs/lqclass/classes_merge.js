CLASSES_MERGE = function(){};

//获取上下文路径
CLASSES_MERGE.basePath = basePath;

//表格对象
CLASSES_MERGE.Grid= null;
CLASSES_MERGE.columns = [];
CLASSES_MERGE.classArray = null;

//班级选取对象
CLASSES_MERGE.select_class_classMerge = null;

//被合并的班级；列表的学员总人数（不包含目标班级）
CLASSES_MERGE.studentCountList = 0;

//初始化加载
/**=====================================init=============================================== */
CLASSES_MERGE.init = function(){
	
	var opType = $("#opType").val();//入口是哪个列表页面
	
	//1,点击选择班级事件
//	$("#classSelect").on("change",function(){
//		CLASSES_MERGE.getClassDetail();
//	});
	
	//2,新建班级
	$("#newClassClassMerge").on("click",function(){
		CLASSES_MERGE.loadPage();
	});
	
	//3,刷新班级列表
	$("#refreshClassBtn").on("click",function(){
		CLASSES_MERGE.refreshClass(type);
	});
	
	//4，返回重选班级
	$("#returnClassList").on("click",function(){
		CLASSES_MERGE.backToList(opType);
	});
	
	//5，新加的班级选取下拉框
	$.ajax({
		type:"post",
		url:basePath+"/student/classForStuIntoClass.do",
		data:{"type":$("#type").val()},
		dataType:"json",
		success:function(rdata){
			CLASSES_MERGE.select_class_classMerge = new selectableExtend({
				inputid:"classSelect",
				placeholder:"请选择班级",
				afterSelectItem:CLASSES_MERGE.getClassDetail,
				data:rdata});
			CLASSES_MERGE.select_class_classMerge.init();
		}
		
	});
	
	
	//学员列表
/**================================表格开始======================================*/
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

	//checkbox 列的定义. 
	var checkboxSelector = new Slick.CheckboxSelectColumn({
		cssClass: "slick-cell-checkboxsel"
	});

	//定义表格的字段
	var columns = [];
//	columns.push(checkboxSelector.getColumnDefinition()); //checkbox 列

	columns.push({
		id: "indexno",  // 唯一标识
		name: "序号",// 字段名字
		field: "indexno", // 显示数据中的某一个字段.对应到数据对象中的属性名
		width: 120		//宽度.

	});

	columns.push({
		id: "className", 
		name: "班级名称",
		field: "className", 
		width: 150
	});
	
	columns.push({
		id: "courseType", 
		name: "课程类别",
		field: "courseType", 
		width: 150,
		formatter:CLASSES_MERGE.colorRender
	});
	
	columns.push({
		id: "status", 
		name: "状态",
		field: "status", 
		width: 150
	});
		
	columns.push({
		id: "studentCount", 
		name: "当前人数",
		field: "studentCount", 
		width: 150
	});

	columns.push({
		id: "option", 
		name: "操作",
		field: "option", 
		cssClass:"slick-cell-action",
		width: 200,
		formatter:CLASSES_MERGE.actionRender
	});
	
	CLASSES_MERGE.columns = columns;
	//产生grid 控件
	CLASSES_MERGE.Grid = new Slick.Grid("#myGridClass",[], columns, options);

	CLASSES_MERGE.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	CLASSES_MERGE.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	CLASSES_MERGE.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	CLASSES_MERGE.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		 var selectedRows = CLASSES_MERGE.Grid.getSelectedRows();
//		 console.log("选择了:" + selectedRows.length +"条");
		 
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 

	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	
	CLASSES_MERGE.ajaxSubmit({"ids":$("#ids").val()},basePath+"/lqClass/getClassListData.do");
/**================================表格结束======================================*/
	
	
	
};
/**=====================================init=============================================== */

/**======================================== 跳转界面========================================== */
CLASSES_MERGE.jumpUrl = function(data,url){
	$.ajax({
        type:"post",
        url:CLASSES_MERGE.basePath+url,
        data:data,
        dataType:"html",
        success:function(html){
        	$("#contentDiv").html(html);
        },
        error:function(err) {
        }
    });
};
/**========================================  跳转界面========================================== */



/** =====================================切换班级下拉框，获取班级详情 =========================================*/
CLASSES_MERGE.getClassDetail = function(){
	var classId = $("#classSelect").val();
	if(classId == ""){//清空span
		$("#classDetailDiv").find(".info-span").find("span").text("");
		//清空班级，人数还原为当前列表人数（可能是做了删除等操作后）
		$("#studentCountAll").text(CLASSES_MERGE.studentCountList);
		//去除渲染的课程类型红色样式
		CLASSES_MERGE.Grid.setData(CLASSES_MERGE.Grid.getData());
		CLASSES_MERGE.Grid.render();
		return;
	}
	$.ajax({
		type:"post",
		url:basePath+"/student/classDetail.do",
		data:{"classId":classId},
		dataType:"json",
		success:function(data){
			var classData = data.lqClass;//注意data和classData的区分
			$("#className").text(classData.className);
			$("#classType").text(data.classType);
			$("#courseType").text(data.courseType);
			$("#startTime").html(zzx_getLocalTime(classData.expectStarttime,'A'));
			$("#factStartTime").html(zzx_getLocalTime(classData.startTime,'A'));
			$("#graduationTime").html(zzx_getLocalTime(classData.expectGraduateTime,'A'));
			
			$("#comTeacher").text(data.comTeacher);
			$("#chrTeacher").text(data.chrTeacher);
			$("#cepTeacher").text(data.cepTeacher);
			
			//重新渲染表格，不一致课程带上颜色
			CLASSES_MERGE.Grid.setData(CLASSES_MERGE.Grid.getData());
			CLASSES_MERGE.Grid.render();
			
			//更新合并后班级总人数
			var ids = $("#ids").val().split(",");
			if(ids.indexOf(classId) == -1){//如果列表中不存在此班级，则更新合并后班级人数
				$("#studentCountAll").text(CLASSES_MERGE.studentCountList + parseInt(data.studentCount));
			}else{//如有重复，则填写列表人数即可
				$("#studentCountAll").text(CLASSES_MERGE.studentCountList);
			}
			
		}
	});
};
//时间戳转化为时间
function zzx_getLocalTime(nS,type) {     
	if(nS == null)
		return "暂无数据";
	var date = new Date(nS);
	Y = date.getFullYear() + '-';
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	D = (date.getDate()<10?'0'+date.getDate():date.getDate()) + ' ';
	h = (date.getHours()<10?'0'+date.getHours():date.getHours()) + ':';
	m = (date.getMinutes()<10?'0'+date.getMinutes():date.getMinutes()) + ':';
	s = date.getSeconds()<10?'0'+date.getSeconds():date.getSeconds(); 
	if(type == 'A') 
		return Y+M+D;
	else
		return Y+M+D+h+m+s;
 }

/** 红色高显示效果 */
CLASSES_MERGE.colorRender=function (row, cell, value, columnDef, dataContext) {
	var classCourseType = $("#courseType").text();
	var s= "";
	if($.trim(classCourseType).length > 0 && dataContext.courseType != classCourseType){
		s = "<span style='color:red;'>"+value+"</span>";
	}
	else{
		s = "<span style='color:black;'>"+value+"</span>";
	}
	return s;
};
/** =====================================切换班级下拉框，获取班级详情结束 =========================================*/


/**====================================加载弹窗==============================================**/
CLASSES_MERGE.loadPage = function(){
	var url = CLASSES_MERGE.basePath+"/student/toAddClass.do";
	$.ajax({
		  type:"post",
		  url:url,
		  data:{},
		  dataType:"html",
		  success: function(html){
			  CLASSES_MERGE.showDialog(html);
		  }
	  });
};
/**====================================加载弹窗===============================================**/

/**====================================显示弹窗=============================================== */
CLASSES_MERGE.showDialog = function(html){
	layer.open({
		  type: 1, 
		  title: ['新建班级'],
		  skin: 'demo-class',
		  shade: 0.6,
		  area: ['520px','652px'],
		  anim:8,
		  content: html //这里content是一个普通的String
		});
};
/**=====================================显示弹窗============================================== */

/**=====================================刷新班级(暂时无用)============================================== */
CLASSES_MERGE.refreshClass = function(type){
	$.ajax({
		type:"post",
		url:basePath+"/student/refreshClass.do",
		data:{"type":type},
		dataType:"json",
		success:function(data){
			if(data.code == 200){
				var list = data.classList;
//				alert(!list);
				var html = "<option value='-1'>选择班级</option>";
				if(list){
					for ( var i = 0; i < list.length; i++) {
						html += "<option value='"+list[i].classId+"'>"+list[i].className+"</option>";
					}
					$(".intro-class-part.info").text("");//清空班级详情
					$("#classSelect").html(html);
					layer.msg("班级添加成功,列表已刷新");
				}
			}else{
				layer.msg("班级添加成功,列表刷新失败");
			}
		}
		
		
	});
	
};
/**=====================================刷新班级（暂时无用）============================================== */


//每行输出的操作按钮. 
//value : 列的内容。 
//dataContext : 行数据对象。 
	//自定义显示列
CLASSES_MERGE.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',
	icon_del='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="删除" onclick="CLASSES_MERGE.deleteClass(this,'+dataContext.classId+','+dataContext.studentCount+');"><i class="opti-icon Hui-iconfont">&#xe609;</i></a>',
	icon_end='</div>';
		s += icon_start+icon_del+icon_end;
	return s;
};

/**=====================================获取表格数据===================================================*/
//ajax提交-for获取表格数据(公用)
CLASSES_MERGE.ajaxSubmit=function(data,url){
	$.ajax({
		type: "POST",
		url:url,
		data:data,
		dataType:"json",
		success: function(rdata) {
			  //填充表格数据
			  CLASSES_MERGE.Grid.setData(rdata.list);
			  CLASSES_MERGE.Grid.render();
			  //设置班级个数和学员人数
			  $("#classCount").text(rdata.classCount);
			  $("#studentCountAll").text(rdata.studentCountAll);
			  CLASSES_MERGE.studentCountList = rdata.studentCountAll;//设置初始列表人数
		}
	});
	
};
/**========================================获取表格数据结束================================================*/

/**===========================================删除班级===================================================*/
CLASSES_MERGE.deleteClass = function(obj,classId,studentCount){
	layer.confirm('确定删除吗？', {
	    btn: ['确定','取消'], //按钮
	    shade: false //不显示遮罩
	}, function(index){
		//1,删除该行
		CLASSES_MERGE.chen_deleteRow(classId);
		
		//2，更新ids
		var ids = $("#ids").val();
		var idsArray = ids.split(",");
		var index = idsArray.indexOf(classId+"");
		if (index > -1) {
			idsArray.splice(index, 1);
			var newIds = idsArray.join(",");
			$("#ids").val(newIds);
		}
		
		//3，处理序号、空位（TODO）
		
		//4，处理班级总数和班级人数
		$("#classCount").text(parseInt($("#classCount").text())-1);
		$("#studentCountAll").text(parseInt($("#studentCountAll").text())-parseInt(studentCount));
		CLASSES_MERGE.studentCountList -=  parseInt(studentCount);//更新列表学员人数
	    layer.closeAll();
	});
	
	
};

//删除行操作
CLASSES_MERGE.chen_deleteRow = function(classId)
{
	var rowindex =-1;
	
	var tdata = CLASSES_MERGE.Grid.getData();
	//查找到记录. 得到序号.
	for(var i=0;i<tdata.length;i++)
	{
		if(tdata[i].classId == classId)
		{	
			rowindex =i;
			break;
		}
	}
	 tdata.splice(rowindex ,1);
	
	 //重新排序
	 for(var i=0;i<tdata.length;i++)
		{
		  tdata[i].indexno=i+1;	
		}
	 
	 CLASSES_MERGE.Grid.setData(tdata);
	 CLASSES_MERGE.Grid.render();
	
}

/**===========================================删除班级====================================================*/


/** 加载确认弹窗 */
CLASSES_MERGE.loadPageConfirm = function(){
	//验证数据完整性
	var newClassId = $("#classSelect").val();
	var ids = $("#ids").val();
	if(newClassId == ""){
		layer.msg("尚未选择合并入的班级");
		return;
	}
	if($.trim(ids).length == 0){
		layer.msg("尚未选择需要被合并的班级");
		return;
	}
	//加载弹窗
	var opType = $("#opType").val();
	var data = {"newClassId":newClassId,"ids":ids,"opType":opType};
	$.ajax({
		type:"post",
		url:basePath+"/lqClass/confirmDialog.do",
		data:data,
		dataType:"html",
		success:function(html){
			CLASSES_MERGE.showDialogConfirm(html);
		}
	});
};

/** 显示确认弹窗 */
CLASSES_MERGE.showDialogConfirm = function(html){
	layer.open({
		  type: 1, 
		  title: ['确认合并班级'],
		  skin: 'demo-class',
		  shade: 0.6,
		  area: '520px',
		  anim:8,
		  content: html //这里content是一个普通的String
		});
};


/** 确认合班,回到列表 */
CLASSES_MERGE.submitMergeClass = function(){
	CLASSES_MERGE.classArray=$("#ids").val().split(",");
	var myrule = {};
	var mymessage = {};
	for(var i=0;i<CLASSES_MERGE.classArray.length;i++)
	{
		var sid = "class"+CLASSES_MERGE.classArray[i];
		
		myrule[sid]={};
		myrule[sid].required=true;
		myrule[sid].digits=true;
		
		mymessage[sid]={};
		mymessage[sid].required="请输入已完成课时数量";
		mymessage[sid].digits="输入必须为数字";
		
	}
	
	var otherRules={
			  happenTime:{
		  			required : true
					},
				remark:{
					required:true,
					maxlength:1000
				}
	};
	
	var otherMessage={
			happenTime:{
	  			required : "请设置合并时间"
				},
				remark:{
					required:"请填写备注",
					maxlength:"输入限制在1000个字符"
				}
	};
	
	myrule = $.extend(otherRules, myrule);
	mymessage = $.extend(otherMessage,mymessage);
	//提交validate验证
	$("#mergeClassform").validate({
		  ignore:"",
		  onfocusout: function(element) { $(element).valid(); },
		  onfocusin: function(element) { $(element).valid(); },
		  rules:myrule,
		  messages:mymessage,
		  errorCallback:function(element){
		  },
		  submitHandler:function(){
			  //提交表单//
			  CLASSES_MERGE.mergeClass();
			  
		  },
		  errorPlacement : function(error, element) {
				element.parents('.line-item-ver').find('.error-tips').html("");
				error.appendTo(element.parents('.line-item-ver').find('.error-tips'));
			}
		    
	  });
	
};

/** 返回列表 */
CLASSES_MERGE.backToList = function(opType){
	var  url = "/lqClass/"+opType+"/goClassManageList.do";
	$.ajax({
		type:"post",
		url:basePath+url,
		data:{},
		dataType:"html",
		success:function(html){
			$("#contentDiv").html(html);
		}
	});
};

/**====================提交表单================================*/
CLASSES_MERGE.mergeClass = function(){
	var ids = $("#ids").val();
	var opType = $("#opType").val();
	var newClassId = $("#classSelect").val();
	var data = $("#mergeClassform").serialize();
	data += "&ids="+ids+"&opType="+opType+"&newClassId="+newClassId;
	console.log(data);
	//2，提交
	$.ajax({
		type:"post",
		url:basePath+"/lqClass/mergeClass.do",
		data:data,
		dataType:"json",
		success:function(rdata){
			if(rdata.code == 200){
				//跳转到对应的列表
				$("#mergeClassDialogReturn").trigger("click");
				CLASSES_MERGE.backToList(opType);
				layer.msg("合并班级完成");
			}else{
				layer.msg("系统异常");
			}
		}
		
		
	});
	
};


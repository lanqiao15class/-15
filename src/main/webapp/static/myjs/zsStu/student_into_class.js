STUDENT_INTO_CLASS = function(){};

//获取上下文路径
STUDENT_INTO_CLASS.basePath = basePath;

//班级选取对象
STUDENT_INTO_CLASS.select_class = null;

//表格对象
STUDENT_INTO_CLASS.Grid= null;
STUDENT_INTO_CLASS.columns = [];

//初始化加载
/**=====================================init=============================================== */

STUDENT_INTO_CLASS.init = function(){
	var type = $("#type").val();//分班或者转班【newIntoClass，changeClass】
	var opType = $("#opType").val();//列表页面
	
	//1,点击选择班级事件
//	$("#classSelect").on("change",function(){
//		STUDENT_INTO_CLASS.getClassDetail();
//	});
	
	//2,新建班级
	$("#newClass").on("click",function(){
		STUDENT_INTO_CLASS.loadPage();
	});
	
	//3,刷新班级列表
	$("#refreshClassBtn").on("click",function(){
		STUDENT_INTO_CLASS.refreshClass(type);
	});
	
	//4，返回重新选择学员
	$("#returnStudentList").on("click",function(){
		STUDENT_INTO_CLASS.backToList();
	});

	
	//5,提交分班,到弹窗
	$("#intoClassSubmitBtn").on("click",function(){
		var newClassId = $("#classSelect").val();
		var ids = $("#ids").val();
		if(newClassId == ""){
			layer.msg("尚未选择班级");
			return;
		}
		if($.trim(ids).length == 0){
			layer.msg("尚未选择学员");
			return;
		}
		STUDENT_INTO_CLASS.loadPageConfirm();
	});
	
	//6,页面文字，区分是转班和分班
	if(type == "newIntoClass"){
		$("#span1").text("新学员分班");$("#span2").text("确认分班信息");$("#stuIntoClassTips").text("确认分班");
	}else{
		$("#span1").text("学员转班");$("#span2").text("确认转班信息");$("#stuIntoClassTips").text("确认转班");
	}
	
	
	//7，新加的班级选取下拉框
	$.ajax({
		type:"post",
		url:basePath+"/student/classForStuIntoClass.do",
		data:{"type":$("#type").val()},
		dataType:"json",
		success:function(rdata){
			STUDENT_INTO_CLASS.select_class = new selectableExtend({
				inputid:"classSelect",
				placeholder:"请选择班级",
				afterSelectItem:STUDENT_INTO_CLASS.getClassDetail,
				data:rdata});
			STUDENT_INTO_CLASS.select_class.init();
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
		id: "realName", 
		name: "姓名",
		field: "realName", 
		width: 200
	});

	if(type == "changeClass")//转班界面显示
		columns.push({
			id: "className", 
			name: "班级名称",
			field: "className", 
			width: 150
		});
	
	columns.push({
		id: "courseType", 
		name: "课程类型",
		field: "courseType", 
		width: 150,
		formatter:STUDENT_INTO_CLASS.colorRender
	});

	columns.push({
		id: "univName", 
		name: "所在院校",
		field: "univName", 
		width: 150
	});
	
	if(type == "changeClass")//转班界面显示
		columns.push({
			id: "status", 
			name: "状态",
			field: "status", 
			width: 150
		});
		
	if(type == "newIntoClass")//分班提示
	columns.push({
		id: "major", 
		name: "专业",
		field: "major", 
		width: 150
	});


	if(type == "newIntoClass")//分班提示
	columns.push({
		id: "invTeacherName", 
		name: "招生老师",
		field: "invTeacherName", 
		width: 150
	});

	columns.push({
		id: "option", 
		name: "操作",
		field: "option", 
		cssClass:"slick-cell-action",
		width: 200,
		formatter:STUDENT_INTO_CLASS.actionRender
	});
	
	STUDENT_INTO_CLASS.columns = columns;
	//产生grid 控件
	STUDENT_INTO_CLASS.Grid = new Slick.Grid("#myGridclass",[], columns, options);

	STUDENT_INTO_CLASS.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	STUDENT_INTO_CLASS.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	STUDENT_INTO_CLASS.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	STUDENT_INTO_CLASS.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		 var selectedRows = STUDENT_INTO_CLASS.Grid.getSelectedRows();
//		 console.log("选择了:" + selectedRows.length +"条");
		 
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 

	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	
	STUDENT_INTO_CLASS.ajaxSubmit({"ids":$("#ids").val()},basePath+"/student/getStudentIntoClassList.do");
/**================================表格结束======================================*/
	
	
	
};
/**=====================================init=============================================== */

/**======================================== 跳转界面========================================== */
STUDENT_INTO_CLASS.jumpUrl = function(data,url){
	$.ajax({
        type:"post",
        url:STUDENT_INTO_CLASS.basePath+url,
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
STUDENT_INTO_CLASS.getClassDetail = function(){
	var classId = $("#classSelect").val();
	if(classId == ""){//清空span
		$(".intro-class-part.info").text("");
		//去除课程红色提示
		STUDENT_INTO_CLASS.Grid.setData( STUDENT_INTO_CLASS.Grid.getData());
		STUDENT_INTO_CLASS.Grid.render();
		return;
	}
	$.ajax({
		type:"post",
		url:basePath+"/student/classDetail.do",
		data:{"classId":classId},
		dataType:"json",
		success:function(data){
			var classData = data.lqClass;
			$("#classType").text(data.classType);
			$("#courseType").text(data.courseType);
			$("#startTime").html(zzx_getLocalTime(classData.expectStarttime,'A'));
			$("#factStartTime").html(zzx_getLocalTime(classData.startTime,'A'));
			$("#graduationTime").html(zzx_getLocalTime(classData.expectGraduateTime,'A'));
			
			$("#factGraduationTime").html(zzx_getLocalTime(classData.graduateTime,'A'));
			$("#QQ").text(classData.qqGroup);
			$("#comTeacher").text(data.comTeacher);
			$("#chrTeacher").text(data.chrTeacher);
			$("#cepTeacher").text(data.cepTeacher);
			
			$("#schoolTime1").html(zzx_getLocalTime(classData.expectSchoolEndtime,'A'));
			$("#monitor").text(data.monitor);
			$("#createTime").html(zzx_getLocalTime(classData.createTime,'B'));
			$("#closeTime").html(zzx_getLocalTime(classData.closeTime,'B'));
			 
			//重新渲染表格，不一致课程带上颜色
			STUDENT_INTO_CLASS.Grid.setData( STUDENT_INTO_CLASS.Grid.getData());
			STUDENT_INTO_CLASS.Grid.render();
			
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
/** =====================================切换班级下拉框，获取班级详情结束 =========================================*/


/**====================================加载弹窗==============================================**/
STUDENT_INTO_CLASS.loadPage = function(){
	var url = STUDENT_INTO_CLASS.basePath+"/student/toAddClass.do";
	$.ajax({
		  type:"post",
		  url:url,
		  data:{},
		  dataType:"html",
		  success: function(html){
			  STUDENT_INTO_CLASS.showDialog(html);
		  }
	  });
};
/**====================================加载弹窗===============================================**/

/**====================================显示弹窗=============================================== */
STUDENT_INTO_CLASS.showDialog = function(html){
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
STUDENT_INTO_CLASS.refreshClass = function(type){
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
STUDENT_INTO_CLASS.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',
	icon_del='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="删除" onclick="CHECK_STUDENT_INFO.deleteStu(this,'+dataContext.userId+');"><i class="opti-icon Hui-iconfont">&#xe609;</i></a>',
	icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="查看" onclick="stuInfoDialogue('+dataContext.userId+')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>',
	icon_end='</div>';
	s += icon_start+icon_look+icon_del+icon_end;
	return s;
};

/** 红色高显示效果 */
STUDENT_INTO_CLASS.colorRender=function (row, cell, value, columnDef, dataContext) {
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

/**=====================================获取表格数据===================================================*/
//ajax提交-for获取表格数据(公用)
STUDENT_INTO_CLASS.ajaxSubmit=function(data,url){
	$.ajax({
		type: "POST",
		url:url,
		data:data,
		dataType:"json",
		success: function(rdata) {
			console.log(rdata);
			  //填充表格数据
			  STUDENT_INTO_CLASS.Grid.setData(rdata.datalist);
			  STUDENT_INTO_CLASS.Grid.render();
		}
	});
	
};
/**========================================获取表格数据结束================================================*/

/**===========================================删除学员===================================================*/
CHECK_STUDENT_INFO.deleteStu = function(obj,stuId){
	layer.confirm('确定删除吗？', {
	    btn: ['确定','取消'], //按钮
	    shade: false //不显示遮罩
	}, function(index){
		//1,删除该行
//		_this=$(obj).parents(".slick-row");
//		_this.remove();
		CHECK_STUDENT_INFO.chen_deleteRow(stuId);
		
		//2，更新ids
		var ids = $("#ids").val();
		var idsArray = ids.split(",");
		var index = idsArray.indexOf(stuId+"");
		if (index > -1) {
			idsArray.splice(index, 1);
			var newIds = idsArray.join(",");
			$("#ids").val(newIds);
		}
		
		//3，处理序号、空位（TODO）
		
	    layer.closeAll();
	});
	
	
};

//删除行操作
CHECK_STUDENT_INFO.chen_deleteRow = function(uid)
{
	var rowindex =-1;
	
	var tdata = STUDENT_INTO_CLASS.Grid.getData();
	//查找到记录. 得到序号.
	for(i=0;i<tdata.length;i++)
	{
		//console.log("userid:"+tdata[i].userid);
		if(tdata[i].userId == uid)
		{	
			rowindex =i;
			break;
		}
	}
	//console.log("after deleterow rowindex " + rowindex +"uid=" + uid);
	 tdata.splice(rowindex ,1);
	
	 //重新排序
	 for(i=0;i<tdata.length;i++)
		{
		  tdata[i].indexno=i+1;	
		}
	 
	 STUDENT_INTO_CLASS.Grid.setData(tdata);
	 STUDENT_INTO_CLASS.Grid.render();
	
}

/**===========================================删除学员====================================================*/


/** 加载确认弹窗 */
STUDENT_INTO_CLASS.loadPageConfirm = function(){
	var ids = $("#ids").val();
	var type = $("#type").val();
	var opType = $("#opType").val();
	var newClassId = $("#classSelect").val();
	var data = {"newClassId":newClassId,"ids":ids,"type":type,"opType":opType};
	$.ajax({
		type:"post",
		url:basePath+"/student/confirmDialog.do",
		data:data,
		dataType:"html",
		success:function(html){
			STUDENT_INTO_CLASS.showDialogConfirm(html);
		}
	});
};

/** 显示确认弹窗 */
STUDENT_INTO_CLASS.showDialogConfirm = function(html){
	var type = $("#type").val(),string = "提示";
	if(type == "newIntoClass")
		string = "确认分班";
	else {
		string = "确认转班";
	}
	layer.open({
		  type: 1, 
		  title: [string],
		  skin: 'demo-class',
		  shade: 0.6,
		  area: '520px',
		  anim:8,
		  content: html //这里content是一个普通的String
		});
};


/** 确认分班，转班,回到列表 */
STUDENT_INTO_CLASS.submitIntoClass = function(){
	var happentime = $("#happentime").val();
	var remark = $("#remark").val();
	//1，验证
	if($.trim(happentime).length == 0){
		layer.msg("请设置时间");
		return;
	}
	if($.trim(remark).length == 0){
		layer.msg("请输入备注");
		return;
	}else if($.trim(remark).length > 4000){
		layer.msg("输入限制在4000字符内");
		return;
	}
	
	var ids = $("#ids").val();
	var type = $("#type").val();
	var opType = $("#opType").val();
	var newClassId = $("#classSelect").val();
	var data = {"newClassId":newClassId,"ids":ids,"type":type,"opType":opType,"happentime":happentime,"remark":remark};
	//2，提交
	$.ajax({
		type:"post",
		url:basePath+"/student/studentIntoClass.do",
		data:data,
		dataType:"json",
		success:function(rdata){
			if(rdata.code == 200){
				$("#returnSuccessBtn").trigger("click");//关闭弹窗
				if(type == "newIntoClass")
					layer.msg("分班完成：【成功"+rdata.count[1]+"人】");
				else
					layer.msg("转班完成：【成功"+rdata.count[1]+"人，"+rdata.count[0]+"人原属本班，无需转班】");
				//跳转到对应的列表
				STUDENT_INTO_CLASS.backToList();
			}else if(data.code == 201){
				layer.msg("学员数据获取失败");
			}else{
				layer.msg("系统异常");
			}
		}
		
		
	});
	
};

/** 返回列表 */
STUDENT_INTO_CLASS.backToList = function(){
	var url = $("#backUrl").val();//由列表传过来，谁调用返回谁
	
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

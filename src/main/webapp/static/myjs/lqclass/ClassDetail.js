ClassDetail = function(){};
ClassDetail.queryParam={};
ClassDetail.Grid= null;
ClassDetail.columns = [];
ClassDetail.queryParam1={};
ClassDetail.Grid1= null;
ClassDetail.columns1 = [];
String.prototype.trim = function() { 
	  return this.replace(/(^\s*)|(\s*$)/g, ''); 
}; 
	
ClassDetail.classid=0;
ClassDetail.init=function(canEdit,_Classid)
{
	ClassDetail.classid=_Classid;
	$(".class-table-student").show();
	//tab切换
	$(".y-tabs a").click(function(){
		$(this).parent(".y-tabs").find("a").removeClass("act");
		$(this).addClass("act");
		$(".con-ggcbox1").hide();
		$(".con-ggcbox1").eq($(this).index()).show();
		if($(this).index()==2){
			var a=$("#myclassTable1").text();
			console.log(a+"11        22222222222"+(a==""));
			if(a==""){
				ClassDetail.initgrad();
				console.log(a+"        2222222222222222  "+ClassDetail.classid+"   222");
				//加载签到记录
				ClassDetail.dopagechange1();
			}
		}
		
		if($(this).index()==3){
			var a=$("#myclassTable2").text();
			console.log(a+"11        22222222222"+(a==""));
			if(a==""){
				ClassDetail.initgraddaily();
				console.log(a+"        2222222222222222  "+ClassDetail.classid+"   222");
				//每日一讲
				ClassDetail.dopagechange(1,20);
			}
		}
	});
	//查看更多按钮点击事件
	$(".sl-down-btn").on("click",function(){
		$(this).hide();
		$(this).parents(".con-ggcbox").find(".more-list-disply").slideDown(100);
		$(this).siblings(".sl-up-btn").show();
	});
	$(".sl-up-btn").on("click",function(){
		$(this).hide();
		$(this).parents(".con-ggcbox").find(".more-list-disply").slideUp(100);
		$(this).siblings(".sl-down-btn").show();
	});
	//点击编辑icon
	if(canEdit)
	{
			$(".able-edit").click(function(e){
				console.log("edit ...");
				e.stopPropagation();
				var _this=$(this).parents(".item");
				$(".eidt-span").hide();
				$(".read-span").show();
				_this.find(".read-span").hide();
				_this.find(".eidt-span").show();
				if(_this.find(".eidt-span").children("input").length>0){
					_this.find(".eidt-span").children("input").focus();
				}
				//1.编辑后赋值到文本框zzh
				if($(this).children("em").html().trim()=="点击填写"||$(this).children("em").html().trim()=="暂无"){
					$(this).next("span").children("input").val("");//为空					
				}else{
					$(this).next("span").children("input").val($(this).children("em").html().trim());//赋值
				}
			});
		}else
			{
				$("div.clearfix a.able-edit").removeClass("able-edit");
			}
	
	$(".eidt-span").click(function(e){
		e.stopPropagation();
	});
	
	//===========================文本框-失去焦点提交(qq群)==========================================
	$(".eidt-span input[type='text']").blur(function(e){
		e.stopPropagation();
		var _this=$(this).parents(".item");
		//1.非空验证
		if($(this).val()==null||$(this).val()==""){//为空
			$(".eidt-span").hide();
			$(".read-span").show();
			_this.find(".read-span").hide();
			_this.find(".eidt-span").show();
			if(_this.find(".eidt-span").children("input")){
				_this.find(".eidt-span").children("input").focus();
			}
			//非空验证zzh
			if(_this.find(".label-text1").html().replace("：","")=="技术老师"){
				_this.find(".error-tips").html("");//技术老师不进行提示
			}else if(_this.find(".label-text1").html().replace("：","")=="CEP老师"){
				_this.find(".error-tips").html("");//CEP不进行提示
			}else if(_this.find(".label-text1").html().replace("：","")=="职业经纪人"){
				_this.find(".error-tips").html("");//职业经济人不进行提示
			}else{
				_this.find(".error-tips").html("请输入"+_this.find(".label-text1").html().replace("：",""));
			}
		}else{//不为空
			_this=$(this).parents(".item");
			_this.find(".read-span").show();
			_this.find(".eidt-span").hide();
			//校园结课日期
			if(_this.find(".label-text1").html().replace("：","")=="校园结课日期"){
				$(".eidt-span").hide();
				$(".read-span").show();
				_this.find(".read-span").hide();
				_this.find(".eidt-span").show();
			}
			if(_this.find(".label-text1").html().replace("：","")=="拟校园结课日期"){
				$(".eidt-span").hide();
				$(".read-span").show();
				_this.find(".read-span").hide();
				_this.find(".eidt-span").show();
			}
			
			//1.清空提示
			_this.find(".error-tips").html("");
			//2.定义传递的参数
			var parameterName;
			var parameterValue;
			//3.获取当前文本框的id名称
			var textId=_this.find(".eidt-span").children("input").attr("id");
			//4.验证是否相等,相等则不执行修改
			if($("#"+textId).val().trim()==$("em[name="+textId+"]").html().trim()){
				return;
			}
			//4.1验证
			var textCheck=$("#"+textId).val();
			if(textId=="qqGroup"){//qq
				if(!/^[1-9]\d{4,12}$/.test(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("QQ号格式不正确");
					return;
				}
			}
			//5赋值
			parameterName=textId;
			parameterValue=$("#"+textId).val();
			//6.组装
			var data="lqClassId="+_Classid+"&"+parameterName+"="+parameterValue;
			//7.异步并回显
			$.ajax({
		    	 type:"post",
	             url:basePath+"/lqClass/editLqClassInfo.do",  
	             data:data,
	             dataType:"json",
	             success:function(data){
	         		if(data.code == 200){
	         			//8.
	         			$("em[name="+textId+"]").html(parameterValue);
         				layer.msg("修改成功");
	         		}else{
	          			layer.msg("修改失败");
	          		}
	             }
		    });
		}
	});
	$("body").click(function(e){
		$(".read-span").show();
		$(".eidt-span").hide();
	});
	//===========================================================================
		
	//===============================下拉框修改监听(班长)==========================================
	$('#monitorId').change(function(){
		//1.定义传递的参数
		var parameterName;
		var parameterValue;
		//2.获取选中的
		var monitorCode=$(this).children('option:selected').val();
		var monitorName=$(this).children('option:selected').text();
		//3.判断
// 		if(monitorName==$("em[name=monitorId]").html()||monitorCode==""){
// 			return;
// 		}else{
			parameterName="monitorId";
			parameterValue=$(this).children('option:selected').val();
// 		}
		//4.组装
		var data="lqClassId="+_Classid+"&"+parameterName+"="+parameterValue;
		//5.提交
		$.ajax({
    	  type:"post",
             url:basePath+"/lqClass/editLqClassInfo.do",  
             data:data,
             dataType:"json",
             success:function(data){
         		if(data.code == 200){
	         		$("em[name=monitorId]").html(monitorName);
         			layer.msg("修改成功");
         		}else{
          			layer.msg("修改失败");
          		}
             }
	    });
	});	
	//===============================下拉框修改监听(课程类别)==========================================
	$('#courseTypeCode').change(function(){
		//1.定义传递的参数
		var parameterName;
		var parameterValue;
		//2.获取选中的
		var courseTypeCode=$(this).children('option:selected').val();
		var courseTypeName=$(this).children('option:selected').text();
		//3.判断
		if(courseTypeName==$("em[name=courseTypeCode]").html()||courseTypeCode==""){
			return;
		}else{
			parameterName="courseTypeCode";
			parameterValue=$(this).children('option:selected').val();
		}
		//4.组装
		var data="lqClassId="+_Classid+"&"+parameterName+"="+parameterValue;
		//5.提交
		$.ajax({
    	  type:"post",
             url:basePath+"/lqClass/editLqClassInfo.do",  
             data:data,
             dataType:"json",
             success:function(data){
         		if(data.code == 200){
	         		$("em[name=courseTypeCode]").html(courseTypeName);
         			layer.msg("修改成功");
         		}else{
          			layer.msg("修改失败");
          		}
             }
    	});
	});
	//===============================下拉框修改监听(班级一级类)==========================================
	$('#typePre').change(function(){
		$("em[name=typeReal]").html("点击填写");
		$("em[name=typePre]").html($(this).children('option:selected').text());
		$.ajax({
    	  type:"post",
             url:basePath+"/student/getClassTypeReal.do",  
             data:{
            	 "typePre":$(this).children('option:selected').val()
             },
             dataType:"json",
             success:function(data){
         		if(data.code == 200){
	         		var data=data.list;
	         		var html="<option value=''> --请选择-- </option>";
         			for(var i=0;i<data.length;i++){
         				html+="<option value='"+data[i].value+"'>"+data[i].label+"</option>";
         			}
         			 $("#typeReal").html(html);
         		}else{
          			
          		}
             }
	    });
	});	
	//===============================下拉框修改监听(班级二级类)==========================================
	$('#typeReal').change(function(){
		var teal=$(this).children('option:selected').text();
		//1.组装
		var data="lqClassId="+_Classid+"&typePre="+$("#typePre").val()+"&typeReal="+$("#typeReal").val()+"";
		//2.提交
		$.ajax({
    	  type:"post",
             url:basePath+"/lqClass/editLqClassInfo.do",  
             data:data,
             dataType:"json",
             success:function(data){
         		if(data.code == 200){
	         		$("em[name=typeReal]").html(teal);
         			layer.msg("修改成功");
         		}else{
          			layer.msg("修改失败");
          		}
             }
	    });
	});	
};






/**================================表格开始======================================*/
	ClassDetail.initgrad=function(){
	ClassDetail.setTableHeight();
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
		width: 120,		//宽度.
		formatter : ClassMember.CommonRender

	});
columns.push({
		id : "stuNo",
		name : "学员编号",
		field : "stuNo",
		width : 200,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "realName",
		name : "学员姓名",
		field : "realName",
		width : 120,
		formatter : ClassMember.CommonRender
	});
columns.push({
		id : "sexname",
		name : "性别",
		field : "sexname",
		width : 100,
		formatter : ClassMember.CommonRender
	});
columns.push({
		id : "tel",
		name : "电话",
		field : "tel",
		width : 150,
		formatter : ClassMember.CommonRender
	});



	columns.push({
		id : "schoolName",
		name : "所在院校",
		field : "schoolName",
		width : 150,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id : "statusName",
		name : "状态",
		field : "statusName",
		width : 120,
		formatter : ClassMember.CommonRender
	});

	columns.push({
		id: "zc", 
		name: "出勤",
		field: "zc", 
		width: 200,
		formatter : ClassMember.CommonRender
	});


	columns.push({
		id: "cd", 
		name: "迟到",
		field: "cd", 
		width: 150,
		formatter : ClassMember.CommonRender
		//formatter:STUDENT_INTO_CLASS.colorRender
	});

	columns.push({
		id: "qj", 
		name: "请假",
		field: "qj", 
		width:250,
		formatter : ClassMember.CommonRender
	});
	
	columns.push({
		id: "kk", 
		name: "旷课",
		field: "kk", 
		width: 150,
		formatter : ClassMember.CommonRender
	});
	
	ClassDetail.columns = columns;
	//产生grid 控件
	ClassDetail.Grid = new Slick.Grid("#myclassTable1",[], columns, options);

	ClassDetail.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	ClassDetail.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	ClassDetail.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	ClassDetail.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		 var selectedRows = ClassDetail.Grid.getSelectedRows();
	 console.log("选择了:" + selectedRows.length +"条");
		 
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 

	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	
	//ClassDetail.ajaxSubmit({"ids":ClassDetail.id.substring(1)},basePath+"/ClassDetail/getUserByClassDetail.do");


	}
/**================================表格结束======================================*/
	

 /**===============分页处理开始==============**/
ClassDetail.dopagechange1=function(){

	ClassDetail.url=basePath+"/class/selectMemberStudent.do";
	ClassDetail.queryParam.classid=ClassDetail.classid;
	ClassDetail.queryParam.startTime=$("#startTime").val();
	ClassDetail.queryParam.endTime=$("#endTime").val();
	ClassDetail.queryParam.studentSign=$("#studentSign").val();
	//alert(JSON.stringify(ClassDetail.queryParam));
	ClassDetail.ajaxSubmit(ClassDetail.queryParam,ClassDetail.url);
};
/**===============分页处理结束==============**/

ClassMember.CommonRender = function(row, cell, value, columnDef, dataContext) {
	var s = value;
	if (dataContext.isgray ==true) 
	{
		s = "<span style='color:#cccccc'>" + value + "</span>"
	}
	return s;
};

/**=====================================获取表格数据===================================================*/
//ajax提交-for获取表格数据(公用)
ClassDetail.ajaxSubmit=function(data,url){
	$.ajax({
		type: "POST",
		url:url,
		data:data,
		dataType:"json",
		success: function(rdata) {
			console.log("    123123    "+JSON.stringify(rdata));
		  	//填充表格数据
		 	 ClassDetail.Grid.setData(rdata.datalist);
			ClassDetail.Grid.render();
			//调整高度
			var i=$(".grid-canvas").attr("width");
			//alert(i);
			$(".grid-canvas").css("width",i-8)
			
		}
	});
	
};
/**========================================获取表格数据结束================================================*/
//设置表格高度
ClassDetail.setTableHeight=function(){
	var dialogue_height = $(".content").height(), dialogue_width = $(document)
			.width()-50;
	var tableh= dialogue_height -170;
	$("#myclassTable1").css("height",tableh);

};	

//设置表格高度
ClassDetail.setTableHeight1=function(){
	var dialogue_height = $(".content").height(), dialogue_width = $(document)
			.width()-50;
	var tableh= dialogue_height -170;
	$("#myclassTable2").css("height",tableh);

};	




 /**===============分页处理开始==============**/
ClassDetail.dopagechange=function(pageno,pagesize){
	console.log(pageno+"   wwwww   "+pagesize);
	ClassDetail.url1=	basePath+"/class/studentDailTalk.do";
	ClassDetail.queryParam1.currpage=pageno;
	ClassDetail.queryParam1.pageSize==PageWiget.getPageSize();
	ClassDetail.queryParam1.classid=ClassDetail.classid;
	//alert(JSON.stringify(ClassDetail.queryParam));
	ClassDetail.ajaxSubmit1(ClassDetail.queryParam1,ClassDetail.url1);
};
/**===============分页处理结束==============**/


/**================================每日一讲表格开始======================================*/
	ClassDetail.initgraddaily=function(){
	ClassDetail.setTableHeight1();
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
		id: "ROWNO",  // 唯一标识
		name: "序号",// 字段名字
		field: "ROWNO", // 显示数据中的某一个字段.对应到数据对象中的属性名
		width: 120		//宽度.

	});

	columns.push({
		id: "studentname", 
		name: "学生姓名",
		field: "studentname", 
		width: 200
	});


	columns.push({
		id: "create_time", 
		name: "一讲时间",
		field: "create_time", 
		width: 150
		//formatter:STUDENT_INTO_CLASS.colorRender
	});

	columns.push({
		id: "dt_content", 
		name: "演讲主题",
		field: "dt_content", 
		width:250
	});
	
	columns.push({
		id: "techername", 
		name: "上课老师",
		field: "techername", 
		width: 150
	});
	columns.push({
		id: "dt_type", 
		name: "评价",
		field: "dt_type", 
		width: 150
	});
columns.push({
		id: "evaluation", 
		name: "评价内容",
		field: "evaluation", 
		width: 150
	});
	columns.push({
		id: "isvalidName", 
		name: "操作",
		field: "isvalidName", 
		//cssClass:"slick-cell-action",
		width: 150,
		formatter:ClassDetail.operation
	});
	
	ClassDetail.columns1 = columns;
	//产生grid 控件
	ClassDetail.Grid1 = new Slick.Grid("#myclassTable2",[], columns, options);

	ClassDetail.Grid1.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	ClassDetail.Grid1.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	ClassDetail.Grid1.registerPlugin(checkboxSelector);
	//检测表格选中事件
	ClassDetail.Grid1.onSelectedRowsChanged.subscribe(function (e, args) {
		 var selectedRows = ClassDetail.Grid1.getSelectedRows();
	 console.log("选择了:" + selectedRows.length +"条");
		 
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 

	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	
	//ClassDetail.ajaxSubmit({"ids":ClassDetail.id.substring(1)},basePath+"/ClassDetail/getUserByClassDetail.do");


	}
/**================================表格结束======================================*/
ClassDetail.operation = function(row, cell, value, columnDef, dataContext) {
				icon_start = '<div class="gird-box">',
				icon_look = '<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="修改"  onclick="ClassDetail.evaluation('
						+ dataContext.dt_id
						+ ')"><i class="opti-icon Hui-iconfont">&#xe6df;</i></a>',
				icon_editStatus = '<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="评价" onclick="ClassDetail.evaluation('
						+ dataContext.dt_id
						+ ')"><i class="opti-icon Hui-iconfont">&#xe622;</i></a>',
				icon_editStatusnoclick = '<a class="c-blue opt-incon-btn"  href="javascript:void(0);" onclick="ClassDetail.delstudentdailytalk('
						+ dataContext.dt_id
						+ ')" title="删除"><i class="opti-icon Hui-iconfont">&#xe60b;</i></a>',
				icon_end = '</div>';
				return icon_start+icon_look+icon_editStatus+icon_editStatusnoclick+icon_end;
};


ClassDetail.evaluation=function(id){
	$.ajax({
		type: "POST",
		url:basePath+"/class/studentDailTalkoption.do",
		data:"id="+id,
		dataType:"json",
		success: function(rdata) {
		
		}
	})

}
	/**=====================================获取表格数据===================================================*/
//ajax提交-for获取表格数据(公用)
ClassDetail.ajaxSubmit1=function(data,url){
	$.ajax({
		type: "POST",
		url:url,
		data:data,
		dataType:"json",
		success: function(rdata) {
			console.log("    123123    "+JSON.stringify(rdata));
		  	//填充表格数据
		 	 ClassDetail.Grid1.setData(rdata.datalist);
			ClassDetail.Grid1.render();
			if(ClassDetail.queryParam1.currpage==1){
				console.log(rdata.pageTotal+"    "+ ClassDetail.queryParam1.currpage);
			//显示分页
		    laypage({
		     skin: '#1bbc9b',
		      groups: 10 ,//连续显示分页数
		      cont: 'demo1', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
		      pages: rdata.pageTotal, //通过后台拿到的总页数
		      curr: ClassDetail.queryParam1.currpage , //当前页
		      jump: function(obj, first){ //触发分页后的回调
		        if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
		          ClassDetail.dopagechange(obj.curr,20);
		        }
		      }
		    });
			}
		
			//调整高度
			var i=$(".grid-canvas").attr("width");
			//alert(i);
			$(".grid-canvas").css("width",i-8)
			
		}
	});
	
};
/**========================================获取表格数据结束================================================*/	
	
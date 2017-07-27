FEE_SERVICE_PAGE=function(){};

//表格对象
FEE_SERVICE_PAGE.Grid= null;

//全局变量，设置费用服务类型
FEE_SERVICE_PAGE.serviceType = null;

//输入的费用的合理性（前后端都会进行合理性验证，先前端再后端）
FEE_SERVICE_PAGE.flag = true;

//费用不合理提示
FEE_SERVICE_PAGE.tips = null;

//原始表格数据
FEE_SERVICE_PAGE.data = null;

FEE_SERVICE_PAGE.init=function(){
	
	
	//1，表格
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
		id: "stuNo", 
		name: "学员编号",
		field: "stuNo", 
		width: 150
	});
	
	columns.push({
		id: "studentName", 
		name: "学员姓名",
		field: "studentName", 
		width: 150
	});
	
	columns.push({
		id: "schoolName", 
		name: "院校名称",
		field: "schoolName", 
		width: 150
	});

	columns.push({
		id: "status", 
		name: "状态",
		field: "status", 
		width: 150
	});
	/*********************三种业务表格显示差异处理**************************/
	columns.push({
		id: "htMoney", 
		name: "合同金额",
		field: "htMoney", 
		width: 150
	});
	
	columns.push({
		id: "jmMoney", 
		name: "累计减免金额",
		field: "jmMoney", 
		width: 150
	});
	
	if(FEE_SERVICE_PAGE.serviceType != "JIANMIAN")//减免不显示已交金额
	columns.push({
		id: "yjMoney", 
		name: "累计收费金额",
		field: "yjMoney", 
		width: 150
	});
	/***********************************************/
	
	columns.push({
		id: "option", 
		name: "操作",
		field: "option", 
		cssClass:"slick-cell-action",
		width: 200,
		formatter:FEE_SERVICE_PAGE.actionRender
	});
	
	FEE_SERVICE_PAGE.columns = columns;
	//产生grid 控件
	FEE_SERVICE_PAGE.Grid = new Slick.Grid("#myGridFeeStudent",[], columns, options);

	FEE_SERVICE_PAGE.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	FEE_SERVICE_PAGE.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	FEE_SERVICE_PAGE.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	FEE_SERVICE_PAGE.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		 var selectedRows = FEE_SERVICE_PAGE.Grid.getSelectedRows();
//		 console.log("选择了:" + selectedRows.length +"条");
		 
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 

	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	
	
/**================================表格结束======================================*/
	
	//2，切换费用类型
	$(":radio[name=feeType]").on("change",function(){
		FEE_SERVICE_PAGE.switchFeeType();
	});
	
	
	//3,点击保存
	$("#saveFeeServiceBtn").on("click",function(){
		FEE_SERVICE_PAGE.feeServiceValidate();
	});
	
	//4,设置监听，处理各个老师的错误提示不消失问题
	$("div[class=searchable-select-holder").bind('DOMNodeInserted', function(e) {
		if($(e.target).html().length > 0)
			$(e.target).parents('.line-item-ver').find('.error-tips').css({"opacity":"0"});
		else
			$(e.target).parents('.line-item-ver').find('.error-tips').css({"opacity":"1"});
	});
	
	//4,监听费用的合理性
	$("#moneyChange").on("change",function(){
		FEE_SERVICE_PAGE.isAvailable();
	});
	
	//5,默认checked第一个radio(页面上已控制)
//	$("input[type=radio][name=feeType]:eq(0)").trigger("click");
	
	//6，获取表单数据
	FEE_SERVICE_PAGE.ajaxSubmit({"ids":$("#ids").val(),"feeType":$(":radio[name=feeType]:checked").val()},basePath+"/income/getFeeServiceData.do");
};

/**
 * Description: 自定义显示列
 * 每行输出的操作按钮. 
 * value : 列的内容
 * dataContext : 行数据对象
 * 
 */
FEE_SERVICE_PAGE.actionRender=function (row, cell, value, columnDef, dataContext) {
	var s= "",
	icon_start='<div class="gird-box">',
	icon_del='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="删除" onclick="FEE_SERVICE_PAGE.deleteStu(this,'+dataContext.stuId+');"><i class="opti-icon Hui-iconfont">&#xe609;</i></a>',
	icon_end='</div>';
		s += icon_start+icon_del+icon_end;
	return s;
};


/**=====================================获取表格数据===================================================*/
//ajax提交-for获取表格数据(公用)
FEE_SERVICE_PAGE.ajaxSubmit=function(data,url){
	$.ajax({
		type: "POST",
		url:url,
		data:data,
		dataType:"json",
		success: function(rdata) {
			FEE_SERVICE_PAGE.data = rdata.dataList;//保存原始数据（切换金额时表格数据会改变）
			//填充表格数据
			FEE_SERVICE_PAGE.Grid.setData(rdata.dataList);
			FEE_SERVICE_PAGE.Grid.render();
			//设置人数
			$("#studentCount").text(rdata.studentCount);
		}
	});
	
};
/**========================================获取表格数据结束================================================*/


/**===========================================删除学员===================================================*/
FEE_SERVICE_PAGE.deleteStu = function(obj,stuId){
	layer.confirm('确定删除吗？', {
	    btn: ['确定','取消'], //按钮
	    shade: false //不显示遮罩
	}, function(index){
		//1,删除该行
		FEE_SERVICE_PAGE.chen_deleteRow(stuId);
		
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
		
		//4，处理班级人数
		$("#studentCount").text(parseInt($("#studentCount").text())-1);
		layer.msg('已删除', {icon: 1});
	});
	
	
};

//删除行操作
FEE_SERVICE_PAGE.chen_deleteRow = function(stuId)
{
	var rowindex =-1;
	
	var tdata = FEE_SERVICE_PAGE.Grid.getData();
	//查找到记录. 得到序号.
	for(var i=0;i<tdata.length;i++)
	{
		if(tdata[i].stuId == stuId)
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
	 
	 FEE_SERVICE_PAGE.Grid.setData(tdata);
	 FEE_SERVICE_PAGE.Grid.render();
	
};

/**===========================================删除学员====================================================*/

/** 切换费用类型 */
FEE_SERVICE_PAGE.switchFeeType = function(){
	var feeType = $("input[name=feeType]:checked").val();
	var ids = $("#resourceIds").val();
	$.ajax({
		type:"post",
		url:basePath+"/income/getFeeServiceData.do",
		data:{"feeType":feeType,"ids":ids},
		dataType:"json",
		success:function(rdata){
			if(rdata.code == 200){
				FEE_SERVICE_PAGE.data = rdata.dataList;//保存原始数据（切换金额时表格数据会改变）
				FEE_SERVICE_PAGE.Grid.setData(rdata.dataList);
				FEE_SERVICE_PAGE.Grid.render();
				//处理学员人数
				$("#studentCount").text(rdata.studentCount);
				//还原ids
				$("#ids").val($("#resourceIds").val());
				//清空表单，删除errortip
				if($("#feeServiceForm input[type='text']").length!=0){
					$("#feeServiceForm input[type='text']").val('');
				}
				if($("#feeServiceForm textarea").length!=0){
					$("#feeServiceForm textarea").val('');
				}
				if($("#feeServiceForm .select-search-box").length!=0){
					$(".searchable-select-close").click();
				}
				$(".error-tips").text("");
				
			}else if(rdata.code == 201){
				layer.msg("学员列表获取失败");
			}else{
				layer.msg("系统异常");
			}
		}
	});
	
};


/** 点击保存，进行验证 */
FEE_SERVICE_PAGE.feeServiceValidate = function(){
	
	
	//提交validate验证
	$("#feeServiceForm").validate({
		  ignore:"",
		  onfocusout: function(element) { $(element).valid(); },
		  onfocusin: function(element) { $(element).valid(); },
		  rules:{
			  moneyChange:{
				  required : true,
					digits : true,
					min : 1
			  },
			  happenTime:"required",
			  remark:{
				  required:true,
				  maxlength:300
			  },
			  zzx_teacherSPR:"required",
			  payWay:"required",
			  zzx_teacherJBR:"required",
			  
		  },
		  messages:{
			  moneyChange:{
				  required : "请填写金额",
					digits : "请输入正确的正整数",
					min : "输入值大于0"
			  },
			  happenTime:"请选择时间",
			  remark:{
				  required:"请输入备注",
				  maxlength:"备注请控制在300字符内"
			  },
			  zzx_teacherSPR:"请选择审批人",
			  payWay:"请选择支付方式",
			  zzx_teacherJBR:"请选择经办人"
		  },
		  errorCallback:function(element){
		  },
		  submitHandler:function(){
			  //提交表单//
			  FEE_SERVICE_PAGE.feeServiceSubmit();
		  },
		  errorPlacement : function(error, element) {
				element.parents('.line-item-ver').find('.error-tips').html("");
				error.appendTo(element.parents('.line-item-ver').find('.error-tips'));
			}
		    
	  });
	
	
	
	
};

/** 提交 */
FEE_SERVICE_PAGE.feeServiceSubmit = function(){
	//学员不能为空
	var ids = $("#ids").val();
	if($.trim(ids).length == 0){
		layer.msg("学员列表为空，请选择学员");
		return;
	}
	
	//费用是否合理
	if(!FEE_SERVICE_PAGE.flag){
		layer.msg(FEE_SERVICE_PAGE.tips);
	}else{
		var data = $("#feeServiceForm").serialize();
		data += "&ids="+$("#ids").val()+"&serviceType="+FEE_SERVICE_PAGE.serviceType;
		
		$.ajax({
			type:"post",
			url:basePath+"/income/doFeeService.do",
			data:data,
			dataType:"json",
			success:function(rdata){
				if(rdata.code == 200){
					$("#feeServiceReturnBtn").trigger("click");
					//刷新列表
					FEE_SERVICE_PAGE.backToList();
					layer.msg("处理完成");
				}else if(rdata.code == 201){
					layer.msg("系统出现异常");
				}else if(rdata.code == 400){
					layer.msg(rdata.message);
				}else{
					layer.msg("费用服务异常");
				}
			}
			
		});//ajax
		
	}
	
	
	
};

/** 费用变化效果、费用合理性验证 */
FEE_SERVICE_PAGE.isAvailable = function(){
	FEE_SERVICE_PAGE.flag = true;//初始化flag
	var moneyChange = $("#moneyChange").val()==""?parseInt(0):parseInt($("#moneyChange").val());//此次变动金额
	var currentPayed;//已交金额
	var standardMoney;//合同金额
	var favourMoney;//减免金额
	var backMoney;//退换金额

	//列表存在的对象
	var idsArray = $("#ids").val().split(",");
	
	//克隆原始数据对象
	var tdata = new Array();
	for(var i=0;i<FEE_SERVICE_PAGE.data.length;i++)
  	{
		//排除已删除的对象
		if($.inArray(FEE_SERVICE_PAGE.data[i].stuId+"",idsArray) > -1){
			var otemp = $.extend({},FEE_SERVICE_PAGE.data[i]);
			tdata.push(otemp);
		}
			
  	}
	//循环每一行数据
	for(var i=0;i<tdata.length;i++)
	{	
		 currentPayed = parseInt(tdata[i].yjMoney);//已交金额
		 standardMoney = parseInt(tdata[i].htMoney);//合同金额
		 favourMoney = parseInt(tdata[i].jmMoney);//减免金额
		 backMoney = parseInt(tdata[i].thMoney);//退款金额
		 
		 console.log(moneyChange+":"+currentPayed+":"+standardMoney+":"+favourMoney+":"+backMoney);
		
		if(FEE_SERVICE_PAGE.serviceType == "JIANMIAN"){//减免金额不能>剩余未缴金额
			if(moneyChange > (standardMoney - currentPayed - favourMoney)){
				FEE_SERVICE_PAGE.flag = false;
				FEE_SERVICE_PAGE.tips = "存在学员的减免金额大于剩余未缴金额，请排除该学员或者更改减免金额";
//				break;
			}
			tdata[i].jmMoney += moneyChange; 
		}else if(FEE_SERVICE_PAGE.serviceType == "TUIFEI"){//退费金额不能>剩余未退金额
			if(moneyChange > (currentPayed - backMoney)){
				FEE_SERVICE_PAGE.flag = false;
				FEE_SERVICE_PAGE.tips = "存在学员的退费金额大于剩余未退金额，请排除该学员或者更改退费金额";
//				break;
			}
//			tdata[i].thMoney -= moneyChange;//原型不做显示
		}else{//回款金额不能>剩余未缴金额
			if(moneyChange > (standardMoney - currentPayed - favourMoney)){
				FEE_SERVICE_PAGE.flag = false;
				FEE_SERVICE_PAGE.tips = "存在学员的回款金额大于剩余未缴金额，请排除该学员或者更改回款金额";
//				break;
			}
			tdata[i].yjMoney += moneyChange;
		}
		
	}
	FEE_SERVICE_PAGE.Grid.setData(tdata);
	FEE_SERVICE_PAGE.Grid.render();
};


/**====================================显示弹窗==============================================**/
FEE_SERVICE_PAGE.loadPage = function(ids,opType,serviceType){
	FEE_SERVICE_PAGE.serviceType = serviceType;//获取费用服务类型
	var url = basePath+"/income/"+opType+"/"+serviceType+"/toFeeServicePage.do";
	$.ajax({
		  type:"post",
		  url:url,
		  data:{"ids":ids},
		  dataType:"html",
		  success: function(html){
			  FEE_SERVICE_PAGE.showDialog(html,serviceType);
		  }
	  });
};
/**=====================================显示弹窗==============================================**/

/**=====================================加载弹窗============================================== */
FEE_SERVICE_PAGE.showDialog = function(html,serviceType){
	var dialogue_height=$(".content").height(),
		dialogue_width=$(document).width()-50;
	var theTitle;
	if(serviceType == "JIANMIAN"){
		theTitle = "添加减免记录";
	}else if(serviceType == "TUIFEI"){
		theTitle = "添加退费记录" ;
	}else{
		theTitle = "添加回款记录";
	}
	layer.open({
	  type: 1, 
	  title: [theTitle],
	  skin: 'demo-class',
	  offset: 'rb',
	  shade: 0,
	  anim:5,
	  area:  [dialogue_width+'px', dialogue_height+'px'],
	  content: html //这里content是一个普通的String
	});
	$("#myGridFeeStudent").height($(".table-out-rel").height());
};
/**=====================================加载弹窗============================================== */

/** 刷新学员列表 */
FEE_SERVICE_PAGE.backToList = function(){
	$.ajax({
		type:"post",
		url:basePath+"/income/"+$("#opType").val()+"/goStuFeeList.do",
		data:{},
		dataType:"html",
		success:function(html){
			$("#contentDiv").html(html);
		}
	
	});
	
};
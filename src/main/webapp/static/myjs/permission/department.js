/**
 * 
 */
 DEPARTMENT={};
 //表格对象
DEPARTMENT.Grid= null;
DEPARTMENT.columns = [];
DEPARTMENT.id="";

DEPARTMENT.url=null;
DEPARTMENT.layerc=null;

//点击查询时保存查询参数for分页
DEPARTMENT.queryParam={};
var  zTree_Menu = null;
//初始化
 DEPARTMENT.init=function(){
 	//初始化树
 	DEPARTMENT.inittree();
	//初始化表格
	DEPARTMENT.initgrad();
	//初始化按钮点击事件
	 DEPARTMENT.onclick();

 }
 
 /**=====================页面上点击事件初始化开始=========================**/
 DEPARTMENT.index;
 DEPARTMENT.onclick=function(){
	 //添加部门
 
	 $("#showAddDemDialog").on("click",function(){
	 	$("#depid").val("");
	 	$("#departmentName").val("");
	 	var nodes = zTree_Menu.getSelectedNodes();
	 	$(".fangtan-person").text(nodes[0].name);
		//在这里面输入任何合法的js语句
		DEPARTMENT.index=layer.open({
		  type: 1, //Page层类型
		  skin: 'demo-class',
		  area: ['450px', '300px']
		  ,title: [ '添加部门'],
		  shade: 0.6 //遮罩透明度
		  ,anim: 8 //0-6的动画形式，-1不开启
		  ,content:$("#showAddDemDialoglayer")
		});  
	 	
	 })
	 
	 //删除部门
	 $("#showDelDemDialog").on("click",function(){
	 	var nodes = zTree_Menu.getSelectedNodes();
		 //询问框
		layer.confirm('您确定要删除部门'+nodes[0].name+'？', {
		  btn: ['确定','取消'] //按钮
		}, function(index){
		 var node = zTree_Menu.getNodesByParam("pId", nodes[0].id, nodes[0]);
		 if(node.length>0){
		 	layer.msg(nodes[0].name+'下面有子部门请先删除子部门', {time: 5000, icon:2});
		 	layer.close(index);
		 }else{
			if(DEPARTMENT.Grid.getData().length>0){
				layer.msg(nodes[0].name+'下有员工请先修改员工部门', {time: 5000, icon:2});	
				layer.close(index);
			}else{
				//调用后台删除部门
				$.ajax({
					type: "POST",
					url:basePath+"/department/delDepartment.do",
					data:"id="+nodes[0].id,
					dataType:"json",
					success: function(rdata) {
								console.log("    123123    "+JSON.stringify(rdata));
								if(rdata.code=="200"){
									layer.msg('删除部门成功', {time: 2000, icon:1});
									zTree_Menu.removeNode(nodes[0]);
								}else  if(rdata.code=="101"){
								layer.msg('删除部门失败，请稍后再试', {time: 5000, icon:2});	
								}
							}
				});
			
				layer.close(index);  
			}
		 }
		}, function(index){
		 	layer.close(index);
		});
	 	
	 })
	 
	 //修改部门
	 $("#showUpDemDialog").on("click",function(){
	 	var nodes = zTree_Menu.getSelectedNodes();
	 	if(isNull(nodes)){
	 	layer.msg('请选择一个部门', {time: 2000, icon:2});
	 	}else{
	 	$("#departmentName").val(nodes[0].name);
	 	$(".fangtan-person").text(nodes[0].name);
	 	$("#depid").val(nodes[0].id);
		//在这里面输入任何合法的js语句
		DEPARTMENT.index=layer.open({
		  type: 1, //Page层类型
		  skin: 'demo-class',
		  area: ['450px', '300px']
		  ,title: [ '修改部门'],
		  shade: 0.6 //遮罩透明度
		  ,anim: 8 //0-6的动画形式，-1不开启
		  ,content:$("#showAddDemDialoglayer")
		});  
	 	}
	 	
	 	
	 	
	 })
	
	 //保存按钮
	 $("#submit_save").on("click",function(){
	 	var departmentName=$("#departmentName").val();
		if(isNull(departmentName))
		{
			$("#departmentName").showTipError("请输入部门名称！");
			//$("#departmentName").parent().parent().find(".error-tips").text("请输入部门名称！");
		}else{
		
			//提交数据
			var data={};
			var nodes = zTree_Menu.getSelectedNodes();
			if(isNull($("#depid").val())){
				//添加
				data.depid=null;
				data.parentid=nodes[0].id;
			}else{
				//修改
				data.depid=nodes[0].id;
				data.parentid=null;
				
			}
			data.isvalid=1
			data.depname=$("#departmentName").val();
			DEPARTMENT.ajaxSubmitAdd(data,basePath+"/department/addDepartment.do");
			
		}
	  })
 
 
 }
 
 
 /**=====================页面上点击事件初始化结束=========================**/
 
 
 
/**========================ztree初始化开始=======================**/
DEPARTMENT.inittree=function(){
var setting = {
			view: {
				showLine: false,
				showIcon: false,
				selectedMulti: false,
				dblClickExpand: false,
				addDiyDom: DEPARTMENT.addDiyDom
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: DEPARTMENT.beforeClick,
				onClick: DEPARTMENT.zTreeOnClick
			
			}
		};
 	var zNodes =eval($("#trees").val());
	var treeObj = $("#treeDemo");
	$.fn.zTree.init(treeObj, setting, zNodes);
	zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
	treeObj.addClass("showIcon");
	zTree_Menu.expandAll(true);//展开所有节点
	/*treeObj.hover(function () {
		if (!treeObj.hasClass("showIcon")) {
			treeObj.addClass("showIcon");
		}
	}, function() {
		treeObj.removeClass("showIcon");
	});*/
	  /*//查询该节点下所有的部门
		$.each(zNodes,function ( i,item){
		DEPARTMENT.id=DEPARTMENT.id+","+item.id;
			
		})*/
		//获取节点  
            var nodes = zTree_Menu.getNodes();  
            if (nodes.length>0)   
            {  
                var node = zTree_Menu.selectNode(nodes[0]);
	 	   		 DEPARTMENT.id=nodes[0].id;
            } 
          
            //初始化数据
    DEPARTMENT.queryParam.currpage=1;
    DEPARTMENT.queryParam.pageSize=PageWiget.getPageSize();
	DEPARTMENT.queryParam.ids=DEPARTMENT.id;
	DEPARTMENT.url=	basePath+"/department/getUserByDepartment.do";
	DEPARTMENT.ajaxSubmit(DEPARTMENT.queryParam,DEPARTMENT.url);
}
/**=================ztree初始化结束===================**/


 /**===============分页处理开始==============**/
DEPARTMENT.dopagechange=function(pageno,pagesize){
	console.log(pageno+"   wwwww   "+pagesize);
	DEPARTMENT.url=	basePath+"/department/getUserByDepartment.do";
	DEPARTMENT.queryParam.currpage=pageno;
	DEPARTMENT.queryParam.pageSize=pagesize;
	DEPARTMENT.queryParam.ids=DEPARTMENT.id;
	//alert(JSON.stringify(DEPARTMENT.queryParam));
	DEPARTMENT.ajaxSubmit(DEPARTMENT.queryParam,DEPARTMENT.url);
};
/**===============分页处理结束==============**/


/**================================表格开始======================================*/
	DEPARTMENT.initgrad=function(){
	DEPARTMENT.setTableHeight();
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
		width: 120		//宽度.

	});

	columns.push({
		id: "depname", 
		name: "部门名称",
		field: "depname", 
		width: 200
	});


	columns.push({
		id: "real_name", 
		name: "真实姓名",
		field: "real_name", 
		width: 150
		//formatter:STUDENT_INTO_CLASS.colorRender
	});

	columns.push({
		id: "login_email", 
		name: "登录邮箱",
		field: "login_email", 
		width:250
	});
	
	columns.push({
		id: "sex1", 
		name: "性别",
		field: "sex1", 
		width: 150
	});

	columns.push({
		id: "isvalidName", 
		name: "账户是否有效",
		field: "isvalidName", 
		//cssClass:"slick-cell-action",
		width: 150
	//	formatter:STUDENT_INTO_CLASS.actionRender
	});
	
	DEPARTMENT.columns = columns;
	//产生grid 控件
	DEPARTMENT.Grid = new Slick.Grid("#myGrid",[], columns, options);

	DEPARTMENT.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
	//设置为行选无效
	DEPARTMENT.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
	//注册插件. checkbox 显示方式做为一种插件
	DEPARTMENT.Grid.registerPlugin(checkboxSelector);
	//检测表格选中事件
	DEPARTMENT.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
		 var selectedRows = DEPARTMENT.Grid.getSelectedRows();
	 console.log("选择了:" + selectedRows.length +"条");
		 
		});

	//移动到行上, 改变背景颜色
	$(document).on("mouseover","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
	}); 

	$(document).on("mouseout","div.slick-cell",function(e){
		$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	}); 
	
	//DEPARTMENT.ajaxSubmit({"ids":DEPARTMENT.id.substring(1)},basePath+"/department/getUserByDepartment.do");


	}
/**================================表格结束======================================*/
	
	
	
/**================================ztree时间触发开始======================================*/
	DEPARTMENT.addDiyDom=function(treeId, treeNode) {
			var spaceWidth = 5;
			var switchObj = $("#" + treeNode.tId + "_switch"),
			icoObj = $("#" + treeNode.tId + "_ico");
			switchObj.remove();
			icoObj.before(switchObj);

			if (treeNode.level > 0) {
				var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
				switchObj.before(spaceStr);
			}
		}
		
	 DEPARTMENT.beforeClick=function(treeId, treeNode) {
			/*if (treeNode.level == 0 ) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.expandNode(treeNode);
				return false;
			}*/
			return true;
		}

	DEPARTMENT.zTreeOnClick=function(event, treeId, treeNode) {
	   //显示该部门所有员工
		console.log(JSON.stringify(treeNode));
		//查询该节点下所有的部门
		 DEPARTMENT.id="";
		DEPARTMENT.f(treeNode);
		//调用数据显示部门
		DEPARTMENT.dopagechange(1,20);
		
		
	};

/*DEPARTMENT.f = function (treeNode) {  
    if (treeNode.children ==""||treeNode.children ==null) {
    
    	DEPARTMENT.id=DEPARTMENT.id+","+treeNode.id;
    } else { 
    	DEPARTMENT.id=DEPARTMENT.id+","+treeNode.id;
    	var ch=treeNode.children;
    	$.each(ch,function(i,item){
    	DEPARTMENT.f(item);
    	})
    } 
}; */ 
DEPARTMENT.f = function (treeNode) {  
    	DEPARTMENT.id=treeNode.id;
   };	
/**================================ztree时间触发结束======================================*/


/**=====================================获取表格数据===================================================*/
//ajax提交-for获取表格数据(公用)
DEPARTMENT.ajaxSubmit=function(data,url){
	$.ajax({
		type: "POST",
		url:url,
		data:data,
		dataType:"json",
		success: function(rdata) {
				console.log("    123123    "+JSON.stringify(rdata));
			  //填充表格数据
			  DEPARTMENT.Grid.setData(rdata.userByDepartment);
			/*$(".grid-canvas").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
			$(".slick-viewport").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
			$(".slick-viewport").css({"overflow-y": "auto"});*/
			
			DEPARTMENT.Grid.render();
			if(DEPARTMENT.queryParam.currpage==1){
			//初始化分页-总记录数/每页条数
			PageWiget.init(rdata.recordcount,PageWiget.getPageSize());
			}
		
			if(rdata.userByDepartment==""||rdata.userByDepartment==null){
				var nodes = zTree_Menu.getSelectedNodes();
				
				  DEPARTMENT.layerc=layer.msg(nodes[0].name+'部门下没有员工',{time:86400000});//24小时后关闭
			}else{
				if(isNull(DEPARTMENT.layerc)){
				}else{
					layer.close(DEPARTMENT.layerc);
				}
			}
		}
	});
	
};
/**========================================获取表格数据结束================================================*/

/**=====================================提交部门添加或修改数据开始===================================================*/
//ajax提交-for获取表格数据(公用)
DEPARTMENT.ajaxSubmitAdd=function(data,url){
	$.ajax({
		type: "POST",
		url:url,
		data:data,
		dataType:"json",
		success: function(rdata) {
				console.log("    123123    "+JSON.stringify(rdata));
				if(rdata.code=="200"){
				//	alert(data.depid);
					if(data.depid==null||data.depid==""||data.depid==undefined){
					
					 layer.msg('添加部门成功', {time: 2000, icon:1});
					 var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	 				var nodes = treeObj.getSelectedNodes();
					var newNode = {name:data.depname,id:rdata.depid};
					newNode=treeObj.addNodes(nodes[0],0, newNode);	
					}else{
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						var nodes = treeObj.getSelectedNodes();
						nodes[0].name = data.depname;
						treeObj.updateNode(nodes[0]);
					 	layer.msg('修改部门成功', {time: 2000, icon:1});	
					}
					layer.close(DEPARTMENT.index); 
					
				}else  if(rdata.code=="101"){
				layer.msg('添加部门失败，请稍后再试', {time: 5000, icon:2});	
				}else  if(rdata.code=="102"){
				layer.msg('修改部门失败，请稍后再试', {time: 5000, icon:2});	
				}
		}
	});
	
};
/**========================================提交部门添加或修改数据结束================================================*/


//设置表格高度
DEPARTMENT.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
	$(".zTreeDemoBackground").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});

};	
	

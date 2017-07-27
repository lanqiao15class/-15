<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath }/static/js/slick/slick.grid.css" type="text/css"/>
 <link rel="stylesheet" href="${pageContext.request.contextPath }/static/js/slick/examples.css" type="text/css"/>
 
 
  <style>
    .slick-cell-checkboxsel {
      background: silver;
      border-right-color: #cccccc;
      border-right-style: solid;
    }
		
   .slick-cell-action {
      text-align:center;
    }
	
	 .slick-row-hover {
      background: "green";
    }
		
	
		
  </style>
 
<script src="${pageContext.request.contextPath }/static/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/jquery.event.drag-2.2.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/slick.core.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/slick.checkboxselectcolumn.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/slick.autotooltips.js"></script>

<script src="${pageContext.request.contextPath }/static/js/slick/slick.cellrangedecorator.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/slick.cellrangeselector.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/slick.cellselectionmodel.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/slick.rowselectionmodel.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/slick.columnpicker.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/slick.formatters.js"></script>
<script src="${pageContext.request.contextPath }/static/js/slick/slick.grid.js"></script>
 
</head>
<body>
<input type="button" value="setData" onclick="dosetdata()">
<div id="myGrid" style="width:100%;height:500px;"></div>

<script>
var spath = "${pageContext.request.contextPath}";


// 测试通过ajax 动态获取数据并用grid 显示
function dosetdata()
{
	data = [];
 
  //产生测试数据. 可以通过ajax 获取后台数据
/*     for (var i = 0; i < 50; i++) {
      var d = (data[i] = {});
		d.indexno=i+"";
		d.realname="雷雷";
		d.coursetype="java";
		d.unicode="青岛大学";
		d.status="在读";
		d.nstatus=1;
		d.teacher="扯淡";
		d.updatetime="2015-01-01";
		d.ismain=1; // 是否是重点. 
		d.userid=10001; // 学生id
		d.ifmy= 1; // 是否是我的.	
		
    } */
    $.ajax({
		type:"post",
		url:spath +"/slickdata.do",
		dataType:"json",
		success:function(dd){
			 grid.setData(dd,0);
			 grid.render();
			return true;
		}
	});
    

    
}



  var grid;
  
  //表格数据
  var data = [];
  //定义表格的功能
  var options = {
    editable: false,		//是否可以表格内编辑
    enableCellNavigation: true,  
    asyncEditorLoading: false,
	enableColumnReorder: false,
	rowHeight:32, // 行高
    autoEdit: false
  };
 
 
  $(document).ready(function () {
  
  
  //产生测试数据. 可以通过ajax 获取后台数据
  /*
    for (var i = 0; i < 50; i++) {
      var d = (data[i] = {});
		d.indexno=i+"";
		d.realname="雷雷";
		d.coursetype="java";
		d.unicode="青岛大学";
		d.status="在读";
		d.nstatus=1;
		d.teacher="扯淡";
		d.updatetime="2015-01-01";
		d.ismain=1; // 是否是重点. 
		d.userid=10001; // 学生id
		d.ifmy= 1; // 是否是我的.	
		
    }
	*/
	
	
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
        width: 120			//宽度.
      });
	  
	 columns.push({
        id: "realname", 
        name: "姓名",
	    field: "realname", 
        width: 200,
		formatter:realnameRender
      });
	   
	  
	  
	   columns.push({
        id: "coursetype", 
        name: "课程类别",
	    field: "coursetype", 
        width: 100
      });
	  
	  
	    
	   columns.push({
        id: "unicode", 
        name: "所在院校",
	    field: "unicode", 
        width: 200
      });
	   
	 columns.push({
        id: "status", 
        name: "状态",
	    field: "status", 
        width: 100
     });
	 
	  columns.push({
        id: "teacher", 
        name: "招生经理",
	    field: "teacher", 
        width: 100
     });
	 
	columns.push({
        id: "updatetime", 
        name: "更新时间",
	    field: "updatetime", 
        width: 100
     });
	 
	 
	 columns.push({
        id: "action", 
        name: "操作",
	    field: "action", 
		cssClass:"slick-cell-action",
        width: 200,
		formatter: actionRender
     });
	 
	 //产生grid 控件
    grid = new Slick.Grid("#myGrid", data, columns, options);
	//设置为行选无效
    grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
    //注册插件. checkbox 显示方式做为一种插件
	grid.registerPlugin(checkboxSelector);


	//移动到行上, 改变背景颜色
	
$(document).on("mouseover","div.slick-cell",function(e){
	 $(e.target).parent("div.slick-row").css("background","green");
}); 

$(document).on("mouseout","div.slick-cell",function(e){
	  $(e.target).parent("div.slick-row").css("background","white");
}); 

	/* 	
	$("div.slick-cell").live("mouseover",function (e) {
		  $(e.target).parent("div.slick-row").css("background","green");
		  
        })
		.live("mouseout",function (e) {
			
		  $(e.target).parent("div.slick-row").css("background","white");
        }); */
		

		
   
  });
  
  //每行输出的操作按钮. 
   function actionRender(row, cell, value, columnDef, dataContext) {
        var s= "<img src='images/icon1.png'/>";
		//根据行数据确定是否显示icon2.png
		if(dataContext.nstatus==1)
		s +="<a href='javascript:dotest()'><img src='images/icon2.png'/></a>";
		return s;
		
    }

	
/**
  自定义显示姓名, 是否是重点, 是否是我的,  显示不同的图标. 
  row: 行号
  cell ?
		  value: 数据. 
 dataContext : 一行数据

**/	
function realnameRender(row, cell, value, columnDef, dataContext) 
{
        var s= value;
		if(dataContext.ismain==1)
		{
			s +="<img src='images/icon3.png'/>";
		}
		
		if(dataContext.ifmy==1)
		{
			s +="<img src='images/icon2.png'/>";
		}
		return s;
		
}
function dotest()
{
  alert("test");
}
	
</script>
</body>
</html>
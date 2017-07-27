<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/view/include/publiccss.jsp"%>
<%@ include file="/WEB-INF/view/include/publicjs.jsp"%>

</head>
<body>

<input type="button" onclick="dorenderdata()" value="显示数据"><br/>

<input type="button" onclick="updateRowBackground(2)" value="设置背景颜色"><br/>

<div id="myGrid" style="width:100%;height:500px;"></div>

</body>

<script>
$(function(){
	
	SlickTemplate.init();
	$(window).resize(function() {
	
		if(SlickTemplate.Grid)
			{
				//dorenderdata();
				dorenderdata();
				//SlickTemplate.init();
				console.log("resize ...");
			}
		
		});
	
	
	
});

function dorenderdata()
{
	var data = new Array();
	for(var i=0;i<20;i++)
	{
		var d = {};
		d.userid=i;
		d.indexno=i;
		d.realName="姓名 " +i;
		d.tel="110  " + i;
		d.schoolName="院校" +i;
		d.updateTime="2014-10-12";
		
		if(i % 2 ==0)
			d.status=0;
		else
			d.status=1;
		
		d.option="";
		data.push(d);
		
	}
	
	SlickTemplate.Grid.setData(data);
	SlickTemplate.Grid.render();
}


///////////表格部分 =========================================
SlickTemplate={};
//表格对象. 
SlickTemplate.Grid=null;
SlickTemplate.init=function()
{

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
	width: 120		//宽度.

});

columns.push({
	id: "realName", 
	name: "姓名",
	field: "realName", 
	width: 200,
	formatter:SlickTemplate.courseRender
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
	formatter:SlickTemplate.actionRender
});
//产生grid 控件
SlickTemplate.Grid = new Slick.Grid("#myGrid",[], columns, options);

SlickTemplate.Grid.registerPlugin( new Slick.AutoTooltips({ enableForHeaderCells: true }) );
//设置为行选无效
SlickTemplate.Grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));
//注册插件. checkbox 显示方式做为一种插件
SlickTemplate.Grid.registerPlugin(checkboxSelector);
//检测表格选中事件
SlickTemplate.Grid.onSelectedRowsChanged.subscribe(function (e, args) {
	 var selectedRows = SlickTemplate.Grid.getSelectedRows();
	 console.log("选择了:" + selectedRows.length +"条");
	 
	});

//移动到行上, 改变背景颜色
$(document).on("mouseover","div.slick-cell",function(e){
	$(e.target).parent("div.slick-row").addClass("hover_bgcolor");
}); 

$(document).on("mouseout","div.slick-cell",function(e){
	$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
}); 

};

function deleteRow(uid)
{
	var rowindex =-1;
	
	var tdata = SlickTemplate.Grid.getData();
	//查找到记录. 得到序号.
	for(i=0;i<tdata.length;i++)
	{
		//console.log("userid:"+tdata[i].userid);
		if(tdata[i].userid == uid)
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
	 
	 SlickTemplate.Grid.setData(tdata);
	 SlickTemplate.Grid.render();
	
}
/**
 * 修改某一行的背景颜色. 
 */

	
 function updateRowBackground(rowindex)
 {
 	var tdata = SlickTemplate.Grid.getData();
 	
 	 tdata[rowindex].issame = true;
 	
 	 SlickTemplate.Grid.setData(tdata);
 	 SlickTemplate.Grid.render();
 	
 	
 }


//自定义显示列
SlickTemplate.courseRender=function (row, cell, value, columnDef, dataContext) 
{
	var s =value;
	 if(dataContext.issame)
	 {
		   s ="<span style='color:red'>" + value +"</span>"
	 }
	return s;
};


//自定义显示列
SlickTemplate.actionRender=function (row, cell, value, columnDef, dataContext) {
	//$(e.target).parent("div.slick-row").removeClass("hover_bgcolor");
	var s= "";
	var r = SlickTemplate.Grid;
	 
	//console.log("row:"+ row + "dataContext.userid:" + dataContext.userid);
	var icon_start='<div class="gird-box">';
	var icon_look='<a class="c-blue opt-incon-btn" href="javascript:void(0);" title="删除" onclick="deleteRow('+dataContext.userid+')"><i class="opti-icon Hui-iconfont">&#xe725;</i></a>';
	var icon_end='</div>';

	//根据行数据确定是否显示icon2.png
		s += icon_start+icon_look+icon_end;
	
	return s;
};

</script>
</html>
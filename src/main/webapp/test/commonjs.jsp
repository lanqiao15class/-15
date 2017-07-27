<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.lanqiao.common.*" %>

<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<c:set var="usertype" value="1" />
<%@ include file="/WEB-INF/view/include/publiccss.jsp"%>
<%@ include file="/WEB-INF/view/include/publicjs.jsp"%>


</head>
<body style="margin:50px">



<br/>
<br/>
<br/>
<br/>
<br/>

学生姓名:
<jsp:include page="/WEB-INF/view/include/student_input_likesearch.jsp">
	<jsp:param name="inputid" value="student_name" />
	<jsp:param name="inputwidth" value="300" />
	<jsp:param name="placeholder" value="请输入学生姓名.." />
	
</jsp:include>
<br/>

学员下拉选择:
<jsp:include page="/WEB-INF/view/include/student_select_likesearch.jsp">
	<jsp:param name="inputid" value="select_student_name" />
	<jsp:param name="placeholder" value="按学员姓名查询" />
</jsp:include>

 <br/>
	
学生专业:
  <jsp:include page="/WEB-INF/view/include/studentmajor_input_search.jsp">
		<jsp:param name="inputid" value="student_major_name" />
		<jsp:param name="inputwidth" value="300" />
  </jsp:include>
 	
<br/> 



学院(分院):
  <jsp:include page="/WEB-INF/view/include/school_subname_input_likesearch.jsp">
		<jsp:param name="inputid" value="school_sub_name" />
		<jsp:param name="inputwidth" value="300" />
		
		<jsp:param name="placeholder" value="请输入学院..." />
		
  </jsp:include>
<br/> 

	=========================================<br/>

 院校查询:
 
    <jsp:include page="/WEB-INF/view/include/school_select_likesearch.jsp">
	<jsp:param name="inputid" value="select_school_name" />
	</jsp:include>
 <br/>	
 
 
 院校查询:
 
    <jsp:include page="/WEB-INF/view/include/school_select_likesearch.jsp">
	<jsp:param name="inputid" value="select_school_name2" />
	<jsp:param name="selectid" value="" />
	<jsp:param name="selecttext" value="按区域总监查询" />
	
	</jsp:include>
 <br/>
<input type="button" value="...显示院校id..." onclick="doshowid()">
 
 
 <span>从</span>
 <input type="text" name="startTime" id="startTime" value="${startTime }" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
 <span>到</span>
 <input type="text" name="endTime" id="endTime" value="${startTime }" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
 
===========================================<br/>
<input type="button" value="...弹窗..." onclick="doclick()">




 民族选择:
 
    <jsp:include page="/WEB-INF/view/include/nation_select_search.jsp">
		<jsp:param name="inputid" value="nation_select" />
	</jsp:include>
	
 <br/> 
 <input type="button" value="...提示信息.." onclick="dohint()">
 
 
 <br/> 
 <input type="button" value="...初始化分页.." onclick="doTest_Pageinit()">
 
 <input type="button" value="...得到pagesize.." onclick="getpagesize()">
 
<br/>
==================测试翻页公共插件==================================<br/>

funcName: 当切换页码时, 需要执行的js 函数名. <br/>


   <jsp:include page="/WEB-INF/view/include/pagejavascript.jsp">
	<jsp:param name="funcName" value="dopagechange" />
	</jsp:include>
<br/>
老师下拉选择:
<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
	<jsp:param name="inputid" value="select_teacher_name" />
	<jsp:param name="placeholder" value="请查询老师.." />
</jsp:include>


老师下拉选择(无placeholder):
<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
	<jsp:param name="inputid" value="select_teache22" />
	
</jsp:include>


<br/>
 <span class="select-search-box">
   <select id="testselect"  name="testselect"  class="scoolname">
  
   </select>
</span>



<input type="button" value="清空" onclick="setValue()">

<br/>
placeholer:
    <jsp:include page="/WEB-INF/view/include/school_select_likesearch.jsp">
	<jsp:param name="inputid" value="select_placeholder_test" />
	<jsp:param name="placeholder" value="按区域总监查询" />
	</jsp:include>

<br/>
班级下拉:

    <jsp:include page="/WEB-INF/view/include/class_select_search.jsp">
	<jsp:param name="inputid" value="select_placeholder_test_class" />
	<jsp:param name="placeholder" value="请输入班级名称" />
	</jsp:include>


<br/>

班级输入:
    <jsp:include page="/WEB-INF/view/include/classname_input_likesearch.jsp">
	<jsp:param name="inputid" value="select_placeholder_test_class22" />
	<jsp:param name="placeholder" value="请输入班级名称" />
	</jsp:include>


<script>
function getpagesize()
{
	var ps = PageWiget.getPageSize();
	
	console.log("pagesize:"+ps);
}


function setValue()
{
	if(Sele__this)
		Sele__this.setValue("","");
}
var Sele__this;


$(function(){
	
	/* 	$('#testselect').searchableSelect({data:gl_nationdata,selectid:'汉族', selecttext:'汉族',
		afterSelectItem: function(sval,stext)
	 	{
			console.log("selectid:"+sval+" text:" + stext);
	 	}}); */
	
	var oval = $('#testselect').searchableSelect({data:gl_nationdata,
		afterInit:function(args){
			console.log(args);
			Sele__this = this;
		//	console.log(oval);
			
		},
		afterSelectItem: function(sval,stext)
	 	{
			console.log("selectid:"+sval+" text:" + stext);
	 	}});
})


function doTest_Pageinit()
{
	//初始化分页显示// 总记录数  , 每页记录数
	PageWiget.init(1000,50);
}

function dohint()
{
	$("#startTime").showTipError("请输入开始时间...");
}
function doshowid()
{
	var v = $("#select_school_name2").val();
	var checkText= $("#select_school_name2 option:selected").text(); 
	alert(v+":"+checkText);
	}
//页吗变化时执行. 的回调函数
function dopagechange(pageno,pagesize)
{
	console.log("dotest :" + pageno +" "+ pagesize);
	alert("选中了;" + pageno +":" + pagesize);
}


function doclick()
{
	var strurl ="a_dialog.jsp";
	 $.ajax({dataType:"html",
		  url:strurl,
		  canback:true,
		  type:"post",
		  success: function(data){
			  //弹出
			  openDialog_ke(data);
			  }
		  });
	
	}
	
	
	function openDialog_ke(htmldata)
	{
		
		layer.open({
			  type: 1, 
			  title: ['创建访谈记录'],
			  skin: 'demo-class',
			  shade: 0.6,
			  anim:8,
			  area: ['500px','450px'],
			  content: htmldata     //这里content是一个普通的String
			});
	}
</script>
</body>
</html>
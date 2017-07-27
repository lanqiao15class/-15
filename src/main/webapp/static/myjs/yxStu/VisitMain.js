/**
 * 
 * 	author : chenbaoji .
 * 
 */
VisitMain={};
VisitMain.init = function()
{
	//访谈查询
	$("#fangtanSearch").change(function(){
		$(".ser-out-span").hide();
		var num=$('#fangtanSearch option:selected').val()-1;
		if(num>=0){
			$(".ser-out-span").eq(num).show();
		}
	});

	
};

//最后一次查询的参数, 翻页时使用.
VisitMain.searchParam={searchtype:0};

VisitMain.beforeSearch = function()
{
	var ftype = $("#fangtanSearch").val();
	var vstartTime = $("#startTime").val();
	var vendTime = $("#endTime").val();
	var student_name = $("#student_name").val();
	var createstartTime = $("#createstartTime").val();
	var createendTime = $("#createendTime").val();
	var visit_schoolid = $("#visit_schoolid").val();
	var teachername1 = $("#teachername1").val();
	var pagesize = $("#p_pageSize").val();
	
	var param = {searchtype:ftype,pagesize:pagesize};
	if(ftype ==0)
	{
		VisitMain.search(param);
		VisitMain.searchParam= $.extend({},param);
		return ;
	}
	
	if(ftype ==1)
	{
		if(isNull(visit_schoolid))
		{
			$(".searchable-select").showTipError("请选择院校...");
			return false;
		}
		param = $.extend(param,{univ_code:visit_schoolid, schooltext:$("#visit_schoolid").text()});
		VisitMain.searchParam= $.extend({},param);
		VisitMain.search(param);
		return ;
	}
	
	
	if(ftype ==2)
	{
		//console.log("vstartTime=" + vstartTime +" vendTime=" + vendTime);
		if(isNull(vstartTime))
		{
			$("#startTime").showTipError("请选择时间区段");
			return ;
		}
		if(isNull(vendTime))
		{
			$("#endTime").showTipError("请选择时间区段");
			return ;
		}
		
		if(VisitMain.isValidDate(vstartTime,vendTime)==false)
		{
			$("#endTime").showTipError("日期不合法, 截止时间必须大于开始时间");
			return ;
		}
			
		param = $.extend(param,{bvisit_time:vstartTime, evisit_time:vendTime });
		VisitMain.searchParam= param;
		VisitMain.search(param);
			
	}
	
	if(ftype ==3)
	{
		if(isNull(student_name))
		{
			$("#student_name").showTipError("请输入学生姓名...");
			return ;
		}
		
		param = $.extend(param,{real_name:student_name});
		VisitMain.searchParam= param;
		VisitMain.search(param);
			
	}
	
	if(ftype ==4)
	{
		if(isNull(createstartTime))
		{
			$("#createstartTime").showTipError("请选择开始时间...");
			return ;
		}
		if(isNull(createendTime))
		{
			$("#createendTime").showTipError("请选择开始时间...");
			return ;
		}
		
		
		if(VisitMain.isValidDate(createstartTime,createendTime)==false)
		{
			$("#createendTime").showTipError("日期不合法, 截止时间必须大于开始时间");
			return ;
		}
		param = $.extend(param,{bcreatetime:createstartTime , ecreatetime:createendTime });
		VisitMain.searchParam= param;
		VisitMain.search(param);
			
	}
	
	if(ftype ==5)
	{
		if(isNull(teachername1))
		{
			$("#teachername1").showTipError("请输入老师姓名...");
			return ;
		}
		
		param = $.extend(param,{teahcer_real_name:teachername1});
		VisitMain.searchParam= param;
		VisitMain.search(param);
			
	}
}

VisitMain.skippage=function(pageno, pagesize)
{
	//拷贝为一个新对象. 
	var o = $.extend({},VisitMain.searchParam);
	o.pageno = pageno;
	o.pagesize = pagesize;
	printObject(o);
	VisitMain.search(o);
}

VisitMain.refresh=function(){
	VisitMain.search(VisitMain.searchParam);
}

VisitMain.isValidDate=function (btime, etime)
{
	
	btime = btime.replace("-","").replace("-","");
	etime = etime.replace("-","").replace("-","");
	return (Number(etime)-Number(btime) >= 0) ? true:false;
}


//查询param : 查询的参数.
VisitMain.search=function(param)
{
	var strurl =basePath + "/visit/visitmain.do";
	
	  $.ajax({
		  dataType:"html",
		  url:strurl,
		  data:param,
		  type:"post",
		  success: function(k_data){
		  $("#contentDiv").html(k_data);
		  }
	  });
	  
}



VisitMain.showaddDialog=function (id,visit_id){
	var data={};
	if(id==1){
	data.userid=visit_id;
	}else if(id==0){
	data.visit_id=visit_id;
	}
	var strurl = basePath + "/visit/showaddvisit.do";
	 $.ajax({
		  dataType:"html",
		  url:strurl,
		  data:data,
		  type:"post",
		  success: function(k_data){
			VisitMain.showaddDialogStatus=  layer.open({
				  type: 1, 
				  title: ['新增跟踪记录'],
				  skin: 'demo-class',
				  shade: 0.6,
				  anim:8,
				  area: ['610px', '620px'],
				  content: k_data 
				});
		  }
	  });
	
	
	
}




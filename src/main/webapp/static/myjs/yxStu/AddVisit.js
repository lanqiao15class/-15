AddVisit={};

AddVisit.studentlist=null;
AddVisit.schoollist=null;
/** 是否选择了学生. */
jQuery.validator.addMethod("checkSelectStudent",function(value,element){
	var str =value;
	//console.log("size:" + AddVisit.studentlist.size());
	if (AddVisit.studentlist.size()>0) {
	    res = true;
	} else {
	    res = false;
	}
    return res;
},"没指定任何访谈对象");

//显示院校.
AddVisit.renderSchool=function()
{
	/* <span class="people-school-box">
 	<i class="peo-sname">湖北工业学院</i>
 </span>*/
	
var strhtml ="";
var vs = AddVisit.schoollist.values();
 for(i=0;i<vs.length;i++)
	 {
	 	strhtml +="<i class='peo-sname'>"+vs[i]+"</i>";
	 }
	
 $(".people-school-box").html(strhtml);
 
}
/**
 * 本地查询学生的所在院校.
 */
AddVisit.localFindSchoolName=function(stuid)
{
	console.log("1111"+stuid);
	var unicode = "";
	for(i=0;i <gl_studentnamelist.length;i++)
	{
		var od = gl_studentnamelist[i];
		if(od.id == stuid)
		{
		console.log("222222");
			unicode = od.univ_code;
			AddVisit.studentlist.put(stuid,gl_studentnamelist[i]);
			break;
		}
	}
var uname ="";
if(unicode !=null && unicode !='')
{
	for(i=0;i< gl_schoolllist.length;i++)
	{
		var od = gl_schoolllist[i];
		if(od.id == unicode)
		{
			uname = od.label;
			break;
		}
	}
	if(uname !=null && uname !='')
		{
			AddVisit.schoollist.put(unicode, uname);
			AddVisit.renderSchool();
		}
}

//console.log("uname ==="+uname);
return uname;
	
}

/**
 * 计算学生下拉列表显示数据. 
 */
AddVisit.searchstudentlist= function()
{
	var schoolmap = new HashMap();
	// 将学校信息放入 hashmap 
	for(i=0;i< gl_schoolllist.length;i++)
	{
		var od = gl_schoolllist[i];
		schoolmap.put(od.id, od.label);
	}
	
	//alert(schoolmap.size()+":" + gl_studentnamelist.length);
	
	var arr = new Array();
	for(i =0 ;i< gl_studentnamelist.length;i++)
	{
		var otemp = $.extend({},gl_studentnamelist[i]);
		// alert(otemp.id +":" + otemp.label);
		var slabel = otemp.label ;
		var schname = schoolmap.get(otemp.univ_code);
		if(schname)
		{
			slabel +="-" + 	schname;
		}
		if(otemp.stu_no)
		{
			slabel +="(" + 	otemp.stu_no +")";
		}
		otemp.label = slabel;
		arr.push(otemp);
		// alert(otemp.id +":" + otemp.label);
	}
	
	return arr;
	
};

//剔除所选择没院校的名称. 
AddVisit.sliceSchool=function(){
	
  var ucodelist = AddVisit.schoollist.keys();
  var stulist = AddVisit.studentlist.values();
  
  for(i=0;i<ucodelist.length;i++)
  {
	  var b =false;
	  for(j=0; j< stulist.length;j++)
		{
		  	if(stulist[j].univ_code == ucodelist[i])
		  		{
		  		  b = true;
		  		  break;  
		  		}
		}
	  
	   if(!b)
		{
		   //移除. 
		   AddVisit.schoollist.remove(ucodelist[i]);
		}
  }
  
};

AddVisit.init=function()
{
	
	AddVisit.schoollist=new HashMap();
	AddVisit.studentlist=new HashMap();
	var stulist = AddVisit.searchstudentlist();
	
	$('#visit_select_student').searchableSelect({data:stulist,isshow:true,
			afterSelectItem:function(sval,stext)
			 {
					if(sval =="") return ;
				
					$(".select-fangtan-people").hide();
				
					//console.log("selectid:"+sval+" text:" + stext);
					if(AddVisit.studentlist.containsKey(sval))
					{
						return ;
					}
			 		
					var _name='<a href="javascript:void(0);" data-id="'+sval+'" class="oneObj-box"><span class="one-people-name">'+stext+'</span><i class="del-btn-icon">╳</i></a>'
					$(".add-stu-box").append(_name);
					//console.log("AddVisit.studentlist.put");
					$("#span_school_tip").html("");
					AddVisit.localFindSchoolName(sval);
				
		 }});
	
	//添加学生
	$(".add-btn").click(function(e){
		e.stopPropagation();
		$(this).parent(".add-btn-box").find(".select-fangtan-people").show();
	//	$(".searchable-select-holder").click();
	//	$('#visit_select_student').show();
		
		
	});
	//删除学生
	$(".add-stu-box").on("click",".del-btn-icon",function(){
		var sid = $(this).parents(".oneObj-box").attr("data-id");
		$(this).parents(".oneObj-box").remove();
		AddVisit.studentlist.remove(sid);
		
		AddVisit.sliceSchool();
		
		AddVisit.renderSchool();
		
		
	});
	
	$(".select-fangtan-people .select-search-box").click(function(e){
		e.stopPropagation();
	});
	
	var defdate = getNowDate();
	
	$("#visittime").val(defdate);
	
	$("body").click(function(e){
		$(".select-fangtan-people").hide();
	});
	

	//验证规则. 
	$("#addvisit_form").validate({
		onfocusout: function(element) {//是否在失去时验证 
			//$(element).valid(); 
		},
		ignore: "",//验证隐藏域
		rules:{
			vgoal:{
				required:true
			},
			vcontent:{
				required:true
			 },
			 visittime:{
				required:true
			 },
			 visit_student:{
					checkSelectStudent:true
			 }
		},
		messages:{
			vgoal:{
				required:"请输入访谈目的"
			 },
			 vcontent:{
				required:"请输入谈话纪要"
			 },
			 visittime:{
				required:"请输入访谈日期"
			 },
			 visit_student:{
				 checkSelectStudent:"没指定任何访谈对象"
			 }
		},
		success : function(element) {
			
		},
		errorPlacement: function(error, element) {  
			error.appendTo(element.parents('.line-item-ver').next('.error-tips'));
			error.appendTo(element.parents('.clearfix').next('.error-tips'));
		},
		errorCallback:function(element){
			//console.log("errorCallback  name=" +$(element).attr("name"));
		},  
		submitHandler:function(form){
			//保存处理
			//console.log("submitHandler");
			
			var vdata ={};
			if(!isNull($("#visitid").val())){
				vdata.visitid = $("#visitid").val();
			}
			vdata.vgoal = $("#vgoal").val();
			vdata.vcontent = $("#vcontent").val();
			vdata.visittime =  $("#visittime").val();
			vdata.stid= AddVisit.studentlist.keys().join(",");
			AddVisit.submitData(vdata);
			//printObject(vdata);
			
		}
	});
	
	//==================================================================

	// 按钮事件, 保存, 取消. 
	$("#cancel_bnt").bind("click",function(){
		layer.close(VisitMain.showaddDialogStatus);
		});
	
	/*$("#submit_save").bind("click",function(){
		console.log("click ...");
		addvisit_form.submit();
	});*/
	
	var data=$("#visitmanlist").val();
	if(!isNull(data)){
	  var str=JSON.parse(data);
	  $.each(str, function(i,item){
	  	console.log(item);
  		var _name='<a href="javascript:void(0);" data-id="'+item.user_id+'" class="oneObj-box"><span class="one-people-name">'+item.real_name+"-"+item.univ_name+"("+item.stu_no+")"+'</span><i class="del-btn-icon">╳</i></a>'
				$(".add-stu-box").append(_name);
				//console.log("AddVisit.studentlist.put");
				$("#span_school_tip").html("");
				AddVisit.localFindSchoolName(item.user_id);
				$("#vgoal").val(item.visit_goal);
				$("#vcontent").val(item.visit_content);
				$("#visittime").val(item.visit_time);
				$("#visitid").val(item.visit_id);
				
	  })
	}
	if(!isNull($("#visitmanlist1").val())){
	  var str=JSON.parse($("#visitmanlist1").val());
  		var _name='<a href="javascript:void(0);" data-id="'+str.userid+'" class="oneObj-box"><span class="one-people-name">'+str.realName+"-"+str.schoolName+"("+str.stuNo+")"+'</span><i class="del-btn-icon">╳</i></a>'
				$(".add-stu-box").append(_name);
				//console.log("AddVisit.studentlist.put");
				$("#span_school_tip").html("");
				AddVisit.localFindSchoolName(str.userid);
	}
}

/**
 * 
 * 提交数据到后台.
 * 
 */
AddVisit.submitData=function(vdata)
{
	var strurl =basePath + "/visit/postaddvisit.do";
	
	  $.ajax({
		  dataType:"json",
		  url:strurl,
		  data:vdata,
		  type:"post",
		  success: function(k_data)
		  {
			 if(k_data.code ==200)
			  {
			  	
			  	//班级进入追踪记录
				 if(!isNull($("#visitmanlist1").val())){
				  var str=JSON.parse($("#visitmanlist1").val());
				  ClassMember.loadData(str.lq_class_id);
				  VisitMain.refresh();
				 }else{
				  VisitMain.refresh();
				 }
				layer.close(VisitMain.showaddDialogStatus); 
			  }else
			  {
				 layer.alert("错误:" +k_data.message);
			  }
		  }
	  });
}

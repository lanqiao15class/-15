JOBVACANCY={}
JOBVACANCY.init=function(){
 	var JobVacancyList= $("#JobVacancyList").val();	
	console.log("       ssss    "+JobVacancyList);
	//克隆模板
	JOBVACANCY.clone();
	//组合数据
	JOBVACANCY. ergodic(JobVacancyList);
	//设置表格高度
	JOBVACANCY.setTableHeight();
	
	
}



/***************按钮事件********************************/
JOBVACANCY.click=function(){
	//查看详情
	
	$(".jobvacancybutxq").on("click",function(){
		var id=$(this).attr("jobvacancybutxq");
		//调用后台查看职位详细信息
		$.ajax({
			type: "POST",
			url:basePath+"/jobVacancy/showJobDetails.do",
			data:"jobid="+id,
			dataType:"json",
			success: function(rdata) {
				console.log("    123123    "+JSON.stringify(rdata));
				
					if(rdata==""){
						layer.msg('该公司没有详细信息', {time: 5000, icon:2});	
					}else{
						var html=JOBVACANCY.showWin.clone();
						for(var key2 in rdata){
							html.find("#jkgszy"+key2).text(rdata[key2]);
					 	}
						layer.open({
						  type: 1, //Page层类型
						  skin: 'demo-class',
						  area: ['450px', '480px']
						  ,title: [ '招聘详细信息'],
						  shade: 0.6 //遮罩透明度
						  ,anim: 8 //0-6的动画形式，-1不开启
						  ,content:html.html()
						}); 
					}
				
			}
		});
		
	})
	//报名
	$(".jobvacancybutton").on("click",function(){
		var id=$(this).attr("jobvacancybutton");
		console.log("         "+id);
		if(id==null||id=="undefined"){
			
		}else{
			//调用后台报名
			$.ajax({
				type: "POST",
				url:basePath+"/jobVacancy/signUp.do",
				data:"jobid="+id,
				dataType:"json",
				success: function(rdata) {
					console.log("    123123    "+JSON.stringify(rdata));
					
					if(rdata.code=="200"){
						layer.msg('报名成功，等待审核', {time: 2000, icon:1});	
						JOBVACANCY.list();
					}else  if(rdata.code=="401") {
					layer.msg('网络不好，请稍后再试', {time: 5000, icon:2});	
					}else if(rdata.code=="402"){
					layer.msg('报名已截止！', {time: 5000, icon:2});	
					}else if(rdata.code=="403"){
					layer.msg('报名时间冲突！', {time: 5000, icon:2});	
					}else{
					layer.msg('系统错误，请稍后再试！', {time: 5000, icon:2});	
					}
					
				}
			});
		
		}
		
	})
}

/***************按钮事件********************************/



JOBVACANCY.list=function(){
	//调用后台删除部门
			$.ajax({
				type: "POST",
				url:basePath+"/jobVacancy/showJob1.do",
				dataType:"json",
				success: function(rdata) {
					console.log("    123123    "+JSON.stringify(rdata));
					//删除页面上的模板
					$("#jobvacancyUl").empty();
					JOBVACANCY. ergodic(JSON.stringify(rdata));
				}
			});
}
/**************************************克隆模板*******************************/
JOBVACANCY.liTop="";
JOBVACANCY.li="";
JOBVACANCY.showWin="";
JOBVACANCY.clone=function(){
	JOBVACANCY.showWin=$("#jobvacancyialoglayer").clone();
	JOBVACANCY.liTop=$("#jobvacancyUl li").first();
	//console.log("JOBVACANCY.liTop             "+JOBVACANCY.liTop.html());
	JOBVACANCY.li=$("#jobvacancyUl li").eq(1);
//	console.log("JOBVACANCY.li             "+JOBVACANCY.li.html());
	//删除页面上的模板
	$("#jobvacancyUl").empty();

}
/**************************************克隆模板*******************************/


/**************************************遍历数据显示在页面上*******************************/
/*JOBVACANCY.liTop="";
JOBVACANCY.li="";*/
JOBVACANCY. ergodic=function(JobVacancyList){
	var JobVacancyListJson="";
	if(!isNull(JobVacancyList)){
		if(JobVacancyList!="1"){
			JobVacancyListJson=JSON.parse(JobVacancyList);
				//遍历数据
				for(var key in JobVacancyListJson){
					var liTop=JOBVACANCY.liTop.clone();
					liTop.find(".jobvacancylispan").text(key);
					var html="<li class='jobvacancyli'>"+liTop.html();
					 
					for(var key1 in JobVacancyListJson[key]){
						var   data=JobVacancyListJson[key][key1];
						var li=JOBVACANCY.li.clone();
						for(var key2 in data){
						//	console.log("key2     " +key2+"           "+data[key2]);
							li.find("#jobvacancy"+key2).text(data[key2]);
						  
					 	}
					 	//未报名
				 		if(data.status==0){
					  		li.find("#jobvacancybut").text("报名中");
					  		li.find("#jobvacancybutton").attr("jobvacancybutton",data.id);
					  		}else  if(data.status==3){
					  		li.find("#jobvacancybut").text("审核中");
					  	}else  if(data.status==2){
					  		li.find("#jobvacancybut").text("报名拒绝");
					  		li.find("#jobvacancybutton").css("background","#fb584c");
					  	}else if(data.status==1){
					  		li.find("#jobvacancybut").text("报名成功");
					  		li.find("#jobvacancybutton").css("background","#56af5b");
					  	}
					  	li.find("#jobvacancybutxq").attr("jobvacancybutxq",data.id);
					 	 html=html+"<li class='first'>"+li.html()+"</li>";
					//	console.log("    ssss                 "+html)
					}
					html=html+"</li>";
					//console.log("    ssss                 "+html);
					$("#jobvacancyUl").append(html);
				}
			JOBVACANCY.click();	
		}else{
			layer.msg("您没有进入求职阶段，好好学习，天天向上",{time:86400000});
		}
		
	}else{
		layer.msg("您目前没有招聘企业信息！",{time:86400000});
	}
}
/*************************************遍历数据显示在页面上*******************************/





//设置表格高度
JOBVACANCY.setTableHeight=function(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
};	

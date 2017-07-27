/** 点击查看班级详情(获取数据) */
function classInfoByClassId(classId){
	 $.ajax({
		  url:basePath+'/lqClass/goClassInfo.do',
		  notloading:false,//是否显示加载层.
		  data:{"classId":classId},
		  type:"post",
		  dataType:"html",
		  success: function(data){
			  classInfoDialogue(data);
		  }
	 });
}

/** 打开班级详情弹窗 */
function classInfoDialogue(data){
	//1.打开窗口
	var dialogue_height=$(".content").height(),
	  	 dialogue_width=$(document).width()/3;
	layer.open({
		  type: 1, 
		  title: ['班级详情'],
		  skin: 'demo-class',
		  offset: 'rb',
		  shade: 0,
		  anim:5,
		  area: ['740px', dialogue_height+'px'],
		  content: data //这里content是一个普通的String
	});
	$(".cbox-rel").css({"height":$(".cbox-rel").height()-30});
	
}


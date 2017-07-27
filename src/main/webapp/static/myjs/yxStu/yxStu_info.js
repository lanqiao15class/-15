
/** 点击查看学员详情(获取数据) */
function stuInfoDialogue(userId,qx){
	 //1.关闭当前弹框
	 layer.closeAll();
	 $.ajax({
		  url:basePath+'/student/goYxStuInfo.do',
		  notloading:false,//是否显示加载层.
		  data:{"userId":userId,"qx":qx},
		  type:"post",
		  dataType:"html",
		  success: function(data){
			  dialogOpen(data);
		  }
	 });
}

/** 打开学员详情弹窗 */
function dialogOpen(data){
	//1.打开窗口
	 var dialogue_height=$(".content").height(),
	  	 dialogue_width=$(document).width()/3;
	layer.open({
		  type: 1, 
		  title: ['学员详情'],
		  skin: 'demo-class',
		  offset: 'rb',
		  shade: 0,
		  anim:5,
		  area: ['740px', dialogue_height+'px'],
		  content: data //这里content是一个普通的String
	});
	$(".cbox-rel").css({"height":$(".cbox-rel").height()-30});
	
}


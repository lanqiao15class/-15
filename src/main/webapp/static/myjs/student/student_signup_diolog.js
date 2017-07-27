
/** 点击查看学员详情(获取数据) */
function goSignUpBox(){
	 $.ajax({
		  url:basePath+'/signup/goSignUp.do',
		  notloading:false,//是否显示加载层.
		  data:{},
		  type:"post",
		  dataType:"html",
		  success: function(data){
			  signUpBox(data);
		  }
	 });
}

/** 填写报名信息弹框 */
function signUpBox(data){
	var dialogue_height=$(".content").height(),
		dialogue_width=$(document).width()-50;
	layer.open({
	  type: 1, 
	  title: ['填写报名信息'],
	  skin: 'demo-class',
	  offset: 'rb',
	  shade: 0,
	  anim:5,
	  area:  [dialogue_width+'px', dialogue_height+'px'],
	  content: data //这里content是一个普通的String
	});
}

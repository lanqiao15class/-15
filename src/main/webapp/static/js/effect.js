$(document).ready(function() {
	
	//设置表格部分的高度
	setTableHeight();
	
    //点击一级菜单
	$(".nav-line-row").click(function(){
		if($(".nav-sub").is(":animated")){
			return false;
		}
		if(!$(this).parents("dl").hasClass("current")){
			$(this).parents("dl").addClass("current");
			$(this).parents("dl").find(".nav-sub").slideDown(300);
		}else {
			$(this).parents("dl").removeClass("current");
			$(this).parents("dl").find(".nav-sub").slideUp(300);
		}
	});
	//点二级菜单
	$(".nav-sub a").click(function(){
		$(".nav-sub a").removeClass("current");
		$(this).addClass("current");
	});
	//菜单显示隐藏
	$(document).on("mouseenter",".nav-left",function(){
		if($(this).is(":animated")) $(this).stop();
		$(".show").show();
		$(".hide").hide();
		$(this).animate({"width":"200px"},200);
	});
	$(document).on("mouseleave",".nav-left",function(){
		if($(this).is(":animated")) $(this).stop();
		$(".show").hide();
		$(".hide").css({"display":"block"});
		$(this).animate({"width":"50px"},200);
	});
	
	/*$(".nav-left").hover(function(){
		if($(this).is(":animated")) $(this).stop();
		$(".show").show();
		$(".hide").hide();
		$(this).animate({"width":"180px"},200);
	},function(){
		if($(this).is(":animated")) $(this).stop();
		$(".show").hide();
		$(".hide").css({"display":"block"});
		$(this).animate({"width":"50px"},200);
		
	})*/
	//用户部分名显示隐藏
	$(".userInfo").hover(function(){
		$(".optionuserinfo").show();
	});
	$(".userInfo").mouseleave(function(){
		$(".optionuserinfo").hide();
	});
	//注册页面切换效果
	$(document).on("click",".tab-change a",function(){
		var _index=$(this).index(),
			num=$(".tab-change a:last").index()+1;
		$(this).addClass("curr").siblings("a").removeClass("curr");
		$(".tab_active").animate({left:_index*100/num+'%'})
		$(".tab-inner").eq(_index).show().siblings(".tab-inner").hide();
	});
	
	/*$(".tab-change a").click(function(){
		var _index=$(this).index(),
			num=$(".tab-change a:last").index()+1;
		$(this).addClass("curr").siblings("a").removeClass("curr");
		$(".tab_active").animate({left:_index*100/num+'%'})
		$(".tab-inner").eq(_index).show().siblings(".tab-inner").hide();
	});*/
	
	//筛选查询的更多筛选按钮点击事件
	$(".much-more").click(function(){
		$(this).hide();
		$(".put-away").show();
		$(this).parents(".query-box-parent").find(".query-box").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").hide();
		setTableHeight();
	});
	$(".put-away").click(function(){
		$(this).hide();
		$(".much-more").show();
		$(this).parents(".query-box-parent").find(".query-box").hide();
		$(this).parents(".query-box-parent").find(".query-box:first").show();
		$(this).parents(".query-box-parent").find(".query-box:first").find(".search-btn").show();
		setTableHeight();
	});
	
	//点击编辑icon
	$(".able-edit").click(function(e){
		e.stopPropagation();
		var _this=$(this).parents(".line-item");
			$(".eidt-span").hide();
			$(".read-span").show();
			_this.find(".read-span").hide();
			_this.find(".eidt-span").show();
			if(_this.find(".eidt-span").children("input").length>0){
				_this.find(".eidt-span").children("input").focus();
			}else if(_this.find("select.scoolname").length>0){
				$('.scoolname').searchableSelect();
			}
	});
	$(".eidt-span").click(function(e){
		e.stopPropagation();
	});
	$(".eidt-span input[type='text']").blur(function(){
		_this=$(this).parents(".line-item");
		_this.find(".read-span").show();
		_this.find(".eidt-span").hide();
	});
	$("body").click(function(e){
		$(".read-span").show();
		$(".eidt-span").hide();
	});
	
	//查看更多按钮点击事件
	$(".sl-down-btn").on("click",function(){
		
		$(this).hide();
		$(this).parents(".con-ggcbox").find(".more-list-disply").slideDown(100);
		$(this).siblings(".sl-up-btn").show();
	});
	$(".sl-up-btn").on("click",function(){
		$(this).hide();
		$(this).parents(".con-ggcbox").find(".more-list-disply").slideUp(100);
		$(this).siblings(".sl-down-btn").show();
	});
	//tab切换
	$(document).on("click",".tabClick a",function(){
		var _this=$(this).parents(".contbox");
			$(this).parents(".tabClick").find("a").removeClass("curr");
			$(this).addClass("curr");
			_this.find(".con-ggcbox").hide();
			_this.find(".con-ggcbox").eq($(this).index()).show();
	});
	/*$(".tabClick a").click(function(){
		
		var _this=$(this).parents(".contbox");
			$(this).parents(".tabClick").find("a").removeClass("curr");
			$(this).addClass("curr");
			_this.find(".con-ggcbox").hide();
			_this.find(".con-ggcbox").eq($(this).index()).show();
	});*/
	//账号tab切换
	$(".tab-accont-change a").click(function(){
		$(this).addClass("curr").siblings("a").removeClass("curr");
		$(".accont-tabbox").eq($(this).index()).show().siblings(".accont-tabbox").hide();
	});
	$(".accont-menu a").click(function(){
		$(this).addClass("current").siblings("a").removeClass("current");
		$(".tab-accont-change a").eq($(this).index()).addClass("curr").siblings("a").removeClass("curr");
		$(".accont-tabbox").eq($(this).index()).show().siblings(".accont-tabbox").hide();
	});
	//添加学生
	$(".add-btn").click(function(){
		$(this).parent(".add-btn-box").find(".select-fangtan-people").show();
		
	});
	//删除学生
	$(".add-stu-box").on("click",".del-btn-icon",function(){
		$(this).parents(".oneObj-box").remove();
	});
	//访谈查询
	$("#fangtanSearch").change(function(){
		$(".ser-out-span").hide();
		var num=$('#fangtanSearch option:selected').val()-1;
		if(num>=0){
			$(".ser-out-span").eq(num).show();
		}
	});
	/*$("#chTime").change(function(){
		if($('#chTime option:selected').val()!='0'){
			console.log($('#chTime option:selected').val());
			$(".ch-time").css({"display":"inline-block","*display":"inline","zoom":"1"});
		}else {
			$(".ch-time").css({"display":"none"});
		}
	});
	$("#fangtanSearch").click();*/
});

/*菜单选中状态*/
selectMenuId('004')
function selectMenuId(id)
{
	//if(gl_selectmenu == id) return ;
	var _this=$("#"+id);
	console.log(!_this.parents("dl").hasClass("current"));
	if(!_this.parents("dl").hasClass("current")){
		$(".nav-menu dl").removeClass("current");
		_this.parents("dl").addClass("current");
		$(".nav-sub").slideUp(300);
		$(".nav-sub a").removeClass("current");
		_this.addClass("current");
		$("dl.current").find(".nav-sub").slideDown(300);
	}else {
		$(".nav-sub a").removeClass("current");
		_this.addClass("current");
		//gl_selectmenu = id;
	}
}

//财务报表导出
function exportDialog(){
	layer.open({
	  type: 1, 
	  title: ['选择报表月份'],
	  skin: 'demo-class',
	  shade: 0.6,
	  area: '400px',
	  anim:8,
	  content: $(".export-dialog") //这里content是一个普通的String
	});
}

//离职登记
function leaveRegistrationDio(){
	layer.open({
	  type: 1, 
	  title: ['离职登记'],
	  skin: 'demo-class',
	  shade: 0.6,
	  area: '520px',
	  anim:8,
	  content: $(".leave-registration") //这里content是一个普通的String
	});
}

//学员转班分班弹框
function divideBackDio(){
	layer.open({
	  type: 1, 
	  title: ['确认分班'],
	  skin: 'demo-class',
	  shade: 0.6,
	  area: '520px',
	  anim:8,
	  content: $(".divide-back-diolog") //这里content是一个普通的String
	});
}

//表格删除行
function delTableRow(obj){
	var obj=obj,
		_this=$(obj).parents(".slick-row");
		_this.remove();
}

//新建班级弹框
function createClass(){
	layer.open({
	  type: 1, 
	  title: ['新建班级'],
	  skin: 'demo-class',
	  shade: 0.6,
	  area: ['520px','652px'],
	  anim:8,
	  content: $(".create-new-class") //这里content是一个普通的String
	});
}

//设置学员状态弹框
function setStuStatusDialogue(){
	layer.open({
	  type: 1, 
	  title: ['设置学员状态'],
	  skin: 'demo-class',
	  shade: 0.6,
	  area: '520px',
	  anim:8,
	  content: $(".set-student-status") //这里content是一个普通的String
	});
}

//设置班级状态弹框
function setClassStatusDialogue(){
	layer.open({
	  type: 1, 
	  title: ['设置班级状态'],
	  skin: 'demo-class',
	  shade: 0.6,
	  area: '520px',
	  anim:8,
	  content: $(".set-class-status") //这里content是一个普通的String
	});
}

//班级学员列表
function classOfStudent(){
	var dialogue_height=$(".content").height(),
		dialogue_width=$(document).width()-50;
	layer.open({
	  type: 1, 
	  title: ['班级学员列表'],
	  skin: 'demo-class',
	  offset: 'rb',
	  shade: 0,
	  anim:5,
	  area:  [dialogue_width+'px', dialogue_height+'px'],
	  content: $(".the-classof-student") //这里content是一个普通的String
	});
}

//班级学员列表
function classOfStudent(){
	var dialogue_height=$(".content").height(),
		dialogue_width=$(document).width()-50;
	layer.open({
	  type: 1, 
	  title: ['班级学员列表'],
	  skin: 'demo-class',
	  offset: 'rb',
	  shade: 0,
	  anim:5,
	  area:  [dialogue_width+'px', dialogue_height+'px'],
	  content: $(".the-classof-student") //这里content是一个普通的String
	});
}

//添加减免记录
function addWaiverRecord(){
	var dialogue_height=$(".content").height(),
		dialogue_width=$(document).width()-50;
	layer.open({
	  type: 1, 
	  title: ['添加减免记录'],
	  skin: 'demo-class',
	  offset: 'rb',
	  shade: 0,
	  anim:5,
	  area:  [dialogue_width+'px', dialogue_height+'px'],
	  content: $(".add-waiver-record") //这里content是一个普通的String
	});
	$("#myGrid01").height($(".table-out-rel").height());
	$('.scoolname').searchableSelect();
}
//填写报名信息弹框
function signUpBox(){
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
	  content: $(".sign-up-box") //这里content是一个普通的String
	});
}

//入职登记弹框
function entryRegistration(){
	var dialogue_height=$(".content").height(),
		dialogue_width=$(document).width()-50;
	layer.open({
	  type: 1, 
	  title: ['入职登记'],
	  skin: 'demo-class',
	  offset: 'rb',
	  shade: 0,
	  anim:5,
	  area:  [dialogue_width+'px', dialogue_height+'px'],
	  content: $(".entry-registration-box") //这里content是一个普通的String
	});
	$("#myGrid01").height($(".table-out-rel").height());	
}

//访ajax请求页面
function loadPage(strurl,idcontant){
  $.ajax({dataType:"html",
  url:strurl,
  canback:true,
  type:"post",
  success: function(data){
	  $("#"+idcontant).html(data);
	  }
  });
}

//学员报名审核弹框
function auditStuDialogue(){
	var dialogue_height=$(".content").height(),
		dialogue_width=$(document).width();
	layer.open({
	  type: 1, 
	  title: ['学员报名审核'],
	  skin: 'demo-class',
	  offset: 'rb',
	  shade: 0,
	  anim:5,
	  area: [1140+'px', dialogue_height+'px'],
	  content: $(".audit-stu-dialog") //这里content是一个普通的String
	});
	
	$(".cbox-rel").css({"height":dialogue_height-185});
	$('.scoolname').searchableSelect();
}

//查看学员详情
function stuInfoDialogue(){
	var dialogue_height=$(".content").height();
	layer.open({
	  type: 1, 
	  title: ['学员详情'],
	  skin: 'demo-class',
	  offset: 'rb',
	  shade: 0,
	  anim:5,
	  area: ['740px', dialogue_height+'px'],
	  content: $(".stu-info-dialog") //这里content是一个普通的String
	});
	$(".cbox-rel").css({"height":$(".cbox-rel").height()-30});
}

//创建班级记录弹框
function createClassRecord(){
	layer.open({
	  type: 1, 
	  title: ['创建班级记录'],
	  skin: 'demo-class',
	  shade: 0.6,
	  area: '520px',
	  anim:8,
	  content: $(".create-class-record") //这里content是一个普通的String
	});
}

//查看班级详情
function classInfoDialogue(){
	var dialogue_height=$(".content").height();
	layer.open({
	  type: 1, 
	  title: ['学员详情'],
	  skin: 'demo-class',
	  offset: 'rb',
	  shade: 0,
	  anim:5,
	  area: ['740px', dialogue_height+'px'],
	  content: $(".class-info-dialog") //这里content是一个普通的String
	});
	$(".cbox-rel").css({"height":$(".cbox-rel").height()-30});
}

//添加访谈记录
function addInterRecode(){
	layer.open({
	  type: 1, 
	  title: ['创建访谈记录'],
	  skin: 'demo-class',
	  shade: 0.6,
	  anim:8,
	  area: ['610px','620px'],
	  content: $(".add-inter-recode") //这里content是一个普通的String
	});
	$('.scoolname22').searchableSelect();
	$('.scoolname11').searchableSelect({
			afterSelectItem: function(sval,stext){
				console.log("selectid:"+sval+" text:" + stext);
			 	$(".scoolname11").val(sval);
				if(sval!=0){
					var _name='<a href="javascript:void(0);" class="oneObj-box"><span class="one-people-name">'+stext+'</span><i class="del-btn-icon">╳</i></a>'
					$(".add-stu-box").append(_name);
					$(".select-fangtan-people").hide();
				}
		 	}
		});
}

//新增意向学员
function addStuRegistration(){
	var body_height=$("body").height();
	layer.open({
	  type: 1, 
	  title: ['新增意向学员'],
	  skin: 'demo-class',
	  shade: 0.6,
	  anim:8,
	  area: ['600px', '650'+'px'],
	  content: $(".stu-registration") //这里content是一个普通的String
	});
}

function setTableHeight(){
	$(".table-scroll-parent").css({"height":$(".inner-reletive").height()-$(".tit-h1-box").height()-$(".tit-h2").height()-$(".query-opt-coopration").height()});
}

/*加载中效果*/
function loadingopen(){
	if($(".loadinglayer").length>0){
		loadingclose();
	}
	var loadingBoxhtml='<div class="loadinglayer" id="loadingBox"><div class="loadingpic"></div></div>';
		$("body .main-right .inner-box").append(loadingBoxhtml);
		showBox('loadingBox');
}
function loadingclose(){
	$(".loadinglayer").hide().remove();
}
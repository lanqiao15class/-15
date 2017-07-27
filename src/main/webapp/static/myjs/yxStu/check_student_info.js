CHECK_STUDENT_INFO = function(){};

//获取上下文路径
CHECK_STUDENT_INFO.basePath = basePath;

//validate对象
CHECK_STUDENT_INFO.validateObj = null;


//未通审核的页面数据变化
CHECK_STUDENT_INFO.noPassCheck = function (){
	$("#tips").html("审核不通过");//按钮提示字
//	$("#submitCheckBtn").attr("type","button");//使按钮属性变成button（submit会触发validate，产生异常）
	$("div.showOrHide").hide();//隐藏部分选项
	$("#pass").val("noPass");//设定是否通过标记
};
//通过审核的页面数据变化
CHECK_STUDENT_INFO.passCheck = function (X){
	$("#tips").html("审核通过");//按钮提示字
//	$("#submitCheckBtn").attr("type","submit");//使按钮属性变成submit（button不会触发validate，产生异常）
	$("#pass").val("pass");//设定是否通过标记
	$("div.showOrHide").show();//显示部分选项
	$("input[name='hasFavour'][value=0]").prop("checked","checked");//还原是否有减免按钮
	if(X == 'A'){//已缴纳
		$("div.TPHJShowOrHide").hide();//关于特批后缴的字段隐藏
		$("div.YJNShowOrHide").show();//关于已缴纳的字段显示
	}else{//特批延迟缴纳
		$("div.YJNShowOrHide").hide();//关于已缴纳的字段隐藏
		$("div.TPHJShowOrHide").show();//关于特批后缴的字段显示
	}
};


//初始化加载
/**=====================================init=============================================== */

CHECK_STUDENT_INFO.init = function(){
	$("input[type=radio][value="+gl_paidStatus.ALLPAY+"]").attr("checked","checked");//全部支付选中
	
	//1，根据选择的radio，控制部分页面的显示与隐藏，以及提交方式
	$("div.okOrNot").find("input[type=radio]").on("change",function(){
		$(".error-tips").text("");//清空错误提示
		var isRightName = $("input[name=isRightName]:checked").val();
		var isAvaiable = $("input[name=isAvaiable]:checked").val();
		if(isRightName == 0){//信息相符
			if(isAvaiable == gl_paidStatus.NOTPAY){//未缴纳-----未通过
				CHECK_STUDENT_INFO.noPassCheck();
			}else if(isAvaiable == gl_paidStatus.ALLPAY){//已缴纳----通过
				CHECK_STUDENT_INFO.passCheck('A');
			}else{//特批延迟缴纳-----通过
				CHECK_STUDENT_INFO.passCheck('B');
			}
		}else{//信息不符----未通过
			CHECK_STUDENT_INFO.noPassCheck();
		}
		CHECK_STUDENT_INFO.getIgnore();//获取忽略对象
	});

	//
	
	//2，如果课程类别没有记录，那么按钮不可用且置灰(暂时关闭，老师添加入口已关闭)
//	if($("#courseType").val() == ""){
//		layer.msg("学员课程类别未设置，暂时无法进行审核");
////		$("#submitCheckBtn").attr("disabled",true).removeClass("btn-blue").addClass("btn-grey");//前端提示，后端限制，不给提交
//	}
	
	//3，是否有减免按钮
	$("input[type=radio][name=hasFavour]").on("change",function(){
		CHECK_STUDENT_INFO.getIgnore();//获取忽略对象
		if($("input[type=radio][name=hasFavour]:checked").val()==0){
			$("div.JMShowOrHide").show();//关于特批后缴的字段隐藏
		}else{
			$("div.JMShowOrHide").hide();//关于特批后缴的字段隐藏
		}
	});
	
	//4,自动补全已缴纳事件[输入失去焦点触发、点击是否有减免触发]
	$("#BMFHTmoney,#BMFJMmoney").on("change",function(e){
		CHECK_STUDENT_INFO.getBMF();
	});
	$(".favourOrNot").on("change",function(){
		CHECK_STUDENT_INFO.getBMF();
	});
	
	//5,设置监听，处理各个老师的错误提示不消失问题
	$("div[class=searchable-select-holder").bind('DOMNodeInserted', function(e) {
//	    alert('element now contains: ' + $(e.target).html());
		if($(e.target).html().length > 0)
			$(e.target).parents('.line-item-ver').find('.error-tips').css({"opacity":"0"});
		else
			$(e.target).parents('.line-item-ver').find('.error-tips').css({"opacity":"1"});
	});
	
	
	//6,挂载validate
	CHECK_STUDENT_INFO.validateMethod();
	
};
/**=====================================init=============================================== */



/**======================================== 页面跳转========================================== */
CHECK_STUDENT_INFO.jumpUrl = function(url){
	$.ajax({
        type:"post",
        url:url,
        dataType:"html",
        success:function(html){
        	$("#contentDiv").html(html);
        },
        error:function(err) {
        }
    });
};
/**======================================== 页面跳转========================================== */



/** =====================================Form提交验证 =========================================*/
CHECK_STUDENT_INFO.submitCheckResult = function(passOrNot){
	var data = {"isAvaiable":$("input[name='isAvaiable']:checked").val(),
				"stuId":$("#stuId").val(),
				"isRightName":$("input[name='isRightName']:checked").val()
	};
	if(passOrNot == 'pass'){
		data = $("#checkStudentInfoForm").serialize();
		data += "&stuId="+$("#stuId").val();//学员id
	}
	
	//后端交互
	$.ajax({
        type:"post",
        url:basePath+"/student/submitCheckResult.do",
        data:data,
        dataType:"json",
        async:false,
        success:function(data){
        	if(data.code == 204){
        		layer.msg("审核失败");
        	}else{
        		if(data.code == 100){
        			layer.msg("数据不完整：学员尚未设置课程类别");
        		}else{
        			if(data.code == 200){
        				layer.msg("审核完成，审核状态是：未通过");
        			}else if(data.code == 201){
        				layer.msg("审核完成，审核状态是：通过");
        			}else if(data.code == 202){
        				layer.msg("审核完成，审核状态是：未缴费");
        			}else if(data.code == 203){
        				layer.msg("审核完成，审核状态是：补充资料");
        			}
//        			layer.closeAll();（会关闭我们的layer.msg()，注意放在layer.msg()上面）
        			$("#checkStudentInfoReturnBtn").trigger("click");
        			//刷新原网页(opType在原页面上，这是一个弹窗)
        			CHECK_STUDENT_INFO.jumpUrl(basePath+"/student/"+$("#opType").val()+"/goYxStu.do");
        		}
        	}
        },
        error:function(err) {
        	layer.msg("审核出现错误");
        }
    });
};

/** =====================================Form提交验证 =========================================*/



/**====================================显示弹窗==============================================**/
CHECK_STUDENT_INFO.loadPage = function(stuId){
	var url = CHECK_STUDENT_INFO.basePath+"/student/showCheckStuInfoDialog.do";
	$.ajax({
		  type:"post",
		  url:url,
		  notloading:false,//是否显示加载层.
		  data:{"stuId":stuId},
		  dataType:"html",
		  success: function(html){
			  CHECK_STUDENT_INFO.showDialog(html);
			  //加载左侧鸡娃哥的信息页面
			  $.ajax({				  
				  url:basePath+'/student/goYxStuInfo.do',
				  notloading:false,//是否显示加载层.
				  data:{"userId":stuId},
				  type:"post",
				  dataType:"html",
				  success: function(htmlSecond){
					  $("#studentInfoInclude").html(htmlSecond);
					  //隐藏按钮
					  $("#hideTagA").find("a").not("[name=jbxx]").not("[name=lxfs]").hide();
				  }
			 });
		  }
	  });
};
/**=====================================显示弹窗==============================================**/

/**=====================================加载弹窗============================================== */
CHECK_STUDENT_INFO.showDialog = function(html){
	/*layer.open({
	  type: 1, 
	  title: ['学员报名审核'],
	  skin: 'demo-class',
	  shade: 0.6,
	  anim:8,
	  area: ['1200px', '650'+'px'],
	  content: html //这里content是一个普通的String
	});*/
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
	  content: html //这里content是一个普通的String
	});
	$(".cbox-rel").css({"height":dialogue_height-185});
};
/**=====================================加载弹窗============================================== */

/**缴纳报名费自动补全*/
CHECK_STUDENT_INFO.getBMF = function(){
	if($("#BMFJMmoney").is(":visible")){//减免金额可见
		if(!isNaN($("#BMFHTmoney").val()) && !isNaN($("#BMFJMmoney").val())){//合法数字
			if(parseInt($("#BMFHTmoney").val())>0 && parseInt($("#BMFJMmoney").val())>0){//都大于0
				if(parseInt($("#BMFHTmoney").val()) >= parseInt($("#BMFJMmoney").val())){//后台金额大于减免
					$("#BMFmoney").val(parseInt($("#BMFHTmoney").val()) - parseInt($("#BMFJMmoney").val()));
				}else{
					$(this).val("");$("#BMFmoney").val("");
					layer.msg("减免金额不能大于合同金额");
				}
			}
		}
	}else{
		if(!isNaN($("#BMFHTmoney").val()) && parseInt($("#BMFHTmoney").val())>0){//合法数字大于0
					$("#BMFmoney").val(parseInt($("#BMFHTmoney").val()));
			}
	}
};

/** validate */
CHECK_STUDENT_INFO.validateMethod = function(){
	//减免不能大于合同金额
	jQuery.validator.addMethod("moneyCheck",function(value,element){
		if($("#BMFHTmoney").val() == "" || parseInt($("#BMFHTmoney").val()) >= parseInt($("#BMFJMmoney").val())){
			return true;
		}else{
			return false;
		}
	},"减免金额不能大于合同金额");
	
	//提交validate验证
	CHECK_STUDENT_INFO.validateObj = $("#checkStudentInfoForm").validate({
		  ignore:"#TPHJ_teacher_name,#TPHJremark",
		  onfocusout: function(element){//怪异的时间不按套路出牌
			  if($(element).attr("name")=='BMFtime')
				  if($(element).val().length>0)
					  $(element).parents('.line-item-ver').find('.error-tips').html("");
				  else
					  $(element).parents('.line-item-ver').find('.error-tips').html("请选择报名时间");
			},
		  onfocusin: false,
		  rules:{
			  BMFtime:"required",
			  JBR_teacher_name:"required",
			  HTmoney:{
				  required : true,
					digits : true,
					min : 1
			  },
			  QYZJ_teacher_name:"required",
			  ZSJL_teacher_name:"required",
			  KCGW_teacher_name:"required",
			  BMFHTmoney:{
				  required : true,
					digits : true,
					min : 1,
			  },
			  BMFJMmoney:{
				  required : true,
					digits : true,
					min : 1,
					moneyCheck:true,
			  },
			  JM_teacher_name:"required",
			  BMFJMremark:{
				  required:true,
				  maxlength:100,
			  },
			  TPHJ_teacher_name:"required",
			  TPHJremark:{
				  required:true,
				  maxlength:100,
			  },
			  //*****************rules*****************
		  },
		  messages:{
			  BMFtime:"请选择报名时间",
			  JBR_teacher_name:"请选择经办人",
			  HTmoney:{
				  required : "请填写实训费合同金额",
					digits : "请输入合法的数字",
					min :"请输入大于0的值"
			  },
			  QYZJ_teacher_name:"请选择区域总监",
			  ZSJL_teacher_name:"请选择招生经理",
			  KCGW_teacher_name:"请选择课程顾问",
			  BMFHTmoney:{
				  required : "请填写报名费合同金额",
					digits : "请输入合法的数字",
					min : "请输入大于0的值",
			  },
			  BMFJMmoney:{
				  required : "请填写报名费减免金额",
					digits : "请输入合法的数字",
					min : "请输入大于0的值",
//					moneyCheck:true,
			  },
			  JM_teacher_name:"请选择报名费减免审批人",
			  BMFJMremark:{
				  required:"请填写报名费减免说明",
				  maxlength:"字数限制在100以内",
			  },
			  TPHJ_teacher_name:"请填写特批后缴审批人",
			  TPHJremark:{
				  required:"请填写特批后缴说明",
				  maxlength:"说明限制在100字符以内",
			  },
			  //****************messages******************
		  },
		  errorCallback:function(element){
		  },
		  submitHandler:function(){
//			  alert("通过了");return;
			  //提交表单//
			  CHECK_STUDENT_INFO.submitCheckResult($("#pass").val());
		  },
		  errorPlacement : function(error, element) {
				element.parents('.line-item-ver').find('.error-tips').html("");
				error.appendTo(element.parents('.line-item-ver').find('.error-tips'));
			}
		    
	  });
};


/** 设定忽略验证的对象 */
CHECK_STUDENT_INFO.getIgnore = function(){
	//设置忽略验证的对象【有减免全缴纳、有减免特批延迟、无减免全缴纳、无减免特批延迟】
	var ignore = [];
	var hasFavour =  $("input[type=radio][name=hasFavour]:checked").val();
	var isAvaiable = $("input[type=radio][name=isAvaiable]:checked").val();
	var passOrNot = $("#pass").val();//获取是否通过的标记
	if(passOrNot == "pass"){
		if(isAvaiable == gl_paidStatus.ALLPAY){//全部支付
			ignore.push("#TPHJ_teacher_name,#TPHJremark");
		}else{//特批后缴
			ignore.push("#BMFtime,#BMFmoney,#JBR_teacher_name");
		}
		
		if(hasFavour == '0'){//有减免
			ignore.push();
		}else{//无减免
			ignore.push("#BMFJMmoney,#JM_teacher_name,#BMFJMremark");
		}
		
		CHECK_STUDENT_INFO.validateObj.settings.ignore = ignore.join(",");
	}else{
		CHECK_STUDENT_INFO.validateObj.settings.ignore = ":hidden";//忽略隐藏项目（默认）
	}
//	alert(CHECK_STUDENT_INFO.validateObj.settings.ignore);
};

/**清除表单数据和错误提示(暂时无用)*/
CHECK_STUDENT_INFO.clearForm =  function(){
	if($("#checkStudentInfoForm input[type='text']").length!=0){
		$("#checkStudentInfoForm input[type='text']").val('');
	}
	if($("#checkStudentInfoForm textarea").length!=0){
		$("#checkStudentInfoForm textarea").val('');
	}
	if($("#checkStudentInfoForm .select-search-box").length!=0){
		$("checkStudentInfoForm .searchable-select-close").click();
	}
};
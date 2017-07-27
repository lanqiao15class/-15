USERINFO=function(){};

/** 获取上下文路径 */
USERINFO.basePath = basePath;

/**==========初始化处理开始=======**/
USERINFO.init=function(){
	//点击编辑按钮
	USERINFO.editClick();
	//取消编辑按钮
	USERINFO.cancelEdit();
	//点击保存，修改用户基本信息
	$("#editUser").click(function(){
		USERINFO.updateUser();	
	});
	//禁用回车事件
    $("#nickname,#uploadHead").keydown(function(event){
        if (event.keyCode == 13){ 
        event.keyCod=0; return false;
         }
   });
	//上传图片
    $(document).on('change','#headImgFile',function(){
    	USERINFO.uploadPhoto();
    });
    
    //切换页面
    $("#account").click(function(){
    	$(this).addClass("curr").siblings().removeClass("curr");
    	$(".account-nav-sub a").eq($(this).index()).addClass("current").siblings().removeClass("current");
    	USERINFO.jumpUrl(USERINFO.basePath+"/account/accountPage.do");
    });
    $("#bind").click(function(){
    	$(this).addClass("curr").siblings().removeClass("curr");
     	$(".account-nav-sub a").eq($(this).index()).addClass("current").siblings().removeClass("current");
    	USERINFO.jumpUrl(USERINFO.basePath+"/goAccountBinding.do");
    });
    $("#userInfo").click(function(){
    	$(this).addClass("curr").siblings().removeClass("curr");
     	$(".account-nav-sub a").eq($(this).index()).addClass("current").siblings().removeClass("current");
    	USERINFO.jumpUrl(USERINFO.basePath+"/user/goUserInfo.do");
    });
};
/**==========初始化处理结束=======**/

/**========= 省改变触发开始 ======== */
USERINFO.change_province=function (objid){
	  if(!objid){//如果省为空
       $("#city").html("<option value=''> --请选择城市-- </option>");//清空市
	  }
	  $.ajax({
       type:"post",
       url: USERINFO.basePath+"/student/findCitysByProv.do",
       data:{"cid":objid},
       success:function(data){
              var jdata=eval("("+data+")");
              var html="<option value=''> --请选择城市-- </option>";
              for(var i=0;i<jdata.length;i++){
                    html+="<option value='"+jdata[i].cityId+"'>"+jdata[i].city+"</option>";
              }
              $("#city").html(html);
       } 
	  });	
};
/**========= 省改变触发结束 ======== */	
USERINFO.editClick=function(){
	$(".editor-btn").click(function(){
		$("input[name='nickname']").val($("#spanNickName").text());
		$("input[name='sex']:eq("+$("#sexTemp").val()+")").attr("checked",true).siblings().removeAttr('checked');
		//格式化日期 begin--z暂时不需要
	/*	var dateValue = new Date($("#birthTemp").val());
		$("input[name='birth']").val(USERINFO.dateTrans(dateValue)); */
		//格式化日期	end
		$("#province").val($("#provCodeTemp").val());
		$("#city").val($("#cityCodeTemp").val());
		$(".justlook").hide();
		$(".editor-btn").hide();//隐藏编辑按钮
		$(".editmodify").addClass("inh");
	});
};

USERINFO.cancelEdit=function(){
	$("#canceledit").click(function(){
		$(".justlook").show();
		$(".editor-btn").show();//显示编辑按钮
		$(".editmodify").removeClass("inh");	
		$(".item_tips").html("");
		USERINFO.change_province($("#provCodeTemp").val());
	});
};

//格式化日期
USERINFO.dateTrans=function dateTrans(date) {
	  var datetime="";
	   if(date!="Invalid Date"){
		   datetime= date.getFullYear()
	           + "-"// "年"
	           + ((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : "0"+ (date.getMonth() + 1))
	           + "-"// "月"
	           + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate());  
	   }

return datetime;
} ;

/**=========修改用户基本信息开始=========**/
USERINFO.updateUser=function(){
	$("#editUserForm").validate({
		  onfocusout: function(element) { //是否在获取焦点时验证
		       $(element).valid(); 
	       },
	      onkeyup:false,//是否在敲击键盘时验证
		  	  rules:{
		  		nickname:{
		  		    required:true,
		  		    inputCheck:true,
		  		    maxlength:16
		  		}
			},
			messages:{
				nickname:{
					required:"请输入昵称",
		  		    inputCheck:"昵称只能包含中文、英文、数字等字符!",
		  		    maxlength:"请输入不超过16字的昵称"
		  		}
			},
			submitHandler:function(){
				$.ajax({
		             type:"post",
		             url:USERINFO.basePath+"/user/updateUserInfo.do",
		             data:$("#editUserForm").serialize(),
		             dataType:"json",
		             success:function(data){
		            	layer.msg(data.message);
		          	   $(".editor-btn").show();//显示编辑按钮 
		          	   USERINFO.jumpUrl(USERINFO.basePath+"/user/goUserInfo.do");
		             }
				});	
		},errorPlacement : function(error, element) {
			error.appendTo(element.parents(".item_box").next('.item_tips'));
		}
	});
};
/**=========修改用户基本信息结束=========**/

/**==========图片上传开始============**/
USERINFO.uploadPhoto=function(){
	$.ajaxFileUpload({
		valid_extensions : ['jpg','png','jpeg','bmp'],
		url : USERINFO.basePath+"/user/uploadPhoto.do", //上传文件的服务端
		secureuri : false, //是否启用安全提交
		dataType : 'json', //数据类型 
		data:{imagetype:"face"},
		fileElementId : 'headImgFile', //表示文件域ID
		//提交成功后处理函数      html为返回值，status为执行的状态
		success : function(data, status) {
	    switch(data.code) {
        	case 201:
        		layer.msg("请选择文件");
        		break;
        	case 202:
        		layer.msg("图片太大，请上传小于的3M的图片");
        		break;
        	case 203:
        		layer.msg("图片上传出现异常");
        		break;
        	case 204:
        		layer.msg("请选择bmp,jpg,jpeg,png格式的图片");
        	default:
        		var resourcePath = $("#resourcePath").val();
        			$("#photoImg").attr("src",resourcePath + data.fileName);
        			$("#headImg").val(data.fileName);
        			gl_syncUserInfo();//同步更新session数据，左侧菜单
        		break;
	        }
			
		},
		//提交失败处理函数
		error : function(html, status, e) {
			alert("错误:"+status);
		}
	});
};
/**==========图片上传结束============**/

/**=========跳转页面开始========**/
USERINFO.jumpUrl=function(url){
    $.ajax({
       	type:"post",
 	   	url:url,
		dataType:"html",
 		success:function(html){
 			$("#contentDiv").html(html);
 			
 		}
		});
};
/**=========跳转页面结束========**/
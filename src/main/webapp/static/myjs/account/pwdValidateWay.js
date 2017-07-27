VALIDATEWAY=function(){};

VALIDATEWAY.basePath=basePath;

VALIDATEWAY.init=function(){
	//初始时设置手机或邮箱默认选中
	var loginEmail=$("#loginEmail").val();
	var originTel=$("#originTel").val();
	if((originTel!=null && originTel!="") && (loginEmail!=null && loginEmail!="")){
		$("#telCheck").attr("checked","checked");
   }else if((loginEmail!=null && loginEmail!="") && (originTel==null || originTel=="")){
	   $("#emailCheck").attr("checked","checked");
   }else{
	   $("#telCheck").attr("checked","checked");
   }
	$("label").click(function() {
		$(this).find("input[name='way']").attr("checked","checked").siblings().removeAttr("checked");
	});
	
	$("#nextBtn").click(function(){
		var way = 0;
		$("label").each(function(){
			if($(this).find("input[name='way']").attr("checked")=="checked") {
				way = $(this).attr("way");
				return;
			}
		});
		var data = {
			"way": way
		};
		$.ajax({
	        type:"post",
	        url:VALIDATEWAY.basePath+"/user/goModifyPwd.do",
	        data:data,
	        dataType:"html",
	        success:function(html){
	        	$("#contentDiv").html(html);
	        },
	        error:function(err) {
	        }
	    });
	});
	
	$("#returnBtn").click(function(){
		$.ajax({
	        type:"post",
	        url:VALIDATEWAY.basePath +"/user/goModifyPwd.do",
	        dataType:"html",
	        success:function(html){
	        	$("#contentDiv").html(html);
	        },
	        error:function(err) {
	        }
	    });
	});	
};


ACCOUNT_SECURITY = function(){};

//获取上下文路径
ACCOUNT_SECURITY.basePath = basePath;

ACCOUNT_SECURITY.init=function(){
	//切换页面
	$("#account").click(function(){
		$(this).addClass("curr").siblings().removeClass("curr");
		$(".account-nav-sub a").eq($(this).index()).addClass("current").siblings().removeClass("current");
		ACCOUNT_SECURITY.jumpUrl(USERINFO.basePath+"/account/accountPage.do");
	});
	$("#bind").click(function(){
		$(this).addClass("curr").siblings().removeClass("curr");
		$(".account-nav-sub a").eq($(this).index()).addClass("current").siblings().removeClass("current");
		ACCOUNT_SECURITY.jumpUrl(USERINFO.basePath+"/goAccountBinding.do");
	});
	$("#userInfo").click(function(){
		$(this).addClass("curr").siblings().removeClass("curr");
		$(".account-nav-sub a").eq($(this).index()).addClass("current").siblings().removeClass("current");
		ACCOUNT_SECURITY.jumpUrl(USERINFO.basePath+"/user/goUserInfo.do");
	});
};


/**
 * 页面跳转
 */
ACCOUNT_SECURITY.jumpUrl = function(url){
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
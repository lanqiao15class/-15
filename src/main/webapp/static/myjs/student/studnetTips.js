STUDENTTIPS = function(){};

/**
 * 获取学员审核和学员状态，根据情况显示表头提示弹窗
 */
STUDENTTIPS.init = function(){
	$.ajax({
        type:"post",
        url:basePath+"/student/studentTips.do",
        dataType:"html",
        success:function(html){
        	$("#studentTips").html(html);
        },
        error:function(err) {
        	layer.msg("系统异常");
        }
    });
	
};

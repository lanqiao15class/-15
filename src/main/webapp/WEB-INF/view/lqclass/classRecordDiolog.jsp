<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!--创建班级记录弹框开始-->
<form id="classRecordForm">
<input type="hidden" id="lqClassId" name="lqClassId" value="${lqClassId}">

	<div class="create-class-record mt20">
	    <div class="line-item-ver">
	    	<div class="clearfix">
	            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">记录内容：</i></label>
	            <textarea class="class-record" placeholder="请输入内容(限制200)" name="visitContent" id="visitContent" maxLength="201"></textarea>
	        </div>
	        <span class="error-tips"></span>
	    </div>
	    <div class="center mb20">
	    	<button class="btn btn-blue mr10" type="submit" id="recordSave">保存</button>
	        <button class="btn btn-wihte layui-layer-close" type="button">返回</button>
	    </div>
	</div>
</form>
<!--创建班级记录弹框结束-->

<script>
$(function(){
	CLASSRECORDDIOLOG.init();
});
</script>
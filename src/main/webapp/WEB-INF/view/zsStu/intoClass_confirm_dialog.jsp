<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<input type="hidden" id="type" value="${type }"/>

<!--学员分班转班开始-->
<div class="divide-back-diolog mt20">
	<div class="line-item-ver">
    	<div class="clearfix">
    		<c:if test="${count > 0}">
           	 您的“<span id="listName"></span>”中存在和班级课程类别不一致的学员:${count}人，分班后学员的课程类别将更新为 和班级一致的”${courseTypeLabel }“课程类别
    		</c:if>
        </div>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">
            <c:if test="${type == 'newIntoClass' }">新学员分班时间：</c:if>
            <c:if test="${type == 'changeClass' }">转班时间：</c:if>
            </i></label>
            <input type="text"  name="happentime" id="happentime"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"  />
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
            <textarea class="back-divide" placeholder="备注" id="remark"></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button type="button" class="btn btn-blue mr10" onclick="STUDENT_INTO_CLASS.submitIntoClass();">保存</button>
        <button type="button" class="btn btn-wihte layui-layer-close" id="returnSuccessBtn">返回</button>
    </div>
</div>
<!--学员分班转班结束-->

<script type="text/javascript">
	$(function(){
		var type = $("#type").val();
		if(type == "newIntoClass"){
			$("#listName").text("待分班学员列表");
		}else{
			$("#listName").text("待转班学员列表");
		}
	});
</script>
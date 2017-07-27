<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<input type="hidden" value="${ids }" id="ids">
<input type="hidden" value="${opType }" id="opType">
<input type="hidden" value="${newClassId }" id="newClassId">
<!--设置班级状态开始-->
<div class="set-class-status mt20">
    <div class="line-item-ver">
    	<div class="clearfix">
    		<c:choose>
    			<c:when test="${count > 0}">
		           	课程类别不一致的班级:${count}个，确定合并为 ${newClassName } 吗？班级合并后将无法撤销
    			</c:when>
    			<c:otherwise>
    				确定合并为 ${newClassName } 吗？班级合并后将无法撤销
    			</c:otherwise>
    		</c:choose>
        </div>
    </div>
    <br>
    <form id="mergeClassform">
	    <div class="line-item-ver">
	    	<div class="clearfix">
	            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">合并时间：</i></label>
	            <input type="text"  name="happenTime" id="happenTime"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"  />
	        </div>
	        <span class="error-tips"></span>
	    </div>
	    <div class="line-item-ver">
	    	<div class="clearfix">
	            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">已完成课时数量：</i></label>
	        </div>
	        <span class="error-tips"></span>
	    </div>
	    <c:forEach items="${list }" var="classVar">
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">${classVar.className }：</i></label>
		            <input class="courseCountClass" placeholder="请输入已完成课时数量" disableautocomplete autocomplete="off" type="text" id="${classVar.classId }" name="class${classVar.classId }">
		        </div>
		        <span class="error-tips"></span>
		    </div>
	    </c:forEach>
	    <div class="line-item-ver">
	    	<div class="clearfix">
	            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
	            <textarea class="status-appendix" placeholder="请输入备注" id="remark" name="remark"></textarea>
	        </div>
	        <span class="error-tips"></span>
	    </div>
	    <div class="center mb20">
	    	<button type="submit" class="btn btn-blue mr10" onclick="CLASSES_MERGE.submitMergeClass();">保存</button>
	        <button type="button" class="btn btn-wihte layui-layer-close" id="mergeClassDialogReturn">返回</button>
	    </div>
	  </form>
</div>
<!--设置班级状态结束-->
<script type="text/javascript">
	$(function(){
	});
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!--设置班级状态开始-->
<input type="hidden" value="${classname }" id="input_classname" />

<form id="classform1">
<input type="hidden" value="${classid }" name="classid" />

<div class="set-class-status mt20">
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">班级状态：</i></label>
            <select name="newstatus">
            <c:forEach var="item" items="${statuslist}" >
             <c:if test="${item.enable }">
             <option value="${item.value }" selected>${item.text }</option>
             </c:if>
             
              <c:if test="${ not item.enable }">
            	<option class="dischoose" disabled>${item.text }</option>
             </c:if>
            </c:forEach>
            
            </select>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">
  <c:choose> 
  <c:when test="${nowstatus == ClassStatus.INTHELECTURE}">   
     开课时间：
  </c:when>
  <c:when test="${nowstatus == ClassStatus.BEFORETHETRAININGSESSION}">   
	集训前结课时间：
  </c:when>
   <c:when test="${nowstatus == ClassStatus.BEFORETHETRAININGSESSION}">   
     集训开始时间：
  </c:when> 
   <c:when test="${nowstatus == ClassStatus.INTHETRAINING}">   
     集训开始时间：
  </c:when> 
   <c:when test="${nowstatus == ClassStatus.THEGRADUATION}">   
     结业时间：
  </c:when>
  <c:when test="${nowstatus == ClassStatus.INTHEEMPLOYMENT}">就业服务开始时间：</c:when>
  
</c:choose> 
            
            </i></label>
          	<input placeholder=""  type="text" name="happentime"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
     
        </div>
        <span class="error-tips"></span>
    </div>
    
<c:if test="${nowstatus == ClassStatus.THEGRADUATION}">   
     
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">拟就业开始时间：</i></label>
        	<input placeholder=""  type="text" name="jobbegintime"  disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
     
        </div>
        <span class="error-tips"></span>
    </div>
    
</c:if>
    
   <c:if test="${nowstatus != ClassStatus.INTHEEMPLOYMENT and nowstatus != ClassStatus.INTHETRAINING and nowstatus != ClassStatus.INTHELECTURE}">    
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">已完成课时数量：</i></label>
          <input name="finishcount" placeholder="请输入已完成课时数量" disableautocomplete autocomplete="off" type="text">
     
        </div>
        <span class="error-tips"></span>
    </div>
   </c:if> 
    
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备&#12288;&#12288;注：</i></label>
            
          <textarea name="remark" class="status-appendix" placeholder="内容(限制200)" maxlength=200></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button class="btn btn-blue mr10">保存</button>
        <button class="btn btn-wihte" type="button" onclick="UpdateClassStatus.closeDialog()">返回</button>
    </div>
</div>
</form>
<script>
  $(function(){
		var stitle = $("#input_classname").val();
		$("#input_classname").parents(".layui-layer").find(".layui-layer-title").append("--"+stitle);

	  UpdateClassStatus.init();
  });
</script>
<!--设置班级状态结束-->
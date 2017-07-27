<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!-- 数据 (resourceIds，防止删除学员后切换费用类型)-->
<input type="hidden" value="${ids }" id="resourceIds">
<input type="hidden" value="${ids }" id="ids">
<input type="hidden" value="${opType }" id="opType">
<input type="hidden" value="${serviceType }" id="serviceType">

<!--添加减免记录开始-->
<div class="add-waiver-record">
	<div class="waiver-box clearfix">
    <div class="rel-waiver-box">
    	<!--左侧表单开始-->
    	<div class="fee-form-box">
	        <form id="feeServiceForm">
	        	<div class="line-item-ver zzxRadio">
	                <div class="clearfix">
	                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">费用类型：</i></label>
	                    <c:forEach items="${payGoalList }" var="typeVar" varStatus="indexObj">
	                    	 <span class="other-radio-check">
	                    		 <input type="radio" name="feeType" value="${typeVar.value }" <c:if test="${indexObj.index == 0}">checked</c:if>>${typeVar.label }
	                    	 </span>
	                    </c:forEach>
	                </div>
	            </div>
	            <!-- 减免业务开始 -->
	            <c:if test="${serviceType == 'JIANMIAN' }">
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">本次减免金额：</i></label>
		                    <input class="val-info money-icon" disableautocomplete autocomplete="off" type="text" id="moneyChange" name="moneyChange">
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">减免发生日期：</i></label>
		                    <input type="text"  name="happenTime" id="happenTime"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})"  />
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">减免审批人：</i></label>
	                        <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="zzx_teacherSPR" />
						    </jsp:include>
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">减免说明：</i></label>
		                    <textarea class="waiver-back" placeholder="内容（限300字符）" id="remark" name="remark"></textarea>
		                </div>
	                <span class="error-tips"></span>
	            </div>
	            </c:if>
	            <!-- 减免业务结束-->
	            <!-- 回款业务开始 -->
	            <c:if test="${serviceType == 'HUIKUAN' }">
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">本次收费金额：</i></label>
		                    <input class="val-info money-icon" disableautocomplete autocomplete="off" type="text" id="moneyChange" name="moneyChange">
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">收费日期：</i></label>
		                    <input type="text"  name="happenTime" id="happenTime"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})"  />
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">收费方式：</i></label>
		                    <select id="payWay" name="payWay">
		                    	<option value="">请选择收费方式</option>
		                    	<c:forEach items="${payTypeList }" var="typeVar">
		                    		<option value="${typeVar.value }">${ typeVar.label}</option>
		                    	</c:forEach>
		                    </select>
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">经办人：</i></label>
		                    <span class="select-search-box">
		                       <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
									<jsp:param name="inputid" value="zzx_teacherJBR" />
							   </jsp:include>
		                    </span>
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">备注：</i></label>
		                    <textarea class="waiver-back" placeholder="内容（限300字符）" id="remark" name="remark"></textarea>
		                </div>
	                <span class="error-tips"></span>
	            </div>
	            </c:if>
	            <!-- 回款业务结束-->
	            <!-- 退费业务开始 -->
	            <c:if test="${serviceType == 'TUIFEI' }">
		           	<div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">退款金额：</i></label>
		                    <input class="val-info money-icon" disableautocomplete autocomplete="off" type="text" id="moneyChange" name="moneyChange">
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">退费日期：</i></label>
		                    <input type="text"  name="happenTime" id="happenTime"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})"  />
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">退费审批人：</i></label>
		                    <span class="select-search-box">
		                       <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
									<jsp:param name="inputid" value="zzx_teacherSPR" />
							   </jsp:include>
		                    </span>
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">经办人：</i></label>
		                    <span class="select-search-box">
		                       <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
									<jsp:param name="inputid" value="zzx_teacherJBR" />
							   </jsp:include>
		                    </span>
		                </div>
		                <span class="error-tips"></span>
		            </div>
		            <div class="line-item-ver">
		                <div class="clearfix">
		                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">退费说明：</i></label>
		                    <textarea class="waiver-back" placeholder="内容（限300字符）" id="remark" name="remark"></textarea>
		                </div>
	                <span class="error-tips"></span>
	            </div>
	            </c:if>
	            <!-- 退费业务结束 -->
	            
	            <div class="center">
	                <button type="submit" class="btn btn-blue mr10" id="saveFeeServiceBtn">保存</button>
	                <button type="button" class="btn btn-wihte layui-layer-close" id="feeServiceReturnBtn">返回</button>
	            </div>
	        </form>
        </div>
        <!--左侧表单结束-->
        
        <!--右侧表开始-->
        <div class="select-stu-fix">选中学员：<span id="studentCount"></span> 人</div>
        <div class="table-out-abs">
        	<div class="table-out-rel">
            	<div class="table" id="myGridFeeStudent" style="height:500px;"></div>
            </div>
        </div>
        <!--右侧表结束-->
    </div>
    </div>
</div>
<!--添加减免记录结束-->

<script>
$(function(){
// 	alert($(":radio[name=feeType]:checked").val());
// 	alert($("#serviceType").val());
// 	alert($("#ids").val());
	FEE_SERVICE_PAGE.init();
});
</script>
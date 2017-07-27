<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!--离职登记弹框开始-->
<div class="leave-registration">
	<form id="dimissionForm" autocomplete="off" novalidate enctype="multipart/form-data">
	<div class="form-leave-registration">
	<c:forEach items="${jobDetailList}" var="jd">
		<c:if test="${empty jd.dismiss_time}">
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">企业名称：</i></label>
		            <span class="only-read">${jd.company_name}</span>
		        </div>
		    </div>
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">企业性质：</i></label>
		            <span class="only-read">
		            	<c:forEach items="${cocompType}" var="ct">
                       		<c:if test="${ct.value==jd.company_type}">${ct.label}</c:if>
                       	</c:forEach>
		            </span>
		        </div>
		    </div>
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">企业规模：</i></label>
		            <span class="only-read">
		            	<c:forEach items="${cocompScale}" var="cs">
                       		<c:if test="${cs.value==jd.company_scale}">${cs.label}人</c:if>
                       	</c:forEach>
		            </span>
		        </div>
		    </div>
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">岗位名称：</i></label>
		            <span class="only-read">${jd.position_name}</span>
		        </div>
		    </div>
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">岗位性质：</i></label>
		            <span class="only-read">
		            	<c:forEach items="${inviteType}" var="it">
                      		<c:if test="${it.value==jd.position_type}">${it.label}</c:if>
                      	</c:forEach>
		            </span>
		        </div>
		    </div>
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">岗位薪资：</i></label>
		            <span class="only-read">${jd.position_salary}</span>
		        </div>
		    </div>
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">工作地点：</i></label>
		            <span class="only-read">${jd.city_id}</span>
		        </div>
		    </div>
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">就业方式：</i></label>
		            <span class="only-read">
		            	<c:forEach items="${jobFromTypeList}" var="jf">
                       		<c:if test="${jd.jobfrom_type==jf.value}">${jf.label}</c:if>
                       	</c:forEach>
		            </span>
		        </div>
		    </div>
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">入职时间：</i></label>
		            <span class="only-read"><fmt:formatDate value="${jd.entry_time}" pattern="yyyy-MM-dd"/></span>
		            <input type="hidden" id="rzTime" value="${jd.entry_time}"/>
		        </div>
		    </div>
		    <div class="line-item-ver">
		    	<div class="clearfix">
		            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">企业BD：</i></label>
		            <span class="only-read">${jd.teacher_id}</span>
		        </div>
		    </div>
		    <input type="hidden" name="jobId" value="${jd.job_id}"/>
   			<input type="hidden" name="userId" value="${jd.user_id}"/>
	    </c:if>
    </c:forEach>
    
    
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">离职时间：</i></label>
            <input name="dismissTime" id="dismissTime"  placeholder="请选择日期" type="text"   class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',isShowClear:true})"/>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">离职原因：</i></label>
            <textarea name="dismissReason" class="status-appendix" placeholder="请填写离职原因"></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    </div>
   
    <div class="center mb20">
    	<button class="btn btn-blue mr10" type="submit">保存</button>
        <button class="btn btn-wihte" type="button" onclick="Dimission.closeDialog();">返回</button>
    </div>
    </form>
    
</div>
<!--离职登记弹框结束-->
<script type="text/javascript"> 
$(function(){
	Dimission.init();
	
	$.validator.addMethod("compareDate",function(value,element){
	    var assigntime = $("#rzTime").val();
	    var deadlinetime = $("#dismissTime").val();
	    var reg = new RegExp('-','g');
	    assigntime = assigntime.replace(reg,'/');//正则替换
	    deadlinetime = deadlinetime.replace(reg,'/');
	    assigntime = new Date(parseInt(Date.parse(assigntime),10));
	    deadlinetime = new Date(parseInt(Date.parse(deadlinetime),10));
	    if(assigntime>=deadlinetime){
	        return false;
	    }else{
	        return true;
	    }
	},"<font color='#E47068'>结束日期必须大于开始日期</font>");

});






</script>

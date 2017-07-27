<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<input  type="hidden" id="visitmanlist" value='${manlist}'>
<input  type="hidden" id="visitmanlist1" value='${manlist1}'>
<!--添加访谈记录开始-->
<form id="addvisit_form" action="http://www.baidu.com/" >
<input  type="hidden" id="visitid" >
<div class="add-inter-recode mt20">
	<div class="line-item-ver">
    	<div class="clearfix">
    		<input type="hidden" value="" id="visit_student" name="visit_student">
            <label class="align-right-label fl"><i class="must-input-icon">*</i><i class="label-text">访谈对象：</i></label>
            <div class="ftr-box fl">
            	<div class="add-stu-box">
            	</div>
            	<span class="add-btn-box">
                	<a href="javascript:void(0);" class="add-btn"><i class="Hui-iconfont">&#xe600;</i><span class="ml10">添加</span></a>
                    <span class="select-fangtan-people">
                    

<span class="select-search-box" style="width:300px">
   <select id="visit_select_student"  name="visit_select_student" >
   </select>
</span>

                    </span>
                </span>
            </div>
        </div>
        <span class="error-tips" id="span_school_tip"></span>
    </div>
	<div class="line-item-ver c-gray">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">访谈对象所在院校：</i></label>
            <span class="people-school-box">
            </span>
        </div>
        <span class="error-tips" ></span>
    </div>
    <div class="line-item-ver c-gray">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">访谈人：</i></label>
            <span class="fangtan-person">${sessionScope.loginuser.realName }</span>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver c-gray">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">访谈人所在部门：</i></label>
            <span class="fangtan-person">${sessionScope.loginuser.deparment.depname }</span>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">访谈日期：</i></label>
            
            <input placeholder="请输入访谈日期" type="text" name="visittime" id="visittime" value="" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
            
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">访谈目的：</i></label>
            <textarea id="vgoal" name="vgoal" maxlength="100" class="fang-tan-aim" placeholder="请输入访谈记录"></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">谈话纪要：</i></label>
            <textarea id="vcontent" name="vcontent"  maxlength="500" class="fang-tan-record" placeholder="请输入访谈记录"></textarea>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="center mb20">
    	<button  type="submit" id="submit_save" class="btn btn-blue mr10">保存</button>
        <button id="cancel_bnt" class="btn btn-wihte">返回</button>
    </div>
</div>
</form>
<script>
$(function(){
	AddVisit.init();
	
});
</script>
<!--添加访谈记录结束-->

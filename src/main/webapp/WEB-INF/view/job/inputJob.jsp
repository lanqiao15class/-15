<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<div class="entry-registration-box">
	<!--表单提交部分开始-->
    <div class="entry-registration-abs">
    	<form id="inputjobformid">
    	<input type="hidden" name="userids" value="${userids }" />
        	<div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">企业名称：</i></label>
                    <input name="companyName" class="val-info" placeholder="" maxlength="32" disableautocomplete autocomplete="off" type="text">
                </div>
                <span class="error-tips"></span>
            </div>
            
            <div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">企业性质：</i></label>
                    <select name="companyType">
                    	<option value="">请选择</option>
                    	<c:forEach var="one" items="${comptype }" >
                        	<option value="${one.value }">${one.label }</option>
                        </c:forEach>
                        
                    </select>
                </div>
                <span class="error-tips"></span>
            </div>
            <div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">企业规模：</i></label>
                    <select name="companyScale">
                    	<option value="">请选择</option>
                    	<c:forEach var="one" items="${compscale }" >
                        	<option value="${one.value }">${one.label }</option>
                        </c:forEach>
                        
                  
                    </select>
                </div>
                <span class="error-tips"></span>
            </div>
            
               <div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">企业BD：</i></label>
     		<input type="hidden" value="" name="hideteacherId">
     		<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
				<jsp:param name="inputid" value="teacherId" />
				<jsp:param name="callback" value="InputJob.doTeacherChange" />
			</jsp:include>

                </div>
                <span id="span_teacher_tip" class="error-tips"></span>
            </div>
            
            
            <div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">岗位名称：</i></label>
                    <input name="positionName" class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text">
                </div>
                <span class="error-tips"></span>
            </div>
            <div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">岗位性质：</i></label>
                    <select name="positionType">
                    	<option value="">请选择</option>
                    		<c:forEach var="one" items="${posiontype }" >
                        	<option value="${one.value }">${one.label }</option>
                        </c:forEach>
                        
                    </select>
                </div>
                <span class="error-tips"></span>
            </div>
            <div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">岗位薪资：</i></label>
                    <input name="positionSalary" class="val-info post-salary" placeholder="" disableautocomplete autocomplete="off" type="text">
                </div>
                <span class="error-tips"></span>
            </div>
            <div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">工作省份：</i></label>
                   <select class="val-info" id="schoolProvCode" name="provId" onchange="SUBMIT_STUDENT_INFO.getCity();">
                        	<option value="">选择省份</option>     
                        	<c:forEach items="${provinceList }" var="provinceVar">
								<option value="${provinceVar.cityId }">${provinceVar.city}</option>                     	
                        	</c:forEach>
                        </select>
                </div>
                <span class="error-tips"></span>
            </div>
            <div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">工作市：</i></label>
                    <select class="val-info" id="schoolCityCode" name="cityId">
                    </select>
                    
                    
                </div>
                <span class="error-tips"></span>
            </div>
            <div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">就业方式：</i></label>
                    <span class="entry-way-check"><input type="radio" name="jobfromType" value="1" checked=""> 推荐就业</span>
                    <span class="entry-way-check ml10"><input type="radio" name="jobfromType" value="0" checked="">自主就业</span>
                </div>
                <span class="error-tips"></span>
            </div>
            <div class="line-item-ver">
                <div class="clearfix">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">入职时间：</i></label>
             	<input type="text"  name="entryTime" id="entryTime"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"  />
	              
                </div>
                <span class="error-tips"></span>
            </div>
         
        </form>
    </div>
    <!--表单提交部分结束-->
 <!--表单提交部分结束-->
    
    <!--入职登记已选学员表格开始-->
    <div class="had-selected-stu">已选学员：<span id="span_mancount">333</span>人</div>
    <div class="had-selected-table">
    	<div class="entry-table-rel">
        	<div class="table-entry">
            	<div id="myinputjobTable"></div>
            </div>
        </div>
    </div>
    <!--入职登记已选学员表格结束-->
	<div class="entry-fix-button">
    	<button class="btn btn-blue mr10" type="button" onclick="InputJob.submitClick()">保存</button>
        <button class="btn btn-wihte" type="button" onclick="InputJob.closeDialog()">返回</button>
    </div>
</div>
<script>
 $(function()
 {
	 InputJob.init($("#inputjobformid input[name=userids]").val());
	
	 
 });
 
</script>
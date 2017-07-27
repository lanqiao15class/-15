<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<input type="hidden" id="type" value="${type }"/>

<!--新建班级开始-->
<div class="create-new-class">
	<form class="clearfix" id="classAddForm">
        <div class="form-scroll">
            <div class="form-scroll-auto">
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">创建目的：</i></label>
                        <select name="classGoal">
							<option value="">请选择创建目的</option>
							<c:forEach items="${classGoalList }" var="goalVar">
								<option value="${goalVar.value }">${goalVar.label }</option>
							</c:forEach>
						</select>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">班级名称：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete="" autocomplete="off" type="text" name="className" id="className">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">班级一级类型：</i></label>
                        <select name="typePre" id="classTypePre">
                        	<option value="">请设置班级一级类型</option>
                        	<c:forEach items="${classTypePreList }" var="typeVar">
                        		<option value="${typeVar.value }">${typeVar.label }</option>
                        	</c:forEach>
                        </select>
                    </div>
                    <span class="error-tips"></span>
                </div>
                 <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">班级二级类型：</i></label>
                        <select name="typeReal" id="classTypeReal"></select>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">课程类别：</i></label>
                        <select name="courseType">
                        	<option value="">请选择课程类别</option>
                        	<c:forEach items="${courseTypeList }" var="courseVar">
                        		<option value="${courseVar.value }">${courseVar.label}</option>
                        	</c:forEach>
                        </select>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">拟开课日期：</i></label>
                        <input type="text"  name="startTime1" id="startTime1"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"  />
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">拟校园结课日期：</i></label>
                        <input type="text"  name="schoolTime1" id="schoolTime1"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"  />
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">拟结业日期：</i></label>
                        <input type="text"  name="graduateTime1" id="graduateTime1"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"  />
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">拟开班人数：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete="" autocomplete="off" type="text" name="stuCount">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">技术老师：</i></label>
                        <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
							<jsp:param name="inputid" value="comTeacherId" />
						</jsp:include>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">cep老师：</i></label>
                        <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
							<jsp:param name="inputid" value="cepTeacherId" />
						</jsp:include>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">职业经纪人：</i></label>
                        <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
							<jsp:param name="inputid" value="chrTeacherId" />
						</jsp:include>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">QQ群：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete="" autocomplete="off" type="text" name="qqGroup">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver monitor">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">班长：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete="" autocomplete="off" type="text" name="monitorId">
                    </div>
                    <span class="error-tips"></span>
                </div>
            </div>
        </div>
        <div class="center fix-btn-box">
            <button type="submit" class="btn btn-blue mr10" id="addClassBtn">保存</button>
            <button type="button" class="btn btn-wihte layui-layer-close" id="classAddReturnBtn">返回</button>
        </div>
    </form>
</div>
<!--新建班级结束-->

<script type="text/javascript">
	$(function(){
		CLASS_ADD_DIALOG.init();
	});
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!--右侧内容部分开始-->
<div class="content-inner-part">
	<div class="inner-reletive">
		<!--右侧标题部分开始-->
		<div class="tit-h1-box">
			<h1 class="tit-first">
				<span>班级管理</span><i class="gt-icon">&gt;</i><span class="curr">新建班级</span>
			</h1>
		</div>
		<!--右侧标题部分结束-->
		<!--右侧内容部分背景白色开始-->
		<div class="inner-white">
			<div class="tit-h2 clearfix">
				<div class="tab-change-inner fl">
					<a class="curr" href="javascript:void(0)">新增班级</a>
				</div>
				<!--<div class="fr btn-box-right">
					<button class="btn btn-green" onClick="addStuRegistration()"><i class="Hui-iconfont">&#xe600;</i><span class="ml10">新增意向学员</span></button>
				</div>-->
			</div>
			<div class="rel-create-class">
			<div class="create-class-page">
				<form class="clearfix" id="pageClassAddForm">
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
	                        	<c:if test="${courseVar.del_flag ==0}">
	                        		<option value="${courseVar.value }">${courseVar.label}</option>
	                        	</c:if>
	                        		
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
	                        <label class="align-right-label"><i class="label-text">职业经纪人：</i></label>
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
	                <div class="center mt15">
                    	<button type="submit" class="btn btn-blue mr10" onclick="CLASS_ADD.submitClassAdd();">保存</button>
                    </div>
				</form>
			</div>
			</div>
		</div>
	</div>
</div>
<!--右侧内容部分结束-->

<script type="text/javascript">
	$(function(){
		CLASS_ADD.init();
	});
</script>
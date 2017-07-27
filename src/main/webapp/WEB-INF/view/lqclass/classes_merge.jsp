<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<input type="hidden" value="${ids }" id="ids">
<input type="hidden" value="${opType }" id="opType"> 
<input type="hidden" id="type" value="mergeClass">
<!--右侧内容部分开始-->
<div class="content-inner-divide">
	<div class="inner-reletive">
		<!--右侧标题部分开始-->
		<div class="tit-h1-box">
			<h1 class="tit-first">
				<span>班级管理</span><i class="gt-icon">&gt;</i><span>班级合并</span><i class="gt-icon">&gt;</i><span class="curr">确认合班信息</span>
			</h1>
		</div>
		<!--右侧标题部分结束-->
		<!--右侧内容白色开始-->
		<div class="divide-inner-padding">
			<div class="choose-class-part clearfix">
				<span class="newcreate-class">
					<select id="classSelect">
<!-- 						<option value="-1">选择班级</option> -->
<%-- 						<c:forEach items="${classList }" var="classVar"> --%>
<%-- 							<option value="${classVar.classId }">${classVar.className }</option> --%>
<%-- 						</c:forEach> --%>
					</select>
				</span>
<!-- 				<a href="javascript:void(0);" class="refresh"><i class="icon-refresh Hui-iconfont">&#xe68f;</i><span class="refresh-intro">刷新</span></a> -->
				<button class="btn btn-green mr10" id="newClassClassMerge">新建班级</button>
				<span class="choose-class-tips">如果没有您需要的班级，请先新建班级后刷新选择新班级名称</span>
			</div>
		</div>
		<!--右侧内容白色结束-->
		<!--班级分班部分开始-->
		<div class="class-bottom-part">
			<div class="inner-divide-rel">
				<!--合并班级开始-->
				<div class="merging-incontent">
					<div class="choose-class-info" id="classDetailDiv">
						<table>
							<tr>
								<td width="30%"><span class="info-span" >班级名称：<span id="className"></span></span></td>
								<td width="30%"><span class="info-span" >班级类型：<span id="classType"></span></span></td>
								<td><span class="info-span" >课程类别：<span id="courseType"></span></span></td>
							</tr>
							<tr>
								<td><span class="info-span" >拟开课日期：<span id="startTime"></span></span></td>
								<td><span class="info-span" >开课日期：<span id="factStartTime"></span></span></td>
								<td><span class="info-span" >拟结业日期:<span id="graduationTime"></span></span></td>
							</tr>
							<tr>
								<td><span class="info-span" >技术老师：<span id="comTeacher"></span></span></td>
								<td><span class="info-span" >职业经纪人：<span id="chrTeacher"></span></span></td>
								<td><span class="info-span" >CEP老师：<span id="cepTeacher"></span></span></td>
							</tr>
						</table>
					</div>
					<div class="table-merging-class">
						<div class="merging-class-tit clearfix"><span class="fl">合并班级列表</span><span class="fr">班级 <span id="classCount"></span>个，合并班级后总人数<span id="studentCountAll"></span>人</span></div>
						<div class="table" id="myGridClass"></div>
					</div>
				</div>
				<!--合并班级结束-->
				<div class="bottom-btn-box">
					<a href="javascript:void(0)" class="c-blue mr10" id="returnClassList">返回重新选择班级</a>
					<button class="btn btn-blue" type="button" id="mergeClassBtn" onclick="CLASSES_MERGE.loadPageConfirm()">确认合班</button>
				</div>
			</div>
		</div>
		<!--班级分班部分结束-->
	</div>
</div>
<!--右侧内容部分结束-->

<script type="text/javascript">
	$(function(){
		CLASSES_MERGE.init();
	});
</script>
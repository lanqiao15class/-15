<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<input type="hidden" id="type" value="${type }"/>

<!--右侧内容部分开始-->
<div class="content-inner-divide">
	<div class="inner-reletive">
		<!--右侧标题部分开始-->
		<div class="tit-h1-box">
			<h1 class="tit-first">
				<span>学员管理</span><i class="gt-icon">&gt;</i><span>新学员分班</span><i class="gt-icon">&gt;</i><span class="curr">确认分班信息</span>
			</h1>
		</div>
		<!--右侧标题部分结束-->
		<!--右侧内容白色开始-->
		<div class="divide-inner-padding">
			<div class="choose-class-part clearfix">
				<span class="select-box">
					<select class="select" id="classSelect">
						<option value="-1">选择班级</option>
						<c:forEach items="${classList }" var="classVar">
							<option value="${classVar.classId }">${classVar.className }</option>
						</c:forEach>
					</select>
				</span>
				<a href="javascript:void(0);" class="refresh" id="refreshClassBtn"><i class="icon-refresh Hui-iconfont">&#xe68f;</i><span class="refresh-intro">刷新</span></a>
				<button class="btn btn-green mr10" id="newClass">新建班级</button>
				<span class="choose-class-tips">如果没有您需要的班级，请先新建班级后刷新选择新班级名称</span>
			</div>
		</div>
		<!--右侧内容白色结束-->
		<!--班级分班部分开始-->
		<div class="class-bottom-part">
			<div class="inner-divide-rel">
				<!--班级信息开始-->
				<div class="class-infomation">
					<h3 class="class-tit-h3">班级信息：</h3>
					<div class="class-info-in">
						<div class="class-scroll-part">
							<div class="clearfix class-info-row">
								<label class="label-about-class">班级类型：</label><span class="intro-class-part info" id="classType"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">培训方向：</label><span class="intro-class-part info" id="courseType"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">拟开课日期：</label><span class="intro-class-part info" id="startTime"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">开课日期：</label><span class="intro-class-part info" id="factStartTime"> </span>
							</div>
							<div class="clearfix class-info-row" style="display: none;"><!-- 不做显示，暂时删除 -->
								<label class="label-about-class">拟校园结课日期：</label><span class="intro-class-part info" id="endTime"> </span>
							</div>
							<div class="clearfix class-info-row" style="display: none;"><!-- 不做显示，暂时删除 -->
								<label class="label-about-class">校园结课日期：</label><span class="intro-class-part info" id="factEndTime"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">拟结业日期：</label><span class="intro-class-part info" id="graduationTime"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">结业日期：</label><span class="intro-class-part info" id="factGraduationTime"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">QQ群：</label><span class="intro-class-part info" id="QQ"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">技术老师：</label><span class="intro-class-part info" id="comTeacher"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">职业经纪人：</label><span class="intro-class-part info" id="chrTeacher"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">CEP老师：</label><span class="intro-class-part info" id="cepTeacher"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">班长：</label><span class="intro-class-part info" id="monitor"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">创建时间：</label><span class="intro-class-part info" id="createTime"> </span>
							</div>
							<div class="clearfix class-info-row">
								<label class="label-about-class">关闭时间：</label><span class="intro-class-part info" id="closeTime"> </span>
							</div>
						</div>
					</div>
				</div>
				<!--班级信息结束-->                               
				<!--转班学员列表开始-->
				<div class="divide-student">
					<div class="divide-student-rel">
						<div class="table-divide-box" id="myGrid"></div>
					</div>
				</div>
				<!--转班学员列表结束-->
				<div class="bottom-btn-box">
					<a href="javascript:void(0)" class="c-blue mr10" id="returnStudentList">返回重新选择学员</a><button class="btn btn-blue" type="button">确认分班</button>
				</div>
			</div>
		</div>
		<!--班级分班部分结束-->
	</div>
</div>
<!--右侧内容部分结束-->
<script type="text/javascript">
	$(function(){
		STUDENT_INTO_CLASS.init();
	});
</script>
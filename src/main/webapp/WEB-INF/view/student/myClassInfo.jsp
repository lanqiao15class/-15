<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!--右侧内容部分开始-->
<div class="student-info-part">
	<div class="inner-reletive">
		<!--右侧标题部分开始-->
		<div class="tit-h1-box">
			<h1 class="tit-first">
				<span>我的班级</span><i class="gt-icon">&gt;</i><span class="curr">班级信息</span>
			</h1>
		</div>
		<!--右侧标题部分结束-->
		<!--右侧内容部分背景白色开始-->
		<div class="inner-white">
			<!-- 这里显示学员提示，去报名等等 -->
            <div id="studentTips"></div>
			<div class="student-info-form">
				<div class="inner-stuInfo">
					<div class="stu-info-inner">
					<form>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">班级名称：</i></label>
							<span class="read-span only-read">
								  <em>
									  <c:choose>
										  <c:when test="${empty classInfo.className}">暂无</c:when>
										  <c:otherwise>${classInfo.className}</c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">班级类型：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.typePre}">暂无</c:when>
										  <c:otherwise>${classInfo.typePre}</c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">课程类别：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.courseType}">暂无</c:when>
										  <c:otherwise>${classInfo.courseType}</c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">拟开课日期：</i></label>
							<span class="read-span only-read">
								  <em>
								     <c:choose>
										  <c:when test="${empty classInfo.expectStarttime}">暂无</c:when>
										  <c:otherwise><fmt:formatDate value="${classInfo.expectStarttime}" type="date"  pattern="yyyy-MM-dd"/></c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon"></i><i class="label-text">开课日期：</i></label>
							<span class="read-span only-read">
								  <em>
								     <c:choose>
										  <c:when test="${empty classInfo.startTime}">暂无</c:when>
										  <c:otherwise><fmt:formatDate value="${classInfo.startTime}" type="date"  pattern="yyyy-MM-dd"/></c:otherwise>
									 </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">拟校园结课日期：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.expectSchoolEndtime}">暂无</c:when>
										  <c:otherwise><fmt:formatDate value="${classInfo.expectSchoolEndtime}" type="date"  pattern="yyyy-MM-dd"/></c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon"></i><i class="label-text">校园结课日期：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.schoolEndtime}">暂无</c:when>
										  <c:otherwise><fmt:formatDate value="${classInfo.schoolEndtime}" type="date"  pattern="yyyy-MM-dd"/></c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">拟结业日期：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.expectGraduateTime}">暂无</c:when>
										  <c:otherwise><fmt:formatDate value="${classInfo.expectGraduateTime}" type="date"  pattern="yyyy-MM-dd"/></c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon"></i><i class="label-text">结业日期：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.graduateTime}">暂无</c:when>
										  <c:otherwise><fmt:formatDate value="${classInfo.graduateTime}" type="date"  pattern="yyyy-MM-dd"/></c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">开班人数：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.realCount}">暂无</c:when>
										  <c:otherwise>${classInfo.realCount}人</c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon"></i><i class="label-text">当前人数：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.currCount}">暂无</c:when>
										  <c:otherwise>${classInfo.currCount}人</c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">技术老师：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.comTeacherId}">暂无</c:when>
										  <c:otherwise>${classInfo.comTeacherId}</c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon"></i><i class="label-text">CEP老师：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.cepTeacherId}">暂无</c:when>
										  <c:otherwise>${classInfo.cepTeacherId}</c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">职业经纪人：</i></label>
						   <span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.chrTeacherId}">暂无</c:when>
										  <c:otherwise>${classInfo.chrTeacherId}</c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon"></i><i class="label-text">QQ群：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.qqGroup}">暂无</c:when>
										  <c:otherwise>${classInfo.qqGroup}</c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
						<div class="line-item">
							<label class="left-label"><i class="must-input-icon"></i><i class="label-text">班长：</i></label>
							<span class="read-span only-read">
								  <em>
								  	 <c:choose>
										  <c:when test="${empty classInfo.monitorId}">暂无</c:when>
										  <c:otherwise>${classInfo.monitorId}</c:otherwise>
									  </c:choose>
								  </em>
							</span>
						</div>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--右侧内容部分结束-->
<script type="text/javascript">
$(function(){
	//表头提示
	STUDENTTIPS.init();
});

</script>
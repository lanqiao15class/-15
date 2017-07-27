<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<input type="hidden" id="restSize" value="${restSize}">
<!--右侧内容部分开始-->
<div class="student-info-part">
	<div class="inner-reletive">
		<!--右侧标题部分开始-->
		<div class="tit-h1-box">
			<h1 class="tit-first">
				<span>就业服务</span><i class="gt-icon">&gt;</i><span class="curr">我的职业经历</span>
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
						<div class="con-ggcbox" style="display:block;">
						<div class="stu-work-info">
						  <c:choose>
						  		<c:when test="${latestSize eq 0}">
									<!--暂时没有样式开始-->
									<div class="no-class-info">
										<span>您的职业生涯暂未开启，好好学习，天天向上</span>
									</div>
									<!--暂时没有样式结束-->
								</c:when>
								<c:otherwise>
								    <!--有就业信息开始-->
									<div class="stu-inwork-info">
										<dl class="current">
											<dt class="tit-dt">最新职业信息<span class="ml10 c-green">${latestWorkExp.occupationStatus}</span></dt>
											<dd>
												<div class="list-record">
													<p>
														<span class="company-name c-blue">${latestWorkExp.companyName}</span>
														<span class="company-stype">${latestWorkExp.companyType }</span>
														<i class="icon-verline">|</i>
														<span class="course-status">${latestWorkExp.companyScale }</span>
													</p>
													<p>
														<span class="work-position">${latestWorkExp.positionName }</span>
														<i class="icon-verline">|</i>
														<span class="work-status">${latestWorkExp.positionType }</span>
													</p>
													<p>
														<span class="startline"><fmt:formatDate value="${latestWorkExp.entryTime}" type="date"  pattern="yyyy-MM-dd"/></span>
														<i class="icon-line">—</i>
														<span class="deadline">
															<c:choose>
																<c:when test="${empty latestWorkExp.dismissTime}">至今</c:when>
																<c:otherwise><fmt:formatDate value="${latestWorkExp.dismissTime}" type="date"  pattern="yyyy-MM-dd"/></c:otherwise>
															</c:choose>
														</span>
													</p>
												</div>
											</dd>
										</dl>
										<dl class="more-list-disply">
											<dt class="tit-dt">历史就业信息</dt>
											<dd>
											    <c:forEach items="${restWorkExpList}" var="work">
													<div class="list-record">
														<p>
															<span class="company-name c-blue">${work.companyName}</span>
															<span class="company-stype">${work.companyType }</span>
															<i class="icon-verline">|</i>
															<span class="course-status">${work.companyScale }</span>
														</p>
														<p>
															<span class="work-position">${work.positionName }</span>
															<i class="icon-verline">|</i>
															<span class="work-status">${work.positionType }</span>
														</p>
														<p>
															<span class="startline"><fmt:formatDate value="${work.entryTime}" type="date"  pattern="yyyy-MM-dd"/></span>
															<i class="icon-line">—</i>
															<span class="deadline">
																<c:choose>
																	<c:when test="${empty work.dismissTime}">至今</c:when>
																	<c:otherwise><fmt:formatDate value="${work.dismissTime}" type="date"  pattern="yyyy-MM-dd"/></c:otherwise>
															    </c:choose>
															</span>
														</p>
													</div>
												</c:forEach>
											</dd>
										</dl>
										<div class="center mb20 mt15">
											<a class="sl-down-btn" href="javascript:void(0);" onclick="showMore();">查看历史职业信息</a>
											<a class="sl-up-btn" href="javascript:void(0);" onclick="putAway();">收起历史职业信息</a>
										</div>
									</div>
									<!--有就业信息结束-->
								</c:otherwise>
						</c:choose>
						</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--右侧内容部分结束-->
<script>
$(function(){
	//表头提示
	STUDENTTIPS.init();
});

function showMore(){
	var t=$("a.sl-down-btn");
	if($("#restSize").val()>0){
		t.hide();
		t.parents(".con-ggcbox").find(".more-list-disply").slideDown(100);
		t.siblings(".sl-up-btn").show();
	}else{
		layer.msg("您当前只有一条职业信息");
	}
}
function putAway(){
	var up=$("a.sl-up-btn");
	up.hide();
	up.parents(".con-ggcbox").find(".more-list-disply").slideUp(100);
	up.siblings(".sl-down-btn").show();
}
</script>
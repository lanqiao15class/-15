<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<div class="y-block">
	<div class="y-block-t">基本信息</div>
	<div class="y-form">

		<input type="hidden" id="cid" value="-1" />
		<!-- 课程id -->
		<input type="hidden" id="updateid" value="-1" />
		<!-- 更新阶段id -->
		<input type="hidden" id="pid" value="-1" />
		<!-- 新建课时父id -->
		<input type="hidden" id="subid" value="-1" />
		<!-- 更新课时id -->
		<form id='courseform'>
			<div class="item">
				<label class="title">课程名称</label> <input type="text"
					name="courseName" id="n" value="${course.courseName}" /><span
					id="m"> </span>
			</div>
			<div class="item">
				<label class="title">合同报名费¥</label> <input type="text"
					name="standardMoney" id="n2" value="${course.standardMoney}" /><span
					id="m2"> </span>
			</div>
			<div class="item">
				<label class="title">合同实训费¥</label> <input type="text"
					name="entryfee" id="n3" value="${course.entryfee}" /><span id="m3">
				</span>
			</div>
			<%--选择框开始--%>
			<div class="item">
				<label class="title">课程类别</label>
				<span>
					<select class="select" id="lqCoursetype" name="lqCoursetype" >
						<option value="">课程类别</option>
						<option value="1">JAVAEE</option>
						<option value="2">Android</option>
						<option value="3">iOS</option>
						<option value="4">测试</option>
						<option value="5">UI</option>
						<option value="6">产品经理</option>
					</select>
				</span>
				<span id="m5"></span>
			</div>
			<%--选择框结束--%>
			<div class="item">
				<label class="title">状态</label> <label class="y-radio"> <input
					checked="true" type="radio" name="type" id="" value="1" /> <span>${course.typeStr}</span>
				</label>
			</div>
		</form>
	</div>
</div>
<div class="y-block">
	<div class="y-block-t">教学大纲</div>
	<ul class="y-tree" id="stageid">
		<c:forEach var="list" items="${lists}" >
		<li class="open open_close">
		<c:forEach var="info" items="${list}" varStatus="status">
			<c:if test="${status.index==0}">
			<div>
				<p>${info.syllabusName}（课时：${info.classTime}）</p>
				<!-- <div class="y-icon add">+&ensp;创建科目</div>
				<div class="y-icon edit">修改</div>
				<div class="y-icon del">删除</div>
				<div class="y-icon arrow-up"></div>
				<div class="y-icon arrow-down"></div> -->
			</div>
			</c:if>
			<c:if test="${status.index!=0}">
			<ul>
				<li>
					<div>
						<p>${info.syllabusName}（课时：${info.classTime}）</p>
						<!-- <div class="y-icon edit">修改</div>
						<div class="y-icon del">删除</div>
						<div class="y-icon arrow-up"></div>
						<div class="y-icon arrow-down"></div> -->
					</div>
				</li>
			</ul>
			</c:if>
			</c:forEach>
		</li>
		</c:forEach>
	</ul>

</div>

<script type="text/javascript">
    //	select标签默认选中
    $("#lqCoursetype option[value='${course.lqCoursetypeStr}']").attr("selected", "selected");
    //	select标签默认选中
</script>



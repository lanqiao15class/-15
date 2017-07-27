<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!-- 初始化数据开始 -->
<input type="hidden" id="opType" value="${opType}">
<!-- 初始化数据结束 -->

<!--右侧内容部分开始-->
<div class="content-inner-part">
	<div class="inner-reletive">
		<!--右侧标题部分开始-->
		<div class="tit-h1-box">
			<h1 class="tit-first">
				<span>收费管理</span><i class="gt-icon">&gt;</i><span class="curr">学员回款管理</span>
			</h1>
		</div>
		<!--右侧标题部分结束-->
		<!--右侧内容部分背景白色开始-->
		<div class="inner-white">
			<div class="tit-h2 clearfix">
				<div class="tab-change-inner fl">
					<a href="javascript:void(0)" id="myManage_lq">我管理的</a>
					<a href="javascript:void(0)" id="allStu_lq">全部</a>
				</div>
			</div>
			<div class="search-part-tall">
				<div class="query-opt-coopration clearfix">
					<div class="query-box-parent">
						<div class="query-box mt15" style="display:block;">
							<span class="select-box">
								<select class="select" id="isAvaiable_lq" name="isAvaiable_lq">
									<option value="">报名费</option>
									<c:forEach items="${isAvaiableList }" var="av">
									
										<option value="${av.value}">${av.label}</option>
									</c:forEach>
								</select>
							</span>
							<span class="select-box">
								<select class="select" id="hasPaid_lq" name="hasPaid_lq">
									<option value="">学费</option>
								    <c:forEach items="${hasPaidList }" var="has">
									<option value="${has.value}">${has.label}</option>
									</c:forEach>
								</select>
							</span>
					        <!-- 引入院校筛选列表 开始-->
							  <jsp:include page="/WEB-INF/view/include/school_select_likesearch.jsp">
								<jsp:param name="inputid" value="select_school_id" />
								<jsp:param name="placeholder" value="所在院校" />
							 </jsp:include>
					    	<!-- 引入院校筛选列表 结束-->
						   <!-- 学员状态开始 -->
	                      <span class="select-box">
	                          <select class="select" id="stuStatus_lq" name="stuStatus_lq">
	                               <option value="">状态</option>
                                   <c:forEach items="${stuStatusList}" var="sta">
                                     <option value="${sta.value}">${sta.label}</option>
                                   </c:forEach>
	                          </select>
	                      </span>
	                      <!-- 学员状态结束 -->
					   	  <!-- 区域总监开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="regional_director_userid" />
								<jsp:param name="placeholder" value="区域总监" />
							</jsp:include>
							<!-- 区域总监结束 -->
							<button class="btn btn-blue search-btn query_lq"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
							
							<button class="btn btn-gray much-more"><span class="mr10">更多筛选</span><i class="Hui-iconfont">&#xe6d5;</i></button>
							<button class="btn btn-gray put-away"><span class="mr10">收起筛选</span><i class="Hui-iconfont">&#xe6d6;</i></button>
						</div>
						<div class="query-box">
							<!-- 招生经理开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="recruit_manager_userid" />
								<jsp:param name="placeholder" value="招生经理" />
							</jsp:include>
							<!-- 招生经理结束 -->
						     <!-- 课程顾问开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="course_advisor_useid" />
								<jsp:param name="placeholder" value="课程顾问" />
							</jsp:include>
							<!-- 课程顾问结束 -->
						<!-- 学员姓名开始 -->
                          <jsp:include page="/WEB-INF/view/include/student_input_likesearch.jsp">
								<jsp:param name="inputid" value="student_name_lq" />
								<jsp:param name="inputwidth" value="300" />
								<jsp:param name="placeholder" value="学员姓名" />
						  </jsp:include>
						  <!-- 学员姓名结束 -->
							<button class="btn btn-blue search-btn query_lq"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
						</div>
					</div>
					<div class="opt-btn-box">
						<i class="mr20" id="hasChoosed">已选：0</i>
						
<!-- 						  无权限显示灰色 -->
						<c:if  test="${lq:ifIn(myFunction,'82')}">
							<a class="c-blue" href="javascript:void(0)" id="addReduRecord_lq">添加减免记录</a><span>|</span>
							<a class="c-blue" href="javascript:void(0)" id="addCashRecord_lq">添加回款记录</a><span>|</span>
							<a class="c-blue" href="javascript:void(0)" id="addRefundRecord_lq">添加退费记录</a>
						</c:if>
						
						 <c:if  test="${not lq:ifIn(myFunction,'82')}">
							<a class="c-gray" href="javascript:void(0)" id="addReduRecord_lq">添加减免记录</a><span>|</span>
							<a class="c-gray" href="javascript:void(0)" id="addCashRecord_lq">添加回款记录</a><span>|</span>
							<a class="c-gray" href="javascript:void(0)" id="addRefundRecord_lq">添加退费记录</a>
						</c:if>
						
					</div>
				</div>
				<div class="table-scroll-parent" id="myGrid"></div>
			</div>
		</div>
	</div>
</div>
<!--右侧内容部分结束-->
<!-- 引入分页开始 -->
<jsp:include page="/WEB-INF/view/include/pagejavascript.jsp">
	<jsp:param name="funcName" value="STUFEELIST.dopagechange" />
</jsp:include>
<!-- 引入分页结束 -->
<script>
var functionIds="${myFunction}";
$(function(){
	var canEdit = false;
	 <c:if  test="${lq:ifIn(myFunction,'82')}">
	 	canEdit =true
	 </c:if>
	STUFEELIST.init(canEdit);
});
</script>
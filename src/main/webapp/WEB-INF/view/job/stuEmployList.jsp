<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!--右侧内容部分开始-->
<div class="content-inner-part">
	<div class="inner-reletive">
		<!--右侧标题部分开始-->
		<div class="tit-h1-box">
			<h1 class="tit-first">
				<span>就业服务</span><i class="gt-icon">&gt;</i><span class="curr">就业学员管理</span>
			</h1>
		</div>
		<!--右侧标题部分结束-->
		<!--右侧内容部分背景白色开始-->
		<div class="inner-white">
			<div class="tit-h2 clearfix">
				<div class="tab-change-inner fl">
					<c:choose>
	          <c:when test="${opType eq 100 }">
	          	   <c:if  test="${lq:ifIn(myFunction,'69')}">
	               	    <c:if test="${not empty default_opType }"> 
	               	        <a   href="javascript:void(0)" id="myManage_lq">我管理的</a>
	                     </c:if> 
	               	    <c:if test="${empty default_opType }"> 
	               	        <a  class="curr"   href="javascript:void(0)" id="myManage_lq">我管理的</a>
	               	         <c:set var="default_opType" value="2"></c:set>
	                    </c:if> 
	                </c:if> 
	                
	                <c:if test="${lq:ifIn(myFunction,'71')}">
	                    <c:if test="${not empty default_opType }">
	                      <a  href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
	                    </c:if>
	                    <c:if test="${empty default_opType}">
	                      <a class="curr"  href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
	                        <c:set var="default_opType" value="0"></c:set>
	                    </c:if>
	                </c:if>
	                
	                 <c:if test="${lq:ifIn(myFunction,'70')}">
	                 	 <c:if test="${not empty default_opType }">
	                       <a  href="javascript:void(0)" id="allStu_lq">全部</a>
	                     </c:if>
	                    <c:if test="${empty default_opType}">
	                      <a class="curr"  href="javascript:void(0)" id="allStu_lq">全部</a>
	                        <c:set var="default_opType" value="1"></c:set>
	                    </c:if>
	                </c:if>
	                
	           </c:when>
	           <c:otherwise>
	           	    <c:if  test="${lq:ifIn(myFunction,'69')}">
	                      <a <c:if test="${opType eq '2' }">class="curr"</c:if>  href="javascript:void(0)" id="myManage_lq">我管理的</a>
	                </c:if> 
	                <c:if test="${lq:ifIn(myFunction,'71')}">
	                      <a <c:if test="${opType eq '0' }">class="curr"</c:if>  href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
	                </c:if>
	           	    <c:if test="${lq:ifIn(myFunction,'70')}">
	                      <a <c:if test="${opType eq '1' }">class="curr"</c:if> href="javascript:void(0)" id="allStu_lq">全部</a>
	                </c:if>
	           </c:otherwise>
	           </c:choose>
	           <!-- 初始化数据开始 -->
				<c:if test="${opType eq 100 }">
				   <c:set var="opType" value="${default_opType }"></c:set>
				</c:if>

				<input type="hidden" id="opType" value="${opType}">
				<!-- 初始化数据结束 -->
				</div>
			</div>
			<div class="search-part-tall">
				<div class="query-opt-coopration clearfix">
					<div class="query-box-parent">
						<div class="query-box mt15" style="display:block;">
							  <!-- 引入院校筛选列表 开始-->
							  <jsp:include page="/WEB-INF/view/include/school_select_likesearch.jsp">
								<jsp:param name="inputid" value="select_school_id" />
								<jsp:param name="placeholder" value="所在院校" />
							 </jsp:include>
					    	<!-- 引入院校筛选列表 结束-->
						    <!-- 引入班级名称开始 -->
							<jsp:include page="/WEB-INF/view/include/classname_input_likesearch.jsp">
								<jsp:param name="inputid" value="select_class_name" />
								<jsp:param name="placeholder" value="班级名称" />
							</jsp:include>
						   <!-- 引入班级名称结束 -->
						   <!-- 学员姓名开始 -->
                          <jsp:include page="/WEB-INF/view/include/student_input_likesearch.jsp">
								<jsp:param name="inputid" value="student_name_lq" />
								<jsp:param name="inputwidth" value="300" />
								<jsp:param name="placeholder" value="学员姓名" />
						  </jsp:include>
						  <!-- 学员姓名结束 -->
							<span class="select-box">
								<select class="select" id="jobStatus_lq" name="jobStatus_lq">
									<option value="">职业状态</option>
									<c:forEach items="${jobStatusList}" var="job">
									<option value="${job.value }">${job.label}</option>
									</c:forEach>
								</select>
							</span>
							<span class="select-box">
								<select class="select" id="jobWay_lq" name="jobWay_lq">
									<option value="">就业方式</option>
									<c:forEach items="${jobWayList}" var="way">
									<option value="${way.value }">${way.label}</option>
									</c:forEach>
								</select>
							</span>
							<button class="btn btn-blue search-btn query_lq"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
							
							<button class="btn btn-gray much-more"><span class="mr10">更多筛选</span><i class="Hui-iconfont">&#xe6d5;</i></button>
							<button class="btn btn-gray put-away"><span class="mr10">收起筛选</span><i class="Hui-iconfont">&#xe6d6;</i></button>
						</div>
						<div class="query-box">
							<span class="select-box">
								<select class="select" id="zsSelectSearch">
									<option value="0">请选择其它搜索项</option>
									<option value="1">区域总监</option>
									<option value="2">招生经理</option>
									<option value="3">课程顾问</option>
									<option value="4">就业开始时间</option>
									<option value="5">首次入职时间</option>
								</select>
							</span>
						    <span class="ser-out-span zs_search">
	                      	 <!-- 区域总监开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="regional_director_userid" />
								<jsp:param name="placeholder" value="区域总监" />
							</jsp:include>
							<!-- 区域总监结束 -->
	                      </span>
	                      <span class="ser-out-span zs_search">
	                      	<!-- 招生经理开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="recruit_manager_userid" />
								<jsp:param name="placeholder" value="招生经理" />
							</jsp:include>
							<!-- 招生经理结束 -->
	                      </span>
	                      <span class="ser-out-span zs_search">
	                      	<!-- 课程顾问开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="course_advisor_userid" />
								<jsp:param name="placeholder" value="课程顾问" />
							</jsp:include>
							<!-- 课程顾问结束 -->
	                      </span>
						  <span class="ser-out-span zs_search">
	                      	  <span>从</span>
	                      	  <input type="text" disableautocomplete autocomplete="off" name="jSerBeginTime_lq" value="" id="jSerBeginTime_lq" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})">
	                          <span>到</span>
	                          <input type="text" disableautocomplete autocomplete="off" name="jSerEndTime_lq" value="" id="jSerEndTime_lq" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})">
	                       </span>
						  <span class="ser-out-span zs_search">
	                      	  <span>从</span>
	                      	  <input type="text" disableautocomplete autocomplete="off" name="fJobBeginTime_lq" value="" id="fJobBeginTime_lq" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})">
	                          <span>到</span>
	                          <input type="text" disableautocomplete autocomplete="off" name="fJobEndTime_lq" value="" id="fJobEndTime_lq" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})">
	                      </span>
							<button class="btn btn-blue search-btn query_lq"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
						</div>
					</div>
					<div class="opt-btn-box">
						<i class="mr20" id="hasChoosed">已选：0</i>
						<a class="c-blue" href="javascript:void(0)" id="inputJob_lq">入职登记</a><span>|</span>
						<a class="c-blue" href="javascript:void(0)" id="remarkMyFocus_lq">标记为我关注</a><span class="i_lq">|</span>
						<a class="c-blue" href="javascript:void(0)" id="cancelFocus_lq">取消关注</a>
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
	<jsp:param name="funcName" value="STUEMPLOYLIST.dopagechange" />
	</jsp:include>
	<!-- 引入分页结束 -->

<script>
var functionIds="${myFunction}";
$(function(){
	STUEMPLOYLIST.init();
});
</script>
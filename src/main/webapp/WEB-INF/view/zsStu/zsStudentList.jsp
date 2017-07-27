<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<!--右侧内容部分开始-->
<div class="content-inner-part">
   	<div class="inner-reletive">
       	<!--右侧标题部分开始-->
		<div class="tit-h1-box">
             <h1 class="tit-first">
                 <span>学员管理</span><i class="gt-icon">&gt;</i><span class="curr">学员维护</span>
             </h1>
       </div>
  		<!--右侧标题部分结束-->
	   <!--右侧内容部分背景白色开始-->
	  <div class="inner-white">
	      <div class="tit-h2 clearfix">
	          <div class="tab-change-inner fl">
	          <c:choose>
	          <c:when test="${opType eq 100 }">
	          	   <c:if  test="${lq:ifIn(myFunction,'52')}">
	               	    <c:if test="${not empty default_opType }"> 
	               	        <a   href="javascript:void(0)" id="myManage_lq">我管理的</a>
	                     </c:if> 
	               	    <c:if test="${empty default_opType }"> 
	               	        <a  class="curr"   href="javascript:void(0)" id="myManage_lq">我管理的</a>
	               	         <c:set var="default_opType" value="2"></c:set>
	                    </c:if> 
	                </c:if> 
	                
	                <c:if test="${lq:ifIn(myFunction,'53')}">
	                    <c:if test="${not empty default_opType }">
	                      <a  href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
	                    </c:if>
	                    <c:if test="${empty default_opType}">
	                      <a class="curr"  href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
	                        <c:set var="default_opType" value="0"></c:set>
	                    </c:if>
	                </c:if>
	                
	                 <c:if test="${lq:ifIn(myFunction,'54')}">
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
	           	    <c:if  test="${lq:ifIn(myFunction,'52')}">
	                      <a <c:if test="${opType eq '2' }">class="curr"</c:if>  href="javascript:void(0)" id="myManage_lq">我管理的</a>
	                </c:if> 
	                <c:if test="${lq:ifIn(myFunction,'53')}">
	                      <a <c:if test="${opType eq '0' }">class="curr"</c:if>  href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
	                </c:if>
	           	    <c:if test="${lq:ifIn(myFunction,'54')}">
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
								<jsp:param name="inputid" value="select_school_name_lq" />
								<jsp:param name="placeholder" value="所在院校" />
							 </jsp:include>
					    	<!-- 引入院校筛选列表 结束-->
					    	<!-- 引入班级名称列表开始 -->
	                        <jsp:include page="/WEB-INF/view/include/class_select_search.jsp">
								<jsp:param name="inputid" value="select_class_name_lq" />
								<jsp:param name="placeholder" value="班级名称" />
	                       </jsp:include>
	                       <!-- 引入班级名称列表结束 -->
	                       <!-- 学员状态开始 -->
	                      <span class="select-box">
	                          <select class="select" id="stuStatus_lq" name="stuStatus_lq">
	                               <option value="">学员状态</option>
                                   <c:forEach items="${stuStatusList}" var="sta">
                                     <option value="${sta.value}">${sta.label}</option>
                                   </c:forEach>
	                          </select>
	                      </span>
	                      <!-- 学员状态结束 -->
	                      <!-- 报名费开始 -->
	                      <span class="select-box">
	                          <select class="select" id="isAvaiable_lq" name="isAvaiable_lq">
	                               <option value="">报名费</option>
                                   <c:forEach items="${isAvaiableList}" var="av">
                                     <option value="${av.value}">${av.label}</option>
                                   </c:forEach>
	                          </select>
	                      </span>
	                      <!-- 报名费结束 -->
	                      <!-- 学费开始 -->
	                      <span class="select-box">
	                          <select class="select" id="hasPaid_lq" name="hasPaid_lq">
	                               <option value="">学费</option>
                                   <c:forEach items="${hasPaidList}" var="pa">
                                     <option value="${pa.value}">${pa.label}</option>
                                   </c:forEach>
	                          </select>
	                      </span>
	                      <!-- 学费结束 -->
	                      <button class="btn btn-blue search-btn query_lq" type="button"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
	                      
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
	                              <option value="4">学员姓名</option>
	                              <option value="5">报名审核通过时间</option>
	                          </select>
	                      </span>
	                      <span class="ser-out-span zs_search">
	                      	 <!-- 区域总监开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="regional_director_name" />
								<jsp:param name="placeholder" value="区域总监" />
							</jsp:include>
							<!-- 区域总监结束 -->
	                      </span>
	                      <span class="ser-out-span zs_search">
	                      	<!-- 招生经理开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="recruit_manager_name" />
								<jsp:param name="placeholder" value="招生经理" />
							</jsp:include>
							<!-- 招生经理结束 -->
	                      </span>
	                      <span class="ser-out-span zs_search">
	                      	<!-- 课程顾问开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="course_advisor_name" />
								<jsp:param name="placeholder" value="课程顾问" />
							</jsp:include>
							<!-- 课程顾问结束 -->
	                      </span>
	                      <span class="ser-out-span zs_search">
                           <!-- 学员姓名开始 -->
                          <jsp:include page="/WEB-INF/view/include/student_input_likesearch.jsp">
								<jsp:param name="inputid" value="student_name_lq" />
								<jsp:param name="inputwidth" value="300" />
								<jsp:param name="placeholder" value="学员姓名" />
						  </jsp:include>
						  <!-- 学员姓名结束 -->
	                      </span>
	                      <span class="ser-out-span zs_search">
	                      	  <span>从</span>
	                      	  <input type="text" disableautocomplete autocomplete="off" name="beginTime_lq" value="" id="beginTime_lq" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})">
	                          <span>到</span>
	                          <input type="text" disableautocomplete autocomplete="off" name="endTime_lq" value="" id="endTime_lq" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})">
	                      </span>
	                      <button class="btn btn-blue search-btn query_lq" type="button"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
	                  </div>
	              </div>
	              <div class="opt-btn-box">
	                  <i class="mr20" id="hasChoosed">已选：0</i>
	                  <a class="c-blue" href="javascript:void(0)" id="changeClass_lq">学员转班</a><span>|</span>
	                  <a class="c-blue" href="javascript:void(0)" id="remarkMyFocus_lq">标记为我关注</a><span class="i_lq">|</span>
	                  <a class="c-blue" href="javascript:void(0)" id="cancelFocus_lq">取消关注</a><span>|</span>
	                  <a class="c-blue" href="javascript:void(0)" id="exportStu_lq">学员信息导出</a>
	                  <!-- 学员导出信息表单开始 -->
	                  <form action="${ctxBase }/report/stuExport.do" target="_blank" style="display:none" method="post" id="exporStuForm">
	                  </form>
	                  <!-- 学员导出信息表单结束 -->
	              </div>
	          </div>
	          <div class="table-scroll-parent" id="myGrid"></div>
	      </div>
	  </div><!-- inner-white end -->
    </div><!-- inner-reletive end -->
  </div>
  <!--右侧内容部分结束-->
  
    <!-- 引入分页开始 -->
   <jsp:include page="/WEB-INF/view/include/pagejavascript.jsp">
	<jsp:param name="funcName" value="ZSSTUDENTLIST.dopagechange" />
	</jsp:include>
	<!-- 引入分页结束 -->
	
	<script>
	var functionIds="${myFunction}";
	$(function(){
		ZSSTUDENTLIST.init();
	});
	</script>
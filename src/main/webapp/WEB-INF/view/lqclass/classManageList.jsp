<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!--右侧内容部分开始-->
<div class="content-inner-part">
	<div class="inner-reletive">
		<!--右侧标题部分开始-->
		<div class="tit-h1-box">
			<h1 class="tit-first">
				<span>班级管理</span><i class="gt-icon">&gt;</i><span class="curr">班级维护</span>
			</h1>
		</div>
		<!--右侧标题部分结束-->
		<!--右侧内容部分背景白色开始-->
		<div class="inner-white">
			<div class="tit-h2 clearfix">
				<div class="tab-change-inner fl">
			      <c:choose>
	          <c:when test="${opType eq 100 }">
	          	   <c:if  test="${lq:ifIn(myFunction,'63')}">
	               	    <c:if test="${not empty default_opType }"> 
	               	        <a   href="javascript:void(0)" id="myManage_lq">我管理的</a>
	                     </c:if> 
	               	    <c:if test="${empty default_opType }"> 
	               	        <a  class="curr"   href="javascript:void(0)" id="myManage_lq">我管理的</a>
	               	         <c:set var="default_opType" value="2"></c:set>
	                    </c:if> 
	                </c:if> 
	                
	                <c:if test="${lq:ifIn(myFunction,'65')}">
	                    <c:if test="${not empty default_opType }">
	                      <a  href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
	                    </c:if>
	                    <c:if test="${empty default_opType}">
	                      <a class="curr"  href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
	                        <c:set var="default_opType" value="0"></c:set>
	                    </c:if>
	                </c:if>
	                
	                 <c:if test="${lq:ifIn(myFunction,'64')}">
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
	           	    <c:if  test="${lq:ifIn(myFunction,'63')}">
	                      <a <c:if test="${opType eq '2' }">class="curr"</c:if>  href="javascript:void(0)" id="myManage_lq">我管理的</a>
	                </c:if> 
	                <c:if test="${lq:ifIn(myFunction,'65')}">
	                      <a <c:if test="${opType eq '0' }">class="curr"</c:if>  href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
	                </c:if>
	           	    <c:if test="${lq:ifIn(myFunction,'64')}">
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
							<span class="select-box">
								<select class="select" id="classStatus" name="classStatus">
								   <option value="">班级状态</option>
							       <c:forEach items="${classStatusList}" var="cSta">
							       		<option value="${cSta.value}">${cSta.label}</option>
							       </c:forEach>
								</select>
							</span>
							<span class="select-box">
								<select class="select" id="courseType" name="courseType">
									<option value="">课程名称</option>
									<c:forEach items="${courseTypeList}" var="coType">
									<option value="${coType.value}">${coType.label}</option>
									</c:forEach>
								</select>
							</span>
						  	 <!-- 技术老师开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="js_teacher_userid" />
								<jsp:param name="placeholder" value="技术老师" />
							</jsp:include>
							<!-- 技术老师结束 -->
							<!-- 职业经纪人开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="broker_teacher_userid" />
								<jsp:param name="placeholder" value="职业经纪人" />
							</jsp:include>
							<!-- 职业经纪人结束 -->
							<!-- cep老师开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="cep_teacher_userid" />
								<jsp:param name="placeholder" value="cep老师" />
							</jsp:include>
							<!-- cep老师结束 -->
							<button class="btn btn-blue search-btn query_lq" type="button"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
							
							<button class="btn btn-gray much-more"><span class="mr10">更多筛选</span><i class="Hui-iconfont">&#xe6d5;</i></button>
							<button class="btn btn-gray put-away"><span class="mr10">收起筛选</span><i class="Hui-iconfont">&#xe6d6;</i></button>
						</div>
						<div class="query-box">
						<!-- 引入班级名称开始 -->
							<jsp:include page="/WEB-INF/view/include/classname_input_likesearch.jsp">
								<jsp:param name="inputid" value="select_class_name" />
								<jsp:param name="placeholder" value="班级名称" />
							</jsp:include>
						<!-- 引入班级名称结束 -->
							<button class="btn btn-blue search-btn query_lq" type="button"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
						</div>
					</div>
					<div class="opt-btn-box">
						<i class="mr20" id="hasChoosed">已选：0</i>
						<a class="c-blue" href="javascript:void(0)" id="mergeClass_lq">合并班级</a><span>|</span>
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
<!--分页开始-->
<!-- 引入分页开始 -->
   <jsp:include page="/WEB-INF/view/include/pagejavascript.jsp">
	<jsp:param name="funcName" value="CLASSMANAGELIST.dopagechange" />
	</jsp:include>
	<!-- 引入分页结束 -->
<!--分页结束-->

<script>
var functionIds="${myFunction}";
$(function(){
	CLASSMANAGELIST.init();
});
</script>
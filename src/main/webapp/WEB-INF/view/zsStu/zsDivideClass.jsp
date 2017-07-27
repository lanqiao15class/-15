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
                 <span>学员管理</span><i class="gt-icon">&gt;</i><span class="curr">新学员分班</span>
             </h1>
        </div>
		<!--右侧标题部分结束-->
        <!--右侧内容部分背景白色开始-->
        <div class="inner-white">
            <div class="tit-h2 clearfix">
                <div class="tab-change-inner fl">
                       <a href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
				       <a href="javascript:void(0)" id="allStu_lq">全部</a>
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
                      		<!-- 课程类别开始 -->
                             <span class="select-box">
                                <select class="select" id="courseType_lq" name="courseType_lq">
                                   <option value="">课程类别</option>
                                   <c:forEach items="${courseTypeList}" var="co">
                                     <option value="${co.value}">${co.label}</option>
                                   </c:forEach>
                                </select>
                            </span>
                            <!-- 课程类别结束 -->
                            <!-- 区域总监开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="regional_director_name" />
								<jsp:param name="placeholder" value="区域总监" />
							</jsp:include>
							<!-- 区域总监结束 -->
                           <!-- 招生经理开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="recruit_manager_name" />
								<jsp:param name="placeholder" value="招生经理" />
							</jsp:include>
							<!-- 招生经理结束 -->
							<!-- 课程顾问开始 -->
                         	<jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
								<jsp:param name="inputid" value="course_advisor_name" />
								<jsp:param name="placeholder" value="课程顾问" />
							</jsp:include>
							<!-- 课程顾问结束 -->
                            <button class="btn btn-blue search-btn query_lq" type="button"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
                            
                            <button class="btn btn-gray much-more"><span class="mr10">更多筛选</span><i class="Hui-iconfont">&#xe6d5;</i></button>
                            <button class="btn btn-gray put-away"><span class="mr10">收起筛选</span><i class="Hui-iconfont">&#xe6d6;</i></button>
                        </div>
                        <div class="query-box">
                        <!-- 学员姓名开始 -->
                          <jsp:include page="/WEB-INF/view/include/student_input_likesearch.jsp">
								<jsp:param name="inputid" value="student_name_lq" />
								<jsp:param name="inputwidth" value="300" />
								<jsp:param name="placeholder" value="学员姓名" />
						  </jsp:include>
						  <!-- 学员姓名结束 -->
                            <button class="btn btn-blue search-btn query_lq" type="button"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
                        </div>
                    </div>
                    <div class="opt-btn-box">
                        <i class="mr20" id="hasChoosed">已选：0</i>
                        <a class="c-blue" href="javascript:void(0)" id="divideClass_lq">新学员分班</a><span>|</span>
                        <a class="c-blue" href="javascript:void(0)" id="remarkMyFocus_lq">标记为我关注</a><span class="i_lq">|</span>
                        <a class="c-blue" href="javascript:void(0)" id="cancelFocus_lq">取消关注</a><span>|</span>
                        <a class="c-blue" href="javascript:void(0)" id="exportStu_lq">学员信息导出</a>
                        <!-- 学员导出信息表单开始 -->
	                  	<form action="${ctxBase}/report/stuNoClassExport.do" target="_blank" style="display:none" method="post" id="exporStuForm">
	                  	</form>
	                  	<!-- 学员导出信息表单结束 -->
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
<jsp:param name="funcName" value="ZSDIVIDECLASS.dopagechange" />
</jsp:include>
<!-- 引入分页结束 -->
<script> 
var functionIds="${myFunction}";
$(function(){
	ZSDIVIDECLASS.init();
}); 
</script>
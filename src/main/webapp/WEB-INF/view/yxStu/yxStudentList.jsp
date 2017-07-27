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
                 <span>意向学员管理</span><i class="gt-icon">&gt;</i><span class="curr">意向学员维护</span>
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
								     <div class="fr btn-box-right">
								     	<button type="button" class="btn btn-green" id="showAddStuDialog"><i class="Hui-iconfont">&#xe600;</i><span class="ml10">新增意向学员</span></button>
								     </div>
                               </div>
                            <div class="search-part-tall">
                                <div class="query-opt-coopration clearfix">
                                    <div class="query-box-parent">
                                        <div class="query-box-nospace mt15"  style="display:block;">
										     <!-- 引入院校筛选列表 开始-->
											  <jsp:include page="/WEB-INF/view/include/school_select_likesearch.jsp">
												<jsp:param name="inputid" value="select_school_name_lq" />
												<jsp:param name="placeholder" value="所在院校" />
											 </jsp:include>
					    					<!-- 引入院校筛选列表 结束-->
  					                		<!-- 引入学员所在学院开始 -->
                                             <jsp:include page="/WEB-INF/view/include/school_subname_input_likesearch.jsp">
													<jsp:param name="inputid" value="school_sub_name_lq" />
													<jsp:param name="inputwidth" value="" />
													<jsp:param name="placeholder" value="所在学院" />
  											</jsp:include>
  											<!-- 引入学员所在学院结束 -->
                                            <!-- 引入学员姓名搜索开始 -->
                                            <jsp:include page="/WEB-INF/view/include/student_input_likesearch.jsp">
												<jsp:param name="inputid" value="student_name_lq" />
												<jsp:param name="inputwidth" value="" />
												<jsp:param name="placeholder" value="学员姓名" />
											</jsp:include>
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
                                            <!-- 所在年级开始 -->
										    <span class="search-input">
                                                <input disableautocomplete autocomplete="off" type="text" placeholder="所在年级" id="grade_lq" name="grade_lq">
                                            </span>
                                            <!-- 所在年级结束 -->
                                            <button class="btn btn-blue search-btn" type="button" id="query_lq"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
                                        </div><!--query-box mt15 end  -->
                                       
                                    </div>
                                    <div class="opt-btn-box">
                                        <i class="mr20" id="hasChoosed">已选:0</i>
                                        <a class="c-blue" href="javascript:void(0)" id="remarkMyFocus_lq">标记为我关注</a><span><i id="i_lq">|</i></span>
                                        <a class="c-blue" href="javascript:void(0)" id="cancelFocus_lq">取消关注</a>
                                    </div>
                                </div>
                                <div class="table-scroll-parent" id="myGrid">
                                	
                                </div>
                            </div>
                          </div>
                          <!--右侧内容部分背景白色结束-->
           
            </div>
      </div>
<!--右侧内容部分结束-->

   <jsp:include page="/WEB-INF/view/include/pagejavascript.jsp">
	<jsp:param name="funcName" value="YXCONTENTWHITE.dopagechange" />
	</jsp:include>

<script> 
  var functionIds="${myFunction}";
	$(function(){
		YXCONTENTWHITE.init();
	}); 
</script>

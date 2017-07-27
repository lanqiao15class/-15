<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<div class="content">
            	<!--右侧内容部分开始-->
            	<div class="content-inner-part">
                	<div class="inner-reletive">
                    	<!--右侧标题部分开始-->
						<div class="tit-h1-box">
                            <h1 class="tit-first">
                                <span>${menuname }</span><i class="gt-icon">&gt;</i><span class="curr">学员跟踪记录</span>
                            </h1>
                        </div>
						<!--右侧标题部分结束-->
                        <!--右侧内容部分背景白色开始-->
                        <div class="inner-white">
                            <div class="tit-h2 clearfix">
                                <div class="tab-change-inner fl">
                                    <a class="curr" href="javascript:void(0)">我的跟踪记录</a>
                                </div>
                                <div class="fr btn-box-right">
                                	<button class="btn btn-green" onclick="VisitMain.showaddDialog()"><i class="Hui-iconfont"></i>
                                	<span class="ml10">新增跟踪记录</span></button>
                                </div>
                            </div>
                            <div class="search-part-tall">
                                <div class="query-opt-coopration clearfix">
                                    <div class="query-box-parent">
                                        <div class="query-box mt15" style="display:block;">
                                            <span class="select-box">
                                                <select class="select" id="fangtanSearch">
                                                    <option value="0">请输入搜索选项</option>
                                                    <option value="1" <c:if test="${searchtype==1}"> selected </c:if> >所在院校</option>
                                                    <option value="2"  <c:if test="${searchtype==2}"> selected </c:if>>访谈日期</option>
                                                    <option value="3" <c:if test="${searchtype==3}"> selected </c:if>>访谈对象</option>
                                                    <option value="4" <c:if test="${searchtype==4}"> selected </c:if>>创建时间</option>
                                                	<option value="5" <c:if test="${searchtype==5}"> selected </c:if>>创建者</option>
                                                </select>
                                            </span>
                                            <span class="ser-out-span"  <c:if test="${searchtype==1}"> style="display:inline" </c:if> >
											     <jsp:include page="/WEB-INF/view/include/school_select_likesearch.jsp">
												  <jsp:param name="inputid" value="visit_schoolid" />
												  <jsp:param name="selectid" value="${univ_code }" />
												  <jsp:param name="selecttext" value="${schooltext }" />
												  <jsp:param name="width" value="258" />
												</jsp:include>
                                            </span>
                                            <span class="ser-out-span"  <c:if test="${searchtype==2}"> style="display:inline" </c:if> >
                                             	<span>从</span>
												 <input type="text" name="startTime" id="startTime" value="${bvisit_time }" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
												    <span>到</span>
												 <input type="text" name="endTime" id="endTime" value="${evisit_time }" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
												  </span>
                                            <span class="ser-out-span"  <c:if test="${searchtype==3}"> style="display:inline" </c:if> >
												 <jsp:include page="/WEB-INF/view/include/student_input_likesearch.jsp">
													<jsp:param name="inputid" value="student_name" />
													<jsp:param name="selecttext" value="${real_name }" />
												</jsp:include>
                                            </span>
                                            <span class="ser-out-span"  <c:if test="${searchtype==4}"> style="display:inline" </c:if> >
                                            	<span>从</span>
													<input type="text" name="createstartTime" id="createstartTime" value="${bcreatetime }" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
													
													   <span>到</span>
													<input type="text" name="createendTime" id="createendTime" value="${ecreatetime }" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" />
                                            </span>
                                                  <span class="ser-out-span"  <c:if test="${searchtype==5}"> style="display:inline" </c:if> >
													 <jsp:include page="/WEB-INF/view/include/teacher_input_likesearch.jsp">
														<jsp:param name="inputid" value="teachername1" />
														<jsp:param name="selecttext" value="${teahcer_real_name }" />
													</jsp:include>
	                                            </span>
	                                             
                                            <button class="btn btn-blue search-btn" id="search-btn" onclick="VisitMain.beforeSearch()">
                                            <i class="Hui-iconfont"></i><span class="ml10">查询</span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <div class="table-scroll-parent" style="height: 393px;">
                                	<ul class="fangtan-list clearfix">
                                	
                                	<c:forEach var="item" items="${datalist}"  varStatus="vstatus">
                                		<li <c:if test="${vstatus.index==0}">class="first"</c:if> >
                                        	<div class="clearfix mb10">
                                            	<p ><label class="label-blue-fangtan">访谈对象：</label>
                                            	<i class="vtop c-blue">
                                            	 <c:forEach var="man" items="${item.manlist}">
                                				
                                				<c:out value="${man.real_name }" />
                                				<c:if test="${man.class_name!=null }">
                                				<a href="javascript:void(0);" onclick="ClassMember.opendialog(${man.lq_class_id})">(<c:out value="${man.class_name}" />)</a>
                                				</c:if>
                                            	
                                            	</c:forEach>
                                            	</i></p>
                                            </div>
                                            <div class="rel-fangtan-con mb10 clearfix">
                                            	<label class="label-blue-fangtan fl">访谈目的：</label>
                                                <span class="fl"><c:out value="${item.visit_goal }" /></span>
                                            </div>
                                            <div class="rel-fangtan-con  mb10  clearfix">
                                            	<label class="label-blue-fangtan fl">访谈纪要：</label>
                                                <span class="fl">
                                                <c:out value="${item.visit_content }" />
                                                </span>
                                            </div>
                                            <div class="clearfix mb10">
                                                	<span><label class="label-blue-fangtan">访谈时间：</label><i class="c-blue">
                                                	  <fmt:formatDate pattern="yyyy-MM-dd" value="${item.visit_time}" type="both"/>
                                                	</i></span>
                                                    <span><label class="label-blue-fangtan" style="padding-left: 20px">所在院校：</label><i class="c-blue">
                                                     <c:forEach var="sch" items="${item.school}">
                                				
                                						<c:out value="${sch}" />
                                            		</c:forEach>
                                            	
                                            	
                                                    </i></span>
                                                </div>
                                            <div class="c-gray clearfix mb10">
                                            		<p class="left-in-ft"><label class="label-blue-fangtan">创建时间：	 </label>
                                            	<i class="vtop c-gray ">
                                            	  
                                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.createtime}" type="both"/>
                                            	</i>
                                            	&nbsp;&nbsp;&nbsp;<span style="padding-left: 10px"><label class="label-blue-fangtan">创建者：</label><i class="c-gray ">
                                                	 <c:out value="${item.real_name }" />
                                                	</i></span>
                                            	</p>
                              				<p class="right-in-ft">
                                               	<c:if test="${item.sign==1}">
							    				 <button type="button" class="btn btn-green" onclick="VisitMain.showaddDialog(0,'${item.visit_id}')" value=""><i class="Hui-iconfont">&#xe60c;</i><span class="ml10">修改</span></button>
                                               	</c:if>
                                             </p>
                                            </div>
                                        </li>
                                      </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--右侧内容部分结束-->
        <jsp:include page="/WEB-INF/view/include/page.jsp">
		<jsp:param name="recordcount" value="${recordcount }" />
		<jsp:param name="currpage" value="${pageno }" />
		<jsp:param name="pageSize" value="${pagesize }" />
		<jsp:param name="funcName" value="VisitMain.skippage" />
	
		</jsp:include>
                   
            </div>
  <script>
  $(function(){
	  
	  VisitMain.init();
  })
  function doteest()
  {
	  //layer.tips('默认就是向右的', '#search-btn');
	$(".searchable-select").showTipError("提示信息....");
	  
  }
  </script>
            
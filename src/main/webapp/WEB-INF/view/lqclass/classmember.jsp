<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<script src="${ctxStatic }/js/layui/laypage-v1.3/laypage/laypage.js"></script>
<link rel="stylesheet" href="${ctxStatic }/js/layui/laypage-v1.3/laypage/skin/laypage.css" type="text/css">
<!--右侧内容部分背景白色开始-->
<div class="class-detail  ">	
 	<div class="y-block">
 		<div class="y-detail">
 			<div class="info-main clearfix">
 				<div class="item ">
       		<span >班级名称：</span>${classinfo.className }
       	</div>
       	<div class="item ">
       		<span>班级状态：</span>${statusname }
       	</div>
       	<div class="item ">
       		<span>开课时间：</span>${lqclassInfo.expect_starttime}
       	</div>
       	<div class="item ">
       		<span>开班人数：</span>${classinfo.realCount }
       	</div>
       	<div class="item ">
       		<span>当前人数：</span>${currentcount }
       	</div>
       	<div class="item ">
       		<span class="label-text1">技术老师：</span>
       		<a href="javascript:void(0);" class="read-span able-edit">
          	<em name="comTeacherId">
          		<c:if test="${empty comRealName}">
          			点击填写
          		</c:if>
          		<c:if test="${!empty comRealName}">
          			${comRealName}
          		</c:if>
          	</em>
          	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
          	  <span class="eidt-span">
             	<span class="select-search-box">
             		<c:if test="${empty lqclassInfo.com_teacher_id}">
                      <jsp:include page="/WEB-INF/view/include/teacher_select_js.jsp">
						<jsp:param name="inputid" value="select_teache22" />
						</jsp:include>
             		</c:if>
               		<c:if test="${!empty lqclassInfo.com_teacher_id}">
               			<jsp:include page="/WEB-INF/view/include/teacher_select_js.jsp">
						<jsp:param name="inputid" value="select_teache22" />
						<jsp:param name="selectid" value="${lqclassInfo.com_teacher_id}" />
						<jsp:param name="selecttext" value="${comRealName}" />
						</jsp:include>
               		</c:if>
                   </span>
               </span>
               <span class="error-tips"></span>
          	
       	</div>
       	<div class="item ">
       		<span class="label-text1">CEP老师：</span>
       		<a href="javascript:void(0);" class="read-span able-edit">
           	<em name="cepTeacherId">
           		<c:if test="${empty cepRealName}">
           			点击填写
           		</c:if>
           		<c:if test="${!empty cepRealName}">
           			${cepRealName}
           		</c:if>
           	</em>
           	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
           	<span class="eidt-span">
	      	<span class="select-search-box">
	             <c:if test="${empty lqclassInfo.cep_teacher_id}">
	               <jsp:include page="/WEB-INF/view/include/teacher_select_cep.jsp">
					<jsp:param name="inputid" value="select_teache_cep" />
					</jsp:include>
					      		</c:if>
					      		<c:if test="${!empty lqclassInfo.cep_teacher_id}">
					      			<jsp:include page="/WEB-INF/view/include/teacher_select_cep.jsp">
					<jsp:param name="inputid" value="select_teache_cep" />
					<jsp:param name="selectid" value="${lqclassInfo.cep_teacher_id}" />
					<jsp:param name="selecttext" value="${cepRealName}" />
					</jsp:include>
	      		</c:if>
	          </span>
	      </span>
	      <span class="error-tips"></span>
       	</div>
       	<div class="item ">
       		<span class="label-text1">职业经纪人：</span>
       		<a href="javascript:void(0);" class="read-span able-edit">
          	<em name="brokersId">
          		<c:if test="${empty broRealName}">
          			点击填写
          		</c:if>
          		<c:if test="${!empty broRealName}">
          			${broRealName}
          		</c:if>
          	</em>
          	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
          	 <span class="eidt-span">
           	<span class="select-search-box">
                   <c:if test="${empty lqclassInfo.cep_teacher_id}">
                    <jsp:include page="/WEB-INF/view/include/teacher_select_bro.jsp">
					<jsp:param name="inputid2" value="select_teache_bro2" />
					</jsp:include>
	           		</c:if>
	           		<c:if test="${!empty lqclassInfo.cep_teacher_id}">
	           			<jsp:include page="/WEB-INF/view/include/teacher_select_bro.jsp">
					<jsp:param name="inputid2" value="select_teache_bro2" />
					<jsp:param name="selectid" value="${lqclassInfo.chr_teacher_id}" />
					<jsp:param name="selecttext" value="${broRealName}" />
					</jsp:include>
           		</c:if>
               </span>
           </span>
           <span class="error-tips"></span>
       	</div>
       	
       	<div  class="item">
       		<span class="label-text1">课程名称：</span>
       		<a href="javascript:void(0);" class="read-span able-edit">
          	<em name="courseTypeCode">
          		<c:if test="${empty lqclassInfo.course_type}">
          			点击填写
          		</c:if>
          		<c:if test="${!empty lqclassInfo.course_type}">
           		<c:forEach items="${courseType}" var="ct">
           			<c:if test="${lqclassInfo.course_type==ct.value}">${ct.label}</c:if>
           		</c:forEach>
          		</c:if>
          	</em>
          	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
          </a>
          <span class="eidt-span">
              <select id="courseTypeCode">
                 <option value="">请选择</option>
                 <c:forEach items="${courseType}" var="ct">
                		 <option value="${ct.value}" <c:if test='${ct.value==lqclassInfo.course_type}'>selected</c:if>>${ct.label}</option>
                 </c:forEach>
              </select>
          </span>
          <span class="error-tips"></span>
       	</div>
       	<div class="item">
       		<span>已上课时/总课时：</span>${classtime}/${totalClass}
       	</div>
       	
       	<div class="item">
       		<span>拟开课日期：</span>
			<span class="read-span only-read">
           		<em>${lqclassInfo.expect_starttime}</em>
           </span>
       	</div>
 			</div>
      	<div class="info-other clearfix">
      		<div class="item">
       		<span class="label-text1">拟校园结课日期：</span>
       		<a href="javascript:void(0);" class="read-span able-edit">
            	<em name="expectSchoolEndtime">
            		<c:if test="${empty lqclassInfo.expect_school_endtime}">暂无</c:if>
            		<c:if test="${!empty lqclassInfo.expect_school_endtime}"><fmt:formatDate value="${lqclassInfo.expect_school_endtime}" pattern="yyyy-MM-dd"/></c:if>
            	</em>
            	</a>
            	<span class="eidt-span">
               <input type="text"  id="expectSchoolEndtime"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',isShowClear:true})"/>
              </span>
              <span class="error-tips"></span>
		</div>
       	<div class="item">
       		<span class="label-text1">QQ群： </span>
       		<a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="qqGroup">
                                		<c:if test="${empty lqclassInfo.qq_group}">点击填写</c:if>
                                		<c:if test="${!empty lqclassInfo.qq_group}">${lqclassInfo.qq_group}</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
                                <span class="eidt-span"><input id="qqGroup" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
       	</div>
       	<div class="item">
       		<span >创建目的：</span>
       		<span class="read-span only-read">
        	<em>
        		<c:forEach items="${classGoal}" var="cg">
        			<c:if test="${cg.value==lqclassInfo.class_goal}">${cg.label}</c:if>
        		</c:forEach>
        	</em> 
       	 </span>
       	</div>
       	<div class="item">
       		<span class="label-text1">校园结课日期：</span>
       		<a href="javascript:void(0);" class="read-span able-edit">
				<em name="schoolEndtime">
					<c:if test="${empty lqclassInfo.school_endtime}">暂无</c:if>
				<c:if test="${!empty lqclassInfo.school_endtime}"><fmt:formatDate value="${lqclassInfo.school_endtime}" pattern="yyyy-MM-dd"/></c:if>
					</em>
					<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
				</a>
				<span class="eidt-span">
				   <input type="text"  id="schoolEndtime"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',isShowClear:true})"/>
				</span>
				<span class="error-tips"></span>
       	</div>
       	<!--<div class="item input act">act切换input显影-->
       	<div class="item input">
       		<span class="label-text1">班长：</span>
       		<a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="monitorId">
                                		<c:if test="${empty  lqclassInfo.monitor_id||lqclassInfo.monitor_id==-1}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty  lqclassInfo.monitor_id}">
                                			<c:forEach items="${lqclassStuList}" var="sl">
                                				<c:if test="${sl.user_id==lqclassInfo.monitor_id}">${sl.real_name}</c:if>
                                			</c:forEach>
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                	<select id="monitorId">
                                    	<option value="-1">请选择</option>
                                       <c:forEach items="${lqclassStuList}" var="sl">
                                       		<option value="${sl.user_id}" <c:if test='${sl.user_id==lqclassInfo.monitor_id}'>selected</c:if>>${sl.real_name}</option>
                                       </c:forEach>
                                    </select>
                                </span>
                                <span class="error-tips"></span>
       	</div>
       	<div class="item">
       		<span >创建时间：</span>
       		<span class="read-span only-read">
                                	<em><fmt:formatDate value="${lqclassInfo.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></em>
                                </span>
       	</div>
       	<div class="item ">
       		<span >拟结业日期：</span> <span class="read-span only-read">
                                	<em>${lqclassInfo.expect_graduate_time}</em>
                                </span>
       	</div>
       	
       	<div class="item ">
       		<span  class="label-text1">班级一级类型：</span>   
      			<a href="javascript:void(0);" class="read-span able-edit">
			<em name="typePre">
				<c:forEach items="${classTypePre}" var="ctp">
			<c:if test="${lqclassInfo.type_pre==ctp.value}">${ctp.label}</c:if>
			</c:forEach>
				</em>
				<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
			<span class="eidt-span">
				<select id="typePre">
			        <c:forEach items="${classTypePre}" var="ctp">
			<option value="${ctp.value}" <c:if test='${ctp.value==lqclassInfo.type_pre}'>selected</c:if>>${ctp.label}</option>
			</c:forEach>
			    </select>
			</span>
			<span class="error-tips"></span>
       	</div>
       	<div class="item ">
       		<span>创建人：</span>
 <span class="read-span only-read">
                                	<em>${lqclassInfo.create_user}</em>
                                </span>
       	</div>
       	<div class="item ">
       		<span>结业日期：</span>
       		 <span class="read-span only-read">
             	<em>${lqclassInfo.expect_graduate_time}</em>
             </span>
       	</div>
       	
       	<div class="item ">
       		<span class="label-text1">班级二级类型：</span>
       		 <a href="javascript:void(0);" class="read-span able-edit">
				<em name="typeReal">
					<c:forEach items="${classTypeRear}" var="ctr">
				<c:if test="${lqclassInfo.type_real==ctr.value}">${ctr.label}</c:if>
				</c:forEach>
					</em>
					<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
				<span class="eidt-span">
				    <select id="typeReal">
				        <c:forEach items="${classTypeRear}" var="ctr">
				<option value="${ctr.value}" <c:if test='${ctr.value==lqclassInfo.type_real}'>selected</c:if>>${ctr.label}</option>
				</c:forEach>
				    </select>
				</span>
				<span class="error-tips"></span>
       	</div>
       	<div class="item">
       		<span>关闭时间：</span>
			<span class="read-span only-read">
              	<em>
              		<c:if test="${empty lqclassInfo.close_time}">暂无</c:if>
              		<c:if test="${!empty lqclassInfo.close_time}"><fmt:formatDate value="${lqclassInfo.close_time}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>
              	</em></span>
       	</div>
      	</div>
      	<div class="drop"></div>
      </div>
      <div class="y-tabs clearfix">
      	<a href="javascript:void(0);" class="act">班级学员</a>
      	<a href="javascript:void(0);">课程列表</a>
     	<a href="javascript:void(0);">签到记录</a>
		<a href="javascript:void(0);">每日一讲</a>
		<a href="javascript:void(0);">学生周报</a>
		<a href="javascript:void(0);">班级跟踪记录</a>
		<a href="javascript:void(0);">班级状态流水</a>
      </div>
 	</div>
 	
    <div class="class-table-student     con-ggcbox1">
    	 <div ><a class="c-blue" href="javascript:void(0);"  id="changeClass_member">学员转班</a></div>
    	<div class="class-of-stuTable" id="myclassTable"></div>
    </div>
    <!-- 课程列表 -->
    <div class="con-ggcbox1">
    	
    </div>
       <!-- 签到记录-->
    <div class="con-ggcbox1"  style="display: none"> 
    	<div >
       			<input name="startTime" id="startTime"  placeholder="授课开始时间" type="text"   class="val-info Wdate" readonly onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')||\'2020-10-01\'}',dateFmt:'yyyy-MM-dd',isShowClear:true})"/>
       			<span class="span">至</span>
       			<input name="endTime" id="endTime"  placeholder="授课结束时间" type="text"   class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true ,minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'2020-10-01'})"/>
        		
        		<button class="btn btn-blue"  onclick="ClassDetail.dopagechange()">查询</button>
     	</div>
    	<div class="table-scroll-parent" id="myclassTable1" style="padding-top: 5px"></div> 
    </div>
     <!-- 每日一讲-->
    <div class="con-ggcbox1" style="display: none">
    	<div class="table-scroll-parent" id="myclassTable2" style="padding-top: 5px"></div> 
    	<div id="demo1"  style="padding-bottom: 10px; text-align: right;"></div>
    </div>
	<!--学生周报 -->
    <div class="con-ggcbox1">
    	 
    </div>
    <!-- <div class="search-part-tall">
        <div class="table-scroll-parent" id="myclassTable">
        </div>
    </div> -->
    
     <!-- ============================================================================================== -->
                <div class="con-ggcbox   con-ggcbox1"  >
                	<c:if test="${fn:length(classVisitList)==0}">
                   	  	<!-- 暂时没有样式开始 -->
                      	<div class="no-class-info">
                       		<span>暂时没有班级跟踪记录</span>
                     	</div>
                      	<!--   暂时没有样式结束 -->
                    </c:if>
                	<c:if test="${fn:length(classVisitList)!=0}">  
	                    <div class="stu-interview-info">
	                    	<c:forEach items="${classVisitList}" var="cv" varStatus="status">
	                    		<c:if test="${status.index<3}">
			                    	<!--班级跟踪记录一条开始-->
			                        <div class="inter-recode">
			                        	<p class="clearfix">
			                        		<label class="inter-label">创建人：</label>
			                            	<span class="bulid-people">${cv.real_name}</span>
			                                <label class="inter-label">所在部门：</label>
			                                <span class="bulid-people">${cv.depname}</span>
			                            	<label class="inter-label">创建时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${cv.visit_time}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			                            </p>
			                            <div class="rel-fangtan-con">
			                                <label class="label-blue-fangtan">内&#12288;&#12288;容：</label>
			                                <span class="aim-talk">${cv.visit_content}</span>
			                            </div>
			                        </div>
			                        <!--班级跟踪记录一条结束-->
	                        	</c:if>
	                        </c:forEach>
	                        
	                        
	                        <!--班级跟踪记录查看更多开始-->
	                        <div class="more-list-disply">
	                        	<c:forEach items="${classVisitList}" var="cv" varStatus="status">
							   	    <c:if test="${status.index>=3}">
			                            <div class="inter-recode">
			                                <p class="clearfix">
			                                	<label class="inter-label">创建人：</label>
			                                    <span class="bulid-people">${cv.real_name}</span>
			                                    <label class="inter-label">所在部门：</label>
			                                    <span class="bulid-people">${cv.depname}</span>
			                                    <label class="inter-label">创建时间：</label>
			                                    <span class="bulid-people"><fmt:formatDate value="${cv.visit_time}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			                                </p>
			                                <div class="rel-fangtan-con">
			                                    <label class="label-blue-fangtan">内&#12288;&#12288;容：</label>
			                                    <span class="aim-talk">${cv.visit_content}</span>
			                                </div>
			                            </div>
		                            </c:if>
	                            </c:forEach>
	                        </div>
	                        <!--班级跟踪记录查看更多结束-->
	                        <c:if test="${classVisitSize>3}">
		                        <div class="center mb20">
		                        	<a class="sl-down-btn" href="javascript:void(0);">查看更多</a><a class="sl-up-btn" href="javascript:void(0);">收起</a>
		                        </div>
	                        </c:if>
	                    </div>
                    </c:if>
                </div>
                <!-- ==========================================班级状态流水================================================ -->
                <div class="con-ggcbox  con-ggcbox1">
                	<!--暂时没有样式开始-->
                   	<c:if test="${fn:length(classStatusList)==0}">
                        <div class="no-class-info">
                       		<span>暂时没有班级状态流水</span>
                        </div>
                    </c:if>
                    <!--暂时没有样式结束-->

                    <c:if test="${fn:length(classStatusList)!=0}">
	                    <div class="stu-interview-info">
	                    	<!-- 当前状态  start  -->
	                    	<c:forEach items="${classStatusList}" var="csl">
		                    	<c:if test="${empty csl.end_time}">
		                    		
			                    	<!--班级状态流水一条开始-->
			                        <div class="inter-recode">
			                        	<p class="clearfix">
			                            	<label class="inter-label">当前状态：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${classStatus}" var="cs">
			                            			<c:if test="${cs.value==csl.newstatus}">${cs.label}</c:if>
			                            		</c:forEach>
			                            	</span>
			                            </p>
			                            <!-- 未开课 -->
			                            <c:if test="${csl.newstatus==NOCLASSES}">
				                            <p class="clearfix">
				                            	<label class="inter-label">班级创建时间：</label><span class="bulid-people">
				                            		<fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/>
				                            	</span>
				                            </p>
			                            </c:if>
			                            <!-- 授课中 -->
			                            <c:if test="${csl.newstatus==INTHELECTURE}">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label><span class="bulid-people">
				                            		<fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/>
				                            	</span>
				                            </p>
			                            </c:if>
			                            <!-- 集训前结课 -->
			                            <c:if test="${csl.newstatus==BEFORETHETRAININGSESSION}">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label><span class="bulid-people">
				                            		<fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">已完成课时数量：</label>
				                            	<span class="bulid-people">${csl.finishcount}课时</span>
				                            </p>
			                            </c:if>
			                            <!-- 集训中 -->
			                            <c:if test="${csl.newstatus==INTHETRAINING }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label><span class="bulid-people">
				                            		<fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/>
				                            	</span>
				                            </p>
			                            </c:if>
			                            <!-- 结业 -->
			                            <c:if test="${csl.newstatus==THEGRADUATION }">
				                            <p class="clearfix">
				                            	<label class="inter-label">结业时间：</label><span class="bulid-people">
				                            		<fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">拟就业开始时间：</label><span class="bulid-people">
				                            		<fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">已完成课时数量：</label>
				                            	<span class="bulid-people">${csl.finishcount}课时</span>
				                            </p>
			                            </c:if>
			                            <!-- 就业中 -->
			                            <c:if test="${csl.newstatus==INTHEEMPLOYMENT }">
				                            <p class="clearfix">
				                            	<label class="inter-label">就业开始时间：</label><span class="bulid-people">
				                            		<fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/>
				                            	</span>
				                            </p>
			                            </c:if>
			                            <!-- 100%就业 -->
			                            <c:if test="${csl.newstatus==OFTHEEMPLOYMENT }">
				                            <p class="clearfix">
				                            	<label class="inter-label">就业结束时间：</label><span class="bulid-people">
				                            		<fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/>
				                            	</span>
				                            </p>
			                            </c:if>
			                            <!-- 关闭 -->
			                            <c:if test="${csl.newstatus==SHUTDOWN }">
				                            <p class="clearfix">
				                            	<label class="inter-label">关闭时间：</label><span class="bulid-people">
				                            		<fmt:formatDate value="${csl.end_time}" pattern="yyyy-MM-dd"/>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">已完成课时数量：</label>
				                            	<span class="bulid-people">${csl.finishcount}课时</span>
				                            </p>
			                            </c:if>
			                            
			                        	<div class="rel-fangtan-con">
			                                <label class="label-blue-fangtan">备&#12288;&#12288;注：</label>
			                                <span class="aim-talk">${csl.remark}</span>
			                            </div>
			                        </div>
			                        <!--班级状态流水一条结束-->	
			                        
		                        </c:if>
	                        </c:forEach>
	                        <!-- 当前状态  end  -->
	                        
	                        <!--班级状态查看更多开始-->
	                        <div class="more-list-disply">
	                        	<!-- 历史状态   start  -->
	                        	<c:forEach items="${classStatusList}" var="csl">
			                    	<c:if test="${!empty csl.end_time}">
			                            <div class="inter-recode">
			                                <p class="clearfix">
			                                    <label class="inter-label">状态：</label><span class="bulid-people">
			                                    	<c:forEach items="${classStatus}" var="cs">
			                            				<c:if test="${cs.value==csl.newstatus}">${cs.label}</c:if>
			                            			</c:forEach>
			                                    </span>
			                                </p>
			                                
			                                
			                                <!-- 未开课   -->
			                                <c:if test="${csl.newstatus==NOCLASSES }">
				                                <p class="clearfix">
				                                    <label class="inter-label">班级创建时间：</label>
				                                    <span class="bulid-people"><fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                                </p>
			                                </c:if>
			                                <!-- 授课中  -->
			                                <c:if test="${csl.newstatus==INTHELECTURE }">
				                                <p class="clearfix">
				                                    <label class="inter-label">状态发生时间：</label>
				                                    <span class="bulid-people"><fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                                </p>
			                                </c:if>
			                                <!-- 集训前结课  -->
			                                <c:if test="${csl.newstatus==BEFORETHETRAININGSESSION }">
				                                <p class="clearfix">
				                                    <label class="inter-label">状态发生时间：</label>
				                                    <span class="bulid-people"><fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                                </p>
				                                <p class="clearfix">
				                                    <label class="inter-label">已完成课时数量：</label>
				                                    <span class="bulid-people">${csl.finishcount}课时</span>
				                                </p>
			                                </c:if>
			                                <!-- 集训中  -->
			                                <c:if test="${csl.newstatus==INTHETRAINING }">
				                                <p class="clearfix">
				                                    <label class="inter-label">状态发生时间：</label>
				                                    <span class="bulid-people"><fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                                </p>
			                                </c:if>
			                                <!-- 结业  -->
			                                <c:if test="${csl.newstatus==THEGRADUATION }">
				                                <p class="clearfix">
				                                    <label class="inter-label">结业时间：</label>
				                                    <span class="bulid-people"><fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                                </p>
				                                <p class="clearfix">
				                                    <label class="inter-label">拟就业开始时间：</label>
				                                    <span class="bulid-people"><fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                                </p>
				                                <p class="clearfix">
				                                    <label class="inter-label">已完成课时数量：</label>
				                                    <span class="bulid-people">${csl.finishcount}课时</span>
				                                </p>
			                                </c:if>
			                                <!-- 就业中  -->
			                                <c:if test="${csl.newstatus==INTHEEMPLOYMENT }">
				                                <p class="clearfix">
				                                    <label class="inter-label">就业开始时间：</label>
				                                    <span class="bulid-people"><fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                                </p>
			                                </c:if>
			                                <!-- 100%就业  -->
			                                <c:if test="${csl.newstatus==OFTHEEMPLOYMENT }">
				                                <p class="clearfix">
				                                    <label class="inter-label">就业结束时间：</label>
				                                    <span class="bulid-people"><fmt:formatDate value="${csl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                                </p>
			                                </c:if>
			                                <!-- 关闭  -->
			                                <c:if test="${csl.newstatus==SHUTDOWN }">
				                                <p class="clearfix">
				                                    <label class="inter-label">关闭时间：</label>
				                                    <span class="bulid-people"><fmt:formatDate value="${csl.end_time}" pattern="yyyy-MM-dd"/></span>
				                                </p>
				                                <p class="clearfix">
				                                    <label class="inter-label">已完成课时数量：</label>
				                                    <span class="bulid-people">${csl.finishcount}课时</span>
				                                </p>
			                                </c:if>
			                                <div class="rel-fangtan-con clearfix">
			                                    <label class="label-blue-fangtan fl">备&#12288;&#12288;注：</label>
			                                    <span class="aim-talk fl">${csl.remark}</span>
			                                </div>
			                            </div> 
		                            </c:if>
	                        	</c:forEach>   
	                        	<!-- 历史状态  end  -->
	                        </div>
	                        <!--班级状态查看更多结束-->
	                        <c:if test="${classStatusSize>0}">
		                        <div class="center mb20">
		                        	<a class="sl-down-btn" href="javascript:void(0);">查看更多</a><a class="sl-up-btn" href="javascript:void(0);">收起</a>
		                        </div>
	                        </c:if>
	                    </div> 
                    </c:if>
                </div>
                <!-- =============================================================================================== -->
</div>   
<!--右侧内容部分背景白色结束-->

<script>
var functionIds="${myFunction}";

  $(function(){
	  
	  ClassMember.init( ${classinfo.lqClassId} );
  });
  $(function(){
		$(".drop,.item.input label").click(function(){
			$(this).parent().toggleClass("act");
		});
	}); 
</script>


<script type="text/javascript"> 
$(function(){
	var _ClassId= "${lqclassInfo.lq_class_id}";
	
    <c:if  test="${lq:ifIn(myFunction,'80')}">
		ClassDetail.init(true,_ClassId);
    </c:if> 
    
    <c:if  test="${not lq:ifIn(myFunction,'80')}">
		ClassDetail.init(false,_ClassId);
	</c:if> 

}); 

</script>
<!--班级学员列表弹框结束-->
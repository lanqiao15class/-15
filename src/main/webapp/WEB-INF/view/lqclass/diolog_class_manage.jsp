<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<input type="hidden" id="resourcePath" value="${ctxResource}">
<!--班级详情弹框开始-->
<div class="class-info-dialog dialog-box">
	<div class="diaTitle">
        <h2 class="dialog-tith2 clearfix">
            <p class="fl">
                <span class="tith2-span">${lqclassInfo.class_name}</span>
                <a class="icon-mark bg-blue" href="javascript:void(0)">
                    <span class="mark-rel"><em class="mark-name">重点</em></span>
                </a>
                <a class="icon-mark bg-orange" href="javascript:void(0)">
                    <span class="mark-rel"><em class="mark-name">我的</em></span>
                </a>
            </p>
            <span class="c-red stu-status fr">
            	<c:forEach items="${classStatus}" var="cs">
            		<c:if test="${cs.value==lqclassInfo.classStatus}">${cs.label}</c:if>
            	</c:forEach>
            </span>
        </h2>
    </div>
    <div class="contbox contbox-stuinfo">
    	<div class="tabClick tabClick-stuinfo">
        	<a class="curr" href="javascript:void(0)">基本信息</a>
            <a href="javascript:void(0)">班级跟踪记录</a>
            <a href="javascript:void(0)">班级状态流水</a>            
        </div>
        <!--切换内部部分开始-->
        <div class="cbox-rel">
            <div class="tabContant tabContant-stuinfo">
                <div class="con-ggcbox" style="display:block;">
                    <div class="stu-basic-info">
                        <!--基本信息展示部分开始-->
                        <div class="clearfix">
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">班级一级类型：</i></label>
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
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">班级二级类型：</i></label>
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
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">课程类别：</i></label>
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
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">拟开课日期：</i></label>
                                <span class="read-span only-read">
                                	<em>${lqclassInfo.expect_starttime}</em>
                                </span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">开课日期：</i></label>
                                <span class="read-span only-read">
                                	<em>
                                		<c:if test="${empty lqclassInfo.start_time}">暂无</c:if>
                                		<c:if test="${!empty lqclassInfo.start_time}">${lqclassInfo.start_time}</c:if>
                                	</em>
                                </span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">拟校园结课日期：</i></label>
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
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">校园结课日期：</i></label>
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
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">拟结业日期：</i></label>
                                <span class="read-span only-read">
                                	<em>${lqclassInfo.expect_graduate_time}</em>
                                </span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">结业日期：</i></label>
                                <span class="read-span only-read">
                                	<em>
                                		<c:if test="${empty lqclassInfo.graduate_time}">暂无</c:if>
                                		<c:if test="${!empty lqclassInfo.graduate_time}">${lqclassInfo.graduate_time}</c:if>
                                	</em>
                                </span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">开班人数：</i></label>
                                <span class="read-span only-read">
                                	<em>${lqclassInfo.stu_count}</em>
                                </span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">当前人数：</i></label>
                                <span class="read-span only-read">
                                	<em>${lqclassInfo.real_count}</em>
                                </span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">技术老师：</i></label>
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
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">CEP老师：</i></label>
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
                            <div class="line-item">
                                <label class="left-label"><i class="label-text">职业经纪人：</i></label>
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
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">QQ群：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="qqGroup">
                                		<c:if test="${empty lqclassInfo.qq_group}">点击填写</c:if>
                                		<c:if test="${!empty lqclassInfo.qq_group}">${lqclassInfo.qq_group}</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
                                <span class="eidt-span"><input id="qqGroup" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">班长：</i></label>
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
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">创建目的：</i></label>
                                <span class="read-span only-read">
                                	<em>
                                		<c:forEach items="${classGoal}" var="cg">
                                			<c:if test="${cg.value==lqclassInfo.class_goal}">${cg.label}</c:if>
                                		</c:forEach>
                                	</em>
                                </span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">创建时间：</i></label>
                                <span class="read-span only-read">
                                	<em><fmt:formatDate value="${lqclassInfo.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></em>
                                </span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">创建人：</i></label>
                                <span class="read-span only-read">
                                	<em>${lqclassInfo.create_user}</em>
                                </span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">关闭时间：</i></label>
                                <span class="read-span only-read">
                                	<em>
                                		<c:if test="${empty lqclassInfo.close_time}">暂无</c:if>
                                		<c:if test="${!empty lqclassInfo.close_time}"><fmt:formatDate value="${lqclassInfo.close_time}" pattern="yyyy-MM-dd HH:mm:ss"/></c:if>
                                	</em></span>
                            </div>
                        </div>
                        
                        <!--基本信息展示部分结束-->
                    </div>
                </div>
                <!-- ============================================================================================== -->
                <div class="con-ggcbox">
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
                <div class="con-ggcbox">
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
        </div>
        <!--切换内部部分结束-->
    </div>
</div>
<!--班级详情弹框结束-->
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

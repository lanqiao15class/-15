<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<input type="hidden" id="resourcePath" value="${ctxResource}">
<!--学员详情弹框开始-->
<div class="stu-info-dialog dialog-box">
	<div class="diaTitle">
        <h2 class="dialog-tith2 clearfix">
            <p class="fl">
                <span class="tith2-span"><em name="realName">${user.realName}</em></span>
                <c:forEach items="${dataTagList}" var="dt">
                	<c:if test="${dt.tagtype==2}">
		                <a class="icon-mark bg-blue" href="javascript:void(0)">
		                    <span class="mark-rel"><em class="mark-name">重点</em></span>
		                </a>
                	</c:if>
                	<c:if test="${dt.tagtype==1}">
		                <a class="icon-mark bg-orange" href="javascript:void(0)">
		                    <span class="mark-rel"><em class="mark-name">我关注</em></span>
		                </a>
                	</c:if>
                </c:forEach>
            </p>
            <span class="c-red stu-status fr">
            	<c:forEach items="${studentStatus}" var="ss">
            		<c:if test="${ss.value==stuInfo.status}">${ss.label}</c:if>
            	</c:forEach>
            </span>
        </h2>
    </div>
    <div class="contbox contbox-stuinfo">
    	<div class="tabClick tabClick-stuinfo" id="hideTagA">
    	
    		<!-- 选项卡  start -->
        	<a class="curr" href="javascript:void(0)" name="jbxx">基本信息</a>
            <a href="javascript:void(0)"  name="lxfs">联系方式</a>
            <a href="javascript:void(0)"  name="bjxx">班级信息</a>
            <a href="javascript:void(0)"  name="jyxx">就业信息</a>
            <a href="javascript:void(0)"  name="zyxx">职业信息</a>
            <a href="javascript:void(0)"  name="bmf">报名费</a>
            <a href="javascript:void(0)"  name="sxf">实训费</a>
            <a href="javascript:void(0)"  name="zstd">招生团队</a>
            <a href="javascript:void(0)"  name="ftjl">跟踪记录</a>
            <a href="javascript:void(0)"  name="xyztls">学员状态流水</a> 
            <!-- 选项卡  end -->
            
            
        </div>
        <!--切换内部部分开始-->
        <div class="cbox-rel">
            <div class="tabContant tabContant-stuinfo">
            	<!-- ============================================================================ -->
            	
                <div class="con-ggcbox" style="display: block;"  name="jbxx">
                    <div class="stu-basic-info">
                    
                        <!--基本信息展示部分开始-->
                        <div class="clearfix">
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">姓名：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="realName">
                                		<c:if test="${empty user.realName}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty user.realName}">
                                			${user.realName }
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span"><input id="realName" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">性别：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="sex">
                                		<c:if test="${empty user.sex}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty user.sex}">
	                                		<c:forEach items="${sex}" var="sex">
	                                			<c:if test="${sex.value==user.sex}">${sex.label}</c:if>
	                                		</c:forEach>
                                		</c:if>
                                		<input type="hidden" id="sexTemp" value="${user.sex}"/>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                	<span class="sex-check">
                                	<input type="radio" name="sex" value="0" <c:if test='${user.sex==0}'>checked</c:if>> 男</span>
            						<span class="sex-check">
            						<input type="radio" name="sex" value="1" <c:if test='${user.sex==1}'>checked</c:if>> 女</span>
                                </span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">民族：</i></label>
                                <a class="read-span able-edit">
                                <em name="nation">
                                	<c:if test="${empty user.nation}">
                                		点击填写
                                	</c:if>
                                	<c:if test="${!empty user.nation}">
                                		${user.nation}
                                	</c:if>
                                </em>
                                <b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
                                <span class="eidt-span">
                                	<c:if test="${empty user.nation}">
                                		<jsp:include page="/WEB-INF/view/include/stuInfo_nation_select_search.jsp">
											<jsp:param name="inputid" value="nation_select_yxstuInfo" />
										</jsp:include>
                                	</c:if>
                                	<c:if test="${!empty user.nation}">
                                		<jsp:include page="/WEB-INF/view/include/stuInfo_nation_select_search.jsp">
											<jsp:param name="inputid" value="nation_select_yxstuInfo" />
											<jsp:param name="selectid" value="${user.nation}" />
											<jsp:param name="selecttext" value="${user.nation}" />
										</jsp:include>
                                	</c:if>
                                   
                                </span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">身份证号：</i></label>
                                <a class="read-span able-edit" href="javascript:void(0);">
                                	<em name="idCard">
                                		<c:if test="${empty user.idCard}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty user.idCard}">
                                			${user.idCard}
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span"><input id="idCard" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="label-text">出生年月：</i></label>
                                <span class="read-span only-read">
                                	<em name="birth">
                                		<c:if test="${empty user.birth}">
                                			暂无
                                		</c:if>
                                		<c:if test="${!empty user.birth}">
		                                	<fmt:formatDate value="${user.birth}" pattern="yyyy-MM-dd"/> 
                                		</c:if>
                                	</em>
                                </span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">院校名称：</i></label>
                                <a class="read-span able-edit" href="javascript:void(0);">
                                	<em name="univCode">
                                		<c:if test="${empty univName}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty univName }">
                                			${univName}
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                	<c:if test="${empty stuInfo.univCode}">
                                		<jsp:include page="/WEB-INF/view/include/stuInfo_school_select_likesearch.jsp">
											<jsp:param name="inputid" value="select_school_yxname2" />
										</jsp:include>
                                	</c:if>
                                	<c:if test="${!empty stuInfo.univCode}">
                                		<jsp:include page="/WEB-INF/view/include/stuInfo_school_select_likesearch.jsp">
											<jsp:param name="inputid" value="select_school_yxname2" />
											<jsp:param name="selectid" value="${stuInfo.univCode}" />
											<jsp:param name="selecttext" value="${univName}" />
										</jsp:include>
                                	</c:if>
                                </span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">院校类别：</i></label>
                                <a class="read-span able-edit">
                                	<em name="schoolTypeCode">
                                		<c:if test="${empty stuInfo.schoolTypeCode}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty stuInfo.schoolTypeCode}">
	                                		<c:forEach items="${schoolType}" var="st">
	                                			<c:if test="${st.value==stuInfo.schoolTypeCode}">${st.label}</c:if>
	                                		</c:forEach>
                                		</c:if>
                                	</em>
                                	<b href="javascript:void(0);" class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                    <select id="schoolTypeCode">
                                    	<option value="">请选择</option>
                                    	<c:forEach items="${schoolType}" var="st">
                                			<option value="${st.value}" <c:if test='${st.value==stuInfo.schoolTypeCode}'>selected</c:if>>${st.label}</option>
                                		</c:forEach>
                                    </select>
                                </span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">院校所在省：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="schoolProv">
                                		<c:if test="${empty schoolProvName}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty schoolProvName}">
	                                		${schoolProvName }
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                	<select id="province" name="provCode" onchange="DialogStuInfo.tm_change_province(this)" >
                                      	<!-- 省 -->
                                    </select>
                                    <input type="hidden"  id="provCodeTemp" value="${stuInfo.schoolProvCode}"/>
                                </span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">院校所在市：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="schoolCity">
                                		<c:if test="${empty schoolCityName }">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty schoolCityName }">
	                                		${schoolCityName }
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                    <select id="city" name="cityCode" onchange="editProvAndCity(this);">
                                      	<!-- 市 --> 
                                    </select>
                   					<input type="hidden"  id="cityCodeTemp" value="${stuInfo.schoolCityCode}"/>
                                </span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">所在年级：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="grade">
                                		<c:if test="${empty stuInfo.grade}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty stuInfo.grade}">
                                			${stuInfo.grade}
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span"><input id="grade" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="label-text">在校担任职务：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="schoolDuty">
                                		<c:if test="${empty  stuInfo.schoolDuty}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty  stuInfo.schoolDuty}">
                                			${stuInfo.schoolDuty}
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span"><input id="schoolDuty" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">所在专业：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="major">
                                		<c:if test="${empty stuInfo.major }">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty stuInfo.major }">
                                			${stuInfo.major}
                                		</c:if>
                                	</em><b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span"><input id="major" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                        </div>
                        <!--基本信息展示部分结束-->
                        
                        
                        
                        <!--查看更多的内容开始-->
                        <div class="more-list-disply">
                            <div class="line-item">
                                <label class="left-label"><i class="label-text">宿舍楼号：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="schoolDormitory">
                                		<c:if test="${empty stuInfo.schoolDormitory }">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty stuInfo.schoolDormitory }">
                                			${stuInfo.schoolDormitory}
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span"><input id="schoolDormitory" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>                            
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">就业意向：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em id="jobCityCode">
                                		<c:if test="${empty stuInfo.jobCityCode}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty stuInfo.jobCityCode}">
	                                		<c:forEach items="${jobCityCode}" var="jc">
	                                			<c:forEach items="${stuInfo.jobCityCode}" var="j" varStatus="status">
	                                				<c:if test="${status.index!=0 }">
	                                					<c:if test="${jc.value==j}">,${jc.label}</c:if>
	                                				</c:if>
	                                				
	                                				<c:if test="${status.index==0 }">
	                                					<c:if test="${jc.value==j}">${jc.label}</c:if>
	                                				</c:if>
	                                			</c:forEach>
	                                		</c:forEach>
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
                                <span class="eidt-span">
      
						       	<c:forEach items="${jobCityCode}" var="jcc">
					       			<span class="addr-check">
						        			<input type="checkbox" title="${jcc.label}" name="jobCityCode" value="${jcc.value}" <c:if test="${fn:contains(stuInfo.jobCityCode, jcc.value)}">
											checked</c:if> >${jcc.label}
					        		</span>
						       	</c:forEach>
      
                                </span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">课程类别：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="courseTypeCode">
                                		<c:if test="${empty stuInfo.courseType}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty stuInfo.courseType}">
	                                		<c:forEach items="${courseType}" var="ct">
	                                			<c:if test="${ct.value==stuInfo.courseType }">${ct.label}</c:if>
	                                		</c:forEach>
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                    <select id="courseTypeCode">
                                    	<option value="">请选择</option>
                                       <c:forEach items="${courseType}" var="ct">
                                       		<option value="${ct.value}" <c:if test='${ct.value==stuInfo.courseType}'>selected</c:if>>${ct.label}</option>
                                       </c:forEach>
                                    </select>
                                </span>
                                <span class="error-tips"></span>
                           	</div>
                           	
                            <!-- =============================按权限展示  start ====================================== -->
                            <c:if test="${qx=='zsStu'||qx=='jyStu'||qx=='hkStu'}">
	                            <div class="line-item">
	                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">报名费：</i></label>
	                                 <span class="read-span only-read">
	                                	<em name="isAvaiableCode">
	                                		<c:if test="${empty stuInfo.isAvaiable}">
	                                			暂无
	                                		</c:if>
	                                		<c:if test="${!empty stuInfo.isAvaiable}">
		                                		<c:forEach items="${isAvaiable}" var="ia">
		                                			<c:if test="${stuInfo.isAvaiable==ia.value}">${ia.label}</c:if>
		                                		</c:forEach>
	                                		</c:if>
	                                	</em>
	                                </span>
	                            </div>
	                            <div class="line-item">
	                               <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">学费：</i></label>
	                               <span class="read-span only-read">
	                                	<em name="hasPaidCode">
	                                		<c:if test="${empty stuInfo.hasPaid}">
                                				暂无
                                			</c:if>
                                			<c:if test="${!empty stuInfo.hasPaid}">
		                                		<c:forEach items="${stuHaspaid}" var="sh">
		                                			<c:if test="${stuInfo.hasPaid==sh.value}">${sh.label}</c:if>
		                                		</c:forEach>
                                			</c:if>
	                                	</em>
	                                </span>
	                            </div>
	                            <div class="line-item">
	                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">培训开始时间：</i></label>
	                                <span class="read-span only-read">
	                                	<em>
	                                		<c:if test="${empty stuInfo.beginStudytime}">
                                				暂无
                                			</c:if>
                                			<c:if test="${!empty stuInfo.beginStudytime}">
                                				<fmt:formatDate value="${stuInfo.beginStudytime}" pattern="yyyy-MM-dd"/> 
                                			</c:if>
	                                	</em>
	                                </span>
	                            </div>
	                            <div class="line-item">
	                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">结业时间：</i></label>
	                                 <span class="read-span only-read">
	                                	<em>
	                                		<c:if test="${empty stuInfo.endStudytime}">
                                				暂无
                                			</c:if>
	                                		<c:if test="${!empty stuInfo.endStudytime}">
	                                			<fmt:formatDate value="${stuInfo.endStudytime}" pattern="yyyy-MM-dd"/> 
	                                		</c:if>
	                                	</em>
	                                </span>
	                            </div>
	                            <div class="line-item">
	                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">学员编号：</i></label>
	                                <span class="read-span only-read">
	                                	<em>
	                                		<c:if test="${empty stuInfo.stuNo}">
                                				暂无
                                			</c:if>
	                                		<c:if test="${!empty stuInfo.stuNo}">
	                                			${stuInfo.stuNo}
	                                		</c:if>
	                                	</em>
	                                </span>
	                            </div>
	                            <div class="line-item">
	                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">学员状态：</i></label>
	                                <span class="read-span only-read">
		                                <em>
		                                	<c:if test="${empty stuInfo.status}">
                                				暂无
                                			</c:if>
	                                		<c:if test="${!empty stuInfo.status}">
			                                	<c:forEach items="${studentStatus}" var="ss">
								            		<c:if test="${ss.value==stuInfo.status}">${ss.label}</c:if>
								            	</c:forEach>
	                                		</c:if>
		                                </em>
	                                </span>
	                            </div>
                            </c:if>
                            <!-- =============================按权限展示  end ====================================== -->
                            <div class="line-item">
                                <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">身份证正面：</i>
                                  <i class="must-input-icon"></i>
                                  <i class="label-text">  <a style="color:blue;cursor:pointer" onclick="ZoomBig('${ctxResource}${stuInfo.idcardFrontImg}')">放大显示</a></i>
                                </label>
                                
                                <span class="inbox-card-span">
                                    <div class="idcard-file-box">
                                    	<c:if test="${empty stuInfo.idcardFrontImg}">
                                    		 <img src="" id="returnShowFront" name="returnShowFront">
                                    	</c:if>
                                    	<c:if test="${!empty stuInfo.idcardFrontImg}">
                                    		  <img src="${ctxResource}${stuInfo.idcardFrontImg}" id="returnShowFront" name="returnShowFront">
                                    	</c:if>
                                        
                                        <input id="frontImgName3" type="hidden" name="frontImgName3">
                                        <input class="upload-input-file"  type="file" id="frontImgFile3" name="imgFile">
                                        <div class="upload-bgimg">
                                            <p class="upload-icon"><i class="Hui-iconfont">&#xe642;</i></p>
                                            <p class="upload-word">上传文件</p>
                                    	</div>
                                    </div>
                                </span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="align-right-label"><i class="must-input-icon"></i>
                                <i class="label-text">身份证反面：</i>
                                   <i class="must-input-icon"></i>
                                  <i class="label-text">  <a style="color:blue;cursor:pointer" onclick="ZoomBig('${ctxResource}${stuInfo.idcardBackImg}')">放大显示</a></i>
                              
                                </label>
                                <span class="inbox-card-span">
                                    <div class="idcard-file-box">
                                        <c:if test="${empty stuInfo.idcardBackImg}">
                                    		<img src="" id="returnShowBack" name="returnShowBack">
                                    	</c:if>
                                    	<c:if test="${!empty stuInfo.idcardBackImg}">
                                    		<img src="${ctxResource}${stuInfo.idcardBackImg}" id="returnShowBack" name="returnShowBack">
                                    	</c:if>
                                        <input id="backImgName3" type="hidden" name="backImgName3">
                                        <input class="upload-input-file"  type="file" id="backImgFile3" name="imgFile">
                                        <div class="upload-bgimg">
                                            <p class="upload-icon"><i class="Hui-iconfont">&#xe642;</i></p>
                                            <p class="upload-word">上传文件</p>
                                        </div>
                                    </div>
                                </span>
                                <span class="error-tips"></span>
                            </div>
                        </div>
                        <!--查看更多的内容结束-->
                        
                        
                        <div class="center mb20">
                        	<a class="sl-down-btn" href="javascript:void(0);">查看更多</a><a class="sl-up-btn" href="javascript:void(0);">收起</a>
                        </div>
                    </div>
                </div>
                <!-- ============================================================================ -->
                
                <div class="con-ggcbox"  name="lxfs" >
                    <div class="stu-link-cont clearfix">
                        <!--联系方式开始-->
                        <div class="clearfix">
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">电话：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="tel">
                                		<c:if test="${empty user.tel}">点击填写</c:if>
                                		<c:if test="${!empty user.tel}">${user.tel}</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span"><input id="tel" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                            	<label class="left-label"><i class="label-text">QQ：</i></label>
                                <a class="read-span able-edit" href="javascript:void(0);">
                                <em name="qq">
                                	<c:if test="${empty user.qq}">点击填写</c:if>
                                	<c:if test="${!empty user.qq}">${user.qq}</c:if>
                                </em>
                                <b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
                                <span class="eidt-span"><input id="qq" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">电子邮箱：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="email">
                                		<c:if test="${empty user.email}">点击填写</c:if>
                                		<c:if test="${!empty user.email}">${user.email}</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span"><input id="email" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="label-text">通讯地址：</i></label>
                                <a class="read-span able-edit" href="javascript:void(0);">
                                	<em name="address">
                                		<c:if test="${empty stuInfo.address}">点击填写</c:if>
                                		<c:if test="${!empty stuInfo.address}">${stuInfo.address}</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
                                <span class="eidt-span"><input id="address" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                               <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">家庭联系人：</i></label>
                                <a class="read-span able-edit" href="javascript:void(0);">
                                <em name="parentInfo">
                                	<c:if test="${empty stuInfo.parentInfo}">点击填写</c:if>
                                	<c:if test="${!empty stuInfo.parentInfo}">${stuInfo.parentInfo}</c:if>
                                </em>
                                <b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
                                <span class="eidt-span"><input id="parentInfo" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                            <div class="line-item">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">家庭联系人电话：</i></label>
                                <a class="read-span able-edit" href="javascript:void(0);">
                                <em name="parentTel">
                                	<c:if test="${empty stuInfo.parentTel}">
                                		点击填写
                                	</c:if>
                                	<c:if test="${!empty stuInfo.parentTel}">
                                		${stuInfo.parentTel}
                                	</c:if>
                                </em>
                                <b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b></a>
                                <span class="eidt-span"><input id="parentTel" disableautocomplete autocomplete="off" type="text"></span>
                                <span class="error-tips"></span>
                            </div>
                        </div>
                        <!--联系方式结束-->
                    </div>
                </div>
                <!-- ==================================班级信息========================================== -->
                
                <div class="con-ggcbox"  name="bjxx">
                    <div class="stu-class-info">
                      <c:if test="${fn:length(classLog)==0}">
                   	  <!-- 暂时没有样式开始 -->
                      <div class="no-class-info">
                       	<span>暂时没有班级信息</span>
                      </div>
                      <!--   暂时没有样式结束 -->
                      </c:if>
                      
                      <c:if test="${fn:length(classLog)!=0}">  
                      <!--   有班级信息开始 -->
                        <div class="stu-inclass-info">
                        	<dl class="current">
                               	<c:forEach items="${classLog}" var="cl">
                      				<c:if test="${empty cl.exit_time}">
	                                	<div class="list-record">
	                                        <p>
	                                            <span class="class-name c-blue">${cl.class_name}班级</span>
	                                            <span class="class-stype">
	                                            	<c:forEach items="${courseType}" var="ct">
	                                            		<c:if test="${ct.value==cl.course_type}">${ct.label}班</c:if> 
	                                            	</c:forEach>
	                                            </span>
	                                            <i class="icon-verline">|</i>
	                                            <span class="course-status">
	                                            	<c:forEach items="${classStatus}" var="cs">
	                                            		<c:if test="${cs.value==cl.status}">${cs.label}</c:if>
	                                            	</c:forEach>
	                                            </span>
	                                        </p>
	                                        <p>
	                                            <span class="startline">
	                                            	<fmt:formatDate value="${cl.create_time}" pattern="yyyy.MM.dd"/><!-- 2016.03.02 --></span>
	                                            <i class="icon-line">—</i>
	                                            <span class="deadline">至今</span>
	                                        </p>
	                                    </div>
                                    </c:if>
                     			</c:forEach>
                            </dl>
                            <dl class="more-list-disply">
                                <c:forEach items="${classLog}" var="cl">
                      				<c:if test="${!empty cl.exit_time}">
	                                    <!--   班级信息隐藏部分开始 -->
	                                    <div class="list-record">
	                                        <p>
	                                            <span class="class-name c-blue">${cl.class_name}班级</span>
	                                            <span class="class-stype">
	                                            	<c:forEach items="${courseType}" var="ct">
	                                            		<c:if test="${ct.value==cl.course_type}">${ct.label}班</c:if> 
	                                            	</c:forEach>
	                                            </span>
	                                            <i class="icon-verline">|</i>
	                                            <span class="course-status">
	                                            	<c:forEach items="${classStatus}" var="cs">
	                                            		<c:if test="${cs.value==cl.status}">${cs.label}</c:if>
	                                            	</c:forEach>
	                                            </span>
	                                        </p>
	                                        <p>
	                                            <span class="startline"><fmt:formatDate value="${cl.create_time}" pattern="yyyy.MM.dd"/></span>
	                                            <i class="icon-line">—</i>
	                                            <span class="deadline"><fmt:formatDate value="${cl.exit_time}" pattern="yyyy.MM.dd"/></span>
	                                        </p>
                                            <div class="rel-fangtan-con clearfix">
                                                <label class="label-blue-fangtan fl">退班原因：</label>
                                                <span class="aim-talk fl">${cl.remark}</span>
                                            </div>
	                                    </div>
	                                    <!--  班级信息隐藏部分结束 -->
                                    </c:if>
                          		</c:forEach>
                            </dl>
                            <div class="center mb20 mt15">
                                <a class="sl-down-btn" href="javascript:void(0);">查看历史班级信息</a><a class="sl-up-btn" href="javascript:void(0);">收起历史班级信息</a>
                            </div>
                        </div>
                      <!--   有班级信息结束 -->
                      </c:if>
                    </div>
                </div>
                <!-- ============================================================================ -->
                
                <div class="con-ggcbox"  name="jyxx">
                    <div class="stu-work-info">
                    	<c:if test="${fn:length(jobDetailList)==0}">
	                    	<!-- 暂时没有样式开始 -->
	                        <div class="no-class-info">
	                        	<span>暂时没有就业信息</span>
	                        </div>
	                        <!-- 暂时没有样式结束 -->
                    	</c:if>
                    	<c:if test="${fn:length(jobDetailList)!=0}">
                        <!-- 有就业信息开始 -->
                        <div class="stu-inwork-info">
                        	<dl class="current">
                            	
                                <c:forEach items="${jobDetailList}" var="sp" varStatus="sta">
                                	<c:if test="${dismissTime==0}"><!-- 在职  -->
                                	<c:if test="${empty sp.dismiss_time}">
                                		<dt class="tit-dt">当前就业信息</dt>
                                		<dd>
	                                		<div class="list-record">
		                                        <p>
		                                            <span class="company-name c-blue">${sp.company_name}</span>
		                                            <span class="company-stype">
		                                            	<c:forEach items="${cocompType}" var="ct">
		                                            		<c:if test="${ct.value==sp.company_type}">${ct.label}</c:if>
		                                            	</c:forEach>
		                                            </span>
		                                            <i class="icon-verline">|</i>
		                                            <span class="course-status">
		                                            	<c:forEach items="${cocompScale}" var="cs">
		                                            		<c:if test="${cs.value==sp.company_scale}">${cs.label}人</c:if>
		                                            	</c:forEach>
		                                            </span>
		                                        </p>
		                                        <p>
		                                        	<span class="work-position">${sp.position_name}</span>
		                                            <i class="icon-verline">|</i>
		                                            <span class="work-status">
		                                            	<c:if test="${sp.position_type==0}">实习</c:if>
		                                            	<c:if test="${sp.position_type==1}">正式</c:if>
		                                            </span>
		                                        </p>
		                                        <p>
		                                            <span class="startline"><fmt:formatDate value="${sp.entry_time}" pattern="yyyy.MM.dd"/></span>
		                                            <i class="icon-line">—</i>
		                                            <span class="deadline">至今</span>
		                                        </p>
	                                    	</div>
	                                	</dd>
                                    </c:if>
                                    </c:if>
                                    <c:if test="${dismissTime==1&&sta.index==0}"><!-- 离职  -->
                                		<dt class="tit-dt">当前就业信息</dt>
                                		<dd>
	                                		<div class="list-record">
		                                        <p>
		                                            <span class="company-name c-blue">${sp.company_name}</span>
		                                            <span class="company-stype">
		                                            	<c:forEach items="${cocompType}" var="ct">
		                                            		<c:if test="${ct.value==sp.company_type}">${ct.label}</c:if>
		                                            	</c:forEach>
		                                            </span>
		                                            <i class="icon-verline">|</i>
		                                            <span class="course-status">
		                                            	<c:forEach items="${cocompScale}" var="cs">
		                                            		<c:if test="${cs.value==sp.company_scale}">${cs.label}人</c:if>
		                                            	</c:forEach>
		                                            </span>
		                                        </p>
		                                        <p>
		                                        	<span class="work-position">${sp.position_name}</span>
		                                            <i class="icon-verline">|</i>
		                                            <span class="work-status">
		                                            	<c:if test="${sp.position_type==0}">实习</c:if>
		                                            	<c:if test="${sp.position_type==1}">正式</c:if>
		                                            </span>
		                                        </p>
		                                        <p>
		                                            <span class="startline"><fmt:formatDate value="${sp.entry_time}" pattern="yyyy.MM.dd"/></span>
		                                            <i class="icon-line">—</i>
		                                            <span class="deadline">至今</span>
		                                        </p>
	                                    	</div>
	                                	</dd>
                                    </c:if>
                               </c:forEach>
                            </dl>
                            <dl class="more-list-disply">
                                <dt class="tit-dt">历史就业信息</dt>
                                <dd>
                                	<c:forEach items="${jobDetailList}" var="sp">
                                	<c:if test="${!empty sp.dismiss_time}">
                                    <div class="list-record">
                                        <p>
                                            <span class="company-name c-blue">${sp.company_name}</span>
                                            <span class="company-stype">
                                            	<c:forEach items="${cocompType}" var="ct">
                                            		<c:if test="${ct.value==sp.company_type}">${ct.label}</c:if>
                                            	</c:forEach>
                                            </span>
                                            <i class="icon-verline">|</i>
                                            <span class="course-status">
                                            	<c:forEach items="${cocompScale}" var="cs">
                                            		<c:if test="${cs.value==sp.company_scale}">${cs.label}人</c:if>
                                            	</c:forEach>
                                            </span>
                                        </p>
                                        <p>
                                        	<span class="work-position">${sp.position_name}</span>
                                            <i class="icon-verline">|</i>
                                            <span class="work-status">
                                            	<c:if test="${sp.position_type==0}">实习</c:if>
	                                            <c:if test="${sp.position_type==1}">正式</c:if>
                                            </span>
                                        </p>
                                        <p>
                                            <span class="startline"><fmt:formatDate value="${sp.entry_time}" pattern="yyyy.MM.dd"/></span>
                                            <i class="icon-line">—</i>
                                            <span class="deadline"><fmt:formatDate value="${sp.dismiss_time}" pattern="yyyy.MM.dd"/></span>
                                        </p>
                                    </div>
                                    </c:if>
                                    </c:forEach>
                                </dd>
                            </dl>
                            <c:if test="${jobDetailListSize>1}">
                            <div class="center mb20 mt15">
                                <a class="sl-down-btn" href="javascript:void(0);">查看历史就业信息</a><a class="sl-up-btn" href="javascript:void(0);">收起历史就业信息</a>
                            </div>
                            </c:if>
                        </div>
                        <!--   有就业信息结束 -->
                        </c:if>
                    </div>
                </div>
                
                <!-- ============================================================================ -->
                <div class="con-ggcbox"  name="zyxx">
                    <div class="stu-work-info">
                    	<!--暂时没有样式开始-->
                    	<c:if test="${fn:length(jobDetailList)==0}">
                        <div class="no-class-info">
                        	<span>暂时没有职业信息</span>
                        </div>
                        </c:if>
                        <!--暂时没有样式结束-->
                        <!--有就业信息开始-->
                        <c:if test="${fn:length(jobDetailList)!=0}">
                        <div class="stu-inwork-info">
                        	<dl class="current">
                            	
                               
                                	<c:forEach items="${jobDetailList}" var="jd" varStatus="sta">
                                		<c:if test="${dismissTime==0}"><!-- 在职  -->
	                                	<c:if test="${empty jd.dismiss_time}">
	                                	
		                                	<dt class="tit-dt">最新职业信息<span class="ml10 c-green">
		                            			<c:forEach items="${jobStatusList}" var="js" >
		                            				<c:if test="${js.value==jd.occupation_status}">
		                            					${js.label}
		                            				</c:if>
		                            			</c:forEach>
			                            	</span></dt>
	                                		<dd>
	                                	
		                                	<div class="list-record">
		                                        <p>
		                                            <span class="company-name c-blue">${jd.company_name}</span>
		                                            <span class="company-stype">
		                                            	<c:forEach items="${cocompType}" var="ct">
		                                            		<c:if test="${ct.value==jd.company_type}">${ct.label}</c:if>
		                                            	</c:forEach>
		                                            </span>
		                                            <i class="icon-verline">|</i>
		                                            <span class="course-status">
		                                            	<c:forEach items="${cocompScale}" var="cs">
		                                            		<c:if test="${cs.value==jd.company_scale}">${cs.label}人</c:if>
		                                            	</c:forEach>
		                                            </span><span class="work-place">${jd.city_id}</span>
		                                        </p>
												<p>
		                                        	<span class="work-position">${jd.position_name}</span>
		                                            <i class="icon-verline">|</i>
		                                            <span class="work-status"><i>${jd.position_salary}</i>元/月</span>
		                                            <i class="icon-verline">|</i>
		                                            <span class="work-status">
		                                            	<c:forEach items="${inviteType}" var="it">
		                                            		<c:if test="${it.value==jd.position_type}">${it.label}</c:if>
		                                            	</c:forEach>
		                                            </span>
		                                        </p>

		                                        <p>
		                                            <span class="startline">
		                                            <fmt:formatDate value="${jd.entry_time}" pattern="yyyy.MM.dd"/></span>
		                                            <i class="icon-line">—</i>
		                                            <span class="deadline">至今</span>
		                                           <%--   <span class="deadline">
		                                             <fmt:formatDate value="${jd.dismiss_time}" pattern="yyyy.MM.dd"/></span>  --%>
		                                        </p>
		                                        <div class="work-msg-info">
		                                            <p class="clearfix">
		                                                <label class="inter-label">就业方式：</label>
		                                                <span class="bulid-people">
		                                                	<c:forEach items="${jobFromTypeList}" var="jf">
		                                                		<c:if test="${jd.jobfrom_type==jf.value}">${jf.label}</c:if>
		                                                	</c:forEach>
		                                                </span>
		                                                <label class="inter-label">企业BD：</label>
		                                                <span class="bulid-people">${jd.teacher_id}</span>
		                                            </p>
		                                            <p class="clearfix">
		                                                <label class="inter-label">入职登记人：</label>
		                                                <span class="bulid-people">${jd.entry_userid}</span>
		                                                <label class="inter-label">登记时间：</label>
		                                                <span class="bulid-people">
		                                                <fmt:formatDate value="${jd.inputtime_in}" pattern="yyyy-MM-dd"/></span>
		                                            </p>
		                            				<c:if test="${jobing!=jd.occupation_status}">
			                                            <p class="clearfix">
			                                                <label class="inter-label">离职登记人：</label>
			                                                <span class="bulid-people">${jd.dismiss_userid}</span>
			                                                <label class="inter-label">登记时间：</label>
			                                                <span class="bulid-people">
			                                                <fmt:formatDate value="${jd.inputtime_out}" pattern="yyyy-MM-dd"/></span>
			                                            </p>
			                                            <div class="rel-fangtan-con">
			                                                <label class="label-blue-fangtan">离职原因：</label>
			                                                <span class="aim-talk">${jd.dismiss_reason}</span>
			                                            </div>
		                            				</c:if>
		                                        </div>
		                                    </div>
		                                    </dd>
	                                    </c:if>
	                                    </c:if>
	                                    <c:if test="${dismissTime==1&&sta.index==0}"><!-- 离职  -->
	                                    	<dt class="tit-dt">最新职业信息<span class="ml10 c-green">
		                            			<c:forEach items="${jobStatusList}" var="js" >
		                            				<c:if test="${js.value==jd.occupation_status}">
		                            					${js.label}
		                            				</c:if>
		                            			</c:forEach>
			                            	</span></dt>
	                                		<dd>
	                                    	<div class="list-record">
		                                        <p>
		                                            <span class="company-name c-blue">${jd.company_name}</span>
		                                            <span class="company-stype">
		                                            	<c:forEach items="${cocompType}" var="ct">
		                                            		<c:if test="${ct.value==jd.company_type}">${ct.label}</c:if>
		                                            	</c:forEach>
		                                            </span>
		                                            <i class="icon-verline">|</i>
		                                            <span class="course-status">
		                                            	<c:forEach items="${cocompScale}" var="cs">
		                                            		<c:if test="${cs.value==jd.company_scale}">${cs.label}人</c:if>
		                                            	</c:forEach>
		                                            </span><span class="work-place">${jd.city_id}</span>
		                                        </p>
												<p>
		                                        	<span class="work-position">${jd.position_name}</span>
		                                            <i class="icon-verline">|</i>
		                                            <span class="work-status"><i>${jd.position_salary}</i>元/月</span>
		                                            <i class="icon-verline">|</i>
		                                            <span class="work-status">
		                                            	<c:forEach items="${inviteType}" var="it">
		                                            		<c:if test="${it.value==jd.position_type}">${it.label}</c:if>
		                                            	</c:forEach>
		                                            </span>
		                                        </p>

		                                        <p>
		                                            <span class="startline">
		                                            <fmt:formatDate value="${jd.entry_time}" pattern="yyyy.MM.dd"/></span>
		                                            <i class="icon-line">—</i>
		                                            <!-- <span class="deadline">至今</span> -->
		                                             <span class="deadline">
		                                             <fmt:formatDate value="${jd.dismiss_time}" pattern="yyyy.MM.dd"/></span> 
		                                        </p>
		                                        <div class="work-msg-info">
		                                            <p class="clearfix">
		                                                <label class="inter-label">就业方式：</label>
		                                                <span class="bulid-people">
		                                                	<c:forEach items="${jobFromTypeList}" var="jf">
		                                                		<c:if test="${jd.jobfrom_type==jf.value}">${jf.label}</c:if>
		                                                	</c:forEach>
		                                                </span>
		                                                <label class="inter-label">企业BD：</label>
		                                                <span class="bulid-people">${jd.teacher_id}</span>
		                                            </p>
		                                            <p class="clearfix">
		                                                <label class="inter-label">入职登记人：</label>
		                                                <span class="bulid-people">${jd.entry_userid}</span>
		                                                <label class="inter-label">登记时间：</label>
		                                                <span class="bulid-people">
		                                                <fmt:formatDate value="${jd.inputtime_in}" pattern="yyyy-MM-dd"/></span>
		                                            </p>
		                            				<c:if test="${jobing!=jd.occupation_status}">
			                                            <p class="clearfix">
			                                                <label class="inter-label">离职登记人：</label>
			                                                <span class="bulid-people">${jd.dismiss_userid}</span>
			                                                <label class="inter-label">登记时间：</label>
			                                                <span class="bulid-people">
			                                                <fmt:formatDate value="${jd.inputtime_out}" pattern="yyyy-MM-dd"/></span>
			                                            </p>
			                                            <div class="rel-fangtan-con">
			                                                <label class="label-blue-fangtan">离职原因：</label>
			                                                <span class="aim-talk">${jd.dismiss_reason}</span>
			                                            </div>
		                            				</c:if>
		                                        </div>
		                                    </div>
		                                    </dd>
	                                    </c:if>
                                    </c:forEach>
                                
                            </dl>
                            <dl class="more-list-disply">
                                <dt class="tit-dt">历史职业信息</dt>
                                <dd>
                                	<c:forEach items="${jobDetailList}" var="jd" varStatus="sta">
	                                <c:if test="${!empty jd.dismiss_time}">
	                                    <div class="list-record">
	                                        <p>
	                                            <span class="company-name c-blue">${jd.company_name}</span>
	                                            <span class="company-stype">
	                                            	<c:forEach items="${cocompType}" var="ct">
	                                            		<c:if test="${ct.value==jd.company_type}">${ct.label}</c:if>
	                                            	</c:forEach>
	                                            </span>
	                                            <i class="icon-verline">|</i>
	                                            <span class="course-status">
	                                            	<c:forEach items="${cocompScale}" var="cs">
	                                            		<c:if test="${cs.value==jd.company_scale}">${cs.label}人</c:if>
	                                            	</c:forEach>
	                                            </span><span class="work-place">${jd.city_id}</span>
	                                        </p>
											<p>
	                                        	<span class="work-position">${jd.position_name}</span>
	                                            <i class="icon-verline">|</i>
	                                            <span class="work-status"><i>${jd.position_salary}</i>元/月</span>
	                                            <i class="icon-verline">|</i>
	                                            <span class="work-status">
	                                            	<c:forEach items="${inviteType}" var="it">
	                                            		<c:if test="${it.value==jd.position_type}">${it.label}</c:if>
	                                            	</c:forEach>
	                                            </span>
	                                        </p>

	                                        <p>
	                                            <span class="startline"> 
	                                            <fmt:formatDate value="${jd.entry_time}" pattern="yyyy.MM.dd"/></span>
	                                            <i class="icon-line">—</i>
	                                            <span class="deadline">
	                                            <fmt:formatDate value="${jd.dismiss_time}" pattern="yyyy.MM.dd"/></span>
	                                        </p>
	                                        <div class="work-msg-info">
	                                            <p class="clearfix">
	                                                <label class="inter-label">就业方式：</label>
	                                                <span class="bulid-people">
	                                                	<c:forEach items="${jobFromTypeList}" var="jf">
	                                                		<c:if test="${jd.jobfrom_type==jf.value}">${jf.label}</c:if>
	                                                	</c:forEach>
	                                                </span>
	                                                <label class="inter-label">企业BD：</label>
	                                                <span class="bulid-people">${jd.teacher_id}</span>
	                                            </p>
	                                            <p class="clearfix">
	                                                <label class="inter-label">入职登记人：</label>
	                                                <span class="bulid-people">${jd.entry_userid}</span>
	                                                <label class="inter-label">登记时间：</label>
	                                                <span class="bulid-people"><fmt:formatDate value="${jd.inputtime_in}" pattern="yyyy-MM-dd"/></span>
	                                            </p>
	                                            <p class="clearfix">
	                                                <label class="inter-label">离职登记人：</label>
	                                                <span class="bulid-people">${jd.dismiss_userid}</span>
	                                                <label class="inter-label">登记时间：</label>
	                                                <span class="bulid-people"><fmt:formatDate value="${jd.inputtime_out}" pattern="yyyy-MM-dd"/></span>
	                                            </p>
	                                            <div class="rel-fangtan-con">
	                                                <label class="label-blue-fangtan">离职原因：</label>
	                                                <span class="aim-talk">${jd.dismiss_reason}</span>
	                                            </div>
	                                        </div>
	                                    </div>
                                    </c:if>
                                    </c:forEach>
                                </dd>
                            </dl>
	                        <c:if test="${jobDetailListSize>1}">
	                            <div class="center mb20 mt15">
	                                <a class="sl-down-btn" href="javascript:void(0);">查看历史就业信息</a><a class="sl-up-btn" href="javascript:void(0);">收起历史就业信息</a>
	                            </div>
                            </c:if>
                        </div>
                        </c:if>
                        <!--有就业信息结束-->
                    </div>
                </div>
                
                <!-- ================================================================================= -->
                 <div class="con-ggcbox"  name="bmf">
                    <div class="fee-record-list">
                    	<!--报名费汇总开始-->
                        <div class="inter-recode">
                        	<p class="clearfix">
                            	<label class="fee-label">状&#12288;&#12288;态：</label>
                            	<span class="about-fee-info">
                            		<c:forEach items="${isAvaiable}" var="ia">
                            			<c:if test="${bmInfo.is_avaiable==ia.value}">${ia.label}</c:if>
                            		</c:forEach>
                            	</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">最新收费时间：</label>
                            	<span class="about-fee-info">
                            	<c:if test="${empty bmInfo.last_pay_time}">无</c:if>
                            	<c:if test="${!empty bmInfo.last_pay_time}"><fmt:formatDate value="${bmInfo.last_pay_time}" pattern="yyyy-MM-dd"/></c:if>
                            	</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">累计收费金额：</label>
                            	<span class="about-fee-info">
                            	<c:if test="${empty bmInfo.current_pay_money}">0</c:if>
                            	<c:if test="${!empty bmInfo.current_pay_money}">${bmInfo.current_pay_money}</c:if>
                            	元</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">收费方式：</label><span class="about-fee-info">
                            	<c:if test="${empty bmInfo.pay_type}">无</c:if>
                            	<c:if test="${!empty bmInfo.pay_type}">
                            		<c:forEach items="${payTypeList}" var="pt">
                            			<c:if test="${bmInfo.pay_type==pt.value}">${pt.label}</c:if>
                            		</c:forEach>
                            	</c:if>
                            	</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">合同金额：</label>
                            	<span class="about-fee-info">
                            		<c:if test="${empty bmInfo.standard_money}">0</c:if>
                            		<c:if test="${!empty bmInfo.standard_money}">${bmInfo.standard_money}</c:if>
                            	元</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">累计减免金额：</label>
                            	<span class="about-fee-info">
                            		<c:if test="${empty bmInfo.favour_money}">0</c:if>
                            		<c:if test="${!empty bmInfo.favour_money}">${bmInfo.favour_money}</c:if>
                            	元</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">退费金额：</label>
                            	<span class="about-fee-info">
                            		<c:if test="${empty bmInfo.back_money}">0</c:if>
                            		<c:if test="${!empty bmInfo.back_money}">${bmInfo.back_money}</c:if>
                            	元</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">退费时间：</label>
                            	<span class="about-fee-info">
                            	<c:if test="${empty bmInfo.last_back_time}">无</c:if>
                            	<c:if test="${!empty bmInfo.last_back_time}"><fmt:formatDate value="${bmInfo.last_back_time}" pattern="yyyy-MM-dd"/></c:if>
                            	</span>
                            </p>
                        </div>
                        <!--报名费汇总结束-->                        
                        
                        <!--报名费查看更多开始-->
                        <div class="more-list-disply">
                        	<c:forEach items="${bmlsList}" var="bl">
                        		<c:if test="${bl.zt==1}">
		                            <div class="inter-recode">
		                                <p class="clearfix">
		                                    <label class="fee-label">收费时间：</label>
		                                    <span class="about-fee-info">
		                                    <fmt:formatDate value="${bl.create_date}" pattern="yyyy-MM-dd"/></span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">收费金额：</label>
		                                    <span class="about-fee-info">${bl.receivable_money}元</span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">收费方式：</label><span class="about-fee-info">
		                                    	<c:forEach items="${payTypeList}" var="pt">
			                            			<c:if test="${bl.pay_way==pt.value}">${pt.label}</c:if>
			                            		</c:forEach>
		                                    </span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">经办人：</label>
		                                    <span class="about-fee-info">${bl.agency_userid}</span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">记录人：</label>
		                                    <span class="about-fee-info">${bl.oper_userid}</span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">记录日期：</label>
		                                    <span class="about-fee-info">
		                                    <fmt:formatDate value="${bl.inputtime}" pattern="yyyy-MM-dd"/></span>
		                                </p>
		                                <div class="rel-fee-con clearfix">
		                                    <label class="label-blue-fangtan fl">备　　注：</label>
		                                    <span class="aim-talk fl">${bl.remarks}</span>
		                                </div>
		                            </div>
	                            </c:if>
	                            <c:if test="${bl.zt==3}">
		                            <div class="inter-recode">
		                                <p class="clearfix">
		                                    <label class="fee-label">减免发生时间：</label>
		                                    <span class="about-fee-info">
		                                    <fmt:formatDate value="${bl.create_date}" pattern="yyyy-MM-dd"/></span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">减免金额：</label>
		                                    <span class="about-fee-info">${bl.receivable_money}元</span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">减免审批人：</label>
		                                    <span class="about-fee-info">${bl.shengpiid}</span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">记录人：</label>
		                                    <span class="about-fee-info">${bl.oper_userid}</span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">记录日期：</label>
		                                    <span class="about-fee-info">
		                                    <fmt:formatDate value="${bl.inputtime}" pattern="yyyy-MM-dd"/></span>
		                                </p>
		                                <div class="rel-fee-con clearfix">
		                                    <label class="label-blue-fangtan fl">减免说明：</label>
		                                    <span class="aim-talk fl">${bl.remarks}</span>
		                                </div>
		                            </div>
	                            </c:if>
	                            <c:if test="${bl.zt==2}">
		                            <div class="inter-recode">
		                                <p class="clearfix">
		                                    <label class="fee-label">退费时间：</label>
		                                    <span class="about-fee-info">
		                                    <fmt:formatDate value="${bl.create_date}" pattern="yyyy-MM-dd"/></span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">退费金额：</label>
		                                    <span class="about-fee-info">${bl.receivable_money}元</span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">退费审批人：</label>
		                                    <span class="about-fee-info">${bl.shengpiid}</span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">经办人：</label>
		                                    <span class="about-fee-info">${bl.agency_userid}</span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">记录人：</label>
		                                    <span class="about-fee-info">${bl.oper_userid}</span>
		                                </p>
		                                <p class="clearfix">
		                                    <label class="fee-label">记录日期：</label>
		                                    <span class="about-fee-info">
		                                    <fmt:formatDate value="${bl.inputtime}" pattern="yyyy-MM-dd"/></span>
		                                </p>
		                                <div class="rel-fee-con clearfix">
		                                    <label class="label-blue-fangtan fl">减免说明：</label>
		                                    <span class="aim-talk fl">${bl.remarks}</span>
		                                </div>
		                            </div>
	                            </c:if>
                            </c:forEach>
                        </div>
                        <!--报名费查看更多结束-->
                        <c:if test="${bmlsSize>0}">
                        <div class="center mb20">
                        	<a class="sl-down-btn" href="javascript:void(0);">查看更多</a><a class="sl-up-btn" href="javascript:void(0);">收起</a>
                        </div>
                        </c:if>
                    </div>
                </div>
                
                
                
                <!-- ================================================================================= -->
                <div class="con-ggcbox"  name="sxf">
                    <div class="fee-record-list">
                    	<!--实训费一条开始-->
                        <div class="inter-recode">
                        	<p class="clearfix">
                            	<label class="fee-label">状&#12288;&#12288;态：</label><span class="about-fee-info">
                            		<c:forEach items="${stuHaspaid}" var="ia">
                            			<c:if test="${sxInfo.has_paid==ia.value}">${ia.label}</c:if>
                            		</c:forEach>
                            	</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">最新收费时间：</label>
                            	<span class="about-fee-info">
                            	<c:if test="${empty sxInfo.last_pay_time}">无</c:if>
                            	<c:if test="${!empty sxInfo.last_pay_time}"><fmt:formatDate value="${sxInfo.last_pay_time}" pattern="yyyy-MM-dd"/></c:if>
                            	</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">累计收费金额：</label>
                            	<span class="about-fee-info">
                            		<c:if test="${empty sxInfo.current_pay_money}">0</c:if>
                            		<c:if test="${!empty sxInfo.current_pay_money}">${sxInfo.current_pay_money}</c:if>
                            	元</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">收费方式：</label>
                            	<span class="about-fee-info">
                            		<c:if test="${empty sxInfo.pay_type}">无</c:if>
                            		<c:if test="${!empty sxInfo.pay_type}">
                            			<c:forEach items="${payTypeList}" var="pt">
                            				<c:if test="${sxInfo.pay_type==pt.value}">${pt.label}</c:if>
                            			</c:forEach>
                            		</c:if>
                            	</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">合同金额：</label>
                            	<span class="about-fee-info">
                            		<c:if test="${empty sxInfo.standard_money}">0</c:if>
                            		<c:if test="${!empty sxInfo.standard_money}">${sxInfo.standard_money}</c:if>
                            	元</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">累计减免金额：</label>
                            	<span class="about-fee-info">
                            		<c:if test="${empty sxInfo.favour_money}">0</c:if>
                            		<c:if test="${!empty sxInfo.favour_money}">${sxInfo.favour_money}</c:if>
                            	元</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">退费金额：</label>
                            	<span class="about-fee-info">
                            		<c:if test="${empty sxInfo.back_money}">0</c:if>
                            		<c:if test="${!empty sxInfo.back_money}">${sxInfo.back_money}</c:if>
                            	元</span>
                            </p>
                            <p class="clearfix">
                            	<label class="fee-label">退费时间：</label>
                            	<span class="about-fee-info">
                            	<c:if test="${empty sxInfo.last_back_time}">无</c:if>
                            	<c:if test="${!empty sxInfo.last_back_time}"><fmt:formatDate value="${sxInfo.last_back_time}" pattern="yyyy-MM-dd"/></c:if>
                            	</span>
                            </p>
                        </div>
                        <!--实训费一条结束-->                        
                        
                        <!--实训费查看更多开始-->
                        <div class="more-list-disply">
                        	<c:forEach items="${sxlsList}" var="sx">
                        		<c:if test="${sx.zt==1}">
                            <div class="inter-recode">
                                <p class="clearfix">
                                    <label class="fee-label">收费时间：</label>
                                    <span class="about-fee-info">
                                    <fmt:formatDate value="${sx.create_date}" pattern="yyyy-MM-dd"/></span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">收费金额：</label>
                                    <span class="about-fee-info">${sx.receivable_money}元</span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">收费方式：</label>
                                    <span class="about-fee-info">
                                    	<c:forEach items="${payTypeList}" var="pt">
	                            			<c:if test="${sx.pay_way==pt.value}">${pt.label}</c:if>
	                            		</c:forEach>
                                    </span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">经办人：</label>
                                    <span class="about-fee-info">${sx.agency_userid}</span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">记录人：</label>
                                    <span class="about-fee-info">${sx.oper_userid}</span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">记录日期：</label><span class="about-fee-info">
                                    <fmt:formatDate value="${sx.inputtime}" pattern="yyyy-MM-dd"/></span>
                                </p>
                                <div class="rel-fee-con clearfix">
                                    <label class="label-blue-fangtan fl">备　　注：</label>
                                    <span class="aim-talk fl">${sx.remarks}</span>
                                </div>
                            </div>
                            </c:if>
                            <c:if  test="${sx.zt==3}">
                            <div class="inter-recode">
                                <p class="clearfix">
                                    <label class="fee-label">减免发生时间：</label>
                                    <span class="about-fee-info">
                                    <fmt:formatDate value="${sx.create_date}" pattern="yyyy-MM-dd"/></span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">减免金额：</label>
                                    <span class="about-fee-info">${sx.receivable_money}元</span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">减免审批人：</label>
                                    <span class="about-fee-info">${sx.shengpiid}</span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">记录人：</label>
                                    <span class="about-fee-info">${sx.oper_userid}</span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">记录日期：</label>
                                    <span class="about-fee-info"> 
                                    <fmt:formatDate value="${sx.inputtime}" pattern="yyyy-MM-dd"/></span>
                                </p>
                                <div class="rel-fee-con clearfix">
                                    <label class="label-blue-fangtan fl">减免说明：</label>
                                    <span class="aim-talk fl">${sx.remarks}</span>
                                </div>
                            </div>
                            </c:if>
                            <c:if test="${sx.zt==2}">
                            <div class="inter-recode">
                                <p class="clearfix">
                                    <label class="fee-label">退费时间：</label>
                                    <span class="about-fee-info">
                                    <fmt:formatDate value="${sx.create_date}" pattern="yyyy-MM-dd"/></span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">退费金额：</label>
                                    <span class="about-fee-info">${sx.receivable_money}元</span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">退费审批人：</label>
                                    <span class="about-fee-info">${sx.shengpiid}</span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">经办人：</label>
                                    <span class="about-fee-info">${sx.agency_userid}</span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">记录人：</label>
                                    <span class="about-fee-info">${sx.oper_userid}</span>
                                </p>
                                <p class="clearfix">
                                    <label class="fee-label">记录日期：</label>
                                    <span class="about-fee-info">
                                    <fmt:formatDate value="${sx.inputtime}" pattern="yyyy-MM-dd"/></span>
                                </p>
                                <div class="rel-fee-con clearfix">
                                    <label class="label-blue-fangtan fl">退费说明：</label>
                                    <span class="aim-talk fl">${sx.remarks}</span>
                                </div>
                            </div>
                            </c:if>
                            </c:forEach>
                        </div>
                        <!--实训费查看更多结束-->
                        <c:if test="${sxlsSize>0}">
                        <div class="center mb20">
                        	<a class="sl-down-btn" href="javascript:void(0);">查看更多</a><a class="sl-up-btn" href="javascript:void(0);">收起</a>
                        </div>
                        </c:if>
                    </div>
                </div>
                
                
                
                
                <!-- ================================================================================= -->
                <div class="con-ggcbox"  name="zstd">
                    <div class="stu-idcard-info">
                    	<div class="line-item">
                        	<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">区域总监：</i></label>
                            <span class="read-span only-read">
                            	<em name="birth">
                            		<c:if test="${empty stuInfo.teacheridInspector}">
                            			暂无
                            		</c:if>
                            		<c:if test="${!empty stuInfo.teacheridInspector}">
                            			<c:forEach items="${distinctTeacher}" var="dt">
                            				<c:if test="${dt.id==stuInfo.teacheridInspector}">${dt.label}</c:if>
                            			</c:forEach>
                            		</c:if>
                            	</em>
                            </span>
                        </div>
                    	<div class="line-item">
                        	<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">招生经理：</i></label>
                            <span class="read-span only-read">
                            	<em name="birth">
                            		<c:if test="${empty stuInfo.invTeacherId}">
                            			暂无
                            		</c:if>
                            		<c:if test="${!empty stuInfo.invTeacherId}">
                            			<c:forEach items="${distinctTeacher}" var="dt">
                            				<c:if test="${dt.id==stuInfo.invTeacherId}">${dt.label}</c:if>
                            			</c:forEach>
                            		</c:if>
                            	</em>
                            </span>
                        </div>
                    	<div class="line-item">
                        	<label class="left-label"><i class="must-input-icon">*</i><i class="label-text">招生顾问：</i></label>
                            <span class="read-span only-read">
                            	<em name="birth">
                            		<c:if test="${empty stuInfo.teacheridAdvisor}">
                            			暂无
                            		</c:if>
                            		<c:if test="${!empty stuInfo.teacheridAdvisor}">
                            			<c:forEach items="${distinctTeacher}" var="dt">
                            				<c:if test="${dt.id==stuInfo.teacheridAdvisor}">${dt.label}</c:if>
                            			</c:forEach>
                            		</c:if>
                            	</em>
                            </span>
                        </div>
                    </div>
                </div>
                <!-- ============================================================================ -->
                
                <div class="con-ggcbox"  name="ftjl">
                    <div class="stu-interview-info">
                    	<c:if test="${fn:length(vslList)==0}">
	                   	  	<!-- 暂时没有样式开始 -->
	                      	<div class="no-class-info">
	                       		<span>暂时没有访谈记录</span>
	                      	</div>
	                      	<!--   暂时没有样式结束 -->
                      	</c:if>
                    
                    	<c:if test="${fn:length(vslList)!=0}">  
						   <c:forEach items="${vslList}" var="vsl" varStatus="status">
						   	   <c:if test="${status.index<3}">
			                       <!--访谈记录一条开始-->
			                       <div class="inter-recode">
			                       		<p class="clearfix">
			                       			<label class="inter-label">访谈日期：</label>
			                       			<span class="bulid-time">${vsl.visit_time}</span>
			                       		</p>
			                        	<div class="rel-fangtan-con clearfix">
			                                <label class="label-blue-fangtan fl">访谈目的：</label>
			                                <span class="aim-talk fl">${vsl.visit_goal}</span>
			                            </div>
			                        	<div class="rel-fangtan-con clearfix">
			                                <label class="label-blue-fangtan fl">访谈纪要：</label>
			                                <span class="aim-talk fl">${vsl.visit_content}</span>
			                            </div>
			                            <p class="clearfix">
			                            	<label class="inter-label">访谈人：</label><span class="bulid-people">${vsl.real_name}</span>
			                                <label class="inter-label">访谈人所在部门：</label><span class="bulid-people">高校合作部</span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">创建时间：</label>
			                            	<span class="bulid-time">${vsl.createtime}</span>
			                            </p>
			                        </div>
			                        <!--访谈记录一条结束-->
		                        </c:if>
	                        </c:forEach>
                       
                        
	                        <!--访谈记录查看更多开始-->
	                        <div class="more-list-disply">
	                           <c:forEach items="${vslList}" var="vsl" varStatus="status">
						   	   <c:if test="${status.index>=3}">
			                       <!--访谈记录一条开始-->
			                       <div class="inter-recode">
			                       		<p class="clearfix">
			                       			<label class="inter-label">访谈日期：</label>
			                       			<span class="bulid-time">${vsl.visit_time}</span>
			                       		</p>
			                        	<div class="rel-fangtan-con clearfix">
			                                <label class="label-blue-fangtan fl">访谈目的：</label>
			                                <span class="aim-talk fl">${vsl.visit_goal}</span>
			                            </div>
			                        	<div class="rel-fangtan-con clearfix">
			                                <label class="label-blue-fangtan fl">访谈纪要：</label>
			                                <span class="aim-talk fl">${vsl.visit_content}</span>
			                            </div>
			                            <p class="clearfix">
			                            	<label class="inter-label">访谈人：</label><span class="bulid-people">${vsl.real_name}</span>
			                                <label class="inter-label">访谈人所在部门：</label><span class="bulid-people">高校合作部</span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">创建时间：</label>
			                            	<span class="bulid-time">${vsl.createtime}</span>
			                            </p>
			                        </div>
			                        <!--访谈记录一条结束-->
		                        </c:if>
	                        	</c:forEach>
	                        </div>
	                        <!--访谈记录查看更多结束-->
	                        <c:if test="${vslListSize>3}">
		                        <div class="center mb20">
		                        	<a class="sl-down-btn" href="javascript:void(0);">查看更多</a><a class="sl-up-btn" href="javascript:void(0);">收起</a>
		                        </div>
	                        </c:if>
                        </c:if>
                    </div>
                </div>
                <!-- ================================学员状态流水========================================= -->
                
                <div class="con-ggcbox"  name="xyztls">
                    <div class="stu-interview-info">
                      	<c:if test="${fn:length(stuStatusList)==0}">
                   	  		<!-- 暂时没有样式开始 -->
                      		<div class="no-class-info">
                       			<span>暂时没有学员状态流水</span>
                      		</div>
                      		<!--   暂时没有样式结束 -->
                      	</c:if>
                    	
                    	<c:if test="${fn:length(stuStatusList)!=0}">  
	                    	<c:forEach items="${stuStatusList}" var="sl">
	                    		<c:if test="${empty sl.endt_time}">
		                    	<!--学员状态流水一条开始-->
		                        <div class="inter-recode">
		                        	<p class="clearfix">
		                            	<label class="inter-label">当前状态：</label>
		                            	<span class="bulid-people">
		                            		<c:forEach items="${studentStatus}" var="ss">
		                            			<c:if test="${ss.value==sl.newstatus}">${ss.label}</c:if>
		                            		</c:forEach>
		                            	</span>
		                            </p>
		                            
		                            
		                            
		                            
		                            
		                            
		                            <!-- 未分班  -->
		                            <c:if test="${sl.newstatus==NOCLASS }">
			                            <p class="clearfix">
			                            	<label class="inter-label">报名审核通过时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
		                            </c:if>
		                            <!-- 未开课  -->
		                            <c:if test="${sl.newstatus==NOSTARTCLASS }">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
		                            </c:if>
		                            <!-- 在读  -->
		                            <c:if test="${sl.newstatus== BESTUDY}">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
		                            </c:if>
		                            <!-- 结业  -->
		                            <c:if test="${sl.newstatus==EndStudy }">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">已完成课时数量：</label><span class="bulid-people">${sl.finish_count}课时</span>
			                            </p>
		                            </c:if>
		                            <!-- 求职中  -->
		                            <c:if test="${sl.newstatus==FindJobing }">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
		                            </c:if>
		                            <!-- 已就业  -->
		                            <c:if test="${sl.newstatus==WORKED }">
			                            <p class="clearfix">
			                            	<label class="inter-label">首次入职时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
		                            </c:if>
		                            <!-- 退学 -->
		                            <c:if test="${sl.newstatus==LEAVESCHOLL }">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">已完成课时数量：</label><span class="bulid-people">${sl.finish_count}课时</span>
			                            </p>
		                            </c:if>
		                            <!-- 开除  -->
		                            <c:if test="${sl.newstatus==EXPEL }">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">已完成课时数量：</label><span class="bulid-people">${sl.finish_count}课时</span>
			                            </p>
		                            </c:if>
		                            <!-- 劝退  -->
		                            <c:if test="${sl.newstatus==QUANTUI }">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">已完成课时数量：</label><span class="bulid-people">${sl.finish_count}课时</span>
			                            </p>
		                            </c:if>
		                            <!-- 休学  -->
		                            <c:if test="${sl.newstatus==XIUXUE }">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">已完成课时数量：</label><span class="bulid-people">${sl.finish_count}课时</span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">拟休学重返时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.backtime}" pattern="yyyy-MM-dd"/></span>
			                            </p>
		                            </c:if>
		                            <!-- 休学重返  -->
		                            <c:if test="${sl.newstatus==XIUXUEBack }">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
		                            </c:if>
		                            <!-- 延期结业  -->
		                            <c:if test="${sl.newstatus==DELAYGRADUATE }">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">拟结业时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.backtime}" pattern="yyyy-MM-dd"/></span>
			                            </p>
		                            </c:if>
		                            <!-- 延期就业  -->
		                            <c:if test="${sl.newstatus==DELAYWORK }">
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">状态发生期间：</label>
			                            	<span class="bulid-people">
			                            		<c:forEach items="${studentStatus}" var="ss">
		                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
		                                    	</c:forEach>
			                            	</span>
			                            </p>
			                            <p class="clearfix">
			                            	<label class="inter-label">拟就业时间：</label>
			                            	<span class="bulid-people"><fmt:formatDate value="${sl.backtime}" pattern="yyyy-MM-dd"/></span>
			                            </p>
		                            </c:if>
		                            
		                            
		                            
		                            
		                            
		                            
		                            
		                            
		                        	<div class="rel-fangtan-con clearfix">
		                                <label class="label-blue-fangtan fl">备&#12288;&#12288;注：</label>
		                                <span class="aim-talk fl">${sl.remark}</span>
		                            </div>
		                        </div>
		                        <!--学员状态流水一条结束-->
		                        </c:if>
	                        </c:forEach>
                        
	                        <!--访谈记录查看更多开始-->
	                        <div class="more-list-disply">
		                        <c:forEach items="${stuStatusList}" var="sl">
		                    		<c:if test="${!empty sl.endt_time}">
		                            <div class="inter-recode">
		                                <p class="clearfix">
		                                    <label class="inter-label">状态：</label>
		                                    <span class="bulid-people">
		                                    	<c:forEach items="${studentStatus}" var="ss">
			                            			<c:if test="${ss.value==sl.newstatus}">${ss.label}</c:if>
			                            		</c:forEach>
		                                    </span>
		                                </p>
		                                
		                                
		                                
		                                <!-- 未分班  -->
			                            <c:if test="${sl.newstatus==NOCLASS }">
				                            <p class="clearfix">
				                            	<label class="inter-label">报名审核通过时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
			                            </c:if>
			                            <!-- 未开课  -->
			                            <c:if test="${sl.newstatus==NOSTARTCLASS }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
			                            </c:if>
			                            <!-- 在读  -->
			                            <c:if test="${sl.newstatus== BESTUDY}">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
			                            </c:if>
			                            <!-- 结业  -->
			                            <c:if test="${sl.newstatus==EndStudy }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">已完成课时数量：</label><span class="bulid-people">${sl.finish_count}课时</span>
				                            </p>
			                            </c:if>
			                            <!-- 求职中  -->
			                            <c:if test="${sl.newstatus==FindJobing }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
			                            </c:if>
			                            <!-- 已就业  -->
			                            <c:if test="${sl.newstatus==WORKED }">
				                            <p class="clearfix">
				                            	<label class="inter-label">首次入职时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
			                            </c:if>
			                            <!-- 退学 -->
			                            <c:if test="${sl.newstatus==LEAVESCHOLL }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">已完成课时数量：</label><span class="bulid-people">${sl.finish_count}课时</span>
				                            </p>
			                            </c:if>
			                            <!-- 开除  -->
			                            <c:if test="${sl.newstatus==EXPEL }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">已完成课时数量：</label><span class="bulid-people">${sl.finish_count}课时</span>
				                            </p>
			                            </c:if>
			                            <!-- 劝退  -->
			                            <c:if test="${sl.newstatus==QUANTUI }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">已完成课时数量：</label><span class="bulid-people">${sl.finish_count}课时</span>
				                            </p>
			                            </c:if>
			                            <!-- 休学  -->
			                            <c:if test="${sl.newstatus==XIUXUE }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">已完成课时数量：</label><span class="bulid-people">${sl.finish_count}课时</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">拟休学重返时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.backtime}" pattern="yyyy-MM-dd"/></span>
				                            </p>
			                            </c:if>
			                            <!-- 休学重返  -->
			                            <c:if test="${sl.newstatus==XIUXUEBack }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
			                            </c:if>
			                            <!-- 延期结业  -->
			                            <c:if test="${sl.newstatus==DELAYGRADUATE }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">拟结业时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.backtime}" pattern="yyyy-MM-dd"/></span>
				                            </p>
			                            </c:if>
			                            <!-- 延期就业  -->
			                            <c:if test="${sl.newstatus==DELAYWORK }">
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.begin_time}" pattern="yyyy-MM-dd"/></span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">状态发生期间：</label>
				                            	<span class="bulid-people">
				                            		<c:forEach items="${studentStatus}" var="ss">
			                                    		<c:if test="${sl.oldstatus==ss.value}">${ss.label}</c:if>
			                                    	</c:forEach>
				                            	</span>
				                            </p>
				                            <p class="clearfix">
				                            	<label class="inter-label">拟就业时间：</label>
				                            	<span class="bulid-people"><fmt:formatDate value="${sl.backtime}" pattern="yyyy-MM-dd"/></span>
				                            </p>
			                            </c:if>
		                                
		                                
		                                
		                                
		                                <div class="rel-fangtan-con clearfix">
		                                    <label class="label-blue-fangtan fl">备&#12288;&#12288;注：</label>
		                                    <span class="aim-talk fl">${sl.remark}</span>
		                                </div>
		                            </div>
		                            </c:if>
		                        </c:forEach>
	                        </div>
                        
	                        <!--访谈记录查看更多结束-->
	                        <div class="center mb20">
	                        	<a class="sl-down-btn" href="javascript:void(0);">查看更多</a><a class="sl-up-btn" href="javascript:void(0);">收起</a>
	                        </div>
                    	</c:if>
                    </div>
                </div>
                <!-- ========================================================================= -->
            </div>
        </div>
        <!--切换内部部分结束-->
    </div>
</div>

<!--学员详情弹框结束-->
<script type="text/javascript"> 
$(function(){
	var qx="${qx}";
	var _UserId="${user.userId}";
	
	//权限判断是否可以编辑。
	<c:choose>
	  <c:when test="${lq:ifIn(myFunction,'81')}">
		DialogStuInfo.init(qx,_UserId,true);
	  </c:when>
	  
	  <c:when test="${lq:ifIn(myFunction,'83')}">
		DialogStuInfo.init(qx,_UserId,true);
	  </c:when>
	  <c:when test="${lq:ifIn(myFunction,'84')}">
		DialogStuInfo.init(qx,_UserId,true);
	  </c:when>
	  <c:when test="${lq:ifIn(myFunction,'85')}">
		DialogStuInfo.init(qx,_UserId,true);
	  </c:when>
	  
	  <c:otherwise>
		DialogStuInfo.init(qx,_UserId,false);
	  </c:otherwise>
	</c:choose>
	
 
	
});
   
 

</script>

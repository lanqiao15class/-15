<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<input type="hidden" id="resourcePath" value="${ctxResource}">
	
<!--右侧内容部分开始-->
<div class="student-info-part">
	<div class="inner-reletive">
    	<!--右侧标题部分开始-->
		<div class="tit-h1-box">
            <h1 class="tit-first">
                <span>个人资料</span><i class="gt-icon">&gt;</i><span class="curr">基本信息</span>
            </h1>
        </div>
		<!--右侧标题部分结束-->
        <!--右侧内容部分背景白色开始-->
        <div class="inner-white">
        	<!-- 这里显示学员提示，去报名等等 -->
            <div id="studentTips"></div>
            
            <div class="student-info-form">
            
            
            	<div class="inner-stuInfo">
                    <div class="stu-info-inner">
        
            						
			<div style="padding-left:155px;">
            <table border=0 width="100%">
              <tr height=300>
                <td valign="top" width="10%"  style="padding-top:30px">我的二维码：</td>
                <td  width="30%"><img src="${ctxBase }/stu/showcode2.do" /></td>
               
                 <td width=60%><a href="${ctxBase }/stu/showcode2.do?down=1" style="color:blue">下载到本地</a>&nbsp;&nbsp;&nbsp;&nbsp;(或手机拍照保存)<br/>
                		 注意：此二维码将作为您参加面试签到的重要凭证，请妥善保存！
               	</td>
              </tr>
            </table>
            
            </div>
                    
                    <form class="clearfix">
                    	<c:choose>
						   <c:when test="${stuInfo.auditStatus==PASS}"><!-- 审核通过 -->  
								<div class="line-item">
		
								
								
		                            <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">姓名：</i></label>
		                            <span class="read-span only-read">
		                            	<em name="realName">
	                                		<c:if test="${empty user.realName}">
	                                			点击填写
	                                		</c:if>
	                                		<c:if test="${!empty user.realName}">
	                                			${user.realName }
	                                		</c:if>
	                                	</em>
		                            </span>
		                        </div>
						   </c:when>
						   <c:otherwise> <!-- 审核不通过 -->
								<div class="line-item">
		                            <div class="clearfix">
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
		                                <span class="eidt-span">
		                                <input id="realName" disableautocomplete autocomplete="off" type="text"></span>
		                            </div>
		                            <span class="error-tips"></span>
		                        </div>
						   </c:otherwise>
						</c:choose>
                        <div class="line-item">
                        
               			 
                        	<div class="clearfix">
                       
               			 
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
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                    <span class="sex-check">
                                    <input type="radio" name="sex" value="0" <c:if test='${user.sex==0}'>checked</c:if>> 男</span>
                                    <span class="sex-check">
                                    <input type="radio" name="sex" value="1" <c:if test='${user.sex==1}'>checked</c:if>> 女</span>
                                </span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                        	<div class="clearfix">
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
                                	<b href="javascript:void(0);" class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                    <c:if test="${empty user.nation}">
                                		<jsp:include page="/WEB-INF/view/include/stuInfoBasic_nation_select_search.jsp">
											<jsp:param name="inputid" value="nation_selectBasic" />
										</jsp:include>
                                	</c:if>
                                	<c:if test="${!empty user.nation}">
                                		<jsp:include page="/WEB-INF/view/include/stuInfoBasic_nation_select_search.jsp">
											<jsp:param name="inputid" value="nation_selectBasic" />
											<jsp:param name="selectid" value="${user.nation}" />
											<jsp:param name="selecttext" value="${user.nation}" />
										</jsp:include>
                                	</c:if>
                                </span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        
                       <c:choose>
						   <c:when test="${stuInfo.auditStatus==PASS}"><!-- 审核通过 -->  
								<div class="line-item">
		                            <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">身份证号：</i></label>
		                            <span class="read-span only-read">
		                            	<em name="idCard">
	                                		<c:if test="${empty user.idCard}">
	                                			点击填写
	                                		</c:if>
	                                		<c:if test="${!empty user.idCard}">
	                                			${user.idCard}
	                                		</c:if>
	                                	</em>
		                            </span>
		                        </div>
						   </c:when>
						   <c:otherwise> <!-- 审核不通过 -->
		                        <div class="line-item">
		                            <div class="clearfix">
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
		                            </div>
		                            <span class="error-tips"></span>
		                        </div>
                          </c:otherwise>
						</c:choose>
                        
                        <div class="line-item">
                            <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">出生年月：</i></label>
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
                            <div class="clearfix">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">所在院校：</i></label>
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
                                		<jsp:include page="/WEB-INF/view/include/stuInfoBasic_school_select_likesearch.jsp">
											<jsp:param name="inputid" value="basic_select_school_name" />
										</jsp:include>
                                	</c:if>
                                	<c:if test="${!empty stuInfo.univCode}">
                                		<jsp:include page="/WEB-INF/view/include/stuInfoBasic_school_select_likesearch.jsp">
											<jsp:param name="inputid" value="basic_select_school_name" />
											<jsp:param name="selectid" value="${stuInfo.univCode}" />
											<jsp:param name="selecttext" value="${univName}" />
										</jsp:include>
                                	</c:if>
                                </span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
							<div class="clearfix">
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
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                            <div class="clearfix">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">院校所在省：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="prov">
                                		<c:if test="${empty schoolProvName}">点击填写</c:if>
                                		<c:if test="${!empty schoolProvName}">${schoolProvName}</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                	<select id="province" name="provCode" onchange="tm_change_province(this)" >
                                		<!-- 省 -->
                                    </select>
                                    <input type="hidden"  id="provCodeTemp" value="${stuInfo.schoolProvCode}"/>
                                </span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                            <div class="clearfix">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">院校所在市：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="city">
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
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                            <div class="clearfix">
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
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                            <div class="clearfix">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">在校担任职务：</i></label>
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
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                            <div class="clearfix">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">所在专业：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="major">
                                		<c:if test="${empty stuInfo.major }">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty stuInfo.major }">
                                			${stuInfo.major}
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span"><input id="major" disableautocomplete autocomplete="off" type="text"></span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                            <div class="clearfix">
                                <label class="left-label"><i class="must-input-icon">*</i><i class="label-text">所在学院：</i></label>
                                <a href="javascript:void(0);" class="read-span able-edit">
                                	<em name="schoolSubname">
                                		<c:if test="${empty  stuInfo.schoolSubname}">
                                			点击填写
                                		</c:if>
                                		<c:if test="${!empty  stuInfo.schoolSubname}">
                                			${stuInfo.schoolSubname}
                                		</c:if>
                                	</em>
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span"><input id="schoolSubname" disableautocomplete autocomplete="off" type="text"></span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                            <div class="clearfix">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">宿舍楼号：</i></label>
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
                            </div>
                            <span class="error-tips"></span>
                        </div>                            
                        <div class="line-item">
                            <div class="clearfix">
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
                                	<b class="icon-edit"><i class="Hui-iconfont">&#xe6df;</i></b>
                                </a>
                                <span class="eidt-span">
                                	<c:forEach items="${jobCityCode}" var="jcc">
						       			<span class="addr-check">
						        			<input type="checkbox" title="${jcc.label}" name="jobCityCode" value="${jcc.value}" <c:if test="${fn:contains(stuInfo.jobCityCode, jcc.value)}">
											checked</c:if> >${jcc.label}
						        		</span>
						       		</c:forEach>
                                </span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                        	<div class="clearfix">
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
                                       		<c:if test="${ct.del_flag==0}">
                                       		<option value="${ct.value}" <c:if test='${ct.value==stuInfo.courseType}'>selected</c:if>>${ct.label}</option>
                                      </c:if>
                                       </c:forEach>
                                    </select>
                                </span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                            <div class="clearfix">
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
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                            <div class="clearfix">
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
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                        	<div class="clearfix">
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
                            <span class="error-tips"></span>
                        </div>
                        <div class="idcard-guige mb10 fl c-green">身份证正反面图片的规格不大于3M</div>
                        <div class="line-item">
                            <div class="clearfix">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">身份证正面：</i>
                                 <br/>
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
                                        <input id="frontImgName4" type="hidden" name="frontImgName4">
                                        <input class="upload-input-file" type="file" id="frontImgFile4" name="imgFile">
                                        <div class="upload-bgimg">
                                            <p class="upload-icon"><i class="Hui-iconfont">&#xe642;</i></p>
                                            <p class="upload-word">上传文件</p>
                                        </div>
                                    </div>
                                </span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                        <div class="line-item">
                            <div class="clearfix">
                                <label class="left-label"><i class="must-input-icon"></i><i class="label-text">身份证反面：</i>
                                
                                  <br/>
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
                                        <input id="backImgName4" type="hidden" name="backImgName4">
                                        <input class="upload-input-file"  type="file" id="backImgFile4" name="imgFile">
                                        <div class="upload-bgimg">
                                            <p class="upload-icon"><i class="Hui-iconfont">&#xe642;</i></p>
                                            <p class="upload-word">上传文件</p>
                                        </div>
                                    </div>
                                </span>
                            </div>
                            <span class="error-tips"></span>
                        </div>
                    </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--右侧内容部分结束-->
<div style="height:0; overflow:hidden;" id="dialogcontant"></div>
<script> 
$(function(){
	//表头提示
	STUDENTTIPS.init();
	
	//=====================上传图片监听====================
	$(document).on('change','#frontImgFile4',function(){
		basicSubmitImg("A");
	});
	$(document).on('change','#backImgFile4',function(){
		basicSubmitImg("B");
	});
	//=================================================
	var provCodeTemp=$("#provCodeTemp").val();
    var cityCodeTemp=$("#cityCodeTemp").val();
	//1.异步加载省
    $.ajax({
         type:"post",
         url:basePath+"/student/getProvList.do",
         async:false,
         data:{},
         dataType:"json",
         success:function(data){
         	 var data=data.provList;
            	 var html="<option value=''> --请选择省份-- </option>";
              for(var i=0;i<data.length;i++){
                if(provCodeTemp==data[i].cityId){
                     html+="<option value='"+data[i].cityId+"' selected='selected' >"+data[i].city+"</option>";
                }else{
                     html+="<option value='"+data[i].cityId+"' >"+data[i].city+"</option>"; 
                }
              }
              $("#province").html(html);
              $("#city").html("<option value=''> --请选择城市-- </option>");
              if(cityCodeTemp!=null&&cityCodeTemp!=''){
                $("#province").trigger("change");//触发城市下拉框加载
              }
          } 
     });  
	//=================================================
	//点击编辑icon
	$(".able-edit").click(function(e){
		e.stopPropagation();
		var _this=$(this).parents(".line-item");
		$(".eidt-span").hide();
		$(".read-span").show();
		_this.find(".read-span").hide();
		_this.find(".eidt-span").show();
		if(_this.find(".eidt-span").children("input").length>0){
			_this.find(".eidt-span").children("input").focus();
		}
		//1.编辑后赋值到文本框zzh
		if($.trim($(this).children("em").html())=="点击填写"||$.trim($(this).children("em").html())=="暂无"){
			$(this).next("span").children("input").val("");//为空					
		}else{
			$(this).next("span").children("input").val($.trim($(this).children("em").html()));//赋值
		}
	});
	
	
	$(".eidt-span").click(function(e){
		e.stopPropagation();
	});
	
	
	//========文本框-失去焦点提交()============
	$(".eidt-span input[type='text']").blur(function(e){
		e.stopPropagation();
		var _this=$(this).parents(".line-item");
		//1.非空验证zzh
		if((_this.find(".label-text").html().replace("：","")=="姓名"&&($(this).val()==null||$(this).val()==""))||
				   (_this.find(".label-text").html().replace("：","")=="民族"&&($(this).val()==null||$(this).val()==""))||
				   (_this.find(".label-text").html().replace("：","")=="身份证号"&&($(this).val()==null||$(this).val()==""))||	
				   (_this.find(".label-text").html().replace("：","")=="所在院校"&&($(this).val()==null||$(this).val()==""))||	
				   (_this.find(".label-text").html().replace("：","")=="所在年级"&&($(this).val()==null||$(this).val()==""))||
				   (_this.find(".label-text").html().replace("：","")=="在校担任职务"&&($(this).val()==null))||
				   (_this.find(".label-text").html().replace("：","")=="所在专业"&&($(this).val()==null||$(this).val()==""))||
				   (_this.find(".label-text").html().replace("：","")=="宿舍楼号"&&($(this).val()==null))||
				   (_this.find(".label-text").html().replace("：","")=="民族")||
				   (_this.find(".label-text").html().replace("：","")=="所在院校")
		){//为空
			$(".eidt-span").hide();
			$(".read-span").show();
			_this.find(".read-span").hide();
			_this.find(".eidt-span").show();
			if(_this.find(".eidt-span").children("input")){
				_this.find(".eidt-span").children("input").focus();
			}
			//非空验证zzh
			if(_this.find(".label-text").html().replace("：","")=="民族"){
				_this.find(".error-tips").html("");//民族不进行提示
			}else if(_this.find(".label-text").html().replace("：","")=="所在院校"){
				_this.find(".error-tips").html("");//院校名称不进行提示
			}else if(_this.find(".label-text").html().replace("：","")=="在校担任职务"){
				_this.find(".error-tips").html("");//不进行提示
				_this=$(this).parents(".line-item");
				_this.find(".read-span").show();
				_this.find(".eidt-span").hide();
			}else if(_this.find(".label-text").html().replace("：","")=="宿舍楼号"){
				_this.find(".error-tips").html("");//不进行提示
				_this=$(this).parents(".line-item");
				_this.find(".read-span").show();
				_this.find(".eidt-span").hide();
			}else{
				_this.find(".error-tips").html("请输入"+_this.find(".label-text").html().replace("：",""));
			}
		}else{//不为空
			_this=$(this).parents(".line-item");
			_this.find(".read-span").show();
			_this.find(".eidt-span").hide();
			
			//1.清空提示
			_this.find(".error-tips").html("");
			//2.定义传递的参数
			var parameterName;
			var parameterValue;
			//3.获取当前文本框的id名称
			var textId=_this.find(".eidt-span").children("input").attr("id");
			//4.验证是否相等,相等则不执行修改
			if($.trim($("#"+textId).val())==$.trim($("em[name="+textId+"]").html())){
				return;
			}
			//4.1验证
			var textCheck=$("#"+textId).val();
			if(textId=="realName"){//姓名
				if(isContainsSpecialChar(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("学员姓名包含非法字符");
					return;
				}
			}else if(textId=="idCard"){//身份证
				if(!isIdCardNo(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("身份证号码格式不正确");
					return;
				}
			}else if(textId=="grade"){//年级
				if(isContainsSpecialChar(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("年级包含非法字符");
					return;
				}
				var patrn=/^[0-9]+$/;
				if(!patrn.test(textCheck)){ 
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("请填写正确的年份数字，如2012");
					return;
				}
			}else if(textId=="schoolDuty"){//在校担任职务
				if(isContainsSpecialChar(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("职务包含非法字符");
					return;
				}
			}else if(textId=="major"){//所在专业
				if(isContainsSpecialChar(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("专业包含非法字符");
					return;
				}
			}else if(textId=="schoolDormitory"){//宿舍楼号
				if(isContainsSpecialChar(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("宿舍楼号包含非法字符");
					return;
				}
			}else if(textId=="schoolSubname"){//所在学院
				if(isContainsSpecialChar(textCheck)){
					_this.find(".read-span").hide();
					_this.find(".eidt-span").show();
					_this.find(".eidt-span").children("input").focus();
					_this.find(".error-tips").html("所在学院包含非法字符");
					return;
				}
			}
			//5赋值
			parameterName=textId;
			parameterValue=$("#"+textId).val();
			//6.组装
			var data=parameterName+"="+parameterValue;
			//7.异步并回显
			 $.ajax({
		    	  type:"post",
		             url:basePath+"/student/editStuBasic.do",  
		             data:data,
		             dataType:"json",
		             success:function(data){
		         		if(data.code == 200){
		         			//8
		         			var originHtml = $.trim($("em[name=schoolDuty]").html());//在校担任职务
		         			var schoolDorHtml=$.trim($("em[name=schoolDormitory]").html());//宿舍楼号
		         			//9.
		         			$("em[name="+textId+"]").html(parameterValue);
		         			//10.根据身份证设置出生年月
		         			if(textId=="idCard"){
		         				var birth=$("#"+textId).val().substring(6, 14);
		         				birth=birth.substring(0, 4) + "-" + birth.substring(4, 6) + "-" + birth.substring(6);
		         				$("em[name=birth]").html(birth);
		         			}
		         			//11在校担任职务
		         			if(textId=="schoolDuty"){
			         			if($("#schoolDuty").val()=="" && originHtml =="点击填写"){
			         				$("em[name=schoolDuty]").html("点击填写");
			         				return;
			         			}else if($("#schoolDuty").val()==""&& originHtml !="点击填写"){
			         				$("em[name=schoolDuty]").html("点击填写");
			         			}
		         			}
		         			//12
		         			if(textId=="schoolDormitory"){
			         			if($("#schoolDormitory").val()=="" && schoolDorHtml =="点击填写"){
			         				$("em[name=schoolDormitory]").html("点击填写");
			         				return;
			         			}else if($("#schoolDormitory").val()==""&& schoolDorHtml !="点击填写"){
			         				$("em[name=schoolDormitory]").html("点击填写");
			         			}
		         			}
		         			//13
		         			gl_syncUserInfo();//同步更新左侧菜单
	         				layer.msg("修改成功");
		         		}else{
		          			layer.msg("修改失败");
		          		}
		             }
		    });
		}
	});
	
	//选择空白处提交
	$("body").click(function(e){
		$(".read-span").show();
		$(".eidt-span").hide();
	});
	
	//==========================单选框-失去焦点提交(性别)======================================
	$(":radio[name=sex]").click(function(){
			//1.定义传递的参数
			var parameterName;
		var parameterValue;
		//2.判断
		if($("#sexTemp").val()!=$(this).val()){
			parameterName="sex";
			parameterValue=$(this).val();
		}else{
			return;
		}
		//3.组装
			var data=parameterName+"="+parameterValue;
			//4.提交
			$.ajax({
	    	  type:"post",
	             url:basePath+"/student/editStuBasic.do",  
	             data:data,
	             dataType:"json",
	             success:function(data){
	         		if(data.code == 200){
	         			if(parameterValue==0){
		         			$("em[name=sex]").html("男");
	         			}else{
	         				$("em[name=sex]").html("女");
	         			}
	         			layer.msg("修改成功");
	         		}else{
	          			layer.msg("修改失败");
	          		}
	             }
	    });
	});
	//===============================下拉框修改监听(院校类别)==========================================
	$('#schoolTypeCode').change(function(){
		//1.定义传递的参数
			var parameterName;
		var parameterValue;
		//2.获取选中的
		var schoolTypeCode=$(this).children('option:selected').val();
		var schoolTypeName=$(this).children('option:selected').text();
		//3.判断
		if(schoolTypeName==$("em[name=schoolTypeCode]").html()||schoolTypeCode==""){
			return;
		}else{
			parameterName="schoolTypeCode";
			parameterValue=$(this).children('option:selected').val();
		}
		//4.组装
			var data=parameterName+"="+parameterValue;
		//5.提交
			$.ajax({
	    	  type:"post",
	             url:basePath+"/student/editStuBasic.do",  
	             data:data,
	             dataType:"json",
	             success:function(data){
	         		if(data.code == 200){
		         		$("em[name=schoolTypeCode]").html(schoolTypeName);
	         			layer.msg("修改成功");
	         		}else{
	          			layer.msg("修改失败");
	          		}
	             }
	    });
	});
	//==============================复选框监听(就业意向)===========================================
	$("input[name='jobCityCode']").click(function(e){ 
		var jobCitys = [];//处理意向就业地点
		var jobCityNames=[];
		$("input[name='jobCityCode']").each(function(index,element){
			if($(element).is(':checked')){
				jobCitys.push(element.value);
			    jobCityNames.push(element.title);
			}
		});
		var data="jobCityCode="+jobCitys.join(",");
		//提交
			$.ajax({
	    	  type:"post",
	             url:basePath+"/student/editStuBasic.do",  
	             data:data,
	             dataType:"json",
	             success:function(data){
	         		if(data.code == 200){
		         		$("#jobCityCode").html(jobCityNames.join(","));
	         			layer.msg("修改成功");
	         		}else{
	          			layer.msg("修改失败");
	          		}
	             }
	    });
	});
	//===============================下拉框修改监听(课程类别)==========================================
	$('#courseTypeCode').change(function(){
		//1.定义传递的参数
			var parameterName;
		var parameterValue;
		//2.获取选中的
		var courseTypeCode=$(this).children('option:selected').val();
		var courseTypeName=$(this).children('option:selected').text();
		//3.判断
		if(courseTypeName==$("em[name=courseTypeCode]").html()||courseTypeCode==""){
			return;
		}else{
			parameterName="courseTypeCode";
			parameterValue=$(this).children('option:selected').val();
		}
		//4.组装
			var data=parameterName+"="+parameterValue;
		//5.提交
			$.ajax({
	    	  type:"post",
	             url:basePath+"/student/editStuBasic.do",  
	             data:data,
	             dataType:"json",
	             success:function(data){
	         		if(data.code == 200){
		         		$("em[name=courseTypeCode]").html(courseTypeName);
	         			layer.msg("修改成功");
	         		}else{
	          			layer.msg("修改失败");
	          		}
	             }
	    });
	});
	
}); 

/** 判断是否包含中英文特殊字符，除英文"-_"字符外  */
function isContainsSpecialChar(value){
	   var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\‘)(\；)(\：)(\”)(\“)(\’)(\。)(\，)(\、)(\？)]+/);   
    if(reg.test(value)){
 	   return true;
    }else{
 	   return false;
    }
}

//======================身份证号=================================
var aCity = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	};
   /** 身份证号码的验证规则 */
function isIdCardNo(sId){ 
	var iSum = 0;
	var info = "";
	if (!/^\d{17}(\d|x|X)$/i.test(sId)){
		//alert("你输入的身份证长度或格式错误");
		return false;
	}
	sId = sId.replace(/x$/i, "a");
	if (aCity[parseInt(sId.substr(0, 2))] == null){
		//alert("你的身份证地区非法");
		return false;
	}
	sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-"+ Number(sId.substr(12, 2));
	var d = new Date(sBirthday.replace(/-/g, "/"));
	if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())){
		//alert("身份证上的出生日期非法");
		return false;
	}
	for (var i = 17; i >= 0; i--){
		iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
	}
	if (iSum % 11 != 1){
		//alert("你输入的身份证号非法");
		return false;
	}
	//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女");//此次还可以判断出输入的身份证号的人性别
	return true;
}
/**======================================图片上传开始===========================================**/
function basicSubmitImg(type){
	var fileElementId = null,returnShow = null,imgName = null;
	if(type === 'A'){
//		alert("正面");
		fileElementId = 'frontImgFile4';returnShow = 'returnShowFront';imgName = 'frontImgName4';
	} 
	if(type === 'B'){
//		alert("反面");
		fileElementId = 'backImgFile4'; returnShow = 'returnShowBack';imgName = 'backImgName4';
	}
		
	$.ajaxFileUpload({
		valid_extensions : ['jpg','png','jpeg','bmp'],
		url : basePath+"/student/uploadImgAndSave.do", //上传文件的服务端
		secureuri : false, //是否启用安全提交
		dataType : 'json', //数据类型 
		data:{
			"userId":"${loginUserId}",
			"type":type
		},
		fileElementId : fileElementId, //表示文件域ID
		//提交成功后处理函数      data为返回值，status为执行的状态
		success : function(data, status) {
	    switch(data.code) {
        	case 201:
        		layer.msg("请选择文件");
        		break;
        	case 202:
        		layer.msg("图片太大，请上传小于的3M的图片");
        		break;
        	case 203:
        		layer.msg("图片上传出现异常");
        		break;
        	case 204:
        		layer.msg("请选择bmp,jpg,jpeg,png格式的图片");
        	default:
        		var resourcePath = $("#resourcePath").val();
        		$("#"+returnShow).attr("src",resourcePath + data.fileName);
        		$("#"+imgName).val(data.fileName);
        		break;
	        }
			
		},
		//提交失败处理函数
		error : function(html, status, e) {
			alert("错误:"+status);
		}
	});
};
/**======================================图片上传結束===========================================**/
	/** 省改变触发  */
   function tm_change_province(obj){
	   schoolProvName=$(obj).find("option:selected").html();//获取文本
	   $("em[name=prov]").html(schoolProvName);
	  
       var cityCodeTemp=$("#cityCodeTemp").val();
       var  objid=obj.value;
       schoolProvCode=obj.value;//保存时用
       if(!objid){//如果省为空
           $("#city").html("<option value=''> --请选择城市-- </option>");//清空市
       }
       $.ajax({
           type:"post",
           url:basePath+"/student/findCitys.do",
           data:{"id":objid},
           dataType:"json",
           success:function(data){
        	  var data=data.cityList;
              var html="<option value=''> --请选择城市-- </option>";
              for(var i=0;i<data.length;i++){
                if(cityCodeTemp==data[i].cityId){
                    html+="<option value='"+data[i].cityId+"' selected='selected' >"+data[i].city+"</option>";
                }else{
                    html+="<option value='"+data[i].cityId+"'>"+data[i].city+"</option>"; 
                }
              }
              $("#city").html(html);
           } 
        });
    }
   //保存院校所在区域
   var schoolProvCode="${stuInfo.schoolProvCode}";//省默认id
   var schoolProvName="${schoolProvName}";//省默认名称
   var schoolCityName="${schoolCityName}";//市默认名称
   function editProvAndCity(obj){
	   schoolCityName=$(obj).find("option:selected").html();//获取文本
	   if(obj.value!=''){
		   var data="schoolProvCode="+schoolProvCode+"&schoolCityCode="+obj.value;
		   //提交
		   $.ajax({
	    	  type:"post",
	             url:basePath+"/student/editStuBasic.do",  
	             data:data,
	             dataType:"json",
	             success:function(data){
	         		if(data.code == 200){
	         			$("em[name=city]").html(schoolCityName);
	         			layer.msg("修改成功");
	         		}else{
	          			layer.msg("修改失败");
	          		}
	             }
		    });
	   }else{
		   
	   }
   }
</script>

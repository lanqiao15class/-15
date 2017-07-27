<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<input type="hidden" id="resourcePath" value="${ctxResource}">
<!-- type 区分 :teacherAdd || studentAdd 老师添加信息/学生自己填写 -->
<input type="hidden" id="type"  value="${type }">
<!--学生报名页面开始-->
<div class="stu-registration clearfix">
    <form class="clearfix" id="addYxStudentInfo">
    <div class="form-scroll">
        <div class="form-scroll-auto">
            <div class="form-inner-con">
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon teacherAdd">*</i><i class="label-text">学员姓名：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text"  name="realName">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">性别：</i></label>
                        <span class="sex-check"><input type="radio" name="sex" value="0" checked> 男</span>
                        <span class="sex-check"><input type="radio" name="sex" value="1" checked> 女</span>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon teacherAdd">*</i><i class="label-text">电话：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="loginTel" id="loginTel">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text ">QQ：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="qq">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon teacherAdd">*</i><i class="label-text">电子邮箱：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="loginEmail" id="loginEmail">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">通讯地址：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="address">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">家庭联系人：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="parent_info_name">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">家庭联系人电话：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="parent_info_tel">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">身份证号：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="idCard" id="idCard">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">民族：</i></label>
                        <span class="bm-school-name val-info">
	                        <jsp:include page="/WEB-INF/view/include/nation_select_search.jsp">
								<jsp:param name="inputid" value="nation" />
							</jsp:include>
						</span>
                    </div>
                    <span class="error-tips"></span>
                </div>
                
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">出生年月：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" readonly type="text" id="birthShow">
                        <input type="hidden" id="birth" name="birth">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">院校名称：</i></label>
                        <span class="select-search-box ">
	                        <jsp:include page="/WEB-INF/view/include/school_select_likesearch.jsp">
								<jsp:param name="inputid" value="zzx_school_name" />
							</jsp:include>
                        </span>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">院校类别：</i></label>
                        <select class="val-info" id="schoolTypeCode" name="schoolTypeCode">
                        	<option value="">选择院校类别</option>
                        	<c:forEach items="${schoolTypeList }" var="schoolVar">
								<option value="${schoolVar.value }">${schoolVar.label}</option>                     	
                        	</c:forEach>
                        </select>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">院校所在省：</i></label>
                        <select class="val-info" id="schoolProvCode" name="schoolProvCode" onchange="SUBMIT_STUDENT_INFO.getCity();">
                        	<option value="">选择省份</option>     
                        	<c:forEach items="${provinceList }" var="provinceVar">
								<option value="${provinceVar.cityId }">${provinceVar.city}</option>                     	
                        	</c:forEach>
                        </select>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">院校所在市：</i></label>
                        <select class="val-info" id="schoolCityCode" name="schoolCityCode">
                        </select>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">所在年级：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="grade">级
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">在校担任职务：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="schoolDuty">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">所在专业：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="major">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">所在学院：</i></label>
                        <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="schoolSubname">
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">宿舍楼号：</i></label>
                    <input class="val-info" placeholder="" disableautocomplete autocomplete="off" type="text" name="schoolDormitory">
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">就业意向地点：</i></label>
                        <c:forEach items="${cityPreferList }" var="cityPrefer">
                        	<span class="addr-check"><input type="checkbox" name="jobCityCode" value="${cityPrefer.value }">${cityPrefer.label }</span>
                        </c:forEach>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">课程类别：</i></label>
                        <select class="val-info" id="courseType" name="courseType">
                        	<option value="">选择课程类别</option>     
                        	<c:forEach items="${courseTypeList }" var="courseVar">
								<option value="${courseVar.value }">${courseVar.label}</option>                     	
                        	</c:forEach>
                        </select>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">身份证正面：</i></label>
                        <span class="inbox-card-span">
                            <div class="idcard-file-box">
                                <img src=""  id="returnShowFront">
                                <input id="frontImgName" type="hidden" name="frontImgName">
                                <input class="upload-input-file" type="file" id="frontImgFile" name="imgFile">
                                
                                <div class="upload-bgimg">
                                    <p class="upload-icon"><i class="Hui-iconfont">&#xe642;</i></p>
                                    <p class="upload-word">上传文件</p>
                                </div>
                            </div>
                        </span>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver">
                    <div class="clearfix">
                        <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">身份证反面：</i></label>
                        <span class="inbox-card-span">
                            <div class="idcard-file-box">
                                <img src="" id="returnShowBack">
                                <input id="backImgName" type="hidden" name="backImgName">
                                <input class="upload-input-file" type="file" id="backImgFile" name="imgFile">
                               
                                <div class="upload-bgimg">
                                    <p class="upload-icon"><i class="Hui-iconfont">&#xe642;</i></p>
                                    <p class="upload-word">上传文件</p>
                                </div>
                            </div>
                        </span>
                    </div>
                    <span class="error-tips"></span>
                </div>
                <div class="line-item-ver" id="protocolAgreeDiv">
                    <div class="clearfix">
                        <label class="align-right-label"></label>
                        <input type="checkbox" name="protocolAgree" id="protocolAgree">
                        <span>我已阅读并同意<a class="c-blue">《人才输送服务协议》</a></span>
                    </div>
                    <span class="error-tips"></span>
                </div>
            </div>
        </div>
        <div class="center fix-btn-box">
            <button type="submit" class="btn btn-blue mr10" id="submitStuInfo">保存</button>
            <button type="button" class="btn btn-wihte layui-layer-close" id="closeDialogBtn">返回</button>
        </div>
    </div>
    </form>
</div>
<!--学生报名页面结束-->
<script type="text/javascript">
$(function(){
	SUBMIT_STUDENT_INFO.init();
});
</script>
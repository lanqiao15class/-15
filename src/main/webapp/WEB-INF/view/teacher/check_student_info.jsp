<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

<input type="hidden" id="resourcePath" value="${ctxResource}">
<!-- 学生id -->
<input type="hidden" id="stuId" value="${stuId }">
<!-- 是否通过 (默认是pass)-->
<input type="hidden" id="pass" value="pass">
<!-- 学员的课程类别 -->
<input type="hidden" id="courseType" value="${stuInfo.courseType }">
<!--学员报名审核弹框开始-->
<div class="audit-stu-dialog clearfix">
	<!-- 左侧信息部分开始 -->
	<div id="studentInfoInclude" class="left-stu-info"></div>
	<!-- 左侧信息部分结束 -->
    <!-- 右侧审核信息开始 -->
    <div class="right-stu-audit">
    	<div class="right-stu-audit-rel">
            <div class="tit-audit">填写审核信息</div>
            <div class="audit-form">
                <form id="checkStudentInfoForm">
                    <div class="line-item-ver okOrNot">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">身份证姓名是否相符：</i></label>
                            <span class="other-radio-check"><input type="radio" name="isRightName" value="0" checked> 是</span>
                            <span class="other-radio-check"><input type="radio" name="isRightName" value="1" > 否</span>
    <!--                         <input onclick="get();" type="checkbox" value="show" id="aaa"> -->
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver okOrNot">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">报名费是否缴纳：</i></label>
                            <c:forEach items="${paidTypeList }" var="paidTypeVar">
                                <span class="other-radio-check"><input type="radio" name="isAvaiable" value="${paidTypeVar.value }" > ${paidTypeVar.text }</span>
                            </c:forEach>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">报名费合同金额：</i></label>
                            <input class="val-info money-icon" placeholder="" disableautocomplete="" autocomplete="off" type="text" name="BMFHTmoney" id="BMFHTmoney">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide favourOrNot">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">是否有减免：</i></label>
                            <span class="other-radio-check"><input type="radio" name="hasFavour" value="0" checked>是</span>
                            <span class="other-radio-check"><input type="radio" name="hasFavour" value="1">否</span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide JMShowOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">报名费减免金额：</i></label>
                            <input class="val-info money-icon" placeholder="" disableautocomplete="" autocomplete="off" type="text" name="BMFJMmoney" id="BMFJMmoney">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide JMShowOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">报名费减免审批人：</i></label>
                            <span class="handle-people">
                                <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
                                    <jsp:param name="inputid" value="JM_teacher_name" />
                                </jsp:include>
                            </span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide JMShowOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">报名费减免审批说明：</i></label>
                            <textarea rows="3" class="val-info-tptro" name="BMFJMremark" id="BMFJMremark"></textarea>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide TPHJShowOrHide" style="display: none;">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">特批后缴审批人：</i></label>
                            <span class="handle-people">
                            <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
                                <jsp:param name="inputid" value="TPHJ_teacher_name" />
                            </jsp:include>
                            </span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide TPHJShowOrHide" style="display: none;">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">特批后缴说明：</i></label>
                            <textarea rows="3" class="val-info-tptro" name="TPHJremark" id="TPHJremark"></textarea>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide YJNShowOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">报名费缴纳时间：</i></label>
                            <input type="text"  name="BMFtime" id="BMFtime"  class="val-info Wdate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})"  />
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide YJNShowOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">报名费缴纳金额：</i></label>
                            <input class="val-info money-icon" placeholder="报名费合同金额-减免金额" readonly autocomplete="off" type="text" name="BMFmoney" id="BMFmoney">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide YJNShowOrHide">
                        <div class="clearfix">
                            <label class="align-right-label "><i class="must-input-icon">*</i><i class="label-text">报名费缴纳方式：</i></label>
                            <c:forEach items="${paidMethodList }" var="methodVar">
                                <span class="other-radio-check"><input type="radio" name="payMethod" value="${methodVar.value }" > ${methodVar.label }</span>
                            </c:forEach>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide YJNShowOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">经办人：</i></label>
                            <span class="handle-people">
                                <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
                                    <jsp:param name="inputid" value="JBR_teacher_name" />
                                </jsp:include>
                            </span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">实训费合同金额：</i></label>
                            <input class="val-info money-icon" placeholder="" disableautocomplete="" autocomplete="off" type="text" name="HTmoney">
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">区域总监：</i></label>
                            <span class="handle-people">
                                <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
                                    <jsp:param name="inputid" value="QYZJ_teacher_name" />
                                </jsp:include>
                            </span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">招生经理：</i></label>
                            <span class="handle-people">
                                <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
                                    <jsp:param name="inputid" value="ZSJL_teacher_name" />
                                </jsp:include>
                            </span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="line-item-ver showOrHide">
                        <div class="clearfix">
                            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">课程顾问：</i></label>
                            <span class="handle-people">
                                <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
                                    <jsp:param name="inputid" value="KCGW_teacher_name" />
                                </jsp:include>
                            </span>
                        </div>
                        <span class="error-tips"></span>
                    </div>
                    <div class="center mb10">
                        <button type="submit" class="ml10 btn btn-blue" id="submitCheckBtn"><span id="tips">审核通过</span></button>
                        <button type="button" class="ml10 btn btn-wihte layui-layer-close" id="checkStudentInfoReturnBtn">返回</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- 右侧审核信息结束 -->
</div>
<!--学员报名审核弹框结束-->
<script type="text/javascript">
$(function(){
	CHECK_STUDENT_INFO.init();
});
</script>
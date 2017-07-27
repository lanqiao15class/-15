<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
  var basePath="${pageContext.request.contextPath}";
  var ctxBase="${pageContext.request.contextPath}";
  
</script>
<script src="${ctxStatic }/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic }/js/layer/layer.js"></script>
<script src="${ctxStatic }/js/validate/jquery.validate.js"></script>
<script src="${ctxStatic }/js/validate/validate-methods.js"></script>
<script src="${ctxStatic }/js/placeholder.js"></script>
<script src="${ctxStatic }/js/select_bag/jquery.searchableSelect.js"></script>
<script src="${ctxStatic }/js/ajaxfileupload.js"></script>
<script src="${ctxStatic }/js/My97DatePicker/WdatePicker.js"></script>
<!-- ztree  js开始 -->
	<script type="text/javascript" src="${ctxStatic }/js/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${ctxStatic }/js/ztree/jquery.ztree.excheck.min.js"></script>


<!-- ztree  js结束-->


<!--搜索框下拉自动填充js开始-->
<script src="${ctxStatic }/js/search_input_bag/jquery.autocompleter.js"></script>
<!--搜索框下拉自动填充js结束-->

<!--  老师账户才载入这些js -->
<c:if test="${usertype ==1 }">
<!--表格js开始-->
<script src="${ctxStatic }/js/slick/jquery-ui.min.js"></script>
<script src="${ctxStatic }/js/slick/jquery.event.drag-2.2.js"></script>
<script src="${ctxStatic }/js/slick/slick.core.js"></script>
<script src="${ctxStatic }/js/slick/slick.checkboxselectcolumn.js"></script>
<script src="${ctxStatic }/js/slick/slick.cellrangedecorator.js"></script>
<script src="${ctxStatic }/js/slick/slick.cellrangeselector.js"></script>
<script src="${ctxStatic }/js/slick/slick.cellselectionmodel.js"></script>
<script src="${ctxStatic }/js/slick/slick.rowselectionmodel.js"></script>
<script src="${ctxStatic }/js/slick/slick.columnpicker.js"></script>
<script src="${ctxStatic }/js/slick/slick.formatters.js"></script>
<script src="${ctxStatic }/js/slick/slick.grid.js"></script>
<script src="${ctxStatic }/js/slick/slick.autotooltips.js"></script>
<!--表格js结束-->
<!-- 下拉列表  start -->
<script src="${pageContext.request.contextPath }/js/commonjs.do"></script>
<!-- 下拉列表  end -->
</c:if>

<!--   学生 -->
<c:if test="${usertype ==0 }">
<script src="${pageContext.request.contextPath }/js/commonjsForStudent.do"></script>
</c:if>



<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/js/public.js" />
</jsp:include>

<!-- 登录 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/user/login.js" />
</jsp:include>


<!-- 账号安全页面 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/account/account_security.js" />
</jsp:include>

<!-- 已绑定的数据项 验证页面 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/account/checkBindCode.js" />
</jsp:include>

<!-- 用户个人信息页面 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/user/userInfo.js" />
</jsp:include>
<!-- 修改密码 js -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/account/modifyPassword.js" />
</jsp:include>
<!-- 修改密码选择验证方式js -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/account/pwdValidateWay.js" />
</jsp:include>

<!-- 新绑定界面 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/account/newBindCode.js" />
</jsp:include>

<!-- 添加意向学员界面（弹窗） -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/yxStu/submit_student_info.js" />
</jsp:include>

<!-- 查看学员详情（弹窗） -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/yxStu/yxStu_info.js" />
</jsp:include>

<!-- 查看班级详情（弹窗） -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/lqclass/diolog_class_manage.js" />
</jsp:include>

<!-- 意向学员白色模板js(我关注的/全部学员/弹窗跳转都写在这)开始 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/yxStu/yxContentWhite.js" />
</jsp:include>
<!-- 意向学员白色模板js(我关注的/全部学员/弹窗跳转都写在这)结束-->


<!-- 意向学员访谈记录列表 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/yxStu/VisitMain.js" />
</jsp:include>
<!-- 意向学员访谈记录列表 -->

<!-- 学员审核弹窗JS -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/yxStu/check_student_info.js" />
</jsp:include>
<!-- 学员审核弹窗JS -->

<!--   添加访谈记录 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/yxStu/AddVisit.js" />
</jsp:include>

<!--  正式学员(学员维护)js -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/zsStu/zsStudentList.js" />
</jsp:include>

<!--  正式学员(新学员分班)js -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/zsStu/zsDivideClass.js" />
</jsp:include>

<!--  正式学员(新学员确认分班转班页面)js -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/zsStu/student_into_class.js" />
</jsp:include>

<!--   改变学生的状态 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/zsStu/ChangeStudentStatus.js" />
</jsp:include>


<!--  新增班级界面 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/zsStu/class_add_dialog.js" />
</jsp:include>

<!-- 正式学员(异常状态)js -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/zsStu/unNormalStuList.js" />
</jsp:include>

<!-- 班级管理列表js -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/lqclass/classManageList.js" />
</jsp:include>

<!--  正式学员(新学员确认合班页面)js -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/lqclass/classes_merge.js" />
</jsp:include>


<!--  修改班级状态 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/lqclass/UpdateClassStatus.js" />
</jsp:include>


<!--  班级内的学员列表 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/lqclass/ClassMember.js" />
</jsp:include>

<!--  页面版添加班级 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/lqclass/class_add.js" />
</jsp:include>

<!--  创建班级记录弹窗 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/lqclass/classRecordDiolog.js" />
</jsp:include>
<!--   入职登记 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/job/inputJob.js" />
</jsp:include>

<!--   学员回款管理列表 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/charge/stuFeeList.js" />
</jsp:include>

<!--   费用业务 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/charge/fee_service_page.js" />
</jsp:include>

<!--   就业学员管理 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/job/stuEmployList.js" />
</jsp:include>

<!--   离职登记管理 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/job/dimission.js" />
</jsp:include>


<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/export/ExportStudent.js" />
</jsp:include>

<!-- 报名  -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/student/student_signup_diolog.js" />
</jsp:include>


<!-- 学员中心表头提示  -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/student/studnetTips.js" />
</jsp:include>

<!-- 学员分班转班、班级合班时     班级选择  -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/js/select_bag/selectableExtend.js" />
</jsp:include>
<!--   班级详细信息 -->

<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/lqclass/ClassDetail.js" />
</jsp:include>

<!--  学生详情 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/student/StuInfo.js" />
</jsp:include>

<!-- 权限     部门管理 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/permission/department.js" />
</jsp:include>

<!-- 权限     账户管理 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/permission/accountManager.js" />
</jsp:include>
<!-- 就业服务   招聘管理 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/job/JobVacancy.js" />
</jsp:include>

<!-- 权限   日志  -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/log/log.js" />
</jsp:include>


<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/course/courselist.js"/>
</jsp:include>

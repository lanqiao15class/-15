<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<div class="accont-inner-box">
    <!-- 初始记录信息开始 -->
	<input type="hidden" id="resourcePath" value="${ctxResource}">
	<!-- 初始记录信息结束 -->
	<!--个人信息页面开始-->
	   <div class="user-info-basic">
	       <div class="tit-h1-box">
	           <h1 class="tit-first">
	               <span>账户管理</span>
	           </h1>
	       </div>
	       <div class="tit-h2 clearfix">
	           <div class="tab-change-inner tab-accont-change fl account-inner-content">
	               <a class="curr" href="javascript:void(0)" id="userInfo">个人信息</a>
	               <a href="javascript:void(0)" id="account">账户安全</a>
	               <a href="javascript:void(0)" id="bind">帐号绑定</a>
	           </div>
	       </div>
	       <!--个人信息开始-->
	       
	       <div class="accont-tabbox clearfix info-mody-box mt30" style="display:block;">
	       <form id="editUserForm" method="post" enctype="multipart/form-data">
	           <div class="left-info-inner fl">
	               <a id="uploadHead" class="editHeadImg" href="javascript:void(0);">
	                   <img id="photoImg" src="${ctxResource}${user.photo}" width="100%" class="default">
	                   <img id="uploadImg" src="${ctxStatic}/images/uploader.png" width="100%" class="hover">
	                   <input type="file" id="headImgFile" name="photoImg" class="headImg_file valid">
	               </a>
	               <span class="mt30">用户id：&nbsp;${user.userId}</span>
	               <p class="fs12 mt30">
	                 <span>
	                <c:choose> 
	<c:when test="${user.type==0}">   
	   学员
	</c:when> 
	<c:otherwise>   
	  老师
	</c:otherwise> 
	</c:choose> 
	                </span>
	                <i class="vertical-line-user">|</i>
	                <span class="c-blue">
	                <c:choose> 
	<c:when test="${user.type==0}">   
	  ${stuStatus}
	</c:when> 
	<c:otherwise>  
	${lq:getTeacherTypeName(user.teacher.teachtype)} 
	</c:otherwise>
	</c:choose> 
	
	                </span>
	                </p>
	           </div><!-- left-info-inner fl end -->
	           <div class="right-info-inner fl">
	               <h3 class="tit-h3-user mb20">基础信息</h3>
	                   <div class="item mt30">
	                       <div class="item_box">
	                           <label class="accontlabel"><i class="must-input-icon">*</i><i class="label-text">昵称：</i></label>
	                           <span class="justlook" id="spanNickName"><c:out value="${user.nickname}"></c:out></span>
	                           <input type="text" class="editmodify" name="nickname" maxLength="17" placeholder="请输入昵称"">
	                       </div>
	                       <p class="item_tips"></p>
	                   </div>
	                   <div class="item mt15">
	                       <div class="item_box editInfobox">
	                           <label class="accontlabel"><i class="must-input-icon"></i><i class="label-text">性别：</i></label>
	                           <span class="justlook"> 
	                               <c:if test="${user.sex==0}">男</c:if>
	                       	    <c:if test="${user.sex==1}">女</c:if>
	                       	</span>
	                           <span class="editmodify">
	                              <input type="hidden" id="sexTemp" value="${user.sex}">
	                            <label class="radio">
	                				<input type="radio" name="sex" value="0"/>男
	            				</label>
	                            <label class="radio">
	                				<input type="radio"  name="sex" value="1"/>女
	            				</label>
	                           </span>
	                       </div>
	                       <p class="item_tips"></p>
	                   </div>
	                   <div class="item mt15">
	                            <input type="hidden" id="birthTemp" value="${user.birth}">
	                           <div class="item_box">
	                               <label class="accontlabel"><i class="label-text"><i class="must-input-icon"></i>生日：</i></label>
	                               <span class="justlook" id="spanBirth" style="display: inline;"><fmt:formatDate value="${user.birth}" type="date"  pattern="yyyy-MM-dd"/></span>
	                                <span class="editmodify"><fmt:formatDate value="${user.birth}" type="date"  pattern="yyyy-MM-dd"/></span>
	                               <!-- <input class="Wdate editmodify" type="text" name="birth" id="birth" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',isShowClear:true})"> -->
	                           <p class="item_tips"></p>
	                       </div>
	                   </div>
	                   <div class="item mt15">
	                      <input type="hidden"  id="provCodeTemp" value="${user.provCode}"/>
	                      <input type="hidden"  id="cityCodeTemp" value="${user.cityCode}"/>
	                        <label class="accontlabel"><i class="label-text"><i class="must-input-icon"></i>地区：</i></label>
	                        <span class="justlook">${prov}${city}</span>
	                        <span class="editmodify">
	                            <span class="provicebox">
	                                <span class="item_box">
	                                   <select id="province" name="provCode" onchange="USERINFO.change_province(this.value)">
	                                     <option value="">--请选择省份--</option>
	                                    <c:forEach items="${provList}" var="ct">
	                                    <option value="${ct.cityId}">${ct.city}</option>  
	                                   </c:forEach>
	                                   </select>
	                                </span>
	                                <span class="item_tips"></span>
	                            </span>
	                            <span class="provicebox" style="width:160px;">
	                                <span class="item_box">
	                                     <select id="city" name="cityCode" style="width: 147px;">
	                                    <option value="">--请选择市--</option>
	                                    <c:forEach items="${cityList}" var="ct">
	                                    <option value="${ct.cityId}">${ct.city}</option> 
	                                   </c:forEach>   
	                                   </select>
	                                </span>
	                                <span class="item_tips"></span>
	                            </span>
	                        </span>
	                   </div>
	                   <div class="center mt30 editmodify">
	                       <button type="submit" id="editUser" class="btn btn-blue save-info-basic">保存</button>
	                       <button id="canceledit" type="button" class="btn btn-blue ml10">取消</button>
	                   </div>
	           </div><!-- right-info-inner fl end -->
	           </form>
	           <button typ="button" class="btn btn-wihte editor-btn">编辑</button>
	       </div>
	       <!-- 个人信息结束 -->
	   </div>
	 <!--个人信息页面结束-->
</div>     
<script type="text/javascript">
$(function(){
	USERINFO.init();	
});
</script>
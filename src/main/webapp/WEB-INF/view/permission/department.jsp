<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>

 <style type="text/css">
#treeDemo * {font-size: 12pt;font-family:"Microsoft Yahei",Verdana,Simsun,"Segoe UI Web Light","Segoe UI Light","Segoe UI Web Regular","Segoe UI","Segoe UI Symbol","Helvetica Neue",Arial}
#treeDemo li ul{ margin-right:0; padding-right:0}
#treeDemo li {line-height:30px;}
#treeDemo li a {height:30px;padding-top: 0px;}
#treeDemo li a:hover {text-decoration:none; background-color: #E7E7E7;}
#treeDemo li a span.button.switch {visibility:hidden}
#treeDemo.showIcon li a span.button.switch {visibility:visible}
#treeDemo li a.curSelectedNode {background-color:#D4D4D4;border:0;height:30px;}
#treeDemo li span {line-height:30px;}
#treeDemo li span.button {margin-top: -7px;}
#treeDemo li span.button.switch {width: 16px;height: 16px;}
ul#treeDemo  {width: 280px;}
#treeDemo li a.level0 span {font-size: 130%;}
#treeDemo li a.level1 span {font-size: 120%;}
#treeDemo li a.level2 span {font-size: 110%;}
#treeDemo li span.button {background-image:url("${ctxStatic }/images/left_menuForOutLook.png"); *background-image:url("${ctxStatic }/images/left_menuForOutLook.gif")}
#treeDemo li span.button.switch.level0 {width: 20px; height:20px}
#treeDemo li span.button.switch.level1 {width: 20px; height:20px}
#treeDemo li span.button.noline_open {background-position: 0 0;}
#treeDemo li span.button.noline_close {background-position: -18px 0;}
#treeDemo li span.button.noline_open.level0 {background-position: 0 -18px;}
#treeDemo li span.button.noline_close.level0 {background-position: -18px -18px;}
	</style>
<!-- 初始化数据开始 -->
<input type="hidden" id="trees" value='${tree}'>
<!-- 初始化数据结束 -->

<!--右侧内容部分开始-->
  	<div class="content-inner-part">
      	<div class="inner-reletive">
           <!--右侧标题部分开始-->
           <div class="tit-h1-box">
             <h1 class="tit-first">
                 <span>系统管理</span><i class="gt-icon">&gt;</i><span class="curr">部门管理</span>
             </h1>
          </div>
          <!--右侧标题部分结束-->
           <!--右侧内容部分背景白色开始-->
                        <div class="inner-white">
                        <!-- 部门树形菜单开始 -->
                        <div class="search-part-tall" style="width: 260px;height:100%; float: left;margin-top: 50px"  >
							<div class="zTreeDemoBackground left"  style="overflow-x: auto;overflow-y: auto;">
								<ul id="treeDemo" class="ztree"   style="overflow: hidden;"></ul>
							</div>
						
						</div>
						 <!-- 部门树形菜单结束-->
                        <div  style="height:500px;">
                           <div class="tit-h2 clearfix">
								   <!--   <div class="tab-change-inner fl">
								         <a href="javascript:void(0)" id="myFocusStu_lq">我关注的</a>
								         <a href="javascript:void(0)" id="allStu_lq">全部</a>
								     </div>-->
								     <div class="fr btn-box-right">
								      <c:if test="${lq:ifIn(myFunction,'93')}">
								     <button type="button" class="btn btn-green" id="showAddDemDialog"><i class="Hui-iconfont">&#xe600;</i><span class="ml10">添加部门</span></button>
								     </c:if>
								   	  <c:if test="${lq:ifIn(myFunction,'94')}">
								     <button type="button" class="btn btn-green" id="showDelDemDialog"><i class="Hui-iconfont">&#xe6e2;</i><span class="ml10">删除部门</span></button>
								     </c:if>
								   	  <c:if test="${lq:ifIn(myFunction,'93')}">
								     <button type="button" class="btn btn-green" id="showUpDemDialog"><i class="  Hui-iconfont">&#xe60c;</i><span class="ml10">修改部门</span></button>
								    </c:if>
								     </div> 
                               </div> 
                           <div class="search-part-tall" >
                                    
                               <div class="table-scroll-parent"  style="overflow: auto;" id="myGrid" ></div>
                            </div>
                          </div>
                          <!--右侧内容部分背景白色结束-->
					<div style="clear: both;"></div>
					<pre>&lt;i class="Hui-iconfont"&gt;&amp;#xe684;&lt;/i&gt;</pre>
            </div>
      </div>
      </div>
   <!--右侧内容部分结束-->   
    <!-- 引入分页开始 -->
   <jsp:include page="/WEB-INF/view/include/pagejavascript.jsp">
	<jsp:param name="funcName" value="DEPARTMENT.dopagechange" />
	</jsp:include>
	<!-- 引入分页结束 -->

<script type="text/javascript">
	$(function(){
		 DEPARTMENT.init();
	})

</script>



<div class="add-inter-recode mt20"  id="showAddDemDialoglayer" style="display: none;">
	
    <div class="line-item-ver c-gray">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">选择的部门</i></label>
            <span class="fangtan-person"></span>
        </div>
        <span class="error-tips"></span>
    </div>
    
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">部门名称</i></label>
            <br/>
            <input placeholder="请输入部门名称" type="text" name="departmentName" id="departmentName" value=""  />
                        <input type="hidden" name="depid" id="depid" value=""  />
            
        </div>
        <span class="error-tips"></span>
    </div>
   <br/>
   <br/>
    <div class="center mb20">
    	<button  type="submit" id="submit_save" class="btn btn-blue mr10">保存</button>
        <button  class="btn btn-wihte"  onclick="javascript:layer.close(DEPARTMENT.index);">返回</button>
    </div>
</div>
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
.accountnav ul {
	background: white; 
	width: 240px; 
}
.accountnav ul li * {font-size: 12pt;font-family:"Microsoft Yahei",Verdana,Simsun,"Segoe UI Web Light","Segoe UI Light","Segoe UI Web Regular","Segoe UI","Segoe UI Symbol","Helvetica Neue",Arial}
.accountnav ul li {
	list-style-type: none;
	/*relative positioning for list items along with overflow hidden to contain the overflowing ripple*/
	position: relative;
	overflow: hidden;
}
.accountnav ul li a {
	/*font: normal 14px/28px Montserrat; 
	 //color: hsl(180, 40%, 40%);  */
	 padding-bottom:5px; 
	padding-top: 5px;
	padding-left: 0px;
	padding-right: 0px
	text-decoration: none;
	cursor: pointer; /*since the links are dummy without href values*/
	/*prevent text selection*/
	user-select: none;
	/*static positioned elements appear behind absolutely positioned siblings(.ink in this case) hence we will make the links relatively positioned to bring them above .ink*/
	position: relative;
}
</style>
<!-- 初始化数据开始 -->
<input type="hidden" id="trees" value='${tree}'>
<input type="hidden" id="accountManagerRoles" value='${accountManagerRoles}'>
<!-- 初始化数据结束 -->

<!--右侧内容部分开始-->
  	<div class="content-inner-part">
      	<div class="inner-reletive">
           <!--右侧标题部分开始-->
           <div class="tit-h1-box">
             <h1 class="tit-first">
                 <span>系统管理</span><i style="" class="gt-icon">&gt;</i><span class="curr">用户管理</span>
             </h1>
          </div>
          <!--右侧标题部分结束-->
           <!--右侧内容部分背景白色开始-->
                        <div class="inner-white">
                        <!-- 部门树形菜单开始 -->
                        <div class="search-part-tall" style="width: 260px;height:100%; float: left;margin-top: 45px;"  >
							<div class="tab-change-inner fl">
								<a href="javascript:void(0)" id="accountManagerzh" class="curr">组织架构</a>
								<a href="javascript:void(0)" id="accountManagerRole">角色</a>
								<div class="zTreeDemoBackground left"   style="overflow-x: auto;overflow-y: auto;" >
									<ul id="treeDemo" class="ztree"  style="overflow: hidden;" >
									</ul>
								</div>
								<div class="accountnav"  style="height:100px;position:static;   text-align: center;margin-top: 10px;display: none;overflow: auto;" >
									<ul  id="accountli">
										
									</ul>
								</div>
							</div>
						</div>
						 <!-- 部门树形菜单结束-->
                        <div  style="height:500px;">
                           <div class="tit-h2 clearfix">
								  
								     <div class="fr btn-box-right">
								       <c:if test="${lq:ifIn(myFunction,'95')}">
								     <button type="button" class="btn btn-green" id="showAddAccUserDialog"><i class="Hui-iconfont">&#xe600;</i><span class="ml10">添加账户</span></button>
								      </c:if>
								      <c:if test="${lq:ifIn(myFunction,'97')}">
								      <button type="button" class="btn btn-green" id="showAddAccRoleDialog"><i class="Hui-iconfont">&#xe600;</i><span class="ml10">添加角色</span></button>
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
	<jsp:param name="funcName" value="ACCOUNTMANAGER.dopagechange" />
	</jsp:include>
	<!-- 引入分页结束 -->

 <script type="text/javascript">
	$(function(){
		 ACCOUNTMANAGER.init();
	})

</script>




<!-- 添加删除弹框 -->
<div class="add-inter-recode mt20"  id="showAddAccUserDialogLayer" style="display: none;">
   <form action="" id="validateForm"  >
  
   <div class="add-inter-recode mt20">
    <div class="line-item-ver">
    	<div class="clearfix rowPart ">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">真实姓名：</i></label>
           <input  type="hidden"  name="userId"  id="userId">
            <input placeholder="输入真实姓名" type="text" name="realName" id="realName" />
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">性别：</i></label>
          	 &emsp;&emsp;男&emsp;<input type="radio" name="sex" checked="checked"  value="0" /> &emsp;&emsp;&emsp;&emsp;女 &emsp;<input type="radio" name="sex"  value="1"/>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">邮箱：</i></label>
            <input placeholder="输入邮箱" type="text" name="loginEmail" id="loginEmail" />
        </div>
        <span class="error-tips"  id="loginEmailerror"></span>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">手机号码：</i></label>
            <input placeholder="输入手机号" type="text" name="loginTel" id="loginTel" />
        </div>
        <span class="error-tips"  id="loginEmailerror"></span>
    </div>
    <div class="line-item-ver" >
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">部门：</i></label>
            <select style="width:348px"  id="accountdepartment" name="departmentId"  >
            </select>
        </div>
        <span class="error-tips"></span>
    </div>
     <div class="line-item-ver">
    	<div class="clearfix" >
            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">角色：</i></label>
             <span  id="accountrole" style="height: 60px;overflow-y:auto;display: inline-block; width: 348px;"></span>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver" >
    	<div class="clearfix" >
            <label class="align-right-label" style="float: left;    position: relative;"><i class="must-input-icon"></i><i class="label-text">角色拥有的功能</i></label>
            	<ul id="accounttreeDemo" class="ztree"  style="float: left;height: 100px;  position:relative;"></ul>
       <div style="clear: both;"></div>
        </div>
        <span class="error-tips"></span>
    </div>
   
    <div class="center mb20">
    	<button  type="submit" id="submit_save" class="btn btn-blue mr10">保存</button>
        <button  type="button" id="cancel_bnt" class="btn btn-wihte  cancel_bnt">返回</button>
    </div>
</div>
</form>
</div>




<!-- 查看 -->
<div class="add-inter-recode mt20"  id="showUserDialogLayer" style="display: none;">
   <div class="add-inter-recode mt20">
    <div class="line-item-ver">
    	<div class="clearfix rowPart ">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">真实姓名：</i></label>
            <span  id="realName1" ></span>
        </div>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">性别：</i></label>
          	 <span  id="sex1"></span>
        </div>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">邮箱：</i></label>
			<span  id="loginEmail1"></span>
        </div>
    </div>
    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">手机号码：</i></label>
			<span  id="loginTel1"></span>
        </div>
        <span class="error-tips"></span>
    </div>
    <div class="line-item-ver" >
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">部门：</i></label>
            <span  id="accountdepartment1"></span>
        </div>
    </div>
     <div class="line-item-ver">
    	<div class="clearfix" >
            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">角色：</i></label>
             <span  id="accountrole1" style="height: 40px;overflow-y:auto;display: inline-block; width: 348px;" ></span>
        </div>
    </div>
    <div class="line-item-ver" >
    	<div class="clearfix" >
            <label class="align-right-label" style="float: left;    position: relative;"><i class="must-input-icon"></i><i class="label-text">角色拥有的功能</i></label>
            	<ul id="accounttreeDemo1" class="ztree"  style="float: left;height: 200px;  position:relative;"></ul>
       <div style="clear: both;"></div>
        </div>
        <span class="error-tips"></span>
    </div>
   
    <div class="center mb20">
        <button  type="button" id="cancel_bnt" class="btn btn-wihte  cancel_bnt" >返回</button>
    </div>
</div>
</div>
<!--角色添加 -->
<div class="add-inter-recode mt20"  id="showUroleDialogLayer" style="display: none;">
   <div class="add-inter-recode mt20">
    <div class="line-item-ver">
    	<div class="clearfix rowPart ">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">角色名称：</i></label>
           <input  type="hidden"  name="RoleId"  id="RoleId">
            <input placeholder="输入角色名称" type="text" name="addUserRole" id="addUserRole" />
        </div>
        <span class="error-tips" id="addUserRoleError"></span>
    </div>
    <div class="line-item-ver" >
    	<div class="clearfix" >
            <label class="align-right-label"   style="float: left;    position: relative;"><i class="must-input-icon"></i><i class="label-text">权限分配</i></label>
            	<ul id="accounttreeDemo2" class="ztree"  style="float: left;height: 300px;  position:relative;"></ul>
       <div style="clear: both;"></div>
        </div>
        <span class="error-tips"></span>
    </div>
   
     <div class="center mb20">
    	<button  type="submit" id="submit_save1" class="btn btn-blue mr10">保存</button>
        <button  type="button" id="cancel_bnt" class="btn btn-wihte  cancel_bnt1">返回</button>
    </div>
</div>
</div>


<!--角色查看 -->
<div class="add-inter-recode mt20"  id="showUroleDialogLayer2" style="display: none;">
   <div class="add-inter-recode mt20">
    <div class="line-item-ver">
    	<div class="clearfix rowPart ">
            <label class="align-right-label"><i class="must-input-icon"></i><i class="label-text">角色名称：</i></label>
            <span id="showUserRole"></span>
        </div>
    </div>
    <div class="line-item-ver" >
    	<div class="clearfix" >
            <label class="align-right-label"  style="float: left;    position: relative;"><i class="must-input-icon"></i><i class="label-text">权限分配</i></label>
            	<ul id="accounttreeDemo3" class="ztree"  style="float: left;height: 350px;  position:relative;"></ul>
       <div style="clear: both;"></div>
        </div>
    </div>
   
     <div class="center mb20">
        <button  type="button" id="cancel_bnt" class="btn btn-wihte  cancel_bnt2"  >返回</button>
    </div>
</div>
</div>


<!--学员详情弹框结束-->
<script type="text/javascript"> 
$(function(){
	
	//权限判断是否可以编辑。
	 <c:if  test="${lq:ifIn(myFunction,'95')}">
	  ACCOUNTMANAGER.userstatus=1;
	  </c:if >
	  <c:if  test="${lq:ifIn(myFunction,'97')}">
	  ACCOUNTMANAGER.rolestatus=1;
	  </c:if >
});
   
 

</script>
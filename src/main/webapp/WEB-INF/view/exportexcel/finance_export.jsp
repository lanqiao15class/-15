<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ include file="/WEB-INF/view/include/taglib.jsp"%> 
    
  	<!--右侧内容部分开始-->
            	<div class="content-inner-part">
                	<div class="inner-reletive">
                    	<!--右侧标题部分开始-->
						<div class="tit-h1-box">
                            <h1 class="tit-first">
                                <span>财务</span><i class="gt-icon">&gt;</i><span class="curr">财务报表导出</span>
                            </h1>
                        </div>
						<!--右侧标题部分结束-->
                        <!--右侧内容部分背景白色开始-->
                        <div class="inner-white">
                            <div class="search-part-tall">
                                <div class="query-opt-coopration clearfix">
                                    <div class="query-box-parent">
                                        <div class="query-box mt15" style="display:block;">
                                        	<span class="select-box">
                                                <select class="select" id="reportselectTime" name="reportselectTime" >
                                                    <option value="0">选择查询时间</option>
                                                    <option value="5">报名费缴纳时间</option>
                                                    <option value="6">实训费缴纳时间</option>
                                                    <option value="1">开课时间</option>
                                                    <option value="2">结业时间</option>
                                                    <option value="3">首次入职时间</option>
                                                    <option value="4">就业服务结束时间</option>
                                                    <option value="7">班级</option>
                                                </select>
                                            </span>
                                            <span class="ser-out-span">
                                            	<span>从</span>
                                                 <input type="text"  name="starttime" id="starttime"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"  />
	   
                                                <span>到</span>
                                                  <input type="text"  name="endtime" id="endtime"  class="val-info Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})"  />
	                                         
                                            </span>
                                          <span class="select-search-box" id="exportclass"  style="display: none;">
						                       <jsp:include page="/WEB-INF/view/include/class_select_search.jsp">
													<jsp:param name="inputid" value="zzx_exportStudent" />
											   </jsp:include>
						                    </span> 
                                            <button class="btn btn-blue search-btn" onclick="ExportStudent.find()"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
                                            <button class="btn btn-gray much-more"><span class="mr10">更多筛选</span><i class="Hui-iconfont">&#xe6d5;</i></button>
                                            <button class="btn btn-gray put-away"><span class="mr10">收起筛选</span><i class="Hui-iconfont">&#xe6d6;</i></button>
                             
                                        </div>
                                        <div class="query-box">
                                            <span class="select-box">
                                                <select class="select" id="stuStatus">
                                                    <option value="" selected>学员状态</option>
                                                   <c:forEach var="one" items="${stuStatus }">
                                                    	<option value="${one.value }">${one.label }</option>
                                                   </c:forEach>
                                                </select>
                                                
                                            </span>
                                            <span class="select-box">
                                                <select class="select"  id="signStatus">
                                                    <option value="" selected>报名费</option>
                                                   <c:forEach var="one" items="${payStatus }">
                                                    	<option value="${one.value }">${one.label }</option>
                                                   </c:forEach>
                                                   
                                                </select>
                                            </span>
                                            <span class="select-box">
                                                <select class="select" id="studyStatus">
                                                    <option value="" selected>学费</option>
                                                   <c:forEach var="one" items="${payStatus }">
                                                    	<option value="${one.value }">${one.label }</option>
                                                   </c:forEach>
                                                   
                                                </select>
                                            </span>
                                            
    <jsp:include page="/WEB-INF/view/include/school_select_likesearch.jsp">
	<jsp:param name="inputid" value="select_school_id" />
	<jsp:param name="placeholder" value="请选择院校..." />
	<jsp:param name="width" value="300" />
	</jsp:include>
   
    <jsp:include page="/WEB-INF/view/include/student_input_likesearch.jsp">
	<jsp:param name="inputid" value="student_name_export" />
	<jsp:param name="inputwidth" value="300" />
	<jsp:param name="placeholder" value="请输入学生姓名.." />		
	</jsp:include>
                                            <button class="btn btn-blue search-btn" onclick="ExportStudent.find()"><i class="Hui-iconfont">&#xe709;</i><span class="ml10">查询</span></button>
                                        </div>
                                    </div>
                                    <div class="opt-btn-box">
                                        <i class="mr20"></i>
                                        <a class="c-blue" onclick="ExportStudent.export_dialog_open()" href="javascript:void(0)">财务报表导出</a>
                                    </div>
                                </div>
                                <div class="table-scroll-parent" id="myexportTable"></div>
                            </div>
                        </div>
                    </div>
                </div>
                
                
  <div style="display:none;overflow:hidden;" id="Exportdialogcontant">
<!--财务报表导出弹框开始-->
  		
<div class="export-dialog mt20">

   <form id="exportform" target="_blank" method="post" action="${ctxBase }/report/financefile.do">

    <div class="line-item-ver">
    	<div class="clearfix">
            <label class="align-right-label"><i class="must-input-icon">*</i><i class="label-text">报表月份：</i></label>
      	<input placeholder="请选择报表月份" type="text" name="exportmonth" id="exportmonth" value="" disableautocomplete="" autocomplete="off"  class="Wdate" readonly onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true})" />
  
           <input type="hidden" name="select_school_id" value="" />
           <input type="hidden" name="student_name_export" value="" />
           <input type="hidden" name="studyStatus" value="" />
           <input type="hidden" name="signStatus" value="" />
           <input type="hidden" name="stuStatus" value="" />
           <input type="hidden" name="stime" value="" />
           <input type="hidden" name="etime" value="" />
           <input type="hidden" name="optype" value="" />
       
       
           <input type="hidden" name="signStatusName" value="" />
           <input type="hidden" name="stuStatusName" value="" />
           <input type="hidden" name="select_school_idName" value="" />
           <input type="hidden" name="studyStatusName" value="" />
           
        
        </div>
        <span class="error-tips" id="monthtiperror"></span>
    </div>
    
    </form>
    <div class="center mb20">
    	<button class="btn btn-blue mr10" onclick="ExportStudent.exportfile()">确定</button>
        <button class="btn btn-wihte" onclick="layer.closeAll()">取消</button>
    </div>
</div>
<!--财务报表导出弹框结束-->
</div>


   <jsp:include page="/WEB-INF/view/include/pagejavascript.jsp">
	<jsp:param name="funcName" value="ExportStudent.dopagechange" />
	</jsp:include>
	
                <!--右侧内容部分结束-->
      <script>
      
    
      
      $(function(){
    	  $("#exportmonth").val(getNowMonth());
    	  ExportStudent.init();
      });
      </script>
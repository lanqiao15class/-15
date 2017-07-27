<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>


<!--右侧内容部分开始-->
  	<div class="content-inner-part">
      	<div class="inner-reletive">
           <!--右侧标题部分开始-->
           <div class="tit-h1-box">
             <h1 class="tit-first">
                 <span>系统管理</span><i class="gt-icon">&gt;</i><span class="curr">日志管理</span>
             </h1>
          </div>
          <!--右侧标题部分结束-->
           <!--右侧内容部分背景白色开始-->
                        <div class="inner-white">
                     
                        <div  style="height:500px;">
                           <div class="tit-h2 clearfix"  style="padding-bottom:7px" >
								 <div class="query-box mt15" style="display:block;">
								 <!-- 加载日期 -->
								 <span >
                                            	<span>开始时间</span>
                                                 <input type="text" name="starttime" id="starttime" class="val-info Wdate" readonly="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endtime\')}',dateFmt:'yyyy-MM-dd',isShowClear:true})">
	   
                                                <span>结束时间</span>
                                                  <input type="text" name="endtime" id="endtime" class="val-info Wdate" readonly="" onclick="WdatePicker({ minDate:'#F{$dp.$D(\'starttime\')}',dateFmt:'yyyy-MM-dd',isShowClear:true})">
	                                         
                                            </span>
                                            <c:if test="${lq:ifIn(myFunction,'99')}">
                                           &nbsp;&nbsp;&nbsp; 操作方式 <select  id="typelog">
                                             <option value="-1" > 请选择</option>
                                              <option value="0">增加 </option>
                                               <option value="1" >修改 </option>
                                                <option value="2"> 删除</option>
                                                 <option value="5"> 异常</option>
                                            </select>
                                          </c:if>
                                          	<%-- 	&nbsp;&nbsp;&nbsp;  姓名： <span class="select-search-box">
						                       <jsp:include page="/WEB-INF/view/include/teacher_select_likesearch.jsp">
													<jsp:param name="inputid" value="zzx_teacherLOG" />
											   </jsp:include>
						                    </span> --%>
			                            <button class="btn btn-blue search-btn query_lq" onclick="LOGINFO.dopagechange(1,20)" type="button"><i class="Hui-iconfont"></i><span class="ml10">查询</span></button>
			                            
                      			  </div> 
                             </div> 
                           <div class="search-part-tall"   >
                                    
                               <div class="table-scroll-parent"  style="overflow: auto;" id="myGrid" ></div>
                            </div>
                          </div>
                          <!--右侧内容部分背景白色结束-->
					<pre>&lt;i class="Hui-iconfont"&gt;&amp;#xe684;&lt;/i&gt;</pre>
            </div>
      </div>
      </div>
   <!--右侧内容部分结束-->   
    <!-- 引入分页开始 -->
   <jsp:include page="/WEB-INF/view/include/pagejavascript.jsp">
	<jsp:param name="funcName" value="LOGINFO.dopagechange" />
	</jsp:include>
	<!-- 引入分页结束 -->

<script type="text/javascript">
	$(function(){
		var logdatacolumn="";
		<c:if test="${lq:ifIn(myFunction,'99')}">
		logdatacolumn={
			id: "old_data", 
			name: "操作数据",
			field: "old_data", 
			width: 250
			};
			 </c:if>
		 LOGINFO.init(logdatacolumn);
	})

</script>


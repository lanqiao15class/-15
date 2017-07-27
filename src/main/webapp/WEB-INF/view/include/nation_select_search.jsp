<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.lanqiao.util.*"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	/*
		民族下拉选择
	 *    支持的参数
	 *  inputid  : 标签的id 属性.  
	 *  
	 */
	String inputid = request.getParameter("inputid");
	
%>

<span class="select-search-box">
   <select id="<%=inputid%>"  name="<%=inputid%>"  class="scoolname">
   </select>
</span>
<script>
 $(function(){
	
		//学院列表.
		$('#<%=inputid%>').searchableSelect({data:gl_nationdata,
			afterSelectItem: function(sval,stext)
		 	{
				console.log("selectid:"+sval+" text:" + stext);
		 	}});
		
 });
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.lanqiao.util.*"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	/*
	 *    支持的参数
	 *  inputid  : 标签的id 属性.  
	 *  inputwidth : 标签的显示宽度
	 */
	String inputid = request.getParameter("inputid");
   String placeholder= request.getParameter("placeholder");
   if(placeholder==null) placeholder ="";

%>

<span class="select-search-box">
   <select id="<%=inputid %>"  name="<%=inputid %>" >
   </select>
</span>
 	
 	
<script>
 $(function(){
		//学生列表
		
		$('#<%=inputid%>').searchableSelect({data:gl_studentnamelist,placeholder:'<%=placeholder%>',
			afterSelectItem:function(sval,stext)
			 {
				console.log("select " + sval +":" + stext);
		 }});
		
 });
</script>
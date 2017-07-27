<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.lanqiao.util.*"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	/*  班级列表
	 *    支持的参数
	 *  inputid  : 标签的id 属性.
	 
	 *  placeholder : 提示信息
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
		//班级列表
		
		$('#<%=inputid%>').searchableSelect({data:gl_classlist,placeholder:'<%=placeholder%>',
			afterSelectItem:function(sval,stext)
			 {
				//console.log("select " + sval +":" + stext);
		 }});
		
 });
</script>
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
	String selectid= request.getParameter("selectid");
	String selecttext= request.getParameter("selecttext");
	
%>

<span class="select-search-box">
   <select id="<%=inputid%>"  name="<%=inputid%>"  class="scoolname">
   </select>
</span>
<script>
 $(function(){
		 <%
		   if(selectid==null || "".equals(selectid))
		   {
		%>
				//民族列表
				$('#<%=inputid%>').searchableSelect({data:gl_nationdata,
					afterSelectItem: function(sval,stext)
				 	{
						$("input[name=nation]").val(stext);
						$("input[name=nation]").parents(".clearfix").next('.error-tips').html("");
				 	}
				});
		<%
		   } 
		%>
		
		//===================================================
		<%
		   if(selectid !=null  && !"".equals(selectid))
		   {
		%>
				$('#<%=inputid%>').searchableSelect({data:gl_nationdata,selectid:'<%=selectid%>', selecttext:'<%=selecttext%>',
					afterSelectItem: function(sval,stext)
				 	{
						$("input[name=nation]").val(stext);
						$("input[name=nation]").parents(".clearfix").next('.error-tips').html("");
				 	}
				});
		<%
		   } 
		%>
		
 });
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.lanqiao.util.*"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	/*
	 *    支持的参数
	 *   inputid  : 标签的id 属性.  
	 *   selectid : 默认选中的id
		 selecttext : 默认选中的文字内容
		 placeholder : 提示信息.
		 width : 宽度
	 */
	String inputid = request.getParameter("inputid");
	String selectid= request.getParameter("selectid");
	String selecttext= request.getParameter("selecttext");
	String placeholder= request.getParameter("placeholder");
	String width= request.getParameter("width");

%>

<span class="select-search-box"  <% if(width !=null) out.println("style='width:" + width +"px'"); %> >
   <select id="<%=inputid%>"  name="<%=inputid%>"  class="scoolname">
  
   </select>
</span>
<script>

 $(function(){
	
		//学院列表.
		<%
		   if(placeholder !=null )
		   {
		%>
		$('#<%=inputid%>').searchableSelect({data:gl_schoolllist,placeholder:'<%=placeholder%>',
			afterSelectItem: function(sval,stext)
		 	{
				console.log("selectid:"+sval+" text:" + stext);
		 	}});
		<%
		
			 // System.out.println("placeholder...");
		   }else if(selectid !=null)
		   {
		%>
		//===================================================
	
		$('#<%=inputid%>').searchableSelect({data:gl_schoolllist,selectid:'<%=selectid%>', selecttext:'<%=selecttext%>',
			afterSelectItem: function(sval,stext)
		 	{
				console.log("selectid:"+sval+" text:" + stext);
		 	}});
		<%
		   }else
		   {
		%>
		
		$('#<%=inputid%>').searchableSelect({data:gl_schoolllist,
			afterSelectItem: function(sval,stext)
		 	{
				console.log("selectid:"+sval+" text:" + stext);
		 	}});
		<%
		   } 
		%>
		
 });
</script>
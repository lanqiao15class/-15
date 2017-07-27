<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.lanqiao.util.*"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	/*
	 *    支持的参数
	 *  inputid  : 标签的id 属性.  
	 *  inputwidth : 标签的显示宽度
		 callback : 数据变化的时候回调的js 函数名字
	 */
	String inputid = request.getParameter("inputid");
	String placeholder= request.getParameter("placeholder");
	if(placeholder==null) placeholder ="";
	
	String selectid= request.getParameter("selectid");
	String selecttext= request.getParameter("selecttext");
	String afterCallback = request.getParameter("callback");
	
	//if(afterCallback ==null) afterCallback ="";
	

%>

<span class="select-search-box">
   <select id="<%=inputid %>"  name="<%=inputid %>" >
   </select>
</span>
 	
 	
<script>

var afterCallback = null;
 <%
    if(afterCallback !=null)
    	out.println ("afterCallback=" + afterCallback +";");
 %>

 $(function(){
		 var   _tlist= _wrapteacherList();
		 
		  console.log("tlist:" + _tlist.length);
		//老师列表.
		<%
		   if(placeholder !=null )
		   {
		%>
		$('#<%=inputid%>').searchableSelect({data:_tlist,placeholder:'<%=placeholder%>',
			afterSelectItem: function(sval,stext)
		 	{
				console.log("selectid:"+sval+" text:" + stext);
				if(afterCallback)
					afterCallback.apply(this,[sval,stext]);
				
		 	}});
		<%
		
			 // System.out.println("placeholder...");
		   }else if(selectid !=null)
		   {
		%>
		//===================================================
	
		$('#<%=inputid%>').searchableSelect({data:_tlist,selectid:'<%=selectid%>', selecttext:'<%=selecttext%>',
			afterSelectItem: function(sval,stext)
		 	{
				if(afterCallback)
					afterCallback.apply(this,[sval,stext]);
				
				console.log("selectid:"+sval+" text:" + stext);
		 	}});
		<%
		   }else
		   {
		%>
		
		$('#<%=inputid%>').searchableSelect({data:_tlist,
			afterSelectItem: function(sval,stext)
		 	{
				if(afterCallback)
					afterCallback.apply(this,[sval,stext]);
				
				console.log("selectid:"+sval+" text:" + stext);
		 	}});
		<%
		   } 
		%>
		
 });
</script>
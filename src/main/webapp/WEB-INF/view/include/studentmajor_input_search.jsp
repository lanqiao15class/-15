<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.lanqiao.util.*"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	/*
	    支持的参数
	 *  inputid  : 标签的id 属性.  
	 *  inputwidth : 标签的显示宽度
	 
	 */
	String inputid = request.getParameter("inputid");
	String inputwidth = request.getParameter("inputwidth");
%>
<span class="search-auto-input">
 <div class="field">
    <input  disableautocomplete autocomplete="off" type="text" name="<%=inputid %>" id="<%=inputid %>" placeholder="学员专业" maxlength="40" />
 </div>
</span>
<script>
 $(function(){
	 

	  	$('#<%=inputid%>').autocompleter({
	        // marker for autocomplete matches
	        highlightMatches: true,
	        // object to local or url to remote search
	        source: gl_majorList,
	        // custom template
	        template: false, 		//"我的名字:{{ label }}",   //'{{ label }}',  //'{{ label }} <span>({{ hex }})</span>',
	        // show hint
	        hint: false,
		    focusOpen:true,
	        // abort source if empty field
	        empty: true,

	        // max results
	        limit: 10,

	         callback: function (value, index, selected) {
	            if (selected) {
	            	if(selected.id)
	            		{
					 		//$('#stuname').val(selected.id);
					 		$('#<%=inputid%>').val(selected.label);
	            		}
					 else
						 $('#<%=inputid%>').val(selected.label)
						 
					
						 
	            }
	        } 
	    });
	  	
	  	
	 
	 
 });
</script>
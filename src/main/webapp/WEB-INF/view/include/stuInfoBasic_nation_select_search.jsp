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
						//console.log("selectid:"+sval+" text:" + stext);
				 		if($("em[name=nation]").html()=='点击填写'||stext==""){
				 			return;
				 		}else{
					 		var data="nation="+stext;
							//提交
				   			$.ajax({
						    	  type:"post",
						             url:basePath+"/student/editStuBasic.do",  
						             data:data,
						             dataType:"json",
						             success:function(data){
						         		if(data.code == 200){
						         			$("em[name=nation]").html(stext);
						         			layer.msg("修改成功");
						         		}else{
						          			layer.msg("修改失败");
						          		}
						             }
						    });
				 		}
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
						//console.log("selectid:"+sval+" text:" + stext);
				 		if($.trim($("em[name=nation]").html())==stext||stext==""){
				 			return;
				 		}else{
					 		var data="nation="+stext;
							//提交
				   			$.ajax({
						    	  type:"post",
						             url:basePath+"/student/editStuBasic.do",  
						             data:data,
						             dataType:"json",
						             success:function(data){
						         		if(data.code == 200){
						         			$("em[name=nation]").html(stext);
						         			layer.msg("修改成功");
						         		}else{
						          			layer.msg("修改失败");
						          		}
						             }
						    });
				 		}
				 	}
				});
		<%
		   } 
		%>
		
 });
</script>
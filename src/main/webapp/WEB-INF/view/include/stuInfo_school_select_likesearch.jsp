<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.lanqiao.util.*"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	/*
	 *    支持的参数
	 *  inputid  : 标签的id 属性.  
	 *   selectid : 默认选中的id
		 selecttext : 默认选中的文字内容
	 */
	String inputid = request.getParameter("inputid");
	String selectid= request.getParameter("selectid");
	String selecttext= request.getParameter("selecttext");
	//System.out.println ("-=-=-="+selectid);
%>

<span class="select-search-box yuanxiao">
   <select id="<%=inputid%>"  name="<%=inputid%>"  class="scoolname">
  
   </select>
</span>
<script>
 $(function(){
	
		//学院列表.
		<%
		 if(selectid==null || "".equals(selectid))
		   {
		%>
				$('#<%=inputid%>').searchableSelect({data:gl_schoolllist,
					afterSelectItem: function(sval,stext)
				 	{
						//console.log("selectid:"+sval+" text:" + stext);
				 		if($("em[name=univCode]").html()=="点击填写"||sval==""){
							return;
						}else{
							var data="userId=${user.userId}&univCode="+sval;
							//提交
				   			$.ajax({
						    	  type:"post",
						             url:basePath+"/student/editStuInfo.do",  
						             data:data,
						             dataType:"json",
						             success:function(data){
						         		if(data.code == 200){
						         			$("em[name=univCode]").html(stext);
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
				$('#<%=inputid%>').searchableSelect({data:gl_schoolllist,selectid:'<%=selectid%>', selecttext:'<%=selecttext%>',
					afterSelectItem: function(sval,stext)
				 	{
						//console.log("selectid:"+sval+" text:" + stext);
						if($.trim($("em[name=univCode]").html())==stext||sval==""){
							return;
						}else{
							var data="userId=${user.userId}&univCode="+sval;
							//提交
				   			$.ajax({
						    	  type:"post",
						             url:basePath+"/student/editStuInfo.do",  
						             data:data,
						             dataType:"json",
						             success:function(data){
						         		if(data.code == 200){
						         			$("em[name=univCode]").html(stext);
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
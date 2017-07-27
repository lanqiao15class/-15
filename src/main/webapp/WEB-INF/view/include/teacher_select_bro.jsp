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
	String inputid = request.getParameter("inputid2");
	String selectid= request.getParameter("selectid");
	String selecttext= request.getParameter("selecttext");

%>

<span class="select-search-box">
   <select id="<%=inputid %>"  name="<%=inputid %>" class="scoolname">
   </select>
</span>
 	
 	
<script>

 $(function(){
		 var   _tlist= _wrapteacherList();
		
		<%
		if(selectid==null||"".equals(selectid))
		{
		%>
		//===================================================
				$('#<%=inputid%>').searchableSelect({data:_tlist,
					afterSelectItem: function(sval,stext)
				 	{
						if($("em[name=brokersId]").html()=="点击填写"||sval==""){
							return;
						}else{
							var data="lqClassId=${lqclassInfo.lq_class_id}&brokersId="+sval;
							//提交
				   			$.ajax({
						    	  type:"post",
						             url:basePath+"/lqClass/editLqClassInfo.do",  
						             data:data,
						             dataType:"json",
						             success:function(data){
						         		if(data.code == 200){
						         			$("em[name=brokersId]").html(stext);
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
		//=====================================================================================================
		<%
		   if(selectid!=null && !"".equals(selectid))
		   {
		%>
				$('#<%=inputid%>').searchableSelect({data:_tlist,selectid:'<%=selectid%>', selecttext:'<%=selecttext%>',
					afterSelectItem: function(sval,stext)
				 	{
						if($.trim($("em[name=brokersId]").html())==stext||sval==""){
							return;
						}else{
							var data="lqClassId=${lqclassInfo.lq_class_id}&brokersId="+sval;
							//提交
				   			$.ajax({
						    	  type:"post",
						             url:basePath+"/lqClass/editLqClassInfo.do",  
						             data:data,
						             dataType:"json",
						             success:function(data){
						         		if(data.code == 200){
						         			$("em[name=brokersId]").html(stext);
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
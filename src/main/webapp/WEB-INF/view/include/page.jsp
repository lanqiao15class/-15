<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
 <%
   int totalpage= 0;
 	int currpage = 0;
 	String funcName ="";
 	int pageSize =20;
 	
 	int recordcount= new Integer(request.getParameter("recordcount"));
 	currpage= new Integer(request.getParameter("currpage"));
 	funcName = request.getParameter("funcName");
 	pageSize= new Integer(request.getParameter("pageSize"));
 	
 	totalpage=(int)(recordcount / (pageSize < 1 ? 20 : pageSize));
	
	if (recordcount % pageSize != 0 || totalpage == 0) {
		totalpage++;
	}
 	request.setAttribute("pageSize", pageSize);
 	
 %>
<!--分页开始-->
<div class="pager-part clearfix">
<input type="hidden" id="p_totalpage" value="<%=totalpage%>" />
<input type="hidden" id="p_currpage" value="<%=currpage %>" />
<input type="hidden" id="p_selectcallback" value="<%=funcName %>" />
<input type="hidden" id="p_pagesize" value="<%=pageSize%>">


	<div class="fl">总数：<em class="stu_total"><%=recordcount %></em></div>
	<div class="pager fr">
        <span>每页显示<select id="p_pageSize" class="select-pager">
        <option <c:if test="${pageSize ==20 }"> selected </c:if> >20</option>
        <option <c:if test="${pageSize ==30 }"> selected </c:if> >30</option>
        <option <c:if test="${pageSize ==50 }"> selected </c:if> >50</option>
        <option <c:if test="${pageSize ==100 }"> selected </c:if> >100</option>
        
        </select>条</span>
        <ul id="ul_pagelist">
          
        </ul>	
    </div>
</div>
<script>
var PageWiget={};
PageWiget.render=function(totalpage,currpage)
{
	var strhtml ="";
	var leftcount = 5;
	var rightcount =5;
	var acount = leftcount + rightcount;
	
	if(currpage >1)
	{
		//有上一页
		strhtml +='<li><a onclick="PageWiget.prev()"  href="javascript:void(0);"><span>«上一页</span></a></li>';
		
	}else
	{
		strhtml +='<li><a class="prev current" href="javascript:void(0);"><span>«上一页</span></a></li>';
	}
		
			
			//页吗很少, 直接全部显示
			if(totalpage <= acount)
			{
				
			 	for(var i=1;i <= totalpage;i++)
				 {
				  if(currpage == i)
					  strhtml +='   <li><a class="current" onclick="PageWiget.go('+i+')" href="javascript:void(0);">'+i+'</a></li>';
				  else
				 	strhtml +='   <li><a onclick="PageWiget.go('+i+')" href="javascript:void(0);">'+i+'</a></li>';
				 }
		 		
			}else
			{
				var bindex = currpage - leftcount <=0 ? 1: currpage - leftcount;
				var eindex = currpage + rightcount > totalpage ? totalpage:  currpage + rightcount;
				
				
				if((eindex - bindex) < acount)
					eindex  += (acount - (eindex - bindex));
				if(eindex > totalpage) eindex=totalpage;
				
				
	
				if((eindex - bindex) <acount)
				{
					bindex  -= (acount - (eindex - bindex));
				}
				if(bindex <=0 ) bindex = 1;
				
				
				console.log("bindex=" + bindex +" eindex=" +eindex);
				
				for(var i=bindex;i <= eindex ;i++)
				 {
				  if(currpage == i)
					  strhtml +='   <li><a class="current" onclick="PageWiget.go('+i+')" href="javascript:void(0);">'+i+'</a></li>';
				  else
				 	strhtml +='   <li><a onclick="PageWiget.go('+i+')" href="javascript:void(0);">'+i+'</a></li>';
				 }
		 		
			}
//========================================================
	if(currpage < totalpage)
	{
		//有下一页
		strhtml +='<li><a onclick="PageWiget.next()" href="javascript:void(0);"><span>下一页»</span></a></li>';
	}else
		{
		
		strhtml +='<li><a   class="prev current" href="javascript:void(0);"><span>下一页»</span></a></li>';

		}
		
		$("#ul_pagelist").html(strhtml);
}

//上一页
PageWiget.prev=function()
{
	var psize = $("#p_pageSize").val();
		var n = parseInt($("#p_currpage").val());
		$("#p_currpage").val(n-1);
	 var tpage =parseInt($("#p_totalpage").val());
	 var cpage =n-1;
	 PageWiget.render(tpage,cpage);
	 var strcall = $("#p_selectcallback").val();
	 eval(strcall+"("+cpage+","+psize+");");
	 
	 // console.log("next " + (n-1));
	    
}

//下一页
PageWiget.next=function()
{
	var psize = $("#p_pageSize").val();
	
	var n = parseInt($("#p_currpage").val());
	$("#p_currpage").val(n+1);
 	var tpage =parseInt($("#p_totalpage").val());
 	var cpage =n+1;
    PageWiget.render(tpage,cpage);
    var strcall = $("#p_selectcallback").val();
    eval(strcall+"("+cpage+","+psize+");");
   // console.log("next " + n+1);
    
 
}

//跳转到某一页
PageWiget.go=function(p)
{
	$("#p_currpage").val(p);
	var psize = $("#p_pageSize").val();
	
 	var tpage =parseInt($("#p_totalpage").val());
 	var cpage =p;
    PageWiget.render(tpage,cpage);
    var strcall = $("#p_selectcallback").val();
    eval(strcall+"("+cpage+","+psize+");");
    //console.log("go " + p);
    
}


 $(function(){
	 // 绘制
	 var tpage =parseInt($("#p_totalpage").val());
	 var cpage =parseInt( $("#p_currpage").val());
	// console.log(tpage +":" + cpage);
	 PageWiget.render(tpage,cpage);
	 
	 
 });
</script>

<!--分页结束-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
 
 <%
	String funcName = request.getParameter("funcName");
 %>
<!--分页开始-->
<div class="pager-part clearfix">
<input type="hidden" id="p_totalpage" value="0" />
<input type="hidden" id="p_currpage" value="0" />
<input type="hidden" id="p_selectcallback" value="<%=funcName %>" />


	<div class="fl">总数：<em class="stu_total">0</em>

	<span id="sp_otherpagemsg"></span>
	</div>
	<div class="pager fr">
        <span>每页显示<select id="p_pageSize" class="select-pager">
        <option value="20">20</option>
        <option value="30">30</option>
        <option value="50">50</option>
        <option value="100">100</option>
        
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
    
}

PageWiget.getPageSize=function()
{
 	return  $("#p_pageSize").val();
}

PageWiget.init=function(recordcount , pageSize)
{
	var totalpage=Math.ceil(recordcount / (pageSize < 1 ? 20 : pageSize));
	
	if (totalpage == 0) {
		totalpage=1;
		}
	$("#p_totalpage").val(totalpage);
	$("#p_currpage").val(1);
	
	 var tpage =parseInt($("#p_totalpage").val());
	 var cpage =parseInt( $("#p_currpage").val());
	 console.log(tpage +":" + cpage);
	 PageWiget.render(tpage,cpage);
	 $("#p_pageSize").val(pageSize);
	 $(".stu_total").text(recordcount);
	 
}

 $(function(){
	 // 绘制
	 PageWiget.init(0,20);
 });
</script>

<!--分页结束-->
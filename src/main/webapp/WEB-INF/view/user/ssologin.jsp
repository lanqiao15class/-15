<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<script src="${ctxStatic }/js/jquery-1.9.1.min.js"></script>
<script>
  var basePath="${pageContext.request.contextPath}";
</script>
<script src="${ctxStatic }/js/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic }/js/layer/layer.js"></script>
<script src="${ctxStatic }/js/validate/jquery.validate.js"></script>
<script src="${ctxStatic }/js/validate/validate-methods.js"></script>
<script src="${ctxStatic }/js/placeholder.js"></script>
<script src="${ctxStatic }/js/select_bag/jquery.searchableSelect.js"></script>
<script src="${ctxStatic }/js/ajaxfileupload.js"></script>
<script src="${ctxStatic }/js/My97DatePicker/WdatePicker.js"></script>

<!--搜索框下拉自动填充js开始-->
<script src="${ctxStatic }/js/search_input_bag/jquery.autocompleter.js"></script>
<!--搜索框下拉自动填充js结束-->

<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/js/public.js" />
</jsp:include>

<!-- 登录 -->
<jsp:include page="/WEB-INF/view/include/loadstatic.jsp">
	<jsp:param name="type" value="script" />
	<jsp:param name="param" value="/myjs/user/login.js" />
</jsp:include>
<!doctype html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<input  type="hidden"   id="str"   value='${str}'> 
<script>
$(function(){
	var str=$("#str").val();
	if(isNull(str)){
		var backurl='<%=GlobalConstant.GlobalWebURL%>';
		var ssourl='<%=GlobalConstant.SSOURL%>';
		var appid='<%=GlobalConstant.appid%>';
		window.location.href=ssourl+"login.do?appid="+appid+"&backurl="+backurl;
	}else{
		var  data=JSON.parse(str);
		if(data.resultcode=="1"){
			console.log(str);
			$.ajax({
				type: "POST",
				url:basePath+"/sso/loginpost.do",
				data:"ssoUserId="+data.userid,
				dataType:"json",
				success: function(rdata) {
					console.log(JSON.stringify(rdata));
					if(rdata.code=="200"){
						if(data.usertype =="1")
							window.location.href = Login.basePath+"/user/home.do";
						else if(data.usertype =="0")
							window.location.href = Login.basePath+"/student/home.do";
					}
				}
			});
		}else{
			var backurl='<%=GlobalConstant.GlobalWebURL%>';
			var ssourl='<%=GlobalConstant.SSOURL%>';
			var appid='<%=GlobalConstant.appid%>';
			window.location.href=ssourl+"login.do?appid="+appid+"&backurl="+backurl;
			
		}
		
		
	}
	
	
});
</script>
</body>
</html>
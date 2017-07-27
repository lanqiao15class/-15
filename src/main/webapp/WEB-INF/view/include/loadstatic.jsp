<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.lanqiao.util.*"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	/*
	 * type = css|script|library
	 * param = xxx.css|xxx.js|xxx
	 */

	String type = request.getParameter("type");
	String param = request.getParameter("param");
	
	//
	if(type!=null && param !=null) {

		if(param.startsWith("/"))
		{
			param = param.substring(1);
		}
		
		// 其它CSS、JS文件
		if (!type.toUpperCase().equals("library".toUpperCase())) {
			otherFile(out, param);
		}
	
	}

	out.println("\r\n");
%>

<%!

	/*
	 * 其它CSS或JS文件
	 */
	private void otherFile(JspWriter out, String filePath) throws Exception {

		out.print(DynamicLoadingFileUtil.getFileLastModified(filePath));
	}%>
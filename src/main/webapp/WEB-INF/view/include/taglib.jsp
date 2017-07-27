<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.lanqiao.constant.GlobalConstant" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lq" uri="http://www.lanqiao.org/functions"%>
 
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxBase" value="${pageContext.request.contextPath}"/>
<c:set var="ctxResource" value="<%=GlobalConstant.httpUploadURL %>"/> 
<%-- <c:set var="ctxResource" value="http://127.0.0.1"/> --%>


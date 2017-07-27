<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="java.util.*" %>
<%

  Enumeration<String> en = request.getParameterNames();
   while(en.hasMoreElements())
  {
	  String key = en.nextElement();
	  String val = request.getParameter(key);
	  out.println (key +":"+ val +"<br/>");
	  
  }
  out.println ("=========================<br/>");
  en =  request.getHeaderNames();
   
  while(en.hasMoreElements())
  {
	  String key = en.nextElement();
	  String val = request.getHeader(key);
	  out.println (key +":"+ val +"<br/>");
	  
  }
  out.println ("=========================");
  
%>
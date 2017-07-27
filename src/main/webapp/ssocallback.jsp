<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.Cookie" %>
<%
//蓝桥SSO 系统使用， 实现跨域名设置cookie 
// author : chen baoji 
String uuid =  request.getParameter("lqtokenkey");
if(uuid ==null) return ;
String domain = request.getServerName();  // 得到URL 地址的域名
if(domain !=null)
{
	// 去掉域名的第1段， 如aaa.lanqiao.org ，计算后为： .lanqiao.org
	int pos = domain.indexOf(".");
	if(pos !=-1)
	{
		domain = domain.substring(pos);
	}
}
//System.out.println ("domain:" + domain);
//System.out.println ("uuid:" + uuid);
Cookie cookie = new Cookie("lqtokenkey", null);
cookie.setDomain(domain);
cookie.setPath("/");
cookie.setMaxAge(-1); 
// 浏览器关闭后， 自动销毁cookie , 安全一些。
cookie.setValue(uuid);
response.addCookie(cookie);
%>
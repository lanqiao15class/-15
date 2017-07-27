package com.lanqiao.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lanqiao.common.Functions;
import com.lanqiao.common.MustLoginAndStudent;
import com.lanqiao.common.MustLoginAndTeacher;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.model.SysFunction;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2, ModelAndView model)
			throws Exception {

		String s = (String) request.getAttribute("BeforemyFunction");
		System.out.println("post myFunction :" + s);
		request.setAttribute("myFunction", s);
	}

	private String getMyFunction(HttpServletRequest request) {
		ArrayList<Integer> alret = new ArrayList<Integer>();
		SessionUser user = WebUtil.getLoginUser(request);
		if (user != null && user.getSysfunctions() != null) {
			for (SysFunction sy : user.getSysfunctions()) {
				if (sy.getIsdir() == 0) {
					alret.add(sy.getFunctionid());

				}

			}

		}

		StringBuffer ssb = new StringBuffer(1024);

		for (int i = 0; i < alret.size(); i++) {
			if (i != 0) {
				ssb.append(",");
			}

			ssb.append(alret.get(i) + "");

		}

		return ssb.toString();

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());

		//	        System.out.println ("handler:"+handler.getClass().getName());
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;

			System.err.println("=======================" + requestUri + "===========================");
			System.err.println("class:" + hm.getBean().getClass().getName());
			System.err.println("method:" + hm.getMethod().getName());
			System.err.println("=======================" + requestUri + "===========================");

			SessionUser user = (SessionUser) request.getSession().getAttribute("loginuser");
			MustLoginAndStudent stmc = hm.getMethodAnnotation(MustLoginAndStudent.class);
			MustLoginAndTeacher techmc = hm.getMethodAnnotation(MustLoginAndTeacher.class);

			if (stmc != null) {
				//验证学生账户.
				if (user == null || user.ifStudent() == false) {
					returnNoLogin(request, response);
					return false;
				}
			}

			if (techmc != null) {
				//验证老师账户必须登录.
				if (user == null || user.ifTeacher() == false) {
					returnNoLogin(request, response);
					return false;
				}
			}

			//是老师账户, 并且登录了
			Functions fc = hm.getMethodAnnotation(Functions.class);
			if (fc != null) {
				if (user == null || user.ifTeacher() == false) {
					returnNoLogin(request, response);
					return false;
				}
			}

			if (fc != null) {
				//检测登录账户是否含有这个方法指定的功能id
				int[] fids = fc.value();
				if (hasFunction(user.getSysfunctions(), fids) == false) {
					returnNoRight(request, response);
					return false;
				}
			}

		}

		String s = getMyFunction(request);
		System.out.println("before request myFunction :" + s);
		request.setAttribute("BeforemyFunction", s);

		//  System.out.println("==================================================");
		return true;
	}

	/**
	 * 判断 methodfunc 中的数是否在 userfunc 存在, 至少1个
	 * @param userfunc
	 * @param methodfunc
	 * @return
	 */
	public static boolean hasFunction(List<SysFunction> lf, int[] methodfunc) {
		boolean b = false;
		if (lf == null)
			return b;
		for (int f : methodfunc) {
			for (SysFunction uc : lf) {
				if (f == uc.getFunctionid()) {
					return true;
				}
			}
		}

		return b;

	}

	private void returnNoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (isAjax(request)) {
			// 如果请求是ajax 请求. 
			response.addHeader("code", "201");
			//String s="";
			//response.addHeader("msg", "No Login . please login again");
		} else {
			response.sendRedirect(GlobalConstant.SSOURL + "login.do?appid=" + GlobalConstant.appid + "&backurl="
					+ java.net.URLEncoder.encode(GlobalConstant.GlobalWebURL,"UTF-8"));
		}

	}

	private void returnNoRight(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (isAjax(request)) {
			// 如果请求是ajax 请求. 
			response.addHeader("code", "202");
		} else {
			response.setContentType("application/javascript;charset=UTF-8");

			PrintWriter out = response.getWriter();
			out.println("alert('无权访问...');");
			//response.sendRedirect(request.getContextPath()+"/login.do");				
		}

	}

	/**
	* 判断是否ajax请求
	* @param request
	* @param response
	* @return
	*/
	private static boolean isAjax(HttpServletRequest request) {
		boolean isAjax = false;
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
			// ajax请求
			isAjax = true;
		}
		return isAjax;
	}
}

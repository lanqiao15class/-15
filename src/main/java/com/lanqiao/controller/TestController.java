package com.lanqiao.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.model.SysDeparment;
import com.lanqiao.model.SysFunction;
import com.lanqiao.service.DepartmentService;
import com.lanqiao.service.LogService;
import com.lanqiao.service.TestService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.Code2Util;
import com.lanqiao.util.JsonUtil;

@Controller
public class TestController {

	private final static Logger log = LogManager.getLogger(TestController.class);

	@Resource
	UserService userservice;

	@Resource
	DepartmentService dservice;

	@Resource(name = "logservice")
	LogService logservice;

	

	@Resource
	TestService service;
	
	
	
	@RequestMapping("/codelist.do")
	public void testRedis(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	 
		OutputStream pout = response.getOutputStream();
		String str="http://sso.lanqiao.org/listcookie.jsp";
		Code2Util.Encode(pout, str, 300, 300);
		pout.flush();
	}
	
	@RequestMapping("/codeset.do")
	public void codeset(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{	 
		OutputStream pout = response.getOutputStream();
		String str="http://sso.lanqiao.org/setcookie.jsp";
		Code2Util.Encode(pout, str, 300, 300);
		pout.flush();
	}
	
	
	@RequestMapping("/aop.do")
	public void aop(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysDeparment dtemp = new SysDeparment();
		dtemp.setDepname("测试部门");
		dtemp.setIsvalid(1);
		dtemp.setParentid(0);

		dservice.addDepartment(dtemp);
		dtemp.setDepid(58);
		dservice.upDepartment(dtemp);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("ok");
		if (logservice == null) {
			System.err.println("logservice is null");
		} else
			System.err.println("logservice is not null");

	}

	public void returnJsonResultError(HttpServletResponse response, String err) {

		try {
			HashMap retmp = new HashMap();
			retmp.put("code", -1);
			retmp.put("message", err);

			response.setContentType("text/html;charset=UTF-8");
			String s = JsonUtil.objectjson(retmp);
			log.info(s);
			response.getOutputStream().write(s.getBytes("utf-8"));
			response.getOutputStream().flush();

		} catch (Exception se) {

		}

	}

	/*
		@RequestMapping("/file/upload.do")
		public void uploadIdCardBackImg(@RequestParam MultipartFile backImg,HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		//	PrintWriter out = response.getWriter();

			String requestType = request.getContentType();
			if (requestType != null
					&& requestType.indexOf("multipart/form-data") != -1)
			{

				String clen = request.getHeader("Content-Length");
				log.info("Content-Length:" + clen);
				int nlen = Integer.parseInt(clen);
				if (nlen > 1024 * 100000)
				{
					log.info("上传文件太大了");
					returnJsonResultError(response, "上传文件太大了。");
					return ;
				}
				if (nlen <= 0)
				{
					log.info("上传文件不能为空");
					returnJsonResultError(response, "上传文件不能为空");
					return ;
				}
				log.info("==================begin upload ==============");

	//			
	//			MultipartFile f = mrequest.getFile("fileToUpload_name");
				log.info("getOriginalFilename::"+backImg.getOriginalFilename());
				
	//			
				ArrayList alist= new ArrayList();
				alist.add(backImg);
				List<String> slist = FileUtil.uploadFile(request,
						alist, "/face",
						new String[] { "png", "bmp", "gif", "jpg" });

				for (String s : slist)
				{
					log.info("-----" + s);
				}
				HashMap mp = new HashMap();
				mp.put("code", 201);
				
				if(slist !=null && slist.size() >0)
				{	mp.put("url", slist.get(0));
					mp.put("code", 200);
				}
				
				String strout = JsonUtil.objectjson(mp);
				
				//response.setContentType("text/html; charset=utf-8");
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println (strout);
				
				
				
				// out.print("ok" + new java.util.Date().toLocaleString());
			}

		//	return ;
		}
	*/

	/**
	 * 商用后, 关闭这个接口. 
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	//@RequestMapping("/function/setfunction.do")
	public void setfunction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		String funcid = request.getParameter("funcid");
		if (funcid == null)
			funcid = "";
		PrintWriter out = response.getWriter();
		SessionUser user = WebUtil.getLoginUser(request);
		if (user == null) {
			out.println("当前没登录");
			return;
		}
		user.setSysfunctions(null);

		funcid = funcid.trim();
		String stra[] = funcid.split(",");

		List<SysFunction> syslist = userservice.getAllFunction();
		List<SysFunction> reallist = new ArrayList<SysFunction>();
		for (String s : stra) {
			if (StringUtils.isEmpty(s))
				continue;
			int n = Integer.parseInt(s);
			for (SysFunction fun : syslist) {
				//忽略目录
				if (fun.getIsdir() == 1)
					continue;
				if (n == fun.getFunctionid()) {
					reallist.add(fun);
				}

			}
		}

		user.setSysfunctions(reallist);

		StringBuffer ssb = new StringBuffer(1024);
		ssb.append("当前用户具有权限:<br/>");

		for (SysFunction func : reallist) {
			ssb.append(func.getFunctionid() + ":" + func.getFunctionname());
			ssb.append("<br/>");
		}

		out.println(ssb);
	}

	@RequestMapping("/slickdata.do")
	public void slickdata(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList al = new ArrayList();
		for (int i = 0; i < 50; i++) {
			HashMap mp = new HashMap();
			mp.put("indexno", i);
			mp.put("realname", "拉拉" + i);
			mp.put("coursetype", "Java");
			mp.put("unicode", "青岛大学");
			mp.put("status", "在读");
			mp.put("nstatus", 1);
			mp.put("teacher", "老师陈");
			mp.put("updatetime", "2015-01-01");
			mp.put("ismain", 1);
			mp.put("userid", i);
			mp.put("ifmy", 1);
			al.add(mp);

		}

		String strout = JsonUtil.objectjson(al);
		System.out.println(strout);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(strout);

	}

	@RequestMapping("/ajaxtest.do")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Thread.sleep(2000);

		PrintWriter out = response.getWriter();
		out.print("ok" + new java.util.Date().toLocaleString());
		return null;
	}

	@RequestMapping("/jsrender.do")
	public ModelAndView jsrender(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String s = loadFileByPath("/templates/jsrender.html");

		PrintWriter out = response.getWriter();
		out.print(s);

		ArrayList aluser = new ArrayList();

		HashMap mp = new HashMap();
		mp.put("ulist", aluser);
		mp.put("count", 10);
		mp.put("code", "32432432");
		String strjson = JsonUtil.objectjson(mp);
		System.out.println(strjson);
		out.println("<script>");
		out.print("var data= " + strjson);
		out.println("</script>");

		return null;
	}

	/**
	 * 载入模板文件为字符串.
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private String loadFileByPath(String path) throws Exception {
		ClassPathResource cp = new ClassPathResource(path);

		InputStream in = cp.getInputStream();

		byte bb[] = new byte[in.available()];
		in.read(bb);
		in.close();
		return new String(bb, "UTF-8");

	}

	@RequestMapping("/adialog.do")
	public ModelAndView adialog(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap user = new HashMap();
		user.put("username", "陈");
		user.put("userpass", "陈3333");

		return new ModelAndView("a_dialog.jsp", user);
	}

}

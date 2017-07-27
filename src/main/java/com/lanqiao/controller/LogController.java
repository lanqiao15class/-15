/*
 * File name:          LogController.java
 * Copyright@Handkoo (China)
 * Editor:           JDK1.6.32
 */
package com.lanqiao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanqiao.common.Functions;
import com.lanqiao.common.WebUtil;
import com.lanqiao.model.SysFunction;
import com.lanqiao.service.LogService;

/**
 * TODO: File comments
 * <p>
 * <p>
 * Author:           卢锡文
 * <p>
 * Date:           2017年5月11日
 * <p>
 * Time:           上午11:17:45
 * <p>
 * Director:        卢锡文
 * <p>
 * <p>
 */
@Controller
@RequestMapping("/log")
public class LogController {
	private static final Logger logger = LogManager.getLogger(LogController.class);
	@Resource(name = "logservice")
	LogService server;

	@RequestMapping("/getlog.do")
	public ModelAndView getAllLog(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("/WEB-INF/view/log/log.jsp");
		return mv;
	}

	@Functions({ 98, 99 })
	@RequestMapping("/showlog.do")
	public @ResponseBody Map<String, Object> showlog(ModelAndView mv, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(request.getParameter("currpage"))) {
				Integer currpage = Integer.parseInt(request.getParameter("currpage"));
				Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
				paramMap.put("currpage", (currpage - 1) * pageSize);
				paramMap.put("pageSize", pageSize);
			}
			if (StringUtils.isNotBlank(request.getParameter("optype"))
					&& !StringUtils.equals(request.getParameter("optype"), "-1")) {
				String optype = request.getParameter("optype");
				paramMap.put("optype", optype);
			}
			if (StringUtils.isNotBlank(request.getParameter("starttime"))) {
				String starttime = request.getParameter("starttime");
				paramMap.put("parm", " and sys_log.create_date>'" + starttime + "'  ");
			}
			if (StringUtils.isNotBlank(request.getParameter("endtime"))) {
				String endtime = request.getParameter("endtime");
				paramMap.put("parm1", " and sys_log.create_date<'" + endtime + " 59:59:59'  ");
			}
			if (StringUtils.isNotBlank(request.getParameter("userid"))) {
				String userid = request.getParameter("userid");
				paramMap.put("userid", userid);
			} else {
				List<SysFunction> sysfunctions = WebUtil.getLoginUser(request).getSysfunctions();
				boolean flag = false;
				for (SysFunction function : sysfunctions) {
					if (function.getFunctionid() == 99) {
						flag = true;
						break;
					}
				}
				if (flag) {

				} else {
					paramMap.put("userid", WebUtil.getLoginUser(request).getUserId());

				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		List<Map<String, Object>> getLogPage = server.getLogPage(paramMap);
		resultMap.put("getLogPage", getLogPage);
		//总记录数
		int ncount = 0;
		ncount = server.getLogCount(paramMap);
		resultMap.put("recordcount", ncount);
		return resultMap;
	}
}

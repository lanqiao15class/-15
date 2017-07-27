/*
 * File name:          JobVacancyController.java
 * Copyright@Handkoo (China)
 * Editor:           JDK1.6.32
 */
package com.lanqiao.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.util.HttpClientUtil;
import com.lanqiao.util.JsonUtil;

/**
 * TODO: File comments
 * <p>
 * <p>
 * Author:           卢锡文
 * <p>
 * Date:           2017年4月11日
 * <p>
 * Time:           下午4:08:50
 * <p>
 * Director:        卢锡文
 * <p>
 * <p>
 */
@Controller
@RequestMapping("/jobVacancy")
public class JobVacancyController {
	private static final Logger logger = LogManager.getLogger(JobVacancyController.class);

	/**
	 * 招聘信息列表
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("/showJob.do")
	public ModelAndView showJob(ModelAndView mv, HttpServletRequest request) {
		SessionUser user = WebUtil.getLoginUser(request);
		System.out
				.println("stu_id      " + user.getUserId().toString() + "   status==" + user.getStudent().getStatus());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (user.getStudent().getStatus() != null && (user.getStudent().getStatus() >= 106)) {
			String baseUrl = GlobalConstant.LanqiaoZYXTURL + "?c=api&m=list";
			logger.info("baseUrl    " + baseUrl);

			paramMap.put("stu_id", user.getUserId().toString());
			String str = HttpClientUtil.postRequestResult(baseUrl, paramMap);
			logger.info("str    " + str);
			mv.addObject("JobVacancyList", str);
		} else {
			mv.addObject("JobVacancyList", "1");

		}

		mv.setViewName("/WEB-INF/view/job/JobVacancy.jsp");
		return mv;
	}

	/**
	 * 招聘报名
	 * @param jobid
	 * @param request
	 * @param response
	 */
	@RequestMapping("/signUp.do")
	public void signUp(String jobid, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String baseUrl = GlobalConstant.LanqiaoZYXTURL + "?c=api&m=apply";
		logger.info("baseUrl    " + baseUrl);
		SessionUser user = WebUtil.getLoginUser(request);
		paramMap.put("stu_id", user.getUserId().toString());
		paramMap.put("position_id", jobid);
		String str = HttpClientUtil.postRequestResult(baseUrl, paramMap);
		logger.info("str    " + str);
		JsonUtil.write(response, str);
	}

	/**
	 * 招聘信息ajax
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showJob1.do")
	public void showJob1(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String baseUrl = GlobalConstant.LanqiaoZYXTURL + "?c=api&m=list";
		logger.info("baseUrl    " + baseUrl);
		SessionUser user = WebUtil.getLoginUser(request);
		paramMap.put("stu_id", user.getUserId().toString());
		String str = HttpClientUtil.postRequestResult(baseUrl, paramMap);
		logger.info("str    " + str);
		JsonUtil.write(response, str);
	}

	/**
	 * 招聘信息详细信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showJobDetails.do")
	public void showJobDetails(String jobid, HttpServletResponse response) {
		System.out.println("                " + jobid);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String baseUrl = GlobalConstant.LanqiaoZYXTURL + "?c=api&m=detail";
		logger.info("baseUrl    " + baseUrl);
		paramMap.put("position_id", jobid);
		String str = HttpClientUtil.postRequestResult(baseUrl, paramMap);
		logger.info("str    " + str);
		JsonUtil.write(response, str);
	}
}

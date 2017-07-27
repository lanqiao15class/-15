/*
 * File name:          DepartmentController.java
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
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.model.DepartmentTree;
import com.lanqiao.model.SysDeparment;
import com.lanqiao.service.DepartmentService;
import com.lanqiao.util.JsonUtil;

/**
 * TODO: File comments
 * <p>
 * <p>
 * Author:           卢锡文
 * <p>
 * Date:           2017年3月31日
 * <p>
 * Time:           上午10:06:30
 * <p>
 * Director:        卢锡文
 * <p>
 * <p>
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {
	private static final Logger logger = LogManager.getLogger(DepartmentController.class);

	@Resource(name = "departmentService")
	DepartmentService ds;

	/**
	 * 获取登录用户所对用的部门树
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/showDpt.do")
	@Functions({ 91 })
	public ModelAndView showDepartment(HttpServletRequest request, ModelAndView mv) throws Exception {
		logger.info("进入部门查询方法" + WebUtil.getLoginUser(request));
		SessionUser user = WebUtil.getLoginUser(request);
		List<DepartmentTree> trees = ds.getDepartmentByid(user.getDeparment());

		mv.addObject("tree", JsonUtil.objectjson(trees).replaceAll("depid", "id").replaceAll("parentid", "pId")
				.replaceAll("depname", "name"));//绑定类型
		mv.setViewName("/WEB-INF/view/permission/department.jsp");
		return mv;

	}

	/**
	 * 根据部门查找用户
	 * @return
	 */
	@Functions({ 91 })
	@RequestMapping("/getUserByDepartment")
	public @ResponseBody Map<String, Object> getUserByDepartment(String ids, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(request.getParameter("currpage"))) {
				Integer currpage = Integer.parseInt(request.getParameter("currpage"));
				Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
				paramMap.put("currpage", (currpage - 1) * pageSize);
				paramMap.put("pageSize", pageSize);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		paramMap.put("ids", ids);
		List<Map<String, Object>> userByDepartment = ds.getUserByDepartment(paramMap);
		resultMap.put("userByDepartment", userByDepartment);
		//总记录数
		int ncount = 0;
		if (ds.getUserByDepartmentCount(paramMap) != null) {
			ncount = ds.getUserByDepartmentCount(paramMap);
		}

		resultMap.put("recordcount", ncount);
		logger.info("根据部门查找用户" + ids);
		return resultMap;
	}

	/**
	 * 添加或者修改部门
	 * @param deparment
	 * 
	 * @return
	 */
	@RequestMapping("/addDepartment.do")
	@Functions({ 93 })
	public @ResponseBody Map<String, Object> addDepartment(SysDeparment deparment) throws Exception {
		logger.info(deparment.getDepname() + deparment.getParentid() + deparment.getIsvalid());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", "200");
		if (deparment.getDepid() == null) {
			//添加
			int i = ds.addDepartment(deparment);
			if (i <= 0) {
				resultMap.put("code", "101");

			}
			resultMap.put("depid", deparment.getDepid());
		} else {
			// 修改
			int i = ds.upDepartment(deparment);
			if (i <= 0) {
				resultMap.put("code", "102");
			}
		}

		return resultMap;

	}

	/**
	 * 删除部门
	 * @param id
	 * @return
	 */

	@RequestMapping("/delDepartment.do")
	@Functions({ 94 })
	public @ResponseBody Map<String, Object> delDepartment(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", "200");
		if (StringUtils.isNotBlank(id)) {
			Integer id1 = Integer.valueOf(id);
			int i = ds.delDepartment(id1);
			if (i > 0) {
			} else {
				resultMap.put("code", "101");
			}
		} else {
			resultMap.put("code", "101");
		}

		return resultMap;
	}
}

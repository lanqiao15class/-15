/*
 * File name:          AccountManager.java
 * Copyright@Handkoo (China)
 * Editor:           JDK1.6.32
 */
package com.lanqiao.controller;

import java.util.Date;
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

import com.google.gson.Gson;
import com.lanqiao.common.Functions;
import com.lanqiao.common.SSOResult;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.model.DepartmentTree;
import com.lanqiao.model.SysRole;
import com.lanqiao.model.User;
import com.lanqiao.service.AccountManagerService;
import com.lanqiao.service.DepartmentService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.HttpClientUtil;
import com.lanqiao.util.JsonUtil;

/**
 * TODO: File comments
 * <p>
 * <p>
 * Author:           卢锡文
 * <p>
 * Date:           2017年4月10日
 * <p>
 * Time:           上午10:03:13
 * <p>
 * Director:        卢锡文
 * <p>
 * <p>
 */
@Controller
@RequestMapping("/accountManager")
public class AccountManagerController {
	private static final Logger logger = LogManager.getLogger(AccountManagerController.class);

	@Resource(name = "departmentService")
	DepartmentService ds;

	@Resource(name = "accountManagerService")
	AccountManagerService as;

	@RequestMapping("/showAct.do")
	@Functions({ 91 })
	public ModelAndView showAct(HttpServletRequest request, ModelAndView mv) throws Exception {
		long time = System.currentTimeMillis();
		SessionUser user = WebUtil.getLoginUser(request);
		//查询部门树
		List<DepartmentTree> trees = ds.getDepartmentByid(user.getDeparment());
		mv.addObject("tree", JsonUtil.objectjson(trees).replaceAll("depid", "id").replaceAll("parentid", "pId")
				.replaceAll("depname", "name"));//绑定类型
		mv.setViewName("/WEB-INF/view/permission/accountManager.jsp");
		long time1 = System.currentTimeMillis();

		System.out.println("                  1111111111111           " + (time1 - time));
		return mv;

	}

	/**
	 * 查询用户对应的角色和功能
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUserRole.do")
	public @ResponseBody Map<String, Object> getUserRole(HttpServletRequest request) {
		SessionUser user = WebUtil.getLoginUser(request);
		//查询该用户的角色
		Map<String, Object> roles = as.getRoles(user.getUserId());
		return roles;
	}

	/**
	 * 根据部门查找用户和角色
	 * @return
	 */
	@RequestMapping("/getUserByDepartment.do")
	@Functions({ 91 })
	public @ResponseBody Map<String, Object> getUserByDepartment(String ids, String status, String roleId,
			HttpServletRequest request) throws Exception {
		logger.info(" ids       " + ids + "             roleId             " + roleId);
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
		if (StringUtils.equals(status, "0")) {
			paramMap.put("ids", ids);
			List<Map<String, Object>> userByDepartment = as.getUserByDepartment(paramMap);
			resultMap.put("userByDepartment", userByDepartment);
			//总记录数
			int ncount = 0;
			if (ds.getUserByDepartmentCount(paramMap) != null) {
				ncount = ds.getUserByDepartmentCount(paramMap);
			}

			resultMap.put("recordcount", ncount);
			logger.info("根据部门查找用户" + ids);
		} else if (StringUtils.equals(status, "1")) {
			logger.info("根据角色查找用户" + roleId);
			paramMap.put("roleId", roleId);
			List<Map<String, Object>> userByDepartment = as.getUserByRole(paramMap);
			resultMap.put("userByDepartment", userByDepartment);
			//总记录数
			int ncount = 0;
			if (ds.getUserByDepartmentCount(paramMap) != null) {
				ncount = as.getUserByRoleCount(paramMap);
			}

			resultMap.put("recordcount", ncount);
		}

		return resultMap;
	}

	/**
	 * 根据用户id修改用户密码为初始密码
	 * @param id
	 * @return
	 */
	@RequestMapping("/getUserBypassword")
	public @ResponseBody Map<String, Object> getUserBypassword(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String salt = CommonUtil.randomSalt();
		String pwd = CommonUtil.md5Pwd("lanqiao2017", salt);
		paramMap.put("userId", id);
		paramMap.put("salt", salt);
		paramMap.put("pwd", pwd);
		Integer resetPassword = as.resetPassword(paramMap);
		resultMap.put("code", 200);
		if (resetPassword == null) {
			resultMap.put("code", 101);
		}
		return resultMap;
	}

	/**
	 * 根据用户id修改账户是否有效
	 * @param id
	 * @return
	 */
	@RequestMapping("/userIsvalid")
	public @ResponseBody Map<String, Object> userIsvalid(String id, String isvalid) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String salt = CommonUtil.randomSalt();
		String pwd = CommonUtil.md5Pwd("lanqiao2017", salt);
		User user = new User();
		user.setUserId(Integer.valueOf(id));
		user.setIsvalid(Integer.valueOf(isvalid));
		Integer resetPassword = as.userIsvalid(user);
		resultMap.put("code", 200);
		if (resetPassword == null) {
			resultMap.put("code", 101);
		}
		return resultMap;
	}

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@Functions({ 95 })
	@RequestMapping("/addUser")
	public @ResponseBody Map<String, Object> addUser(User user, String departmentId, String[] roleId) throws Exception {
		logger.info(user.getUserId() + "           ssss       " + user.getLoginEmail() + user.getRealName() + "  "
				+ departmentId + "  " + roleId);
		Map<String, Object> resultmap = new HashMap<String, Object>();
		if (user.getUserId() != null) {
			//查询手机号和邮箱是否存在
			int i = as.getuserByuserTelOrEmail(user);
			if (i == 200) {
				user.setEmail(user.getLoginEmail());
				user.setTel(user.getLoginTel());
				boolean flag = as.updateUser(user, departmentId, roleId);
				if (flag) {
					resultmap.put("code", "400");
				} else {
					resultmap.put("code", "402");
				}
			} else {
				resultmap.put("code", i);
			}

		} else {
			int i = as.getuserByuserTelOrEmail(user);
			if (i == 200) {
				//查询手机号和邮箱是否存在
				//user  对象添加密码
				String salt = CommonUtil.randomSalt();
				String pwd = CommonUtil.md5Pwd("lanqiao2017", salt);
				user.setPassword(pwd);
				user.setSalt(salt);
				user.setIsvalid(1);
				user.setType("1");
				user.setEmail(user.getLoginEmail());
				user.setTel(user.getLoginTel());
				//调用 sso注册
				/*realname:真实姓名(当memebertype=1 时，必须填写)
				email:注册绑定的邮箱
				phone：注册绑定的手机号
				password:密码 （不能为空）
				membertype:账户类型1：蓝桥老师， 0：互联网用户（不能为空）
				appid: 应用的标识（不能为空）*/
				//调用sso   查看用户登录信息
				Map<String, Object> paramMap = new HashMap<String, Object>();
				String baseUrl = GlobalConstant.SSOURL;
				paramMap.put("email", user.getLoginEmail());
				paramMap.put("phone", user.getLoginTel());
				paramMap.put("password", "lanqiao2017");
				paramMap.put("realname", user.getRealName());
				paramMap.put("usertype", "1");
				paramMap.put("appid", GlobalConstant.appid);
				String str = HttpClientUtil.postRequestResult(baseUrl + "regist.do", paramMap);
				System.out.println(str);
				Gson gson = new Gson();
				SSOResult result = gson.fromJson(str, SSOResult.class);
				System.out.println(result.getResultcode() + "       2222  " + result.getResultmsg());
				if (result.getResultcode() == 1 || result.getResultcode() == 100) {
					user.setSsoUserid(Integer.valueOf(result.getUserid()));

					boolean flag = as.addUser(user, departmentId, roleId);

					if (flag) {
						resultmap.put("code", "200");
					} else {
						resultmap.put("code", "101");
					}
				} else {
					resultmap.put("code", "101");
				}
			} else {
				resultmap.put("code", i);
			}

		}
		return resultmap;

	}

	/**
	 * 根据用户id查询用户和角色
	 * @param user
	 * @return
	 */
	@RequestMapping("/selUserAndRoleByUserId")
	public @ResponseBody Map<String, Object> selUserAndRoleByUserId(String id) throws Exception {
		logger.info("           ssss       " + id);
		Map<String, Object> map = as.selUserAndRoleByUserId(id);
		return map;
	}

	/**
	 * 查询登录用户所对应的功能
	 * @param user
	 * @return
	 */
	@RequestMapping("/getFunctionByLoginUser")
	public @ResponseBody Map<String, Object> getFunctionByLoginUser(HttpServletRequest request) throws Exception {
		SessionUser user = WebUtil.getLoginUser(request);
		Map<String, Object> map = as.selUserAndRoleByUserId(user.getUserId().toString());
		return map;
	}

	/**
	 * 添加角色
	 * @param user
	 * @return
	 */
	@Functions({ 97 })
	@RequestMapping("/addRole")
	public @ResponseBody Map<String, Object> addRole(String roleId, String roleName, String functionid,
			HttpServletRequest request) throws Exception {
		if (StringUtils.isNotBlank(roleId)) {
			SysRole role = new SysRole();
			role.setName(roleName);
			role.setId(roleId);
			Map<String, Object> map = as.updateRole(role, functionid);
			return map;
			//修改
		} else {
			SessionUser user = WebUtil.getLoginUser(request);
			//添加角色
			SysRole role = new SysRole();
			role.setCreateDate(new Date());
			role.setDelFlag("0");
			role.setName(roleName);
			role.setUserId(user.getUserId());
			role.setId(String.valueOf(System.currentTimeMillis()));
			Map<String, Object> map = as.addRole(role, functionid);
			return map;
		}

	}

	/**
	 * 查看角色所拥有的的权限
	 * @param user
	 * @return
	 */
	@RequestMapping("/getFunctionByRoleId")
	public @ResponseBody Map<String, Object> getFunctionByRoleId(String roleId) throws Exception {
		Map<String, Object> functionByRoleId = as.getFunctionByRoleId(roleId);
		return functionByRoleId;

	}
}
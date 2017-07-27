package com.lanqiao.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.WebUtil;
import com.lanqiao.model.Email;
import com.lanqiao.service.EmailService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.JsonUtil;

/**
 * 
* 项目名称:lqzp2
* 类名称： ResetPwdController
* 类描述:重置密码
* 创建人: ZhouZhiHua
* 创建时间:2016年12月8日 下午2:10:40 
* 修改人： 
* 修改时间：2016年12月8日 下午2:10:40
* 修改备注:
 */
@Controller
public class ResetPwdController {
	private static final Logger logger = LogManager.getLogger(ResetPwdController.class);
	
	@Resource
	UserService userService;
	
	@Resource
	EmailService emailService;
	
	/**
	 * 
	* @Description:重置密码
	* @param mv
	* @return 
	* @return String 
	* @author ZhouZhiHua
	* @createTime 2016年12月8日 下午2:06:56
	 */
	@RequestMapping("/user/toResetPwd.do")
	public String toResetPwd(ModelAndView mv){
		return "/WEB-INF/view/user/resetPwd_check.jsp";
	}
	
	/**
	 * 
	* @Description:登录检查用户是否存在
	* @param userName
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月8日 下午2:47:05
	 */
	@RequestMapping("/user/userNameCheck.do")
	public void userNameCheck(String userName, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			map = userService.userNameCheck(userName);
			if(map != null) {
				map.put("code", 200);
			} else {
				map = new HashMap<String,Object>();
				map.put("code", 201);
			}
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}
	
	/**
	 * 检查验证码是否正确
	 * @param jsonScoreData
	 * @param response
	 */
	@RequestMapping("/user/validateCodeCheck.do")
	public void validateCodeCheck(String validateCode,String type,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			map.put("isRightCode", userService.validateCodeCheck(validateCode,type,request));
			map.put("code", 200);
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}
	
	/**
	 * 
	* @Description:跳转到修改密码页面
	* @param userId
	* @param loginTel
	* @param loginEmail
	* @param userName
	* @param mv
	* @param request
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年12月8日 下午3:17:06
	 */
	@RequestMapping("/user/toUpdatePwd.do")
	public ModelAndView toUpdatePwd(String userId,String loginTel,String loginEmail,String userName,ModelAndView mv,HttpServletRequest request) {
		
		
		try {
			mv.addObject("realTel", loginTel);
			if(StringUtils.isNotEmpty(loginTel)) {//是否为空
				loginTel = CommonUtil.hideMobile(loginTel);//添加隐藏码
			}
			mv.addObject("userId", userId);
			if(userName.contains("@")) {//邮箱登录
				mv.addObject("type", "1");
				mv.addObject("loginEmail", userName);
					
			} else {//蓝桥帐号，手机号登录
				mv.addObject("type", "0");
				mv.addObject("loginTel", userName);
				
			}
			mv.setViewName("/WEB-INF/view/user/resetPwd.jsp");
		} catch(Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}
	
	
	/**
	 * 
	* @Description: 修改密码
	* @param userId
	* @param pwd
	* @param mv
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月8日 下午4:12:34
	 */
	@RequestMapping(value="/user/updatePwd.do",method=RequestMethod.POST)
	public void updatePwd(Integer userId,String pwd,ModelAndView mv,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			userService.updatePwd(userId,pwd);
			map.put("code", 200);
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}
}

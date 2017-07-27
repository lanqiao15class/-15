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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.model.Email;
import com.lanqiao.model.User;
import com.lanqiao.service.EmailService;
import com.lanqiao.service.SmsService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.JsonUtil;
import com.lanqiao.util.RandomValidateCode;

@Controller
public class AccountController {

	private static final Logger logger = LogManager.getLogger(AccountController.class);

	@Resource
	UserService userService;

	@Resource
	SmsService smsService;

	@Resource
	EmailService emailService;

	/**
	 * 
	 *@description:跳转到账号绑定（账号安全，密码修改）界面
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 * @param session 
	 *@2016年11月23日上午11:00:09
	 *
	 */
	@RequestMapping("/account/accountPage.do")
	public ModelAndView accountPage(ModelAndView mv, HttpServletRequest request) {

		SessionUser sessionUser = WebUtil.getLoginUser(request);

		String loginEmail = sessionUser.getLoginEmail();
		String loginTel = sessionUser.getLoginTel();
		if (StringUtils.isNotEmpty(loginTel)) {
			mv.addObject("loginTel", CommonUtil.hideMobile(loginTel));//隐藏部分电话号码
		}
		if (StringUtils.isNotEmpty(loginEmail)) {
			if (loginEmail.indexOf("@") > 4) {
				mv.addObject("loginEmail", CommonUtil.hideEmail(loginEmail));
			} else {
				mv.addObject("loginEmail", loginEmail);
			}

		}
		mv.setViewName("/WEB-INF/view/account/account_security.jsp");
		return mv;
	}

	/**
	 * 
	 *@description:更改绑定，选择验证方式：手机、邮箱
	 *@param type
	 *@param tipType
	 *@param request
	 *@param model
	 *@return
	 *@return String 
	 *@author:ZhuDiaoHua
	 *@2016年11月23日下午3:14:54
	 *
	 */
	@RequestMapping("/account/toChooseType/{type}/{tipType}.do")
	public String toChooseType(@PathVariable(value = "type") String type,
			@PathVariable(value = "tipType") String tipType, HttpServletRequest request, Model model) {
		SessionUser user = WebUtil.getLoginUser(request);

		model.addAttribute("type", type);
		model.addAttribute("tipType", tipType);
		if (StringUtils.isNotEmpty(user.getLoginTel())) {
			model.addAttribute("loginTel", CommonUtil.hideMobile(user.getLoginTel()));
		}
		if (StringUtils.isNotEmpty(user.getLoginEmail())) {
			model.addAttribute("loginEmail", CommonUtil.hideEmail(user.getLoginEmail()));
		}
		model.addAttribute("user", user);
		return "/WEB-INF/view/account/choose_way_bind.jsp";

	}

	/**
	 * 
	 *@description:切换到  已绑定的邮箱或者手机号 确认页面
	 *@param paramMap
	 *@param session
	 *@param model
	 *@return String 
	 *@author:ZhuDiaoHua
	 *@2016年11月23日下午4:54:21
	 *
	 */
	@RequestMapping("/account/toCheckAcc.do")
	public String toCheckAcc(@RequestParam Map<String, Object> paramMap, HttpServletRequest request, Model model) {
		SessionUser user = WebUtil.getLoginUser(request);
		String way = (String) paramMap.get("way");
		model.addAllAttributes(paramMap);
		if ("0".equals(way)) {//验证原手机号
			model.addAttribute("tel", user.getLoginTel());
			model.addAttribute("loginTel", CommonUtil.hideMobile(user.getLoginTel()));
			return "/WEB-INF/view/account/accMobileCheck.jsp";
		} else {//验证原邮箱
			model.addAttribute("email", user.getLoginEmail());
			model.addAttribute("loginEmail", CommonUtil.hideEmail(user.getLoginEmail()));
			return "/WEB-INF/view/account/accEmailCheck.jsp";
		}
	}

	/**
	 * 
	 *@description:验证邮箱验证码是否正确
	 *@param request
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月23日下午5:34:15
	 *
	 */
	@RequestMapping(value = "/account/bindCodeValidate.do")
	public void emailValidate(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//1.sesison与填写的验证码进行比较
			String bindCode = request.getParameter("bindCode");
			String codeType = request.getParameter("codeType");//是何种验证码:1手机  2邮箱
			String verificationCode;
			if (codeType != null && "1".equals(codeType)) {
				verificationCode = (String) request.getSession().getAttribute("smsVerificationCode");
			} else {
				verificationCode = (String) request.getSession().getAttribute("emailVerificationCode");
			}
			if (verificationCode != null) {
				if (verificationCode.equals(bindCode)) {
					result.put("message", false);//通过
				} else {
					result.put("message", true);//不通过
				}
			} else {
				result.put("message", true);//不通过
			}
		} catch (Exception e) {
			result.put("message", true);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(result));
	}

	/**
	 * 
	 *@description:存储定时器返回值
	 *@param timerInterVal
	 *@param session
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月23日下午5:39:55
	 *
	 */
	@RequestMapping("/account/setTimerReturnVal.do")
	public void setTimerReturnVal(int timerInterVal, HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			session.setAttribute("timerInterVal", timerInterVal);
			map.put("code", 200);
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 
	 *@description:获取定时器的返回值ID
	 *@param session
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月28日下午2:42:10
	 *
	 */
	@RequestMapping("/account/getTimerReturnVal.do")
	public void getTimerReturnVal(HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Object object = session.getAttribute("timerInterVal");
			if (object != null) {
				int timerInterVal = (Integer) session.getAttribute("timerInterVal");
				map.put("timerInterVal", timerInterVal);
				session.removeAttribute("timerInterVal");
				map.put("code", 200);
			} else {
				map.put("code", 202);
			}
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 
	 *@description:发送邮件
	 *@param type（定义是何种功能类型的邮件）
	 *@param request
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月23日下午5:48:53
	 *
	 */
	@RequestMapping(value = "/account/sendEmailBind.do")
	public void sendEmailBind(String type, HttpServletRequest request, HttpServletResponse response) {
		//1.生成验证码
		String charValue = CommonUtil.gen6ValidateCode();
		System.out.println("邮箱验证码为:" + charValue);
		//2.邮箱请求基本信息
		Email email = new Email();
		email.setVerificationCode(charValue);
		email.setEmail(request.getParameter("email").trim());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			//3.发送邮件
			emailService.sendEmail(email, type);
			returnMap.put("code", 200);
			//4.清空session中的验证码
			request.getSession().removeAttribute("emailVerificationCode");
			//5.验证码保存在session,用于比较
			request.getSession().setAttribute("emailVerificationCode", charValue); //验证码
			//	request.getSession().setMaxInactiveInterval(1800);//保存30分钟
		} catch (Exception e) {
			returnMap.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:发送短信（注意这里的时间限制，当我们用验证通过以后，需要清除掉）
	 *@param type(定义是何种功能类型的短信)
	 *@param request
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月24日上午11:08:38
	 *
	 */
	@RequestMapping(value = "/account/sendSMSBind.do")
	public void sendSMSBind(String type, HttpServletRequest request, HttpServletResponse response, String newOrOld) {
		//1.生成验证码
		String charValue = CommonUtil.gen6ValidateCode();
		System.out.println("短信验证码为:" + charValue);
		Map<String, Object> map = new HashMap<String, Object>();
		//2.发送短信
		try {
			//发送条件：不存在或者大于60秒间隔
			long currentTime = System.currentTimeMillis();
			Object smsLastSendTime;
			if ("new".equals(newOrOld)) {
				smsLastSendTime = request.getSession().getAttribute("newSMSLastSendTime");//新绑定页面的号码
			} else {
				smsLastSendTime = request.getSession().getAttribute("oldSMSLastSendTime");//原绑定页面的号码
			}
			if (smsLastSendTime == null
					|| (currentTime - Long.valueOf(String.valueOf(smsLastSendTime)).longValue()) > 60 * 1000) {

				smsService.SendRegisterSMS(charValue, request.getParameter("tel").trim());
				map.put("code", 200);
				//3.清空session中的验证码（放在try，catch里面，当出现异常，不对session进行处理）
				request.getSession().removeAttribute("smsVerificationCode");
				//4.验证码保存在session,用于比较
				request.getSession().setAttribute("smsVerificationCode", charValue); //短信验证码
				//	request.getSession().setMaxInactiveInterval(1800);//保存30分钟

				//5,session保存最近一次发送的时间，间隔小于60秒，不予发送
				if ("new".equals(newOrOld)) {
					request.getSession().setAttribute("newSMSLastSendTime", currentTime);
				} else {
					request.getSession().setAttribute("oldSMSLastSendTime", currentTime);
				}

			} else {
				map.put("code", 202);//时间间隔太小
			}

		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}

		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 
	 *@description:转到对应的页面（修改手机或者邮箱）
	 *@param mv
	 *@param map<type:0是修改手机绑定，1是修改邮箱绑定>
	 *@return
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年11月25日下午6:09:14
	 *
	 */
	@RequestMapping("/account/newBind.do")
	public ModelAndView newBind(ModelAndView mv, @RequestParam Map<String, Object> map) {
		if (map.get("type") != null && "0".equals(map.get("type"))) {
			mv.setViewName("/WEB-INF/view/account/newBindMobile.jsp");
		} else {
			mv.setViewName("/WEB-INF/view/account/newBindEmail.jsp");
		}
		mv.addAllObjects(map);//参数是：1，手机 或 邮箱	2，绑定 或 更改绑定 (暂时貌似没啥用)
		return mv;
	}

	/**
	 * 
	 *@description:修改绑定，返回账号安全界面
	 *@param map<type:1手机   2邮箱>
	 *@param mv
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年11月24日下午9:37:58
	 *
	 */
	@RequestMapping("/account/changeBind.do")
	public void changeBind(@RequestParam Map<String, Object> map, HttpServletResponse response,
			HttpServletRequest request) {
		SessionUser sUser = WebUtil.getLoginUser(request);
		User user = new User();
		user.setUserId(sUser.getUserId());
		String newCount = (String) map.get("bindAccount");

		if ("1".equals(map.get("type"))) {
			user.setLoginTel(newCount);
		}
		if ("2".equals(map.get("type"))) {
			user.setLoginEmail(newCount);
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			int count = userService.updateByPrimaryKeySelective(user);
			if (count > 0) {
				returnMap.put("message", true);
				//更新当前session的loginTel或者loginEmail
				if ("1".equals(map.get("type"))) {
					sUser.setLoginTel(newCount);
				}
				if ("2".equals(map.get("type"))) {
					sUser.setLoginEmail(newCount);
				}
			} else {
				returnMap.put("message", false);
			}
		} catch (Exception e) {
			returnMap.put("message", false);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:判断是否存在相同的绑定（决定是否可以发送短信）
	 *@param map
	 *@param request
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月25日上午12:54:39
	 *
	 */
	@RequestMapping("/account/existedCount.do")
	public void existedCount(@RequestParam Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		SessionUser sUser = WebUtil.getLoginUser(request);
		try {
			if ("1".equals(map.get("type")) && map.get("newAccount").equals(sUser.getLoginTel())) {
				returnMap.put("message", "200");//新输入的手机绑定数据与当前的绑定一样
			} else if ("2".equals(map.get("type")) && map.get("newAccount").equals(sUser.getLoginEmail())) {
				returnMap.put("message", "201");//新输入的邮箱绑定数据与当前的绑定一样
			} else {
				if (userService.existedCount(map) > 0)
					returnMap.put("message", "202");//该账号已存在其他绑定
				else
					returnMap.put("message", "203");//该账号可以用于绑定
			}
		} catch (Exception e) {
			returnMap.put("message", "204");
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:验证图片验证码是否正确
	 *@param oldPwd
	 *@param request
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月25日上午10:49:24
	 *
	 */
	@RequestMapping("/account/checkTelJqCode.do")
	public void checkTelJqCode(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//1.验证码是否输入正确
			String inputValidateCode = (String) request.getParameter("inputImageCode");
			String savedValidateCode;
			if ("1".equals((String) request.getParameter("type")))//1是手机号码的图片验证RANDOMCODEKEY_BINDMOBILE
				savedValidateCode = (String) request.getSession().getAttribute(
						RandomValidateCode.RANDOMCODEKEY_BINDMOBILE);
			else
				//2是邮箱的图片验证
				savedValidateCode = (String) request.getSession().getAttribute(
						RandomValidateCode.RANDOMCODEKEY_BINDEMAIL);
			if (StringUtils.isEmpty(inputValidateCode)
					|| (StringUtils.isNotEmpty(inputValidateCode) && StringUtils.isNotEmpty(savedValidateCode) && !inputValidateCode
							.toUpperCase().equals(savedValidateCode.toUpperCase()))) {
				result.put("message", true);//不通过
			} else {
				result.put("message", false);//通过
			}
		} catch (Exception e) {
			result.put("message", true);//不通过
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(result));
	}

}

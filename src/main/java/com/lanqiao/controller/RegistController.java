package com.lanqiao.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lanqiao.common.SSOResult;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.StuPaidEnum;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.constant.GlobalConstant.auditStatusEnum;
import com.lanqiao.model.Email;
import com.lanqiao.model.Student;
import com.lanqiao.model.User;
import com.lanqiao.service.EmailService;
import com.lanqiao.service.SmsService;
import com.lanqiao.service.SmssendLogService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.HttpClientUtil;
import com.lanqiao.util.JsonUtil;
import com.lanqiao.util.RandomValidateCode;

/**
 * 
* 项目名称:lqzp2
* 类名称： RegistController
* 类描述:注册controller
* 创建人: ZhouZhiHua
* 创建时间:2016年11月23日 上午10:07:37 
* 修改人： 
* 修改时间：2016年11月23日 上午10:07:37
* 修改备注:
 */
@Controller
@RequestMapping("/regist")
public class RegistController {

	private static final Logger logger = LogManager.getLogger(RegistController.class);

	@Resource
	UserService userService;

	@Resource
	SmsService smsService;

	@Resource
	SmssendLogService smssendLogService;

	@Resource
	EmailService emailService;

	/**
	 * 
	* @Description:跳转注册
	* @param mv
	* @param request
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年11月23日 上午10:57:53
	 */
	@RequestMapping("/goRegist.do")
	public ModelAndView goRegister(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("/WEB-INF/view/user/regist.jsp");
		return mv;
	}

	@RequestMapping("/goRegist1.do")
	public ModelAndView goRegister1(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("/WEB-INF/view/user/tip.jsp");
		return mv;
	}

	/**
	 * 
	* @Description:注册时去用户协议页面
	* @param request
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年11月23日 下午2:19:21
	 */
	@RequestMapping("/goRegisterAgreement.do")
	public ModelAndView goRegisterAgreement(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/WEB-INF/view/user/registerAgreement.jsp");
		return mv;
	}

	/**
	 * 
	* @Description:手机注册-验证手机号是否存在
	* @param loginTel
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月23日 下午2:48:04
	 */
	@RequestMapping("/loginTelCheck.do")
	public void loginTelCheck(String loginTel, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = userService.loginTelCheck(loginTel);
			if (map != null) {
				map.put("code", 200);
			} else {
				map = new HashMap<String, Object>();
				map.put("code", 201);
			}
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 
	* @Description:手机注册-验证机器验证码
	* @param oldPwd
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月23日 下午3:25:36
	 */
	@RequestMapping("/checkTelJqCode.do")
	public void checkTelJqCode(String oldPwd, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//1.验证码是否输入正确
			String inputValidateCode = (String) request.getParameter("jqauthcode");
			String savedValidateCode = (String) request.getSession().getAttribute(
					RandomValidateCode.RANDOMCODEKEY_LOGIN);
			if (StringUtils.isEmpty(inputValidateCode)
					|| (StringUtils.isNotEmpty(inputValidateCode) && StringUtils.isNotEmpty(savedValidateCode) && !inputValidateCode
							.toUpperCase().equals(savedValidateCode.toUpperCase()))) {
				result.put("message", true);
			} else {
				result.put("message", false);
			}
		} catch (Exception e) {
			result.put("message", true);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(result));
	}

	/**
	 * 
	* @Description:发送手机注册验证码
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月23日 下午4:11:13
	 */
	@RequestMapping(value = "/sendRegisterVerificationCode.do")
	public void sendRegisterVerificationCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			//1.生成验证码
			String charValue = CommonUtil.gen6ValidateCode();
			System.out.println("短信验证码为:" + charValue);
			//2.短信请求基本信息
			String tel = request.getParameter("tel");
			//3.清空session中的验证码
			request.getSession().removeAttribute("verificationCode");
			//4.验证码保存在session,用于比较
			request.getSession().setAttribute("verificationCode", charValue); //短信验证码
			//request.getSession().setMaxInactiveInterval(1800);//保存30分钟
			//4.发送短信
			smsService.SendRegisterSMS(charValue, tel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(""));
	}

	/**
	 * 
	* @Description:短信验证
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月23日 下午4:40:23
	 */
	@RequestMapping(value = "/smsValidate.do")
	public void smsValidate(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//1.sesison与填写的验证码进行比较
			String authcode = request.getParameter("authcode");
			String verificationCode = (String) request.getSession().getAttribute("verificationCode");
			if (verificationCode != null) {
				if (verificationCode.equals(authcode)) {
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
	* @Description:保存-手机号注册信息
	* @param mv
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月23日 下午4:55:39
	 */
	@RequestMapping(value = "/saveRegister.do", method = RequestMethod.POST)
	public void saveRegister(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//1.验证手机号是否存在
			map = userService.loginTelCheck(request.getParameter("tel"));
			if (map != null) {
				map = new HashMap<String, Object>();
				map.put("code", 201);
				JsonUtil.write(response, JSON.toJSONString(map));
				return;
			}
			//2.验证机器验证码
			String inputValidateCode = (String) request.getParameter("jqauthcode");
			String savedValidateCode = (String) request.getSession().getAttribute(
					RandomValidateCode.RANDOMCODEKEY_LOGIN);
			if (StringUtils.isEmpty(inputValidateCode)
					|| (StringUtils.isNotEmpty(inputValidateCode) && StringUtils.isNotEmpty(savedValidateCode) && !inputValidateCode
							.toUpperCase().equals(savedValidateCode.toUpperCase()))) {
				map = new HashMap<String, Object>();
				map.put("code", 201);
				JsonUtil.write(response, JSON.toJSONString(map));
				return;
			}
			//3.短信验证
			//sesison与填写的验证码进行比较
			String authcode = request.getParameter("authcode");
			String verificationCode = (String) request.getSession().getAttribute("verificationCode");
			if (verificationCode != null) {
				if (!verificationCode.equals(authcode)) {
					map = new HashMap<String, Object>();
					map.put("code", 201);
					JsonUtil.write(response, JSON.toJSONString(map));
					return;
				}
			} else {
				map = new HashMap<String, Object>();
				map.put("code", 201);
				JsonUtil.write(response, JSON.toJSONString(map));
				return;
			}

			//4.上传用户默认图片
			File file = new File(request.getServletContext().getRealPath("/static/images/defaultphoto.png"));//获取指定文件
			String targetPath = GlobalConstant.uploadPath;//获取上传路径
			Date curDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String curMonth = sdf.format(curDate);//
			StringBuffer sb = new StringBuffer();
			StringBuffer dbSbf = new StringBuffer();
			dbSbf.append("/image/photo/").append(curMonth);//追加文件夹,以时间命名
			sb.append(targetPath).append(File.separator).append("photo").append(File.separator).append(curMonth);
			//判断是否存在当年年月目录
			File dirFile = new File(sb.toString());
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			logger.debug("#######################上传图片的路径" + dirFile + "##########################");
			StringBuffer fileNameSbf = new StringBuffer();
			String randomStr = UUID.randomUUID().toString();
			fileNameSbf.append(File.separator).append(randomStr).append(".png");
			dbSbf.append("/").append(randomStr).append(".png");
			sb.append(fileNameSbf);
			//开始上传
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(sb.toString()));
			byte[] data = new byte[1024];
			while (in.read(data) != -1) {
				out.write(data);
			}
			out.flush();
			out.close();
			in.close();

			//5.用户信息添加
			User user = new User();
			user.setLoginTel(request.getParameter("tel"));
			user.setTel(request.getParameter("tel"));
			String salt = CommonUtil.randomSalt();
			user.setSalt(salt);
			user.setPassword(CommonUtil.md5Pwd(request.getParameter("pwd"), salt));
			user.setType("0");//登陆用户类型:0学生1老师(可为空)
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsvalid(1);//默认有效
			user.setPhoto(dbSbf.toString());
			//6.学生信息添加
			Student student = new Student();
			student.setStatus(StuStatusEnum.NOREGISTRATION.getValue());//注意:状态1.添加报名学生的状态为待审核||状态15.未报名
			student.setAuditStatus(auditStatusEnum.NOREGIST.getValue());
			student.setCreateTime(new Date());
			student.setUpdateTime(new Date());
			student.setIsAvaiable(StuPaidEnum.NOTPAY.getValue());//报名费是否缴费
			student.setHasPaid(StuPaidEnum.NOTPAY.getValue());////是否缴纳了学费 

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
			paramMap.put("phone", request.getParameter("tel"));
			paramMap.put("password", request.getParameter("pwd"));
			paramMap.put("usertype", "0");
			paramMap.put("appid", GlobalConstant.appid);
			String str = HttpClientUtil.postRequestResult(baseUrl + "regist.do", paramMap);
			System.out.println(str);
			Gson gson = new Gson();
			SSOResult result = gson.fromJson(str, SSOResult.class);
			System.out.println(result.getResultcode() + "       2222  " + result.getResultmsg());
			if (result.getResultcode() == 1) {
				user.setSsoUserid(Integer.valueOf(result.getUserid()));
				//7.保存注册信息(用户+学生)
				userService.saveRegister(user, student);
				map = new HashMap<String, Object>();
				map.put("code", 200);
			} else {
				map = new HashMap<String, Object>();
				map.put("code", 201);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = new HashMap<String, Object>();
			map.put("code", 201);
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 
	* @Description:手机注册-验证邮箱是否存在
	* @param loginEmail
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月24日 下午1:55:46
	 */
	@RequestMapping("/loginEmailCheck.do")
	public void loginEmailCheck(String loginEmail, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = userService.loginEmailCheck(loginEmail);
			if (map != null) {
				map.put("code", 200);
			} else {
				map = new HashMap<String, Object>();
				map.put("code", 201);
			}
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 
	* @Description:发送邮箱注册验证码
	* @param type
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月24日 下午2:10:58
	 */
	@RequestMapping(value = "/sendEmailRegisterCode.do")
	public void sendEmailRegisterCode(String type, HttpServletRequest request, HttpServletResponse response) {
		//1.生成验证码
		String charValue = CommonUtil.gen6ValidateCode();
		System.out.println("邮箱验证码为:" + charValue);
		//2.邮箱请求基本信息
		Email email = new Email();
		email.setVerificationCode(charValue);
		email.setEmail(request.getParameter("email").trim());
		//3.清空session中的验证码
		request.getSession().removeAttribute("verificationCode");
		//4.验证码保存在session,用于比较
		request.getSession().setAttribute("verificationCode", charValue); //短信验证码
		//	request.getSession().setMaxInactiveInterval(1800);//保存30分钟
		try {
			//4.发送邮件
			emailService.sendEmail(email, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(""));
	}

	/**
	 * 
	* @Description:注册时邮箱验证
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月24日 下午2:14:16
	 */
	@RequestMapping(value = "/emailValidate.do")
	public void emailValidate(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//1.sesison与填写的验证码进行比较
			String authcode = request.getParameter("emailAuthcode");
			String verificationCode = (String) request.getSession().getAttribute("verificationCode");
			if (verificationCode != null) {
				if (verificationCode.equals(authcode)) {
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
	* @Description:保存邮箱注册信息
	* @param mv
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月24日 下午2:22:46
	 */
	@RequestMapping(value = "/saveEmailRegister.do", method = RequestMethod.POST)
	public void saveEmailRegister(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//1.验证邮箱是否存在
			map = userService.loginEmailCheck(request.getParameter("email"));
			if (map != null) {
				map.put("code", 201);
				JsonUtil.write(response, JSON.toJSONString(map));
				return;
			}
			//2.注册时邮箱验证
			//sesison与填写的验证码进行比较
			String authcode = request.getParameter("emailauthcode");
			String verificationCode = (String) request.getSession().getAttribute("verificationCode");
			if (verificationCode != null) {
				if (!verificationCode.equals(authcode)) {
					map = new HashMap<String, Object>();
					map.put("code", 201);
					JsonUtil.write(response, JSON.toJSONString(map));
					return;
				}
			} else {
				map = new HashMap<String, Object>();
				map.put("code", 201);
				JsonUtil.write(response, JSON.toJSONString(map));
				return;
			}

			//3.上传用户默认图片
			File file = new File(request.getServletContext().getRealPath("/static/images/defaultphoto.png"));//获取指定文件
			String targetPath = GlobalConstant.uploadPath;//获取上传路径
			Date curDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String curMonth = sdf.format(curDate);//
			StringBuffer sb = new StringBuffer();
			StringBuffer dbSbf = new StringBuffer();
			dbSbf.append("/image/photo/").append(curMonth);//追加文件夹,以时间命名
			sb.append(targetPath).append(File.separator).append("photo").append(File.separator).append(curMonth);
			//判断是否存在当年年月目录
			File dirFile = new File(sb.toString());
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			logger.debug("#######################上传图片的路径" + dirFile + "##########################");
			StringBuffer fileNameSbf = new StringBuffer();
			String randomStr = UUID.randomUUID().toString();
			fileNameSbf.append(File.separator).append(randomStr).append(".png");
			dbSbf.append("/").append(randomStr).append(".png");
			sb.append(fileNameSbf);
			//开始上传
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(sb.toString()));
			byte[] data = new byte[1024];
			while (in.read(data) != -1) {
				out.write(data);
			}
			out.flush();
			out.close();
			in.close();
			//4.用户信息添加
			User user = new User();
			user.setLoginEmail(request.getParameter("email"));
			user.setEmail(request.getParameter("email"));
			String salt = CommonUtil.randomSalt();
			user.setSalt(salt);
			user.setPassword(CommonUtil.md5Pwd(request.getParameter("ePwd"), salt));
			user.setType("0");//登陆用户类型:0学生1老师(可为空)
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setIsvalid(1);//默认有效
			user.setPhoto(dbSbf.toString());
			//5.学生信息添加
			Student student = new Student();
			student.setStatus(StuStatusEnum.NOREGISTRATION.getValue());//注意:状态1.添加报名学生的状态为待审核||状态15.未报名
			student.setAuditStatus(4);
			student.setCreateTime(new Date());
			student.setUpdateTime(new Date());
			student.setIsAvaiable(StuPaidEnum.NOTPAY.getValue());//报名费是否缴费
			student.setHasPaid(StuPaidEnum.NOTPAY.getValue());//是否缴纳了学费 

			//调用sso   查看用户登录信息
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String baseUrl = GlobalConstant.SSOURL;
			paramMap.put("email", request.getParameter("email"));
			paramMap.put("password", request.getParameter("ePwd"));
			paramMap.put("usertype", 0 + "");
			paramMap.put("appid", GlobalConstant.appid);
			String str = HttpClientUtil.postRequestResult(baseUrl + "regist.do", paramMap);
			System.out.println(str);
			Gson gson = new Gson();
			SSOResult result = gson.fromJson(str, SSOResult.class);
			System.out.println(result.getResultcode() + "       2222233333222  " + result.getResultmsg()
					+ user.toString());
			if (result.getResultcode() == 1) {
				//添加ssoid 到sso_userid
				user.setSsoUserid(Integer.valueOf(result.getUserid()));
				//6.保存注册信息(用户+学生)
				userService.saveRegister(user, student);
				map = new HashMap<String, Object>();
				map.put("code", 200);
			} else {
				map = new HashMap<String, Object>();
				map.put("code", 201);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 201);
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

}

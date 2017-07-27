package com.lanqiao.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lanqiao.common.MustLoginAndTeacher;
import com.lanqiao.common.SSOResult;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.SysMenuExt;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.model.Email;
import com.lanqiao.model.Student;
import com.lanqiao.model.SysFunction;
import com.lanqiao.model.SysRole;
import com.lanqiao.model.User;
import com.lanqiao.service.DictService;
import com.lanqiao.service.EmailService;
import com.lanqiao.service.LQCityService;
import com.lanqiao.service.SmsService;
import com.lanqiao.service.StudentService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.FileUtil;
import com.lanqiao.util.HttpClientUtil;
import com.lanqiao.util.JsonUtil;
import com.lanqiao.util.RandomStrUtil;
import com.lanqiao.util.RandomValidateCode;

@Controller
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Resource
	UserService service;

	@Resource
	LQCityService lQCityService;

	@Resource
	StudentService studentService;

	@Resource
	DictService dictService;

	@Resource
	UserService userService;

	@Resource
	SmsService smsService;

	@Resource
	EmailService emailService;

	/**
	 * 验证码
	 * @param type : RandomValidateCode 中有定义
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/validateCode/{type}.do", method = RequestMethod.GET)
	public void validateCode(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		RandomValidateCode randomValidateCode = new RandomValidateCode(type);
		try {
			randomValidateCode.getRandcode(request, response);//输出图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/exitlogin.do")
	public ModelAndView exitlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("loginuser", null);
		//String s = request.getContextPath();
		return new ModelAndView("redirect:/");
	}

	/**
	 * 登录显示
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/login.do")
	public ModelAndView showlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String backurl = request.getParameter("backurl");

		ModelAndView mv = new ModelAndView("/WEB-INF/view/user/login.jsp","backurl",backurl);
		//mv.addObject("backurl", backurl);

		return mv;

	}

	/**
	 * 首页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@MustLoginAndTeacher
	@RequestMapping("/user/home.do")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap pm = new HashMap();
		SessionUser suser = WebUtil.getLoginUser(request);
		String stypename = "";
		if (suser.getStudent() != null)
			stypename = "学生";
		else {
			stypename = WebUtil.getTeacherTypeName(suser.getTeacher().getTeachtype());
		}
		String realname = suser.getRealName();
		if (StringUtils.isEmpty(realname))
			realname = "无真实姓名";

		pm.put("identityname", stypename);
		pm.put("realname", realname);

		return new ModelAndView("/WEB-INF/view/user/index.jsp", pm);
	}

	/**
	 * 登录提交
	 * @param paramMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/user/loginpost.do")
	public void login(@RequestParam Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WebUtil.printMap((HashMap) paramMap);

		Integer bindtype = (Integer) request.getSession().getAttribute("bindtype");
		paramMap.put("bindtype", bindtype);

		String vcode = (String) paramMap.get("vcode");
		String sesscode = (String) request.getSession().getAttribute(RandomValidateCode.RANDOMCODEKEY_LOGIN);

		HashMap map = new HashMap();
		if (!sesscode.equalsIgnoreCase(vcode)) {
			map.put("code", 210);
			map.put("msg", "验证码错误");
		} else {
			map = service.login2(paramMap);
			//	logger.debug (new ToStringBuilder(map).toString() );
			SessionUser user = (SessionUser) map.get("user");
			if (user != null) {
				//保存到session
				request.getSession().setAttribute("loginuser", user);
				//打印测试.
				printLog(user);
				map.put("usertype", user.getType()); // 根据这个参数, 跳转到不同的页面

				// 测试用. 登录有就有所有的哦功能权限. 
				//user.setSysfunctions(service.getAllFunction());
				// 支持backurl 跳转. 
				String backurl = (String) paramMap.get("backurl");
				if (backurl != null && !"".equals(backurl)) {
					String tokenkey = RandomStrUtil.createRandomString(64);
					map.put("tokenkey", tokenkey);
					//存储到tokenkey到redis服务器中
					System.out.println("tokenkey:" + tokenkey);
					System.out.println("value:" + user.getUserId());

					userService.saveToRedis(tokenkey, user.getUserId(), (long) 24 * 60 * 60);
					//link url 
					if (backurl.indexOf("?") != -1)
						backurl = backurl + "&tokenkey=" + tokenkey;
					else
						backurl += "?tokenkey=" + tokenkey;
					map.put("code", 300);
					map.put("backurl", backurl);
				}

			}
		}

		map.remove("user");
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 显示查询界面, 测试权限.	
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception//	@Functions(1)
	 */

	@RequestMapping("/student/search.do")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Thread.sleep(2000);
		return new ModelAndView("/WEB-INF/view/user/stu_inquiry.jsp");
	}

	private void printLog(SessionUser user) {

		//打印测试 =============================
		List<SysMenuExt> l = user.getSysmenus();
		logger.debug("=========用户菜单=========");
		if (l != null)
			for (SysMenuExt m : l) {
				logger.debug(m.getId() + ":" + m.getName());
				List<SysMenuExt> l2 = m.getSortSubMenus();
				for (SysMenuExt m2 : l2) {
					logger.debug("\t\t" + m2.getId() + ":" + m2.getName());

				}
			}

		logger.debug("=========用户功能=========");
		List<SysFunction> lf = user.getSysfunctions();
		for (SysFunction f : lf) {
			logger.debug(f.getFunctionid() + ":" + f.getFunctionname());
		}

		logger.debug("=========用户角色=========");
		List<SysRole> rs = user.getRoles();
		for (SysRole r : rs) {
			logger.debug(r.getId() + ":" + r.getName());
		}
		logger.debug("=========记录权限,跟组织结构相关=========");

		HashMap<Integer, HashSet<Integer>> hs = user.getAllDataID();
		if (hs != null) {
			for (Map.Entry en : hs.entrySet()) {
				HashSet<Integer> st = (HashSet<Integer>) en.getValue();
				String strlog = "";
				for (Integer n : st) {
					strlog += n + ",";
				}
				logger.debug(en.getKey() + ":" + strlog);

			}

		}

	}

	/**
	 * @author 罗玉琳
	 * 跳转到个人信息页面
	 * @return
	 */
	@RequestMapping("/user/goUserInfo.do")
	public ModelAndView goUserInfo(ModelAndView mv, HttpServletRequest request) throws Exception {

		SessionUser user = WebUtil.getLoginUser(request);
		User userInfo = userService.getUserInfoByUserId(user.getUserId());//获取用户信息
		user.setProvCode(userInfo.getProvCode());
		user.setCityCode(userInfo.getCityCode());
		user.setSex(userInfo.getSex());
		user.setNickname(userInfo.getNickname());
		user.setPhoto(userInfo.getPhoto());
		//1.获取省列表
		List<Map<String, Object>> provList = lQCityService.findProvinces();
		mv.addObject("provList", provList);
		//2.获取学生所在市列表
		if (userInfo.getProvCode() != null && !"".equals(userInfo.getProvCode())) {
			List<Map<String, Object>> cityList = lQCityService.selectByProvId(Integer.parseInt(userInfo.getProvCode()));
			mv.addObject("cityList", cityList);
			//3.获取省文字
			String prov = lQCityService.getById(Integer.parseInt(userInfo.getProvCode()));
			mv.addObject("prov", prov);
		}
		//获取市文字
		if (userInfo.getCityCode() != null && !"".equals(userInfo.getCityCode())) {
			String city = lQCityService.getById(Integer.parseInt(userInfo.getCityCode()));
			mv.addObject("city", city);
		}
		//学生才需要这部分数据
		if (user.getType() != null && !"".equals(user.getType()) && user.getType().equals("0")) {
			//获取学生的审核审核状态和状态，用于抬头显示提示
			int userId = (Integer) user.getUserId();
			mv.addObject("stuInfo", studentService.statusForHead(userId));
			//获取学生状态转换成文字，用于个人信息页面显示学生状态
			Student stu = studentService.getStuInfoByUserId(userId);//获取学员状态
			if (stu.getStatus() != null && !"".equals(stu.getStatus())) {
				mv.addObject("stuStatus", WebUtil.getStuStatusByValue(stu.getStatus()));
			}
		}
		//共享user
		mv.addObject("user", user);
		mv.setViewName("/WEB-INF/view/user/userinfo.jsp");
		return mv;
	}

	/**
	 * 
	* @Description:修改用户个人信息(昵称/头像)
	* @param nickname,photo
	* @param request
	* @return resultMap 
	* @author 罗玉琳
	* @createTime 2016年11月23日 下午17:41
	 */
	@RequestMapping("/user/updateUserInfo.do")
	public void updateUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SessionUser sessionUser = WebUtil.getLoginUser(request);
		int userId = sessionUser.getUserId();
		User user = new User();
		user.setUserId(userId);
		user.setNickname(request.getParameter("nickname"));
		if (request.getParameter("sex") != null && !"".equals(request.getParameter("sex"))) {
			user.setSex(request.getParameter("sex"));
		}
		//System.out.println("birth"+request.getParameter("birth"));
		/*		try {
					if(request.getParameter("birth")!=null && !"".equals( request.getParameter("birth"))){
						user.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birth")));//出生年月
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}*/

		user.setProvCode(request.getParameter("provCode"));
		user.setCityCode(request.getParameter("cityCode"));
		try {
			int row = userService.updateUserInfo(user, request, sessionUser);
			if (row > 0) {//更新session
				userService.BindDirectLogin(user.getUserId(), request);
			}
			resultMap.put("success", true);
			resultMap.put("message", "修改个人信息成功!");
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("success", "修改个人信息失败!");
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(resultMap));
	}

	/**
	 * 上传用户头像
	 * @param photo
	 * @param request
	 * @param response
	 * @author 罗玉琳
	 */
	@RequestMapping(value = "/user/uploadPhoto.do")
	public void uploadPhoto(@RequestParam MultipartFile photoImg, HttpServletRequest request,
			HttpServletResponse response) {
		//这句很重要, 保证文件上传的ajax 能正常接收.
		response.setContentType("text/html; charset=UTF-8");

		Map<String, Object> map = new HashMap<String, Object>();
		if (photoImg == null || photoImg.isEmpty()) {
			map.put("code", 201);
			JsonUtil.write(response, JSON.toJSONString(map));
			return;
		}
		if (photoImg.getSize() > 3 * 1024 * 1024) {
			map.put("code", 202);
			JsonUtil.write(response, JSON.toJSONString(map));
			return;
		}

		//判断图片的后缀
		String suffixs = GlobalConstant.PICTURESUFFIX;
		String suff = photoImg.getOriginalFilename().substring(photoImg.getOriginalFilename().lastIndexOf("."));
		if (suffixs.indexOf(suff) != -1) {
			try {
				String fileName = FileUtil.uploadPhotoImgFile(request, photoImg);
				//更新用户头像信息
				SessionUser sessionUser = WebUtil.getLoginUser(request);
				int userId = sessionUser.getUserId();
				User user = new User();
				user.setUserId(userId);
				user.setPhoto(fileName);
				userService.updateByPrimaryKeySelective(user);
				sessionUser.setPhoto(fileName);
				map.put("code", 200);
				map.put("fileName", fileName);
			} catch (Exception e) {
				map.put("code", 203);
			}
		} else {
			map.put("code", 204);
			return;
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 跳转到修改密码页面
	 * @param frontImg
	 * @param request
	 * @param response
	 */
	@RequestMapping("/user/goModifyPwd.do")
	public ModelAndView goUpdatePwd(HttpSession session, ModelAndView mv, HttpServletRequest request) {
		SessionUser sessionUser = WebUtil.getLoginUser(request);
		try {
			int userId = sessionUser.getUserId();
			String tel = sessionUser.getLoginTel();
			String email = sessionUser.getLoginEmail();
			mv.addObject("email", email);
			mv.addObject("originTel", tel);
			if (StringUtils.isNotEmpty(tel)) {
				tel = CommonUtil.hideMobile(tel);
			}
			mv.addObject("userId", userId);
			mv.addObject("tel", tel);
			if (request.getParameter("way") != null && !"".equals(request.getParameter("way"))) {
				mv.addObject("way", request.getParameter("way"));//选择验证方式处会传way过来
			} else {
				mv.addObject("way", "-1");
			}
			mv.setViewName("/WEB-INF/view/user/modifyPassword.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}

	/**
	 * 跳转到选择修改密码验证方式
	 * @param frontImg
	 * @param request
	 * @param response
	 */
	@RequestMapping("/user/goChooseValidation.do")
	public ModelAndView goChooseValidation(HttpSession session, ModelAndView mv, HttpServletRequest request) {
		SessionUser sessionUser = WebUtil.getLoginUser(request);
		try {
			String tel = sessionUser.getLoginTel();
			String email = sessionUser.getLoginEmail();
			if (StringUtils.isNotEmpty(tel)) {
				tel = CommonUtil.hideMobile(tel);
			}
			if (StringUtils.isNotEmpty(email)) {
				email = CommonUtil.hideEmail(email);
			}
			mv.addObject("tel", tel);
			mv.addObject("email", email);
			mv.addObject("nickname", sessionUser.getNickname());
			mv.addObject("userId", sessionUser.getUserId());
			mv.setViewName("/WEB-INF/view/user/pwdValidateWay.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}

	/**
	 * 修改密码旧密码检查
	 * @param jsonScoreData
	 * @param response
	 */
	@RequestMapping("/user/oldPwdCheck.do")
	public void oldPwdCheck(String oldPwd, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("isRightCode", userService.oldPwdCheck(oldPwd, request));
			map.put("code", 200);
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 修改新密码
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/updateNewPwd.do", method = RequestMethod.POST)
	public void updateNewPwd(Integer userId, String pwd, ModelAndView mv, HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userService.updatePwd(userId, pwd);
			session.removeAttribute("userMap");
			map.put("code", 200);
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 存储定时器返回值
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/setTimerReturnVal.do")
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
	 * 发送短信验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/sendSmsVerificationCode.do")
	public void sendSMSBind(String type, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		//1.生成验证码
		String charValue = CommonUtil.gen6ValidateCode();
		//2.发送短信
		try {
			//发送条件：不存在或者大于60秒间隔
			long currentTime = System.currentTimeMillis();
			Object smsLastSendTime = request.getSession().getAttribute("smsLastSendTime");
			if (smsLastSendTime == null
					|| (currentTime - Long.valueOf(String.valueOf(smsLastSendTime)).longValue()) > 60 * 1000) {
				smsService.SendRegisterSMS(charValue, request.getParameter("tel").trim());
				System.out.println("###########################短信验证码为:" + charValue + "###########################");
				map.put("code", 200);
				//3.清空session中的验证码（放在try，catch里面，当出现异常，不对session进行处理）
				request.getSession().removeAttribute("pwd_verificationCode");
				//4.验证码保存在session,用于比较
				request.getSession().setAttribute("pwd_verificationCode", charValue); //短信验证码

				//5,session保存最近一次发送的时间，间隔小于60秒，不予发送
				request.getSession().setAttribute("smsLastSendTime", currentTime);
			} else {
				map.put("code", 202);//时间间隔太小
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 201);
		}

		JsonUtil.write(response, JSON.toJSONString(map));
	}

	@RequestMapping("user/msgVerificationCodeCheck.do")
	public void msgVerificationCodeCheck(String validateCode, String type, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("isRightCode", userService.msgVerificationCodeCheck(validateCode, type, request));
			map.put("code", 200);
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 
	* @Description:
	* @param validateCode
	* @param type
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月23日 下午5:48:02
	 */
	@RequestMapping("user/msgVerificationCodeCheckZh.do")
	public void msgVerificationCodeCheckZh(String validateCode, String type, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("isRightCode", userService.msgVerificationCodeCheckZh(validateCode, type, request));
			map.put("code", 200);
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 
	* @Description:发送邮箱注册验证码
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年8月29日 上午10:20:58
	 */
	@RequestMapping(value = "/user/sendEmailRegisterCode.do")
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
	* @Description:注册账户
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年8月29日 上午10:20:58
	 */
	@RequestMapping(value = "/user/setsso.do")
	public void setsso() {
		List<User> getallUser = service.getallUser();
		for (User user : getallUser) {
			//调用sso   查看用户登录信息
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String baseUrl = GlobalConstant.SSOURL;
			if (StringUtils.isNotBlank(user.getLoginTel())) {
				paramMap.put("phone", user.getLoginTel());
			} else {
				paramMap.put("phone", user.getTel());
			}
			if (StringUtils.isNotBlank(user.getLoginEmail())) {
				paramMap.put("email", user.getLoginEmail());
			} else {
				paramMap.put("email", user.getEmail());
			}
			paramMap.put("password", user.getPassword());
			paramMap.put("usertype", user.getType());
			paramMap.put("appid", GlobalConstant.appid);
			paramMap.put("realname", user.getRealName());
			paramMap.put("salt", user.getSalt());

			String str = HttpClientUtil.postRequestResult(baseUrl + "regist.do", paramMap);
			logger.info(str + "                " + user.getSalt());
			Gson gson = new Gson();
			if (str.contains("html")) {
				logger.error(user.toString() + "       出现错误error ");
			} else {
				SSOResult result = gson.fromJson(str, SSOResult.class);

				if (result.getResultcode() == 1) {
					//添加ssoid 到sso_userid
					user.setSsoUserid(Integer.valueOf(result.getUserid()));
					//6.保存注册信息(用户+学生)
					service.updateUser(user);
				} else if (result.getResultcode() == 100) {
					logger.info(user.toString() + "  已经注册过了");
					//添加ssoid 到sso_userid
					user.setSsoUserid(Integer.valueOf(result.getUserid()));
					//6.保存注册信息(用户+学生)
					service.updateUser(user);
				} else {
					logger.error(result.getResultcode() + "     " + user.toString());
				}

			}

		}

	}
}

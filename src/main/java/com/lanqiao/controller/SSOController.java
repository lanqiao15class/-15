/*
 * File name:          SSOController.java
 * Copyright@Handkoo (China)
 * Editor:           JDK1.6.32
 */
package com.lanqiao.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lanqiao.common.SSOResult;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.SysMenuExt;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.StuPaidEnum;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
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
import com.lanqiao.util.HttpClientUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

/**
 * TODO: File comments
 * <p>
 * <p>
 * Author:           卢锡文
 * <p>
 * Date:           2017年5月2日
 * <p>
 * Time:           下午3:02:27
 * <p>
 * Director:        卢锡文
 * <p>
 * <p>
 */
@RequestMapping("/sso")
@Controller
public class SSOController {
	private static final Logger logger = LogManager.getLogger(SSOController.class);

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
	 * 登录显示
	 * @param request@re
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/login.do")
	public void showlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//System.out.println("1111111111111111111111");
		//停止500ms
		Thread.sleep(2000);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if (StringUtils.equals("lqtokenkey", cookieName)) {
					String cookieValue = cookie.getValue();
					//调用sso   查看用户登录信息
					Map<String, Object> paramMap = new HashMap<String, Object>();
					String baseUrl = GlobalConstant.SSOURL;
					logger.info("baseUrl    " + baseUrl + "         " + getIpAddr(request));
					//客户端ip
					paramMap.put("clientip", getIpAddr(request));
					//lqtokenkey 
					paramMap.put("lqtokenkey", cookieValue);
					String str = HttpClientUtil.postRequestResult(baseUrl + "tokenkey.do", paramMap);
					System.out.println(str);
					Gson gson = new Gson();
					if (str.contains("html")) {
						logger.error("  sso  连接出现错误error ");
						response.sendRedirect(GlobalConstant.SSOURL + "login.do?appid=" + GlobalConstant.appid
								+ "&backurl=" + GlobalConstant.GlobalWebURL);
					} else {
						SSOResult result = gson.fromJson(str, SSOResult.class);
						if (result.getResultcode() == 1) {
							//存储数据到session
							Map<String, Object> map = loginpost(request, result.getUserid());
							if (map != null && StringUtils.equals(map.get("code").toString(), "200")) {
								if (StringUtils.equals(map.get("usertype").toString(), "1")) {
									response.sendRedirect(GlobalConstant.GlobalWebURL + "/user/home.do");
								} else {
									response.sendRedirect(GlobalConstant.GlobalWebURL + "/student/home.do");
								}
							} else {
								//该用户没有注册本系统添加基本数据到系统
								if (result.getUsertype() == 0) {
									//学生
									Map<String, Object> mapresult = saveEmailRegister(result, request);
									System.out
											.println(mapresult.get("code") + "                        11111111111111");
									System.out.println(StringUtils.equals(mapresult.get("code").toString(), "200")
											+ "                        11111111111111");

									if (StringUtils.equals(mapresult.get("code").toString(), "200")) {
										loginpost(request, result.getUserid());
										response.sendRedirect(GlobalConstant.GlobalWebURL + "/student/home.do");

									} else {
										//去注册
										response.sendRedirect(GlobalConstant.GlobalWebURL + "/regist/goRegist.do");
									}
								} else {
									response.sendRedirect(GlobalConstant.GlobalWebURL + "/regist/goRegist1.do");
								}
							}

						} else {
							response.sendRedirect(GlobalConstant.SSOURL + "login.do?appid=" + GlobalConstant.appid
									+ "&backurl=" + GlobalConstant.GlobalWebURL);
						}
					}

					return;
				}
			}

		}

		//没有cookie 跳转到sso 登陆
		response.sendRedirect(GlobalConstant.SSOURL + "login.do?appid=" + GlobalConstant.appid + "&backurl="
				+ GlobalConstant.GlobalWebURL);

	}

	public Map<String, Object> saveEmailRegister(SSOResult result, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNoneBlank(result.getLoginemail())) {
				//1.验证邮箱是否存在
				map = userService.loginEmailCheck(result.getLoginemail());
				if (map != null) {
					map.put("code", 201);

					return map;
				}
			} else if (StringUtils.isNoneBlank(result.getLogintel())) {
				//1.验证邮箱是否存在
				map = userService.loginTelCheck(result.getLogintel());
				if (map != null) {
					map.put("code", 202);
					return map;
				}
			} else {
				map.put("code", 203);

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
			if (StringUtils.isNoneBlank(result.getLoginemail())) {
				user.setLoginEmail(result.getLoginemail());
				user.setEmail(result.getLoginemail());
			}
			if (StringUtils.isNoneBlank(result.getLogintel())) {
				user.setLoginTel(result.getLogintel());
				user.setTel(result.getLogintel());
			}

			user.setRealName(result.getRealname());
			String salt = CommonUtil.randomSalt();
			user.setSalt(salt);
			user.setPassword(CommonUtil.md5Pwd("lanqiao2017", salt));
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

			//添加ssoid 到sso_userid
			user.setSsoUserid(Integer.valueOf(result.getUserid()));
			//6.保存注册信息(用户+学生)
			userService.saveRegister(user, student);
			map = new HashMap<String, Object>();
			map.put("code", 200);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 204);
		}
		return map;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private Map<String, Object> loginpost(HttpServletRequest request, String ssouserid) throws Exception {

		HashMap map = new HashMap();
		HashMap map1 = new HashMap();
		map1.put("ssoUserId", ssouserid);
		map = service.login3(map1);
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
		}
		map.remove("user");
		return map;
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
	 * sso  退出账户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exitlogin.do")
	public ModelAndView exitlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String domain = request.getServerName(); // 得到URL 地址的域名
		if (domain != null) {
			// 去掉域名的第1段， 如aaa.lanqiao.org ，计算后为： .lanqiao.org
			int pos = domain.indexOf(".");
			if (pos != -1) {
				domain = domain.substring(pos);
			}
		}
		//System.out.println ("domain:" + domain);
		//System.out.println ("uuid:" + uuid);
		Cookie cookie = new Cookie("lqtokenkey", null);
		cookie.setDomain(domain);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		request.getSession().setAttribute("loginuser", null);
		//String s = request.getContextPath();
		response.addCookie(cookie);
		return new ModelAndView("redirect:/");
	}

	/**
	 * sso  退出账户
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exitlogin1.do")
	public void exitlogin1(int flag, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (flag == 1) {
			throw new MySQLSyntaxErrorException("MySQLSyntaxErrorException");
		} else if (flag == 2) {
			throw new MySQLDataException("MySQLDataException");
		} else if (flag == 3) {
			throw new IndexOutOfBoundsException("IndexOutOfBoundsException");
		} else if (flag == 4) {
			Map map = null;
			service.login3(map);
		} else if (flag == 5) {
			int i = 0;
			int i1 = 0;
			System.out.println(i / i1);
		}

	}
}

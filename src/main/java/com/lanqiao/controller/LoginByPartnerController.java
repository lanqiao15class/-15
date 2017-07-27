package com.lanqiao.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import weibo4j.Account;
import weibo4j.Users;
import weibo4j.model.WeiboException;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.model.TUserBind;
import com.lanqiao.model.User;
import com.lanqiao.service.UserBindService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.HttpClientUtil;
import com.lanqiao.util.JsonUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;

/**
 * 
* 项目名称:lqzp
* 类名称： LoginByPartnerController.java
* 类描述: 第3方登录,或绑定
* 创建人: chenbaoji
* 创建时间:2016年9月27日 上午10:09:59 

 */
@Controller
public class LoginByPartnerController {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(LoginByPartnerController.class);

	@Resource
	private UserBindService userBindService;

	@Resource
	private UserService userService;

	/**
	 * 
	* @Description:跳转到第三方绑定页面
	* @param mv
	* @param request
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年11月25日 上午10:17:50
	 */
	@RequestMapping("/goAccountBinding.do")
	public ModelAndView goAccountBinding(ModelAndView mv, HttpServletRequest request) {
		//调用sso   查看用户登录信息
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String baseUrl = GlobalConstant.SSOURL;
		paramMap.put("userId", WebUtil.getLoginUser(request).getSsoUserid().toString());
		String str = HttpClientUtil.postRequestResult(baseUrl + "goAccountBinding.do", paramMap);
		System.out.println(str);

		TUserBind qqUserBind = null;
		TUserBind sinaUserBind = null;
		//1.查看qq和微博账号
		List<TUserBind> ubList = new Gson().fromJson(str, new TypeToken<List<TUserBind>>() {
		}.getType());

		if (ubList != null) {
			for (int i = 0; i < ubList.size(); i++) {
				if (ubList.get(i).getOpenidType() == 0) {//qq
					qqUserBind = ubList.get(i);
				}
				if (ubList.get(i).getOpenidType() == 1) {//sina
					sinaUserBind = ubList.get(i);
				}
			}
		}
		mv.addObject("qqBind", qqUserBind);
		mv.addObject("sinaBind", sinaUserBind);
		mv.setViewName("/WEB-INF/view/user/account_binding.jsp");
		return mv;
	}

	/**
	 * 
	* @Description:跳转到绑定成功页面
	* @param mv
	* @param request
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年11月26日 下午2:01:22
	 */
	@RequestMapping("/goBindComplate.do")
	public ModelAndView goBindComplate(ModelAndView mv, HttpServletRequest request, String openidType) {
		mv.addObject("openidType", openidType);//绑定类型
		mv.setViewName("/WEB-INF/view/user/bind_complate.jsp");
		return mv;
	}

	/**
	 * 
	* @Description:公用的回调+数据库添加绑定
	* @param request
	* @param response
	* @return
	* @throws Exception 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年11月25日 上午11:13:33
	 */
	@RequestMapping("/thirdcallback.do")
	public ModelAndView Callback(ModelAndView mv, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		//1.根据bindtype 区分 qq 登录或sina 登录0 : qq  1:ｓｉｎａ
		int bindtype = 0;
		Integer otype = (Integer) request.getSession().getAttribute("bindtype");
		if (otype != null) {
			bindtype = otype.intValue();
			if (bindtype == 0) // qq 授权登录.
			{
				this.QQCallback(request, response, model);
			} else if (bindtype == 1)// sina 微博授权登录. 
			{
				this.SinaCallback(request, response, model);
			}

			String openid = (String) model.get("uid");
			System.out.println("openid=" + openid);

			//根据openid查询数据库
			Map<String, Object> userbindMap = userBindService.bindOpenidCheck(openid);
			if (userbindMap != null) {//存在
				//调用登录方法(根据用户id调用service)
				userService.BindDirectLogin((int) userbindMap.get("user_id"), request);
				//根据用户判断是老师还是学生
				User u = userService.getUserInfoByUserId(Integer.parseInt(userbindMap.get("user_id").toString()));
				if ("0".equals(u.getType())) {
					response.sendRedirect(request.getContextPath() + "/student/home.do");//学生的主页
				} else {
					response.sendRedirect(request.getContextPath() + "/user/home.do");//老师的主页
				}
				return null;
			} else {//不存在
				mv.addObject("openid", openid);
				mv.setViewName("/WEB-INF/view/user/bind_lanqiao.jsp");//跳转到绑定账户页面
			}

			return mv;

		}
		//===========================================================================
		//2.绑定跳转
		//检查数据库是否存在openid,根据结果跳转到不同的页面
		int addbind = 0;
		Integer obind = (Integer) request.getSession().getAttribute("addbind");
		if (obind != null) {
			//1.
			addbind = obind.intValue();//赋值
			if (addbind == 0) // qq 授权登录.
			{
				this.QQCallback(request, response, model);
			} else if (addbind == 1)// sina 微博授权登录. 
			{
				this.SinaCallback(request, response, model);
			}
			//2.数据库添加绑定记录
			SessionUser sUser = WebUtil.getLoginUser(request);
			TUserBind userbind = new TUserBind();
			userbind.setCreatetime(new Date());

			userbind.setOpenid((String) model.get("uid"));
			userbind.setUserId(sUser.getUserId());
			userbind.setOpenidType(addbind);

			userBindService.saveUserBind(userbind);
			//3.
			response.sendRedirect(request.getContextPath() + "/goBindComplate.do?openidType=" + addbind);//跳转到绑定成功界面
			return null;
		}
		return mv;
	}

	/**
	 * sina 微博授权登录后回调的地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void SinaCallback(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model)
			throws Exception {

		String status = "1"; // 0:失败 1：成功 2:未登录 3:已绑定过
		String code = "";
		String uid = "";
		try {
			code = request.getParameter("code");

			weibo4j.Oauth oauth = new weibo4j.Oauth();

			// System.out.println(oauth.getAccessTokenByCode(code));

			weibo4j.http.AccessToken accessTokenObj = oauth.getAccessTokenByCode(code);

			String accessToken = accessTokenObj.getAccessToken();

			weibo4j.Account account = new Account(accessToken);

			weibo4j.org.json.JSONObject uidJson = null;

			uidJson = account.getUid();

			uid = uidJson.getString("uid");

			weibo4j.Users users = new Users(accessToken);
			weibo4j.model.User weiboUser = users.showUserById(uid);
			String username = weiboUser.getName();

			model.put("username", username); // 微博账户的昵称
			model.put("uid", uid); // 微博账户的唯一标识 openid

		} catch (WeiboException e) {

			status = "0";
		}

	}

	/**
	 * qq 授权登录成功后.回调
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void QQCallback(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model)
			throws Exception {

		String openid = "";
		try {

			String username = "";
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

			String accessToken = null;
			long tokenExpireIn = 0L;

			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
				System.out.println("accessTokenObj is null");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();

				request.getSession().setAttribute("demo_access_token", accessToken);
				request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));

				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				// openIDObj.getUserOpenID()
				openid = openIDObj.getUserOpenID();

				//	request.getSession().setAttribute("demo_openid", openID);

				//				UserInfo qzoneUserInfo = new UserInfo(accessToken, openid);
				//				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				//				if (userInfoBean.getRet() == 0) {
				//					userFaceLarge = userInfoBean.getAvatar().getAvatarURL100();
				//					username = userInfoBean.getNickname();
				//				} 
				System.out.println("===openid" + openid);
				model.put("username", username);
				model.put("uid", openid);
			}
		} catch (QQConnectException e) {
			e.printStackTrace();

		}

	}

	/**
	 * 启动 qq 登录. 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/startqqlogin.do")
	public ModelAndView qqlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//	System.out.println ("uploadPath========"+GlobalConstant.uploadPath);
		request.getSession().setAttribute("bindtype", new Integer(0));
		response.sendRedirect(new Oauth().getAuthorizeURL(request));

		return null;

	}

	/**
	 * 启动sina 微博登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/startsinalogin.do")
	public ModelAndView sinalogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("bindtype", new Integer(1));

		weibo4j.Oauth oauth = new weibo4j.Oauth();
		String url = oauth.authorize("code");
		response.sendRedirect(url);
		return null;

	}

	/**
	 * 
	* @Description:添加qq绑定
	* @param request
	* @param response
	* @return
	* @throws Exception 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年11月25日 下午5:03:16
	 */
	@RequestMapping("/addqqbind.do")
	public ModelAndView addqqbind(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//	System.out.println ("uploadPath========"+GlobalConstant.uploadPath);
		request.getSession().setAttribute("addbind", new Integer(0));

		response.sendRedirect(new Oauth().getAuthorizeURL(request));

		return null;

	}

	/**
	 * 
	* @Description:添加新浪绑定
	* @param request
	* @param response
	* @return
	* @throws Exception 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年11月25日 下午5:03:53
	 */
	@RequestMapping("/addsinabind.do")
	public ModelAndView addsinabind(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("addbind", new Integer(1));

		weibo4j.Oauth oauth = new weibo4j.Oauth();
		String url = oauth.authorize("code");
		response.sendRedirect(url);
		return null;

	}

	/**
	 * 
	* @Description:解除绑定
	* @param mv
	* @param request
	* @param openidType
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年11月26日 下午5:14:08
	 */
	@RequestMapping("/telBind.do")
	public void telBind(ModelAndView mv, HttpServletRequest request, Integer openidType, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SessionUser se = WebUtil.getLoginUser(request);
			TUserBind ub = new TUserBind();
			ub.setUserId(se.getUserId());
			ub.setOpenidType(openidType);
			userBindService.deleteByUserIdAndOpenidType(ub);
			//清除session
			request.getSession().removeAttribute("addbind");

			//清空微博的cookie
			//			Cookie SCF=new Cookie("SCF",null); //假如要删除名称为username的Cookie
			//			SCF.setMaxAge(0); //立即删除型
			//			SCF.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
			//			response.addCookie(SCF); //重新写入，将覆盖之前的
			//			Cookie SUB=new Cookie("SUB",null); //假如要删除名称为username的Cookie
			//			SUB.setMaxAge(0); //立即删除型
			//			SUB.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
			//			response.addCookie(SUB); //重新写入，将覆盖之前的
			//			Cookie SUBP=new Cookie("SUBP",null); //假如要删除名称为username的Cookie
			//			SUBP.setMaxAge(0); //立即删除型
			//			SUBP.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
			//			response.addCookie(SUBP); //重新写入，将覆盖之前的
			//			Cookie sso_info=new Cookie("sso_info",null); //假如要删除名称为username的Cookie
			//			sso_info.setMaxAge(0); //立即删除型
			//			sso_info.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
			//			response.addCookie(sso_info); //重新写入，将覆盖之前的
			//			Cookie LT=new Cookie("LT",null); //假如要删除名称为username的Cookie
			//			LT.setMaxAge(0); //立即删除型
			//			LT.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
			//			response.addCookie(LT); //重新写入，将覆盖之前的
			//			Cookie tgc=new Cookie("tgc",null); //假如要删除名称为username的Cookie
			//			tgc.setMaxAge(0); //立即删除型
			//			tgc.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
			//			response.addCookie(tgc); //重新写入，将覆盖之前的
			//			Cookie SUHB=new Cookie("SUHB",null); //假如要删除名称为username的Cookie
			//			SUHB.setMaxAge(0); //立即删除型
			//			SUHB.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
			//			response.addCookie(SUHB); //重新写入，将覆盖之前的

			map.put("code", 200);
		} catch (Exception e) {
			map.put("code", 201);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

}

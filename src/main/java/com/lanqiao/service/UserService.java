package com.lanqiao.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lanqiao.common.SessionUser;
import com.lanqiao.common.SysMenuExt;
import com.lanqiao.common.WebUtil;
import com.lanqiao.dao.StudentMapper;
import com.lanqiao.dao.SysDeparmentMapper;
import com.lanqiao.dao.TUserBindMapper;
import com.lanqiao.dao.TeacherMapper;
import com.lanqiao.dao.UserMapper;
import com.lanqiao.model.Student;
import com.lanqiao.model.SysDeparment;
import com.lanqiao.model.SysFunction;
import com.lanqiao.model.SysMenu;
import com.lanqiao.model.SysRole;
import com.lanqiao.model.TUserBind;
import com.lanqiao.model.TUserDataAuth;
import com.lanqiao.model.Teacher;
import com.lanqiao.model.User;
import com.lanqiao.util.CommonUtil;

@Service
public class UserService extends BaseService {

	private static final Logger logger = LogManager.getLogger(UserService.class);

	// 具有所有的id
	public static int All_Data_ID = -1;

	/**
	 * 保存到redis服务器中.
	 * @param keyname
	 * @param userid
	 * @param ltime
	 */
	public void saveToRedis(String keyname, int userid, long ltime) {
		//this.CacheSet(key, value, expireTime)
		CacheSet(keyname, userid + "", ltime);
	}

	/**
	 * 查询所有的权限
	 * @return
	 */
	public List<SysFunction> getAllFunction() {
		List<SysFunction> list = (List<SysFunction>) dao.selectList("com.lanqiao.dao.UserMapper.getAllFunctions", null);
		return list;
	}

	/**
	 * 登录业务处理,
	 * 
	 * @param paramMap
	 * @return
	 */
	public HashMap<String, Object> login2(Map<String, Object> paramMap) throws Exception {
		String userName = (String) paramMap.get("userName");
		String password = (String) paramMap.get("password");
		User u = null;
		HashMap<String, Object> retmp = new HashMap<String, Object>();
		retmp.put("code", 200);
		retmp.put("msg", "");

		u = (User) dao.selectOne("com.lanqiao.dao.UserMapper.getUserForLogin", paramMap);
		if (u == null) {
			retmp.put("code", 204);
			retmp.put("msg", "登录失败,用户不存在");
			return retmp;
		}

		if (u.getIsvalid() != 1) {
			retmp.put("code", 203);
			retmp.put("msg", "登录失败,用户已经被注销,请与管理员联系");
			return retmp;
		}

		String md5Pwd = CommonUtil.md5Pwd(password, u.getSalt());
		String dbPwd = u.getPassword();
		System.out.println(u.getUserId() + ":" + md5Pwd + ":" + dbPwd + ":" + u.getSalt());
		if (!md5Pwd.equalsIgnoreCase(dbPwd)) {
			// 密码错误
			retmp.put("code", 205);
			retmp.put("msg", "登录失败,用户密码错误.");
			return retmp;
		}

		SessionUser sessUser = getloginSessionUser(u);
		retmp.put("user", sessUser);

		String openid = (String) paramMap.get("openid");

		Integer bindtype = (Integer) paramMap.get("bindtype");

		if (!StringUtils.isEmpty(openid)) {
			// 第3方登录. 
			TUserBindMapper mpbind = dao.getMapper(TUserBindMapper.class);
			TUserBind record = new TUserBind();
			record.setCreatetime(new Date());
			record.setOpenid(openid);
			record.setOpenidType(bindtype);
			record.setUserId(u.getUserId());
			mpbind.insert(record);
		}

		return retmp;
	}

	/**
	 * SSO登录业务处理,
	 * 
	 * @param paramMap
	 * @return
	 */
	public HashMap<String, Object> login3(Map<String, Object> paramMap) throws Exception {
		User u = null;
		HashMap<String, Object> retmp = new HashMap<String, Object>();
		retmp.put("code", 200);
		retmp.put("msg", "");

		List<User> list = (List<User>) dao.selectList("com.lanqiao.dao.UserMapper.loginSSO", paramMap);
		if (list != null && list.size() > 0) {
			a: for (User user : list) {
				StudentMapper studentmapper = dao.getMapper(StudentMapper.class);
				Student s = studentmapper.selectByPrimaryKey(user.getUserId());
				if (s != null && s.getLqClassId() > 0) {
					u = user;
					break a;
				} else {
					u = user;
				}
			}

			SessionUser sessUser = getloginSessionUser(u);
			retmp.put("user", sessUser);
			String openid = (String) paramMap.get("openid");
			Integer bindtype = (Integer) paramMap.get("bindtype");
			if (!StringUtils.isEmpty(openid)) {
				// 第3方登录. 
				TUserBindMapper mpbind = dao.getMapper(TUserBindMapper.class);
				TUserBind record = new TUserBind();
				record.setCreatetime(new Date());
				record.setOpenid(openid);
				record.setOpenidType(bindtype);
				record.setUserId(u.getUserId());
				mpbind.insert(record);
			}
		} else {
			retmp.put("code", 100);
		}

		return retmp;
	}

	/**
	 * 绑定直接登录. 
	 * @param userid : 用户id 
	 * @param request  : 请求的request, 会存储数据到session中. 
	 * @return true:成功, false:失败.
	 */
	public boolean BindDirectLogin(int userid, HttpServletRequest request) throws Exception {
		boolean b = false;
		UserMapper mp = dao.getMapper(UserMapper.class);

		User u = mp.selectByPrimaryKey(userid);

		if (u != null) {

			SessionUser sessUser = getloginSessionUser(u);
			request.getSession().setAttribute("loginuser", sessUser);
			//printLog(sessUser);
			b = true;
		}
		return b;

	}

	/**
	 * 由User  转换为 SessionUser
	 * @param u
	 * @return
	 * @throws Exception
	 */
	private SessionUser getloginSessionUser(User u) throws Exception {

		SessionUser sessUser = CommonUtil.copyBean(u, SessionUser.class);

		// 获取用户的功能列表.
		List<SysFunction> list = (List<SysFunction>) dao.selectList("com.lanqiao.dao.UserMapper.getUserFunctions",
				u.getUserId());

		sessUser.setSysfunctions(list);

		//测试代码.  默认登录后拥有所有权限.
		//sessUser.setSysfunctions(getAllFunction());

		TeacherMapper teachmapper = dao.getMapper(TeacherMapper.class);
		StudentMapper studentmapper = dao.getMapper(StudentMapper.class);
		SysDeparmentMapper depmapper = dao.getMapper(SysDeparmentMapper.class);
		Teacher t = teachmapper.selectByPrimaryKey(u.getUserId());
		Student s = studentmapper.selectByPrimaryKey(u.getUserId());
		if (t != null) {
			SysDeparment dep = depmapper.selectByPrimaryKey(t.getDepId());
			sessUser.setDeparment(dep);
			sessUser.setTeacher(t);
		}
		sessUser.setStudent(s);

		List<SysRole> listrole = (List<SysRole>) dao.selectList("com.lanqiao.dao.UserMapper.getUserRoles",
				u.getUserId());
		sessUser.setRoles(listrole);

		List<SysMenuExt> resultList = new ArrayList<SysMenuExt>();
		List<SysMenu> listmenu = (List<SysMenu>) dao.selectList("com.lanqiao.dao.UserMapper.getAllMenu", u.getUserId());
		if (listmenu != null) {
			HashMap<String, SysMenuExt> hashmenu = new HashMap<String, SysMenuExt>();
			for (SysMenu m : listmenu) {
				SysMenuExt mext = CommonUtil.copyBean(m, SysMenuExt.class);
				hashmenu.put(m.getId(), mext);
			}

			// 整理菜单为一颗树形结构.
			for (SysFunction f : list) {
				String menuids = f.getMenuids();
				if (StringUtils.isEmpty(menuids))
					continue;
				String arrids[] = menuids.split(",");
				for (String mid : arrids) {
					if (StringUtils.isEmpty(mid))
						continue;
					SysMenuExt menuobj = hashmenu.get(mid);
					// 忽略1级菜单.
					if (menuobj.isFirstMenu())
						continue;
					// 先保存1级菜单到结果列表.
					SysMenuExt parentmenu = hashmenu.get(menuobj.getParentId());
					SysMenuExt ntemp = findInList(parentmenu, resultList);
					if (ntemp == null) {
						resultList.add(parentmenu);
					} else {
						parentmenu = ntemp;
					}

					// 如果不存在, 添加到列表中.
					if (findInList(menuobj, parentmenu.getSortSubMenus()) == null)
						parentmenu.getSortSubMenus().add(menuobj);

				}

			}

			// 排序.
			SortMenu(resultList);
			for (SysMenuExt m : resultList) {
				SortMenu(m.getSortSubMenus());
			}
		}

		sessUser.setSysmenus(resultList);
		if (sessUser.getTeacher() != null) {
			// 统计当前登录用户具有的数据id
			//HashMap alids[] = getUserDatads(sessUser.getTeacher());
			//跟组织结构无关的记录id
			//	sessUser.setSelfDataID(alids[0]);
			//跟组织结构有关的记录id
			//sessUser.setAllDataID(alids[1]);
		}
		return sessUser;

	}

	private boolean HasValue(ArrayList<Integer> al, int nval) {
		boolean b = false;
		if (al == null)
			return b;
		for (Integer n : al) {
			if (n.intValue() == nval) {
				b = true;
				break;
			}

		}
		return b;

	}

	/**
	 * 
	 * 获取用户所管理的部门
	 * 可能会多个. 1级别部门+2 级部门.
	 * 普通员工没有管理部门. 
	 *  
	 * @param userid
	 * @return
	 */
	private List<SysDeparment> getDepartmentForAdmin(Teacher user) {
		List<SysDeparment> ret = new ArrayList<SysDeparment>();

		List<SysDeparment> ldata = (List<SysDeparment>) dao.selectList("com.lanqiao.dao.UserMapper.getAllDepartments",
				null);

		for (SysDeparment a : ldata) {
			if (a.getIsvalid() != 1)
				continue;

			String muserid = a.getManagerUserid();
			if (muserid == null)
				muserid = "";
			muserid = muserid.trim();
			String us[] = muserid.split(",");
			for (String s : us) {
				if (s.isEmpty())
					continue;
				int nus = new Integer(s);
				if (nus == user.getUserId()) {
					ret.add(a);
					FindChildDepartment(ret, ldata, a);
				}
			}

		}
		return ret;

	}

	/**	
	 * 找到多个部门里的所有的人. 
	 * @param dlist
	 * @return
	 */
	private List<Teacher> findTeacherByDeparment(List<SysDeparment> dlist) {
		if (dlist == null || dlist.size() == 0)
			return null;
		List<Teacher> ldata = (List<Teacher>) dao.selectList("com.lanqiao.dao.UserMapper.findUserInDeparments", dlist);

		return ldata;
	}

	/**
	 * 
	 * @param dlist
	 * @return
	 */
	private List<TUserDataAuth> findDataIDByTeacher(List<Teacher> dlist) {
		if (dlist == null || dlist.size() == 0)
			return null;
		List<TUserDataAuth> ldata = (List<TUserDataAuth>) dao.selectList("com.lanqiao.dao.UserMapper.findManyDataIDs",
				dlist);
		return ldata;
	}

	private void FindChildDepartment(List<SysDeparment> outlist, List<SysDeparment> alldep, SysDeparment parent) {
		for (SysDeparment d : alldep) {
			if (d.getParentid().intValue() == parent.getDepid().intValue()) {
				outlist.add(d);
				FindChildDepartment(outlist, alldep, d);
			}

		}
	}

	/**
	 * 查询某一用户具有的数据权限id
	 * 
	 * @param userid
	 * @return
	 */
	private HashMap[] getUserDatads(Teacher user) {
		if (user == null)
			return null;
		HashMap hsret[] = new HashMap[2];

		HashMap<Integer, HashSet<Integer>> ret = new HashMap<Integer, HashSet<Integer>>();
		List<TUserDataAuth> ldata = (List<TUserDataAuth>) dao.selectList("com.lanqiao.dao.UserMapper.getUserIds",
				user.getUserId());

		if (ldata != null) {
			for (TUserDataAuth a : ldata) {
				String ids = a.getDataId();
				ids = (ids == null ? "" : ids);
				ids = ids.trim();
				if (!ids.equals("")) {
					String sa[] = ids.split(",");
					HashSet<Integer> al = new HashSet<Integer>();
					for (String s : sa) {
						al.add(new Integer(s));
					}
					ret.put(a.getDataType(), al);

				}

			}
		}

		HashMap<Integer, HashSet<Integer>> selfid = new HashMap<Integer, HashSet<Integer>>();
		for (Map.Entry en : ret.entrySet()) {
			HashSet<Integer> st = (HashSet<Integer>) en.getValue();
			selfid.put((Integer) en.getKey(), (HashSet<Integer>) st.clone());
		}
		hsret[0] = selfid;

		// 获取管理者的记录权限. 
		List<SysDeparment> deplist = getDepartmentForAdmin(user);
		//		 if(deplist !=null)
		//		 {
		//			 System.out.println ("admin deparment:" + deplist.size());
		//			 for(SysDeparment a: deplist)
		//			 System.out.println (a.getDepid() +":" + a.getDepname());
		//		 }
		if (deplist != null && deplist.size() > 0) {
			//部门里的人
			List<Teacher> renlist = findTeacherByDeparment(deplist);
			List<TUserDataAuth> idlist = findDataIDByTeacher(renlist);

			//将下属的记录权限合并到一起.
			if (idlist != null) {
				for (TUserDataAuth a : idlist) {
					String ids = a.getDataId();
					ids = (ids == null ? "" : ids);
					ids = ids.trim();
					if (!ids.equals("")) {
						String sa[] = ids.split(",");
						HashSet<Integer> al = ret.get(a.getDataType());
						if (al == null) {
							al = new HashSet<Integer>();
							ret.put(a.getDataType(), al);
						}
						for (String s : sa) {
							al.add(new Integer(s));
						}

					}

				}
			}

		}
		hsret[1] = ret;

		return hsret;
	}

	/**
	 * 
	 * 查找列表的菜单.
	 * 
	 * @param m
	 * @param resultList
	 * @return
	 * 
	 */
	private SysMenuExt findInList(SysMenuExt m, List<SysMenuExt> list) {

		SysMenuExt b = null;
		for (SysMenuExt t : list) {
			if (t.getId().equals(m.getId())) {
				b = t;
				break;
			}
		}

		return b;
	}

	private SysMenuExt findInList(String mid, List<SysMenuExt> list) {

		SysMenuExt b = null;
		for (SysMenuExt t : list) {
			if (t.getId().equals(mid)) {
				b = t;
				break;
			}
		}

		return b;
	}

	private void SortMenu(List<SysMenuExt> list) {
		Collections.sort(list, new Comparator<SysMenuExt>() {
			public int compare(SysMenuExt b1, SysMenuExt b2) {
				return b1.getSort().compareTo(b2.getSort());
			}

		});

	}

	/**
	 * 
	* @Description:手机注册-验证手机号是否存在
	* @param loginTel
	* @return 
	* @return Map<String,Object> 
	* @author ZhouZhiHua
	* @createTime 2016年11月23日 下午2:54:06
	 */
	public Map<String, Object> loginTelCheck(String login_tel) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.UserMapper.loginTelCheck", login_tel);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	//修改用户个人信息
	public int updateUserInfo(User user, HttpServletRequest request, SessionUser sessionUser) {
		UserMapper userMapper = dao.getMapper(UserMapper.class);
		int row1 = userMapper.updateByPrimaryKeySelective(user);
		return row1;
	}

	public int updateByPrimaryKeySelective(User record) throws Exception {
		UserMapper userMapper = dao.getMapper(UserMapper.class);
		return userMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 
	* @Description: 根据手机号注册/根据邮箱注册
	* @param user
	* @param student
	* @throws Exception 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月24日 上午11:23:27
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveRegister(User user, Student student) throws Exception {
		//1.
		dao.insert("com.lanqiao.dao.UserMapper.insertSelectiveReturnKey", user);
		//2.
		student.setUserId(user.getUserId());
		dao.insert("com.lanqiao.dao.StudentMapper.insertSelective", student);
		//3.绑定角色
		//SysUserRole sysUserRole=new SysUserRole();
		//sysUserRole.setRoleId(GlobalConstant.roleId);
		//sysUserRole.setUserId(user.getUserId()+"");
		//dao.insert("com.lanqiao.dao.SysUserRoleMapper.insert", sysUserRole);
	}

	/**
	 * 
	* @Description:手机注册-验证邮箱是否存在
	* @param loginEmail
	* @return 
	* @return Map<String,Object> 
	* @author ZhouZhiHua
	* @createTime 2016年11月24日 下午1:56:43
	 */
	public Map<String, Object> loginEmailCheck(String loginEmail) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.UserMapper.loginEmailCheck", loginEmail);
		//List<Map<String, Object>> list = userMapper.loginEmailCheck(loginEmail);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 *@description:计算使用该邮箱或手机登录的数据条数
	 *@param map【参数是type(‘1’代表手机，‘2’代表邮箱 ，  ‘3’代表两者) , 具体的值(邮箱或手机号码)】
	 *@return
	 *@return int 
	 *@author:ZhuDiaoHua
	 *@2016年11月25日上午12:32:12
	 *
	 */
	public int existedCount(Map<String, Object> map) {
		//		dao.selectList("com.lanqiao.dao.UserMapper.existedCount", map);

		UserMapper userMapper = dao.getMapper(UserMapper.class);
		return userMapper.existedCount(map);
	}

	/**
	 * 修改密码旧密码检查
	 */
	public boolean oldPwdCheck(String oldPwd, HttpServletRequest request) {
		SessionUser sessionUser = WebUtil.getLoginUser(request);
		String salt = sessionUser.getSalt();
		String savedPwd = sessionUser.getPassword();
		String inputRealPwd = CommonUtil.md5Pwd(oldPwd, salt);
		return inputRealPwd.equals(savedPwd);
	}

	/**
	 * 修改密码
	 */
	public void updatePwd(Integer userId, String pwd) throws Exception {
		UserMapper userMapper = dao.getMapper(UserMapper.class);
		User user = new User();
		user.setUserId(userId);
		String salt = CommonUtil.randomSalt();
		user.setSalt(salt);
		user.setPassword(CommonUtil.md5Pwd(pwd, salt));
		userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 检查短信验证码
	 */
	public boolean msgVerificationCodeCheck(String validateCode, String type, HttpServletRequest request) {
		String savedMsgValidateCode = (String) request.getSession().getAttribute("pwd_verificationCode");
		String savedEmailValidateCode = (String) request.getSession().getAttribute("verificationCode");
		if ("smValidCode".equals(type)) {//短信验证
			if (StringUtils.isEmpty(validateCode)
					|| StringUtils.isEmpty(savedMsgValidateCode)
					|| ((validateCode != null && !"".equals(validateCode))
							&& (savedMsgValidateCode != null && !"".equals(savedMsgValidateCode)) && !validateCode
							.toUpperCase().equals(savedMsgValidateCode.toUpperCase()))) {
				return false;
			}
		} else {//邮件验证
			if (StringUtils.isEmpty(validateCode)
					|| StringUtils.isEmpty(savedEmailValidateCode)
					|| ((validateCode != null && !"".equals(validateCode))
							&& (savedEmailValidateCode != null && !"".equals(savedEmailValidateCode)) && !validateCode
							.toUpperCase().equals(savedEmailValidateCode.toUpperCase()))) {
				return false;
			}
		}
		return true;
	}

	public boolean msgVerificationCodeCheckZh(String validateCode, String type, HttpServletRequest request) {
		String savedMsgValidateCode = (String) request.getSession().getAttribute("pwd_verificationCode");
		String savedEmailValidateCode = (String) request.getSession().getAttribute("verificationCode");
		if ("0".equals(type)) {//短信验证
			if (StringUtils.isEmpty(validateCode)
					|| StringUtils.isEmpty(savedMsgValidateCode)
					|| ((validateCode != null && !"".equals(validateCode))
							&& (savedMsgValidateCode != null && !"".equals(savedMsgValidateCode)) && !validateCode
							.toUpperCase().equals(savedMsgValidateCode.toUpperCase()))) {
				return false;
			}
		} else {//邮件验证
			if (StringUtils.isEmpty(validateCode)
					|| StringUtils.isEmpty(savedEmailValidateCode)
					|| ((validateCode != null && !"".equals(validateCode))
							&& (savedEmailValidateCode != null && !"".equals(savedEmailValidateCode)) && !validateCode
							.toUpperCase().equals(savedEmailValidateCode.toUpperCase()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	* @Description:根据用户id查询用户信息
	* @param userId
	* @return 
	* @return User 
	* @author ZhouZhiHua
	* @createTime 2016年11月29日 下午6:03:21
	 */
	public User getUserInfoByUserId(Integer userId) {
		User user = (User) dao.selectOne("com.lanqiao.dao.UserMapper.getUserInfoByUserId", userId);
		return user;
	}

	/**
	 * 
	* @Description:根据用户id修改用户信息
	* @param map 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月1日 上午10:53:58
	 */
	public void updateUserInfo(Map<String, Object> map) {
		User user = new User();
		user.setUserId(Integer.parseInt(map.get("userId").toString()));
		if (map.get("realName") != null) {
			user.setRealName(map.get("realName").toString());
			dao.update("com.lanqiao.dao.UserMapper.updateByPrimaryKeySelective", user);
		}
		if (map.get("idCard") != null) {
			user.setIdCard(map.get("idCard").toString());
			String birthTemp = map.get("idCard").toString().substring(6, 14);
			birthTemp = birthTemp.substring(0, 4) + "-" + birthTemp.substring(4, 6) + "-" + birthTemp.substring(6);
			user.setBirth(CommonUtil.dateStrToDate(birthTemp));
			dao.update("com.lanqiao.dao.UserMapper.updateByPrimaryKeySelective", user);
		}
		if (map.get("tel") != null) {
			user.setTel(map.get("tel").toString());
			dao.update("com.lanqiao.dao.UserMapper.updateByPrimaryKeySelective", user);
		}
		if (map.get("email") != null) {
			user.setEmail(map.get("email").toString());
			dao.update("com.lanqiao.dao.UserMapper.updateByPrimaryKeySelective", user);
		}
		if (map.get("sex") != null) {
			user.setSex(map.get("sex").toString());
			dao.update("com.lanqiao.dao.UserMapper.updateByPrimaryKeySelective", user);
		}
		if (map.get("qq") != null) {
			user.setQq(map.get("qq").toString());
			dao.update("com.lanqiao.dao.UserMapper.updateByPrimaryKeySelective", user);
		}
		if (map.get("nation") != null) {
			user.setNation(map.get("nation").toString());
			dao.update("com.lanqiao.dao.UserMapper.updateByPrimaryKeySelective", user);
		}
	}

	/**
	 *
	 *@description:根据id获取用户真实姓名 :电话
	 *@param userId
	 *@return
	 *@return Map<String,Object> 
	 *@author:ZhuDiaoHua
	 *@2016年12月5日下午7:32:34
	 *
	 */
	public String getInfoById(Integer userId) {
		UserMapper userMapper = dao.getMapper(UserMapper.class);
		return userMapper.getInfoById(userId);
	}

	/**
	 * 
	* @Description:检查用户是否存在
	* @param userName
	* @return 
	* @return Map<String,Object> 
	* @author ZhouZhiHua
	* @createTime 2016年12月8日 下午3:02:25
	 */
	public Map<String, Object> userNameCheck(String userName) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.UserMapper.getUserListByUserName", userName);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	* @Description:检查验证码
	* @param validateCode
	* @param type
	* @param request
	* @return 
	* @return boolean 
	* @author ZhouZhiHua
	* @createTime 2016年12月8日 下午3:06:43
	 */
	public boolean validateCodeCheck(String validateCode, String type, HttpServletRequest request) {
		String savedValidateCode = (String) request.getSession().getAttribute(type);
		if (StringUtils.isEmpty(validateCode)
				|| (StringUtils.isNotEmpty(validateCode) && StringUtils.isNotEmpty(savedValidateCode) && !validateCode
						.toUpperCase().equals(savedValidateCode.toUpperCase()))) {
			return false;
		}
		return true;
	}

	//根据userId获取realname
	public String getRealNameById(Integer userId) {
		UserMapper mapper = dao.getMapper(UserMapper.class);
		return mapper.getRealNameById(userId);
	}

	public User getUserByuserid(Integer userId) {
		UserMapper mapper = dao.getMapper(UserMapper.class);
		return mapper.selectByPrimaryKey(userId);

	}

	public List<User> getallUser() {
		UserMapper mapper = dao.getMapper(UserMapper.class);
		return mapper.getallUser();
	}

	public void updateUser(User user) {
		UserMapper mapper = dao.getMapper(UserMapper.class);
		int i = mapper.updateByPrimaryKeySelective(user);
		if (i > 0) {

		} else {

			logger.error(user.getLoginTel() + "   " + user.getEmail() + "    " + user.getLoginEmail() + "  "
					+ user.getTel() + "    添加User表失败");
		}
	}

	/**
	 * 根据身份证查询用户是否已通过缴费  防止重复注册
	 */

	public Integer getidCard(Map<String, Object> map) {
		UserMapper mapper = dao.getMapper(UserMapper.class);
		Integer getidCard = mapper.getidCard(map);
		return getidCard;
	}
}

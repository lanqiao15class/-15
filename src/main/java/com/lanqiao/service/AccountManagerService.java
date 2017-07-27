/*
 * File name:          accountManagerService.java
 * Copyright@Handkoo (China)
 * Editor:           JDK1.6.32
 */
package com.lanqiao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanqiao.dao.SysDeparmentMapper;
import com.lanqiao.dao.SysRoleFunctionMapper;
import com.lanqiao.dao.SysRoleMapper;
import com.lanqiao.dao.SysUserRoleMapper;
import com.lanqiao.dao.TeacherMapper;
import com.lanqiao.dao.UserMapper;
import com.lanqiao.model.SysDeparment;
import com.lanqiao.model.SysRole;
import com.lanqiao.model.SysRoleFunction;
import com.lanqiao.model.SysUserRole;
import com.lanqiao.model.Teacher;
import com.lanqiao.model.User;
import com.lanqiao.util.CommonUtil;

/**
 * TODO: File comments
 * <p>
 * <p>
 * Author:           卢锡文
 * <p>
 * Date:           2017年4月11日
 * <p>
 * Time:           上午10:05:04
 * <p>
 * Director:        卢锡文
 * <p>
 * <p>
 */
@Service(value = "accountManagerService")
public class AccountManagerService extends BaseService {
	/**
	 * 查询该用户所创建的所有角色
	 */
	public Map<String, Object> getRoles(Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		SysRoleMapper mapper = dao.getMapper(SysRoleMapper.class);

		List<SysRole> roles = mapper.getRoles(userId);
		map.put("Roles", roles);
		for (SysRole role : roles) {
			List<Map<String, Object>> functionlist = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> functions = mapper.roleIdGetFunction(role.getId());

			for (Map<String, Object> map2 : functions) {
				//向上递归
				List<Map<String, Object>> list = getPFunction((Integer) map2.get("pId"));
				System.out.println("        list     " + list.toString());
				functionlist.addAll(list);
			}
			functionlist.addAll(functions);

			//取出重复的数据
			List<Map<String, Object>> listMap2 = new LinkedList<Map<String, Object>>();
			Set<Map> setMap = new HashSet<Map>();
			for (Map<String, Object> map1 : functionlist) {
				if (setMap.add(map1)) {
					listMap2.add(map1);
				}
			}
			map.put(role.getId(), functionlist);
		}
		return map;
	}

	/**
	 * 向上递归
	 * 
	 */
	public List<Map<String, Object>> getPFunction(Integer id) {
		System.out.println("            id        " + id);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SysRoleMapper mapper = dao.getMapper(SysRoleMapper.class);

		Map<String, Object> map = mapper.functionbyPid(id);
		if (map != null) {
			list.add(map);
			if ((Integer) map.get("pId") != 0) {
				getPFunction((Integer) map.get("pId"));
			}

		}
		return list;
	}

	/**
	 * 根据部门查找用户和角色
	 * @return
	 */
	public List<Map<String, Object>> getUserByDepartment(Map<String, Object> paramMap) {
		SysDeparmentMapper mapper = dao.getMapper(SysDeparmentMapper.class);
		List<Map<String, Object>> maps = mapper.getUserByDepartment(paramMap);
		int i = 1;
		for (Map<String, Object> map2 : maps) {
			map2.put("indexNo", i);
			if (CommonUtil.isNotNull(map2.get("sex")) && StringUtils.equals("1", map2.get("sex").toString())) {
				map2.put("sex1", "女");
			} else if (CommonUtil.isNotNull(map2.get("sex")) && StringUtils.equals("0", map2.get("sex").toString())) {
				map2.put("sex1", "男");
			} else {
				map2.put("sex1", "");
			}
			if (CommonUtil.isNotNull(map2.get("isvalid")) && StringUtils.equals("1", map2.get("isvalid").toString())) {
				map2.put("isvalidName", "有效");
			} else {
				map2.put("isvalidName", "无效");
			}
			i++;

			//查询该用户所对用的角色
			SysRoleMapper mapper1 = dao.getMapper(SysRoleMapper.class);

			List<Map<String, Object>> rolesName = mapper1.getRolesName((Integer) map2.get("user_id"));
			String name = "";
			String id = "";
			for (Map<String, Object> map : rolesName) {
				name = name + "," + map.get("name");
				id = id + "," + map.get("id");
			}
			if (StringUtils.isNotBlank(id)) {
				name = name.substring(1);
				id = id.substring(1);
			}
			map2.put("roleName", name);
			map2.put("roleId", id);
		}
		return maps;
	}

	/**
	 * 根据角色查找用户和角色
	 * @return
	 */
	public List<Map<String, Object>> getUserByRole(Map<String, Object> paramMap) {
		//根据角色id查询角色
		SysRoleMapper mapper1 = dao.getMapper(SysRoleMapper.class);
		SysRole role = mapper1.selectByPrimaryKey((String) paramMap.get("roleId"));

		SysDeparmentMapper mapper = dao.getMapper(SysDeparmentMapper.class);
		List<Map<String, Object>> maps = mapper.getUserByRole(paramMap);
		int i = 1;
		for (Map<String, Object> map2 : maps) {
			map2.put("indexNo", i);
			if (StringUtils.equals("1", map2.get("sex").toString())) {
				map2.put("sex1", "女");
			} else {
				map2.put("sex1", "男");
			}
			if (StringUtils.equals("1", map2.get("isvalid").toString())) {
				map2.put("isvalidName", "有效");
			} else {
				map2.put("isvalidName", "无效");
			}

			//根据部门id查询部门
			if (map2.get("dep_id") != null) {
				SysDeparment deparment = mapper.selectByPrimaryKey((Integer) map2.get("dep_id"));
				if (deparment != null) {
					map2.put("depname", deparment.getDepname());
				} else {
					map2.put("depname", "");
				}
			} else {

				map2.put("depname", "");
			}
			map2.put("roleName", role.getName());
			i++;
		}
		return maps;
	}

	/**
	 * 根据用户查找用户总记录数
	 * @return
	 */
	public Integer getUserByRoleCount(Map<String, Object> paramMap) {
		SysDeparmentMapper mapper = dao.getMapper(SysDeparmentMapper.class);
		Integer i = mapper.getUserByRoleCount(paramMap);

		return i;
	}

	/**
	 * 重置密码
	 * @return
	 */
	public Integer resetPassword(Map<String, Object> paramMap) {
		SysRoleMapper mapper = dao.getMapper(SysRoleMapper.class);
		Integer i = mapper.resetPassword(paramMap);
		return i;
	}

	/**
	 * 账号是否有效
	 * @return
	 */
	public Integer userIsvalid(User user) {
		UserMapper mapper = dao.getMapper(UserMapper.class);
		Integer i = mapper.updateByPrimaryKeySelective(user);
		return i;
	}

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@Transactional
	public boolean addUser(User user, String departmentId, String[] roleId) {
		//添加用户
		UserMapper mapper = dao.getMapper(UserMapper.class);
		int i = mapper.insertUserAccount(user);
		if (i > 0) {

		} else {
			return false;

		}
		//添加部门
		Teacher teacher = new Teacher();
		teacher.setUserId(user.getUserId());
		teacher.setCreatetime(new Date());
		teacher.setDepId(Integer.valueOf(departmentId));
		TeacherMapper mapper2 = dao.getMapper(TeacherMapper.class);
		int i1 = mapper2.insertSelective(teacher);
		if (i1 > 0) {

		} else {
			return false;

		}
		//添加角色
		if (roleId != null && roleId.length > 0) {
			for (String s : roleId) {
				SysUserRole role = new SysUserRole();
				role.setRoleId(s);
				role.setUserId(user.getUserId().toString());
				SysUserRoleMapper mapper3 = dao.getMapper(SysUserRoleMapper.class);
				int insertSelective = mapper3.insertSelective(role);
				if (insertSelective > 0) {

				} else {
					return false;

				}
			}
		}
		return true;
	}

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@Transactional
	public boolean updateUser(User user, String departmentId, String[] roleId) {
		//添加用户
		UserMapper mapper = dao.getMapper(UserMapper.class);
		int i = mapper.updateByPrimaryKeySelective(user);
		if (i > 0) {

		} else {
			return false;

		}
		//添加部门
		Teacher teacher = new Teacher();
		teacher.setUserId(user.getUserId());
		teacher.setDepId(Integer.valueOf(departmentId));
		TeacherMapper mapper2 = dao.getMapper(TeacherMapper.class);
		int i1 = mapper2.updateByPrimaryKeySelective(teacher);
		if (i1 > 0) {

		} else {
			return false;

		}
		//添加角色
		//先删除
		SysRoleMapper mapper4 = dao.getMapper(SysRoleMapper.class);
		mapper4.deleteByUserId(user.getUserId().toString());
		if (roleId != null && roleId.length > 0) {
			for (String s : roleId) {
				SysUserRole role = new SysUserRole();
				role.setRoleId(s);
				role.setUserId(user.getUserId().toString());
				SysUserRoleMapper mapper3 = dao.getMapper(SysUserRoleMapper.class);
				int insertSelective = mapper3.insertSelective(role);
				if (insertSelective > 0) {

				} else {
					return false;

				}
			}
		}
		return true;
	}

	/**
	 * 根据用户id查询用户和角色
	 * @param user
	 * @return
	 */
	public Map<String, Object> selUserAndRoleByUserId(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		//查询用户
		UserMapper mapper = dao.getMapper(UserMapper.class);
		User user = mapper.selectByPrimaryKey(Integer.valueOf(id));
		map.put("user", user);
		//查询t_teacher
		TeacherMapper mapper2 = dao.getMapper(TeacherMapper.class);
		Teacher teacher = mapper2.selectByPrimaryKey(Integer.valueOf(id));
		map.put("teacher", teacher);
		//查询部门
		SysDeparmentMapper mapper3 = dao.getMapper(SysDeparmentMapper.class);
		SysDeparment deparment = mapper3.selectByPrimaryKey(teacher.getDepId());
		map.put("deparment", deparment);
		//查询角色
		SysRoleMapper mapper4 = dao.getMapper(SysRoleMapper.class);
		List<Map<String, Object>> rolesName = mapper4.getRolesName(Integer.valueOf(id));
		map.put("rolesName", rolesName);
		//角色拥有的功能
		List<Map<String, Object>> functionlist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> role : rolesName) {
			List<Map<String, Object>> functions = mapper4.roleIdGetFunction(((String) role.get("id")));

			for (Map<String, Object> map2 : functions) {
				//向上递归
				List<Map<String, Object>> list = getPFunction((Integer) map2.get("pId"));
				System.out.println("        list     " + list.toString());
				functionlist.addAll(list);
			}
			functionlist.addAll(functions);
		}

		List<Map<String, Object>> listMap2 = new LinkedList<Map<String, Object>>();
		Set<Map> setMap = new HashSet<Map>();
		for (Map<String, Object> map1 : functionlist) {
			if (setMap.add(map1)) {
				listMap2.add(map1);
			}
		}

		map.put("fucntion", listMap2);
		return map;

	}

	/**
	 * 判断用户名密码是否重复
	 */

	public int getuserByuserTelOrEmail(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserMapper mapper = dao.getMapper(UserMapper.class);
		map.put("loginTel", user.getLoginTel());
		map.put("loginEmail", user.getLoginEmail());
		List<User> list = mapper.loginAccount(map);
		if (list != null && list.size() > 0) {
			for (User user2 : list) {
				if (user.getUserId() != null) {
					if (StringUtils.isNotBlank(user.getLoginTel())) {
						if (StringUtils.equals(user2.getLoginTel(), user.getLoginTel())
								&& !StringUtils.equals(user2.getUserId().toString(), user.getUserId().toString())) {
							return 103;

						}
					}

					if (StringUtils.equals(user2.getLoginEmail(), user.getLoginEmail())
							&& !StringUtils.equals(user2.getUserId().toString(), user.getUserId().toString())) {

						return 104;

					}
				} else {
					if (StringUtils.isNotBlank(user.getLoginTel())) {
						if (StringUtils.equals(user2.getLoginTel(), user.getLoginTel())) {
							return 403;
						}
					}
					if (StringUtils.equals(user2.getLoginEmail(), user.getLoginEmail())) {
						return 404;

					}
				}

			}
		} else {
			return 200;

		}
		return 200;
	}

	/**
	 * 添加角色
	 * @param role
	 * @param functionid
	 * @return
	 */
	public Map<String, Object> addRole(SysRole role, String functionid) {
		SysRoleMapper mapper = dao.getMapper(SysRoleMapper.class);
		int i = mapper.insertSelective(role);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		if (i > 0) {
			if (StringUtils.isNotBlank(functionid)) {
				for (String s : functionid.substring(1).split(",")) {
					SysRoleFunction function = new SysRoleFunction();
					function.setFunctionid(Integer.valueOf(s));
					function.setRoleid(role.getId());
					SysRoleFunctionMapper mapper2 = dao.getMapper(SysRoleFunctionMapper.class);
					mapper2.insertSelective(function);
				}
			}

			map.put("code", 200);
			map.put("roleId", role.getId());
			map.put("roleName", role.getName());
		} else {
			map.put("code", 101);

		}
		return map;

	}

	/**
	 * 查看角色所拥有的的权限
	 * @param user
	 * @return
	 */
	public Map<String, Object> getFunctionByRoleId(String roleId) {
		SysRoleMapper mapper = dao.getMapper(SysRoleMapper.class);
		List<Map<String, Object>> roleIdGetFunction = mapper.roleIdGetFunction(roleId);
		List<Map<String, Object>> functionlist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map2 : roleIdGetFunction) {
			//向上递归
			List<Map<String, Object>> list = getPFunction((Integer) map2.get("pId"));
			System.out.println("        list     " + list.toString());
			functionlist.addAll(list);
		}
		functionlist.addAll(roleIdGetFunction);

		//取出重复的数据
		List<Map<String, Object>> listMap2 = new LinkedList<Map<String, Object>>();
		Set<Map> setMap = new HashSet<Map>();
		for (Map<String, Object> map1 : functionlist) {
			if (setMap.add(map1)) {
				listMap2.add(map1);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fucntion", listMap2);
		//获取roleNmae
		SysRole role = mapper.selectByPrimaryKey(roleId);
		map.put("roleName", role.getName());
		return map;

	}

	/**
	 * 添加角色
	 * @param role
	 * @param functionid
	 * @return
	 */
	public Map<String, Object> updateRole(SysRole role, String functionid) {
		SysRoleMapper mapper = dao.getMapper(SysRoleMapper.class);
		int i = mapper.updateByPrimaryKeySelective(role);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 200);
		//删除
		mapper.deleteByRoleId(role.getId());
		if (i > 0) {
			if (StringUtils.isNotBlank(functionid)) {
				for (String s : functionid.substring(1).split(",")) {
					SysRoleFunction function = new SysRoleFunction();
					function.setFunctionid(Integer.valueOf(s));
					function.setRoleid(role.getId());
					SysRoleFunctionMapper mapper2 = dao.getMapper(SysRoleFunctionMapper.class);
					mapper2.insertSelective(function);
				}
			}

			map.put("code", 400);
			map.put("roleId", role.getId());
			map.put("roleName", role.getName());
		} else {
			map.put("code", 102);

		}
		return map;

	}

}

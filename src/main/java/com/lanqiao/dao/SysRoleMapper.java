package com.lanqiao.dao;

import java.util.List;
import java.util.Map;

import com.lanqiao.model.SysRole;

public interface SysRoleMapper {
	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table sys_role
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table sys_role
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	int insert(SysRole record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table sys_role
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	int insertSelective(SysRole record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table sys_role
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	SysRole selectByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table sys_role
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	int updateByPrimaryKeySelective(SysRole record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table sys_role
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	int updateByPrimaryKey(SysRole record);

	/**
	 * 查询该用户所创建的所有角色
	 */
	List<SysRole> getRoles(Integer userId);

	/**
	 * 查询该用户所创建的所有角色
	 */
	List<Map<String, Object>> getRolesName(Integer userId);

	//重置密码
	public int resetPassword(Map<String, Object> map);

	/**
	 * 查询该角色所对应的功能
	 */
	public List<Map<String, Object>> roleIdGetFunction(String roleId);

	/**
	 *根据父id查询功能
	 */
	public Map<String, Object> functionbyPid(Integer roleId);

	int deleteByUserId(String userId);

	int deleteByRoleId(String roleId);
}
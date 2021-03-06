package com.lanqiao.dao;

import java.util.List;
import java.util.Map;

import com.lanqiao.model.User;

public interface UserMapper {
	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table t_user
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	int deleteByPrimaryKey(Integer userId);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table t_user
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	int insert(User record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table t_user
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	int insertSelective(User record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table t_user
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	User selectByPrimaryKey(Integer userId);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table t_user
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	int updateByPrimaryKeySelective(User record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table t_user
	 *
	 * @mbggenerated Sat Nov 19 14:42:38 CST 2016
	 */
	int updateByPrimaryKey(User record);

	//计算使用该email或者tel的数据条数
	int existedCount(Map<String, Object> map);

	//添加意向学员 zzx
	int zzxInsertSelective(User user);

	//根据id获取用户真实姓名 :电话
	String getInfoById(Integer userId);

	//根据userId获取realname
	String getRealNameById(Integer userId);

	int insertUserAccount(User user);

	List<User> loginAccount(Map<String, Object> map);

	List<User> getallUser();

	public Integer getidCard(Map<String, Object> map);

}
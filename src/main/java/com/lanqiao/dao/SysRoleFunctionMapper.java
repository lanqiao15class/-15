package com.lanqiao.dao;

import com.lanqiao.model.SysRoleFunction;

public interface SysRoleFunctionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_function
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_function
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insert(SysRoleFunction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_function
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insertSelective(SysRoleFunction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_function
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    SysRoleFunction selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_function
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKeySelective(SysRoleFunction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_role_function
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKey(SysRoleFunction record);
}
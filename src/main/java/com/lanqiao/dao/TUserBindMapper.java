package com.lanqiao.dao;

import com.lanqiao.model.TUserBind;

public interface TUserBindMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_bind
     *
     * @mbggenerated Mon Nov 21 16:31:35 CST 2016
     */
    int deleteByPrimaryKey(Integer nnid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_bind
     *
     * @mbggenerated Mon Nov 21 16:31:35 CST 2016
     */
    int insert(TUserBind record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_bind
     *
     * @mbggenerated Mon Nov 21 16:31:35 CST 2016
     */
    int insertSelective(TUserBind record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_bind
     *
     * @mbggenerated Mon Nov 21 16:31:35 CST 2016
     */
    TUserBind selectByPrimaryKey(Integer nnid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_bind
     *
     * @mbggenerated Mon Nov 21 16:31:35 CST 2016
     */
    int updateByPrimaryKeySelective(TUserBind record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_bind
     *
     * @mbggenerated Mon Nov 21 16:31:35 CST 2016
     */
    int updateByPrimaryKey(TUserBind record);
}
package com.lanqiao.dao;

import com.lanqiao.model.Teacher;

public interface TeacherMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insert(Teacher record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insertSelective(Teacher record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    Teacher selectByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKeySelective(Teacher record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKey(Teacher record);
}
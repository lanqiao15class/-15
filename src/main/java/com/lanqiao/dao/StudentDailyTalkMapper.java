package com.lanqiao.dao;

import com.lanqiao.model.StudentDailyTalk;

public interface StudentDailyTalkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_daily_talk
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int deleteByPrimaryKey(Integer dtId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_daily_talk
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int insert(StudentDailyTalk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_daily_talk
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int insertSelective(StudentDailyTalk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_daily_talk
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    StudentDailyTalk selectByPrimaryKey(Integer dtId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_daily_talk
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int updateByPrimaryKeySelective(StudentDailyTalk record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_daily_talk
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int updateByPrimaryKey(StudentDailyTalk record);
}
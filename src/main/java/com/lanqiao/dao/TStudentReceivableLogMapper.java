package com.lanqiao.dao;

import com.lanqiao.model.TStudentReceivableLog;

public interface TStudentReceivableLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_receivable_log
     *
     * @mbggenerated Mon Dec 12 12:08:33 CST 2016
     */
    int deleteByPrimaryKey(Integer studentReceivableLogId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_receivable_log
     *
     * @mbggenerated Mon Dec 12 12:08:33 CST 2016
     */
    int insert(TStudentReceivableLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_receivable_log
     *
     * @mbggenerated Mon Dec 12 12:08:33 CST 2016
     */
    int insertSelective(TStudentReceivableLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_receivable_log
     *
     * @mbggenerated Mon Dec 12 12:08:33 CST 2016
     */
    TStudentReceivableLog selectByPrimaryKey(Integer studentReceivableLogId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_receivable_log
     *
     * @mbggenerated Mon Dec 12 12:08:33 CST 2016
     */
    int updateByPrimaryKeySelective(TStudentReceivableLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_student_receivable_log
     *
     * @mbggenerated Mon Dec 12 12:08:33 CST 2016
     */
    int updateByPrimaryKey(TStudentReceivableLog record);
}
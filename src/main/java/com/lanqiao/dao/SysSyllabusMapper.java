package com.lanqiao.dao;

import com.lanqiao.model.SysSyllabus;

public interface SysSyllabusMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_syllabus
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int deleteByPrimaryKey(Integer syllabusId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_syllabus
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int insert(SysSyllabus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_syllabus
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int insertSelective(SysSyllabus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_syllabus
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    SysSyllabus selectByPrimaryKey(Integer syllabusId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_syllabus
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int updateByPrimaryKeySelective(SysSyllabus record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_syllabus
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int updateByPrimaryKey(SysSyllabus record);
}
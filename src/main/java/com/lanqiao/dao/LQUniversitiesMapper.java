package com.lanqiao.dao;

import com.lanqiao.model.LQUniversities;

public interface LQUniversitiesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lq_universities
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int deleteByPrimaryKey(Long univCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lq_universities
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insert(LQUniversities record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lq_universities
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insertSelective(LQUniversities record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lq_universities
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    LQUniversities selectByPrimaryKey(Long univCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lq_universities
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKeySelective(LQUniversities record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lq_universities
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKey(LQUniversities record);
}
package com.lanqiao.dao;

import java.util.Map;

import com.lanqiao.model.TStuno;

public interface TStunoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_stuno
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int deleteByPrimaryKey(Integer stunoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_stuno
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insert(TStuno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_stuno
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insertSelective(TStuno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_stuno
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    TStuno selectByPrimaryKey(Integer stunoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_stuno
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKeySelective(TStuno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_stuno
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKey(TStuno record);
    
    
    //当前年份记录的条数
    int selectByYears(String year);
    
    //获取某年某种课程的数据
    TStuno selectByCouseTypeAndYear(Map<String, Object> paramMap);
    
    //更新数据
    void updateByCouseTypeAndYear(Map<String, Object> paramMap);
}
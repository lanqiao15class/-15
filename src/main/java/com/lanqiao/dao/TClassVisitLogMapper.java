package com.lanqiao.dao;

import com.lanqiao.model.TClassVisitLog;

public interface TClassVisitLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_class_visit_log
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    int deleteByPrimaryKey(Integer visitId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_class_visit_log
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    int insert(TClassVisitLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_class_visit_log
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    int insertSelective(TClassVisitLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_class_visit_log
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    TClassVisitLog selectByPrimaryKey(Integer visitId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_class_visit_log
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    int updateByPrimaryKeySelective(TClassVisitLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_class_visit_log
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(TClassVisitLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_class_visit_log
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    int updateByPrimaryKey(TClassVisitLog record);
}
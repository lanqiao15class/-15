package com.lanqiao.dao;

import com.lanqiao.model.TVisit_log;

public interface TVisit_logMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_visit_log
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int deleteByPrimaryKey(Integer nnid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_visit_log
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insert(TVisit_log record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_visit_log
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insertSelective(TVisit_log record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_visit_log
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    TVisit_log selectByPrimaryKey(Integer nnid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_visit_log
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKeySelective(TVisit_log record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_visit_log
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(TVisit_log record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_visit_log
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKey(TVisit_log record);
}
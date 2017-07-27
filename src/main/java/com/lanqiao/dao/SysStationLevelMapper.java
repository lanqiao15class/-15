package com.lanqiao.dao;

import com.lanqiao.model.SysStationLevel;

public interface SysStationLevelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_station_level
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int deleteByPrimaryKey(Integer levelid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_station_level
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insert(SysStationLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_station_level
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insertSelective(SysStationLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_station_level
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    SysStationLevel selectByPrimaryKey(Integer levelid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_station_level
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKeySelective(SysStationLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_station_level
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKey(SysStationLevel record);
}
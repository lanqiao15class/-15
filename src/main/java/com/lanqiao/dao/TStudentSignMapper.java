package com.lanqiao.dao;

/**
 * Created by wei62_000 on 2017/7/17.
 */
public interface TStudentSignMapper {

    //查询当前课程签到人数
    int selectCurrentCount(Integer caId);
}

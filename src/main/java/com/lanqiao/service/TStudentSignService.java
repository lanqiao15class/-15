package com.lanqiao.service;

import org.springframework.stereotype.Service;

/**
 * Created by wei62_000 on 2017/7/17.
 */
@Service
public class TStudentSignService extends BaseService{


    //获取当前课程的签到人数
    public int getCurrentCount(Integer caId){
        return (int)dao.selectOne("com.lanqiao.dao.TStudentSignMapper.selectCurrentCount",caId);
    }
}

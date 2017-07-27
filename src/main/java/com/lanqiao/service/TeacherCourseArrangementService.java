package com.lanqiao.service;

import com.lanqiao.dao.TeacherCourseArrangementMapper;
import com.lanqiao.model.TeacherCourseArrangement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wei62_000 on 2017/7/12.
 */

@Service
public class TeacherCourseArrangementService extends BaseService{


    //通过ca_id删除
    public int deleteByPrimaryKey(Integer caId){
        int ires = dao.delete("com.lanqiao.dao.TeacherCourseArrangementMapper.deleteByPrimaryKey",caId);
        return ires;
    }

    //插入老师课程安排记录
    public int insert(TeacherCourseArrangement record){
        int ires = dao.insert("com.lanqiao.dao.TeacherCourseArrangementMapper.insert",record);
        return ires;
    }

    //通过登录Id查询当前老师的所有课程
    public List<Map<String,Object>> findAllByUserId(Integer userId){
        return (List<Map<String,Object>>) dao.selectList("com.lanqiao.dao.TeacherCourseArrangementMapper.findAllByUserId",userId);
    }

    public List<Map<String,Object>> findArrangeByInfo(Map<String, Object> paramMap) {
        TeacherCourseArrangementMapper tcam = dao.getMapper(TeacherCourseArrangementMapper.class);
        return tcam.selectArrangeByInfo(paramMap);
    }


}

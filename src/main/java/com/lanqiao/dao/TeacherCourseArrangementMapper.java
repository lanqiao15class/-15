package com.lanqiao.dao;

import com.lanqiao.model.TeacherCourseArrangement;

import java.util.List;
import java.util.Map;

public interface TeacherCourseArrangementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher_course_arrangement
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int deleteByPrimaryKey(Integer caId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher_course_arrangement
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int insert(TeacherCourseArrangement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher_course_arrangement
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int insertSelective(TeacherCourseArrangement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher_course_arrangement
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    TeacherCourseArrangement selectByPrimaryKey(Integer caId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher_course_arrangement
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int updateByPrimaryKeySelective(TeacherCourseArrangement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_teacher_course_arrangement
     *
     * @mbggenerated Tue Jun 27 14:23:02 CST 2017
     */
    int updateByPrimaryKey(TeacherCourseArrangement record);


    //根据查询当前教师的课程安排
//    List<CourseArrangementDTO> findAllByAdmin(Integer userId);

    List<Map<String,Object>> findAllByUserId(Integer usrId);

    List<Map<String,Object>> selectArrangeByInfo(Map<String, Object> paramMap);

}
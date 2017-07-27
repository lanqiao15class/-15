package com.lanqiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;


import com.lanqiao.model.LQUniversities;


@Service
public class CommonService extends BaseService {
	
	
	
	
	
	public List<Map> ListDistinctClassName()
	{
		List<Map> list =(List<Map> ) dao.selectList("CommonMapper_Ext.select_distinct_class_name", null);
		return list ;
	}
	/**
	 * 查询所有老师
	 * @param loginame
	 * @param pass
	 * @return
	 */
	public List<Map> ListDistinctTeacherName()
	{
		List<Map> list =(List<Map> ) dao.selectList("CommonMapper_Ext.select_distinct_teacher_name", null);
		return list ;
	}
	
	public List<Map> ListDepartMentList()
	{
		List<Map> list =(List<Map> ) dao.selectList("CommonMapper_Ext.select_distinct_department", null);
		return list ;
	}
	
	
	/**
	 * 查询所有的学生名字. 
	 * @param loginame
	 * @param pass
	 * @return
	 */
	public List<Map> ListDistinctStudentName()
	{
		List<Map> list =(List<Map> ) dao.selectList("CommonMapper_Ext.select_distinct_student_name", null);
		return list ;
	}
	/**
	 * 查询所有的院校信息到
	 * @return
	 */
	public List<Map> ListAllUniversity()
	{
		List<Map> list =(List<Map> ) dao.selectList("CommonMapper_Ext.select_school_for_auto", null);
		return list ;
	}
	
	/**
	 * 查询所有的专业, 以便页面通过下拉自动填全
	 * @return
	 */
	public List<Map> ListAllMajor()
	{
		List<Map> list =(List<Map> ) dao.selectList("CommonMapper_Ext.select_major_for_auto", null);
		return list ;
	}
	
	
	
	/**
	 * 查询所有的子学院, 以便页面通过下拉自动填全
	 * @return
	 */
	public List<Map> ListAllSubSchoolName()
	{
		List<Map> list =(List<Map> ) dao.selectList("CommonMapper_Ext.select_school_subname_for_auto", null);
		return list ;
	}
	
	/**
	 * 查询所有的子学院, 以便页面通过下拉自动填全
	 * @return
	 */
	public List<Map> ListAllNation()
	{
		List<Map> list =(List<Map> ) dao.selectList("CommonMapper_Ext.select_nation_for_auto", null);
		return list ;
	}
	
	
	
}

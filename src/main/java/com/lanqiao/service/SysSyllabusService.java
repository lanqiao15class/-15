package com.lanqiao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lanqiao.model.SysSyllabus;

@Service
public class SysSyllabusService extends BaseService {
	/**
	 * 根据父id查找科目
	 * @param pid
	 * @return
	 */
	public List<SysSyllabus> findSyllabusByPid(Integer pid){
		return (List<SysSyllabus>) dao.selectList(
				"com.lanqiao.dao.SysSyllabusMapper.findSyllabusByPid", pid);
		
	}
	/**
	 * 根据课程id查找科目
	 * @param courseId
	 * @return
	 */
	public List<SysSyllabus> findSyllabusByCourseid(Integer courseId){
		return (List<SysSyllabus>) dao.selectList(
				"com.lanqiao.dao.SysSyllabusMapper.findSyllabusByCourseid", courseId);
		
	}
	public int deleteByPrimaryKey(int id){
		int ires=dao.insert("com.lanqiao.dao.SysSyllabusMapper.deleteByPrimaryKey", id);
		return ires;
	}
	
	public SysSyllabus getSyllabusBysysSyllabusid(Integer syllabusId){
		return (SysSyllabus) dao.selectOne(
				"com.lanqiao.dao.SysSyllabusMapper.getSyllabusBysysSyllabusid", syllabusId);
		
	}
}

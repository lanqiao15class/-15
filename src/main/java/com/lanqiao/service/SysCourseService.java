package com.lanqiao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanqiao.dao.SysCourseMapper;
import com.lanqiao.dto.CourseDTO;
import com.lanqiao.model.SysCourse;
import com.lanqiao.model.SysSyllabus;
@Service
public class SysCourseService extends BaseService  {
	public List<CourseDTO> findAllCourseByAdmin(){
		return (List<CourseDTO>) dao.selectList("com.lanqiao.dao.SysCourseMapper.findAllCourseByAdmin",null);
	}

	/**
	 * 修改科目和阶段
	 * 
	 * @param record
	 * @return
	 */

	public int updateByPrimaryKeySelective(SysCourse record) {

		int ires = dao.insert("com.lanqiao.dao.SysCourseMapper.updateByPrimaryKeySelective",record);

		return ires;

	}
    public int insertCourseByAdmin(SysCourse record){
		
		int ires=dao.insert("com.lanqiao.dao.SysCourseMapper.insertCourseByAdmin", record);
		if (ires>0) {
			ires=record.getCourseId();
		}
		return ires;
		
	}
	
	/**
	 * 添加阶段和科目
	 * @param record
	 * @return
	 */
	
	public int insertSyllabusByAdmin(SysSyllabus record){
		
		int ires=dao.insert("com.lanqiao.dao.SysSyllabusMapper.insertSyllabusByAdmin", record);
		if (ires>0) {
			ires=record.getSyllabusId();
		}
		return ires;
		
	}
	public SysSyllabus selectBySysSyllabusPrimaryKey(int record){
		SysSyllabus ires=(SysSyllabus) dao.selectOne("com.lanqiao.dao.SysSyllabusMapper.selectByPrimaryKey", record);
		return ires;
	}
	public int updateSysSyllabusByPrimaryKeySelective(SysSyllabus record){
		int ires=dao.insert("com.lanqiao.dao.SysSyllabusMapper.updateByPrimaryKeySelective", record);
		return ires;
	}
	public SysCourse selectSysCourseByPrimaryKey(int record){
		SysCourse ires=(SysCourse) dao.selectOne("com.lanqiao.dao.SysCourseMapper.selectByPrimaryKey", record);
		return ires;
	}
	public int updateSysCourseByPrimaryKeySelective(SysCourse record){
		int ires=dao.insert("com.lanqiao.dao.SysCourseMapper.updateByPrimaryKeySelective", record);
		return ires;
	}

	public CourseDTO getCourseById(int courseId) {
		CourseDTO infoCourse=(CourseDTO) dao.selectOne("com.lanqiao.dao.SysCourseMapper.getCourseById", courseId);
		return infoCourse;
		
	}
	
	
}

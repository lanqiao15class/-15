package com.lanqiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanqiao.dao.TStudentclassLogMapper;
import com.lanqiao.model.TStudentclassLog;

@Service
public class StudentclassLogService extends BaseService {

	// 具有所有的id
	public static int All_Data_ID = -1;

	/**
	 * 
	* @Description:根据学员编号获取班级信息记录列表
	* @param stuNo
	* @return 
	* @return List<Map<String,Object>> 
	* @author ZhouZhiHua
	* @createTime 2016年12月5日 下午6:05:25
	 */
	public List<Map<String, Object>> getClassLogByUserId(Integer stuNo) {
		return (List<Map<String, Object>>) dao.selectList("com.lanqiao.dao.TStudentclassLogMapper.getClassLogByUserId",
				stuNo);
	}

	/**
	 * 
	 *@description:增加学员班级记录表
	 *@param record
	 *@return
	 *@return int 
	 *@author:ZhuDiaoHua
	 *@2016年12月7日下午2:28:25
	 *
	 */
	public int insertSelective(TStudentclassLog record) throws Exception {
		TStudentclassLogMapper tStudentclassLogMapper = (TStudentclassLogMapper) dao
				.getMapper(TStudentclassLogMapper.class);
		return tStudentclassLogMapper.insertSelective(record);
	}

	/**
	 * 
	 *@description:更新学员班级记录表
	 *@param record
	 *@return
	 *@return int 
	 *@author:ZhuDiaoHua
	 *@2016年12月7日下午2:28:10
	 *
	 */
	public int updateByPrimaryKeySelective(TStudentclassLog record) throws Exception {
		TStudentclassLogMapper tStudentclassLogMapper = (TStudentclassLogMapper) dao
				.getMapper(TStudentclassLogMapper.class);
		return tStudentclassLogMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 
	 *@description:获取某个学员的最后一条学员班级记录
	 *@param stuId
	 *@return
	 *@return Integer 
	 *@author:ZhuDiaoHua
	 *@2016年12月7日下午2:41:40
	 *
	 */
	public Integer getLastLogByUserId(Integer stuId) {
		TStudentclassLogMapper tStudentclassLogMapper = (TStudentclassLogMapper) dao
				.getMapper(TStudentclassLogMapper.class);
		return tStudentclassLogMapper.getLastLogByUserId(stuId);
	}

}

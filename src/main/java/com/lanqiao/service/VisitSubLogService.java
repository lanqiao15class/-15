package com.lanqiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class VisitSubLogService extends BaseService
{

	// 具有所有的id
	public static int All_Data_ID = -1;
	
	/**
	 * 
	* @Description:根据学生id获取访谈记录
	* @param StudentId
	* @return 
	* @return List<Map<String,Object>> 
	* @author ZhouZhiHua
	* @createTime 2016年11月30日 下午6:19:10
	 */
	public List<Map<String,Object>> getVisitSubLogList(int studentId){
		return (List<Map<String,Object>>)dao.selectList("com.lanqiao.dao.TVisitSubLogMapper.getVisitSubLogList", studentId);
	}

}

package com.lanqiao.service;

import org.springframework.stereotype.Service;
import com.lanqiao.model.LQUniversities;

@Service
public class LQUniversitiesService extends BaseService
{

	// 具有所有的id
	public static int All_Data_ID = -1;
	
	/**
	 * 
	* @Description:根据院校编号查询院校信息
	* @param StudentId
	* @return 
	* @return List<Map<String,Object>> 
	* @author ZhouZhiHua
	* @createTime 2016年11月30日 下午6:19:10
	 */
	public LQUniversities getLqUniverInfo(Long univCode){
		return (LQUniversities)dao.selectOne("com.lanqiao.dao.LQUniversitiesMapper.selectByPrimaryKey", univCode);
	}

}

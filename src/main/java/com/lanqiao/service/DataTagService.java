package com.lanqiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanqiao.dao.StudentMapper;
import com.lanqiao.dao.TDataTagMapper;
import com.lanqiao.model.TDataTag;

@Service
public class DataTagService extends BaseService
{

	// 具有所有的id
	public static int All_Data_ID = -1;
	
	/**
	 * 
	* @Description:获取指定学员的标记
	* @return 
	* @return Map<String,Object> 
	* @author ZhouZhiHua
	* @createTime 2016年11月30日 下午3:00:57
	 */
	public List<Map<String, Object>> getTagByUserIdAndData(TDataTag dataTag){
		return (List<Map<String, Object>>)dao.selectList("com.lanqiao.dao.TDataTagMapper.getTagByUserIdAndData",dataTag);
	}
	
	public int teaIfRemarkStu(TDataTag dataTag){
	 TDataTagMapper dataTagMapper=dao.getMapper(TDataTagMapper.class);
	 return dataTagMapper.teaIfRemarkStu(dataTag);
	};
}

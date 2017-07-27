package com.lanqiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lanqiao.dao.TClassVisitLogMapper;
import com.lanqiao.model.TClassVisitLog;

@Service
public class ClassVisitLogService extends BaseService {

	/**
	 * 
	 * @Description:根据班级id获取班级的状态列表
	 * @param userId
	 * @return
	 * @return List<Map<String,Object>>
	 * @author ZhouZhiHua
	 * @createTime 2016年12月7日 上午10:13:00
	 */
	public List<Map<String, Object>> getClassVisitLogByClassId(Integer classId) {
		return (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.TClassVisitLogMapper.getClassVisitLogByClassId", classId);
	}

	//创建班级记录
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int insertSelective(TClassVisitLog record) throws Exception {
		TClassVisitLogMapper classVisitLogMapper = dao.getMapper(TClassVisitLogMapper.class);
		return classVisitLogMapper.insertSelective(record);
	}

}

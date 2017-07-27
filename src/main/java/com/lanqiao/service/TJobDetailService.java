package com.lanqiao.service;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lanqiao.dao.TJobDetailMapper;
import com.lanqiao.model.Student;
import com.lanqiao.model.TJobDetail;

@Service
public class TJobDetailService extends BaseService {
	private final static Logger logger = LogManager.getLogger(TJobDetailService.class);

	public List<Map<String, Object>> getJobDetailBystuId(int stuId) {
		return (List<Map<String, Object>>) dao
				.selectList("com.lanqiao.dao.TJobDetailMapper.getJobDetailBystuId", stuId);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveJobDetailBystuId(TJobDetail jobDetail, Student student) throws Exception {
		dao.update("com.lanqiao.dao.TJobDetailMapper.updateByPrimaryKeySelective", jobDetail);
		dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
	}

	//根据学员userId获取学员的职业经历
	public List<Map<String, Object>> getMyWorkExpByStuUserId(Integer userId) {
		TJobDetailMapper mapper = dao.getMapper(TJobDetailMapper.class);
		return mapper.getMyWorkExpByStuUserId(userId);
	}

}

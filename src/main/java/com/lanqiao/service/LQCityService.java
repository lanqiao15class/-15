package com.lanqiao.service;

import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;

import com.lanqiao.dao.LQCityMapper;

@Service
public class LQCityService extends BaseService{
	
	
	public List<Map<String, Object>> findProvinces() {
		 LQCityMapper lQCityMapper = dao.getMapper(LQCityMapper.class);
		
		 List<Map<String, Object>>  ltemp =  lQCityMapper.findProvinces();
		// System.out.println ("findProvinces:"+ ltemp.size() );
		 return ltemp;
	}

	public List<Map<String, Object>> selectByProvId(Integer cid) {
		 LQCityMapper lQCityMapper = dao.getMapper(LQCityMapper.class);
		return lQCityMapper.selectByProvId(cid);
	}

	public String getById(int provId) {
		 LQCityMapper lQCityMapper = dao.getMapper(LQCityMapper.class);
		return lQCityMapper.getById(provId);
	}

}

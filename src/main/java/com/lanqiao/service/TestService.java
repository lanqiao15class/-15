package com.lanqiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanqiao.dao.TLqclassMapper;
import com.lanqiao.dao.UserMapper;
import com.lanqiao.model.TLqclass;
import com.lanqiao.model.User;
import com.lanqiao.util.JsonUtil;


@Service
public class TestService extends BaseService {
	
	public void setRedisValue()
	{
		this.CacheSet("a", "222222222222", 5L);
	}
	
	public Object getRedisValue()
	{
		return this.CacheGet("a");
	}
	
	
}

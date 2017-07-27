package com.lanqiao.service;


import java.util.List;


import org.mybatis.spring.support.SqlSessionDaoSupport;


/**
 * 
 * author:chenbaoji 
 * 封装mybatis 对数据库操作. 
 * 
 *
 */
public class BaseDao extends SqlSessionDaoSupport  {

	
	
	public <T> T getMapper(Class<T> clz) {

		return getSqlSession().getMapper(clz);
	}

	
	public Object selectOne(String statement, Object parameter) {

		return getSqlSession().selectOne(statement, parameter);
	}

	

	public List<?> selectList(String statement, Object parameter) {

		return getSqlSession().selectList(statement, parameter);
	}
	
	
	public int insert(String statement, Object parameter) {

	
		return getSqlSession().insert(statement, parameter);
	}

	
	public int update(String statement, Object parameter) {

	
		return getSqlSession().update(statement, parameter);
	}

	
	public int delete(String statement, Object parameter) {

	
		return getSqlSession().delete(statement, parameter);
	}
	

}

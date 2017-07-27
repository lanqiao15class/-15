package com.lanqiao.service;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public abstract class BaseService {
	protected static final Log log = org.apache.commons.logging.LogFactory.getLog(BaseService.class);

	protected BaseDao dao;

	@Resource(name = "redisTemplate")
	private RedisTemplate redisTemplate;

	@Resource(name = "baseDao")
	public void setDao(BaseDao dao) {

		this.dao = dao;
	}

	@Resource
	protected LogService logservice;

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void CacheRemove(final String... keys) {
		for (String key : keys) {
			CacheRemove(key);
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public void CacheRemovePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void CacheRemove(final String key) {
		if (CacheExists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean CacheExists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object CacheGet(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean CacheSet(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean CacheSet(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value, expireTime, TimeUnit.SECONDS);

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}

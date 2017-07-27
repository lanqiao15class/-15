package com.lanqiao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.lanqiao.common.LQtokenkey;
import com.lanqiao.constant.GlobalConstant;
import com.sun.istack.internal.logging.Logger;

@Service
public class OuterService extends BaseService {
	static Logger log = Logger.getLogger(OuterService.class);

	/**
	 * 获取学生信息
	 */
	public Map<String, Object> getStuInfo(String tokenkey, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (this.CacheExists(tokenkey)) {
			int userId = (((LQtokenkey) this.CacheGet(tokenkey)).userid);

			log.info(tokenkey + ":" + userId);

			Map<String, Object> uMap = (Map<String, Object>) dao.selectOne("com.lanqiao.dao.OuterMapper.getStuInfo",
					userId);
			if (uMap != null) {
				// 处理头像。
				String strphoto = (String) uMap.get("photo");
				if (StringUtils.isEmpty(strphoto)) {
					strphoto = GlobalConstant.defaultheadface;
					uMap.put("photo", strphoto);
				} else {
					uMap.put("photo", GlobalConstant.httpUploadURL + strphoto);
				}

				map.put("data", uMap);
				map.put("code", 100);

			} else
				map.put("code", -1);

		} else {
			map.put("code", -1);
		}
		return map;
	}

	/**
	 * 获取老师信息接口
	 */
	public Map<String, Object> getTeaInfo(String tokenkey, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Object ocache = this.CacheGet(tokenkey);

		if (ocache != null) {
			LQtokenkey lQtokenkey = (LQtokenkey) ocache;
			int userId = lQtokenkey.userid;
			log.info(tokenkey + ":" + userId);

			Map<String, Object> uMap = (Map<String, Object>) dao.selectOne("com.lanqiao.dao.OuterMapper.getTeaInfo",
					userId);
			if (uMap != null) {
				// 处理头像。
				String strphoto = (String) uMap.get("photo");
				if (StringUtils.isEmpty(strphoto)) {
					strphoto = GlobalConstant.defaultheadface;
					uMap.put("photo", strphoto);
				} else {
					uMap.put("photo", GlobalConstant.httpUploadURL + strphoto);
				}
				map.put("data", uMap);
				map.put("code", 100);

			} else
				map.put("code", -1);

		} else {
			map.put("code", -1);
		}
		return map;
	}

	private String getClassIdByTeacher(int teachid) {
		List<Map<String, Object>> l = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.OuterMapper.getClassByTeacherid", teachid);
		String sret = "";
		for (Map<String, Object> m : l) {
			if (!sret.equals(""))
				sret += ",";
			sret += m.get("lq_class_id").toString();
		}

		return sret;
	}

	/**
	 * 获取新添加的学生信息
	 */
	public Map<String, Object> getNewAddStuInfo(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount = 100;
		int totalRestCount = (Integer) dao.selectOne("com.lanqiao.dao.OuterMapper.getNewAddStuCount", paramMap);
		if (totalRestCount > recordCount) {
			map.put("end", 0);
		} else {
			map.put("end", 1);
		}

		List<Map<String, Object>> userList = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.OuterMapper.getNewAddStuList", paramMap);
		if (userList != null) {
			for (Map<String, Object> uMap : userList) {
				// 处理头像。
				String strphoto = (String) uMap.get("photo");
				if (StringUtils.isEmpty(strphoto)) {
					strphoto = GlobalConstant.defaultheadface;
					uMap.put("photo", strphoto);
				} else {
					uMap.put("photo", GlobalConstant.httpUploadURL + strphoto);
				}
			}
			map.put("data", userList);
			map.put("code", 100);

		} else
			map.put("code", -1);

		return map;
	}

	/**
	 * 获取新添加的老师信息
	 */
	public Map<String, Object> getNewAddTeaInfo(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> userList = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.OuterMapper.getNewAddTeaList", paramMap);
		if (userList != null) {
			for (Map<String, Object> uMap : userList) {
				// 处理头像。
				String strphoto = (String) uMap.get("photo");
				if (StringUtils.isEmpty(strphoto)) {
					strphoto = GlobalConstant.defaultheadface;
					uMap.put("photo", strphoto);
				} else {
					uMap.put("photo", GlobalConstant.httpUploadURL + strphoto);
				}

				//老师所管理的班级。 
				Integer nuserid = (Integer) uMap.get("id");

				String sid = getClassIdByTeacher(nuserid);
				uMap.put("classIds", sid);

			}
			map.put("data", userList);
			map.put("code", 100);

		} else
			map.put("code", -1);

		return map;
	}

	public Map<String, Object> getNewAddClassInfo(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> userList = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.OuterMapper.getNewAddClassInfo", paramMap);
		if (userList != null) {

			map.put("data", userList);
			map.put("code", 100);

		} else
			map.put("code", -1);

		return map;
	}

	public Map<String, Object> getTeacherClass(Integer techerId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> userList = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.OuterMapper.getTeacherClass", techerId);
		if (userList != null) {

			map.put("data", userList);
			map.put("code", 100);

		} else {
			map.put("code", -1);
			map.put("msg", "该id没有班级数据");
		}

		return map;
	}

	public Map<String, Object> getClassByid(Integer classid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> userList = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.OuterMapper.getClassByid", classid);
		if (userList != null) {

			map.put("data", userList);
			map.put("code", 100);

		} else {
			map.put("code", -1);
			map.put("msg", "该id没有班级数据");
		}

		return map;
	}
}

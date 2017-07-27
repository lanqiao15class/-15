package com.lanqiao.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lanqiao.service.OuterService;
import com.lanqiao.util.JsonUtil;

@Controller
@RequestMapping("/outer")
public class OuterController {

	@Resource
	private OuterService outerservice;

	@RequestMapping("/matchkey.do")
	public void matchkey(HttpServletResponse response, HttpServletRequest request) {
		HashMap ret = new HashMap();
		String s = ServletRequestUtils.getStringParameter(request, "lquuid", "");
		if ("888999".equals(s)) {
			ret.put("userid", 10022);
			ret.put("resultcode", 1);
			ret.put("resultmsg", "成功");
		} else {
			ret.put("resultcode", 0);
			ret.put("resultmsg", "uuid 不存在");
		}
		response.setContentType("text/html;charset=UTF-8");

		JsonUtil.write(response,
				JSON.toJSONStringWithDateFormat(ret, "yyyy-MM-dd", SerializerFeature.WriteMapNullValue));
	}

	@RequestMapping("/getStuInfo.do")
	public void getStuInfo(String tokenkey, HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = null;
		try {
			map = outerservice.getStuInfo(tokenkey, session);
		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("code", 0);
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");

		JsonUtil.write(response,
				JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd", SerializerFeature.WriteMapNullValue));
	}

	/**
	 * 获取老师用户信息接口
	 * 
	 * @param jsonScoreData
	 * @param response
	 */
	@RequestMapping("/getTeaInfo.do")
	public void getTeaInfo(String tokenkey, HttpSession session, HttpServletResponse response) {
		Map<String, Object> map = null;
		try {
			map = outerservice.getTeaInfo(tokenkey, session);

		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("code", 0);
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");

		JsonUtil.write(response,
				JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue));
	}

	/**
	 * 获取新增学生信息接口
	 * 
	 * @param lastId
	 * @param session
	 * @param response
	 */
	@RequestMapping("/getNewAddStuInfo.do")
	public void getNewAddStuInfo(int lastId, HttpServletResponse response) {
		Map<String, Object> map = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lastId", lastId);
			paramMap.put("recordCount", 100);
			map = outerservice.getNewAddStuInfo(paramMap);
		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("code", 0);
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");

		JsonUtil.write(response,
				JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd", SerializerFeature.WriteMapNullValue));
	}

	/**
	 * 获取老师班级接口
	 * 
	 * @param lastId
	 * @param session
	 * @param response
	 */
	@RequestMapping("/getTeacherClass.do")
	public void getTeacherClass(Integer teacherId, HttpServletResponse response) {
		Map<String, Object> map = null;

		try {
			if (teacherId == null) {
				map = new HashMap<String, Object>();
				map.put("code", -1);
				map.put("msg", "id  传值错误");
			} else {
				map = new HashMap<String, Object>();
				map.put("teacherId", teacherId);
				map = outerservice.getTeacherClass(teacherId);
			}

		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("code", 0);
			map.put("msg", "系统异常");
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");

		JsonUtil.write(response,
				JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd", SerializerFeature.WriteMapNullValue));
	}

	/**
	 * 获取班级详情接口
	 * 
	 * @param lastId
	 * @param session
	 * @param response
	 */
	@RequestMapping("/getClassByid.do")
	public void getClassByid(Integer classid, HttpServletResponse response) {
		Map<String, Object> map = null;

		try {
			if (classid == null) {
				map = new HashMap<String, Object>();
				map.put("code", -1);
				map.put("msg", "id  传值错误");
			} else {
				map = new HashMap<String, Object>();
				map.put("teacherId", classid);
				map = outerservice.getClassByid(classid);
			}

		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("code", 0);
			map.put("msg", "系统异常");
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");

		JsonUtil.write(response,
				JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd", SerializerFeature.WriteMapNullValue));
	}

	/**
	 * 获取新增老师信息接口
	 * 
	 * @param lastId
	 * @param session
	 * @param response
	 */
	@RequestMapping("/getNewAddTeaInfo.do")
	public void getNewAddTeaInfo(int lastId, HttpServletResponse response) {
		Map<String, Object> map = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lastId", lastId);
			paramMap.put("recordCount", 100);
			map = outerservice.getNewAddTeaInfo(paramMap);
		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("code", 0);
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");

		JsonUtil.write(response,
				JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue));
	}

	/**
	 * 新增加的班级信息。 
	 * 
	 * @param lastId
	 * @param response
	 */
	@RequestMapping("/getNewAddclassInfo.do")
	public void getNewAddclassInfo(int lastId, HttpServletResponse response) {
		Map<String, Object> map = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lastId", lastId);
			map = outerservice.getNewAddClassInfo(paramMap);
		} catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("code", 0);
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");

		JsonUtil.write(response,
				JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue));
	}

}
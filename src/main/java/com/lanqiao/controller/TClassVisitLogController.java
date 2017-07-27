package com.lanqiao.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.WebUtil;
import com.lanqiao.model.TClassVisitLog;
import com.lanqiao.service.ClassVisitLogService;
import com.lanqiao.util.JsonUtil;

@Controller
@RequestMapping("/classVisit")
public class TClassVisitLogController {
	@Resource
	private ClassVisitLogService classVisitLogService;

	/**
	 * 创建班级记录
	 * @param lqClassId
	 */
	@RequestMapping("/saveClassRecord.do")
	public void saveClassRecord(HttpServletResponse response, HttpServletRequest request, @RequestParam int lqClassId,
			@RequestParam String visitContent) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		TClassVisitLog record = new TClassVisitLog();
		record.setClassid(lqClassId);//访谈对象班级id
		record.setCreatorUserid(WebUtil.getLoginUser(request).getUserId());//创建者userid
		record.setVisitContent(visitContent);
		record.setVisitTime(new Date());//创建时间
		int row = classVisitLogService.insertSelective(record);
		if (row > 0) {
			resultMap.put("code", 200);
			resultMap.put("message", "创建班级记录成功");
		} else {
			resultMap.put("code", 201);
			resultMap.put("message", "创建班级记录失败");
		}
		JsonUtil.write(response, JSON.toJSONString(resultMap));
	}
}

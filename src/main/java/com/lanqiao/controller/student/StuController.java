package com.lanqiao.controller.student;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.lanqiao.common.MustLoginAndStudent;
import com.lanqiao.common.WebUtil;
import com.lanqiao.model.Student;
import com.lanqiao.model.User;
import com.lanqiao.service.DictService;
import com.lanqiao.service.StudentService;
import com.lanqiao.service.TJobDetailService;
import com.lanqiao.service.TLqclassService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.Code2Util;

@RequestMapping("/stu")
@Controller
public class StuController {
	@Resource
	private TLqclassService tLqclassService;

	@Resource
	private DictService dictService;

	@Resource
	private UserService userService;

	@Resource
	private TJobDetailService jobDetailService;

	@Resource
	StudentService stuserivce;

	@MustLoginAndStudent
	@RequestMapping("/showcode2.do")
	public void code2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer userId = WebUtil.getLoginUser(request).getUserId();
		User u = userService.getUserInfoByUserId(userId);
		if (u != null) {
			HashMap mp = new HashMap();
			mp.put("userid", u.getUserId());
			mp.put("realname", u.getRealName() == null ? "" : u.getRealName());
			mp.put("idcard", u.getIdCard() == null ? "" : u.getIdCard());
			mp.put("email", u.getLoginEmail() == null ? "" : u.getLoginEmail());
			mp.put("phone", u.getLoginTel() == null ? "" : u.getLoginTel());
			Student stu = stuserivce.selectByPrimaryKey(userId);
			mp.put("stuno", stu.getStuNo() == null ? "" : stu.getStuNo());
			String json = JSON.toJSONString(mp);
			System.err.println(json);
			String down = request.getParameter("down");
			if (StringUtils.isEmpty(down))
				response.setContentType("image/png");
			else {
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String("蓝桥签名.png".getBytes("gb2312"), "iso-8859-1"));
			}
			OutputStream pout = response.getOutputStream();

			Code2Util.Encode(pout, json, 300, 300);
			pout.flush();

		}

	}

	/**
	 * 去我的班级信息页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@MustLoginAndStudent
	@RequestMapping("/goMyClassInfo.do")
	public ModelAndView goMyClassInfo(HttpServletRequest request, ModelAndView mv) {
		Integer userId = WebUtil.getLoginUser(request).getUserId();
		Integer lqClassId = tLqclassService.selectClassIdByUserId(userId);
		Map<String, Object> classInfo = new HashMap<String, Object>();
		if (lqClassId != null) {
			classInfo = tLqclassService.getMyClassInfo(lqClassId);
			if (classInfo != null) {
				//获取班级的当前人数
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("lqClassId", classInfo.get("lqClassId"));
				int currCount = tLqclassService.getClassCurrentCount(paramMap);
				classInfo.put("currCount", currCount);
				Map<String, Object> param = new HashMap<String, Object>();
				if (classInfo != null && classInfo.get("courseType") != null) {
					param.put("type", "lq_courseType");
					param.put("value", classInfo.get("courseType"));
					classInfo.put("courseType", dictService.getLabelByValAndType(param));
				}
				if (classInfo != null && classInfo.get("typePre") != null && classInfo.get("typeReal") != null) {
					param.put("dictTypePre", "lq_classType1");
					param.put("classTypePre", classInfo.get("typePre"));//一级班级类型 
					param.put("dictTypeReal", "lq_classType2");
					param.put("classTypeReal", classInfo.get("typeReal"));//二级班级类型
					classInfo.put("typePre", dictService.getClassType(param));
				}
				if (classInfo != null && classInfo != null && classInfo.get("comTeacherId") != null) {
					classInfo.put("comTeacherId",
							userService.getRealNameById(Integer.parseInt(classInfo.get("comTeacherId").toString())));//技术老师realName
				}
				if (classInfo != null && classInfo.get("cepTeacherId") != null) {
					classInfo.put("cepTeacherId",
							userService.getRealNameById(Integer.parseInt(classInfo.get("cepTeacherId").toString())));//cep老师realName
				}
				if (classInfo != null && classInfo.get("chrTeacherId") != null) {
					classInfo.put("chrTeacherId",
							userService.getRealNameById(Integer.parseInt(classInfo.get("chrTeacherId").toString())));//职业经纪人realName
				}
				if (classInfo != null && classInfo.get("monitorId") != null) {
					classInfo.put("monitorId",
							userService.getRealNameById(Integer.parseInt(classInfo.get("monitorId").toString())));//技术老师realName
				}
			}
		}
		mv.addObject("classInfo", classInfo);//共享班级信息
		mv.setViewName("/WEB-INF/view/student/myClassInfo.jsp");
		return mv;
	}

	/**
	 * 去我的职业经历页面
	 * @param mv
	 * @return
	 */
	@MustLoginAndStudent
	@RequestMapping("/goMyWorkExperience.do")
	public ModelAndView goMyWorkExperience(HttpServletRequest request, ModelAndView mv) {
		Integer userId = WebUtil.getLoginUser(request).getUserId();
		List<Map<String, Object>> workExpList = jobDetailService.getMyWorkExpByStuUserId(userId);//我的职业经历list
		Map<String, Object> resultMap = new HashMap<String, Object>();//返回的map
		Map<String, Object> latestWorkExp = new HashMap<String, Object>();//最新的职业经历
		List<Map<String, Object>> restWorkExpList = new ArrayList<Map<String, Object>>();//剩余职业经历
		int size = workExpList.size();
		if (size > 0) {
			//1获取最新职业经历
			latestWorkExp.putAll(workExpList.get(0));//最新的职业经历
			//2转换字典表的value成相应label
			Map<String, Object> param = new HashMap<String, Object>();
			if (latestWorkExp.get("positionType") != null) {
				param.put("type", "inviteType");//岗位性质
				param.put("value", latestWorkExp.get("positionType"));
				latestWorkExp.put("positionType", dictService.getLabelByValAndType(param));
			}
			if (latestWorkExp.get("companyScale") != null) {
				param.put("type", "cocompScale");//公司规模
				param.put("value", latestWorkExp.get("companyScale"));
				latestWorkExp.put("companyScale", dictService.getLabelByValAndType(param));
			}
			if (latestWorkExp.get("companyType") != null) {
				param.put("type", "cocompType");//公司性质
				param.put("value", latestWorkExp.get("companyType"));
				latestWorkExp.put("companyType", dictService.getLabelByValAndType(param));
			}
			if (latestWorkExp.get("occupationStatus") != null) {//职业状态
				latestWorkExp.put("occupationStatus",
						WebUtil.getJobStaLabel(Integer.parseInt(latestWorkExp.get("occupationStatus").toString())));
			}
			//3获取剩余职业经历list
			if ((size - 1) > 0) {
				for (int i = 1; i < size; i++) {//剩余职业经历
					//1
					Map<String, Object> tempMap = new HashMap<String, Object>();
					tempMap.putAll(workExpList.get(i));
					//2
					if (tempMap.get("positionType") != null) {
						param.put("type", "inviteType");//岗位性质
						param.put("value", tempMap.get("positionType"));
						tempMap.put("positionType", dictService.getLabelByValAndType(param));
					}
					if (tempMap.get("companyScale") != null) {
						param.put("type", "cocompScale");//公司规模
						param.put("value", tempMap.get("companyScale"));
						tempMap.put("companyScale", dictService.getLabelByValAndType(param));
					}
					if (tempMap.get("companyType") != null) {
						param.put("type", "cocompType");//公司性质
						param.put("value", tempMap.get("companyType"));
						tempMap.put("companyType", dictService.getLabelByValAndType(param));
					}
					if (tempMap.get("occupationStatus") != null) {//职业状态
						tempMap.put("occupationStatus",
								WebUtil.getJobStaLabel(Integer.parseInt(tempMap.get("occupationStatus").toString())));
					}
					//3
					restWorkExpList.add(tempMap);
				}
			}
		}
		resultMap.put("latestWorkExp", latestWorkExp);
		resultMap.put("latestSize", latestWorkExp.size());
		resultMap.put("restWorkExpList", restWorkExpList);
		resultMap.put("restSize", restWorkExpList.size());
		mv.addAllObjects(resultMap);
		mv.setViewName("/WEB-INF/view/student/myWorkExperience.jsp");
		return mv;
	}
}

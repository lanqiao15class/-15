package com.lanqiao.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant.ClassStatusEnum;
import com.lanqiao.model.TLqclass;
import com.lanqiao.model.User;
import com.lanqiao.service.ClassMemberService;
import com.lanqiao.service.ClassStatusService;
import com.lanqiao.service.ClassVisitLogService;
import com.lanqiao.service.DictService;
import com.lanqiao.service.TLqclassService;
import com.lanqiao.service.UserService;

@Controller
public class ClassMemberController {
	@Resource
	ClassMemberService memberservice;

	@Resource
	private DictService dictService;

	@Resource
	private TLqclassService tLqclassService;

	@Resource
	ClassStatusService classStatusService;

	@Resource
	ClassVisitLogService classVisitLogService;

	@Resource
	UserService userService;
	private static final Logger logger = LogManager.getLogger(ClassMemberController.class);

	/**
	 * 班级内的学员.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/class/showclassmember.do")
	public ModelAndView showclassmember(HttpServletRequest request, HttpServletResponse response) {
		HashMap hsret = new HashMap();
		try {
			int classid = ServletRequestUtils.getIntParameter(request, "classid", 0);
			if (classid == 0) {
				return WebUtil.getModeAndView404("参数错误:无classid");
			}
			TLqclass lqclass = memberservice.getlqClass(classid);
			if (lqclass == null) {
				return WebUtil.getModeAndView404("班级不存在");
			}

			// 当前班级人数.
			HashMap param = new HashMap();
			param.put("lqClassId", classid);
			int curcount = memberservice.getlqClassMemberCount(classid);

			hsret.put("classinfo", lqclass);
			hsret.put("currentcount", curcount);

			//班级状态名称.
			String statusname = WebUtil.getClassStatusByValue(lqclass.getStatus());
			hsret.put("statusname", statusname);
			/*
			 * 获取班级课时 
			 */
			//总课时
			int totalClass = memberservice.getcourse(lqclass.getCourseType()).getTotalClass();
			//以上课时
			Integer classtime = memberservice.selectsyllabusbyclassId(classid);
			hsret.put("totalClass", totalClass);
			hsret.put("classtime", classtime);
			/**
			 * 班级基本资料
			 * 
			 * 
			 */
			//1.获取班级基本信息
			Map<String, Object> lqclassInfo = tLqclassService.getLqClassInfoList(classid);

			hsret.put("lqclassInfo", lqclassInfo);
			//2.班级一级类型
			hsret.put("classTypePre", dictService.getDictByType(DictService.DICT_CLASSTYPE_PRE));
			//3.班级二级类型
			Map<String, Object> map = new HashMap<>();
			map.put("type", DictService.DICT_CLASSTYPE_PRE);
			map.put("value", lqclassInfo.get("type_pre"));
			String parentId = dictService.getIdByValAndType(map);
			//寻找子级列表
			List<Map<String, Object>> rearlist = dictService.getDictByParentAndType(DictService.DICT_CLASSTYPE_REAR,
					parentId);
			hsret.put("classTypeRear", rearlist);
			//4.课程类别
			hsret.put("courseType", dictService.getSysCourse());
			//5.班级状态流水列表
			hsret.put("classStatusList", classStatusService.getClassStatusLogByClassId(classid));
			List<Map<String, Object>> classStatusList = classStatusService.getClassStatusLogByClassId(classid);
			int classStatusListTemp = 0;
			for (int i = 0; i < classStatusList.size(); i++) {
				if (classStatusList.get(i).get("end_time") != null
						&& !"".equals(classStatusList.get(i).get("end_time"))) {
					classStatusListTemp = classStatusListTemp + 1;
				}
			}
			hsret.put("classStatusSize", classStatusListTemp);
			//6.班级状态
			hsret.put("classStatus", WebUtil.getClassStatusList());

			hsret.put("NOCLASSES", ClassStatusEnum.NOCLASSES.getValue());
			hsret.put("INTHELECTURE", ClassStatusEnum.INTHELECTURE.getValue());
			hsret.put("BEFORETHETRAININGSESSION", ClassStatusEnum.BEFORETHETRAININGSESSION.getValue());
			hsret.put("INTHETRAINING", ClassStatusEnum.INTHETRAINING.getValue());
			hsret.put("THEGRADUATION", ClassStatusEnum.THEGRADUATION.getValue());
			hsret.put("INTHEEMPLOYMENT", ClassStatusEnum.INTHEEMPLOYMENT.getValue());
			hsret.put("OFTHEEMPLOYMENT", ClassStatusEnum.OFTHEEMPLOYMENT.getValue());
			hsret.put("SHUTDOWN", ClassStatusEnum.SHUTDOWN.getValue());
			//7.班级跟踪记录
			hsret.put("classVisitList", classVisitLogService.getClassVisitLogByClassId(classid));
			hsret.put("classVisitSize", classVisitLogService.getClassVisitLogByClassId(classid).size());
			//8.根据技术老师id获取技术老师名称
			if (lqclassInfo.get("com_teacher_id") != null && !"".equals(lqclassInfo.get("com_teacher_id").toString())) {
				User user = userService.getUserInfoByUserId(Integer.parseInt(lqclassInfo.get("com_teacher_id")
						.toString()));
				hsret.put("comRealName", user.getRealName());
			} else {
				hsret.put("comRealName", null);
			}
			//9.根据cep老师id获取cep老师的名称
			if (lqclassInfo.get("cep_teacher_id") != null && !"".equals(lqclassInfo.get("cep_teacher_id").toString())) {
				User user = userService.getUserInfoByUserId(Integer.parseInt(lqclassInfo.get("cep_teacher_id")
						.toString()));
				hsret.put("cepRealName", user.getRealName());
			} else {
				hsret.put("cepRealName", null);
			}
			//10.根据职业经济人id获取职业经济人名称
			if (lqclassInfo.get("chr_teacher_id") != null && !"".equals(lqclassInfo.get("chr_teacher_id").toString())) {
				User user = userService.getUserInfoByUserId(Integer.parseInt(lqclassInfo.get("chr_teacher_id")
						.toString()));
				if (user != null) {
					hsret.put("broRealName", user.getRealName());
				} else {
					hsret.put("broRealName", null);
				}
			} else {
				hsret.put("broRealName", null);
			}
			//11.根据班级id获取班级学员列表
			List<Map<String, Object>> lqclassStuList = tLqclassService.getLqClassStuListByClassId(classid);
			hsret.put("lqclassStuList", lqclassStuList);
			//12.获取班级状态
			hsret.put("classStatus", WebUtil.getClassStatusList());
			//13.创建班级目的
			hsret.put("classGoal", dictService.getDictByType(DictService.DICT_CLASS_GOAL));

			return new ModelAndView("/WEB-INF/view/lqclass/classmember.jsp", hsret);

		} catch (Exception e) {
			e.printStackTrace();
			return WebUtil.getModeAndView404("异常错误.");
		}

	}

	/**
	 * 返回班级的学员列表. 	
	 * @param request
	 * @param response
	 */
	@RequestMapping("/class/classmemberjson.do")
	public void classmemberjson(HttpServletRequest request, HttpServletResponse response) {
		int classid = ServletRequestUtils.getIntParameter(request, "classid", 0);
		if (classid == 0) {
			WebUtil.sendJson(response, 201, "参数不能为空");
			return;
		}

		//==============================================
		try {

			List l = memberservice.getlqClassMember(classid);
			HashMap mp = new HashMap();
			mp.put("code", 200);
			mp.put("datalist", l);

			PrintWriter p = response.getWriter();
			p.print(JSON.toJSONString(mp));

		} catch (Exception se) {
			se.printStackTrace();
			WebUtil.sendJson(response, 202, "异常错误");
		}

	}

	/**
	 * 查询一个班的签到记录 	
	 * @param request
	 * @param response
	 */
	@RequestMapping("/class/selectMemberStudent.do")
	public void selectMemberStudent(HttpServletRequest request, HttpServletResponse response) {
		int classid = ServletRequestUtils.getIntParameter(request, "classid", 0);
		String starttime = ServletRequestUtils.getStringParameter(request, "startTime", "");
		String endtime = ServletRequestUtils.getStringParameter(request, "endTime", "");
		HashMap parm = new HashMap();

		if (StringUtils.isNotBlank(starttime)) {
			parm.put("starttime", starttime);
		}
		if (StringUtils.isNotBlank(endtime)) {
			parm.put("endtime", endtime + " 23:59:59");
		}
		if (classid == 0) {
			WebUtil.sendJson(response, 201, "参数不能为空");
			return;
		}
		parm.put("classid", classid);
		//==============================================
		try {

			List l = memberservice.selectMemberStudent(parm);
			HashMap mp = new HashMap();
			mp.put("code", 200);
			mp.put("datalist", l);

			PrintWriter p = response.getWriter();
			p.print(JSON.toJSONString(mp));

		} catch (Exception se) {
			se.printStackTrace();
			WebUtil.sendJson(response, 202, "异常错误");
		}

	}

}

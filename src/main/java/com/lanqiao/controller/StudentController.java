package com.lanqiao.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.Functions;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.DataTagTypeEnum;
import com.lanqiao.constant.GlobalConstant.PayGoalEnum;
import com.lanqiao.constant.GlobalConstant.StuPaidEnum;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.constant.GlobalConstant.jobStatusEnum;
import com.lanqiao.model.LQUniversities;
import com.lanqiao.model.Student;
import com.lanqiao.model.TDataTag;
import com.lanqiao.model.TLqclass;
import com.lanqiao.model.User;
import com.lanqiao.service.CommonService;
import com.lanqiao.service.DataTagService;
import com.lanqiao.service.DictService;
import com.lanqiao.service.IncomeService;
import com.lanqiao.service.LQCityService;
import com.lanqiao.service.LQUniversitiesService;
import com.lanqiao.service.StudentService;
import com.lanqiao.service.StudentStatusService;
import com.lanqiao.service.StudentclassLogService;
import com.lanqiao.service.TJobDetailService;
import com.lanqiao.service.TLqclassService;
import com.lanqiao.service.UserService;
import com.lanqiao.service.VisitSubLogService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.FileUtil;
import com.lanqiao.util.JsonUtil;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Resource
	LQCityService lQCityService;

	@Resource
	DictService dictService;

	@Resource
	UserService userService;

	@Resource
	StudentService studentService;

	@Resource
	DataTagService dataTagService;

	@Resource
	VisitSubLogService visitSubLogService;

	@Resource
	LQUniversitiesService lqUniversitiesService;

	@Resource
	CommonService cmService;

	@Resource
	StudentclassLogService studentclassLogService;

	@Resource
	TLqclassService tLqclassService;

	@Resource
	StudentStatusService studentStatusService;

	@Resource
	TJobDetailService JobDetailService;

	@Resource
	IncomeService incomeService;

	/**
	 * 
	* @Description:异步查询省份对应的城市(lq_city表)
	* @param pid
	* @return 
	* @return List<HashMap<String,Object>> 
	* @author 罗玉琳
	* @createTime 2016年9月5日 
	 */
	@RequestMapping("/findCitysByProv.do")
	public void findCitysByProv(Integer cid, HttpServletResponse response) throws Exception {
		List<Map<String, Object>> cityList = null;
		try {
			cityList = lQCityService.selectByProvId(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(cityList));
	}

	/**
	 * 
	 *@description:Ajax获取所有省份
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月28日下午11:18:12
	 *
	 */
	@RequestMapping("/getProvince.do")
	public void getProvince(HttpServletResponse response) throws Exception {
		List<Map<String, Object>> list = null;
		try {
			list = lQCityService.findProvinces();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(list));
	}

	/**
	 * 
	 * @param mv
	 * @param opType
	 * @param paramMap
	 * @author 罗玉琳 --去意向学员列表页面
	 * 2016-11-30上午
	 * @return
	 */
	@RequestMapping("/{opType}/goYxStu.do")
	@Functions({ 32, 47, 48, 49 })
	public ModelAndView goYxStu(HttpServletRequest request, ModelAndView mv,
			@PathVariable(value = "opType") String opType) throws Exception {
		StuStatusEnum[] stuStatusEnum = new StuStatusEnum[] { StuStatusEnum.NOREGISTRATION,//未报名
				StuStatusEnum.AUDIT //报名待审核
		};
		List<Map<String, Object>> stuStatusList = new ArrayList<Map<String, Object>>();
		for (int k = 0; k < stuStatusEnum.length; k++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", stuStatusEnum[k].getValue());
			map.put("label", stuStatusEnum[k].getText());
			stuStatusList.add(map);
		}
		//共享学员状态数据
		mv.addObject("stuStatusList", stuStatusList);
		mv.addObject("optype", opType); // jsp 中根据这个数字, 来判断是显示"我关注的"  还是 "全部" 标签. 
		mv.setViewName("/WEB-INF/view/yxStu/yxStudentList.jsp");

		return mv;
	}

	/**
	 * 
	 * @param mv
	 * @param opType
	 * @param paramMap
	 * @author 罗玉琳--获取意向学员表格数据
	 * 2016-11-30上午
	 * @return
	 */
	@RequestMapping("/getYxStuList.do")
	@Functions({ 32, 47, 48, 49 })
	public void getYxStuList(HttpServletRequest request, HttpServletResponse response, @RequestParam String opType)
			throws Exception {

		// 打印调试信息. 
		WebUtil.printParameters(request);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		//设置查询参数
		if (request.getParameter("select_school_name_lq") != null
				&& !"".equals(request.getParameter("select_school_name_lq"))) {
			paramMap.put("select_school_name_lq", request.getParameter("select_school_name_lq"));
		}
		if (request.getParameter("school_sub_name_lq") != null
				&& !"".equals(request.getParameter("school_sub_name_lq"))) {
			paramMap.put("school_sub_name_lq", request.getParameter("school_sub_name_lq"));
		}
		if (request.getParameter("student_name_lq") != null && !"".equals(request.getParameter("student_name_lq"))) {
			paramMap.put("student_name_lq", request.getParameter("student_name_lq"));
		}
		if (request.getParameter("stuStatus_lq") != null && !"".equals(request.getParameter("stuStatus_lq"))) {
			paramMap.put("stuStatus_lq", request.getParameter("stuStatus_lq"));
		}
		if (request.getParameter("grade_lq") != null && !"".equals(request.getParameter("grade_lq"))) {
			paramMap.put("grade_lq", request.getParameter("grade_lq"));
		}
		try {
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))) {
				Integer currpage = Integer.parseInt(request.getParameter("currpage"));
				Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
				paramMap.put("currpage", (currpage - 1) * pageSize);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("pageSize") != null && !"".equals(request.getParameter("pageSize"))) {
				paramMap.put("pageSize", Integer.parseInt(request.getParameter("pageSize")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		SessionUser sessionUser = WebUtil.getLoginUser(request);
		Integer userId = sessionUser.getUserId();
		HashMap<String, Object> hsret = new HashMap<String, Object>();

		paramMap.put("teaUserId", userId);//设置老师的userId以查找关注的学员
		paramMap.put("statusParam", GlobalConstant.StuStatusEnum.AUDIT.getValue());//去掉了未报名条件
		List<Map<String, Object>> yxStuList = new ArrayList<Map<String, Object>>();
		if (opType.equals("0")) {//我关注的操作
									//获取我的关注意向学员列表
			yxStuList = studentService.selectYxStuList(paramMap);
			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {

				int ncount = studentService.getMyFocusYxStuCount(paramMap);
				hsret.put("recordcount", ncount);

			}
		} else {
			//.获取全部意向学员列表
			yxStuList = studentService.selectAllYxStuList(paramMap);
			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = studentService.getAllYxStuCount(paramMap);
				hsret.put("recordcount", ncount);

			}
		}

		hsret.put("datalist", yxStuList);
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		String formateDate = "";
		int i = 0;//计数
		for (Map<String, Object> map : yxStuList) {

			formateDate = formate.format(map.get("updateTime"));
			map.put("updateTime", formateDate);

			int temp = i + 1;
			map.put("option", "");
			map.put("nstatus", 1);//设置是否显示表格里的图标(不为1，亦表头不显示任何图标)
			String auditStatus = "";
			if (Integer.parseInt(map.get("auditStatus").toString()) == GlobalConstant.auditStatusEnum.NOAUDIT
					.getValue()) {
				auditStatus = GlobalConstant.auditStatusEnum.NOAUDIT.getText();
			} else if (Integer.parseInt(map.get("auditStatus").toString()) == GlobalConstant.auditStatusEnum.NOREGIST
					.getValue()) {
				auditStatus = GlobalConstant.auditStatusEnum.NOREGIST.getText();
			} else {
				auditStatus = "审核不通过";
			}
			map.put("auditStatus", auditStatus);
			//map.put("stuStatus", WebUtil.getStuStatusByValue(Integer.parseInt(map.get("status").toString())));//获取学员状态的label值
			if (map.get("dataId") != null) {
				map.put("ifmy", 1);
			} else {
				map.put("ifmy", 0);
			}
			map.put("indexno", temp);
			i++;
		}
		String strout = JSON.toJSONString(hsret);
		// System.out.println (strout);
		JsonUtil.write(response, strout);

	}

	/**
	 * 
	 * @param mv
	 * @param opType
	 * @param paramMap
	 * @author 罗玉琳--去正式学员学员维护列表页面
	 * 2016-11-30上午
	 * @return
	 */
	@RequestMapping("/{opType}/goZsStu.do")
	@Functions({ 52, 53, 54, 55, 56, 57 })
	public ModelAndView goZsStu(HttpServletRequest request, ModelAndView mv,
			@PathVariable(value = "opType") String opType) throws Exception {
		StuPaidEnum[] isAvaiableEnum = new StuPaidEnum[] { StuPaidEnum.PARTOFTHEPAY, StuPaidEnum.ALLPAY,
				StuPaidEnum.SPECIALPAY, StuPaidEnum.PartReturn, StuPaidEnum.AllReturn };

		StuPaidEnum[] hasPaidEnum = new StuPaidEnum[] { StuPaidEnum.NOTPAY, StuPaidEnum.PARTOFTHEPAY,
				StuPaidEnum.ALLPAY, StuPaidEnum.PartReturn, StuPaidEnum.AllReturn };

		StuStatusEnum[] stuStatusEnum = new StuStatusEnum[] { StuStatusEnum.NOCLASS, StuStatusEnum.NOSTARTCLASS,
				StuStatusEnum.BESTUDY, StuStatusEnum.EndStudy, StuStatusEnum.FindJobing, StuStatusEnum.EXPEL,
				StuStatusEnum.QUANTUI, StuStatusEnum.LEAVESCHOLL, StuStatusEnum.XIUXUE, StuStatusEnum.XIUXUEBack,
				StuStatusEnum.DELAYGRADUATE, StuStatusEnum.DELAYWORK, StuStatusEnum.WORKED };
		List<Map<String, Object>> isAvaiableList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < isAvaiableEnum.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", isAvaiableEnum[i].getValue());
			map.put("label", isAvaiableEnum[i].getText());
			isAvaiableList.add(map);
		}
		List<Map<String, Object>> hasPaidList = new ArrayList<Map<String, Object>>();
		for (int j = 0; j < hasPaidEnum.length; j++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", hasPaidEnum[j].getValue());
			map.put("label", hasPaidEnum[j].getText());
			hasPaidList.add(map);
		}
		List<Map<String, Object>> stuStatusList = new ArrayList<Map<String, Object>>();
		for (int k = 0; k < stuStatusEnum.length; k++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", stuStatusEnum[k].getValue());
			map.put("label", stuStatusEnum[k].getText());
			stuStatusList.add(map);
		}
		//共享学员状态/报名费/学费数据
		mv.addObject("stuStatusList", stuStatusList);
		mv.addObject("isAvaiableList", isAvaiableList);
		mv.addObject("hasPaidList", hasPaidList);
		mv.addObject("optype", opType); // jsp 中根据这个数字, 来判断是显示"我管理的"/"我关注的" /  "全部" 标签. 
		mv.setViewName("/WEB-INF/view/zsStu/zsStudentList.jsp");

		return mv;
	}

	/**
	 * 
	 * @param mv
	 * @param opType
	 * @param paramMap
	 * @author 罗玉琳--获取正式学员学员维护表格数据
	 * 2016-12-7上午
	 * @return
	 */
	@RequestMapping("/getZsStudentList.do")
	@Functions({ 52, 53, 54, 55, 56, 57 })
	public void getZsStudentList(HttpServletRequest request, HttpServletResponse response, @RequestParam String opType)
			throws Exception {

		// 打印调试信息. 
		WebUtil.printParameters(request);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		/**====================设置查询参数开始=====================**/
		if (request.getParameter("select_school_name_lq") != null
				&& !"".equals(request.getParameter("select_school_name_lq"))) {
			paramMap.put("select_school_name_lq", request.getParameter("select_school_name_lq"));
		}
		try {
			if (request.getParameter("select_class_name_lq") != null
					&& !"".equals(request.getParameter("select_class_name_lq"))) {
				paramMap.put("select_class_name_lq", Integer.parseInt(request.getParameter("select_class_name_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("stuStatus_lq") != null && !"".equals(request.getParameter("stuStatus_lq"))) {
				paramMap.put("stuStatus_lq", Integer.parseInt(request.getParameter("stuStatus_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("isAvaiable_lq") != null && !"".equals(request.getParameter("isAvaiable_lq"))) {
				paramMap.put("isAvaiable_lq", Integer.parseInt(request.getParameter("isAvaiable_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("hasPaid_lq") != null && !"".equals(request.getParameter("hasPaid_lq"))) {
				paramMap.put("hasPaid_lq", Integer.parseInt(request.getParameter("hasPaid_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("regional_director_name") != null
					&& !"".equals(request.getParameter("regional_director_name"))) {
				paramMap.put("regional_director_name", Integer.parseInt(request.getParameter("regional_director_name")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("recruit_manager_name") != null
					&& !"".equals(request.getParameter("recruit_manager_name"))) {
				paramMap.put("recruit_manager_name", Integer.parseInt(request.getParameter("recruit_manager_name")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("course_advisor_name") != null
					&& !"".equals(request.getParameter("course_advisor_name"))) {
				paramMap.put("course_advisor_name", Integer.parseInt(request.getParameter("course_advisor_name")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		if (request.getParameter("student_name_lq") != null && !"".equals(request.getParameter("student_name_lq"))) {
			paramMap.put("student_name_lq", request.getParameter("student_name_lq"));
		}

		try {
			if (request.getParameter("beginTime_lq") != null && !"".equals(request.getParameter("beginTime_lq"))) {
				paramMap.put("beginTime_lq",
						new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("beginTime_lq")));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			if (request.getParameter("endTime_lq") != null && !"".equals(request.getParameter("endTime_lq"))) {
				paramMap.put("endTime_lq", new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("endTime_lq")));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))) {
				Integer currpage = Integer.parseInt(request.getParameter("currpage"));
				Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
				paramMap.put("currpage", (currpage - 1) * pageSize);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("pageSize") != null && !"".equals(request.getParameter("pageSize"))) {
				paramMap.put("pageSize", Integer.parseInt(request.getParameter("pageSize")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		/**====================设置查询参数结束=====================**/
		SessionUser sessionUser = WebUtil.getLoginUser(request);
		Integer userId = sessionUser.getUserId();//当前登录者userId
		HashMap<String, Object> hsret = new HashMap<String, Object>();

		paramMap.put("teaUserId", userId);//设置老师的userId以查找我关注/管理的学员
		//设置学员状态查询参数
		StringBuffer str = new StringBuffer().append(GlobalConstant.StuStatusEnum.NOCLASS.getValue()).append(",");//未分班
		str.append(GlobalConstant.StuStatusEnum.NOSTARTCLASS.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.BESTUDY.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.EndStudy.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.FindJobing.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.EXPEL.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.QUANTUI.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.LEAVESCHOLL.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.XIUXUE.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.XIUXUEBack.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.DELAYGRADUATE.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.DELAYWORK.getValue()).append(",");
		str.append(GlobalConstant.StuStatusEnum.WORKED.getValue());
		String stuStatusParam = str.toString();
		paramMap.put("stuStatusParam", stuStatusParam);//学员状态
		paramMap.put("auditStatusParam", GlobalConstant.auditStatusEnum.PASS.getValue());//学员审核状态:通过
		List<Map<String, Object>> zsStuList = new ArrayList<Map<String, Object>>();
		if (opType.equals("0")) {//我关注的操作
									//获取我的关注的正式学员列表
			zsStuList = studentService.selectMyFocusZsStuList(paramMap);
			// 将参数存储到session中, 以便后面导出的时候使用. 
			HashMap hmclone = new HashMap();
			hmclone.putAll(paramMap);
			request.getSession().setAttribute("Last_Export_Param", hmclone);
			request.getSession().setAttribute("Last_optype", 0);

			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = studentService.getMyFocusZsStuCount(paramMap);
				hsret.put("recordcount", ncount);//我关注的正式学员总数(包含掉退学，开除，劝退)
			}
		} else if (opType.equals("1")) {
			//.获取全部正式学员列表
			zsStuList = studentService.selectAllZsStuList(paramMap);
			// 将参数存储到session中, 以便后面导出的时候使用. 
			HashMap hmclone = new HashMap();
			hmclone.putAll(paramMap);
			request.getSession().setAttribute("Last_Export_Param", hmclone);
			request.getSession().setAttribute("Last_optype", 1);

			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = studentService.getAllZsStuCount(paramMap);
				hsret.put("recordcount", ncount);//全部正式学员总数(包含退学，开除，劝退)
			}
		} else {
			//.获取我管理的正式学员列表
			zsStuList = studentService.selectMyManageZsStuList(paramMap);
			// 将参数存储到session中, 以便后面导出的时候使用. 
			HashMap hmclone = new HashMap();
			hmclone.putAll(paramMap);
			request.getSession().setAttribute("Last_Export_Param", hmclone);
			request.getSession().setAttribute("Last_optype", 2);

			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = studentService.getMyManageZsStuCount(paramMap);
				hsret.put("recordcount", ncount);//管理的正式学员总数(包换退学，开除，劝退)
			}
		}
		/**==========统计开除，劝退，退学人数开始=========**/
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.EXPEL.getValue());//设置学员状态为开除
		int expelCount = studentService.getStuCount(paramMap);//查询今年我关注的正式学员中被开除的记录数
		hsret.put("expelCount", expelCount);
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.QUANTUI.getValue());//设置学员状态为劝退
		int quanTuiCount = studentService.getStuCount(paramMap);//查询今年我关注的正式学员中被劝退的记录数
		hsret.put("quanTuiCount", quanTuiCount);
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.LEAVESCHOLL.getValue());//设置学员状态为退学
		int leaveSchollCount = studentService.getStuCount(paramMap);//查询今年我关注的正式学员中被退学的记录数
		hsret.put("leaveSchollCount", leaveSchollCount);
		/**==========统计开除，劝退，退学人数结束=========**/

		hsret.put("datalist", zsStuList);
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		String formateDate = "";
		Map<String, Object> param = new HashMap<String, Object>();
		int i = 0;//计数
		for (Map<String, Object> map : zsStuList) {

			formateDate = formate.format(map.get("updateTime"));
			map.put("updateTime", formateDate);
			int temp = i + 1;
			map.put("option", "");
			map.put("nstatus", 1);//设置是否显示表格里的图标(不为1，亦表头不显示任何图标)
			map.put("stuStatus", WebUtil.getStuStatusByValue(Integer.parseInt(map.get("status").toString())));//获取学员状态的label值
			//设置将课程类别value转换成文字的参数
			param.put("type", "lq_courseType");
			param.put("value", map.get("courseType"));
			map.put("courseType", dictService.getLabelByValAndType(param));//courseType文字显示
			//设置将院校类别value转换成文字的参数
			param.put("type", "school_type");
			param.put("value", map.get("schoolTypeCode"));
			map.put("schoolType", dictService.getLabelByValAndType(param));//schoolType文字显示
			//设置将报名费value转换成文字的参数
			if (map.get("isAvaiable") != null) {
				map.put("isAvaiable", WebUtil.getSignFeeName(Integer.parseInt(map.get("isAvaiable").toString())));//isAvaiable文字显示
			}
			//设置将学费value转换成文字的参数
			if (map.get("hasPaid") != null) {
				map.put("hasPaid", WebUtil.getSignFeeName(Integer.parseInt(map.get("hasPaid").toString())));//hasPaid文字显示
			}
			if (map.get("dataId") != null) {
				map.put("ifmy", 1);
			} else {
				map.put("ifmy", 0);
			}
			map.put("indexno", temp);
			i++;
		}
		String strout = JSON.toJSONString(hsret);
		JsonUtil.write(response, strout);

	}

	/**
	 * 
	 * @param mv
	 * @param opType
	 * @param paramMap
	 * @author 罗玉琳--去正式学员异常学员列表页面
	 * 2016-11-30上午
	 * @return
	 */
	@RequestMapping("/{opType}/goUnNormalStu.do")
	@Functions({ 58, 59, 60 })
	public ModelAndView goUnNormalStu(HttpServletRequest request, ModelAndView mv,
			@PathVariable(value = "opType") String opType) throws Exception {
		StuPaidEnum[] isAvaiableEnum = new StuPaidEnum[] { StuPaidEnum.PARTOFTHEPAY, StuPaidEnum.ALLPAY,
				StuPaidEnum.SPECIALPAY, StuPaidEnum.PartReturn, StuPaidEnum.AllReturn };

		StuPaidEnum[] hasPaidEnum = new StuPaidEnum[] { StuPaidEnum.NOTPAY, StuPaidEnum.PARTOFTHEPAY,
				StuPaidEnum.ALLPAY, StuPaidEnum.PartReturn, StuPaidEnum.AllReturn };

		StuStatusEnum[] stuStatusEnum = new StuStatusEnum[] { StuStatusEnum.NOCLASS, StuStatusEnum.NOSTARTCLASS,
				StuStatusEnum.BESTUDY, StuStatusEnum.EndStudy, StuStatusEnum.FindJobing, StuStatusEnum.EXPEL,
				StuStatusEnum.QUANTUI, StuStatusEnum.LEAVESCHOLL, StuStatusEnum.XIUXUE, StuStatusEnum.XIUXUEBack,
				StuStatusEnum.DELAYGRADUATE, StuStatusEnum.DELAYWORK, StuStatusEnum.WORKED };
		List<Map<String, Object>> isAvaiableList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < isAvaiableEnum.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", isAvaiableEnum[i].getValue());
			map.put("label", isAvaiableEnum[i].getText());
			isAvaiableList.add(map);
		}
		List<Map<String, Object>> hasPaidList = new ArrayList<Map<String, Object>>();
		for (int j = 0; j < hasPaidEnum.length; j++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", hasPaidEnum[j].getValue());
			map.put("label", hasPaidEnum[j].getText());
			hasPaidList.add(map);
		}
		List<Map<String, Object>> stuStatusList = new ArrayList<Map<String, Object>>();
		for (int k = 0; k < stuStatusEnum.length; k++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", stuStatusEnum[k].getValue());
			map.put("label", stuStatusEnum[k].getText());
			stuStatusList.add(map);
		}
		//共享学员状态/报名费/学费数据
		mv.addObject("stuStatusList", stuStatusList);
		mv.addObject("isAvaiableList", isAvaiableList);
		mv.addObject("hasPaidList", hasPaidList);
		mv.addObject("optype", opType); // jsp 中根据这个数字, 来判断是显示"我管理的"/"我关注的" /  "全部" 标签. 
		mv.setViewName("/WEB-INF/view/zsStu/unNormalStuList.jsp");

		return mv;
	}

	/**
	 * 
	 * @param mv
	 * @param opType
	 * @param paramMap
	 * @author 罗玉琳--获取正式学员异常学员表格数据
	 * 2016-12-7上午
	 * @return
	 */
	@RequestMapping("/getUnNormalStuList.do")
	@Functions({ 58, 59, 60 })
	public void getUnNormalStuList(HttpServletRequest request, HttpServletResponse response, @RequestParam String opType)
			throws Exception {

		// 打印调试信息. 
		WebUtil.printParameters(request);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		/**====================设置查询参数开始=====================**/
		if (request.getParameter("select_school_name_lq") != null
				&& !"".equals(request.getParameter("select_school_name_lq"))) {
			paramMap.put("select_school_name_lq", request.getParameter("select_school_name_lq"));
		}
		try {
			if (request.getParameter("select_class_name_lq") != null
					&& !"".equals(request.getParameter("select_class_name_lq"))) {
				paramMap.put("select_class_name_lq", Integer.parseInt(request.getParameter("select_class_name_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("stuStatus_lq") != null && !"".equals(request.getParameter("stuStatus_lq"))) {
				paramMap.put("stuStatus_lq", Integer.parseInt(request.getParameter("stuStatus_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("isAvaiable_lq") != null && !"".equals(request.getParameter("isAvaiable_lq"))) {
				paramMap.put("isAvaiable_lq", Integer.parseInt(request.getParameter("isAvaiable_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("hasPaid_lq") != null && !"".equals(request.getParameter("hasPaid_lq"))) {
				paramMap.put("hasPaid_lq", Integer.parseInt(request.getParameter("hasPaid_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("regional_director_name") != null
					&& !"".equals(request.getParameter("regional_director_name"))) {
				paramMap.put("regional_director_name", Integer.parseInt(request.getParameter("regional_director_name")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("recruit_manager_name") != null
					&& !"".equals(request.getParameter("recruit_manager_name"))) {
				paramMap.put("recruit_manager_name", Integer.parseInt(request.getParameter("recruit_manager_name")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("course_advisor_name") != null
					&& !"".equals(request.getParameter("course_advisor_name"))) {
				paramMap.put("course_advisor_name", Integer.parseInt(request.getParameter("course_advisor_name")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		if (request.getParameter("student_name_lq") != null && !"".equals(request.getParameter("student_name_lq"))) {
			paramMap.put("student_name_lq", request.getParameter("student_name_lq"));
		}

		try {
			if (request.getParameter("beginTime_lq") != null && !"".equals(request.getParameter("beginTime_lq"))) {
				paramMap.put("beginTime_lq",
						new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("beginTime_lq")));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			if (request.getParameter("endTime_lq") != null && !"".equals(request.getParameter("endTime_lq"))) {
				paramMap.put("endTime_lq", new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("endTime_lq")));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		try {
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))) {
				Integer currpage = Integer.parseInt(request.getParameter("currpage"));
				Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
				paramMap.put("currpage", (currpage - 1) * pageSize);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("pageSize") != null && !"".equals(request.getParameter("pageSize"))) {
				paramMap.put("pageSize", Integer.parseInt(request.getParameter("pageSize")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		/**====================设置查询参数结束=====================**/
		SessionUser sessionUser = WebUtil.getLoginUser(request);
		Integer userId = sessionUser.getUserId();//当前登录者userId
		HashMap<String, Object> hsret = new HashMap<String, Object>();

		paramMap.put("teaUserId", userId);//设置老师的userId以查找我关注/管理的学员
		//设置学员状态查询参数
		StringBuffer str = new StringBuffer().append(GlobalConstant.StuStatusEnum.EXPEL.getValue()).append(",");//开除
		str.append(GlobalConstant.StuStatusEnum.QUANTUI.getValue()).append(",");//劝退
		str.append(GlobalConstant.StuStatusEnum.LEAVESCHOLL.getValue()).append(",");//退学
		str.append(GlobalConstant.StuStatusEnum.XIUXUE.getValue()).append(",");//休学
		str.append(GlobalConstant.StuStatusEnum.XIUXUEBack.getValue()).append(",");//休学重返
		str.append(GlobalConstant.StuStatusEnum.DELAYGRADUATE.getValue()).append(",");//延期结业
		str.append(GlobalConstant.StuStatusEnum.DELAYWORK.getValue());//延期就业
		String stuStatusParam = str.toString();
		paramMap.put("stuStatusParam", stuStatusParam);//学员状态
		paramMap.put("auditStatusParam", GlobalConstant.auditStatusEnum.PASS.getValue());//学员审核状态:通过
		List<Map<String, Object>> zsStuList = new ArrayList<Map<String, Object>>();
		if (opType.equals("0")) {//我关注的操作
									//获取我的关注的正式学员列表
			zsStuList = studentService.selectMyFocusZsStuList(paramMap);

			//保存到session, 以便导出
			HashMap hmclone = new HashMap();
			hmclone.putAll(paramMap);
			request.getSession().setAttribute("Last_Export_Param", hmclone);
			request.getSession().setAttribute("Last_optype", 0);

			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = studentService.getMyFocusZsStuCount(paramMap);
				hsret.put("recordcount", ncount);//当前年份我关注的正式学员中异常状态的学员总数(包含掉退学，开除，劝退，休学，休学重返，延期结业，延期就业)
			}
		} else if (opType.equals("1")) {
			//.获取全部正式学员列表
			zsStuList = studentService.selectAllZsStuList(paramMap);
			//保存到session, 以便导出
			HashMap hmclone = new HashMap();
			hmclone.putAll(paramMap);
			request.getSession().setAttribute("Last_Export_Param", hmclone);
			request.getSession().setAttribute("Last_optype", 1);

			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = studentService.getAllZsStuCount(paramMap);
				hsret.put("recordcount", ncount);//当前年份我关注的正式学员中异常状态的学员总数(包含掉退学，开除，劝退，休学，休学重返，延期结业，延期就业)
			}
		} else {
			//.获取我管理的正式学员列表
			zsStuList = studentService.selectMyManageZsStuList(paramMap);
			//保存到session, 以便导出
			HashMap hmclone = new HashMap();
			hmclone.putAll(paramMap);
			request.getSession().setAttribute("Last_Export_Param", hmclone);
			request.getSession().setAttribute("Last_optype", 2);

			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = studentService.getMyManageZsStuCount(paramMap);
				hsret.put("recordcount", ncount);//当前年份我关注的正式学员中异常状态的学员总数(包含掉退学，开除，劝退，休学，休学重返，延期结业，延期就业)
			}
		}
		/**================统计当前年份开除/劝退/退学/休学/休学重返/延期结业/延期就业人数==================**/
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.EXPEL.getValue());//设置学员状态为开除
		int expelCount = studentService.getStuCount(paramMap);//查询今年正式学员中被开除的记录数
		hsret.put("expelCount", expelCount);
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.QUANTUI.getValue());//设置学员状态为劝退
		int quanTuiCount = studentService.getStuCount(paramMap);//查询今年正式学员中被劝退的记录数
		hsret.put("quanTuiCount", quanTuiCount);
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.LEAVESCHOLL.getValue());//设置学员状态为退学
		int leaveSchollCount = studentService.getStuCount(paramMap);//查询今年正式学员中被退学的记录数
		hsret.put("leaveSchollCount", leaveSchollCount);
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.XIUXUE.getValue());//设置学员状态为休学
		int xiuXueCount = studentService.getStuCount(paramMap);//查询今年正式学员中被休学的记录数
		hsret.put("xiuXueCount", xiuXueCount);
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.XIUXUEBack.getValue());//设置学员状态为休学重返
		int xiuXueBackCount = studentService.getStuCount(paramMap);//查询今年正式学员中休学重返的记录数
		hsret.put("xiuXueBackCount", xiuXueBackCount);
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.DELAYGRADUATE.getValue());//设置学员状态为延期结业
		int delayGraduateCount = studentService.getStuCount(paramMap);//查询今年正式学员中延期结业的记录数
		hsret.put("delayGraduateCount", delayGraduateCount);
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.DELAYWORK.getValue());//设置学员状态为延期就业
		int delayWorkCount = studentService.getStuCount(paramMap);//查询今年正式学员中延期就业的记录数
		hsret.put("delayWorkCount", delayWorkCount);
		/**================统计当前年份开除/劝退/退学/休学/休学重返/延期结业/延期就业人数==================**/

		hsret.put("datalist", zsStuList);
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		String formateDate = "";
		Map<String, Object> param = new HashMap<String, Object>();
		int i = 0;//计数
		for (Map<String, Object> map : zsStuList) {

			formateDate = formate.format(map.get("updateTime"));
			map.put("updateTime", formateDate);
			int temp = i + 1;
			map.put("option", "");
			map.put("nstatus", 1);//设置是否显示表格里的图标(不为1，亦表头不显示任何图标)
			map.put("stuStatus", WebUtil.getStuStatusByValue(Integer.parseInt(map.get("status").toString())));//获取学员状态的label值
			//设置将课程类别value转换成文字的参数
			param.put("type", "lq_courseType");
			param.put("value", map.get("courseType"));
			map.put("courseType", dictService.getLabelByValAndType(param));//courseType文字显示
			//设置将院校类别value转换成文字的参数
			param.put("type", "school_type");
			param.put("value", map.get("schoolTypeCode"));
			map.put("schoolType", dictService.getLabelByValAndType(param));//schoolType文字显示
			//设置将报名费value转换成文字的参数
			if (map.get("isAvaiable") != null) {
				map.put("isAvaiable", WebUtil.getSignFeeName(Integer.parseInt(map.get("isAvaiable").toString())));//isAvaiable文字显示
			}
			//设置将学费value转换成文字的参数
			if (map.get("hasPaid") != null) {
				map.put("hasPaid", WebUtil.getSignFeeName(Integer.parseInt(map.get("hasPaid").toString())));//hasPaid文字显示
			}
			if (map.get("dataId") != null) {
				map.put("ifmy", 1);
			} else {
				map.put("ifmy", 0);
			}
			map.put("indexno", temp);
			i++;
		}
		String strout = JSON.toJSONString(hsret);
		JsonUtil.write(response, strout);

	}

	/**
	 * 
	 * @param mv
	 * @param opType
	 * @param paramMap
	 * @author 罗玉琳--去正式学员新学员分班列表页面
	 * 2016-11-30上午
	 * @return
	 */
	@RequestMapping("/{opType}/goZsDivideClass.do")
	@Functions({ 50, 51 })
	public ModelAndView goZsDivideClass(HttpServletRequest request, ModelAndView mv,
			@PathVariable(value = "opType") String opType) throws Exception {
		List<Map<String, Object>> courseTypeList = null;
		try {
			courseTypeList = dictService.getDictByType("lq_courseType");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//共享学员状态数据
		mv.addObject("courseTypeList", courseTypeList);
		mv.addObject("optype", opType); // jsp 中根据这个数字, 来判断是显示"我管理的"/"我关注的" /  "全部" 标签. 
		mv.setViewName("/WEB-INF/view/zsStu/zsDivideClass.jsp");

		return mv;
	}

	/**
	 * 
	 * @param mv
	 * @param opType
	 * @param paramMap
	 * @author 罗玉琳--获取正式学员待分班表格数据
	 * 2016-12-6上午
	 * @return
	 */
	@RequestMapping("/getNoClassStuList.do")
	public void getNoClassStuList(HttpServletRequest request, HttpServletResponse response, @RequestParam String opType)
			throws Exception {

		// 打印调试信息. 
		WebUtil.printParameters(request);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		/**====================设置查询参数开始=====================**/
		if (request.getParameter("select_school_name_lq") != null
				&& !"".equals(request.getParameter("select_school_name_lq"))) {
			paramMap.put("select_school_name_lq", request.getParameter("select_school_name_lq"));
		}
		try {
			if (request.getParameter("courseType_lq") != null && !"".equals(request.getParameter("courseType_lq"))) {
				paramMap.put("courseType_lq", Integer.parseInt(request.getParameter("courseType_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("regional_director_name") != null
					&& !"".equals(request.getParameter("regional_director_name"))) {
				paramMap.put("regional_director_name", Integer.parseInt(request.getParameter("regional_director_name")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("recruit_manager_name") != null
					&& !"".equals(request.getParameter("recruit_manager_name"))) {
				paramMap.put("recruit_manager_name", Integer.parseInt(request.getParameter("recruit_manager_name")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("course_advisor_name") != null
					&& !"".equals(request.getParameter("course_advisor_name"))) {
				paramMap.put("course_advisor_name", Integer.parseInt(request.getParameter("course_advisor_name")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		if (request.getParameter("student_name_lq") != null && !"".equals(request.getParameter("student_name_lq"))) {
			paramMap.put("student_name_lq", request.getParameter("student_name_lq"));
		}

		try {
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))) {
				Integer currpage = Integer.parseInt(request.getParameter("currpage"));
				Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
				paramMap.put("currpage", (currpage - 1) * pageSize);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("pageSize") != null && !"".equals(request.getParameter("pageSize"))) {
				paramMap.put("pageSize", Integer.parseInt(request.getParameter("pageSize")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		/**====================设置查询参数结束=====================**/
		SessionUser sessionUser = WebUtil.getLoginUser(request);
		Integer userId = sessionUser.getUserId();//当前登录者userId
		HashMap<String, Object> hsret = new HashMap<String, Object>();

		paramMap.put("teaUserId", userId);//设置老师的userId以查找关注的学员
		paramMap.put("statusParam", GlobalConstant.StuStatusEnum.NOCLASS.getValue());//学员状态:未分班
		paramMap.put("auditStatus", GlobalConstant.auditStatusEnum.PASS.getValue());//学员审核状态:通过
		List<Map<String, Object>> noClassStuList = new ArrayList<Map<String, Object>>();
		if (opType.equals("0")) {//我关注的操作
									//获取我的关注未分班学员列表
			noClassStuList = studentService.selectMyFocusNoClassStuList(paramMap);
			//参数保存到session , 以便报表导出
			HashMap hmclone = new HashMap();
			hmclone.putAll(paramMap);
			request.getSession().setAttribute("Last_Export_Param", hmclone);
			request.getSession().setAttribute("Last_optype", 0);

			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {

				int ncount = studentService.getMyFocusNoClassStuCount(paramMap);
				hsret.put("recordcount", ncount);

			}
		} else {
			//.获取全部未分班学员列表
			noClassStuList = studentService.selectAllNoClassStuList(paramMap);
			//参数保存到session , 以便报表导出
			HashMap hmclone = new HashMap();
			hmclone.putAll(paramMap);
			request.getSession().setAttribute("Last_Export_Param", hmclone);
			request.getSession().setAttribute("Last_optype", 1);

			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = studentService.getAllNoClassStuCount(paramMap);
				hsret.put("recordcount", ncount);

			}
		}

		hsret.put("datalist", noClassStuList);
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		String formateDate = "";
		Map<String, Object> param = new HashMap<String, Object>();
		int i = 0;//计数
		for (Map<String, Object> map : noClassStuList) {

			formateDate = formate.format(map.get("updateTime"));
			map.put("updateTime", formateDate);
			int temp = i + 1;
			map.put("option", "");
			map.put("nstatus", 1);//设置是否显示表格里的图标(不为1，亦表头不显示任何图标)
			map.put("stuStatus", WebUtil.getStuStatusByValue(Integer.parseInt(map.get("status").toString())));//获取学员状态的label值
			//设置将课程类别value转换成文字的参数
			param.put("type", "lq_courseType");
			param.put("value", map.get("courseType"));
			map.put("courseType", dictService.getLabelByValAndType(param));//courseType文字显示
			map.put("courseTypeValue", map.get("courseType"));//courseType的value,用于列表选中学员的课程类别判断
			if (map.get("dataId") != null) {
				map.put("ifmy", 1);
			} else {
				map.put("ifmy", 0);
			}
			map.put("indexno", temp);
			i++;
		}
		String strout = JSON.toJSONString(hsret);
		JsonUtil.write(response, strout);

	}

	/**
	 * 
	 *@description:跳转到填写学生信息弹窗
	 *@param type：区分是老师为学生添加还是学生自己添加信息，验证提交的方法不一样
	 *@return：
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年11月28日下午6:46:53
	 *
	 */
	@RequestMapping("/showSubmitStuInfoDialog.do")
	@Functions({ 32 })
	public ModelAndView showSubmitStuInfoDialog(@RequestParam String type, ModelAndView mv) throws Exception {
		try {
			mv = new ModelAndView("/WEB-INF/view/yxStu/submit_student_info.jsp");
			//1,teacherAdd 或者 studentAdd
			mv.addObject("type", type);
			//2，加载培训类型
			mv.addObject("courseTypeList", dictService.getDictByType(DictService.DICT_COURSE_TYPE));
			//3，加载院校类型
			mv.addObject("schoolTypeList", dictService.getDictByType(DictService.DICT_SCHOOL_TYPE));
			//4，加载省份
			mv.addObject("provinceList", lQCityService.findProvinces());
			//5,就业意向地点
			mv.addObject("cityPreferList", dictService.getDictByType(DictService.DICT_JOB_JOBCITYCODE));

		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}

	/**
	 * 
	* @Description:根据学员id跳转到学员详情页面
	* @param mv
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年11月28日 下午4:24:02
	 */
	@RequestMapping("/goYxStuInfo.do")
	public ModelAndView goYxStuInfo(ModelAndView mv, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		mv.setViewName("/WEB-INF/view/yxStu/yxStu_info.jsp");
		try {
			int reqUserId = Integer.parseInt(request.getParameter("userId"));
			//1.用户基本信息
			User user = userService.getUserInfoByUserId(reqUserId);
			mv.addObject("user", user);
			//2.学员信息
			Student stuInfo = studentService.getStuInfoByUserId(reqUserId);
			mv.addObject("stuInfo", stuInfo);
			//3.根据权限展示不同的选项卡
			String qx = request.getParameter("qx");
			if ("yxStu".equals(qx)) {//意向
				mv.addObject("qx", "yxStu");
			} else if ("zsStu".equals(qx)) {//正式
				mv.addObject("qx", "zsStu");
			} else if ("jyStu".equals(qx)) {//就业
				mv.addObject("qx", "jyStu");
			} else if ("hkStu".equals(qx)) {//回款
				mv.addObject("qx", "hkStu");
			}
			//4.查询学员标记
			TDataTag dataTag = new TDataTag();
			SessionUser sessionUser = WebUtil.getLoginUser(request);
			int userId = sessionUser.getUserId();
			dataTag.setUserid(userId);//当前登录用户id
			dataTag.setDatatype(Integer.parseInt(DataTagTypeEnum.STUDENTS.getValue()));//被标记的数据类型 1:院校2:班级,3:学员
			dataTag.setDataId(reqUserId);//学员id
			List<Map<String, Object>> dataTagList = dataTagService.getTagByUserIdAndData(dataTag);
			mv.addObject("dataTagList", dataTagList);
			//5.学员状态
			mv.addObject("studentStatus", WebUtil.getStuStatusList());

			mv.addObject("NOCLASS", StuStatusEnum.NOCLASS.getValue());
			mv.addObject("NOSTARTCLASS", StuStatusEnum.NOSTARTCLASS.getValue());
			mv.addObject("BESTUDY", StuStatusEnum.BESTUDY.getValue());
			mv.addObject("EndStudy", StuStatusEnum.EndStudy.getValue());
			mv.addObject("FindJobing", StuStatusEnum.FindJobing.getValue());
			mv.addObject("EXPEL", StuStatusEnum.EXPEL.getValue());
			mv.addObject("QUANTUI", StuStatusEnum.QUANTUI.getValue());
			mv.addObject("LEAVESCHOLL", StuStatusEnum.LEAVESCHOLL.getValue());
			mv.addObject("XIUXUE", StuStatusEnum.XIUXUE.getValue());
			mv.addObject("XIUXUEBack", StuStatusEnum.XIUXUEBack.getValue());
			mv.addObject("DELAYGRADUATE", StuStatusEnum.DELAYGRADUATE.getValue());
			mv.addObject("DELAYWORK", StuStatusEnum.DELAYWORK.getValue());
			mv.addObject("WORKED", StuStatusEnum.WORKED.getValue());

			//6.性别
			mv.addObject("sex", dictService.getDictByType(DictService.DICT_SEX));
			//7.院校类别
			mv.addObject("schoolType", dictService.getDictByType(DictService.DICT_SCHOOL_TYPE));
			//8.报名费
			mv.addObject("isAvaiable", WebUtil.getPayDList());
			//9.学费
			mv.addObject("stuHaspaid", WebUtil.getPayDList());
			//10.访谈记录
			List<Map<String, Object>> vslList = visitSubLogService.getVisitSubLogList(reqUserId);
			mv.addObject("vslList", vslList);
			mv.addObject("vslListSize", vslList.size());
			//11.就业意向地址
			mv.addObject("jobCityCode", dictService.getDictByType(DictService.DICT_JOB_JOBCITYCODE));
			//12.根据院校code获取院校名称
			if (stuInfo.getUnivCode() != null && !"".equals(stuInfo.getUnivCode())) {
				LQUniversities lquni = lqUniversitiesService.getLqUniverInfo((long) stuInfo.getUnivCode());
				mv.addObject("univName", lquni.getUnivName());
			} else {
				mv.addObject("univName", null);
			}
			//13.获取省市
			if (stuInfo.getSchoolProvCode() != null && !"".equals(stuInfo.getSchoolProvCode())
					&& stuInfo.getSchoolCityCode() != null && !"".equals(stuInfo.getSchoolCityCode())) {
				mv.addObject("schoolProvName", lQCityService.getById(stuInfo.getSchoolProvCode()));
				mv.addObject("schoolCityName", lQCityService.getById(stuInfo.getSchoolCityCode()));
			} else {
				mv.addObject("schoolProvName", null);
				mv.addObject("schoolCityName", null);
			}
			//14.课程类别
			mv.addObject("courseType", dictService.getDictByType(DictService.DICT_COURSE_TYPE));
			//15.老师列表
			mv.addObject("distinctTeacher", cmService.ListDistinctTeacherName());
			//16.班级信息(当前+历史)
			List<Map<String, Object>> classLog = studentclassLogService.getClassLogByUserId(reqUserId);
			mv.addObject("classLog", classLog);
			//17.班级状态
			mv.addObject("classStatus", WebUtil.getClassStatusList());
			//18.企业规模
			mv.addObject("cocompScale", dictService.getDictByType(DictService.DICT_COCOMP_SCALE));
			//19.企业性质
			mv.addObject("cocompType", dictService.getDictByType(DictService.DICT_COCOMP_TYPE));
			//20.学员状态流水
			mv.addObject("stuStatusList", studentStatusService.getStuStatusLogByUserId(reqUserId));
			//21.职业信息(t_job_detail)+就业信息(当前+历史)
			List<Map<String, Object>> jobDetailList = JobDetailService.getJobDetailBystuId(reqUserId);
			mv.addObject("jobDetailList", jobDetailList);
			mv.addObject("jobDetailListSize", jobDetailList.size());
			int dismissTime = 1;//0:有一条离职时间不存在  1:都存在离职时间
			for (int t = 0; t < jobDetailList.size(); t++) {
				if (jobDetailList.get(t).get("dismiss_time") == null
						|| "".equals(jobDetailList.get(t).get("dismiss_time"))) {
					dismissTime = 0;
				}
			}
			mv.addObject("dismissTime", dismissTime);//判断是否存在离职时间
			//22.报名费(汇总)
			List<Map<String, Object>> bmList = incomeService.getIncomeLogBystuId(reqUserId,
					PayGoalEnum.SIGNMONEY.getValue());
			if (bmList.size() > 0) {
				mv.addObject("bmInfo", bmList.get(0));
			} else {
				mv.addObject("bmInfo", null);
			}
			//23.报名费(历史)
			List<Map<String, Object>> bmlsList = incomeService.getIncomeListBystuId(reqUserId,
					PayGoalEnum.SIGNMONEY.getValue());
			mv.addObject("bmlsList", bmlsList);
			mv.addObject("bmlsSize", bmlsList.size());
			//24.实训费(汇总)
			List<Map<String, Object>> sxList = incomeService.getIncomeLogBystuId(reqUserId,
					PayGoalEnum.LEARNMONEY.getValue());
			if (sxList.size() > 0) {
				mv.addObject("sxInfo", sxList.get(0));
			} else {
				mv.addObject("sxInfo", null);
			}
			//25.实训费(历史)
			List<Map<String, Object>> sxlsList = incomeService.getIncomeListBystuId(reqUserId,
					PayGoalEnum.LEARNMONEY.getValue());
			mv.addObject("sxlsList", sxlsList);
			mv.addObject("sxlsSize", sxlsList.size());
			//26.就业方式列表
			mv.addObject("jobFromTypeList", WebUtil.getJobFromTypeList());
			//27.岗位性质
			mv.addObject("inviteType", dictService.getDictByType(DictService.DICT_POSITION_TYPE));
			//28.职业状态列表
			mv.addObject("jobStatusList", WebUtil.getJobStatusList());
			//29.在职的职业状态
			mv.addObject("jobing", jobStatusEnum.JOBING.getValue());
			//30.缴费方式
			mv.addObject("payTypeList", dictService.getDictByType(DictService.DICT_PAY_TYPE));
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}

	/**
	 * 
	* @Description:编辑后保存学生信息
	* @param map
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月2日 下午3:35:46
	 */

	@Functions({ 81, 83, 84, 85 })
	@RequestMapping("/editStuInfo.do")
	public void editStuInfo(@RequestParam Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			//1.如果存在user表信息,则修改
			userService.updateUserInfo(map);
			//2.如果存在student表信息,则修改
			studentService.updateStuInfo(map);
			//3.更新session
			userService.BindDirectLogin(WebUtil.getLoginUser(request).getUserId(), request);
			returnMap.put("code", "200");
		} catch (Exception e) {
			returnMap.put("code", "201");
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	* @Description:获取省列表
	* @param map
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月2日 下午3:36:42
	 */
	@RequestMapping("/getProvList.do")
	public void getProvList(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> provList = lQCityService.findProvinces();
			returnMap.put("provList", provList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	* @Description:上传图片并保存
	* @param imgFile
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月3日 上午11:01:00
	 */
	@RequestMapping(value = "/uploadImgAndSave.do")
	public void uploadImgAndSave(@RequestParam MultipartFile imgFile, Integer userId, String type,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//这句很重要, 保证文件上传的ajax 能正常接收.
		response.setContentType("text/html; charset=UTF-8");

		System.out.println("begin uploadImgAndSave");
		Map<String, Object> map = new HashMap<String, Object>();
		if (imgFile == null || imgFile.isEmpty()) {
			map.put("code", 201);
			JsonUtil.write(response, JSON.toJSONString(map));
			return;
		}
		if (imgFile.getSize() > 3 * 1024 * 1024) {
			map.put("code", 202);
			JsonUtil.write(response, JSON.toJSONString(map));
			return;
		}

		//判断图片的后缀
		String suffixs = GlobalConstant.PICTURESUFFIX;
		String suff = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
		if (suffixs.indexOf(suff) != -1) {
			try {
				//1.上传
				String fileName = FileUtil.uploadIdCardImgFile(request, imgFile);
				//2.保存
				if ("A".equals(type)) {
					map.put("userId", userId);
					map.put("idcardFrontImg", fileName);
				} else {
					map.put("userId", userId);
					map.put("idcardBackImg", fileName);
				}
				studentService.updateStuInfo(map);
				map.put("code", 200);
				map.put("fileName", fileName);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", 203);
			}
		} else {
			map.put("code", 204);
			return;
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 
	* @Description:根据省获取市
	* @param map
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月2日 下午3:50:29
	 */
	@RequestMapping("/findCitys.do")
	public void findCitys(@RequestParam Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> cityList = lQCityService.selectByProvId(Integer.parseInt(request
					.getParameter("id")));
			returnMap.put("cityList", cityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:上传身份证图片
	 *@param imgFile
	 *@param request
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月29日下午5:56:09
	 *
	 */
	@RequestMapping(value = "/uploadIdCardImg.do")
	public void uploadIdCardFrontImg(@RequestParam MultipartFile imgFile, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//这句很重要, 保证文件上传的ajax 能正常接收.
		response.setContentType("text/html; charset=UTF-8");

		Map<String, Object> map = new HashMap<String, Object>();
		if (imgFile == null || imgFile.isEmpty()) {
			map.put("code", 201);
			JsonUtil.write(response, JSON.toJSONString(map));
			return;
		}
		if (imgFile.getSize() > 3 * 1024 * 1024) {
			map.put("code", 202);
			JsonUtil.write(response, JSON.toJSONString(map));
			return;
		}

		//判断图片的后缀
		String suffixs = GlobalConstant.PICTURESUFFIX;
		String suff = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
		if (suffixs.indexOf(suff) != -1) {
			try {
				String fileName = FileUtil.uploadIdCardImgFile(request, imgFile);
				map.put("code", 200);
				map.put("fileName", fileName);
			} catch (Exception e) {
				map.put("code", 203);
			}
		} else {
			map.put("code", 204);
			return;
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}

	/**
	 * 
	 *@description:获取课程类别
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月28日下午11:05:01
	 *
	 */
	@RequestMapping("/getCourseType.do")
	public void getCourseType(HttpServletResponse response) throws Exception {
		List<Map<String, Object>> list = null;
		try {
			list = dictService.getDictByType(DictService.DICT_COURSE_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(list));
	}

	/**
	 * 
	 *@description:获取院校类型
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月28日下午11:04:40
	 *
	 */
	@RequestMapping("/getSchoolType.do")
	public void getSchoolType(HttpServletResponse response) throws Exception {
		List<Map<String, Object>> list = null;
		try {
			list = dictService.getDictByType(DictService.DICT_SCHOOL_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(list));
	}

	/**
	 * 
	 *@description:(老师)添加意向学生
	 *@param map
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月29日下午11:38:12
	 *
	 */
	@RequestMapping("/addYxStudent.do")
	public void addYxStudent(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		//		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		//		while(it.hasNext()){
		//			Map.Entry<String, Object> entry = it.next();
		//			System.out.println(entry.getKey() + "：" + entry.getValue()); 
		//		}
		try {
			studentService.addYxStudent(map);
			returnMap.put("code", "200");
		} catch (Exception e) {
			returnMap.put("code", "201");
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:老师添加意向学生，账号(邮箱或手机号)的重复性检测
	 *@param response
	 *@param map
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年11月30日下午8:13:23
	 *
	 */
	@RequestMapping("/validAccount.do")
	public void validAccount(HttpServletResponse response, @RequestParam String newAccount) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newAccount", newAccount.trim());
		map.put("type", "3");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		int count = userService.existedCount(map);
		if (count == 0) {
			returnMap.put("code", "200");
		} else {
			returnMap.put("code", "201");
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 标记为我关注的
	 * by罗玉琳
	 */
	@RequestMapping("/remarkMyFocus.do")
	public void remarkMyFocus(HttpServletResponse response, HttpServletRequest request) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String stuUserIds = request.getParameter("stuUserIds");
		Integer userId = WebUtil.getLoginUser(request).getUserId();
		try {
			studentService.remarkMyFocusBatch(stuUserIds, userId);
			returnMap.put("code", 200);
			returnMap.put("message", "标记为我关注成功");
		} catch (Exception e) {
			returnMap.put("code", 201);
			returnMap.put("message", "标记为我关注失败");
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 取消关注
	 * by罗玉琳
	 */
	@RequestMapping("/cancelFocus.do")
	public void cancelFocus(HttpServletResponse response, HttpServletRequest request) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String stuUserIds = request.getParameter("stuUserIds");
		Integer userId = WebUtil.getLoginUser(request).getUserId();
		try {
			studentService.cancelFocusBatch(stuUserIds, userId);
			returnMap.put("code", 200);
			returnMap.put("message", "取消关注成功");
		} catch (Exception e) {
			returnMap.put("code", 201);
			returnMap.put("message", "取消关注失败");
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:展示审核弹窗
	 *@param stuId
	 *@param mv
	 *@return
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年12月1日下午6:09:24
	 *
	 */
	@RequestMapping("/showCheckStuInfoDialog.do")
	@Functions({ 49 })
	public ModelAndView showCheckStuInfoDialog(@RequestParam Integer stuId, ModelAndView mv) throws Exception {
		try {
			mv = new ModelAndView("/WEB-INF/view/teacher/check_student_info.jsp");
			mv.addObject("stuId", stuId);//选择的学员的id
			/**=============================    前端数据           ============================== */
			//1,学员student信息
			mv.addObject("stuInfo", studentService.selectByPrimaryKey(stuId));

			//2,支付状态列表【未支付、全部支付、特批后缴】
			StuPaidEnum[] hasPaidEnum = new StuPaidEnum[] { StuPaidEnum.NOTPAY, StuPaidEnum.ALLPAY,
					StuPaidEnum.SPECIALPAY };

			mv.addObject("paidTypeList", hasPaidEnum);

			//3,支付方式
			mv.addObject("paidMethodList", dictService.getDictByType(DictService.DICT_PAY_TYPE));

		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}

	/**
	 * 
	 *@description:学生审核
	 *@param response
	 *@param map
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月1日下午6:13:00
	 *
	 */
	@RequestMapping("/submitCheckResult.do")
	public void submitCheckResult(HttpServletResponse response, @RequestParam Map<String, Object> map,
			HttpServletRequest request) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回的数据包
		//验证学员数据完整，是否有课程类别[定学号]
		Student student = studentService.selectByPrimaryKey(Integer.parseInt((String) map.get("stuId")));
		if (student.getCourseType() == null) {
			returnMap.put("code", "100");
			JsonUtil.write(response, JSON.toJSONString(returnMap));
			return;
		} else {
			map.put("courseType", student.getCourseType());
		}
		//设置了课程类别，继续执行
		String isRightName = ((String) map.get("isRightName")).trim();//身份证姓名是否相符【0是  1否】
		String isAvaiable = ((String) map.get("isAvaiable")).trim();//是否缴纳报名费【0未缴纳 1已缴纳 2特批后缴 】

		Integer checkStatus = null;
		String thePaidStatus = Integer.parseInt(isAvaiable) == GlobalConstant.StuPaidEnum.NOTPAY.getValue() ? "A" : //未支付
				Integer.parseInt(isAvaiable) == GlobalConstant.StuPaidEnum.ALLPAY.getValue() ? "B" : //全部支付
						Integer.parseInt(isAvaiable) == GlobalConstant.StuPaidEnum.SPECIALPAY.getValue() ? "C" : "D";//特批延迟：未知
		//根据审核的结果，判定该学员的最终审核状态
		if (isRightName.equals("0")) {

			switch (thePaidStatus) {
			case "A":
				checkStatus = GlobalConstant.auditStatusEnum.NOPAY.getValue();//2未缴费(身份证通过,未缴费)
				break;
			case "B":
				checkStatus = GlobalConstant.auditStatusEnum.PASS.getValue();//1已通过(身份证和缴费都通过.用于正式学员,其他的状态都是意向学员)
				break;
			case "C":
				checkStatus = GlobalConstant.auditStatusEnum.PASS.getValue();//1已通过(身份证和特批后缴都通过.用于正式学员,其他的状态都是意向学员)
				break;
			default:
				System.out.println("**************************submitCheckResult学员审核确定审核状态时出错******************");
				break;
			}
		} else {
			switch (thePaidStatus) {
			case "A":
				checkStatus = GlobalConstant.auditStatusEnum.NOPASS.getValue();//0未通过(身份证不通过,未缴费)
				break;
			case "B":
				checkStatus = GlobalConstant.auditStatusEnum.SUPPLYINFO.getValue();//3补充资料(身份证未通过,已缴费)
				break;
			case "C":
				checkStatus = GlobalConstant.auditStatusEnum.SUPPLYINFO.getValue();//3补充资料(身份证未通过,特批后缴)
				break;
			default:
				System.out.println("************************submitCheckResult学员审核确定审核状态时出错*********************");
				break;
			}
		}

		map.put("checkStatus", checkStatus);
		//为了结合枚举，这里使用枚举匹配，不可以直接与数字对比
		int theCheckStatus = checkStatus == GlobalConstant.auditStatusEnum.NOPASS.getValue() ? 0 : //未通过
				checkStatus == GlobalConstant.auditStatusEnum.PASS.getValue() ? 1 : //通过
						checkStatus == GlobalConstant.auditStatusEnum.NOPAY.getValue() ? 2 : //未缴费
								checkStatus == GlobalConstant.auditStatusEnum.SUPPLYINFO.getValue() ? 3 : 4;//补充资料
		try {
			//根据状态确定跳转的处理方法，四种状态【0,1,2,3】
			switch (theCheckStatus) {
			case 0:
				studentService.checkYxStudentNotPass(map);
				returnMap.put("code", 200);//未通过
				break;
			case 1:
				//获取登陆者id，用于填写费用记录表填写者id
				SessionUser sUser = WebUtil.getLoginUser(request);
				map.put("operatorUserid", sUser.getUserId());
				studentService.checkYxStudentPass(map);
				returnMap.put("code", 201);//通过
				break;
			case 2:
				studentService.checkYxStudentNotPass(map);
				returnMap.put("code", 202);//未缴费
				break;
			case 3:
				studentService.checkYxStudentNotPass(map);
				returnMap.put("code", 203);//补充资料
				break;
			default:
				System.out.println("************************submitCheckResult获取状态出错*****************************");
				break;
			}

		} catch (Exception e) {
			returnMap.put("code", 204);//异常
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:跳转到学员入班界面【重要参数：type--changeClass或者newIntoClass】
	 *@param mv
	 *@return
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年12月5日下午4:50:43
	 *
	 */
	@RequestMapping("/toChangeClassPage.do")
	@Functions({ 55, 76, 50, 51 })
	public ModelAndView toChangeClassPage(ModelAndView mv, @RequestParam Map<String, Object> map) throws Exception {
		try {
			//1,班级列表//改为用ajax获取
			//			mv.addObject("classList", tLqclassService.classForStuIntoClass((String)map.get("type")));//typ标注（分班newIntoClass、合班changeClass、转班mergeClass）

			//2,被选学员列表
			//			TODO 获取学员ids字符串
			//			String ids = (String)map.get("ids");

			mv.addAllObjects(map);
			mv.setViewName("/WEB-INF/view/zsStu/student_into_class.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}

		return mv;
	}

	/**
	 * 
	 *@description: Ajax获取用于分班转班合班的班级
	 *@param type
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月20日下午5:42:44
	 *
	 */
	@RequestMapping("/classForStuIntoClass.do")
	public void classForStuIntoClass(String type, HttpServletResponse response) throws Exception {
		List<Map<String, Object>> classList = tLqclassService.classForStuIntoClass(type);//typ标注（分班newIntoClass、合班changeClass、转班mergeClass）
		JsonUtil.write(response, JSON.toJSONString(classList));
	}

	/**
	 * 
	 *@description:获取班级详情
	 *@param classId
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月5日下午8:34:24
	 *
	 */
	@RequestMapping("/classDetail.do")
	public void classDetail(@RequestParam Integer classId, HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		//1，班级基本信息
		TLqclass lqClass = tLqclassService.classDetail(classId);
		returnMap.put("lqClass", lqClass);

		//TODO 时间转换
		//		CommonUtil.dateStrToDate();

		//2，班级相关师生姓名
		//班主任
		if (lqClass.getChrTeacherId() != null)
			returnMap.put("chrTeacher", userService.getInfoById(lqClass.getChrTeacherId()));
		//招生老师
		if (lqClass.getInvTeacherId() != null)
			returnMap.put("invTeacher", userService.getInfoById(lqClass.getInvTeacherId()));
		//技术老师
		if (lqClass.getComTeacherId() != null)
			returnMap.put("comTeacher", userService.getInfoById(lqClass.getComTeacherId()));
		//CEP老师
		if (lqClass.getCepTeacherId() != null)
			returnMap.put("cepTeacher", userService.getInfoById(lqClass.getCepTeacherId()));
		//班长
		if (lqClass.getMonitorId() != null)
			returnMap.put("monitor", userService.getInfoById(lqClass.getMonitorId()));

		//3,培训方向
		Map<String, Object> map = new HashMap<>();
		map.put("value", lqClass.getCourseType());
		map.put("type", DictService.DICT_COURSE_TYPE);
		returnMap.put("courseType", dictService.getLabelByValAndType(map));

		//4,班级类型
		map.clear();
		map.put("classTypePre", lqClass.getTypePre());
		map.put("dictTypePre", DictService.DICT_CLASSTYPE_PRE);
		map.put("classTypeReal", lqClass.getTypeReal());
		map.put("dictTypeReal", DictService.DICT_CLASSTYPE_REAR);
		returnMap.put("classType", dictService.getClassType(map));
		//TODO 测试班级类型是否正确显示

		//5,班级人数
		List<Integer> listStatus = new ArrayList<>();//用来计算本班班级人数的学员状态
		listStatus.add(GlobalConstant.StuStatusEnum.NOSTARTCLASS.getValue());
		listStatus.add(GlobalConstant.StuStatusEnum.BESTUDY.getValue());
		listStatus.add(GlobalConstant.StuStatusEnum.EndStudy.getValue());
		listStatus.add(GlobalConstant.StuStatusEnum.FindJobing.getValue());
		listStatus.add(GlobalConstant.StuStatusEnum.WORKED.getValue());
		map.clear();
		map.put("classId", classId);
		map.put("list", listStatus);
		int studentCount = tLqclassService.studentCountCanMerge(map);
		returnMap.put("studentCount", studentCount);

		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:打开新增班级界面（弹窗）
	 *@param mv
	 *@return
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年12月6日下午3:44:26
	 *
	 */
	@RequestMapping("/toAddClass.do")
	public ModelAndView toAddClass(ModelAndView mv) throws Exception {
		mv.setViewName("/WEB-INF/view/zsStu/class_add_dialog.jsp");
		try {
			//1，班级一级类型
			mv.addObject("classTypePreList", dictService.getDictByType(DictService.DICT_CLASSTYPE_PRE));

			//2,班级课程类别
			mv.addObject("courseTypeList", dictService.getDictByType(DictService.DICT_COURSE_TYPE));

			//3,按类型选择的教师
			//TODO 暂时用所有老师的搜索下拉控件

			//4,选班长的学生列表
			//TODO 无学生可选

			//5,创建班级目的
			mv.addObject("classGoalList", dictService.getDictByType(DictService.DICT_CLASS_GOAL));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mv;

	}

	/**
	 * 
	 *@description:根据班级一级类型获取二级类型列表
	 *@param typePre
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月5日下午10:24:09
	 *
	 */
	@RequestMapping("/getClassTypeReal.do")
	public void getClassTypeReal(@RequestParam String typePre, HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			//1,获取父级id
			Map<String, Object> map = new HashMap<>();
			map.put("type", DictService.DICT_CLASSTYPE_PRE);
			map.put("value", typePre);
			String parentId = dictService.getIdByValAndType(map);
			//2，寻找子级列表
			List<Map<String, Object>> list = dictService.getDictByParentAndType(DictService.DICT_CLASSTYPE_REAR,
					parentId);
			returnMap.put("list", list);
			returnMap.put("code", 200);
		} catch (Exception e) {
			returnMap.put("code", 202);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:执行添加班级操作
	 *@param tLqclass
	 *@param map
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月5日下午11:07:24
	 *
	 */
	@RequestMapping("/addClass.do")
	public void addClass(TLqclass tLqclass, @RequestParam Map<String, Object> map, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			//TODO 1,处理时间的转换、各个不可输入字段的完善
			tLqclass.setExpectStarttime(CommonUtil.dateStrToDate((String) map.get("startTime1")));
			tLqclass.setExpectGraduateTime(CommonUtil.dateStrToDate((String) map.get("graduateTime1")));
			tLqclass.setExpectSchoolEndtime((CommonUtil.dateStrToDate((String) map.get("schoolTime1"))));

			//2,处理附带信息
			SessionUser sUser = WebUtil.getLoginUser(request);//获取登陆者（操作者）id
			tLqclass.setStatus(GlobalConstant.ClassStatusEnum.NOCLASSES.getValue());//状态为未开课
			tLqclass.setCreateUserId(sUser.getUserId());//设置创建者id
			tLqclass.setCreateTime(new Date());//创建时间
			tLqclass.setUpdateTime(new Date());//更新时间
			if (map.get("cepTeacherId") != null)
				tLqclass.setCepTeacherId(Integer.parseInt(((String) map.get("cepTeacherId"))));//cep老师
			if (map.get("comTeacherId") != null)
				tLqclass.setComTeacherId(Integer.parseInt(((String) map.get("comTeacherId"))));//技术老师
			if (map.get("chrTeacherId") != null)
				tLqclass.setChrTeacherId(Integer.parseInt(((String) map.get("chrTeacherId"))));//职业经纪人

			tLqclassService.addClass(tLqclass, sUser.getUserId());
			returnMap.put("newClassId", tLqclass.getLqClassId());
			returnMap.put("className", tLqclass.getClassName());
			returnMap.put("code", 200);

		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("code", 202);
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:统计课程类别不同的学员个数，跳到确认分班界面
	 *@param map
	 *@param mv
	 *@return
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年12月8日下午2:39:50
	 *
	 */
	@RequestMapping("/confirmDialog.do")
	public ModelAndView confirmDialog(@RequestParam Map<String, Object> map, ModelAndView mv) throws Exception {

		Integer newClassId = Integer.parseInt((String) map.get("newClassId"));
		int classCourseType = tLqclassService.classDetail(newClassId).getCourseType();
		//得到新班级的课程类别
		Map<String, Object> parmMap = new HashMap<>();
		parmMap.put("type", DictService.DICT_COURSE_TYPE);
		parmMap.put("value", classCourseType);
		String courseTypeLabel = dictService.getLabelByValAndType(parmMap);

		//统计课程类别不同于所选班级的学员个数
		String ids = (String) map.get("ids");
		String idList[] = ids.split(",");
		int count = 0;
		for (String string : idList) {
			String studentCourseType = studentService.selectByPrimaryKey(Integer.parseInt(string)).getCourseType();
			if (studentCourseType != null && !studentCourseType.equals(classCourseType + ""))
				count++;
		}
		map.put("count", count);//课程与班级不同的学员个数
		map.put("courseTypeLabel", courseTypeLabel);//新班级课程label
		mv.addAllObjects(map);
		mv.setViewName("/WEB-INF/view/zsStu/intoClass_confirm_dialog.jsp");
		return mv;
	}

	/**
	 * 
	 *@description:学员批量加入班级
	 *@param map
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月7日下午1:40:27
	 *
	 */
	@RequestMapping("/studentIntoClass.do")
	public void studentIntoClass(@RequestParam Map<String, Object> map, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			int count[] = studentService.studentIntoClass(map, response, request);//【第一个数是更改成功的数量，第二个是换班的列表里原来就属于该班级的人数】
			if (count != null) {
				returnMap.put("count", count);
				returnMap.put("code", 200);
			} else {
				returnMap.put("code", 201);//ids是null
			}
		} catch (Exception e) {
			returnMap.put("code", 202);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:班级名称存在性检测【除本班级以外，规避：班级编辑时，班级名称重复误检测】
	 *@param map[className,classId]
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月5日下午11:59:16
	 *
	 */
	@RequestMapping("/classNameUniqueCheck.do")
	public void classNameUniqueCheck(@RequestParam Map<String, Object> map, HttpServletResponse response)
			throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			int count = tLqclassService.classNameUniqueCheck(map);
			if (count > 0) {
				returnMap.put("code", 200);//已存在，该名称不可用
			} else {
				returnMap.put("code", 201);//不存在,该名称可用
			}
		} catch (Exception e) {
			returnMap.put("code", 202);
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:新建班级后ajax刷新班级列表
	 *@param response
	 *@param type【新分班newIntoClass、转班changeClass】
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月7日下午4:25:33
	 *
	 */
	@RequestMapping("/refreshClass.do")
	public void refreshClass(HttpServletResponse response, @RequestParam String type) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			returnMap.put("classList", tLqclassService.classForStuIntoClass(type));
			returnMap.put("code", 200);

		} catch (Exception e) {
			returnMap.put("code", 202);
		}

		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:学员入班页面，注入表格数据
	 *@param ids[前端控制不空]
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月7日下午6:25:58
	 *
	 */
	@RequestMapping("/getStudentIntoClassList.do")
	public void getStudentIntoClassList(@RequestParam String ids, HttpServletResponse response) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		List<Map<String, Object>> datalist = studentService.getStudentByIds(ids);
		returnMap.put("datalist", datalist);
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

}

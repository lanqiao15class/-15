package com.lanqiao.controller.student;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.MustLoginAndStudent;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.auditStatusEnum;
import com.lanqiao.model.LQUniversities;
import com.lanqiao.model.Student;
import com.lanqiao.model.User;
import com.lanqiao.service.DictService;
import com.lanqiao.service.LQCityService;
import com.lanqiao.service.LQUniversitiesService;
import com.lanqiao.service.StudentService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.JsonUtil;

/**
 * 
* 项目名称:lqzp2
* 类名称： BasicInformationController
* 类描述:学员基本信息
* 创建人: ZhouZhiHua
* 创建时间:2016年12月15日 上午10:52:54 
* 修改人： 
* 修改时间：2016年12月15日 上午10:52:54
* 修改备注:
 */
@Controller
@RequestMapping("/student")
public class BasicInformationController {
	@Resource
	LQCityService lQCityService;

	@Resource
	UserService userService;

	@Resource
	StudentService studentService;

	@Resource
	DictService dictService;

	@Resource
	LQUniversitiesService lqUniversitiesService;

	/**
	 * 
	* @Description:去学员基本信息页面
	* @param request
	* @param mv
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年12月15日 上午10:58:40
	 */
	@MustLoginAndStudent
	@RequestMapping("/goBasic.do")
	public ModelAndView goBasic(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("/WEB-INF/view/student/student_basic.jsp");
		try {
			//1.用户基本信息
			User user = userService.getUserInfoByUserId(WebUtil.getLoginUser(request).getUserId());
			mv.addObject("user", user);
			//2.性别
			mv.addObject("sex", dictService.getDictByType(DictService.DICT_SEX));
			//3.院校类别
			mv.addObject("schoolType", dictService.getDictByType(DictService.DICT_SCHOOL_TYPE));
			//4.学员信息
			Student stuInfo = studentService.getStuInfoByUserId(WebUtil.getLoginUser(request).getUserId());
			mv.addObject("stuInfo", stuInfo);
			//5.课程类别
			mv.addObject("courseType", dictService.getDictByType(DictService.DICT_COURSE_TYPE));
			//6.根据院校code获取院校名称
			if (stuInfo.getUnivCode() != null && !"".equals(stuInfo.getUnivCode())) {
				LQUniversities lquni = lqUniversitiesService.getLqUniverInfo((long) stuInfo.getUnivCode());
				mv.addObject("univName", lquni.getUnivName());
			} else {
				mv.addObject("univName", null);
			}
			//7.就业意向地址
			mv.addObject("jobCityCode", dictService.getDictByType(DictService.DICT_JOB_JOBCITYCODE));
			//8.用于图片上传保存时根据用户id保存
			mv.addObject("loginUserId", WebUtil.getLoginUser(request).getUserId());
			//9.获取省市
			if (stuInfo.getSchoolProvCode() != null && !"".equals(stuInfo.getSchoolProvCode())
					&& stuInfo.getSchoolCityCode() != null && !"".equals(stuInfo.getSchoolCityCode())) {
				mv.addObject("schoolProvName", lQCityService.getById(stuInfo.getSchoolProvCode()));
				mv.addObject("schoolCityName", lQCityService.getById(stuInfo.getSchoolCityCode()));
			} else {
				mv.addObject("schoolProvName", null);
				mv.addObject("schoolCityName", null);
			}
			//10.学员审核通过状态
			mv.addObject("PASS", auditStatusEnum.PASS.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}

	/**
	 * 
	* @Description:编辑后保存学生基本信息
	* @param map
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月2日 下午3:35:46
	 */
	@MustLoginAndStudent
	@RequestMapping("/editStuBasic.do")
	public void editStuBasic(@RequestParam Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			map.put("userId", WebUtil.getLoginUser(request).getUserId());
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
	* @Description:去学员联系方式
	* @param request
	* @param mv
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年12月15日 下午5:09:58
	 */
	@MustLoginAndStudent
	@RequestMapping("/golink.do")
	public ModelAndView golink(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("/WEB-INF/view/student/link.jsp");
		try {
			//1.用户基本信息
			User user = userService.getUserInfoByUserId(WebUtil.getLoginUser(request).getUserId());
			mv.addObject("user", user);
			//2.学员信息
			Student stuInfo = studentService.getStuInfoByUserId(WebUtil.getLoginUser(request).getUserId());
			mv.addObject("stuInfo", stuInfo);
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}

	/**
	 * 
	 *@description:学员界面顶端提示
	 *@param mv
	 *@param request
	 *@return
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年12月19日下午12:01:15
	 *
	 */
	@MustLoginAndStudent
	@RequestMapping("/studentTips.do")
	public ModelAndView studentTips(ModelAndView mv, HttpServletRequest request) {
		SessionUser sUser = WebUtil.getLoginUser(request);
		Student student = studentService.selectByPrimaryKey(sUser.getUserId());
		int stuAuditStatus = student.getAuditStatus();
		if (stuAuditStatus == GlobalConstant.auditStatusEnum.NOREGIST.getValue()) {//未报名
			mv.addObject("showTips", "noSignUp");
		} else if (stuAuditStatus == GlobalConstant.auditStatusEnum.NOAUDIT.getValue()) {//报名待审核
			mv.addObject("showTips", "waitForCheck");
		} else if (stuAuditStatus == GlobalConstant.auditStatusEnum.SUPPLYINFO.getValue()) {//身份证信息不符，已缴费
			mv.addObject("showTips", "notMatchInfo");
		} else if (stuAuditStatus == GlobalConstant.auditStatusEnum.NOPAY.getValue()) {//身份证通过，未缴费
			mv.addObject("showTips", "noPaidMoney");
		} else if (stuAuditStatus == GlobalConstant.auditStatusEnum.NOPASS.getValue()) {//身份证信息不符，报名费未缴纳
			mv.addObject("showTips", "bothRefused");
		}

		mv.setViewName("/WEB-INF/view/include/studentTip.jsp");

		return mv;
	}

}

package com.lanqiao.controller.student;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.MustLoginAndStudent;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.constant.GlobalConstant.auditStatusEnum;
import com.lanqiao.model.LQUniversities;
import com.lanqiao.model.Student;
import com.lanqiao.model.User;
import com.lanqiao.service.DictService;
import com.lanqiao.service.LQCityService;
import com.lanqiao.service.LQUniversitiesService;
import com.lanqiao.service.StudentService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.FileUtil;
import com.lanqiao.util.JsonUtil;

/**
 * 
* 项目名称:lqzp2
* 类名称： BasicInformationController
* 类描述:学员报名相关
* 创建人: ZhouZhiHua
* 创建时间:2016年12月15日 上午10:52:54 
* 修改人： 
* 修改时间：2016年12月15日 上午10:52:54
* 修改备注:
 */
@MustLoginAndStudent
@Controller
@RequestMapping("/signup")
public class ToSignUpController {
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
	* @Description:去报名页面
	* @param mv
	* @param request
	* @param response
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年12月16日 下午2:50:33
	 */
	@MustLoginAndStudent
	@RequestMapping("/goSignUp.do")
	public ModelAndView goSignUp(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
		mv.setViewName("/WEB-INF/view/student/student_signup_diolog.jsp");
		try {
			//1.用户基本信息
			User user = userService.getUserInfoByUserId(WebUtil.getLoginUser(request).getUserId());
			mv.addObject("user", user);
			//2.院校类别
			mv.addObject("schoolType", dictService.getDictByType(DictService.DICT_SCHOOL_TYPE));
			//3.学员信息
			Student stuInfo = studentService.getStuInfoByUserId(WebUtil.getLoginUser(request).getUserId());
			mv.addObject("stuInfo", stuInfo);
			//4.就业意向地址
			mv.addObject("jobCityCode", dictService.getDictByType(DictService.DICT_JOB_JOBCITYCODE));
			//5.用于图片上传保存时根据用户id保存
			mv.addObject("loginUserId", WebUtil.getLoginUser(request).getUserId());
			//6.根据院校code获取院校名称
			if (stuInfo.getUnivCode() != null && !"".equals(stuInfo.getUnivCode())) {
				LQUniversities lquni = lqUniversitiesService.getLqUniverInfo((long) stuInfo.getUnivCode());
				mv.addObject("univName", lquni.getUnivName());
			} else {
				mv.addObject("univName", null);
			}
			//7.课程类别
			mv.addObject("courseType", dictService.getDictByType(DictService.DICT_COURSE_TYPE));
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}

	/**
	 * 
	* @Description:学员-补充学员报名信息
	* @param req
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月16日 下午5:12:19
	 */
	@MustLoginAndStudent
	@RequestMapping("/editSignUpInfo.do")
	public void editSignUpInfo(HttpServletRequest req, HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> resultInfo = new HashMap<String, Object>();

		//查询该用户是否为报名学生

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idCard", req.getParameter("idCard"));
		map.put("userId", WebUtil.getLoginUser(request).getUserId());
		map.put("auditStatus", GlobalConstant.auditStatusEnum.PASS.getValue());
		Integer count = userService.getidCard(map);
		if (count > 0) {

			resultInfo.put("code", "105");
		} else {
			try {
				//1.用户对象
				User user = new User();
				user.setUserId(WebUtil.getLoginUser(request).getUserId());
				user.setRealName(req.getParameter("realName"));//真实姓名
				//			user.setSex(Integer.parseInt(req.getParameter("sex")));
				//			if(req.getParameter("birth")!=null&&!"".equals(req.getParameter("birth"))){
				//				user.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("birth")));//出生年月
				//			}
				user.setSex(req.getParameter("sex"));
				//			user.setNativePlace(req.getParameter("nativePlace"));//籍贯
				user.setNation(req.getParameter("nation"));//民族
				user.setIdCard(req.getParameter("idCard"));//身份证
				//根据身份证号获取生日
				String birth = req.getParameter("idCard").substring(6, 14);
				birth = birth.substring(0, 4) + "-" + birth.substring(4, 6) + "-" + birth.substring(6);
				user.setBirth(CommonUtil.dateStrToDate(birth));
				user.setTel(req.getParameter("tel"));//联系方式
				user.setQq(req.getParameter("qq"));//qq号
				user.setEmail(req.getParameter("email"));//邮箱
				//			user.setType("0");//登陆用户类型(0-学生 1-老师)
				user.setCreateTime(new Date());//创建时间
				user.setUpdateTime(new Date());//更新时间
				//2.学生对象
				Student student = new Student();
				student.setUserId(WebUtil.getLoginUser(request).getUserId());//学生id
				student.setUnivCode(Integer.parseInt(req.getParameter("univCode")));//所在院校id
				student.setSchoolProvCode(Integer.parseInt(req.getParameter("provCodeSignUp")));//院校所在省
				student.setSchoolCityCode(Integer.parseInt(req.getParameter("cityCodeSignUp")));//院校所在市
				student.setSchoolTypeCode(Integer.parseInt(req.getParameter("schoolTypeCode")));//院校类别
				student.setSchoolDuty(req.getParameter("schoolDuty"));//在校担任职务
				student.setSchoolDormitory(req.getParameter("schoolDormitory"));//宿舍号
				//			//根据院校名称查询院校id
				//			Map<String,Object> map=new HashMap<String,Object>();
				//			map.put("univName", req.getParameter("schoolName"));
				//			student.setUnivCode(universitiesService.selectUnivCodeByUnivName(map));//院校名称
				student.setSchoolSubname(req.getParameter("schoolSubname"));//所在学院
				student.setGrade(req.getParameter("grade"));//年级
				student.setMajor(req.getParameter("major"));//所在专业
				student.setCourseType(req.getParameter("courseTypeCode"));//课程类型
				String[] jobCitys = req.getParameterValues("jobCityCode");
				String jobTemp = "";
				for (int i = 0; i < jobCitys.length; i++) {
					jobTemp = jobTemp + jobCitys[i] + ",";
				}
				student.setJobCityCode(jobTemp);//就业意向地点
				student.setParentInfo(req.getParameter("parentInfo"));//家庭联系人
				student.setParentTel(req.getParameter("parentTel"));//家庭联系电话
				student.setStatus(StuStatusEnum.AUDIT.getValue());//学生状态:报名待审核101
				student.setAuditStatus(auditStatusEnum.NOAUDIT.getValue());//报名待审核5
				student.setAddress(req.getParameter("address"));
				student.setCreateTime(new Date());
				student.setUpdateTime(new Date());
				student.setIdcardFrontImg(req.getParameter("frontImgName2"));//身份证正面
				student.setIdcardBackImg(req.getParameter("backImgName2"));//身份证反面
				//3.修改
				studentService.editSignUpInfo(user, student);//修改
				//6.
				//			Map<String, Object> userMap = (Map<String, Object>)req.getSession().getAttribute("userMap");
				//			userMap.put("auditStatus", 2);
				//			req.getSession().setAttribute("userMap", userMap);
				//4.更新session
				userService.BindDirectLogin(WebUtil.getLoginUser(request).getUserId(), request);
				resultInfo.put("message", "报名成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		JsonUtil.write(response, JSON.toJSONString(resultInfo));
	}

	/**
	 * 
	* @Description:报名时去人才输送服务协议页面
	* @param request
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年12月16日 下午6:51:57
	 */
	@RequestMapping("/goAgreement.do")
	public ModelAndView goAgreement(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/WEB-INF/view/student/agreement.jsp");
		return mv;
	}

	/**
	 * 
	* @Description:上传图片
	* @param imgFile
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月3日 上午11:01:00
	 */
	@RequestMapping(value = "/signupUploadImg.do")
	public void signupUploadImg(@RequestParam MultipartFile imgFile, Integer userId, String type,
			HttpServletRequest request, HttpServletResponse response) {
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
				//studentService.updateStuInfo(map);
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

}

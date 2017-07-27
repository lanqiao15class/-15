package com.lanqiao.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.Functions;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.ClassStatusEnum;
import com.lanqiao.model.TLqclass;
import com.lanqiao.model.User;
import com.lanqiao.service.ClassStatusService;
import com.lanqiao.service.ClassVisitLogService;
import com.lanqiao.service.DictService;
import com.lanqiao.service.TLqclassService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.JsonUtil;

@Controller
@RequestMapping("/lqClass")
public class LqClassController {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(LqClassController.class);

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

	/**
	 * 
	* @Description:根据班级id跳转到班级详情页面
	* @param mv
	* @param request
	* @param response
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年12月9日 下午4:04:47
	 */
	@RequestMapping("/goClassInfo.do")
	public ModelAndView goClassInfo(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
		mv.setViewName("/WEB-INF/view/lqclass/diolog_class_manage.jsp");
		try {
			int classId = Integer.parseInt(request.getParameter("classId"));//班级id
			//1.获取班级基本信息
			Map<String, Object> lqclassInfo = tLqclassService.getLqClassInfoList(classId);

			mv.addObject("lqclassInfo", lqclassInfo);
			//2.班级一级类型
			mv.addObject("classTypePre", dictService.getDictByType(DictService.DICT_CLASSTYPE_PRE));
			//3.班级二级类型
			Map<String, Object> map = new HashMap<>();
			map.put("type", DictService.DICT_CLASSTYPE_PRE);
			map.put("value", lqclassInfo.get("type_pre"));
			String parentId = dictService.getIdByValAndType(map);
			//寻找子级列表
			List<Map<String, Object>> rearlist = dictService.getDictByParentAndType(DictService.DICT_CLASSTYPE_REAR,
					parentId);
			mv.addObject("classTypeRear", rearlist);
			//4.课程类别
			mv.addObject("courseType", dictService.getSysCourse());
			//5.班级状态流水列表
			mv.addObject("classStatusList", classStatusService.getClassStatusLogByClassId(classId));
			List<Map<String, Object>> classStatusList = classStatusService.getClassStatusLogByClassId(classId);
			int classStatusListTemp = 0;
			for (int i = 0; i < classStatusList.size(); i++) {
				if (classStatusList.get(i).get("end_time") != null
						&& !"".equals(classStatusList.get(i).get("end_time"))) {
					classStatusListTemp = classStatusListTemp + 1;
				}
			}
			mv.addObject("classStatusSize", classStatusListTemp);
			//6.班级状态
			mv.addObject("classStatus", WebUtil.getClassStatusList());

			mv.addObject("NOCLASSES", ClassStatusEnum.NOCLASSES.getValue());
			mv.addObject("INTHELECTURE", ClassStatusEnum.INTHELECTURE.getValue());
			mv.addObject("BEFORETHETRAININGSESSION", ClassStatusEnum.BEFORETHETRAININGSESSION.getValue());
			mv.addObject("INTHETRAINING", ClassStatusEnum.INTHETRAINING.getValue());
			mv.addObject("THEGRADUATION", ClassStatusEnum.THEGRADUATION.getValue());
			mv.addObject("INTHEEMPLOYMENT", ClassStatusEnum.INTHEEMPLOYMENT.getValue());
			mv.addObject("OFTHEEMPLOYMENT", ClassStatusEnum.OFTHEEMPLOYMENT.getValue());
			mv.addObject("SHUTDOWN", ClassStatusEnum.SHUTDOWN.getValue());
			//7.班级跟踪记录
			mv.addObject("classVisitList", classVisitLogService.getClassVisitLogByClassId(classId));
			mv.addObject("classVisitSize", classVisitLogService.getClassVisitLogByClassId(classId).size());
			//8.根据技术老师id获取技术老师名称
			if (lqclassInfo.get("com_teacher_id") != null && !"".equals(lqclassInfo.get("com_teacher_id").toString())) {
				User user = userService.getUserInfoByUserId(Integer.parseInt(lqclassInfo.get("com_teacher_id")
						.toString()));
				mv.addObject("comRealName", user.getRealName());
			} else {
				mv.addObject("comRealName", null);
			}
			//9.根据cep老师id获取cep老师的名称
			if (lqclassInfo.get("cep_teacher_id") != null && !"".equals(lqclassInfo.get("cep_teacher_id").toString())) {
				User user = userService.getUserInfoByUserId(Integer.parseInt(lqclassInfo.get("cep_teacher_id")
						.toString()));
				mv.addObject("cepRealName", user.getRealName());
			} else {
				mv.addObject("cepRealName", null);
			}
			//10.根据职业经济人id获取职业经济人名称
			if (lqclassInfo.get("chr_teacher_id") != null && !"".equals(lqclassInfo.get("chr_teacher_id").toString())) {
				User user = userService.getUserInfoByUserId(Integer.parseInt(lqclassInfo.get("chr_teacher_id")
						.toString()));
				if (user != null) {
					mv.addObject("broRealName", user.getRealName());
				} else {
					mv.addObject("broRealName", null);
				}
			} else {
				mv.addObject("broRealName", null);
			}
			//11.根据班级id获取班级学员列表
			List<Map<String, Object>> lqclassStuList = tLqclassService.getLqClassStuListByClassId(classId);
			mv.addObject("lqclassStuList", lqclassStuList);
			//12.获取班级状态
			mv.addObject("classStatus", WebUtil.getClassStatusList());
			//13.创建班级目的
			mv.addObject("classGoal", dictService.getDictByType(DictService.DICT_CLASS_GOAL));
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}

	/**
	 * 
	* @Description:编辑后保存班级信息
	* @param map
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月10日 下午3:11:01
	 */

	@Functions({ 80 })
	@RequestMapping("/editLqClassInfo.do")
	public void editLqClassInfo(@RequestParam Map<String, Object> map, HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			tLqclassService.editLqClassInfo(map);
			returnMap.put("code", "200");
		} catch (Exception e) {
			returnMap.put("code", "201");
			e.printStackTrace();
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 * @param mv
	 * @param opType
	 * @return 去班级管理列表页面
	 * 罗玉琳 2016-12-09
	 * @throws Exception 
	 */
	@RequestMapping("/{opType}/goClassManageList.do")
	@Functions({ 63, 64, 65 })
	public ModelAndView goClassManageList(ModelAndView mv, @PathVariable(value = "opType") String opType) {
		List<Map<String, Object>> classStatusList = WebUtil.getClassStatusList();
		List<Map<String, Object>> courseTypeList = new ArrayList<Map<String, Object>>();
		try {
			courseTypeList = dictService.getSysCourse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("classStatusList", classStatusList);
		mv.addObject("courseTypeList", courseTypeList);
		mv.addObject("optype", opType); // jsp 中根据这个数字, 来判断是显示"我管理的"/"我关注的" /  "全部" 标签. 
		mv.setViewName("/WEB-INF/view/lqclass/classManageList.jsp");
		return mv;
	}

	@RequestMapping("/getClassManageList.do")
	@Functions({ 63, 64, 65 })
	public void getClassManageList(HttpServletRequest request, HttpServletResponse response, @RequestParam String opType) {
		// 打印调试信息. 
		WebUtil.printParameters(request);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		/**====================设置查询参数开始=====================**/
		try {
			if (request.getParameter("classStatus") != null && !"".equals(request.getParameter("classStatus"))) {
				paramMap.put("classStatus", Integer.parseInt(request.getParameter("classStatus")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("courseType") != null && !"".equals(request.getParameter("courseType"))) {
				paramMap.put("courseType", Integer.parseInt(request.getParameter("courseType")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("js_teacher_userid") != null
					&& !"".equals(request.getParameter("js_teacher_userid"))) {
				paramMap.put("js_teacher_userid", Integer.parseInt(request.getParameter("js_teacher_userid")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			if (request.getParameter("broker_teacher_userid") != null
					&& !"".equals(request.getParameter("broker_teacher_userid"))) {
				paramMap.put("broker_teacher_userid", Integer.parseInt(request.getParameter("broker_teacher_userid")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if (request.getParameter("cep_teacher_userid") != null
					&& !"".equals(request.getParameter("cep_teacher_userid"))) {
				paramMap.put("cep_teacher_userid", Integer.parseInt(request.getParameter("cep_teacher_userid")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (request.getParameter("select_class_name") != null && !"".equals(request.getParameter("select_class_name"))) {
			paramMap.put("select_class_name", request.getParameter("select_class_name"));
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
		List<Map<String, Object>> lqClassList = new ArrayList<Map<String, Object>>();
		if (opType.equals("0")) {//我关注的操作
									//获取我的关注的班级列表
			lqClassList = tLqclassService.selectMyFocusClassList(paramMap);
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = tLqclassService.getMyFocusClassCount(paramMap);
				hsret.put("recordcount", ncount);//我关注的班级总记录数
			}
		} else if (opType.equals("1")) {
			//.获取全部班级列表
			lqClassList = tLqclassService.selectAllClassList(paramMap);
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = tLqclassService.getAllClassCount(paramMap);
				hsret.put("recordcount", ncount);//全部班级记录数
			}
		} else {
			//.获取我管理的正式学员列表
			lqClassList = tLqclassService.selectMyManageClassList(paramMap);
			//1把记录数放session
			if (request.getParameter("currpage") != null && !"".equals(request.getParameter("currpage"))
					&& request.getParameter("currpage").equals("1")) {
				int ncount = tLqclassService.getMyManageClassCount(paramMap);
				hsret.put("recordcount", ncount);//我管理的班级记录数
			}
		}
		/**=================统计当前年份新开班数============**/
		int newClassCount = tLqclassService.getNewClassCount();//当前年份的新开班数
		hsret.put("newClassCount", newClassCount);
		/**=================统计当前年份新开班数============**/

		hsret.put("datalist", lqClassList);
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		String formateDate = "";
		Map<String, Object> param = new HashMap<String, Object>();
		int i = 0;//计数
		for (Map<String, Object> map : lqClassList) {
			paramMap.put("lqClassId", map.get("lqClassId"));
			map.put("currentCount", tLqclassService.getClassCurrentCount(paramMap));//获取某班级当前人数
			formateDate = formate.format(map.get("updateTime"));
			map.put("updateTime", formateDate);
			int temp = i + 1;
			map.put("option", "");
			map.put("nstatus", 1);//设置是否显示表格里的图标(不为1，亦表头不显示任何图标)
			map.put("classStatus", WebUtil.getClassStatusByValue(Integer.parseInt(map.get("status").toString())));//获取班级状态的label值
			//设置将课程类别value转换成文字的参数
			param.put("type", "lq_courseType");
			param.put("value", map.get("courseType"));
			map.put("courseType", map.get("courseType"));//courseType文字显示、
			//查询已上课时
			int sum = tLqclassService.selectcourse_arrangementbyclassId((Integer) map.get("lqClassId"));
			map.put("sum", sum);
			//设置标志
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

	//	public ModelAndView goClassesMerge(ModelAndView mv,@RequestParam Map<String,Object> map){
	//		
	//	}

	/**
	 * 标记为我关注的
	 * by罗玉琳
	 */
	@RequestMapping("/remarkMyFocus.do")
	public void remarkMyFocus(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String lqClassIds = request.getParameter("lqClassIds");
		Integer userId = WebUtil.getLoginUser(request).getUserId();
		try {
			tLqclassService.remarkMyFocusBatch(lqClassIds, userId);
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
	public void cancelFocus(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String lqClassIds = request.getParameter("lqClassIds");
		Integer userId = WebUtil.getLoginUser(request).getUserId();
		try {
			tLqclassService.cancelFocusBatch(lqClassIds, userId);
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
	 *@description:跳转到合并班级界面
	 *@param mv
	 *@param map
	 *@return
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年12月10日上午9:56:20
	 *
	 */
	@RequestMapping("/goMergerClass.do")
	@Functions({ 67 })
	public ModelAndView goMergerClass(ModelAndView mv, @RequestParam Map<String, Object> map) {
		try {
			//1,班级列表(用ajax获取，页面已处理)
			//			mv.addObject("classList", tLqclassService.classForStuIntoClass("mergeClass"));//参数标注（分班newIntoClass、合班changeClass、转班mergeClass）

			//2,被选班级列表
			//			String ids = (String)map.get("ids");

			mv.addAllObjects(map);
			mv.setViewName("/WEB-INF/view/lqclass/classes_merge.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}

	/**
	 * 
	 *@description:合班页面班级列表信息
	 *@param ids
	 *@param response
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月10日下午5:39:10
	 *
	 */
	@RequestMapping("/getClassListData.do")
	@Functions({ 67 })
	public void getClassListData(@RequestParam String ids, HttpServletResponse response) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		List<Integer> listStatus = new ArrayList<>();//可以合班的学员状态
		listStatus.add(GlobalConstant.StuStatusEnum.NOSTARTCLASS.getValue());
		listStatus.add(GlobalConstant.StuStatusEnum.BESTUDY.getValue());
		listStatus.add(GlobalConstant.StuStatusEnum.EndStudy.getValue());
		listStatus.add(GlobalConstant.StuStatusEnum.FindJobing.getValue());
		int studentCountAll = 0;//被合并的学员总数
		int classCount = 0;//被合并的班级个数

		if (ids != null && !ids.trim().equals("")) {

			//1，获取班级信息列表
			String idsArray[] = ids.split(",");
			classCount = idsArray.length;//被合并班级总数
			List<Integer> listIds = new ArrayList<>();//被选的合班的班级ids
			for (String id : idsArray) {
				listIds.add(Integer.parseInt(id));
			}
			List<Map<String, Object>> listSource = tLqclassService.getMergeClassData(listIds);
			returnMap.put("list", listSource);
			//2，处理表格显示（序号、状态、班级人数）
			int indexno = 1;
			for (Map<String, Object> map : listSource) {
				//获取可转班学员人数
				int classId = (Integer) map.get("classId");//要查询的班级id
				Map<String, Object> seachMap = new HashMap<>();
				seachMap.put("list", listStatus);//可合班的学员状态列表
				seachMap.put("classId", classId);//要合班的班级id
				int studentCount = tLqclassService.studentCountCanMerge(seachMap);
				map.put("studentCount", studentCount);
				//设置循环的indexno
				map.put("indexno", indexno++);

				//班级状态码转文字
				map.put("status", WebUtil.getClassStatusByValue((Integer) map.get("status")));

				//计算被合并学员总人数
				studentCountAll += studentCount;
			}
			returnMap.put("classCount", classCount);//被合并班级总数
			returnMap.put("studentCountAll", studentCountAll);//被合并学员总数
			returnMap.put("code", 200);
		} else {
			returnMap.put("code", 202);
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:确认合班弹窗
	 *@param map
	 *@param mv
	 *@return
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年12月10日下午5:38:56
	 *
	 */
	@RequestMapping("/confirmDialog.do")
	public ModelAndView confirmDialog(@RequestParam Map<String, Object> map, ModelAndView mv) {
		//获取新班级信息
		Integer newClassId = Integer.parseInt((String) map.get("newClassId"));
		TLqclass tLqclass = tLqclassService.classDetail(newClassId);
		int classCourseType = tLqclass.getCourseType();
		String className = tLqclass.getClassName();

		//统计课程类别不同于所选班级的班级个数
		int count = 0;
		Integer mergedCourseType;
		String ids = (String) map.get("ids");
		String idList[] = ids.split(",");
		List<Map<String, Object>> list = new ArrayList<>();//被合并的班级列表 
		for (String string : idList) {
			//被合并的班级列表 
			TLqclass tLqclassOld = tLqclassService.classDetail(Integer.parseInt(string));
			Map<String, Object> mapOld = new HashMap<>();
			mapOld.put("classId", tLqclassOld.getLqClassId());
			mapOld.put("className", tLqclassOld.getClassName());
			list.add(mapOld);
			//看课程是否一致
			mergedCourseType = tLqclassOld.getCourseType();
			if (mergedCourseType != null && mergedCourseType != classCourseType)
				count++;
		}
		map.put("count", count);//课程与班级不同的合并班级个数
		map.put("newClassName", className);//新班级课程名称
		map.put("newClassId", newClassId);//新班级id
		map.put("list", list);//被合并的班级列表
		mv.addAllObjects(map);
		mv.setViewName("/WEB-INF/view/lqclass/mergeClass_confirm_dialog.jsp");
		return mv;
	}

	/**
	 * 
	 *@description:合并班级操作
	 *@param map
	 *@param response
	 *@param request
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月10日下午10:02:33
	 *
	 */
	@RequestMapping("/mergeClass.do")
	public void mergeClass(@RequestParam Map<String, Object> map, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<>();
		try {
			tLqclassService.mergeClass(map, response, request);
			returnMap.put("code", 200);
		} catch (Exception e) {
			returnMap.put("code", 202);
		}
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}

	/**
	 * 
	 *@description:跳到添加班级页面
	 *@param mv
	 *@return
	 *@return ModelAndView 
	 *@author:ZhuDiaoHua
	 *@2016年12月12日下午2:55:46
	 *
	 */
	@RequestMapping("/toAddClassPage.do")
	@Functions({ 79 })
	public ModelAndView toAddClassPage(ModelAndView mv) {
		try {
			//1，班级一级类型
			mv.addObject("classTypePreList", dictService.getDictByType(DictService.DICT_CLASSTYPE_PRE));

			//2,班级课程类别
			mv.addObject("courseTypeList", dictService.getSysCourse());

			//3,按类型选择的教师
			//TODO 暂时用所有老师的搜索下拉控件

			//4,选班长的学生列表
			//TODO 无学生可选

			//5,创建班级目的
			mv.addObject("classGoalList", dictService.getDictByType(DictService.DICT_CLASS_GOAL));

			mv.setViewName("/WEB-INF/view/lqclass/class_add.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("跳转异常");
		}

		return mv;

	}

	/**
	 * 去创建班级记录弹窗
	 * @param mv
	 * @return
	 */
	@RequestMapping("/showClassRecord.do")
	@Functions({ 66 })
	public ModelAndView showClassRecord(ModelAndView mv, @RequestParam int lqClassId) {
		mv.addObject("lqClassId", lqClassId);
		mv.setViewName("/WEB-INF/view/lqclass/classRecordDiolog.jsp");
		return mv;
	}
}

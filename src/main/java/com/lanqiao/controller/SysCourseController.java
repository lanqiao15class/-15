package com.lanqiao.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.dto.CourseDTO;
import com.lanqiao.dto.ResultBody;
import com.lanqiao.dto.ReturnResult;
import com.lanqiao.model.SysCourse;
import com.lanqiao.model.SysSyllabus;
import com.lanqiao.model.TUserBind;
import com.lanqiao.model.User;
import com.lanqiao.service.SysCourseService;
import com.lanqiao.service.SysSyllabusService;
import com.lanqiao.util.JsonUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;

@Controller
@RequestMapping("/course")
public class SysCourseController {
	@Autowired
	private SysCourseService service;
	@Autowired
	private SysSyllabusService subService;
	@RequestMapping("/list.do")
	public ModelAndView showCourseView(HttpServletRequest request,HttpServletResponse response){
//		List<CourseDTO> list=service.findAllCourseByAdmin();
		ModelAndView mv= new ModelAndView();
//		mv.addObject("courseList",list);
		mv.setViewName("/WEB-INF/view/course/sysCourseList.jsp");
		return mv;
	}
	/**
	 * 查看或修改弹框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/lookCourse.do")
	public ModelAndView lookCourse(HttpServletRequest request,HttpServletResponse response){
		String cidstr = request.getParameter("id");
		String type = request.getParameter("t");
		int courseId=Integer.parseInt(cidstr);
		CourseDTO course=service.getCourseById(courseId);
		ModelAndView mv= new ModelAndView();
		List<SysSyllabus> pList=new ArrayList<SysSyllabus>();
		List<SysSyllabus> sbuList=new ArrayList<SysSyllabus>();
		List<SysSyllabus> list=subService.findSyllabusByCourseid(courseId);
		for (SysSyllabus sysSyllabus : list) {
			if (sysSyllabus.getPid()==null) {
				pList.add(sysSyllabus);//科目
			}else {
				sbuList.add(sysSyllabus);//阶段
			}
		}
		List<List<SysSyllabus>> lists=new ArrayList<List<SysSyllabus>>();
		for (SysSyllabus p : pList) {
			List<SysSyllabus> temp=new ArrayList<SysSyllabus>();
			temp.add(p);
			for (SysSyllabus sub : sbuList) {
				int id=p.getSyllabusId();
				int pid=sub.getPid();
				if (id==pid) {
					temp.add(sub);
				}
			}
			lists.add(temp);
		}

		mv.addObject("lists",lists);
		mv.addObject("course",course);
		if (type.equals("1")) {
			mv.setViewName("/WEB-INF/view/course/lookCourse.jsp");
		}else if (type.equals("2")) {			
			mv.setViewName("/WEB-INF/view/course/updateCourse.jsp");
		}
		return mv;
	}

	@RequestMapping("/showlist.do")
	public void findAllCourseByAdmin(HttpServletRequest request,HttpServletResponse response){
		List<CourseDTO> list=service.findAllCourseByAdmin();
		JsonUtil.write(response, JSON.toJSONString(list));
	}
	@RequestMapping("/showCreateCourse.do")
	public ModelAndView showCreateCourseView(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv= new ModelAndView();
		mv.setViewName("/WEB-INF/view/course/createCourse.jsp");
		return mv;
	}
	@RequestMapping("/createCourse.do")
	@ResponseBody
	public ReturnResult<Object> CreateCourse(HttpServletRequest request,HttpServletResponse response,SysCourse course){
		Date createTime=new Date(System.currentTimeMillis());
		ReturnResult<Object> result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),ResultBody.SUCCESS.getResultMsg());
		SessionUser  user=WebUtil.getLoginUser(request);
		if (user==null) {
			result.setResultCode(-1);
			result.setResultMsg(ResultBody.getResultMsg(-3));
			return result;
		}
		int userId=user.getUserId();
		course.setUserId(userId);
		course.setCreateTime(createTime);
		int id=service.insertCourseByAdmin(course);
		if (id>0) {
			result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),ResultBody.SUCCESS.getResultMsg(),id);
		}else {
			result=new ReturnResult<Object>(ResultBody.ERROR_130002.getResultCode(),ResultBody.ERROR_130002.getResultMsg());
		}
		return result;
	}

	@RequestMapping("/updateCourse.do")
	@ResponseBody
	public ReturnResult<Object> updateCourse(HttpServletRequest request,
			HttpServletResponse response, SysCourse course) {
		ReturnResult<Object> result = new ReturnResult<Object>(
				ResultBody.SUCCESS.getResultCode(),
				ResultBody.SUCCESS.getResultMsg());
		int id = service.updateByPrimaryKeySelective(course);
		if (id > 0) {
			result = new ReturnResult<Object>(
					ResultBody.SUCCESS.getResultCode(),
					ResultBody.SUCCESS.getResultMsg(), course.getCourseId());
		} else {
			result = new ReturnResult<Object>(
					ResultBody.ERROR_130002.getResultCode(),
					ResultBody.ERROR_130002.getResultMsg());
		}
		return result;
	}
	
	@RequestMapping("/createSub.do")
	@ResponseBody
	public ReturnResult<Object> CreateSub(HttpServletRequest request,HttpServletResponse response,SysSyllabus syllabus ) throws ServletRequestBindingException{
		Date createTime=new Date(System.currentTimeMillis());
		ReturnResult<Object> result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),ResultBody.SUCCESS.getResultMsg());
		String cidstr = request.getParameter("id");
		String name =ServletRequestUtils.getStringParameter(request, "name");
		if (cidstr.equals("-1")) {
			result.setResultCode(-1);
			result.setResultMsg(ResultBody.getResultMsg(110001));
			return result;
		}
		if (name==null) {
			result.setResultCode(-1);
			result.setResultMsg("请先填写阶段名。");
			return result;
		}
		int courseId=Integer.parseInt(cidstr);
		syllabus.setCreateTime(createTime);
		syllabus.setCourseId(courseId);
		syllabus.setClassTime(0);
		syllabus.setSyllabusName(name);
		int id=service.insertSyllabusByAdmin(syllabus);
		syllabus.setSort(id);
		int ires=service.updateSysSyllabusByPrimaryKeySelective(syllabus);
		syllabus.setCourseId(id);
		if (id>0) {
			result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),
					ResultBody.SUCCESS.getResultMsg(),syllabus);
		}else {
			result=new ReturnResult<Object>(ResultBody.ERROR_130002.getResultCode(),
					ResultBody.ERROR_130002.getResultMsg());
		}
		return result;
	}
	@RequestMapping("/createSubCourse.do")
	@ResponseBody
	public ReturnResult<Object> CreateSubCourse(HttpServletRequest request,HttpServletResponse response,SysSyllabus syllabus ){
		Date createTime=new Date(System.currentTimeMillis());
		ReturnResult<Object> result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),ResultBody.SUCCESS.getResultMsg());
		String cidstr = request.getParameter("id");
		String name = request.getParameter("name");
		String pidstr = request.getParameter("pid");
		String ctime = request.getParameter("ctime");
		if (cidstr.equals("-1")||pidstr.equals("-1")) {
			result.setResultCode(-1);
			result.setResultMsg("");
			return result;
		}
		if (name==null) {
			result.setResultCode(-1);
			result.setResultMsg("请先填写课时名。");
			return result;
		}
		int courseId=Integer.parseInt(cidstr);
		int pid=Integer.parseInt(pidstr);
		int classTime=Integer.parseInt(ctime);
		//设置插入对象
		syllabus.setCreateTime(createTime);
		syllabus.setCourseId(courseId);
		syllabus.setPid(pid);
		syllabus.setClassTime(classTime);
		syllabus.setSyllabusName(name) ;
		//插入课时
		int id=service.insertSyllabusByAdmin(syllabus);
		//设置排序字段
		syllabus.setSort(id);
		int ires=service.updateSysSyllabusByPrimaryKeySelective(syllabus);
		syllabus.setSyllabusId(id);
		SysSyllabus pInfo=service.selectBySysSyllabusPrimaryKey(pid);
		//修改父阶段课时
		int pclassTime=pInfo.getClassTime();
		pclassTime=pclassTime+classTime;
		pInfo.setClassTime(pclassTime);
		int upires=service.updateSysSyllabusByPrimaryKeySelective(pInfo);
		//修改课程总课时
		SysCourse course=service.selectSysCourseByPrimaryKey(courseId);
		int total=course.getTotalClass();
		total=total+classTime;
		course.setTotalClass(total);
		upires=service.updateSysCourseByPrimaryKeySelective(course);
		if (id>0) {
			result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),
					ResultBody.SUCCESS.getResultMsg(),syllabus);
			result.setOtherProperties("pctime", pclassTime);
		}else {
			result=new ReturnResult<Object>(ResultBody.ERROR_130002.getResultCode(),
					ResultBody.ERROR_130002.getResultMsg());
		}
		return result;
	}
	/**
	 * 修改时提供弹框值
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSysSyllabus.do")
	@ResponseBody
	public ReturnResult<Object> getSysSyllabus(HttpServletRequest request,HttpServletResponse response){
		ReturnResult<Object> result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),ResultBody.SUCCESS.getResultMsg());
		String cidstr = request.getParameter("id");
		int pid=Integer.parseInt(cidstr);
		SysSyllabus info=service.selectBySysSyllabusPrimaryKey(pid);
		if (info!=null) {
			result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),
					ResultBody.SUCCESS.getResultMsg(),info);
		}else {
			result=new ReturnResult<Object>(ResultBody.ERROR_130002.getResultCode(),
					ResultBody.ERROR_130002.getResultMsg());
		}
		return result;
	}
	@RequestMapping("/resetSortSysSyllabus.do")
	@ResponseBody
	public ReturnResult<Object> resetSortSysSyllabus(HttpServletRequest request,HttpServletResponse response){
		ReturnResult<Object> result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),ResultBody.SUCCESS.getResultMsg());
		String idstr = request.getParameter("id");
		int id=Integer.parseInt(idstr);
		String pidstr = request.getParameter("prev_id");
		int prev_id=Integer.parseInt(pidstr);
		SysSyllabus info=service.selectBySysSyllabusPrimaryKey(id);
		SysSyllabus prev_info=service.selectBySysSyllabusPrimaryKey(prev_id);
		int sort=info.getSort();
		int prev_sort=prev_info.getSort();
		
		prev_info.setSort(sort);
		info.setSort(prev_sort);
		int ires=service.updateSysSyllabusByPrimaryKeySelective(info);
		ires=service.updateSysSyllabusByPrimaryKeySelective(prev_info);
			
		if (ires!=0) {
			result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),
					ResultBody.SUCCESS.getResultMsg());
		}else {
			result=new ReturnResult<Object>(ResultBody.ERROR_130002.getResultCode(),
					ResultBody.ERROR_130002.getResultMsg());
		}
		return result;
	}
	/**
	 * 更新阶段
	 * @param request
	 * @param response
	 * @param syllabus
	 * @return
	 */
	@RequestMapping("/updateSta.do")
	@ResponseBody
	public ReturnResult<Object> updateSta(HttpServletRequest request,HttpServletResponse response,SysSyllabus syllabus ){
		ReturnResult<Object> result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),ResultBody.SUCCESS.getResultMsg());
		String idstr = request.getParameter("id");
		String name = request.getParameter("name");
		if (idstr.equals("-1")) {
			result.setResultCode(-1);
			result.setResultMsg("");
			return result;
		}
		if (name==null) {
			result.setResultCode(-1);
			result.setResultMsg("请先填写阶段名。");
			return result;
		}
		int syllabusId=Integer.parseInt(idstr);
	
		syllabus.setSyllabusId(syllabusId);
		syllabus.setSyllabusName(name) ;

		int upires=service.updateSysSyllabusByPrimaryKeySelective(syllabus);
		
		if (upires >0) {
			result=new ReturnResult<Object>(ResultBody.SUCCESS.getResultCode(),
					ResultBody.SUCCESS.getResultMsg(),syllabus);
		}else {
			result=new ReturnResult<Object>(ResultBody.ERROR_130002.getResultCode(),
					ResultBody.ERROR_130002.getResultMsg());
		}
		return result;
	}
	
	@RequestMapping("/updateSubCourse.do")
	@ResponseBody
	public ReturnResult<Object> updateSubCourse(HttpServletRequest request,
			HttpServletResponse response, SysSyllabus syllabus) {
		ReturnResult<Object> result = new ReturnResult<Object>(
				ResultBody.SUCCESS.getResultCode(),
				ResultBody.SUCCESS.getResultMsg());
		String idstr = request.getParameter("id");
		String name = request.getParameter("name");
		String ctime = request.getParameter("ctime");
		if (idstr.equals("-1")) {
			result.setResultCode(-1);
			result.setResultMsg("");
			return result;
		}
		if (name == null) {
			result.setResultCode(-1);
			result.setResultMsg("请先填写课时名。");
			return result;
		}
		int syllabusId = Integer.parseInt(idstr);
		int classTime = Integer.parseInt(ctime);
		// 获取修改对象
		SysSyllabus Info = service.selectBySysSyllabusPrimaryKey(syllabusId);
		int pid=Info.getPid();
		// 获取父对象
		SysSyllabus pInfo = service.selectBySysSyllabusPrimaryKey(pid);
		Integer courseId = pInfo.getCourseId();
		int oldsubclasstime = Info.getClassTime();
		int pclasstime = pInfo.getClassTime();
		// 新父课时
		int newPClassTime = pclasstime - oldsubclasstime + classTime;
		pInfo.setClassTime(newPClassTime);
		int pupdate = service.updateSysSyllabusByPrimaryKeySelective(pInfo);
		// 修改课程总课时
		SysCourse course = service.selectSysCourseByPrimaryKey(courseId);
		int total = course.getTotalClass();
		total = total - oldsubclasstime + classTime;
		course.setTotalClass(total);
		int upires = service.updateSysCourseByPrimaryKeySelective(course);
		// 设置修改对象
		syllabus.setSyllabusId(syllabusId);
		syllabus.setClassTime(classTime);
		syllabus.setSyllabusName(name);
		// 插入课时
		int id = service.updateSysSyllabusByPrimaryKeySelective(syllabus);
		syllabus.setPid(pid);
		if (id > 0) {
			result = new ReturnResult<Object>(
					ResultBody.SUCCESS.getResultCode(),
					ResultBody.SUCCESS.getResultMsg(), syllabus);
			result.setOtherProperties("pctime", newPClassTime);
		} else {
			result = new ReturnResult<Object>(
					ResultBody.ERROR_130002.getResultCode(),
					ResultBody.ERROR_130002.getResultMsg());
		}
		return result;
	}
	@RequestMapping("/delsub.do")
	@ResponseBody
	public ReturnResult<Object> delsub(HttpServletRequest request,
			HttpServletResponse response, SysSyllabus syllabus) {
		ReturnResult<Object> result = new ReturnResult<Object>(
				ResultBody.SUCCESS.getResultCode(),
				ResultBody.SUCCESS.getResultMsg());
		String idstr = request.getParameter("id");
		String pidstr = request.getParameter("ppid");
		int pid=Integer.parseInt(pidstr);
		int syllabusId = Integer.parseInt(idstr);
		// 获取修改对象
		SysSyllabus Info = service.selectBySysSyllabusPrimaryKey(syllabusId);
		// 获取父对象
		SysSyllabus pInfo = service.selectBySysSyllabusPrimaryKey(pid);
		Integer courseId = pInfo.getCourseId();
		int oldsubclasstime = Info.getClassTime();
		int pclasstime = pInfo.getClassTime();
		//更新父课时
		int newPClassTime = pclasstime - oldsubclasstime;
		pInfo.setClassTime(newPClassTime);
		int pupdate = service.updateSysSyllabusByPrimaryKeySelective(pInfo);
		// 修改课程总课时
		SysCourse course = service.selectSysCourseByPrimaryKey(courseId);
		int total = course.getTotalClass();
		total = total - oldsubclasstime;
		course.setTotalClass(total);
		int upires = service.updateSysCourseByPrimaryKeySelective(course);
		// 删除改对象
		syllabus.setSyllabusId(syllabusId);
		int id = subService.deleteByPrimaryKey(syllabusId);
		syllabus.setPid(pid);
		if (id > 0) {
			result = new ReturnResult<Object>(
					ResultBody.SUCCESS.getResultCode(),
					ResultBody.SUCCESS.getResultMsg(), syllabus);
			result.setOtherProperties("pctime", newPClassTime);
		} else {
			result = new ReturnResult<Object>(
					ResultBody.ERROR_130002.getResultCode(),
					ResultBody.ERROR_130002.getResultMsg());
		}
		return result;
	}
	//删除阶段
	@RequestMapping("/delstage.do")
	@ResponseBody
	public ReturnResult<Object> delstage(HttpServletRequest request,
			HttpServletResponse response, SysSyllabus syllabus) {
		ReturnResult<Object> result = new ReturnResult<Object>(
				ResultBody.SUCCESS.getResultCode(),
				ResultBody.SUCCESS.getResultMsg());
		String idstr = request.getParameter("id");
		int syllabusId = Integer.parseInt(idstr);
		// 获取修改对象
		SysSyllabus Info = service.selectBySysSyllabusPrimaryKey(syllabusId);
		//获取子科目
		List<SysSyllabus> list=subService.findSyllabusByPid(syllabusId);
		if (list!=null||list.size()!=0) {
			for (SysSyllabus sysSyllabus : list) {
				int i=subService.deleteByPrimaryKey(sysSyllabus.getSyllabusId());
			}
		}
		// 获取父对象
		Integer courseId = Info.getCourseId();
		int oldsubclasstime=Info.getClassTime();
		SysCourse course = service.selectSysCourseByPrimaryKey(courseId);
		int total = course.getTotalClass();
		total = total - oldsubclasstime;
		course.setTotalClass(total);
		int upires = service.updateSysCourseByPrimaryKeySelective(course);
		// 删除改对象
		syllabus.setSyllabusId(syllabusId);
		int id = subService.deleteByPrimaryKey(syllabusId);
		
		if (id > 0) {
			result = new ReturnResult<Object>(
					ResultBody.SUCCESS.getResultCode(),
					ResultBody.SUCCESS.getResultMsg(), syllabus);
		} else {
			result = new ReturnResult<Object>(
					ResultBody.ERROR_130002.getResultCode(),
					ResultBody.ERROR_130002.getResultMsg());
		}
		return result;
	}
}

package com.lanqiao.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.Functions;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant.jobStatusEnum;
import com.lanqiao.model.Student;
import com.lanqiao.model.TJobDetail;
import com.lanqiao.service.DictService;
import com.lanqiao.service.TJobDetailService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.JsonUtil;

@Controller
@RequestMapping("/dimission")
public class DimissionController {

	private static final Logger logger = LogManager.getLogger(DimissionController.class);
	
	@Resource
	DictService dictService;
	
	@Resource
	TJobDetailService JobDetailService;

	/**
	 * 
	* @Description:根据学员id跳转到离职登记页面
	* @param mv
	* @return 
	* @return ModelAndView 
	* @author ZhouZhiHua
	* @createTime 2016年11月28日 下午4:24:02
	 */
	@RequestMapping("/goDimission.do")
	@Functions({73})
	public ModelAndView goDimission(ModelAndView mv,HttpServletRequest request,HttpServletResponse response){
		mv.setViewName("/WEB-INF/view/job/dimission.jsp");
		try {
			int reqUserId=Integer.parseInt(request.getParameter("userId"));
			//1.职业信息(t_job_detail)+就业信息(当前+历史)
			List<Map<String, Object>> jobDetailList=JobDetailService.getJobDetailBystuId(reqUserId);
			mv.addObject("jobDetailList",jobDetailList);
			//2.职业状态列表
			mv.addObject("jobStatusList", WebUtil.getJobStatusList());
			//3.企业性质
			mv.addObject("cocompType", dictService.getDictByType(DictService.DICT_COCOMP_TYPE));
			//4.企业规模
			mv.addObject("cocompScale", dictService.getDictByType(DictService.DICT_COCOMP_SCALE));
			//5.岗位性质
			mv.addObject("inviteType",dictService.getDictByType(DictService.DICT_POSITION_TYPE));
			//6.就业方式列表
			mv.addObject("jobFromTypeList", WebUtil.getJobFromTypeList());
			//7.在职的职业状态
			mv.addObject("jobing", jobStatusEnum.JOBING.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.getModeAndView404("异常错误.");
		}
		return mv;
	}
	
	/**
	 * 
	* @Description:添加离职登记
	* @param mv
	* @param request
	* @param response 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月14日 下午5:18:13
	 */
	@RequestMapping(value="/saveDimission.do",method=RequestMethod.POST)
	public void saveDimission(ModelAndView mv,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			TJobDetail jobDetail=new TJobDetail();
			jobDetail.setJobId(Integer.parseInt(request.getParameter("jobId")));
			jobDetail.setDismissTime(CommonUtil.dateStrToDate(request.getParameter("dismissTime")));
			jobDetail.setDismissReason(request.getParameter("dismissReason"));
			jobDetail.setOccupationStatus(jobStatusEnum.LEAVEJOB.getValue());
			jobDetail.setInputtimeOut(new Date());
			SessionUser sessionUser=WebUtil.getLoginUser(request);
			Integer userId=sessionUser.getUserId();//当前登录者userId
			jobDetail.setDismissUserid(userId);
			Student student=new Student();
			student.setUserId(Integer.parseInt(request.getParameter("userId")));
			student.setJobstatus(jobStatusEnum.LEAVEJOB.getValue());
			JobDetailService.saveJobDetailBystuId(jobDetail,student);
			map.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 201);
		}
		JsonUtil.write(response, JSON.toJSONString(map));
	}
	
}

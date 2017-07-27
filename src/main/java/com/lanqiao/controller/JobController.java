package com.lanqiao.controller;

import java.io.PrintWriter;
import java.text.ParseException;
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

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.lanqiao.common.Functions;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.jobStatusEnum;
import com.lanqiao.model.TJobDetail;
import com.lanqiao.service.DictService;
import com.lanqiao.service.JobService;
import com.lanqiao.service.LQCityService;
import com.lanqiao.service.StudentService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.JsonUtil;

@Controller
public class JobController
{
	@Resource
	DictService dictservice;
	
	@Resource
	JobService jobservice;
	@Resource
	LQCityService lQCityService;
	
	@Resource
	StudentService studentService;

	private static final Logger logger = LogManager
			.getLogger(JobController.class);

	
	/**
	 * 根据用户ID 得到学生的详细信息.
	 * @param request
	 * @param response
	 */
	@RequestMapping("/job/getstudentByid.do")
	@Functions({72})
	public void getstudentByid(HttpServletRequest request,
			HttpServletResponse response)
	{

		String userids = request.getParameter("userids");
		if(StringUtils.isEmpty(userids))
		{
			WebUtil.sendJson(response, 203, "参数userids 不能为空.");
			return ;
		}
		
		String as[]= userids.split(",");
		Integer nas [] = new Integer[as.length];
		for(int i=0;i<as.length;i++)
		{
			String s = as[i];
			nas[i] = new Integer(s);
		}
		
		try
		{
			
			List<Map>  list = jobservice.getStudentById(nas);
			HashMap hsret = new HashMap();
			hsret.put("code", 200);
			hsret.put("datalist", list);
			
			PrintWriter out = response.getWriter();
			out.println (JSON.toJSONString(hsret));
			
		}catch(Exception se)
		{
			WebUtil.sendJson(response, 202, "异常错误");
		}
		
		
	}
	
	/**
	 *  显示入职登记
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/job/showjobview.do")
	@Functions({72})
	public ModelAndView showclassmember(HttpServletRequest request,
			HttpServletResponse response)
	{
		
		String userids = request.getParameter("userids");
		if(StringUtils.isEmpty(userids))
		{
			return null;
		}
		
		HashMap hsret = new HashMap();
		try
		{
			//查询字典表, 得到下拉列表数据.
			List<Map<String,Object>> scale =  dictservice.getDictByType(DictService.DICT_COCOMP_SCALE);
			List<Map<String,Object>> comptype =  dictservice.getDictByType(DictService.DICT_COCOMP_TYPE);
			List<Map<String,Object>> posiontype =  dictservice.getDictByType(DictService.DICT_POSITION_TYPE);
		
			hsret.put("compscale", scale);
			hsret.put("comptype", comptype);
			hsret.put("posiontype", posiontype);
			hsret.put("provinceList", lQCityService.findProvinces());
			hsret.put("userids", userids);
			
			
			return new ModelAndView("/WEB-INF/view/job/inputJob.jsp",hsret);

		} catch (Exception e)
		{
			e.printStackTrace();
			return WebUtil.getModeAndView404("异常错误.");
		}
		
	}
	
	/**
	 * 在入职登记前, 检查学生的状态, 是否处于离职状态. 
	 * 批量学生, 找出不合法的学生. 
	 * @param pp
	 * @param request
	 * @param response
	 */
	@Functions({72})
	@RequestMapping("/job/checkjobstatus.do")
	public void checkjobstatus(@RequestParam String userids, HttpServletRequest request,
			HttpServletResponse response)
	{
		if(userids ==null || "".equals(userids))
		{
			WebUtil.sendJson(response, 201, "参数不能为空 ");
			return ;
		}
		ArrayList <Integer> aldata = new ArrayList<Integer>();
		String stra[]=userids.split(",");
		for(String s: stra)
		{
			if(StringUtils.isEmpty(s)) continue;
			aldata.add(new Integer(s));
		}
		
		List<Map> mplist = jobservice.getBatchUserJobStatus(aldata);
		StringBuffer ssb = new StringBuffer(2014);
		int failcount = 0;
		for(Map mp : mplist)
		{
			Integer jobstatus = (Integer)mp.get("jobstatus");
			if(jobstatus !=null && jobstatus ==jobStatusEnum.JOBING.getValue())
			{
				String sname = (String)mp.get("real_name");
				if(ssb.length() !=0)
					ssb.append(",");
				if(sname !=null)
					ssb.append(sname);
				else
					ssb.append(mp.get("user_id").toString());
				
				failcount++;
			}
			
		}
		ssb.append("  " + failcount +"人, 处理在职状态, 请先做离职登记");
		
		if(failcount >0)
			WebUtil.sendJson(response, 202, ssb.toString());
		else
			WebUtil.sendJson(response, 200, "");
			
		return ;

		
	}
/**
 *  保存入职登记信息.
 * @param request
 * @param response
 */
	@Functions({72})
	@RequestMapping("/job/postjob.do")
	public void classmemberjson(@RequestParam HashMap pp, HttpServletRequest request,
			HttpServletResponse response)
	{
		
		WebUtil.printMap(pp);
		
		try
		{
			int loginuserid =WebUtil.getLoginUser(request).getUserId(); // 当前登录者
			
			TJobDetail job = new TJobDetail();
			
			job.setCityId(new Integer(pp.get("cityId").toString()));
			job.setCompanyName(pp.get("companyName").toString());
			job.setCompanyScale(pp.get("companyScale").toString());
			job.setCompanyType(pp.get("companyType").toString());
			String strtemp = pp.get("entryTime").toString();
			
			job.setEntryTime(CommonUtil.dateStrToDate(strtemp));
			job.setJobfromType(new Integer(pp.get("jobfromType").toString()));
			//job.setOccupationStatus(OccupationStatus.Online.getValue());
			
			job.setPositionName(pp.get("positionName").toString() );
			job.setPositionSalary(new Integer(pp.get("positionSalary").toString()) );
			job.setPositionType(new Integer(pp.get("positionType").toString()) );
			job.setProvId(new Integer(pp.get("provId").toString())  );
			job.setTeacherId(new Integer(pp.get("teacherId").toString()));
			
			job.setEntryUserid(loginuserid);
			
			String userids = request.getParameter("userids");
			if(StringUtils.isEmpty(userids))
			{
				WebUtil.sendJson(response, 203, "参数userids 不能为空.");
				return ;
			}
			
			String stra [] = userids.split(",");
			for(int i=0;i<stra.length;i++)
			{
				job.setUserId(Integer.parseInt(stra[i]));
				jobservice.SaveJobDetail(job,loginuserid);
			}
			//成功.
			
			
			WebUtil.sendJson(response, 200, "");
			
		}catch(Exception se){
			se.printStackTrace();
			WebUtil.sendJson(response, 202, "异常错误");
		}
		
		
		
	}
	/**
	 * 去学员就业服务列表
	 * @param request
	 * @param mv
	 * @param opType
	 * @return
	 */
	@RequestMapping("/job/{opType}/goStuEmployList.do")
	@Functions({69,70,71})
	public ModelAndView goStuEmployList(HttpServletRequest request,ModelAndView mv,@PathVariable(value="opType")String opType){
		mv.addObject("jobStatusList", WebUtil.getJobStatusList());//职业状态列表
		mv.addObject("jobWayList", WebUtil.getJobFromTypeList());//就业方式列表
		mv.setViewName("/WEB-INF/view/job/stuEmployList.jsp");
	    return mv;	
	}
	/**
	 * 获取学员就业服务列表表格数据
	 * @param request
	 * @param mv
	 * @param opType
	 * @return
	 */
	@RequestMapping("/job/getStuEmployList.do")
	@Functions({69,70,71})
	public void getStuEmployList(HttpServletRequest request,HttpServletResponse response,@RequestParam String opType){

		// 打印调试信息. 
		WebUtil.printParameters(request);
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		/**====================设置查询参数开始=====================**/
		if(request.getParameter("select_class_name")!=null && !"".equals(request.getParameter("select_class_name"))){
			paramMap.put("select_class_name", request.getParameter("select_class_name"));
		}
		try {
			if(request.getParameter("select_school_id")!=null && !"".equals(request.getParameter("select_school_id"))){
				paramMap.put("select_school_id", Integer.parseInt(request.getParameter("select_school_id")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if(request.getParameter("jobStatus_lq")!=null && !"".equals(request.getParameter("jobStatus_lq"))){
				paramMap.put("jobStatus_lq", Integer.parseInt(request.getParameter("jobStatus_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		try {
			if(request.getParameter("jobWay_lq")!=null && !"".equals(request.getParameter("jobWay_lq"))){
				paramMap.put("jobWay_lq", Integer.parseInt(request.getParameter("jobWay_lq")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		try {
			if(request.getParameter("regional_director_userid")!=null && !"".equals(request.getParameter("regional_director_userid"))){
				paramMap.put("regional_director_userid", Integer.parseInt(request.getParameter("regional_director_userid")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		try {
			if(request.getParameter("recruit_manager_userid")!=null && !"".equals(request.getParameter("recruit_manager_userid"))){
				paramMap.put("recruit_manager_userid", Integer.parseInt(request.getParameter("recruit_manager_userid")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		try {
			if(request.getParameter("course_advisor_userid")!=null && !"".equals(request.getParameter("course_advisor_userid"))){
				paramMap.put("course_advisor_userid", Integer.parseInt(request.getParameter("course_advisor_userid")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		if(request.getParameter("student_name_lq")!=null && !"".equals(request.getParameter("student_name_lq"))){
			paramMap.put("student_name_lq", request.getParameter("student_name_lq"));
		}
		
		try {
			if(request.getParameter("jSerBeginTime_lq")!=null && !"".equals(request.getParameter("jSerBeginTime_lq"))){
				paramMap.put("jSerBeginTime_lq", new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("jSerBeginTime_lq")));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			if(request.getParameter("jSerEndTime_lq")!=null && !"".equals(request.getParameter("jSerEndTime_lq"))){
				paramMap.put("jSerEndTime_lq", new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("jSerEndTime_lq")));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			if(request.getParameter("fJobBeginTime_lq")!=null && !"".equals(request.getParameter("fJobBeginTime_lq"))){
				paramMap.put("fJobBeginTime_lq", new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fJobBeginTime_lq")));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			if(request.getParameter("fJobEndTime_lq")!=null && !"".equals(request.getParameter("fJobEndTime_lq"))){
				paramMap.put("fJobEndTime_lq", new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fJobEndTime_lq")));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		try {
			if(request.getParameter("currpage")!=null && !"".equals(request.getParameter("currpage"))){
				Integer currpage=Integer.parseInt(request.getParameter("currpage"));
				Integer pageSize=Integer.parseInt(request.getParameter("pageSize"));
				paramMap.put("currpage",(currpage-1)*pageSize );
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			if(request.getParameter("pageSize")!=null && !"".equals(request.getParameter("pageSize"))){
				paramMap.put("pageSize", Integer.parseInt(request.getParameter("pageSize")));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		/**====================设置查询参数结束=====================**/
		SessionUser sessionUser=WebUtil.getLoginUser(request);
		Integer userId=sessionUser.getUserId();//当前登录者userId
		HashMap<String,Object> hsret = new HashMap<String,Object>();
		
		paramMap.put("teaUserId", userId);//设置老师的userId以查找我关注/管理的学员
		//设置学员状态查询参数
		StringBuffer str=new StringBuffer();
		str.append(GlobalConstant.StuStatusEnum.FindJobing.getValue()).append(",");//求职中
		str.append(GlobalConstant.StuStatusEnum.WORKED.getValue());//已就业
		String stuStatusParam=str.toString();
		paramMap.put("stuStatusParam", stuStatusParam);//学员状态
		paramMap.put("auditStatusParam", GlobalConstant.auditStatusEnum.PASS.getValue());//学员审核状态:通过
		List<Map<String,Object>> stuJobList=new ArrayList<Map<String,Object>>();
		if(opType.equals("0"))
		{//我关注的操作
			//获取我的关注的就业学员列表
			stuJobList=studentService.selectMyFocusStuJobList(paramMap);
			//1把记录数放session
		 if(request.getParameter("currpage")!=null && !"".equals(request.getParameter("currpage")) && request.getParameter("currpage").equals("1")){
				int ncount = studentService.getMyFocusStuJobCount(paramMap);
				hsret.put("recordcount", ncount);//我关注的就业学员总数
				
			}
		}else if(opType.equals("1")){
			//.获取全部就业学员列表
			stuJobList=studentService.selectAllStuJobList(paramMap);
			
			if(request.getParameter("currpage")!=null && !"".equals(request.getParameter("currpage")) && request.getParameter("currpage").equals("1")){
				int ncount =studentService.getAllStuJobCount(paramMap);
				hsret.put("recordcount", ncount);//全部的就业学员人数
			}
		}else{
			//.获取我管理的就业学员列表
			stuJobList=studentService.selectMyManageStuJobList(paramMap);
			
			if(request.getParameter("currpage")!=null && !"".equals(request.getParameter("currpage")) && request.getParameter("currpage").equals("1")){
				int ncount =studentService.getMyManageStuJobCount(paramMap);
				hsret.put("recordcount", ncount);//我管理的就业学员人数
			}
		}
		//统计求职中/在职/离职/已就业学员人数开始
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.FindJobing.getValue());//设置学员状态为求职中
		int jobHuntingCount=studentService.getStuCount(paramMap);//查询今年求职中学员人数
		hsret.put("jobHuntingCount", jobHuntingCount);
		paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.WORKED.getValue());//设置学员状态为已就业
		int hasWorkCount=studentService.getStuCount(paramMap);//查询今年已就业学员人数
		hsret.put("hasWorkCount", hasWorkCount);
		paramMap.put("jobstatus", GlobalConstant.jobStatusEnum.JOBING.getValue());//设置学员职业状态为在职
		int inWorkCount=studentService.getInOrOutWorkCount(paramMap);//查询今年在职学员人数
		hsret.put("inWorkCount", inWorkCount);
		paramMap.put("jobstatus", GlobalConstant.jobStatusEnum.LEAVEJOB.getValue());//设置学员职业状态为离职
		int outWorkCount=studentService.getInOrOutWorkCount(paramMap);//查询今年离职学员人数
		hsret.put("outWorkCount", outWorkCount);
		//统计求职中/在职/离职/已就业学员人数结束
		
		hsret.put("datalist", stuJobList);
     	SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd");
     	String formateDate="";
     	String jobSerTime="";//就业服务开始时间
     	String empTime="";//首次就业时间
     	Map<String,Object> param=new HashMap<String,Object>();
		int i=0;//计数
	    for(Map<String,Object> map:stuJobList)
	    {

	    	formateDate=formate.format(map.get("updateTime"));
	    	map.put("updateTime",formateDate);
	    	if(map.get("jobSerStarTime")!=null){
	    		jobSerTime=formate.format(map.get("jobSerStarTime"));
	    	}
	    	map.put("jobserviceStarttime",jobSerTime );//就业服务开始时间
	    	if(map.get("empTime")!=null){
	    		empTime=formate.format(map.get("empTime"));
	    	}
	    	map.put("empTime",empTime );//首次就业时间
	    	int temp=i+1;
        	map.put("option", "");
        	map.put("nstatus", 1);//设置是否显示表格里的图标(不为1，亦表头不显示任何图标)
        	map.put("stuStatus", WebUtil.getStuStatusByValue(Integer.parseInt(map.get("status").toString())));//获取学员状态的label值
            //设置将课程类别value转换成文字的参数
        	param.put("type", "lq_courseType");
        	param.put("value", map.get("courseType"));
        	map.put("courseType", dictservice.getLabelByValAndType(param));//courseType文字显示
        	//将职业状态value转换成文字
        	if(map.get("jobstatus")!=null){
        	map.put("jobStatus",WebUtil.getJobStaLabel(Integer.parseInt(map.get("jobstatus").toString())));//职业状态文字显示	
        	}
        	//将就业方式value转换成文字
        	if(map.get("jobfromtype")!=null){
        	map.put("jobWay", WebUtil.getJobFromTypeLabel(Integer.parseInt(map.get("jobfromtype").toString())));//就业方式文字显示
        	}
        	//设置关注
    	 	if(map.get("dataId")!=null){
        		map.put("ifmy", 1);
        	}else{
        		map.put("ifmy", 0);
        	}
        	map.put("indexno", temp);
        	i++;
	    }
	    String strout =  JSON.toJSONString(hsret);
        JsonUtil.write(response, strout);	
	}
}

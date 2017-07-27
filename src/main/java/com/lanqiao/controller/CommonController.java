package com.lanqiao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.ClassStatusEnum;
import com.lanqiao.constant.GlobalConstant.StuPaidEnum;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.constant.GlobalConstant.jobStatusEnum;
import com.lanqiao.model.User;
import com.lanqiao.service.CommonService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.CommonUtil;

@Controller
public class CommonController {

	private final static Logger log = LogManager
			.getLogger(CommonController.class);

	private static int Cache_Time = 0; // 动态js 的浏览器的缓存时间
	
	@Resource
	CommonService cmService;
	
	@Resource
	UserService  userservice;
	
	/**
	 * 同步用户信息. 
	 * 当用户信息更改后,同步到session中. 并返回到浏览器中. 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/user/syncUserInfo.do")
	public void syncUserInfo(HttpServletRequest request, HttpServletResponse response) 
	{
		SessionUser sessuser = WebUtil.getLoginUser(request);
		HashMap mpret = new HashMap();
		if(sessuser !=null)
		{
			
			try
			{
				User newuser = userservice.getUserInfoByUserId(sessuser.getUserId());
				
				if(newuser !=null)
				{
					CommonUtil.copyBean(sessuser, newuser);
					
				}
				//将修改后的数据同步到页面上. 
				if(sessuser.getRealName() ==null || "".equals(sessuser.getRealName()))
				{
					mpret.put("realname", "无真实姓名");
				}else
					mpret.put("realname", sessuser.getRealName());
				
				if(sessuser.getPhoto() ==null || "".equals(sessuser.getPhoto()))
				{
					mpret.put("photo", GlobalConstant.defaultheadface);
				}
				else
				mpret.put("photo", GlobalConstant.httpUploadURL + sessuser.getPhoto());
				
				mpret.put("code", 200);
				response.setContentType("text/html;charset=UTF-8");
				String s = JSON.toJSONString(mpret);
				response.getOutputStream().write(s.getBytes("utf-8"));
				response.getOutputStream().flush();
				
			}catch(Exception se)
			{
				WebUtil.sendJson(response, 202, "异常错误");
			}
			
		}else
			WebUtil.sendJson(response, 201, "没登录");
		
		
	}
	
	@RequestMapping("/js/commonjsForStudent.do")
	public void commonjsForStudent(HttpServletRequest request, HttpServletResponse response) 
	throws Exception {
		
		response.setContentType("application/javascript;charset=UTF-8");
	
		log.info("调用:" + new java.util.Date().toLocaleString());
		//控制缓存. 
		response.setHeader("Cache-Control", "max-age=" + Cache_Time);  
		StringBuffer sbData = new StringBuffer(2048);
		ServletOutputStream out = response.getOutputStream();
		
		 List<Map>  ll = cmService.ListAllNation();
		sbData.append ("var gl_nationdata="+JSON.toJSONString(ll)+";");
		
		//院校列表
		ll = cmService.ListAllUniversity();
		sbData.append("var gl_schoolllist="+JSON.toJSONString(ll)+";");
		
		out.write(sbData.toString().getBytes("UTF-8"));
		out.flush();
	}
	
	@RequestMapping("/js/commonjs.do")
	public void Commonjs(HttpServletRequest request, HttpServletResponse response) 
	throws Exception {
		
		response.setContentType("application/javascript;charset=UTF-8");
	
		log.info("调用:" + new java.util.Date().toLocaleString());
		//控制缓存. 
		response.setHeader("Cache-Control", "max-age=" + Cache_Time);  
		
		 List<Map>  ll = cmService.ListDistinctStudentName();
		StringBuffer sbData = new StringBuffer(2048);
		
		//学员列表
		String s = JSON.toJSONString(ll);
		
		//log.info(s);
		ServletOutputStream out = response.getOutputStream();
		sbData.append("var gl_studentnamelist="+s+";");
		System.out.println ("gl_studentnamelist:"+ + ll.size());
		
		//out.println ("var gl_studentnamelist="+s+";");
		//院校列表
		ll = cmService.ListAllUniversity();
		sbData.append("var gl_schoolllist="+JSON.toJSONString(ll)+";");
		
		//out.println ("var gl_schoolllist="+JSON.toJSONString(ll)+";");
		
		//专业列表
		ll = cmService.ListAllMajor();
		sbData.append ("var gl_majorList="+JSON.toJSONString(ll)+";");
		
		//子院校名字
		ll = cmService.ListAllSubSchoolName();
		sbData.append ("var gl_subschoolname="+JSON.toJSONString(ll)+";");
		
		ll = cmService.ListAllNation();
		sbData.append ("var gl_nationdata="+JSON.toJSONString(ll)+";");
		ll = cmService.ListDistinctTeacherName();
		sbData.append ("var gl_teacherlist="+JSON.toJSONString(ll)+";");
		
		ll = cmService.ListDistinctClassName();
		sbData.append ("var gl_classlist="+JSON.toJSONString(ll)+";");
	
		ll = cmService.ListDepartMentList();
		sbData.append ("var gl_departmentlist="+JSON.toJSONString(ll)+";");
		
		
		
		//输入学员状态编码到javascript
		HashMap<String,Object> mpstatus = new HashMap<String,Object>();
		mpstatus.put("NOREGISTRATION", StuStatusEnum.NOREGISTRATION.getValue());
		mpstatus.put("AUDIT", StuStatusEnum.AUDIT.getValue());
		mpstatus.put("NOCLASS", StuStatusEnum.NOCLASS.getValue());
		mpstatus.put("NOSTARTCLASS", StuStatusEnum.NOSTARTCLASS.getValue());
		mpstatus.put("BESTUDY", StuStatusEnum.BESTUDY.getValue());
		mpstatus.put("EndStudy", StuStatusEnum.EndStudy.getValue());
		mpstatus.put("FindJobing", StuStatusEnum.FindJobing.getValue());
		mpstatus.put("EXPEL", StuStatusEnum.EXPEL.getValue());
		mpstatus.put("QUANTUI", StuStatusEnum.QUANTUI.getValue());
		mpstatus.put("LEAVESCHOLL", StuStatusEnum.LEAVESCHOLL.getValue());
		
		mpstatus.put("XIUXUE", StuStatusEnum.XIUXUE.getValue());
		mpstatus.put("XIUXUEBack", StuStatusEnum.XIUXUEBack.getValue());
		mpstatus.put("DELAYGRADUATE", StuStatusEnum.DELAYGRADUATE.getValue());
		mpstatus.put("DELAYWORK", StuStatusEnum.DELAYWORK.getValue());
		mpstatus.put("WORKED", StuStatusEnum.WORKED.getValue());
		sbData.append ("var gl_enumStatus="+JSON.toJSONString(mpstatus)+";");
			
		
		mpstatus = new HashMap<String,Object>();
		mpstatus.put("NOCLASSES", ClassStatusEnum.NOCLASSES.getValue());
		mpstatus.put("INTHELECTURE", ClassStatusEnum.INTHELECTURE.getValue());
		mpstatus.put("BEFORETHETRAININGSESSION", ClassStatusEnum.BEFORETHETRAININGSESSION.getValue());
		mpstatus.put("INTHETRAINING", ClassStatusEnum.INTHETRAINING.getValue());
		mpstatus.put("THEGRADUATION", ClassStatusEnum.THEGRADUATION.getValue());
			
		mpstatus.put("INTHEEMPLOYMENT", ClassStatusEnum.INTHEEMPLOYMENT.getValue());
		mpstatus.put("OFTHEEMPLOYMENT", ClassStatusEnum.OFTHEEMPLOYMENT.getValue());
		mpstatus.put("SHUTDOWN", ClassStatusEnum.SHUTDOWN.getValue());
		sbData.append ("var gl_classStatus="+JSON.toJSONString(mpstatus)+";");
		
		mpstatus = new HashMap<String,Object>();
		mpstatus.put("NOJOB", jobStatusEnum.NOJOB.getValue());//未就业
		mpstatus.put("JOBING", jobStatusEnum.JOBING.getValue());//在职
		mpstatus.put("LEAVEJOB", jobStatusEnum.LEAVEJOB.getValue());//离职
		sbData.append ("var gl_jobStatus="+JSON.toJSONString(mpstatus)+";");
		
		
		//费用缴纳状态. 
		mpstatus = new HashMap<String,Object>();
		mpstatus.put("NOTPAY", StuPaidEnum.NOTPAY.getValue());//
		mpstatus.put("PARTOFTHEPAY", StuPaidEnum.PARTOFTHEPAY.getValue());//
		mpstatus.put("ALLPAY", StuPaidEnum.ALLPAY.getValue());//
		mpstatus.put("SPECIALPAY", StuPaidEnum.SPECIALPAY.getValue());//
		mpstatus.put("PartReturn", StuPaidEnum.PartReturn.getValue());//
		mpstatus.put("AllReturn", StuPaidEnum.AllReturn.getValue());//
		
		sbData.append ("var gl_paidStatus="+JSON.toJSONString(mpstatus)+";");
		
		
		
		
		
		
		
		
		if(WebUtil.isSupportGzip(request))
		{
			byte bData[] = WebUtil.gzip(sbData.toString());
			response.addHeader("Content-Encoding", "gzip");
			response.setContentLength(bData.length);
			
			System.out.println ("原始数据大小:"+sbData.length()+"压缩后数据大小:" + bData.length);
			out.write(bData);
			out.flush();
		}else
		{
			//浏览器不支持压缩. 
			out.write(sbData.toString().getBytes("UTF-8"));
			out.flush();
		}
		
		
	}
}

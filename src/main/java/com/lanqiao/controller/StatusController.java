package com.lanqiao.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.Functions;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant.ClassStatusEnum;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.model.Student;
import com.lanqiao.model.TLqclass;
import com.lanqiao.service.ClassStatusService;
import com.lanqiao.service.StudentStatusService;
import com.lanqiao.service.UserService;
import com.lanqiao.util.CommonUtil;

@Controller
public class StatusController
{

	@Resource
	StudentStatusService service;

	@Resource
	ClassStatusService classservice;

	@Resource
	UserService userservice;
	
	/**
	 * 根据学会当前状态,判断可以变化的状态.
	 * 
	 * @param currentstatus
	 * @return
	 */
	public HashMap<Integer, String> getCanChangeStatusList(int currentstatus)
	{
		HashMap<Integer, String> hsret = new HashMap<Integer, String>();
		// 可操作。下拉可选项为“开除、劝退、退学、延期结业”5个

		// 当前未分班.
		if (StuStatusEnum.NOCLASS.getValue() == currentstatus)
		{

			hsret.put(StuStatusEnum.LEAVESCHOLL.getValue(),
					StuStatusEnum.LEAVESCHOLL.getText());
		}

		// 未开课.
		if (StuStatusEnum.NOSTARTCLASS.getValue() == currentstatus)
		{
			hsret.put(StuStatusEnum.LEAVESCHOLL.getValue(),
					StuStatusEnum.LEAVESCHOLL.getText());
		}
		// 在读
		if (StuStatusEnum.BESTUDY.getValue() == currentstatus)
		{
			hsret.put(StuStatusEnum.EXPEL.getValue(),
					StuStatusEnum.EXPEL.getText());
			hsret.put(StuStatusEnum.QUANTUI.getValue(),
					StuStatusEnum.QUANTUI.getText());
			hsret.put(StuStatusEnum.LEAVESCHOLL.getValue(),
					StuStatusEnum.LEAVESCHOLL.getText());
			hsret.put(StuStatusEnum.DELAYGRADUATE.getValue(),
					StuStatusEnum.DELAYGRADUATE.getText());
			hsret.put(StuStatusEnum.XIUXUE.getValue(),
					StuStatusEnum.XIUXUE.getText());

		}

		// 结业.
		if (StuStatusEnum.EndStudy.getValue() == currentstatus)
		{
//			hsret.put(StuStatusEnum.DELAYGRADUATE.getValue(),
//					StuStatusEnum.DELAYGRADUATE.getText());
//		
			hsret.put(StuStatusEnum.DELAYWORK.getValue(),
					StuStatusEnum.DELAYWORK.getText());
		}

//		// 求职中.
//		if (StuStatusEnum.FindJobing.getValue() == currentstatus)
//		{
////			hsret.put(StuStatusEnum.DELAYGRADUATE.getValue(),
////					StuStatusEnum.DELAYGRADUATE.getText());
////			hsret.put(StuStatusEnum.DELAYWORK.getValue(),
////					StuStatusEnum.DELAYWORK.getText());
//		}

		// 休学.
		if (StuStatusEnum.XIUXUE.getValue() == currentstatus)
		{
			hsret.put(StuStatusEnum.XIUXUEBack.getValue(),
					StuStatusEnum.XIUXUEBack.getText());
			hsret.put(StuStatusEnum.LEAVESCHOLL.getValue(),
					StuStatusEnum.LEAVESCHOLL.getText());
		}
		// 延期结业
		if (StuStatusEnum.DELAYGRADUATE.getValue() == currentstatus)
		{
			hsret.put(StuStatusEnum.EXPEL.getValue(),
					StuStatusEnum.EXPEL.getText());
			hsret.put(StuStatusEnum.QUANTUI.getValue(),
					StuStatusEnum.QUANTUI.getText());
			hsret.put(StuStatusEnum.LEAVESCHOLL.getValue(),
					StuStatusEnum.LEAVESCHOLL.getText());
			hsret.put(StuStatusEnum.FindJobing.getValue(),
					StuStatusEnum.FindJobing.getText());
		}

		// 延期就业
		if (StuStatusEnum.DELAYWORK.getValue() == currentstatus)
		{
			hsret.put(StuStatusEnum.FindJobing.getValue(),
					StuStatusEnum.FindJobing.getText());
		}

		return hsret;
	}

	/**
	 * 显示修改班级状态的窗口.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/status/showclassstatus.do")
	@Functions({68})
	public ModelAndView showClassStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		int classid = ServletRequestUtils
				.getIntParameter(request, "classid", 0);
		if (classid == 0)
		{
			return WebUtil.getModeAndView404("参数错误:无classid");
		}
		TLqclass lqclass = classservice.getlqClass(classid);
		if (lqclass == null)
		{
			return WebUtil.getModeAndView404("班级不存在");
		}

		/*
		 * 未开课 授课中 集训前结课 集训中 结业 就业中
		 */

		ClassStatusEnum[] arrlist = new ClassStatusEnum[] {
				ClassStatusEnum.NOCLASSES,
				ClassStatusEnum.INTHELECTURE,
				ClassStatusEnum.BEFORETHETRAININGSESSION,
				ClassStatusEnum.INTHETRAINING, 
				ClassStatusEnum.THEGRADUATION,
				ClassStatusEnum.INTHEEMPLOYMENT };
		
		//传递到jsp 中, 方便使用. 
		HashMap clmap = new HashMap();
		clmap.put("NOCLASSES", ClassStatusEnum.NOCLASSES.getValue());
		clmap.put("INTHELECTURE", ClassStatusEnum.INTHELECTURE.getValue());
		clmap.put("BEFORETHETRAININGSESSION", ClassStatusEnum.BEFORETHETRAININGSESSION.getValue());
		clmap.put("INTHETRAINING", ClassStatusEnum.INTHETRAINING.getValue());
		clmap.put("THEGRADUATION", ClassStatusEnum.THEGRADUATION.getValue());
		clmap.put("INTHEEMPLOYMENT", ClassStatusEnum.INTHEEMPLOYMENT.getValue());
		
		
		ArrayList alstatus = new ArrayList();
		// 计算班级的下一个状态.
		int enableindex = -1;
		int currentstatus=-1;
		
		for (int i = 0; i < arrlist.length; i++)
		{
			ClassStatusEnum en = arrlist[i];
			if (en.getValue() == lqclass.getStatus())
			{
				if (i + 1 < arrlist.length)
					enableindex = i + 1;
				break;
			}
		}

		//为了显示下拉列表, 传递到jsp页面.
		for (int i = 0; i < arrlist.length; i++)
		{
			ClassStatusEnum en = arrlist[i];
			HashMap mpstatus = new HashMap();
			mpstatus.put("value", en.getValue());
			mpstatus.put("text", en.getText());

			if (enableindex == i)
			{
				currentstatus=  en.getValue();
				mpstatus.put("enable", true);
			} else
				mpstatus.put("enable", false);

			alstatus.add(mpstatus);
		}
		
		HashMap hsret = new HashMap();
		hsret.put("statuslist", alstatus);
		hsret.put("classid", classid);
		hsret.put("nowstatus", currentstatus);
		hsret.put("ClassStatus", clmap);
		hsret.put("classname", lqclass.getClassName());
		
		
		
		
		return new ModelAndView("/WEB-INF/view/lqclass/updateclass_stauts.jsp",
				hsret);
	}

	/**
	 * 显示修改 学员状态的页面.
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/status/showstatus.do")
	@Functions({56,77})
	public ModelAndView showStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		int userid = ServletRequestUtils.getIntParameter(request, "studentid",
				0);
		if (userid == 0)
		{
			return WebUtil.getModeAndView404("参数错误:无userid");
		}
		Student sdt = service.getStudentByid(userid);
		if (sdt == null)
		{
			return WebUtil.getModeAndView404("找不到指定的学生信息.");
		}
		HashMap<Integer, String> hsstatus = getCanChangeStatusList(sdt
				.getStatus());
		ArrayList al = new ArrayList();
		int arr[] = new int[hsstatus.size()];
		int i = 0;
		for (Map.Entry<Integer, String> en : hsstatus.entrySet())
		{
			HashMap hm = new HashMap();
			hm.put("value", en.getKey());
			hm.put("text", en.getValue());
			al.add(hm);
			arr[i] = en.getKey();
			i++;
			//System.out.println("can status code:" + en.getKey());
		}

		String srealname = userservice.getRealNameById(userid);
		
		HashMap hsret = new HashMap();
		hsret.put("statuslist", al);
		hsret.put("statucodesarray", arr);
		hsret.put("statusjson", JSON.toJSONString(arr));

		hsret.put("statusname", WebUtil.getStudentStatusName(sdt.getStatus()));
		hsret.put("userid", userid);
		hsret.put("realname",srealname );

		
		if (sdt.getEndStudytime() != null)
			hsret.put(
					"endstudytime",
					CommonUtil.dateToDateStr("yyyy-MM-dd",
							sdt.getEndStudytime()));

		return new ModelAndView(
				"/WEB-INF/view/zsStu/change_student_status.jsp", hsret);

	}

	/**
	 * 
	 * 修改班级状态, 保存数据
	 * @param pp
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping("/status/postclassstatus.do")
	public void postclassstatus(@RequestParam HashMap<String, Object> pp,
			HttpServletRequest request, HttpServletResponse response)

	{
		WebUtil.printMap(pp);
		SessionUser sessuser = WebUtil.getLoginUser(request);
		int loginuserid = sessuser.getUserId();
		int classid = Integer.parseInt(pp.get("classid").toString());
		int newstatus = Integer.parseInt(pp.get("newstatus").toString());
		String remark = pp.get("remark").toString();
		Object ohappen = pp.get("happentime");
		
		Object ojobbegintime = pp.get("jobbegintime");
		
		// 发发生课时数量
		int finishcount = 0;
		Object ofinish = pp.get("finishcount");
		if (ofinish != null)
			finishcount = Integer.parseInt(ofinish.toString());

		Date hpdate = new Date();
		if (ohappen != null)
			hpdate = CommonUtil.dateStrToDate(ohappen.toString());
		
		HashMap extParam = new HashMap();
		if (ojobbegintime != null)
		{
			//与修改学员状态, 参数对应. 
			Date jobbegintime = CommonUtil.dateStrToDate(ojobbegintime.toString());
			extParam.put("otherdate", jobbegintime);
			extParam.put("finishcount", finishcount);
		}
		
		try
		{
			classservice.UpdateClassStatus(classid, newstatus, loginuserid,
					hpdate, finishcount, remark,extParam);
			WebUtil.sendJson(response, 200, "");

		} catch (Exception se)
		{
			WebUtil.sendJson(response, 201, "保存数据异常错误.");

		}

	}

	/**
	 * 修改学生状态, 保存数据.
	 * 
	 * @param pp
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/status/postshowstatus.do")
	@Functions({56,77})
	public void postshowStatus(@RequestParam HashMap<String, Object> pp,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		WebUtil.printMap(pp);
		SessionUser sessuser = WebUtil.getLoginUser(request);
		int loginuserid = sessuser.getUserId();
		int userid = Integer.parseInt(pp.get("userid").toString());
		int newstatus = Integer.parseInt(pp.get("newstatus").toString());
		String remark = pp.get("remark").toString();
		Object ohappen = pp.get("happentime");

		Object otherdate = pp.get("otherdate");
		HashMap omap = new HashMap();
		if (otherdate != null)
		{
			Date t = CommonUtil.dateStrToDate(otherdate.toString());
			omap.put("otherdate", t);
		}

		// 结业时间.
		Object endstudytime = pp.get("endstudytime");
		if (endstudytime != null)
		{
			Date t = CommonUtil.dateStrToDate(endstudytime.toString());
			omap.put("endstudytime", t);
		}

		// 发发生课时数量
		int finishcount = 0;
		Object ofinish = pp.get("finishcount");
		if (ofinish != null)
			finishcount = Integer.parseInt(ofinish.toString());
		omap.put("finishcount", finishcount);

		Date hpdate = new Date();
		if (ohappen != null)
			hpdate = CommonUtil.dateStrToDate(ohappen.toString());

		if (service.UpdateStudentStatus(userid, newstatus, hpdate, remark,
				omap, loginuserid))
			WebUtil.sendJson(response, 200, "");
		else
			WebUtil.sendJson(response, 201, "保存数据异常错误.");

	}
}

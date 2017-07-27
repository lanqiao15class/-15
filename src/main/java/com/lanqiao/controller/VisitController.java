package com.lanqiao.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.lanqiao.common.Functions;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.model.SysFunction;
import com.lanqiao.service.VisitService;
import com.lanqiao.util.CommonUtil;

@Controller
public class VisitController {

	private final static Logger log = LogManager.getLogger(VisitController.class);

	@Resource
	VisitService service;

	@RequestMapping("/visit/postaddvisit.do")
	@Functions({ 34, 37 })
	public ModelAndView postaddvisit(@RequestParam HashMap<String, Object> p, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SessionUser sessuser = WebUtil.getLoginUser(request);

		boolean b = service.InsertVisit(p, sessuser.getUserId());
		if (b) {
			WebUtil.sendJson(response, 200, "");

		} else
			WebUtil.sendJson(response, -1, "保存数据异常错误..");

		return null;
	}

	@RequestMapping("/visit/showaddvisit.do")
	@Functions({ 34, 37 })
	public ModelAndView showaddvisit(String userid, String visit_id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap model = new HashMap();
		if (CommonUtil.isNotNull(visit_id)) {
			//修改
			List<HashMap> manlist = service.findVisitManInfo(Integer.valueOf(visit_id));
			model.put("manlist", new Gson().toJson(manlist));
		} else if (CommonUtil.isNotNull(userid)) {
			//修改
			HashMap manlist = service.selectMemberInOneUser(Integer.valueOf(userid));
			model.put("manlist1", new Gson().toJson(manlist));

		}
		return new ModelAndView("/WEB-INF/view/visit/addvisit.jsp", model);
	}

	@RequestMapping("/visit/visitmain.do")
	@Functions({ 34, 37 })
	public ModelAndView visitmain(@RequestParam HashMap<String, Object> parammap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap model = new HashMap();
		SessionUser sessuser = WebUtil.getLoginUser(request);

		String searchtype = (String) parammap.get("searchtype");
		int pagesize = 20;
		int pageno = 1;
		String strpagesize = (String) parammap.get("pagesize");
		String strpageno = (String) parammap.get("pageno");
		String menuid = (String) parammap.get("menuid");
		if (menuid == null)
			menuid = "";

		System.out.println("menuid===" + menuid);

		if (strpagesize == null)
			parammap.put("pagesize", pagesize);
		else
			pagesize = Integer.parseInt(strpagesize);

		if (strpageno == null)
			parammap.put("pageno", pageno);
		else
			pageno = Integer.parseInt(strpageno);

		// 数据传递到页面
		model.putAll(parammap);

		//mysql limit 
		int offset = (pageno - 1) * pagesize;

		parammap.put("offset", offset);
		// 时间数据整理, 开始时间是一天的最小时间, 截止时间是一天的最大时间.
		WebUtil.printMap(parammap);
		Date d = CommonUtil.dateStrToDate_small((String) parammap.get("bvisit_time"));
		if (d != null)
			parammap.put("bvisit_time", d);

		d = CommonUtil.dateStrToDate_big((String) parammap.get("evisit_time"));
		if (d != null)
			parammap.put("evisit_time", d);

		d = CommonUtil.dateStrToDate_small((String) parammap.get("bcreatetime"));
		if (d != null)
			parammap.put("bcreatetime", d);

		d = CommonUtil.dateStrToDate_big((String) parammap.get("ecreatetime"));
		if (d != null)
			parammap.put("ecreatetime", d);
		//查看该用户权限
		List<SysFunction> sysfunctions = WebUtil.getLoginUser(request).getSysfunctions();
		boolean flag = false;
		for (SysFunction function : sysfunctions) {
			if (function.getFunctionid() == 100) {
				flag = true;
				break;
			}
		}
		if (flag) {

		} else {
			parammap.put("loginuserid", WebUtil.getLoginUser(request).getUserId());

		}
		List<HashMap> list = service.findVisitLog(parammap);
		model.put("searchtype", searchtype);
		model.put("datalist", list);
		int ncount = service.findVisitLogCount(parammap);
		model.put("recordcount", ncount);
		if ("m23".equals(menuid))
			model.put("menuname", "学员管理");
		else
			model.put("menuname", "意向学员管理");

		for (HashMap v : list) {
			HashSet sch = new HashSet();
			ArrayList alman = new ArrayList();
			List<HashMap> manlist = service.findVisitManInfo((Long) v.get("visit_id"));
			for (HashMap m : manlist) {
				sch.add(m.get("univ_name"));
			}
			v.put("manlist", manlist);
			v.put("school", sch);
			//判断创建时间三天内可以修改
			Object value = v.get("createtime");

			if (CommonUtil.isNotNull(value)) {
				Date date1 = (Date) value;

				int i = CommonUtil.compareDay(date1, new Date());
				if (i < 3 && i >= 0) {
					//判断追踪记录是否为本人操作
					Integer str = (Integer) v.get("creator_userid");
					if (str.intValue() == WebUtil.getLoginUser(request).getUserId().intValue()) {
						v.put("sign", 1);
					}

				}
			}
		}

		//Thread.sleep(2000);
		return new ModelAndView("/WEB-INF/view/visit/visitmain.jsp", model);
	}
}
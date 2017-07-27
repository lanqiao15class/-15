package com.lanqiao.controller.student;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lanqiao.common.MustLoginAndStudent;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;

@Controller
public class HomeController {
	@MustLoginAndStudent
	@RequestMapping("/student/home.do")
	public ModelAndView studenthome(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap pm = new HashMap();
		SessionUser suser = WebUtil.getLoginUser(request);
		if (suser == null) {
			return new ModelAndView("redirect:" + GlobalConstant.LoginURL);
		}
		String stypename = "";
		if (suser.getStudent() != null)
			stypename = "学生";
		else {
			stypename = WebUtil.getTeacherTypeName(suser.getTeacher().getTeachtype());
		}
		String realname = suser.getRealName();
		if (StringUtils.isEmpty(realname))
			realname = "无真实姓名";

		pm.put("identityname", stypename);
		pm.put("realname", realname);

		return new ModelAndView("/WEB-INF/view/student/index.jsp", pm);
	}

}

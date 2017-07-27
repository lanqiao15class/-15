package com.lanqiao.aop;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.service.LogService;

/**
 * 实现 controller 异常拦截，记录日志
 * @author chenbaoji
 *
 */

//@EnableWebMvc
@ControllerAdvice
public class ControllerExceptionInterceptor {
	private static final Logger logger = LogManager.getLogger(ControllerExceptionInterceptor.class);

	@Resource(name="logservice")
	LogService logservice ;
	
	@ExceptionHandler(Exception.class)
	public void signException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		ex.printStackTrace();
		//输出到日志
		logger.error("获取操作者地址" + request.getRequestURI().toString() + "      获取错误名称 " + ex.toString() + "    获取错误内容 "
				+ ex.getLocalizedMessage());
		try {
			//输出到数据库
			// 获取当前登陆者的userid . 
			int userid = 0;
			String requrl = "";
			String reqaddr = "";
			String olddata = "";
			String optype = "5";
			String content = ex.toString();
			SessionUser sessuser = WebUtil.getLoginUser(request);
			if (sessuser != null) {
				userid = sessuser.getUserId();
			//	System.out.println("userid                   " + userid);
			}

			reqaddr = request.getRemoteAddr();
			requrl = request.getRequestURI().toString();
			StringWriter strwriter = new StringWriter(1024);
			PrintWriter pout = new PrintWriter(strwriter);
			ex.printStackTrace(pout);
			olddata =  strwriter.toString();
			/*LogService logservice = SpringContextUtil.getBean("logservice");

			if (logservice != null) {

			} else {
				logger.error("logservice  is  null");
			}*/
			
			
			logger.info("添加");
			logger.info("reqaddr:" + reqaddr);
			logger.info("requrl:" + requrl);
			logger.info("userid:" + userid);
			logger.info("optype:" + optype);
			logger.info("olddata:" + olddata);
			logger.info("content:" + content);
			logger.info("-----------------------------------------------------");
			logservice.WriteLog(olddata, content, optype, userid, reqaddr, requrl);

		} catch (Exception se) {
			logger.error("添加数据库出错   " + se.getLocalizedMessage());
		}
	}
}

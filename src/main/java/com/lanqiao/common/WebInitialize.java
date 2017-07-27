package com.lanqiao.common;

/**
 *  应用启动时立即执行
 *  
 *  tomcat 启动后立即执行的代码, 放入 contextInitialized 方法中. 
 *  
 *  
 */
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.util.DynamicLoadingFileUtil;

/**
 * Application Lifecycle Listener implementation class WebInitialize
 *
 */
public class WebInitialize implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public WebInitialize() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent context)  { 
    	//载入静态页面的最后修改时间到内存. 
    	String staticpath = context.getServletContext().getRealPath("/") +"static/";
    	String staticurl = context.getServletContext().getContextPath() +"/static/";
    	DynamicLoadingFileUtil.initialize(staticpath, staticurl);
    	
    	System.out.println ("发布目录:"+context.getServletContext().getRealPath("/"));
    	ServletContext application = context.getServletContext(); 
//    	
//    	System.out.println ("httpUploadURL=="+GlobalConstant.httpUploadURL);
//    	//载入配置, 放入到 application中,  方便jsp 中调用.
//    	application.setAttribute("httpUploadURL", GlobalConstant.httpUploadURL);
//    	
    	
    	//FunctionEnum.getAllMap();
    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
      
    }
	
}

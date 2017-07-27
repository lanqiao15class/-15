package com.lanqiao.common;

/**
 * 
 * 
 * 此类实现跟业务相关的一些静态方法. 
 * 
 * 
 */
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.constant.GlobalConstant.ClassStatusEnum;
import com.lanqiao.constant.GlobalConstant.JOBWAY;
import com.lanqiao.constant.GlobalConstant.PayGoalEnum;
import com.lanqiao.constant.GlobalConstant.StuPaidEnum;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.constant.GlobalConstant.UserTypeEnum;
import com.lanqiao.constant.GlobalConstant.jobStatusEnum;
import com.lanqiao.constant.GlobalConstant.PayGoalEnum;;

public class WebUtil
{
	
	/**
	 * 得到报名费缴费状态名称. 
	 * @param payid
	 * @return
	 */
	public static String getSignFeeName(int payid)
	{
		String sret = "未知";

		for (StuPaidEnum one : StuPaidEnum.values())
		{
			
			if (one.getValue()==payid)
			{
				sret = one.getText();
				break;
			}
		}
		return sret;
	}
	/**
	 * 获取报名费/学费list for下拉
	 * @return
	 */
	public static List<Map<String,Object>> getPayDList(){
		List<Map<String,Object>> payList=new ArrayList<Map<String,Object>>();
		for(StuPaidEnum one:StuPaidEnum.values()){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("value", one.getValue());
			map.put("label", one.getText());
			payList.add(map);
		}
		return payList;
	}
	
	/**
	 * 根据id 得到实训费的付款状态名称
	 * @param payid
	 * @return
	 */
	public static String getPaydNameByid(int payid)
	{
		String sret = "未知";

		for (StuPaidEnum one : StuPaidEnum.values())
		{
			
			if (one.getValue()==payid)
			{
				sret = one.getText();
				break;
			}
		}
		return sret;
	}
	
	
	/**
	 * 根据状态码,得到状态名字.
	 * @param nstatus
	 * @return
	 */
	public static String getStudentStatusName(int nstatus)
	{
		String sret = "未知";

		for (StuStatusEnum one : StuStatusEnum.values())
		{
			
			if (one.getValue()==nstatus)
			{
				sret = one.getText();
				break;
			}
		}
		return sret;
	}
	
	/**
	 * 数组中是否含有指定的数值
	 * @param arr
	 * @param oneint
	 * @return
	 */
	public static boolean hasInArray(Object oarr,int onint )
	{
		boolean bret =false;
		int arr[] = (int[])oarr; 
		//System.out.println ("hasInArray +" + arr.length);
		if(arr == null) return bret;
		for(int a :arr)
		{
			if(a == onint)
			{
				bret =true;
				break;
			}
		}
		return bret;
	}
	
	
	/**
	 * 返回错误页面 modelandview . 
	 * @param errmsg : 错误信息.
	 * @return
	 */
	public static ModelAndView getModeAndView404(String errmsg)
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("errmsg", errmsg);
		mv.setViewName("/WEB-INF/view/error/404.jsp");
		return mv;
	}
	
	
	public static void main(String[] args)throws Exception 
	{
		
		byte bb[] = gzip("1111111111111111111111111111111111111111111111111111111");
		
		System.out.println (bb.length);
		
//		System.out.println(GlobalConstant.StuStatusEnum.AUDIT.getValue());
	}
	
	/**
	 * 打印调试信息, 所有的请求数据.
	 * @param request
	 */
	public static void printParameters(HttpServletRequest request)
	{
		Map<String,String[]> ps = request.getParameterMap();
		System.out.println ("=========================");
		for(Map.Entry<String, String[]> en : ps.entrySet())
		{
			String val ="";
			if(en.getValue() !=null && en.getValue().length >.0)
			{
				for(String s:en.getValue())
				{
					if(!"".equals(val))
						val+="\t";
					val +=s;
				}
				
			}
			System.out.println (en.getKey() +":" + val);
		}
		
		System.out.println ("=========================");
		
		
	}
	
	
	/**
	 * 发送错误码到浏览器
	 * @param response
	 * @param code
	 * @param msg
	 */
	public static  void sendJson(HttpServletResponse response,int code ,  String msg)
	{

		try
		{
			HashMap retmp = new HashMap();
			retmp.put("code", code);
			retmp.put("message", msg);

			response.setContentType("text/html;charset=UTF-8");
			String s = JSON.toJSONString(retmp);
			response.getOutputStream().write(s.getBytes("utf-8"));
			response.getOutputStream().flush();

		} catch (Exception se)
		{

		}

	}

	
	
	/**
	 * 打印hashmp 的数据 ,测试用.
	 * @param mp
	 */
	public static void printMap(HashMap mp)
	{
		Iterator iter = mp.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    Object key = entry.getKey(); 
		    Object val = entry.getValue(); 
		    System.out.println (key + ":" + val);
		} 
		
		
	}
	
	
	/**
	 * 判断浏览器是否支持gzip
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isSupportGzip(HttpServletRequest request)
	{

		boolean b = false;
		String strhead = request.getHeader("Accept-Encoding");
		if (strhead != null && strhead.indexOf("gzip") != -1)
		{
			b = true;
		}
		return b;

	}

	/**
	 * 压缩字符串为字节数组
	 * @param primStr
	 * @return
	 * @throws Exception
	 */
	public static byte[] gzip(String primStr) throws Exception
	{
		if (primStr == null || primStr.length() == 0)
		{
			return null;
		}

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			GZIPOutputStream gzip = null;
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes("utf-8"));
			gzip.close();
			return out.toByteArray();
	}

	/**
	 * 根据老师的身份ID 得到名字
	 * 
	 * @param typeid
	 * @return
	 */
	public static String getTeacherTypeName(Integer typeid)
	{
		String sret = "未知";

		for (UserTypeEnum one : UserTypeEnum.values())
		{
			if (one.getValue()==typeid)
			{
				sret = one.getText();
				break;
			}
		}
		return sret;
	}
	//遍历学员状态枚举，获取学员状态的list-for下拉列表-by罗玉琳
	public static List<Map<String,Object>> getStuStatusList(){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(StuStatusEnum one:StuStatusEnum.values()){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("value", one.getValue());
			map.put("label", one.getText());
			resultList.add(map);
		}
		return resultList;
	}
	
	//根据学员状态value,获取枚举的text--by罗玉琳
	public static String getStuStatusByValue(int value){
		String label="";
		for(StuStatusEnum one:StuStatusEnum.values()){
			if(one.getValue()==value){
				label=one.getText();	
			}
		}
		return label;
	}
	
	//遍历班级状态枚举，获取班级状态的list-for下拉列表-by罗玉琳
	public static List<Map<String,Object>> getClassStatusList(){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(ClassStatusEnum one:ClassStatusEnum.values()){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("value", one.getValue());
			map.put("label", one.getText());
			resultList.add(map);
		}
		return resultList;
	}
	
	//根据班级状态value,获取枚举的text--by罗玉琳
	public static String getClassStatusByValue(int value){
		String label="";
		for(ClassStatusEnum one:ClassStatusEnum.values()){
			if(one.getValue()==value){
				label=one.getText();
				break;
			}
		}
		return label;
	}
	

	/**
	 * 得到当前登录者的对象.
	 * 
	 * @param request
	 * @return
	 */
	public static SessionUser getLoginUser(HttpServletRequest request)
	{
		SessionUser ret = null;
		ret = (SessionUser) request.getSession().getAttribute("loginuser");
		return ret;
	}

	/**
	 * 是否登录了.
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isLogin(HttpServletRequest request)
	{
		SessionUser ret = null;
		ret = (SessionUser) request.getSession().getAttribute("loginuser");
		return ret == null ? false : true;
	}
	
	/**
	 * 
	* @Description:学员就业方式列表
	* @return 
	* @return List<Map<String,Object>> 
	* @author ZhouZhiHua
	* @createTime 2016年12月13日 下午4:37:44
	 */
	public static List<Map<String,Object>> getJobFromTypeList(){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(JOBWAY one:JOBWAY.values()){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("value", one.getValue());
			map.put("label", one.getText());
			resultList.add(map);
		}
		return resultList;
	}
	
	   /**
	    * 根据value获取学员就业方式label
	    * @param value
	    * @return
	    */
		public static String getJobFromTypeLabel(int value){
			String label="";
			for(JOBWAY one:JOBWAY.values()){
				if(one.getValue()==value){
					label=one.getText();
					break;
				}
			}
			return label;
		}
	
	/**
	 * 学员就业状态列表
	 * @return
	 */
	public static List<Map<String,Object>> getJobStatusList(){
		List<Map<String,Object>> jobStatusList=new ArrayList<Map<String,Object>>();
		for(jobStatusEnum one:jobStatusEnum.values()){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("value", one.getValue());
			map.put("label", one.getText());
			jobStatusList.add(map);
		}
		return jobStatusList;
	}
   /**
    * 根据value获取学员就业状态label
    * @param value
    * @return
    */
	public static String getJobStaLabel(int value){
		String label="";
		for(jobStatusEnum one:jobStatusEnum.values()){
			if(one.getValue()==value){
				label=one.getText();
				break;
			}
		}
		return label;
	}
	
	
	//遍历费用类型枚举，获取费用类型的list-for下拉列表
	public static List<Map<String,Object>> getCostTypeList(){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(PayGoalEnum one:PayGoalEnum.values()){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("value", one.getValue());
			map.put("label", one.getText());
			resultList.add(map);
		}
		return resultList;
	}
	
	/**
	 * 得到正式学员的状态ID , 传递到sql中.
	 * @return
	 */
	public static List<Integer> getFormalStuStatusId(){
		List<Integer> resultList=new ArrayList<Integer>();
		
		for(StuStatusEnum one:StuStatusEnum.values()){
			if(one.getValue() != StuStatusEnum.NOREGISTRATION.getValue()  && 
					one.getValue() != StuStatusEnum.AUDIT.getValue())
			resultList.add(one.getValue());
		}
		return resultList;
	}
	
	
	/**
	 * 正式学员, 剔除异常状态的学员.
	 * @return
	 */
	public static List<Integer> getNormalStuStatusId(){
		List<Integer> resultList=new ArrayList<Integer>();
		
		for(StuStatusEnum one:StuStatusEnum.values()){
			if(one.getValue() != StuStatusEnum.NOREGISTRATION.getValue()  
					&&  one.getValue() != StuStatusEnum.AUDIT.getValue()
					&& one.getValue() != StuStatusEnum.EXPEL.getValue()
					&& one.getValue() != StuStatusEnum.QUANTUI.getValue()
					&& one.getValue() != StuStatusEnum.LEAVESCHOLL.getValue()
					&& one.getValue() != StuStatusEnum.XIUXUE.getValue()
					&& one.getValue() != StuStatusEnum.DELAYGRADUATE.getValue()
					&& one.getValue() != StuStatusEnum.DELAYWORK.getValue()
					
					)
			resultList.add(one.getValue());
		}
		return resultList;
	}
}

package com.lanqiao.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lanqiao.common.WebUtil;
import com.lanqiao.util.CommonUtil;

@Service
public class ReportService extends BaseService
{
	public static void main(String[] args)
	{
		Date d = parseDate("2015-12",1);
		System.out.println (d.toLocaleString());
		
		 d = parseDate("2015-12",2);
		System.out.println (d.toLocaleString());
		
		
	}
	
	@Resource 
	DictService dictservice;
	
	public Map<String,Object> findOneUserFee(int userid)
	{
		 Map<String,Object> retlist =null;
		 retlist = (Map<String,Object> )dao.selectOne("com.lanqiao.dao.ReportExportExcel.selectsubFee", userid);
			
		 return retlist;
	}
	
	
	/**
	 * 某一学生首次入职的企业名称
	 * @param userid
	 * @return
	 */
	public String  findStudentFirstCompanyName(int userid)
	{
		 
		 String sname  = (String)dao.selectOne("com.lanqiao.dao.ReportExportExcel.export_studentCompanyName", userid);
			
		 return sname;
	}
	
	/**
	 * 减免原因
	 * @param userid
	 * @return
	 */
	public Map<String,Object>  findRemissReason(int userid)
	{
		 
		Map<String,Object> mp  = (Map<String,Object>)dao.selectOne("com.lanqiao.dao.ReportExportExcel.export_studentRemissReson", userid);
			
		 return mp;
	}
	
	/**
	 *  字符转成时间, 页面传递过来的是 yyyy-MM 格式, 需要确定月的开始时间, 月的截止时间.
	 * @param smonth
	 * @param mode
	 * @return
	 */
	public  static Date parseDate(String smonth, int mode)
	{
		if(smonth==null) return null;
		Date dret = null;
		String stemp = smonth +"-01";
		// mode =1  月开始时间. 
		if(mode ==1)
		{
			dret = CommonUtil.dateStrToDate_small(stemp);
		}
		if(mode ==2)
		{
			//月截止时间. 
			String stra[] = smonth.split("-");
			int nm = (Integer.parseInt(stra[1]) +1);
			int year = Integer.parseInt(stra[0]);
			
			if(nm >12)
			{
				nm =1;
				year++;
			}
			stemp = year + "-" + nm + "-01";
			dret = CommonUtil.dateStrToDate_small(stemp);
		}
		
		return dret;
		
	}
	
	/**
	 * 每月收入
	 * @param param
	 * @return
	 */
	public Map<String,Object> ReceivemonthData(HashMap param)
	{
		
		Map<String,Object> mp  = (Map<String,Object>)dao.selectOne("com.lanqiao.dao.ReportExportExcel.selectExportMonthData", param);
		return mp;
	}
	
	
	
	/**
	 * 历史收入累计
	 * @param param
	 * @return
	 */
	public Map<String,Object> ReceiveData_add(HashMap param)
	{
		Map<String,Object> mp  = (Map<String,Object>)dao.selectOne("com.lanqiao.dao.ReportExportExcel.selectExportMonthData_accu", param);
		return mp;
	}
	/**
	 * 退费数据
	 * @param param
	 * @return
	 */
	public Map<String,Object> RefundMonthData(HashMap param)
	{
		
		Map<String,Object> mp  = (Map<String,Object>)dao.selectOne("com.lanqiao.dao.ReportExportExcel.selectExportMonthData_back", param);
		return mp;
	}
	
	private String getPaytypeName(String typeid,  List<Map<String,Object>> list)
	{
		String sret ="";
		for(Map<String,Object> mp : list)
		{
			String key = mp.get("value").toString();
			if(key.equals(typeid))
			{
				sret =  mp.get("label").toString();
				break;
			}
		}
		return sret;
		
	}
	
	/**
	 * 查询数据,准备写入excel文件
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> findExcelDataList(HashMap param)throws Exception 
	{
		 Date monthbegin = parseDate(param.get("exportmonth").toString(),1);
		 Date monthend = parseDate(param.get("exportmonth").toString(),2);
		 // 得到支付方式的字典表. 
		 List<Map<String,Object>> hspaytype =  dictservice.getDictByType(DictService.DICT_PAY_TYPE);
		 
		 
		 
		 List<Map<String,Object>> retlist = null;
		 List<Integer> statuslist = WebUtil.getFormalStuStatusId();
		 param.put("statuslist", statuslist);
		 retlist = (List<Map<String,Object>> )dao.selectList("com.lanqiao.dao.ReportExportExcel.selectExportListForFile", param);
		 int i=1;
		 for(Map<String,Object> one :retlist)
		 {
			String s = WebUtil.getPaydNameByid((Integer)one.get("signStatus"));
			one.put("signStatus", s);
			
			s = WebUtil.getPaydNameByid((Integer)one.get("studyStatus"));
			one.put("studyStatus", s);
			
			one.put("indexNo", i);
			Integer userid = (Integer)one.get("user_id");
			// 学生所在 的班级. 
			 List<Map<String,Object>> classlog = getStudentClassLog( userid);
			 
			 for(int c1=0; c1 < classlog.size() && c1 < 4;c1++ )
			 {
				 Map<String,Object> oneclass = classlog.get(c1);
				 one.put("className"+(c1+1), oneclass.get("class_name"));
				 one.put("classEnterTime"+(c1+1), oneclass.get("create_time"));
				 one.put("classQuitTime"+(c1+1), oneclass.get("exit_time"));
			 }
			 
			 //就业服务期是否结束
			 Date jobendtime = (Date)one.get("jobServiceEndtime");
			 Date tnow = new Date();
			 if(jobendtime !=null && tnow.after(jobendtime) )
			 {
				 // 结束了.
				 one.put("isEndjobTime", "已结束");
				 one.put("jobMoney", "100");
				 
			 }else
			 {
				 one.put("isEndjobTime", "未结束");
				 one.put("jobMoney", "0");
			 }
			 //首次入职企业. 
			 String sname = findStudentFirstCompanyName(userid);
			 one.put("comanyName", sname);
			 
			 //减免原因
			  Map<String,Object>remission= findRemissReason( userid);
			  if(remission !=null)
				  one.putAll(remission);
			  
			  //单月数据计算.  报名费.
			  HashMap pp = new HashMap();
			  pp.put("monthbegin", monthbegin);
			  pp.put("monthend", monthend);
			  pp.put("user_id", userid);
			  pp.put("costtype", 1);
			  
			  Map<String , Object> omp = ReceivemonthData(pp);
			  if(omp !=null)
			  {
				  Object o = omp.get("month_money");
				  one.put("month_signpayMoney", o);
				  one.put("month_signpaytime", omp.get("month_paytime"));
				    
			  }
			  
			  omp = ReceiveData_add(pp);
			  if(omp !=null)
			  {
				  one.put("month_signAddMoney", omp.get("totalMoney"));
				    
			  }
			  
			  omp = RefundMonthData(pp);
			  if(omp !=null)
			  {
				  one.put("month_signbackMoney", omp.get("month_money"));
				  one.put("month_signbackTime", omp.get("month_remark"));
			  }
			  
			  //实训费
			  pp.put("costtype", 2);
			  omp = ReceivemonthData(pp);
				
			  if(omp !=null)
			  {
				  Object o = omp.get("month_money");
				  one.put("month_studypayMoney",o);
				  one.put("month_studypaytime", omp.get("month_paytime"));
			  }
			  
			  omp = ReceiveData_add(pp);
			  if(omp !=null)
			  {
				  one.put("month_studyAddMoney", omp.get("totalMoney"));
				    
			  }
			  
			  omp = RefundMonthData(pp);
			  if(omp !=null)
			  {
				  one.put("month_studybackMoney", omp.get("month_money"));
				  one.put("month_studybackTime", omp.get("month_remark"));
			  }
			  //studypaytype  
			   sname  =  getPaytypeName((one.get("pay_type")==null)?"": one.get("pay_type").toString(),hspaytype );
			   one.put("studypaytype", sname);
						 
			  // 打印测试. 
			//   WebUtil.printMap((HashMap)one);
			 //  System.out.println("====================================");
			i++;
		 }
		 return retlist;
		 
		
	}
	
	
	 /**
	  * 查询每个学生所在的班级记录.
	  * @param userid
	  * @return
	  */
	public List<Map<String,Object>> getStudentClassLog(Integer userid)
	{
		 List<Map<String,Object>> retlist = (List<Map<String,Object>> )dao.selectList("com.lanqiao.dao.ReportExportExcel.export_studentClassLog", userid);
		return  retlist;
	}

	
	
	
	public int  findTableListCount(HashMap param)
	{
		 List<Integer> statuslist = WebUtil.getFormalStuStatusId();
		 param.put("statuslist", statuslist);
		 
		 Integer nret = (Integer)dao.selectOne("com.lanqiao.dao.ReportExportExcel.selectExportList_count", param);
		
		 return nret;
	}
	
	/**
	 * 
	 * 查询表格数据. 
	 * @return
	 * 
	 */
	public List<Map<String,Object>> findTableList(HashMap param)
	{
		 List<Map<String,Object>> retlist =null;
		 List<Integer> statuslist = WebUtil.getFormalStuStatusId();
		 param.put("statuslist", statuslist);
		 
		 retlist = (List<Map<String,Object>> )dao.selectList("com.lanqiao.dao.ReportExportExcel.selectExportList", param);
		int i=1;
		 for(Map<String,Object> one :retlist)
		 {
			String s = WebUtil.getPaydNameByid((Integer)one.get("is_avaiable"));
			one.put("signStatus", s);
				
			Map<String,Object> fee = findOneUserFee((Integer)one.get("user_id"));
			if(fee !=null)
			{
				one.putAll(fee);
			
			}
			
			one.put("indexNo", i);
			i++;
			
		 }
		 return retlist;
	}
	
}

package com.lanqiao.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lanqiao.dao.TIncomeLogMapper;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.dao.StudentMapper;
import com.lanqiao.dao.TStudentReceivableLogMapper;
import com.lanqiao.dao.TStudentRefundLogMapper;
import com.lanqiao.dao.TStudentRemissionLogMapper;
import com.lanqiao.model.Student;
import com.lanqiao.model.TIncomeLog;
import com.lanqiao.model.TStudentReceivableLog;
import com.lanqiao.model.TStudentRefundLog;
import com.lanqiao.model.TStudentRemissionLog;
import com.lanqiao.util.CommonUtil;

@Service
public class IncomeService extends BaseService{
	
	/**
	 * 
	 *@description:Ajax获取费用表数据
	 *@param ids[学员ids]
	 *@return
	 *@return Map<String,Object> 
	 *@author:ZhuDiaoHua
	 *@2016年12月13日上午11:31:05
	 *
	 */
	public Map<String,Object> getTableData(String ids,int feeType) throws Exception{
		Map<String,Object> returnMap = new HashMap<String, Object>();
		
		//传过来的IDS为null(获取对应学员ids失败)
		if(ids == null || ids.trim().equals("")){
			return null;
		}
		
		List<Map<String,Object>> listReturn = new ArrayList<>();
		List<Integer> list = new ArrayList<>();//存放学员ids
		String idArray[] = ids.split(",");
		returnMap.put("studentCount", idArray.length);//学员人数
		
		for (String string : idArray) {
			list.add(Integer.parseInt(string));
		}
		Map<String,Object> mapTemp = new HashMap<>();
		mapTemp.put("list", list);
		mapTemp.put("payGoal", feeType);
		TIncomeLogMapper tIncomeLogMapper = dao.getMapper(TIncomeLogMapper.class);
		listReturn = tIncomeLogMapper.getTableData(mapTemp);//获取表格数据,报名费或者学费
		int index = 1;
		for (Map<String,Object> map : listReturn) {
			map.put("indexno",index++);
			map.put("status", WebUtil.getStuStatusByValue((Integer)map.get("status")));
		}
		
		returnMap.put("dataList", listReturn);
		return returnMap;
	}
	//我管理的学费回款列表(正式学员)
	public List<Map<String,Object>> getMyManageStuFeeList(Map<String,Object> paramMap){
		TIncomeLogMapper incomeLogMapper=dao.getMapper(TIncomeLogMapper.class);
		return incomeLogMapper.getMyManageStuFeeList(paramMap);
	}
	
	//我管理的学费回款记录数(正式学员)
	public int getMyManageStuFeeCount(Map<String,Object> paramMap){
		TIncomeLogMapper incomeLogMapper=dao.getMapper(TIncomeLogMapper.class);	
		return incomeLogMapper.getMyManageStuFeeCount(paramMap);
	}
	
	//全部的学费回款列表(正式学员)
	public List<Map<String,Object>> getAllStuFeeList(Map<String,Object> paramMap){
		TIncomeLogMapper incomeLogMapper=dao.getMapper(TIncomeLogMapper.class);
		return incomeLogMapper.getAllStuFeeList(paramMap);
	}
	//全部的学费回款记录数(正式学员)
	public int getAllStuFeeCount(Map<String,Object> paramMap){
		TIncomeLogMapper incomeLogMapper=dao.getMapper(TIncomeLogMapper.class);	
		return incomeLogMapper.getAllStuFeeCount(paramMap);
	}
	

	/**
	 * 
	 *@description:回款业务
	 *@param log
	 *@param map
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月13日上午10:59:46
	 *
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void incomeReceivable(TStudentReceivableLog log,Map<String,Object> map) throws Exception{
		
		Date happenTime = CommonUtil.dateStrToDate((String)map.get("happenTime"));//缴费时间
		log.setCreateDate(happenTime);
		int payGoal = log.getCostType();//获取费用类型
		
		String ids = (String)map.get("ids");
		if(ids == null || ids.trim().equals("")){
			return;
		}
		String idsArray[] = ids.split(",");
		TStudentReceivableLogMapper receivableLogMapper = dao.getMapper(TStudentReceivableLogMapper.class);
		TIncomeLogMapper incomeLogMapper = dao.getMapper(TIncomeLogMapper.class);
		for (String string : idsArray) {
			int stuId = Integer.parseInt(string);

			//1，插入学员回款记录表
			log.setReceivableUserid(stuId);
			receivableLogMapper.insertSelective(log);
			
			//2,修改统计表incomeLog
			Map<String,Object> mapTemp = new HashMap<>();
			mapTemp.put("stuId", stuId);mapTemp.put("payGoal", payGoal);
			TIncomeLog tIncomeLog = incomeLogMapper.getIncomeLogByUserIdAndType(mapTemp);
			
			Long payedMoney = tIncomeLog.getCurrentPayMoney()+log.getReceivableMoney();//回款金额
			tIncomeLog.setCurrentPayMoney(payedMoney);//当前已付
			tIncomeLog.setLastPayTime(happenTime);//付款时间
			tIncomeLog.setPayType(log.getPayWay());//更新支付方式（最后一次支付方式）
			incomeLogMapper.updateByPrimaryKeySelective(tIncomeLog);
			
			//更新学员交费状态
			StudentMapper sMapper = dao.getMapper(StudentMapper.class);
			int payStatus = CommonUtil.getFeeStatus(tIncomeLog, "HUIKUAN");//获取交费状态
			Student student = sMapper.selectByPrimaryKey(stuId);
			student.setUpdateTime(new Date());
			if(payGoal == 1){//报名费
				if(student.getIsAvaiable() != payStatus){//状态不同才更新
					student.setIsAvaiable(payStatus);
					sMapper.updateByPrimaryKeySelective(student);
				}
			}else {//2，学费
				if(student.getHasPaid() != payStatus){//状态不同才更新
					student.setHasPaid(payStatus);
					sMapper.updateByPrimaryKeySelective(student);
				}
			}
			
		}//学员循环
		
	}
	/**
	 * 
	 *@description:退费业务
	 *@param log
	 *@param map
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月13日上午11:19:16
	 *
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void incomeRefund(TStudentRefundLog log,Map<String,Object> map) throws Exception{
		
		Date happenTime = CommonUtil.dateStrToDate((String)map.get("happenTime"));//缴费时间
		log.setCreateDate(happenTime);
		int payGoal = log.getCostType();//获取费用类型
		
		String ids = (String)map.get("ids");
		if(ids == null || ids.trim().equals("")){
			return;
		}
		String idsArray[] = ids.split(",");
		TStudentRefundLogMapper refundLogMapper = dao.getMapper(TStudentRefundLogMapper.class);
		TIncomeLogMapper incomeLogMapper = dao.getMapper(TIncomeLogMapper.class);
		for (String string : idsArray) {
			int stuId = Integer.parseInt(string);

			//1，插入学员退款记录表
			log.setRefundUserid(stuId);
			refundLogMapper.insertSelective(log);
			
			//2,修改统计表incomeLog
			Map<String,Object> mapTemp = new HashMap<>();
			mapTemp.put("stuId", stuId);mapTemp.put("payGoal", payGoal);
			TIncomeLog tIncomeLog = incomeLogMapper.getIncomeLogByUserIdAndType(mapTemp);
			Long backMoney = tIncomeLog.getBackMoney() + log.getRefundMoney();//退款金额
			tIncomeLog.setBackMoney(backMoney);
			tIncomeLog.setLastBackTime(happenTime);
			incomeLogMapper.updateByPrimaryKeySelective(tIncomeLog);
			
			//更新学员交费状态
			StudentMapper sMapper = dao.getMapper(StudentMapper.class);
			int payStatus = CommonUtil.getFeeStatus(tIncomeLog, "TUIFEI");//获取交费状态
			Student student = sMapper.selectByPrimaryKey(stuId);
			student.setUpdateTime(new Date());
			if(payGoal == 1){//报名费
				if(student.getIsAvaiable() != payStatus){//状态不同才更新
					student.setIsAvaiable(payStatus);
					sMapper.updateByPrimaryKeySelective(student);
				}
			}else {//2，学费
				if(student.getHasPaid() != payStatus){//状态不同才更新
					student.setHasPaid(payStatus);
					sMapper.updateByPrimaryKeySelective(student);
				}
			}
			
		}//学员循环
		
		
		
	}
	
	/**
	 * 
	 *@description:减免业务
	 *@param log
	 *@param map
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月13日上午11:30:32
	 *
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void incomeRemission(TStudentRemissionLog log,Map<String,Object> map) throws Exception{
		
		int payGoal = log.getCostType();//获取费用类型
		
		String ids = (String)map.get("ids");
		if(ids == null || ids.trim().equals("")){
			return;
		}
		String idsArray[] = ids.split(",");
		TStudentRemissionLogMapper remissionLogMapper = dao.getMapper(TStudentRemissionLogMapper.class);
		TIncomeLogMapper incomeLogMapper = dao.getMapper(TIncomeLogMapper.class);
		for (String string : idsArray) {
			int stuId = Integer.parseInt(string);

			//1，插入学员退款记录表
			log.setRemissionUserid(stuId);
			remissionLogMapper.insertSelective(log);
			
			//2,修改统计表incomeLog
			Map<String,Object> mapTemp = new HashMap<>();
			mapTemp.put("stuId", stuId);mapTemp.put("payGoal", payGoal);
			TIncomeLog tIncomeLog = incomeLogMapper.getIncomeLogByUserIdAndType(mapTemp);
			Long remissionMoney = tIncomeLog.getFavourMoney() + log.getReduceMoney();//减免金额
			tIncomeLog.setFavourMoney(remissionMoney);
			incomeLogMapper.updateByPrimaryKeySelective(tIncomeLog);
			
			//更新学员交费状态
			StudentMapper sMapper = dao.getMapper(StudentMapper.class);
			int payStatus = CommonUtil.getFeeStatus(tIncomeLog, "JIANMIAN");//获取交费状态
			Student student = sMapper.selectByPrimaryKey(stuId);
			student.setUpdateTime(new Date());
			if(payGoal == 1){//报名费
				if(student.getIsAvaiable() != payStatus){//状态不同才更新
					student.setIsAvaiable(payStatus);
					sMapper.updateByPrimaryKeySelective(student);
				}
			}else {//2，学费
				if(student.getHasPaid() != payStatus){//状态不同才更新
					student.setHasPaid(payStatus);
					sMapper.updateByPrimaryKeySelective(student);
				}
			}
			
		}//学员循环
		
		
	}
	
	/** 报名费和学费累计 */
	public List<Map<String,Object>> getIncomeLogBystuId(int stuId,int payGoal){
		Map<String,Object> mapTemp = new HashMap<>();
		mapTemp.put("stuId", stuId);
		mapTemp.put("payGoal", payGoal);
		return (List<Map<String,Object>>)dao.selectList("com.lanqiao.dao.TIncomeLogMapper.getIncomeLogBystuId", mapTemp);
	}
	/** 报名费和学费历史记录  */
	public List<Map<String,Object>> getIncomeListBystuId(int stuId,int payGoal){
		Map<String,Object> mapTemp = new HashMap<>();
		mapTemp.put("stuId", stuId);
		mapTemp.put("payGoal", payGoal);
		return (List<Map<String,Object>>)dao.selectList("com.lanqiao.dao.TIncomeLogMapper.getIncomeListBystuId", mapTemp);
	}
	
	/** 获取可缴费的最大金额、可减免的最大金额 */
	public Long getMaxInMoney(Map<String,Object> map){
		TIncomeLogMapper tLogMapper = dao.getMapper(TIncomeLogMapper.class);
		return tLogMapper.getMaxInMoney(map);
	}
	
	/** 获取可退费的最大金额 */
	public Long getMaxOutMoney(Map<String,Object> map){
		TIncomeLogMapper tLogMapper = dao.getMapper(TIncomeLogMapper.class);
		return tLogMapper.getMaxOutMoney(map);
	}
	
}

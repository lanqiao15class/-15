package com.lanqiao.controller;

import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.lanqiao.common.Functions;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.ClassStatusEnum;
import com.lanqiao.constant.GlobalConstant.StuPaidEnum;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.model.TStudentReceivableLog;
import com.lanqiao.model.TStudentRefundLog;
import com.lanqiao.model.TStudentRemissionLog;
import com.lanqiao.service.DictService;
import com.lanqiao.service.IncomeService;
import com.lanqiao.service.StudentService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.JsonUtil;

@Controller
@RequestMapping("/income")
public class IncomeController {
	
	private static final Logger logger = LogManager.getLogger(IncomeController.class);
	
	@Resource
	private IncomeService incomeService;
	
	@Resource
	private DictService dictService; 
	
	@Resource
	private StudentService studentService;
	
	
	/**
	 * 
	 *@description:跳转到费用业务界面【回款、退费、减免】
	 *@param ids
	 *@param opType【列表】
	 *@param serviceType【费用服务类型】
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月13日下午12:56:04
	 *
	 */
	@RequestMapping("/{opType}/{serviceType}/toFeeServicePage.do")
	public ModelAndView toFeeServicePage(String ids,@PathVariable(value="opType")String opType,@PathVariable(value="serviceType")String serviceType,HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		mv.addObject("opType", opType);
		mv.addObject("serviceType", serviceType);
		mv.addObject("ids", ids);
		mv.addObject("payGoalList", WebUtil.getCostTypeList());
		try {
			mv.addObject("payTypeList", dictService.getDictByType(DictService.DICT_PAY_TYPE));
		} catch (Exception e) {
			WebUtil.getModeAndView404("获取支付状态失败");
		}
		mv.setViewName("/WEB-INF/view/charge/fee_service_page.jsp");
		return mv; 
	}
	
	/**
	 * 去学费回款列表页面
	 * @param mv
	 * @return
	 */
	@RequestMapping("/{opType}/goStuFeeList.do")
	@Functions({61,62})
	public ModelAndView goStuFeeList(ModelAndView mv,@PathVariable(value="opType")String opType){

		StuPaidEnum[] isAvaiableEnum= new StuPaidEnum[] {
				StuPaidEnum.PARTOFTHEPAY,
				StuPaidEnum.ALLPAY,
				StuPaidEnum.SPECIALPAY,
				StuPaidEnum.PartReturn, 
				StuPaidEnum.AllReturn };
		
		StuPaidEnum[] hasPaidEnum= new StuPaidEnum[] {
				StuPaidEnum.NOTPAY,
				StuPaidEnum.PARTOFTHEPAY,
				StuPaidEnum.ALLPAY,
				StuPaidEnum.PartReturn, 
				StuPaidEnum.AllReturn };
		
		StuStatusEnum[] stuStatusEnum= new StuStatusEnum[] {
				StuStatusEnum.NOCLASS,
				StuStatusEnum.NOSTARTCLASS,
				StuStatusEnum.BESTUDY,
				StuStatusEnum.EndStudy, 
				StuStatusEnum.FindJobing,
				StuStatusEnum.EXPEL,
				StuStatusEnum.QUANTUI,
				StuStatusEnum.LEAVESCHOLL,
				StuStatusEnum.XIUXUE,
				StuStatusEnum.XIUXUEBack,
				StuStatusEnum.DELAYGRADUATE,
				StuStatusEnum.DELAYWORK,
				StuStatusEnum.WORKED};
		List<Map<String,Object>> isAvaiableList=new ArrayList<Map<String,Object>>();
		
         for(int i=0;i<isAvaiableEnum.length;i++){
        	 Map<String,Object> map=new HashMap<String,Object>();
        	 map.put("value", isAvaiableEnum[i].getValue());
        	 map.put("label", isAvaiableEnum[i].getText());
        	 isAvaiableList.add(map);
         }
		List<Map<String,Object>> hasPaidList=new ArrayList<Map<String,Object>>();
		for(int j=0;j<hasPaidEnum.length;j++){
			 Map<String,Object> map=new HashMap<String,Object>();
	       	 map.put("value", hasPaidEnum[j].getValue());
	       	 map.put("label", hasPaidEnum[j].getText());
	       	hasPaidList.add(map);
		}
		List<Map<String,Object>> stuStatusList=new ArrayList<Map<String,Object>>();
		for(int k=0;k<stuStatusEnum.length;k++){
			 Map<String,Object> map=new HashMap<String,Object>();
	       	 map.put("value", stuStatusEnum[k].getValue());
	       	 map.put("label", stuStatusEnum[k].getText());
	       	 stuStatusList.add(map); 
		}
		mv.addObject("isAvaiableList",isAvaiableList);//报名费list
		mv.addObject("hasPaidList", hasPaidList);//学费list
		mv.addObject("stuStatusList", stuStatusList);//学员状态list
		mv.addObject("opType", opType);
		mv.setViewName("/WEB-INF/view/charge/stuFeeList.jsp");
		return mv;
	}
	/**
	 * 获取学员回款列表表格数据
	 */
	@RequestMapping("/getStuFeeList.do")
	@Functions({61,62})
	public void getStuFeeList(HttpServletRequest request,HttpServletResponse response,@RequestParam String opType){
		        // 打印调试信息. 
				WebUtil.printParameters(request);
				
				Map<String,Object> paramMap=new HashMap<String,Object>();
				/**====================设置查询参数开始=====================**/
				try {
					if(request.getParameter("isAvaiable_lq")!=null && !"".equals(request.getParameter("isAvaiable_lq"))){
						paramMap.put("isAvaiable_lq", Integer.parseInt(request.getParameter("isAvaiable_lq")));
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				try {
					if(request.getParameter("hasPaid_lq")!=null && !"".equals(request.getParameter("hasPaid_lq"))){
						paramMap.put("hasPaid_lq", Integer.parseInt(request.getParameter("hasPaid_lq")));
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
				try {
					if(request.getParameter("select_school_id")!=null && !"".equals(request.getParameter("select_school_id"))){
						paramMap.put("select_school_id", Integer.parseInt(request.getParameter("select_school_id")));
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
				try {
					if(request.getParameter("stuStatus_lq")!=null && !"".equals(request.getParameter("stuStatus_lq"))){
						paramMap.put("stuStatus_lq", Integer.parseInt(request.getParameter("stuStatus_lq")));
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
					if(request.getParameter("course_advisor_useid")!=null && !"".equals(request.getParameter("course_advisor_useid"))){
						paramMap.put("course_advisor_useid", Integer.parseInt(request.getParameter("course_advisor_useid")));
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
				if(request.getParameter("student_name_lq")!=null && !"".equals(request.getParameter("student_name_lq"))){
					paramMap.put("student_name_lq", request.getParameter("student_name_lq"));
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
				
				paramMap.put("teaUserId", userId);//设置老师的userId以查找我管理的学员
				//设置学员状态查询参数
				StringBuffer str=new StringBuffer().append(GlobalConstant.StuStatusEnum.NOCLASS.getValue()).append(",");//未分班
				str.append(GlobalConstant.StuStatusEnum.NOSTARTCLASS.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.BESTUDY.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.EndStudy.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.FindJobing.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.EXPEL.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.QUANTUI.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.LEAVESCHOLL.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.XIUXUE.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.XIUXUEBack.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.DELAYGRADUATE.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.DELAYWORK.getValue()).append(",");
				str.append(GlobalConstant.StuStatusEnum.WORKED.getValue());
				String stuStatusParam=str.toString();
				paramMap.put("stuStatusParam", stuStatusParam);//学员状态
				paramMap.put("auditStatusParam", GlobalConstant.auditStatusEnum.PASS.getValue());//学员审核状态:通过
				List<Map<String,Object>> stuFeeList=new ArrayList<Map<String,Object>>();
               if(opType.equals("1")){
					//.获取全部正式学员回款列表
					stuFeeList=incomeService.getAllStuFeeList(paramMap);
					//1把记录数放session
					if(request.getParameter("currpage")!=null && !"".equals(request.getParameter("currpage")) && request.getParameter("currpage").equals("1")){
						int ncount =incomeService.getAllStuFeeCount(paramMap);
						hsret.put("recordcount", ncount);//全部正式学员总数(包含退学，开除，劝退)
					}
				}else{
					//.获取我管理的正式学员回款列表
					stuFeeList=incomeService.getMyManageStuFeeList(paramMap);
					//1把记录数放session
					if(request.getParameter("currpage")!=null && !"".equals(request.getParameter("currpage")) && request.getParameter("currpage").equals("1")){
						int ncount =incomeService.getMyManageStuFeeCount(paramMap);
						hsret.put("recordcount", ncount);//我管理的正式学员总数(包换退学，开除，劝退)
					}
				}
				/**============统计开除/劝退/退学学员人数==================**/
				paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.EXPEL.getValue());//设置学员状态为开除
				int expelCount=studentService.getStuCount(paramMap);//查询今年全部正式学员中被开除的记录数
				hsret.put("expelCount", expelCount);
				paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.QUANTUI.getValue());//设置学员状态为劝退
				int quanTuiCount=studentService.getStuCount(paramMap);//查询今年全部正式学员中被劝退的记录数
				hsret.put("quanTuiCount", quanTuiCount);
				paramMap.put("stuStatus", GlobalConstant.StuStatusEnum.LEAVESCHOLL.getValue());//设置学员状态为退学
				int leaveSchollCount=studentService.getStuCount(paramMap);//查询今年全部正式学员中被退学的记录数
				hsret.put("leaveSchollCount", leaveSchollCount);
				/**============统计开除/劝退/退学学员人数==================**/
				
				hsret.put("datalist", stuFeeList);
		     	SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd");
		     	String formateDate="";
				int i=0;//计数
			    for(Map<String,Object> map:stuFeeList)
			    {

			    	formateDate=formate.format(map.get("updateTime"));
			    	map.put("updateTime",formateDate);
			    	int temp=i+1;
		        	map.put("option", "");
		        	map.put("nstatus", 1);//设置是否显示表格里的图标(不为1，亦表头不显示任何图标)
		        	map.put("stuStatus", WebUtil.getStuStatusByValue(Integer.parseInt(map.get("status").toString())));//获取学员状态的label值
		        	//设置将报名费value转换成文字的参数
		            if(map.get("isAvaiable")!=null){
			        	map.put("isAvaiable", WebUtil.getSignFeeName(Integer.parseInt(map.get("isAvaiable").toString())));//isAvaiable文字显示
		        	}
		        	//设置将学费value转换成文字的参数
                    if(map.get("hasPaid")!=null){
                    	map.put("hasPaid", WebUtil.getSignFeeName(Integer.parseInt(map.get("hasPaid").toString())));//hasPaid文字显示
                    }
		        	map.put("indexno", temp);
		        	i++;
			    }
			    String strout =  JSON.toJSONString(hsret);
		        JsonUtil.write(response, strout);
	}
		
	
	/**
	 * 
	 *@description: ajax获取费用业务学员表格信息
	 *@param response
	 *@param ids
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月13日下午2:20:54
	 *
	 */
	@RequestMapping("/getFeeServiceData.do")
	public void getFeeServiceData(HttpServletResponse response,String ids,int feeType){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		try {
			Map<String,Object> map = incomeService.getTableData(ids,feeType);
			if(map == null){
				returnMap.put("code", 201);//学员列表信息获取失败
			}else{
				returnMap = map;
				returnMap.put("code", 200);
			}
		} catch (Exception e) {
			returnMap.put("code", 202);//系统异常
			e.printStackTrace();
		}
		
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}
	
	/**
	 * 
	 *@description:执行费用业务处理
	 *@param map
	 *@param response
	 *@param request
	 *@return void 
	 *@author:ZhuDiaoHua
	 *@2016年12月14日下午6:08:51
	 *
	 */
	@RequestMapping("/doFeeService.do")
	public void doFeeService(@RequestParam Map<String,Object> map,HttpServletResponse response,HttpServletRequest request){
		 Map<String,Object> returnMap = new HashMap<>();
		 SessionUser sUser = WebUtil.getLoginUser(request);
		 String serviceType = (String) map.get("serviceType");
		 Date happenTime = CommonUtil.dateStrToDate((String)map.get("happenTime"));//发生时间格式转换
		 //输入的金额的合理性【缴费不超、减免不超、退款不超】
		 Map<String,Object> mapTemp = new HashMap<String,Object>();
		 mapTemp.put("list", ((String)map.get("ids")).split(","));
		 mapTemp.put("payGoal", Integer.parseInt((String)map.get("feeType")));
		 
		 if(serviceType != null){
			 switch (serviceType) {
				case "JIANMIAN":
					if(Long.parseLong((String)map.get("moneyChange")) > incomeService.getMaxInMoney(mapTemp)){
						returnMap.put("code", 400);//减免金额大于剩余未缴金额
						returnMap.put("message","存在学员的减免金额大于剩余未缴金额，请排除该学员或者更改减免金额");
					}else{
						TStudentRemissionLog log = new TStudentRemissionLog();
						log.setApproralUserid(Integer.parseInt((String)map.get("zzx_teacherSPR")));//审批人
						log.setCostType(Integer.parseInt((String)map.get("feeType")));//费用类型
						log.setCreateDate(happenTime);//发生时间
						log.setOperUserid(sUser.getUserId());//记录人
						log.setReduceMoney(Long.parseLong((String)map.get("moneyChange")));//减免金额
						log.setRemarks((String)map.get("remark"));//备注
						log.setInputtime(new Date());//记录时间
//						log.setRemissionUserid(remissionUserid);//学员id
//						log.setStudentRemissionLogId(studentRemissionLogId);//记录id
						try {
							incomeService.incomeRemission(log, map);
							returnMap.put("code", 200);//成功
						} catch (Exception e) {
							returnMap.put("code", 201);//处理金额异常
						}
						
					}
					break;
				case "TUIFEI":
					if(Long.parseLong((String)map.get("moneyChange")) > incomeService.getMaxOutMoney(mapTemp)){
						returnMap.put("code", 400);//退费金额大于已缴金额
						returnMap.put("message","存在学员的退费金额大于剩余未退金额，请排除该学员或者更改退费金额");
					}else{
						TStudentRefundLog log2 = new TStudentRefundLog();
						log2.setAgencyUserid(Integer.parseInt((String)map.get("zzx_teacherJBR")));//经办人
						log2.setApprovalUserid(Integer.parseInt((String)map.get("zzx_teacherSPR")));//审批人
						log2.setCostType(Integer.parseInt((String)map.get("feeType")));//费用类型
						log2.setCreateDate(happenTime);//发生时间
						log2.setOperUserid(sUser.getUserId());//记录人
						log2.setRefundMoney(Long.parseLong((String)map.get("moneyChange")));//退费金额
	//					log2.setRefundUserid(refundUserid);//学员id
						log2.setRemarks((String)map.get("remark"));//说明
						log2.setInputtime(new Date());//记录时间
	//					log2.setStudentRefundLogId(studentRefundLogId);//记录id
						try {
							incomeService.incomeRefund(log2, map);
							returnMap.put("code", 200);//成功
						} catch (Exception e) {
							returnMap.put("code", 201);//处理金额异常
						}
					}
					break;
				case "HUIKUAN":
					if(Long.parseLong((String)map.get("moneyChange")) > incomeService.getMaxInMoney(mapTemp)){
						returnMap.put("code", 400);//回款金额大于剩余未缴金额
						returnMap.put("message","存在学员的回款金额大于剩余未缴金额，请排除该学员或者更改回款金额");
					}else{
						TStudentReceivableLog log3 = new TStudentReceivableLog();
						log3.setAgencyUserid(Integer.parseInt((String)map.get("zzx_teacherJBR")));//经办人
						log3.setCostType(Integer.parseInt((String)map.get("feeType")));//费用类型
						log3.setCreateDate(happenTime);//发生时间
						log3.setOperUserid(sUser.getUserId());//记录人
						log3.setPayWay(Integer.parseInt((String)map.get("payWay")));//支付方式
						log3.setReceivableMoney(Long.parseLong((String)map.get("moneyChange")));//缴纳金额
	//					log3.setReceivableUserid(receivableUserid);//回款学员id
						log3.setRemarks((String)map.get("remark"));//说明
						log3.setInputtime(new Date());//记录时间
	//					log3.setStudentReceivableLogId(studentReceivableLogId);//记录id
						try {
							incomeService.incomeReceivable(log3, map);
							returnMap.put("code", 200);//成功
						} catch (Exception e) {
							returnMap.put("code", 201);//处理金额异常
						}
					}
					break;
	
				default:
					 returnMap.put("code", 300);//费用服务类型获取失败
					break;
				}
		 }else{
			 returnMap.put("code", 300);//费用服务类型获取失败
		 }
		JsonUtil.write(response, JSON.toJSONString(returnMap));
	}
	
	
	
	
	
	
	
	
	
	
	
}

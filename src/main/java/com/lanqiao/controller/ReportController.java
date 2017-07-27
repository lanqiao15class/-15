package com.lanqiao.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lanqiao.common.Functions;
import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.model.StuExportBean;
import com.lanqiao.service.ReportService;
import com.lanqiao.service.StudentService;
import com.lanqiao.util.CommonUtil;
import com.lanqiao.util.ExcelColumn;
import com.lanqiao.util.LqExcelFinance;
import com.lanqiao.util.LqExcelUtil;

/**
 * @author chenbaoji 
 * @date 2016年10月31日 上午11:07:22
 * @desc 报表导出
 */
@Controller
@RequestMapping("/report")
public class ReportController {

	@Resource
	private ReportService service;

	@Resource
	private StudentService studentService;

	@RequestMapping("/stuUnnormalExport.do")
	@Functions({ 57 })
	public void stuUnnormalExport(HttpServletRequest request, HttpServletResponse response) {
		try {
			OutputStream out = response.getOutputStream();
			Date curDate = new Date();
			String format = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String fileName = "学生信息表_" + sdf.format(curDate) + ".xls";

			String[] columnKeys = { "no", "stuNo", "stuName", "idCard", "univName", "inspectorTeaName", "invTeaName",
					"advisorTeaName", "entryFeePayStatus", "entryFeeFactMoney", "entryFeeCurPayMoney",
					"entryFeeFavourMoney", "payTime", "payeeUserName" };

			ExcelColumn[] headers = new ExcelColumn[14];
			headers[0] = new ExcelColumn("序号", 5);
			headers[1] = new ExcelColumn("学员编码", 20);
			headers[2] = new ExcelColumn("姓名", 20);
			headers[3] = new ExcelColumn("身份证号", 30);
			headers[4] = new ExcelColumn("院校名称", 40);
			headers[5] = new ExcelColumn("区域总监", 20);
			headers[6] = new ExcelColumn("招生经理", 20);
			headers[7] = new ExcelColumn("招生顾问", 20);
			headers[8] = new ExcelColumn("报名费缴纳状态", 20);
			headers[9] = new ExcelColumn("报名应收免金额", 20);
			headers[10] = new ExcelColumn("报名费实收金额", 20);
			headers[11] = new ExcelColumn("报名费减免金额", 20);
			headers[12] = new ExcelColumn("报名费缴纳时间", 20);
			headers[13] = new ExcelColumn("经办人", 20);
			//报名费缴纳状态	报名费应收金额	报名费减免金额	报名费实收金额	报名费缴纳时间	经办人

			SessionUser sessionUser = WebUtil.getLoginUser(request);
			Integer userId = sessionUser.getUserId();//当前登录者userId
			HashMap paramMap = (HashMap) request.getSession().getAttribute("Last_Export_Param");
			int opType = (Integer) request.getSession().getAttribute("Last_optype");
			if (paramMap == null) {
				response.setContentType("application/javascript;charset=UTF-8");
				String s = "layer.alert('请点击查询后才能导出报表文件');";
				out.write(s.getBytes("UTF-8"));
				return;
			}

			List<StuExportBean> dataList = null;
			if (opType == 0) {//我关注的
				dataList = studentService.getMyFocusStuExportList(paramMap);
			} else if (opType == 1) {
				//全部
				dataList = studentService.getAllStuExportList(paramMap);
			} else {
				//我管理的
				dataList = studentService.getMyManagedStuExportList(paramMap);
			}

			int dataLen = 0;
			if (dataList != null && dataList.size() > 0) {
				dataLen = dataList.size();
				int index = 0;
				for (StuExportBean zsExportStuBean : dataList) {
					zsExportStuBean.setNo(++index);
				}
			}

			sdf = new SimpleDateFormat("yyyy年MM月dd日");
			String headMsg = String.format("共计%s名学员           导出时间：%s", dataLen, sdf.format(curDate));
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "iso-8859-1"));

			HSSFWorkbook workbook = LqExcelUtil.exportExcelData(headers, columnKeys, headMsg, "学生信息", dataList, format);
			workbook.write(out);

			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新学员分班, 学员导出. 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/stuNoClassExport.do")
	@Functions({ 57 })
	public void stuNoClassExport(HttpServletRequest request, HttpServletResponse response) {
		try {
			OutputStream out = response.getOutputStream();
			Date curDate = new Date();
			String format = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String fileName = "学生信息表_" + sdf.format(curDate) + ".xls";

			String[] columnKeys = { "no", "stuNo", "stuName", "idCard", "univName", "inspectorTeaName", "invTeaName",
					"advisorTeaName", "entryFeePayStatus", "entryFeeFactMoney", "entryFeeCurPayMoney",
					"entryFeeFavourMoney", "payTime", "payeeUserName" };

			ExcelColumn[] headers = new ExcelColumn[14];
			headers[0] = new ExcelColumn("序号", 5);
			headers[1] = new ExcelColumn("学员编码", 20);
			headers[2] = new ExcelColumn("姓名", 20);
			headers[3] = new ExcelColumn("身份证号", 30);
			headers[4] = new ExcelColumn("院校名称", 40);
			headers[5] = new ExcelColumn("区域总监", 20);
			headers[6] = new ExcelColumn("招生经理", 20);
			headers[7] = new ExcelColumn("招生顾问", 20);
			headers[8] = new ExcelColumn("报名费缴纳状态", 20);
			headers[9] = new ExcelColumn("报名应收免金额", 20);
			headers[10] = new ExcelColumn("报名费实收金额", 20);
			headers[11] = new ExcelColumn("报名费减免金额", 20);
			headers[12] = new ExcelColumn("报名费缴纳时间", 20);
			headers[13] = new ExcelColumn("经办人", 20);
			//报名费缴纳状态	报名费应收金额	报名费减免金额	报名费实收金额	报名费缴纳时间	经办人

			SessionUser sessionUser = WebUtil.getLoginUser(request);
			Integer userId = sessionUser.getUserId();//当前登录者userId
			HashMap paramMap = (HashMap) request.getSession().getAttribute("Last_Export_Param");
			int opType = (Integer) request.getSession().getAttribute("Last_optype");
			if (paramMap == null) {
				response.setContentType("application/javascript;charset=UTF-8");
				String s = "layer.alert('请点击查询后才能导出报表文件');";
				out.write(s.getBytes("UTF-8"));
				return;
			}

			List<StuExportBean> dataList = null;
			if (opType == 0) {//我关注的
				dataList = studentService.selectMyFocusNoClassExport(paramMap);
			} else {
				//全部标签
				dataList = studentService.selectAllNoClassExport(paramMap);
			}

			int dataLen = 0;
			if (dataList != null && dataList.size() > 0) {
				dataLen = dataList.size();
				int index = 0;
				for (StuExportBean zsExportStuBean : dataList) {
					zsExportStuBean.setNo(++index);
				}
			}

			sdf = new SimpleDateFormat("yyyy年MM月dd日");
			String headMsg = String.format("共计%s名学员           导出时间：%s", dataLen, sdf.format(curDate));
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "iso-8859-1"));

			HSSFWorkbook workbook = LqExcelUtil.exportExcelData(headers, columnKeys, headMsg, "学生信息", dataList, format);
			workbook.write(out);

			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * excel 文件下载
	 * @param pp
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/financefile.do")
	@Functions({ 75 })
	public void financefile(@RequestParam HashMap<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();

			String[] timelist = new String[] { "开课时间", "结业时间", "首次入职时间", "就业服务结束时间", "报名费缴纳时间", "实训费缴纳时间", };

			String headstr = "导出学员数据的范围:\r\n";
			headstr += "报表月份:" + param.get("exportmonth").toString() + "\r\n";
			String optype = (String) param.get("optype");
			if (!StringUtils.isEmpty(optype) && !"0".equals(optype)) {
				int noptype = Integer.parseInt(optype);
				String timename = timelist[noptype - 1];

				String stime = (String) param.get("stime");
				String etime = (String) param.get("etime");
				headstr += timename + ":从" + stime + "到" + etime + "\r\n";
			}

			String student_name_export = (String) param.get("student_name_export");
			if (!StringUtils.isEmpty(student_name_export)) {
				headstr += "学员姓名:" + student_name_export + "\r\n";
			}

			String select_school_id = (String) param.get("select_school_id");
			if (!StringUtils.isEmpty(select_school_id)) {
				headstr += "院校名称:" + (String) param.get("select_school_idName") + "\r\n";
			}

			String studyStatus = (String) param.get("studyStatus");
			if (!StringUtils.isEmpty(studyStatus)) {
				headstr += "实训费状态:" + (String) param.get("studyStatusName") + "\r\n";
			}

			String signStauts = (String) param.get("studyStatus");
			if (!StringUtils.isEmpty(signStauts)) {
				headstr += "报名费状态:" + (String) param.get("signStatusName") + "\r\n";
			}

			String stuStatus = (String) param.get("stuStatus");
			if (!StringUtils.isEmpty(stuStatus)) {
				headstr += "学生状态:" + (String) param.get("stuStatusName") + "\r\n";
			}

			System.out.println(headstr);
			WebUtil.printMap(param);

			Date curDate = new Date();
			//String format = "yyyy-MM-dd HH:mm:ss";

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

			String fileName = "学生信息表_" + sdf.format(curDate) + ".xls";

			List<Map<String, Object>> datalist = service.findExcelDataList(param);

			headstr += "共计 " + datalist.size() + "名学员\r\n";
			System.out.println("dataList size:" + datalist.size());
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "iso-8859-1"));

			LqExcelFinance.createExcelOutputStream(out, headstr, datalist);

			out.flush();
			out.close();
		} catch (Exception se) {
			se.printStackTrace();
		}
	}

	/**
	 *  查询列表中的数据.	
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ExportStudentjson.do")
	@Functions({ 75 })
	public void ExportStudentjson(@RequestParam HashMap<String, Object> pp, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebUtil.printMap(pp);
		int pageno = ServletRequestUtils.getIntParameter(request, "pageno", 1);
		int pagesize = ServletRequestUtils.getIntParameter(request, "pagesize", 20);
		int currindex = (pageno - 1) * pagesize;
		pp.put("pagesize", pagesize);
		pp.put("currindex", currindex);
		//时间. 
		String stime = ServletRequestUtils.getStringParameter(request, "stime");
		String etime = ServletRequestUtils.getStringParameter(request, "etime");

		if (!StringUtils.isEmpty(stime) && !StringUtils.isEmpty(etime)) {
			pp.put("stime", CommonUtil.dateStrToDate_small(stime));
			pp.put("etime", CommonUtil.dateStrToDate_big(etime));
		}
		String classid = ServletRequestUtils.getStringParameter(request, "classid");
		if (StringUtils.isNotBlank(classid)) {
			pp.put("classid", classid);
		}
		List list = service.findTableList(pp);
		HashMap ret = new HashMap();

		if (pageno == 1) {
			int nRecord = service.findTableListCount(pp);
			ret.put("recordcount", nRecord);
		}

		ret.put("datalist", list);
		ret.put("code", 200);
		String s = JSON.toJSONString(ret);
		//	System.out.println (s);
		response.getWriter().print(s);

	}

	/**
	 * 显示财务报表导出界面.
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showfinance.do")
	@Functions({ 75 })
	public ModelAndView finaceshow(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/WEB-INF/view/exportexcel/finance_export.jsp");
		//学生状态. 
		List<Map<String, Object>> stuStatus = WebUtil.getStuStatusList();
		ListIterator<Map<String, Object>> listiter = stuStatus.listIterator();

		while (listiter.hasNext()) {
			Map<String, Object> mp = listiter.next();
			Integer it = (Integer) mp.get("value");

			if (it == StuStatusEnum.NOREGISTRATION.getValue() || it == StuStatusEnum.AUDIT.getValue()) {
				listiter.remove();
			}

		}

		mv.addObject("stuStatus", stuStatus);
		//学费状态.
		List<Map<String, Object>> payStatus = WebUtil.getPayDList();
		mv.addObject("payStatus", payStatus);

		return mv;
	}

	/**
	 * 招生老师导出学生信息
	 * @param map
	 * @param request
	 * @param response
	 */
	@RequestMapping("/stuExport.do")
	@Functions({ 57, 78 })
	public void stuExport(HttpServletRequest request, HttpServletResponse response) {
		try {
			OutputStream out = response.getOutputStream();
			Date curDate = new Date();
			String format = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String fileName = "学生信息表_" + sdf.format(curDate) + ".xls";

			String[] columnKeys = { "no", "stuNo", "stuName", "idCard", "univName", "inspectorTeaName", "invTeaName",
					"advisorTeaName", "entryFeePayStatus", "entryFeeFactMoney", "entryFeeCurPayMoney",
					"entryFeeFavourMoney", "payTime", "payeeUserName" };

			ExcelColumn[] headers = new ExcelColumn[14];
			headers[0] = new ExcelColumn("序号", 5);
			headers[1] = new ExcelColumn("学员编码", 20);
			headers[2] = new ExcelColumn("姓名", 20);
			headers[3] = new ExcelColumn("身份证号", 30);
			headers[4] = new ExcelColumn("院校名称", 40);
			headers[5] = new ExcelColumn("区域总监", 20);
			headers[6] = new ExcelColumn("招生经理", 20);
			headers[7] = new ExcelColumn("招生顾问", 20);
			headers[8] = new ExcelColumn("报名费缴纳状态", 20);
			headers[9] = new ExcelColumn("报名应收免金额", 20);
			headers[10] = new ExcelColumn("报名费实收金额", 20);
			headers[11] = new ExcelColumn("报名费减免金额", 20);
			headers[12] = new ExcelColumn("报名费缴纳时间", 20);
			headers[13] = new ExcelColumn("经办人", 20);
			//报名费缴纳状态	报名费应收金额	报名费减免金额	报名费实收金额	报名费缴纳时间	经办人

			SessionUser sessionUser = WebUtil.getLoginUser(request);
			Integer userId = sessionUser.getUserId();//当前登录者userId
			HashMap paramMap = (HashMap) request.getSession().getAttribute("Last_Export_Param");
			int opType = (Integer) request.getSession().getAttribute("Last_optype");
			if (paramMap == null) {
				response.setContentType("application/javascript;charset=UTF-8");
				String s = "layer.alert('请点击查询后才能导出报表文件');";
				out.write(s.getBytes("UTF-8"));
				return;
			}

			List<StuExportBean> dataList = null;
			if (opType == 0) {//我关注的
				dataList = studentService.getMyFocusStuExportList(paramMap);
			}
			if (opType == 2) {//我管理的
				dataList = studentService.getMyManagedStuExportList(paramMap);
			}
			if (opType == 1) {
				//全部
				dataList = studentService.getAllStuExportList(paramMap);
			}
			int dataLen = 0;
			if (dataList != null && dataList.size() > 0) {
				dataLen = dataList.size();
				int index = 0;
				for (StuExportBean zsExportStuBean : dataList) {
					zsExportStuBean.setNo(++index);
				}
			}

			sdf = new SimpleDateFormat("yyyy年MM月dd日");
			String headMsg = String.format("共计%s名学员           导出时间：%s", dataLen, sdf.format(curDate));
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "iso-8859-1"));

			HSSFWorkbook workbook = LqExcelUtil.exportExcelData(headers, columnKeys, headMsg, "学生信息", dataList, format);
			workbook.write(out);

			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

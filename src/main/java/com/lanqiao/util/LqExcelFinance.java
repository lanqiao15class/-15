package com.lanqiao.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.ehcache.hibernate.management.impl.BeanUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author chenbaoji 
 * @date 2016年10月31日 上午10:30:27
 * @desc 财务学生报表导出
 */
public class LqExcelFinance {
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String[] args) throws Exception 
	{
		String headstring = "查询条件:\n日期:2013-01\n查询条件:\n日期:2013-01\n查询条件:\n日期:2013-01\n";
		
		FileOutputStream out = new FileOutputStream("d:\\aa.xls");
		ArrayList<HashMap<String,Object>> al =new ArrayList<HashMap<String,Object>>();
		
		//createExcelOutputStream(out, headstring, al);
		
		out.close();
		
		System.out.println ("保存成功. ");
	}
	
	static String HashMapNameList [][] = new String[][]{
			{"indexNo","序号"},
			{"stuNo","学员编码"},
			
			{"realName","姓名"},
			{"idCard","身份证号"},
			{"unvName","所在院校"},
			
			{"className1","所在班级1名称"},
			{"classEnterTime1","所在班级1进入时间"},
			{"classQuitTime1","所在班级1退出时间"},
			
			{"className2","所在班级2名称"},
			{"classEnterTime2","所在班级2进入时间"},
			{"classQuitTime2","所在班级2退出时间"},
			
			{"className3","所在班级3名称"},
			{"classEnterTime3","所在班级3进入时间"},
			{"classQuitTime3","所在班级3退出时间"},
			
			{"className4","所在4名称"},
			{"classEnterTime4","所在班级4进入时间"},
			{"classQuitTime4","所在班级4退出时间"},
			
			{"jobServiceStarttime","就业服务开始时间"},
			{"empTime","首次入职时间"},
			{"comanyName","首次入职企业"},
			{"jobServiceEndtime","就业服务期结束时间"},
			{"isEndjobTime","就业服务期是否结束"},
			{"jobMoney","就业服务费确认收入金额"},
			
			{"signStandardFee","报名费合同金额"},
			{"signfavourFee","报名费累计减免金额"},
			{"signfavourReason","报名费减免原因"},
			{"signShouldFee","报名费应收金额"},
			{"signStatus","报名费缴纳状态"},
			
			{"studyStandardFee","实训费合同金额"},
			{"studyfavourFee","实训费累计减免金额"},
			{"studyfavourReason","实训费减免原因"},
			{"studyShouldFee","实训费应收金额"},
			{"studyStatus","实训费缴纳状态"},
			{"studypaytype","实训费收费方式"},
			
			{"month_signpayMoney",""},
			{"month_signpaytime",""},
			{"month_signAddMoney",""},
			{"month_signbackMoney",""},
			{"month_signbackTime",""},
			
			{"month_studypayMoney",""},
			{"month_studypaytime",""},
			{"month_studyAddMoney",""},
			{"month_studybackMoney",""},
			{"month_studybackTime",""}
	};
	

			
	public static void createExcelOutputStream(OutputStream out,String headstring, List<Map<String,Object>> dataList)
	throws Exception
	{
		
		HSSFWorkbook  book = LqExcelFinance.makeExcelHead("报表",headstring,50);
		//写入 列名
		
		ExcelColumn [] cols = new ExcelColumn[HashMapNameList.length -10];
		for(int i=0;i<cols.length;i++)
		{
			cols[i] = new ExcelColumn(HashMapNameList[i][1] ,20);
		}
		
		ExcelColumn [] signfee= new ExcelColumn[5];
		signfee[0] = new ExcelColumn("当月收费金额",20);
		signfee[1] = new ExcelColumn("收费时间",20);
		signfee[2] = new ExcelColumn("累计收费金额",20);
		signfee[3] = new ExcelColumn("退费金额",20);
		signfee[4] = new ExcelColumn("退费备注",20);
		
		ExcelColumn [] studyfee = signfee;
		
		LqExcelFinance.makeSecondHead(book,cols,
				signfee, studyfee);
		
		putIntoData(book, dataList);
		
		
		book.write(out);
		
	}
	
	/**
	 * 放入数据到book中.
	 * @param workbook
	 * @param list
	 * @return
	 */
	public static HSSFWorkbook putIntoData(HSSFWorkbook workbook,List<Map<String,Object>> dataList)
	{
		int beginrow = 3;
		HSSFSheet sheet = workbook.getSheetAt(0);
		String format ="yyyy-MM-dd";
		// 填充数据
		HSSFCellStyle styleData = workbook.createCellStyle();
		styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		NumberFormat numFormatter = null;
		String textValue = null;
		if(dataList != null && dataList.size() > 0) {
			for (int j = 0; j < dataList.size(); j++) {
				HSSFRow rowData = sheet.createRow(j + beginrow);
				HashMap<String , Object> t =(HashMap) dataList.get(j);
				for(int k=0; k<HashMapNameList.length; k++){
					Object value = t.get(HashMapNameList[k][0]);
				//	System.out.println ("row" +(j + beginrow)+" " +HashMapNameList[k][0] +":" + value);
					if(value == null) {
						value = "";
					} else if(value instanceof Date) {
						value = sdf.format(value);
					} else if(value instanceof BigDecimal) {
						double val = ((BigDecimal) value).doubleValue();
						if (String.valueOf(val).indexOf('0') > 0) {
							numFormatter = new DecimalFormat("#.00");
						} else {
							numFormatter = new DecimalFormat("0.00");
						}
						value = numFormatter.format(val);
					}
					HSSFCell cellData = rowData.createCell(k);
					cellData.setCellValue(value.toString());
					cellData.setCellStyle(styleData);
				}			
			}
		}
		return workbook;
	}
	
	
	/**
	 * 设定二级标题
	 * @param workbook
	 * @param secondTitles
	 * @return
	 */
	public static HSSFWorkbook makeSecondHead(HSSFWorkbook workbook, ExcelColumn[] headers,
			ExcelColumn [] signfee, ExcelColumn [] studyfee)
	{
		// 创建用户属性栏
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		for(int i = 0;i <headers.length;i++)
		{
			CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 2, i,i);
			sheet.addMergedRegion(cellRangeAddress);
		}
		
		HSSFRow rowField = sheet.createRow(1);
		rowField.setHeight((short)500);
		HSSFCellStyle styleField = createHeaderStyle(workbook);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = rowField.createCell(i);
			cell.setCellValue(headers[i].title);
			cell.setCellStyle(styleField);
			sheet.setColumnWidth(i, headers[i].width * 256);
			
		}
		
		HSSFRow rowField2 = sheet.createRow(2);
		//==============================================================
		CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 1, headers.length,headers.length+4);
		sheet.addMergedRegion(cellRangeAddress);
		
		cellRangeAddress = new CellRangeAddress(1, 1,headers.length +5,
				headers.length+9 );
		sheet.addMergedRegion(cellRangeAddress);
		
		
		HSSFCell cell = rowField.createCell(headers.length);
		cell.setCellValue("报名费");
		cell.setCellStyle(styleField);
		
		for(int i=0;i<signfee.length;i++)
		{
			HSSFCell cellx = rowField.createCell(headers.length+i);
			cellx.setCellStyle(styleField);
			cellx.setCellValue("报名费");
			
			
			HSSFCell cell1 = rowField2.createCell(headers.length+i);
			cell1.setCellValue(signfee[i].title);
			cell1.setCellStyle(styleField);
			sheet.setColumnWidth(headers.length+i, signfee[i].width * 256);
		}
		//设置边框. 
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cellx = rowField2.createCell(i);
			cellx.setCellValue(headers[i].title);
			cellx.setCellStyle(styleField);
			//sheet.setColumnWidth(i, headers[i].width * 256);
		}
		
		HSSFCell cellstudy = rowField.createCell(headers.length+5);
		cellstudy.setCellValue("实训费");
		cellstudy.setCellStyle(styleField);
		
		
		for(int i=0;i<studyfee.length;i++)
		{
			HSSFCell cell1 = rowField2.createCell(headers.length+5+i);
			cell1.setCellValue(studyfee[i].title);
			cell1.setCellStyle(styleField);
			sheet.setColumnWidth(headers.length+5+i, signfee[i].width * 256);
			

			HSSFCell cellx = rowField.createCell(headers.length+5+i);
			cellx.setCellStyle(styleField);
			
			cellx.setCellValue("实训费");
			
			
		}
	
		
		return workbook;
	}
	
	/**
	 * 导出excel头部标题
	 * @param title
	 * @param cellRangeAddressLength
	 * @return
	 */
	public static HSSFWorkbook makeExcelHead(String sheetName,String title, int cellRangeAddressLength){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle styleTitle = createStyle(workbook, (short)12 ,HSSFCellStyle.ALIGN_LEFT);
		
		styleTitle.setWrapText(true);
		HSSFSheet sheet = workbook.createSheet(sheetName);
		//sheet.setDefaultColumnWidth(600);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, cellRangeAddressLength);
		sheet.addMergedRegion(cellRangeAddress);
		HSSFRow rowTitle = sheet.createRow(0);
		rowTitle.setHeight((short)(2000));
		HSSFCell cellTitle = rowTitle.createCell(0);
		// 为标题设置背景颜色
		styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleTitle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellTitle.setCellValue(new HSSFRichTextString(title));
		cellTitle.setCellStyle(styleTitle);
		 
		return workbook;
	}
	/**
	 * @param headers 表头
	 * @param columnKeys 表列名
	 * @param headMsg 第一行提示信息
	 * @param headMsgLen 第一行合并单元格个数
	 * @param dataList 数据列表
	 * @return 导出Excel数据
	 */
	public static <T> HSSFWorkbook exportExcelData(ExcelColumn[] headers,String []columnKeys,String headMsg,String sheetName, List<T> dataList,String format) {
		//创建头部信息
		HSSFWorkbook workbook = LqExcelUtil.makeExcelHead(sheetName, headMsg, headers.length - 1);
		//创建表头
		workbook = LqExcelUtil.makeSecondHead(workbook, headers);
		//获取第一个sheet页
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 填充数据
		HSSFCellStyle styleData = workbook.createCellStyle();
		styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		NumberFormat numFormatter = null;
		if(dataList != null && dataList.size() > 0) {
			for (int j = 0; j < dataList.size(); j++) {
				HSSFRow rowData = sheet.createRow(j + 2);
				T t = dataList.get(j);
				for(int k=0; k<columnKeys.length; k++){
					Object value = BeanUtils.getBeanProperty(t, columnKeys[k]);
					if(value == null) {
						value = "";
					} else if(value instanceof Date) {
						value = sdf.format(value);
					} else if(value instanceof BigDecimal) {
						double val = ((BigDecimal) value).doubleValue();
						if (String.valueOf(val).indexOf('0') > 0) {
							numFormatter = new DecimalFormat("#.00");
						} else {
							numFormatter = new DecimalFormat("0.00");
						}
						value = numFormatter.format(val);
					}
					HSSFCell cellData = rowData.createCell(k);
					cellData.setCellValue(value.toString());
					cellData.setCellStyle(styleData);
				}			
			}
		}
		return workbook;
	}
	
	/**
	 * 使用批量导入方法时，请注意需要导入的Bean的字段和excel的列一一对应
	 * @param clazz
	 * @param file
	 * @param beanPropertys
	 * @return
	 */
	public static <T> List<T> parserExcel(Class<T> clazz, File file, String[] beanPropertys) {
		// 得到workbook
		List<T> list = new ArrayList<T>();
		try {
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(0);
			// 直接从第三行开始获取数据
			int rowSize = sheet.getPhysicalNumberOfRows();
			if(rowSize > 2){				
				for (int i = 2; i < rowSize; i++) {
					T t = clazz.newInstance();
					Row row = sheet.getRow(i);
					int cellSize = row.getPhysicalNumberOfCells();
					for(int j=0; j<cellSize; j++){
						Object cellValue = getCellValue(row.getCell(j));
						org.apache.commons.beanutils.BeanUtils.copyProperty(t, beanPropertys[j], cellValue);
					}						
					list.add(t);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 通用的读取excel单元格的处理方法
	 * @param cell
	 * @return
	 */
	private static Object getCellValue(Cell cell) {
		Object result = null;
		if (cell != null) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					result = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					//对日期进行判断和解析
					if(HSSFDateUtil.isCellDateFormatted(cell)){
						double cellValue = cell.getNumericCellValue();
						result = HSSFDateUtil.getJavaDate(cellValue);
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					result = cell.getBooleanCellValue();
					break;
				case Cell.CELL_TYPE_FORMULA:
					result = cell.getCellFormula();
					break;
				case Cell.CELL_TYPE_ERROR:
					result = cell.getErrorCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					break;
				default:
					break;
			}
		}
		return result;
	}
	
	/**
	 * 提取公共的样式
	 * @param workbook
	 * @param fontSize
	 * @return
	 */
	private static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fontSize,short alignment){
		HSSFCellStyle style = workbook.createCellStyle();		
		style.setAlignment(alignment);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 创建一个字体样式
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		style.setFont(font);
		return style;
	}

	
/**
 * 创建 列头部的样式
 * @param workbook
 * @return
 */
	private static HSSFCellStyle createHeaderStyle(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();		
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		style.setBottomBorderColor(HSSFColor.WHITE.index);
		style.setLeftBorderColor(HSSFColor.WHITE.index);
		style.setRightBorderColor(HSSFColor.WHITE.index);
		style.setTopBorderColor(HSSFColor.WHITE.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
		
		// 创建一个字体样式
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
	
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.BROWN.index);
		
		return style;
	}
	
	
}

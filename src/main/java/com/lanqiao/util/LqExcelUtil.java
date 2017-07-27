package com.lanqiao.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.ehcache.hibernate.management.impl.BeanUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
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
 * @author zjyou
 * @date 2016年10月31日 上午10:30:27
 * @desc 财务学生报表导出
 */
public class LqExcelUtil {

	private LqExcelUtil() {
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
		HSSFSheet sheet = workbook.createSheet(sheetName);
		sheet.setDefaultColumnWidth(25);
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, cellRangeAddressLength);
		sheet.addMergedRegion(cellRangeAddress);
		HSSFRow rowTitle = sheet.createRow(0);
		rowTitle.setHeight((short)(600));
		HSSFCell cellTitle = rowTitle.createCell(0);
		// 为标题设置背景颜色
		styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleTitle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellTitle.setCellValue(title);
		cellTitle.setCellStyle(styleTitle);
		return workbook;
	}
	
	/**
	 * 设定二级标题
	 * @param workbook
	 * @param secondTitles
	 * @return
	 */
	public static HSSFWorkbook makeSecondHead(HSSFWorkbook workbook, ExcelColumn[] headers){
		// 创建用户属性栏
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow rowField = sheet.createRow(1);
		HSSFCellStyle styleField = createStyle(workbook, (short)10 , HSSFCellStyle.ALIGN_CENTER);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = rowField.createCell(i);
			cell.setCellValue(headers[i].title);
			cell.setCellStyle(styleField);
			sheet.setColumnWidth(i, headers[i].width * 256);
		}
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

}

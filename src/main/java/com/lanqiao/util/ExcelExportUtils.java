package com.lanqiao.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author zjyou
 * @date 2013-12-26 下午20:18:38
 * @func 抽取类用于对excel进行导出操作
 */
public class ExcelExportUtils<T> {
	
    private final static Logger logger = LogManager.getLogger(ExcelExportUtils.class);

	public void exportExcel(String title, ExcelColumn[] headers, String[] keys, List<Map<String, Object>> list, OutputStream out, String pattern, String[] toPersentColumns) {
		HSSFWorkbook workbook = new HSSFWorkbook(); // 声明一个工作薄
		HSSFSheet sheet = workbook.createSheet(title); // 生成一个表格
		sheet.setDefaultColumnWidth(20);

		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		HSSFRow row = sheet.createRow(0);
		int columnIndex = 0;
		for (ExcelColumn column : headers) {
			HSSFCell cell = row.createCell(columnIndex);
			HSSFRichTextString text = new HSSFRichTextString(column.title);
			cell.setCellValue(text);
			sheet.setColumnWidth(columnIndex, column.width * 256);
			cell.setCellStyle(cellStyle);
			columnIndex++;
		}

		Iterator<Map<String, Object>> it = (Iterator<Map<String, Object>>) list.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			Map<String, Object> map = it.next();
			for (int i = 0; i < keys.length; i++) {
				HSSFCell cell = row.createCell(i);
				String key = keys[i];
				try {
					Object value = map.get(key);
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Integer) { // 整型
						int intValue = (Integer) value;
						cell.setCellValue(intValue);
					} else if (value instanceof Float) { // 浮点型
						float fValue = (Float) value;
						textValue = new HSSFRichTextString(String.valueOf(fValue)).toString();
						cell.setCellValue(textValue);
					} else if (value instanceof Double) { // 双精度
						String dValue = String.valueOf(value);
						// 循环便利需要转换成百分数显示的列
						for (int j = 0; j < toPersentColumns.length; j++) {
							NumberFormat formatter = null;
							if (value.toString().indexOf('0') > 0) {
								formatter = new DecimalFormat("#.00");
							} else {
								formatter = new DecimalFormat("0.00");
							}
							String column = toPersentColumns[j];
							if (key.equals(column)) {// 转成百分数显示
								dValue = String.valueOf(value) + "%";
								break;
							}
						}
						textValue = new HSSFRichTextString(dValue).toString();
						cell.setCellValue(textValue);
					} else if (value instanceof Long) { // 长整形
						long longValue = (Long) value;
						cell.setCellValue(longValue);
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						row.setHeightInPoints(60); // 有图片时，设置行高为60px;
						sheet.setColumnWidth(i, (short) (35.7 * 80)); // 设置图片所在列宽度为80px,注意这里单位的一个换算
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
					} else {
						if (value == null || "null".equalsIgnoreCase(value + "")) {
							textValue = "";
						} else {
							for (int j = 0; j < toPersentColumns.length; j++) {
								String column = toPersentColumns[j];
								if (key.equals(column)) {// 转成百分数显示
									Pattern p = Pattern.compile("^[0-9]*\\.?{0,1}[0-9]*$");
									String numStr = value.toString();
									Matcher matcher = p.matcher(numStr);
									if (matcher.matches()) {
										NumberFormat formatter = null;
										if (numStr.indexOf('0') > 0) {
											formatter = new DecimalFormat("#.00");
										} else {
											formatter = new DecimalFormat("0.00");
										}
										double val = Double.valueOf(value.toString());
										value = formatter.format(val);
									}
									value = value + "%";
									break;
								}
							}
							textValue = String.valueOf(value);// 其它数据类型都当作字符串简单处理
						}
					}
					if (textValue != null) { // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							cell.setCellValue(Double.parseDouble(textValue)); // 是数字当作double处理
						} else {
							cell.setCellValue(textValue);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("EXCEL文档导出，异常类型：SecurityException \n" + "异常信息：" + e.getMessage());
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("EXCEL文档导出，异常类型：IOException \n" + "异常信息：" + e.getMessage());
		}

	}

	/***
	 * 字体样式：设计粗体
	 * 
	 * @param workbook
	 * @return
	 */
	public static CellStyle getFontCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		return cellStyle;
	}

	/***
	 * 字体样式：粗体，剧中，加大
	 * 
	 * @param workbook
	 * @return
	 */
	public static CellStyle getFontSizeCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 24);
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		return cellStyle;
	}
}
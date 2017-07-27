package com.lanqiao.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author zjyou
 * @date 2016年7月14日 下午7:03:53
 * @desc Excel获取Cell工具类
 */
public class ExcelUtils {
    public static int getIntCellValue(HSSFRow row,int index){
        int rtn = 0;
        try {
            HSSFCell cell = row.getCell((short)index);
            rtn = (int)cell.getNumericCellValue();
        } catch (RuntimeException e) {
        }
        return rtn;
    }

    public static String getStringValue(HSSFRow row,int index){
        String rtn = "";
        try {
            HSSFCell cell = row.getCell((short)index);
            rtn = cell.getRichStringCellValue().getString();
        } catch (RuntimeException e) {
        }
        return rtn;
    }

    public static double getDoubleCellValue(HSSFRow row,int index){
        double rtn = 0d;
        try {
            HSSFCell cell = row.getCell((short)index);
            rtn = (Double)cell.getNumericCellValue();
        } catch (RuntimeException e) {
        }
        return rtn;
    }
    
    public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (null != cell) {
			// 以下是判断数据的类型
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				cellValue = cell.getNumericCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				cellValue = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				cellValue = cell.getBooleanCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				cellValue = cell.getCellFormula() + "";
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				cellValue = "";
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				cellValue = "非法字符";
				break;
			default:
				cellValue = "未知类型";
				break;
			}
		}
		return cellValue.replace(" ", "").trim();
	}
}
package com.lanqiao.util;

public class ExcelColumn {

	public String title;
	public int width;
	public int cellrow ;
	
	public ExcelColumn(String title, int width) {
		this.title = title;
		this.width = width;
		
	}
	
	
	public ExcelColumn(String title, int width, int cellrow) {
		this.title = title;
		this.width = width;
		this.cellrow = cellrow;
		
	}
}

package com.lanqiao.dto;

import com.lanqiao.model.SysCourse;

public class CourseDTO extends SysCourse {
	private String typeStr;
	private String timeStr;
	private String lqCoursetypeStr;

	public String getLqCoursetypeStr() {
		return lqCoursetypeStr;
	}

	public void setLqCoursetypeStr(String lqCoursetypeStr) {
		this.lqCoursetypeStr = lqCoursetypeStr;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

}

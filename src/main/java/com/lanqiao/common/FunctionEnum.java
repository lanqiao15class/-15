package com.lanqiao.common;

import java.util.HashMap;

/**
 * 
 * 系统权限定义. 与数据库表sys_function 里的数据对应. 
 * 方便程序编写. 
 * 
 * @author chen baoji
 * 
 *
 */

public enum FunctionEnum
{
	;
/*	Fun_yxstudentnew(32, "新增意向学员"),
	Fun_yxvisitlog(34, "学员跟踪记录"),
	Fun_visitlog(37, "学员跟踪记录"),
	Fun_yxstudentall(47, "全部意向学员"),
	Fun_yxfocusstudent(48,"关注意向学员"),
	Fun_yxauditstudent(49, "审核意向学员报名"),

	Fun_studentexport(57, "学员信息导出"),
	Fun_studentchangestatus(56, "修改学员状态	"),
	Fun_studentchangeclass(55, "学员转班"),
	Fun_studentall(54, "全部学员"),
	Fun_studentfocus(53, "关注学员"),
	Fun_studentmymanage(52, "我管理的学员"),
	Fun_studentfocusnew(51, "关注新学员"),
	Fun_studentalldistribution(50, "全部新学员分班"),
	
	Fun_studentmyunusual(58, "我管理的异常状态学员"),
	Fun_studentunusualfocus(59, "关注异常状态学员"),
	Fun_studentallunusual(60, "全部异常状态学员"),
	Fun_moneymy(61, "我管理的学员回款管理"),
	Fun_moneyall(62, "全部学员回款管理"),
	
	Fun_classmymanage(63, "我管理的班级"),
	Fun_classall(64, "全部班级"),
	Fun_classfocus(65, "关注班级"),
	Fun_classchangelog(66, "班级跟踪记录"),
	Fun_classmerge(67, "合并班级	"),
	Fun_classchangestatus(68, "修改班级状态"),
	
	Fun_jobmymanage(69, "我管理的就业学员"),
	Fun_joball(70,"全部就业学员"),
	Fun_jobfocus(71,"关注就业学员"),
	Fun_jobdismiss(73, "离职登记"),
	Fun_jobentry(72, "入职登记");
	
*/
	
	
	private int value;
	private String text;

	private FunctionEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	/*
	public static HashMap getAllMap()
	{
		if(GL_MP ==null)
		{
			//将功能权限ID 放入application, 以便jsp 使用. 
	    	HashMap fmp = new HashMap();
	    //	fmp.put("Fun_CollegeDelete", FunctionEnum.Fun_CollegeDelete.getValue());
	    	//fmp.put("Fun_CollegeEdit", FunctionEnum.Fun_CollegeEdit.getValue());
	    	
			GL_MP= fmp;
		}
		
		return GL_MP;
	}*/
	
	
	private static HashMap GL_MP = null;
	
}
package com.lanqiao.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.lanqiao.model.Student;
import com.lanqiao.model.SysDeparment;
import com.lanqiao.model.SysFunction;
import com.lanqiao.model.SysRole;
import com.lanqiao.model.Teacher;
import com.lanqiao.model.User;
/**
 * 登录后保存到session中的用户对象
 * @author chen baoji
 *
 */
public class SessionUser extends User {

	//保存到session中 key 的名字
	public static String Session_User_Name="loginuser";
	
	Teacher teacher;// 老师信息, 可能为null
	Student student; // 学生信息, 可能为null
	List<SysMenuExt> sysmenus=null; // 当前登录者拥有的菜单项
	List<SysFunction> sysfunctions= null ; // 当前登录者拥有的系统功能. 
	List<SysRole> roles=null; // 当前登录者拥有的系统角色. 
	SysDeparment deparment=null;
	//1: 院校id 2:班级id 3:企业id'
	
	/**
	 * 是否是学生账户
	 * @return
	 */
	public boolean ifStudent()
	{
		return (student != null && "0".equals(this.getType())) ? true : false;
	}

	/**
	 * 是否是老师账户
	 * @return
	 */
	public boolean ifTeacher()
	{
		return (teacher != null && "1".equals(this.getType())) ? true : false;
	}
	
	public SysDeparment getDeparment()
	{
		return deparment;
	}
	
	public void setDeparment(SysDeparment deparment)
	{
		this.deparment = deparment;
	}

	HashMap<Integer,HashSet<Integer>> AllDataID= new HashMap<Integer,HashSet<Integer>>();
	
	//跟组织结构无关的记录id
	HashMap<Integer,HashSet<Integer>> SelfDataID= new HashMap<Integer,HashSet<Integer>>();
	
	public HashMap<Integer, HashSet<Integer>> getSelfDataID()
	{
		return SelfDataID;
	}

	public void setSelfDataID(HashMap<Integer, HashSet<Integer>> selfDataID)
	{
		SelfDataID = selfDataID;
	}

	public HashMap<Integer, HashSet<Integer>> getAllDataID()
	{
		return AllDataID;
	}
	
	public void setAllDataID(HashMap<Integer, HashSet<Integer>> allDataID)
	{
		AllDataID = allDataID;
	}
	
	/**
	 * 得到当前登录用户具有的数据id
	 * @param nval
	 * @return
	 */
	public HashSet<Integer> getIds(int nval)
	{
		HashSet<Integer> ret = null;
		if(AllDataID!=null)
			ret = AllDataID.get(nval);
		
		return ret;
	}
	

	
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public List<SysMenuExt> getSysmenus() {
		return sysmenus;
	}
	public void setSysmenus(List<SysMenuExt> sysmenus) {
		this.sysmenus = sysmenus;
	}
	public List<SysFunction> getSysfunctions() {
		return sysfunctions;
	}
	public void setSysfunctions(List<SysFunction> sysfunctions) {
		this.sysfunctions = sysfunctions;
	}
	public List<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "SessionUser [teacher=" + teacher + ", student=" + student
				+ ", sysmenus=" + sysmenus + ", sysfunctions=" + sysfunctions
				+ ", roles=" + roles + ", AllDataID=" + AllDataID
				+ ", SelfDataID=" + SelfDataID + "]";
	}
	
	
	
	
}

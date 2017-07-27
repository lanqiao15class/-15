/*
 * File name:          DepartmentTree.java
 * Copyright@Handkoo (China)
 * Editor:           JDK1.6.32
 */
package com.lanqiao.model;

/**
 * TODO: File comments
 * <p>
 * <p>
 * Author:           卢锡文
 * <p>
 * Date:           2017年3月31日
 * <p>
 * Time:           下午1:12:14
 * <p>
 * Director:        卢锡文
 * <p>
 * <p>
 */
public class DepartmentTree extends SysDeparment implements Comparable<DepartmentTree> {
	private boolean open;
	private String icon;

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "DepartmentTree [open=" + open + ", icon=" + icon + ", isOpen()=" + isOpen() + ", getIcon()="
				+ getIcon() + ", getDepid()=" + getDepid() + ", getDepname()=" + getDepname() + ", getParentid()="
				+ getParentid() + ", getIsvalid()=" + getIsvalid() + ", getSortnum()=" + getSortnum()
				+ ", getStationids()=" + getStationids() + ", getManagerUserid()=" + getManagerUserid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	@Override
	public int compareTo(DepartmentTree o) {
		int result = 0;
		if (o.getParentid() == this.getParentid()) {
			result = o.getSortnum() > this.getSortnum() ? 1 : -1;

		}
		return result;
	}

}

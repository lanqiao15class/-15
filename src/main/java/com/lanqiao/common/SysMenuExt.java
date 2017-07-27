package com.lanqiao.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.lanqiao.model.SysMenu;

public class SysMenuExt extends SysMenu {


	/**
	 * 是否是一级菜单. 
	 * @return
	 */
public boolean isFirstMenu()
{
	return "0".equals(this.getId());
}

	//排序好的子菜单列表. 
	List<SysMenuExt> sortSubMenus= new ArrayList<SysMenuExt>();
	
	public List<SysMenuExt> getSortSubMenus() {
		return sortSubMenus;
	}


	public void setSortSubMenus(List<SysMenuExt> sortSubMenus) {
		this.sortSubMenus = sortSubMenus;
	}


	
	@Override
	public int hashCode()
	{
		return this.getId().hashCode();
	}
	
	@Override
	public boolean equals(Object o)
	{
		SysMenuExt m = (SysMenuExt)o;
		return this.getId().equals(m.getId());
	}
	
	
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
		//return new ToStringBuilder(this).toString();
		
		
		
	}
}

package com.lanqiao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DictMapper {
	 //根据value和type获取label值(通用)
	String getLabelByValAndType(Map<String,Object> param);
	
	//根据type获取各类型字典value+label（通用）
	List<Map<String,Object>>  selectDictByType(String type);
	
	//根据类型和父级编号获取字典列表
	List<Map<String,Object>> getDictByParentAndType(@Param("type")String type,@Param("parentId")String parentId);
    
	 //根据value和type获取Id值(通用)
	String getIdByValAndType(Map<String,Object> param);
	
	//根据父级和子级的value、type，获取父级和子级label（用在班级类型）
	String getClassType(Map<String,Object> map);
}

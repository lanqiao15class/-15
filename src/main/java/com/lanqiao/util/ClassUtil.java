package com.lanqiao.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;

public class ClassUtil {
	
	
	/**
	 *  将一个对象的所有的(key)属性,(value)数值 保存到map 中. 
	 *  
	 * @param o
	 * @return
	 */
	public static HashMap BeanToMap(Object o )
	{
		HashMap mpret =new HashMap();
		Map mp =  new BeanMap(o);
		mpret.putAll(mp);
		mpret.remove("class");
		
		return mpret;
	}

	/**
	 * 将一个entity 中的所有属性，数值取出保存到 hashmap 中， 
	 * 这样方便后面扩展。 增加其他属性， 可以作为数据库中的一条记录
	 * 
	 * 这个方法有个缺陷, 如果多级继承的实体, 无法解析子类的属性. 
	 * 用上面的方法
	 * @param c
	 * @return
	 */
	public static HashMap BeanPropertiesToMap(Object o)
	{
		HashMap mp = new HashMap();
		List<HashMap> ll = getFiledsInfo(o);
		for( HashMap m : ll)
		{
			mp.put(m.get("name"), m.get("value"));
		}
		return mp;
	}
	
	
	/**
	 * 检测对象中的所有属性数值是否为空。 
	 * @param obj
	 * @return
	 */
	public static boolean HashEnmptyField(Object obj)
	{
		
		boolean b =false;
		List<HashMap> ll = ClassUtil.getFiledsInfo(obj);
		for(HashMap mp : ll)
		{
				String sName = (String)mp.get("name");
					if(mp.get("value") ==null)
						return true;
					String stype = (String)mp.get("type");
					//log.info("==========>>>"+stype );
					if("class java.lang.String".equals(stype))
					{
						
						String sval = (String)mp.get("value");
						sval = sval.trim();
						//log.info(stype +"\t" +sval );
						if("".equals(sval))
							return true;
						
					}
			
		}
		return b;
	}
	
	/**
	 * 判断指定的字段是否为空。 
	 * @param fieldname
	 * @param obj
	 * @return
	 */

	public static boolean HasEmptyField(String []fieldname, Object obj)
	{
		boolean b =false;
		List<HashMap> ll = ClassUtil.getFiledsInfo(obj);
		for(HashMap mp : ll)
		{
			String sName = (String)mp.get("name");
			for(String fname : fieldname)
			{
				if(sName.equals(fname))
				{
					if(mp.get("value") ==null)
						return true;
					String stype = (String)mp.get("type");
					//log.info("==========>>>"+stype );
					if("class java.lang.String".equals(stype))
					{
						
						String sval = (String)mp.get("value");
						sval = sval.trim();
						//log.info(stype +"\t" +sval );
						if("".equals(sval))
							return true;
						
					}
				}
				
			}
			
		}
		
		
		return b;
	}


	
	/**
	 * 根据属性名获取属性值
	 * */
    public static  Object getFieldValueByName(String fieldName, Object o) {
        try {  
            String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "get" + firstLetter + fieldName.substring(1);  
            Method method = o.getClass().getMethod(getter, new Class[] {});  
            Object value = method.invoke(o, new Object[] {});  
            return value;  
        } catch (Exception e) {  
        	e.printStackTrace();
        	return null;  
        }  
    } 
    
    /**
     * 获取属性名数组
     * */
    public static   String[] getFiledName(Object o){
    	Field[] fields=o.getClass().getDeclaredFields();
       	String[] fieldNames=new String[fields.length];
    	for(int i=0;i<fields.length;i++){
    		System.out.println(fields[i].getType());
    		fieldNames[i]=fields[i].getName();
    	}
    	return fieldNames;
    }
    
    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * */
    public static  List getFiledsInfo(Object o){
    	Field[] fields=o.getClass().getDeclaredFields();
       	String[] fieldNames=new String[fields.length];
       	List list = new ArrayList();
       	Map infoMap=null;
    	for(int i=0;i<fields.length;i++){
    		infoMap = new HashMap();
    		infoMap.put("type", fields[i].getType().toString());
    		infoMap.put("name", fields[i].getName());
    		infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
    		list.add(infoMap);
    	}
    	return list;
    }
    
    /**
     * 获取对象的所有属性值，返回一个对象数组
     * */
    public static  Object[] getFiledValues(Object o){
    	String[] fieldNames=getFiledName(o);
    	Object[] value=new Object[fieldNames.length];
    	for(int i=0;i<fieldNames.length;i++){
    		value[i]=getFieldValueByName(fieldNames[i], o);
    	}
    	return value;
    }	

}

package com.lanqiao.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjyou
 * @date 2014-6-15 上午10:14:21
 * @func 数组工具类
 */
public class ArrayUtil {

	/**
	 * 两个集合取差
	 * 获取oList中没有，nList有的元素集合
	 * @param <T>
	 * @param oList
	 * @param nList
	 * @return
	 */
	public static <T> List<T> getDifference(List<T> oList, List<T> nList) {
		List<T> list3 = new ArrayList<T>();
		if((oList!=null && oList.size()>0) && (nList!=null && nList.size()>0)) {
			if(oList!=null && oList.size()>0) {
				if(nList!=null && nList.size()>0) {
					for (T t : nList) {
						if (!oList.contains(t)) {
							list3.add(t);
						}
					}
					return list3;
				} else {
					return oList;
				}
			} else {
				if(nList!=null && nList.size()>0) {
					return nList;
				}
			}
		} else {
			return null;
		}
		return null;
	}
	
	/**
	 * 两个集合取交集
	 * @param <T>
	 * @param oList
	 * @param nList
	 * @return
	 */
	public static <T> List<T> getCommon(List<T> oList, List<T> nList) {
		List<T> list3 = new ArrayList<T>();
		if((oList!=null && oList.size()>0) && (nList!=null && nList.size()>0)) {
			if(oList==null){
				return nList;
			}else if(nList==null){
				return oList;
			} else {
				for (T t : nList) {
					if (oList.contains(t)) {
						list3.add(t);
					}
				}
				return list3;
			}
		} else {
			return null;
		}
		
	}

    /**
     * 添加两个集合的并集
     * @param oList
     * @param nList
     * @param <T>
     * @return
     */
	public static <T> List<T> getTotalList(List<T> oList, List<T> nList) {
		if(oList==null) {
			return nList;
		} else if(nList==null) {
			return oList;
		} else {
			for (T t : nList) {
				oList.add(t);
			}
			return oList;
		}
		
	}
}

package com.lanqiao.common;

import java.util.ArrayList;

import com.alibaba.druid.util.StringUtils;
import com.lanqiao.model.SysFunction;

public class TLDUtils
{
	
	/**
	 * 判断在原字符stringarr (逗号分隔的字符), 是否含有某一字符串
	 * @param stringarr
	 * @param instr
	 * @return
	 */
	public static boolean ifIn(String stringarr, String instr)
	{
	//	System.err.println ("ifIn .....");
		boolean b =false;
		if(StringUtils.isEmpty(stringarr) || StringUtils.isEmpty(instr))
			return false;
		String stra[] = stringarr.split(",");
		for(String s: stra)
		{
			if(s.equals(instr))
				return true;
		}
		return b;
	}
	

	private static int [] parseIntarray(String s)
	{
		ArrayList al = new ArrayList();
		String stra [] = s.split(",");
		int np [] = new int[stra.length];
		for(int i=0;i<np.length;i++)
		{
			String str = stra[i];
			if(str ==null || "".equals(str)) continue;
			np[i] = Integer.parseInt(str);
		}
		return np;
	}
	/**
	 * 通过jstl 判断是否具有某一种,多种权限. 
	 * 如:
	 * <c:if test="${lq:hasRight(sessionScope.loginuser,"1,2,3") }">
  		 有权限, 显示....
		</c:if>

	 * @param user
	 * @param param
	 * @return
	 */
	public static boolean HasRight(com.lanqiao.common.SessionUser sessuser,int fid)
	{
		boolean b =false;
		
		SessionUser user = (SessionUser) sessuser;
		
		if(user !=null && user.getSysfunctions() !=null )
		{
			for(SysFunction sf : user.getSysfunctions())
			{
				
					if(sf.getFunctionid() == fid)
					{
						b =true;
						break;
					}
				
			}
			
		}
		return b;
	}
	
	 
	 
	 
}

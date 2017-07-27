package com.lanqiao.common;

public class LQtokenkey implements java.io.Serializable
{
	public String clientip;
	public String tokenkey;
	public int userid;
	public String toString()
	{
		
		return "tokenkey=" + tokenkey  +"\tclientip=" + clientip +"\tuserid="+userid;
	}
}

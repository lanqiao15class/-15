/*
 * File name:          SSOResult.java
 * Copyright@Handkoo (China)
 * Editor:           JDK1.6.32
 */
package com.lanqiao.common;

/**
 * TODO: File comments
 * <p>
 * <p>
 * Author:           卢锡文
 * <p>
 * Date:           2017年5月3日
 * <p>
 * Time:           下午1:38:55
 * <p>
 * Director:        卢锡文
 * <p>
 * <p>
 */
public class SSOResult {
	Integer resultcode;
	String userid;
	String resultmsg;
	String logintel;
	String realname;
	String loginemail;
	Integer usertype;

	public Integer getResultcode() {
		return resultcode;
	}

	public void setResultcode(Integer resultcode) {
		this.resultcode = resultcode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getResultmsg() {
		return resultmsg;
	}

	public void setResultmsg(String resultmsg) {
		this.resultmsg = resultmsg;
	}

	public String getLogintel() {
		return logintel;
	}

	public void setLogintel(String logintel) {
		this.logintel = logintel;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getLoginemail() {
		return loginemail;
	}

	public void setLoginemail(String loginemail) {
		this.loginemail = loginemail;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

}

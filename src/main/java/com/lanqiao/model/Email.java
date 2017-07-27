package com.lanqiao.model;
/**
 * 
* 项目名称:lqzp
* 类名称： Email
* 类描述:邮箱
* 创建人: ZhouZhiHua
* 创建时间:2016年8月30日 下午2:12:00 
* 修改人： 
* 修改时间：2016年8月30日 下午2:12:00
* 修改备注:
 */
public class Email {
	private String verificationCode;//验证码
	private String email;//邮箱

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

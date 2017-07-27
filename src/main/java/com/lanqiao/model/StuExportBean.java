package com.lanqiao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author zjyou
 * @date 2016年12月9日 上午11:18:03
 * @desc 学生信息导出实体类
 */
public class StuExportBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 456656L;
	
	private int no;//序号
	private String stuNo;//学员编码
	private String stuName;//学生姓名
	private String idCard;//身份证号
	private String univName;//学校名称
	private String inspectorTeaName;//区域总监
	private String invTeaName;//招生经理
	private String advisorTeaName;//招生顾问
	private String entryFeePayStatus;//报名费缴纳状态
	private java.math.BigDecimal entryFeeFactMoney;//报名应收免金额
	private BigDecimal entryFeeCurPayMoney;//报名费实收金额
	private BigDecimal entryFeeFavourMoney;//报名费减免金额
	private Date payTime;//报名费缴纳时间
	private String payeeUserName;//经办人
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getUnivName() {
		return univName;
	}
	public void setUnivName(String univName) {
		this.univName = univName;
	}
	public String getInspectorTeaName() {
		return inspectorTeaName;
	}
	public void setInspectorTeaName(String inspectorTeaName) {
		this.inspectorTeaName = inspectorTeaName;
	}
	public String getInvTeaName() {
		return invTeaName;
	}
	public void setInvTeaName(String invTeaName) {
		this.invTeaName = invTeaName;
	}
	public String getAdvisorTeaName() {
		return advisorTeaName;
	}
	public void setAdvisorTeaName(String advisorTeaName) {
		this.advisorTeaName = advisorTeaName;
	}
	public String getEntryFeePayStatus() {
		return entryFeePayStatus;
	}
	public void setEntryFeePayStatus(String entryFeePayStatus) {
		this.entryFeePayStatus = entryFeePayStatus;
	}
	
	
	public java.math.BigDecimal getEntryFeeFactMoney()
	{
		return entryFeeFactMoney;
	}
	public void setEntryFeeFactMoney(java.math.BigDecimal entryFeeFactMoney)
	{
		this.entryFeeFactMoney = entryFeeFactMoney;
	}
	public BigDecimal getEntryFeeCurPayMoney()
	{
		return entryFeeCurPayMoney;
	}
	public void setEntryFeeCurPayMoney(BigDecimal entryFeeCurPayMoney)
	{
		this.entryFeeCurPayMoney = entryFeeCurPayMoney;
	}
	public BigDecimal getEntryFeeFavourMoney()
	{
		return entryFeeFavourMoney;
	}
	public void setEntryFeeFavourMoney(BigDecimal entryFeeFavourMoney)
	{
		this.entryFeeFavourMoney = entryFeeFavourMoney;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getPayeeUserName() {
		return payeeUserName;
	}
	public void setPayeeUserName(String payeeUserName) {
		this.payeeUserName = payeeUserName;
	}
	public String toString()
	{
		
		return ToStringBuilder.reflectionToString(this);
				// return BeanUtils.describe(oo).toString();
	}
}
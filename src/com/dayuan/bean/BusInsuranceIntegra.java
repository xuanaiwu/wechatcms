package com.dayuan.bean;

import java.util.Date;

public class BusInsuranceIntegra{
	private Integer id;
	private String loanPerson;
	private String mortgagePerson;
	private String creditPerson;
	private String otherHappening;
	private Date createTime;
	private Date updateTime;
	private String uId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoanPerson() {
		return loanPerson;
	}
	public void setLoanPerson(String loanPerson) {
		this.loanPerson = loanPerson;
	}
	public String getMortgagePerson() {
		return mortgagePerson;
	}
	public void setMortgagePerson(String mortgagePerson) {
		this.mortgagePerson = mortgagePerson;
	}
	public String getCreditPerson() {
		return creditPerson;
	}
	public void setCreditPerson(String creditPerson) {
		this.creditPerson = creditPerson;
	}
	public String getOtherHappening() {
		return otherHappening;
	}
	public void setOtherHappening(String otherHappening) {
		this.otherHappening = otherHappening;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
}

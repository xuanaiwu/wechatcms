package com.dayuan.model;

import java.util.Date;

public class BusFileModel extends BaseModel{
	private Integer id;
	private String lName;
	private String lIdCard;
	private String lTelPhone;
	private String lStatus;
	private String lUserName;
	private String lUId;
	private Date createTime;
	private Date updateTime;
	
	
	
	public String getlUserName() {
		return lUserName;
	}
	public void setlUserName(String lUserName) {
		this.lUserName = lUserName;
	}
	public String getlUId() {
		return lUId;
	}
	public void setlUId(String lUId) {
		this.lUId = lUId;
	}
	public String getlStatus() {
		return lStatus;
	}
	public void setlStatus(String lStatus) {
		this.lStatus = lStatus;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getlIdCard() {
		return lIdCard;
	}
	public void setlIdCard(String lIdCard) {
		this.lIdCard = lIdCard;
	}
	public String getlTelPhone() {
		return lTelPhone;
	}
	public void setlTelPhone(String lTelPhone) {
		this.lTelPhone = lTelPhone;
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

}

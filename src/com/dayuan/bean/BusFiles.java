package com.dayuan.bean;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

import com.dayuan.utils.DateUtil;
import com.dayuan.utils.json.JSONStringObject;

public class BusFiles {
	
	private Integer id;
	private String lName;
	private String lIdCard;
	private String lTelPhone;
	private String lStatus;
	private String lUserName;
	private String lUId;
	private Date createTime;
	private Date updateTime;
	
	//转换时间格式
	private String createTime2;
	private String updateTime2;
	
	public Integer getId() {
		return id;
	}
	public String getCreateTime2() {
		return createTime2;
	}
	public void setCreateTime2(String createTime2) {
		this.createTime2 = createTime2;
	}
	public String getUpdateTime2() {
		return updateTime2;
	}
	public void setUpdateTime2(String updateTime2) {
		this.updateTime2 = updateTime2;
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
	public String getlStatus() {
		return lStatus;
	}
	public void setlStatus(String lStatus) {
		this.lStatus = lStatus;
	}
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

package com.dayuan.model;

import java.util.Date;

public class SysUserModel extends BaseModel {
	

	
	
    private Integer superAdmin;//超级管理员
	
	private String kind;//用户类型
	private String gender;//用户性别
	private Date birthday; //生日
	private String oId;//所属行政机构id
	private String oName;//所属行政机构名称
	private String addr;//详细地址
	private String phone;//电话
	private String memo;//备注
	private String pId;//所属检测点ID
	private String pName;//所属检测点名称
	private Date uDate;//跟新时间
	
	
	
	
	public Integer getSuperAdmin() {
		return superAdmin;
	}
	public void setSuperAdmin(Integer superAdmin) {
		this.superAdmin = superAdmin;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getOId() {
		return oId;
	}
	public void setOId(String id) {
		oId = id;
	}
	public String getOName() {
		return oName;
	}
	public void setOName(String name) {
		oName = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPId() {
		return pId;
	}
	public void setPId(String id) {
		pId = id;
	}
	public String getPName() {
		return pName;
	}
	public void setPName(String name) {
		pName = name;
	}
	public Date getUDate() {
		return uDate;
	}
	public void setUDate(Date date) {
		uDate = date;
	}
	
}
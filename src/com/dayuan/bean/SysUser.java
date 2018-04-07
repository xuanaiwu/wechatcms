package com.dayuan.bean;



import java.util.Date;


public class SysUser extends BaseBean {
	
		private Integer id;//   id主键	private String email;//   邮箱也是登录帐号	private String pwd;//   登录密码	private String nickName;//   昵称	private Integer state;//   状态 0=可用,1=禁用	private Integer loginCount;//   登录总次数	private java.sql.Timestamp loginTime;//   最后登录时间	private Integer deleted;//   删除状态 0=未删除,1=已删除	private java.sql.Timestamp createTime;//   创建时间	private java.sql.Timestamp updateTime;//   修改时间	private Integer createBy;//   创建人	private Integer updateBy;//   修改人
	
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
	
	
	
	
	
	
	
	private String roleStr;//用户权限, 按","区分
	
	private Integer excelAuth;//excel权限
	
	
		public String getRoleStr() {
		return roleStr;
	}
	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr;
	}
	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getEmail() {	    return this.email;	}	public void setEmail(String email) {	    this.email=email;	}	public String getPwd() {	    return this.pwd;	}	public void setPwd(String pwd) {	    this.pwd=pwd;	}	public String getNickName() {	    return this.nickName;	}	public void setNickName(String nickName) {	    this.nickName=nickName;	}	public Integer getState() {	    return this.state;	}	public void setState(Integer state) {	    this.state=state;	}	public Integer getLoginCount() {	    return this.loginCount;	}	public void setLoginCount(Integer loginCount) {	    this.loginCount=loginCount;	}	public java.sql.Timestamp getLoginTime() {	    return this.loginTime;	}	public void setLoginTime(java.sql.Timestamp loginTime) {	    this.loginTime=loginTime;	}	public Integer getDeleted() {	    return this.deleted;	}	public void setDeleted(Integer deleted) {	    this.deleted=deleted;	}	public java.sql.Timestamp getCreateTime() {	    return this.createTime;	}	public void setCreateTime(java.sql.Timestamp createTime) {	    this.createTime=createTime;	}	public java.sql.Timestamp getUpdateTime() {	    return this.updateTime;	}	public void setUpdateTime(java.sql.Timestamp updateTime) {	    this.updateTime=updateTime;	}	public Integer getCreateBy() {	    return this.createBy;	}	public void setCreateBy(Integer createBy) {	    this.createBy=createBy;	}	public Integer getUpdateBy() {	    return this.updateBy;	}	public void setUpdateBy(Integer updateBy) {	    this.updateBy=updateBy;	}
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
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		
		this.birthday=birthday;
	}
	public Integer getExcelAuth() {
		return excelAuth;
	}
	public void setExcelAuth(Integer excelAuth) {
		this.excelAuth = excelAuth;
	}
	
	
	
	
}

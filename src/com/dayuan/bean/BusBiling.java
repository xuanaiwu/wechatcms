package com.dayuan.bean;

import java.util.Date;

/**贷后台帐*/
public class BusBiling {
	private Integer id;
	private Integer bid;
	private Date checkDate;
	private String creditorIfNormal;
	private String guarantorIfNormal;
	private String cloudLoanIfWarning;
	private String shopOperation;
	private String otherNeedToExplained;
	private String loanCardNumber;
	private Date creditEndDate;
	private String loanAccount;
	private String loanTerm;
	
	/**扩展属性，更新时临时保存主键id*/
	private Integer bilingTempId;
	
	
	
	public Integer getBilingTempId() {
		return bilingTempId;
	}
	public void setBilingTempId(Integer bilingTempId) {
		this.bilingTempId = bilingTempId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public Date getCheckDate() {
		return checkDate!=null?checkDate:new Date();
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public String getCreditorIfNormal() {
		return creditorIfNormal!=null?creditorIfNormal:"";
	}
	public void setCreditorIfNormal(String creditorIfNormal) {
		this.creditorIfNormal = creditorIfNormal;
	}
	public String getGuarantorIfNormal() {
		return guarantorIfNormal!=null?guarantorIfNormal:"";
	}
	public void setGuarantorIfNormal(String guarantorIfNormal) {
		this.guarantorIfNormal = guarantorIfNormal;
	}
	public String getCloudLoanIfWarning() {
		return cloudLoanIfWarning!=null?cloudLoanIfWarning:"";
	}
	public void setCloudLoanIfWarning(String cloudLoanIfWarning) {
		this.cloudLoanIfWarning = cloudLoanIfWarning;
	}
	public String getShopOperation() {
		return shopOperation!=null?shopOperation:"";
	}
	public void setShopOperation(String shopOperation) {
		this.shopOperation = shopOperation;
	}
	public String getOtherNeedToExplained() {
		return otherNeedToExplained!=null?otherNeedToExplained:"";
	}
	public void setOtherNeedToExplained(String otherNeedToExplained) {
		this.otherNeedToExplained = otherNeedToExplained;
	}
	public String getLoanCardNumber() {
		return loanCardNumber!=null?loanCardNumber:"";
	}
	public void setLoanCardNumber(String loanCardNumber) {
		this.loanCardNumber = loanCardNumber;
	}
	public Date getCreditEndDate() {
		return creditEndDate!=null?creditEndDate:new Date();
	}
	public void setCreditEndDate(Date creditEndDate) {
		this.creditEndDate = creditEndDate;
	}
	public String getLoanAccount() {
		return loanAccount!=null?loanAccount:"";
	}
	public void setLoanAccount(String loanAccount) {
		this.loanAccount = loanAccount;
	}
	public String getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}
	
	
	
	
	
	
	

}

package com.dayuan.bean;

import java.util.Date;

/**放款*/
public class BusLending {
	private Integer id;
	private Integer bid;
	private String guaranteeCompany1;
	private String guaranteeAddress1;
	private String guaranteeLegal1;
	private String numberOfShareHolders1;
	private String shareHoldersName1;
	private String guaranteeCompany2;
	private String guaranteeAddress2;
	private String guaranteeLegal2;
	private String numberOfShareHolders2;
	private String shareHoldersName2;
	private String loanAmount;
	private String openingQuota;
	private String creditTerm;
	private Date startTerm;
	private Date endTerm;
	
	
	/**扩展属性，更新时临时保存主键id*/	
	private Integer lendingTempId;

	public Integer getLendingTempId() {
		return lendingTempId;
	}
	public void setLendingTempId(Integer lendingTempId) {
		this.lendingTempId = lendingTempId;
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
	public String getGuaranteeCompany1() {
		return guaranteeCompany1;
	}
	public void setGuaranteeCompany1(String guaranteeCompany1) {
		this.guaranteeCompany1 = guaranteeCompany1;
	}
	public String getGuaranteeAddress1() {
		return guaranteeAddress1;
	}
	public void setGuaranteeAddress1(String guaranteeAddress1) {
		this.guaranteeAddress1 = guaranteeAddress1;
	}
	public String getGuaranteeLegal1() {
		return guaranteeLegal1;
	}
	public void setGuaranteeLegal1(String guaranteeLegal1) {
		this.guaranteeLegal1 = guaranteeLegal1;
	}
	public String getNumberOfShareHolders1() {
		return numberOfShareHolders1;
	}
	public void setNumberOfShareHolders1(String numberOfShareHolders1) {
		this.numberOfShareHolders1 = numberOfShareHolders1;
	}
	public String getShareHoldersName1() {
		return shareHoldersName1;
	}
	public void setShareHoldersName1(String shareHoldersName1) {
		this.shareHoldersName1 = shareHoldersName1;
	}
	public String getGuaranteeCompany2() {
		return guaranteeCompany2;
	}
	public void setGuaranteeCompany2(String guaranteeCompany2) {
		this.guaranteeCompany2 = guaranteeCompany2;
	}
	public String getGuaranteeAddress2() {
		return guaranteeAddress2;
	}
	public void setGuaranteeAddress2(String guaranteeAddress2) {
		this.guaranteeAddress2 = guaranteeAddress2;
	}
	public String getGuaranteeLegal2() {
		return guaranteeLegal2;
	}
	public void setGuaranteeLegal2(String guaranteeLegal2) {
		this.guaranteeLegal2 = guaranteeLegal2;
	}
	public String getNumberOfShareHolders2() {
		return numberOfShareHolders2;
	}
	public void setNumberOfShareHolders2(String numberOfShareHolders2) {
		this.numberOfShareHolders2 = numberOfShareHolders2;
	}
	public String getShareHoldersName2() {
		return shareHoldersName2;
	}
	public void setShareHoldersName2(String shareHoldersName2) {
		this.shareHoldersName2 = shareHoldersName2;
	}
	public String getLoanAmount() {
		return loanAmount!=null?loanAmount:"";
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getOpeningQuota() {
		return openingQuota!=null?loanAmount:"";
	}
	public void setOpeningQuota(String openingQuota) {
		this.openingQuota = openingQuota;
	}
	public String getCreditTerm() {
		return creditTerm;
	}
	public void setCreditTerm(String creditTerm) {
		this.creditTerm = creditTerm;
	}
	public Date getStartTerm() {
		return startTerm;
	}
	public void setStartTerm(Date startTerm) {
		this.startTerm = startTerm;
	}
	public Date getEndTerm() {
		return endTerm;
	}
	public void setEndTerm(Date endTerm) {
		this.endTerm = endTerm;
	}
	
	
	
	
	
	
	
	
	

}
